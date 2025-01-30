package com.android.wm.shell.windowdecor;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.RippleDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WindowMenuAnimationView extends LottieAnimationView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public final Handler mHandler;
    public boolean mIsNightMode;
    public boolean mIsTaskFocused;

    public WindowMenuAnimationView(Context context, AttributeSet attributeSet) {
        super(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight), attributeSet);
        this.mIsTaskFocused = true;
        this.mHandler = new Handler();
        this.mContext = context;
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.decor_button_padding);
        int dimensionPixelSize2 = (resources.getDimensionPixelSize(com.android.systemui.R.dimen.sec_decor_button_size) - resources.getDimensionPixelSize(com.android.systemui.R.dimen.sec_decor_button_ripple_size)) / 2;
        int abs = Math.abs(dimensionPixelSize - getPaddingBottom());
        RippleDrawable rippleDrawable = (RippleDrawable) this.mContext.getDrawable(com.android.systemui.R.drawable.sec_decor_caption_button_ripple);
        rippleDrawable.setLayerInset(0, dimensionPixelSize2, dimensionPixelSize2 - abs, dimensionPixelSize2, dimensionPixelSize2);
        if (getPaddingTop() < getPaddingBottom()) {
            rippleDrawable.setLayerInsetBottom(0, abs + dimensionPixelSize2);
        }
        if (getPaddingStart() > dimensionPixelSize) {
            rippleDrawable.setLayerInsetStart(0, (getPaddingStart() - dimensionPixelSize) + dimensionPixelSize2);
        }
        setBackground(rippleDrawable);
        applyIconColor();
    }

    public final void applyIconColor() {
        ColorStateList colorStateList;
        if (CaptionGlobalState.COLOR_THEME_ENABLED) {
            colorStateList = this.mContext.getResources().getColorStateList(17171332, null);
        } else {
            colorStateList = this.mContext.getResources().getColorStateList(this.mIsNightMode ? com.android.systemui.R.color.sec_decor_icon_color_dark : com.android.systemui.R.color.sec_decor_icon_color_light, null);
        }
        final ColorStateList withAlpha = colorStateList.withAlpha(this.mIsTaskFocused ? 255 : 154);
        addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback(this) { // from class: com.android.wm.shell.windowdecor.WindowMenuAnimationView.1
            @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
            public final Object getValue() {
                return new PorterDuffColorFilter(withAlpha.getDefaultColor(), PorterDuff.Mode.SRC_IN);
            }
        });
    }

    public final void createLottieTask(final String str) {
        Slog.d("WindowMenuAnimationView", "createLottieTask: asset=".concat(str));
        final LottieTask fromAsset = LottieCompositionFactory.fromAsset(this.mContext, str);
        fromAsset.addListener(new LottieListener() { // from class: com.android.wm.shell.windowdecor.WindowMenuAnimationView$$ExternalSyntheticLambda0
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                final LottieComposition lottieComposition = (LottieComposition) obj;
                int i = WindowMenuAnimationView.$r8$clinit;
                final WindowMenuAnimationView windowMenuAnimationView = WindowMenuAnimationView.this;
                windowMenuAnimationView.getClass();
                StringBuilder sb = new StringBuilder("createLottieTask: onResult, asset=");
                final String str2 = str;
                sb.append(str2);
                Slog.d("WindowMenuAnimationView", sb.toString());
                Handler handler = windowMenuAnimationView.mHandler;
                final LottieTask lottieTask = fromAsset;
                handler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.WindowMenuAnimationView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowMenuAnimationView windowMenuAnimationView2 = windowMenuAnimationView;
                        LottieTask lottieTask2 = lottieTask;
                        String str3 = str2;
                        LottieComposition lottieComposition2 = lottieComposition;
                        int i2 = WindowMenuAnimationView.$r8$clinit;
                        windowMenuAnimationView2.getClass();
                        Slog.d("WindowMenuAnimationView", "createLottieTask: setComposition, lottieTask=" + lottieTask2 + ", asset=" + str3);
                        try {
                            Field declaredField = LottieTask.class.getDeclaredField("handler");
                            declaredField.setAccessible(true);
                            declaredField.set(lottieTask2, new Handler(Looper.myLooper()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        windowMenuAnimationView2.setComposition(lottieComposition2);
                    }
                });
            }
        });
        fromAsset.addFailureListener(new LottieListener() { // from class: com.android.wm.shell.windowdecor.WindowMenuAnimationView$$ExternalSyntheticLambda1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                int i = WindowMenuAnimationView.$r8$clinit;
                Slog.e("WindowMenuAnimationView", "createLottieTask: Unable to parse json composition : " + str);
            }
        });
    }

    @Override // android.view.View
    public final void setContentDescription(CharSequence charSequence) {
        super.setContentDescription(charSequence);
        setTooltipText(charSequence);
    }

    public final void updateNightMode(boolean z) {
        if (this.mIsNightMode != z) {
            this.mIsNightMode = z;
            if (z != this.mContext.getResources().getConfiguration().isNightModeActive()) {
                Configuration configuration = new Configuration(this.mContext.getResources().getConfiguration());
                configuration.uiMode = ((this.mIsNightMode ? 32 : 16) & 48) | (configuration.uiMode & (-49));
                this.mContext = this.mContext.createConfigurationContext(configuration);
            }
            applyIconColor();
        }
    }
}
