package androidx.compose.runtime;

import androidx.compose.runtime.changelist.ChangeList;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EffectsKt {
    public static final DisposableEffectScope InternalDisposableEffectScope = new DisposableEffectScope();

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DisposableEffect(java.lang.Object r1, kotlin.jvm.functions.Function1 r2, androidx.compose.runtime.Composer r3) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.DisposableEffect (Effects.kt:150)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            boolean r1 = r3.changed(r1)
            java.lang.Object r0 = r3.rememberedValue()
            if (r1 != 0) goto L20
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r1) goto L28
        L20:
            androidx.compose.runtime.DisposableEffectImpl r0 = new androidx.compose.runtime.DisposableEffectImpl
            r0.<init>(r2)
            r3.updateRememberedValue(r0)
        L28:
            androidx.compose.runtime.DisposableEffectImpl r0 = (androidx.compose.runtime.DisposableEffectImpl) r0
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L33
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L33:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.DisposableEffect(java.lang.Object, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0020, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LaunchedEffect(androidx.compose.runtime.Composer r2, java.lang.Object r3, kotlin.jvm.functions.Function2 r4) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.LaunchedEffect (Effects.kt:338)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
            kotlin.coroutines.CoroutineContext r0 = r2.applyCoroutineContext
            boolean r3 = r2.changed(r3)
            java.lang.Object r1 = r2.rememberedValue()
            if (r3 != 0) goto L22
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r3) goto L2a
        L22:
            androidx.compose.runtime.LaunchedEffectImpl r1 = new androidx.compose.runtime.LaunchedEffectImpl
            r1.<init>(r0, r4)
            r2.updateRememberedValue(r1)
        L2a:
            androidx.compose.runtime.LaunchedEffectImpl r1 = (androidx.compose.runtime.LaunchedEffectImpl) r1
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L35
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L35:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.LaunchedEffect(androidx.compose.runtime.Composer, java.lang.Object, kotlin.jvm.functions.Function2):void");
    }

    public static final void SideEffect(Function0 function0, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.SideEffect (Effects.kt:51)");
        }
        ChangeList changeList = ((ComposerImpl) composer).changeListWriter.changeList;
        changeList.getClass();
        Operation.SideEffect sideEffect = Operation.SideEffect.INSTANCE;
        Operations operations = changeList.operations;
        operations.pushOp(sideEffect);
        Operations.WriteScope.m338setObjectDKhxnng(operations, 0, function0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    public static final CoroutineScope createCompositionCoroutineScope(EmptyCoroutineContext emptyCoroutineContext, Composer composer) {
        Job.Key key = Job.Key;
        emptyCoroutineContext.getClass();
        return new RememberedCoroutineScope(((ComposerImpl) composer).applyCoroutineContext, emptyCoroutineContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0023, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DisposableEffect(java.lang.Object r1, java.lang.Object r2, kotlin.jvm.functions.Function1 r3, androidx.compose.runtime.Composer r4) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.DisposableEffect (Effects.kt:187)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r1 = r4.changed(r1)
            boolean r2 = r4.changed(r2)
            r1 = r1 | r2
            java.lang.Object r2 = r4.rememberedValue()
            if (r1 != 0) goto L25
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L2d
        L25:
            androidx.compose.runtime.DisposableEffectImpl r2 = new androidx.compose.runtime.DisposableEffectImpl
            r2.<init>(r3)
            r4.updateRememberedValue(r2)
        L2d:
            androidx.compose.runtime.DisposableEffectImpl r2 = (androidx.compose.runtime.DisposableEffectImpl) r2
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L38
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L38:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.DisposableEffect(java.lang.Object, java.lang.Object, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0025, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LaunchedEffect(java.lang.Object r1, java.lang.Object r2, kotlin.jvm.functions.Function2 r3, androidx.compose.runtime.Composer r4) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.LaunchedEffect (Effects.kt:357)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            kotlin.coroutines.CoroutineContext r0 = r4.applyCoroutineContext
            boolean r1 = r4.changed(r1)
            boolean r2 = r4.changed(r2)
            r1 = r1 | r2
            java.lang.Object r2 = r4.rememberedValue()
            if (r1 != 0) goto L27
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L2f
        L27:
            androidx.compose.runtime.LaunchedEffectImpl r2 = new androidx.compose.runtime.LaunchedEffectImpl
            r2.<init>(r0, r3)
            r4.updateRememberedValue(r2)
        L2f:
            androidx.compose.runtime.LaunchedEffectImpl r2 = (androidx.compose.runtime.LaunchedEffectImpl) r2
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L3a
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L3a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.LaunchedEffect(java.lang.Object, java.lang.Object, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0031, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DisposableEffect(java.lang.Object[] r5, kotlin.jvm.functions.Function1 r6, androidx.compose.runtime.Composer r7) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.DisposableEffect (Effects.kt:261)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            int r0 = r5.length
            java.lang.Object[] r5 = java.util.Arrays.copyOf(r5, r0)
            int r0 = r5.length
            r1 = 0
            r2 = r1
        L13:
            if (r1 >= r0) goto L22
            r3 = r5[r1]
            r4 = r7
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r3 = r4.changed(r3)
            r2 = r2 | r3
            int r1 = r1 + 1
            goto L13
        L22:
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            java.lang.Object r5 = r7.rememberedValue()
            if (r2 != 0) goto L33
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r0) goto L3b
        L33:
            androidx.compose.runtime.DisposableEffectImpl r5 = new androidx.compose.runtime.DisposableEffectImpl
            r5.<init>(r6)
            r7.updateRememberedValue(r5)
        L3b:
            boolean r5 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r5 == 0) goto L44
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L44:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.DisposableEffect(java.lang.Object[], kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002a, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LaunchedEffect(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, kotlin.jvm.functions.Function2 r4, androidx.compose.runtime.Composer r5) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.LaunchedEffect (Effects.kt:376)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            kotlin.coroutines.CoroutineContext r0 = r5.applyCoroutineContext
            boolean r1 = r5.changed(r1)
            boolean r2 = r5.changed(r2)
            r1 = r1 | r2
            boolean r2 = r5.changed(r3)
            r1 = r1 | r2
            java.lang.Object r2 = r5.rememberedValue()
            if (r1 != 0) goto L2c
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L34
        L2c:
            androidx.compose.runtime.LaunchedEffectImpl r2 = new androidx.compose.runtime.LaunchedEffectImpl
            r2.<init>(r0, r4)
            r5.updateRememberedValue(r2)
        L34:
            androidx.compose.runtime.LaunchedEffectImpl r2 = (androidx.compose.runtime.LaunchedEffectImpl) r2
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L3f
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L3f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.LaunchedEffect(java.lang.Object, java.lang.Object, java.lang.Object, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0030, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LaunchedEffect(java.lang.Object[] r5, kotlin.jvm.functions.Function2 r6, androidx.compose.runtime.Composer r7) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.runtime.LaunchedEffect (Effects.kt:399)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            kotlin.coroutines.CoroutineContext r0 = r7.applyCoroutineContext
            int r1 = r5.length
            java.lang.Object[] r5 = java.util.Arrays.copyOf(r5, r1)
            int r1 = r5.length
            r2 = 0
            r3 = r2
        L17:
            if (r2 >= r1) goto L23
            r4 = r5[r2]
            boolean r4 = r7.changed(r4)
            r3 = r3 | r4
            int r2 = r2 + 1
            goto L17
        L23:
            java.lang.Object r5 = r7.rememberedValue()
            if (r3 != 0) goto L32
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r1) goto L3a
        L32:
            androidx.compose.runtime.LaunchedEffectImpl r5 = new androidx.compose.runtime.LaunchedEffectImpl
            r5.<init>(r0, r6)
            r7.updateRememberedValue(r5)
        L3a:
            boolean r5 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r5 == 0) goto L43
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L43:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.EffectsKt.LaunchedEffect(java.lang.Object[], kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer):void");
    }
}
