package com.android.internal.widget;

import android.app.Person;

import java.util.List;

/* loaded from: classes5.dex */
final class MessagingData {
    private ConversationHeaderData mConversationHeaderData;
    private final List<List<MessagingMessage>> mGroups;
    private final List<MessagingMessage> mHistoricMessagingMessages;
    private final List<MessagingMessage> mNewMessagingMessages;
    private final List<Person> mSenders;
    private final boolean mShowSpinner;
    private final int mUnreadCount;
    private final Person mUser;

    MessagingData(
            Person user,
            boolean showSpinner,
            List<MessagingMessage> historicMessagingMessages,
            List<MessagingMessage> newMessagingMessages,
            List<List<MessagingMessage>> groups,
            List<Person> senders) {
        this(
                user,
                showSpinner,
                0,
                historicMessagingMessages,
                newMessagingMessages,
                groups,
                senders,
                null);
    }

    MessagingData(
            Person user,
            boolean showSpinner,
            int unreadCount,
            List<MessagingMessage> historicMessagingMessages,
            List<MessagingMessage> newMessagingMessages,
            List<List<MessagingMessage>> groups,
            List<Person> senders,
            ConversationHeaderData conversationHeaderData) {
        this.mUser = user;
        this.mShowSpinner = showSpinner;
        this.mUnreadCount = unreadCount;
        this.mHistoricMessagingMessages = historicMessagingMessages;
        this.mNewMessagingMessages = newMessagingMessages;
        this.mGroups = groups;
        this.mSenders = senders;
        this.mConversationHeaderData = conversationHeaderData;
    }

    public Person getUser() {
        return this.mUser;
    }

    public boolean getShowSpinner() {
        return this.mShowSpinner;
    }

    public List<MessagingMessage> getHistoricMessagingMessages() {
        return this.mHistoricMessagingMessages;
    }

    public List<MessagingMessage> getNewMessagingMessages() {
        return this.mNewMessagingMessages;
    }

    public int getUnreadCount() {
        return this.mUnreadCount;
    }

    public List<Person> getSenders() {
        return this.mSenders;
    }

    public List<List<MessagingMessage>> getGroups() {
        return this.mGroups;
    }

    public ConversationHeaderData getConversationHeaderData() {
        return this.mConversationHeaderData;
    }
}
