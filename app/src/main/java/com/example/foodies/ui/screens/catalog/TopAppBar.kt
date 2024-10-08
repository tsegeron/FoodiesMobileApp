package com.example.foodies.ui.screens.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.foodies.R
import com.example.foodies.data.model.Category
import com.example.foodies.ui.screens.shared.ActionIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreenTopAppBar(
    markedTagsNum: Int,
    categories: List<Category>,
    onCategoryChipClick: (Category) -> Unit,
    onFilterIconClick: () -> Unit,
    onSearchIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CenterAlignedTopAppBar(
            title = {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )
            },
            navigationIcon = {
                NavigationIconButton(
                    onClick = onFilterIconClick,
                    modifier = Modifier,
                    markedTagsNum = markedTagsNum
                )
            },
            actions = {
                ActionIconButton(
                    onClick = onSearchIconClick,
                    painterRes = R.drawable.search,
                    iconDes = R.string.search_button
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )

        LazyRow( // TODO: change to ScrollableTabRow?
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
            ),
            modifier = Modifier.background(Color.White)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category.isSelected,
                    onClick = { onCategoryChipClick(category) },
                    label = { Text(text = category.name) },
                    shape = MaterialTheme.shapes.medium,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorResource(id = R.color.orange),
                        selectedLabelColor = Color.White,
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = Color.Transparent,
                        selected = false,
                        enabled = true
                    )
                )
            }
        }
    }
}

@Composable
private fun NavigationIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    markedTagsNum: Int = 0,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd,
    ) {
        ActionIconButton(
            onClick = onClick,
            painterRes = R.drawable.filter,
            iconDes = R.string.filter_button
        )

        if (markedTagsNum != 0) {
            Text(
                text = "$markedTagsNum",
                color = Color.White,
                modifier = Modifier.drawBehind {
                    drawCircle(Color(0xFFF15412), size.minDimension + 1)
                }
            )
        }
    }
}
