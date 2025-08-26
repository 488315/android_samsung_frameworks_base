package com.android.systemui.bouncer.ui.viewmodel;

import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class MessageViewModel {
    public final boolean isUpdateAnimated;
    public final String secondaryText;
    public final String text;

    public MessageViewModel(String str, String str2, boolean z) {
        this.text = str;
        this.secondaryText = str2;
        this.isUpdateAnimated = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageViewModel)) {
            return false;
        }
        MessageViewModel messageViewModel = (MessageViewModel) obj;
        return Intrinsics.areEqual(this.text, messageViewModel.text) && Intrinsics.areEqual(this.secondaryText, messageViewModel.secondaryText) && this.isUpdateAnimated == messageViewModel.isUpdateAnimated;
    }

    public final int hashCode() {
        int iHashCode = this.text.hashCode() * 31;
        String str = this.secondaryText;
        return Boolean.hashCode(this.isUpdateAnimated) + ((iHashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MessageViewModel(text=");
        sb.append(this.text);
        sb.append(", secondaryText=");
        sb.append(this.secondaryText);
        sb.append(", isUpdateAnimated=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isUpdateAnimated, ")");
    }

    public /* synthetic */ MessageViewModel(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? true : z);
    }
}
