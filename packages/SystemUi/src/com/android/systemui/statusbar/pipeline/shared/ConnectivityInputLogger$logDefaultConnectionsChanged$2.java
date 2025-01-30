package com.android.systemui.statusbar.pipeline.shared;

import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class ConnectivityInputLogger$logDefaultConnectionsChanged$2 extends FunctionReferenceImpl implements Function1 {
    public ConnectivityInputLogger$logDefaultConnectionsChanged$2(Object obj) {
        super(1, obj, DefaultConnectionModel.class, "messagePrinter", "messagePrinter(Lcom/android/systemui/log/LogMessage;)Ljava/lang/String;", 0);
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
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("DefaultConnectionModel(wifi.isDefault=", bool1, ", mobile.isDefault=", bool2, ", carrierMerged.isDefault=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, bool3, ", ethernet.isDefault=", bool4, ", btTether.isDefault=");
        m69m.append(bool5);
        m69m.append(", isValidated=");
        m69m.append(str);
        m69m.append(")");
        return m69m.toString();
    }
}
