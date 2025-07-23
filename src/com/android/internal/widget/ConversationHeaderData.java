package com.android.internal.widget;

/* loaded from: classes6.dex */
public final class ConversationHeaderData {
    private final ConversationAvatarData mConversationAvatarData;
    private final CharSequence mConversationText;

    ConversationHeaderData(CharSequence charSequence, ConversationAvatarData conversationAvatarData) {
        this.mConversationText = charSequence;
        this.mConversationAvatarData = conversationAvatarData;
    }

    CharSequence getConversationText() {
        return this.mConversationText;
    }

    public ConversationAvatarData getConversationAvatar() {
        return this.mConversationAvatarData;
    }
}
