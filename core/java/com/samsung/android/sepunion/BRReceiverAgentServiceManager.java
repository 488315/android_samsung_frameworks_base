package com.samsung.android.sepunion;

import android.content.Context;
import android.os.IBinder;
import com.samsung.android.sepunion.IBRReceiverAgent;

/* loaded from: classes6.dex */
public class BRReceiverAgentServiceManager {
    private static final String TAG = BRReceiverAgentServiceManager.class.getSimpleName();
    private Context mContext;
    private IBRReceiverAgent mService;

    public BRReceiverAgentServiceManager(Context context) {
        this.mContext = context;
    }

    private IBRReceiverAgent getService() {
        if (this.mService == null) {
            SemUnionManager um = (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService(UnionConstants.SERVICE_FW_BR_RECEIVER_AGENT);
            this.mService = IBRReceiverAgent.Stub.asInterface(b);
        }
        return this.mService;
    }
}
