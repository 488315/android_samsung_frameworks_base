package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.service.controls.Control;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlStatus implements ControlInterface, SecControlInterface {
    public final ComponentName component;
    public final Control control;
    public boolean favorite;
    public final boolean removed;

    public ControlStatus(Control control, ComponentName componentName, boolean z, boolean z2) {
        this.control = control;
        this.component = componentName;
        this.favorite = z;
        this.removed = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlStatus)) {
            return false;
        }
        ControlStatus controlStatus = (ControlStatus) obj;
        return Intrinsics.areEqual(this.control, controlStatus.control) && Intrinsics.areEqual(this.component, controlStatus.component) && this.favorite == controlStatus.favorite && this.removed == controlStatus.removed;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final ComponentName getComponent() {
        return this.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final String getControlId() {
        return this.control.getControlId();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final Icon getCustomIcon() {
        return this.control.getCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final int getDeviceType() {
        return this.control.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getFavorite() {
        return this.favorite;
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final boolean getIconWithoutPadding() {
        return this.control.getCustomControl().getUseCustomIconWithoutPadding();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final boolean getIconWithoutShadowBg() {
        return this.control.getCustomControl().getUseCustomIconWithoutShadowBg();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final int getLottieIconAnimationEndFrame() {
        return this.control.getCustomControl().getCustomIconAnimationEndFrame();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final String getLottieIconAnimationJson() {
        return this.control.getCustomControl().getCustomIconAnimationJson();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final String getLottieIconAnimationJsonCache() {
        return this.control.getCustomControl().getCustomIconAnimationJsonCache();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final int getLottieIconAnimationRepeatCount() {
        return this.control.getCustomControl().getCustomIconAnimationRepeatCount();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final int getLottieIconAnimationStartFrame() {
        return this.control.getCustomControl().getCustomIconAnimationStartFrame();
    }

    @Override // com.android.systemui.controls.SecControlInterface
    public final Icon getOverlayCustomIcon() {
        return this.control.getCustomControl().getOverlayCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getRemoved() {
        return this.removed;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getSubtitle() {
        return this.control.getSubtitle();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getTitle() {
        return this.control.getTitle();
    }

    public final int hashCode() {
        return Boolean.hashCode(this.removed) + TransitionData$$ExternalSyntheticOutline0.m((this.component.hashCode() + (this.control.hashCode() * 31)) * 31, 31, this.favorite);
    }

    public final String toString() {
        Control control = this.control;
        ComponentName componentName = this.component;
        boolean z = this.favorite;
        StringBuilder sb = new StringBuilder("ControlStatus(control=");
        sb.append(control);
        sb.append(", component=");
        sb.append(componentName);
        sb.append(", favorite=");
        sb.append(z);
        sb.append(", removed=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.removed, ")");
    }

    public /* synthetic */ ControlStatus(Control control, ComponentName componentName, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(control, componentName, z, (i & 8) != 0 ? false : z2);
    }
}
