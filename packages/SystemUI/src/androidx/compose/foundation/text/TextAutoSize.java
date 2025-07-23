package androidx.compose.foundation.text;

import androidx.compose.foundation.text.modifiers.TextAutoSizeLayoutScope;
import androidx.compose.ui.text.AnnotatedString;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface TextAutoSize {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* renamed from: StepBased-vU-0ePk$default, reason: not valid java name */
        public static TextAutoSize m200StepBasedvU0ePk$default(Companion companion, long j, long j2) {
            TextAutoSizeDefaults.INSTANCE.getClass();
            long j3 = TextAutoSizeDefaults.MinFontSize;
            companion.getClass();
            return new AutoSizeStepBased(j3, j, j2, null);
        }
    }

    /* renamed from: getFontSize-Ci0_558 */
    long mo190getFontSizeCi0_558(TextAutoSizeLayoutScope textAutoSizeLayoutScope, long j, AnnotatedString annotatedString);

    int hashCode();
}
