package com.android.systemui.statusbar.notification;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RemoteInputControllerLogger {
    public final LogBuffer logBuffer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public RemoteInputControllerLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logAddRemoteInput(String str, String str2, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        RemoteInputControllerLogger$logAddRemoteInput$2 remoteInputControllerLogger$logAddRemoteInput$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$logAddRemoteInput$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str22 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                String str3 = logMessage.getStr3();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("addRemoteInput reason:", str22, " entry: ", str1, ", style:");
                m.append(str3);
                m.append(", isAlreadyActive: ");
                m.append(bool1);
                m.append(", isFound:");
                m.append(bool2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("RemoteInputControllerLog", logLevel, remoteInputControllerLogger$logAddRemoteInput$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = "RemoteInputView#focus";
        logMessageImpl.str3 = str2;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void logRemoteInputApplySkipped(String str, String str2, String str3) {
        LogLevel logLevel = LogLevel.DEBUG;
        RemoteInputControllerLogger$logRemoteInputApplySkipped$2 remoteInputControllerLogger$logRemoteInputApplySkipped$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$logRemoteInputApplySkipped$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str22 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("removeRemoteInput[apply is skipped] reason: ", str22, "for entry: ", str1, ", style: "), logMessage.getStr3(), " ");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("RemoteInputControllerLog", logLevel, remoteInputControllerLogger$logRemoteInputApplySkipped$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logBuffer.commit(obtain);
    }

    public final void logRemoveRemoteInput(String str, boolean z, boolean z2, boolean z3, boolean z4, String str2, String str3) {
        LogLevel logLevel = LogLevel.DEBUG;
        RemoteInputControllerLogger$logRemoveRemoteInput$2 remoteInputControllerLogger$logRemoveRemoteInput$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$logRemoveRemoteInput$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str22 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                String str32 = logMessage.getStr3();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("removeRemoteInput reason: ", str22, " entry: ", str1, ", style: ");
                m.append(str32);
                m.append(", remoteEditImeVisible: ");
                m.append(bool1);
                m.append(", remoteEditImeAnimatingAway: ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool2, ", isRemoteInputActiveForEntry: ", bool3, ", isRemoteInputActive: ");
                m.append(bool4);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("RemoteInputControllerLog", logLevel, remoteInputControllerLogger$logRemoveRemoteInput$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logBuffer.commit(obtain);
    }
}
