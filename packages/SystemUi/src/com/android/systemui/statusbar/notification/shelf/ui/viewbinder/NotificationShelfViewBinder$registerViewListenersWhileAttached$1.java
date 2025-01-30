package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder", m277f = "NotificationShelfViewBinder.kt", m278l = {107}, m279m = "registerViewListenersWhileAttached")
/* loaded from: classes2.dex */
final class NotificationShelfViewBinder$registerViewListenersWhileAttached$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ NotificationShelfViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$registerViewListenersWhileAttached$1(NotificationShelfViewBinder notificationShelfViewBinder, Continuation<? super NotificationShelfViewBinder$registerViewListenersWhileAttached$1> continuation) {
        super(continuation);
        this.this$0 = notificationShelfViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return NotificationShelfViewBinder.access$registerViewListenersWhileAttached(this.this$0, null, null, this);
    }
}
