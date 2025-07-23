package com.android.systemui.qs.panels.ui.compose;

import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileListenerKt$TileListener$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $listeningEnabled;
    final /* synthetic */ List<TileViewModel> $tiles;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileListenerKt$TileListener$1$1(List<TileViewModel> list, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$tiles = list;
        this.$listeningEnabled = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TileListenerKt$TileListener$1$1(this.$tiles, this.$listeningEnabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileListenerKt$TileListener$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0071 A[LOOP:1: B:18:0x006b->B:20:0x0071, LOOP_END] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L1a
            if (r1 != r3) goto L12
            java.lang.Object r0 = r7.L$0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L10
            goto L40
        L10:
            r8 = move-exception
            goto L63
        L12:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1a:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = new java.lang.Object
            r8.<init>()
            kotlin.jvm.functions.Function0 r1 = r7.$listeningEnabled     // Catch: java.lang.Throwable -> L61
            com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L61
            r4.<init>()     // Catch: java.lang.Throwable -> L61
            kotlinx.coroutines.flow.SafeFlow r1 = androidx.compose.runtime.SnapshotStateKt.snapshotFlow(r4)     // Catch: java.lang.Throwable -> L61
            com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1$2 r4 = new com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1$2     // Catch: java.lang.Throwable -> L61
            java.util.List<com.android.systemui.qs.panels.ui.viewmodel.TileViewModel> r5 = r7.$tiles     // Catch: java.lang.Throwable -> L61
            r4.<init>()     // Catch: java.lang.Throwable -> L61
            r7.L$0 = r8     // Catch: java.lang.Throwable -> L61
            r7.label = r3     // Catch: java.lang.Throwable -> L61
            java.lang.Object r1 = r1.collect(r4, r7)     // Catch: java.lang.Throwable -> L61
            if (r1 != r0) goto L3f
            return r0
        L3f:
            r0 = r8
        L40:
            java.util.List<com.android.systemui.qs.panels.ui.viewmodel.TileViewModel> r7 = r7.$tiles
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.Iterator r7 = r7.iterator()
        L48:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L5a
            java.lang.Object r8 = r7.next()
            com.android.systemui.qs.panels.ui.viewmodel.TileViewModel r8 = (com.android.systemui.qs.panels.ui.viewmodel.TileViewModel) r8
            com.android.systemui.plugins.qs.QSTile r8 = r8.tile
            r8.setListening(r0, r2)
            goto L48
        L5a:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L5d:
            r6 = r0
            r0 = r8
            r8 = r6
            goto L63
        L61:
            r0 = move-exception
            goto L5d
        L63:
            java.util.List<com.android.systemui.qs.panels.ui.viewmodel.TileViewModel> r7 = r7.$tiles
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.Iterator r7 = r7.iterator()
        L6b:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L7d
            java.lang.Object r1 = r7.next()
            com.android.systemui.qs.panels.ui.viewmodel.TileViewModel r1 = (com.android.systemui.qs.panels.ui.viewmodel.TileViewModel) r1
            com.android.systemui.plugins.qs.QSTile r1 = r1.tile
            r1.setListening(r0, r2)
            goto L6b
        L7d:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
