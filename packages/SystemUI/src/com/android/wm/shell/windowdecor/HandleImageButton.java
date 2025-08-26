package com.android.wm.shell.windowdecor;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.ImageButton;
import com.samsung.android.rune.CoreRune;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class HandleImageButton extends ImageButton {
    public final int HANDLE_DEFAULT_PADDING;
    public final int HANDLE_HOVER_ENTER_PADDING;
    public final int HANDLE_PRESS_DOWN_PADDING;
    public String appName;
    public final ValueAnimator handleAnimator;
    public final int handleWidth;
    public final int initHorizontalPadding;
    public final int initVerticalPaddingBottom;
    public final int initVerticalPaddingTop;
    public boolean isPaddingAdjusted;
    public final PackageManager pm;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public HandleImageButton(Context context, AttributeSet attributeSet) {
        Context applicationContext;
        super(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight), attributeSet);
        this.handleAnimator = new ValueAnimator();
        this.HANDLE_HOVER_ENTER_PADDING = loadDimensionPixelSize(com.android.systemui.R.dimen.desktop_mode_fullscreen_decor_caption_horizontal_padding_hovered);
        this.HANDLE_PRESS_DOWN_PADDING = loadDimensionPixelSize(com.android.systemui.R.dimen.desktop_mode_fullscreen_decor_caption_horizontal_padding_touched);
        this.HANDLE_DEFAULT_PADDING = loadDimensionPixelSize(com.android.systemui.R.dimen.desktop_mode_fullscreen_decor_caption_horizontal_padding_default);
        this.pm = (context == null || (applicationContext = context.getApplicationContext()) == null) ? null : applicationContext.getPackageManager();
        this.appName = "";
        if (context != null) {
            this.initHorizontalPadding = loadDimensionPixelSize(com.android.systemui.R.dimen.mw_handle_padding_horizontal);
            this.initVerticalPaddingTop = loadDimensionPixelSize(com.android.systemui.R.dimen.mw_handle_padding_vertical_top);
            this.initVerticalPaddingBottom = loadDimensionPixelSize(com.android.systemui.R.dimen.mw_handle_padding_vertical_bottom);
            this.handleWidth = CoreRune.IS_TABLET_DEVICE ? loadDimensionPixelSize(com.android.systemui.R.dimen.mw_handle_width_tablet) : loadDimensionPixelSize(com.android.systemui.R.dimen.mw_handle_width);
            setFocusable(false);
        }
    }

    public final void adjustHorizontalHandlePadding(int i) {
        int i2 = this.handleWidth;
        int i3 = i2 - (this.initHorizontalPadding * 2);
        int i4 = i > i3 ? (i - i3) / 2 : 0;
        setPadding(i4, getPaddingTop(), (i2 - i4) - i3, getPaddingBottom());
        this.isPaddingAdjusted = true;
    }

    public final void animateHandle(int i, long j) {
        if (this.handleAnimator.isRunning()) {
            this.handleAnimator.cancel();
        }
        this.handleAnimator.setDuration(j);
        this.handleAnimator.setIntValues(getPaddingLeft(), i);
        this.handleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.HandleImageButton.animateHandle.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                HandleImageButton handleImageButton = HandleImageButton.this;
                handleImageButton.setPadding(iIntValue, handleImageButton.getPaddingTop(), iIntValue, HandleImageButton.this.getPaddingBottom());
            }
        });
        this.handleAnimator.start();
    }

    public final int loadDimensionPixelSize(int i) {
        if (i == 0) {
            return 0;
        }
        return getContext().getResources().getDimensionPixelSize(i);
    }

    @Override // android.view.View
    public final void onHoverChanged(boolean z) {
        super.onHoverChanged(z);
        if (CoreRune.MW_CAPTION_HANDLE) {
            return;
        }
        if (z) {
            animateHandle(this.HANDLE_HOVER_ENTER_PADDING, 300L);
        } else {
            if (isPressed()) {
                return;
            }
            animateHandle(this.HANDLE_DEFAULT_PADDING, 300L);
        }
    }

    @Override // android.view.View
    public final void setPressed(boolean z) {
        if (isPressed() != z) {
            super.setPressed(z);
            if (CoreRune.MW_CAPTION_HANDLE) {
                return;
            }
            if (z) {
                animateHandle(this.HANDLE_PRESS_DOWN_PADDING, 200L);
            } else {
                animateHandle(this.HANDLE_DEFAULT_PADDING, 200L);
            }
        }
    }
}
