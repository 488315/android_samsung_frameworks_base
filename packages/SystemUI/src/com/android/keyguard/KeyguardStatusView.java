package com.android.keyguard;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.GridLayout;
import com.android.keyguard.KeyguardClockFrame;
import com.android.keyguard.KeyguardSliceView;
import com.android.systemui.R;
import com.android.systemui.shade.TouchLogger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class KeyguardStatusView extends GridLayout {
    public KeyguardClockSwitch mClockView;
    public int mDrawAlpha;
    public KeyguardSliceView mKeyguardSlice;
    public View mMediaHostContainer;
    public ViewGroup mStatusViewContainer;

    public static /* synthetic */ Unit $r8$lambda$NnkgDh6l21JxDlPpQeOyBKg8g94(KeyguardStatusView keyguardStatusView, Canvas canvas) {
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
                return KeyguardStatusView.$r8$lambda$NnkgDh6l21JxDlPpQeOyBKg8g94(KeyguardStatusView.this, (Canvas) obj);
            }
        };
        KeyguardClockFrame.Companion.getClass();
        KeyguardClockFrame.Companion.saveCanvasAlpha(this, canvas, i, function1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "KeyguardStatusView", dispatchTouchEvent);
        return dispatchTouchEvent;
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
