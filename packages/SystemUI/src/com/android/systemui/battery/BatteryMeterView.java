package com.android.systemui.battery;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.BatteryManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.flags.Flags;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R;
import com.android.systemui.battery.unified.BatteryColors;
import com.android.systemui.battery.unified.BatteryDrawableState;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$styleable;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BatteryMeterView extends LinearLayout implements DarkIconDispatcher.DarkReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BatteryMeterViewController$$ExternalSyntheticLambda1 mBatteryEstimateFetcher;
    public final float mBatteryIconDarkModeAlpha;
    public final float mBatteryIconLightModeAlpha;
    public final ImageView mBatteryIconView;
    public SamsungBatteryState mBatteryState;
    public final int mDarkModeBackgroundColor;
    public final int mDarkModeFillColor;
    public boolean mDisplayShieldEnabled;
    public final DualToneHandler mDualToneHandler;
    public int mGrayColor;
    public boolean mIsBatteryDefender;
    public boolean mIsDirectPowerMode;
    public boolean mIsGrayColor;
    public boolean mIsIncompatibleCharging;
    public int mLevel;
    public final int mLightModeBackgroundColor;
    public final int mLightModeFillColor;
    public int mNonAdaptedBackgroundColor;
    public int mNonAdaptedForegroundColor;
    public boolean mPowerSaveEnabled;
    public float mRatio;
    public final SamsungBatteryMeterDrawable mSamsungDrawable;
    public int mShowPercentMode;
    public boolean mShowPercentSamsungSetting;
    public int mTextColor;
    public final BatteryColors.LightThemeColors mUnifiedBatteryColors;
    public final BatteryDrawableState mUnifiedBatteryState;
    public boolean showing;

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  BatteryMeterView:");
        printWriter.println("    mDrawable.getPowerSave: null");
        printWriter.println("    mDrawable.getDisplayShield: null");
        printWriter.println("    mDrawable.getCharging: null");
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "    mBatteryPercentView.getText(): null", "    mTextColor: #");
        m.append(Integer.toHexString(this.mTextColor));
        printWriter.println(m.toString());
        printWriter.println("    mBatteryStateUnknown: false");
        printWriter.println("    mIsIncompatibleCharging: " + this.mIsIncompatibleCharging);
        printWriter.println("    mPluggedIn: false");
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mLevel: "), this.mLevel, printWriter, "    mMode: ");
        m2.append(this.mShowPercentMode);
        printWriter.println(m2.toString());
        Flags.newStatusBarIcons();
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("    mIsDirectPowerMode: "), this.mIsDirectPowerMode, printWriter);
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
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateShowPercent();
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        float f2;
        Flags.newStatusBarIcons();
        boolean isInAreas = DarkIconDispatcher.isInAreas(arrayList, this);
        if (!isInAreas) {
            f = 0.0f;
        }
        this.mSamsungDrawable.darkIntensity = f;
        if (isInAreas) {
            float f3 = this.mBatteryIconLightModeAlpha;
            f2 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mBatteryIconDarkModeAlpha, f3, f, f3);
        } else {
            f2 = 0.74f;
        }
        if (this.mIsGrayColor) {
            this.mGrayColor = i;
            f2 = 1.0f;
        }
        this.mBatteryIconView.setAlpha(f2);
        int i2 = this.mLightModeFillColor;
        int i3 = this.mDarkModeFillColor;
        ArgbEvaluator argbEvaluator = ArgbEvaluator.sInstance;
        this.mNonAdaptedForegroundColor = ((Integer) argbEvaluator.evaluate(f, Integer.valueOf(i2), Integer.valueOf(i3))).intValue();
        ((Integer) argbEvaluator.evaluate(f, Integer.valueOf(this.mLightModeBackgroundColor), Integer.valueOf(this.mDarkModeBackgroundColor))).intValue();
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        int i4 = this.mNonAdaptedForegroundColor;
        if (this.mIsGrayColor) {
            SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = this.mSamsungDrawable;
            int i5 = this.mGrayColor;
            samsungBatteryMeterDrawable.grayIconColor = i5;
            samsungBatteryMeterDrawable.batteryLevelBackgroundColor = Color.argb(Math.round(Color.alpha(i5) * SamsungBatteryMeterDrawable.BATTERY_BACKGROUND_ALPHA), Color.red(i5), Color.green(i5), Color.blue(i5));
            this.mSamsungDrawable.postInvalidate();
            return;
        }
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable2 = this.mSamsungDrawable;
        samsungBatteryMeterDrawable2.iconTint = i4;
        samsungBatteryMeterDrawable2.batteryLevelColor = i4;
        Paint paint = samsungBatteryMeterDrawable2.batteryLevelBackgroundPaint;
        float f4 = samsungBatteryMeterDrawable2.darkIntensity;
        int i6 = samsungBatteryMeterDrawable2.batteryLevelBackgroundLightColor;
        int i7 = samsungBatteryMeterDrawable2.batteryLevelBackgroundDarkColor;
        ArgbEvaluator argbEvaluator2 = ArgbEvaluator.sInstance;
        paint.setColor(((Integer) argbEvaluator2.evaluate(f4, Integer.valueOf(i6), Integer.valueOf(i7))).intValue());
        samsungBatteryMeterDrawable2.batteryOuterPaint.setColor(((Integer) argbEvaluator2.evaluate(samsungBatteryMeterDrawable2.darkIntensity, Integer.valueOf(samsungBatteryMeterDrawable2.batteryOuterBackgroundLightColor), Integer.valueOf(samsungBatteryMeterDrawable2.batteryOuterBackgroundDarkColor))).intValue());
        samsungBatteryMeterDrawable2.invalidateSelf();
        this.mTextColor = tint;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public final void scaleBatteryMeterViewsLegacy() {
        Resources resources = getContext().getResources();
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.status_bar_icon_scale_factor, typedValue, true);
        typedValue.getFloat();
        float f = this.mRatio;
        float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_height) * f;
        float dimensionPixelSize2 = resources.getDimensionPixelSize(this.mSamsungDrawable.shouldDrawIcon() ? R.dimen.samsung_status_bar_battery_icon_width_extended : R.dimen.samsung_status_bar_battery_icon_width) * f;
        boolean z = this.mDisplayShieldEnabled && this.mIsBatteryDefender;
        int i = BatterySpecs.$r8$clinit;
        float f2 = !z ? dimensionPixelSize : (dimensionPixelSize / 20.0f) * 23.0f;
        if (z) {
            dimensionPixelSize2 = (dimensionPixelSize2 / 12.0f) * 18.0f;
        }
        int round = z ? Math.round(f2 - dimensionPixelSize) - resources.getDimensionPixelSize(R.dimen.status_bar_battery_extra_vertical_spacing) : 0;
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.battery_margin_bottom);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(dimensionPixelSize2), Math.round(f2));
        layoutParams.setMargins(0, round, 0, dimensionPixelSize3);
        this.mBatteryIconView.setLayoutParams(layoutParams);
        this.mBatteryIconView.invalidateDrawable(this.mSamsungDrawable);
    }

    public final void setPercentShowMode(int i) {
        if (i == this.mShowPercentMode) {
            return;
        }
        this.mShowPercentMode = i;
        updateShowPercent();
        updatePercentText();
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
        Flags.newStatusBarIcons();
        if (this.mBatteryEstimateFetcher == null) {
            updateContentDescription();
        } else {
            updateContentDescription();
        }
    }

    public final void updateShowPercent() {
        int i;
        Flags.newStatusBarIcons();
        if (!((this.mShowPercentSamsungSetting && this.mShowPercentMode != 2) || (i = this.mShowPercentMode) == 1 || i == 3) || this.mIsDirectPowerMode) {
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
        Flags.newStatusBarIcons();
        scaleBatteryMeterViewsLegacy();
        this.showing = true;
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowPercentMode = 0;
        BatteryColors.LightThemeColors lightThemeColors = BatteryColors.LIGHT_THEME_COLORS;
        BatteryDrawableState.Companion.getClass();
        this.mUnifiedBatteryState = BatteryDrawableState.DefaultInitialState;
        this.mRatio = 1.0f;
        this.showing = false;
        setOrientation(0);
        setGravity(8388627);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BatteryMeterView, i, 0);
        obtainStyledAttributes.getColor(0, context.getColor(R.color.meter_background_color));
        obtainStyledAttributes.getResourceId(1, 0);
        SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = new SamsungBatteryMeterDrawable(context);
        this.mSamsungDrawable = samsungBatteryMeterDrawable;
        this.mBatteryState = new SamsungBatteryState();
        this.mBatteryIconLightModeAlpha = context.getResources().getFloat(R.dimen.status_bar_battery_light_mode_alpha);
        this.mBatteryIconDarkModeAlpha = context.getResources().getFloat(R.dimen.status_bar_battery_dark_mode_alpha);
        this.mLightModeFillColor = context.getColor(R.color.status_bar_battery_frame_light_color);
        this.mDarkModeFillColor = context.getColor(R.color.status_bar_battery_frame_dark_color);
        this.mLightModeBackgroundColor = context.getColor(R.color.light_mode_battery_icon_color_dual_tone_background);
        this.mDarkModeBackgroundColor = context.getColor(R.color.dark_mode_battery_icon_color_dual_tone_background);
        obtainStyledAttributes.recycle();
        context.getResources().getBoolean(android.R.bool.config_bg_current_drain_event_duration_based_threshold_enabled);
        this.mLevel = ((BatteryManager) context.getSystemService("batterymanager")).getIntProperty(4);
        ImageView imageView = new ImageView(context);
        this.mBatteryIconView = imageView;
        imageView.setImageDrawable(samsungBatteryMeterDrawable);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        marginLayoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.battery_margin_bottom));
        addView(imageView, marginLayoutParams);
        updateShowPercent();
        this.mDualToneHandler = new DualToneHandler(context);
        onDarkChanged(new ArrayList(), 0.0f, -301989889);
        setClipChildren(false);
        setClipToPadding(false);
        setWillNotDraw(false);
    }
}
