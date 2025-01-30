package com.android.server;

import java.util.regex.Pattern;

/* compiled from: ResetReasonCode.java */
/* loaded from: classes.dex */
class ResetReasonNT extends CommonNativeResetReasonCode {
    @Override // com.android.server.ResetReasonCode
    public String addCauseContents() {
        return "system";
    }

    @Override // com.android.server.ResetReasonCode
    public Pattern getPatternByReason() {
        Pattern compile = Pattern.compile("pid: (\\d+).*system_server.*|pid: (\\d+).*netd.*|pid: (\\d+).*zygote.*|pid: (\\d+).*vold.*");
        this.pattern = compile;
        return compile;
    }
}
