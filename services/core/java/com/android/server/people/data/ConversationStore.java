package com.android.server.people.data;

import android.content.LocusId;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Slog;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ConversationStore {
    public ConversationInfosProtoDiskReadWriter mConversationInfosProtoDiskReadWriter;
    public final File mPackageDir;
    public final ScheduledExecutorService mScheduledExecutorService;
    public final Map mConversationInfoMap = new ArrayMap();
    public final Map mLocusIdToShortcutIdMap = new ArrayMap();
    public final Map mContactUriToShortcutIdMap = new ArrayMap();
    public final Map mPhoneNumberToShortcutIdMap = new ArrayMap();
    public final Map mNotifChannelIdToShortcutIdMap = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConversationInfosProtoDiskReadWriter extends AbstractProtoDiskReadWriter {
        public final String mConversationInfoFileName;

        public ConversationInfosProtoDiskReadWriter(
                File file, ScheduledExecutorService scheduledExecutorService) {
            super(file, scheduledExecutorService);
            this.mConversationInfoFileName = "conversations";
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        public final AbstractProtoDiskReadWriter.ProtoStreamReader protoStreamReader() {
            return new ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda0();
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        public final AbstractProtoDiskReadWriter.ProtoStreamWriter protoStreamWriter() {
            return new ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda0();
        }
    }

    public ConversationStore(File file, ScheduledExecutorService scheduledExecutorService) {
        this.mScheduledExecutorService = scheduledExecutorService;
        this.mPackageDir = file;
    }

    public final void forAllConversations(Consumer consumer) {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(((ArrayMap) this.mConversationInfoMap).values());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            consumer.accept((ConversationInfo) it.next());
        }
    }

    public final synchronized ConversationInfo getConversation(String str) {
        return str != null
                ? (ConversationInfo) ((ArrayMap) this.mConversationInfoMap).get(str)
                : null;
    }

    public final synchronized ConversationInfo getConversationByLocusId(LocusId locusId) {
        return getConversation((String) ((ArrayMap) this.mLocusIdToShortcutIdMap).get(locusId));
    }

    public final synchronized ConversationInfo getConversationByPhoneNumber(String str) {
        return getConversation((String) ((ArrayMap) this.mPhoneNumberToShortcutIdMap).get(str));
    }

    public final ConversationInfosProtoDiskReadWriter getConversationInfosProtoDiskReadWriter() {
        if (this.mPackageDir.exists()) {
            if (this.mConversationInfosProtoDiskReadWriter == null) {
                this.mConversationInfosProtoDiskReadWriter =
                        new ConversationInfosProtoDiskReadWriter(
                                this.mPackageDir, this.mScheduledExecutorService);
            }
            return this.mConversationInfosProtoDiskReadWriter;
        }
        Slog.e(
                "ConversationStore",
                "Package data directory does not exist: " + this.mPackageDir.getAbsolutePath());
        return null;
    }

    public final void scheduleUpdateConversationsOnDisk() {
        ArrayList arrayList;
        ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter =
                getConversationInfosProtoDiskReadWriter();
        if (conversationInfosProtoDiskReadWriter != null) {
            synchronized (this) {
                arrayList = new ArrayList(((ArrayMap) this.mConversationInfoMap).values());
            }
            conversationInfosProtoDiskReadWriter.scheduleSave(
                    conversationInfosProtoDiskReadWriter.mConversationInfoFileName, arrayList);
        }
    }

    public final synchronized void updateConversationsInMemory(ConversationInfo conversationInfo) {
        try {
            ((ArrayMap) this.mConversationInfoMap)
                    .put(conversationInfo.mShortcutId, conversationInfo);
            LocusId locusId = conversationInfo.mLocusId;
            if (locusId != null) {
                ((ArrayMap) this.mLocusIdToShortcutIdMap)
                        .put(locusId, conversationInfo.mShortcutId);
            }
            Uri uri = conversationInfo.mContactUri;
            if (uri != null) {
                ((ArrayMap) this.mContactUriToShortcutIdMap).put(uri, conversationInfo.mShortcutId);
            }
            String str = conversationInfo.mContactPhoneNumber;
            if (str != null) {
                ((ArrayMap) this.mPhoneNumberToShortcutIdMap)
                        .put(str, conversationInfo.mShortcutId);
            }
            String str2 = conversationInfo.mNotificationChannelId;
            if (str2 != null) {
                ((ArrayMap) this.mNotifChannelIdToShortcutIdMap)
                        .put(str2, conversationInfo.mShortcutId);
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
