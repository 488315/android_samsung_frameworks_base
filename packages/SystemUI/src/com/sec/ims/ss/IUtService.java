package com.sec.ims.ss;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.ss.IImsUtEventListener;

/* loaded from: classes4.dex */
public interface IUtService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ss.IUtService";

    void deRegisterForUtEvent(int i, IImsUtEventListener iImsUtEventListener) throws RemoteException;

    boolean isUtEnabled(int i) throws RemoteException;

    int queryCLIP(int i) throws RemoteException;

    int queryCLIR(int i) throws RemoteException;

    int queryCOLP(int i) throws RemoteException;

    int queryCOLR(int i) throws RemoteException;

    int queryCallBarring(int i, int i2, int i3) throws RemoteException;

    int queryCallForward(int i, int i2, String str) throws RemoteException;

    int queryCallWaiting(int i) throws RemoteException;

    void registerForUtEvent(int i, IImsUtEventListener iImsUtEventListener) throws RemoteException;

    int updateCLIP(int i, boolean z) throws RemoteException;

    int updateCLIR(int i, int i2) throws RemoteException;

    int updateCOLP(int i, boolean z) throws RemoteException;

    int updateCOLR(int i, int i2) throws RemoteException;

    int updateCallBarring(int i, int i2, int i3, int i4, String str, String[] strArr) throws RemoteException;

    int updateCallForward(int i, int i2, int i3, String str, int i4, int i5) throws RemoteException;

    int updateCallWaiting(int i, boolean z, int i2) throws RemoteException;

    public abstract class Stub extends Binder implements IUtService {
        static final int TRANSACTION_deRegisterForUtEvent = 2;
        static final int TRANSACTION_isUtEnabled = 17;
        static final int TRANSACTION_queryCLIP = 7;
        static final int TRANSACTION_queryCLIR = 6;
        static final int TRANSACTION_queryCOLP = 9;
        static final int TRANSACTION_queryCOLR = 8;
        static final int TRANSACTION_queryCallBarring = 3;
        static final int TRANSACTION_queryCallForward = 4;
        static final int TRANSACTION_queryCallWaiting = 5;
        static final int TRANSACTION_registerForUtEvent = 1;
        static final int TRANSACTION_updateCLIP = 14;
        static final int TRANSACTION_updateCLIR = 13;
        static final int TRANSACTION_updateCOLP = 16;
        static final int TRANSACTION_updateCOLR = 15;
        static final int TRANSACTION_updateCallBarring = 10;
        static final int TRANSACTION_updateCallForward = 11;
        static final int TRANSACTION_updateCallWaiting = 12;

        class Proxy implements IUtService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.ss.IUtService
            public void deRegisterForUtEvent(int i, IImsUtEventListener iImsUtEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsUtEventListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUtService.DESCRIPTOR;
            }

            @Override // com.sec.ims.ss.IUtService
            public boolean isUtEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCLIP(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCLIR(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCOLP(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCOLR(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCallBarring(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCallForward(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCallWaiting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public void registerForUtEvent(int i, IImsUtEventListener iImsUtEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsUtEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCLIP(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCLIR(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCOLP(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCOLR(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCallBarring(int i, int i2, int i3, int i4, String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCallForward(int i, int i2, int i3, String str, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCallWaiting(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUtService.DESCRIPTOR);
        }

        public static IUtService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUtService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUtService)) ? new Proxy(iBinder) : (IUtService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUtService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUtService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    IImsUtEventListener iImsUtEventListenerAsInterface = IImsUtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForUtEvent(i3, iImsUtEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    IImsUtEventListener iImsUtEventListenerAsInterface2 = IImsUtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deRegisterForUtEvent(i4, iImsUtEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iQueryCallBarring = queryCallBarring(i5, i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCallBarring);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iQueryCallForward = queryCallForward(i8, i9, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCallForward);
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iQueryCallWaiting = queryCallWaiting(i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCallWaiting);
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iQueryCLIR = queryCLIR(i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCLIR);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iQueryCLIP = queryCLIP(i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCLIP);
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iQueryCOLR = queryCOLR(i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCOLR);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iQueryCOLP = queryCOLP(i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCOLP);
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    String string2 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int iUpdateCallBarring = updateCallBarring(i15, i16, i17, i18, string2, strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCallBarring);
                    return true;
                case 11:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    String string3 = parcel.readString();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUpdateCallForward = updateCallForward(i19, i20, i21, string3, i22, i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCallForward);
                    return true;
                case 12:
                    int i24 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUpdateCallWaiting = updateCallWaiting(i24, z, i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCallWaiting);
                    return true;
                case 13:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUpdateCLIR = updateCLIR(i26, i27);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCLIR);
                    return true;
                case 14:
                    int i28 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iUpdateCLIP = updateCLIP(i28, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCLIP);
                    return true;
                case 15:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUpdateCOLR = updateCOLR(i29, i30);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCOLR);
                    return true;
                case 16:
                    int i31 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iUpdateCOLP = updateCOLP(i31, z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateCOLP);
                    return true;
                case 17:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUtEnabled = isUtEnabled(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUtEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IUtService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ss.IUtService
        public boolean isUtEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCLIP(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCLIR(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCOLP(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCOLR(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCallBarring(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCallForward(int i, int i2, String str) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCallWaiting(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCLIP(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCLIR(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCOLP(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCOLR(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCallBarring(int i, int i2, int i3, int i4, String str, String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCallForward(int i, int i2, int i3, String str, int i4, int i5) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCallWaiting(int i, boolean z, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public void deRegisterForUtEvent(int i, IImsUtEventListener iImsUtEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.ss.IUtService
        public void registerForUtEvent(int i, IImsUtEventListener iImsUtEventListener) throws RemoteException {
        }
    }
}
