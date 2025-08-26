package androidx.compose.foundation.text;

import androidx.compose.foundation.text.modifiers.TextAutoSizeLayoutScope;
import androidx.compose.ui.text.AnnotatedString;

/* loaded from: classes.dex */
public interface TextAutoSize {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* renamed from: StepBased-vU-0ePk$default, reason: not valid java name */
        public static TextAutoSize m201StepBasedvU0ePk$default(Companion companion, long j, long j2) {
            TextAutoSizeDefaults.INSTANCE.getClass();
            long j3 = TextAutoSizeDefaults.MinFontSize;
            companion.getClass();
            return new AutoSizeStepBased(j3, j, j2, null);
        }
    }

    /* renamed from: getFontSize-Ci0_558 */
    long mo191getFontSizeCi0_558(TextAutoSizeLayoutScope textAutoSizeLayoutScope, long j, AnnotatedString annotatedString);

    int hashCode();
}
