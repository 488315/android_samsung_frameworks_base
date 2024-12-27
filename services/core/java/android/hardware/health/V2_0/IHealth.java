package android.hardware.health.V2_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.health.Translate;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.BatteryProperty;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.util.MutableInt;

import com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda1;
import com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda2;

import vendor.samsung.hardware.health.V2_0.ISehHealthInfoCallback$Stub;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IHealth extends IBase {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Proxy implements IHealth {
        public IHwBinder mRemote;

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            IBase.kInterfaceName, nativeHandle, arrayList);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256131655, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getCapacity(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(7, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                int readInt322 = hwParcel.readInt32();
                MutableInt mutableInt = healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$0;
                BatteryProperty batteryProperty =
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$1;
                mutableInt.value = readInt32;
                if (readInt32 == 0) {
                    batteryProperty.setLong(readInt322);
                }
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getChargeCounter(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(4, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                int readInt322 = hwParcel.readInt32();
                MutableInt mutableInt = healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$0;
                BatteryProperty batteryProperty =
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$1;
                mutableInt.value = readInt32;
                if (readInt32 == 0) {
                    batteryProperty.setLong(readInt322);
                }
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getChargeStatus(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(9, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                int readInt322 = hwParcel.readInt32();
                MutableInt mutableInt = healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$0;
                BatteryProperty batteryProperty =
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$1;
                mutableInt.value = readInt32;
                if (readInt32 == 0) {
                    batteryProperty.setLong(readInt322);
                }
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getCurrentAverage(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(6, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                int readInt322 = hwParcel.readInt32();
                MutableInt mutableInt = healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$0;
                BatteryProperty batteryProperty =
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$1;
                mutableInt.value = readInt32;
                if (readInt32 == 0) {
                    batteryProperty.setLong(readInt322);
                }
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getCurrentNow(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(5, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                int readInt322 = hwParcel.readInt32();
                MutableInt mutableInt = healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$0;
                BatteryProperty batteryProperty =
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$1;
                mutableInt.value = readInt32;
                if (readInt32 == 0) {
                    batteryProperty.setLong(readInt322);
                }
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257049926, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel);
                return debugInfo;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getEnergyCounter(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(8, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                long readInt64 = hwParcel.readInt64();
                MutableInt mutableInt = healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$0;
                BatteryProperty batteryProperty =
                        healthServiceWrapperHidl$$ExternalSyntheticLambda1.f$1;
                mutableInt.value = readInt32;
                if (readInt32 == 0) {
                    batteryProperty.setLong(readInt64);
                }
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer =
                        hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final void getHealthInfo(
                HealthServiceWrapperHidl$$ExternalSyntheticLambda2
                        healthServiceWrapperHidl$$ExternalSyntheticLambda2) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(12, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                HealthInfo healthInfo = new HealthInfo();
                healthInfo.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L));
                if (readInt32 == 0) {
                    android.hardware.health.V1_0.HealthInfo healthInfo2 = healthInfo.legacy;
                    android.hardware.health.HealthInfo healthInfo3 =
                            new android.hardware.health.HealthInfo();
                    Translate.h2aTranslateInternal(healthInfo3, healthInfo2);
                    healthServiceWrapperHidl$$ExternalSyntheticLambda2.f$0.value = healthInfo3;
                }
            } finally {
                hwParcel.release();
            }
        }

        public final int hashCode() {
            return this.mRemote.hashCode();
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256067662, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readStringVector();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256136003, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readString();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257120595, m, hwParcel, 1);
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256921159, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final int registerCallback(IHealthInfoCallback iHealthInfoCallback) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            m.writeStrongBinder(
                    iHealthInfoCallback == null
                            ? null
                            : (ISehHealthInfoCallback$Stub) iHealthInfoCallback);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256462420, m, hwParcel, 1);
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        public final String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.health@2.0::IHealth]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final int unregisterCallback(IHealthInfoCallback iHealthInfoCallback) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            m.writeStrongBinder(
                    iHealthInfoCallback == null
                            ? null
                            : (ISehHealthInfoCallback$Stub) iHealthInfoCallback);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(2, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.health.V2_0.IHealth
        public final int update() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.health@2.0::IHealth");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(3, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }
    }

    void getCapacity(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                    healthServiceWrapperHidl$$ExternalSyntheticLambda1);

    void getChargeCounter(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                    healthServiceWrapperHidl$$ExternalSyntheticLambda1);

    void getChargeStatus(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                    healthServiceWrapperHidl$$ExternalSyntheticLambda1);

    void getCurrentAverage(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                    healthServiceWrapperHidl$$ExternalSyntheticLambda1);

    void getCurrentNow(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                    healthServiceWrapperHidl$$ExternalSyntheticLambda1);

    void getEnergyCounter(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                    healthServiceWrapperHidl$$ExternalSyntheticLambda1);

    void getHealthInfo(
            HealthServiceWrapperHidl$$ExternalSyntheticLambda2
                    healthServiceWrapperHidl$$ExternalSyntheticLambda2);

    int registerCallback(IHealthInfoCallback iHealthInfoCallback);

    int unregisterCallback(IHealthInfoCallback iHealthInfoCallback);

    int update();
}
