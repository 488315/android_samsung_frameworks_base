package com.android.server.audio;

import android.os.IBinder;
import android.os.IInterface;
import android.os.IServiceCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ServiceHolder implements IBinder.DeathRecipient {
    public final Function mCastFunction;
    public final Executor mExecutor;
    public final Set mOnDeathTasks;
    public final Set mOnStartTasks;
    public final AtomicReference mService;
    public final AnonymousClass1 mServiceListener;
    public final String mServiceName;
    public final AnonymousClass2 mServiceProvider;
    public final String mTag;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.audio.ServiceHolder$2, reason: invalid class name */
    public final class AnonymousClass2 {}

    public ServiceHolder(
            DefaultAudioPolicyFacade$$ExternalSyntheticLambda0
                    defaultAudioPolicyFacade$$ExternalSyntheticLambda0,
            Executor executor) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mService = new AtomicReference();
        this.mOnStartTasks = ConcurrentHashMap.newKeySet();
        this.mOnDeathTasks = ConcurrentHashMap.newKeySet();
        IServiceCallback.Stub stub =
                new IServiceCallback
                        .Stub() { // from class: com.android.server.audio.ServiceHolder.1
                    public final void onRegistration(String str, IBinder iBinder) {
                        ServiceHolder.this.onServiceInited(iBinder);
                    }
                };
        this.mServiceName = "media.audio_policy";
        this.mCastFunction = defaultAudioPolicyFacade$$ExternalSyntheticLambda0;
        Objects.requireNonNull(executor);
        this.mExecutor = executor;
        this.mServiceProvider = anonymousClass2;
        this.mTag = "ServiceHolder: ".concat("media.audio_policy");
        try {
            ServiceManager.registerForNotifications("media.audio_policy", stub);
        } catch (RemoteException e) {
            throw new IllegalStateException("ServiceManager died!!", e);
        }
    }

    public final void attemptClear(IBinder iBinder) {
        IInterface iInterface = (IInterface) this.mService.get();
        if (iInterface != null
                && Objects.equals(iInterface.asBinder(), iBinder)
                && this.mService.compareAndSet(iInterface, null)) {
            iBinder.unlinkToDeath(this, 0);
            Iterator it = this.mOnDeathTasks.iterator();
            while (it.hasNext()) {
                this.mExecutor.execute(
                        new ServiceHolder$$ExternalSyntheticLambda0(
                                (Consumer) it.next(), iInterface, 0));
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        throw new AssertionError("Wrong binderDied called, this should never happen");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        attemptClear(iBinder);
    }

    public final IInterface onServiceInited(IBinder iBinder) {
        IInterface iInterface = (IInterface) this.mCastFunction.apply(iBinder);
        Objects.requireNonNull(iInterface);
        if (!this.mService.compareAndSet(null, iInterface)) {
            return iInterface;
        }
        Iterator it = this.mOnStartTasks.iterator();
        while (it.hasNext()) {
            this.mExecutor.execute(
                    new ServiceHolder$$ExternalSyntheticLambda0(
                            (Consumer) it.next(), iInterface, 2));
        }
        try {
            iBinder.linkToDeath(this, 0);
        } catch (RemoteException unused) {
            Log.e(this.mTag, "Immediate service death. Service crash-looping");
            attemptClear(iBinder);
        }
        return iInterface;
    }
}
