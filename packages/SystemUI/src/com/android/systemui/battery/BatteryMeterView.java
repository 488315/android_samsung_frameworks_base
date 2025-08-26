package com.android.systemui.battery;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.BatteryManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.battery.unified.BatteryColors;
import com.android.systemui.battery.unified.BatteryDrawableState;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$styleable;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BatteryMeterView extends LinearLayout implements DarkIconDispatcher.DarkReceiver {
    public BatteryMeterViewController$$ExternalSyntheticLambda1 mBatteryEstimateFetcher;
    public float mBatteryIconDarkModeAlpha;
    public float mBatteryIconLightModeAlpha;
    public final ImageView mBatteryIconView;
    public SamsungBatteryState mBatteryState;
    public float mDarkIntensity;
    public ArrayList mDarkLastAreas;
    public int mDarkModeBackgroundColor;
    public int mDarkModeFillColor;
    public int mDarkTint;
    public final DualToneHandler mDualToneHandler;
    public int mGrayColor;
    public AnonymousClass1 mInvalidateRunnable;
    public boolean mIsBatteryDefender;
    public boolean mIsDarkReceiverRegistered;
    public boolean mIsDirectPowerMode;
    public boolean mIsGrayColor;
    public boolean mIsIncompatibleCharging;
    public int mLevel;
    public int mLightModeBackgroundColor;
    public int mLightModeFillColor;
    public int mNonAdaptedBackgroundColor;
    public int mNonAdaptedForegroundColor;
    public boolean mPowerSaveEnabled;
    public float mRatio;
    public SamsungBatteryMeterDrawable mSamsungDrawable;
    public int mShowPercentMode;
    public boolean mShowPercentSamsungSetting;
    public int mTextColor;
    public final BatteryColors.LightThemeColors mUnifiedBatteryColors;
    public final BatteryDrawableState mUnifiedBatteryState;
    public final Handler mUpdateBatteryStateHandler;
    public boolean showing;

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  BatteryMeterView:");
        printWriter.println("    mDrawable.getPowerSave: " + ((String) null));
        printWriter.println("    mDrawable.getDisplayShield: " + ((String) null));
        printWriter.println("    mDrawable.getCharging: " + ((String) null));
        printWriter.println("    mBatteryPercentView.getText(): " + ((Object) null));
        printWriter.println("    mTextColor: #" + Integer.toHexString(this.mTextColor));
        printWriter.println("    mBatteryStateUnknown: false");
        printWriter.println("    mIsIncompatibleCharging: " + this.mIsIncompatibleCharging);
        printWriter.println("    mPluggedIn: false");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mLevel: "), this.mLevel, printWriter, "    mMode: "), this.mShowPercentMode, printWriter, "    mIsDirectPowerMode: "), this.mIsDirectPowerMode, printWriter);
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = this.mSamsungDrawable;
        samsungBatteryMeterDrawable.getClass();
        printWriter.println("  SamsungBatteryMeterDrawable:");
        Paint paint = samsungBatteryMeterDrawable.batteryOuterPaint;
        boolean zIsAntiAlias = paint.isAntiAlias();
        int color = samsungBatteryMeterDrawable.batteryOuterPaint.getColor();
        StringBuilder sb = new StringBuilder("    batteryOuterPaint: ");
        sb.append(paint);
        sb.append(" isAntiAlias: ");
        sb.append(zIsAntiAlias);
        sb.append(" color ");
        MagnificationImpl$$ExternalSyntheticOutline0.m(sb, color, printWriter);
        Paint paint2 = samsungBatteryMeterDrawable.roundedRectPaint;
        boolean zIsAntiAlias2 = paint2.isAntiAlias();
        int color2 = samsungBatteryMeterDrawable.roundedRectPaint.getColor();
        StringBuilder sb2 = new StringBuilder("    roundedRectPaint: ");
        sb2.append(paint2);
        sb2.append(" isAntiAlias: ");
        sb2.append(zIsAntiAlias2);
        sb2.append(" color ");
        MagnificationImpl$$ExternalSyntheticOutline0.m(sb2, color2, printWriter);
        Paint paint3 = samsungBatteryMeterDrawable.batteryLevelBackgroundPaint;
        printWriter.println("    batteryLevelBackgroundPaint: " + paint3 + " isAntiAlias " + paint3.isAntiAlias() + " color" + samsungBatteryMeterDrawable.batteryLevelBackgroundPaint.getColor() + " xfermode: " + samsungBatteryMeterDrawable.batteryLevelBackgroundPaint.getXfermode());
        Paint paint4 = samsungBatteryMeterDrawable.batteryLevelPaint;
        printWriter.println("    batteryLevelPaint: " + paint4 + " isAntiAlias: " + paint4.isAntiAlias() + " isDither: " + samsungBatteryMeterDrawable.batteryLevelPaint.isDither() + " strokeWidth: " + samsungBatteryMeterDrawable.batteryLevelPaint.getStrokeWidth() + " style: " + samsungBatteryMeterDrawable.batteryLevelPaint.getStyle() + " color: " + samsungBatteryMeterDrawable.batteryLevelPaint.getColor() + " xfermode: " + samsungBatteryMeterDrawable.batteryLevelPaint.getXfermode());
        Paint paint5 = samsungBatteryMeterDrawable.textPaint;
        printWriter.println("    textPaint: " + paint5 + " isAntiAlias: " + paint5.isAntiAlias() + " textAlign: " + samsungBatteryMeterDrawable.textPaint.getTextAlign() + " xfermode: " + samsungBatteryMeterDrawable.textPaint.getXfermode() + " typeface: " + samsungBatteryMeterDrawable.textPaint.getTypeface() + " textSize: " + samsungBatteryMeterDrawable.textPaint.getTextSize());
    }

    public TextView getBatteryPercentView() {
        return null;
    }

    public CharSequence getBatteryPercentViewText() {
        throw null;
    }

    public BatteryDrawableState getUnifiedBatteryState() {
        return this.mUnifiedBatteryState;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isCharging() {
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        updateShowPercent();
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        this.mIsDarkReceiverRegistered = true;
        this.mDarkLastAreas = arrayList;
        this.mDarkIntensity = f;
        this.mDarkTint = i;
        onDarkChangedLegacy(arrayList, f, i);
    }

    public final void onDarkChangedLegacy(ArrayList arrayList, float f, int i) {
        float fM$1;
        boolean zIsInAreas = DarkIconDispatcher.isInAreas(arrayList, this);
        if (!zIsInAreas) {
            f = 0.0f;
        }
        this.mSamsungDrawable.darkIntensity = f;
        if (zIsInAreas) {
            float f2 = this.mBatteryIconLightModeAlpha;
            fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mBatteryIconDarkModeAlpha, f2, f, f2);
        } else {
            fM$1 = 0.74f;
        }
        if (this.mIsGrayColor) {
            this.mGrayColor = i;
            fM$1 = 1.0f;
        }
        this.mBatteryIconView.setAlpha(fM$1);
        int i2 = this.mLightModeFillColor;
        int i3 = this.mDarkModeFillColor;
        ArgbEvaluator argbEvaluator = ArgbEvaluator.sInstance;
        this.mNonAdaptedForegroundColor = ((Integer) argbEvaluator.evaluate(f, Integer.valueOf(i2), Integer.valueOf(i3))).intValue();
        this.mNonAdaptedBackgroundColor = ((Integer) argbEvaluator.evaluate(f, Integer.valueOf(this.mLightModeBackgroundColor), Integer.valueOf(this.mDarkModeBackgroundColor))).intValue();
        updateColors(this.mNonAdaptedForegroundColor, this.mNonAdaptedBackgroundColor, DarkIconDispatcher.getTint(arrayList, this, i));
    }

    public final void scaleBatteryMeterViewsLegacy() throws Resources.NotFoundException {
        Resources resources = getContext().getResources();
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.status_bar_icon_scale_factor, typedValue, true);
        typedValue.getFloat();
        float f = this.mRatio;
        float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_height) * f;
        float dimensionPixelSize2 = resources.getDimensionPixelSize(this.mSamsungDrawable.getBatteryWidthLevelBased()) * f;
        boolean z = this.mIsBatteryDefender;
        int i = BatterySpecs.$r8$clinit;
        float f2 = !z ? dimensionPixelSize : (dimensionPixelSize / 20.0f) * 23.0f;
        if (z) {
            dimensionPixelSize2 = (dimensionPixelSize2 / 12.0f) * 18.0f;
        }
        int iRound = z ? Math.round(f2 - dimensionPixelSize) - resources.getDimensionPixelSize(R.dimen.status_bar_battery_extra_vertical_spacing) : 0;
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.battery_margin_bottom);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(dimensionPixelSize2), Math.round(f2));
        layoutParams.setMargins(0, iRound, 0, dimensionPixelSize3);
        this.mBatteryIconView.setLayoutParams(layoutParams);
        this.mBatteryIconView.invalidateDrawable(this.mSamsungDrawable);
    }

    public final void setPercentShowMode(int i) throws Resources.NotFoundException {
        if (i == this.mShowPercentMode) {
            return;
        }
        this.mShowPercentMode = i;
        updateShowPercent();
        updatePercentText();
    }

    public final void updateColors(int i, int i2, int i3) {
        if (this.mIsGrayColor) {
            SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = this.mSamsungDrawable;
            int i4 = this.mGrayColor;
            samsungBatteryMeterDrawable.grayIconColor = i4;
            samsungBatteryMeterDrawable.batteryLevelBackgroundColor = Color.argb(Math.round(Color.alpha(i4) * SamsungBatteryMeterDrawable.BATTERY_BACKGROUND_ALPHA), Color.red(i4), Color.green(i4), Color.blue(i4));
            this.mSamsungDrawable.postInvalidate();
            return;
        }
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable2 = this.mSamsungDrawable;
        samsungBatteryMeterDrawable2.batteryLevelBackgroundLightColor = i2;
        samsungBatteryMeterDrawable2.batteryLevelBackgroundDarkColor = i2;
        samsungBatteryMeterDrawable2.batteryOuterBackgroundLightColor = i2;
        samsungBatteryMeterDrawable2.batteryOuterBackgroundDarkColor = i2;
        samsungBatteryMeterDrawable2.iconTint = i;
        samsungBatteryMeterDrawable2.batteryLevelColor = i;
        samsungBatteryMeterDrawable2.batteryLevelBackgroundPaint.setColor(SamsungBatteryMeterDrawable.getColorForDarkIntensity(samsungBatteryMeterDrawable2.darkIntensity, i2, i2));
        samsungBatteryMeterDrawable2.batteryOuterPaint.setColor(SamsungBatteryMeterDrawable.getColorForDarkIntensity(samsungBatteryMeterDrawable2.darkIntensity, samsungBatteryMeterDrawable2.batteryOuterBackgroundLightColor, samsungBatteryMeterDrawable2.batteryOuterBackgroundDarkColor));
        samsungBatteryMeterDrawable2.invalidateSelf();
        this.mTextColor = i3;
    }

    public final void updateContentDescription() {
        String string;
        Context context = getContext();
        if (this.mShowPercentMode != 3 || TextUtils.isEmpty(null)) {
            string = this.mIsBatteryDefender ? context.getString(R.string.accessibility_battery_level_charging_paused, Integer.valueOf(this.mLevel)) : isCharging() ? context.getString(R.string.accessibility_battery_level_charging, Integer.valueOf(this.mLevel)) : context.getString(R.string.accessibility_battery_level, Integer.valueOf(this.mLevel));
        } else {
            string = context.getString(this.mIsBatteryDefender ? R.string.accessibility_battery_level_charging_paused_with_estimate : R.string.accessibility_battery_level_with_estimate, Integer.valueOf(this.mLevel), null);
        }
        setContentDescription(string);
    }

    public final void updatePercentText() {
        if (this.mBatteryEstimateFetcher == null) {
            updateContentDescription();
        } else {
            updateContentDescription();
        }
    }

    public final void updateShowPercent() throws Resources.NotFoundException {
        int i;
        boolean z = this.mShowPercentSamsungSetting;
        boolean z2 = ((z && this.mShowPercentMode != 2) || (i = this.mShowPercentMode) == 1 || i == 3) && !this.mIsDirectPowerMode;
        this.mSamsungDrawable.setShowPercentSetting(z && !this.mIsDirectPowerMode);
        if (!z2) {
            if (this.showing) {
                this.mSamsungDrawable.postInvalidate();
                this.showing = false;
                return;
            }
            return;
        }
        if (this.showing) {
            return;
        }
        this.mSamsungDrawable.postInvalidate();
        updatePercentText();
        scaleBatteryMeterViewsLegacy();
        this.showing = true;
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.mShowPercentMode = 0;
        this.mShowPercentSamsungSetting = true;
        this.mIsDarkReceiverRegistered = false;
        this.mUnifiedBatteryColors = BatteryColors.LIGHT_THEME_COLORS;
        BatteryDrawableState.Companion.getClass();
        this.mUnifiedBatteryState = BatteryDrawableState.DefaultInitialState;
        this.mRatio = 1.0f;
        this.mUpdateBatteryStateHandler = new Handler();
        this.mInvalidateRunnable = null;
        this.showing = this.mShowPercentSamsungSetting;
        setOrientation(0);
        setGravity(8388627);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BatteryMeterView, i, 0);
        typedArrayObtainStyledAttributes.getColor(0, context.getColor(R.color.meter_background_color));
        typedArrayObtainStyledAttributes.getResourceId(1, 0);
        this.mSamsungDrawable = new SamsungBatteryMeterDrawable(context);
        this.mBatteryState = new SamsungBatteryState();
        this.mBatteryIconLightModeAlpha = context.getResources().getFloat(R.dimen.status_bar_battery_light_mode_alpha);
        this.mBatteryIconDarkModeAlpha = context.getResources().getFloat(R.dimen.status_bar_battery_dark_mode_alpha);
        this.mLightModeFillColor = context.getColor(R.color.status_bar_battery_frame_light_color);
        this.mDarkModeFillColor = context.getColor(R.color.status_bar_battery_frame_dark_color);
        this.mLightModeBackgroundColor = context.getColor(R.color.status_bar_battery_level_background_light_color);
        this.mDarkModeBackgroundColor = context.getColor(R.color.status_bar_battery_level_background_dark_color);
        typedArrayObtainStyledAttributes.recycle();
        context.getResources().getBoolean(android.R.bool.config_bg_prompt_abusive_apps_to_bg_restricted);
        this.mLevel = ((BatteryManager) context.getSystemService("batterymanager")).getIntProperty(4);
        ImageView imageView = new ImageView(context);
        this.mBatteryIconView = imageView;
        imageView.setImageDrawable(this.mSamsungDrawable);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        marginLayoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.battery_margin_bottom));
        addView(imageView, marginLayoutParams);
        updateShowPercent();
        this.mDualToneHandler = new DualToneHandler(context);
        onDarkChanged(new ArrayList(), 0.0f, -301989889);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(false);
    }
}
