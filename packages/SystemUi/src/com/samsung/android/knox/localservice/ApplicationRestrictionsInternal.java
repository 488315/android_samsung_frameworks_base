package com.samsung.android.knox.localservice;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ApplicationRestrictionsInternal {
    public abstract Bundle getApplicationRestrictionsInternal(String str, int i);

    public abstract void sendBroadcastAsUserInternal(String str, int i);

    public abstract void setApplicationRestrictionsInternal(String str, Bundle bundle, int i, boolean z);

    public abstract void setKeyedAppStatesReport(String str, Bundle bundle, int i);
}
