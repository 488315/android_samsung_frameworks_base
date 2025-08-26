package android.hardware.hdmi;

import android.hardware.hdmi.IHdmiCecSettingChangeListener;
import android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiControlStatusChangeListener;
import android.hardware.hdmi.IHdmiDeviceEventListener;
import android.hardware.hdmi.IHdmiHotplugEventListener;
import android.hardware.hdmi.IHdmiInputChangeListener;
import android.hardware.hdmi.IHdmiMhlVendorCommandListener;
import android.hardware.hdmi.IHdmiRecordListener;
import android.hardware.hdmi.IHdmiSystemAudioModeChangeListener;
import android.hardware.hdmi.IHdmiVendorCommandListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IHdmiControlService extends IInterface {

    public static class Default implements IHdmiControlService {
        @Override // android.hardware.hdmi.IHdmiControlService
        public void addCecSettingChangeListener(String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addDeviceEventListener(IHdmiDeviceEventListener iHdmiDeviceEventListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiCecVolumeControlFeatureListener(IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiControlStatusChangeListener(IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void addVendorCommandListener(IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void askRemoteDeviceToBecomeActiveSource(int i) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean canChangeSystemAudioMode() throws RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void clearTimerRecording(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void deviceSelect(int i, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public HdmiDeviceInfo getActiveSource() throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int[] getAllowedCecSettingIntValues(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public List<String> getAllowedCecSettingStringValues(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getCecSettingIntValue(String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public String getCecSettingStringValue(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public List<HdmiDeviceInfo> getDeviceList() throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public List<HdmiDeviceInfo> getInputDevices() throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getMessageHistorySize() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int getPhysicalAddress() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public List<HdmiPortInfo> getPortInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public int[] getSupportedTypes() throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean getSystemAudioMode() throws RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public List<String> getUserCecSettings() throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void portSelect(int i, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void powerOffRemoteDevice(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void powerOnRemoteDevice(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeCecSettingChangeListener(String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHdmiCecVolumeControlFeatureListener(IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHdmiControlStatusChangeListener(IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void reportAudioStatus(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendKeyEvent(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendStandby(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void sendVolumeKeyEvent(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setArcMode(boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setCecSettingIntValue(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setCecSettingStringValue(String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setHdmiRecordListener(IHdmiRecordListener iHdmiRecordListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setInputChangeListener(IHdmiInputChangeListener iHdmiInputChangeListener) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean setMessageHistorySize(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setProhibitMode(boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setStandbyMode(boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioMode(boolean z, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioModeOnForAudioOnlySource() throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioMute(boolean z) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void setSystemAudioVolume(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public boolean shouldHandleTvPowerKey() throws RemoteException {
            return false;
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void startOneTouchRecord(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void startTimerRecording(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void stopOneTouchRecord(int i) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiControlService
        public void toggleAndFollowTvPower() throws RemoteException {
        }
    }

    void addCecSettingChangeListener(String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws RemoteException;

    void addDeviceEventListener(IHdmiDeviceEventListener iHdmiDeviceEventListener) throws RemoteException;

    void addHdmiCecVolumeControlFeatureListener(IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws RemoteException;

    void addHdmiControlStatusChangeListener(IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws RemoteException;

    void addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws RemoteException;

    void addHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException;

    void addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException;

    void addVendorCommandListener(IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) throws RemoteException;

    void askRemoteDeviceToBecomeActiveSource(int i) throws RemoteException;

    boolean canChangeSystemAudioMode() throws RemoteException;

    void clearTimerRecording(int i, int i2, byte[] bArr) throws RemoteException;

    void deviceSelect(int i, IHdmiControlCallback iHdmiControlCallback) throws RemoteException;

    HdmiDeviceInfo getActiveSource() throws RemoteException;

    int[] getAllowedCecSettingIntValues(String str) throws RemoteException;

    List<String> getAllowedCecSettingStringValues(String str) throws RemoteException;

    int getCecSettingIntValue(String str) throws RemoteException;

    String getCecSettingStringValue(String str) throws RemoteException;

    List<HdmiDeviceInfo> getDeviceList() throws RemoteException;

    List<HdmiDeviceInfo> getInputDevices() throws RemoteException;

    int getMessageHistorySize() throws RemoteException;

    int getPhysicalAddress() throws RemoteException;

    List<HdmiPortInfo> getPortInfo() throws RemoteException;

    int[] getSupportedTypes() throws RemoteException;

    boolean getSystemAudioMode() throws RemoteException;

    List<String> getUserCecSettings() throws RemoteException;

    void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) throws RemoteException;

    void portSelect(int i, IHdmiControlCallback iHdmiControlCallback) throws RemoteException;

    void powerOffRemoteDevice(int i, int i2) throws RemoteException;

    void powerOnRemoteDevice(int i, int i2) throws RemoteException;

    void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) throws RemoteException;

    void removeCecSettingChangeListener(String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws RemoteException;

    void removeHdmiCecVolumeControlFeatureListener(IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws RemoteException;

    void removeHdmiControlStatusChangeListener(IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws RemoteException;

    void removeHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException;

    void removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException;

    void reportAudioStatus(int i, int i2, int i3, boolean z) throws RemoteException;

    void sendKeyEvent(int i, int i2, boolean z) throws RemoteException;

    void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void sendStandby(int i, int i2) throws RemoteException;

    void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) throws RemoteException;

    void sendVolumeKeyEvent(int i, int i2, boolean z) throws RemoteException;

    void setArcMode(boolean z) throws RemoteException;

    void setCecSettingIntValue(String str, int i) throws RemoteException;

    void setCecSettingStringValue(String str, String str2) throws RemoteException;

    void setHdmiRecordListener(IHdmiRecordListener iHdmiRecordListener) throws RemoteException;

    void setInputChangeListener(IHdmiInputChangeListener iHdmiInputChangeListener) throws RemoteException;

    boolean setMessageHistorySize(int i) throws RemoteException;

    void setProhibitMode(boolean z) throws RemoteException;

    void setStandbyMode(boolean z) throws RemoteException;

    void setSystemAudioMode(boolean z, IHdmiControlCallback iHdmiControlCallback) throws RemoteException;

    void setSystemAudioModeOnForAudioOnlySource() throws RemoteException;

    void setSystemAudioMute(boolean z) throws RemoteException;

    void setSystemAudioVolume(int i, int i2, int i3) throws RemoteException;

    boolean shouldHandleTvPowerKey() throws RemoteException;

    void startOneTouchRecord(int i, byte[] bArr) throws RemoteException;

    void startTimerRecording(int i, int i2, byte[] bArr) throws RemoteException;

    void stopOneTouchRecord(int i) throws RemoteException;

    void toggleAndFollowTvPower() throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiControlService {
        public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiControlService";
        static final int TRANSACTION_addCecSettingChangeListener = 50;
        static final int TRANSACTION_addDeviceEventListener = 13;
        static final int TRANSACTION_addHdmiCecVolumeControlFeatureListener = 9;
        static final int TRANSACTION_addHdmiControlStatusChangeListener = 7;
        static final int TRANSACTION_addHdmiMhlVendorCommandListener = 44;
        static final int TRANSACTION_addHotplugEventListener = 11;
        static final int TRANSACTION_addSystemAudioModeChangeListener = 23;
        static final int TRANSACTION_addVendorCommandListener = 36;
        static final int TRANSACTION_askRemoteDeviceToBecomeActiveSource = 34;
        static final int TRANSACTION_canChangeSystemAudioMode = 19;
        static final int TRANSACTION_clearTimerRecording = 42;
        static final int TRANSACTION_deviceSelect = 14;
        static final int TRANSACTION_getActiveSource = 2;
        static final int TRANSACTION_getAllowedCecSettingIntValues = 54;
        static final int TRANSACTION_getAllowedCecSettingStringValues = 53;
        static final int TRANSACTION_getCecSettingIntValue = 57;
        static final int TRANSACTION_getCecSettingStringValue = 55;
        static final int TRANSACTION_getDeviceList = 31;
        static final int TRANSACTION_getInputDevices = 30;
        static final int TRANSACTION_getMessageHistorySize = 49;
        static final int TRANSACTION_getPhysicalAddress = 21;
        static final int TRANSACTION_getPortInfo = 18;
        static final int TRANSACTION_getSupportedTypes = 1;
        static final int TRANSACTION_getSystemAudioMode = 20;
        static final int TRANSACTION_getUserCecSettings = 52;
        static final int TRANSACTION_oneTouchPlay = 3;
        static final int TRANSACTION_portSelect = 15;
        static final int TRANSACTION_powerOffRemoteDevice = 32;
        static final int TRANSACTION_powerOnRemoteDevice = 33;
        static final int TRANSACTION_queryDisplayStatus = 6;
        static final int TRANSACTION_removeCecSettingChangeListener = 51;
        static final int TRANSACTION_removeHdmiCecVolumeControlFeatureListener = 10;
        static final int TRANSACTION_removeHdmiControlStatusChangeListener = 8;
        static final int TRANSACTION_removeHotplugEventListener = 12;
        static final int TRANSACTION_removeSystemAudioModeChangeListener = 24;
        static final int TRANSACTION_reportAudioStatus = 46;
        static final int TRANSACTION_sendKeyEvent = 16;
        static final int TRANSACTION_sendMhlVendorCommand = 43;
        static final int TRANSACTION_sendStandby = 37;
        static final int TRANSACTION_sendVendorCommand = 35;
        static final int TRANSACTION_sendVolumeKeyEvent = 17;
        static final int TRANSACTION_setArcMode = 25;
        static final int TRANSACTION_setCecSettingIntValue = 58;
        static final int TRANSACTION_setCecSettingStringValue = 56;
        static final int TRANSACTION_setHdmiRecordListener = 38;
        static final int TRANSACTION_setInputChangeListener = 29;
        static final int TRANSACTION_setMessageHistorySize = 48;
        static final int TRANSACTION_setProhibitMode = 26;
        static final int TRANSACTION_setStandbyMode = 45;
        static final int TRANSACTION_setSystemAudioMode = 22;
        static final int TRANSACTION_setSystemAudioModeOnForAudioOnlySource = 47;
        static final int TRANSACTION_setSystemAudioMute = 28;
        static final int TRANSACTION_setSystemAudioVolume = 27;
        static final int TRANSACTION_shouldHandleTvPowerKey = 5;
        static final int TRANSACTION_startOneTouchRecord = 39;
        static final int TRANSACTION_startTimerRecording = 41;
        static final int TRANSACTION_stopOneTouchRecord = 40;
        static final int TRANSACTION_toggleAndFollowTvPower = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 57;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiControlService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHdmiControlService)) {
                return (IHdmiControlService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedTypes";
                case 2:
                    return "getActiveSource";
                case 3:
                    return "oneTouchPlay";
                case 4:
                    return "toggleAndFollowTvPower";
                case 5:
                    return "shouldHandleTvPowerKey";
                case 6:
                    return "queryDisplayStatus";
                case 7:
                    return "addHdmiControlStatusChangeListener";
                case 8:
                    return "removeHdmiControlStatusChangeListener";
                case 9:
                    return "addHdmiCecVolumeControlFeatureListener";
                case 10:
                    return "removeHdmiCecVolumeControlFeatureListener";
                case 11:
                    return "addHotplugEventListener";
                case 12:
                    return "removeHotplugEventListener";
                case 13:
                    return "addDeviceEventListener";
                case 14:
                    return "deviceSelect";
                case 15:
                    return "portSelect";
                case 16:
                    return "sendKeyEvent";
                case 17:
                    return "sendVolumeKeyEvent";
                case 18:
                    return "getPortInfo";
                case 19:
                    return "canChangeSystemAudioMode";
                case 20:
                    return "getSystemAudioMode";
                case 21:
                    return "getPhysicalAddress";
                case 22:
                    return "setSystemAudioMode";
                case 23:
                    return "addSystemAudioModeChangeListener";
                case 24:
                    return "removeSystemAudioModeChangeListener";
                case 25:
                    return "setArcMode";
                case 26:
                    return "setProhibitMode";
                case 27:
                    return "setSystemAudioVolume";
                case 28:
                    return "setSystemAudioMute";
                case 29:
                    return "setInputChangeListener";
                case 30:
                    return "getInputDevices";
                case 31:
                    return "getDeviceList";
                case 32:
                    return "powerOffRemoteDevice";
                case 33:
                    return "powerOnRemoteDevice";
                case 34:
                    return "askRemoteDeviceToBecomeActiveSource";
                case 35:
                    return "sendVendorCommand";
                case 36:
                    return "addVendorCommandListener";
                case 37:
                    return "sendStandby";
                case 38:
                    return "setHdmiRecordListener";
                case 39:
                    return "startOneTouchRecord";
                case 40:
                    return "stopOneTouchRecord";
                case 41:
                    return "startTimerRecording";
                case 42:
                    return "clearTimerRecording";
                case 43:
                    return "sendMhlVendorCommand";
                case 44:
                    return "addHdmiMhlVendorCommandListener";
                case 45:
                    return "setStandbyMode";
                case 46:
                    return "reportAudioStatus";
                case 47:
                    return "setSystemAudioModeOnForAudioOnlySource";
                case 48:
                    return "setMessageHistorySize";
                case 49:
                    return "getMessageHistorySize";
                case 50:
                    return "addCecSettingChangeListener";
                case 51:
                    return "removeCecSettingChangeListener";
                case 52:
                    return "getUserCecSettings";
                case 53:
                    return "getAllowedCecSettingStringValues";
                case 54:
                    return "getAllowedCecSettingIntValues";
                case 55:
                    return "getCecSettingStringValue";
                case 56:
                    return "setCecSettingStringValue";
                case 57:
                    return "getCecSettingIntValue";
                case 58:
                    return "setCecSettingIntValue";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] supportedTypes = getSupportedTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedTypes);
                    return true;
                case 2:
                    HdmiDeviceInfo activeSource = getActiveSource();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSource, 1);
                    return true;
                case 3:
                    IHdmiControlCallback iHdmiControlCallbackAsInterface = IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    oneTouchPlay(iHdmiControlCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    toggleAndFollowTvPower();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean zShouldHandleTvPowerKey = shouldHandleTvPowerKey();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldHandleTvPowerKey);
                    return true;
                case 6:
                    IHdmiControlCallback iHdmiControlCallbackAsInterface2 = IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryDisplayStatus(iHdmiControlCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IHdmiControlStatusChangeListener iHdmiControlStatusChangeListenerAsInterface = IHdmiControlStatusChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHdmiControlStatusChangeListener(iHdmiControlStatusChangeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IHdmiControlStatusChangeListener iHdmiControlStatusChangeListenerAsInterface2 = IHdmiControlStatusChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeHdmiControlStatusChangeListener(iHdmiControlStatusChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListenerAsInterface = IHdmiCecVolumeControlFeatureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHdmiCecVolumeControlFeatureListener(iHdmiCecVolumeControlFeatureListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListenerAsInterface2 = IHdmiCecVolumeControlFeatureListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeHdmiCecVolumeControlFeatureListener(iHdmiCecVolumeControlFeatureListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IHdmiHotplugEventListener iHdmiHotplugEventListenerAsInterface = IHdmiHotplugEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHotplugEventListener(iHdmiHotplugEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IHdmiHotplugEventListener iHdmiHotplugEventListenerAsInterface2 = IHdmiHotplugEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeHotplugEventListener(iHdmiHotplugEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IHdmiDeviceEventListener iHdmiDeviceEventListenerAsInterface = IHdmiDeviceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addDeviceEventListener(iHdmiDeviceEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i3 = parcel.readInt();
                    IHdmiControlCallback iHdmiControlCallbackAsInterface3 = IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deviceSelect(i3, iHdmiControlCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i4 = parcel.readInt();
                    IHdmiControlCallback iHdmiControlCallbackAsInterface4 = IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    portSelect(i4, iHdmiControlCallbackAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendKeyEvent(i5, i6, z);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendVolumeKeyEvent(i7, i8, z2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    List<HdmiPortInfo> portInfo = getPortInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(portInfo, 1);
                    return true;
                case 19:
                    boolean zCanChangeSystemAudioMode = canChangeSystemAudioMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanChangeSystemAudioMode);
                    return true;
                case 20:
                    boolean systemAudioMode = getSystemAudioMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(systemAudioMode);
                    return true;
                case 21:
                    int physicalAddress = getPhysicalAddress();
                    parcel2.writeNoException();
                    parcel2.writeInt(physicalAddress);
                    return true;
                case 22:
                    boolean z3 = parcel.readBoolean();
                    IHdmiControlCallback iHdmiControlCallbackAsInterface5 = IHdmiControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSystemAudioMode(z3, iHdmiControlCallbackAsInterface5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListenerAsInterface = IHdmiSystemAudioModeChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListenerAsInterface2 = IHdmiSystemAudioModeChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setArcMode(z4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setProhibitMode(z5);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioVolume(i9, i10, i11);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSystemAudioMute(z6);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IHdmiInputChangeListener iHdmiInputChangeListenerAsInterface = IHdmiInputChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInputChangeListener(iHdmiInputChangeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    List<HdmiDeviceInfo> inputDevices = getInputDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(inputDevices, 1);
                    return true;
                case 31:
                    List<HdmiDeviceInfo> deviceList = getDeviceList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(deviceList, 1);
                    return true;
                case 32:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    powerOffRemoteDevice(i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    powerOnRemoteDevice(i14, i15);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    askRemoteDeviceToBecomeActiveSource(i16);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendVendorCommand(i17, i18, bArrCreateByteArray, z7);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    IHdmiVendorCommandListener iHdmiVendorCommandListenerAsInterface = IHdmiVendorCommandListener.Stub.asInterface(parcel.readStrongBinder());
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addVendorCommandListener(iHdmiVendorCommandListenerAsInterface, i19);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStandby(i20, i21);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IHdmiRecordListener iHdmiRecordListenerAsInterface = IHdmiRecordListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setHdmiRecordListener(iHdmiRecordListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i22 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    startOneTouchRecord(i22, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOneTouchRecord(i23);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    startTimerRecording(i24, i25, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    clearTimerRecording(i26, i27, bArrCreateByteArray4);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendMhlVendorCommand(i28, i29, i30, bArrCreateByteArray5);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListenerAsInterface = IHdmiMhlVendorCommandListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addHdmiMhlVendorCommandListener(iHdmiMhlVendorCommandListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStandbyMode(z8);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportAudioStatus(i31, i32, i33, z9);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    setSystemAudioModeOnForAudioOnlySource();
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean messageHistorySize = setMessageHistorySize(i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(messageHistorySize);
                    return true;
                case 49:
                    int messageHistorySize2 = getMessageHistorySize();
                    parcel2.writeNoException();
                    parcel2.writeInt(messageHistorySize2);
                    return true;
                case 50:
                    String string = parcel.readString();
                    IHdmiCecSettingChangeListener iHdmiCecSettingChangeListenerAsInterface = IHdmiCecSettingChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCecSettingChangeListener(string, iHdmiCecSettingChangeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String string2 = parcel.readString();
                    IHdmiCecSettingChangeListener iHdmiCecSettingChangeListenerAsInterface2 = IHdmiCecSettingChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCecSettingChangeListener(string2, iHdmiCecSettingChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    List<String> userCecSettings = getUserCecSettings();
                    parcel2.writeNoException();
                    parcel2.writeStringList(userCecSettings);
                    return true;
                case 53:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> allowedCecSettingStringValues = getAllowedCecSettingStringValues(string3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedCecSettingStringValues);
                    return true;
                case 54:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] allowedCecSettingIntValues = getAllowedCecSettingIntValues(string4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allowedCecSettingIntValues);
                    return true;
                case 55:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String cecSettingStringValue = getCecSettingStringValue(string5);
                    parcel2.writeNoException();
                    parcel2.writeString(cecSettingStringValue);
                    return true;
                case 56:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCecSettingStringValue(string6, string7);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cecSettingIntValue = getCecSettingIntValue(string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(cecSettingIntValue);
                    return true;
                case 58:
                    String string9 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCecSettingIntValue(string9, i35);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IHdmiControlService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int[] getSupportedTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public HdmiDeviceInfo getActiveSource() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (HdmiDeviceInfo) parcelObtain2.readTypedObject(HdmiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void toggleAndFollowTvPower() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean shouldHandleTvPowerKey() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHdmiControlStatusChangeListener(IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiControlStatusChangeListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeHdmiControlStatusChangeListener(IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiControlStatusChangeListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHdmiCecVolumeControlFeatureListener(IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiCecVolumeControlFeatureListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeHdmiCecVolumeControlFeatureListener(IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiCecVolumeControlFeatureListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiHotplugEventListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiHotplugEventListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addDeviceEventListener(IHdmiDeviceEventListener iHdmiDeviceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiDeviceEventListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void deviceSelect(int i, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void portSelect(int i, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendKeyEvent(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendVolumeKeyEvent(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public List<HdmiPortInfo> getPortInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HdmiPortInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean canChangeSystemAudioMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean getSystemAudioMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int getPhysicalAddress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioMode(boolean z, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iHdmiControlCallback);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiSystemAudioModeChangeListener);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiSystemAudioModeChangeListener);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setArcMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setProhibitMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioVolume(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioMute(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setInputChangeListener(IHdmiInputChangeListener iHdmiInputChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiInputChangeListener);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public List<HdmiDeviceInfo> getInputDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HdmiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public List<HdmiDeviceInfo> getDeviceList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HdmiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void powerOffRemoteDevice(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void powerOnRemoteDevice(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void askRemoteDeviceToBecomeActiveSource(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendVendorCommand(int i, int i2, byte[] bArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addVendorCommandListener(IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiVendorCommandListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendStandby(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setHdmiRecordListener(IHdmiRecordListener iHdmiRecordListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiRecordListener);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void startOneTouchRecord(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void stopOneTouchRecord(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void startTimerRecording(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void clearTimerRecording(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iHdmiMhlVendorCommandListener);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setStandbyMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void reportAudioStatus(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setSystemAudioModeOnForAudioOnlySource() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public boolean setMessageHistorySize(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int getMessageHistorySize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void addCecSettingChangeListener(String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iHdmiCecSettingChangeListener);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void removeCecSettingChangeListener(String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iHdmiCecSettingChangeListener);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public List<String> getUserCecSettings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public List<String> getAllowedCecSettingStringValues(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int[] getAllowedCecSettingIntValues(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public String getCecSettingStringValue(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setCecSettingStringValue(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public int getCecSettingIntValue(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiControlService
            public void setCecSettingIntValue(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
