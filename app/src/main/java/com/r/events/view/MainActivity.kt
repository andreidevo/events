package com.r.events.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.fxn.BubbleTabBar
import com.fxn.OnBubbleClickListener
import com.r.events.R
import com.r.events.ViewModel
import com.r.events.model.PagerAdapt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //создаем связь
        val viewModel = ViewModel()

        //передаем контекст в модель
        viewModel.getContext(this)

        //меняем текст
        val text = findViewById<TextView>(R.id.text)
        viewModel.editText(text, "Мероприятия")

        //отправим рандомное уведомление
        //viewModel.pushNotification("1 окт 2019", "Программирование", null)
        viewModel.getDataFromPage()


    }
}
