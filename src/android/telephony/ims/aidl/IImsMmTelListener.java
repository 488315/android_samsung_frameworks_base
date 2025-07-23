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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsMmTelListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsMmTelListener)) {
                return (IImsMmTelListener) queryLocalInterface;
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
                    IImsCallSession asInterface = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSessionListener onIncomingCall = onIncomingCall(asInterface, readString, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(onIncomingCall);
                    return true;
                case 2:
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRejectedCall(imsCallProfile, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVoiceMessageCountUpdate(readInt);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCdpnReceived(readString2, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioModeIsVoipChanged(readInt3);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTriggerEpsFallback(readInt4);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    IImsTrafficSessionCallback asInterface2 = IImsTrafficSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onStartImsTrafficSession(readInt5, readInt6, readInt7, readInt8, asInterface2);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onModifyImsTrafficSession(readInt9, readInt10);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStopImsTrafficSession(readInt11);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSessionListener.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onVoiceMessageCountUpdate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onCdpnReceived(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onAudioModeIsVoipChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onTriggerEpsFallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onStartImsTrafficSession(int i, int i2, int i3, int i4, IImsTrafficSessionCallback iImsTrafficSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStrongInterface(iImsTrafficSessionCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onModifyImsTrafficSession(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onStopImsTrafficSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelListener
            public void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelListener.DESCRIPTOR);
                    obtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
