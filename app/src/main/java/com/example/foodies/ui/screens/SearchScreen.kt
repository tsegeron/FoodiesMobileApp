package com.example.foodies.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodies.R
import com.example.foodies.presentation.FoodiesViewModel
import com.example.foodies.ui.screens.uiElements.ActionIconButton
import com.example.foodies.ui.screens.uiElements.BottomBar
import com.example.foodies.ui.screens.uiElements.EmptyResultsBoxLayout
import com.example.foodies.ui.screens.uiElements.cartScreen.BarNavIcon

@Composable
fun SearchScreen(
    onDishCardClick: (Int) -> Unit,
    onCartButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    appViewModel: FoodiesViewModel = viewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = appViewModel.uiState.value.searchPrompt))
    }
    val foodiesUiState by appViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            SearchScreenTopAppBar(
                keyboardController = keyboardController,
                focusManager = focusManager,
                textFieldValue = textFieldValue,
                onTextFieldValueChange = { textFieldValue = it },
                onCancelIconClick = {
                    textFieldValue = TextFieldValue()
                    appViewModel.onSearchActionClick("")
                },
                onSearchActionClick = { appViewModel.onSearchActionClick(textFieldValue.text) },
                onBackButtonClick = onBackButtonClick
            )
        },
        bottomBar = {
            if (foodiesUiState.totalCost != 0) {
                BottomBar(
                    totalCost = foodiesUiState.totalCost,
                    onClick = onCartButtonClick
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        if (foodiesUiState.searchPrompt.isEmpty()) {
            EmptyResultsBoxLayout(
                textResId = R.string.search_invitation,
                modifier = Modifier
                    .padding(innerPadding)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { keyboardController?.hide(); focusManager.clearFocus(true) }
                    )
            )
        } else {
            val dishesListBySearch = appViewModel.getDishesBySearchPrompt(textFieldValue.text)

            if (dishesListBySearch.isEmpty()) {
                EmptyResultsBoxLayout(
                    textResId = R.string.search_no_results,
                    modifier = Modifier
                        .padding(innerPadding)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { keyboardController?.hide(); focusManager.clearFocus(true) }
                        )
                )
            } else {
                DishesList(
                    dishesList = dishesListBySearch,
                    tagsList = foodiesUiState.tagsList,
                    onDishCardClick = onDishCardClick,
                    onCostButtonClick = { appViewModel.addDishToCart(it) },
                    onCounterAddButtonClick = { appViewModel.increaseDishCounter(it) },
                    onCounterReduceButtonClick = { appViewModel.decreaseDishCounter(it) },
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_large),
                        top = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_large)
                    ),
                    contentPadding = innerPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenTopAppBar(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    onCancelIconClick: () -> Unit,
    onSearchActionClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
) {
    TopAppBar(
        title = {
            TopAppBarTextField(
                textFieldValue = textFieldValue,
                keyboardController = keyboardController,
                focusManager = focusManager,
                onValueChange = onTextFieldValueChange,
                onSearchAction = onSearchActionClick
            )
        },
        navigationIcon = {
            BarNavIcon(
                onBackButtonClick = onBackButtonClick,
                iconRes = R.drawable.arrowleft,
                iconDescription = R.string.back_button
            )
        },
        actions = {
            if (textFieldValue.text.isNotEmpty()) {
                ActionIconButton(
                    onClick = onCancelIconClick,
                    painterRes = R.drawable.cancel,
                    iconDes = R.string.cancel,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = modifier.shadow(8.dp)
    )
}

@Composable
fun TopAppBarTextField(
    textFieldValue: TextFieldValue,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    onValueChange: (TextFieldValue) -> Unit,
    onSearchAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textFieldValue,
        onValueChange = { onValueChange(it); onSearchAction() },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFFF15412),
            selectionColors = TextSelectionColors(
                handleColor = Color(0xFFF15412),
                backgroundColor = Color(0x4FEBAC68)
            )
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_prompt),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                onSearchAction()
            }
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray),
        modifier = modifier.fillMaxWidth()//.offset(x = -12.dp) // TODO: AppBar offset?
    )
}


//@Preview(name = "SearchScreen", showBackground = true)
//@Composable
//fun SearchScreenPreview() {
//    SearchScreen()
//}
