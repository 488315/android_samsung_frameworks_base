package android.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.UserHandle;
import java.util.concurrent.Executor;

/* compiled from: ContextImpl.java */
/* loaded from: classes.dex */
class ReceiverRestrictedContext extends ContextWrapper {
    ReceiverRestrictedContext(Context context) {
        super(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        if (broadcastReceiver == null) {
            return super.registerReceiver(null, intentFilter, str, handler);
        }
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to register to receive intents");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Intent registerReceiverForAllUsers(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, str, handler);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        if (broadcastReceiver == null) {
            return super.registerReceiverAsUser(null, userHandle, intentFilter, str, handler);
        }
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to register to receive intents");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindService(Intent intent, int i, Executor executor, ServiceConnection serviceConnection) {
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindIsolatedService(Intent intent, int i, String str, Executor executor, ServiceConnection serviceConnection) {
        throw new ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }
}
