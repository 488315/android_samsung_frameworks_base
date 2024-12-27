package com.android.server.people.data;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final /* synthetic */ class DataManager$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ DataManager f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DataManager$$ExternalSyntheticLambda5(
            DataManager dataManager, long j, int i) {
        this.f$0 = dataManager;
        this.f$1 = j;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        final DataManager dataManager = this.f$0;
        final long j = this.f$1;
        int i = this.f$2;
        PackageData packageData = (PackageData) obj;
        dataManager.getClass();
        final String str = packageData.mPackageName;
        final ArrayList arrayList = new ArrayList();
        final int i2 = packageData.mUserId;
        packageData.mConversationStore.forAllConversations(
                new Consumer() { // from class:
                                 // com.android.server.people.data.DataManager$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        DataManager dataManager2 = DataManager.this;
                        long j2 = j;
                        String str2 = str;
                        int i3 = i2;
                        List list = arrayList;
                        ConversationInfo conversationInfo = (ConversationInfo) obj2;
                        dataManager2.getClass();
                        String str3 = conversationInfo.mShortcutId;
                        if (conversationInfo.hasShortcutFlags(
                                        EndpointMonitorConst
                                                .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                                && Objects.equals(
                                        conversationInfo.mNotificationChannelId,
                                        conversationInfo.mParentNotificationChannelId)) {
                            long j3 = conversationInfo.mLastEventTimestamp;
                            if (j3 <= 0
                                    || j2 - j3 <= 864000000
                                    || dataManager2.hasActiveNotifications(i3, str2, str3)) {
                                return;
                            }
                            list.add(str3);
                        }
                    }
                });
        if (arrayList.isEmpty()) {
            return;
        }
        dataManager.mShortcutServiceInternal.uncacheShortcuts(
                i,
                dataManager.mContext.getPackageName(),
                str,
                arrayList,
                i2,
                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
    }
}
