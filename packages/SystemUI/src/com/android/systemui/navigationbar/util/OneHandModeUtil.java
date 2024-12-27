package com.android.systemui.navigationbar.util;

import android.graphics.Rect;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OneHandModeUtil {
    public static final Companion Companion = new Companion(null);
    public static NavBarStoreAction.OneHandModeInfo oneHandModeInfo = new NavBarStoreAction.OneHandModeInfo(0, 0, 1.0f);
    private final SettingsHelper settingsHelper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public OneHandModeUtil(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public final Rect getRegionSamplingBounds(Rect rect) {
        if (!this.settingsHelper.isOneHandModeRunning()) {
            return rect;
        }
        NavBarStoreAction.OneHandModeInfo oneHandModeInfo2 = oneHandModeInfo;
        int i = oneHandModeInfo2.offsetX;
        int i2 = oneHandModeInfo2.offsetY;
        float f = oneHandModeInfo2.scale;
        Rect rect2 = new Rect();
        float f2 = i;
        rect2.left = (int) ((rect.left * f) + f2);
        float f3 = i2;
        rect2.top = (int) ((rect.top * f) + f3);
        rect2.right = (int) ((rect.right * f) + f2);
        rect2.bottom = (int) ((rect.bottom * f) + f3);
        return rect2;
    }
}
