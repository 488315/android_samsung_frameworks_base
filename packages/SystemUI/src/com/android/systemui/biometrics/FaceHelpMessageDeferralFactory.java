package com.android.systemui.biometrics;

import android.content.res.Resources;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import java.util.UUID;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceHelpMessageDeferralFactory {
    public final DumpManager dumpManager;
    public final LogBuffer logBuffer;
    public final Resources resources;

    public FaceHelpMessageDeferralFactory(Resources resources, LogBuffer logBuffer, DumpManager dumpManager) {
        this.resources = resources;
        this.logBuffer = logBuffer;
        this.dumpManager = dumpManager;
    }

    public final FaceHelpMessageDeferral create() {
        String uuid = UUID.randomUUID().toString();
        return new FaceHelpMessageDeferral(this.resources, new BiometricMessageDeferralLogger(this.logBuffer, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("FaceHelpMessageDeferral[", uuid, "]")), this.dumpManager, uuid);
    }
}
