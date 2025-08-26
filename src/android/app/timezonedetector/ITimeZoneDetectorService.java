package android.app.timezonedetector;

import android.app.time.ITimeZoneDetectorListener;
import android.app.time.TimeZoneCapabilitiesAndConfig;
import android.app.time.TimeZoneConfiguration;
import android.app.time.TimeZoneState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITimeZoneDetectorService extends IInterface {
    public static final String DESCRIPTOR = "android.app.timezonedetector.ITimeZoneDetectorService";

    public static class Default implements ITimeZoneDetectorService {
        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public void addListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean confirmTimeZone(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() throws RemoteException {
            return null;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public TimeZoneState getTimeZoneState() throws RemoteException {
            return null;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public void removeListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) throws RemoteException {
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean setManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean suggestManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws RemoteException {
            return false;
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public void suggestTelephonyTimeZone(TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) throws RemoteException {
        }

        @Override // android.app.timezonedetector.ITimeZoneDetectorService
        public boolean updateConfiguration(TimeZoneConfiguration timeZoneConfiguration) throws RemoteException {
            return false;
        }
    }

    void addListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) throws RemoteException;

    boolean confirmTimeZone(String str) throws RemoteException;

    TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() throws RemoteException;

    TimeZoneState getTimeZoneState() throws RemoteException;

    void removeListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) throws RemoteException;

    boolean setManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws RemoteException;

    boolean suggestManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws RemoteException;

    void suggestTelephonyTimeZone(TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) throws RemoteException;

    boolean updateConfiguration(TimeZoneConfiguration timeZoneConfiguration) throws RemoteException;

    public static abstract class Stub extends Binder implements ITimeZoneDetectorService {
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_confirmTimeZone = 6;
        static final int TRANSACTION_getCapabilitiesAndConfig = 1;
        static final int TRANSACTION_getTimeZoneState = 5;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setManualTimeZone = 7;
        static final int TRANSACTION_suggestManualTimeZone = 8;
        static final int TRANSACTION_suggestTelephonyTimeZone = 9;
        static final int TRANSACTION_updateConfiguration = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ITimeZoneDetectorService.DESCRIPTOR);
        }

        public static ITimeZoneDetectorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITimeZoneDetectorService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITimeZoneDetectorService)) {
                return (ITimeZoneDetectorService) iInterfaceQueryLocalInterface;
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
                    return "getTimeZoneState";
                case 6:
                    return "confirmTimeZone";
                case 7:
                    return "setManualTimeZone";
                case 8:
                    return "suggestManualTimeZone";
                case 9:
                    return "suggestTelephonyTimeZone";
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
                parcel.enforceInterface(ITimeZoneDetectorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITimeZoneDetectorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    TimeZoneCapabilitiesAndConfig capabilitiesAndConfig = getCapabilitiesAndConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesAndConfig, 1);
                    return true;
                case 2:
                    ITimeZoneDetectorListener iTimeZoneDetectorListenerAsInterface = ITimeZoneDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addListener(iTimeZoneDetectorListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITimeZoneDetectorListener iTimeZoneDetectorListenerAsInterface2 = ITimeZoneDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(iTimeZoneDetectorListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    TimeZoneConfiguration timeZoneConfiguration = (TimeZoneConfiguration) parcel.readTypedObject(TimeZoneConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateConfiguration = updateConfiguration(timeZoneConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateConfiguration);
                    return true;
                case 5:
                    TimeZoneState timeZoneState = getTimeZoneState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(timeZoneState, 1);
                    return true;
                case 6:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zConfirmTimeZone = confirmTimeZone(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConfirmTimeZone);
                    return true;
                case 7:
                    ManualTimeZoneSuggestion manualTimeZoneSuggestion = (ManualTimeZoneSuggestion) parcel.readTypedObject(ManualTimeZoneSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean manualTimeZone = setManualTimeZone(manualTimeZoneSuggestion);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(manualTimeZone);
                    return true;
                case 8:
                    ManualTimeZoneSuggestion manualTimeZoneSuggestion2 = (ManualTimeZoneSuggestion) parcel.readTypedObject(ManualTimeZoneSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSuggestManualTimeZone = suggestManualTimeZone(manualTimeZoneSuggestion2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSuggestManualTimeZone);
                    return true;
                case 9:
                    TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion = (TelephonyTimeZoneSuggestion) parcel.readTypedObject(TelephonyTimeZoneSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestTelephonyTimeZone(telephonyTimeZoneSuggestion);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITimeZoneDetectorService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeZoneDetectorService.DESCRIPTOR;
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TimeZoneCapabilitiesAndConfig) parcelObtain2.readTypedObject(TimeZoneCapabilitiesAndConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public void addListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTimeZoneDetectorListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public void removeListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTimeZoneDetectorListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean updateConfiguration(TimeZoneConfiguration timeZoneConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(timeZoneConfiguration, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public TimeZoneState getTimeZoneState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TimeZoneState) parcelObtain2.readTypedObject(TimeZoneState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean confirmTimeZone(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean setManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(manualTimeZoneSuggestion, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public boolean suggestManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(manualTimeZoneSuggestion, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.timezonedetector.ITimeZoneDetectorService
            public void suggestTelephonyTimeZone(TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITimeZoneDetectorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(telephonyTimeZoneSuggestion, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
