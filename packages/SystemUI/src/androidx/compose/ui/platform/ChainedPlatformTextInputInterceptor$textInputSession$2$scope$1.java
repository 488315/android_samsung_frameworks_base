package androidx.compose.ui.platform;

import android.view.View;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1 implements PlatformTextInputSessionScope {
    public final /* synthetic */ PlatformTextInputSessionScope $$delegate_0;
    public final /* synthetic */ AtomicReference $inputMethodMutex;
    public final /* synthetic */ PlatformTextInputSessionScope $parentSession;
    public final /* synthetic */ ChainedPlatformTextInputInterceptor this$0;

    public ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1(PlatformTextInputSessionScope platformTextInputSessionScope, AtomicReference atomicReference, ChainedPlatformTextInputInterceptor chainedPlatformTextInputInterceptor) {
        this.$parentSession = platformTextInputSessionScope;
        this.$inputMethodMutex = atomicReference;
        this.this$0 = chainedPlatformTextInputInterceptor;
        this.$$delegate_0 = platformTextInputSessionScope;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public final CoroutineContext getCoroutineContext() {
        return this.$$delegate_0.getCoroutineContext();
    }

    @Override // androidx.compose.ui.platform.PlatformTextInputSession
    public final View getView() {
        return this.$$delegate_0.getView();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // androidx.compose.ui.platform.PlatformTextInputSession
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.intrinsics.CoroutineSingletons startInputMethod(androidx.compose.ui.platform.PlatformTextInputMethodRequest r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$1 r0 = (androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$1 r0 = new androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$1
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2b:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L49
        L2f:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.concurrent.atomic.AtomicReference r9 = r7.$inputMethodMutex
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2 r2 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2
                static {
                    /*
                        androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2 r0 = new androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2) androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2.INSTANCE androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ java.lang.Object mo779invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$2.mo779invoke(java.lang.Object):java.lang.Object");
                }
            }
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3 r4 = new androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3
            r5 = 0
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r6 = r7.this$0
            androidx.compose.ui.platform.PlatformTextInputSessionScope r7 = r7.$parentSession
            r4.<init>(r6, r8, r7, r5)
            r0.label = r3
            java.lang.Object r7 = androidx.compose.ui.SessionMutex.m354withSessionCancellingPreviousimpl(r9, r2, r4, r0)
            if (r7 != r1) goto L49
            return r1
        L49:
            kotlin.KotlinNothingValueException r7 = new kotlin.KotlinNothingValueException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1.startInputMethod(androidx.compose.ui.platform.PlatformTextInputMethodRequest, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
