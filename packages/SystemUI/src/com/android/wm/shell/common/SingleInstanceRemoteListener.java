package com.android.wm.shell.common;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Slog;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SingleInstanceRemoteListener {
    public final RemoteCallable mCallableController;
    public IInterface mListener;
    public final AnonymousClass1 mListenerDeathRecipient = new AnonymousClass1();
    public final Consumer mOnRegisterCallback;
    public final Consumer mOnUnregisterCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.common.SingleInstanceRemoteListener$1, reason: invalid class name */
    public class AnonymousClass1 implements IBinder.DeathRecipient {
        public AnonymousClass1() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            final RemoteCallable remoteCallable = SingleInstanceRemoteListener.this.mCallableController;
            remoteCallable.getRemoteCallExecutor().execute(new Runnable() { // from class: com.android.wm.shell.common.SingleInstanceRemoteListener$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SingleInstanceRemoteListener.AnonymousClass1 anonymousClass1 = SingleInstanceRemoteListener.AnonymousClass1.this;
                    RemoteCallable remoteCallable2 = remoteCallable;
                    SingleInstanceRemoteListener singleInstanceRemoteListener = SingleInstanceRemoteListener.this;
                    singleInstanceRemoteListener.mListener = null;
                    singleInstanceRemoteListener.mOnUnregisterCallback.accept(remoteCallable2);
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface RemoteCall {
        void accept(Object obj);
    }

    public SingleInstanceRemoteListener(RemoteCallable remoteCallable, Consumer<RemoteCallable> consumer, Consumer<RemoteCallable> consumer2) {
        this.mCallableController = remoteCallable;
        this.mOnRegisterCallback = consumer;
        this.mOnUnregisterCallback = consumer2;
    }

    public final void call(RemoteCall remoteCall) {
        IInterface iInterface = this.mListener;
        if (iInterface == null) {
            Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
            return;
        }
        try {
            remoteCall.accept(iInterface);
        } catch (RemoteException e) {
            Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
        }
    }

    public final void register(IInterface iInterface) {
        IInterface iInterface2 = this.mListener;
        AnonymousClass1 anonymousClass1 = this.mListenerDeathRecipient;
        if (iInterface2 != null) {
            iInterface2.asBinder().unlinkToDeath(anonymousClass1, 0);
        }
        if (iInterface != null) {
            try {
                iInterface.asBinder().linkToDeath(anonymousClass1, 0);
            } catch (RemoteException unused) {
                Slog.e("SingleInstanceRemoteListener", "Failed to link to death");
                return;
            }
        }
        this.mListener = iInterface;
        this.mOnRegisterCallback.accept(this.mCallableController);
    }

    public final void unregister() {
        IInterface iInterface = this.mListener;
        if (iInterface != null) {
            iInterface.asBinder().unlinkToDeath(this.mListenerDeathRecipient, 0);
        }
        this.mListener = null;
        this.mOnUnregisterCallback.accept(this.mCallableController);
    }
}
