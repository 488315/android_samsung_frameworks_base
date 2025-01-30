package com.android.keyguard;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.GridLayout;
import com.android.keyguard.KeyguardClockFrame;
import com.android.keyguard.KeyguardSliceView;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardStatusView extends GridLayout {
    public KeyguardClockSwitch mClockView;
    public int mDrawAlpha;
    public KeyguardSliceView mKeyguardSlice;
    public View mMediaHostContainer;
    public ViewGroup mStatusViewContainer;

    public static /* synthetic */ Unit $r8$lambda$2zLb5nro1dltopXz_AUx2yl_rM4(KeyguardStatusView keyguardStatusView, Canvas canvas) {
        super.dispatchDraw(canvas);
        return Unit.INSTANCE;
    }

    public KeyguardStatusView(Context context) {
        this(context, null, 0);
    }

    @Override // android.view.View
    public final ViewPropertyAnimator animate() {
        if (Build.IS_DEBUGGABLE) {
            throw new IllegalArgumentException("KeyguardStatusView does not support ViewPropertyAnimator. Use PropertyAnimator instead.");
        }
        return super.animate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        int i = this.mDrawAlpha;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardStatusView.$r8$lambda$2zLb5nro1dltopXz_AUx2yl_rM4(KeyguardStatusView.this, (Canvas) obj);
            }
        };
        KeyguardClockFrame.Companion.getClass();
        KeyguardClockFrame.Companion.saveCanvasAlpha(this, canvas, i, function1);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mStatusViewContainer = (ViewGroup) findViewById(R.id.status_view_container);
        this.mClockView = (KeyguardClockSwitch) findViewById(R.id.keyguard_clock_container);
        Context context = ((GridLayout) this).mContext;
        int i = KeyguardClockAccessibilityDelegate.$r8$clinit;
        if (!TextUtils.isEmpty(context.getString(R.string.keyguard_fancy_colon))) {
            this.mClockView.setAccessibilityDelegate(new KeyguardClockAccessibilityDelegate(((GridLayout) this).mContext));
        }
        this.mKeyguardSlice = (KeyguardSliceView) findViewById(R.id.keyguard_slice_view);
        this.mMediaHostContainer = findViewById(R.id.status_view_media_container);
        KeyguardSliceView keyguardSliceView = this.mKeyguardSlice;
        keyguardSliceView.mDarkAmount = 0.0f;
        KeyguardSliceView.Row row = keyguardSliceView.mRow;
        row.getClass();
        if (row.mDarkAmount != 0.0f) {
            row.mDarkAmount = 0.0f;
            row.setLayoutAnimationListener(row.mKeepAwakeListener);
        }
        keyguardSliceView.updateTextColors();
    }

    @Override // android.widget.GridLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("KeyguardStatusView#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    @Override // android.view.View
    public final boolean onSetAlpha(int i) {
        this.mDrawAlpha = i;
        return true;
    }

    public KeyguardStatusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardStatusView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawAlpha = 255;
    }
}
