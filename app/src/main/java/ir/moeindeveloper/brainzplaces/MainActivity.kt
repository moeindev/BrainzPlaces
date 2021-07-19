package ir.moeindeveloper.brainzplaces

import dagger.hilt.android.AndroidEntryPoint
import ir.moeindeveloper.brainzplaces.core.platform.activity.BaseActivity
import ir.moeindeveloper.brainzplaces.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun layoutRes(): Int = R.layout.activity_main
}