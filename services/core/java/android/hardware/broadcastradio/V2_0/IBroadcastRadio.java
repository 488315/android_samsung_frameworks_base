package android.hardware.broadcastradio.V2_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.server.broadcastradio.hal2.Convert;
import com.android.server.broadcastradio.hal2.Mutable;
import com.android.server.broadcastradio.hal2.RadioModule;
import com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda0;
import com.android.server.broadcastradio.hal2.RadioModule$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IBroadcastRadio extends IBase {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Proxy implements IBroadcastRadio {
        public IHwBinder mRemote;

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName, nativeHandle, arrayList);
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

        public final void getAmFmRegionConfig(RadioModule$$ExternalSyntheticLambda2 radioModule$$ExternalSyntheticLambda2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::IBroadcastRadio");
            hwParcel.writeBool(false);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                int readInt32 = hwParcel2.readInt32();
                AmFmRegionConfig amFmRegionConfig = new AmFmRegionConfig();
                amFmRegionConfig.ranges = new ArrayList();
                amFmRegionConfig.readFromParcel(hwParcel2);
                if (readInt32 == 0) {
                    ((Mutable) radioModule$$ExternalSyntheticLambda2.f$0).value = amFmRegionConfig;
                }
            } finally {
                hwParcel2.release();
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
                HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
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

        public final Properties getProperties() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.broadcastradio@2.0::IBroadcastRadio");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                Properties properties = new Properties();
                properties.readFromParcel(hwParcel);
                return properties;
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

        public final void openSession(RadioModule.AnonymousClass1 anonymousClass1, RadioModule$$ExternalSyntheticLambda0 radioModule$$ExternalSyntheticLambda0) {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m("android.hardware.broadcastradio@2.0::IBroadcastRadio");
            ITunerSession$Proxy iTunerSession$Proxy = null;
            if (anonymousClass1 == null) {
                anonymousClass1 = null;
            }
            m.writeStrongBinder(anonymousClass1);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(4, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                int readInt32 = hwParcel.readInt32();
                IHwBinder readStrongBinder = hwParcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IHwInterface queryLocalInterface = readStrongBinder.queryLocalInterface("android.hardware.broadcastradio@2.0::ITunerSession");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof ITunerSession$Proxy)) {
                        ITunerSession$Proxy iTunerSession$Proxy2 = new ITunerSession$Proxy();
                        iTunerSession$Proxy2.mRemote = readStrongBinder;
                        try {
                            Iterator it = iTunerSession$Proxy2.interfaceChain().iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                } else if (((String) it.next()).equals("android.hardware.broadcastradio@2.0::ITunerSession")) {
                                    iTunerSession$Proxy = iTunerSession$Proxy2;
                                    break;
                                }
                            }
                        } catch (RemoteException unused) {
                        }
                    } else {
                        iTunerSession$Proxy = (ITunerSession$Proxy) queryLocalInterface;
                    }
                }
                RadioModule radioModule = radioModule$$ExternalSyntheticLambda0.f$0;
                radioModule.getClass();
                Convert.throwOnError(readInt32, "openSession");
                radioModule$$ExternalSyntheticLambda0.f$1.value = iTunerSession$Proxy;
                radioModule.mEventLogger.logRadioEvent("New HIDL 2.0 tuner session is opened", new Object[0]);
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
                return "[class or subclass of android.hardware.broadcastradio@2.0::IBroadcastRadio]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }
}
