package com.jcchrun.albums.presentation.albums.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.jcchrun.album.core.extensions.isPortrait
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.presentation.R
import com.jcchrun.albums.presentation.albums.ui.MarginItemDecoration
import com.jcchrun.albums.presentation.databinding.FragmentAlbumDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsFragment: Fragment(R.layout.fragment_album_detail) {

    private val albumDetailsViewModel: AlbumDetailsViewModel by viewModels()

    private val albumDescendantAdapter = AlbumDescendantAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bindings = FragmentAlbumDetailBinding.bind(view)

        arguments?.let {
            val safeArgs = AlbumDetailsFragmentArgs.fromBundle(it)
            val albumId = safeArgs.albumId

            setToolbar(view.context, bindings, safeArgs.albumTitle)
            bindings.albumImage.load(safeArgs.albumImageUrl) {
                listener(
                    onStart = {},
                    onCancel = {},
                    onSuccess = {_, metadata -> startPostponedEnterTransition() },
                    onError = {_, metadata -> startPostponedEnterTransition() }
                )
            }

            bindings.descendantList.apply {
                adapter = albumDescendantAdapter
                layoutManager = LinearLayoutManager(view.context)
                addItemDecoration(
                    MarginItemDecoration(
                        1,
                        resources.getDimension(R.dimen.album_root_item_margin).toInt()
                    )
                )
            }

            observeAlbumList()
            loadData(albumId)
        }
        setupAppBar(view.context, bindings.appBar)
    }

    private fun setToolbar(context: Context, bindings: FragmentAlbumDetailBinding, toolbarTitle: String) {
        bindings.toolbar.title = toolbarTitle
        val arrowDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_back_24)
        arrowDrawable?.setTint(ContextCompat.getColor(context, R.color.white))
        bindings.toolbar.apply {
            navigationIcon = arrowDrawable
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
    }

    private fun observeAlbumList() {
        albumDetailsViewModel.albumsLiveData.observe(viewLifecycleOwner, Observer<List<Album>> {
            albumDescendantAdapter.submitList(it)
        })
    }

    private fun loadData(albumId: Int) {
        albumDetailsViewModel.getAlbumsById(albumId)
    }

    private fun setupAppBar(context: Context, appBarLayout: AppBarLayout) {
        if (!context.isPortrait) {
            appBarLayout.setExpanded(false)
        }
    }
}