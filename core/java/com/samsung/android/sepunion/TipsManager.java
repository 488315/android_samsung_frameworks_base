package com.samsung.android.sepunion;

import android.content.Context;
import android.os.IBinder;

public class TipsManager {
    public static final String FOTA_READY_ACTION = "android.samsung.tips.FOTA_READY";
    private static final String TAG = TipsManager.class.getSimpleName();
    private Context mContext;
    private ITipsManager mService;

    public TipsManager(Context context) {
        this.mContext = context;
    }

    private ITipsManager getService() {
        if (this.mService == null) {
            SemUnionManager um =
                    (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService(UnionConstants.SERVICE_TIPS);
            this.mService = ITipsManager.Stub.asInterface(b);
        }
        return this.mService;
    }
}
