package androidx.compose.material3;

import androidx.compose.ui.platform.AccessibilityManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:32:0x0084 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            if (r1 == 0) goto L16
            if (r1 != r2) goto Le
            kotlin.ResultKt.throwOnFailure(r12)
            goto L85
        Le:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L16:
            kotlin.ResultKt.throwOnFailure(r12)
            androidx.compose.material3.SnackbarData r12 = r11.$currentSnackbarData
            if (r12 == 0) goto L8c
            androidx.compose.material3.SnackbarHostState$SnackbarDataImpl r12 = (androidx.compose.material3.SnackbarHostState.SnackbarDataImpl) r12
            androidx.compose.material3.SnackbarVisuals r12 = r12.visuals
            androidx.compose.material3.SnackbarDuration r12 = r12.getDuration()
            androidx.compose.material3.SnackbarData r1 = r11.$currentSnackbarData
            androidx.compose.material3.SnackbarHostState$SnackbarDataImpl r1 = (androidx.compose.material3.SnackbarHostState.SnackbarDataImpl) r1
            androidx.compose.material3.SnackbarVisuals r1 = r1.visuals
            java.lang.String r1 = r1.getActionLabel()
            if (r1 == 0) goto L33
            r1 = r2
            goto L34
        L33:
            r1 = 0
        L34:
            androidx.compose.ui.platform.AccessibilityManager r3 = r11.$accessibilityManager
            int[] r4 = androidx.compose.material3.SnackbarHostKt.WhenMappings.$EnumSwitchMapping$0
            int r12 = r12.ordinal()
            r12 = r4[r12]
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r6 = 3
            if (r12 == r2) goto L57
            r7 = 2
            if (r12 == r7) goto L54
            if (r12 != r6) goto L4e
            r7 = 4000(0xfa0, double:1.9763E-320)
            goto L58
        L4e:
            kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
            r11.<init>()
            throw r11
        L54:
            r7 = 10000(0x2710, double:4.9407E-320)
            goto L58
        L57:
            r7 = r4
        L58:
            if (r3 != 0) goto L5b
            goto L64
        L5b:
            androidx.compose.ui.platform.AndroidAccessibilityManager r3 = (androidx.compose.ui.platform.AndroidAccessibilityManager) r3
            r9 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r12 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r12 < 0) goto L66
        L64:
            r4 = r7
            goto L7c
        L66:
            if (r1 == 0) goto L69
            r6 = 7
        L69:
            androidx.compose.ui.platform.Api29Impl r12 = androidx.compose.ui.platform.Api29Impl.INSTANCE
            android.view.accessibility.AccessibilityManager r1 = r3.accessibilityManager
            int r3 = (int) r7
            r12.getClass()
            int r12 = r1.getRecommendedTimeoutMillis(r3, r6)
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r12 != r1) goto L7b
            goto L7c
        L7b:
            long r4 = (long) r12
        L7c:
            r11.label = r2
            java.lang.Object r12 = kotlinx.coroutines.DelayKt.delay(r4, r11)
            if (r12 != r0) goto L85
            return r0
        L85:
            androidx.compose.material3.SnackbarData r11 = r11.$currentSnackbarData
            androidx.compose.material3.SnackbarHostState$SnackbarDataImpl r11 = (androidx.compose.material3.SnackbarHostState.SnackbarDataImpl) r11
            r11.dismiss()
        L8c:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SnackbarHostKt$SnackbarHost$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
