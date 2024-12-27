package com.android.server;

import android.content.ContentResolver;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExtendedEthernetServiceImpl.InterfaceObserver f$0;

    public /* synthetic */ ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda1(
            ExtendedEthernetServiceImpl.InterfaceObserver interfaceObserver, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = interfaceObserver;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ExtendedEthernetServiceImpl.InterfaceObserver interfaceObserver = this.f$0;
        switch (i) {
            case 0:
                ExtendedEthernetServiceImpl extendedEthernetServiceImpl = interfaceObserver.this$0;
                ContentResolver contentResolver =
                        extendedEthernetServiceImpl.mContext.getContentResolver();
                Settings.System.putIntForUser(contentResolver, "eth_device_conn", 2, 0);
                Log.i(
                        "ExtendedEthernetServiceImpl",
                        "ETH_DEVICE_CONNECTED is : "
                                + Settings.System.getIntForUser(
                                        contentResolver, "eth_device_conn", 0, 0));
                if ("on"
                                .equals(
                                        Settings.System.getString(
                                                contentResolver, "ETHERNET_TETHERING_MODE"))
                        && extendedEthernetServiceImpl.mTetheredRequest == null) {
                    Log.i(
                            "ExtendedEthernetServiceImpl",
                            "onChange() call requestTetheredInterface()");
                    extendedEthernetServiceImpl.mTetheredRequest =
                            extendedEthernetServiceImpl.mEthernetManager.requestTetheredInterface(
                                    new HandlerExecutor(extendedEthernetServiceImpl.mHandler),
                                    extendedEthernetServiceImpl.mEthernetCallback);
                    break;
                }
                break;
            default:
                ExtendedEthernetServiceImpl extendedEthernetServiceImpl2 = interfaceObserver.this$0;
                ContentResolver contentResolver2 =
                        extendedEthernetServiceImpl2.mContext.getContentResolver();
                Settings.System.putIntForUser(contentResolver2, "eth_device_conn", 1, 0);
                Log.i(
                        "ExtendedEthernetServiceImpl",
                        "ETH_DEVICE_CONNECTED is : "
                                + Settings.System.getIntForUser(
                                        contentResolver2, "eth_device_conn", 0, 0));
                extendedEthernetServiceImpl2.mTetheredRequest = null;
                break;
        }
    }
}
