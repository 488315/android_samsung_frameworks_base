package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int hashCode = (this.avatar.hashCode() + ((charSequence == null ? 0 : charSequence.hashCode()) * 31)) * 31;
        CharSequence charSequence2 = this.summarization;
        return hashCode + (charSequence2 != null ? charSequence2.hashCode() : 0);
    }

    public final String toString() {
        return "ConversationData(conversationSenderName=" + ((Object) this.conversationSenderName) + ", avatar=" + this.avatar + ", summarization=" + ((Object) this.summarization) + ")";
    }
}
