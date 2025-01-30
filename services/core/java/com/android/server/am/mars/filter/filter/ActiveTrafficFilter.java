package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.IFilter;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;

/* loaded from: classes.dex */
public class ActiveTrafficFilter implements IFilter {
    public static String TAG = "MARs:" + ActiveTrafficFilter.class.getSimpleName();
    public Context mContext;
    public boolean mIsDataConnectionConnected;

    public abstract class ActiveTrafficFilterHolder {
        public static final ActiveTrafficFilter INSTANCE = new ActiveTrafficFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    public ActiveTrafficFilter() {
        this.mContext = null;
        this.mIsDataConnectionConnected = false;
    }

    public static ActiveTrafficFilter getInstance() {
        return ActiveTrafficFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        IBinder service;
        ISamsungDeviceHealthManager asInterface;
        if ((!MARsPolicyManager.getInstance().isChinaPolicyEnabled() || MARsPolicyManager.getInstance().isForegroundServicePkg(i2) || MARsVersionManager.getInstance().isAdjustRestrictionMatch(15, null, str, null)) && this.mIsDataConnectionConnected && (service = ServiceManager.getService("sdhms")) != null && (asInterface = ISamsungDeviceHealthManager.Stub.asInterface(service)) != null) {
            try {
                if (asInterface.isDownLoadingForUid(i2)) {
                    Slog.d(TAG, "filter : " + str + "(" + i + ")");
                    return 8;
                }
            } catch (RemoteException e) {
                Slog.e(TAG, "isDownloadingPackage exception:" + e);
            }
        }
        return 0;
    }

    public void updateDataConnectionInfo() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        this.mIsDataConnectionConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (MARsDebugConfig.DEBUG_FILTER) {
            Slog.d(TAG, "DataConnection: " + this.mIsDataConnectionConnected);
        }
    }
}
