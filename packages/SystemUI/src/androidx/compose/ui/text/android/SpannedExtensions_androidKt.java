package androidx.compose.ui.text.android;

import android.text.Spanned;

/* loaded from: classes.dex */
public abstract class SpannedExtensions_androidKt {
    public static final boolean hasSpan(Spanned spanned, Class cls) {
        return spanned.nextSpanTransition(-1, spanned.length(), cls) != spanned.length();
    }
}
