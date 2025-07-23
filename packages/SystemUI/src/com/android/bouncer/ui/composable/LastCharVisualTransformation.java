package com.android.bouncer.ui.composable;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.text.input.VisualTransformation;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt___StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LastCharVisualTransformation implements VisualTransformation {
    @Override // androidx.compose.ui.text.input.VisualTransformation
    public final TransformedText filter(AnnotatedString annotatedString) {
        String str;
        if (annotatedString.text.length() > 0) {
            str = StringsKt__StringsJVMKt.repeat(annotatedString.text.length() - 1, String.valueOf((char) 8226)) + StringsKt___StringsKt.last(annotatedString.text);
        } else {
            str = "";
        }
        AnnotatedString annotatedString2 = new AnnotatedString(str, null, 2, null);
        OffsetMapping.Companion.getClass();
        return new TransformedText(annotatedString2, OffsetMapping.Companion.Identity);
    }
}
