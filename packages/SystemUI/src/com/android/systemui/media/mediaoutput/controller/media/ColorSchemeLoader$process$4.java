package com.android.systemui.media.mediaoutput.controller.media;

import android.app.WallpaperColors;
import android.graphics.drawable.Drawable;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ColorSchemeLoader$process$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $callback;
    final /* synthetic */ Drawable $drawable;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorSchemeLoader$process$4(Drawable drawable, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$drawable = drawable;
        this.$callback = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ColorSchemeLoader$process$4(this.$drawable, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ColorSchemeLoader$process$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WallpaperColors fromDrawable = WallpaperColors.fromDrawable(this.$drawable);
            try {
                int i2 = Result.$r8$clinit;
                failure = new ColorScheme(fromDrawable, true, Style.CONTENT);
            } catch (Throwable th) {
                int i3 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
            ColorSchemeLoader$process$4$3$1 colorSchemeLoader$process$4$3$1 = new ColorSchemeLoader$process$4$3$1(this.$callback, this.$drawable, (ColorScheme) failure, null);
            this.L$0 = failure;
            this.label = 1;
            if (BuildersKt.withContext(mainCoroutineDispatcher, colorSchemeLoader$process$4$3$1, this) == coroutineSingletons) {
                return coroutineSingletons;
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
