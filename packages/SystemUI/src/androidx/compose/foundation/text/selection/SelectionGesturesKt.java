package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.text.TextDragObserver;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectionGesturesKt {
    /* JADX WARN: Removed duplicated region for block: B:12:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0042 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0040 -> B:10:0x0043). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$awaitDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope r7, kotlin.coroutines.jvm.internal.BaseContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof androidx.compose.foundation.text.selection.SelectionGesturesKt$awaitDown$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.text.selection.SelectionGesturesKt$awaitDown$1 r0 = (androidx.compose.foundation.text.selection.SelectionGesturesKt$awaitDown$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.text.selection.SelectionGesturesKt$awaitDown$1 r0 = new androidx.compose.foundation.text.selection.SelectionGesturesKt$awaitDown$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r7 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r7 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L43
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L33:
            kotlin.ResultKt.throwOnFailure(r8)
        L36:
            androidx.compose.ui.input.pointer.PointerEventPass r8 = androidx.compose.ui.input.pointer.PointerEventPass.Main
            r0.L$0 = r7
            r0.label = r3
            java.lang.Object r8 = r7.awaitPointerEvent(r8, r0)
            if (r8 != r1) goto L43
            return r1
        L43:
            androidx.compose.ui.input.pointer.PointerEvent r8 = (androidx.compose.ui.input.pointer.PointerEvent) r8
            java.util.List r2 = r8.changes
            r4 = r2
            java.util.Collection r4 = (java.util.Collection) r4
            int r4 = r4.size()
            r5 = 0
        L4f:
            if (r5 >= r4) goto L61
            java.lang.Object r6 = r2.get(r5)
            androidx.compose.ui.input.pointer.PointerInputChange r6 = (androidx.compose.ui.input.pointer.PointerInputChange) r6
            boolean r6 = androidx.compose.ui.input.pointer.PointerEventKt.changedToDownIgnoreConsumed(r6)
            if (r6 != 0) goto L5e
            goto L36
        L5e:
            int r5 = r5 + 1
            goto L4f
        L61:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.SelectionGesturesKt.access$awaitDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$mouseSelection(androidx.compose.ui.input.pointer.AwaitPointerEventScope r17, androidx.compose.foundation.text.selection.MouseSelectionObserver r18, androidx.compose.foundation.text.selection.ClicksCounter r19, androidx.compose.ui.input.pointer.PointerEvent r20, kotlin.coroutines.jvm.internal.BaseContinuationImpl r21) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.SelectionGesturesKt.access$mouseSelection(androidx.compose.ui.input.pointer.AwaitPointerEventScope, androidx.compose.foundation.text.selection.MouseSelectionObserver, androidx.compose.foundation.text.selection.ClicksCounter, androidx.compose.ui.input.pointer.PointerEvent, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a8, code lost:
    
        if (r14 == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0073 A[Catch: CancellationException -> 0x0035, TryCatch #0 {CancellationException -> 0x0035, blocks: (B:12:0x0030, B:13:0x00ab, B:15:0x00b3, B:17:0x00c2, B:19:0x00ce, B:21:0x00d1, B:24:0x00d4, B:28:0x00d8, B:32:0x004c, B:34:0x006f, B:36:0x0073, B:40:0x0091, B:45:0x0056), top: B:7:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$touchSelection(androidx.compose.ui.input.pointer.AwaitPointerEventScope r11, final androidx.compose.foundation.text.TextDragObserver r12, androidx.compose.ui.input.pointer.PointerEvent r13, kotlin.coroutines.jvm.internal.BaseContinuationImpl r14) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.SelectionGesturesKt.access$touchSelection(androidx.compose.ui.input.pointer.AwaitPointerEventScope, androidx.compose.foundation.text.TextDragObserver, androidx.compose.ui.input.pointer.PointerEvent, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
    }

    public static final boolean isPrecisePointer(PointerEvent pointerEvent) {
        List list = pointerEvent.changes;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int i2 = ((PointerInputChange) list.get(i)).type;
            PointerType.Companion.getClass();
            if (i2 != PointerType.Mouse) {
                return false;
            }
        }
        return true;
    }

    public static final Modifier selectionGestureInput(Modifier modifier, final MouseSelectionObserver mouseSelectionObserver, final TextDragObserver textDragObserver) {
        PointerInputEventHandler pointerInputEventHandler = new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.selection.SelectionGesturesKt$selectionGestureInput$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: androidx.compose.foundation.text.selection.SelectionGesturesKt$selectionGestureInput$1$1, reason: invalid class name */
            final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                final /* synthetic */ ClicksCounter $clicksCounter;
                final /* synthetic */ MouseSelectionObserver $mouseSelectionObserver;
                final /* synthetic */ TextDragObserver $textDragObserver;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(MouseSelectionObserver mouseSelectionObserver, ClicksCounter clicksCounter, TextDragObserver textDragObserver, Continuation continuation) {
                    super(2, continuation);
                    this.$mouseSelectionObserver = mouseSelectionObserver;
                    this.$clicksCounter = clicksCounter;
                    this.$textDragObserver = textDragObserver;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$mouseSelectionObserver, this.$clicksCounter, this.$textDragObserver, continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:
                
                    if (androidx.compose.foundation.text.selection.SelectionGesturesKt.access$mouseSelection(r1, r2, r3, r10, r9) == r0) goto L32;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:26:0x0083, code lost:
                
                    return r0;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:30:0x0081, code lost:
                
                    if (androidx.compose.foundation.text.selection.SelectionGesturesKt.access$touchSelection(r1, r2, r10, r9) == r0) goto L32;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:32:0x0034, code lost:
                
                    if (r10 == r0) goto L32;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r10) {
                    /*
                        r9 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r9.label
                        r2 = 1
                        r3 = 3
                        r4 = 2
                        if (r1 == 0) goto L24
                        if (r1 == r2) goto L1c
                        if (r1 == r4) goto L18
                        if (r1 != r3) goto L10
                        goto L18
                    L10:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L18:
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L84
                    L1c:
                        java.lang.Object r1 = r9.L$0
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L37
                    L24:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.lang.Object r10 = r9.L$0
                        r1 = r10
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                        r9.L$0 = r1
                        r9.label = r2
                        java.lang.Object r10 = androidx.compose.foundation.text.selection.SelectionGesturesKt.access$awaitDown(r1, r9)
                        if (r10 != r0) goto L37
                        goto L83
                    L37:
                        androidx.compose.ui.input.pointer.PointerEvent r10 = (androidx.compose.ui.input.pointer.PointerEvent) r10
                        boolean r2 = androidx.compose.foundation.text.selection.SelectionGesturesKt.isPrecisePointer(r10)
                        r5 = 0
                        if (r2 == 0) goto L71
                        int r2 = r10.buttons
                        r2 = r2 & 33
                        if (r2 == 0) goto L71
                        java.util.List r2 = r10.changes
                        r6 = r2
                        java.util.Collection r6 = (java.util.Collection) r6
                        int r6 = r6.size()
                        r7 = 0
                    L50:
                        if (r7 >= r6) goto L62
                        java.lang.Object r8 = r2.get(r7)
                        androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8
                        boolean r8 = r8.isConsumed()
                        if (r8 == 0) goto L5f
                        goto L71
                    L5f:
                        int r7 = r7 + 1
                        goto L50
                    L62:
                        androidx.compose.foundation.text.selection.MouseSelectionObserver r2 = r9.$mouseSelectionObserver
                        androidx.compose.foundation.text.selection.ClicksCounter r3 = r9.$clicksCounter
                        r9.L$0 = r5
                        r9.label = r4
                        java.lang.Object r9 = androidx.compose.foundation.text.selection.SelectionGesturesKt.access$mouseSelection(r1, r2, r3, r10, r9)
                        if (r9 != r0) goto L84
                        goto L83
                    L71:
                        boolean r2 = androidx.compose.foundation.text.selection.SelectionGesturesKt.isPrecisePointer(r10)
                        if (r2 != 0) goto L84
                        androidx.compose.foundation.text.TextDragObserver r2 = r9.$textDragObserver
                        r9.L$0 = r5
                        r9.label = r3
                        java.lang.Object r9 = androidx.compose.foundation.text.selection.SelectionGesturesKt.access$touchSelection(r1, r2, r10, r9)
                        if (r9 != r0) goto L84
                    L83:
                        return r0
                    L84:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.SelectionGesturesKt$selectionGestureInput$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass1(MouseSelectionObserver.this, new ClicksCounter(pointerInputScope.getViewConfiguration()), textDragObserver, null), continuation);
                return awaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitEachGesture : Unit.INSTANCE;
            }
        };
        PointerEvent pointerEvent = SuspendingPointerInputFilterKt.EmptyPointerEvent;
        return modifier.then(new SuspendPointerInputElement(mouseSelectionObserver, textDragObserver, null, pointerInputEventHandler, 4, null));
    }

    public static final Modifier updateSelectionTouchMode(Modifier.Companion companion, final Function1 function1) {
        return SuspendingPointerInputFilterKt.pointerInput((Modifier) companion, (Object) 8675309, new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.selection.SelectionGesturesKt$updateSelectionTouchMode$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: androidx.compose.foundation.text.selection.SelectionGesturesKt$updateSelectionTouchMode$1$1, reason: invalid class name */
            final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                final /* synthetic */ Function1 $updateTouchMode;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(Function1 function1, Continuation continuation) {
                    super(2, continuation);
                    this.$updateTouchMode = function1;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$updateTouchMode, continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                    jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
                    	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                    	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                    	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                    */
                /* JADX WARN: Removed duplicated region for block: B:8:0x002d A[RETURN] */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:7:0x002b -> B:5:0x002e). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r5) {
                    /*
                        r4 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r4.label
                        r2 = 1
                        if (r1 == 0) goto L19
                        if (r1 != r2) goto L11
                        java.lang.Object r1 = r4.L$0
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                        kotlin.ResultKt.throwOnFailure(r5)
                        goto L2e
                    L11:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L19:
                        kotlin.ResultKt.throwOnFailure(r5)
                        java.lang.Object r5 = r4.L$0
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r5 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r5
                        r1 = r5
                    L21:
                        androidx.compose.ui.input.pointer.PointerEventPass r5 = androidx.compose.ui.input.pointer.PointerEventPass.Initial
                        r4.L$0 = r1
                        r4.label = r2
                        java.lang.Object r5 = r1.awaitPointerEvent(r5, r4)
                        if (r5 != r0) goto L2e
                        return r0
                    L2e:
                        androidx.compose.ui.input.pointer.PointerEvent r5 = (androidx.compose.ui.input.pointer.PointerEvent) r5
                        kotlin.jvm.functions.Function1 r3 = r4.$updateTouchMode
                        boolean r5 = androidx.compose.foundation.text.selection.SelectionGesturesKt.isPrecisePointer(r5)
                        r5 = r5 ^ r2
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r3.mo779invoke(r5)
                        goto L21
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.SelectionGesturesKt$updateSelectionTouchMode$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object awaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new AnonymousClass1(Function1.this, null), continuation);
                return awaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitPointerEventScope : Unit.INSTANCE;
            }
        });
    }
}
