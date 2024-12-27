package com.android.server;

class ResetReasonEX extends CommonPlatformResetReasonCode {
    @Override // com.android.server.ResetReasonCode
    public String addSuffix() {
        return "sys_error";
    }
}
