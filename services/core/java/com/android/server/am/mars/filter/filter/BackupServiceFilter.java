package com.android.server.am.mars.filter.filter;

import android.content.Context;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;

/* loaded from: classes.dex */
public class BackupServiceFilter implements IFilter {
    public static String TAG = "MARs:" + BackupServiceFilter.class.getSimpleName();
    public Context mContext;

    public abstract class BackupServiceFilterHolder {
        public static final BackupServiceFilter INSTANCE = new BackupServiceFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public BackupServiceFilter() {
        this.mContext = null;
    }

    public static BackupServiceFilter getInstance() {
        return BackupServiceFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        return MARsPolicyManager.getInstance().isBackupServicePkg(i2) ? 26 : 0;
    }
}
