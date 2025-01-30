package com.android.systemui.keyguard.shared.model;

import android.util.Log;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.FaceAuthUiEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SysUiFaceAuthenticateOptions {
    public final int authenticateReason;
    public final UiEventLogger.UiEventEnum faceAuthUiEvent;
    public final int userId;
    public final int wakeReason;

    public SysUiFaceAuthenticateOptions(int i, UiEventLogger.UiEventEnum uiEventEnum, int i2) {
        this.userId = i;
        this.faceAuthUiEvent = uiEventEnum;
        this.wakeReason = i2;
        int i3 = 1;
        if (uiEventEnum != FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP) {
            if (uiEventEnum != FaceAuthUiEvent.FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN && uiEventEnum != FaceAuthUiEvent.FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN_OR_WILL_BE_SHOWN) {
                i3 = 0;
            }
            if (i3 != 0) {
                i3 = 2;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED) {
                i3 = 3;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN) {
                i3 = 4;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED) {
                i3 = 5;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED) {
                i3 = 6;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED) {
                i3 = 7;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER) {
                i3 = 9;
            } else if (uiEventEnum == FaceAuthUiEvent.FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN) {
                i3 = 10;
            } else {
                Log.e("FaceAuthenticateOptions", " unmapped FaceAuthUiEvent " + uiEventEnum);
                i3 = 0;
            }
        }
        this.authenticateReason = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SysUiFaceAuthenticateOptions)) {
            return false;
        }
        SysUiFaceAuthenticateOptions sysUiFaceAuthenticateOptions = (SysUiFaceAuthenticateOptions) obj;
        return this.userId == sysUiFaceAuthenticateOptions.userId && Intrinsics.areEqual(this.faceAuthUiEvent, sysUiFaceAuthenticateOptions.faceAuthUiEvent) && this.wakeReason == sysUiFaceAuthenticateOptions.wakeReason;
    }

    public final int hashCode() {
        return Integer.hashCode(this.wakeReason) + ((this.faceAuthUiEvent.hashCode() + (Integer.hashCode(this.userId) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SysUiFaceAuthenticateOptions(userId=");
        sb.append(this.userId);
        sb.append(", faceAuthUiEvent=");
        sb.append(this.faceAuthUiEvent);
        sb.append(", wakeReason=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.wakeReason, ")");
    }

    public /* synthetic */ SysUiFaceAuthenticateOptions(int i, UiEventLogger.UiEventEnum uiEventEnum, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, uiEventEnum, (i3 & 4) != 0 ? 0 : i2);
    }
}
