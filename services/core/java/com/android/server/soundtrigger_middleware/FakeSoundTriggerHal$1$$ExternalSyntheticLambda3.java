package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.IBinder;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.soundtrigger_middleware.FakeSoundTriggerHal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class FakeSoundTriggerHal$1$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FakeSoundTriggerHal$1$$ExternalSyntheticLambda3(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FakeSoundTriggerHal.AnonymousClass1 anonymousClass1 = (FakeSoundTriggerHal.AnonymousClass1) this.f$0;
                IBinder.DeathRecipient deathRecipient = (IBinder.DeathRecipient) this.f$1;
                anonymousClass1.getClass();
                try {
                    deathRecipient.binderDied(FakeSoundTriggerHal.this.asBinder());
                    break;
                } catch (Throwable th) {
                    Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th);
                    return;
                }
            case 1:
                FakeSoundTriggerHal.CallbackDispatcher callbackDispatcher = (FakeSoundTriggerHal.CallbackDispatcher) this.f$0;
                FunctionalUtils.ThrowingConsumer throwingConsumer = (FunctionalUtils.ThrowingConsumer) this.f$1;
                callbackDispatcher.getClass();
                try {
                    throwingConsumer.accept((ISoundTriggerHwCallback) callbackDispatcher.mCallback);
                    break;
                } catch (Throwable th2) {
                    Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th2);
                    return;
                }
            case 2:
                FakeSoundTriggerHal.CallbackDispatcher callbackDispatcher2 = (FakeSoundTriggerHal.CallbackDispatcher) this.f$0;
                FunctionalUtils.ThrowingConsumer throwingConsumer2 = (FunctionalUtils.ThrowingConsumer) this.f$1;
                callbackDispatcher2.getClass();
                try {
                    throwingConsumer2.accept((ISoundTriggerHwGlobalCallback) callbackDispatcher2.mCallback);
                    break;
                } catch (Throwable th3) {
                    Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th3);
                    return;
                }
            default:
                FakeSoundTriggerHal.CallbackDispatcher callbackDispatcher3 = (FakeSoundTriggerHal.CallbackDispatcher) this.f$0;
                FunctionalUtils.ThrowingConsumer throwingConsumer3 = (FunctionalUtils.ThrowingConsumer) this.f$1;
                callbackDispatcher3.getClass();
                try {
                    throwingConsumer3.accept((ISoundTriggerInjection) callbackDispatcher3.mCallback);
                    break;
                } catch (Throwable th4) {
                    Slog.wtf("FakeSoundTriggerHal", "Callback dispatch threw", th4);
                }
        }
    }
}
