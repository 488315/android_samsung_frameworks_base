package com.android.keyguard.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricMessageDeferralLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(int1, int2, "frameProcessed acquiredInfo=", " totalFrames=", " messageToShowOnTimeout=");
                m.append(str1);
                return m.toString();
            case 1:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "frameIgnored acquiredInfo=");
            default:
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "updateMessage acquiredInfo=", " helpString=", logMessage.getStr1());
        }
    }
}
