package com.android.server.companion.virtual;

import android.util.ArraySet;

import com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda4;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public /* synthetic */ VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0(
            Set set, List list) {
        this.$r8$classId = 1;
        this.f$0 = set;
        this.f$1 = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ProxyManager$$ExternalSyntheticLambda4[] proxyManager$$ExternalSyntheticLambda4Arr =
                        (ProxyManager$$ExternalSyntheticLambda4[]) this.f$0;
                ArraySet arraySet = (ArraySet) this.f$1;
                for (ProxyManager$$ExternalSyntheticLambda4 proxyManager$$ExternalSyntheticLambda4 :
                        proxyManager$$ExternalSyntheticLambda4Arr) {
                    proxyManager$$ExternalSyntheticLambda4.f$0.notifyProxyOfRunningAppsChange(
                            arraySet);
                }
                break;
            case 1:
                Set<String> set = (Set) this.f$0;
                List list = (List) this.f$1;
                for (String str : set) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((Consumer) it.next()).accept(str);
                    }
                }
                break;
            default:
                VirtualDeviceManagerService.PendingTrampolineMap pendingTrampolineMap =
                        (VirtualDeviceManagerService.PendingTrampolineMap) this.f$0;
                VirtualDeviceImpl.PendingTrampoline pendingTrampoline =
                        (VirtualDeviceImpl.PendingTrampoline) this.f$1;
                pendingTrampolineMap.getClass();
                String creatorPackage = pendingTrampoline.mPendingIntent.getCreatorPackage();
                if (creatorPackage != null) {
                    pendingTrampolineMap.mHandler.removeCallbacksAndMessages(
                            (VirtualDeviceImpl.PendingTrampoline)
                                    pendingTrampolineMap.mMap.remove(creatorPackage));
                    break;
                }
                break;
        }
    }
}
