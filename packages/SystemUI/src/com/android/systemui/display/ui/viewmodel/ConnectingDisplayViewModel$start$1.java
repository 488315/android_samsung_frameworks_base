package com.android.systemui.display.ui.viewmodel;

import android.os.Trace;
import android.util.Log;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl$pendingDisplay$1$1;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.StateFlowImpl;

final class ConnectingDisplayViewModel$start$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ConnectingDisplayViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectingDisplayViewModel$start$1(ConnectingDisplayViewModel connectingDisplayViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = connectingDisplayViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Boolean) obj2).booleanValue();
        ConnectingDisplayViewModel$start$1 connectingDisplayViewModel$start$1 = new ConnectingDisplayViewModel$start$1(this.this$0, (Continuation) obj3);
        connectingDisplayViewModel$start$1.L$0 = (ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1) obj;
        return connectingDisplayViewModel$start$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1 connectedDisplayInteractorImpl$toInteractorPendingDisplay$1 = (ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1) this.L$0;
            if (connectedDisplayInteractorImpl$toInteractorPendingDisplay$1 == null) {
                this.this$0.getClass();
            } else {
                this.label = 1;
                DisplayRepositoryImpl$pendingDisplay$1$1 displayRepositoryImpl$pendingDisplay$1$1 = (DisplayRepositoryImpl$pendingDisplay$1$1) connectedDisplayInteractorImpl$toInteractorPendingDisplay$1.$this_toInteractorPendingDisplay;
                StringBuilder sb = new StringBuilder("DisplayRepository#enable(");
                int i2 = displayRepositoryImpl$pendingDisplay$1$1.$id;
                String m = Anchor$$ExternalSyntheticOutline0.m(i2, ")", sb);
                DisplayRepositoryImpl displayRepositoryImpl = displayRepositoryImpl$pendingDisplay$1$1.this$0;
                boolean isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice(m);
                }
                try {
                    DisplayRepositoryImpl.Companion.getClass();
                    if (DisplayRepositoryImpl.DEBUG) {
                        Log.d("DisplayRepository", "Enabling display with id=" + i2);
                    }
                    displayRepositoryImpl.displayManager.enableConnectedDisplay(i2);
                    Unit unit = Unit.INSTANCE;
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    String m2 = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i2, "DisplayRepository#ignore(", ")");
                    boolean isEnabled2 = Trace.isEnabled();
                    if (isEnabled2) {
                        TraceUtilsKt.beginSlice(m2);
                    }
                    try {
                        StateFlowImpl stateFlowImpl = displayRepositoryImpl._ignoredDisplayIds;
                        stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), new Integer(i2)));
                        if (Unit.INSTANCE == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } finally {
                        if (isEnabled2) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                } catch (Throwable th) {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    throw th;
                }
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
