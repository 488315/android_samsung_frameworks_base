package android.hardware.radio;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes2.dex */
public interface ITunerCallback extends IInterface {

    public static class Default implements ITunerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onAntennaState(boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onBackgroundScanAvailabilityChange(boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onBackgroundScanComplete() throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onConfigFlagUpdated(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onConfigurationChanged(RadioManager.BandConfig bandConfig) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onEmergencyAnnouncement(boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onParametersUpdated(Map<String, String> map) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onProgramListChanged() throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onTrafficAnnouncement(boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onTuneFailed(int i, ProgramSelector programSelector) throws RemoteException {
        }
    }

    void onAntennaState(boolean z) throws RemoteException;

    void onBackgroundScanAvailabilityChange(boolean z) throws RemoteException;

    void onBackgroundScanComplete() throws RemoteException;

    void onConfigFlagUpdated(int i, boolean z) throws RemoteException;

    void onConfigurationChanged(RadioManager.BandConfig bandConfig) throws RemoteException;

    void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) throws RemoteException;

    void onEmergencyAnnouncement(boolean z) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onParametersUpdated(Map<String, String> map) throws RemoteException;

    void onProgramListChanged() throws RemoteException;

    void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException;

    void onTrafficAnnouncement(boolean z) throws RemoteException;

    void onTuneFailed(int i, ProgramSelector programSelector) throws RemoteException;

    public static abstract class Stub extends Binder implements ITunerCallback {
        public static final String DESCRIPTOR = "android.hardware.radio.ITunerCallback";
        static final int TRANSACTION_onAntennaState = 7;
        static final int TRANSACTION_onBackgroundScanAvailabilityChange = 8;
        static final int TRANSACTION_onBackgroundScanComplete = 9;
        static final int TRANSACTION_onConfigFlagUpdated = 12;
        static final int TRANSACTION_onConfigurationChanged = 3;
        static final int TRANSACTION_onCurrentProgramInfoChanged = 4;
        static final int TRANSACTION_onEmergencyAnnouncement = 6;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onParametersUpdated = 13;
        static final int TRANSACTION_onProgramListChanged = 10;
        static final int TRANSACTION_onProgramListUpdated = 11;
        static final int TRANSACTION_onTrafficAnnouncement = 5;
        static final int TRANSACTION_onTuneFailed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITunerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITunerCallback)) {
                return (ITunerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onError";
                case 2:
                    return "onTuneFailed";
                case 3:
                    return "onConfigurationChanged";
                case 4:
                    return "onCurrentProgramInfoChanged";
                case 5:
                    return "onTrafficAnnouncement";
                case 6:
                    return "onEmergencyAnnouncement";
                case 7:
                    return "onAntennaState";
                case 8:
                    return "onBackgroundScanAvailabilityChange";
                case 9:
                    return "onBackgroundScanComplete";
                case 10:
                    return "onProgramListChanged";
                case 11:
                    return "onProgramListUpdated";
                case 12:
                    return "onConfigFlagUpdated";
                case 13:
                    return "onParametersUpdated";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    ProgramSelector programSelector = (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTuneFailed(readInt2, programSelector);
                    return true;
                case 3:
                    RadioManager.BandConfig bandConfig = (RadioManager.BandConfig) parcel.readTypedObject(RadioManager.BandConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConfigurationChanged(bandConfig);
                    return true;
                case 4:
                    RadioManager.ProgramInfo programInfo = (RadioManager.ProgramInfo) parcel.readTypedObject(RadioManager.ProgramInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCurrentProgramInfoChanged(programInfo);
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTrafficAnnouncement(readBoolean);
                    return true;
                case 6:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEmergencyAnnouncement(readBoolean2);
                    return true;
                case 7:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAntennaState(readBoolean3);
                    return true;
                case 8:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBackgroundScanAvailabilityChange(readBoolean4);
                    return true;
                case 9:
                    onBackgroundScanComplete();
                    return true;
                case 10:
                    onProgramListChanged();
                    return true;
                case 11:
                    ProgramList.Chunk chunk = (ProgramList.Chunk) parcel.readTypedObject(ProgramList.Chunk.CREATOR);
                    parcel.enforceNoDataAvail();
                    onProgramListUpdated(chunk);
                    return true;
                case 12:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfigFlagUpdated(readInt3, readBoolean5);
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    final HashMap hashMap = readInt4 < 0 ? null : new HashMap();
                    IntStream.range(0, readInt4).forEach(new IntConsumer() { // from class: android.hardware.radio.ITunerCallback$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    onParametersUpdated(hashMap);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements ITunerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onTuneFailed(int i, ProgramSelector programSelector) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(programSelector, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onConfigurationChanged(RadioManager.BandConfig bandConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bandConfig, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(programInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onTrafficAnnouncement(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onEmergencyAnnouncement(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onAntennaState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onBackgroundScanAvailabilityChange(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onBackgroundScanComplete() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onProgramListChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onProgramListUpdated(ProgramList.Chunk chunk) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(chunk, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onConfigFlagUpdated(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onParametersUpdated(Map<String, String> map) throws RemoteException {
                final Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.radio.ITunerCallback$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ITunerCallback.Stub.Proxy.lambda$onParametersUpdated$0(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$onParametersUpdated$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }
        }
    }
}
