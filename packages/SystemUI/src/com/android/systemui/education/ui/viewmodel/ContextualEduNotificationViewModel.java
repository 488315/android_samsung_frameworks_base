package com.android.systemui.education.ui.viewmodel;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.contextualeducation.GestureType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ContextualEduNotificationViewModel extends ContextualEduContentViewModel {
    public final GestureType gestureType;
    public final String message;
    public final String title;
    public final int userId;

    public ContextualEduNotificationViewModel(String str, String str2, GestureType gestureType, int i) {
        super(i, null);
        this.title = str;
        this.message = str2;
        this.gestureType = gestureType;
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContextualEduNotificationViewModel)) {
            return false;
        }
        ContextualEduNotificationViewModel contextualEduNotificationViewModel = (ContextualEduNotificationViewModel) obj;
        return Intrinsics.areEqual(this.title, contextualEduNotificationViewModel.title) && Intrinsics.areEqual(this.message, contextualEduNotificationViewModel.message) && this.gestureType == contextualEduNotificationViewModel.gestureType && this.userId == contextualEduNotificationViewModel.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + ((this.gestureType.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.title.hashCode() * 31, 31, this.message)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ContextualEduNotificationViewModel(title=");
        sb.append(this.title);
        sb.append(", message=");
        sb.append(this.message);
        sb.append(", gestureType=");
        sb.append(this.gestureType);
        sb.append(", userId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
    }
}
