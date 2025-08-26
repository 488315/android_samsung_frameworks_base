package com.samsung.android.knox.localservice;

import android.os.Bundle;
import com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback;

/* loaded from: classes4.dex */
public abstract class ApplicationRestrictionsInternal {
    public abstract Bundle getApplicationRestrictionsInternal(String str, int i);

    public abstract void sendBroadcastAsUserInternal(String str, int i);

    public abstract void setApplicationRestrictionsInternal(String str, Bundle bundle, int i, boolean z, IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallback);

    public abstract void setKeyedAppStatesReport(String str, Bundle bundle, int i);
}
