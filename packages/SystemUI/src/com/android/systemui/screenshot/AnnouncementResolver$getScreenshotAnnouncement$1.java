package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class AnnouncementResolver$getScreenshotAnnouncement$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AnnouncementResolver this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnouncementResolver$getScreenshotAnnouncement$1(AnnouncementResolver announcementResolver, Continuation continuation) {
        super(continuation);
        this.this$0 = announcementResolver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.getScreenshotAnnouncement(0, this);
    }
}
