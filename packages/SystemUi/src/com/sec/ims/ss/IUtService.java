package com.sec.ims.ss;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.ss.IImsUtEventListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IUtService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ss.IUtService";

    void deRegisterForUtEvent(int i, IImsUtEventListener iImsUtEventListener);

    boolean isUtEnabled(int i);

    int queryCLIP(int i);

    int queryCLIR(int i);

    int queryCOLP(int i);

    int queryCOLR(int i);

    int queryCallBarring(int i, int i2, int i3);

    int queryCallForward(int i, int i2, String str);

    int queryCallWaiting(int i);

    void registerForUtEvent(int i, IImsUtEventListener iImsUtEventListener);

    int updateCLIP(int i, boolean z);

    int updateCLIR(int i, int i2);

    int updateCOLP(int i, boolean z);

    int updateCOLR(int i, int i2);

    int updateCallBarring(int i, int i2, int i3, int i4, String str, String[] strArr);

    int updateCallForward(int i, int i2, int i3, String str, int i4, int i5);

    int updateCallWaiting(int i, boolean z, int i2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IUtService {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IUtService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.ss.IUtService
            public void deRegisterForUtEvent(int i, IImsUtEventListener iImsUtEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsUtEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUtService.DESCRIPTOR;
            }

            @Override // com.sec.ims.ss.IUtService
            public boolean isUtEnabled(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCLIP(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCLIR(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCOLP(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCOLR(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCallBarring(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCallForward(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int queryCallWaiting(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public void registerForUtEvent(int i, IImsUtEventListener iImsUtEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsUtEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCLIP(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCLIR(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCOLP(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCOLR(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCallBarring(int i, int i2, int i3, int i4, String str, String[] strArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCallForward(int i, int i2, int i3, String str, int i4, int i5) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IUtService
            public int updateCallWaiting(int i, boolean z, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUtService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUtService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUtService)) ? new Proxy(iBinder) : (IUtService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUtService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUtService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    IImsUtEventListener asInterface = IImsUtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForUtEvent(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    IImsUtEventListener asInterface2 = IImsUtEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deRegisterForUtEvent(readInt2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCallBarring = queryCallBarring(readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallBarring);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int queryCallForward = queryCallForward(readInt6, readInt7, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallForward);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCallWaiting = queryCallWaiting(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCallWaiting);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCLIR = queryCLIR(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCLIR);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCLIP = queryCLIP(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCLIP);
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCOLR = queryCOLR(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCOLR);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int queryCOLP = queryCOLP(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCOLP);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String readString2 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int updateCallBarring = updateCallBarring(readInt13, readInt14, readInt15, readInt16, readString2, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallBarring);
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCallForward = updateCallForward(readInt17, readInt18, readInt19, readString3, readInt20, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallForward);
                    return true;
                case 12:
                    int readInt22 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCallWaiting = updateCallWaiting(readInt22, readBoolean, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCallWaiting);
                    return true;
                case 13:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCLIR = updateCLIR(readInt24, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCLIR);
                    return true;
                case 14:
                    int readInt26 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int updateCLIP = updateCLIP(readInt26, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCLIP);
                    return true;
                case 15:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateCOLR = updateCOLR(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCOLR);
                    return true;
                case 16:
                    int readInt29 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int updateCOLP = updateCOLP(readInt29, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateCOLP);
                    return true;
                case 17:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUtEnabled = isUtEnabled(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUtEnabled);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IUtService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ss.IUtService
        public boolean isUtEnabled(int i) {
            return false;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCLIP(int i) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCLIR(int i) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCOLP(int i) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCOLR(int i) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCallBarring(int i, int i2, int i3) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCallForward(int i, int i2, String str) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int queryCallWaiting(int i) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCLIP(int i, boolean z) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCLIR(int i, int i2) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCOLP(int i, boolean z) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCOLR(int i, int i2) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCallBarring(int i, int i2, int i3, int i4, String str, String[] strArr) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCallForward(int i, int i2, int i3, String str, int i4, int i5) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public int updateCallWaiting(int i, boolean z, int i2) {
            return 0;
        }

        @Override // com.sec.ims.ss.IUtService
        public void deRegisterForUtEvent(int i, IImsUtEventListener iImsUtEventListener) {
        }

        @Override // com.sec.ims.ss.IUtService
        public void registerForUtEvent(int i, IImsUtEventListener iImsUtEventListener) {
        }
    }
}
