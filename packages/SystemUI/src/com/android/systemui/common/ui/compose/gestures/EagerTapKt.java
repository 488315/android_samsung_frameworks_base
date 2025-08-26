package com.android.systemui.common.ui.compose.gestures;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
public abstract class EagerTapKt {

    /* renamed from: com.android.systemui.common.ui.compose.gestures.EagerTapKt$detectEagerTapGestures$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $doubleTapEnabled;
        final /* synthetic */ Function1 $onDoubleTap;
        final /* synthetic */ Function0 $onTap;
        final /* synthetic */ PointerInputScope $this_detectEagerTapGestures;
        int label;

        /* renamed from: com.android.systemui.common.ui.compose.gestures.EagerTapKt$detectEagerTapGestures$2$1, reason: invalid class name */
        final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ Function0 $doubleTapEnabled;
            final /* synthetic */ Function1 $onDoubleTap;
            final /* synthetic */ Function0 $onTap;
            private /* synthetic */ Object L$0;
            boolean Z$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function0 function0, Function0 function02, Function1 function1, Continuation continuation) {
                super(2, continuation);
                this.$doubleTapEnabled = function0;
                this.$onTap = function02;
                this.$onDoubleTap = function1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$doubleTapEnabled, this.$onTap, this.$onDoubleTap, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:32:0x00ab, code lost:
            
                if (r10 == r0) goto L33;
             */
            /* JADX WARN: Removed duplicated region for block: B:23:0x0078  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x009f  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                AwaitPointerEventScope awaitPointerEventScope;
                boolean z;
                AwaitPointerEventScope awaitPointerEventScope2;
                PointerInputChange pointerInputChange;
                AwaitPointerEventScope awaitPointerEventScope3;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    this.L$0 = awaitPointerEventScope;
                    this.label = 1;
                    obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 3);
                    if (obj != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
                            if (pointerInputChange2 != null) {
                                pointerInputChange2.consume();
                                this.$onDoubleTap.mo781invoke(Offset.m395boximpl(pointerInputChange2.position));
                            }
                            return Unit.INSTANCE;
                        }
                        awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (((PointerInputChange) obj) != null) {
                            this.L$0 = null;
                            this.label = 4;
                            Function3 function3 = TapGestureDetectorKt.NoPressGesture;
                            obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope3, PointerEventPass.Main, this);
                        }
                        return Unit.INSTANCE;
                    }
                    z = this.Z$0;
                    awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    pointerInputChange = (PointerInputChange) obj;
                    if (pointerInputChange != null) {
                        pointerInputChange.consume();
                        this.$onTap.invoke();
                        if (z) {
                            long doubleTapTimeoutMillis = awaitPointerEventScope2.getViewConfiguration().getDoubleTapTimeoutMillis();
                            EagerTapKt$detectEagerTapGestures$2$1$secondDown$1 eagerTapKt$detectEagerTapGestures$2$1$secondDown$1 = new EagerTapKt$detectEagerTapGestures$2$1$secondDown$1(pointerInputChange, null);
                            this.L$0 = awaitPointerEventScope2;
                            this.label = 3;
                            obj = awaitPointerEventScope2.withTimeoutOrNull(doubleTapTimeoutMillis, eagerTapKt$detectEagerTapGestures$2$1$secondDown$1, this);
                            if (obj != coroutineSingletons) {
                                awaitPointerEventScope3 = awaitPointerEventScope2;
                                if (((PointerInputChange) obj) != null) {
                                }
                            }
                            return coroutineSingletons;
                        }
                    }
                    return Unit.INSTANCE;
                }
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                ((PointerInputChange) obj).consume();
                boolean zBooleanValue = ((Boolean) this.$doubleTapEnabled.invoke()).booleanValue();
                this.L$0 = awaitPointerEventScope;
                this.Z$0 = zBooleanValue;
                this.label = 2;
                Function3 function32 = TapGestureDetectorKt.NoPressGesture;
                Object objWaitForUpOrCancellation = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, PointerEventPass.Main, this);
                if (objWaitForUpOrCancellation != coroutineSingletons) {
                    AwaitPointerEventScope awaitPointerEventScope4 = awaitPointerEventScope;
                    z = zBooleanValue;
                    obj = objWaitForUpOrCancellation;
                    awaitPointerEventScope2 = awaitPointerEventScope4;
                    pointerInputChange = (PointerInputChange) obj;
                    if (pointerInputChange != null) {
                    }
                    return Unit.INSTANCE;
                }
                return coroutineSingletons;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PointerInputScope pointerInputScope, Function0 function0, Function0 function02, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$this_detectEagerTapGestures = pointerInputScope;
            this.$doubleTapEnabled = function0;
            this.$onTap = function02;
            this.$onDoubleTap = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$this_detectEagerTapGestures, this.$doubleTapEnabled, this.$onTap, this.$onDoubleTap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PointerInputScope pointerInputScope = this.$this_detectEagerTapGestures;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$doubleTapEnabled, this.$onTap, this.$onDoubleTap, null);
                this.label = 1;
                if (ForEachGestureKt.awaitEachGesture(pointerInputScope, anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
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

    public static final Object detectEagerTapGestures(PointerInputScope pointerInputScope, MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0 mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0, MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1 mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1, MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2 mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(pointerInputScope, mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0, mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2, mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }
}
