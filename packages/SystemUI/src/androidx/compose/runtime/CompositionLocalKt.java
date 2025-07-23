package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CompositionLocalKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.compose.runtime.PersistentCompositionLocalMap, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CompositionLocalProvider(final androidx.compose.runtime.ProvidedValue[] r7, final kotlin.jvm.functions.Function2 r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r0 = -1390796515(0xffffffffad1a211d, float:-8.761239E-12)
            r9.startRestartGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "androidx.compose.runtime.CompositionLocalProvider (CompositionLocal.kt:361)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            androidx.compose.runtime.PersistentCompositionLocalMap r0 = r9.currentCompositionLocalScope()
            androidx.compose.runtime.OpaqueKey r1 = androidx.compose.runtime.ComposerKt.provider
            r2 = 201(0xc9, float:2.82E-43)
            r9.startGroup(r2, r1)
            boolean r1 = r9.inserting
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L34
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r1 = androidx.compose.runtime.internal.PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf()
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r1 = androidx.compose.runtime.CompositionLocalMapKt.updateCompositionMap(r7, r0, r1)
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r0 = r9.updateProviderMapGroup(r0, r1)
            r9.writerHasAProvider = r3
        L32:
            r1 = r2
            goto L79
        L34:
            androidx.compose.runtime.SlotReader r1 = r9.reader
            int r4 = r1.currentGroup
            java.lang.Object r1 = r1.groupGet(r4, r2)
            androidx.compose.runtime.PersistentCompositionLocalMap r1 = (androidx.compose.runtime.PersistentCompositionLocalMap) r1
            androidx.compose.runtime.SlotReader r4 = r9.reader
            int r5 = r4.currentGroup
            java.lang.Object r4 = r4.groupGet(r5, r3)
            androidx.compose.runtime.PersistentCompositionLocalMap r4 = (androidx.compose.runtime.PersistentCompositionLocalMap) r4
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r5 = androidx.compose.runtime.CompositionLocalMapKt.updateCompositionMap(r7, r0, r4)
            boolean r6 = r9.getSkipping()
            if (r6 == 0) goto L6a
            boolean r6 = r9.reusing
            if (r6 != 0) goto L6a
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 != 0) goto L5d
            goto L6a
        L5d:
            int r0 = r9.groupNodeCount
            androidx.compose.runtime.SlotReader r4 = r9.reader
            int r4 = r4.skipGroup()
            int r4 = r4 + r0
            r9.groupNodeCount = r4
            r0 = r1
            goto L32
        L6a:
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r0 = r9.updateProviderMapGroup(r0, r5)
            boolean r4 = r9.reusing
            if (r4 != 0) goto L78
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r1 != 0) goto L32
        L78:
            r1 = r3
        L79:
            if (r1 == 0) goto L82
            boolean r4 = r9.inserting
            if (r4 != 0) goto L82
            r9.recordProviderUpdate(r0)
        L82:
            boolean r4 = r9.providersInvalid
            androidx.compose.runtime.IntStack r5 = r9.providersInvalidStack
            r5.push(r4)
            r9.providersInvalid = r1
            r9.providerCache = r0
            androidx.compose.runtime.OpaqueKey r1 = androidx.compose.runtime.ComposerKt.compositionLocalMap
            androidx.compose.runtime.GroupKind$Companion r4 = androidx.compose.runtime.GroupKind.Companion
            r4.getClass()
            r4 = 202(0xca, float:2.83E-43)
            r9.m331startBaiHCIY(r4, r2, r1, r0)
            int r0 = r10 >> 3
            r0 = r0 & 14
            androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m(r0, r8, r9, r2, r2)
            int r0 = r5.pop()
            if (r0 == 0) goto La7
            r2 = r3
        La7:
            r9.providersInvalid = r2
            r0 = 0
            r9.providerCache = r0
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb5
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lb5:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto Lc2
            androidx.compose.runtime.CompositionLocalKt$CompositionLocalProvider$1 r0 = new androidx.compose.runtime.CompositionLocalKt$CompositionLocalProvider$1
            r0.<init>()
            r9.block = r0
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(androidx.compose.runtime.ProvidedValue[], kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int):void");
    }

    public static DynamicProvidableCompositionLocal compositionLocalOf$default(Function0 function0) {
        return new DynamicProvidableCompositionLocal(StructuralEqualityPolicy.INSTANCE, function0);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CompositionLocalProvider(final androidx.compose.runtime.ProvidedValue r10, final kotlin.jvm.functions.Function2 r11, androidx.compose.runtime.Composer r12, final int r13) {
        /*
            androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
            r0 = -1350970552(0xffffffffaf79d348, float:-2.272148E-10)
            r12.startRestartGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "androidx.compose.runtime.CompositionLocalProvider (CompositionLocal.kt:381)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            androidx.compose.runtime.PersistentCompositionLocalMap r0 = r12.currentCompositionLocalScope()
            androidx.compose.runtime.OpaqueKey r1 = androidx.compose.runtime.ComposerKt.provider
            r2 = 201(0xc9, float:2.82E-43)
            r12.startGroup(r2, r1)
            java.lang.Object r1 = r12.rememberedValue()
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            r3 = 0
            if (r2 == 0) goto L32
            r1 = r3
            goto L34
        L32:
            androidx.compose.runtime.ValueHolder r1 = (androidx.compose.runtime.ValueHolder) r1
        L34:
            androidx.compose.runtime.CompositionLocal r2 = r10.compositionLocal
            androidx.compose.runtime.ValueHolder r4 = r2.updatedStateOf$runtime_release(r10, r1)
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L43
            r12.updateRememberedValue(r4)
        L43:
            boolean r5 = r12.inserting
            r6 = 0
            r7 = 1
            if (r5 == 0) goto L5d
            boolean r1 = r10.canOverride
            if (r1 != 0) goto L53
            boolean r1 = r0.containsKey(r2)
            if (r1 != 0) goto L59
        L53:
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r0 = (androidx.compose.runtime.internal.PersistentCompositionLocalHashMap) r0
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r0 = r0.putValue(r2, r4)
        L59:
            r12.writerHasAProvider = r7
        L5b:
            r1 = r6
            goto L97
        L5d:
            androidx.compose.runtime.SlotReader r5 = r12.reader
            int r8 = r5.currentGroup
            int[] r9 = r5.groups
            java.lang.Object r5 = r5.aux(r8, r9)
            androidx.compose.runtime.PersistentCompositionLocalMap r5 = (androidx.compose.runtime.PersistentCompositionLocalMap) r5
            boolean r8 = r12.getSkipping()
            if (r8 == 0) goto L71
            if (r1 != 0) goto L7c
        L71:
            boolean r8 = r10.canOverride
            if (r8 != 0) goto L8a
            boolean r8 = r0.containsKey(r2)
            if (r8 != 0) goto L7c
            goto L8a
        L7c:
            if (r1 == 0) goto L83
            boolean r1 = r12.providersInvalid
            if (r1 != 0) goto L83
            goto L88
        L83:
            boolean r1 = r12.providersInvalid
            if (r1 == 0) goto L88
            goto L90
        L88:
            r0 = r5
            goto L90
        L8a:
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r0 = (androidx.compose.runtime.internal.PersistentCompositionLocalHashMap) r0
            androidx.compose.runtime.internal.PersistentCompositionLocalHashMap r0 = r0.putValue(r2, r4)
        L90:
            boolean r1 = r12.reusing
            if (r1 != 0) goto L96
            if (r5 == r0) goto L5b
        L96:
            r1 = r7
        L97:
            if (r1 == 0) goto La0
            boolean r2 = r12.inserting
            if (r2 != 0) goto La0
            r12.recordProviderUpdate(r0)
        La0:
            boolean r2 = r12.providersInvalid
            androidx.compose.runtime.IntStack r4 = r12.providersInvalidStack
            r4.push(r2)
            r12.providersInvalid = r1
            r12.providerCache = r0
            androidx.compose.runtime.OpaqueKey r1 = androidx.compose.runtime.ComposerKt.compositionLocalMap
            androidx.compose.runtime.GroupKind$Companion r2 = androidx.compose.runtime.GroupKind.Companion
            r2.getClass()
            r2 = 202(0xca, float:2.83E-43)
            r12.m331startBaiHCIY(r2, r6, r1, r0)
            int r0 = r13 >> 3
            r0 = r0 & 14
            androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m(r0, r11, r12, r6, r6)
            int r0 = r4.pop()
            if (r0 == 0) goto Lc5
            r6 = r7
        Lc5:
            r12.providersInvalid = r6
            r12.providerCache = r3
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Ld2
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Ld2:
            androidx.compose.runtime.RecomposeScopeImpl r12 = r12.endRestartGroup()
            if (r12 == 0) goto Ldf
            androidx.compose.runtime.CompositionLocalKt$CompositionLocalProvider$2 r0 = new androidx.compose.runtime.CompositionLocalKt$CompositionLocalProvider$2
            r0.<init>()
            r12.block = r0
        Ldf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(androidx.compose.runtime.ProvidedValue, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int):void");
    }
}
