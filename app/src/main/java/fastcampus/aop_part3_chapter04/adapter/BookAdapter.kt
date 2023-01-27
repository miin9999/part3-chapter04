package fastcampus.aop_part3_chapter04.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop_part3_chapter04.databinding.ItemBookBinding
import fastcampus.aop_part3_chapter04.model.Book

class BookAdapter : ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {



    inner class BookItemViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(bookModel: Book){
            binding.titleTextView.text = bookModel.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])

    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Book>(){ // Book에 대한 판단
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                // oldItem 과 newItem 이 실제로 같은 아이템이냐 판단
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                // 그 안에 있는 컨텐츠들이 서로 같냐 판단
                return oldItem.id == newItem.id
            }
        }
    }

}