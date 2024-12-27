package com.android.server;

import android.os.SystemProperties;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class ResetReasonRP extends ResetReasonCode {
    private String[] boot_reason = SystemProperties.get("sys.boot.reason", "unknown").split(",");

    @Override // com.android.server.ResetReasonCode
    public String addCauseContents() {
        return this.boot_reason[0];
    }

    @Override // com.android.server.ResetReasonCode
    public String addStackContents() {
        String[] strArr = this.boot_reason;
        return strArr.length > 1 ? strArr[1] : "";
    }
}
