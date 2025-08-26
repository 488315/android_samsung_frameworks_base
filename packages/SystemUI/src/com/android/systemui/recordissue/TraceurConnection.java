package com.android.systemui.recordissue;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.settings.UserContextProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TraceurConnection extends UserAwareConnection {
    public final Looper bgLooper;
    public final List onBound;

    public final class Provider {
        public final Looper bgLooper;
        public final UserContextProvider userContextProvider;

        public Provider(UserContextProvider userContextProvider, Looper looper) {
            this.userContextProvider = userContextProvider;
            this.bgLooper = looper;
        }
    }

    public /* synthetic */ TraceurConnection(UserContextProvider userContextProvider, Looper looper, DefaultConstructorMarker defaultConstructorMarker) {
        this(userContextProvider, looper);
    }

    public static Object sendMessage$default(final TraceurConnection traceurConnection, int i, Bundle bundle, Messenger messenger, int i2) throws RemoteException {
        if ((i2 & 2) != 0) {
            bundle = new Bundle();
        }
        if ((i2 & 4) != 0) {
            messenger = null;
        }
        traceurConnection.getClass();
        try {
            final Message messageObtain = Message.obtain();
            messageObtain.what = i;
            messageObtain.setData(bundle);
            messageObtain.replyTo = messenger;
            Messenger messenger2 = traceurConnection.binder;
            if (messenger2 != null) {
                messenger2.send(messageObtain);
                return Unit.INSTANCE;
            }
            return Boolean.valueOf(((CopyOnWriteArrayList) traceurConnection.onBound).add(new Runnable() { // from class: com.android.systemui.recordissue.TraceurConnection$sendMessage$1
                @Override // java.lang.Runnable
                public final void run() throws RemoteException {
                    Messenger messenger3 = this.this$0.binder;
                    messenger3.getClass();
                    messenger3.send(messageObtain);
                }
            }));
        } catch (Exception e) {
            return Integer.valueOf(Log.e("TraceurConnection", "failed to notify Traceur", e));
        }
    }

    @Override // com.android.systemui.recordissue.UserAwareConnection, android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        super.onServiceConnected(componentName, iBinder);
        Iterator it = this.onBound.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        ((CopyOnWriteArrayList) this.onBound).clear();
    }

    private TraceurConnection(UserContextProvider userContextProvider, Looper looper) {
        super(userContextProvider, new Intent().setClassName("com.android.traceur", "com.android.traceur.BindableTraceService"));
        this.bgLooper = looper;
        this.onBound = new CopyOnWriteArrayList(new ArrayList());
    }
}
