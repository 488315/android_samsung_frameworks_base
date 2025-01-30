package com.android.systemui.controls.management.model;

import android.content.ComponentName;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.CustomControlInterface;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlStatusWrapper extends CustomElementWrapper implements ControlInterface, CustomControlInterface {
    public final ControlStatus controlStatus;

    public CustomControlStatusWrapper(ControlStatus controlStatus) {
        super(null);
        this.controlStatus = controlStatus;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CustomControlStatusWrapper) && Intrinsics.areEqual(this.controlStatus, ((CustomControlStatusWrapper) obj).controlStatus);
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final ComponentName getComponent() {
        return this.controlStatus.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final String getControlId() {
        return this.controlStatus.getControlId();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final ColorStateList getCustomColor() {
        return this.controlStatus.getCustomColor();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final Icon getCustomIcon() {
        return this.controlStatus.getCustomIcon();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final int getCustomIconAnimationEndFrame() {
        return this.controlStatus.getCustomIconAnimationEndFrame();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final String getCustomIconAnimationJson() {
        return this.controlStatus.getCustomIconAnimationJson();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final String getCustomIconAnimationJsonCache() {
        return this.controlStatus.getCustomIconAnimationJsonCache();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final int getCustomIconAnimationRepeatCount() {
        return this.controlStatus.getCustomIconAnimationRepeatCount();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final int getCustomIconAnimationStartFrame() {
        return this.controlStatus.getCustomIconAnimationStartFrame();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final int getDeviceType() {
        return this.controlStatus.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getFavorite() {
        return this.controlStatus.favorite;
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final Icon getOverlayCustomIcon() {
        return this.controlStatus.getOverlayCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getRemoved() {
        return this.controlStatus.removed;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getSubtitle() {
        return this.controlStatus.getSubtitle();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getTitle() {
        return this.controlStatus.getTitle();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final boolean getUseCustomIconWithoutPadding() {
        return this.controlStatus.getUseCustomIconWithoutPadding();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final boolean getUseCustomIconWithoutShadowBg() {
        return this.controlStatus.getUseCustomIconWithoutShadowBg();
    }

    public final int hashCode() {
        return this.controlStatus.hashCode();
    }

    public final String toString() {
        return "CustomControlStatusWrapper(controlStatus=" + this.controlStatus + ")";
    }
}
