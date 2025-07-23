package com.android.systemui.screenrecord;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecordingControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = RecordingControllerLogger.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Updating state. isRecording=", logMessage.getBool1());
            case 1:
                int i2 = RecordingControllerLogger.$r8$clinit;
                return "Received update intent with no state";
            case 2:
                int i3 = RecordingControllerLogger.$r8$clinit;
                return "Sent start intent";
            case 3:
                int i4 = RecordingControllerLogger.$r8$clinit;
                return "Pending intent was cancelled";
            case 4:
                int i5 = RecordingControllerLogger.$r8$clinit;
                return "Couldn't stop recording because stop intent was null";
            case 5:
                int i6 = RecordingControllerLogger.$r8$clinit;
                return "Couldn't stop recording";
            case 6:
                int i7 = RecordingControllerLogger.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Update intent has state. isRecording=", logMessage.getBool1());
            case 7:
                int i8 = RecordingControllerLogger.$r8$clinit;
                return "Stopping recording";
            case 8:
                int i9 = RecordingControllerLogger.$r8$clinit;
                return "Couldn't cancel countdown because timer was null";
            default:
                int i10 = RecordingControllerLogger.$r8$clinit;
                return "Record countdown cancelled";
        }
    }
}
