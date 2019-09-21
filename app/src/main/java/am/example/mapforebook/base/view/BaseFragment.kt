package am.example.mapforebook.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * Created by Ara Hakobyan on 9/21/19.
 * company IDT
 */
abstract class BaseFragment<A: ViewModel, F: ViewModel> : Fragment() {

    lateinit var activityViewModel: A
    lateinit var fragmentViewModel: F

    var baseActivity: BaseActivity<*>? = null
        get() = requireActivity() as BaseActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initFragmentViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(onCreateView(), null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun setupView()

    abstract fun initFragmentViewModel()

    abstract fun initActivityViewModel()
}