package com.android.systemui.statusbar.notification.row;

import android.text.PrecomputedText;
import android.widget.TextView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface TextPrecomputer {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

    static TextPrecomputer$precompute$1 precompute$default(TextPrecomputer textPrecomputer, TextView textView, CharSequence charSequence) {
        textPrecomputer.getClass();
        return new TextPrecomputer$precompute$1(textView, charSequence != null ? PrecomputedText.create(charSequence, textView.getTextMetricsParams()) : null, true, charSequence);
    }
}
