package com.example.todowaerapp.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.todowaerapp.R
import com.example.todowaerapp.databinding.ActivityMainBinding
import com.example.todowaerapp.model.Task
import com.example.todowaerapp.utils.ConfirmUtils
import com.example.todowaerapp.utils.Constants.ACTION_SEND
import com.example.todowaerapp.utils.TaskUtils

class MainActivity : AppCompatActivity() {

    private var mainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(mainBinding?.root)

        init()
    }

    private fun init(){
        initTask()
    }

    private fun updateTask(task: Task,action:Int){
        if(action==ACTION_SEND){
            TaskUtils.saveTask(task,this)
            ConfirmUtils.showSuccessMessage(getString(R.string.task_saved),this)
        }
    }

    private fun initTask() {
        mainBinding?.edtTask?.setOnEditorActionListener { textView, i, _ ->
            if (i==EditorInfo.IME_ACTION_SEND){
                val text = textView.text.toString()
                if (text.isNullOrEmpty()){
                    val task = Task(System.currentTimeMillis().toString(),text)
                    updateTask(task,ACTION_SEND)
                }
            }
            true
        }
    }
}