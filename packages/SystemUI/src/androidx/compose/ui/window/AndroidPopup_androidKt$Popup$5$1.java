package androidx.compose.ui.window;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidPopup_androidKt$Popup$5$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PopupLayout $popupLayout;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidPopup_androidKt$Popup$5$1(PopupLayout popupLayout, Continuation continuation) {
        super(2, continuation);
        this.$popupLayout = popupLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AndroidPopup_androidKt$Popup$5$1 androidPopup_androidKt$Popup$5$1 = new AndroidPopup_androidKt$Popup$5$1(this.$popupLayout, continuation);
        androidPopup_androidKt$Popup$5$1.L$0 = obj;
        return androidPopup_androidKt$Popup$5$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AndroidPopup_androidKt$Popup$5$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x0045 -> B:5:0x0048). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 1
            if (r1 == 0) goto L19
            if (r1 != r2) goto L11
            java.lang.Object r1 = r8.L$0
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            kotlin.ResultKt.throwOnFailure(r9)
            goto L48
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            r1 = r9
        L21:
            boolean r9 = kotlinx.coroutines.CoroutineScopeKt.isActive(r1)
            if (r9 == 0) goto L6a
            androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1$1 r9 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.1
                static {
                    /*
                        androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1$1 r0 = new androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1$1) androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.1.INSTANCE androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.AnonymousClass1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.AnonymousClass1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ java.lang.Object mo779invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        java.lang.Number r1 = (java.lang.Number) r1
                        r1.longValue()
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.AnonymousClass1.mo779invoke(java.lang.Object):java.lang.Object");
                }
            }
            r8.L$0 = r1
            r8.label = r2
            kotlin.coroutines.CoroutineContext r3 = r8.getContext()
            androidx.compose.ui.platform.InfiniteAnimationPolicy$Key r4 = androidx.compose.ui.platform.InfiniteAnimationPolicy.Key
            kotlin.coroutines.CoroutineContext$Element r3 = r3.get(r4)
            if (r3 != 0) goto L64
            kotlin.coroutines.CoroutineContext r3 = r8.getContext()
            androidx.compose.runtime.MonotonicFrameClock r3 = androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r3)
            java.lang.Object r9 = r3.withFrameNanos(r9, r8)
            if (r9 != r0) goto L48
            return r0
        L48:
            androidx.compose.ui.window.PopupLayout r9 = r8.$popupLayout
            int[] r3 = r9.locationOnScreen
            r4 = 0
            r5 = r3[r4]
            r6 = r3[r2]
            android.view.View r7 = r9.composeView
            r7.getLocationOnScreen(r3)
            int[] r3 = r9.locationOnScreen
            r4 = r3[r4]
            if (r5 != r4) goto L60
            r3 = r3[r2]
            if (r6 == r3) goto L21
        L60:
            r9.updateParentBounds$ui_release()
            goto L21
        L64:
            java.lang.ClassCastException r8 = new java.lang.ClassCastException
            r8.<init>()
            throw r8
        L6a:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
