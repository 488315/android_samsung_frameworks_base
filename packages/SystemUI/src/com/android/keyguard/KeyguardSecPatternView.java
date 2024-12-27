package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.samsung.android.tsp.SemTspStateManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardSecPatternView extends KeyguardPatternView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAttached;

    public KeyguardSecPatternView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardPatternView, com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return null;
    }

    @Override // com.android.keyguard.KeyguardPatternView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (LsRune.SECURITY_DEAD_ZONE) {
            this.mAttached = true;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (LsRune.SECURITY_DEAD_ZONE) {
            this.mAttached = false;
        }
    }

    @Override // com.android.keyguard.KeyguardPatternView, com.android.keyguard.KeyguardInputView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ((LinearLayout) findViewById(R.id.split_touch_top_container)).setOnTouchListener(new KeyguardSecPatternView$$ExternalSyntheticLambda1());
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (LsRune.SECURITY_DEAD_ZONE && this.mAttached) {
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "onVisibilityChanged() visibility : ", "KeyguardSecPatternView");
            if (i == 0) {
                SemTspStateManager.setDeadZone(getRootView(), AbsAdapter$1$$ExternalSyntheticOutline0.m("deadzone_v2", "3.33%,3.33%,0%"));
            } else {
                if (LsRune.SECURITY_BOUNCER_WINDOW) {
                    return;
                }
                SemTspStateManager.clearDeadZone(getRootView());
            }
        }
    }

    public KeyguardSecPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
