package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.app.displaylib.DisplayRepositoryImpl;
import com.android.app.tracing.TraceUtilsKt;
import java.util.ArrayList;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DisplayRepositoryImpl$connectedDisplayIds$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Handler $backgroundHandler;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DisplayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayRepositoryImpl$connectedDisplayIds$1(DisplayRepositoryImpl displayRepositoryImpl, Handler handler, Continuation continuation) {
        super(2, continuation);
        this.this$0 = displayRepositoryImpl;
        this.$backgroundHandler = handler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayRepositoryImpl$connectedDisplayIds$1 displayRepositoryImpl$connectedDisplayIds$1 = new DisplayRepositoryImpl$connectedDisplayIds$1(this.this$0, this.$backgroundHandler, continuation);
        displayRepositoryImpl$connectedDisplayIds$1.L$0 = obj;
        return displayRepositoryImpl$connectedDisplayIds$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayRepositoryImpl$connectedDisplayIds$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.hardware.display.DisplayManager$DisplayListener, com.android.app.displaylib.DisplayRepositoryImpl$connectedDisplayIds$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            DisplayRepositoryImpl displayRepositoryImpl = this.this$0;
            DisplayRepositoryImpl.Companion companion = DisplayRepositoryImpl.Companion;
            displayRepositoryImpl.getClass();
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("DisplayRepository#getInitialConnectedDisplays");
            }
            try {
                Display[] displays = displayRepositoryImpl.displayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
                ArrayList arrayList = new ArrayList(displays.length);
                for (Display display : displays) {
                    arrayList.add(Integer.valueOf(display.getDisplayId()));
                }
                Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                if (DisplayRepositoryImpl.DEBUG) {
                    Log.d("DisplayRepository", "getInitialConnectedDisplays: " + set);
                }
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                final Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
                final DisplayRepositoryImpl displayRepositoryImpl2 = this.this$0;
                ?? r4 = new DisplayManager.DisplayListener() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$connectedDisplayIds$1$callback$1
                    public final void onDisplayConnected(int i2) {
                        DisplayRepositoryImpl.Companion.getClass();
                        if (DisplayRepositoryImpl.DEBUG) {
                            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i2, "display with id=", " connected.", "DisplayRepository");
                        }
                        mutableSet.add(Integer.valueOf(i2));
                        StateFlowImpl stateFlowImpl = displayRepositoryImpl2._ignoredDisplayIds;
                        stateFlowImpl.updateState(null, SetsKt___SetsKt.minus((Set) stateFlowImpl.getValue(), Integer.valueOf(i2)));
                        ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(CollectionsKt___CollectionsKt.toSet(mutableSet));
                    }

                    public final void onDisplayDisconnected(int i2) {
                        mutableSet.remove(Integer.valueOf(i2));
                        DisplayRepositoryImpl.Companion.getClass();
                        if (DisplayRepositoryImpl.DEBUG) {
                            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i2, "display with id=", " disconnected.", "DisplayRepository");
                        }
                        StateFlowImpl stateFlowImpl = displayRepositoryImpl2._ignoredDisplayIds;
                        stateFlowImpl.updateState(null, SetsKt___SetsKt.minus((Set) stateFlowImpl.getValue(), Integer.valueOf(i2)));
                        ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(CollectionsKt___CollectionsKt.toSet(mutableSet));
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayAdded(int i2) {
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayChanged(int i2) {
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayRemoved(int i2) {
                    }
                };
                ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(CollectionsKt___CollectionsKt.toSet(mutableSet));
                this.this$0.displayManager.registerDisplayListener(r4, this.$backgroundHandler, 0L, 4L);
                DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0 displayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0 = new DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0(this.this$0, (DisplayRepositoryImpl$connectedDisplayIds$1$callback$1) r4);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, displayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } finally {
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
