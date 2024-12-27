package com.android.systemui.battery;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.power.BatteryProtectionUtil;
import com.android.systemui.util.DeviceType;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SamsungBatteryMeterDrawable extends Drawable {
    public static final float BATTERY_BACKGROUND_ALPHA;
    public static final int BLINKING_INTERVAL;
    public static final boolean DEBUG;
    public static final int MSG_POST_INVALIDATE;
    public final int additionalWidth;
    public int batteryLevelBackgroundColor;
    public final int batteryLevelBackgroundDarkColor;
    public final int batteryLevelBackgroundLightColor;
    public int batteryLevelColor;
    public final int batteryOuterBackgroundDarkColor;
    public final int batteryOuterBackgroundLightColor;
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
    public final float sideMargin;
    public float textMeasuredWidth;
    public final String warningString;
    public final Paint warningTextPaint;
    public final int width;
    public final int widthExtended;
    public final Paint batteryLevelPaint = new Paint();
    public final Paint batteryLevelBackgroundPaint = new Paint();
    public final Paint batteryOuterPaint = new Paint();
    public final Paint roundedRectPaint = new Paint();
    public final Paint textPaint = new Paint();
    public int iconTint = -1;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        DEBUG = DeviceType.isEngOrUTBinary();
        MSG_POST_INVALIDATE = 1;
        BLINKING_INTERVAL = 1000;
        BATTERY_BACKGROUND_ALPHA = 0.35f;
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.battery.SamsungBatteryMeterDrawable$postInvalidateHandler$1] */
    public SamsungBatteryMeterDrawable(Context context) {
        TypedArray typedArray;
        int length;
        this.context = context;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_height);
        this.height = dimensionPixelSize;
        this.width = context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_width);
        this.widthExtended = context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_width_extended);
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
                this.warningString = this.context.getString(R.string.battery_meter_very_low_overlay_symbol);
                Resources resources2 = this.context.getResources();
                int color = resources2.getColor(R.color.status_bar_battery_frame_light_color, null);
                resources2.getColor(R.color.status_bar_battery_frame_dark_color, null);
                this.batteryLevelColor = color;
                int color2 = resources2.getColor(R.color.status_bar_battery_level_background_light_color, null);
                this.batteryLevelBackgroundLightColor = color2;
                this.batteryLevelBackgroundDarkColor = resources2.getColor(R.color.status_bar_battery_level_background_dark_color, null);
                int color3 = resources2.getColor(R.color.status_bar_battery_level_outer_light_color, null);
                this.batteryOuterBackgroundLightColor = color3;
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
                paint2.setColor(color3);
                Paint paint3 = this.batteryLevelBackgroundPaint;
                paint3.setAntiAlias(true);
                paint3.setColor(color2);
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x029e  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(android.graphics.Canvas r27) {
        /*
            Method dump skipped, instructions count: 1022
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.battery.SamsungBatteryMeterDrawable.draw(android.graphics.Canvas):void");
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

    public final boolean isBatteryProtectionWorking() {
        Context context = this.context;
        SamsungBatteryState samsungBatteryState = this.batteryState;
        int i = samsungBatteryState.level;
        int i2 = samsungBatteryState.batteryStatus;
        int i3 = samsungBatteryState.miscEvent;
        int protectBatteryValue = BatteryProtectionUtil.getProtectBatteryValue(context);
        return ((protectBatteryValue == 3 || protectBatteryValue == 4) && i < 100 && i2 == 5) || BatteryProtectionUtil.isProtectedFullyByMaximum(i3);
    }

    public final boolean isRtl() {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1;
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
        int i = shouldDrawIcon() ? this.widthExtended : this.width;
        this.intrinsicWidth = i;
        int i2 = this.height;
        this.intrinsicHeight = i2;
        setBounds(0, 0, i, i2);
    }

    public final boolean shouldDrawIcon() {
        return this.batteryState.shouldShowChargingIcon() || isBatteryProtectionWorking() || (this.powerSaveEnabled && this.batteryState.level > 4);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
