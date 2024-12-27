package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.database.ContentObserver;

import com.samsung.android.sepunion.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemDeviceInfoManagerService$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemDeviceInfoManagerService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SemDeviceInfoManagerService$$ExternalSyntheticLambda1(
            SemDeviceInfoManagerService semDeviceInfoManagerService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = semDeviceInfoManagerService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemDeviceInfoManagerService semDeviceInfoManagerService = this.f$0;
                ContentObserver contentObserver = (ContentObserver) this.f$1;
                int i = SemDeviceInfoManagerService.$r8$clinit;
                semDeviceInfoManagerService.getClass();
                try {
                    semDeviceInfoManagerService
                            .mContext
                            .getContentResolver()
                            .unregisterContentObserver(contentObserver);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                SemDeviceInfoManagerService semDeviceInfoManagerService2 = this.f$0;
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) this.f$1;
                int i2 = SemDeviceInfoManagerService.$r8$clinit;
                semDeviceInfoManagerService2.getClass();
                try {
                    semDeviceInfoManagerService2.mContext.unregisterReceiver(broadcastReceiver);
                    break;
                } catch (Exception e2) {
                    Log.e("SemDeviceInfoManagerService", "Failed to unregister receiver " + e2);
                }
        }
    }
}
