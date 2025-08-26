package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.text.TextDragObserver;
import androidx.compose.foundation.text.selection.SelectionAdjustment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.platform.ViewConfiguration;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes.dex */
public abstract class SelectionGesturesKt {
    /* JADX WARN: Removed duplicated region for block: B:17:0x0042 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0040 -> B:18:0x0043). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$awaitDown(AwaitPointerEventScope awaitPointerEventScope, BaseContinuationImpl baseContinuationImpl) {
        SelectionGesturesKt$awaitDown$1 selectionGesturesKt$awaitDown$1;
        int size;
        int i;
        if (baseContinuationImpl instanceof SelectionGesturesKt$awaitDown$1) {
            selectionGesturesKt$awaitDown$1 = (SelectionGesturesKt$awaitDown$1) baseContinuationImpl;
            int i2 = selectionGesturesKt$awaitDown$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                selectionGesturesKt$awaitDown$1.label = i2 - Integer.MIN_VALUE;
            } else {
                selectionGesturesKt$awaitDown$1 = new SelectionGesturesKt$awaitDown$1(baseContinuationImpl);
            }
        }
        Object objAwaitPointerEvent = selectionGesturesKt$awaitDown$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = selectionGesturesKt$awaitDown$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objAwaitPointerEvent);
            PointerEventPass pointerEventPass = PointerEventPass.Main;
            selectionGesturesKt$awaitDown$1.L$0 = awaitPointerEventScope;
            selectionGesturesKt$awaitDown$1.label = 1;
            objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, selectionGesturesKt$awaitDown$1);
            if (objAwaitPointerEvent == coroutineSingletons) {
            }
            PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent;
            List list = pointerEvent.changes;
            size = list.size();
            i = 0;
            while (i < size) {
            }
            return pointerEvent;
        }
        if (i3 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        awaitPointerEventScope = (AwaitPointerEventScope) selectionGesturesKt$awaitDown$1.L$0;
        ResultKt.throwOnFailure(objAwaitPointerEvent);
        PointerEvent pointerEvent2 = (PointerEvent) objAwaitPointerEvent;
        List list2 = pointerEvent2.changes;
        size = list2.size();
        i = 0;
        while (i < size) {
            if (PointerEventKt.changedToDownIgnoreConsumed((PointerInputChange) list2.get(i))) {
                i++;
            } else {
                PointerEventPass pointerEventPass2 = PointerEventPass.Main;
                selectionGesturesKt$awaitDown$1.L$0 = awaitPointerEventScope;
                selectionGesturesKt$awaitDown$1.label = 1;
                objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass2, selectionGesturesKt$awaitDown$1);
                if (objAwaitPointerEvent == coroutineSingletons) {
                    return coroutineSingletons;
                }
                PointerEvent pointerEvent22 = (PointerEvent) objAwaitPointerEvent;
                List list22 = pointerEvent22.changes;
                size = list22.size();
                i = 0;
                while (i < size) {
                }
            }
        }
        return pointerEvent22;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$mouseSelection(AwaitPointerEventScope awaitPointerEventScope, MouseSelectionObserver mouseSelectionObserver, ClicksCounter clicksCounter, PointerEvent pointerEvent, BaseContinuationImpl baseContinuationImpl) {
        SelectionGesturesKt$mouseSelection$1 selectionGesturesKt$mouseSelection$1;
        final SelectionAdjustment$Companion$$ExternalSyntheticLambda0 selectionAdjustment$Companion$$ExternalSyntheticLambda0;
        final Ref$BooleanRef ref$BooleanRef;
        Object objM72dragjO51t88;
        int size;
        AwaitPointerEventScope awaitPointerEventScope2 = awaitPointerEventScope;
        final MouseSelectionObserver mouseSelectionObserver2 = mouseSelectionObserver;
        if (baseContinuationImpl instanceof SelectionGesturesKt$mouseSelection$1) {
            selectionGesturesKt$mouseSelection$1 = (SelectionGesturesKt$mouseSelection$1) baseContinuationImpl;
            int i = selectionGesturesKt$mouseSelection$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                selectionGesturesKt$mouseSelection$1.label = i - Integer.MIN_VALUE;
            } else {
                selectionGesturesKt$mouseSelection$1 = new SelectionGesturesKt$mouseSelection$1(baseContinuationImpl);
            }
        }
        Object obj = selectionGesturesKt$mouseSelection$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = selectionGesturesKt$mouseSelection$1.label;
        int i3 = 0;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputChange pointerInputChange = clicksCounter.prevClick;
            PointerInputChange pointerInputChange2 = (PointerInputChange) pointerEvent.changes.get(0);
            if (pointerInputChange != null) {
                long j = pointerInputChange2.uptimeMillis - pointerInputChange.uptimeMillis;
                ViewConfiguration viewConfiguration = clicksCounter.viewConfiguration;
                if (j < viewConfiguration.getDoubleTapTimeoutMillis()) {
                    if (Offset.m399getDistanceimpl(Offset.m402minusMKHz9U(pointerInputChange.position, pointerInputChange2.position)) < DragGestureDetectorKt.m75pointerSlopE8SPZFQ(viewConfiguration, pointerInputChange.type)) {
                        clicksCounter.clicks++;
                    } else {
                        clicksCounter.clicks = 1;
                    }
                    clicksCounter.prevClick = pointerInputChange2;
                    PointerInputChange pointerInputChange3 = (PointerInputChange) pointerEvent.changes.get(0);
                    int i4 = clicksCounter.clicks;
                    if (i4 == 1) {
                        SelectionAdjustment.Companion.getClass();
                        selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.None;
                    } else if (i4 != 2) {
                        SelectionAdjustment.Companion.getClass();
                        selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.Paragraph;
                    } else {
                        SelectionAdjustment.Companion.getClass();
                        selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.Word;
                    }
                    if (mouseSelectionObserver2.mo231onStart3MmeM6k(pointerInputChange3.position, selectionAdjustment$Companion$$ExternalSyntheticLambda0)) {
                        ref$BooleanRef = new Ref$BooleanRef();
                        SelectionAdjustment.Companion.getClass();
                        ref$BooleanRef.element = !Intrinsics.areEqual(selectionAdjustment$Companion$$ExternalSyntheticLambda0, SelectionAdjustment.Companion.None);
                        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionGesturesKt$mouseSelection$shouldConsumeUp$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                PointerInputChange pointerInputChange4 = (PointerInputChange) obj2;
                                if (mouseSelectionObserver2.mo230onDrag3MmeM6k(pointerInputChange4.position, selectionAdjustment$Companion$$ExternalSyntheticLambda0)) {
                                    pointerInputChange4.consume();
                                    ref$BooleanRef.element = true;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        selectionGesturesKt$mouseSelection$1.L$0 = awaitPointerEventScope2;
                        selectionGesturesKt$mouseSelection$1.L$1 = mouseSelectionObserver2;
                        selectionGesturesKt$mouseSelection$1.L$2 = ref$BooleanRef;
                        selectionGesturesKt$mouseSelection$1.label = 2;
                        objM72dragjO51t88 = DragGestureDetectorKt.m72dragjO51t88(awaitPointerEventScope2, pointerInputChange3.id, function1, selectionGesturesKt$mouseSelection$1);
                        if (objM72dragjO51t88 == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        if (((Boolean) objM72dragjO51t88).booleanValue()) {
                            List list = awaitPointerEventScope2.getCurrentEvent().changes;
                            size = list.size();
                            while (i3 < size) {
                            }
                        }
                        mouseSelectionObserver2.onDragDone();
                    }
                }
            }
        } else if (i2 == 1) {
            MouseSelectionObserver mouseSelectionObserver3 = (MouseSelectionObserver) selectionGesturesKt$mouseSelection$1.L$1;
            AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) selectionGesturesKt$mouseSelection$1.L$0;
            ResultKt.throwOnFailure(obj);
            if (((Boolean) obj).booleanValue()) {
                List list2 = awaitPointerEventScope3.getCurrentEvent().changes;
                int size2 = list2.size();
                while (i3 < size2) {
                    PointerInputChange pointerInputChange4 = (PointerInputChange) list2.get(i3);
                    if (PointerEventKt.changedToUp(pointerInputChange4)) {
                        pointerInputChange4.consume();
                    }
                    i3++;
                }
            }
            mouseSelectionObserver3.onDragDone();
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Ref$BooleanRef ref$BooleanRef2 = (Ref$BooleanRef) selectionGesturesKt$mouseSelection$1.L$2;
            mouseSelectionObserver2 = (MouseSelectionObserver) selectionGesturesKt$mouseSelection$1.L$1;
            AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) selectionGesturesKt$mouseSelection$1.L$0;
            ResultKt.throwOnFailure(obj);
            ref$BooleanRef = ref$BooleanRef2;
            awaitPointerEventScope2 = awaitPointerEventScope4;
            objM72dragjO51t88 = obj;
            if (((Boolean) objM72dragjO51t88).booleanValue() && ref$BooleanRef.element) {
                List list3 = awaitPointerEventScope2.getCurrentEvent().changes;
                size = list3.size();
                while (i3 < size) {
                    PointerInputChange pointerInputChange5 = (PointerInputChange) list3.get(i3);
                    if (PointerEventKt.changedToUp(pointerInputChange5)) {
                        pointerInputChange5.consume();
                    }
                    i3++;
                }
            }
            mouseSelectionObserver2.onDragDone();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a8, code lost:
    
        if (r14 == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$touchSelection(AwaitPointerEventScope awaitPointerEventScope, final TextDragObserver textDragObserver, PointerEvent pointerEvent, BaseContinuationImpl baseContinuationImpl) {
        SelectionGesturesKt$touchSelection$1 selectionGesturesKt$touchSelection$1;
        PointerInputChange pointerInputChange;
        if (baseContinuationImpl instanceof SelectionGesturesKt$touchSelection$1) {
            selectionGesturesKt$touchSelection$1 = (SelectionGesturesKt$touchSelection$1) baseContinuationImpl;
            int i = selectionGesturesKt$touchSelection$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                selectionGesturesKt$touchSelection$1.label = i - Integer.MIN_VALUE;
            } else {
                selectionGesturesKt$touchSelection$1 = new SelectionGesturesKt$touchSelection$1(baseContinuationImpl);
            }
        }
        Object objM70awaitLongPressOrCancellationrnUCldI = selectionGesturesKt$touchSelection$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = selectionGesturesKt$touchSelection$1.label;
        boolean z = true;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objM70awaitLongPressOrCancellationrnUCldI);
                pointerInputChange = (PointerInputChange) CollectionsKt___CollectionsKt.first(pointerEvent.changes);
                long j = pointerInputChange.id;
                selectionGesturesKt$touchSelection$1.L$0 = awaitPointerEventScope;
                selectionGesturesKt$touchSelection$1.L$1 = textDragObserver;
                selectionGesturesKt$touchSelection$1.L$2 = pointerInputChange;
                selectionGesturesKt$touchSelection$1.label = 1;
                objM70awaitLongPressOrCancellationrnUCldI = DragGestureDetectorKt.m70awaitLongPressOrCancellationrnUCldI(awaitPointerEventScope, j, selectionGesturesKt$touchSelection$1);
                if (objM70awaitLongPressOrCancellationrnUCldI == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                textDragObserver = (TextDragObserver) selectionGesturesKt$touchSelection$1.L$1;
                awaitPointerEventScope = (AwaitPointerEventScope) selectionGesturesKt$touchSelection$1.L$0;
                ResultKt.throwOnFailure(objM70awaitLongPressOrCancellationrnUCldI);
                if (((Boolean) objM70awaitLongPressOrCancellationrnUCldI).booleanValue()) {
                    List list = awaitPointerEventScope.getCurrentEvent().changes;
                    int size = list.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        PointerInputChange pointerInputChange2 = (PointerInputChange) list.get(i3);
                        if (PointerEventKt.changedToUp(pointerInputChange2)) {
                            pointerInputChange2.consume();
                        }
                    }
                    textDragObserver.onStop();
                } else {
                    textDragObserver.onCancel();
                }
                return Unit.INSTANCE;
            }
            PointerInputChange pointerInputChange3 = (PointerInputChange) selectionGesturesKt$touchSelection$1.L$2;
            textDragObserver = (TextDragObserver) selectionGesturesKt$touchSelection$1.L$1;
            AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) selectionGesturesKt$touchSelection$1.L$0;
            ResultKt.throwOnFailure(objM70awaitLongPressOrCancellationrnUCldI);
            pointerInputChange = pointerInputChange3;
            awaitPointerEventScope = awaitPointerEventScope2;
            PointerInputChange pointerInputChange4 = (PointerInputChange) objM70awaitLongPressOrCancellationrnUCldI;
            if (pointerInputChange4 != null) {
                long j2 = pointerInputChange4.position;
                if (Offset.m399getDistanceimpl(Offset.m402minusMKHz9U(pointerInputChange.position, j2)) >= DragGestureDetectorKt.m75pointerSlopE8SPZFQ(awaitPointerEventScope.getViewConfiguration(), pointerInputChange.type)) {
                    z = false;
                }
                if (z) {
                    textDragObserver.mo204onStartk4lQ0M(j2);
                    long j3 = pointerInputChange4.id;
                    Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionGesturesKt$touchSelection$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            PointerInputChange pointerInputChange5 = (PointerInputChange) obj;
                            textDragObserver.mo203onDragk4lQ0M(PointerEventKt.positionChangeInternal(pointerInputChange5, false));
                            pointerInputChange5.consume();
                            return Unit.INSTANCE;
                        }
                    };
                    selectionGesturesKt$touchSelection$1.L$0 = awaitPointerEventScope;
                    selectionGesturesKt$touchSelection$1.L$1 = textDragObserver;
                    selectionGesturesKt$touchSelection$1.L$2 = null;
                    selectionGesturesKt$touchSelection$1.label = 2;
                    objM70awaitLongPressOrCancellationrnUCldI = DragGestureDetectorKt.m72dragjO51t88(awaitPointerEventScope, j3, function1, selectionGesturesKt$touchSelection$1);
                }
            }
            return Unit.INSTANCE;
        } catch (CancellationException e) {
            textDragObserver.onCancel();
            throw e;
        }
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
        PointerInputEventHandler pointerInputEventHandler = new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.selection.SelectionGesturesKt.selectionGestureInput.1

            /* renamed from: androidx.compose.foundation.text.selection.SelectionGesturesKt$selectionGestureInput$1$1, reason: invalid class name and collision with other inner class name */
            final class C00241 extends RestrictedSuspendLambda implements Function2 {
                final /* synthetic */ ClicksCounter $clicksCounter;
                final /* synthetic */ MouseSelectionObserver $mouseSelectionObserver;
                final /* synthetic */ TextDragObserver $textDragObserver;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00241(MouseSelectionObserver mouseSelectionObserver, ClicksCounter clicksCounter, TextDragObserver textDragObserver, Continuation continuation) {
                    super(2, continuation);
                    this.$mouseSelectionObserver = mouseSelectionObserver;
                    this.$clicksCounter = clicksCounter;
                    this.$textDragObserver = textDragObserver;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00241 c00241 = new C00241(this.$mouseSelectionObserver, this.$clicksCounter, this.$textDragObserver, continuation);
                    c00241.L$0 = obj;
                    return c00241;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00241) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:26:0x006e, code lost:
                
                    if (androidx.compose.foundation.text.selection.SelectionGesturesKt.access$mouseSelection(r1, r2, r3, r10, r9) == r0) goto L32;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:31:0x0081, code lost:
                
                    if (androidx.compose.foundation.text.selection.SelectionGesturesKt.access$touchSelection(r1, r2, r10, r9) == r0) goto L32;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    AwaitPointerEventScope awaitPointerEventScope;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        this.L$0 = awaitPointerEventScope;
                        this.label = 1;
                        obj = SelectionGesturesKt.access$awaitDown(awaitPointerEventScope, this);
                        if (obj != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i != 1) {
                        if (i != 2 && i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    PointerEvent pointerEvent = (PointerEvent) obj;
                    if (SelectionGesturesKt.isPrecisePointer(pointerEvent) && (pointerEvent.buttons & 33) != 0) {
                        List list = pointerEvent.changes;
                        int size = list.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            if (!((PointerInputChange) list.get(i2)).isConsumed()) {
                            }
                        }
                        MouseSelectionObserver mouseSelectionObserver = this.$mouseSelectionObserver;
                        ClicksCounter clicksCounter = this.$clicksCounter;
                        this.L$0 = null;
                        this.label = 2;
                    }
                    if (!SelectionGesturesKt.isPrecisePointer(pointerEvent)) {
                        TextDragObserver textDragObserver = this.$textDragObserver;
                        this.L$0 = null;
                        this.label = 3;
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new C00241(mouseSelectionObserver, new ClicksCounter(pointerInputScope.getViewConfiguration()), textDragObserver, null), continuation);
                return objAwaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitEachGesture : Unit.INSTANCE;
            }
        };
        PointerEvent pointerEvent = SuspendingPointerInputFilterKt.EmptyPointerEvent;
        return modifier.then(new SuspendPointerInputElement(mouseSelectionObserver, textDragObserver, null, pointerInputEventHandler, 4, null));
    }

    public static final Modifier updateSelectionTouchMode(Modifier.Companion companion, final Function1 function1) {
        return SuspendingPointerInputFilterKt.pointerInput((Modifier) companion, (Object) 8675309, new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.selection.SelectionGesturesKt.updateSelectionTouchMode.1

            /* renamed from: androidx.compose.foundation.text.selection.SelectionGesturesKt$updateSelectionTouchMode$1$1, reason: invalid class name and collision with other inner class name */
            final class C00251 extends RestrictedSuspendLambda implements Function2 {
                final /* synthetic */ Function1 $updateTouchMode;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00251(Function1 function1, Continuation continuation) {
                    super(2, continuation);
                    this.$updateTouchMode = function1;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00251 c00251 = new C00251(this.$updateTouchMode, continuation);
                    c00251.L$0 = obj;
                    return c00251;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00251) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                    jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
                    	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                    	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                    	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                    */
                /* JADX WARN: Removed duplicated region for block: B:11:0x002d A[RETURN] */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x002b -> B:12:0x002e). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
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
                        r3.mo781invoke(r5)
                        goto L21
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.SelectionGesturesKt.C07191.C00251.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object objAwaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new C00251(function1, null), continuation);
                return objAwaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitPointerEventScope : Unit.INSTANCE;
            }
        });
    }
}
