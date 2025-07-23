package com.android.systemui.shared.rotation;

import android.content.Context;
import android.util.Log;
import com.android.internal.view.RotationPolicy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RotationPolicyUtil {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
