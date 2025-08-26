package androidx.compose.foundation;

import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.PressGestureScopeImpl;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* loaded from: classes.dex */
final class AbstractClickableNode$handlePressInteraction$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ long $offset;
    final /* synthetic */ PressGestureScope $this_handlePressInteraction;
    private /* synthetic */ Object L$0;
    boolean Z$0;
    int label;
    final /* synthetic */ AbstractClickableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClickableNode$handlePressInteraction$2$1(PressGestureScope pressGestureScope, long j, MutableInteractionSource mutableInteractionSource, AbstractClickableNode abstractClickableNode, Continuation continuation) {
        super(2, continuation);
        this.$this_handlePressInteraction = pressGestureScope;
        this.$offset = j;
        this.$interactionSource = mutableInteractionSource;
        this.this$0 = abstractClickableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AbstractClickableNode$handlePressInteraction$2$1 abstractClickableNode$handlePressInteraction$2$1 = new AbstractClickableNode$handlePressInteraction$2$1(this.$this_handlePressInteraction, this.$offset, this.$interactionSource, this.this$0, continuation);
        abstractClickableNode$handlePressInteraction$2$1.L$0 = obj;
        return abstractClickableNode$handlePressInteraction$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AbstractClickableNode$handlePressInteraction$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x009f, code lost:
    
        if (r15.emit(r1, r14) != r0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bf, code lost:
    
        if (r2.emit(r15, r14) == r0) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Job jobLaunch$default;
        boolean z;
        PressInteraction$Release pressInteraction$Release;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            jobLaunch$default = BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AbstractClickableNode$handlePressInteraction$2$1$delayJob$1(this.this$0, this.$offset, this.$interactionSource, null), 3);
            PressGestureScope pressGestureScope = this.$this_handlePressInteraction;
            this.L$0 = jobLaunch$default;
            this.label = 1;
            obj = ((PressGestureScopeImpl) pressGestureScope).tryAwaitRelease(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            jobLaunch$default = (Job) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i == 2) {
                z = this.Z$0;
                ResultKt.throwOnFailure(obj);
                if (z) {
                    PressInteraction$Press pressInteraction$Press = new PressInteraction$Press(this.$offset, null);
                    pressInteraction$Release = new PressInteraction$Release(pressInteraction$Press);
                    MutableInteractionSource mutableInteractionSource = this.$interactionSource;
                    this.L$0 = pressInteraction$Release;
                    this.label = 3;
                    if (mutableInteractionSource.emit(pressInteraction$Press, this) != coroutineSingletons) {
                        MutableInteractionSource mutableInteractionSource2 = this.$interactionSource;
                        this.L$0 = null;
                        this.label = 4;
                    }
                    return coroutineSingletons;
                }
                this.this$0.pressInteraction = null;
                return Unit.INSTANCE;
            }
            if (i != 3) {
                if (i != 4 && i != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.pressInteraction = null;
                return Unit.INSTANCE;
            }
            pressInteraction$Release = (PressInteraction$Release) this.L$0;
            ResultKt.throwOnFailure(obj);
            MutableInteractionSource mutableInteractionSource22 = this.$interactionSource;
            this.L$0 = null;
            this.label = 4;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        if (!jobLaunch$default.isActive()) {
            PressInteraction$Press pressInteraction$Press2 = this.this$0.pressInteraction;
            if (pressInteraction$Press2 != null) {
                MutableInteractionSource mutableInteractionSource3 = this.$interactionSource;
                Interaction pressInteraction$Release2 = zBooleanValue ? new PressInteraction$Release(pressInteraction$Press2) : new PressInteraction$Cancel(pressInteraction$Press2);
                this.L$0 = null;
                this.label = 5;
            }
            this.this$0.pressInteraction = null;
            return Unit.INSTANCE;
        }
        this.L$0 = null;
        this.Z$0 = zBooleanValue;
        this.label = 2;
        if (JobKt.cancelAndJoin(jobLaunch$default, this) != coroutineSingletons) {
            z = zBooleanValue;
            if (z) {
            }
            this.this$0.pressInteraction = null;
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }
}
