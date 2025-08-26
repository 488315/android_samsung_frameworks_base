package com.android.ims.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsSsData;
import android.telephony.ims.ImsSsInfo;
import com.android.ims.internal.IImsUt;

/* loaded from: classes5.dex */
public interface IImsUtListener extends IInterface {

    public static class Default implements IImsUtListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void lineIdentificationSupplementaryServiceResponse(int i, ImsSsInfo imsSsInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void onSupplementaryServiceIndication(ImsSsData imsSsData) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationCallBarringQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationCallForwardQueried(IImsUt iImsUt, int i, ImsCallForwardInfo[] imsCallForwardInfoArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationCallWaitingQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationQueried(IImsUt iImsUt, int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationQueryFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationUpdateFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsUtListener
        public void utConfigurationUpdated(IImsUt iImsUt, int i) throws RemoteException {
        }
    }

    void lineIdentificationSupplementaryServiceResponse(int i, ImsSsInfo imsSsInfo) throws RemoteException;

    void onSupplementaryServiceIndication(ImsSsData imsSsData) throws RemoteException;

    void utConfigurationCallBarringQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException;

    void utConfigurationCallForwardQueried(IImsUt iImsUt, int i, ImsCallForwardInfo[] imsCallForwardInfoArr) throws RemoteException;

    void utConfigurationCallWaitingQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException;

    void utConfigurationQueried(IImsUt iImsUt, int i, Bundle bundle) throws RemoteException;

    void utConfigurationQueryFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void utConfigurationUpdateFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void utConfigurationUpdated(IImsUt iImsUt, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsUtListener {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsUtListener";
        static final int TRANSACTION_lineIdentificationSupplementaryServiceResponse = 5;
        static final int TRANSACTION_onSupplementaryServiceIndication = 9;
        static final int TRANSACTION_utConfigurationCallBarringQueried = 6;
        static final int TRANSACTION_utConfigurationCallForwardQueried = 7;
        static final int TRANSACTION_utConfigurationCallWaitingQueried = 8;
        static final int TRANSACTION_utConfigurationQueried = 3;
        static final int TRANSACTION_utConfigurationQueryFailed = 4;
        static final int TRANSACTION_utConfigurationUpdateFailed = 2;
        static final int TRANSACTION_utConfigurationUpdated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsUtListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsUtListener)) {
                return (IImsUtListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "utConfigurationUpdated";
                case 2:
                    return "utConfigurationUpdateFailed";
                case 3:
                    return "utConfigurationQueried";
                case 4:
                    return "utConfigurationQueryFailed";
                case 5:
                    return "lineIdentificationSupplementaryServiceResponse";
                case 6:
                    return "utConfigurationCallBarringQueried";
                case 7:
                    return "utConfigurationCallForwardQueried";
                case 8:
                    return "utConfigurationCallWaitingQueried";
                case 9:
                    return "onSupplementaryServiceIndication";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsUt iImsUtAsInterface = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    utConfigurationUpdated(iImsUtAsInterface, i3);
                    return true;
                case 2:
                    IImsUt iImsUtAsInterface2 = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationUpdateFailed(iImsUtAsInterface2, i4, imsReasonInfo);
                    return true;
                case 3:
                    IImsUt iImsUtAsInterface3 = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i5 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationQueried(iImsUtAsInterface3, i5, bundle);
                    return true;
                case 4:
                    IImsUt iImsUtAsInterface4 = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i6 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo2 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationQueryFailed(iImsUtAsInterface4, i6, imsReasonInfo2);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    ImsSsInfo imsSsInfo = (ImsSsInfo) parcel.readTypedObject(ImsSsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    lineIdentificationSupplementaryServiceResponse(i7, imsSsInfo);
                    return true;
                case 6:
                    IImsUt iImsUtAsInterface5 = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i8 = parcel.readInt();
                    ImsSsInfo[] imsSsInfoArr = (ImsSsInfo[]) parcel.createTypedArray(ImsSsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationCallBarringQueried(iImsUtAsInterface5, i8, imsSsInfoArr);
                    return true;
                case 7:
                    IImsUt iImsUtAsInterface6 = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    ImsCallForwardInfo[] imsCallForwardInfoArr = (ImsCallForwardInfo[]) parcel.createTypedArray(ImsCallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationCallForwardQueried(iImsUtAsInterface6, i9, imsCallForwardInfoArr);
                    return true;
                case 8:
                    IImsUt iImsUtAsInterface7 = IImsUt.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    ImsSsInfo[] imsSsInfoArr2 = (ImsSsInfo[]) parcel.createTypedArray(ImsSsInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    utConfigurationCallWaitingQueried(iImsUtAsInterface7, i10, imsSsInfoArr2);
                    return true;
                case 9:
                    ImsSsData imsSsData = (ImsSsData) parcel.readTypedObject(ImsSsData.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSupplementaryServiceIndication(imsSsData);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsUtListener {
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

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationUpdated(IImsUt iImsUt, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationUpdateFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationQueried(IImsUt iImsUt, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationQueryFailed(IImsUt iImsUt, int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void lineIdentificationSupplementaryServiceResponse(int i, ImsSsInfo imsSsInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsSsInfo, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationCallBarringQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(imsSsInfoArr, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationCallForwardQueried(IImsUt iImsUt, int i, ImsCallForwardInfo[] imsCallForwardInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(imsCallForwardInfoArr, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void utConfigurationCallWaitingQueried(IImsUt iImsUt, int i, ImsSsInfo[] imsSsInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsUt);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(imsSsInfoArr, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsUtListener
            public void onSupplementaryServiceIndication(ImsSsData imsSsData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsSsData, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
