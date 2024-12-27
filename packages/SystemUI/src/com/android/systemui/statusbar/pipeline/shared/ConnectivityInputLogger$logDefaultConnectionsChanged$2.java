package com.android.systemui.statusbar.pipeline.shared;

import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class ConnectivityInputLogger$logDefaultConnectionsChanged$2 extends FunctionReferenceImpl implements Function1 {
    public ConnectivityInputLogger$logDefaultConnectionsChanged$2(Object obj) {
        super(1, obj, DefaultConnectionModel.class, "messagePrinter", "messagePrinter(Lcom/android/systemui/log/core/LogMessage;)Ljava/lang/String;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        ((DefaultConnectionModel) this.receiver).getClass();
        boolean bool1 = logMessage.getBool1();
        boolean bool2 = logMessage.getBool2();
        boolean bool3 = logMessage.getBool3();
        boolean bool4 = logMessage.getBool4();
        boolean bool5 = logMessage.getBool5();
        String str = logMessage.getInt1() == 1 ? "true" : "false";
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("DefaultConnectionModel(wifi.isDefault=", ", mobile.isDefault=", ", carrierMerged.isDefault=", bool1, bool2);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool3, ", ethernet.isDefault=", bool4, ", btTether.isDefault=");
        m.append(bool5);
        m.append(", isValidated=");
        m.append(str);
        m.append(")");
        return m.toString();
    }
}
