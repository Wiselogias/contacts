package com.wiselogia.contacts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val onCLick: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    var contacts = listOf<Contact>()
        set(value) {
            Log.println(Log.INFO, "rrr", value.toString())
            val res = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize() = field.size

                override fun getNewListSize() = value.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    field[oldItemPosition] == value[newItemPosition]

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    field[oldItemPosition] == value[newItemPosition]

            })
            field = value
            res.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactAdapter.ContactHolder {
        return ContactHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactHolder, position: Int) =
        holder.bind(contacts[position], onCLick)

    override fun getItemCount(): Int = contacts.size


    inner class ContactHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        private val nameView = root.findViewById<TextView>(R.id.contactName)
        private val numberView = root.findViewById<TextView>(R.id.contactNumber)

        fun bind(contact: Contact, onCLick: (Contact) -> Unit) {
            nameView.text = contact.name
            numberView.text = contact.number
            root.setOnClickListener {
                onCLick(contact)
            }
        }
    }
}