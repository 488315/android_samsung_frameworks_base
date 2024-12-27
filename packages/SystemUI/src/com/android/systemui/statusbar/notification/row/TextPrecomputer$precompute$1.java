package com.android.systemui.statusbar.notification.row;

import android.text.Spannable;
import android.util.Log;
import android.widget.TextView;

public final class TextPrecomputer$precompute$1 implements Runnable {
    public final /* synthetic */ boolean $logException;
    public final /* synthetic */ Spannable $precomputedText;
    public final /* synthetic */ CharSequence $text;
    public final /* synthetic */ TextView $textView;

    public TextPrecomputer$precompute$1(TextView textView, Spannable spannable, boolean z, CharSequence charSequence) {
        this.$textView = textView;
        this.$precomputedText = spannable;
        this.$logException = z;
        this.$text = charSequence;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.$textView.setText(this.$precomputedText);
        } catch (IllegalArgumentException e) {
            if (this.$logException) {
                Log.wtf("TextPrecomputer", "PrecomputedText setText failed for TextView:" + this.$textView, e);
            }
            this.$textView.setText(this.$text);
        }
    }
}
