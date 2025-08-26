package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ConversationData {
    public final ConversationAvatar avatar;
    public final CharSequence conversationSenderName;
    public final CharSequence summarization;

    public ConversationData(CharSequence charSequence, ConversationAvatar conversationAvatar, CharSequence charSequence2) {
        this.conversationSenderName = charSequence;
        this.avatar = conversationAvatar;
        this.summarization = charSequence2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConversationData)) {
            return false;
        }
        ConversationData conversationData = (ConversationData) obj;
        return Intrinsics.areEqual(this.conversationSenderName, conversationData.conversationSenderName) && Intrinsics.areEqual(this.avatar, conversationData.avatar) && Intrinsics.areEqual(this.summarization, conversationData.summarization);
    }

    public final int hashCode() {
        CharSequence charSequence = this.conversationSenderName;
        int iHashCode = (this.avatar.hashCode() + ((charSequence == null ? 0 : charSequence.hashCode()) * 31)) * 31;
        CharSequence charSequence2 = this.summarization;
        return iHashCode + (charSequence2 != null ? charSequence2.hashCode() : 0);
    }

    public final String toString() {
        return "ConversationData(conversationSenderName=" + ((Object) this.conversationSenderName) + ", avatar=" + this.avatar + ", summarization=" + ((Object) this.summarization) + ")";
    }
}
