package androidx.compose.ui.text.font;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AsyncFontListLoader implements State<Object> {
    public final AsyncTypefaceCache asyncTypefaceCache;
    public boolean cacheable = true;
    public final List fontList;
    public final Function1 onCompletion;
    public final PlatformFontLoader platformFontLoader;
    public final TypefaceRequest typefaceRequest;
    public final MutableState value$delegate;

    public AsyncFontListLoader(List<? extends Font> list, Object obj, TypefaceRequest typefaceRequest, AsyncTypefaceCache asyncTypefaceCache, Function1 function1, PlatformFontLoader platformFontLoader) {
        this.fontList = list;
        this.typefaceRequest = typefaceRequest;
        this.asyncTypefaceCache = asyncTypefaceCache;
        this.onCompletion = function1;
        this.platformFontLoader = platformFontLoader;
        this.value$delegate = SnapshotStateKt.mutableStateOf$default(obj);
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return ((SnapshotMutableStateImpl) this.value$delegate).getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0070 A[Catch: all -> 0x00d6, TryCatch #0 {all -> 0x00d6, blocks: (B:16:0x0070, B:18:0x0083, B:25:0x00a5, B:27:0x00b3, B:35:0x00da, B:53:0x0064), top: B:52:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a5 A[Catch: all -> 0x00d6, TRY_LEAVE, TryCatch #0 {all -> 0x00d6, blocks: (B:16:0x0070, B:18:0x0083, B:25:0x00a5, B:27:0x00b3, B:35:0x00da, B:53:0x0064), top: B:52:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00da A[Catch: all -> 0x00d6, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x00d6, blocks: (B:16:0x0070, B:18:0x0083, B:25:0x00a5, B:27:0x00b3, B:35:0x00da, B:53:0x0064), top: B:52:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0081 -> B:14:0x00f6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x00ed -> B:13:0x00f2). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object load(kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.AsyncFontListLoader.load(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadWithTimeoutOrNull$ui_text_release(androidx.compose.ui.text.font.Font r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$1 r0 = (androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$1 r0 = new androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r5 = r0.L$0
            r6 = r5
            androidx.compose.ui.text.font.Font r6 = (androidx.compose.ui.text.font.Font) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Exception -> L2d java.util.concurrent.CancellationException -> L2f
            return r7
        L2d:
            r5 = move-exception
            goto L4f
        L2f:
            r5 = move-exception
            goto L78
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L39:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$2 r7 = new androidx.compose.ui.text.font.AsyncFontListLoader$loadWithTimeoutOrNull$2     // Catch: java.lang.Exception -> L2d java.util.concurrent.CancellationException -> L2f
            r7.<init>(r5, r6, r4)     // Catch: java.lang.Exception -> L2d java.util.concurrent.CancellationException -> L2f
            r0.L$0 = r6     // Catch: java.lang.Exception -> L2d java.util.concurrent.CancellationException -> L2f
            r0.label = r3     // Catch: java.lang.Exception -> L2d java.util.concurrent.CancellationException -> L2f
            r2 = 15000(0x3a98, double:7.411E-320)
            java.lang.Object r5 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r2, r7, r0)     // Catch: java.lang.Exception -> L2d java.util.concurrent.CancellationException -> L2f
            if (r5 != r1) goto L4e
            return r1
        L4e:
            return r5
        L4f:
            kotlin.coroutines.CoroutineContext r7 = r0.getContext()
            kotlinx.coroutines.CoroutineExceptionHandler$Key r1 = kotlinx.coroutines.CoroutineExceptionHandler.Key
            kotlin.coroutines.CoroutineContext$Element r7 = r7.get(r1)
            kotlinx.coroutines.CoroutineExceptionHandler r7 = (kotlinx.coroutines.CoroutineExceptionHandler) r7
            if (r7 == 0) goto L82
            kotlin.coroutines.CoroutineContext r0 = r0.getContext()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Unable to load font "
            r2.<init>(r3)
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            r1.<init>(r6, r5)
            r7.handleException(r1, r0)
            goto L82
        L78:
            kotlin.coroutines.CoroutineContext r6 = r0.getContext()
            boolean r6 = kotlinx.coroutines.JobKt.isActive(r6)
            if (r6 == 0) goto L83
        L82:
            return r4
        L83:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.AsyncFontListLoader.loadWithTimeoutOrNull$ui_text_release(androidx.compose.ui.text.font.Font, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
