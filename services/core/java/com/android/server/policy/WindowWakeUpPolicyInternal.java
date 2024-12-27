package com.android.server.policy;

public interface WindowWakeUpPolicyInternal {

    public interface InputWakeUpDelegate {
        boolean wakeUpFromKey(long j, int i, boolean z);

        boolean wakeUpFromMotion(long j, int i, boolean z);
    }

    void setInputWakeUpDelegate(InputWakeUpDelegate inputWakeUpDelegate);
}
