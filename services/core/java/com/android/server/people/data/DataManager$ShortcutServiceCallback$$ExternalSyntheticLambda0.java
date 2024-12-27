package com.android.server.people.data;

import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutServiceInternal;
import android.os.UserHandle;
import com.android.server.notification.ShortcutHelper;
import com.android.server.people.data.DataManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ DataManager.ShortcutServiceCallback f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ UserHandle f$3;

    public /* synthetic */ DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda0(DataManager.ShortcutServiceCallback shortcutServiceCallback, String str, UserHandle userHandle, List list) {
        this.f$0 = shortcutServiceCallback;
        this.f$2 = str;
        this.f$3 = userHandle;
        this.f$1 = list;
    }

    public /* synthetic */ DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda0(DataManager.ShortcutServiceCallback shortcutServiceCallback, List list, String str, UserHandle userHandle) {
        this.f$0 = shortcutServiceCallback;
        this.f$1 = list;
        this.f$2 = str;
        this.f$3 = userHandle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DataManager.ShortcutServiceCallback shortcutServiceCallback = this.f$0;
                List list = this.f$1;
                String str = this.f$2;
                UserHandle userHandle = this.f$3;
                shortcutServiceCallback.getClass();
                HashSet hashSet = new HashSet();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    hashSet.add(((ShortcutInfo) it.next()).getId());
                }
                DataManager.this.removeConversations(userHandle.getIdentifier(), str, hashSet);
                break;
            default:
                DataManager.ShortcutServiceCallback shortcutServiceCallback2 = this.f$0;
                String str2 = this.f$2;
                UserHandle userHandle2 = this.f$3;
                List<ShortcutInfo> list2 = this.f$1;
                PackageData packageData = DataManager.this.getPackage(userHandle2.getIdentifier(), str2);
                boolean z = false;
                for (ShortcutInfo shortcutInfo : list2) {
                    ShortcutServiceInternal shortcutServiceInternal = DataManager.this.mShortcutServiceInternal;
                    userHandle2.getIdentifier();
                    int i = ShortcutHelper.$r8$clinit;
                    if (shortcutInfo != null && shortcutInfo.isLongLived() && shortcutInfo.isEnabled()) {
                        if (shortcutInfo.isCached()) {
                            ConversationInfo conversation = packageData != null ? packageData.mConversationStore.getConversation(shortcutInfo.getId()) : null;
                            if (conversation == null || !conversation.hasShortcutFlags(EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)) {
                                z = true;
                            }
                        }
                        DataManager.this.addOrUpdateConversationInfo(shortcutInfo);
                    }
                }
                if (z) {
                    DataManager.this.cleanupCachedShortcuts(userHandle2.getIdentifier());
                    break;
                }
                break;
        }
    }
}
