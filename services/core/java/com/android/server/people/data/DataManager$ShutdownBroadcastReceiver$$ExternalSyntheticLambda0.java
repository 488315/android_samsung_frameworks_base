package com.android.server.people.data;

import android.util.ArrayMap;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public final /* synthetic */ class DataManager$ShutdownBroadcastReceiver$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DataManager$ShutdownBroadcastReceiver$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ArrayList arrayList;
        switch (this.$r8$classId) {
            case 0:
                ((UserData) obj)
                        .forAllPackages(
                                new DataManager$ShutdownBroadcastReceiver$$ExternalSyntheticLambda0(
                                        1));
                return;
            default:
                PackageData packageData = (PackageData) obj;
                ConversationStore conversationStore = packageData.mConversationStore;
                ConversationStore.ConversationInfosProtoDiskReadWriter
                        conversationInfosProtoDiskReadWriter =
                                conversationStore.getConversationInfosProtoDiskReadWriter();
                if (conversationInfosProtoDiskReadWriter != null) {
                    synchronized (conversationStore) {
                        arrayList =
                                new ArrayList(
                                        ((ArrayMap) conversationStore.mConversationInfoMap)
                                                .values());
                    }
                    conversationInfosProtoDiskReadWriter.saveImmediately(
                            conversationInfosProtoDiskReadWriter.mConversationInfoFileName,
                            arrayList);
                }
                EventStore eventStore = packageData.mEventStore;
                synchronized (eventStore) {
                    Iterator it = ((ArrayList) eventStore.mEventHistoryMaps).iterator();
                    while (it.hasNext()) {
                        for (EventHistoryImpl eventHistoryImpl : ((Map) it.next()).values()) {
                            synchronized (eventHistoryImpl) {
                                eventHistoryImpl.mEventsProtoDiskReadWriter.saveImmediately(
                                        "recent", eventHistoryImpl.mRecentEvents);
                                eventHistoryImpl.mEventIndexesProtoDiskReadWriter.saveImmediately(
                                        LauncherConfigurationInternal.KEY_INDEX_INT,
                                        eventHistoryImpl.mEventIndexArray);
                            }
                        }
                    }
                }
                return;
        }
    }
}
