package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;

public class SecPasswordTextView extends PasswordTextView {
    public KeyguardSecPinViewController$$ExternalSyntheticLambda1 mClickCallback;

    public SecPasswordTextView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.PasswordTextView
    public final Rect getCharBounds() {
        this.mDrawPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.kg_pin_text_size));
        Rect rect = new Rect();
        this.mDrawPaint.getTextBounds("0", 0, 1, rect);
        return rect;
    }

    @Override // com.android.keyguard.BaseSecPasswordTextView
    public final int getTextCharsSize() {
        return this.mTextChars.size();
    }

    @Override // com.android.keyguard.PasswordTextView
    public final void onAppend(char c, int i) {
        if (this.mTextChars.size() >= this.mMaxLength) {
            if (this.mClickCallback != null) {
                reset(false, false);
            }
        } else {
            super.onAppend(c, i);
            KeyguardSecPinViewController$$ExternalSyntheticLambda1 keyguardSecPinViewController$$ExternalSyntheticLambda1 = this.mClickCallback;
            if (keyguardSecPinViewController$$ExternalSyntheticLambda1 != null) {
                keyguardSecPinViewController$$ExternalSyntheticLambda1.f$0.verifyNDigitsPIN();
            }
        }
    }

    @Override // com.android.systemui.widget.SystemUIEditText, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int length = getFilters().length;
        for (int i = 0; i < length; i++) {
            InputFilter inputFilter = getFilters()[i];
            if (inputFilter instanceof InputFilter.LengthFilter) {
                this.mMaxLength = ((InputFilter.LengthFilter) inputFilter).getMax();
            }
        }
    }

    @Override // com.android.systemui.widget.SystemUIEditText, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        Log.d("SecPasswordTextView", "updateStyle : " + KeyguardWallpaperColors.getChangeFlagsString(j) + " colors : " + semWallpaperColors);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        boolean isUltraPowerSavingMode = settingsHelper.isUltraPowerSavingMode();
        if (!settingsHelper.isColorThemeEnabled() || (1024 & j) == 0 || isUltraPowerSavingMode) {
            super.updateStyle(j, semWallpaperColors);
            return;
        }
        if (semWallpaperColors == null) {
            Log.d("SecPasswordTextView", "updateStyle : colors is null!");
            return;
        }
        int colorThemeColor = semWallpaperColors.getColorThemeColor(512L);
        Log.d("SecPasswordTextView", "updateStyle themeColor : #" + Integer.toHexString(colorThemeColor));
        Paint paint = this.mDrawPaint;
        if (colorThemeColor == 0) {
            colorThemeColor = -16777216;
        }
        paint.setColor(colorThemeColor);
    }

    public SecPasswordTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecPasswordTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecPasswordTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDrawPaint.setTypeface(Typeface.create(Typeface.create(context.getString(R.string.password_textview_font_family), 0), 400, false));
    }
}
