package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.telephony.cdma.CdmaSmsCbProgramData;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICellBroadcastService extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ICellBroadcastService";

    public static class Default implements ICellBroadcastService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ICellBroadcastService
        public CharSequence getCellBroadcastAreaInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) throws RemoteException {
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaScpMessage(int i, List<CdmaSmsCbProgramData> list, String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleGsmCellBroadcastSms(int i, byte[] bArr) throws RemoteException {
        }
    }

    CharSequence getCellBroadcastAreaInfo(int i) throws RemoteException;

    void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) throws RemoteException;

    void handleCdmaScpMessage(int i, List<CdmaSmsCbProgramData> list, String str, RemoteCallback remoteCallback) throws RemoteException;

    void handleGsmCellBroadcastSms(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ICellBroadcastService {
        static final int TRANSACTION_getCellBroadcastAreaInfo = 4;
        static final int TRANSACTION_handleCdmaCellBroadcastSms = 2;
        static final int TRANSACTION_handleCdmaScpMessage = 3;
        static final int TRANSACTION_handleGsmCellBroadcastSms = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ICellBroadcastService.DESCRIPTOR);
        }

        public static ICellBroadcastService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICellBroadcastService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICellBroadcastService)) {
                return (ICellBroadcastService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "handleGsmCellBroadcastSms";
            }
            if (i == 2) {
                return "handleCdmaCellBroadcastSms";
            }
            if (i == 3) {
                return "handleCdmaScpMessage";
            }
            if (i != 4) {
                return null;
            }
            return "getCellBroadcastAreaInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICellBroadcastService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICellBroadcastService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                handleGsmCellBroadcastSms(i3, bArrCreateByteArray);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                handleCdmaCellBroadcastSms(i4, bArrCreateByteArray2, i5);
            } else if (i == 3) {
                int i6 = parcel.readInt();
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CdmaSmsCbProgramData.CREATOR);
                String string = parcel.readString();
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                handleCdmaScpMessage(i6, arrayListCreateTypedArrayList, string, remoteCallback);
            } else if (i == 4) {
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                CharSequence cellBroadcastAreaInfo = getCellBroadcastAreaInfo(i7);
                parcel2.writeNoException();
                if (cellBroadcastAreaInfo != null) {
                    parcel2.writeInt(1);
                    TextUtils.writeToParcel(cellBroadcastAreaInfo, parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICellBroadcastService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICellBroadcastService.DESCRIPTOR;
            }

            @Override // android.telephony.ICellBroadcastService
            public void handleGsmCellBroadcastSms(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICellBroadcastService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ICellBroadcastService
            public void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICellBroadcastService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ICellBroadcastService
            public void handleCdmaScpMessage(int i, List<CdmaSmsCbProgramData> list, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICellBroadcastService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ICellBroadcastService
            public CharSequence getCellBroadcastAreaInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICellBroadcastService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
