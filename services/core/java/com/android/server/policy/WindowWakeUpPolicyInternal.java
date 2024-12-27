package com.android.server.policy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface WindowWakeUpPolicyInternal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface InputWakeUpDelegate {
        boolean wakeUpFromKey(long j, int i, boolean z);

        boolean wakeUpFromMotion(long j, int i, boolean z);
    }

    void setInputWakeUpDelegate(InputWakeUpDelegate inputWakeUpDelegate);
}
