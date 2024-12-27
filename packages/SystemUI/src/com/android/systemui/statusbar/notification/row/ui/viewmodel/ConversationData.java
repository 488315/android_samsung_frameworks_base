package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

public final class ConversationData {
    public final ConversationAvatar avatar;
    public final CharSequence conversationSenderName;

    public ConversationData(CharSequence charSequence, ConversationAvatar conversationAvatar) {
        this.conversationSenderName = charSequence;
        this.avatar = conversationAvatar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConversationData)) {
            return false;
        }
        ConversationData conversationData = (ConversationData) obj;
        return Intrinsics.areEqual(this.conversationSenderName, conversationData.conversationSenderName) && Intrinsics.areEqual(this.avatar, conversationData.avatar);
    }

    public final int hashCode() {
        CharSequence charSequence = this.conversationSenderName;
        return this.avatar.hashCode() + ((charSequence == null ? 0 : charSequence.hashCode()) * 31);
    }

    public final String toString() {
        return "ConversationData(conversationSenderName=" + ((Object) this.conversationSenderName) + ", avatar=" + this.avatar + ")";
    }
}
