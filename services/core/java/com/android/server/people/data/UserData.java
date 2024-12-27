package com.android.server.people.data;

import android.os.Environment;
import android.os.FileUtils;
import android.util.ArrayMap;
import com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda0;
import com.android.server.people.data.ConversationStore;
import com.android.server.people.data.EventHistoryImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserData {
    public String mDefaultDialer;
    public String mDefaultSmsApp;
    public boolean mIsUnlocked;
    public final Map mPackageDataMap = new ArrayMap();
    public final File mPerUserPeopleDataDir;
    public final ScheduledExecutorService mScheduledExecutorService;
    public final int mUserId;

    public UserData(int i, ScheduledExecutorService scheduledExecutorService) {
        this.mUserId = i;
        this.mPerUserPeopleDataDir = new File(Environment.getDataSystemCeDirectory(i), "people");
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    public final void deletePackageData(String str) {
        PackageData packageData = (PackageData) ((ArrayMap) this.mPackageDataMap).remove(str);
        if (packageData != null) {
            EventStore eventStore = packageData.mEventStore;
            synchronized (eventStore) {
                Iterator it = ((ArrayList) eventStore.mEventHistoryMaps).iterator();
                while (it.hasNext()) {
                    Iterator it2 = ((Map) it.next()).values().iterator();
                    while (it2.hasNext()) {
                        ((EventHistoryImpl) it2.next()).onDestroy();
                    }
                }
            }
            ConversationStore conversationStore = packageData.mConversationStore;
            synchronized (conversationStore) {
                ((ArrayMap) conversationStore.mConversationInfoMap).clear();
                ((ArrayMap) conversationStore.mContactUriToShortcutIdMap).clear();
                ((ArrayMap) conversationStore.mLocusIdToShortcutIdMap).clear();
                ((ArrayMap) conversationStore.mNotifChannelIdToShortcutIdMap).clear();
                ((ArrayMap) conversationStore.mPhoneNumberToShortcutIdMap).clear();
            }
            ConversationStore.ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter = conversationStore.getConversationInfosProtoDiskReadWriter();
            if (conversationInfosProtoDiskReadWriter != null) {
                conversationInfosProtoDiskReadWriter.delete(conversationInfosProtoDiskReadWriter.mConversationInfoFileName);
            }
            FileUtils.deleteContentsAndDir(packageData.mPackageDataDir);
        }
    }

    public final void forAllPackages(Consumer consumer) {
        Iterator it = ((ArrayMap) this.mPackageDataMap).values().iterator();
        while (it.hasNext()) {
            consumer.accept((PackageData) it.next());
        }
    }

    public final void loadUserData() {
        List list;
        this.mPerUserPeopleDataDir.mkdir();
        int i = this.mUserId;
        UserData$$ExternalSyntheticLambda1 userData$$ExternalSyntheticLambda1 = new UserData$$ExternalSyntheticLambda1(this, 0);
        UserData$$ExternalSyntheticLambda1 userData$$ExternalSyntheticLambda12 = new UserData$$ExternalSyntheticLambda1(this, 1);
        ScheduledExecutorService scheduledExecutorService = this.mScheduledExecutorService;
        File file = this.mPerUserPeopleDataDir;
        ArrayMap arrayMap = new ArrayMap();
        File[] listFiles = file.listFiles(new CpuInfoReader$$ExternalSyntheticLambda0(2));
        if (listFiles != null) {
            int length = listFiles.length;
            int i2 = 0;
            while (i2 < length) {
                File file2 = listFiles[i2];
                int i3 = i;
                int i4 = i2;
                PackageData packageData = new PackageData(file2.getName(), i, userData$$ExternalSyntheticLambda1, userData$$ExternalSyntheticLambda12, scheduledExecutorService, file);
                ConversationStore conversationStore = packageData.mConversationStore;
                ConversationStore.ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter = conversationStore.getConversationInfosProtoDiskReadWriter();
                if (conversationInfosProtoDiskReadWriter != null && (list = (List) conversationInfosProtoDiskReadWriter.read("conversations")) != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        conversationStore.updateConversationsInMemory((ConversationInfo) it.next());
                    }
                }
                EventStore eventStore = packageData.mEventStore;
                synchronized (eventStore) {
                    for (int i5 = 0; i5 < ((ArrayList) eventStore.mEventsCategoryDirs).size(); i5++) {
                        ((Map) ((ArrayList) eventStore.mEventHistoryMaps).get(i5)).putAll(EventHistoryImpl.eventHistoriesImplFromDisk(new EventHistoryImpl.Injector(), (File) ((ArrayList) eventStore.mEventsCategoryDirs).get(i5), eventStore.mScheduledExecutorService));
                    }
                }
                arrayMap.put(file2.getName(), packageData);
                i2 = i4 + 1;
                i = i3;
            }
        }
        ((ArrayMap) this.mPackageDataMap).putAll((Map) arrayMap);
    }
}
