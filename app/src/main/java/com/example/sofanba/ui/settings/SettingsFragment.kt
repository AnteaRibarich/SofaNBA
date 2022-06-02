package com.example.sofanba.ui.settings

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sofanba.AboutActivity
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentSettingsBinding
import com.example.sofanba.databinding.PopupLayoutBinding
import com.example.sofanba.databinding.SnackbarLayoutBinding
import com.example.sofanba.model.NBAhelper
import com.example.sofanba.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var dialog: Dialog
    private lateinit var snackBar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dialog = createDialog()

        binding.buttonMore.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            context?.startActivity(intent)
        }

        binding.buttonClear.setOnClickListener {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        binding.toolbar.textToolbarTitle.text = getString(R.string.title_settings)

        return root
    }

    // setup custom dialog
    private fun createDialog(): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = this.layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val bindingDialog = PopupLayoutBinding.inflate(inflater)
            bindingDialog.buttonCancel.setOnClickListener {
                dialog.dismiss()
            }
            bindingDialog.buttonClear.setOnClickListener {
                context?.let { it1 -> viewModel.deleteAllFavouritePlayers(it1) }
                context?.let { it1 -> viewModel.deleteAllFavouriteTeams(it1) }
                dialog.dismiss()
                // snackbar
                if (!this::snackBar.isInitialized) {
                    snackBar = createSnackBar(binding.root)
                    snackBar.setBackgroundTint(Color.TRANSPARENT)
                }
                snackBar.show()
            }

            builder.setView(bindingDialog.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // setup snackbar
    @SuppressLint("RestrictedApi")
    private fun createSnackBar(view: View): Snackbar {
        val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)

        val inflater = this.layoutInflater
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val binding = SnackbarLayoutBinding.inflate(inflater)
        binding.imageClose.setOnClickListener {
            snackbar.dismiss()
        }

        val snackBarView = snackbar.view as SnackbarLayout
        val bottomMargin = 64
        val parentParams = snackBarView.layoutParams as FrameLayout.LayoutParams
        context?.let { parentParams.setMargins(0, 0, 0, NBAhelper.dpsToPixels(bottomMargin, it)) }
        snackBarView.layoutParams = parentParams

        snackBarView.addView(binding.root, 0)
        snackBarView.elevation = 0F
        return snackbar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
