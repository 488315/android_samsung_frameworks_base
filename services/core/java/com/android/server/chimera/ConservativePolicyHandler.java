package com.android.server.chimera;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ConservativePolicyHandler extends PolicyHandler {
    @Override // com.android.server.chimera.PolicyHandler
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (strArr.length < 1 || !"-a".equals(strArr[0])) {
            return;
        }
        dumpQuotaPPN(printWriter);
    }

    @Override // com.android.server.chimera.PolicyHandler
    public final int executePolicy(ChimeraCommonUtil.TriggerSource triggerSource, int i) {
        return 0;
    }
}
