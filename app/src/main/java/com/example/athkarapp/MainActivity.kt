package com.example.athkarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.lifecycleScope
import com.example.athkarapp.model.ResponseAthakr
import com.example.athkarapp.screens.AthkarCategoryScreen
import com.example.athkarapp.screens.MapView
import com.example.athkarapp.ui.theme.AthkarAppTheme
import com.example.athkarapp.viewmodels.AthkarViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AthkarAppTheme {
                val sheetState: SheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()


                MapView()
            }
        }
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