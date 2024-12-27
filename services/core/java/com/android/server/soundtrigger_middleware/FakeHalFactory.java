package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FakeHalFactory implements HalFactory {
    public final ISoundTriggerInjection mInjection;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.soundtrigger_middleware.FakeHalFactory$1, reason: invalid class name */
    public final class AnonymousClass1 extends SoundTriggerHw3Compat {
        public final /* synthetic */ IInjectGlobalEvent val$session;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(
                FakeSoundTriggerHal fakeSoundTriggerHal,
                FakeHalFactory$$ExternalSyntheticLambda0 fakeHalFactory$$ExternalSyntheticLambda0,
                FakeSoundTriggerHal.AnonymousClass1 anonymousClass1) {
            super(fakeSoundTriggerHal, fakeHalFactory$$ExternalSyntheticLambda0);
            this.val$session = anonymousClass1;
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat,
                  // com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public final void clientAttached(final IBinder iBinder) {
            Executor executor = FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR;
            final FakeSoundTriggerHal.AnonymousClass1 anonymousClass1 = this.val$session;
            executor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FakeHalFactory.AnonymousClass1 anonymousClass12 =
                                    FakeHalFactory.AnonymousClass1.this;
                            IBinder iBinder2 = iBinder;
                            IInjectGlobalEvent iInjectGlobalEvent = anonymousClass1;
                            anonymousClass12.getClass();
                            try {
                                FakeHalFactory.this.mInjection.onClientAttached(
                                        iBinder2, iInjectGlobalEvent);
                            } catch (RemoteException unused) {
                                Slog.wtf(
                                        "FakeHalFactory",
                                        "Unexpected RemoteException from same process");
                            }
                        }
                    });
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat,
                  // com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public final void clientDetached(IBinder iBinder) {
            FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR.execute(
                    new FakeHalFactory$1$$ExternalSyntheticLambda0(this, iBinder));
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat,
                  // com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public final void detach() {
            FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR.execute(
                    new FakeHalFactory$1$$ExternalSyntheticLambda0(this, this.val$session));
        }
    }

    public FakeHalFactory(SoundTriggerInjection soundTriggerInjection) {
        this.mInjection = soundTriggerInjection;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.soundtrigger_middleware.FakeHalFactory$$ExternalSyntheticLambda0] */
    @Override // com.android.server.soundtrigger_middleware.HalFactory
    public final ISoundTriggerHal create() {
        FakeSoundTriggerHal fakeSoundTriggerHal =
                new FakeSoundTriggerHal((SoundTriggerInjection) this.mInjection);
        final FakeSoundTriggerHal.AnonymousClass1 anonymousClass1 =
                fakeSoundTriggerHal.mGlobalEventSession;
        return new AnonymousClass1(
                fakeSoundTriggerHal,
                new Runnable() { // from class:
                                 // com.android.server.soundtrigger_middleware.FakeHalFactory$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            anonymousClass1.triggerRestart();
                        } catch (RemoteException unused) {
                            Slog.wtf(
                                    "FakeHalFactory",
                                    "Unexpected RemoteException from same process");
                        }
                    }
                },
                anonymousClass1);
    }
}
