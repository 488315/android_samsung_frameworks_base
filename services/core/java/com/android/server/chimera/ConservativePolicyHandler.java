package com.android.server.chimera;

import java.io.PrintWriter;

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
