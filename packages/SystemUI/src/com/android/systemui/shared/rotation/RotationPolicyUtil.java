package com.android.systemui.shared.rotation;

import android.content.Context;
import android.util.Log;
import com.android.internal.view.RotationPolicy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class RotationPolicyUtil {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public static final Boolean isRotationLocked(Context context) {
        Companion.getClass();
        try {
            return Boolean.valueOf(RotationPolicy.isRotationLocked(context));
        } catch (SecurityException e) {
            Log.e("RotationPolicy", "Failed to get isRotationLocked", e);
            return null;
        }
    }
}
