package fi.monad.asteroidradar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fi.monad.asteroidradar.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}