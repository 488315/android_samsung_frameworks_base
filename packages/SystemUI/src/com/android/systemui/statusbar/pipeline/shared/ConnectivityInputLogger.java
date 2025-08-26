package com.android.systemui.statusbar.pipeline.shared;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
public final class ConnectivityInputLogger {
    public final LogBuffer buffer;

    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger$logDefaultConnectionsChanged$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass2(Object obj) {
            super(1, obj, DefaultConnectionModel.class, "messagePrinter", "messagePrinter(Lcom/android/systemui/log/core/LogMessage;)Ljava/lang/String;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            LogMessage logMessage = (LogMessage) obj;
            ((DefaultConnectionModel) this.receiver).getClass();
            boolean bool1 = logMessage.getBool1();
            boolean bool2 = logMessage.getBool2();
            boolean bool3 = logMessage.getBool3();
            boolean bool4 = logMessage.getBool4();
            boolean bool5 = logMessage.getBool5();
            String str = logMessage.getInt1() == 1 ? "true" : "false";
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("DefaultConnectionModel(wifi.isDefault=", ", mobile.isDefault=", ", carrierMerged.isDefault=", bool1, bool2);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, bool3, ", ethernet.isDefault=", bool4, ", btTether.isDefault=");
            sbM.append(bool5);
            sbM.append(", isValidated=");
            sbM.append(str);
            sbM.append(")");
            return sbM.toString();
        }
    }

    public ConnectivityInputLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDefaultConnectionsChanged(DefaultConnectionModel defaultConnectionModel) {
        LogLevel logLevel = LogLevel.DEBUG;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(defaultConnectionModel);
        LogBuffer logBuffer = this.buffer;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logBuffer.obtain("ConnectivityInputLogger", logLevel, anonymousClass2, null);
        logMessageImpl.setBool1(defaultConnectionModel.wifi.isDefault);
        logMessageImpl.setBool2(defaultConnectionModel.mobile.isDefault);
        logMessageImpl.setBool3(defaultConnectionModel.carrierMerged.isDefault);
        logMessageImpl.setBool4(defaultConnectionModel.ethernet.isDefault);
        logMessageImpl.setBool5(defaultConnectionModel.btTether.isDefault);
        logMessageImpl.setInt1(defaultConnectionModel.isValidated ? 1 : 0);
        logBuffer.commit(logMessageImpl);
    }
}
