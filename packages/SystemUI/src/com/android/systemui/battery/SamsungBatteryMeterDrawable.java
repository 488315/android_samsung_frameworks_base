package com.android.systemui.battery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.NumberFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.ThreadedRenderer;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.power.utils.BatteryProtectionUtils;
import com.android.systemui.util.DeviceType;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SamsungBatteryMeterDrawable extends Drawable {
    public static final float BATTERY_BACKGROUND_ALPHA;
    public static final int BLINKING_INTERVAL;
    public static final boolean DEBUG;
    public static final int MSG_POST_INVALIDATE;
    public final int additionalWidth;
    public int batteryLevelBackgroundColor;
    public int batteryLevelBackgroundDarkColor;
    public int batteryLevelBackgroundLightColor;
    public int batteryLevelColor;
    public int batteryOuterBackgroundDarkColor;
    public int batteryOuterBackgroundLightColor;
    public SamsungBatteryState batteryState;
    public final int[] colors;
    public final Context context;
    public final float cornerRadius;
    public float darkIntensity;
    public boolean flagBlinkingNeeded;
    public boolean flagDrawIcon;
    public final float fontSize;
    public int grayIconColor;
    public final int height;
    public final int icongap;
    public int intrinsicHeight;
    public int intrinsicWidth;
    public final AccessibilityManager mAccessibilityManager;
    public final SamsungBatteryMeterDrawable$postInvalidateHandler$1 postInvalidateHandler;
    public boolean powerSaveEnabled;
    public boolean shouldShowGrayIcon;
    public boolean showPercentSetting;
    public boolean showWarningText;
    public final float sideMargin;
    public float textMeasuredWidth;
    public final Paint warningTextPaint;
    public final Paint batteryLevelPaint = new Paint();
    public final Paint batteryLevelBackgroundPaint = new Paint();
    public final Paint batteryOuterPaint = new Paint();
    public final Paint roundedRectPaint = new Paint();
    public final Paint textPaint = new Paint();
    public final int SINGLE_LEVEL = 10;
    public final int MAX_LEVEL = 100;
    public int iconTint = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DEBUG = DeviceType.isEngOrUTBinary();
        MSG_POST_INVALIDATE = 1;
        BLINKING_INTERVAL = 1000;
        BATTERY_BACKGROUND_ALPHA = 0.35f;
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.battery.SamsungBatteryMeterDrawable$postInvalidateHandler$1] */
    public SamsungBatteryMeterDrawable(Context context) {
        TypedArray typedArray;
        int length;
        this.context = context;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_height);
        this.height = dimensionPixelSize;
        context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_width);
        this.additionalWidth = context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_additional_width);
        this.icongap = context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_gap);
        float f = dimensionPixelSize;
        this.fontSize = 0.78f * f;
        this.cornerRadius = f * 0.5f;
        this.sideMargin = (float) (context.getResources().getDisplayMetrics().density * 1.5d);
        this.warningTextPaint = new Paint(1);
        this.batteryState = new SamsungBatteryState();
        this.darkIntensity = -1.0f;
        this.showPercentSetting = true;
        this.grayIconColor = -1;
        this.batteryLevelBackgroundColor = -1;
        this.flagDrawIcon = true;
        final Looper mainLooper = Looper.getMainLooper();
        this.postInvalidateHandler = new Handler(mainLooper) { // from class: com.android.systemui.battery.SamsungBatteryMeterDrawable$postInvalidateHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == SamsungBatteryMeterDrawable.MSG_POST_INVALIDATE) {
                    SamsungBatteryMeterDrawable samsungBatteryMeterDrawable = SamsungBatteryMeterDrawable.this;
                    if (samsungBatteryMeterDrawable.flagBlinkingNeeded) {
                        samsungBatteryMeterDrawable.flagDrawIcon = !samsungBatteryMeterDrawable.flagDrawIcon;
                    }
                    samsungBatteryMeterDrawable.postInvalidate();
                }
            }
        };
        Resources resources = context.getResources();
        TypedArray typedArray2 = null;
        try {
            TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.batterymeter_color_levels);
            try {
                typedArray = resources.obtainTypedArray(R.array.batterymeter_color_values);
                if (obtainTypedArray != null) {
                    try {
                        length = obtainTypedArray.length();
                    } catch (Throwable th) {
                        th = th;
                        typedArray2 = obtainTypedArray;
                        if (typedArray2 != null) {
                            typedArray2.recycle();
                        }
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                } else {
                    length = 0;
                }
                this.colors = new int[length * 2];
                for (int i = 0; i < length; i++) {
                    int[] iArr = this.colors;
                    int i2 = i * 2;
                    (iArr == null ? null : iArr)[i2] = obtainTypedArray != null ? obtainTypedArray.getInt(i, 0) : 0;
                    if (typedArray == null || typedArray.getType(i) != 2) {
                        int[] iArr2 = this.colors;
                        (iArr2 == null ? null : iArr2)[i2 + 1] = typedArray != null ? typedArray.getColor(i, 0) : 0;
                    } else {
                        int[] iArr3 = this.colors;
                        (iArr3 == null ? null : iArr3)[i2 + 1] = Utils.getColorAttrDefaultColor(this.context, typedArray.getThemeAttributeId(i, 0), 0);
                    }
                }
                if (obtainTypedArray != null) {
                    obtainTypedArray.recycle();
                }
                if (typedArray != null) {
                    typedArray.recycle();
                }
                Resources resources2 = this.context.getResources();
                int color = resources2.getColor(R.color.status_bar_battery_frame_light_color, null);
                resources2.getColor(R.color.status_bar_battery_frame_dark_color, null);
                this.batteryLevelColor = color;
                this.batteryLevelBackgroundLightColor = resources2.getColor(R.color.status_bar_battery_level_background_light_color, null);
                this.batteryLevelBackgroundDarkColor = resources2.getColor(R.color.status_bar_battery_level_background_dark_color, null);
                this.batteryOuterBackgroundLightColor = resources2.getColor(R.color.status_bar_battery_level_outer_light_color, null);
                this.batteryOuterBackgroundDarkColor = resources2.getColor(R.color.status_bar_battery_level_outer_dark_color, null);
                Paint paint = this.batteryLevelPaint;
                paint.setAntiAlias(true);
                paint.setDither(true);
                paint.setStrokeWidth(0.0f);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setColor(this.batteryLevelColor);
                PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                paint.setXfermode(new PorterDuffXfermode(mode));
                Paint paint2 = this.batteryOuterPaint;
                paint2.setAntiAlias(true);
                paint2.setColor(this.batteryOuterBackgroundLightColor);
                Paint paint3 = this.batteryLevelBackgroundPaint;
                paint3.setAntiAlias(true);
                paint3.setColor(this.batteryLevelBackgroundLightColor);
                paint3.setXfermode(new PorterDuffXfermode(mode));
                Paint paint4 = this.roundedRectPaint;
                paint4.setAntiAlias(true);
                paint4.setColor(-1);
                Typeface create = Typeface.create(Typeface.create("sec", 0), KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, false);
                Paint paint5 = this.textPaint;
                paint5.setAntiAlias(true);
                Paint.Align align = Paint.Align.CENTER;
                paint5.setTextAlign(align);
                paint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                paint5.setTypeface(create);
                paint5.setTextSize(this.fontSize);
                Paint paint6 = this.warningTextPaint;
                paint6.setTypeface(create);
                paint6.setTextAlign(align);
                paint6.setTextSize(this.fontSize);
                int[] iArr4 = this.colors;
                if ((iArr4 == null ? null : iArr4).length > 1) {
                    this.warningTextPaint.setColor((iArr4 != null ? iArr4 : null)[1]);
                }
                resizeDrawable();
            } catch (Throwable th2) {
                th = th2;
                typedArray = null;
            }
        } catch (Throwable th3) {
            th = th3;
            typedArray = null;
        }
    }

    public static int getColorForDarkIntensity(float f, int i, int i2) {
        return ((Integer) ArgbEvaluator.sInstance.evaluate(f, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        SamsungBatteryState samsungBatteryState = this.batteryState;
        if (!samsungBatteryState.isDirectPowerMode && samsungBatteryState.batteryStatus == 4 && ((i4 = samsungBatteryState.batteryHealth) == 3 || i4 == 7 || i4 == SamsungBatteryState.BATTERY_HEALTH_OVERHEAT_LIMIT || i4 == 6)) {
            this.flagBlinkingNeeded = true;
            if (DEBUG) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("battery icon blink for battery health... mFlagDrawIconTurn:", "SamsungBatteryMeterDrawable", this.flagDrawIcon);
            }
            SamsungBatteryMeterDrawable$postInvalidateHandler$1 samsungBatteryMeterDrawable$postInvalidateHandler$1 = this.postInvalidateHandler;
            int i5 = MSG_POST_INVALIDATE;
            if (!samsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i5)) {
                sendEmptyMessageDelayed(i5, BLINKING_INTERVAL);
            }
            z = !this.flagDrawIcon;
        } else {
            this.flagBlinkingNeeded = false;
            z = false;
        }
        if (z) {
            return;
        }
        SamsungBatteryState samsungBatteryState2 = this.batteryState;
        boolean z2 = samsungBatteryState2.isDirectPowerMode;
        if ((z2 ? 100 : samsungBatteryState2.level) == -1) {
            return;
        }
        int i6 = z2 ? 100 : samsungBatteryState2.level;
        int i7 = this.intrinsicWidth;
        int i8 = this.intrinsicHeight;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap createBitmap = Bitmap.createBitmap(i7, i8, config);
        Canvas canvas2 = new Canvas(createBitmap);
        int i9 = (int) (this.intrinsicWidth * (i6 >= 96 ? 1.0f : i6 <= 4 ? 0.0f : i6 / 100.0f));
        boolean z3 = i6 <= 4 && !shouldDrawIcon();
        this.showWarningText = z3;
        if (this.showPercentSetting || z3) {
            i = i9;
            i2 = 16;
            float f = this.intrinsicWidth;
            float f2 = this.intrinsicHeight;
            float f3 = this.cornerRadius;
            canvas2.drawRoundRect(0.0f, 0.0f, f, f2, f3, f3, this.roundedRectPaint);
            if (this.shouldShowGrayIcon) {
                this.batteryLevelBackgroundPaint.setColor(this.batteryLevelBackgroundColor);
            }
            if (this.showWarningText && !this.showPercentSetting) {
                this.batteryLevelBackgroundPaint.setColor(getColorForLevel(this.MAX_LEVEL));
            }
            int i10 = this.batteryState.level;
            if (i10 <= 4 || i10 >= 16) {
                if (isRtl()) {
                    canvas2.drawRect(this.intrinsicWidth - i, 0.0f, 0.0f, this.intrinsicHeight, this.batteryLevelBackgroundPaint);
                } else {
                    canvas2.drawRect(i, 0.0f, this.intrinsicWidth, this.intrinsicHeight, this.batteryLevelBackgroundPaint);
                }
            } else if (isRtl()) {
                canvas2.drawRect(this.intrinsicWidth * 0.85f, 0.0f, 0.0f, this.intrinsicHeight, this.batteryLevelBackgroundPaint);
            } else {
                float f4 = this.intrinsicWidth;
                canvas2.drawRect(f4 * 0.15f, 0.0f, f4, this.intrinsicHeight, this.batteryLevelBackgroundPaint);
            }
            if (this.showWarningText && !this.showPercentSetting) {
                this.batteryLevelBackgroundPaint.setColor(getColorForDarkIntensity(this.darkIntensity, this.batteryLevelBackgroundLightColor, this.batteryLevelBackgroundDarkColor));
            }
        } else {
            float f5 = this.intrinsicWidth;
            float f6 = this.intrinsicHeight;
            float f7 = this.cornerRadius;
            i2 = 16;
            i = i9;
            canvas2.drawRoundRect(0.0f, 0.0f, f5, f6, f7, f7, this.batteryOuterPaint);
        }
        if (this.showPercentSetting || this.showWarningText) {
            this.batteryLevelPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        } else {
            canvas2.save();
            Path path = new Path();
            path.reset();
            float f8 = this.sideMargin;
            float f9 = this.cornerRadius;
            path.addRoundRect(f8, f8, this.intrinsicWidth - f8, this.intrinsicHeight - f8, f9, f9, Path.Direction.CW);
            canvas2.clipPath(path);
            this.batteryLevelPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        }
        Paint paint = this.batteryLevelPaint;
        SamsungBatteryState samsungBatteryState3 = this.batteryState;
        paint.setColor(getColorForLevel(samsungBatteryState3.isDirectPowerMode ? 100 : samsungBatteryState3.level));
        if (!this.shouldShowGrayIcon || ((i3 = this.batteryState.level) > 4 && i3 < i2)) {
            SamsungBatteryState samsungBatteryState4 = this.batteryState;
            int i11 = samsungBatteryState4.level;
            if (i11 > 4 && i11 < i2 && !samsungBatteryState4.charging) {
                this.batteryLevelPaint.setColor(this.context.getResources().getColor(R.color.status_bar_battery_fixed_level_color, null));
            }
        } else {
            this.batteryLevelPaint.setColor(this.grayIconColor);
        }
        int i12 = this.batteryState.level;
        if (i12 <= 4 || i12 >= i2) {
            if (isRtl()) {
                float f10 = this.intrinsicWidth;
                canvas2.drawRect(f10, 0.0f, f10 - i, this.intrinsicHeight, this.batteryLevelPaint);
            } else {
                canvas2.drawRect(0.0f, 0.0f, i, this.intrinsicHeight, this.batteryLevelPaint);
            }
        } else if (isRtl()) {
            float f11 = this.intrinsicWidth;
            canvas2.drawRect(f11, 0.0f, f11 * 0.85f, this.intrinsicHeight, this.batteryLevelPaint);
        } else {
            canvas2.drawRect(0.0f, 0.0f, this.intrinsicWidth * 0.15f, this.intrinsicHeight, this.batteryLevelPaint);
        }
        if (!this.showPercentSetting && !this.showWarningText) {
            canvas2.restore();
        }
        if (this.showPercentSetting) {
            String valueOf = String.valueOf(i6);
            Paint paint2 = this.textPaint;
            Rect textBounds = getTextBounds(valueOf);
            this.textMeasuredWidth = paint2.measureText(valueOf);
            float height = (textBounds.height() / 2.0f) + (this.intrinsicHeight / 2.0f);
            int i13 = textBounds.bottom;
            float f12 = (height - i13) - (i13 != 0 ? 0.0f : 1.0f);
            float textOriginX = getTextOriginX(textBounds);
            boolean isHighContrastTextEnabled = this.mAccessibilityManager.isHighContrastTextEnabled();
            if (isHighContrastTextEnabled) {
                ThreadedRenderer.setHighContrastText(false);
            }
            Locale locale = this.context.getResources().getConfiguration().locale;
            NumberFormat numberFormat = NumberFormat.getInstance(locale);
            if (Intrinsics.areEqual(locale.toString(), "my_MM")) {
                canvas2.drawText(valueOf, textOriginX, f12, paint2);
            } else {
                canvas2.drawText(numberFormat.format(Integer.valueOf(Integer.parseInt(valueOf))), textOriginX, f12, paint2);
            }
            if (isHighContrastTextEnabled) {
                ThreadedRenderer.setHighContrastText(true);
            }
        }
        if (shouldDrawIcon() || this.showWarningText) {
            Drawable drawable = this.flagBlinkingNeeded ? this.showPercentSetting ? this.context.getResources().getDrawable(R.drawable.stat_sys_power_saving_mode, null) : this.context.getResources().getDrawable(R.drawable.stat_sys_power_saving_mode_percentage_off, null) : isBatteryProtectionWorking() ? this.showPercentSetting ? this.context.getResources().getDrawable(R.drawable.stat_sys_battery_protection, null) : this.context.getResources().getDrawable(R.drawable.stat_sys_battery_protection_percentage_off, null) : this.batteryState.shouldShowChargingIcon() ? this.showPercentSetting ? this.context.getResources().getDrawable(R.drawable.stat_sys_battery_charging, null) : this.context.getResources().getDrawable(R.drawable.stat_sys_battery_charging_percentage_off, null) : this.showWarningText ? this.showPercentSetting ? this.context.getResources().getDrawable(R.drawable.stat_sys_battery_warning, null) : this.context.getResources().getDrawable(R.drawable.stat_sys_battery_warning_percentage_off, null) : this.showPercentSetting ? this.context.getResources().getDrawable(R.drawable.stat_sys_power_saving_mode, null) : this.context.getResources().getDrawable(R.drawable.stat_sys_power_saving_mode_percentage_off, null);
            Bitmap createBitmap2 = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), config);
            Canvas canvas3 = new Canvas(createBitmap2);
            canvas3.save();
            drawable.setBounds(0, 0, canvas3.getWidth(), canvas3.getHeight());
            drawable.draw(canvas3);
            canvas3.restore();
            Rect chargingIconBounds = getChargingIconBounds();
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(createBitmap2, chargingIconBounds.width(), chargingIconBounds.height(), false);
            if (this.showWarningText) {
                canvas2.drawBitmap(createScaledBitmap, chargingIconBounds.left, chargingIconBounds.top, this.warningTextPaint);
            } else {
                canvas2.drawBitmap(createScaledBitmap, chargingIconBounds.left, chargingIconBounds.top, this.textPaint);
            }
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.context.getResources(), createBitmap);
        Paint paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setFilterBitmap(true);
        canvas.drawBitmap(bitmapDrawable.getBitmap(), 0.0f, 0.0f, paint3);
    }

    public final int getBatteryWidthLevelBased() {
        if (!this.showPercentSetting) {
            return R.dimen.samsung_status_bar_battery_icon_width;
        }
        int i = this.batteryState.level;
        if (!shouldDrawIcon()) {
            return i == this.MAX_LEVEL ? R.dimen.samsung_status_bar_battery_icon_max_width : R.dimen.samsung_status_bar_battery_icon_width;
        }
        int i2 = this.MAX_LEVEL;
        return i == i2 ? R.dimen.samsung_status_bar_battery_icon_max_width_extended : (i < this.SINGLE_LEVEL || i >= i2) ? R.dimen.samsung_status_bar_battery_icon_width : R.dimen.samsung_status_bar_battery_icon_width_extended;
    }

    public final Rect getChargingIconBounds() {
        int dimensionPixelSize = this.showPercentSetting ? this.context.getResources().getDimensionPixelSize(R.dimen.drawable_icon_width_battery_percent_on) : this.context.getResources().getDimensionPixelSize(R.dimen.drawable_icon_width_battery_percent_off);
        int dimensionPixelSize2 = this.showPercentSetting ? this.context.getResources().getDimensionPixelSize(R.dimen.drawable_icon_height_battery_percent_on) : this.context.getResources().getDimensionPixelSize(R.dimen.drawable_icon_height_battery_percent_off);
        int i = this.intrinsicWidth;
        int i2 = ((((i - dimensionPixelSize) - this.icongap) - this.additionalWidth) - ((int) this.textMeasuredWidth)) / 2;
        int i3 = (this.intrinsicHeight - dimensionPixelSize2) / 2;
        if (!this.showPercentSetting) {
            i2 = (i / 2) - (dimensionPixelSize / 2);
        }
        if (isRtl()) {
            i2 = ((((this.intrinsicWidth - dimensionPixelSize) + ((int) this.textMeasuredWidth)) + this.icongap) + this.additionalWidth) / 2;
        }
        return new Rect(i2, i3, dimensionPixelSize + i2, dimensionPixelSize2 + i3);
    }

    public final int getColorForLevel(int i) {
        SamsungBatteryState samsungBatteryState = this.batteryState;
        if (samsungBatteryState.pluggedIn && samsungBatteryState.charging) {
            return this.batteryLevelColor;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.colors;
            if (i2 >= (iArr == null ? null : iArr).length) {
                break;
            }
            int i4 = (iArr == null ? null : iArr)[i2];
            int i5 = (iArr == null ? null : iArr)[i2 + 1];
            if (i <= i4) {
                if (iArr == null) {
                    iArr = null;
                }
                i3 = i2 == iArr.length + (-2) ? this.iconTint : i5;
            } else {
                i2 += 2;
                i3 = i5;
            }
        }
        return i3 == this.iconTint ? this.batteryLevelColor : i3;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.intrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.intrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    public final Rect getTextBounds(String str) {
        Paint paint = this.textPaint;
        Rect rect = new Rect();
        paint.setTextLocale(this.context.getResources().getConfiguration().locale);
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect;
    }

    public final float getTextOriginX(Rect rect) {
        int i;
        boolean z = this.showPercentSetting;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.drawable_icon_width_battery_percent_on);
        if (!shouldDrawIcon() && !this.showWarningText) {
            i = this.intrinsicWidth;
        } else {
            if (!isRtl() || !z) {
                return (this.intrinsicWidth / 2.0f) + this.additionalWidth + this.icongap + rect.left;
            }
            i = this.intrinsicWidth - dimensionPixelSize;
        }
        return i / 2.0f;
    }

    public final boolean isBatteryProtectionWorking() {
        try {
            Intent registerReceiver = this.context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver != null ? registerReceiver.getIntExtra("protection", 0) : 0;
            int intExtra2 = registerReceiver != null ? registerReceiver.getIntExtra("misc_event", 0) : 0;
            if (intExtra == 0) {
                return BatteryProtectionUtils.isProtectedFullyByMaximum(intExtra2);
            }
            BatteryProtectionUtils batteryProtectionUtils = BatteryProtectionUtils.INSTANCE;
            return true;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("ERROR: isBatteryProtectionWorking() ", e, "SamsungBatteryMeterDrawable");
            Context context = this.context;
            SamsungBatteryState samsungBatteryState = this.batteryState;
            int i = samsungBatteryState.level;
            int i2 = samsungBatteryState.batteryStatus;
            int i3 = samsungBatteryState.miscEvent;
            int protectBatteryValue = BatteryProtectionUtils.getProtectBatteryValue(context);
            return ((protectBatteryValue == 3 || protectBatteryValue == 4) && i < 100 && i2 == 5) || BatteryProtectionUtils.isProtectedFullyByMaximum(i3);
        }
    }

    public final boolean isRtl() {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1;
    }

    public final void onBatteryLevelChanged(SamsungBatteryState samsungBatteryState) {
        SamsungBatteryState samsungBatteryState2 = this.batteryState;
        samsungBatteryState2.getClass();
        boolean z = samsungBatteryState2.level == samsungBatteryState.level && samsungBatteryState2.charging == samsungBatteryState.charging && samsungBatteryState2.pluggedIn == samsungBatteryState.pluggedIn && samsungBatteryState2.batteryHealth == samsungBatteryState.batteryHealth && samsungBatteryState2.batteryOnline == samsungBatteryState.batteryOnline && samsungBatteryState2.batteryStatus == samsungBatteryState.batteryStatus && samsungBatteryState2.isDirectPowerMode == samsungBatteryState.isDirectPowerMode;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onBatteryLevelChanged isSomethingChanged: ", "SamsungBatteryMeterDrawable", !z);
        if (z) {
            return;
        }
        this.batteryState = samsungBatteryState;
        resizeDrawable();
        if (DEBUG) {
            SamsungBatteryState samsungBatteryState3 = this.batteryState;
            StringBuilder sb = new StringBuilder("Level: ");
            sb.append(samsungBatteryState3.level);
            sb.append(", PluggedIn: ");
            sb.append(samsungBatteryState3.pluggedIn);
            sb.append(", Charging: ");
            sb.append(samsungBatteryState3.charging);
            sb.append(", BatteryHealth: ");
            sb.append(samsungBatteryState3.batteryHealth);
            sb.append(",BatteryStatus: ");
            sb.append(samsungBatteryState3.batteryStatus);
            sb.append(", BatteryOnline: ");
            sb.append(samsungBatteryState3.batteryOnline);
            sb.append(", IsDirectPowerMode: ");
            sb.append(samsungBatteryState3.isDirectPowerMode);
            sb.append(", miscEvent: ");
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onBatteryLevelChanged - ", ReorderTile$$ExternalSyntheticOutline0.m(samsungBatteryState3.miscEvent, ",", sb), "SamsungBatteryMeterDrawable");
        }
        SamsungBatteryMeterDrawable$postInvalidateHandler$1 samsungBatteryMeterDrawable$postInvalidateHandler$1 = this.postInvalidateHandler;
        int i = MSG_POST_INVALIDATE;
        if (samsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i)) {
            return;
        }
        sendEmptyMessage(i);
    }

    public final void postInvalidate() {
        unscheduleSelf(new Runnable() { // from class: com.android.systemui.battery.SamsungBatteryMeterDrawable$postInvalidate$1
            @Override // java.lang.Runnable
            public final void run() {
                SamsungBatteryMeterDrawable.this.invalidateSelf();
            }
        });
        scheduleSelf(new Runnable() { // from class: com.android.systemui.battery.SamsungBatteryMeterDrawable$postInvalidate$2
            @Override // java.lang.Runnable
            public final void run() {
                SamsungBatteryMeterDrawable.this.invalidateSelf();
            }
        }, 0L);
    }

    public final void resizeDrawable() {
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(getBatteryWidthLevelBased());
        this.intrinsicWidth = dimensionPixelSize;
        int i = this.height;
        this.intrinsicHeight = i;
        setBounds(0, 0, dimensionPixelSize, i);
    }

    public final void setShowPercentSetting(boolean z) {
        this.showPercentSetting = z;
        resizeDrawable();
        SamsungBatteryMeterDrawable$postInvalidateHandler$1 samsungBatteryMeterDrawable$postInvalidateHandler$1 = this.postInvalidateHandler;
        int i = MSG_POST_INVALIDATE;
        if (samsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i)) {
            return;
        }
        sendEmptyMessage(i);
    }

    public final boolean shouldDrawIcon() {
        if (this.batteryState.shouldShowChargingIcon() && !this.flagBlinkingNeeded) {
            return true;
        }
        if (!isBatteryProtectionWorking() || this.flagBlinkingNeeded) {
            return this.powerSaveEnabled && this.batteryState.level > 4;
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
