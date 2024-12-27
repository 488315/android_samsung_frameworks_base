package com.android.server.voiceinteraction;

import android.app.AppOpsManager;
import android.media.permission.Identity;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class VisualQueryDetectorSession$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ VisualQueryDetectorSession f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2 =
            "Providing query detection result from VisualQueryDetectionService to"
                + " VoiceInteractionService";
    public final /* synthetic */ int f$3;

    public /* synthetic */ VisualQueryDetectorSession$$ExternalSyntheticLambda1(
            VisualQueryDetectorSession visualQueryDetectorSession, String str, int i) {
        this.f$0 = visualQueryDetectorSession;
        this.f$1 = str;
        this.f$3 = i;
    }

    public final void runOrThrow() {
        VisualQueryDetectorSession visualQueryDetectorSession = this.f$0;
        String str = this.f$1;
        String str2 = this.f$2;
        int i = this.f$3;
        synchronized (visualQueryDetectorSession.mLock) {
            DetectorSession.enforcePermissionForDataDelivery(
                    visualQueryDetectorSession.mContext,
                    visualQueryDetectorSession.mVoiceInteractorIdentity,
                    str,
                    str2);
            AppOpsManager appOpsManager = visualQueryDetectorSession.mAppOpsManager;
            Identity identity = visualQueryDetectorSession.mVoiceInteractorIdentity;
            appOpsManager.noteOpNoThrow(
                    i, identity.uid, identity.packageName, identity.attributionTag, str2);
        }
    }
}
