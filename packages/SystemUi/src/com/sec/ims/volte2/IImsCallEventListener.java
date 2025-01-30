package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.volte2.data.ImsCallInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsCallEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsCallEventListener";

    void onAudioPathUpdated(String str);

    void onCallEnded(ImsCallInfo imsCallInfo, int i);

    void onCallEstablished(ImsCallInfo imsCallInfo);

    void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2);

    void onCallModified(ImsCallInfo imsCallInfo);

    void onCallModifyRequested(ImsCallInfo imsCallInfo, int i);

    void onCallResumed(ImsCallInfo imsCallInfo);

    void onCallRinging(ImsCallInfo imsCallInfo);

    void onCallRingingBack(ImsCallInfo imsCallInfo);

    void onCallStarted(ImsCallInfo imsCallInfo);

    void onCallTrying(ImsCallInfo imsCallInfo);

    void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str);

    void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str);

    void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2);

    void onIncomingCall(ImsCallInfo imsCallInfo, String str);

    void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str);

    void onRtpLossRateNoti(int i, float f, float f2, int i2);

    void onVideoAvailable(ImsCallInfo imsCallInfo);

    void onVideoHeld(ImsCallInfo imsCallInfo);

    void onVideoResumed(ImsCallInfo imsCallInfo);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsCallEventListener {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsCallEventListener {
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
            public void onAudioPathUpdated(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallEnded(ImsCallInfo imsCallInfo, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallEstablished(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallModified(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallResumed(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallRinging(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallRingingBack(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallStarted(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onCallTrying(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onIncomingCall(ImsCallInfo imsCallInfo, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onRtpLossRateNoti(int i, float f, float f2, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onVideoAvailable(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onVideoHeld(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallEventListener
            public void onVideoResumed(ImsCallInfo imsCallInfo) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsCallEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsCallEventListener)) ? new Proxy(iBinder) : (IImsCallEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onIncomingPreAlerting(imsCallInfo, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ImsCallInfo imsCallInfo2 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onIncomingCall(imsCallInfo2, readString2);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallModifyRequested(imsCallInfo8, readInt);
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
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallEnded(imsCallInfo10, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ImsCallInfo imsCallInfo11 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCallHeld(imsCallInfo11, readBoolean, readBoolean2);
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
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onConferenceParticipantAdded(imsCallInfo13, readString3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    ImsCallInfo imsCallInfo14 = (ImsCallInfo) parcel.readTypedObject(ImsCallInfo.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onConferenceParticipantRemoved(imsCallInfo14, readString4);
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
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDedicatedBearerEvent(imsCallInfo18, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt5 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRtpLossRateNoti(readInt5, readFloat, readFloat2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAudioPathUpdated(readString5);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IImsCallEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onAudioPathUpdated(String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallEstablished(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallModified(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallResumed(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallRinging(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallRingingBack(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallStarted(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallTrying(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onVideoAvailable(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onVideoHeld(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onVideoResumed(ImsCallInfo imsCallInfo) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallEnded(ImsCallInfo imsCallInfo, int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onIncomingCall(ImsCallInfo imsCallInfo, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IImsCallEventListener
        public void onRtpLossRateNoti(int i, float f, float f2, int i2) {
        }
    }
}
