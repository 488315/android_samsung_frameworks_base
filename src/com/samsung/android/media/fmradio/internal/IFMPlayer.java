package com.samsung.android.media.fmradio.internal;

import android.media.tv.interactive.TvInteractiveAppService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.media.fmradio.internal.IFMEventListener;

/* loaded from: classes6.dex */
public interface IFMPlayer extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.fmradio.internal.IFMPlayer";

    public static class Default implements IFMPlayer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void cancelAFSwitching() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean cancelScan() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void cancelSeek() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void disableAF() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void disableRDS() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void enableAF() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void enableRDS() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getCurrentChannel() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public int getIntegerTunningParameter(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long[] getLastScanResult() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getLongTunningParameter(String str, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getMaxVolume() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getPlayedFreq() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean getSoftMuteMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public String getStringTunningParameter(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long getVolume() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isAFEnable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isAirPlaneMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isBatteryLow() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public int isBusy() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isDeviceSpeakerEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isHeadsetPlugged() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isOn() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean isRDSEnable() throws RemoteException {
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
        public boolean isTvOutPlugged() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void mute(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean off() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean on() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public boolean on_in_testmode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void removeListener(IFMEventListener iFMEventListener) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void scan() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long searchAll() throws RemoteException {
            return 0L;
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
        public long seekDown() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public long seekUp() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setBand(int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setChannelSpacing(int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setFMIntenna(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setIntegerTunningParameter(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setListener(IFMEventListener iFMEventListener) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setLongTunningParameter(String str, long j) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setMono() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setRecordMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setSoftmute(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setSpeakerOn(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setStereo() throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setStringTunningParameter(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void setVolume(long j) throws RemoteException {
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
        public void tune(long j) throws RemoteException {
        }
    }

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

    public static abstract class Stub extends Binder implements IFMPlayer {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 51;
        }

        public Stub() {
            attachInterface(this, IFMPlayer.DESCRIPTOR);
        }

        public static IFMPlayer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFMPlayer.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFMPlayer)) {
                return (IFMPlayer) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFMPlayer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFMPlayer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IFMEventListener iFMEventListenerAsInterface = IFMEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(iFMEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IFMEventListener iFMEventListenerAsInterface2 = IFMEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(iFMEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    tune(j);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean zOn = on();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOn);
                    return true;
                case 5:
                    boolean zOn_in_testmode = on_in_testmode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOn_in_testmode);
                    return true;
                case 6:
                    boolean zOff = off();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOff);
                    return true;
                case 7:
                    boolean zIsOn = isOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOn);
                    return true;
                case 8:
                    long jSeekUp = seekUp();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSeekUp);
                    return true;
                case 9:
                    long jSeekDown = seekDown();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSeekDown);
                    return true;
                case 10:
                    cancelSeek();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    long currentChannel = getCurrentChannel();
                    parcel2.writeNoException();
                    parcel2.writeLong(currentChannel);
                    return true;
                case 12:
                    scan();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean zCancelScan = cancelScan();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCancelScan);
                    return true;
                case 14:
                    boolean zIsScanning = isScanning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScanning);
                    return true;
                case 15:
                    boolean zIsSeeking = isSeeking();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSeeking);
                    return true;
                case 16:
                    long jSearchDown = searchDown();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSearchDown);
                    return true;
                case 17:
                    long jSearchUp = searchUp();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSearchUp);
                    return true;
                case 18:
                    long jSearchAll = searchAll();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSearchAll);
                    return true;
                case 19:
                    long playedFreq = getPlayedFreq();
                    parcel2.writeNoException();
                    parcel2.writeLong(playedFreq);
                    return true;
                case 20:
                    enableRDS();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    disableRDS();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    enableAF();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    disableAF();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBand(i3);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setChannelSpacing(i4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int iIsBusy = isBusy();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsBusy);
                    return true;
                case 27:
                    boolean zIsRDSEnable = isRDSEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRDSEnable);
                    return true;
                case 28:
                    boolean zIsAFEnable = isAFEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAFEnable);
                    return true;
                case 29:
                    cancelAFSwitching();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    long[] lastScanResult = getLastScanResult();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(lastScanResult);
                    return true;
                case 31:
                    setStereo();
                    parcel2.writeNoException();
                    return true;
                case 32:
                    setMono();
                    parcel2.writeNoException();
                    return true;
                case 33:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setVolume(j2);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    long volume = getVolume();
                    parcel2.writeNoException();
                    parcel2.writeLong(volume);
                    return true;
                case 35:
                    boolean zIsHeadsetPlugged = isHeadsetPlugged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeadsetPlugged);
                    return true;
                case 36:
                    boolean zIsTvOutPlugged = isTvOutPlugged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTvOutPlugged);
                    return true;
                case 37:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpeakerOn(z);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecordMode(z2);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    long maxVolume = getMaxVolume();
                    parcel2.writeNoException();
                    parcel2.writeLong(maxVolume);
                    return true;
                case 40:
                    boolean zIsAirPlaneMode = isAirPlaneMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAirPlaneMode);
                    return true;
                case 41:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    mute(z3);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean zIsBatteryLow = isBatteryLow();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBatteryLow);
                    return true;
                case 43:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFMIntenna(z4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSoftmute(z5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    boolean softMuteMode = getSoftMuteMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(softMuteMode);
                    return true;
                case 46:
                    String string = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIntegerTunningParameter(string, i5);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String string2 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int integerTunningParameter = getIntegerTunningParameter(string2, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(integerTunningParameter);
                    return true;
                case 48:
                    String string3 = parcel.readString();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setLongTunningParameter(string3, j3);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string4 = parcel.readString();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long longTunningParameter = getLongTunningParameter(string4, j4);
                    parcel2.writeNoException();
                    parcel2.writeLong(longTunningParameter);
                    return true;
                case 50:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setStringTunningParameter(string5, string6);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String stringTunningParameter = getStringTunningParameter(string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeString(stringTunningParameter);
                    return true;
                case 52:
                    boolean zIsDeviceSpeakerEnabled = isDeviceSpeakerEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceSpeakerEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFMPlayer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFMPlayer.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setListener(IFMEventListener iFMEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFMEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void removeListener(IFMEventListener iFMEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFMEventListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void tune(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean on() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean on_in_testmode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean off() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long seekUp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long seekDown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void cancelSeek() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getCurrentChannel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void scan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean cancelScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isScanning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isSeeking() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long searchDown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long searchUp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long searchAll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getPlayedFreq() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void enableRDS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void disableRDS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void enableAF() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void disableAF() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setBand(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setChannelSpacing(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public int isBusy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isRDSEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isAFEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void cancelAFSwitching() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long[] getLastScanResult() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setStereo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setMono() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setVolume(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getVolume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isHeadsetPlugged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isTvOutPlugged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setSpeakerOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setRecordMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getMaxVolume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isAirPlaneMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void mute(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isBatteryLow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setFMIntenna(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setSoftmute(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean getSoftMuteMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setIntegerTunningParameter(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public int getIntegerTunningParameter(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setLongTunningParameter(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public long getLongTunningParameter(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public void setStringTunningParameter(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public String getStringTunningParameter(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.fmradio.internal.IFMPlayer
            public boolean isDeviceSpeakerEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFMPlayer.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
