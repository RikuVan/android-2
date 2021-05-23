package fi.monad.asteroidradar.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fi.monad.asteroidradar.MainAsteroidAdapter
import fi.monad.asteroidradar.R
import fi.monad.asteroidradar.api.NasaApiClient
import fi.monad.asteroidradar.api.getApiServiceImpl
import fi.monad.asteroidradar.databinding.MainFragmentBinding
import fi.monad.asteroidradar.persistence.AsteroidDatabase
import fi.monad.asteroidradar.repository.AsteroidRepository
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var adapter: MainAsteroidAdapter
    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ViewModelFactory(
            application, AsteroidRepository(
                NasaApiClient(getApiServiceImpl()),
                AsteroidDatabase.getDatabase(requireNotNull(this.activity).application)
            )
        )
        ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val asteroidAdapter = MainAsteroidAdapter(
            MainAsteroidAdapter.AsteroidClickListener {
                viewModel.onClickAsteroid(it)
            }
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.asteroid_recycler)
        recyclerView.adapter = asteroidAdapter

        viewModel.asteroids.observe(viewLifecycleOwner, {
            it?.let {
                Timber.i("here")
                Timber.i(it.toString())
                asteroidAdapter.submitList(it)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
