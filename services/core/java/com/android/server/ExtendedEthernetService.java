package com.android.server;

import android.content.ContentResolver;
import android.content.Context;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.TetheringManager;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExtendedEthernetService extends SystemService {
    public final ExtendedEthernetServiceImpl mImpl;

    public ExtendedEthernetService(Context context) {
        super(context);
        this.mImpl = new ExtendedEthernetServiceImpl(getContext());
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        ArrayMap arrayMap;
        if (i == 550) {
            ExtendedEthernetServiceImpl extendedEthernetServiceImpl = this.mImpl;
            extendedEthernetServiceImpl.getClass();
            Log.i("ExtendedEthernetServiceImpl", "handleSystemReady");
            extendedEthernetServiceImpl.mTetheringManager =
                    (TetheringManager)
                            extendedEthernetServiceImpl.mContext.getSystemService("tethering");
            EthernetManager ethernetManager =
                    (EthernetManager)
                            extendedEthernetServiceImpl.mContext.getSystemService(
                                    KnoxCustomManagerService.ETHERNET_SERVICE);
            extendedEthernetServiceImpl.mEthernetManager = ethernetManager;
            ethernetManager.addInterfaceStateListener(
                    new HandlerExecutor(extendedEthernetServiceImpl.mHandler),
                    extendedEthernetServiceImpl.mStateListener);
            ContentResolver contentResolver =
                    extendedEthernetServiceImpl.mContext.getContentResolver();
            if ("on"
                    .equals(
                            Settings.System.getString(
                                    contentResolver, "ETHERNET_TETHERING_MODE"))) {
                Log.i("ExtendedEthernetServiceImpl", "ETHERNET_TETHERING_MODE is on");
                extendedEthernetServiceImpl.mTetheredRequest =
                        extendedEthernetServiceImpl.mEthernetManager.requestTetheredInterface(
                                new HandlerExecutor(extendedEthernetServiceImpl.mHandler),
                                extendedEthernetServiceImpl.mEthernetCallback);
            }
            try {
                if (Settings.System.getInt(contentResolver, "eth_disabled") == 1) {
                    extendedEthernetServiceImpl.mEthernetManager.setEthernetEnabled(false);
                    Log.e("ExtendedEthernetServiceImpl", "call setLinkDown");
                    extendedEthernetServiceImpl.setLinkDown();
                }
            } catch (Settings.SettingNotFoundException unused) {
                Log.i("ExtendedEthernetServiceImpl", "Not found ETH_DISABLED");
                Settings.System.putInt(contentResolver, "eth_disabled", 0);
            }
            ExtendedEthernetConfigStore extendedEthernetConfigStore =
                    new ExtendedEthernetConfigStore();
            extendedEthernetServiceImpl.mConfigStore = extendedEthernetConfigStore;
            extendedEthernetConfigStore.read();
            ExtendedEthernetConfigStore extendedEthernetConfigStore2 =
                    extendedEthernetServiceImpl.mConfigStore;
            synchronized (extendedEthernetConfigStore2.mSync) {
                arrayMap = new ArrayMap(extendedEthernetConfigStore2.mIpConfigurations);
            }
            for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                extendedEthernetServiceImpl.mIpConfigurations.put(
                        (String) arrayMap.keyAt(i2), (IpConfiguration) arrayMap.valueAt(i2));
            }
            extendedEthernetServiceImpl
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("eth_disabled"),
                            false,
                            extendedEthernetServiceImpl.mStateObserver,
                            0);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Log.i("ExtendedEthernetService", "Registering extendedethernetservice");
        publishBinderService("extendedethernetservice", this.mImpl);
    }
}
