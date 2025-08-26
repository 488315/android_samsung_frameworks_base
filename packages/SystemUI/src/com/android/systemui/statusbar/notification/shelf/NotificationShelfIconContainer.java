package com.android.systemui.statusbar.notification.shelf;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationShelfIconContainer extends NotificationIconContainer {
    /* JADX WARN: Multi-variable type inference failed */
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
