package com.android.systemui.statusbar.notification.shelf;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationShelfIconContainer extends NotificationIconContainer {
    public NotificationShelfIconContainer(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    public float getLeftBound() {
        return getActualPaddingStart();
    }

    @Override // com.android.systemui.statusbar.phone.NotificationIconContainer
    public float getRightBound() {
        return super.getRightBound();
    }

    public /* synthetic */ NotificationShelfIconContainer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public NotificationShelfIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static /* synthetic */ void isAlignedToRight$annotations() {
    }
}
