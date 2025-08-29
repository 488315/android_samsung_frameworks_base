package com.android.systemui.media.mediaoutput.controller.media;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.monet.ColorScheme;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0089, code lost:
    
        if (r9.invoke(r7, (com.android.systemui.monet.ColorScheme) r6, r8) == r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x009a, code lost:
    
        if (r9.invoke(null, null, r8) == r0) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object failure;
        Drawable drawable;
        Object failure2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PackageManager packageManager = this.$context.getPackageManager();
            String str = this.$packageName;
            try {
                int i2 = Result.$r8$clinit;
                failure = packageManager.getApplicationIcon(str);
            } catch (Throwable th) {
                int i3 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
            if (thM3441exceptionOrNullimpl != null) {
                thM3441exceptionOrNullimpl.printStackTrace();
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            drawable = (Drawable) failure;
            if (drawable != null) {
                Function3 function3 = this.$callback;
                try {
                    failure2 = new ColorScheme(WallpaperColors.fromDrawable(drawable), true, 6);
                } catch (Throwable th2) {
                    int i4 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th2);
                }
                Throwable thM3441exceptionOrNullimpl2 = Result.m3441exceptionOrNullimpl(failure2);
                if (thM3441exceptionOrNullimpl2 != null) {
                    thM3441exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                TintDrawablePainter.Companion.getClass();
                TintDrawablePainter converter = TintDrawablePainter.Companion.toConverter(drawable);
                this.L$0 = drawable;
                this.L$1 = failure2;
                this.label = 1;
            } else {
                Function3 function32 = this.$callback;
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        drawable = (Drawable) this.L$0;
        ResultKt.throwOnFailure(obj);
        if (drawable == null) {
        }
        return Unit.INSTANCE;
    }
}
