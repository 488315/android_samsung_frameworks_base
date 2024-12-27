package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

public final class AllowListFilter implements IFilter {
    public List mCarrierAllowList;
    public Context mContext;
    public ArrayList mDefaultAllowList;

    public abstract class AllowListFilterHolder {
        public static final AllowListFilter INSTANCE;

        static {
            AllowListFilter allowListFilter = new AllowListFilter();
            allowListFilter.mContext = null;
            allowListFilter.mDefaultAllowList = new ArrayList();
            allowListFilter.mCarrierAllowList = new ArrayList();
            INSTANCE = allowListFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        synchronized (this.mDefaultAllowList) {
            this.mDefaultAllowList.clear();
        }
        synchronized (this.mCarrierAllowList) {
            ((ArrayList) this.mCarrierAllowList).clear();
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        setCarrierAllowList();
    }

    public final boolean isInDefaultAllowList(String str) {
        synchronized (this.mCarrierAllowList) {
            try {
                if (((ArrayList) this.mCarrierAllowList).contains(str)) {
                    return true;
                }
                synchronized (this.mDefaultAllowList) {
                    try {
                        return this.mDefaultAllowList.contains(str);
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final void setCarrierAllowList() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        if (telephonyManager != null) {
            List carrierPrivilegedPackagesForAllActiveSubscriptions =
                    telephonyManager.getCarrierPrivilegedPackagesForAllActiveSubscriptions();
            synchronized (this.mCarrierAllowList) {
                ((ArrayList) this.mCarrierAllowList).clear();
                ((ArrayList) this.mCarrierAllowList)
                        .addAll(carrierPrivilegedPackagesForAllActiveSubscriptions);
            }
        }
    }
}
