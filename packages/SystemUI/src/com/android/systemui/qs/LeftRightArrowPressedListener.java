package com.android.systemui.qs;

import android.view.KeyEvent;
import android.view.View;
import androidx.core.util.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class LeftRightArrowPressedListener implements View.OnKeyListener, View.OnFocusChangeListener {
    public static final Companion Companion = new Companion(null);
    public Integer lastKeyCode;
    public Consumer listener;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        if (keyEvent.getAction() == 1 && (num = this.lastKeyCode) != null && i == num.intValue()) {
            Consumer consumer = this.listener;
            if (consumer != null) {
                consumer.accept(Integer.valueOf(i));
            }
            this.lastKeyCode = null;
        } else if (keyEvent.getRepeatCount() == 0) {
            this.lastKeyCode = Integer.valueOf(i);
        }
        return true;
    }

    private LeftRightArrowPressedListener() {
        this.lastKeyCode = 0;
    }
}
