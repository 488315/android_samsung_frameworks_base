package com.android.systemui.statusbar.notification.shelf.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationShelfButtonViewModel {
    public final Flow isVisible;

    public NotificationShelfButtonViewModel(Flow flow) {
        this.isVisible = flow;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof NotificationShelfButtonViewModel) && Intrinsics.areEqual(this.isVisible, ((NotificationShelfButtonViewModel) obj).isVisible);
    }

    public final int hashCode() {
        return this.isVisible.hashCode();
    }

    public final String toString() {
        return "NotificationShelfButtonViewModel(isVisible=" + this.isVisible + ")";
    }
}
