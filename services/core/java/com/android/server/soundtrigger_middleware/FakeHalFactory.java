package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import java.util.concurrent.Executor;

public final class FakeHalFactory implements HalFactory {
    public final ISoundTriggerInjection mInjection;

    /* renamed from: com.android.server.soundtrigger_middleware.FakeHalFactory$1, reason: invalid class name */
    public final class AnonymousClass1 extends SoundTriggerHw3Compat {
        public final /* synthetic */ IInjectGlobalEvent val$session;

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
