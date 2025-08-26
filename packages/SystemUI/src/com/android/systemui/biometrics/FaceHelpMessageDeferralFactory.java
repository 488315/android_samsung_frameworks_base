package com.android.systemui.biometrics;

import android.content.res.Resources;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import dagger.Lazy;
import java.util.UUID;

/* loaded from: classes.dex */
public final class FaceHelpMessageDeferralFactory {
    public final DumpManager dumpManager;
    public final LogBuffer logBuffer;
    public final Resources resources;
    public final Lazy systemClock;

    public FaceHelpMessageDeferralFactory(Resources resources, LogBuffer logBuffer, DumpManager dumpManager, Lazy lazy) {
        this.resources = resources;
        this.logBuffer = logBuffer;
        this.dumpManager = dumpManager;
        this.systemClock = lazy;
    }

    public final FaceHelpMessageDeferral create() {
        String string = UUID.randomUUID().toString();
        return new FaceHelpMessageDeferral(this.resources, new BiometricMessageDeferralLogger(this.logBuffer, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("FaceHelpMessageDeferral[", string, "]")), this.dumpManager, string, this.systemClock);
    }
}
