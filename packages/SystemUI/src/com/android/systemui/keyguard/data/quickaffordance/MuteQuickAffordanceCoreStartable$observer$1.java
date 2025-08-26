package com.android.systemui.keyguard.data.quickaffordance;

import androidx.lifecycle.Observer;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class MuteQuickAffordanceCoreStartable$observer$1 implements Observer, FunctionAdapter {
    public final /* synthetic */ MuteQuickAffordanceCoreStartable $tmp0;

    public MuteQuickAffordanceCoreStartable$observer$1(MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable) {
        this.$tmp0 = muteQuickAffordanceCoreStartable;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof Observer) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, MuteQuickAffordanceCoreStartable.class, "updateLastNonSilentRingerMode", "updateLastNonSilentRingerMode(I)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        int iIntValue = ((Number) obj).intValue();
        int i = MuteQuickAffordanceCoreStartable.$r8$clinit;
        MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable = this.$tmp0;
        muteQuickAffordanceCoreStartable.getClass();
        CoroutineTracingKt.launchTraced$default(muteQuickAffordanceCoreStartable.coroutineScope, muteQuickAffordanceCoreStartable.backgroundDispatcher, null, new MuteQuickAffordanceCoreStartable$updateLastNonSilentRingerMode$1(iIntValue, muteQuickAffordanceCoreStartable, null), 5);
    }
}
