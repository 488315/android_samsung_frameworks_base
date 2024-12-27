package com.android.systemui.screenshot;

import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class AnnouncementResolver$getScreenshotAnnouncement$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Consumer<String> $announceCallback;
    final /* synthetic */ int $userId;
    Object L$0;
    int label;
    final /* synthetic */ AnnouncementResolver this$0;

    public AnnouncementResolver$getScreenshotAnnouncement$2(Consumer<String> consumer, AnnouncementResolver announcementResolver, int i, Continuation continuation) {
        super(2, continuation);
        this.$announceCallback = consumer;
        this.this$0 = announcementResolver;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AnnouncementResolver$getScreenshotAnnouncement$2(this.$announceCallback, this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnnouncementResolver$getScreenshotAnnouncement$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Consumer consumer;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Consumer<String> consumer2 = this.$announceCallback;
            AnnouncementResolver announcementResolver = this.this$0;
            int i2 = this.$userId;
            this.L$0 = consumer2;
            this.label = 1;
            Object screenshotAnnouncement = announcementResolver.getScreenshotAnnouncement(i2, this);
            if (screenshotAnnouncement == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = screenshotAnnouncement;
            consumer = consumer2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            consumer = (Consumer) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        consumer.accept(obj);
        return Unit.INSTANCE;
    }
}
