package com.android.server.input.debug;

import android.R;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.util.Locale;

public final class RotaryInputValueView extends TextView {
    public static final ColorFilter ACTIVE_BACKGROUND_FILTER =
            new ColorMatrixColorFilter(
                    new float[] {
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        255.0f,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        255.0f,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        200.0f
                    });
    public final Locale mDefaultLocale;
    public final float mScaledVerticalScrollFactor;
    public final RotaryInputValueView$$ExternalSyntheticLambda0 mUpdateActivityStatusCallback;

    public RotaryInputValueView(Context context) {
        super(context);
        this.mUpdateActivityStatusCallback =
                new Runnable() { // from class:
                                 // com.android.server.input.debug.RotaryInputValueView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        RotaryInputValueView rotaryInputValueView = RotaryInputValueView.this;
                        rotaryInputValueView.setTextColor(-65281);
                        rotaryInputValueView.getBackground().clearColorFilter();
                    }
                };
        this.mDefaultLocale = Locale.getDefault();
        DisplayMetrics displayMetrics =
                ((TextView) this).mContext.getResources().getDisplayMetrics();
        this.mScaledVerticalScrollFactor =
                ViewConfiguration.get(context).getScaledVerticalScrollFactor();
        setText(getFormattedValue(FullScreenMagnificationGestureHandler.MAX_SCALE));
        setTextColor(-65281);
        setTextSize((int) TypedValue.applyDimension(2, 8, displayMetrics));
        float f = 4;
        setPaddingRelative(
                (int) TypedValue.applyDimension(2, f, displayMetrics),
                0,
                (int) TypedValue.applyDimension(2, f, displayMetrics),
                0);
        setTypeface(null, 1);
        setBackgroundResource(R.drawable.ic_ab_back_material);
    }

    public final String getFormattedValue(float f) {
        return String.format(
                this.mDefaultLocale,
                "%s%.1f",
                f < FullScreenMagnificationGestureHandler.MAX_SCALE
                        ? PackageManagerShellCommandDataLoader.STDIN_PATH
                        : "+",
                Float.valueOf(Math.abs(f)));
    }
}
