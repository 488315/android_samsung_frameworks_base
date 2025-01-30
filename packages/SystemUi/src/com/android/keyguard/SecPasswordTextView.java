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
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SecPasswordTextView extends PasswordTextView {
    public KeyguardSecPinViewController$$ExternalSyntheticLambda1 mClickCallback;
    public int mMaxLength;

    public SecPasswordTextView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.PasswordTextView
    public final void append(char c) {
        if (this.mTextChars.size() >= this.mMaxLength) {
            return;
        }
        super.append(c);
        KeyguardSecPinViewController$$ExternalSyntheticLambda1 keyguardSecPinViewController$$ExternalSyntheticLambda1 = this.mClickCallback;
        if (keyguardSecPinViewController$$ExternalSyntheticLambda1 != null) {
            keyguardSecPinViewController$$ExternalSyntheticLambda1.f$0.verifyNDigitsPIN();
        }
    }

    @Override // com.android.keyguard.PasswordTextView
    public final Rect getCharBounds() {
        this.mDrawPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.kg_pin_text_size));
        Rect rect = new Rect();
        this.mDrawPaint.getTextBounds(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, 0, 1, rect);
        return rect;
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

    @Override // com.android.keyguard.PasswordTextView
    public final void reset(boolean z, boolean z2) {
        super.reset(z, z2);
        if (z || !z2) {
            return;
        }
        sendAccessibilityEventTypeViewTextChanged("", 0, 0, 0);
    }

    @Override // com.android.systemui.widget.SystemUIEditText, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        Log.d("SecPasswordTextView", "updateStyle : " + KeyguardWallpaperColors.getChangeFlagsString(j) + " colors : " + semWallpaperColors);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        boolean isUltraPowerSavingMode = settingsHelper.isUltraPowerSavingMode();
        if (!(settingsHelper.isColorThemeEnabled$1() && (1024 & j) != 0) || isUltraPowerSavingMode) {
            super.updateStyle(j, semWallpaperColors);
            return;
        }
        if (semWallpaperColors == null) {
            Log.d("SecPasswordTextView", "updateStyle : colors is null!");
            return;
        }
        int colorThemeColor = semWallpaperColors.getColorThemeColor(SystemUIWidgetUtil.convertFlag("background"));
        Log.d("SecPasswordTextView", "updateStyle themeColor : #" + Integer.toHexString(colorThemeColor));
        Paint paint = this.mDrawPaint;
        if (colorThemeColor == 0) {
            colorThemeColor = EmergencyPhoneWidget.BG_COLOR;
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
        this.mMaxLength = 256;
        this.mDrawPaint.setTypeface(Typeface.create(Typeface.create(context.getString(R.string.password_textview_font_family), 0), 400, false));
    }
}
