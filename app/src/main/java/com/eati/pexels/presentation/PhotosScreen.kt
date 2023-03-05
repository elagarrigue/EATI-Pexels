package com.eati.pexels.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eati.pexels.R
import com.eati.pexels.domain.Photo
import com.eati.pexels.presentation.ui.theme.EATIPexelsTheme
import kotlinx.coroutines.launch



@Composable
fun PhotosScreen(viewModel: PhotosViewModel) {
    val result by viewModel.photosFlow.collectAsState()
    PhotosScaffold(result, viewModel::updateResults)
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PhotosScaffold(results: List<Photo>, updateResults: (String) -> Unit) {
    //val scaffoldState= rememberScaffoldState()
    //val scope= rememberCoroutineScope()

    var text by remember {mutableStateOf("archictecture")}
    var expandedPhoto by remember { mutableStateOf<Photo?>(null) }

    Scaffold(
        topBar = { SearchBar(updateResults = updateResults) }

    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                ExpandedPhoto(expandedPhoto)
            }
        ) {
            Photos(
                results,
                updateResults,
                onClick = {photo ->
                    expandedPhoto = photo
                    scope.launch{
                        drawerState.apply { if (isClosed) open() else close() }
                    }
                }
            )
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Photos(results: List<Photo>,
           updateResults: (String) -> Unit,
           onClick: (Photo) -> Unit
    ) {



    LaunchedEffect(Unit) {
        updateResults("Cats")
    }
    Surface(modifier = Modifier.padding(4.dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)){
            items(results){
                    result -> ImageItem(result,onClick =onClick)

            }
        }
    }

}

@Composable
fun ImageItem(result: Photo, onClick: (Photo) -> Unit) {
    Column(modifier = Modifier.clickable {onClick(result)}
        .padding(4.dp)
    ) {
        AsyncImage(model = result.sourceURL, contentDescription= result.alt)
        Text(text = result.photographer)
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    updateResults: (String) -> Unit
) {
    Surface(modifier= modifier.padding(4.dp),
        elevation = 3.dp
    ){
        var text = remember {mutableStateOf("")}
        TextField(
            value= text.value,
            onValueChange = {newValue -> text.value= newValue},
            trailingIcon = {
                Button(
                    modifier = Modifier.padding(end = 6.dp),
                    onClick = { updateResults(text.value) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            label = { Text(text = stringResource(R.string.SearchPlaceholder)) },
            singleLine= true,
            modifier= modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )


    }
}


@Preview
@Composable
fun ScaffoldPreview(){
    EATIPexelsTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            PhotosScaffold(listOf()) {}
        }
    }
}