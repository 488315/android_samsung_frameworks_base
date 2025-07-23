package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface VisualTransformation {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final VisualTransformation$Companion$$ExternalSyntheticLambda0 None = new VisualTransformation$Companion$$ExternalSyntheticLambda0();

        private Companion() {
        }
    }

    TransformedText filter(AnnotatedString annotatedString);
}
