package com.sec.ims.volte2;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.sec.ims.volte2.data.MediaProfile;

/* loaded from: classes4.dex */
public interface IImsCallSessionEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsCallSessionEventListener";

    void notifyReadyToHandleImsCallbacks() throws RemoteException;

    void onCallQualityChanged() throws RemoteException;

    void onCalling() throws RemoteException;

    void onConfParticipantHeld(int i, boolean z) throws RemoteException;

    void onConfParticipantResumed(int i, boolean z) throws RemoteException;

    void onConferenceEstablished() throws RemoteException;

    void onEPdgUnavailable(int i) throws RemoteException;

    void onEarlyMediaStarted(int i) throws RemoteException;

    void onEnded(int i) throws RemoteException;

    void onEpdgStateChanged() throws RemoteException;

    void onError(int i, String str, int i2) throws RemoteException;

    void onEstablished(int i) throws RemoteException;

    void onFailure(int i) throws RemoteException;

    void onForwarded() throws RemoteException;

    void onHeld(boolean z, boolean z2) throws RemoteException;

    void onImsGeneralEvent(String str, Bundle bundle) throws RemoteException;

    void onParticipantAdded(int i) throws RemoteException;

    void onParticipantRemoved(int i) throws RemoteException;

    void onParticipantUpdated(int i, String[] strArr, int[] iArr, int[] iArr2) throws RemoteException;

    void onProfileUpdated(MediaProfile mediaProfile, MediaProfile mediaProfile2) throws RemoteException;

    void onResumed(boolean z) throws RemoteException;

    void onRetryingVoLteOrCsCall(int i) throws RemoteException;

    void onRingingBack() throws RemoteException;

    void onSessionChanged(int i) throws RemoteException;

    void onSessionProgress(int i) throws RemoteException;

    void onSessionUpdateRequested(int i, byte[] bArr) throws RemoteException;

    void onStopAlertTone() throws RemoteException;

    void onSwitched(int i) throws RemoteException;

    void onTrying() throws RemoteException;

    void onTtyTextRequest(int i, byte[] bArr) throws RemoteException;

    void onUssdReceived(int i, int i2, byte[] bArr) throws RemoteException;

    void onUssdResponse(int i) throws RemoteException;

    public class Default implements IImsCallSessionEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void notifyReadyToHandleImsCallbacks() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onCallQualityChanged() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onCalling() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onConferenceEstablished() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEpdgStateChanged() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onForwarded() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onRingingBack() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onStopAlertTone() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onTrying() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEPdgUnavailable(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEarlyMediaStarted(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEnded(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEstablished(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onFailure(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onParticipantAdded(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onParticipantRemoved(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onResumed(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onRetryingVoLteOrCsCall(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSessionChanged(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSessionProgress(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSwitched(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onUssdResponse(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onConfParticipantHeld(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onConfParticipantResumed(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onHeld(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onImsGeneralEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onProfileUpdated(MediaProfile mediaProfile, MediaProfile mediaProfile2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSessionUpdateRequested(int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onTtyTextRequest(int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onError(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onUssdReceived(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onParticipantUpdated(int i, String[] strArr, int[] iArr, int[] iArr2) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IImsCallSessionEventListener {
        static final int TRANSACTION_notifyReadyToHandleImsCallbacks = 1;
        static final int TRANSACTION_onCallQualityChanged = 31;
        static final int TRANSACTION_onCalling = 2;
        static final int TRANSACTION_onConfParticipantHeld = 12;
        static final int TRANSACTION_onConfParticipantResumed = 13;
        static final int TRANSACTION_onConferenceEstablished = 20;
        static final int TRANSACTION_onEPdgUnavailable = 27;
        static final int TRANSACTION_onEarlyMediaStarted = 5;
        static final int TRANSACTION_onEnded = 15;
        static final int TRANSACTION_onEpdgStateChanged = 28;
        static final int TRANSACTION_onError = 18;
        static final int TRANSACTION_onEstablished = 7;
        static final int TRANSACTION_onFailure = 8;
        static final int TRANSACTION_onForwarded = 14;
        static final int TRANSACTION_onHeld = 10;
        static final int TRANSACTION_onImsGeneralEvent = 30;
        static final int TRANSACTION_onParticipantAdded = 22;
        static final int TRANSACTION_onParticipantRemoved = 23;
        static final int TRANSACTION_onParticipantUpdated = 21;
        static final int TRANSACTION_onProfileUpdated = 19;
        static final int TRANSACTION_onResumed = 11;
        static final int TRANSACTION_onRetryingVoLteOrCsCall = 32;
        static final int TRANSACTION_onRingingBack = 4;
        static final int TRANSACTION_onSessionChanged = 29;
        static final int TRANSACTION_onSessionProgress = 6;
        static final int TRANSACTION_onSessionUpdateRequested = 16;
        static final int TRANSACTION_onStopAlertTone = 17;
        static final int TRANSACTION_onSwitched = 9;
        static final int TRANSACTION_onTrying = 3;
        static final int TRANSACTION_onTtyTextRequest = 24;
        static final int TRANSACTION_onUssdReceived = 26;
        static final int TRANSACTION_onUssdResponse = 25;

        class Proxy implements IImsCallSessionEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsCallSessionEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void notifyReadyToHandleImsCallbacks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onCallQualityChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onCalling() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onConfParticipantHeld(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onConfParticipantResumed(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onConferenceEstablished() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEPdgUnavailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEarlyMediaStarted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEnded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEpdgStateChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onError(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEstablished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onFailure(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onForwarded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onHeld(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onImsGeneralEvent(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onParticipantAdded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onParticipantRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onParticipantUpdated(int i, String[] strArr, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onProfileUpdated(MediaProfile mediaProfile, MediaProfile mediaProfile2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaProfile, 0);
                    parcelObtain.writeTypedObject(mediaProfile2, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onResumed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onRetryingVoLteOrCsCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onRingingBack() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSessionChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSessionProgress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSessionUpdateRequested(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onStopAlertTone() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSwitched(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onTrying() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onTtyTextRequest(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onUssdReceived(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onUssdResponse(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsCallSessionEventListener.DESCRIPTOR);
        }

        public static IImsCallSessionEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsCallSessionEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsCallSessionEventListener)) ? new Proxy(iBinder) : (IImsCallSessionEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsCallSessionEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsCallSessionEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    notifyReadyToHandleImsCallbacks();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onCalling();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    onTrying();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onRingingBack();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEarlyMediaStarted(i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionProgress(i4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEstablished(i5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFailure(i6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSwitched(i7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onHeld(z, z2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onResumed(z3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i8 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfParticipantHeld(i8, z4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i9 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfParticipantResumed(i9, z5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    onForwarded();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnded(i10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i11 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSessionUpdateRequested(i11, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    onStopAlertTone();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i12 = parcel.readInt();
                    String string = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(i12, string, i13);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    Parcelable.Creator<MediaProfile> creator = MediaProfile.CREATOR;
                    MediaProfile mediaProfile = (MediaProfile) parcel.readTypedObject(creator);
                    MediaProfile mediaProfile2 = (MediaProfile) parcel.readTypedObject(creator);
                    parcel.enforceNoDataAvail();
                    onProfileUpdated(mediaProfile, mediaProfile2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    onConferenceEstablished();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i14 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onParticipantUpdated(i14, strArrCreateStringArray, iArrCreateIntArray, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onParticipantAdded(i15);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onParticipantRemoved(i16);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i17 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onTtyTextRequest(i17, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUssdResponse(i18);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onUssdReceived(i19, i20, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEPdgUnavailable(i21);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    onEpdgStateChanged();
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionChanged(i22);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImsGeneralEvent(string2, bundle);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    onCallQualityChanged();
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRetryingVoLteOrCsCall(i23);
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
}
