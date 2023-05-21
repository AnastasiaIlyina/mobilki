package com.example.mobilki.presentation.screens.auth_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.domain.models.auth.AuthScreenPages
import com.example.mobilki.presentation.dim.Dimens
import com.example.mobilki.presentation.screens.auth_screen.screens.LoginScreen
import com.example.mobilki.presentation.screens.auth_screen.screens.RegistrationScreen
import com.example.mobilki.ui.theme.Purple700
import com.example.mobilki.ui.theme.typography
import kotlinx.coroutines.launch






@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthPageScreen(
    navController: NavController,
    viewModel: UserViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(all = Dimens.Paddings.basePadding),
    ) {
        val pagerState = rememberPagerState()
        val pages = remember { AuthScreenPages.values() }

        TabBar(tabs = pages.map { stringResource(id = it.toStringRes()) }, pagerState = pagerState)

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
        ) {
            when (pages[it]) {
                AuthScreenPages.REGISTRATION -> RegistrationScreen(viewModel = viewModel)
                AuthScreenPages.LOGIN -> LoginScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabBar(
    tabs: List<String>,
    pagerState: PagerState
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(title = title, index = index, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Tab(title: String, index: Int, pagerState: PagerState) {

    val rememberCoroutineScope = rememberCoroutineScope();

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        TextButton(
            onClick =  {
                rememberCoroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        ) {
            Text(
                text = title,
                style = typography.h2
            )
        }

        Divider(
            thickness = if (pagerState.currentPage == index)
                    Dimens.Sizes.boldDividerThickness
                else
                    Dimens.Sizes.dividerThickness,
            color = if (pagerState.currentPage == index)
                    Purple700
                else
                    Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


