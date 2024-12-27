package com.samsung.android.sepunion;

import android.content.Context;
import android.os.IBinder;

/* loaded from: classes6.dex */
public class OneHandServiceManager {
    private static final String TAG = OneHandServiceManager.class.getSimpleName();
    private Context mContext;
    private IOneHandService mService;

    public OneHandServiceManager(Context context) {
        this.mContext = context;
    }

    private IOneHandService getService() {
        if (this.mService == null) {
            SemUnionManager um =
                    (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService(UnionConstants.SERVICE_ONE_HAND);
            this.mService = IOneHandService.Stub.asInterface(b);
        }
        return this.mService;
    }
}
