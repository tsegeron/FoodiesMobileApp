package com.example.foodies.ui.screens.uiElements

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


@Composable
fun ActionIconButton(
    onClick: () -> Unit,
    @DrawableRes painterRes: Int,
    @StringRes iconDes: Int,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = painterRes),
            contentDescription = stringResource(id = iconDes)
        )
    }
}
