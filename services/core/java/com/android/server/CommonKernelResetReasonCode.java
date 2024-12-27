package com.android.server;

public abstract class CommonKernelResetReasonCode extends ResetReasonCode {
    @Override // com.android.server.ResetReasonCode
    public String addSuffix() {
        return "lastkmsg";
    }
}
