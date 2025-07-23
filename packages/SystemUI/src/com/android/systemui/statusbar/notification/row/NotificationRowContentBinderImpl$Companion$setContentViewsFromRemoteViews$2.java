package com.android.systemui.statusbar.notification.row;

import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$2 extends FunctionReferenceImpl implements Function1 {
    public NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$2(Object obj) {
        super(1, obj, NotificationContentView.class, "setExpandedChild", "setExpandedChild(Landroid/view/View;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((NotificationContentView) this.receiver).setExpandedChild((View) obj);
        return Unit.INSTANCE;
    }
}
