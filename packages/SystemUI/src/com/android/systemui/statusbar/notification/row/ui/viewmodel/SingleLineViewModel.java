package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SingleLineViewModel {
    public final CharSequence contentText;
    public final ConversationData conversationData;
    public final CharSequence titleText;

    public SingleLineViewModel(CharSequence charSequence, CharSequence charSequence2, ConversationData conversationData) {
        this.titleText = charSequence;
        this.contentText = charSequence2;
        this.conversationData = conversationData;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SingleLineViewModel)) {
            return false;
        }
        SingleLineViewModel singleLineViewModel = (SingleLineViewModel) obj;
        return Intrinsics.areEqual(this.titleText, singleLineViewModel.titleText) && Intrinsics.areEqual(this.contentText, singleLineViewModel.contentText) && Intrinsics.areEqual(this.conversationData, singleLineViewModel.conversationData);
    }

    public final int hashCode() {
        CharSequence charSequence = this.titleText;
        int hashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
        CharSequence charSequence2 = this.contentText;
        int hashCode2 = (hashCode + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        ConversationData conversationData = this.conversationData;
        return hashCode2 + (conversationData != null ? conversationData.hashCode() : 0);
    }

    public final String toString() {
        return "SingleLineViewModel(titleText=" + ((Object) this.titleText) + ", contentText=" + ((Object) this.contentText) + ", conversationData=" + this.conversationData + ")";
    }
}
