package com.android.systemui.bluetooth;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BluetoothLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BluetoothLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("DeviceAdded. address=", logMessage.getStr1());
            case 1:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "ActiveDeviceChanged. address=", logMessage.getStr1(), " profileId=");
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("DeviceDeleted. address=", logMessage.getStr1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("BluetoothStateChanged. state=", logMessage.getStr1());
            case 4:
                return "DeviceAttributesChanged.";
            case 5:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("DeviceConnectionStateChanged. address=", logMessage.getStr1(), " state=", logMessage.getStr2());
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("AclConnectionStateChanged. address=", logMessage.getStr1(), " state=", logMessage.getStr2());
            case 7:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "DeviceBondStateChanged. address=", logMessage.getStr1(), " state=");
            default:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ProfileConnectionStateChanged. address=", str1, " state=", str2, " profileId=");
                m.append(int1);
                return m.toString();
        }
    }
}
