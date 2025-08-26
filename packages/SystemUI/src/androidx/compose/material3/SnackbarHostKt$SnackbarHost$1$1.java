package androidx.compose.material3;

import androidx.compose.material3.SnackbarHostKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.ui.platform.AccessibilityManager;
import androidx.compose.ui.platform.AndroidAccessibilityManager;
import androidx.compose.ui.platform.Api29Impl;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes.dex */
final class SnackbarHostKt$SnackbarHost$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AccessibilityManager $accessibilityManager;
    final /* synthetic */ SnackbarData $currentSnackbarData;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnackbarHostKt$SnackbarHost$1$1(SnackbarData snackbarData, AccessibilityManager accessibilityManager, Continuation continuation) {
        super(2, continuation);
        this.$currentSnackbarData = snackbarData;
        this.$accessibilityManager = accessibilityManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SnackbarHostKt$SnackbarHost$1$1(this.$currentSnackbarData, this.$accessibilityManager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SnackbarHostKt$SnackbarHost$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0084 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        long j;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SnackbarData snackbarData = this.$currentSnackbarData;
            if (snackbarData != null) {
                SnackbarDuration duration = ((SnackbarHostState.SnackbarDataImpl) snackbarData).visuals.getDuration();
                boolean z = ((SnackbarHostState.SnackbarDataImpl) this.$currentSnackbarData).visuals.getActionLabel() != null;
                AccessibilityManager accessibilityManager = this.$accessibilityManager;
                int i2 = SnackbarHostKt.WhenMappings.$EnumSwitchMapping$0[duration.ordinal()];
                long j2 = Long.MAX_VALUE;
                if (i2 == 1) {
                    j = Long.MAX_VALUE;
                } else if (i2 == 2) {
                    j = 10000;
                } else {
                    if (i2 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    j = 4000;
                }
                if (accessibilityManager == null) {
                    j2 = j;
                    this.label = 1;
                    if (DelayKt.delay(j2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    AndroidAccessibilityManager androidAccessibilityManager = (AndroidAccessibilityManager) accessibilityManager;
                    if (j < 2147483647L) {
                        int i3 = z ? 7 : 3;
                        Api29Impl.INSTANCE.getClass();
                        int recommendedTimeoutMillis = androidAccessibilityManager.accessibilityManager.getRecommendedTimeoutMillis((int) j, i3);
                        if (recommendedTimeoutMillis != Integer.MAX_VALUE) {
                            j2 = recommendedTimeoutMillis;
                        }
                    }
                    this.label = 1;
                    if (DelayKt.delay(j2, this) == coroutineSingletons) {
                    }
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((SnackbarHostState.SnackbarDataImpl) this.$currentSnackbarData).dismiss();
        return Unit.INSTANCE;
    }
}
