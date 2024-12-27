package com.android.server.voiceinteraction;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.PermissionChecker;
import android.media.permission.Identity;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.FunctionalUtils;
import com.android.server.policy.AppOpsPolicy;

public final /* synthetic */ class DetectorSession$$ExternalSyntheticLambda6
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DetectorSession f$0;

    public final void runOrThrow() {
        DetectorSession detectorSession = this.f$0;
        synchronized (detectorSession.mLock) {
            try {
                if (AppOpsPolicy.isHotwordDetectionServiceRequired(
                        detectorSession.mContext.getPackageManager())) {
                    Context context = detectorSession.mContext;
                    Identity identity = detectorSession.mVoiceInteractorIdentity;
                    if (PermissionChecker.checkPermissionForPreflight(
                                    context,
                                    "android.permission.RECORD_AUDIO",
                                    -1,
                                    identity.uid,
                                    identity.packageName)
                            != 0) {
                        throw new SecurityException(
                                "Failed to obtain permission RECORD_AUDIO for identity "
                                        + detectorSession.mVoiceInteractorIdentity);
                    }
                    AppOpsManager appOpsManager = detectorSession.mAppOpsManager;
                    String opToPublicName =
                            AppOpsManager.opToPublicName(
                                    Flags.voiceActivationPermissionApis() ? 136 : 102);
                    Identity identity2 = detectorSession.mVoiceInteractorIdentity;
                    int unsafeCheckOpNoThrow =
                            appOpsManager.unsafeCheckOpNoThrow(
                                    opToPublicName, identity2.uid, identity2.packageName);
                    if (unsafeCheckOpNoThrow != 3 && unsafeCheckOpNoThrow != 0) {
                        throw new SecurityException(
                                "The app op OP_RECEIVE_SANDBOX_TRIGGER_AUDIO is denied for identity"
                                        + detectorSession.mVoiceInteractorIdentity);
                    }
                    AppOpsManager appOpsManager2 = detectorSession.mAppOpsManager;
                    int i = Flags.voiceActivationPermissionApis() ? 136 : 102;
                    Identity identity3 = detectorSession.mVoiceInteractorIdentity;
                    appOpsManager2.noteOpNoThrow(
                            i,
                            identity3.uid,
                            identity3.packageName,
                            identity3.attributionTag,
                            "Providing hotword detection result to VoiceInteractionService");
                } else {
                    DetectorSession.enforcePermissionForDataDelivery(
                            detectorSession.mContext,
                            detectorSession.mVoiceInteractorIdentity,
                            "android.permission.RECORD_AUDIO",
                            "Providing hotword detection result to VoiceInteractionService");
                }
                DetectorSession.enforcePermissionForDataDelivery(
                        detectorSession.mContext,
                        detectorSession.mVoiceInteractorIdentity,
                        "android.permission.CAPTURE_AUDIO_HOTWORD",
                        "Providing hotword detection result to VoiceInteractionService");
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
