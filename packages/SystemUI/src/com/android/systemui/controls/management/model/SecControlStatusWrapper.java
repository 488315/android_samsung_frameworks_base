package com.android.systemui.controls.management.model;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.SecControlInterface;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecControlStatusWrapper extends SecElementWrapper implements ControlInterface, SecControlInterface {
    public final ControlStatus controlStatus;

    public SecControlStatusWrapper(ControlStatus controlStatus) {
        super(null);
        this.controlStatus = controlStatus;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SecControlStatusWrapper) && Intrinsics.areEqual(this.controlStatus, ((SecControlStatusWrapper) obj).controlStatus);
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final ComponentName getComponent() {
        return this.controlStatus.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final String getControlId() {
        return this.controlStatus.control.getControlId();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final Icon getCustomIcon() {
        return this.controlStatus.control.getCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final int getDeviceType() {
        return this.controlStatus.control.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getFavorite() {
        return this.controlStatus.favorite;
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final boolean getIconWithoutPadding() {
        return this.controlStatus.getIconWithoutPadding();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final boolean getIconWithoutShadowBg() {
        return this.controlStatus.getIconWithoutShadowBg();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final int getLottieIconAnimationEndFrame() {
        return this.controlStatus.getLottieIconAnimationEndFrame();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final String getLottieIconAnimationJson() {
        return this.controlStatus.getLottieIconAnimationJson();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final String getLottieIconAnimationJsonCache() {
        return this.controlStatus.getLottieIconAnimationJsonCache();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final int getLottieIconAnimationRepeatCount() {
        return this.controlStatus.getLottieIconAnimationRepeatCount();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final int getLottieIconAnimationStartFrame() {
        return this.controlStatus.getLottieIconAnimationStartFrame();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final Icon getOverlayCustomIcon() {
        return this.controlStatus.getOverlayCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getRemoved() {
        return this.controlStatus.removed;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getSubtitle() {
        return this.controlStatus.control.getSubtitle();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getTitle() {
        return this.controlStatus.control.getTitle();
    }

    public final int hashCode() {
        return this.controlStatus.hashCode();
    }

    public final String toString() {
        return "SecControlStatusWrapper(controlStatus=" + this.controlStatus + ")";
    }
}
