package com.android.server;

import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class ResetReasonSF extends CommonNativeResetReasonCode {
    @Override // com.android.server.ResetReasonCode
    public String addCauseContents() {
        return "graphics";
    }

    @Override // com.android.server.ResetReasonCode
    public Pattern getPatternByReason() {
        Pattern compile = Pattern.compile("pid: (\\d+).*surfaceflinger.*|pid: (\\d+).*android\\.hardware\\.graphics.*|pid: (\\d+).*vendor\\.qti\\.hardware\\.display.*");
        this.pattern = compile;
        return compile;
    }
}
