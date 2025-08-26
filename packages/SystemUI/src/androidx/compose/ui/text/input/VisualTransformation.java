package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;

/* loaded from: classes.dex */
public interface VisualTransformation {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final VisualTransformation$Companion$$ExternalSyntheticLambda0 None = new VisualTransformation$Companion$$ExternalSyntheticLambda0();

        private Companion() {
        }
    }

    TransformedText filter(AnnotatedString annotatedString);
}
