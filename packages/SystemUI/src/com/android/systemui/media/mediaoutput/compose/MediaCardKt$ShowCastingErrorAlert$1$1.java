package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.material3.SnackbarHostState;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaCardKt$ShowCastingErrorAlert$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ RouteDevice $device;
    final /* synthetic */ Function1 $onAction;
    final /* synthetic */ Function0 $onDismiss;
    final /* synthetic */ SessionController $sessionController;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ String $text;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$ShowCastingErrorAlert$1$1(RouteDevice routeDevice, SnackbarHostState snackbarHostState, String str, Context context, SessionController sessionController, Function0 function0, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$device = routeDevice;
        this.$snackbarHostState = snackbarHostState;
        this.$text = str;
        this.$context = context;
        this.$sessionController = sessionController;
        this.$onDismiss = function0;
        this.$onAction = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$ShowCastingErrorAlert$1$1(this.$device, this.$snackbarHostState, this.$text, this.$context, this.$sessionController, this.$onDismiss, this.$onAction, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$ShowCastingErrorAlert$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0055 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L19
            if (r1 != r2) goto L11
            java.lang.Object r0 = r6.L$0
            android.content.Intent r0 = (android.content.Intent) r0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L58
        L11:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L19:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.media.mediaoutput.entity.RouteDevice r7 = r6.$device
            android.content.Intent r7 = r7.getTargetIntent()
            if (r7 == 0) goto L44
            android.content.Context r1 = r6.$context
            com.android.systemui.media.mediaoutput.controller.media.SessionController r3 = r6.$sessionController
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            java.lang.String r3 = r3.getPackageName()
            java.lang.String r1 = com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt.getAppLabel(r1, r3)
            if (r1 == 0) goto L44
            android.content.Context r3 = r6.$context
            r4 = 2131951996(0x7f13017c, float:1.9540422E38)
            java.lang.Object[] r1 = new java.lang.Object[]{r1}
            java.lang.String r1 = r3.getString(r4, r1)
            goto L45
        L44:
            r1 = 0
        L45:
            androidx.compose.material3.SnackbarHostState r3 = r6.$snackbarHostState
            java.lang.String r4 = r6.$text
            androidx.compose.material3.SnackbarDuration r5 = androidx.compose.material3.SnackbarDuration.Short
            r6.L$0 = r7
            r6.label = r2
            java.lang.Object r1 = androidx.compose.material3.SnackbarHostState.showSnackbar$default(r3, r4, r1, r5, r6)
            if (r1 != r0) goto L56
            return r0
        L56:
            r0 = r7
            r7 = r1
        L58:
            kotlin.jvm.functions.Function0 r1 = r6.$onDismiss
            kotlin.jvm.functions.Function1 r6 = r6.$onAction
            androidx.compose.material3.SnackbarResult r7 = (androidx.compose.material3.SnackbarResult) r7
            androidx.compose.material3.SnackbarResult r2 = androidx.compose.material3.SnackbarResult.ActionPerformed
            if (r7 != r2) goto L67
            if (r0 == 0) goto L67
            r6.mo779invoke(r0)
        L67:
            r1.invoke()
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ShowCastingErrorAlert$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
