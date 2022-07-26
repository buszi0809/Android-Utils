# Android-Utils
Useful utilities that I use across projects

## View scoped delegates

Read write delegate that scopes property's value to Fragment's view lifecycle. Example usage:
```kotlin
class MainFragment : Fragment(R.layout.fragment_main) {

    private val withProducer: Int by viewScoped(producer = { 0 })
    private var withManualInitialization: Int by viewScoped(onPreDestroy = { /* TODO */ })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withManualInitialization = 0
    }
}
```
- The value will be initialized (if producer was provided) after `onCreateView()` and cleared before `onDestroyView()`.
- Accessing property out of view's lifecycle will throw `IllegalStateException`.
- Accessing property when producer was not provided and property was not initialized will throw `IllegalStateException`.
- Initializing property manually out of view's lifecycle will throw `IllegalStateException`.

Usage requires artifacts:
- library `androidx.fragment:fragment` or `androidx.fragment:fragment-ktx` ([Ref](https://developer.android.com/jetpack/androidx/releases/fragment))
- library `androidx.lifecycle:lifecycle-runtime` or `androidx.lifecycle:lifecycle-runtime-ktx` ([Ref](https://developer.android.com/jetpack/androidx/releases/lifecycle))
#### ViewBinding delegate

View binding delegate is a read only delegate that will take care of instantiating the `ViewBinding`. Example usage:
```kotlin
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.isVisible = false
    }
}

```
- `ViewBinding` will be initialized after `onCreateView()` and cleared before `onDestroyView()`.
- Accessing `ViewBinding` out of view's lifecycle will throw `IllegalStateException`.
- This delegate uses `viewScoped()` delegate under the hood

Usage requires:
- library `androidx.fragment:fragment` or `androidx.fragment:fragment-ktx` ([Ref](https://developer.android.com/jetpack/androidx/releases/fragment))
- library `androidx.lifecycle:lifecycle-runtime` or `androidx.lifecycle:lifecycle-runtime-ktx` ([Ref](https://developer.android.com/jetpack/androidx/releases/lifecycle))
- view binding enabled ([Documentation](https://developer.android.com/topic/libraries/view-binding))
