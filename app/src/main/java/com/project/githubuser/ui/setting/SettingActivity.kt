package com.project.githubuser.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.project.githubuser.data.datastore.SettingPreferences
import com.project.githubuser.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {


    private val viewModel by viewModels<SettingViewModel>{
        SettingViewModel.Factory(SettingPreferences(this))
    }

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*
        button switch on off
         */
        getThemeButton()

        binding.swNightMode.setOnCheckedChangeListener{_, checked ->
            viewModel.saveTheme(checked)
        }
    }


    /*
    Pengecekan tema
     */
    private fun getThemeButton(){
        viewModel.getTheme().observe(this){isModeMalam ->
            val textMode = if (isModeMalam) "Mode Malam" else "Mode Siang"
            binding.swNightMode.text = textMode
            binding.swNightMode.isChecked = isModeMalam

            val modeMalam = if (isModeMalam) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(modeMalam)
        }
    }
}