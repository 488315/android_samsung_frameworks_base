package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.volte2.data.ImsCallInfo;

/* loaded from: classes4.dex */
public interface IImsCallEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsCallEventListener";

    void onAudioPathUpdated(String str) throws RemoteException;

    void onCallEnded(ImsCallInfo imsCallInfo, int i) throws RemoteException;

    void onCallEstablished(ImsCallInfo imsCallInfo) throws RemoteException;

    void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) throws RemoteException;

    void onCallModified(ImsCallInfo imsCallInfo) throws RemoteException;

    void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) throws RemoteException;

    void onCallResumed(ImsCallInfo imsCallInfo) throws RemoteException;

    void onCallRinging(ImsCallInfo imsCallInfo) throws RemoteException;

    void onCallRingingBack(ImsCallInfo imsCallInfo) throws RemoteException;

    void onCallStarted(ImsCallInfo imsCallInfo) throws RemoteException;

    void onCallTrying(ImsCallInfo imsCallInfo) throws RemoteException;

    void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str) throws RemoteException;

    void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str) throws RemoteException;

    void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2) throws RemoteException;

    void onIncomingCall(ImsCallInfo imsCallInfo, String str) throws RemoteException;

    void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) throws RemoteException;

    void onRtpLossRateNoti(int i, float f, float f2, int i2) throws RemoteException;

    void onVideoAvailable(ImsCallInfo imsCallInfo) throws RemoteException;

    void onVideoHeld(ImsCallInfo imsCallInfo) throws RemoteException;

    void onVideoResumed(ImsCallInfo imsCallInfo) throws RemoteException;

    public abstract class Stub extends Binder implements IImsCallEventListener {
        static final int TRANSACTION_onAudioPathUpdated = 20;
        static final int TRANSACTION_onCallEnded = 10;
        static final int TRANSACTION_onCallEstablished = 7;
        static final int TRANSACTION_onCallHeld = 11;
        static final int TRANSACTION_onCallModified = 9;
        static final int TRANSACTION_onCallModifyRequested = 8;
        static final int TRANSACTION_onCallResumed = 12;
        static final int TRANSACTION_onCallRinging = 5;
        static final int TRANSACTION_onCallRingingBack = 6;
        static final int TRANSACTION_onCallStarted = 3;
        static final int TRANSACTION_onCallTrying = 4;
        static final int TRANSACTION_onConferenceParticipantAdded = 13;
        static final int TRANSACTION_onConferenceParticipantRemoved = 14;
        static final int TRANSACTION_onDedicatedBearerEvent = 18;
        static final int TRANSACTION_onIncomingCall = 2;
        static final int TRANSACTION_onIncomingPreAlerting = 1;
        static final int TRANSACTION_onRtpLossRateNoti = 19;
        static final int TRANSACTION_onVideoAvailable = 15;
        static final int TRANSACTION_onVideoHeld = 16;
        static final int TRANSACTION_onVideoResumed = 17;

        class Proxy implements IImsCallEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsCallEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onAudioPathUpdated(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallEnded(ImsCallInfo imsCallInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallEstablished(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallModified(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallResumed(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallRinging(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallRingingBack(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallStarted(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallTrying(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onIncomingCall(ImsCallInfo imsCallInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onRtpLossRateNoti(int i, float f, float f2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onVideoAvailable(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onVideoHeld(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onVideoResumed(ImsCallInfo imsCallInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsCallEventListener.DESCRIPTOR);
        }

        public static IImsCallEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsCallEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsCallEventListener)) ? new Proxy(iBinder) : (IImsCallEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsCallEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsCallEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ImsCallInfo imsCallInfo = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onIncomingPreAlerting(imsCallInfo, string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ImsCallInfo imsCallInfo2 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onIncomingCall(imsCallInfo2, string2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ImsCallInfo imsCallInfo3 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStarted(imsCallInfo3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ImsCallInfo imsCallInfo4 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallTrying(imsCallInfo4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ImsCallInfo imsCallInfo5 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallRinging(imsCallInfo5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ImsCallInfo imsCallInfo6 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallRingingBack(imsCallInfo6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ImsCallInfo imsCallInfo7 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEstablished(imsCallInfo7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    ImsCallInfo imsCallInfo8 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallModifyRequested(imsCallInfo8, i3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ImsCallInfo imsCallInfo9 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallModified(imsCallInfo9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ImsCallInfo imsCallInfo10 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallEnded(imsCallInfo10, i4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ImsCallInfo imsCallInfo11 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCallHeld(imsCallInfo11, z, z2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ImsCallInfo imsCallInfo12 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallResumed(imsCallInfo12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ImsCallInfo imsCallInfo13 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onConferenceParticipantAdded(imsCallInfo13, string3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    ImsCallInfo imsCallInfo14 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onConferenceParticipantRemoved(imsCallInfo14, string4);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    ImsCallInfo imsCallInfo15 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVideoAvailable(imsCallInfo15);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ImsCallInfo imsCallInfo16 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVideoHeld(imsCallInfo16);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ImsCallInfo imsCallInfo17 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVideoResumed(imsCallInfo17);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ImsCallInfo imsCallInfo18 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDedicatedBearerEvent(imsCallInfo18, i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i7 = parcel.readInt();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRtpLossRateNoti(i7, f, f2, i8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAudioPathUpdated(string5);
                    parcel2.writeNoException();
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

    public class Default implements IImsCallEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onAudioPathUpdated(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallEstablished(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallModified(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallResumed(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallRinging(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallRingingBack(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallStarted(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallTrying(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onVideoAvailable(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onVideoHeld(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onVideoResumed(ImsCallInfo imsCallInfo) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallEnded(ImsCallInfo imsCallInfo, int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onIncomingCall(ImsCallInfo imsCallInfo, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onRtpLossRateNoti(int i, float f, float f2, int i2) throws RemoteException {
        }
    }
}
