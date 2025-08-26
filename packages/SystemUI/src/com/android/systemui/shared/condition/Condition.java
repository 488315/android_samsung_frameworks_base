package com.android.systemui.shared.condition;

import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public abstract class Condition {
    public Boolean _isConditionMet;
    public final CoroutineScope _scope;
    public final List callbacks;
    public StandaloneCoroutine currentJob;
    public final boolean isOverridingCondition;
    public final String mTag;
    public boolean started;

    public interface Callback {
        void onConditionChanged(Condition condition);
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.shared.condition.Condition$addCallback$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Condition.this.new AnonymousClass1(continuation);
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
                Condition condition = Condition.this;
                this.label = 1;
                if (condition.start(this) == coroutineSingletons) {
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

    public Condition(CoroutineScope coroutineScope) {
        this(coroutineScope, null, false, 6, null);
    }

    public final void addCallback(Callback callback) {
        String str = this.mTag;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "adding callback");
        }
        ((ArrayList) this.callbacks).add(new WeakReference(callback));
        if (this.started) {
            callback.onConditionChanged(this);
            return;
        }
        this.currentJob = BuildersKt.launch$default(this._scope, null, null, new AnonymousClass1(null), 3);
        this.started = true;
    }

    public abstract int getStartStrategy();

    public final void removeCallback(Callback callback) {
        String str = this.mTag;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "removing callback");
        }
        Iterator it = ((ArrayList) this.callbacks).iterator();
        while (it.hasNext()) {
            Callback callback2 = (Callback) ((WeakReference) it.next()).get();
            if (callback2 == null || callback2 == callback) {
                it.remove();
            }
        }
        if (((ArrayList) this.callbacks).isEmpty() && this.started) {
            stop();
            StandaloneCoroutine standaloneCoroutine = this.currentJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            this.currentJob = null;
            this.started = false;
        }
    }

    public final void sendUpdate() {
        Iterator it = ((ArrayList) this.callbacks).iterator();
        while (it.hasNext()) {
            Callback callback = (Callback) ((WeakReference) it.next()).get();
            if (callback == null) {
                it.remove();
            } else {
                callback.onConditionChanged(this);
            }
        }
    }

    public abstract Object start(Continuation continuation);

    public abstract void stop();

    public final void updateCondition(boolean z) {
        Boolean bool = this._isConditionMet;
        if (bool == null || !bool.equals(Boolean.valueOf(z))) {
            String str = this.mTag;
            if (Log.isLoggable(str, 3)) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("updating condition to ", str, z);
            }
            this._isConditionMet = Boolean.valueOf(z);
            sendUpdate();
        }
    }

    public Condition(CoroutineScope coroutineScope, Boolean bool) {
        this(coroutineScope, bool, false, 4, null);
    }

    public Condition(CoroutineScope coroutineScope, Boolean bool, boolean z) {
        this._scope = coroutineScope;
        this._isConditionMet = bool;
        this.isOverridingCondition = z;
        this.mTag = getClass().getSimpleName();
        this.callbacks = new ArrayList();
    }

    public /* synthetic */ Condition(CoroutineScope coroutineScope, Boolean bool, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, (i & 2) != 0 ? Boolean.FALSE : bool, (i & 4) != 0 ? false : z);
    }
}
