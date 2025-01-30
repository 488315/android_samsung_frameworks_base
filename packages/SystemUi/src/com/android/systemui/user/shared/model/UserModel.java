package com.android.systemui.user.shared.model;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserModel {

    /* renamed from: id */
    public final int f389id;
    public final Drawable image;
    public final boolean isGuest;
    public final boolean isSelectable;
    public final boolean isSelected;
    public final Text name;

    public UserModel(int i, Text text, Drawable drawable, boolean z, boolean z2, boolean z3) {
        this.f389id = i;
        this.name = text;
        this.image = drawable;
        this.isSelected = z;
        this.isSelectable = z2;
        this.isGuest = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserModel)) {
            return false;
        }
        UserModel userModel = (UserModel) obj;
        return this.f389id == userModel.f389id && Intrinsics.areEqual(this.name, userModel.name) && Intrinsics.areEqual(this.image, userModel.image) && this.isSelected == userModel.isSelected && this.isSelectable == userModel.isSelectable && this.isGuest == userModel.isGuest;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.image.hashCode() + ((this.name.hashCode() + (Integer.hashCode(this.f389id) * 31)) * 31)) * 31;
        boolean z = this.isSelected;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.isSelectable;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isGuest;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("UserModel(id=");
        sb.append(this.f389id);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", image=");
        sb.append(this.image);
        sb.append(", isSelected=");
        sb.append(this.isSelected);
        sb.append(", isSelectable=");
        sb.append(this.isSelectable);
        sb.append(", isGuest=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isGuest, ")");
    }
}
