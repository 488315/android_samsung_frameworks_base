package com.android.systemui.media.mediaoutput.controller.media;

import android.app.WallpaperColors;
import android.graphics.Bitmap;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import com.android.systemui.monet.ColorScheme;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class ColorSchemeLoader$process$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Bitmap $bitmap;
    final /* synthetic */ Function2 $callback;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorSchemeLoader$process$1(Bitmap bitmap, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$bitmap = bitmap;
        this.$callback = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ColorSchemeLoader$process$1(this.$bitmap, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ColorSchemeLoader$process$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WallpaperColors wallpaperColorsFromBitmap = WallpaperColors.fromBitmap(this.$bitmap);
            try {
                int i2 = Result.$r8$clinit;
                failure = new ColorScheme(wallpaperColorsFromBitmap, true, 6);
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
            ColorScheme colorScheme = (ColorScheme) failure;
            if (colorScheme != null) {
                Function2 function2 = this.$callback;
                Pair pair = new Pair(new AndroidImageBitmap(this.$bitmap), colorScheme);
                this.L$0 = colorScheme;
                this.label = 1;
                if (function2.invoke(pair, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
