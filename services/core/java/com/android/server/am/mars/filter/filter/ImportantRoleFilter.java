package com.android.server.am.mars.filter.filter;

import android.app.role.RoleManager;
import android.content.Context;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ImportantRoleFilter implements IFilter {
    public final ArrayMap mSystemGalleryHolderList = new ArrayMap();
    public Context mContext = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ImportantRoleFilterHolder {
        public static final ImportantRoleFilter INSTANCE = new ImportantRoleFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        this.mSystemGalleryHolderList.clear();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
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
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception "), "MARs:ImportantRoleFilter");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        SemPersonaManager semPersonaManager;
        int knoxId;
        this.mContext = context;
        int userId = context.getUserId();
        getSystemGalleryHolder(userId);
        if (userId != 0 || (semPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona")) == null || (knoxId = semPersonaManager.getKnoxId(2, true)) < 150 || knoxId > 160) {
            return;
        }
        getSystemGalleryHolder(knoxId);
    }
}
