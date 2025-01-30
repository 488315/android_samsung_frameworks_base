package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlStatusWrapper extends ElementWrapper implements ControlInterface {
    public final ControlStatus controlStatus;

    public ControlStatusWrapper(ControlStatus controlStatus) {
        super(null);
        this.controlStatus = controlStatus;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ControlStatusWrapper) && Intrinsics.areEqual(this.controlStatus, ((ControlStatusWrapper) obj).controlStatus);
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final ComponentName getComponent() {
        return this.controlStatus.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final String getControlId() {
        return this.controlStatus.getControlId();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final Icon getCustomIcon() {
        return this.controlStatus.getCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final int getDeviceType() {
        return this.controlStatus.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getFavorite() {
        return this.controlStatus.favorite;
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

    public final int hashCode() {
        return this.controlStatus.hashCode();
    }

    public final String toString() {
        return "ControlStatusWrapper(controlStatus=" + this.controlStatus + ")";
    }
}
