package com.samsung.android.mcfds.lib;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.UserHandle;
import com.samsung.android.mcfds.lib.sem.SemWrapper;

/* loaded from: classes2.dex */
public class DeviceSyncManager extends AbstractDeviceSyncManager {
    public DeviceSyncManager(Context context) {
        super(context);
    }

    @Override // com.samsung.android.mcfds.lib.AbstractDeviceSyncManager
    public boolean bindService(UserHandle userHandle, Intent intent, ServiceConnection serviceConnection) {
        return SemWrapper.semBindServiceAsUser(this.mContext, intent, serviceConnection, 1, userHandle);
    }
}
