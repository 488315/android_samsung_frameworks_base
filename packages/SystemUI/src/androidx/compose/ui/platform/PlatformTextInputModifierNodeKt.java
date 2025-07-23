package androidx.compose.ui.platform;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PlatformTextInputModifierNodeKt {
    public static final StaticProvidableCompositionLocal LocalChainedPlatformTextInputInterceptor = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$LocalChainedPlatformTextInputInterceptor$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return null;
        }
    });

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0066, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void InterceptPlatformTextInput(final androidx.compose.ui.platform.PlatformTextInputInterceptor r5, final kotlin.jvm.functions.Function2 r6, androidx.compose.runtime.Composer r7, final int r8) {
        /*
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r0 = 1315007550(0x4e616c3e, float:9.4549184E8)
            r7.startRestartGroup(r0)
            r0 = r8 & 6
            if (r0 != 0) goto L20
            r0 = r8 & 8
            if (r0 != 0) goto L15
            boolean r0 = r7.changed(r5)
            goto L19
        L15:
            boolean r0 = r7.changedInstance(r5)
        L19:
            if (r0 == 0) goto L1d
            r0 = 4
            goto L1e
        L1d:
            r0 = 2
        L1e:
            r0 = r0 | r8
            goto L21
        L20:
            r0 = r8
        L21:
            r1 = r8 & 48
            if (r1 != 0) goto L31
            boolean r1 = r7.changedInstance(r6)
            if (r1 == 0) goto L2e
            r1 = 32
            goto L30
        L2e:
            r1 = 16
        L30:
            r0 = r0 | r1
        L31:
            r1 = r0 & 19
            r2 = 18
            if (r1 == r2) goto L39
            r1 = 1
            goto L3a
        L39:
            r1 = 0
        L3a:
            r2 = r0 & 1
            boolean r1 = r7.shouldExecute(r2, r1)
            if (r1 == 0) goto L8f
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L4d
            java.lang.String r1 = "androidx.compose.ui.platform.InterceptPlatformTextInput (PlatformTextInputModifierNode.kt:155)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        L4d:
            androidx.compose.runtime.StaticProvidableCompositionLocal r1 = androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.LocalChainedPlatformTextInputInterceptor
            java.lang.Object r2 = r7.consume(r1)
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r2 = (androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor) r2
            boolean r3 = r7.changed(r2)
            java.lang.Object r4 = r7.rememberedValue()
            if (r3 != 0) goto L68
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r3) goto L70
        L68:
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r4 = new androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor
            r4.<init>(r5, r2)
            r7.updateRememberedValue(r4)
        L70:
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r4 = (androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor) r4
            androidx.compose.runtime.MutableState r2 = r4.interceptor$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r2 = (androidx.compose.runtime.SnapshotMutableStateImpl) r2
            r2.setValue(r5)
            androidx.compose.runtime.ProvidedValue r1 = r1.defaultProvidedValue$runtime_release(r4)
            r0 = r0 & 112(0x70, float:1.57E-43)
            r2 = 8
            r0 = r0 | r2
            androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(r1, r6, r7, r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L92
            androidx.compose.runtime.ComposerKt.traceEventEnd()
            goto L92
        L8f:
            r7.skipToGroupEnd()
        L92:
            androidx.compose.runtime.RecomposeScopeImpl r7 = r7.endRestartGroup()
            if (r7 == 0) goto L9f
            androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$InterceptPlatformTextInput$1 r0 = new androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$InterceptPlatformTextInput$1
            r0.<init>()
            r7.block = r0
        L9f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.InterceptPlatformTextInput(androidx.compose.ui.platform.PlatformTextInputInterceptor, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons establishTextInputSession(androidx.compose.ui.platform.PlatformTextInputModifierNode r4, kotlin.jvm.functions.Function2 r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            boolean r0 = r6 instanceof androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$establishTextInputSession$1
            if (r0 == 0) goto L13
            r0 = r6
            androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$establishTextInputSession$1 r0 = (androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$establishTextInputSession$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$establishTextInputSession$1 r0 = new androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$establishTextInputSession$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
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
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5b
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r6 = r4
            androidx.compose.ui.Modifier$Node r6 = (androidx.compose.ui.Modifier.Node) r6
            androidx.compose.ui.Modifier$Node r6 = r6.node
            boolean r6 = r6.isAttached
            if (r6 == 0) goto L61
            androidx.compose.ui.node.Owner r6 = androidx.compose.ui.node.DelegatableNodeKt.requireOwner(r4)
            androidx.compose.ui.node.LayoutNode r4 = androidx.compose.ui.node.DelegatableNodeKt.requireLayoutNode(r4)
            androidx.compose.runtime.CompositionLocalMap r4 = r4.compositionLocalMap
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r4 = (androidx.compose.runtime.internal.PersistentCompositionLocalHashMap) r4
            r4.getClass()
            androidx.compose.runtime.StaticProvidableCompositionLocal r2 = androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.LocalChainedPlatformTextInputInterceptor
            java.lang.Object r4 = androidx.compose.runtime.CompositionLocalMapKt.read(r4, r2)
            androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r4 = (androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor) r4
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = interceptedTextInputSession(r6, r4, r5, r0)
            if (r4 != r1) goto L5b
            return r1
        L5b:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        L61:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "establishTextInputSession called from an unattached node"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.establishTextInputSession(androidx.compose.ui.platform.PlatformTextInputModifierNode, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0043, code lost:
    
        if (((androidx.compose.ui.platform.AndroidComposeView) r5).textInputSession(r7, r0) == r1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0052, code lost:
    
        if (r6.textInputSession(r5, r7, r0) == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons interceptedTextInputSession(androidx.compose.ui.node.Owner r5, androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor r6, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$interceptedTextInputSession$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$interceptedTextInputSession$1 r0 = (androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$interceptedTextInputSession$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$interceptedTextInputSession$1 r0 = new androidx.compose.ui.platform.PlatformTextInputModifierNodeKt$interceptedTextInputSession$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2e:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L55
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L46
        L36:
            kotlin.ResultKt.throwOnFailure(r8)
            if (r6 != 0) goto L4c
            r0.label = r4
            androidx.compose.ui.platform.AndroidComposeView r5 = (androidx.compose.ui.platform.AndroidComposeView) r5
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = r5.textInputSession(r7, r0)
            if (r5 != r1) goto L46
            goto L54
        L46:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        L4c:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = r6.textInputSession(r5, r7, r0)
            if (r5 != r1) goto L55
        L54:
            return r1
        L55:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.interceptedTextInputSession(androidx.compose.ui.node.Owner, androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }
}
