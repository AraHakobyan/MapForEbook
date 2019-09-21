package am.example.mapforebook.base.view

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
abstract class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivityViewModel()
        super.onCreate(savedInstanceState)
        setContentView(onCreateView())
        setupView()
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun setupView()

    abstract fun initActivityViewModel()

}