package com.android.systemui.statusbar.notification.row;

import android.content.res.Resources;
import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$5 extends FunctionReferenceImpl implements Function1 {
    public NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$5(Object obj) {
        super(1, obj, NotificationContentView.class, "setHeadsUpChild", "setHeadsUpChild(Landroid/view/View;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
        ((NotificationContentView) this.receiver).setHeadsUpChild((View) obj);
        return Unit.INSTANCE;
    }
}
