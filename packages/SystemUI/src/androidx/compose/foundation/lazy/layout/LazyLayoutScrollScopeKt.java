package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.AnimationScope;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public abstract class LazyLayoutScrollScopeKt {
    public static final float BoundDistance;
    public static final float MinimumDistance;
    public static final float TargetDistance;

    /* renamed from: androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt$animateScrollToItem$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        float F$0;
        float F$1;
        float F$2;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LazyLayoutScrollScopeKt.animateScrollToItem(null, 0, 0, 0, null, this);
        }
    }

    static {
        float f = KnoxAiManagerInternal.CONN_MAX_WAIT_TIME;
        Dp.Companion companion = Dp.Companion;
        TargetDistance = f;
        BoundDistance = 1500;
        MinimumDistance = 50;
    }

    public static final boolean access$animateScrollToItem$isOvershot(boolean z, LazyLayoutScrollScope lazyLayoutScrollScope, int i, int i2) {
        if (z) {
            if (lazyLayoutScrollScope.getFirstVisibleItemIndex() > i) {
                return true;
            }
            return lazyLayoutScrollScope.getFirstVisibleItemIndex() == i && lazyLayoutScrollScope.getFirstVisibleItemScrollOffset() > i2;
        }
        if (lazyLayoutScrollScope.getFirstVisibleItemIndex() < i) {
            return true;
        }
        return lazyLayoutScrollScope.getFirstVisibleItemIndex() == i && lazyLayoutScrollScope.getFirstVisibleItemScrollOffset() < i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d4 A[Catch: ItemFoundInScroll -> 0x01bb, TryCatch #5 {ItemFoundInScroll -> 0x01bb, blocks: (B:34:0x00d0, B:36:0x00d4, B:38:0x00da, B:51:0x0104, B:55:0x0133), top: B:105:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x022b  */
    /* JADX WARN: Type inference failed for: r11v0, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Type inference failed for: r12v6, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x018b -> B:18:0x006c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object animateScrollToItem(LazyLayoutScrollScope lazyLayoutScrollScope, int i, int i2, int i3, Density density, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        final LazyLayoutScrollScope lazyLayoutScrollScope2;
        int i4;
        int i5;
        float fMo58toPx0680j_4;
        float fMo58toPx0680j_42;
        float fMo58toPx0680j_43;
        Ref$BooleanRef ref$BooleanRef;
        Ref$ObjectRef ref$ObjectRef;
        final int i6;
        final int i7;
        final float f;
        final Ref$IntRef ref$IntRef;
        float f2;
        AnonymousClass1 anonymousClass12;
        float f3;
        int i8;
        int i9;
        LazyLayoutScrollScope lazyLayoutScrollScope3;
        AnonymousClass1 anonymousClass13;
        AnimationState animationStateCopy$default;
        Float f4;
        boolean z;
        Function1 function1;
        LazyLayoutScrollScope lazyLayoutScrollScope4;
        int i10;
        int i11;
        AnonymousClass1 anonymousClass14;
        float fMax;
        final LazyLayoutScrollScope lazyLayoutScrollScope5;
        final int i12;
        int i13;
        float f5;
        int i14;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i15 = anonymousClass1.label;
            if ((i15 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i15 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i16 = anonymousClass1.label;
        float f6 = 0.0f;
        boolean z2 = true;
        if (i16 == 0) {
            ResultKt.throwOnFailure(obj);
            if (i < 0.0f) {
                InlineClassHelperKt.throwIllegalArgumentException("Index should be non-negative");
            }
            try {
                fMo58toPx0680j_4 = density.mo58toPx0680j_4(TargetDistance);
                fMo58toPx0680j_42 = density.mo58toPx0680j_4(BoundDistance);
                fMo58toPx0680j_43 = density.mo58toPx0680j_4(MinimumDistance);
                ref$BooleanRef = new Ref$BooleanRef();
                ref$BooleanRef.element = true;
                ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = AnimationStateKt.AnimationState$default(0.0f, 0.0f, 30);
            } catch (ItemFoundInScroll e) {
                e = e;
                lazyLayoutScrollScope2 = lazyLayoutScrollScope;
                i4 = i2;
                i5 = i;
            }
            if (isItemVisible(lazyLayoutScrollScope, i)) {
                throw new ItemFoundInScroll(lazyLayoutScrollScope.calculateDistanceTo(i), (AnimationState) ref$ObjectRef.element);
            }
            int i17 = i > lazyLayoutScrollScope.getFirstVisibleItemIndex() ? 1 : 0;
            Ref$IntRef ref$IntRef2 = new Ref$IntRef();
            ref$IntRef2.element = 1;
            i6 = i2;
            i7 = i3;
            f = fMo58toPx0680j_42;
            ref$IntRef = ref$IntRef2;
            f2 = fMo58toPx0680j_4;
            anonymousClass12 = anonymousClass1;
            f3 = fMo58toPx0680j_43;
            i8 = i;
            i9 = i17;
            lazyLayoutScrollScope3 = lazyLayoutScrollScope;
            if (ref$BooleanRef.element) {
            }
            return Unit.INSTANCE;
        }
        if (i16 != 1) {
            if (i16 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i11 = anonymousClass1.I$1;
            i10 = anonymousClass1.I$0;
            lazyLayoutScrollScope4 = (LazyLayoutScrollScope) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            lazyLayoutScrollScope4.snapToItem(i10, i11);
            return Unit.INSTANCE;
        }
        i9 = anonymousClass1.I$3;
        float f7 = anonymousClass1.F$2;
        float f8 = anonymousClass1.F$1;
        f2 = anonymousClass1.F$0;
        int i18 = anonymousClass1.I$2;
        int i19 = anonymousClass1.I$1;
        int i20 = anonymousClass1.I$0;
        Ref$IntRef ref$IntRef3 = (Ref$IntRef) anonymousClass1.L$3;
        Ref$ObjectRef ref$ObjectRef2 = (Ref$ObjectRef) anonymousClass1.L$2;
        Ref$BooleanRef ref$BooleanRef2 = (Ref$BooleanRef) anonymousClass1.L$1;
        LazyLayoutScrollScope lazyLayoutScrollScope6 = (LazyLayoutScrollScope) anonymousClass1.L$0;
        try {
            ResultKt.throwOnFailure(obj);
            f5 = f8;
            lazyLayoutScrollScope2 = lazyLayoutScrollScope6;
            i6 = i19;
            anonymousClass12 = anonymousClass1;
            f3 = f7;
            i14 = 1;
            i13 = i18;
            try {
                ref$BooleanRef = ref$BooleanRef2;
                ref$ObjectRef = ref$ObjectRef2;
                try {
                    ref$IntRef3.element += i14;
                } catch (ItemFoundInScroll e2) {
                    e = e2;
                    anonymousClass13 = anonymousClass12;
                    i5 = i20;
                    i4 = i6;
                }
            } catch (ItemFoundInScroll e3) {
                e = e3;
                i5 = i8;
                anonymousClass14 = anonymousClass12;
                i4 = i6;
                lazyLayoutScrollScope2 = lazyLayoutScrollScope3;
            }
            lazyLayoutScrollScope3 = lazyLayoutScrollScope2;
            i8 = i20;
            ref$IntRef = ref$IntRef3;
            f = f5;
            f6 = 0.0f;
            i7 = i13;
            z2 = true;
        } catch (ItemFoundInScroll e4) {
            e = e4;
            lazyLayoutScrollScope2 = lazyLayoutScrollScope6;
            i4 = i19;
            i5 = i20;
        }
        if (ref$BooleanRef.element && lazyLayoutScrollScope3.getItemCount() > 0) {
            try {
                try {
                    try {
                        int iCalculateDistanceTo = lazyLayoutScrollScope3.calculateDistanceTo(i8) + i6;
                        if (Math.abs(iCalculateDistanceTo) >= f2) {
                            fMax = i9 != 0 ? f2 : -f2;
                        } else {
                            try {
                                fMax = Math.max(Math.abs(iCalculateDistanceTo), f3);
                                if (i9 == 0) {
                                    fMax = -fMax;
                                }
                            } catch (ItemFoundInScroll e5) {
                                e = e5;
                                i5 = i8;
                                anonymousClass13 = anonymousClass12;
                                i4 = i6;
                                lazyLayoutScrollScope2 = lazyLayoutScrollScope3;
                            }
                        }
                        ref$ObjectRef.element = AnimationStateKt.copy$default((AnimationState) ref$ObjectRef.element, f6, f6, 30);
                        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                        AnimationState animationState = (AnimationState) ref$ObjectRef.element;
                        Float f9 = new Float(fMax);
                        boolean z3 = ((Number) ((AnimationState) ref$ObjectRef.element).getVelocity()).floatValue() != f6 ? false : z2;
                        final boolean z4 = i9 == 0 ? z2 : false;
                        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt.animateScrollToItem.4
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
                            /* JADX WARN: Removed duplicated region for block: B:39:0x00de  */
                            /* JADX WARN: Removed duplicated region for block: B:40:0x00e6  */
                            /* JADX WARN: Removed duplicated region for block: B:42:0x00f4  */
                            /* JADX WARN: Removed duplicated region for block: B:43:0x0105  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0028 A[PHI: r2
                              0x0028: PHI (r2v19 float) = (r2v3 float), (r2v20 float) binds: [B:10:0x003c, B:7:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object mo781invoke(Object obj2) {
                                float fFloatValue;
                                float f10;
                                float f11;
                                float fScrollBy;
                                AnimationScope animationScope = (AnimationScope) obj2;
                                if (LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12)) {
                                    if (!LazyLayoutScrollScopeKt.access$animateScrollToItem$isOvershot(z4, lazyLayoutScrollScope5, i12, i6)) {
                                        lazyLayoutScrollScope5.snapToItem(i12, i6);
                                        ref$BooleanRef.element = false;
                                        animationScope.cancelAnimation();
                                    } else if (LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12)) {
                                        throw new ItemFoundInScroll(lazyLayoutScrollScope5.calculateDistanceTo(i12), ref$ObjectRef.element);
                                    }
                                } else if (f > 0.0f) {
                                    fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                    f10 = f;
                                    if (fFloatValue > f10) {
                                        fFloatValue = f10;
                                    }
                                    f11 = fFloatValue - ref$FloatRef.element;
                                    fScrollBy = lazyLayoutScrollScope5.scrollBy(f11);
                                    if (!LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12) && !LazyLayoutScrollScopeKt.access$animateScrollToItem$isOvershot(z4, lazyLayoutScrollScope5, i12, i6)) {
                                        if (f11 != fScrollBy) {
                                            ref$FloatRef.element += f11;
                                            if (z4) {
                                                if (((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() > f) {
                                                    animationScope.cancelAnimation();
                                                }
                                            } else if (((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() < (-f)) {
                                                animationScope.cancelAnimation();
                                            }
                                            if (z4) {
                                                if (ref$IntRef.element >= 2) {
                                                    int lastVisibleItemIndex = i12 - lazyLayoutScrollScope5.getLastVisibleItemIndex();
                                                    int i21 = i7;
                                                    if (lastVisibleItemIndex > i21) {
                                                        lazyLayoutScrollScope5.snapToItem(i12 - i21, 0);
                                                    }
                                                }
                                            } else if (ref$IntRef.element >= 2) {
                                                int firstVisibleItemIndex = lazyLayoutScrollScope5.getFirstVisibleItemIndex();
                                                int i22 = i12;
                                                int i23 = firstVisibleItemIndex - i22;
                                                int i24 = i7;
                                                if (i23 > i24) {
                                                    lazyLayoutScrollScope5.snapToItem(i22 + i24, 0);
                                                }
                                            }
                                            if (!LazyLayoutScrollScopeKt.access$animateScrollToItem$isOvershot(z4, lazyLayoutScrollScope5, i12, i6)) {
                                            }
                                        } else {
                                            animationScope.cancelAnimation();
                                            ref$BooleanRef.element = false;
                                        }
                                    }
                                } else {
                                    fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                    f10 = f;
                                    if (fFloatValue < f10) {
                                    }
                                    f11 = fFloatValue - ref$FloatRef.element;
                                    fScrollBy = lazyLayoutScrollScope5.scrollBy(f11);
                                    if (!LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12)) {
                                        if (f11 != fScrollBy) {
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        anonymousClass12.L$0 = lazyLayoutScrollScope2;
                        anonymousClass12.L$1 = ref$BooleanRef2;
                        anonymousClass12.L$2 = ref$ObjectRef2;
                        anonymousClass12.L$3 = ref$IntRef3;
                        anonymousClass12.I$0 = i5;
                        anonymousClass12.I$1 = i4;
                        anonymousClass12.I$2 = i13;
                        anonymousClass12.F$0 = f2;
                        anonymousClass12.F$1 = f;
                        anonymousClass12.F$2 = f3;
                        anonymousClass12.I$3 = i9;
                        f5 = f;
                        i14 = 1;
                        anonymousClass12.label = 1;
                        if (SuspendAnimationKt.animateTo$default(animationState, f9, null, z3, function12, anonymousClass14, 2) != coroutineSingletons) {
                            i20 = i5;
                            i6 = i4;
                            anonymousClass12 = anonymousClass14;
                            ref$BooleanRef = ref$BooleanRef2;
                            ref$ObjectRef = ref$ObjectRef2;
                            ref$IntRef3.element += i14;
                            lazyLayoutScrollScope3 = lazyLayoutScrollScope2;
                            i8 = i20;
                            ref$IntRef = ref$IntRef3;
                            f = f5;
                            f6 = 0.0f;
                            i7 = i13;
                            z2 = true;
                            if (ref$BooleanRef.element) {
                                int iCalculateDistanceTo2 = lazyLayoutScrollScope3.calculateDistanceTo(i8) + i6;
                                if (Math.abs(iCalculateDistanceTo2) >= f2) {
                                }
                                ref$ObjectRef.element = AnimationStateKt.copy$default((AnimationState) ref$ObjectRef.element, f6, f6, 30);
                                final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
                                AnimationState animationState2 = (AnimationState) ref$ObjectRef.element;
                                Float f92 = new Float(fMax);
                                if (((Number) ((AnimationState) ref$ObjectRef.element).getVelocity()).floatValue() != f6) {
                                }
                                if (i9 == 0) {
                                }
                                lazyLayoutScrollScope5 = lazyLayoutScrollScope3;
                                i12 = i8;
                                final Ref$BooleanRef ref$BooleanRef3 = ref$BooleanRef;
                                final Ref$ObjectRef<AnimationState<Float, AnimationVector1D>> ref$ObjectRef3 = ref$ObjectRef;
                                final float f10 = fMax;
                                Function1 function122 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt.animateScrollToItem.4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
                                    /* JADX WARN: Removed duplicated region for block: B:39:0x00de  */
                                    /* JADX WARN: Removed duplicated region for block: B:40:0x00e6  */
                                    /* JADX WARN: Removed duplicated region for block: B:42:0x00f4  */
                                    /* JADX WARN: Removed duplicated region for block: B:43:0x0105  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0028 A[PHI: r2
                                      0x0028: PHI (r2v19 float) = (r2v3 float), (r2v20 float) binds: [B:10:0x003c, B:7:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object mo781invoke(Object obj2) {
                                        float fFloatValue;
                                        float f102;
                                        float f11;
                                        float fScrollBy;
                                        AnimationScope animationScope = (AnimationScope) obj2;
                                        if (LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12)) {
                                            if (!LazyLayoutScrollScopeKt.access$animateScrollToItem$isOvershot(z4, lazyLayoutScrollScope5, i12, i6)) {
                                                lazyLayoutScrollScope5.snapToItem(i12, i6);
                                                ref$BooleanRef3.element = false;
                                                animationScope.cancelAnimation();
                                            } else if (LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12)) {
                                                throw new ItemFoundInScroll(lazyLayoutScrollScope5.calculateDistanceTo(i12), ref$ObjectRef3.element);
                                            }
                                        } else if (f10 > 0.0f) {
                                            fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                            f102 = f10;
                                            if (fFloatValue > f102) {
                                                fFloatValue = f102;
                                            }
                                            f11 = fFloatValue - ref$FloatRef2.element;
                                            fScrollBy = lazyLayoutScrollScope5.scrollBy(f11);
                                            if (!LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12) && !LazyLayoutScrollScopeKt.access$animateScrollToItem$isOvershot(z4, lazyLayoutScrollScope5, i12, i6)) {
                                                if (f11 != fScrollBy) {
                                                    ref$FloatRef2.element += f11;
                                                    if (z4) {
                                                        if (((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() > f) {
                                                            animationScope.cancelAnimation();
                                                        }
                                                    } else if (((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() < (-f)) {
                                                        animationScope.cancelAnimation();
                                                    }
                                                    if (z4) {
                                                        if (ref$IntRef.element >= 2) {
                                                            int lastVisibleItemIndex = i12 - lazyLayoutScrollScope5.getLastVisibleItemIndex();
                                                            int i21 = i7;
                                                            if (lastVisibleItemIndex > i21) {
                                                                lazyLayoutScrollScope5.snapToItem(i12 - i21, 0);
                                                            }
                                                        }
                                                    } else if (ref$IntRef.element >= 2) {
                                                        int firstVisibleItemIndex = lazyLayoutScrollScope5.getFirstVisibleItemIndex();
                                                        int i22 = i12;
                                                        int i23 = firstVisibleItemIndex - i22;
                                                        int i24 = i7;
                                                        if (i23 > i24) {
                                                            lazyLayoutScrollScope5.snapToItem(i22 + i24, 0);
                                                        }
                                                    }
                                                    if (!LazyLayoutScrollScopeKt.access$animateScrollToItem$isOvershot(z4, lazyLayoutScrollScope5, i12, i6)) {
                                                    }
                                                } else {
                                                    animationScope.cancelAnimation();
                                                    ref$BooleanRef3.element = false;
                                                }
                                            }
                                        } else {
                                            fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                            f102 = f10;
                                            if (fFloatValue < f102) {
                                            }
                                            f11 = fFloatValue - ref$FloatRef2.element;
                                            fScrollBy = lazyLayoutScrollScope5.scrollBy(f11);
                                            if (!LazyLayoutScrollScopeKt.isItemVisible(lazyLayoutScrollScope5, i12)) {
                                                if (f11 != fScrollBy) {
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                lazyLayoutScrollScope2 = lazyLayoutScrollScope5;
                                i5 = i12;
                                ref$BooleanRef2 = ref$BooleanRef3;
                                float f11 = f;
                                ref$IntRef3 = ref$IntRef;
                                i13 = i7;
                                i4 = i6;
                                ref$ObjectRef2 = ref$ObjectRef3;
                                anonymousClass12.L$0 = lazyLayoutScrollScope2;
                                anonymousClass12.L$1 = ref$BooleanRef2;
                                anonymousClass12.L$2 = ref$ObjectRef2;
                                anonymousClass12.L$3 = ref$IntRef3;
                                anonymousClass12.I$0 = i5;
                                anonymousClass12.I$1 = i4;
                                anonymousClass12.I$2 = i13;
                                anonymousClass12.F$0 = f2;
                                anonymousClass12.F$1 = f11;
                                anonymousClass12.F$2 = f3;
                                anonymousClass12.I$3 = i9;
                                f5 = f11;
                                i14 = 1;
                                anonymousClass12.label = 1;
                                anonymousClass14 = anonymousClass12;
                                if (SuspendAnimationKt.animateTo$default(animationState2, f92, null, z3, function122, anonymousClass14, 2) != coroutineSingletons) {
                                }
                            }
                        }
                    } catch (ItemFoundInScroll e6) {
                        e = e6;
                        anonymousClass13 = anonymousClass14;
                        animationStateCopy$default = AnimationStateKt.copy$default(e.getPreviousAnimation(), 0.0f, 0.0f, 30);
                        final float itemOffset = e.getItemOffset() + i4;
                        final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
                        f4 = new Float(itemOffset);
                        if (((Number) animationStateCopy$default.getVelocity()).floatValue() != 0.0f) {
                        }
                        function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt.animateScrollToItem.6
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x001e A[PHI: r0
                              0x001e: PHI (r0v10 float) = (r0v6 float), (r0v15 float) binds: [B:11:0x0036, B:5:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object mo781invoke(Object obj2) {
                                float fFloatValue;
                                AnimationScope animationScope = (AnimationScope) obj2;
                                float f12 = itemOffset;
                                float f13 = 0.0f;
                                if (f12 > 0.0f) {
                                    fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                    f13 = itemOffset;
                                    if (fFloatValue <= f13) {
                                        f13 = fFloatValue;
                                    }
                                } else if (f12 < 0.0f) {
                                    fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                    f13 = itemOffset;
                                    if (fFloatValue >= f13) {
                                    }
                                }
                                float f14 = f13 - ref$FloatRef3.element;
                                if (f14 != lazyLayoutScrollScope2.scrollBy(f14) || f13 != ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue()) {
                                    animationScope.cancelAnimation();
                                }
                                ref$FloatRef3.element += f14;
                                return Unit.INSTANCE;
                            }
                        };
                        anonymousClass13.L$0 = lazyLayoutScrollScope2;
                        anonymousClass13.L$1 = null;
                        anonymousClass13.L$2 = null;
                        anonymousClass13.L$3 = null;
                        anonymousClass13.I$0 = i5;
                        anonymousClass13.I$1 = i4;
                        anonymousClass13.label = 2;
                        if (SuspendAnimationKt.animateTo$default(animationStateCopy$default, f4, null, !z, function1, anonymousClass13, 2) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    anonymousClass14 = anonymousClass12;
                } catch (ItemFoundInScroll e7) {
                    e = e7;
                    anonymousClass14 = anonymousClass12;
                }
                lazyLayoutScrollScope2 = lazyLayoutScrollScope5;
                i5 = i12;
                ref$BooleanRef2 = ref$BooleanRef3;
                float f112 = f;
                ref$IntRef3 = ref$IntRef;
                i13 = i7;
                i4 = i6;
                ref$ObjectRef2 = ref$ObjectRef3;
            } catch (ItemFoundInScroll e8) {
                e = e8;
                anonymousClass14 = anonymousClass12;
                lazyLayoutScrollScope2 = lazyLayoutScrollScope5;
                i5 = i12;
                i4 = i6;
            }
            lazyLayoutScrollScope5 = lazyLayoutScrollScope3;
            i12 = i8;
            final Ref$BooleanRef ref$BooleanRef32 = ref$BooleanRef;
            final Ref$ObjectRef<AnimationState<Float, AnimationVector1D>> ref$ObjectRef32 = ref$ObjectRef;
            final float f102 = fMax;
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
        anonymousClass13 = anonymousClass1;
        animationStateCopy$default = AnimationStateKt.copy$default(e.getPreviousAnimation(), 0.0f, 0.0f, 30);
        final float itemOffset2 = e.getItemOffset() + i4;
        final Ref$FloatRef ref$FloatRef32 = new Ref$FloatRef();
        f4 = new Float(itemOffset2);
        z = ((Number) animationStateCopy$default.getVelocity()).floatValue() != 0.0f;
        function1 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt.animateScrollToItem.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x001e A[PHI: r0
              0x001e: PHI (r0v10 float) = (r0v6 float), (r0v15 float) binds: [B:11:0x0036, B:5:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj2) {
                float fFloatValue;
                AnimationScope animationScope = (AnimationScope) obj2;
                float f12 = itemOffset2;
                float f13 = 0.0f;
                if (f12 > 0.0f) {
                    fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                    f13 = itemOffset2;
                    if (fFloatValue <= f13) {
                        f13 = fFloatValue;
                    }
                } else if (f12 < 0.0f) {
                    fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                    f13 = itemOffset2;
                    if (fFloatValue >= f13) {
                    }
                }
                float f14 = f13 - ref$FloatRef32.element;
                if (f14 != lazyLayoutScrollScope2.scrollBy(f14) || f13 != ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue()) {
                    animationScope.cancelAnimation();
                }
                ref$FloatRef32.element += f14;
                return Unit.INSTANCE;
            }
        };
        anonymousClass13.L$0 = lazyLayoutScrollScope2;
        anonymousClass13.L$1 = null;
        anonymousClass13.L$2 = null;
        anonymousClass13.L$3 = null;
        anonymousClass13.I$0 = i5;
        anonymousClass13.I$1 = i4;
        anonymousClass13.label = 2;
        if (SuspendAnimationKt.animateTo$default(animationStateCopy$default, f4, null, !z, function1, anonymousClass13, 2) != coroutineSingletons) {
            lazyLayoutScrollScope4 = lazyLayoutScrollScope2;
            i10 = i5;
            i11 = i4;
            lazyLayoutScrollScope4.snapToItem(i10, i11);
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }

    public static final boolean isItemVisible(LazyLayoutScrollScope lazyLayoutScrollScope, int i) {
        return i <= lazyLayoutScrollScope.getLastVisibleItemIndex() && lazyLayoutScrollScope.getFirstVisibleItemIndex() <= i;
    }
}
