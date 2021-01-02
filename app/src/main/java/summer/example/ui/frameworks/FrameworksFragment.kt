package summer.example.ui.frameworks

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.serialization.Serializable
import summer.example.databinding.FrameworksFragmentBinding
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.FrameworksView
import summer.example.presentation.FrameworksViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs
import summer.example.ui.base.routing.toScreen

class FrameworksFragment :
    BaseFragment<FrameworksFragment.Args>(),
    FrameworksView {

    override val viewModel by bindViewModel { FrameworksViewModel() }
    private val binding by viewBinding { FrameworksFragmentBinding.inflate(it) }

    override var items: List<Basket.Item> by didSet {
        frameworksAdapter.submitList(items)
    }

    override val toDetails = { framework: Framework ->
        ciceroneRouter.navigateTo(FrameworkDetailsFragment.Args(framework).toScreen())
    }

    private lateinit var frameworksAdapter: FrameworksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frameworksAdapter = FrameworksAdapter(viewModel)
        binding.frameworksView.adapter = frameworksAdapter
        (binding.frameworksView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<FrameworksFragment>(::FrameworksFragment)
}