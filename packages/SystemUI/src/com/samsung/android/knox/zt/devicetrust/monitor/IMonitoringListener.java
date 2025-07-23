package com.samsung.android.knox.zt.devicetrust.monitor;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IMonitoringListener {
    int checkUrlReputation(String str);

    void onEvent(int i, Bundle bundle);

    void onEventGeneralized(int i, String str);

    void onEventSimplified(int i, String str);

    int onSignal(String str);

    void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2);
}
