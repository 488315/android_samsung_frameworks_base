package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class NotificationStackSizeCalculator$childrenSequence$1 extends Lambda implements Function1 {
    public static final NotificationStackSizeCalculator$childrenSequence$1 INSTANCE = new NotificationStackSizeCalculator$childrenSequence$1();

    public NotificationStackSizeCalculator$childrenSequence$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return (ExpandableView) ((View) obj);
    }
}
