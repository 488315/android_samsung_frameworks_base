package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.controller.ControlInfo;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlInfoWrapper extends ElementWrapper implements ControlInterface {
    public final ComponentName component;
    public final ControlInfo controlInfo;
    public final Function2 customIconGetter;
    public boolean favorite;

    public ControlInfoWrapper(ComponentName componentName, ControlInfo controlInfo, boolean z) {
        super(null);
        this.component = componentName;
        this.controlInfo = controlInfo;
        this.favorite = z;
        this.customIconGetter = ControlInfoWrapper$customIconGetter$1.INSTANCE;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlInfoWrapper)) {
            return false;
        }
        ControlInfoWrapper controlInfoWrapper = (ControlInfoWrapper) obj;
        return Intrinsics.areEqual(this.component, controlInfoWrapper.component) && Intrinsics.areEqual(this.controlInfo, controlInfoWrapper.controlInfo) && this.favorite == controlInfoWrapper.favorite;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final ComponentName getComponent() {
        return this.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final String getControlId() {
        return this.controlInfo.controlId;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final Icon getCustomIcon() {
        return (Icon) this.customIconGetter.invoke(this.component, this.controlInfo.controlId);
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final int getDeviceType() {
        return this.controlInfo.deviceType;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getFavorite() {
        return this.favorite;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getRemoved() {
        return false;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getSubtitle() {
        return this.controlInfo.controlSubtitle;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final CharSequence getTitle() {
        return this.controlInfo.controlTitle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.controlInfo.hashCode() + (this.component.hashCode() * 31)) * 31;
        boolean z = this.favorite;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "ControlInfoWrapper(component=" + this.component + ", controlInfo=" + this.controlInfo + ", favorite=" + this.favorite + ")";
    }

    public ControlInfoWrapper(ComponentName componentName, ControlInfo controlInfo, boolean z, Function2 function2) {
        this(componentName, controlInfo, z);
        this.customIconGetter = function2;
    }
}
