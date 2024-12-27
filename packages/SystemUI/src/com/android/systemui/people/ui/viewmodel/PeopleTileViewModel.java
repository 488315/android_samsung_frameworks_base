package com.android.systemui.people.ui.viewmodel;

import android.graphics.Bitmap;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.systemui.people.widget.PeopleTileKey;
import kotlin.jvm.internal.Intrinsics;

public final class PeopleTileViewModel {
    public final Bitmap icon;
    public final PeopleTileKey key;
    public final String username;

    public PeopleTileViewModel(PeopleTileKey peopleTileKey, Bitmap bitmap, String str) {
        this.key = peopleTileKey;
        this.icon = bitmap;
        this.username = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PeopleTileViewModel)) {
            return false;
        }
        PeopleTileViewModel peopleTileViewModel = (PeopleTileViewModel) obj;
        return Intrinsics.areEqual(this.key, peopleTileViewModel.key) && Intrinsics.areEqual(this.icon, peopleTileViewModel.icon) && Intrinsics.areEqual(this.username, peopleTileViewModel.username);
    }

    public final int hashCode() {
        int hashCode = (this.icon.hashCode() + (this.key.hashCode() * 31)) * 31;
        String str = this.username;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        Bitmap bitmap = this.icon;
        StringBuilder sb = new StringBuilder("PeopleTileViewModel(key=");
        sb.append(this.key);
        sb.append(", icon=");
        sb.append(bitmap);
        sb.append(", username=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.username, ")");
    }
}
