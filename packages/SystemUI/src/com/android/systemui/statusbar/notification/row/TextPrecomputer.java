package com.android.systemui.statusbar.notification.row;

import android.text.PrecomputedText;
import android.widget.TextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface TextPrecomputer {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return new TextPrecomputer$precompute$1(textView, charSequence != null ? PrecomputedText.create(charSequence, textView.getTextMetricsParams()) : null, true, charSequence);
    }
}
