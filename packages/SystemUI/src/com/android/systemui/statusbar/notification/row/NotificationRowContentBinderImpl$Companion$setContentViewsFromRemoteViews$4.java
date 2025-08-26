package com.android.systemui.statusbar.notification.row;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$4 extends FunctionReferenceImpl implements Function1 {
    public NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$4(Object obj) {
        super(1, obj, NotificationContentView.class, "setOngoingChildDummy", "setOngoingChildDummy(Landroid/view/View;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((NotificationContentView) this.receiver).getClass();
        return Unit.INSTANCE;
    }
}
