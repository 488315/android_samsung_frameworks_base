package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.DisplayCutout;
import com.android.systemui.BasicRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicatorCutoutUtil {
    public final Context context;
    public String cutoutString;
    public CutoutType cutoutType = CutoutType.NO_CUTOUT;
    public final DisplayLifecycle displayLifecycle;
    public final IndicatorGardenInputProperties inputProperties;
    public boolean isFrontCameraUsing;
    public final boolean isUDCModel;

    public IndicatorCutoutUtil(Context context, IndicatorGardenInputProperties indicatorGardenInputProperties, DisplayLifecycle displayLifecycle) {
        boolean z;
        String string;
        this.context = context;
        this.inputProperties = indicatorGardenInputProperties;
        this.displayLifecycle = displayLifecycle;
        try {
            int identifier = context.getResources().getIdentifier("config_mainBuiltInDisplayCutoutForUDC", "string", "android");
            string = identifier > 0 ? context.getResources().getString(identifier) : null;
        } catch (Exception unused) {
        }
        if (string != null) {
            if (!TextUtils.isEmpty(string)) {
                z = true;
                this.isUDCModel = z;
                loadDisplayCutout();
            }
        }
        z = false;
        this.isUDCModel = z;
        loadDisplayCutout();
    }

    public final Rect getDisplayCutoutAreaToExclude() {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        boolean z = true;
        boolean z2 = indicatorGardenInputProperties.displayCutout != null;
        boolean z3 = indicatorGardenInputProperties.rotation == 0;
        if (!z2 || !isMainDisplay() || !z3 || indicatorGardenInputProperties.isRTL() || this.displayLifecycle.mIsFitToActiveDisplay || (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC && !this.isFrontCameraUsing)) {
            z = false;
        }
        if (!z) {
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadDisplayCutout() {
        String str;
        CutoutType cutoutType;
        boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FOLD;
        Context context = this.context;
        if (z) {
            if (context.getResources().getConfiguration().semDisplayDeviceType == 5) {
                str = "config_subBuiltInDisplayCutout";
                int identifier = context.getResources().getIdentifier(str, "string", "android");
                this.cutoutString = identifier <= 0 ? context.getResources().getString(identifier) : null;
                if (Intrinsics.areEqual(str, "config_mainBuiltInDisplayCutoutForUDC")) {
                    String str2 = this.cutoutString;
                    if (str2 != null) {
                        if (str2.length() > 0) {
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
                    cutoutType = CutoutType.NO_CUTOUT;
                } else {
                    cutoutType = CutoutType.UDC;
                }
                this.cutoutType = cutoutType;
            }
        }
        str = (z && isUDCMainDisplay()) ? "config_mainBuiltInDisplayCutoutForUDC" : "config_mainBuiltInDisplayCutout";
        int identifier2 = context.getResources().getIdentifier(str, "string", "android");
        this.cutoutString = identifier2 <= 0 ? context.getResources().getString(identifier2) : null;
        if (Intrinsics.areEqual(str, "config_mainBuiltInDisplayCutoutForUDC")) {
        }
        this.cutoutType = cutoutType;
    }
}
