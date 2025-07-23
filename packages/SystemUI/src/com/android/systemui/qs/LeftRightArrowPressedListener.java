package com.android.systemui.qs;

import android.view.KeyEvent;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LeftRightArrowPressedListener implements View.OnKeyListener, View.OnFocusChangeListener {
    public static final Companion Companion = new Companion(null);
    public Integer lastKeyCode;
    public PageIndicator$$ExternalSyntheticLambda0 listener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ LeftRightArrowPressedListener(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        if (z) {
            this.lastKeyCode = null;
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        Integer num;
        if (i != 21 && i != 22) {
            return false;
        }
        if (keyEvent.getAction() != 1 || (num = this.lastKeyCode) == null || i != num.intValue()) {
            if (keyEvent.getRepeatCount() == 0) {
                this.lastKeyCode = Integer.valueOf(i);
            }
            return true;
        }
        PageIndicator$$ExternalSyntheticLambda0 pageIndicator$$ExternalSyntheticLambda0 = this.listener;
        if (pageIndicator$$ExternalSyntheticLambda0 != null) {
            pageIndicator$$ExternalSyntheticLambda0.accept(Integer.valueOf(i));
        }
        this.lastKeyCode = null;
        return true;
    }

    private LeftRightArrowPressedListener() {
        this.lastKeyCode = 0;
    }
}
