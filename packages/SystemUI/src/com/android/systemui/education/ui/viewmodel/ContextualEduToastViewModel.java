package com.android.systemui.education.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ContextualEduToastViewModel extends ContextualEduContentViewModel {
    public final int icon;
    public final String message;
    public final int userId;

    public ContextualEduToastViewModel(String str, int i, int i2) {
        super(i2, null);
        this.message = str;
        this.icon = i;
        this.userId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContextualEduToastViewModel)) {
            return false;
        }
        ContextualEduToastViewModel contextualEduToastViewModel = (ContextualEduToastViewModel) obj;
        return Intrinsics.areEqual(this.message, contextualEduToastViewModel.message) && this.icon == contextualEduToastViewModel.icon && this.userId == contextualEduToastViewModel.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + ReorderTile$$ExternalSyntheticOutline0.m(this.icon, this.message.hashCode() * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ContextualEduToastViewModel(message=");
        sb.append(this.message);
        sb.append(", icon=");
        sb.append(this.icon);
        sb.append(", userId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
    }
}
