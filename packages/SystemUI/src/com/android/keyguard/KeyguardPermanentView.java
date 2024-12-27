package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;

public class KeyguardPermanentView extends KeyguardInputView {
    public KeyguardPermanentView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return null;
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
        setAlpha(0.0f);
        setTranslationY(0.0f);
        animate().alpha(1.0f).withLayer().setDuration(200L);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final boolean startDisappearAnimation(Runnable runnable) {
        animate().alpha(0.0f).setDuration(100L).withEndAction(runnable);
        return true;
    }

    public KeyguardPermanentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }
}
