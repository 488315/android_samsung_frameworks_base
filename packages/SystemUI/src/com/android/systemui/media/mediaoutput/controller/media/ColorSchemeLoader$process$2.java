package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ColorSchemeLoader$process$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function3 $callback;
    final /* synthetic */ Context $context;
    final /* synthetic */ String $packageName;
    Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorSchemeLoader$process$2(Context context, Function3 function3, String str, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$callback = function3;
        this.$packageName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ColorSchemeLoader$process$2(this.$context, this.$callback, this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ColorSchemeLoader$process$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x008c, code lost:
    
        if (r1 == null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x009a, code lost:
    
        if (r9.invoke(null, null, r8) == r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0089, code lost:
    
        if (r9.invoke(r7, (com.android.systemui.monet.ColorScheme) r6, r8) == r0) goto L40;
     */
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
            r2 = 2
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L22
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r9)
            goto L9d
        L12:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1a:
            java.lang.Object r1 = r8.L$0
            android.graphics.drawable.Drawable r1 = (android.graphics.drawable.Drawable) r1
            kotlin.ResultKt.throwOnFailure(r9)
            goto L8c
        L22:
            kotlin.ResultKt.throwOnFailure(r9)
            android.content.Context r9 = r8.$context
            android.content.pm.PackageManager r9 = r9.getPackageManager()
            java.lang.String r1 = r8.$packageName
            int r5 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L34
            android.graphics.drawable.Drawable r9 = r9.getApplicationIcon(r1)     // Catch: java.lang.Throwable -> L34
            goto L3d
        L34:
            r9 = move-exception
            int r1 = kotlin.Result.$r8$clinit
            kotlin.Result$Failure r1 = new kotlin.Result$Failure
            r1.<init>(r9)
            r9 = r1
        L3d:
            java.lang.Throwable r1 = kotlin.Result.m3422exceptionOrNullimpl(r9)
            if (r1 == 0) goto L46
            r1.printStackTrace()
        L46:
            boolean r1 = r9 instanceof kotlin.Result.Failure
            if (r1 == 0) goto L4b
            r9 = r4
        L4b:
            r1 = r9
            android.graphics.drawable.Drawable r1 = (android.graphics.drawable.Drawable) r1
            if (r1 == 0) goto L8e
            kotlin.jvm.functions.Function3 r9 = r8.$callback
            android.app.WallpaperColors r5 = android.app.WallpaperColors.fromDrawable(r1)
            com.android.systemui.monet.ColorScheme r6 = new com.android.systemui.monet.ColorScheme     // Catch: java.lang.Throwable -> L5d
            r7 = 6
            r6.<init>(r5, r3, r7)     // Catch: java.lang.Throwable -> L5d
            goto L65
        L5d:
            r5 = move-exception
            int r6 = kotlin.Result.$r8$clinit
            kotlin.Result$Failure r6 = new kotlin.Result$Failure
            r6.<init>(r5)
        L65:
            java.lang.Throwable r5 = kotlin.Result.m3422exceptionOrNullimpl(r6)
            if (r5 == 0) goto L6e
            r5.printStackTrace()
        L6e:
            boolean r5 = r6 instanceof kotlin.Result.Failure
            if (r5 == 0) goto L73
            r6 = r4
        L73:
            r5 = r6
            com.android.systemui.monet.ColorScheme r5 = (com.android.systemui.monet.ColorScheme) r5
            com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter$Companion r7 = com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter.Companion
            r7.getClass()
            com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter r7 = com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter.Companion.toConverter(r1)
            r8.L$0 = r1
            r8.L$1 = r6
            r8.label = r3
            java.lang.Object r9 = r9.invoke(r7, r5, r8)
            if (r9 != r0) goto L8c
            goto L9c
        L8c:
            if (r1 != 0) goto L9d
        L8e:
            kotlin.jvm.functions.Function3 r9 = r8.$callback
            r8.L$0 = r4
            r8.L$1 = r4
            r8.label = r2
            java.lang.Object r8 = r9.invoke(r4, r4, r8)
            if (r8 != r0) goto L9d
        L9c:
            return r0
        L9d:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.ColorSchemeLoader$process$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
