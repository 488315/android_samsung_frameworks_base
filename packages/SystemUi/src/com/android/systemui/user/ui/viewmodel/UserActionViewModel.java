package com.android.systemui.user.ui.viewmodel;

import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserActionViewModel {
    public final int iconResourceId;
    public final Function0 onClicked;
    public final int textResourceId;
    public final long viewKey;

    public UserActionViewModel(long j, int i, int i2, Function0 function0) {
        this.viewKey = j;
        this.iconResourceId = i;
        this.textResourceId = i2;
        this.onClicked = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserActionViewModel)) {
            return false;
        }
        UserActionViewModel userActionViewModel = (UserActionViewModel) obj;
        return this.viewKey == userActionViewModel.viewKey && this.iconResourceId == userActionViewModel.iconResourceId && this.textResourceId == userActionViewModel.textResourceId && Intrinsics.areEqual(this.onClicked, userActionViewModel.onClicked);
    }

    public final int hashCode() {
        return this.onClicked.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.textResourceId, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.iconResourceId, Long.hashCode(this.viewKey) * 31, 31), 31);
    }

    public final String toString() {
        return "UserActionViewModel(viewKey=" + this.viewKey + ", iconResourceId=" + this.iconResourceId + ", textResourceId=" + this.textResourceId + ", onClicked=" + this.onClicked + ")";
    }
}
