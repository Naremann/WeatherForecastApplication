package com.example.weatherforecastapplication.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
abstract class BaseFragment<DB:ViewDataBinding,VM:BaseViewModel<*>>:Fragment() {
    lateinit var viewDataBinding: DB
     lateinit var viewModel: VM
    private var alertDialog: AlertDialog?=null
    private lateinit var progressDialog : ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding=DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        viewModel=initViewModel()
        return getFragmentView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLayoutId()
    }

    fun observeToLiveData(){
        viewModel.messageLiveData.observe(viewLifecycleOwner) { msg ->
            showAlertDialog(msg, "Ok")
        }
        viewModel.showLoading.observe(viewLifecycleOwner) { loadingMessage ->
            if (loadingMessage == true) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

    }
    private fun showProgressDialog(){
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading....")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
    private fun hideProgressDialog(){
        progressDialog.dismiss()

    }

    private fun showAlertDialog(
        message:String, posActionName:String?=null,
        posActionListener: DialogInterface.OnClickListener?=null,
        negActionName : String?=null,
        negActionListener: DialogInterface.OnClickListener?=null,
        cancellable: Boolean = true
    ){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
        if(posActionName != null){
            builder.setPositiveButton(posActionName, posActionListener)
        }
        if(negActionName != null){
            builder.setNegativeButton(negActionName,negActionListener)
        }
        builder.setCancelable(cancellable)
        alertDialog = builder.show()
    }

    abstract fun getFragmentView(): View?

    abstract fun initViewModel(): VM

    abstract fun getLayoutId():Int
}