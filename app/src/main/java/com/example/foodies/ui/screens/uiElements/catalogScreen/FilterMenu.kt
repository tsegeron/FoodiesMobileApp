package com.example.foodies.ui.screens.uiElements.catalogScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.foodies.R
import com.example.foodies.data.model.Tag


/*
    Options: ModalBottomSheet, BottomDrawer, ModalDrawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterMenu(
    tagsList: List<Tag>,
    modalSheetState: SheetState,
    filterMenuOnDismissRequest: () -> Unit,
    applyFilterOptionsOnClick: (MutableList<Boolean>) -> Unit,
    modifier: Modifier = Modifier
) {
    val tagsCheckedState: MutableList<Boolean> = tagsList.map { it.isSelected }.toMutableStateList()

    ModalBottomSheet(
        onDismissRequest = filterMenuOnDismissRequest,
        modifier = modifier.wrapContentHeight(),
        sheetState = modalSheetState,
        shape = MaterialTheme.shapes.extraLarge,
        dragHandle = null,
        contentColor = Color.White,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.modal_bottom_sheet_padding),
                bottom = dimensionResource(id = R.dimen.modal_bottom_sheet_padding),
                start = dimensionResource(id = R.dimen.padding_extra_large),
                end = dimensionResource(id = R.dimen.padding_extra_large),
            )
        ) {
            Text(
                text = stringResource(id = R.string.choose_dishes),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
            )

            FilterOptionsColumn(
                filterOptions = tagsList,
                tagsCheckedState = tagsCheckedState
            )

            Button(
                onClick = { applyFilterOptionsOnClick(tagsCheckedState) },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF15412)),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_large)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_large))
            ) {
                Text(
                    text = stringResource(id = R.string.submit),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                )
            }
        }
    }
}


@Composable
fun FilterOptionsColumn(
    filterOptions: List<Tag>,
    tagsCheckedState: MutableList<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        for (i in filterOptions.indices) {
            FilterTagRow(
                tagName = filterOptions[i].name,
                tagsCheckedState = tagsCheckedState,
                tagIndex = i
            )
            if (i < filterOptions.size - 1) {
                HorizontalDivider(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.divider_thickness))
                        .fillMaxWidth(),
                    color = Color(0xFFE0E0E0)
                )
            }
        }
    }
}

@Composable
fun FilterTagRow(
    tagName: String,
    tagsCheckedState: MutableList<Boolean>,
    tagIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = tagName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = tagsCheckedState[tagIndex],
            onCheckedChange = { tagsCheckedState[tagIndex] = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFF15412),
                uncheckedColor = Color.LightGray,
                checkmarkColor = Color.White
            )
        )
    }
}

//private fun shareOrder(
//    context: Context,
//    subject: String,
//    summary: String
//) {
//    val intent = Intent(Intent.ACTION_SEND).apply {
//        type = "text/plain"
//        putExtra(Intent.EXTRA_SUBJECT, subject)
//        putExtra(Intent.EXTRA_TEXT, summary)
//    }
//
//    context.startActivity(
//        Intent.createChooser(
//            intent,
//            context.getString(R.string.filter)
//        )
//    )
//}
