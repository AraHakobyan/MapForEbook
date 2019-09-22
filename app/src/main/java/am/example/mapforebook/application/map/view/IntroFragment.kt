package am.example.mapforebook.application.map.view

import am.example.mapforebook.R
import am.example.mapforebook.application.base.view.BaseFragment
import am.example.mapforebook.application.map.viewmodel.MapActivityViewModel
import androidx.lifecycle.ViewModel

/**
 * Created by Ara Hakobyan on 9/23/19.
 * company IDT
 */
class IntroFragment : BaseFragment<MapActivityViewModel, ViewModel>() {
    override fun onCreateView(): Int = R.layout.intro_fragment_layout

    override fun setupView() = Unit

    override fun initFragmentViewModel() = Unit

    override fun initActivityViewModel() = Unit

}