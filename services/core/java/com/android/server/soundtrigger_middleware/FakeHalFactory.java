package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.soundtrigger_middleware.FakeHalFactory;
import com.android.server.soundtrigger_middleware.FakeSoundTriggerHal;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class FakeHalFactory implements HalFactory {
    public final ISoundTriggerInjection mInjection;

    public FakeHalFactory(ISoundTriggerInjection iSoundTriggerInjection) {
        this.mInjection = iSoundTriggerInjection;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal] */
    @Override // com.android.server.soundtrigger_middleware.HalFactory
    public ISoundTriggerHal create() {
        ?? fakeSoundTriggerHal = new FakeSoundTriggerHal(this.mInjection);
        final IInjectGlobalEvent globalEventInjection = fakeSoundTriggerHal.getGlobalEventInjection();
        return new C26131(fakeSoundTriggerHal, new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FakeHalFactory.lambda$create$0(globalEventInjection);
            }
        }, globalEventInjection);
    }

    public static /* synthetic */ void lambda$create$0(IInjectGlobalEvent iInjectGlobalEvent) {
        try {
            iInjectGlobalEvent.triggerRestart();
        } catch (RemoteException unused) {
            Slog.wtf("FakeHalFactory", "Unexpected RemoteException from same process");
        }
    }

    /* renamed from: com.android.server.soundtrigger_middleware.FakeHalFactory$1 */
    public class C26131 extends SoundTriggerHw3Compat {
        public final /* synthetic */ IInjectGlobalEvent val$session;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C26131(IBinder iBinder, Runnable runnable, IInjectGlobalEvent iInjectGlobalEvent) {
            super(iBinder, runnable);
            this.val$session = iInjectGlobalEvent;
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat, com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public void detach() {
            Executor executor = FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR;
            final IInjectGlobalEvent iInjectGlobalEvent = this.val$session;
            executor.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FakeHalFactory.C26131.this.lambda$detach$0(iInjectGlobalEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$detach$0(IInjectGlobalEvent iInjectGlobalEvent) {
            try {
                FakeHalFactory.this.mInjection.onFrameworkDetached(iInjectGlobalEvent);
            } catch (RemoteException unused) {
                Slog.wtf("FakeHalFactory", "Unexpected RemoteException from same process");
            }
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat, com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public void clientAttached(final IBinder iBinder) {
            Executor executor = FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR;
            final IInjectGlobalEvent iInjectGlobalEvent = this.val$session;
            executor.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FakeHalFactory.C26131.this.lambda$clientAttached$1(iBinder, iInjectGlobalEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clientAttached$1(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) {
            try {
                FakeHalFactory.this.mInjection.onClientAttached(iBinder, iInjectGlobalEvent);
            } catch (RemoteException unused) {
                Slog.wtf("FakeHalFactory", "Unexpected RemoteException from same process");
            }
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat, com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public void clientDetached(final IBinder iBinder) {
            FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR.execute(new Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FakeHalFactory.C26131.this.lambda$clientDetached$2(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clientDetached$2(IBinder iBinder) {
            try {
                FakeHalFactory.this.mInjection.onClientDetached(iBinder);
            } catch (RemoteException unused) {
                Slog.wtf("FakeHalFactory", "Unexpected RemoteException from same process");
            }
        }
    }
}
