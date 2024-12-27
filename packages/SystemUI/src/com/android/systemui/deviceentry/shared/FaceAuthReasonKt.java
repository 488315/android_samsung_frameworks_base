package com.android.systemui.deviceentry.shared;

import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class FaceAuthReasonKt {
    public static final Map apiRequestReasonToUiEvent;

    static {
        Pair pair = new Pair("Face auth due to swipe up on bouncer", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER);
        Pair pair2 = new Pair("Face auth triggered due to finger down on UDFPS", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN);
        Pair pair3 = new Pair("Face auth due to notification panel click.", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED);
        Pair pair4 = new Pair("Face auth due to QS expansion.", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_QS_EXPANDED);
        FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED;
        apiRequestReasonToUiEvent = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, new Pair("Face auth due to pickup gesture triggered when the device is awake and not from AOD.", faceAuthUiEvent), new Pair("Face auth due to pickup gesture triggered when the device is awake and not from AOD.", faceAuthUiEvent), new Pair("Face auth due to an accessibility action.", FaceAuthUiEvent.FACE_AUTH_ACCESSIBILITY_ACTION), new Pair("Face auth triggered due to retry button click.", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_RETRY_BUTTON_CLICKED), new Pair("Face auth because qs is fully expanded", FaceAuthUiEvent.FACE_AUTH_UPDATED_QS_FULLY_EXPANDED), new Pair("Face auth started/stopped because PPP lockout deadline is set", FaceAuthUiEvent.FACE_AUTH_UPDATED_LOCKOUT_DEADLINE), new Pair("Face auth started/stopped because biometric lockout deadline is set", FaceAuthUiEvent.FACE_AUTH_UPDATED_BIOMETRIC_LOCKOUT_DEADLINE), new Pair("Face auth started/stopped because cocktail bar showing state is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_COCKTAIL_BAR_SHOWING_CHANGED), new Pair("Face auth started/stopped because secure state unlock is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_SECURE_STATE_UNLOCK_CHANGED), new Pair("Face auth started/stopped because sub screen requests biometrics", FaceAuthUiEvent.FACE_AUTH_UPDATED_SUB_SCREEN), new Pair("Face auth started/stopped because keyguard is unlocking", FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_UNLOCKING), new Pair("Face auth stopped because face manager session is closed", FaceAuthUiEvent.FACE_AUTH_STOPPED_SESSION_CLOSE), new Pair("Face auth started/stopped because window focus is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED), new Pair("Face auth stopped due to face recognition error", FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_ERROR), new Pair("Face auth stopped due to face recognition failed", FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_FAILED), new Pair("Face auth started/stopped because cover state is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_COVER_STATE_CHANGED), new Pair("Face auth started/stopped because full screen face widget is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET), new Pair("Face auth started/stopped because dynamic lock screen is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_DYNAMIC_LOCK), new Pair("Face auth started/stopped because noti star state is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_NOTI_STAR_STATE_CHANGED), new Pair("Face auth started/stopped because lock icon is pressed", FaceAuthUiEvent.FACE_AUTH_UPDATED_LOCK_ICON_PRESSED), new Pair("Face auth started/stopped because folder state is changed", FaceAuthUiEvent.FACE_AUTH_UPDATED_FOLDER_STATE_CHANGED), new Pair("Face auth stopped because showing prev credential view is changed", FaceAuthUiEvent.FACE_AUTH_STOPPED_PREV_CREDENTIAL_VIEW));
    }
}
