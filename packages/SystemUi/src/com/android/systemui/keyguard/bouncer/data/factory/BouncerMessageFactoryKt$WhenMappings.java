package com.android.systemui.keyguard.bouncer.data.factory;

import com.android.keyguard.KeyguardSecurityModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract /* synthetic */ class BouncerMessageFactoryKt$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
        try {
            iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        $EnumSwitchMapping$0 = iArr;
    }
}
