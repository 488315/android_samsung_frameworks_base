package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.VisualTransformation;

/* loaded from: classes.dex */
public final /* synthetic */ class VisualTransformation$Companion$$ExternalSyntheticLambda0 implements VisualTransformation {
    @Override // androidx.compose.ui.text.input.VisualTransformation
    public final TransformedText filter(AnnotatedString annotatedString) {
        VisualTransformation.Companion companion = VisualTransformation.Companion.$$INSTANCE;
        OffsetMapping.Companion.getClass();
        return new TransformedText(annotatedString, OffsetMapping.Companion.Identity);
    }
}
