package com.android.server.people.data;

import android.util.ArrayMap;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataManager$MmsSmsContentObserver$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Event f$1;

    public /* synthetic */ DataManager$MmsSmsContentObserver$$ExternalSyntheticLambda0(
            String str, Event event, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
        this.f$1 = event;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                Event event = this.f$1;
                UserData userData = (UserData) obj;
                String str2 = userData.mDefaultSmsApp;
                PackageData packageData =
                        str2 != null
                                ? (PackageData) ((ArrayMap) userData.mPackageDataMap).get(str2)
                                : null;
                if (packageData != null
                        && packageData.mConversationStore.getConversationByPhoneNumber(str)
                                != null) {
                    packageData.mEventStore.getOrCreateEventHistory(3, str).addEvent(event);
                    break;
                }
                break;
            default:
                String str3 = this.f$0;
                Event event2 = this.f$1;
                UserData userData2 = (UserData) obj;
                String str4 = userData2.mDefaultDialer;
                PackageData packageData2 =
                        str4 != null
                                ? (PackageData) ((ArrayMap) userData2.mPackageDataMap).get(str4)
                                : null;
                if (packageData2 != null
                        && packageData2.mConversationStore.getConversationByPhoneNumber(str3)
                                != null) {
                    packageData2.mEventStore.getOrCreateEventHistory(2, str3).addEvent(event2);
                    break;
                }
                break;
        }
    }
}
