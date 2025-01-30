package com.samsung.android.sepunion;

import android.p009os.Bundle;
import android.p009os.IBinder;
import com.samsung.android.cover.CoverState;

/* loaded from: classes5.dex */
public abstract class SemUnionManagerLocal {
    public abstract void accessoryStateChanged(boolean z, byte[] bArr, byte[] bArr2);

    public abstract void createSemSystemService(String str);

    public abstract IBinder getSemSystemService(String str, Bundle bundle);

    public abstract void notifyCoverSwitchStateChanged(long j, boolean z);

    public abstract void notifySmartCoverAttachStateChanged(long j, boolean z, CoverState coverState);

    public abstract void screenTurnedOff();
}
