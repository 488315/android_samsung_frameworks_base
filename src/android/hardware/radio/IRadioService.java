package android.hardware.radio;

import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IRadioService extends IInterface {

    public static class Default implements IRadioService {
        @Override // android.hardware.radio.IRadioService
        public ICloseHandle addAnnouncementListener(int[] iArr, IAnnouncementListener iAnnouncementListener) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.IRadioService
        public List<RadioManager.ModuleProperties> listModules() throws RemoteException {
            return null;
        }

        @Override // android.hardware.radio.IRadioService
        public ITuner openTuner(int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) throws RemoteException {
            return null;
        }
    }

    ICloseHandle addAnnouncementListener(int[] iArr, IAnnouncementListener iAnnouncementListener) throws RemoteException;

    List<RadioManager.ModuleProperties> listModules() throws RemoteException;

    ITuner openTuner(int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IRadioService {
        public static final String DESCRIPTOR = "android.hardware.radio.IRadioService";
        static final int TRANSACTION_addAnnouncementListener = 3;
        static final int TRANSACTION_listModules = 1;
        static final int TRANSACTION_openTuner = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioService)) {
                return (IRadioService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "listModules";
            }
            if (i == 2) {
                return "openTuner";
            }
            if (i != 3) {
                return null;
            }
            return "addAnnouncementListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                List<RadioManager.ModuleProperties> listModules = listModules();
                parcel2.writeNoException();
                parcel2.writeTypedList(listModules, 1);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                RadioManager.BandConfig bandConfig = (RadioManager.BandConfig) parcel.readTypedObject(RadioManager.BandConfig.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                ITunerCallback asInterface = ITunerCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ITuner openTuner = openTuner(readInt, bandConfig, readBoolean, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(openTuner);
            } else if (i == 3) {
                int[] createIntArray = parcel.createIntArray();
                IAnnouncementListener asInterface2 = IAnnouncementListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ICloseHandle addAnnouncementListener = addAnnouncementListener(createIntArray, asInterface2);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(addAnnouncementListener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRadioService {
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

            @Override // android.hardware.radio.IRadioService
            public List<RadioManager.ModuleProperties> listModules() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(RadioManager.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.IRadioService
            public ITuner openTuner(int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bandConfig, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iTunerCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITuner.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.IRadioService
            public ICloseHandle addAnnouncementListener(int[] iArr, IAnnouncementListener iAnnouncementListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAnnouncementListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICloseHandle.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
