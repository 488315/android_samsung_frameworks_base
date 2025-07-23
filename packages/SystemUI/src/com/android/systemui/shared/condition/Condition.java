package com.android.systemui.shared.condition;

import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class Condition {
    public Boolean _isConditionMet;
    public final CoroutineScope _scope;
    public final List callbacks;
    public StandaloneCoroutine currentJob;
    public final boolean isOverridingCondition;
    public final String mTag;
    public boolean started;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onConditionChanged(Condition condition);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        this.currentJob = BuildersKt.launch$default(this._scope, null, null, new Condition$addCallback$1(this, null), 3);
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
