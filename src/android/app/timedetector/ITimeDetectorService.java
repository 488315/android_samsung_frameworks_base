package android.app.timedetector;

import android.app.time.ExternalTimeSuggestion;
import android.app.time.ITimeDetectorListener;
import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeConfiguration;
import android.app.time.TimeState;
import android.app.time.UnixEpochTime;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITimeDetectorService extends IInterface {
    public static final String DESCRIPTOR = "android.app.timedetector.ITimeDetectorService";

    public static class Default implements ITimeDetectorService {
        @Override // android.app.timedetector.ITimeDetectorService
        public void addListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean confirmTime(UnixEpochTime unixEpochTime) throws RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public TimeCapabilitiesAndConfig getCapabilitiesAndConfig() throws RemoteException {
            return null;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public TimeState getTimeState() throws RemoteException {
            return null;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public UnixEpochTime latestNetworkTime() throws RemoteException {
            return null;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void removeListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean setManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) throws RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean suggestManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) throws RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean updateConfiguration(TimeConfiguration timeConfiguration) throws RemoteException {
            return false;
        }
    }

    void addListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException;

    boolean confirmTime(UnixEpochTime unixEpochTime) throws RemoteException;

    TimeCapabilitiesAndConfig getCapabilitiesAndConfig() throws RemoteException;

    TimeState getTimeState() throws RemoteException;

    UnixEpochTime latestNetworkTime() throws RemoteException;

    void removeListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException;

    boolean setManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException;

    void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) throws RemoteException;

    boolean suggestManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException;

    void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) throws RemoteException;

    boolean updateConfiguration(TimeConfiguration timeConfiguration) throws RemoteException;

    public static abstract class Stub extends Binder implements ITimeDetectorService {
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_confirmTime = 6;
        static final int TRANSACTION_getCapabilitiesAndConfig = 1;
        static final int TRANSACTION_getTimeState = 5;
        static final int TRANSACTION_latestNetworkTime = 11;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setManualTime = 7;
        static final int TRANSACTION_suggestExternalTime = 8;
        static final int TRANSACTION_suggestManualTime = 9;
        static final int TRANSACTION_suggestTelephonyTime = 10;
        static final int TRANSACTION_updateConfiguration = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, ITimeDetectorService.DESCRIPTOR);
        }

        public static ITimeDetectorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITimeDetectorService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITimeDetectorService)) {
                return (ITimeDetectorService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCapabilitiesAndConfig";
                case 2:
                    return "addListener";
                case 3:
                    return "removeListener";
                case 4:
                    return "updateConfiguration";
                case 5:
                    return "getTimeState";
                case 6:
                    return "confirmTime";
                case 7:
                    return "setManualTime";
                case 8:
                    return "suggestExternalTime";
                case 9:
                    return "suggestManualTime";
                case 10:
                    return "suggestTelephonyTime";
                case 11:
                    return "latestNetworkTime";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITimeDetectorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITimeDetectorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    TimeCapabilitiesAndConfig capabilitiesAndConfig = getCapabilitiesAndConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesAndConfig, 1);
                    return true;
                case 2:
                    ITimeDetectorListener asInterface = ITimeDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITimeDetectorListener asInterface2 = ITimeDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    TimeConfiguration timeConfiguration = (TimeConfiguration) parcel.readTypedObject(TimeConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(timeConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 5:
                    TimeState timeState = getTimeState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(timeState, 1);
                    return true;
                case 6:
                    UnixEpochTime unixEpochTime = (UnixEpochTime) parcel.readTypedObject(UnixEpochTime.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean confirmTime = confirmTime(unixEpochTime);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(confirmTime);
                    return true;
                case 7:
                    ManualTimeSuggestion manualTimeSuggestion = (ManualTimeSuggestion) parcel.readTypedObject(ManualTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean manualTime = setManualTime(manualTimeSuggestion);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(manualTime);
                    return true;
                case 8:
                    ExternalTimeSuggestion externalTimeSuggestion = (ExternalTimeSuggestion) parcel.readTypedObject(ExternalTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestExternalTime(externalTimeSuggestion);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ManualTimeSuggestion manualTimeSuggestion2 = (ManualTimeSuggestion) parcel.readTypedObject(ManualTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean suggestManualTime = suggestManualTime(manualTimeSuggestion2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(suggestManualTime);
                    return true;
                case 10:
                    TelephonyTimeSuggestion telephonyTimeSuggestion = (TelephonyTimeSuggestion) parcel.readTypedObject(TelephonyTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestTelephonyTime(telephonyTimeSuggestion);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    UnixEpochTime latestNetworkTime = latestNetworkTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(latestNetworkTime, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITimeDetectorService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeDetectorService.DESCRIPTOR;
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public TimeCapabilitiesAndConfig getCapabilitiesAndConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TimeCapabilitiesAndConfig) obtain2.readTypedObject(TimeCapabilitiesAndConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void addListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeDetectorListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void removeListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeDetectorListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean updateConfiguration(TimeConfiguration timeConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(timeConfiguration, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public TimeState getTimeState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TimeState) obtain2.readTypedObject(TimeState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean confirmTime(UnixEpochTime unixEpochTime) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(unixEpochTime, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean setManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(manualTimeSuggestion, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(externalTimeSuggestion, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean suggestManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(manualTimeSuggestion, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(telephonyTimeSuggestion, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public UnixEpochTime latestNetworkTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UnixEpochTime) obtain2.readTypedObject(UnixEpochTime.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
