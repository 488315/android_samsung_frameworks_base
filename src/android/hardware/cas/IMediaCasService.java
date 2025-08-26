package android.hardware.cas;

import android.hardware.cas.ICas;
import android.hardware.cas.ICasListener;
import android.hardware.cas.IDescrambler;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMediaCasService extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$cas$IMediaCasService".replace('$', '.');
    public static final String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    IDescrambler createDescrambler(int i) throws RemoteException;

    ICas createPlugin(int i, ICasListener iCasListener) throws RemoteException;

    AidlCasPluginDescriptor[] enumeratePlugins() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean isDescramblerSupported(int i) throws RemoteException;

    boolean isSystemIdSupported(int i) throws RemoteException;

    public static class Default implements IMediaCasService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public IDescrambler createDescrambler(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public ICas createPlugin(int i, ICasListener iCasListener) throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public AidlCasPluginDescriptor[] enumeratePlugins() throws RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.IMediaCasService
        public boolean isDescramblerSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IMediaCasService
        public boolean isSystemIdSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IMediaCasService
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IMediaCasService {
        static final int TRANSACTION_createDescrambler = 1;
        static final int TRANSACTION_createPlugin = 2;
        static final int TRANSACTION_enumeratePlugins = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isDescramblerSupported = 4;
        static final int TRANSACTION_isSystemIdSupported = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaCasService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaCasService)) {
                return (IMediaCasService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                IDescrambler iDescramblerCreateDescrambler = createDescrambler(i3);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iDescramblerCreateDescrambler);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                ICasListener iCasListenerAsInterface = ICasListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ICas iCasCreatePlugin = createPlugin(i4, iCasListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iCasCreatePlugin);
            } else if (i == 3) {
                AidlCasPluginDescriptor[] aidlCasPluginDescriptorArrEnumeratePlugins = enumeratePlugins();
                parcel2.writeNoException();
                parcel2.writeTypedArray(aidlCasPluginDescriptorArrEnumeratePlugins, 1);
            } else if (i == 4) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zIsDescramblerSupported = isDescramblerSupported(i5);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsDescramblerSupported);
            } else if (i == 5) {
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zIsSystemIdSupported = isSystemIdSupported(i6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsSystemIdSupported);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaCasService {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.cas.IMediaCasService
            public IDescrambler createDescrambler(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method createDescrambler is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IDescrambler.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public ICas createPlugin(int i, ICasListener iCasListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCasListener);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method createPlugin is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICas.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public AidlCasPluginDescriptor[] enumeratePlugins() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method enumeratePlugins is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (AidlCasPluginDescriptor[]) parcelObtain2.createTypedArray(AidlCasPluginDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public boolean isDescramblerSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method isDescramblerSupported is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public boolean isSystemIdSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method isSystemIdSupported is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.cas.IMediaCasService
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
