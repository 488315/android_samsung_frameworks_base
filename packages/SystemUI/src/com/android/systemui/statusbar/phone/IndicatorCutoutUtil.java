package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslWindowInsetsReflector;
import com.android.systemui.BasicRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IndicatorCutoutUtil {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public String cutoutString;
    public CutoutType cutoutType = CutoutType.NO_CUTOUT;
    public final DisplayLifecycle displayLifecycle;
    public final IndicatorGardenInputProperties inputProperties;
    public boolean isFrontCameraUsing;
    public final boolean isUDCModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static WindowInsets getHidWindowInsetsFromUDC(WindowInsets windowInsets) {
            DisplayCutout displayCutout;
            if (windowInsets.getDisplayCutout() != null) {
                return windowInsets;
            }
            WindowInsets.Builder builder = new WindowInsets.Builder(windowInsets);
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslWindowInsetsReflector.mClass, "hidden_getDisplayCutoutForUdc", new Class[0]);
            if (declaredMethod != null) {
                Object invoke = SeslBaseReflector.invoke(windowInsets, declaredMethod, new Object[0]);
                if (invoke instanceof DisplayCutout) {
                    displayCutout = (DisplayCutout) invoke;
                    return builder.setDisplayCutout(displayCutout).build();
                }
            }
            displayCutout = null;
            return builder.setDisplayCutout(displayCutout).build();
        }

        private Companion() {
        }
    }

    public IndicatorCutoutUtil(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties, DisplayLifecycle displayLifecycle) {
        this.context = context;
        this.inputProperties = indicatorGardenInputProperties;
        this.displayLifecycle = displayLifecycle;
        boolean z = false;
        if (!BasicRune.BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT) {
            try {
                int identifier = context.getResources().getIdentifier("config_mainBuiltInDisplayCutoutForUDC", "string", "android");
                String string = identifier > 0 ? context.getResources().getString(identifier) : null;
                if (string != null) {
                    if (!TextUtils.isEmpty(string)) {
                        z = true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        this.isUDCModel = z;
        loadDisplayCutout();
    }

    public final Rect getDisplayCutoutAreaToExclude() {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        boolean z = indicatorGardenInputProperties.displayCutout != null;
        boolean z2 = indicatorGardenInputProperties.rotation == 0;
        if (!z || !isMainDisplay() || !z2 || indicatorGardenInputProperties.isRTL() || this.displayLifecycle.mIsFitToActiveDisplay || (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC && !this.isFrontCameraUsing)) {
            return null;
        }
        DisplayCutout displayCutout = indicatorGardenInputProperties.displayCutout;
        displayCutout.getClass();
        Stream<Rect> stream = displayCutout.getBoundingRects().stream();
        final IndicatorCutoutUtil$$ExternalSyntheticLambda0 indicatorCutoutUtil$$ExternalSyntheticLambda0 = new IndicatorCutoutUtil$$ExternalSyntheticLambda0();
        return stream.filter(new Predicate() { // from class: com.android.systemui.statusbar.phone.IndicatorCutoutUtilKt$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
            }
        }).findFirst().orElse(null);
    }

    public final String getLogText() {
        StringBuilder sb = new StringBuilder("    IndicatorCutoutUtil IndicatorGardenCutout ");
        sb.append("( cutoutType:" + this.cutoutType);
        sb.append(", isUDCModel:" + this.isUDCModel);
        sb.append(", isUDCMainDisplay:" + isUDCMainDisplay());
        sb.append(", isMainDisplay:" + isMainDisplay());
        sb.append(", excludeArea:" + getDisplayCutoutAreaToExclude());
        sb.append(", cutoutString:" + this.cutoutString);
        sb.append(" )");
        return sb.toString();
    }

    public final boolean isMainDisplay() {
        return this.context.getResources().getConfiguration().semDisplayDeviceType == 0;
    }

    public final boolean isUDCMainDisplay() {
        return isMainDisplay() && this.isUDCModel;
    }

    public final void loadDisplayCutout() {
        CutoutType cutoutType;
        boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        String str = (z && this.context.getResources().getConfiguration().semDisplayDeviceType == 5) ? "config_subBuiltInDisplayCutout" : (!(BasicRune.BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT && isUDCMainDisplay()) && z && isUDCMainDisplay()) ? "config_mainBuiltInDisplayCutoutForUDC" : "config_mainBuiltInDisplayCutout";
        int identifier = this.context.getResources().getIdentifier(str, "string", "android");
        this.cutoutString = identifier > 0 ? this.context.getResources().getString(identifier) : null;
        if (str.equals("config_mainBuiltInDisplayCutoutForUDC")) {
            cutoutType = CutoutType.UDC;
        } else if (z && str.equals("config_mainBuiltInDisplayCutout")) {
            cutoutType = CutoutType.SIDELING_CENTER_CUTOUT;
        } else {
            String str2 = this.cutoutString;
            if (str2 == null || str2.length() <= 0) {
                cutoutType = CutoutType.NO_CUTOUT;
            } else {
                String str3 = this.cutoutString;
                str3.getClass();
                if (str3.endsWith("@left")) {
                    cutoutType = CutoutType.LEFT_CUTOUT;
                } else {
                    String str4 = this.cutoutString;
                    str4.getClass();
                    cutoutType = str4.endsWith("@right") ? CutoutType.RIGHT_CUTOUT : CutoutType.CENTER_CUTOUT;
                }
            }
        }
        this.cutoutType = cutoutType;
        Log.d("IndicatorCutoutUtil", String.valueOf(getLogText()));
    }
}
