package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder", m277f = "ActivatableNotificationViewBinder.kt", m278l = {60}, m279m = "registerListenersWhileAttached")
/* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$registerListenersWhileAttached$1 */
/* loaded from: classes2.dex */
final class C2897x1b9578a4 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ActivatableNotificationViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2897x1b9578a4(ActivatableNotificationViewBinder activatableNotificationViewBinder, Continuation<? super C2897x1b9578a4> continuation) {
        super(continuation);
        this.this$0 = activatableNotificationViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return ActivatableNotificationViewBinder.access$registerListenersWhileAttached(this.this$0, null, null, this);
    }
}
