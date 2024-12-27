package com.android.systemui.common.ui.compose;

import androidx.compose.ui.platform.PlatformTextInputInterceptor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1 implements PlatformTextInputInterceptor {
    public final /* synthetic */ int $selectedUserId;

    public SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1(int i) {
        this.$selectedUserId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.intrinsics.CoroutineSingletons interceptStartInputMethod(final androidx.compose.ui.platform.PlatformTextInputMethodRequest r5, androidx.compose.ui.platform.PlatformTextInputSession r6, kotlin.coroutines.Continuation r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1 r0 = (com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1 r0 = new com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L42
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$modifiedRequest$1 r7 = new com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$modifiedRequest$1
            int r4 = r4.$selectedUserId
            r7.<init>()
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = r6.startInputMethod(r7, r0)
            if (r4 != r1) goto L42
            return r1
        L42:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1.interceptStartInputMethod(androidx.compose.ui.platform.PlatformTextInputMethodRequest, androidx.compose.ui.platform.PlatformTextInputSession, kotlin.coroutines.Continuation):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
