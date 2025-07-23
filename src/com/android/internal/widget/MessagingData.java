package com.android.internal.widget;

import android.app.Person;
import java.util.List;

/* loaded from: classes6.dex */
final class MessagingData {
    private ConversationHeaderData mConversationHeaderData;
    private final List<List<MessagingMessage>> mGroups;
    private final List<MessagingMessage> mHistoricMessagingMessages;
    private final List<MessagingMessage> mNewMessagingMessages;
    private final List<Person> mSenders;
    private final boolean mShowSpinner;
    private final CharSequence mSummarization;
    private final int mUnreadCount;
    private final Person mUser;

    MessagingData(Person person, boolean z, List<MessagingMessage> list, List<MessagingMessage> list2, List<List<MessagingMessage>> list3, List<Person> list4, CharSequence charSequence) {
        this(person, z, 0, list, list2, list3, list4, null, charSequence);
    }

    MessagingData(Person person, boolean z, int i, List<MessagingMessage> list, List<MessagingMessage> list2, List<List<MessagingMessage>> list3, List<Person> list4, ConversationHeaderData conversationHeaderData, CharSequence charSequence) {
        this.mUser = person;
        this.mShowSpinner = z;
        this.mUnreadCount = i;
        this.mHistoricMessagingMessages = list;
        this.mNewMessagingMessages = list2;
        this.mGroups = list3;
        this.mSenders = list4;
        this.mConversationHeaderData = conversationHeaderData;
        this.mSummarization = charSequence;
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

    public CharSequence getSummarization() {
        return this.mSummarization;
    }
}
