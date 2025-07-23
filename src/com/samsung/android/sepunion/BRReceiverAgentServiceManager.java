package com.samsung.android.sepunion;

import android.content.Context;
import com.samsung.android.sepunion.IBRReceiverAgent;

/* loaded from: classes6.dex */
public class BRReceiverAgentServiceManager {
    private static final String TAG = "BRReceiverAgentServiceManager";
    private Context mContext;
    private IBRReceiverAgent mService;

    public BRReceiverAgentServiceManager(Context context) {
        this.mContext = context;
    }

    private IBRReceiverAgent getService() {
        if (this.mService == null) {
            this.mService = IBRReceiverAgent.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_FW_BR_RECEIVER_AGENT));
        }
        return this.mService;
    }
}
