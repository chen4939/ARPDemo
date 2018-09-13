package com.bftv.arpinfo

import android.app.Application
import com.abooc.widget.Toast

/**
 *
 * @author Junpu
 * @time 2018/9/3 11:28
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Toast.init(this)
    }

}