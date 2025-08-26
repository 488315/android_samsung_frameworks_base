package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.telephony.ims.aidl.IImsTrafficSessionCallback;
import com.android.ims.internal.IImsCallSession;

/* loaded from: classes4.dex */
public interface IImsMmTelListener extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelListener";

    public static class Default implements IImsMmTelListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onAudioModeIsVoipChanged(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onCdpnReceived(String str, int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public IImsCallSessionListener onIncomingCall(IImsCallSession iImsCallSession, String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onModifyImsTrafficSession(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStartImsTrafficSession(int i, int i2, int i3, int i4, IImsTrafficSessionCallback iImsTrafficSessionCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStopImsTrafficSession(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onTriggerEpsFallback(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onVoiceMessageCountUpdate(int i) throws RemoteException {
        }
    }

    void onAudioModeIsVoipChanged(int i) throws RemoteException;

    void onCdpnReceived(String str, int i) throws RemoteException;

    IImsCallSessionListener onIncomingCall(IImsCallSession iImsCallSession, String str, Bundle bundle) throws RemoteException;

    void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException;

    void onModifyImsTrafficSession(int i, int i2) throws RemoteException;

    void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void onStartImsTrafficSession(int i, int i2, int i3, int i4, IImsTrafficSessionCallback iImsTrafficSessionCallback) throws RemoteException;

    void onStopImsTrafficSession(int i) throws RemoteException;

    void onTriggerEpsFallback(int i) throws RemoteException;

    void onVoiceMessageCountUpdate(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsMmTelListener {
        static final int TRANSACTION_onAudioModeIsVoipChanged = 5;
        static final int TRANSACTION_onCdpnReceived = 4;
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onMediaQualityStatusChanged = 10;
        static final int TRANSACTION_onModifyImsTrafficSession = 8;
        static final int TRANSACTION_onRejectedCall = 2;
        static final int TRANSACTION_onStartImsTrafficSession = 7;
        static final int TRANSACTION_onStopImsTrafficSession = 9;
        static final int TRANSACTION_onTriggerEpsFallback = 6;
        static final int TRANSACTION_onVoiceMessageCountUpdate = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IImsMmTelListener.DESCRIPTOR);
        }

        public static IImsMmTelListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsMmTelListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsMmTelListener)) {
                return (IImsMmTelListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onIncomingCall";
                case 2:
                    return "onRejectedCall";
                case 3:
                    return "onVoiceMessageCountUpdate";
                case 4:
                    return "onCdpnReceived";
                case 5:
                    return "onAudioModeIsVoipChanged";
                case 6:
                    return "onTriggerEpsFallback";
                case 7:
                    return "onStartImsTrafficSession";
                case 8:
                    return "onModifyImsTrafficSession";
                case 9:
                    return "onStopImsTrafficSession";
                case 10:
                    return "onMediaQualityStatusChanged";
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
                parcel.enforceInterface(IImsMmTelListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsMmTelListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsCallSession iImsCallSessionAsInterface = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSessionListener iImsCallSessionListenerOnIncomingCall = onIncomingCall(iImsCallSessionAsInterface, string, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsCallSessionListenerOnIncomingCall);
                    return true;
                case 2:
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRejectedCall(imsCallProfile, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVoiceMessageCountUpdate(i3);
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCdpnReceived(string2, i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioModeIsVoipChanged(i5);
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTriggerEpsFallback(i6);
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    IImsTrafficSessionCallback iImsTrafficSessionCallbackAsInterface = IImsTrafficSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onStartImsTrafficSession(i7, i8, i9, i10, iImsTrafficSessionCallbackAsInterface);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onModifyImsTrafficSession(i11, i12);
                    return true;
                case 9:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStopImsTrafficSession(i13);
                    return true;
                case 10:
                    MediaQualityStatus mediaQualityStatus = (MediaQualityStatus) parcel.readTypedObject(MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaQualityStatusChanged(mediaQualityStatus);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsMmTelListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsMmTelListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public IImsCallSessionListener onIncomingCall(IImsCallSession iImsCallSession, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallSession);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSessionListener.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallProfile, 0);
                    parcelObtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onVoiceMessageCountUpdate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onCdpnReceived(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onAudioModeIsVoipChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onTriggerEpsFallback(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onStartImsTrafficSession(int i, int i2, int i3, int i4, IImsTrafficSessionCallback iImsTrafficSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeStrongInterface(iImsTrafficSessionCallback);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onModifyImsTrafficSession(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onStopImsTrafficSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
