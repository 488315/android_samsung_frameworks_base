package com.android.systemui.keyguard.domain.interactor;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.KeyguardServiceShowLockscreenRepository;
import com.android.systemui.keyguard.data.repository.ShowLockscreenCallback;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardServiceShowLockscreenInteractor implements CoreStartable {
    public final CoroutineScope backgroundScope;
    public final KeyguardEnabledInteractor keyguardEnabledInteractor;
    public final KeyguardServiceShowLockscreenRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final SharedFlowImpl showNowEvents = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
    public final UserTracker userTracker;
    public final Lazy wmLockscreenVisibilityInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardServiceShowLockscreenInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardServiceShowLockscreenInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = ((WindowManagerLockscreenVisibilityInteractor) KeyguardServiceShowLockscreenInteractor.this.wmLockscreenVisibilityInteractor.get()).lockscreenVisibility;
                final KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor = KeyguardServiceShowLockscreenInteractor.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardServiceShowLockscreenInteractor.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Boolean) obj2).booleanValue()) {
                            KeyguardServiceShowLockscreenInteractor.access$notifyShowLockscreenCallbacks(keyguardServiceShowLockscreenInteractor);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardServiceShowLockscreenInteractor(CoroutineScope coroutineScope, SelectedUserInteractor selectedUserInteractor, KeyguardServiceShowLockscreenRepository keyguardServiceShowLockscreenRepository, UserTracker userTracker, Lazy lazy, KeyguardEnabledInteractor keyguardEnabledInteractor) {
        this.backgroundScope = coroutineScope;
        this.selectedUserInteractor = selectedUserInteractor;
        this.repository = keyguardServiceShowLockscreenRepository;
        this.userTracker = userTracker;
        this.wmLockscreenVisibilityInteractor = lazy;
        this.keyguardEnabledInteractor = keyguardEnabledInteractor;
    }

    public static final void access$notifyShowLockscreenCallbacks(KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor) {
        ArrayList arrayList;
        synchronized (keyguardServiceShowLockscreenInteractor.repository.showLockscreenCallbacks) {
            arrayList = new ArrayList(keyguardServiceShowLockscreenInteractor.repository.showLockscreenCallbacks);
            keyguardServiceShowLockscreenInteractor.repository.showLockscreenCallbacks.clear();
            Unit unit = Unit.INSTANCE;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ShowLockscreenCallback showLockscreenCallback = (ShowLockscreenCallback) obj;
            if (showLockscreenCallback.userId != keyguardServiceShowLockscreenInteractor.selectedUserInteractor.getSelectedUserId()) {
                Log.i("ShowLockscreenInteractor", "Not notifying lockNowCallback due to user mismatch");
                return;
            }
            Log.i("ShowLockscreenInteractor", "Notifying lockNowCallback");
            try {
                showLockscreenCallback.remoteCallback.sendResult((Bundle) null);
            } catch (RemoteException e) {
                Log.e("ShowLockscreenInteractor", "Could not issue LockNowCallback sendResult", e);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.backgroundScope, null, null, new AnonymousClass1(null), 3);
    }
}
