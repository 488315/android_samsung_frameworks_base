package android.hardware.thermal.V2_0;

import android.hardware.thermal.V1_0.CpuUsage;
import android.hardware.thermal.V1_0.IThermal;
import android.hardware.thermal.V1_0.ThermalStatus;
import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IThermal extends android.hardware.thermal.V1_0.IThermal {
    public static final String kInterfaceName = "android.hardware.thermal@2.0::IThermal";

    @FunctionalInterface
    public interface getCurrentCoolingDevicesCallback {
        void onValues(ThermalStatus thermalStatus, ArrayList<CoolingDevice> arrayList);
    }

    @FunctionalInterface
    public interface getCurrentTemperaturesCallback {
        void onValues(ThermalStatus thermalStatus, ArrayList<Temperature> arrayList);
    }

    @FunctionalInterface
    public interface getTemperatureThresholdsCallback {
        void onValues(ThermalStatus thermalStatus, ArrayList<TemperatureThreshold> arrayList);
    }

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void getCurrentCoolingDevices(boolean z, int i, getCurrentCoolingDevicesCallback getcurrentcoolingdevicescallback) throws RemoteException;

    void getCurrentTemperatures(boolean z, int i, getCurrentTemperaturesCallback getcurrenttemperaturescallback) throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getTemperatureThresholds(boolean z, int i, getTemperatureThresholdsCallback gettemperaturethresholdscallback) throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    ThermalStatus registerThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback, boolean z, int i) throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    ThermalStatus unregisterThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException;

    static IThermal asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IThermal)) {
            return (IThermal) iHwInterfaceQueryLocalInterface;
        }
        Proxy proxy = new Proxy(iHwBinder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    static IThermal castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IThermal getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IThermal getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IThermal getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IThermal getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IThermal {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.thermal@2.0::IThermal]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getTemperatures(IThermal.getTemperaturesCallback gettemperaturescallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                gettemperaturescallback.onValues(thermalStatus, android.hardware.thermal.V1_0.Temperature.readVectorFromParcel(hwParcel2));
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getCpuUsages(IThermal.getCpuUsagesCallback getcpuusagescallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                getcpuusagescallback.onValues(thermalStatus, CpuUsage.readVectorFromParcel(hwParcel2));
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V1_0.IThermal
        public void getCoolingDevices(IThermal.getCoolingDevicesCallback getcoolingdevicescallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                getcoolingdevicescallback.onValues(thermalStatus, android.hardware.thermal.V1_0.CoolingDevice.readVectorFromParcel(hwParcel2));
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public void getCurrentTemperatures(boolean z, int i, getCurrentTemperaturesCallback getcurrenttemperaturescallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IThermal.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                getcurrenttemperaturescallback.onValues(thermalStatus, Temperature.readVectorFromParcel(hwParcel2));
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public void getTemperatureThresholds(boolean z, int i, getTemperatureThresholdsCallback gettemperaturethresholdscallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IThermal.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                gettemperaturethresholdscallback.onValues(thermalStatus, TemperatureThreshold.readVectorFromParcel(hwParcel2));
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public ThermalStatus registerThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback, boolean z, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IThermal.kInterfaceName);
            hwParcel.writeStrongBinder(iThermalChangedCallback == null ? null : iThermalChangedCallback.asBinder());
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                return thermalStatus;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public ThermalStatus unregisterThermalChangedCallback(IThermalChangedCallback iThermalChangedCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IThermal.kInterfaceName);
            hwParcel.writeStrongBinder(iThermalChangedCallback == null ? null : iThermalChangedCallback.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                return thermalStatus;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal
        public void getCurrentCoolingDevices(boolean z, int i, getCurrentCoolingDevicesCallback getcurrentcoolingdevicescallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IThermal.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ThermalStatus thermalStatus = new ThermalStatus();
                thermalStatus.readFromParcel(hwParcel2);
                getcurrentcoolingdevicescallback.onValues(thermalStatus, CoolingDevice.readVectorFromParcel(hwParcel2));
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public String interfaceDescriptor() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
                HwBlob buffer = hwParcel2.readBuffer(16L);
                int int32 = buffer.getInt32(8L);
                HwBlob embeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    embeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public void ping() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IThermal {
        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IThermal.kInterfaceName, android.hardware.thermal.V1_0.IThermal.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IThermal.kInterfaceName;
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-67, -120, -76, -122, 57, -54, -29, 9, -126, 2, 16, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -30, 35, 113, 7, 108, 118, -6, -88, 70, 110, 56, -54, 89, -123, 41, 69, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, SprAttributeBase.TYPE_ANIMATOR_SET, -114, -82}, new byte[]{-105, MidiConstants.STATUS_MIDI_TIME_CODE, -20, 68, SprAttributeBase.TYPE_DURATION, 67, -68, 90, 102, 69, -73, 69, 41, -90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 100, -106, -67, -77, 94, 10, -18, 65, -19, -91, 92, -71, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 81, -21, 120, 2}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.thermal.V2_0.IThermal, android.hardware.thermal.V1_0.IThermal, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IThermal.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String str) throws RemoteException {
            registerService(str);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                    getTemperatures(new IThermal.getTemperaturesCallback(this) { // from class: android.hardware.thermal.V2_0.IThermal.Stub.1
                        @Override // android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback
                        public void onValues(ThermalStatus thermalStatus, ArrayList<android.hardware.thermal.V1_0.Temperature> arrayList) {
                            hwParcel2.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel2);
                            android.hardware.thermal.V1_0.Temperature.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                    getCpuUsages(new IThermal.getCpuUsagesCallback(this) { // from class: android.hardware.thermal.V2_0.IThermal.Stub.2
                        @Override // android.hardware.thermal.V1_0.IThermal.getCpuUsagesCallback
                        public void onValues(ThermalStatus thermalStatus, ArrayList<CpuUsage> arrayList) {
                            hwParcel2.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel2);
                            CpuUsage.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.thermal.V1_0.IThermal.kInterfaceName);
                    getCoolingDevices(new IThermal.getCoolingDevicesCallback(this) { // from class: android.hardware.thermal.V2_0.IThermal.Stub.3
                        @Override // android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback
                        public void onValues(ThermalStatus thermalStatus, ArrayList<android.hardware.thermal.V1_0.CoolingDevice> arrayList) {
                            hwParcel2.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel2);
                            android.hardware.thermal.V1_0.CoolingDevice.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 4:
                    hwParcel.enforceInterface(IThermal.kInterfaceName);
                    getCurrentTemperatures(hwParcel.readBool(), hwParcel.readInt32(), new getCurrentTemperaturesCallback(this) { // from class: android.hardware.thermal.V2_0.IThermal.Stub.4
                        @Override // android.hardware.thermal.V2_0.IThermal.getCurrentTemperaturesCallback
                        public void onValues(ThermalStatus thermalStatus, ArrayList<Temperature> arrayList) {
                            hwParcel2.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel2);
                            Temperature.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 5:
                    hwParcel.enforceInterface(IThermal.kInterfaceName);
                    getTemperatureThresholds(hwParcel.readBool(), hwParcel.readInt32(), new getTemperatureThresholdsCallback(this) { // from class: android.hardware.thermal.V2_0.IThermal.Stub.5
                        @Override // android.hardware.thermal.V2_0.IThermal.getTemperatureThresholdsCallback
                        public void onValues(ThermalStatus thermalStatus, ArrayList<TemperatureThreshold> arrayList) {
                            hwParcel2.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel2);
                            TemperatureThreshold.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 6:
                    hwParcel.enforceInterface(IThermal.kInterfaceName);
                    ThermalStatus thermalStatusRegisterThermalChangedCallback = registerThermalChangedCallback(IThermalChangedCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readBool(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    thermalStatusRegisterThermalChangedCallback.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(IThermal.kInterfaceName);
                    ThermalStatus thermalStatusUnregisterThermalChangedCallback = unregisterThermalChangedCallback(IThermalChangedCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    thermalStatusUnregisterThermalChangedCallback.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(IThermal.kInterfaceName);
                    getCurrentCoolingDevices(hwParcel.readBool(), hwParcel.readInt32(), new getCurrentCoolingDevicesCallback(this) { // from class: android.hardware.thermal.V2_0.IThermal.Stub.6
                        @Override // android.hardware.thermal.V2_0.IThermal.getCurrentCoolingDevicesCallback
                        public void onValues(ThermalStatus thermalStatus, ArrayList<CoolingDevice> arrayList) {
                            hwParcel2.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel2);
                            CoolingDevice.writeVectorToParcel(hwParcel2, arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                default:
                    switch (i) {
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<String> arrayListInterfaceChain = interfaceChain();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeStringVector(arrayListInterfaceChain);
                            hwParcel2.send();
                            return;
                        case 256131655:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            String strInterfaceDescriptor = interfaceDescriptor();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(strInterfaceDescriptor);
                            hwParcel2.send();
                            return;
                        case 256398152:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<byte[]> hashChain = getHashChain();
                            hwParcel2.writeStatus(0);
                            HwBlob hwBlob = new HwBlob(16);
                            int size = hashChain.size();
                            hwBlob.putInt32(8L, size);
                            hwBlob.putBool(12L, false);
                            HwBlob hwBlob2 = new HwBlob(size * 32);
                            for (int i3 = 0; i3 < size; i3++) {
                                long j = i3 * 32;
                                byte[] bArr = hashChain.get(i3);
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
                            setHALInstrumentation();
                            return;
                        case 256921159:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ping();
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
                            notifySyspropsChanged();
                            return;
                        default:
                            return;
                    }
            }
        }
    }
}
