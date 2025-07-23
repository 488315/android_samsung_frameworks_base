package com.android.systemui.education.shared.model;

import com.android.systemui.contextualeducation.GestureType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EducationInfo {
    public final EducationUiType educationUiType;
    public final GestureType gestureType;
    public final int userId;

    public EducationInfo(GestureType gestureType, EducationUiType educationUiType, int i) {
        this.gestureType = gestureType;
        this.educationUiType = educationUiType;
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EducationInfo)) {
            return false;
        }
        EducationInfo educationInfo = (EducationInfo) obj;
        return this.gestureType == educationInfo.gestureType && this.educationUiType == educationInfo.educationUiType && this.userId == educationInfo.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + ((this.educationUiType.hashCode() + (this.gestureType.hashCode() * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EducationInfo(gestureType=");
        sb.append(this.gestureType);
        sb.append(", educationUiType=");
        sb.append(this.educationUiType);
        sb.append(", userId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
    }
}
