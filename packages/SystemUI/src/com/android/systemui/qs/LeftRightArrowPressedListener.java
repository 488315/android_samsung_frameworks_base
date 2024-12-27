package com.android.systemui.qs;

import android.view.KeyEvent;
import android.view.View;
import androidx.core.util.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LeftRightArrowPressedListener implements View.OnKeyListener, View.OnFocusChangeListener {
    public static final Companion Companion = new Companion(null);
    public Integer lastKeyCode;
    public Consumer listener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
