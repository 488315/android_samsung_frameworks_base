package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RemoteInputControllerLogger {
    public final LogBuffer logBuffer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final void logAddRemoteInput(String str, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        RemoteInputControllerLogger$logAddRemoteInput$2 remoteInputControllerLogger$logAddRemoteInput$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$logAddRemoteInput$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "addRemoteInput entry: " + logMessage.getStr1() + ", isAlreadyActive: " + logMessage.getBool1() + ", isFound:" + logMessage.getBool2();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("RemoteInputControllerLog", logLevel, remoteInputControllerLogger$logAddRemoteInput$2, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }

    public final void logRemoveRemoteInput(String str, boolean z, boolean z2, Boolean bool) {
        String str2;
        LogLevel logLevel = LogLevel.DEBUG;
        RemoteInputControllerLogger$logRemoveRemoteInput$2 remoteInputControllerLogger$logRemoveRemoteInput$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.RemoteInputControllerLogger$logRemoveRemoteInput$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "removeRemoteInput entry: " + logMessage.getStr1() + ", remoteEditImeVisible: " + logMessage.getBool1() + ", remoteEditImeAnimatingAway: " + logMessage.getBool2() + ", isActive: " + logMessage.getStr2();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("RemoteInputControllerLog", logLevel, remoteInputControllerLogger$logRemoveRemoteInput$2, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        if (bool == null || (str2 = bool.toString()) == null) {
            str2 = PeripheralConstants.Result.NOT_AVAILABLE;
        }
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }
}
