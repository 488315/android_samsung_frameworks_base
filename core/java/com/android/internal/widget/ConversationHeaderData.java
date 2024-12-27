package com.android.internal.widget;

/* loaded from: classes5.dex */
public final class ConversationHeaderData {
    private final ConversationAvatarData mConversationAvatarData;
    private final CharSequence mConversationText;

    ConversationHeaderData(
            CharSequence conversationText, ConversationAvatarData conversationAvatarData) {
        this.mConversationText = conversationText;
        this.mConversationAvatarData = conversationAvatarData;
    }

    CharSequence getConversationText() {
        return this.mConversationText;
    }

    public ConversationAvatarData getConversationAvatar() {
        return this.mConversationAvatarData;
    }
}
