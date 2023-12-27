package com.example.pinapphomework.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.pinapphomework.Convertor.ConvertorActivity

class DeleteConfirmationDialogFragment : DialogFragment() {
    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): DeleteConfirmationDialogFragment {
            val fragment = DeleteConfirmationDialogFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Delete Confirmation")
            .setMessage("Are you sure you want to delete this currency?")
            .setPositiveButton("Yes") { _, _ ->
                // Notify the activity about the confirmation
                val position = arguments?.getInt(ARG_POSITION, -1) ?: -1
                if (position != -1 && activity is ConvertorActivity) {
                    val convertorActivity = activity as ConvertorActivity
                    convertorActivity.adapterRecyclerView.deleteItem(position)
                    convertorActivity.adapterRecyclerView.notifyItemRemoved(position)
            }}
            .setNegativeButton("No", null)
            .create()
    }
}