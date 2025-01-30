package com.sec.ims.volte2;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.volte2.data.MediaProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsCallSessionEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsCallSessionEventListener";

    void notifyReadyToHandleImsCallbacks();

    void onCallQualityChanged();

    void onCalling();

    void onConfParticipantHeld(int i, boolean z);

    void onConfParticipantResumed(int i, boolean z);

    void onConferenceEstablished();

    void onEPdgUnavailable(int i);

    void onEarlyMediaStarted(int i);

    void onEnded(int i);

    void onEpdgStateChanged();

    void onError(int i, String str, int i2);

    void onEstablished(int i);

    void onFailure(int i);

    void onForwarded();

    void onHeld(boolean z, boolean z2);

    void onImsGeneralEvent(String str, Bundle bundle);

    void onParticipantAdded(int i);

    void onParticipantRemoved(int i);

    void onParticipantUpdated(int i, String[] strArr, int[] iArr, int[] iArr2);

    void onProfileUpdated(MediaProfile mediaProfile, MediaProfile mediaProfile2);

    void onResumed(boolean z);

    void onRetryingVoLteOrCsCall(int i);

    void onRingingBack();

    void onSessionChanged(int i);

    void onSessionProgress(int i);

    void onSessionUpdateRequested(int i, byte[] bArr);

    void onStopAlertTone();

    void onSwitched(int i);

    void onTrying();

    void onTtyTextRequest(int i, byte[] bArr);

    void onUssdReceived(int i, int i2, byte[] bArr);

    void onUssdResponse(int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IImsCallSessionEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void notifyReadyToHandleImsCallbacks() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onCallQualityChanged() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onCalling() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onConferenceEstablished() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEpdgStateChanged() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onForwarded() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onRingingBack() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onStopAlertTone() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onTrying() {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEPdgUnavailable(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEarlyMediaStarted(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEnded(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onEstablished(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onFailure(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onParticipantAdded(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onParticipantRemoved(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onResumed(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onRetryingVoLteOrCsCall(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSessionChanged(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSessionProgress(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSwitched(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onUssdResponse(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onConfParticipantHeld(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onConfParticipantResumed(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onHeld(boolean z, boolean z2) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onImsGeneralEvent(String str, Bundle bundle) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onProfileUpdated(MediaProfile mediaProfile, MediaProfile mediaProfile2) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onSessionUpdateRequested(int i, byte[] bArr) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onTtyTextRequest(int i, byte[] bArr) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onError(int i, String str, int i2) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onUssdReceived(int i, int i2, byte[] bArr) {
        }

        @Override // com.sec.ims.volte2.IImsCallSessionEventListener
        public void onParticipantUpdated(int i, String[] strArr, int[] iArr, int[] iArr2) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsCallSessionEventListener {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsCallSessionEventListener {
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
            public void notifyReadyToHandleImsCallbacks() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onCallQualityChanged() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onCalling() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onConfParticipantHeld(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onConfParticipantResumed(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onConferenceEstablished() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEPdgUnavailable(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEarlyMediaStarted(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEnded(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEpdgStateChanged() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onError(int i, String str, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onEstablished(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onFailure(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onForwarded() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onHeld(boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onImsGeneralEvent(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onParticipantAdded(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onParticipantRemoved(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onParticipantUpdated(int i, String[] strArr, int[] iArr, int[] iArr2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onProfileUpdated(MediaProfile mediaProfile, MediaProfile mediaProfile2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(mediaProfile, 0);
                    obtain.writeTypedObject(mediaProfile2, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onResumed(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onRetryingVoLteOrCsCall(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onRingingBack() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSessionChanged(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSessionProgress(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSessionUpdateRequested(int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onStopAlertTone() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onSwitched(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onTrying() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onTtyTextRequest(int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onUssdReceived(int i, int i2, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSessionEventListener
            public void onUssdResponse(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsCallSessionEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsCallSessionEventListener)) ? new Proxy(iBinder) : (IImsCallSessionEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEarlyMediaStarted(readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionProgress(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEstablished(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFailure(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSwitched(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onHeld(readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onResumed(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt6 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfParticipantHeld(readInt6, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfParticipantResumed(readInt7, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    onForwarded();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnded(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt9 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSessionUpdateRequested(readInt9, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    onStopAlertTone();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt10 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt10, readString, readInt11);
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
                    int readInt12 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onParticipantUpdated(readInt12, createStringArray, createIntArray, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onParticipantAdded(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onParticipantRemoved(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onTtyTextRequest(readInt15, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUssdResponse(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onUssdReceived(readInt17, readInt18, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEPdgUnavailable(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    onEpdgStateChanged();
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionChanged(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImsGeneralEvent(readString2, bundle);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    onCallQualityChanged();
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRetryingVoLteOrCsCall(readInt21);
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
