package com.android.systemui.screenshot.policy;

import android.content.ComponentName;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LegacyCaptureParameters {
    public final ComponentName component;
    public final UserHandle owner;
    public final CaptureType type;

    public LegacyCaptureParameters(CaptureType captureType, ComponentName componentName, UserHandle userHandle) {
        this.type = captureType;
        this.component = componentName;
        this.owner = userHandle;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LegacyCaptureParameters)) {
            return false;
        }
        LegacyCaptureParameters legacyCaptureParameters = (LegacyCaptureParameters) obj;
        return Intrinsics.areEqual(this.type, legacyCaptureParameters.type) && Intrinsics.areEqual(this.component, legacyCaptureParameters.component) && Intrinsics.areEqual(this.owner, legacyCaptureParameters.owner);
    }

    public final int hashCode() {
        int hashCode = this.type.hashCode() * 31;
        ComponentName componentName = this.component;
        return this.owner.hashCode() + ((hashCode + (componentName == null ? 0 : componentName.hashCode())) * 31);
    }

    public final String toString() {
        return "LegacyCaptureParameters(type=" + this.type + ", component=" + this.component + ", owner=" + this.owner + ")";
    }
}
