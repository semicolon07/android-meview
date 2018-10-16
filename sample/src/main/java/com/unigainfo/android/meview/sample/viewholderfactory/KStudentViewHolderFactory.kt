package com.unigainfo.android.meview.sample.viewholderfactory

import android.view.View
import com.unigainfo.android.meview.adapter.ItemViewHolder
import com.unigainfo.android.meview.adapter.ViewHolderFactory
import com.unigainfo.android.meview.sample.R
import com.unigainfo.android.meview.sample.model.Student

/**
 * Created by Semicolon07 on 7/29/2017 AD.
 */
class KStudentViewHolderFactory : ViewHolderFactory<Student, KStudentViewHolderFactory.ViewHolder>(){
    override fun getLayoutRes() = R.layout.list_item_student

    override fun bindData(viewHolder: ViewHolder?, item: Student?, position: Int) {

    }


    class ViewHolder(itemView: View) : ItemViewHolder(itemView){

    }
}