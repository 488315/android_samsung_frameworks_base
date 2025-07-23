package com.samsung.android.sepunion;

import android.content.Context;
import com.samsung.android.sepunion.IOneHandService;

/* loaded from: classes6.dex */
public class OneHandServiceManager {
    private static final String TAG = "OneHandServiceManager";
    private Context mContext;
    private IOneHandService mService;

    public OneHandServiceManager(Context context) {
        this.mContext = context;
    }

    private IOneHandService getService() {
        if (this.mService == null) {
            this.mService = IOneHandService.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_ONE_HAND));
        }
        return this.mService;
    }
}
