package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ResizeVeil$setupResizeVeil$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ActivityManager.RunningTaskInfo $taskInfo;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ResizeVeil this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.windowdecor.ResizeVeil$setupResizeVeil$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Bitmap $icon;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ResizeVeil this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ResizeVeil resizeVeil, Bitmap bitmap, Continuation continuation) {
            super(2, continuation);
            this.this$0 = resizeVeil;
            this.$icon = bitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$icon, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                return Unit.INSTANCE;
            }
            ImageView imageView = this.this$0.iconView;
            if (imageView == null) {
                imageView = null;
            }
            imageView.setImageBitmap(this.$icon);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResizeVeil$setupResizeVeil$1(ResizeVeil resizeVeil, ActivityManager.RunningTaskInfo runningTaskInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = resizeVeil;
        this.$taskInfo = runningTaskInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ResizeVeil$setupResizeVeil$1 resizeVeil$setupResizeVeil$1 = new ResizeVeil$setupResizeVeil$1(this.this$0, this.$taskInfo, continuation);
        resizeVeil$setupResizeVeil$1.L$0 = obj;
        return resizeVeil$setupResizeVeil$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ResizeVeil$setupResizeVeil$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Bitmap bitmap;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                return Unit.INSTANCE;
            }
            WindowDecorTaskResourceLoader windowDecorTaskResourceLoader = this.this$0.taskResourceLoader;
            ActivityManager.RunningTaskInfo runningTaskInfo = this.$taskInfo;
            windowDecorTaskResourceLoader.checkWindowDecorExists(runningTaskInfo);
            WindowDecorTaskResourceLoader.AppResources appResources = (WindowDecorTaskResourceLoader.AppResources) windowDecorTaskResourceLoader.taskToResourceCache.get(Integer.valueOf(runningTaskInfo.taskId));
            if (appResources != null) {
                bitmap = appResources.veilIcon;
            } else {
                WindowDecorTaskResourceLoader.AppResources loadAppResources = windowDecorTaskResourceLoader.loadAppResources(runningTaskInfo);
                windowDecorTaskResourceLoader.taskToResourceCache.put(Integer.valueOf(runningTaskInfo.taskId), loadAppResources);
                windowDecorTaskResourceLoader.localeListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), runningTaskInfo.getConfiguration().getLocales());
                bitmap = loadAppResources.veilIcon;
            }
            ResizeVeil resizeVeil = this.this$0;
            CoroutineDispatcher coroutineDispatcher = resizeVeil.mainDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(resizeVeil, bitmap, null);
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
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
