package com.samsung.android.media.fmradio.internal;

import android.media.tv.interactive.TvInteractiveAppService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IFMPlayer extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.fmradio.internal.IFMPlayer";

    void cancelAFSwitching() throws RemoteException;

    boolean cancelScan() throws RemoteException;

    void cancelSeek() throws RemoteException;

    void disableAF() throws RemoteException;

    void disableRDS() throws RemoteException;

    void enableAF() throws RemoteException;

    void enableRDS() throws RemoteException;

    long getCurrentChannel() throws RemoteException;

    int getIntegerTunningParameter(String str, int i) throws RemoteException;

    long[] getLastScanResult() throws RemoteException;

    long getLongTunningParameter(String str, long j) throws RemoteException;

    long getMaxVolume() throws RemoteException;

    long getPlayedFreq() throws RemoteException;

    boolean getSoftMuteMode() throws RemoteException;

    String getStringTunningParameter(String str, String str2) throws RemoteException;

    long getVolume() throws RemoteException;

    boolean isAFEnable() throws RemoteException;

    boolean isAirPlaneMode() throws RemoteException;

    boolean isBatteryLow() throws RemoteException;

    int isBusy() throws RemoteException;

    boolean isDeviceSpeakerEnabled() throws RemoteException;

    boolean isHeadsetPlugged() throws RemoteException;

    boolean isOn() throws RemoteException;

    boolean isRDSEnable() throws RemoteException;

    boolean isScanning() throws RemoteException;

    boolean isSeeking() throws RemoteException;

    boolean isTvOutPlugged() throws RemoteException;

    void mute(boolean z) throws RemoteException;

    boolean off() throws RemoteException;

    boolean on() throws RemoteException;

    boolean on_in_testmode() throws RemoteException;

    void removeListener(IFMEventListener iFMEventListener) throws RemoteException;

    void scan() throws RemoteException;

    long searchAll() throws RemoteException;

    long searchDown() throws RemoteException;

    long searchUp() throws RemoteException;

    long seekDown() throws RemoteException;

    long seekUp() throws RemoteException;

    void setBand(int i) throws RemoteException;

    void setChannelSpacing(int i) throws RemoteException;

    void setFMIntenna(boolean z) throws RemoteException;

    void setIntegerTunningParameter(String str, int i) throws RemoteException;

    void setListener(IFMEventListener iFMEventListener) throws RemoteException;

    void setLongTunningParameter(String str, long j) throws RemoteException;

    void setMono() throws RemoteException;

    void setRecordMode(boolean z) throws RemoteException;

    void setSoftmute(boolean z) throws RemoteException;

    void setSpeakerOn(boolean z) throws RemoteException;

    void setStereo() throws RemoteException;

    void setStringTunningParameter(String str, String str2) throws RemoteException;

    void setVolume(long j) throws RemoteException;

    void tune(long j) throws RemoteException;

    public static class Default implements IFMPlayer {
        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setListener(IFMEventListener listener) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void removeListener(IFMEventListener listener) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void tune(long freq) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean on() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean on_in_testmode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean off() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isOn() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long seekUp() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long seekDown() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void cancelSeek() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getCurrentChannel() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void scan() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean cancelScan() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isScanning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isSeeking() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long searchDown() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long searchUp() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long searchAll() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getPlayedFreq() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void enableRDS() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void disableRDS() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void enableAF() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void disableAF() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setBand(int band) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setChannelSpacing(int spacing) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public int isBusy() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isRDSEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isAFEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void cancelAFSwitching() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long[] getLastScanResult() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setStereo() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setMono() throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setVolume(long val) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getVolume() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isHeadsetPlugged() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isTvOutPlugged() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setSpeakerOn(boolean bSpeakerOn) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setRecordMode(boolean isRecord) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getMaxVolume() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isAirPlaneMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void mute(boolean value) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isBatteryLow() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setFMIntenna(boolean setFMIntenna) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setSoftmute(boolean state) throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean getSoftMuteMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setIntegerTunningParameter(String parameterName, int value)
                throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public int getIntegerTunningParameter(String parameterName, int defaultValue)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setLongTunningParameter(String parameterName, long value)
                throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getLongTunningParameter(String parameterName, long defaultValue)
                throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setStringTunningParameter(String parameterName, String value)
                throws RemoteException {}

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public String getStringTunningParameter(String parameterName, String defaultValue)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isDeviceSpeakerEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IFMPlayer {
        static final int TRANSACTION_cancelAFSwitching = 29;
        static final int TRANSACTION_cancelScan = 13;
        static final int TRANSACTION_cancelSeek = 10;
        static final int TRANSACTION_disableAF = 23;
        static final int TRANSACTION_disableRDS = 21;
        static final int TRANSACTION_enableAF = 22;
        static final int TRANSACTION_enableRDS = 20;
        static final int TRANSACTION_getCurrentChannel = 11;
        static final int TRANSACTION_getIntegerTunningParameter = 47;
        static final int TRANSACTION_getLastScanResult = 30;
        static final int TRANSACTION_getLongTunningParameter = 49;
        static final int TRANSACTION_getMaxVolume = 39;
        static final int TRANSACTION_getPlayedFreq = 19;
        static final int TRANSACTION_getSoftMuteMode = 45;
        static final int TRANSACTION_getStringTunningParameter = 51;
        static final int TRANSACTION_getVolume = 34;
        static final int TRANSACTION_isAFEnable = 28;
        static final int TRANSACTION_isAirPlaneMode = 40;
        static final int TRANSACTION_isBatteryLow = 42;
        static final int TRANSACTION_isBusy = 26;
        static final int TRANSACTION_isDeviceSpeakerEnabled = 52;
        static final int TRANSACTION_isHeadsetPlugged = 35;
        static final int TRANSACTION_isOn = 7;
        static final int TRANSACTION_isRDSEnable = 27;
        static final int TRANSACTION_isScanning = 14;
        static final int TRANSACTION_isSeeking = 15;
        static final int TRANSACTION_isTvOutPlugged = 36;
        static final int TRANSACTION_mute = 41;
        static final int TRANSACTION_off = 6;
        static final int TRANSACTION_on = 4;
        static final int TRANSACTION_on_in_testmode = 5;
        static final int TRANSACTION_removeListener = 2;
        static final int TRANSACTION_scan = 12;
        static final int TRANSACTION_searchAll = 18;
        static final int TRANSACTION_searchDown = 16;
        static final int TRANSACTION_searchUp = 17;
        static final int TRANSACTION_seekDown = 9;
        static final int TRANSACTION_seekUp = 8;
        static final int TRANSACTION_setBand = 24;
        static final int TRANSACTION_setChannelSpacing = 25;
        static final int TRANSACTION_setFMIntenna = 43;
        static final int TRANSACTION_setIntegerTunningParameter = 46;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setLongTunningParameter = 48;
        static final int TRANSACTION_setMono = 32;
        static final int TRANSACTION_setRecordMode = 38;
        static final int TRANSACTION_setSoftmute = 44;
        static final int TRANSACTION_setSpeakerOn = 37;
        static final int TRANSACTION_setStereo = 31;
        static final int TRANSACTION_setStringTunningParameter = 50;
        static final int TRANSACTION_setVolume = 33;
        static final int TRANSACTION_tune = 3;

        public Stub() {
            attachInterface(this, IFMPlayer.DESCRIPTOR);
        }

        public static IFMPlayer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFMPlayer.DESCRIPTOR);
            if (iin != null && (iin instanceof IFMPlayer)) {
                return (IFMPlayer) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setListener";
                case 2:
                    return "removeListener";
                case 3:
                    return TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE;
                case 4:
                    return "on";
                case 5:
                    return "on_in_testmode";
                case 6:
                    return "off";
                case 7:
                    return "isOn";
                case 8:
                    return "seekUp";
                case 9:
                    return "seekDown";
                case 10:
                    return "cancelSeek";
                case 11:
                    return "getCurrentChannel";
                case 12:
                    return "scan";
                case 13:
                    return "cancelScan";
                case 14:
                    return "isScanning";
                case 15:
                    return "isSeeking";
                case 16:
                    return "searchDown";
                case 17:
                    return "searchUp";
                case 18:
                    return "searchAll";
                case 19:
                    return "getPlayedFreq";
                case 20:
                    return "enableRDS";
                case 21:
                    return "disableRDS";
                case 22:
                    return "enableAF";
                case 23:
                    return "disableAF";
                case 24:
                    return "setBand";
                case 25:
                    return "setChannelSpacing";
                case 26:
                    return "isBusy";
                case 27:
                    return "isRDSEnable";
                case 28:
                    return "isAFEnable";
                case 29:
                    return "cancelAFSwitching";
                case 30:
                    return "getLastScanResult";
                case 31:
                    return "setStereo";
                case 32:
                    return "setMono";
                case 33:
                    return "setVolume";
                case 34:
                    return "getVolume";
                case 35:
                    return "isHeadsetPlugged";
                case 36:
                    return "isTvOutPlugged";
                case 37:
                    return "setSpeakerOn";
                case 38:
                    return "setRecordMode";
                case 39:
                    return "getMaxVolume";
                case 40:
                    return "isAirPlaneMode";
                case 41:
                    return "mute";
                case 42:
                    return "isBatteryLow";
                case 43:
                    return "setFMIntenna";
                case 44:
                    return "setSoftmute";
                case 45:
                    return "getSoftMuteMode";
                case 46:
                    return "setIntegerTunningParameter";
                case 47:
                    return "getIntegerTunningParameter";
                case 48:
                    return "setLongTunningParameter";
                case 49:
                    return "getLongTunningParameter";
                case 50:
                    return "setStringTunningParameter";
                case 51:
                    return "getStringTunningParameter";
                case 52:
                    return "isDeviceSpeakerEnabled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFMPlayer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFMPlayer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IFMEventListener _arg0 =
                            IFMEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setListener(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    IFMEventListener _arg02 =
                            IFMEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    long _arg03 = data.readLong();
                    data.enforceNoDataAvail();
                    tune(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    boolean _result = on();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 5:
                    boolean _result2 = on_in_testmode();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 6:
                    boolean _result3 = off();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 7:
                    boolean _result4 = isOn();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 8:
                    long _result5 = seekUp();
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 9:
                    long _result6 = seekDown();
                    reply.writeNoException();
                    reply.writeLong(_result6);
                    return true;
                case 10:
                    cancelSeek();
                    reply.writeNoException();
                    return true;
                case 11:
                    long _result7 = getCurrentChannel();
                    reply.writeNoException();
                    reply.writeLong(_result7);
                    return true;
                case 12:
                    scan();
                    reply.writeNoException();
                    return true;
                case 13:
                    boolean _result8 = cancelScan();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 14:
                    boolean _result9 = isScanning();
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 15:
                    boolean _result10 = isSeeking();
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 16:
                    long _result11 = searchDown();
                    reply.writeNoException();
                    reply.writeLong(_result11);
                    return true;
                case 17:
                    long _result12 = searchUp();
                    reply.writeNoException();
                    reply.writeLong(_result12);
                    return true;
                case 18:
                    long _result13 = searchAll();
                    reply.writeNoException();
                    reply.writeLong(_result13);
                    return true;
                case 19:
                    long _result14 = getPlayedFreq();
                    reply.writeNoException();
                    reply.writeLong(_result14);
                    return true;
                case 20:
                    enableRDS();
                    reply.writeNoException();
                    return true;
                case 21:
                    disableRDS();
                    reply.writeNoException();
                    return true;
                case 22:
                    enableAF();
                    reply.writeNoException();
                    return true;
                case 23:
                    disableAF();
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    setBand(_arg04);
                    reply.writeNoException();
                    return true;
                case 25:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    setChannelSpacing(_arg05);
                    reply.writeNoException();
                    return true;
                case 26:
                    int _result15 = isBusy();
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 27:
                    boolean _result16 = isRDSEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 28:
                    boolean _result17 = isAFEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 29:
                    cancelAFSwitching();
                    reply.writeNoException();
                    return true;
                case 30:
                    long[] _result18 = getLastScanResult();
                    reply.writeNoException();
                    reply.writeLongArray(_result18);
                    return true;
                case 31:
                    setStereo();
                    reply.writeNoException();
                    return true;
                case 32:
                    setMono();
                    reply.writeNoException();
                    return true;
                case 33:
                    long _arg06 = data.readLong();
                    data.enforceNoDataAvail();
                    setVolume(_arg06);
                    reply.writeNoException();
                    return true;
                case 34:
                    long _result19 = getVolume();
                    reply.writeNoException();
                    reply.writeLong(_result19);
                    return true;
                case 35:
                    boolean _result20 = isHeadsetPlugged();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 36:
                    boolean _result21 = isTvOutPlugged();
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 37:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSpeakerOn(_arg07);
                    reply.writeNoException();
                    return true;
                case 38:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRecordMode(_arg08);
                    reply.writeNoException();
                    return true;
                case 39:
                    long _result22 = getMaxVolume();
                    reply.writeNoException();
                    reply.writeLong(_result22);
                    return true;
                case 40:
                    boolean _result23 = isAirPlaneMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 41:
                    boolean _arg09 = data.readBoolean();
                    data.enforceNoDataAvail();
                    mute(_arg09);
                    reply.writeNoException();
                    return true;
                case 42:
                    boolean _result24 = isBatteryLow();
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 43:
                    boolean _arg010 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFMIntenna(_arg010);
                    reply.writeNoException();
                    return true;
                case 44:
                    boolean _arg011 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSoftmute(_arg011);
                    reply.writeNoException();
                    return true;
                case 45:
                    boolean _result25 = getSoftMuteMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 46:
                    String _arg012 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    setIntegerTunningParameter(_arg012, _arg1);
                    reply.writeNoException();
                    return true;
                case 47:
                    String _arg013 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result26 = getIntegerTunningParameter(_arg013, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result26);
                    return true;
                case 48:
                    String _arg014 = data.readString();
                    long _arg13 = data.readLong();
                    data.enforceNoDataAvail();
                    setLongTunningParameter(_arg014, _arg13);
                    reply.writeNoException();
                    return true;
                case 49:
                    String _arg015 = data.readString();
                    long _arg14 = data.readLong();
                    data.enforceNoDataAvail();
                    long _result27 = getLongTunningParameter(_arg015, _arg14);
                    reply.writeNoException();
                    reply.writeLong(_result27);
                    return true;
                case 50:
                    String _arg016 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    setStringTunningParameter(_arg016, _arg15);
                    reply.writeNoException();
                    return true;
                case 51:
                    String _arg017 = data.readString();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    String _result28 = getStringTunningParameter(_arg017, _arg16);
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case 52:
                    boolean _result29 = isDeviceSpeakerEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFMPlayer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFMPlayer.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setListener(IFMEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void removeListener(IFMEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void tune(long freq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeLong(freq);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean on() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean on_in_testmode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean off() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long seekUp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long seekDown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void cancelSeek() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getCurrentChannel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void scan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean cancelScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isScanning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isSeeking() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long searchDown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long searchUp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long searchAll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getPlayedFreq() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void enableRDS() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void disableRDS() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void enableAF() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void disableAF() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setBand(int band) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeInt(band);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setChannelSpacing(int spacing) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeInt(spacing);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public int isBusy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isRDSEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isAFEnable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void cancelAFSwitching() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long[] getLastScanResult() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setStereo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setMono() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setVolume(long val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeLong(val);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getVolume() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isHeadsetPlugged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isTvOutPlugged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setSpeakerOn(boolean bSpeakerOn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeBoolean(bSpeakerOn);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setRecordMode(boolean isRecord) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeBoolean(isRecord);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getMaxVolume() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isAirPlaneMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void mute(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isBatteryLow() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setFMIntenna(boolean setFMIntenna) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeBoolean(setFMIntenna);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setSoftmute(boolean state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeBoolean(state);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean getSoftMuteMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setIntegerTunningParameter(String parameterName, int value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeString(parameterName);
                    _data.writeInt(value);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public int getIntegerTunningParameter(String parameterName, int defaultValue)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeString(parameterName);
                    _data.writeInt(defaultValue);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setLongTunningParameter(String parameterName, long value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeString(parameterName);
                    _data.writeLong(value);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getLongTunningParameter(String parameterName, long defaultValue)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeString(parameterName);
                    _data.writeLong(defaultValue);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setStringTunningParameter(String parameterName, String value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeString(parameterName);
                    _data.writeString(value);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public String getStringTunningParameter(String parameterName, String defaultValue)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    _data.writeString(parameterName);
                    _data.writeString(defaultValue);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isDeviceSpeakerEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 51;
        }
    }
}
