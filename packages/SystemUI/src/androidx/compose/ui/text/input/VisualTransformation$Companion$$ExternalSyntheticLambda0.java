package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.VisualTransformation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class VisualTransformation$Companion$$ExternalSyntheticLambda0 implements VisualTransformation {
    @Override // androidx.compose.ui.text.input.VisualTransformation
    public final TransformedText filter(AnnotatedString annotatedString) {
        VisualTransformation.Companion companion = VisualTransformation.Companion.$$INSTANCE;
        OffsetMapping.Companion.getClass();
        return new TransformedText(annotatedString, OffsetMapping.Companion.Identity);
    }
}
