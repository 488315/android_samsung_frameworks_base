package com.android.systemui.recordissue;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class UserAwareConnection implements ServiceConnection {
    public Messenger binder;
    public final Intent intent;
    public boolean shouldUnBind;
    public final UserContextProvider userContextProvider;

    public UserAwareConnection(UserContextProvider userContextProvider, Intent intent) {
        this.userContextProvider = userContextProvider;
        this.intent = intent;
    }

    public final void doBind() {
        if (this.shouldUnBind) {
            return;
        }
        try {
            this.shouldUnBind = ((UserTrackerImpl) this.userContextProvider).getUserContext().bindService(this.intent, this, 33554465);
        } catch (Exception e) {
            Log.e("UserAwareConnection", "failed to bind to the service", e);
        }
    }

    public final void doUnBind() {
        if (this.shouldUnBind) {
            try {
                ((UserTrackerImpl) this.userContextProvider).getUserContext().unbindService(this);
            } catch (IllegalArgumentException e) {
                Log.e("UserAwareConnection", "Can't disconnect because service wasn't connected anyways.", e);
            }
            this.shouldUnBind = false;
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = new Messenger(iBinder);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.binder = null;
    }

    public static /* synthetic */ void getBinder$annotations() {
    }
}
