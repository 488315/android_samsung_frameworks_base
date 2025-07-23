package androidx.compose.foundation.text;

import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ TextDragObserver $observer;
    final /* synthetic */ PointerInputScope $this_detectDownAndDragGesturesWithObserver;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.foundation.text.LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ TextDragObserver $observer;
        final /* synthetic */ PointerInputScope $this_detectDownAndDragGesturesWithObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PointerInputScope pointerInputScope, TextDragObserver textDragObserver, Continuation continuation) {
            super(2, continuation);
            this.$this_detectDownAndDragGesturesWithObserver = pointerInputScope;
            this.$observer = textDragObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$this_detectDownAndDragGesturesWithObserver, this.$observer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PointerInputScope pointerInputScope = this.$this_detectDownAndDragGesturesWithObserver;
                TextDragObserver textDragObserver = this.$observer;
                this.label = 1;
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new LongPressTextDragObserverKt$detectPreDragGesturesWithObserver$2(textDragObserver, null), this);
                if (awaitEachGesture != obj2) {
                    awaitEachGesture = Unit.INSTANCE;
                }
                if (awaitEachGesture == obj2) {
                    return obj2;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.foundation.text.LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ TextDragObserver $observer;
        final /* synthetic */ PointerInputScope $this_detectDownAndDragGesturesWithObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PointerInputScope pointerInputScope, TextDragObserver textDragObserver, Continuation continuation) {
            super(2, continuation);
            this.$this_detectDownAndDragGesturesWithObserver = pointerInputScope;
            this.$observer = textDragObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$this_detectDownAndDragGesturesWithObserver, this.$observer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PointerInputScope pointerInputScope = this.$this_detectDownAndDragGesturesWithObserver;
                final TextDragObserver textDragObserver = this.$observer;
                this.label = 1;
                Object detectDragGestures = DragGestureDetectorKt.detectDragGestures(pointerInputScope, new Function1() { // from class: androidx.compose.foundation.text.LongPressTextDragObserverKt$detectDragGesturesWithObserver$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj3) {
                        TextDragObserver.this.mo203onStartk4lQ0M(((Offset) obj3).packedValue);
                        return Unit.INSTANCE;
                    }
                }, new Function0() { // from class: androidx.compose.foundation.text.LongPressTextDragObserverKt$detectDragGesturesWithObserver$3
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        TextDragObserver.this.onStop();
                        return Unit.INSTANCE;
                    }
                }, new Function0() { // from class: androidx.compose.foundation.text.LongPressTextDragObserverKt$detectDragGesturesWithObserver$4
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        TextDragObserver.this.onCancel();
                        return Unit.INSTANCE;
                    }
                }, new Function2() { // from class: androidx.compose.foundation.text.LongPressTextDragObserverKt$detectDragGesturesWithObserver$5
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        TextDragObserver.this.mo202onDragk4lQ0M(((Offset) obj4).packedValue);
                        return Unit.INSTANCE;
                    }
                }, this);
                if (detectDragGestures != obj2) {
                    detectDragGestures = Unit.INSTANCE;
                }
                if (detectDragGestures == obj2) {
                    return obj2;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2(PointerInputScope pointerInputScope, TextDragObserver textDragObserver, Continuation continuation) {
        super(2, continuation);
        this.$this_detectDownAndDragGesturesWithObserver = pointerInputScope;
        this.$observer = textDragObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2 longPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2 = new LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2(this.$this_detectDownAndDragGesturesWithObserver, this.$observer, continuation);
        longPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2.L$0 = obj;
        return longPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LongPressTextDragObserverKt$detectDownAndDragGesturesWithObserver$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineStart coroutineStart = CoroutineStart.UNDISPATCHED;
        BuildersKt.launch$default(coroutineScope, null, coroutineStart, new AnonymousClass1(this.$this_detectDownAndDragGesturesWithObserver, this.$observer, null), 1);
        return BuildersKt.launch$default(coroutineScope, null, coroutineStart, new AnonymousClass2(this.$this_detectDownAndDragGesturesWithObserver, this.$observer, null), 1);
    }
}
