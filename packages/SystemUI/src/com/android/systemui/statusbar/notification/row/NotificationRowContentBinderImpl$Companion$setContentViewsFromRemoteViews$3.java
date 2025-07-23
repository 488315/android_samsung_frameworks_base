package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3 extends FunctionReferenceImpl implements Function1 {
    public NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3(Object obj) {
        super(1, obj, NotificationContentView.class, "setExpandedInflatedSmartReplies", "setExpandedInflatedSmartReplies(Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = (InflatedSmartReplyViewHolder) obj;
        NotificationContentView notificationContentView = (NotificationContentView) this.receiver;
        notificationContentView.mExpandedInflatedSmartReplies = inflatedSmartReplyViewHolder;
        if (inflatedSmartReplyViewHolder == null) {
            notificationContentView.mExpandedSmartReplyView = null;
        }
        return Unit.INSTANCE;
    }
}
