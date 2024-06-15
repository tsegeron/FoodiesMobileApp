package com.example.foodies.ui.screens.uiElements.cartScreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenTopAppBar(
    onBackActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { BarTitle(titleText = R.string.cart) },
        navigationIcon = {
            BarNavIcon(
                onBackButtonClick = onBackActionButtonClick,
                iconRes = R.drawable.arrowleft,
                iconDescription = R.string.back_button
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        modifier = modifier.shadow(
            elevation = 8.dp,
            spotColor = Color.Black,
            shape = RectangleShape
        )
    )
}

@Composable
fun BarTitle(
    @StringRes titleText: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(titleText),
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier.padding(
            start = dimensionResource(id = R.dimen.padding_large),
            end = dimensionResource(id = R.dimen.padding_large)
        )
    )
}

@Composable
fun BarNavIcon(
    onBackButtonClick: () -> Unit,
    @DrawableRes iconRes: Int,
    @StringRes iconDescription: Int,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onBackButtonClick,
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large)
            )
            .size(dimensionResource(id = R.dimen.tag_size))
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(iconDescription),
            tint = Color(0xFFF15412),
            modifier = Modifier
        )
    }
}
