package com.samsung.android.sepunion;

import android.content.Context;
import com.samsung.android.sepunion.IDexModeService;

/* loaded from: classes6.dex */
public class DexModeManagers {
    private static final String TAG = "DexModeManagers";
    private Context mContext;
    private IDexModeService mService;

    public DexModeManagers(Context context) {
        this.mContext = context;
    }

    private IDexModeService getService() {
        if (this.mService == null) {
            this.mService = IDexModeService.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_DESKTOPMODE_TV));
        }
        return this.mService;
    }
}
