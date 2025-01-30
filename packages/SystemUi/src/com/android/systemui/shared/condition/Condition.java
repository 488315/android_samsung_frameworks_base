package com.android.systemui.shared.condition;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.shared.condition.Monitor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class Condition {
    public final ArrayList mCallbacks;
    public Boolean mIsConditionMet;
    public final boolean mOverriding;
    public boolean mStarted;
    public final String mTag;

    public Condition(CoroutineScope coroutineScope) {
        this(coroutineScope, Boolean.FALSE, false);
    }

    public abstract void start();

    public abstract void stop();

    public final void updateCondition(boolean z) {
        Boolean bool = this.mIsConditionMet;
        if (bool == null || bool.booleanValue() != z) {
            String str = this.mTag;
            if (Log.isLoggable(str, 3)) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("updating condition to ", z, str);
            }
            this.mIsConditionMet = Boolean.valueOf(z);
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                Monitor.C24781 c24781 = (Monitor.C24781) ((WeakReference) it.next()).get();
                if (c24781 == null) {
                    it.remove();
                } else {
                    Monitor.this.mExecutor.execute(new Monitor$$ExternalSyntheticLambda2(1, c24781, this));
                }
            }
        }
    }

    public Condition(CoroutineScope coroutineScope, Boolean bool, boolean z) {
        this.mTag = getClass().getSimpleName();
        this.mCallbacks = new ArrayList();
        this.mStarted = false;
        this.mIsConditionMet = bool;
        this.mOverriding = z;
    }
}
