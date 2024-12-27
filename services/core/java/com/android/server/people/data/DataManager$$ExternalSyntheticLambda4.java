package com.android.server.people.data;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DataManager$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DataManager f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ DataManager$$ExternalSyntheticLambda4(
            DataManager dataManager, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = dataManager;
        this.f$1 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DataManager dataManager = this.f$0;
                List list = this.f$1;
                PackageData packageData = (PackageData) obj;
                dataManager.getClass();
                packageData.mConversationStore.forAllConversations(
                        new DataManager$$ExternalSyntheticLambda8(dataManager, packageData, list));
                break;
            default:
                DataManager dataManager2 = this.f$0;
                List list2 = this.f$1;
                PackageData packageData2 = (PackageData) obj;
                dataManager2.getClass();
                packageData2.mConversationStore.forAllConversations(
                        new DataManager$$ExternalSyntheticLambda8(
                                dataManager2, list2, packageData2));
                break;
        }
    }
}
