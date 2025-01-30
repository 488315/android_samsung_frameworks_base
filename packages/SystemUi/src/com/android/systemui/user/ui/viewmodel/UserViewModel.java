package com.android.systemui.user.ui.viewmodel;

import android.graphics.drawable.Drawable;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserViewModel {
    public final float alpha;
    public final Drawable image;
    public final boolean isSelectionMarkerVisible;
    public final Text name;
    public final Function0 onClicked;
    public final int viewKey;

    public UserViewModel(int i, Text text, Drawable drawable, boolean z, float f, Function0 function0) {
        this.viewKey = i;
        this.name = text;
        this.image = drawable;
        this.isSelectionMarkerVisible = z;
        this.alpha = f;
        this.onClicked = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserViewModel)) {
            return false;
        }
        UserViewModel userViewModel = (UserViewModel) obj;
        return this.viewKey == userViewModel.viewKey && Intrinsics.areEqual(this.name, userViewModel.name) && Intrinsics.areEqual(this.image, userViewModel.image) && this.isSelectionMarkerVisible == userViewModel.isSelectionMarkerVisible && Float.compare(this.alpha, userViewModel.alpha) == 0 && Intrinsics.areEqual(this.onClicked, userViewModel.onClicked);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.image.hashCode() + ((this.name.hashCode() + (Integer.hashCode(this.viewKey) * 31)) * 31)) * 31;
        boolean z = this.isSelectionMarkerVisible;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int m90m = UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.alpha, (hashCode + i) * 31, 31);
        Function0 function0 = this.onClicked;
        return m90m + (function0 == null ? 0 : function0.hashCode());
    }

    public final String toString() {
        return "UserViewModel(viewKey=" + this.viewKey + ", name=" + this.name + ", image=" + this.image + ", isSelectionMarkerVisible=" + this.isSelectionMarkerVisible + ", alpha=" + this.alpha + ", onClicked=" + this.onClicked + ")";
    }
}
