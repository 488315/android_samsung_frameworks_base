package com.android.systemui.controls;

import android.content.ComponentName;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.service.controls.Control;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlStatus implements ControlInterface, CustomControlInterface {
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

    @Override // com.android.systemui.controls.CustomControlInterface
    public final ColorStateList getCustomColor() {
        return this.control.getCustomColor();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final Icon getCustomIcon() {
        return this.control.getCustomIcon();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final int getCustomIconAnimationEndFrame() {
        return this.control.getCustomControl().getCustomIconAnimationEndFrame();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final String getCustomIconAnimationJson() {
        return this.control.getCustomControl().getCustomIconAnimationJson();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final String getCustomIconAnimationJsonCache() {
        return this.control.getCustomControl().getCustomIconAnimationJsonCache();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final int getCustomIconAnimationRepeatCount() {
        return this.control.getCustomControl().getCustomIconAnimationRepeatCount();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final int getCustomIconAnimationStartFrame() {
        return this.control.getCustomControl().getCustomIconAnimationStartFrame();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final int getDeviceType() {
        return this.control.getDeviceType();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public final boolean getFavorite() {
        return this.favorite;
    }

    @Override // com.android.systemui.controls.CustomControlInterface
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

    @Override // com.android.systemui.controls.CustomControlInterface
    public final boolean getUseCustomIconWithoutPadding() {
        return this.control.getCustomControl().getUseCustomIconWithoutPadding();
    }

    @Override // com.android.systemui.controls.CustomControlInterface
    public final boolean getUseCustomIconWithoutShadowBg() {
        return this.control.getCustomControl().getUseCustomIconWithoutShadowBg();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.component.hashCode() + (this.control.hashCode() * 31)) * 31;
        boolean z = this.favorite;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.removed;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        boolean z = this.favorite;
        StringBuilder sb = new StringBuilder("ControlStatus(control=");
        sb.append(this.control);
        sb.append(", component=");
        sb.append(this.component);
        sb.append(", favorite=");
        sb.append(z);
        sb.append(", removed=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.removed, ")");
    }

    public /* synthetic */ ControlStatus(Control control, ComponentName componentName, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(control, componentName, z, (i & 8) != 0 ? false : z2);
    }
}
