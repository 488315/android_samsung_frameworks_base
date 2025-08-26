package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6 extends FunctionReferenceImpl implements Function1 {
    public NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6(Object obj) {
        super(1, obj, NotificationContentView.class, "setHeadsUpInflatedSmartReplies", "setHeadsUpInflatedSmartReplies(Lcom/android/systemui/statusbar/policy/InflatedSmartReplyViewHolder;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = (InflatedSmartReplyViewHolder) obj;
        NotificationContentView notificationContentView = (NotificationContentView) this.receiver;
        notificationContentView.mHeadsUpInflatedSmartReplies = inflatedSmartReplyViewHolder;
        if (inflatedSmartReplyViewHolder == null) {
            notificationContentView.mHeadsUpSmartReplyView = null;
        }
        return Unit.INSTANCE;
    }
}
