package com.android.server;

import android.os.SystemProperties;

class ResetReasonNP extends ResetReasonCode {
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
