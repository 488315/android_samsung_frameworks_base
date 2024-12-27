package com.android.server.people.data;

import android.app.people.ConversationChannel;
import android.util.Pair;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final /* synthetic */ class DataManager$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ DataManager f$0;
    public final /* synthetic */ PackageData f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ DataManager$$ExternalSyntheticLambda8(
            DataManager dataManager, PackageData packageData, List list) {
        this.f$0 = dataManager;
        this.f$1 = packageData;
        this.f$2 = list;
    }

    public /* synthetic */ DataManager$$ExternalSyntheticLambda8(
            DataManager dataManager, List list, PackageData packageData) {
        this.f$0 = dataManager;
        this.f$2 = list;
        this.f$1 = packageData;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConversationChannel conversationChannel;
        switch (this.$r8$classId) {
            case 0:
                DataManager dataManager = this.f$0;
                PackageData packageData = this.f$1;
                List list = this.f$2;
                ConversationInfo conversationInfo = (ConversationInfo) obj;
                dataManager.getClass();
                if (conversationInfo.hasShortcutFlags(
                                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                        && Objects.equals(
                                conversationInfo.mNotificationChannelId,
                                conversationInfo.mParentNotificationChannelId)
                        && conversationInfo.mLastEventTimestamp > 0
                        && (conversationChannel =
                                        dataManager.getConversationChannel(
                                                packageData.mPackageName,
                                                packageData.mUserId,
                                                conversationInfo.mShortcutId,
                                                conversationInfo))
                                != null
                        && conversationChannel.getNotificationChannel() != null) {
                    list.add(conversationChannel);
                    break;
                }
                break;
            default:
                DataManager dataManager2 = this.f$0;
                List list2 = this.f$2;
                PackageData packageData2 = this.f$1;
                ConversationInfo conversationInfo2 = (ConversationInfo) obj;
                dataManager2.getClass();
                if (conversationInfo2.hasShortcutFlags(
                                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                        && Objects.equals(
                                conversationInfo2.mNotificationChannelId,
                                conversationInfo2.mParentNotificationChannelId)) {
                    list2.add(Pair.create(packageData2.mPackageName, conversationInfo2));
                    break;
                }
                break;
        }
    }
}
