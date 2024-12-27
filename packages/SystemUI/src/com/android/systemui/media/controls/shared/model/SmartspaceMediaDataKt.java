package com.android.systemui.media.controls.shared.model;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public abstract class SmartspaceMediaDataKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(SmartspaceMediaData.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }

    public static /* synthetic */ void getKEY_SMARTSPACE_APP_NAME$annotations() {
    }
}
