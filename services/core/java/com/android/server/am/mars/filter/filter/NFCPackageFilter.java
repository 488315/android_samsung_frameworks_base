package com.android.server.am.mars.filter.filter;

import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.Context;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NFCPackageFilter implements IFilter {
    public Context mContext;
    public NFCPackageFilter$$ExternalSyntheticLambda0 mOnRoleHoldersChangedListener;
    public int userId;
    public final Object mLock = new Object();
    public String mPaymentDefaultPackage = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class NFCPackageFilterHolder {
        public static final NFCPackageFilter INSTANCE = new NFCPackageFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
        if (roleManager != null) {
            roleManager.removeOnRoleHoldersChangedListenerAsUser(this.mOnRoleHoldersChangedListener, UserHandle.of(this.userId));
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        String str2;
        synchronized (this.mLock) {
            str2 = this.mPaymentDefaultPackage;
        }
        return (str2 == null || !str2.equals(str)) ? 0 : 31;
    }

    public final String getPaymentDefaultPackageFromRoleManager() {
        RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
        if (roleManager == null) {
            return null;
        }
        try {
            if (!roleManager.isRoleAvailable("android.app.role.WALLET")) {
                Slog.e("NFCFilter", "ROLE_WALLET is not available");
                return null;
            }
            List roleHoldersAsUser = roleManager.getRoleHoldersAsUser("android.app.role.WALLET", UserHandle.of(this.userId));
            if (roleHoldersAsUser.isEmpty()) {
                return null;
            }
            return (String) roleHoldersAsUser.get(0);
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception "), "NFCFilter");
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.am.mars.filter.filter.NFCPackageFilter$$ExternalSyntheticLambda0] */
    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        this.userId = context.getUserId();
        setPaymentDefaultPackage(getPaymentDefaultPackageFromRoleManager());
        this.mOnRoleHoldersChangedListener = new OnRoleHoldersChangedListener() { // from class: com.android.server.am.mars.filter.filter.NFCPackageFilter$$ExternalSyntheticLambda0
            public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                NFCPackageFilter nFCPackageFilter = NFCPackageFilter.this;
                nFCPackageFilter.getClass();
                if (str.equals("android.app.role.WALLET")) {
                    nFCPackageFilter.setPaymentDefaultPackage(nFCPackageFilter.getPaymentDefaultPackageFromRoleManager());
                }
            }
        };
        RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
        if (roleManager != null) {
            roleManager.addOnRoleHoldersChangedListenerAsUser(context.getMainExecutor(), this.mOnRoleHoldersChangedListener, UserHandle.of(this.userId));
        }
    }

    public final void setPaymentDefaultPackage(String str) {
        synchronized (this.mLock) {
            try {
                this.mPaymentDefaultPackage = str;
                if (str == null) {
                    str = "null";
                }
                MARsUtils.addFilterDebugInfoToHistory("FILTER 31", str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
