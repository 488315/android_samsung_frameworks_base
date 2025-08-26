package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.platform.ViewConfiguration;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
    final /* synthetic */ Function0 $onClick;
    final /* synthetic */ ViewConfiguration $viewConfiguration;
    long J$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1(ViewConfiguration viewConfiguration, Function0 function0, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$viewConfiguration = viewConfiguration;
        this.$onClick = function0;
        this.$isPressed$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1(this.$viewConfiguration, this.$onClick, this.$isPressed$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r4, r7) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r3, r7) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x004d -> B:19:0x0050). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        long jMax;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long longPressTimeoutMillis = this.$viewConfiguration.getLongPressTimeoutMillis();
            this.label = 1;
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            jMax = this.J$0;
            ResultKt.throwOnFailure(obj);
            jMax = Math.max((long) (jMax * 0.9f), 100L);
            if (((Boolean) this.$isPressed$delegate.getValue()).booleanValue()) {
                return Unit.INSTANCE;
            }
            this.$onClick.invoke();
            this.J$0 = jMax;
            this.label = 2;
        }
        jMax = 200;
        if (((Boolean) this.$isPressed$delegate.getValue()).booleanValue()) {
        }
    }
}
