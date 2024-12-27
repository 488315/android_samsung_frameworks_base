package vendor.samsung.hardware.health.V2_0;

import android.hardware.health.V2_0.HealthInfo;
import android.hardware.health.V2_0.IHealthInfoCallback;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import com.android.server.health.HealthHalCallbackHidl;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.att.iqi.lib.metrics.mm.MM05;
import java.util.ArrayList;
import java.util.Arrays;
import vendor.samsung.hardware.health.Translate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ISehHealthInfoCallback$Stub extends HwBinder implements IHealthInfoCallback {
    @Override // android.hidl.base.V1_0.IBase
    public final IHwBinder asBinder() {
        return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
    }

    @Override // android.hidl.base.V1_0.IBase
    public final DebugInfo getDebugInfo() {
        DebugInfo debugInfo = new DebugInfo();
        debugInfo.pid = HidlSupport.getPidIfSharable();
        debugInfo.ptr = 0L;
        debugInfo.arch = 0;
        return debugInfo;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
        return new ArrayList(Arrays.asList(new byte[]{-16, 51, 72, 46, 117, -51, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, -91, -77, 49, 14, -117, -19, 72, -12, -26, 20, -40, -89, 109, -125, -98, -82, -114, 73, -9, 109, 54, -54, 96, 27, -43}, new byte[]{38, -16, 69, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, -96, -75, 122, -70, 81, 103, -59, -64, -89, -62, -16, 119, -62, -84, -69, -104, -72, 25, 2, -96, 114, 81, 120, 41, -3, -97, -42, Byte.MAX_VALUE}, new byte[]{67, 76, 76, 50, -64, MM05.IQ_SIP_CALL_STATE_DISCONNECTING, 14, 84, -69, 5, -28, 12, 121, 80, 50, 8, -76, 15, 120, 106, 49, Byte.MIN_VALUE, 41, -94, -92, -10, 110, 52, -15, 15, 42, 118}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
        return new ArrayList(Arrays.asList("vendor.samsung.hardware.health@2.0::ISehHealthInfoCallback", "android.hardware.health@2.1::IHealthInfoCallback", "android.hardware.health@2.0::IHealthInfoCallback", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
        return "vendor.samsung.hardware.health@2.0::ISehHealthInfoCallback";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
        return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
        HwBinder.enableInstrumentation();
    }

    public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
        if (i == 1) {
            hwParcel.enforceInterface("android.hardware.health@2.0::IHealthInfoCallback");
            HealthInfo healthInfo = new HealthInfo();
            healthInfo.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L));
            SehHealthInfo sehHealthInfo = new SehHealthInfo();
            android.hardware.health.V2_1.HealthInfo healthInfo2 = sehHealthInfo.legacy;
            healthInfo2.legacy = healthInfo;
            healthInfo2.batteryCapacityLevel = -1;
            healthInfo2.batteryChargeTimeToFullNowSeconds = -1L;
            ((HealthHalCallbackHidl) this).mCallback.update(Translate.h2saTranslate(sehHealthInfo));
            return;
        }
        if (i == 2) {
            hwParcel.enforceInterface("android.hardware.health@2.1::IHealthInfoCallback");
            android.hardware.health.V2_1.HealthInfo healthInfo3 = new android.hardware.health.V2_1.HealthInfo();
            healthInfo3.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(136L));
            SehHealthInfo sehHealthInfo2 = new SehHealthInfo();
            sehHealthInfo2.legacy = healthInfo3;
            ((HealthHalCallbackHidl) this).mCallback.update(Translate.h2saTranslate(sehHealthInfo2));
            return;
        }
        if (i == 3) {
            hwParcel.enforceInterface("vendor.samsung.hardware.health@2.0::ISehHealthInfoCallback");
            SehHealthInfo sehHealthInfo3 = new SehHealthInfo();
            HwBlob readBuffer = hwParcel.readBuffer(176L);
            sehHealthInfo3.legacy.readEmbeddedFromParcel(hwParcel, readBuffer);
            sehHealthInfo3.batteryCurrentNow = readBuffer.getInt32(136L);
            sehHealthInfo3.batteryOnline = readBuffer.getInt32(140L);
            sehHealthInfo3.batteryChargeType = readBuffer.getInt32(144L);
            sehHealthInfo3.batteryPowerSharingOnline = readBuffer.getBool(148L);
            sehHealthInfo3.chargerPogoOnline = readBuffer.getBool(149L);
            sehHealthInfo3.batteryHighVoltageCharger = readBuffer.getInt32(152L);
            sehHealthInfo3.batteryEvent = readBuffer.getInt32(156L);
            sehHealthInfo3.batteryCurrentEvent = readBuffer.getInt32(160L);
            sehHealthInfo3.chargerOtgOnline = readBuffer.getBool(164L);
            sehHealthInfo3.wirelessPowerSharingTxEvent = readBuffer.getInt32(168L);
            ((HealthHalCallbackHidl) this).mCallback.update(Translate.h2saTranslate(sehHealthInfo3));
            return;
        }
        switch (i) {
            case 256067662:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                ArrayList interfaceChain = interfaceChain();
                hwParcel2.writeStatus(0);
                hwParcel2.writeStringVector(interfaceChain);
                hwParcel2.send();
                return;
            case 256131655:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                hwParcel.readNativeHandle();
                hwParcel.readStringVector();
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            case 256136003:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                hwParcel2.writeStatus(0);
                hwParcel2.writeString("vendor.samsung.hardware.health@2.0::ISehHealthInfoCallback");
                hwParcel2.send();
                return;
            case 256398152:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                ArrayList hashChain = getHashChain();
                hwParcel2.writeStatus(0);
                HwBlob hwBlob = new HwBlob(16);
                int size = hashChain.size();
                hwBlob.putInt32(8L, size);
                hwBlob.putBool(12L, false);
                HwBlob hwBlob2 = new HwBlob(size * 32);
                for (int i3 = 0; i3 < size; i3++) {
                    long j = i3 * 32;
                    byte[] bArr = (byte[]) hashChain.get(i3);
                    if (bArr == null || bArr.length != 32) {
                        throw new IllegalArgumentException("Array element is not of the expected length");
                    }
                    hwBlob2.putInt8Array(j, bArr);
                }
                hwBlob.putBlob(0L, hwBlob2);
                hwParcel2.writeBuffer(hwBlob);
                hwParcel2.send();
                return;
            case 256462420:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                return;
            case 256921159:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            case 257049926:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                DebugInfo debugInfo = getDebugInfo();
                hwParcel2.writeStatus(0);
                debugInfo.writeToParcel(hwParcel2);
                hwParcel2.send();
                return;
            case 257120595:
                hwParcel.enforceInterface(IBase.kInterfaceName);
                HwBinder.enableInstrumentation();
                return;
            default:
                return;
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {
    }

    public final IHwInterface queryLocalInterface(String str) {
        if ("vendor.samsung.hardware.health@2.0::ISehHealthInfoCallback".equals(str)) {
            return this;
        }
        return null;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {
    }

    public final String toString() {
        return "vendor.samsung.hardware.health@2.0::ISehHealthInfoCallback@Stub";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return true;
    }
}
