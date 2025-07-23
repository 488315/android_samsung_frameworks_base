package com.samsung.android.sepunion;

import android.content.Context;
import com.samsung.android.sepunion.ITipsManager;

/* loaded from: classes6.dex */
public class TipsManager {
    public static final String FOTA_READY_ACTION = "android.samsung.tips.FOTA_READY";
    private static final String TAG = "TipsManager";
    private Context mContext;
    private ITipsManager mService;

    public TipsManager(Context context) {
        this.mContext = context;
    }

    private ITipsManager getService() {
        if (this.mService == null) {
            this.mService = ITipsManager.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_TIPS));
        }
        return this.mService;
    }
}
