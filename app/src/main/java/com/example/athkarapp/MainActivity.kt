package com.example.athkarapp

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import com.example.athkarapp.screens.ACTION_GEOFENCE_EVENT
import com.example.athkarapp.screens.GeofenceBroadcastReceiver
import com.example.athkarapp.screens.MapView
import com.example.athkarapp.ui.theme.AthkarAppTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted =
            permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val backgroundLocationGranted =
            permissions[android.Manifest.permission.ACCESS_BACKGROUND_LOCATION] ?: false

        if (fineLocationGranted && backgroundLocationGranted) {
            //setupGeofence()

        } else {
            // Handle permission denial
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            )
        } else {

            enableEdgeToEdge()
            setContent {
                AthkarAppTheme {
                    val sheetState: SheetState = rememberModalBottomSheetState()
                    val scope = rememberCoroutineScope()

                    MapView()
                }
            }
        }

        // register this Activity as the receiver of Geofence notifications
        val filter = IntentFilter()
        filter.addAction(ACTION_GEOFENCE_EVENT)
        this.registerReceiver(GeofenceBroadcastReceiver(), filter)
    }


}


//val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//val scope = rememberCoroutineScope()
//ModalNavigationDrawer(
//drawerState = drawerState,
//drawerContent = {
//    ModalDrawerSheet { /* Drawer content */ }
//},
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = "Title App Bar",
//                        textAlign = TextAlign.Center
//                    )
//                },
//                navigationIcon = {
//                    Icon(Icons.Filled.Menu, "icon ", modifier = Modifier.clickable {
//                        scope.launch {
//                            drawerState.apply {
//                                if (isClosed) open() else close()
//                            }
//                        }
//                    })
//                }
//            )
//        },
//        bottomBar = {
//            BottomAppBar(
//                content = {
//                    Icon(Icons.Filled.Menu, "icon ",)
//
//                }
//            )
//        }
//    ) { contentPadding ->
//        // Screen content
//
//        val viewModel by viewModels<AthkarViewModel>()
//        Column(modifier = Modifier.padding(contentPadding)) {
//
//            LaunchedEffect(Unit) {
//                val x =
//                    viewModel.loadJsonData(
//                        this@MainActivity,
//                        "athkar.json",
//                        ResponseAthakr::class.java
//                    )
//                viewModel.setAthkarList(x.list)
//            }
//            AthkarCategoryScreen(viewModel.athkarList.value?.list?.map { it.category }
//                ?.distinct())
//        }
//    }
//}