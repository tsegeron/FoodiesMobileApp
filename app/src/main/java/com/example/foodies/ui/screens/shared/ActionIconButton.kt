package com.example.foodies.ui.screens.shared

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodies.R
import com.example.foodies.ui.theme.FoodiesTheme


/**
 * Icon Button for Search, Filter, Cancel
 */
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

@Preview(showBackground = true)
@Composable
fun ActionIconButtonPreview() {
    FoodiesTheme {
        ActionIconButton(
            onClick = {},
            painterRes = R.drawable.cancel,
            iconDes = R.string.cancel,
        )
    }
}
