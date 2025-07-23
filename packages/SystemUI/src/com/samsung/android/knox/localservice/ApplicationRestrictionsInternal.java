package com.samsung.android.knox.localservice;

import android.os.Bundle;
import com.samsung.android.knox.appconfig.IApplicationRestrictionsResultCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ApplicationRestrictionsInternal {
    public abstract Bundle getApplicationRestrictionsInternal(String str, int i);

    public abstract void sendBroadcastAsUserInternal(String str, int i);

    public abstract void setApplicationRestrictionsInternal(String str, Bundle bundle, int i, boolean z, IApplicationRestrictionsResultCallback iApplicationRestrictionsResultCallback);

    public abstract void setKeyedAppStatesReport(String str, Bundle bundle, int i);
}
