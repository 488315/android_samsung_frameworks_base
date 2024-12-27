package com.android.server.people.data;

import android.net.Uri;
import android.util.ArrayMap;
import com.android.server.people.data.DataManager;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataManager$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DataManager$$ExternalSyntheticLambda12(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConversationInfo conversation;
        switch (this.$r8$classId) {
            case 0:
                Set set = (Set) this.f$0;
                List list = (List) this.f$1;
                PackageData packageData = (PackageData) obj;
                if (set.contains(packageData.mPackageName)) {
                    return;
                }
                list.add(packageData.mPackageName);
                return;
            default:
                Uri uri = (Uri) this.f$0;
                DataManager.ContactsContentObserver.ConversationSelector conversationSelector = (DataManager.ContactsContentObserver.ConversationSelector) this.f$1;
                PackageData packageData2 = (PackageData) obj;
                ConversationStore conversationStore = packageData2.mConversationStore;
                synchronized (conversationStore) {
                    conversation = conversationStore.getConversation((String) ((ArrayMap) conversationStore.mContactUriToShortcutIdMap).get(uri));
                }
                if (conversation != null) {
                    conversationSelector.mConversationStore = packageData2.mConversationStore;
                    conversationSelector.mConversationInfo = conversation;
                    conversationSelector.mPackageName = packageData2.mPackageName;
                    return;
                }
                return;
        }
    }
}
