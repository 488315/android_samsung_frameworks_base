package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import com.sec.android.sdhms.ISamsungDeviceHealthManager;

public final class ActiveTrafficFilter implements IFilter {
    public Context mContext;
    public boolean mIsDataConnectionConnected;

    public abstract class ActiveTrafficFilterHolder {
        public static final ActiveTrafficFilter INSTANCE;

        static {
            ActiveTrafficFilter activeTrafficFilter = new ActiveTrafficFilter();
            activeTrafficFilter.mContext = null;
            activeTrafficFilter.mIsDataConnectionConnected = false;
            INSTANCE = activeTrafficFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        IBinder service;
        ISamsungDeviceHealthManager asInterface;
        if (MARsUtils.isChinaPolicyEnabled()) {
            boolean z = MARsPolicyManager.MARs_ENABLE;
            if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getForegroundServiceStartTime(i2)
                    == 0) {
                return 0;
            }
        }
        if (this.mIsDataConnectionConnected
                && (service = ServiceManager.getService("sdhms")) != null
                && (asInterface = ISamsungDeviceHealthManager.Stub.asInterface(service)) != null) {
            try {
                if (asInterface.isDownLoadingForUid(i2)) {
                    Slog.d("MARs:ActiveTrafficFilter", "filter : " + str + "(" + i + ")");
                    return 8;
                }
            } catch (RemoteException e) {
                Slog.e("MARs:ActiveTrafficFilter", "isDownloadingPackage exception:" + e);
            }
        }
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
    }
}
