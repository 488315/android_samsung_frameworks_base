package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Result;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 */
/* loaded from: classes.dex */
public final class C0308xfdb59cc4 implements LifecycleEventObserver {
    public final /* synthetic */ Function0 $block;
    public final /* synthetic */ CancellableContinuation $co;
    public final /* synthetic */ Lifecycle.State $state;
    public final /* synthetic */ Lifecycle $this_suspendWithStateAtLeastUnchecked;

    public C0308xfdb59cc4(Lifecycle.State state, Lifecycle lifecycle, CancellableContinuation cancellableContinuation, Function0 function0) {
        this.$state = state;
        this.$this_suspendWithStateAtLeastUnchecked = lifecycle;
        this.$co = cancellableContinuation;
        this.$block = function0;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Object failure;
        Lifecycle.Event event2 = Lifecycle.Event.ON_CREATE;
        int i = Lifecycle.AbstractC03001.$SwitchMap$androidx$lifecycle$Lifecycle$State[this.$state.ordinal()];
        Lifecycle.Event event3 = i != 1 ? i != 2 ? i != 3 ? null : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START : Lifecycle.Event.ON_CREATE;
        CancellableContinuation cancellableContinuation = this.$co;
        Lifecycle lifecycle = this.$this_suspendWithStateAtLeastUnchecked;
        if (event != event3) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                lifecycle.removeObserver(this);
                int i2 = Result.$r8$clinit;
                ((CancellableContinuationImpl) cancellableContinuation).resumeWith(new Result.Failure(new LifecycleDestroyedException()));
                return;
            }
            return;
        }
        lifecycle.removeObserver(this);
        Function0 function0 = this.$block;
        try {
            int i3 = Result.$r8$clinit;
            failure = function0.invoke();
        } catch (Throwable th) {
            int i4 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        ((CancellableContinuationImpl) cancellableContinuation).resumeWith(failure);
    }
}
