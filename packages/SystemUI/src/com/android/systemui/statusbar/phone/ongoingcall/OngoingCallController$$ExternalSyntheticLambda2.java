package com.android.systemui.statusbar.phone.ongoingcall;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCallController$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = OngoingCallController.$r8$clinit;
                return "Ongoing call chip view could not be found; Not displaying chip in status bar";
            case 1:
                int i2 = OngoingCallController.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Creating OngoingCallModel.InCall. hasIcon=", logMessage.getBool1());
            case 2:
                int i3 = OngoingCallController.$r8$clinit;
                return "NotifInteractorCallModel: null";
            case 3:
                int i4 = OngoingCallController.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Notification Interactor sent ActiveNotificationModel with callType=", logMessage.getStr1());
            case 4:
                int i5 = OngoingCallController.$r8$clinit;
                return "NotifInteractorCallModel: key=" + logMessage.getStr1() + " when=" + logMessage.getLong1() + " callType=" + logMessage.getStr2() + " hasIcon=" + logMessage.getBool1();
            case 5:
                int i6 = OngoingCallController.$r8$clinit;
                return "Swipe away gesture detected";
            case 6:
                int i7 = OngoingCallController.CallAppUidObserver.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("#onUidStateChanged. isCallAppVisible=", logMessage.getBool1());
            case 7:
                int i8 = OngoingCallController.CallAppUidObserver.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("On uid observer registration, isCallAppVisible=", logMessage.getBool1());
            default:
                int i9 = OngoingCallController.CallAppUidObserver.$r8$clinit;
                return "Security exception when trying to set up uid observer";
        }
    }
}
