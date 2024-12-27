package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.soundtrigger_middleware.FakeHalFactory;
import com.android.server.soundtrigger_middleware.FakeSoundTriggerHal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class FakeHalFactory$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ FakeHalFactory.AnonymousClass1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FakeHalFactory$1$$ExternalSyntheticLambda0(FakeHalFactory.AnonymousClass1 anonymousClass1, IBinder iBinder) {
        this.f$0 = anonymousClass1;
        this.f$1 = iBinder;
    }

    public /* synthetic */ FakeHalFactory$1$$ExternalSyntheticLambda0(FakeHalFactory.AnonymousClass1 anonymousClass1, FakeSoundTriggerHal.AnonymousClass1 anonymousClass12) {
        this.f$0 = anonymousClass1;
        this.f$1 = anonymousClass12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FakeHalFactory.AnonymousClass1 anonymousClass1 = this.f$0;
                IInjectGlobalEvent iInjectGlobalEvent = (IInjectGlobalEvent) this.f$1;
                anonymousClass1.getClass();
                try {
                    anonymousClass1.this$0.mInjection.onFrameworkDetached(iInjectGlobalEvent);
                    break;
                } catch (RemoteException unused) {
                    Slog.wtf("FakeHalFactory", "Unexpected RemoteException from same process");
                    return;
                }
            default:
                FakeHalFactory.AnonymousClass1 anonymousClass12 = this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                anonymousClass12.getClass();
                try {
                    anonymousClass12.this$0.mInjection.onClientDetached(iBinder);
                    break;
                } catch (RemoteException unused2) {
                    Slog.wtf("FakeHalFactory", "Unexpected RemoteException from same process");
                }
        }
    }
}
