package androidx.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.StartOffset;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes.dex */
final class MarqueeModifierNode$runAnimation$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MarqueeModifierNode this$0;

    /* renamed from: androidx.compose.foundation.MarqueeModifierNode$runAnimation$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ MarqueeModifierNode this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MarqueeModifierNode marqueeModifierNode, Continuation continuation) {
            super(2, continuation);
            this.this$0 = marqueeModifierNode;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Float) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00d7, code lost:
        
            if (r0.snapTo(r1, r19) != r7) goto L35;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            Float f;
            AnimationSpec animationSpec;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
            } catch (Throwable th) {
                Animatable animatable = this.this$0.offset;
                Float f2 = new Float(0.0f);
                this.L$0 = th;
                this.L$1 = null;
                this.label = 4;
                if (animatable.snapTo(f2, this) != coroutineSingletons) {
                    throw th;
                }
            }
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                f = (Float) this.L$0;
                if (f == null) {
                    return Unit.INSTANCE;
                }
                int i2 = this.this$0.iterations;
                float fFloatValue = f.floatValue();
                MarqueeModifierNode marqueeModifierNode = this.this$0;
                int i3 = marqueeModifierNode.initialDelayMillis;
                int i4 = marqueeModifierNode.delayMillis;
                TweenSpec tweenSpec = new TweenSpec((int) Math.ceil(fFloatValue / (Math.abs(DelegatableNodeKt.requireLayoutNode(marqueeModifierNode).density.mo58toPx0680j_4(marqueeModifierNode.velocity)) / 1000.0f)), i4, EasingKt.LinearEasing);
                long jM12constructorimpl$default = StartOffset.m12constructorimpl$default((-i4) + i3);
                AnimationSpec animationSpecM9infiniteRepeatable9IiC70o$default = i2 == Integer.MAX_VALUE ? AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(2, tweenSpec, null, jM12constructorimpl$default) : AnimationSpecKt.m10repeatable91I0pcU$default(i2, tweenSpec, null, jM12constructorimpl$default, 4);
                Animatable animatable2 = this.this$0.offset;
                Float f3 = new Float(0.0f);
                this.L$0 = f;
                this.L$1 = animationSpecM9infiniteRepeatable9IiC70o$default;
                this.label = 1;
                if (animatable2.snapTo(f3, this) != coroutineSingletons) {
                    animationSpec = animationSpecM9infiniteRepeatable9IiC70o$default;
                }
                return coroutineSingletons;
            }
            if (i == 1) {
                AnimationSpec animationSpec2 = (AnimationSpec) this.L$1;
                Float f4 = (Float) this.L$0;
                ResultKt.throwOnFailure(obj);
                animationSpec = animationSpec2;
                f = f4;
            } else {
                if (i != 2) {
                    if (i == 3) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    if (i != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    Throwable th2 = (Throwable) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    throw th2;
                }
                ResultKt.throwOnFailure(obj);
                Animatable animatable3 = this.this$0.offset;
                Float f5 = new Float(0.0f);
                this.label = 3;
            }
            Animatable animatable4 = this.this$0.offset;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 2;
            if (Animatable.animateTo$default(animatable4, f, animationSpec, null, null, this, 12) != coroutineSingletons) {
                Animatable animatable32 = this.this$0.offset;
                Float f52 = new Float(0.0f);
                this.label = 3;
            }
            return coroutineSingletons;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MarqueeModifierNode$runAnimation$2(MarqueeModifierNode marqueeModifierNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = marqueeModifierNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MarqueeModifierNode$runAnimation$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MarqueeModifierNode$runAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final MarqueeModifierNode marqueeModifierNode = this.this$0;
            SafeFlow safeFlowSnapshotFlow = SnapshotStateKt.snapshotFlow(new Function0() { // from class: androidx.compose.foundation.MarqueeModifierNode$runAnimation$2.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    if (marqueeModifierNode.getContentWidth() <= marqueeModifierNode.getContainerWidth()) {
                        return null;
                    }
                    int i2 = ((MarqueeAnimationMode) ((SnapshotMutableStateImpl) marqueeModifierNode.animationMode$delegate).getValue()).value;
                    MarqueeAnimationMode.Companion.getClass();
                    if (i2 != MarqueeAnimationMode.WhileFocused || ((Boolean) ((SnapshotMutableStateImpl) marqueeModifierNode.hasFocus$delegate).getValue()).booleanValue()) {
                        return Float.valueOf(marqueeModifierNode.getContentWidth() + marqueeModifierNode.getSpacingPx());
                    }
                    return null;
                }
            });
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, null);
            this.label = 1;
            if (FlowKt.collectLatest(safeFlowSnapshotFlow, anonymousClass2, this) == coroutineSingletons) {
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
