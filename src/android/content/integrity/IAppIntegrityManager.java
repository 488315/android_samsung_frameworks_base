package android.content.integrity;

import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IAppIntegrityManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.integrity.IAppIntegrityManager";

    public static class Default implements IAppIntegrityManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public String getCurrentRuleSetProvider() throws RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public String getCurrentRuleSetVersion() throws RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public ParceledListSlice<Rule> getCurrentRules() throws RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public List<String> getWhitelistedRuleProviders() throws RemoteException {
            return null;
        }

        @Override // android.content.integrity.IAppIntegrityManager
        public void updateRuleSet(String str, ParceledListSlice<Rule> parceledListSlice, IntentSender intentSender) throws RemoteException {
        }
    }

    String getCurrentRuleSetProvider() throws RemoteException;

    String getCurrentRuleSetVersion() throws RemoteException;

    ParceledListSlice<Rule> getCurrentRules() throws RemoteException;

    List<String> getWhitelistedRuleProviders() throws RemoteException;

    void updateRuleSet(String str, ParceledListSlice<Rule> parceledListSlice, IntentSender intentSender) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppIntegrityManager {
        static final int TRANSACTION_getCurrentRuleSetProvider = 3;
        static final int TRANSACTION_getCurrentRuleSetVersion = 2;
        static final int TRANSACTION_getCurrentRules = 4;
        static final int TRANSACTION_getWhitelistedRuleProviders = 5;
        static final int TRANSACTION_updateRuleSet = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IAppIntegrityManager.DESCRIPTOR);
        }

        public static IAppIntegrityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAppIntegrityManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppIntegrityManager)) {
                return (IAppIntegrityManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "updateRuleSet";
            }
            if (i == 2) {
                return "getCurrentRuleSetVersion";
            }
            if (i == 3) {
                return "getCurrentRuleSetProvider";
            }
            if (i == 4) {
                return "getCurrentRules";
            }
            if (i != 5) {
                return null;
            }
            return "getWhitelistedRuleProviders";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppIntegrityManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppIntegrityManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                ParceledListSlice<Rule> parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                parcel.enforceNoDataAvail();
                updateRuleSet(string, parceledListSlice, intentSender);
                parcel2.writeNoException();
            } else if (i == 2) {
                String currentRuleSetVersion = getCurrentRuleSetVersion();
                parcel2.writeNoException();
                parcel2.writeString(currentRuleSetVersion);
            } else if (i == 3) {
                String currentRuleSetProvider = getCurrentRuleSetProvider();
                parcel2.writeNoException();
                parcel2.writeString(currentRuleSetProvider);
            } else if (i == 4) {
                ParceledListSlice<Rule> currentRules = getCurrentRules();
                parcel2.writeNoException();
                parcel2.writeTypedObject(currentRules, 1);
            } else if (i == 5) {
                List<String> whitelistedRuleProviders = getWhitelistedRuleProviders();
                parcel2.writeNoException();
                parcel2.writeStringList(whitelistedRuleProviders);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAppIntegrityManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppIntegrityManager.DESCRIPTOR;
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public void updateRuleSet(String str, ParceledListSlice<Rule> parceledListSlice, IntentSender intentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppIntegrityManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public String getCurrentRuleSetVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public String getCurrentRuleSetProvider() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public ParceledListSlice<Rule> getCurrentRules() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.integrity.IAppIntegrityManager
            public List<String> getWhitelistedRuleProviders() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppIntegrityManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
