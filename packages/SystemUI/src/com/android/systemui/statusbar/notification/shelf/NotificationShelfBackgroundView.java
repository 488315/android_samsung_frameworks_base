package com.android.systemui.statusbar.notification.shelf;

import android.content.Context;
import android.util.AttributeSet;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationShelfBackgroundView extends NotificationBackgroundView {
    /* JADX WARN: Multi-variable type inference failed */
    public NotificationShelfBackgroundView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationBackgroundView
    public boolean isAlignedToRight() {
        return isLayoutRtl();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationBackgroundView
    public final String toDumpString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(super.toDumpString(), " alignToEnd=false");
    }

    public /* synthetic */ NotificationShelfBackgroundView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public NotificationShelfBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
