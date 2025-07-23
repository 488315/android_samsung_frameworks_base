package com.android.wm.shell.windowdecor.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.wm.shell.windowdecor.TaskFocusStateConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CaptionAnimationButton extends LottieAnimationView implements TaskFocusStateConsumer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ColorStateList mColorStateList;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsTaskFocused;

    public CaptionAnimationButton(Context context, AttributeSet attributeSet) {
        super(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight), attributeSet);
        this.mIsTaskFocused = true;
        this.mHandler = new Handler();
        this.mContext = context;
    }

    public final void applyIconColor(final ColorStateList colorStateList) {
        final int i = this.mIsTaskFocused ? 255 : 154;
        addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback(this) { // from class: com.android.wm.shell.windowdecor.widget.CaptionAnimationButton.1
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final Object getValue() {
                return new PorterDuffColorFilter(colorStateList.withAlpha(i).getDefaultColor(), PorterDuff.Mode.SRC_IN);
            }
        });
    }

    @Override // android.view.View
    public final void setContentDescription(CharSequence charSequence) {
        super.setContentDescription(charSequence);
        setTooltipText(charSequence);
    }

    @Override // com.android.wm.shell.windowdecor.TaskFocusStateConsumer
    public final void setTaskFocusState(boolean z) {
        if (this.mIsTaskFocused != z) {
            this.mIsTaskFocused = z;
            applyIconColor(this.mColorStateList);
        }
    }
}
