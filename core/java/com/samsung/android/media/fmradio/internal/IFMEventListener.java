package com.samsung.android.media.fmradio.internal;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IFMEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.fmradio.internal.IFMEventListener";

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

    public static class Default implements IFMEventListener {
        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioEnabled() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDisabled(int reasonCode) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onChannelFound(long freq) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStarted() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStopped(long[] freqArray) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanFinished(long[] freqArray) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onTuned(long freq) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onHeadsetConnected() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onHeadsetDisconnected() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemReceived(long freq, String channelName, String radioText) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioTextPlusReceived(int contentType1, int startPos1, int additionalLen1, int contentType2, int startPos2, int additionalLen2) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemEnabled() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemDisabled() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onAlternateFrequencyStarted() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onAlternateFrequencyReceived(long freq) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onVolumeLocked() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRecordingFinished() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onProgrammeIdentificationExtendedCountryCodesReceived(int pi, int ecc) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFMEventListener {
        static final int TRANSACTION_onAlternateFrequencyReceived = 15;
        static final int TRANSACTION_onAlternateFrequencyStarted = 14;
        static final int TRANSACTION_onChannelFound = 3;
        static final int TRANSACTION_onHeadsetConnected = 8;
        static final int TRANSACTION_onHeadsetDisconnected = 9;

        /* renamed from: TRANSACTION_onProgrammeIdentificationExtendedCountryCodesReceived */
        static final int f3030xac9d95b4 = 18;
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

        public Stub() {
            attachInterface(this, IFMEventListener.DESCRIPTOR);
        }

        public static IFMEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFMEventListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IFMEventListener)) {
                return (IFMEventListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            long[] _arg0;
            long[] _arg02;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFMEventListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IFMEventListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onRadioEnabled();
                            return true;
                        case 2:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            onRadioDisabled(_arg03);
                            return true;
                        case 3:
                            long _arg04 = data.readLong();
                            data.enforceNoDataAvail();
                            onChannelFound(_arg04);
                            return true;
                        case 4:
                            onScanStarted();
                            return true;
                        case 5:
                            int _arg0_length = data.readInt();
                            if (_arg0_length < 0) {
                                _arg0 = null;
                            } else {
                                _arg0 = new long[_arg0_length];
                            }
                            data.enforceNoDataAvail();
                            onScanStopped(_arg0);
                            reply.writeNoException();
                            reply.writeLongArray(_arg0);
                            return true;
                        case 6:
                            int _arg0_length2 = data.readInt();
                            if (_arg0_length2 < 0) {
                                _arg02 = null;
                            } else {
                                _arg02 = new long[_arg0_length2];
                            }
                            data.enforceNoDataAvail();
                            onScanFinished(_arg02);
                            reply.writeNoException();
                            reply.writeLongArray(_arg02);
                            return true;
                        case 7:
                            long _arg05 = data.readLong();
                            data.enforceNoDataAvail();
                            onTuned(_arg05);
                            return true;
                        case 8:
                            onHeadsetConnected();
                            return true;
                        case 9:
                            onHeadsetDisconnected();
                            return true;
                        case 10:
                            long _arg06 = data.readLong();
                            String _arg1 = data.readString();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            onRadioDataSystemReceived(_arg06, _arg1, _arg2);
                            return true;
                        case 11:
                            int _arg07 = data.readInt();
                            int _arg12 = data.readInt();
                            int _arg22 = data.readInt();
                            int _arg3 = data.readInt();
                            int _arg4 = data.readInt();
                            int _arg5 = data.readInt();
                            data.enforceNoDataAvail();
                            onRadioTextPlusReceived(_arg07, _arg12, _arg22, _arg3, _arg4, _arg5);
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
                            long _arg08 = data.readLong();
                            data.enforceNoDataAvail();
                            onAlternateFrequencyReceived(_arg08);
                            return true;
                        case 16:
                            onVolumeLocked();
                            return true;
                        case 17:
                            onRecordingFinished();
                            return true;
                        case 18:
                            int _arg09 = data.readInt();
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            onProgrammeIdentificationExtendedCountryCodesReceived(_arg09, _arg13);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IFMEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFMEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDisabled(int reasonCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeInt(reasonCode);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onChannelFound(long freq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeLong(freq);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onScanStarted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onScanStopped(long[] freqArray) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeInt(freqArray.length);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    _reply.readLongArray(freqArray);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onScanFinished(long[] freqArray) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeInt(freqArray.length);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    _reply.readLongArray(freqArray);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onTuned(long freq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeLong(freq);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onHeadsetConnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onHeadsetDisconnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDataSystemReceived(long freq, String channelName, String radioText) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeLong(freq);
                    _data.writeString(channelName);
                    _data.writeString(radioText);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioTextPlusReceived(int contentType1, int startPos1, int additionalLen1, int contentType2, int startPos2, int additionalLen2) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeInt(contentType1);
                    _data.writeInt(startPos1);
                    _data.writeInt(additionalLen1);
                    _data.writeInt(contentType2);
                    _data.writeInt(startPos2);
                    _data.writeInt(additionalLen2);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDataSystemEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRadioDataSystemDisabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onAlternateFrequencyStarted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onAlternateFrequencyReceived(long freq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeLong(freq);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onVolumeLocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onRecordingFinished() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
            public void onProgrammeIdentificationExtendedCountryCodesReceived(int pi, int ecc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFMEventListener.DESCRIPTOR);
                    _data.writeInt(pi);
                    _data.writeInt(ecc);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
