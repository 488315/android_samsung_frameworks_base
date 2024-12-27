package com.android.systemui.util.wrapper;

import android.content.Context;
import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.view.RotationPolicy;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.Unit;

public final class RotationPolicyWrapperImpl implements RotationPolicyWrapper {
    public static final int $stable = 8;
    private final Context context;
    private final SecureSettings secureSettings;

    public RotationPolicyWrapperImpl(Context context, SecureSettings secureSettings) {
        this.context = context;
        this.secureSettings = secureSettings;
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public int getRotationLockOrientation() {
        return RotationPolicy.getRotationLockOrientation(this.context);
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public boolean isCameraRotationEnabled() {
        return this.secureSettings.getInt("camera_autorotate", 0) == 1;
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public boolean isRotationLockToggleVisible() {
        return RotationPolicy.isRotationLockToggleVisible(this.context);
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public boolean isRotationLocked() {
        return RotationPolicy.isRotationLocked(this.context);
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public void registerRotationPolicyListener(RotationPolicy.RotationPolicyListener rotationPolicyListener, int i) {
        RotationPolicy.registerRotationPolicyListener(this.context, rotationPolicyListener, i);
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public void setRotationLock(boolean z, String str) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("RotationPolicyWrapperImpl#setRotationLock");
        }
        try {
            RotationPolicy.setRotationLock(this.context, z, str);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public void setRotationLockAtAngle(boolean z, int i, String str) {
        RotationPolicy.setRotationLockAtAngle(this.context, z, i, str);
    }

    @Override // com.android.systemui.util.wrapper.RotationPolicyWrapper
    public void unregisterRotationPolicyListener(RotationPolicy.RotationPolicyListener rotationPolicyListener) {
        RotationPolicy.unregisterRotationPolicyListener(this.context, rotationPolicyListener);
    }
}
