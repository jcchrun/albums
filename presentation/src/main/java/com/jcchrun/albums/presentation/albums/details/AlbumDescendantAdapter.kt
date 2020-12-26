package com.jcchrun.albums.presentation.albums.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jcchrun.albums.models.Album
import com.jcchrun.albums.presentation.R
import com.jcchrun.albums.presentation.databinding.ItemAlbumDescendantBinding
import com.jcchrun.albums.presentation.databinding.ItemAlbumRootBinding

class AlbumDescendantAdapter(
) : ListAdapter<Album, AlbumDescendantAdapter.AlbumRootViewHolder>(ContentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumRootViewHolder {
        return AlbumRootViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_album_descendant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlbumRootViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindings.title.text = item.title
        holder.bindings.image.load(item.thumbnailUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder_image)
            listener(
                onStart = {},
                onCancel = {},
                onSuccess = {_, metadata -> },
                onError = {_, metadata -> holder.bindings.image.load(R.drawable.ic_placeholder_image) }
            )
        }
    }

    private class ContentDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean = oldItem == newItem
    }

    class AlbumRootViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val bindings = ItemAlbumDescendantBinding.bind(view)
    }
}