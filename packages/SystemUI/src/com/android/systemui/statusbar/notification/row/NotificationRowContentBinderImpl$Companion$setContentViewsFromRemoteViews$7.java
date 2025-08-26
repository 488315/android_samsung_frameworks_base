package com.android.systemui.statusbar.notification.row;

import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$7 extends FunctionReferenceImpl implements Function1 {
    public NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$7(Object obj) {
        super(1, obj, NotificationContentView.class, "setContractedChild", "setContractedChild(Landroid/view/View;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((NotificationContentView) this.receiver).setContractedChild((View) obj);
        return Unit.INSTANCE;
    }
}
