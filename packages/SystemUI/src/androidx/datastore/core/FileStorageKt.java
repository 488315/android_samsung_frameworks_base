package androidx.datastore.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FileStorageKt {
    /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$runFileDiagnosticsIfNotCorruption(java.io.File r4, kotlin.jvm.functions.Function1 r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            boolean r0 = r6 instanceof androidx.datastore.core.FileStorageKt$runFileDiagnosticsIfNotCorruption$1
            if (r0 == 0) goto L13
            r0 = r6
            androidx.datastore.core.FileStorageKt$runFileDiagnosticsIfNotCorruption$1 r0 = (androidx.datastore.core.FileStorageKt$runFileDiagnosticsIfNotCorruption$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.FileStorageKt$runFileDiagnosticsIfNotCorruption$1 r0 = new androidx.datastore.core.FileStorageKt$runFileDiagnosticsIfNotCorruption$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r4 = r0.L$0
            java.io.File r4 = (java.io.File) r4
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.io.IOException -> L2b
            return r6
        L2b:
            r5 = move-exception
            goto L44
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4     // Catch: java.io.IOException -> L2b
            r0.label = r3     // Catch: java.io.IOException -> L2b
            java.lang.Object r4 = r5.mo779invoke(r0)     // Catch: java.io.IOException -> L2b
            if (r4 != r1) goto L43
            return r1
        L43:
            return r4
        L44:
            boolean r6 = r5 instanceof androidx.datastore.core.CorruptionException
            if (r6 != 0) goto Laa
            androidx.datastore.core.FileDiagnostics r6 = androidx.datastore.core.FileDiagnostics.INSTANCE
            r6.getClass()
            boolean r6 = r4.exists()
            if (r6 == 0) goto La5
            boolean r6 = r4.isFile()
            if (r6 == 0) goto L7f
            boolean r6 = r4.canRead()
            if (r6 == 0) goto L6f
            boolean r6 = r4.canWrite()
            if (r6 == 0) goto L6a
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        L6a:
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        L6f:
            boolean r6 = r4.canWrite()
            if (r6 == 0) goto L7a
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        L7a:
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        L7f:
            boolean r6 = r4.canRead()
            if (r6 == 0) goto L95
            boolean r6 = r4.canWrite()
            if (r6 == 0) goto L90
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        L90:
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        L95:
            boolean r6 = r4.canWrite()
            if (r6 == 0) goto La0
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        La0:
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
            goto La9
        La5:
            java.io.IOException r4 = androidx.datastore.core.FileDiagnostics.attachParentStacktrace(r4, r5)
        La9:
            throw r4
        Laa:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileStorageKt.access$runFileDiagnosticsIfNotCorruption(java.io.File, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
