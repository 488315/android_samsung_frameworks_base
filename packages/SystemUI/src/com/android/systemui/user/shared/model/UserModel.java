package com.android.systemui.user.shared.model;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserModel {
    public final int id;
    public final Drawable image;
    public final boolean isGuest;
    public final boolean isSelectable;
    public final boolean isSelected;
    public final Text name;

    public UserModel(int i, Text text, Drawable drawable, boolean z, boolean z2, boolean z3) {
        this.id = i;
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
        return this.id == userModel.id && Intrinsics.areEqual(this.name, userModel.name) && Intrinsics.areEqual(this.image, userModel.image) && this.isSelected == userModel.isSelected && this.isSelectable == userModel.isSelectable && this.isGuest == userModel.isGuest;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isGuest) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.image.hashCode() + ((this.name.hashCode() + (Integer.hashCode(this.id) * 31)) * 31)) * 31, 31, this.isSelected), 31, this.isSelectable);
    }

    public final String toString() {
        Drawable drawable = this.image;
        StringBuilder sb = new StringBuilder("UserModel(id=");
        sb.append(this.id);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", image=");
        sb.append(drawable);
        sb.append(", isSelected=");
        sb.append(this.isSelected);
        sb.append(", isSelectable=");
        sb.append(this.isSelectable);
        sb.append(", isGuest=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isGuest, ")");
    }
}
