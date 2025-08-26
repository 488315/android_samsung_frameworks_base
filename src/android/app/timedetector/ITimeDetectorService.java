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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITimeDetectorService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITimeDetectorService)) {
                return (ITimeDetectorService) iInterfaceQueryLocalInterface;
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
                    ITimeDetectorListener iTimeDetectorListenerAsInterface = ITimeDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addListener(iTimeDetectorListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITimeDetectorListener iTimeDetectorListenerAsInterface2 = ITimeDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(iTimeDetectorListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    TimeConfiguration timeConfiguration = (TimeConfiguration) parcel.readTypedObject(TimeConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateConfiguration = updateConfiguration(timeConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateConfiguration);
                    return true;
                case 5:
                    TimeState timeState = getTimeState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(timeState, 1);
                    return true;
                case 6:
                    UnixEpochTime unixEpochTime = (UnixEpochTime) parcel.readTypedObject(UnixEpochTime.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zConfirmTime = confirmTime(unixEpochTime);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConfirmTime);
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
                    boolean zSuggestManualTime = suggestManualTime(manualTimeSuggestion2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSuggestManualTime);
                    return true;
                case 10:
                    TelephonyTimeSuggestion telephonyTimeSuggestion = (TelephonyTimeSuggestion) parcel.readTypedObject(TelephonyTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestTelephonyTime(telephonyTimeSuggestion);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    UnixEpochTime unixEpochTimeLatestNetworkTime = latestNetworkTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(unixEpochTimeLatestNetworkTime, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TimeCapabilitiesAndConfig) parcelObtain2.readTypedObject(TimeCapabilitiesAndConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void addListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTimeDetectorListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void removeListener(ITimeDetectorListener iTimeDetectorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTimeDetectorListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean updateConfiguration(TimeConfiguration timeConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(timeConfiguration, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public TimeState getTimeState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TimeState) parcelObtain2.readTypedObject(TimeState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean confirmTime(UnixEpochTime unixEpochTime) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(unixEpochTime, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean setManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(manualTimeSuggestion, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(externalTimeSuggestion, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean suggestManualTime(ManualTimeSuggestion manualTimeSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(manualTimeSuggestion, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(telephonyTimeSuggestion, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public UnixEpochTime latestNetworkTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UnixEpochTime) parcelObtain2.readTypedObject(UnixEpochTime.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
