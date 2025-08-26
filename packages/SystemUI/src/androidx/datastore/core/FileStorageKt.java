package androidx.datastore.core;

import java.io.File;
import java.io.IOException;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class FileStorageKt {
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$runFileDiagnosticsIfNotCorruption(File file, Function1 function1, ContinuationImpl continuationImpl) throws IOException {
        FileStorageKt$runFileDiagnosticsIfNotCorruption$1 fileStorageKt$runFileDiagnosticsIfNotCorruption$1;
        if (continuationImpl instanceof FileStorageKt$runFileDiagnosticsIfNotCorruption$1) {
            fileStorageKt$runFileDiagnosticsIfNotCorruption$1 = (FileStorageKt$runFileDiagnosticsIfNotCorruption$1) continuationImpl;
            int i = fileStorageKt$runFileDiagnosticsIfNotCorruption$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                fileStorageKt$runFileDiagnosticsIfNotCorruption$1.label = i - Integer.MIN_VALUE;
            } else {
                fileStorageKt$runFileDiagnosticsIfNotCorruption$1 = new FileStorageKt$runFileDiagnosticsIfNotCorruption$1(continuationImpl);
            }
        }
        Object obj = fileStorageKt$runFileDiagnosticsIfNotCorruption$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = fileStorageKt$runFileDiagnosticsIfNotCorruption$1.label;
        try {
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            fileStorageKt$runFileDiagnosticsIfNotCorruption$1.L$0 = file;
            fileStorageKt$runFileDiagnosticsIfNotCorruption$1.label = 1;
            Object objMo781invoke = function1.mo781invoke(fileStorageKt$runFileDiagnosticsIfNotCorruption$1);
            return objMo781invoke == obj2 ? obj2 : objMo781invoke;
        } catch (IOException e) {
            if (e instanceof CorruptionException) {
                throw e;
            }
            FileDiagnostics.INSTANCE.getClass();
            if (!file.exists()) {
                throw FileDiagnostics.attachParentStacktrace(file, e);
            }
            if (file.isFile()) {
                if (file.canRead()) {
                    if (file.canWrite()) {
                        throw FileDiagnostics.attachParentStacktrace(file, e);
                    }
                    throw FileDiagnostics.attachParentStacktrace(file, e);
                }
                if (file.canWrite()) {
                    throw FileDiagnostics.attachParentStacktrace(file, e);
                }
                throw FileDiagnostics.attachParentStacktrace(file, e);
            }
            if (file.canRead()) {
                if (file.canWrite()) {
                    throw FileDiagnostics.attachParentStacktrace(file, e);
                }
                throw FileDiagnostics.attachParentStacktrace(file, e);
            }
            if (file.canWrite()) {
                throw FileDiagnostics.attachParentStacktrace(file, e);
            }
            throw FileDiagnostics.attachParentStacktrace(file, e);
        }
    }
}
