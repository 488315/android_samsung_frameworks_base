package com.android.systemui.shared.condition;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.shared.condition.Monitor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updating condition to ", str, z);
            }
            this.mIsConditionMet = Boolean.valueOf(z);
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                Monitor.AnonymousClass1 anonymousClass1 = (Monitor.AnonymousClass1) ((WeakReference) it.next()).get();
                if (anonymousClass1 == null) {
                    it.remove();
                } else {
                    Monitor.this.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(1, anonymousClass1, this));
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
