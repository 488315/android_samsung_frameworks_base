package com.android.systemui.graphics;

import android.content.Context;
import android.graphics.drawable.Icon;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ImageLoader$loadDrawable$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $allocator;
    final /* synthetic */ Context $context;
    final /* synthetic */ Icon $icon;
    final /* synthetic */ int $maxHeight;
    final /* synthetic */ int $maxWidth;
    int label;
    final /* synthetic */ ImageLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageLoader$loadDrawable$4(ImageLoader imageLoader, Icon icon, Context context, int i, int i2, int i3, Continuation continuation) {
        super(2, continuation);
        this.this$0 = imageLoader;
        this.$icon = icon;
        this.$context = context;
        this.$maxWidth = i;
        this.$maxHeight = i2;
        this.$allocator = i3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ImageLoader$loadDrawable$4(this.this$0, this.$icon, this.$context, this.$maxWidth, this.$maxHeight, this.$allocator, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ImageLoader$loadDrawable$4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ImageLoader imageLoader = this.this$0;
        Icon icon = this.$icon;
        Context context = this.$context;
        int i = this.$maxWidth;
        int i2 = this.$maxHeight;
        int i3 = this.$allocator;
        imageLoader.getClass();
        return ImageLoader.loadDrawableSync(icon, context, i, i2, i3);
    }
}
