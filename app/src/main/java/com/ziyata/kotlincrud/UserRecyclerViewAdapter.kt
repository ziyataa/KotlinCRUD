package com.ziyata.kotlincrud

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.ziyata.kotlincrud.dummy.DummyContent.DummyItem
import com.ziyata.kotlincrud.model.User
import kotlinx.android.synthetic.main.fragment_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class UserRecyclerViewAdapter(
        private val mValues: List<User>?,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {


    private val mOnClickListener: View.OnClickListener
    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as User
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues?.get(position)
        holder.mId.text = item?.id.toString()
        holder.mNama.text = item?.nama

        with(holder.mView){
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues?.size?:0


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mId: TextView = mView.tv_id
        val mNama: TextView = mView.tv_name
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: User?)
    }
}
