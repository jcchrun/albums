package com.jcchrun.albums.presentation.albums.list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.jcchrun.album.core.Constants
import com.jcchrun.album.core.extensions.isPortrait
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.models.Output
import com.jcchrun.albums.presentation.R
import com.jcchrun.albums.presentation.albums.ui.MarginItemDecoration
import com.jcchrun.albums.presentation.databinding.FragmentAlbumListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlbumRootListFragment : Fragment(R.layout.fragment_album_list), AlbumRootAdapter.Listener {

    private val albumRootListViewModel: AlbumRootListViewModel by viewModels()
    private val albumRootAdapter = AlbumRootAdapter(this)
    private var showShimmerCallback: (() -> Unit)? = null
    private var hideShimmerCallback: (() -> Unit)? = null

    override fun onItemClick(imageView: ImageView, album: Album) {
        ViewCompat.setTransitionName(imageView, getString(R.string.shared_transition_image))
        val extras = FragmentNavigatorExtras(
            imageView to "album_thumbnail"
        )
        findNavController().navigate(
            AlbumRootListFragmentDirections.nextAction(
                albumId = album.id,
                albumImageUrl = album.url,
                albumTitle = album.title
            ), extras
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bindings = FragmentAlbumListBinding.bind(view)

        showShimmerCallback = {
            bindings.shimmerRoot.shimmerView.visibility = View.VISIBLE
            bindings.shimmerRoot.shimmerFrameLayout.startShimmer()
            bindings.errorRoot.errorImage.visibility = View.GONE
            bindings.errorRoot.errorText.visibility = View.GONE
            bindings.errorRoot.errorButton.visibility = View.GONE
            bindings.albumList.visibility = View.GONE
        }

        hideShimmerCallback = {
            bindings.shimmerRoot.shimmerView.visibility = View.GONE
            bindings.shimmerRoot.shimmerFrameLayout.stopShimmer()
        }

        bindings.toolbar.title = getString(R.string.app_name)

        val gridLayoutColumns = getGridLayoutColumns(view.context)
        bindings.albumList.apply {
            adapter = albumRootAdapter
            layoutManager = createLayoutManager(view.context, gridLayoutColumns)
            addItemDecoration(
                MarginItemDecoration(
                    gridLayoutColumns,
                    resources.getDimension(R.dimen.album_root_item_margin).toInt()
                )
            )
        }

        observeAlbumList(bindings)
        loadData()
        setupAppBar(view.context, bindings.appBar)
    }

    private fun getGridLayoutColumns(context: Context): Int {
        return if (context.isPortrait) Constants.GRID_LAYOUT_COLUMN_PORTRAIT else Constants.GRID_LAYOUT_COLUMN_LANDSCAPE
    }

    private fun createLayoutManager(
        context: Context,
        gridLayoutColumns: Int
    ): RecyclerView.LayoutManager {
        val layoutManager = GridLayoutManager(context, gridLayoutColumns)
        layoutManager.orientation = RecyclerView.VERTICAL
        return layoutManager
    }

    private fun observeAlbumList(bindings: FragmentAlbumListBinding) {
        albumRootListViewModel.albumsRootListLiveData.observe(viewLifecycleOwner, {
            hideShimmerCallback?.invoke()

            bindings.errorRoot.errorImage.visibility = View.VISIBLE
            bindings.errorRoot.errorText.visibility = View.VISIBLE
            bindings.errorRoot.errorButton.visibility = View.VISIBLE
            bindings.albumList.visibility = View.GONE
            bindings.errorRoot.errorImage.setBackgroundResource(R.drawable.ic_empty)
            bindings.errorRoot.errorText.text = getString(R.string.empty_data)
            bindings.errorRoot.errorButton.setOnClickListener { loadData() }

            when (it) {
                is Output.Success -> {
                    if (it.result.isNotEmpty()) {
                        bindings.errorRoot.errorImage.visibility = View.GONE
                        bindings.errorRoot.errorText.visibility = View.GONE
                        bindings.errorRoot.errorButton.visibility = View.GONE
                        bindings.albumList.visibility = View.VISIBLE
                        albumRootAdapter.submitList(it.result)
                    } else {
                        showErrorLayout(bindings, R.drawable.ic_empty, R.string.empty_data)
                    }
                }
                is Output.Error -> {
                    if (it.errorCode == Output.Error.ERROR_CODE_NO_NETWORK) {
                        showErrorLayout(
                            bindings,
                            R.drawable.ic_connection_off,
                            R.string.error_no_connection
                        )
                    } else {
                        showErrorLayout(
                            bindings,
                            R.drawable.ic_error_unknown,
                            R.string.error_unknown
                        )
                    }
                }
            }
        })
    }

    private fun showErrorLayout(
        bindings: FragmentAlbumListBinding,
        errorImageDrawableId: Int,
        errorStringId: Int
    ) {
        bindings.errorRoot.errorImage.visibility = View.VISIBLE
        bindings.errorRoot.errorText.visibility = View.VISIBLE
        bindings.errorRoot.errorButton.visibility = View.VISIBLE
        bindings.albumList.visibility = View.GONE
        bindings.errorRoot.errorImage.setBackgroundResource(errorImageDrawableId)
        bindings.errorRoot.errorText.text = getString(errorStringId)
        bindings.errorRoot.errorButton.setOnClickListener { loadData() }
    }

    private fun loadData() {
        showShimmerCallback?.invoke()
        albumRootListViewModel.getAlbumsRootList()
    }

    private fun setupAppBar(context: Context, appBarLayout: AppBarLayout) {
        if (!context.isPortrait) {
            appBarLayout.setExpanded(false)
        }
    }
}