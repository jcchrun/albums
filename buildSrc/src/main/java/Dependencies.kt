object ApplicationId {
	val id = "com.jcchrun.albums"
}

object Modules {
	const val app = ":app"
	const val core = ":core"
	const val domain = ":domain"
	const val locale = ":locale"
	const val models = ":models"
	const val presentation = ":presentation"
	const val remote = ":remote"
	const val repository = ":repository"
	const val testUtils = ":testUtils"
}

object Versions {
	const val compile_sdk = 30
	const val min_sdk = 21
	const val target_sdk = 30
	const val build_tools_version = "30.0.2"
	const val version_code = 1
	const val version_name = "1.0"
	const val navigation = "2.3.2"
}

object Libraries {
	// Android
	const val appcompat = "androidx.appcompat:appcompat:1.2.0"
	const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
	const val core_ktx = "androidx.core:core-ktx:1.3.2"
	const val fragment_ktx = "androidx.fragment:fragment-ktx:1.2.5"
	const val activity_ktx = "androidx.activity:activity-ktx:1.1.0"
	const val materialViews = "com.google.android.material:material:1.2.1"
	const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"

	// Coroutines
	const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"
	const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"

	// Lifecycle
	const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
	const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
	const val lifecycle_commons_java8 = "androidx.lifecycle:lifecycle-common-java8:2.2.0"

	// Hilt
	const val hilt = "com.google.dagger:hilt-android:2.29.1-alpha"
	const val hilt_compiler = "com.google.dagger:hilt-android-compiler:2.29.1-alpha"
	const val hilt_lifecycle_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"
	const val hilt_jetpack_compiler = "androidx.hilt:hilt-compiler:1.0.0-alpha02"

	// Navigation
	const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
	const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

	// Retrofit
	const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
	const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
	const val okhttp3 = "com.squareup.okhttp3:okhttp:4.9.0"
	const val okhttp3_logging = "com.squareup.okhttp3:logging-interceptor:4.9.0"

	// Room
	const val room_compiler = "androidx.room:room-compiler:2.2.6"
	const val room_ktx = "androidx.room:room-ktx:2.2.6"
	const val room_runtime = "androidx.room:room-runtime:2.2.6"

	// Third parties
	const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
	const val stetho = "com.facebook.stetho:stetho:1.5.1"
	const val coil = "io.coil-kt:coil:1.1.0"
}

object TestLibraries {
	const val junit = "junit:junit:4.13.1"
	const val mockk = "io.mockk:mockk:1.10.0"
	const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1"
	const val core_testing = "androidx.arch.core:core-testing:2.1.0"
}

object AndroidTestLibraries {
	const val junit = "androidx.test.ext:junit:1.1.2"
	const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
	const val espresso_contrib = "androidx.test.espresso:espresso-contrib:3.3.0"
	const val core_testing = "androidx.arch.core:core-testing:2.1.0"
	const val runner = "androidx.test:runner:1.3.0"
	const val rules = "androidx.test:rules:1.3.0"
	const val truth = "androidx.test.ext:truth:1.3.0"
}