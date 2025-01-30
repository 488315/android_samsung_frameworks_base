package com.android.server.am.mars.filter.filter;

import android.app.role.RoleManager;
import android.content.Context;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes.dex */
public class ImportantRoleFilter implements IFilter {
    public final String TAG;
    public Context mContext;
    public int mSecureFolderUserId;
    public ArrayMap mSystemGalleryHolderList;

    public abstract class ImportantRoleFilterHolder {
        public static final ImportantRoleFilter INSTANCE = new ImportantRoleFilter();
    }

    public ImportantRoleFilter() {
        this.TAG = "MARs:" + ImportantRoleFilter.class.getSimpleName();
        this.mSystemGalleryHolderList = new ArrayMap();
        this.mContext = null;
    }

    public static ImportantRoleFilter getInstance() {
        return ImportantRoleFilterHolder.INSTANCE;
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        SemPersonaManager semPersonaManager;
        setContext(context);
        int userId = this.mContext.getUserId();
        getSystemGalleryHolder(userId);
        if (userId != 0 || (semPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona")) == null) {
            return;
        }
        int knoxId = semPersonaManager.getKnoxId(2, true);
        this.mSecureFolderUserId = knoxId;
        if (knoxId < 150 || knoxId > 160) {
            return;
        }
        getSystemGalleryHolder(knoxId);
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        this.mSystemGalleryHolderList.clear();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        String str2 = (String) this.mSystemGalleryHolderList.get(Integer.valueOf(i));
        return (str2 == null || !str2.equals(str)) ? 0 : 22;
    }

    public final void getSystemGalleryHolder(int i) {
        try {
            String str = (String) ((RoleManager) this.mContext.getSystemService("role")).getRoleHoldersAsUser("android.app.role.SYSTEM_GALLERY", UserHandle.of(i)).get(0);
            if (str == null || str.equals(this.mSystemGalleryHolderList.get(Integer.valueOf(i)))) {
                return;
            }
            this.mSystemGalleryHolderList.put(Integer.valueOf(i), str);
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception " + e.getMessage());
        }
    }
}
