package com.android.systemui.battery;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LegacySamsungBatteryMeterDrawable extends SamsungBatteryMeterDrawable {
    public static final float BATTERY_BACKGROUND_ALPHA;
    public static final int BLINKING_INTERVAL;
    public static final boolean DEBUG;
    public static final String INVALID_STRING;
    public static final int MSG_POST_INVALIDATE;
    public final Paint batteryFramePaint;
    public final int batteryLevelBackgroundDarkColor;
    public final int batteryLevelBackgroundLightColor;
    public final Paint batteryLevelBackgroundPaint;
    public int batteryLevelColor;
    public final Paint batteryLevelPaint;
    public final int batteryLightningBoltDarkColor;
    public final Paint batteryLightningBoltDarkPaint;
    public final int batteryLightningBoltLightColor;
    public final Paint batteryLightningBoltLightPaint;
    public SamsungBatteryState batteryState;
    public int[] colors;
    public final Context context;
    public final int criticalLevel;
    public float darkIntensity;
    public final int extraThreshold;
    public boolean flagBlinkingNeeded;
    public boolean flagDrawIcon;
    public Drawable frameOver95;
    public Drawable frameUnder15;
    public int height;
    public int iconTint;
    public int intrinsicHeight;
    public int intrinsicWidth;
    public float invalidTextHeight;
    public final Paint invalidTextPaint;
    public final LegacySamsungBatteryMeterDrawable$postInvalidateHandler$1 postInvalidateHandler;
    public final String warningString;
    public float warningTextHeight;
    public final Paint warningTextPaint;
    public int width;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        INVALID_STRING = "X";
        BATTERY_BACKGROUND_ALPHA = 0.35f;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.battery.LegacySamsungBatteryMeterDrawable$postInvalidateHandler$1] */
    public LegacySamsungBatteryMeterDrawable(Context context) {
        super(null);
        this.context = context;
        this.batteryFramePaint = new Paint();
        this.batteryLevelPaint = new Paint();
        this.batteryLevelBackgroundPaint = new Paint();
        this.batteryLightningBoltDarkPaint = new Paint();
        this.batteryLightningBoltLightPaint = new Paint();
        this.iconTint = -1;
        this.warningTextPaint = new Paint(1);
        this.batteryState = new SamsungBatteryState();
        this.darkIntensity = -1.0f;
        this.flagDrawIcon = true;
        final Looper mainLooper = Looper.getMainLooper();
        this.postInvalidateHandler = new Handler(mainLooper) { // from class: com.android.systemui.battery.LegacySamsungBatteryMeterDrawable$postInvalidateHandler$1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == LegacySamsungBatteryMeterDrawable.MSG_POST_INVALIDATE) {
                    final LegacySamsungBatteryMeterDrawable legacySamsungBatteryMeterDrawable = LegacySamsungBatteryMeterDrawable.this;
                    if (legacySamsungBatteryMeterDrawable.flagBlinkingNeeded) {
                        legacySamsungBatteryMeterDrawable.flagDrawIcon = !legacySamsungBatteryMeterDrawable.flagDrawIcon;
                    }
                    legacySamsungBatteryMeterDrawable.getClass();
                    legacySamsungBatteryMeterDrawable.unscheduleSelf(new Runnable() { // from class: com.android.systemui.battery.LegacySamsungBatteryMeterDrawable$postInvalidate$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            LegacySamsungBatteryMeterDrawable.this.invalidateSelf();
                        }
                    });
                    legacySamsungBatteryMeterDrawable.scheduleSelf(new Runnable() { // from class: com.android.systemui.battery.LegacySamsungBatteryMeterDrawable$postInvalidate$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            LegacySamsungBatteryMeterDrawable.this.invalidateSelf();
                        }
                    }, 0L);
                }
            }
        };
        Resources resources = context.getResources();
        TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.batterymeter_color_levels);
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(R.array.batterymeter_color_values);
        int length = obtainTypedArray.length();
        this.colors = new int[length * 2];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int[] iArr = this.colors;
            int i3 = i2 * 2;
            (iArr == null ? null : iArr)[i3] = obtainTypedArray.getInt(i2, 0);
            if (obtainTypedArray2.getType(i2) == 2) {
                int[] iArr2 = this.colors;
                (iArr2 == null ? null : iArr2)[i3 + 1] = Utils.getColorAttrDefaultColor(obtainTypedArray2.getThemeAttributeId(i2, 0), this.context, 0);
            } else {
                int[] iArr3 = this.colors;
                (iArr3 == null ? null : iArr3)[i3 + 1] = obtainTypedArray2.getColor(i2, 0);
            }
        }
        obtainTypedArray.recycle();
        obtainTypedArray2.recycle();
        this.criticalLevel = 4;
        this.warningString = this.context.getString(R.string.battery_meter_very_low_overlay_symbol);
        Typeface create = Typeface.create("sans-serif", 1);
        this.warningTextPaint.setTypeface(create);
        this.warningTextPaint.setTextAlign(Paint.Align.CENTER);
        int[] iArr4 = this.colors;
        if ((iArr4 == null ? null : iArr4).length > 1) {
            this.warningTextPaint.setColor((iArr4 == null ? null : iArr4)[1]);
        }
        Paint paint = new Paint(1);
        this.invalidTextPaint = paint;
        paint.setColor(-1559543);
        paint.setTypeface(create);
        paint.setTextAlign(Paint.Align.CENTER);
        Resources resources2 = this.context.getResources();
        int color = resources2.getColor(R.color.status_bar_battery_frame_light_color, null);
        resources2.getColor(R.color.status_bar_battery_frame_dark_color, null);
        this.batteryLevelColor = color;
        int color2 = resources2.getColor(R.color.status_bar_battery_level_background_light_color, null);
        this.batteryLevelBackgroundLightColor = color2;
        this.batteryLevelBackgroundDarkColor = resources2.getColor(R.color.status_bar_battery_level_background_dark_color, null);
        int color3 = resources2.getColor(R.color.status_bar_battery_lightning_bolt_light_color, null);
        this.batteryLightningBoltLightColor = color3;
        int color4 = resources2.getColor(R.color.status_bar_battery_lightning_bolt_dark_color, null);
        this.batteryLightningBoltDarkColor = color4;
        this.batteryFramePaint.setAntiAlias(true);
        this.batteryFramePaint.setColor(color);
        this.batteryLevelPaint.setAntiAlias(true);
        this.batteryLevelPaint.setDither(true);
        this.batteryLevelPaint.setStrokeWidth(0.0f);
        this.batteryLevelPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.batteryLevelPaint.setColor(color);
        this.batteryLevelPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.batteryLevelBackgroundPaint.setAntiAlias(true);
        this.batteryLevelBackgroundPaint.setColor(color2);
        this.batteryLevelBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.batteryLightningBoltDarkPaint.setAntiAlias(true);
        this.batteryLightningBoltDarkPaint.setColor(color4);
        this.batteryLightningBoltLightPaint.setAntiAlias(true);
        this.batteryLightningBoltLightPaint.setColor(color3);
        this.frameUnder15 = this.context.getResources().getDrawable(R.drawable.stat_sys_battery_under_15, null);
        this.frameOver95 = this.context.getResources().getDrawable(R.drawable.stat_sys_battery_over_95, null);
        this.intrinsicWidth = this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_battery_icon_width);
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_battery_icon_height);
        this.intrinsicHeight = dimensionPixelSize;
        setBounds(0, 0, this.intrinsicWidth, dimensionPixelSize);
        String str = SystemProperties.get("ro.product.name", "");
        if (str.startsWith("gta7lite") || (str.startsWith("gta9") && !str.startsWith("gta9p"))) {
            i = 10;
        }
        this.extraThreshold = i;
    }

    public static int getColorForDarkIntensity(float f, int i, int i2) {
        return ((Integer) ArgbEvaluator.sInstance.evaluate(f, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0146 A[EDGE_INSN: B:158:0x0146->B:81:0x0146 BREAK  A[LOOP:0: B:75:0x0132->B:78:0x0141], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0050 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0176  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas) {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        Drawable drawable;
        Drawable drawable2;
        float f;
        SamsungBatteryState samsungBatteryState;
        int i10;
        SamsungBatteryState samsungBatteryState2 = this.batteryState;
        boolean z2 = false;
        if (!samsungBatteryState2.isDirectPowerMode && samsungBatteryState2.batteryStatus == 4) {
            int i11 = samsungBatteryState2.batteryHealth;
            if (i11 == 3 || i11 == 7 || i11 == SamsungBatteryState.BATTERY_HEALTH_OVERHEAT_LIMIT || i11 == 6) {
                this.flagBlinkingNeeded = true;
                if (DEBUG) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("battery icon blink for battery health... mFlagDrawIconTurn:", this.flagDrawIcon, "SamsungBatteryMeterDrawable");
                }
                LegacySamsungBatteryMeterDrawable$postInvalidateHandler$1 legacySamsungBatteryMeterDrawable$postInvalidateHandler$1 = this.postInvalidateHandler;
                int i12 = MSG_POST_INVALIDATE;
                if (!legacySamsungBatteryMeterDrawable$postInvalidateHandler$1.hasMessages(i12)) {
                    sendEmptyMessageDelayed(i12, BLINKING_INTERVAL);
                }
                z = !this.flagDrawIcon;
                if (!z) {
                    return;
                }
                SamsungBatteryState samsungBatteryState3 = this.batteryState;
                if (samsungBatteryState3.isDirectPowerMode) {
                    i = 100;
                } else {
                    i = samsungBatteryState3.batteryOnline == 0 ? 0 : samsungBatteryState3.level;
                }
                if (i == -1) {
                    return;
                }
                Drawable drawable3 = this.frameOver95;
                if (drawable3 == null) {
                    drawable3 = null;
                }
                drawable3.setColorFilter(new BlendModeColorFilter(this.batteryFramePaint.getColor(), BlendMode.SRC_ATOP));
                Drawable drawable4 = this.frameOver95;
                if (drawable4 == null) {
                    drawable4 = null;
                }
                drawable4.setBounds(0, 0, this.width, this.height);
                if (i < 96) {
                    Drawable drawable5 = this.frameOver95;
                    if (drawable5 == null) {
                        drawable5 = null;
                    }
                    drawable5.setAlpha(89);
                } else {
                    Drawable drawable6 = this.frameOver95;
                    if (drawable6 == null) {
                        drawable6 = null;
                    }
                    drawable6.setAlpha(255);
                }
                Drawable drawable7 = this.frameUnder15;
                if (drawable7 == null) {
                    drawable7 = null;
                }
                drawable7.setColorFilter(new BlendModeColorFilter(i > this.criticalLevel ? getColorForLevel(i) : this.batteryFramePaint.getColor(), BlendMode.SRC_ATOP));
                Drawable drawable8 = this.frameUnder15;
                if (drawable8 == null) {
                    drawable8 = null;
                }
                drawable8.setBounds(0, 0, this.width, this.height);
                if (i <= this.criticalLevel) {
                    Drawable drawable9 = this.frameUnder15;
                    if (drawable9 == null) {
                        drawable9 = null;
                    }
                    drawable9.setAlpha(89);
                } else {
                    Drawable drawable10 = this.frameUnder15;
                    if (drawable10 == null) {
                        drawable10 = null;
                    }
                    drawable10.setAlpha(255);
                }
                Drawable drawable11 = this.frameOver95;
                if (drawable11 == null) {
                    drawable11 = null;
                }
                int intrinsicWidth = drawable11.getIntrinsicWidth();
                Drawable drawable12 = this.frameOver95;
                if (drawable12 == null) {
                    drawable12 = null;
                }
                int intrinsicHeight = drawable12.getIntrinsicHeight();
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap);
                Drawable drawable13 = this.frameOver95;
                if (drawable13 == null) {
                    drawable13 = null;
                }
                drawable13.draw(canvas2);
                Drawable drawable14 = this.frameUnder15;
                if (drawable14 == null) {
                    drawable14 = null;
                }
                drawable14.draw(canvas2);
                Rect rect = new Rect();
                int i13 = intrinsicWidth / 2;
                int i14 = intrinsicHeight / 2;
                if (i >= 96) {
                    i2 = 255;
                } else {
                    if (this.criticalLevel + 1 <= i && i < 96) {
                        z2 = true;
                    }
                    i2 = 89;
                    if (!z2) {
                        i3 = 89;
                        i4 = i14;
                        i5 = i4;
                        while (true) {
                            if (i4 <= 0) {
                                break;
                            }
                            if (Color.alpha(createBitmap.getPixel(i13, i4)) >= i2) {
                                i5++;
                                break;
                            } else {
                                i5--;
                                i4--;
                            }
                        }
                        i6 = i14;
                        i7 = i6;
                        while (i6 < intrinsicHeight && Color.alpha(createBitmap.getPixel(i13, i6)) < i3 - this.extraThreshold) {
                            i7++;
                            i6++;
                        }
                        i8 = i13;
                        i9 = i8;
                        while (i8 > 0 && Color.alpha(createBitmap.getPixel(i8, i14)) < i2) {
                            i9--;
                            i8--;
                        }
                        int i15 = i13;
                        while (i13 < intrinsicWidth) {
                            i15++;
                            if (Color.alpha(createBitmap.getPixel(i13, i14)) >= i2) {
                                break;
                            } else {
                                i13++;
                            }
                        }
                        if (this.extraThreshold <= 0) {
                            rect.set(i9, i5, i15, i7);
                        } else {
                            rect.set(i9, i5 - 1, i15, i7 + 1);
                        }
                        drawable = this.frameOver95;
                        if (drawable == null) {
                            drawable = null;
                        }
                        drawable.draw(canvas);
                        drawable2 = this.frameUnder15;
                        if (drawable2 == null) {
                            drawable2 = null;
                        }
                        drawable2.draw(canvas);
                        if (i < 96) {
                            f = 1.0f;
                        } else {
                            int[] iArr = this.colors;
                            if (iArr == null) {
                                iArr = null;
                            }
                            f = i <= iArr[0] ? 0.0f : (i - 15) / 80.0f;
                        }
                        int height = (int) (rect.height() * f);
                        int i16 = rect.bottom - height;
                        Rect rect2 = new Rect();
                        rect2.set(rect.left, rect.top, rect.right, i16);
                        canvas.drawRect(rect2, this.batteryLevelBackgroundPaint);
                        Rect rect3 = new Rect();
                        this.batteryLevelPaint.setColor(getColorForLevel(i));
                        rect3.set(rect.left, i16, rect.right, rect.bottom);
                        canvas.drawRect(rect3, this.batteryLevelPaint);
                        samsungBatteryState = this.batteryState;
                        if (!(samsungBatteryState.batteryOnline != 0)) {
                            float f2 = (this.height + this.invalidTextHeight) * 0.48f;
                            String str = INVALID_STRING;
                            Paint paint = this.invalidTextPaint;
                            Intrinsics.checkNotNull(paint);
                            canvas.drawText(str, this.width * 0.5f, f2, paint);
                            return;
                        }
                        boolean z3 = samsungBatteryState.isDirectPowerMode;
                        boolean z4 = samsungBatteryState.pluggedIn;
                        if (!(z3 || !(!z4 || (i10 = samsungBatteryState.batteryStatus) == 5 || i10 == 3 || i10 == 4))) {
                            if (z4 || i > this.criticalLevel) {
                                return;
                            }
                            canvas.drawText(this.warningString, this.width * 0.5f, (this.height + this.warningTextHeight) * 0.48f, this.warningTextPaint);
                            return;
                        }
                        Drawable drawable15 = this.context.getResources().getDrawable(R.drawable.stat_sys_battery_charging, null);
                        int color = this.batteryLightningBoltLightPaint.getColor();
                        int color2 = this.batteryLightningBoltDarkPaint.getColor();
                        int[] iArr2 = new int[4];
                        int i17 = this.criticalLevel;
                        iArr2[0] = i <= i17 ? color : color2;
                        if (i <= i17) {
                            color2 = color;
                        }
                        iArr2[1] = color2;
                        iArr2[2] = color;
                        iArr2[3] = color;
                        float f3 = height;
                        LinearGradient linearGradient = new LinearGradient(0.0f, rect.bottom, 0.0f, rect.top, iArr2, new float[]{0.0f, f3 / rect.height(), f3 / rect.height(), 1.0f}, Shader.TileMode.CLAMP);
                        Context context = this.context;
                        int i18 = this.width;
                        int i19 = this.height;
                        Paint paint2 = new Paint();
                        paint2.setColor(EmergencyPhoneWidget.BG_COLOR);
                        paint2.setDither(false);
                        paint2.setStrokeWidth(10.0f);
                        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        paint2.setShader(linearGradient);
                        Matrix matrix = new Matrix();
                        linearGradient.getLocalMatrix(matrix);
                        float f4 = i18;
                        float f5 = i19;
                        matrix.postRotate(0.0f, f4 / 2.0f, f5 / 2.0f);
                        linearGradient.setLocalMatrix(matrix);
                        drawable15.setBounds(0, 0, i18, i19);
                        Bitmap createBitmap2 = Bitmap.createBitmap(i18, i19, Bitmap.Config.ARGB_8888);
                        Canvas canvas3 = new Canvas(createBitmap2);
                        drawable15.draw(canvas3);
                        canvas3.drawRect(0.0f, 0.0f, f4, f5, paint2);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), createBitmap2);
                        bitmapDrawable.setBounds(0, 0, this.width, this.height);
                        bitmapDrawable.draw(canvas);
                        return;
                    }
                }
                i3 = 255;
                i4 = i14;
                i5 = i4;
                while (true) {
                    if (i4 <= 0) {
                    }
                    i5--;
                    i4--;
                }
                i6 = i14;
                i7 = i6;
                while (i6 < intrinsicHeight) {
                    i7++;
                    i6++;
                }
                i8 = i13;
                i9 = i8;
                while (i8 > 0) {
                    i9--;
                    i8--;
                }
                int i152 = i13;
                while (i13 < intrinsicWidth) {
                }
                if (this.extraThreshold <= 0) {
                }
                drawable = this.frameOver95;
                if (drawable == null) {
                }
                drawable.draw(canvas);
                drawable2 = this.frameUnder15;
                if (drawable2 == null) {
                }
                drawable2.draw(canvas);
                if (i < 96) {
                }
                int height2 = (int) (rect.height() * f);
                int i162 = rect.bottom - height2;
                Rect rect22 = new Rect();
                rect22.set(rect.left, rect.top, rect.right, i162);
                canvas.drawRect(rect22, this.batteryLevelBackgroundPaint);
                Rect rect32 = new Rect();
                this.batteryLevelPaint.setColor(getColorForLevel(i));
                rect32.set(rect.left, i162, rect.right, rect.bottom);
                canvas.drawRect(rect32, this.batteryLevelPaint);
                samsungBatteryState = this.batteryState;
                if (!(samsungBatteryState.batteryOnline != 0)) {
                }
            }
        }
        this.flagBlinkingNeeded = false;
        z = false;
        if (!z) {
        }
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

    @Override // android.graphics.drawable.Drawable
    public final void setBounds(int i, int i2, int i3, int i4) {
        int i5 = i4 - i2;
        this.height = i5;
        this.width = i3 - i;
        float f = this.context.getResources().getFloat(R.dimen.battery_meter_text_size_ratio) * i5;
        this.warningTextPaint.setTextSize(f);
        this.warningTextHeight = -this.warningTextPaint.getFontMetrics().ascent;
        Paint paint = this.invalidTextPaint;
        Intrinsics.checkNotNull(paint);
        paint.setTextSize(f);
        this.invalidTextHeight = this.warningTextHeight;
    }

    public final void setColors(int i) {
        this.iconTint = i;
        this.batteryFramePaint.setColor(i);
        this.batteryLevelColor = i;
        this.batteryLevelBackgroundPaint.setColor(getColorForDarkIntensity(this.darkIntensity, this.batteryLevelBackgroundLightColor, this.batteryLevelBackgroundDarkColor));
        updateLightningBoltColor();
        invalidateSelf();
    }

    public final void setGrayColors(int i) {
        this.iconTint = i;
        this.batteryLevelBackgroundPaint.setColor(Color.argb(MathKt__MathJVMKt.roundToInt(Color.alpha(i) * BATTERY_BACKGROUND_ALPHA), Color.red(i), Color.green(i), Color.blue(i)));
        this.batteryFramePaint.setColor(i);
        this.batteryLevelColor = i;
        updateLightningBoltColor();
        invalidateSelf();
    }

    public final void updateLightningBoltColor() {
        this.batteryLightningBoltLightPaint.setColor(getColorForDarkIntensity(this.darkIntensity, this.batteryLightningBoltLightColor, this.batteryLightningBoltDarkColor));
        this.batteryLightningBoltDarkPaint.setColor(getColorForDarkIntensity(this.darkIntensity, this.batteryLightningBoltDarkColor, this.batteryLightningBoltLightColor));
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
