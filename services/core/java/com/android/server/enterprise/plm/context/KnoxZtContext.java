package com.android.server.enterprise.plm.context;

import android.content.Context;
import android.util.Log;
import com.android.server.enterprise.plm.IStateDelegate;

/* loaded from: classes2.dex */
public class KnoxZtContext extends ProcessContext {
    public static final String TAG = "KnoxZtContext";

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public String getDisplayName() {
        return "KnoxZtService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public String getPackageName() {
        return "com.samsung.android.knox.zt.framework";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public String getServiceName() {
        return "com.samsung.android.knox.zt.framework.core.KnoxZtService";
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public boolean needToSupportThisDevice() {
        return true;
    }

    public KnoxZtContext(Context context) {
        super(context);
    }

    @Override // com.android.server.enterprise.plm.context.ProcessContext
    public boolean needToKeepProcessAlive(IStateDelegate iStateDelegate) {
        boolean isKlmActivated = iStateDelegate.isKlmActivated();
        boolean isEdmServiceReady = iStateDelegate.isEdmServiceReady();
        String str = TAG;
        Log.d(str, "klm activated : " + isKlmActivated + ", edm service ready : " + isEdmServiceReady);
        StringBuilder sb = new StringBuilder();
        sb.append("keep alive ");
        sb.append(isKlmActivated);
        Log.i(str, sb.toString());
        return isKlmActivated;
    }
}
