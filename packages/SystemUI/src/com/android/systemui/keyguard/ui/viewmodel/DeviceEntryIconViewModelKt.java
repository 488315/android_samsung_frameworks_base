package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes2.dex */
public abstract class DeviceEntryIconViewModelKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceEntryIconView.IconType.values().length];
            try {
                iArr[DeviceEntryIconView.IconType.FINGERPRINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceEntryIconView.IconType.LOCK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceEntryIconView.IconType.UNLOCK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceEntryIconView.IconType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final DeviceEntryIconView.AccessibilityHintType toAccessibilityHintType(DeviceEntryIconView.IconType iconType) {
        int i = WhenMappings.$EnumSwitchMapping$0[iconType.ordinal()];
        if (i == 1 || i == 2) {
            return DeviceEntryIconView.AccessibilityHintType.BOUNCER;
        }
        if (i == 3) {
            return DeviceEntryIconView.AccessibilityHintType.ENTER;
        }
        if (i == 4) {
            return DeviceEntryIconView.AccessibilityHintType.NONE;
        }
        throw new NoWhenBranchMatchedException();
    }
}
