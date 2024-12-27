package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.DisplayCutout;
import com.android.systemui.BasicRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IndicatorCutoutUtil {
    public final Context context;
    public String cutoutString;
    public CutoutType cutoutType = CutoutType.NO_CUTOUT;
    public final DisplayLifecycle displayLifecycle;
    public final IndicatorGardenInputProperties inputProperties;
    public boolean isFrontCameraUsing;
    public final boolean isUDCModel;

    public IndicatorCutoutUtil(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties, DisplayLifecycle displayLifecycle) {
        this.context = context;
        this.inputProperties = indicatorGardenInputProperties;
        this.displayLifecycle = displayLifecycle;
        boolean z = false;
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
        Intrinsics.checkNotNull(displayCutout);
        return displayCutout.getBoundingRects().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.phone.IndicatorCutoutUtil$getDisplayCutoutAreaToExclude$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !((Rect) obj).isEmpty();
            }
        }).findFirst().orElse(null);
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
        String str = (z && this.context.getResources().getConfiguration().semDisplayDeviceType == 5) ? "config_subBuiltInDisplayCutout" : (z && isUDCMainDisplay()) ? "config_mainBuiltInDisplayCutoutForUDC" : "config_mainBuiltInDisplayCutout";
        int identifier = this.context.getResources().getIdentifier(str, "string", "android");
        this.cutoutString = identifier > 0 ? this.context.getResources().getString(identifier) : null;
        if (str.equals("config_mainBuiltInDisplayCutoutForUDC")) {
            cutoutType = CutoutType.UDC;
        } else {
            String str2 = this.cutoutString;
            if (str2 == null || str2.length() <= 0) {
                cutoutType = CutoutType.NO_CUTOUT;
            } else {
                String str3 = this.cutoutString;
                Intrinsics.checkNotNull(str3);
                if (str3.endsWith("@left")) {
                    cutoutType = CutoutType.LEFT_CUTOUT;
                } else {
                    String str4 = this.cutoutString;
                    Intrinsics.checkNotNull(str4);
                    cutoutType = str4.endsWith("@right") ? CutoutType.RIGHT_CUTOUT : CutoutType.CENTER_CUTOUT;
                }
            }
        }
        this.cutoutType = cutoutType;
    }
}
