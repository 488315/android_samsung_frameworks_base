package com.android.server.remoteappmode;

import android.content.Intent;
import com.android.server.wm.RemoteAppController;
import com.android.server.uri.NeededUriGrants;

/* loaded from: classes3.dex */
public class InterceptedActivityInfo {
    public RemoteAppController.CallerInfo callerInfo;
    public Intent intent;
    public NeededUriGrants intentGrants;
    public int userId;

    public InterceptedActivityInfo(Intent intent, RemoteAppController.CallerInfo callerInfo, NeededUriGrants neededUriGrants, int i) {
        this.intent = intent;
        this.callerInfo = callerInfo;
        this.intentGrants = neededUriGrants;
        this.userId = i;
    }

    public RemoteAppController.CallerInfo getCallerInfo() {
        return this.callerInfo;
    }

    public int getUserId() {
        return this.userId;
    }
}
