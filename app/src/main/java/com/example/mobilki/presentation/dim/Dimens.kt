package com.example.mobilki.presentation.dim

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier


//Объект Dimens содержит предопределенные значения для размеров, отступов, форм, размеров и
// модификаторов, используемых во всем приложении.
object Dimens {
    object Paddings {
        val smallPadding = 4.dp
        val halfPadding = 8.dp
        val basePadding = 16.dp
    }

    object Shapes {
        val smallBorderShape = 15
        val baseBorderShape = 30
    }
    
    object Sizes {
        val dividerThickness = 1.dp
        val boldDividerThickness = 3.dp

        val countryCodeFieldWidth = 60.dp

        val baseBorderWidth = 2.dp
    }

    object Modifiers {
        val commonModifier = Modifier.clip(RoundedCornerShape(Shapes.baseBorderShape))
                                     .border(
                                        width = Dimens.Sizes.baseBorderWidth,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(Shapes.baseBorderShape),
                                    )
    }
}
