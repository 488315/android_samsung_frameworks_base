package androidx.compose.ui.text.input;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface OffsetMapping {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final OffsetMapping$Companion$Identity$1 Identity = new OffsetMapping() { // from class: androidx.compose.ui.text.input.OffsetMapping$Companion$Identity$1
            @Override // androidx.compose.ui.text.input.OffsetMapping
            public final int originalToTransformed(int i) {
                return i;
            }

            @Override // androidx.compose.ui.text.input.OffsetMapping
            public final int transformedToOriginal(int i) {
                return i;
            }
        };

        private Companion() {
        }
    }

    int originalToTransformed(int i);

    int transformedToOriginal(int i);
}
