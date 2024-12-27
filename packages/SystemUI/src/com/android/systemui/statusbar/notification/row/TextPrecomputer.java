package com.android.systemui.statusbar.notification.row;

import android.text.PrecomputedText;
import android.widget.TextView;

public interface TextPrecomputer {

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
