package androidx.compose.material.ripple;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
public final class RippleAnimation {
    public final Animatable animatedAlpha;
    public final Animatable animatedCenterPercent;
    public final Animatable animatedRadiusPercent;
    public final boolean bounded;
    public final MutableState finishRequested$delegate;
    public final CompletableDeferredImpl finishSignalDeferred;
    public final MutableState finishedFadingIn$delegate;
    public Offset origin;
    public final float radius;
    public Float startRadius;
    public Offset targetCenter;

    /* renamed from: androidx.compose.material.ripple.RippleAnimation$animate$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RippleAnimation.this.animate(this);
        }
    }

    public /* synthetic */ RippleAnimation(Offset offset, float f, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(offset, f, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0089, code lost:
    
        if (r7 != r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object animate(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Object objCoroutineScope;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            Object objCoroutineScope2 = CoroutineScopeKt.coroutineScope(new RippleAnimation$fadeIn$2(this, null), anonymousClass1);
            if (objCoroutineScope2 != obj2) {
                objCoroutineScope2 = Unit.INSTANCE;
            }
            if (objCoroutineScope2 != obj2) {
            }
            return obj2;
        }
        if (i2 == 1) {
            this = (RippleAnimation) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            this = (RippleAnimation) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = null;
            anonymousClass1.label = 3;
            this.getClass();
            objCoroutineScope = CoroutineScopeKt.coroutineScope(new RippleAnimation$fadeOut$2(this, null), anonymousClass1);
            if (objCoroutineScope != obj2) {
                objCoroutineScope = Unit.INSTANCE;
            }
        }
        ((SnapshotMutableStateImpl) this.finishedFadingIn$delegate).setValue(Boolean.TRUE);
        anonymousClass1.L$0 = this;
        anonymousClass1.label = 2;
        if (this.finishSignalDeferred.awaitInternal(anonymousClass1) != obj2) {
            anonymousClass1.L$0 = null;
            anonymousClass1.label = 3;
            this.getClass();
            objCoroutineScope = CoroutineScopeKt.coroutineScope(new RippleAnimation$fadeOut$2(this, null), anonymousClass1);
            if (objCoroutineScope != obj2) {
            }
        }
        return obj2;
    }

    private RippleAnimation(Offset offset, float f, boolean z) {
        this.origin = offset;
        this.radius = f;
        this.bounded = z;
        this.animatedAlpha = AnimatableKt.Animatable(0.0f, 0.01f);
        this.animatedRadiusPercent = AnimatableKt.Animatable(0.0f, 0.01f);
        this.animatedCenterPercent = AnimatableKt.Animatable(0.0f, 0.01f);
        this.finishSignalDeferred = new CompletableDeferredImpl(null);
        Boolean bool = Boolean.FALSE;
        this.finishedFadingIn$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.finishRequested$delegate = SnapshotStateKt.mutableStateOf$default(bool);
    }
}
