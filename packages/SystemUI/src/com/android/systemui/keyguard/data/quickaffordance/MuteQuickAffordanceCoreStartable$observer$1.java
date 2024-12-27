package com.android.systemui.keyguard.data.quickaffordance;

import androidx.lifecycle.Observer;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MuteQuickAffordanceCoreStartable$observer$1 implements Observer, FunctionAdapter {
    public final /* synthetic */ MuteQuickAffordanceCoreStartable $tmp0;

    public MuteQuickAffordanceCoreStartable$observer$1(MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable) {
        this.$tmp0 = muteQuickAffordanceCoreStartable;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof Observer) && (obj instanceof FunctionAdapter)) {
            return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
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
        int intValue = ((Number) obj).intValue();
        MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable = this.$tmp0;
        muteQuickAffordanceCoreStartable.getClass();
        BuildersKt.launch$default(muteQuickAffordanceCoreStartable.coroutineScope, muteQuickAffordanceCoreStartable.backgroundDispatcher, null, new MuteQuickAffordanceCoreStartable$updateLastNonSilentRingerMode$1(intValue, muteQuickAffordanceCoreStartable, null), 2);
    }
}
