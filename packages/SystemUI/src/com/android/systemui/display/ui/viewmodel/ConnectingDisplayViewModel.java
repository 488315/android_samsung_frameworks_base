package com.android.systemui.display.ui.viewmodel;

import android.content.Context;
import android.os.Trace;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.app.displaylib.DisplayRepositoryImpl;
import com.android.app.displaylib.DisplayRepositoryImpl$pendingDisplay$1$1;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1;
import com.android.systemui.display.ui.view.MirroringConfirmationDialogDelegate$Factory;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class ConnectingDisplayViewModel implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConnectedDisplayInteractor connectedDisplayInteractor;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.display.ui.viewmodel.ConnectingDisplayViewModel$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = ConnectingDisplayViewModel.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1 connectedDisplayInteractorImpl$toInteractorPendingDisplay$1 = (ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1) this.L$0;
                if (connectedDisplayInteractorImpl$toInteractorPendingDisplay$1 == null) {
                    ConnectingDisplayViewModel connectingDisplayViewModel = ConnectingDisplayViewModel.this;
                    int i2 = ConnectingDisplayViewModel.$r8$clinit;
                    connectingDisplayViewModel.getClass();
                } else {
                    this.label = 1;
                    DisplayRepositoryImpl$pendingDisplay$1$1 displayRepositoryImpl$pendingDisplay$1$1 = (DisplayRepositoryImpl$pendingDisplay$1$1) connectedDisplayInteractorImpl$toInteractorPendingDisplay$1.$this_toInteractorPendingDisplay;
                    StringBuilder sb = new StringBuilder("DisplayRepository#enable(");
                    int i3 = displayRepositoryImpl$pendingDisplay$1$1.$id;
                    String strM = ReorderTile$$ExternalSyntheticOutline0.m(i3, ")", sb);
                    DisplayRepositoryImpl displayRepositoryImpl = displayRepositoryImpl$pendingDisplay$1$1.this$0;
                    boolean zIsEnabled = Trace.isEnabled();
                    if (zIsEnabled) {
                        TraceUtilsKt.beginSlice(strM);
                    }
                    try {
                        DisplayRepositoryImpl.Companion.getClass();
                        if (DisplayRepositoryImpl.DEBUG) {
                            Log.d("DisplayRepository", "Enabling display with id=" + i3);
                        }
                        displayRepositoryImpl.displayManager.enableConnectedDisplay(i3);
                        Unit unit = Unit.INSTANCE;
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                        String strM2 = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i3, "DisplayRepository#ignore(", ")");
                        boolean zIsEnabled2 = Trace.isEnabled();
                        if (zIsEnabled2) {
                            TraceUtilsKt.beginSlice(strM2);
                        }
                        try {
                            StateFlowImpl stateFlowImpl = displayRepositoryImpl._ignoredDisplayIds;
                            stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), new Integer(i3)));
                            if (Unit.INSTANCE == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } finally {
                            if (zIsEnabled2) {
                                TraceUtilsKt.endSlice();
                            }
                        }
                    } catch (Throwable th) {
                        if (zIsEnabled) {
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

    public ConnectingDisplayViewModel(Context context, ConnectedDisplayInteractor connectedDisplayInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, MirroringConfirmationDialogDelegate$Factory mirroringConfirmationDialogDelegate$Factory) {
        this.connectedDisplayInteractor = connectedDisplayInteractor;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl = (ConnectedDisplayInteractorImpl) this.connectedDisplayInteractor;
        ConnectedDisplayInteractorImpl$special$$inlined$map$4 connectedDisplayInteractorImpl$special$$inlined$map$4 = connectedDisplayInteractorImpl.pendingDisplay;
        Flow flow = connectedDisplayInteractorImpl.concurrentDisplaysInProgress;
        Duration.Companion companion = Duration.Companion;
        FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.m3482debounceHG0u8IE(connectedDisplayInteractorImpl$special$$inlined$map$4, DurationKt.toDuration(200, DurationUnit.MILLISECONDS)), flow, new AnonymousClass1(null)), this.scope);
    }
}
