package com.android.systemui.people.data.model;

import android.graphics.drawable.Icon;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.systemui.people.widget.PeopleTileKey;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.userIcon.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.username, this.key.hashCode() * 31, 31)) * 31;
        boolean z = this.hasNewStory;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode + i) * 31;
        boolean z2 = this.isImportant;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isDndBlocking;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PeopleTileModel(key=");
        sb.append(this.key);
        sb.append(", username=");
        sb.append(this.username);
        sb.append(", userIcon=");
        sb.append(this.userIcon);
        sb.append(", hasNewStory=");
        sb.append(this.hasNewStory);
        sb.append(", isImportant=");
        sb.append(this.isImportant);
        sb.append(", isDndBlocking=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isDndBlocking, ")");
    }
}
