package com.app.hospital.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hospital.data.model.Department
import com.app.hospital.databinding.ItemDepartmentBinding

class DepartmentAdapter(
    private val onClick: (Department) -> Unit
) : RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder>() {

    private val departments = mutableListOf<Department>()

    fun submitList(list: List<Department>) {
        departments.clear()
        departments.addAll(list)
        notifyDataSetChanged()
    }

    inner class DepartmentViewHolder(
        private val binding: ItemDepartmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Department) {

            binding.tvDepartmentName.text = item.name
            binding.tvDepartmentDescription.text = item.description

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {

        val binding = ItemDepartmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DepartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(departments[position])
    }

    override fun getItemCount(): Int {
        return departments.size
    }
}