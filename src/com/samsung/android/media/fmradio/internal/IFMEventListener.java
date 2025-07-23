package com.samsung.android.media.fmradio.internal;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IFMEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.fmradio.internal.IFMEventListener";

    public static class Default implements IFMEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onAlternateFrequencyReceived(long j) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onAlternateFrequencyStarted() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onChannelFound(long j) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onHeadsetConnected() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onHeadsetDisconnected() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onProgrammeIdentificationExtendedCountryCodesReceived(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemDisabled() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemEnabled() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemReceived(long j, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDisabled(int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioEnabled() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioTextPlusReceived(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRecordingFinished() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanFinished(long[] jArr) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStarted() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStopped(long[] jArr) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onTuned(long j) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onVolumeLocked() throws RemoteException {
        }
    }

    void onAlternateFrequencyReceived(long j) throws RemoteException;

    void onAlternateFrequencyStarted() throws RemoteException;

    void onChannelFound(long j) throws RemoteException;

    void onHeadsetConnected() throws RemoteException;

    void onHeadsetDisconnected() throws RemoteException;

    void onProgrammeIdentificationExtendedCountryCodesReceived(int i, int i2) throws RemoteException;

    void onRadioDataSystemDisabled() throws RemoteException;

    void onRadioDataSystemEnabled() throws RemoteException;

    void onRadioDataSystemReceived(long j, String str, String str2) throws RemoteException;

    void onRadioDisabled(int i) throws RemoteException;

    void onRadioEnabled() throws RemoteException;

    void onRadioTextPlusReceived(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void onRecordingFinished() throws RemoteException;

    void onScanFinished(long[] jArr) throws RemoteException;

    void onScanStarted() throws RemoteException;

    void onScanStopped(long[] jArr) throws RemoteException;

    void onTuned(long j) throws RemoteException;

    void onVolumeLocked() throws RemoteException;

    public static abstract class Stub extends Binder implements IFMEventListener {
        static final int TRANSACTION_onAlternateFrequencyReceived = 15;
        static final int TRANSACTION_onAlternateFrequencyStarted = 14;
        static final int TRANSACTION_onChannelFound = 3;
        static final int TRANSACTION_onHeadsetConnected = 8;
        static final int TRANSACTION_onHeadsetDisconnected = 9;
        static final int TRANSACTION_onProgrammeIdentificationExtendedCountryCodesReceived = 18;
        static final int TRANSACTION_onRadioDataSystemDisabled = 13;
        static final int TRANSACTION_onRadioDataSystemEnabled = 12;
        static final int TRANSACTION_onRadioDataSystemReceived = 10;
        static final int TRANSACTION_onRadioDisabled = 2;
        static final int TRANSACTION_onRadioEnabled = 1;
        static final int TRANSACTION_onRadioTextPlusReceived = 11;
        static final int TRANSACTION_onRecordingFinished = 17;
        static final int TRANSACTION_onScanFinished = 6;
        static final int TRANSACTION_onScanStarted = 4;
        static final int TRANSACTION_onScanStopped = 5;
        static final int TRANSACTION_onTuned = 7;
        static final int TRANSACTION_onVolumeLocked = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, IFMEventListener.DESCRIPTOR);
        }

        public static IFMEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFMEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFMEventListener)) {
                return (IFMEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRadioEnabled";
                case 2:
                    return "onRadioDisabled";
                case 3:
                    return "onChannelFound";
                case 4:
                    return "onScanStarted";
                case 5:
                    return "onScanStopped";
                case 6:
                    return "onScanFinished";
                case 7:
                    return "onTuned";
                case 8:
                    return "onHeadsetConnected";
                case 9:
                    return "onHeadsetDisconnected";
                case 10:
                    return "onRadioDataSystemReceived";
                case 11:
                    return "onRadioTextPlusReceived";
                case 12:
                    return "onRadioDataSystemEnabled";
                case 13:
                    return "onRadioDataSystemDisabled";
                case 14:
                    return "onAlternateFrequencyStarted";
                case 15:
                    return "onAlternateFrequencyReceived";
                case 16:
                    return "onVolumeLocked";
                case 17:
                    return "onRecordingFinished";
                case 18:
                    return "onProgrammeIdentificationExtendedCountryCodesReceived";
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
            long[] jArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFMEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFMEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onRadioEnabled();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRadioDisabled(readInt);
                    return true;
                case 3:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChannelFound(readLong);
                    return true;
                case 4:
                    onScanStarted();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    if (readInt2 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt2);
                    }
                    jArr = readInt2 >= 0 ? new long[readInt2] : null;
                    parcel.enforceNoDataAvail();
                    onScanStopped(jArr);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(jArr);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    if (readInt3 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt3);
                    }
                    jArr = readInt3 >= 0 ? new long[readInt3] : null;
                    parcel.enforceNoDataAvail();
                    onScanFinished(jArr);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(jArr);
                    return true;
                case 7:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onTuned(readLong2);
                    return true;
                case 8:
                    onHeadsetConnected();
                    return true;
                case 9:
                    onHeadsetDisconnected();
                    return true;
                case 10:
                    long readLong3 = parcel.readLong();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRadioDataSystemReceived(readLong3, readString, readString2);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRadioTextPlusReceived(readInt4, readInt5, readInt6, readInt7, readInt8, readInt9);
                    return true;
                case 12:
                    onRadioDataSystemEnabled();
                    return true;
                case 13:
                    onRadioDataSystemDisabled();
                    return true;
                case 14:
                    onAlternateFrequencyStarted();
                    return true;
                case 15:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAlternateFrequencyReceived(readLong4);
                    return true;
                case 16:
                    onVolumeLocked();
                    return true;
                case 17:
                    onRecordingFinished();
                    return true;
                case 18:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onProgrammeIdentificationExtendedCountryCodesReceived(readInt10, readInt11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFMEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFMEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDisabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onChannelFound(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onScanStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onScanStopped(long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readLongArray(jArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onScanFinished(long[] jArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readLongArray(jArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onTuned(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onHeadsetConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onHeadsetDisconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDataSystemReceived(long j, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioTextPlusReceived(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDataSystemEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDataSystemDisabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onAlternateFrequencyStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onAlternateFrequencyReceived(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onVolumeLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRecordingFinished() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onProgrammeIdentificationExtendedCountryCodesReceived(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
