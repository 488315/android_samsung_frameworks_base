package com.android.systemui.display.ui.viewmodel;

import android.os.Trace;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.app.displaylib.DisplayRepositoryImpl;
import com.android.app.displaylib.DisplayRepositoryImpl$pendingDisplay$1$1;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
                ConnectingDisplayViewModel connectingDisplayViewModel = this.this$0;
                int i2 = ConnectingDisplayViewModel.$r8$clinit;
                connectingDisplayViewModel.getClass();
            } else {
                this.label = 1;
                DisplayRepositoryImpl$pendingDisplay$1$1 displayRepositoryImpl$pendingDisplay$1$1 = (DisplayRepositoryImpl$pendingDisplay$1$1) connectedDisplayInteractorImpl$toInteractorPendingDisplay$1.$this_toInteractorPendingDisplay;
                StringBuilder sb = new StringBuilder("DisplayRepository#enable(");
                int i3 = displayRepositoryImpl$pendingDisplay$1$1.$id;
                String m = ReorderTile$$ExternalSyntheticOutline0.m(i3, ")", sb);
                DisplayRepositoryImpl displayRepositoryImpl = displayRepositoryImpl$pendingDisplay$1$1.this$0;
                boolean isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice(m);
                }
                try {
                    DisplayRepositoryImpl.Companion.getClass();
                    if (DisplayRepositoryImpl.DEBUG) {
                        Log.d("DisplayRepository", "Enabling display with id=" + i3);
                    }
                    displayRepositoryImpl.displayManager.enableConnectedDisplay(i3);
                    Unit unit = Unit.INSTANCE;
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    String m2 = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i3, "DisplayRepository#ignore(", ")");
                    boolean isEnabled2 = Trace.isEnabled();
                    if (isEnabled2) {
                        TraceUtilsKt.beginSlice(m2);
                    }
                    try {
                        StateFlowImpl stateFlowImpl = displayRepositoryImpl._ignoredDisplayIds;
                        stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), new Integer(i3)));
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
