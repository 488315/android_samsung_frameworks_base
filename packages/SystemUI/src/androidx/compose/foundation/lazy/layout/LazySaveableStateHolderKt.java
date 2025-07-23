package androidx.compose.foundation.lazy.layout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazySaveableStateHolderKt {
    /* JADX WARN: Code restructure failed: missing block: B:18:0x006f, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LazySaveableStateHolderProvider(final kotlin.jvm.functions.Function3 r7, androidx.compose.runtime.Composer r8, final int r9) {
        /*
            r4 = r8
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            r8 = 674185128(0x282f3fa8, float:9.728255E-15)
            r4.startRestartGroup(r8)
            r8 = r9 & 6
            r0 = 2
            if (r8 != 0) goto L19
            boolean r8 = r4.changedInstance(r7)
            if (r8 == 0) goto L16
            r8 = 4
            goto L17
        L16:
            r8 = r0
        L17:
            r8 = r8 | r9
            goto L1a
        L19:
            r8 = r9
        L1a:
            r1 = r8 & 3
            r2 = 1
            if (r1 == r0) goto L21
            r0 = r2
            goto L22
        L21:
            r0 = 0
        L22:
            r8 = r8 & r2
            boolean r8 = r4.shouldExecute(r8, r0)
            if (r8 == 0) goto La4
            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r8 == 0) goto L34
            java.lang.String r8 = "androidx.compose.foundation.lazy.layout.LazySaveableStateHolderProvider (LazySaveableStateHolder.kt:39)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r8)
        L34:
            androidx.compose.runtime.StaticProvidableCompositionLocal r8 = androidx.compose.runtime.saveable.SaveableStateRegistryKt.LocalSaveableStateRegistry
            java.lang.Object r0 = r4.consume(r8)
            androidx.compose.runtime.saveable.SaveableStateRegistry r0 = (androidx.compose.runtime.saveable.SaveableStateRegistry) r0
            androidx.compose.runtime.saveable.SaveableStateHolder r1 = androidx.compose.runtime.saveable.SaveableStateHolderKt.rememberSaveableStateHolder(r4)
            r2 = r0
            java.lang.Object[] r0 = new java.lang.Object[]{r2}
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion r3 = androidx.compose.foundation.lazy.layout.LazySaveableStateHolder.Companion
            r3.getClass()
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1 r3 = new kotlin.jvm.functions.Function2() { // from class: androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1
                static {
                    /*
                        androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1 r0 = new androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1) androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1.INSTANCE androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 2
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2) {
                    /*
                        r0 = this;
                        androidx.compose.runtime.saveable.SaverScope r1 = (androidx.compose.runtime.saveable.SaverScope) r1
                        androidx.compose.foundation.lazy.layout.LazySaveableStateHolder r2 = (androidx.compose.foundation.lazy.layout.LazySaveableStateHolder) r2
                        java.util.Map r0 = r2.performSave()
                        boolean r1 = r0.isEmpty()
                        if (r1 == 0) goto Lf
                        r0 = 0
                    Lf:
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$2 r5 = new androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$Companion$saver$2
            r5.<init>()
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r6 = androidx.compose.runtime.saveable.SaverKt.AutoSaver
            r6 = r1
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r1 = new androidx.compose.runtime.saveable.SaverKt$Saver$1
            r1.<init>(r3, r5)
            boolean r3 = r4.changedInstance(r2)
            boolean r5 = r4.changedInstance(r6)
            r3 = r3 | r5
            java.lang.Object r5 = r4.rememberedValue()
            if (r3 != 0) goto L71
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r3) goto L79
        L71:
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt$LazySaveableStateHolderProvider$holder$1$1 r5 = new androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt$LazySaveableStateHolderProvider$holder$1$1
            r5.<init>()
            r4.updateRememberedValue(r5)
        L79:
            r3 = r5
            kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
            r6 = 4
            r2 = 0
            r5 = 0
            java.lang.Object r0 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r0, r1, r2, r3, r4, r5, r6)
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolder r0 = (androidx.compose.foundation.lazy.layout.LazySaveableStateHolder) r0
            androidx.compose.runtime.ProvidedValue r8 = r8.defaultProvidedValue$runtime_release(r0)
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt$LazySaveableStateHolderProvider$1 r1 = new androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt$LazySaveableStateHolderProvider$1
            r1.<init>()
            r0 = 1863926504(0x6f1942e8, float:4.743209E28)
            androidx.compose.runtime.internal.ComposableLambdaImpl r0 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r0, r1, r4)
            r1 = 56
            androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(r8, r0, r4, r1)
            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r8 == 0) goto La7
            androidx.compose.runtime.ComposerKt.traceEventEnd()
            goto La7
        La4:
            r4.skipToGroupEnd()
        La7:
            androidx.compose.runtime.RecomposeScopeImpl r8 = r4.endRestartGroup()
            if (r8 == 0) goto Lb4
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt$LazySaveableStateHolderProvider$2 r0 = new androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt$LazySaveableStateHolderProvider$2
            r0.<init>()
            r8.block = r0
        Lb4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazySaveableStateHolderKt.LazySaveableStateHolderProvider(kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }
}
