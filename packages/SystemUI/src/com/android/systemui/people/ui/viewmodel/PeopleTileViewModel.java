package com.android.systemui.people.ui.viewmodel;

import android.graphics.Bitmap;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.people.widget.PeopleTileKey;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.username, ")");
    }
}
