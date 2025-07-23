package com.android.systemui.people.data.model;

import android.graphics.drawable.Icon;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.people.widget.PeopleTileKey;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PeopleTileModel {
    public final boolean hasNewStory;
    public final boolean isDndBlocking;
    public final boolean isImportant;
    public final PeopleTileKey key;
    public final Icon userIcon;
    public final String username;

    public PeopleTileModel(PeopleTileKey peopleTileKey, String str, Icon icon, boolean z, boolean z2, boolean z3) {
        this.key = peopleTileKey;
        this.username = str;
        this.userIcon = icon;
        this.hasNewStory = z;
        this.isImportant = z2;
        this.isDndBlocking = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PeopleTileModel)) {
            return false;
        }
        PeopleTileModel peopleTileModel = (PeopleTileModel) obj;
        return Intrinsics.areEqual(this.key, peopleTileModel.key) && Intrinsics.areEqual(this.username, peopleTileModel.username) && Intrinsics.areEqual(this.userIcon, peopleTileModel.userIcon) && this.hasNewStory == peopleTileModel.hasNewStory && this.isImportant == peopleTileModel.isImportant && this.isDndBlocking == peopleTileModel.isDndBlocking;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isDndBlocking) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.userIcon.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.username)) * 31, 31, this.hasNewStory), 31, this.isImportant);
    }

    public final String toString() {
        Icon icon = this.userIcon;
        StringBuilder sb = new StringBuilder("PeopleTileModel(key=");
        sb.append(this.key);
        sb.append(", username=");
        sb.append(this.username);
        sb.append(", userIcon=");
        sb.append(icon);
        sb.append(", hasNewStory=");
        sb.append(this.hasNewStory);
        sb.append(", isImportant=");
        sb.append(this.isImportant);
        sb.append(", isDndBlocking=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isDndBlocking, ")");
    }
}
