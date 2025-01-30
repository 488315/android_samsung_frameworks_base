package android.hardware.usb;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.IUsbOperationInternal;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import java.util.List;

/* loaded from: classes2.dex */
public interface IUsbManager extends IInterface {
    void addAccessoryPackagesToPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException;

    void addDevicePackagesToPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException;

    void clearDefaults(String str, int i) throws RemoteException;

    void enableContaminantDetection(String str, boolean z) throws RemoteException;

    void enableLimitPowerTransfer(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException;

    boolean enableUsbData(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException;

    void enableUsbDataWhileDocked(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException;

    ParcelFileDescriptor getControlFd(long j) throws RemoteException;

    UsbAccessory getCurrentAccessory() throws RemoteException;

    long getCurrentFunctions() throws RemoteException;

    int getCurrentUsbSpeed() throws RemoteException;

    void getDeviceList(Bundle bundle) throws RemoteException;

    int getGadgetHalVersion() throws RemoteException;

    UsbPortStatus getPortStatus(String str) throws RemoteException;

    List<ParcelableUsbPort> getPorts() throws RemoteException;

    long getScreenUnlockedFunctions() throws RemoteException;

    int getUsbHalVersion() throws RemoteException;

    void grantAccessoryPermission(UsbAccessory usbAccessory, int i) throws RemoteException;

    void grantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException;

    boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException;

    boolean hasAccessoryPermissionWithIdentity(UsbAccessory usbAccessory, int i, int i2) throws RemoteException;

    boolean hasDefaults(String str, int i) throws RemoteException;

    boolean hasDevicePermission(UsbDevice usbDevice, String str) throws RemoteException;

    boolean hasDevicePermissionWithIdentity(UsbDevice usbDevice, String str, int i, int i2) throws RemoteException;

    boolean isFunctionEnabled(String str) throws RemoteException;

    boolean isSupportDexRestrict() throws RemoteException;

    boolean isSupportScrLckBlk() throws RemoteException;

    boolean isUsbBlocked() throws RemoteException;

    ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) throws RemoteException;

    ParcelFileDescriptor openDevice(String str, String str2) throws RemoteException;

    boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException;

    void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException;

    void removeDevicePackagesFromPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException;

    void requestAccessoryPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent) throws RemoteException;

    void requestDevicePermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent) throws RemoteException;

    void resetUsbGadget() throws RemoteException;

    void resetUsbPort(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException;

    int restrictUsbHostInterface(boolean z, String str) throws RemoteException;

    int semGetDataRoleStatus() throws RemoteException;

    int semGetPowerRoleStatus() throws RemoteException;

    void semGrantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException;

    void semSetDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException;

    void semSetMode(int i) throws RemoteException;

    void setAccessoryPackage(UsbAccessory usbAccessory, String str, int i) throws RemoteException;

    void setAccessoryPersistentPermission(UsbAccessory usbAccessory, int i, UserHandle userHandle, boolean z) throws RemoteException;

    void setCurrentFunction(String str, boolean z, int i) throws RemoteException;

    void setCurrentFunctions(long j, int i) throws RemoteException;

    void setDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException;

    void setDevicePersistentPermission(UsbDevice usbDevice, int i, UserHandle userHandle, boolean z) throws RemoteException;

    void setPortRoles(String str, int i, int i2) throws RemoteException;

    void setScreenUnlockedFunctions(long j) throws RemoteException;

    void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException;

    void setUsbHiddenMenuState(boolean z) throws RemoteException;

    void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException;

    public static class Default implements IUsbManager {
        @Override // android.hardware.usb.IUsbManager
        public void getDeviceList(Bundle devices) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public ParcelFileDescriptor openDevice(String deviceName, String packageName) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public UsbAccessory getCurrentAccessory() throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public ParcelFileDescriptor openAccessory(UsbAccessory accessory) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setDevicePackage(UsbDevice device, String packageName, int userId) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setAccessoryPackage(UsbAccessory accessory, String packageName, int userId) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void addDevicePackagesToPreferenceDenied(UsbDevice device, String[] packageNames, UserHandle user) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void addAccessoryPackagesToPreferenceDenied(UsbAccessory accessory, String[] packageNames, UserHandle user) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void removeDevicePackagesFromPreferenceDenied(UsbDevice device, String[] packageNames, UserHandle user) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory device, String[] packageNames, UserHandle user) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setDevicePersistentPermission(UsbDevice device, int uid, UserHandle user, boolean shouldBeGranted) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setAccessoryPersistentPermission(UsbAccessory accessory, int uid, UserHandle user, boolean shouldBeGranted) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDevicePermission(UsbDevice device, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDevicePermissionWithIdentity(UsbDevice device, String packageName, int pid, int uid) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasAccessoryPermission(UsbAccessory accessory) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasAccessoryPermissionWithIdentity(UsbAccessory accessory, int pid, int uid) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void requestDevicePermission(UsbDevice device, String packageName, PendingIntent pi) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void requestAccessoryPermission(UsbAccessory accessory, String packageName, PendingIntent pi) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void grantDevicePermission(UsbDevice device, int uid) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void grantAccessoryPermission(UsbAccessory accessory, int uid) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDefaults(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void clearDefaults(String packageName, int userId) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isFunctionEnabled(String function) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setCurrentFunctions(long functions, int operationId) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setCurrentFunction(String function, boolean usbDataUnlocked, int operationId) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public long getCurrentFunctions() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.usb.IUsbManager
        public int getCurrentUsbSpeed() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public int getGadgetHalVersion() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setScreenUnlockedFunctions(long functions) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public long getScreenUnlockedFunctions() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.usb.IUsbManager
        public void resetUsbGadget() throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void resetUsbPort(String portId, int operationId, IUsbOperationInternal callback) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean enableUsbData(String portId, boolean enable, int operationId, IUsbOperationInternal callback) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableUsbDataWhileDocked(String portId, int operationId, IUsbOperationInternal callback) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public int getUsbHalVersion() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public ParcelFileDescriptor getControlFd(long function) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public List<ParcelableUsbPort> getPorts() throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public UsbPortStatus getPortStatus(String portId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setPortRoles(String portId, int powerRole, int dataRole) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableLimitPowerTransfer(String portId, boolean limit, int operationId, IUsbOperationInternal callback) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableContaminantDetection(String portId, boolean enable) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setUsbDeviceConnectionHandler(ComponentName usbDeviceConnectionHandler) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener listener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener listener) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isUsbBlocked() throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isSupportScrLckBlk() throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isSupportDexRestrict() throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public int restrictUsbHostInterface(boolean enable, String strWhiteList) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public void setUsbHiddenMenuState(boolean HiddenMenuEnable) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void semSetMode(int usbMode) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void semSetDevicePackage(UsbDevice device, String packageName, int userId) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void semGrantDevicePermission(UsbDevice device, int uid) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public int semGetPowerRoleStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public int semGetDataRoleStatus() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUsbManager {
        public static final String DESCRIPTOR = "android.hardware.usb.IUsbManager";
        static final int TRANSACTION_addAccessoryPackagesToPreferenceDenied = 8;
        static final int TRANSACTION_addDevicePackagesToPreferenceDenied = 7;
        static final int TRANSACTION_clearDefaults = 22;
        static final int TRANSACTION_enableContaminantDetection = 41;
        static final int TRANSACTION_enableLimitPowerTransfer = 40;
        static final int TRANSACTION_enableUsbData = 33;
        static final int TRANSACTION_enableUsbDataWhileDocked = 34;
        static final int TRANSACTION_getControlFd = 36;
        static final int TRANSACTION_getCurrentAccessory = 3;
        static final int TRANSACTION_getCurrentFunctions = 26;
        static final int TRANSACTION_getCurrentUsbSpeed = 27;
        static final int TRANSACTION_getDeviceList = 1;
        static final int TRANSACTION_getGadgetHalVersion = 28;
        static final int TRANSACTION_getPortStatus = 38;
        static final int TRANSACTION_getPorts = 37;
        static final int TRANSACTION_getScreenUnlockedFunctions = 30;
        static final int TRANSACTION_getUsbHalVersion = 35;
        static final int TRANSACTION_grantAccessoryPermission = 20;
        static final int TRANSACTION_grantDevicePermission = 19;
        static final int TRANSACTION_hasAccessoryPermission = 15;
        static final int TRANSACTION_hasAccessoryPermissionWithIdentity = 16;
        static final int TRANSACTION_hasDefaults = 21;
        static final int TRANSACTION_hasDevicePermission = 13;
        static final int TRANSACTION_hasDevicePermissionWithIdentity = 14;
        static final int TRANSACTION_isFunctionEnabled = 23;
        static final int TRANSACTION_isSupportDexRestrict = 47;
        static final int TRANSACTION_isSupportScrLckBlk = 46;
        static final int TRANSACTION_isUsbBlocked = 45;
        static final int TRANSACTION_openAccessory = 4;
        static final int TRANSACTION_openDevice = 2;
        static final int TRANSACTION_registerForDisplayPortEvents = 43;
        static final int TRANSACTION_removeAccessoryPackagesFromPreferenceDenied = 10;
        static final int TRANSACTION_removeDevicePackagesFromPreferenceDenied = 9;
        static final int TRANSACTION_requestAccessoryPermission = 18;
        static final int TRANSACTION_requestDevicePermission = 17;
        static final int TRANSACTION_resetUsbGadget = 31;
        static final int TRANSACTION_resetUsbPort = 32;
        static final int TRANSACTION_restrictUsbHostInterface = 48;
        static final int TRANSACTION_semGetDataRoleStatus = 54;
        static final int TRANSACTION_semGetPowerRoleStatus = 53;
        static final int TRANSACTION_semGrantDevicePermission = 52;
        static final int TRANSACTION_semSetDevicePackage = 51;
        static final int TRANSACTION_semSetMode = 50;
        static final int TRANSACTION_setAccessoryPackage = 6;
        static final int TRANSACTION_setAccessoryPersistentPermission = 12;
        static final int TRANSACTION_setCurrentFunction = 25;
        static final int TRANSACTION_setCurrentFunctions = 24;
        static final int TRANSACTION_setDevicePackage = 5;
        static final int TRANSACTION_setDevicePersistentPermission = 11;
        static final int TRANSACTION_setPortRoles = 39;
        static final int TRANSACTION_setScreenUnlockedFunctions = 29;
        static final int TRANSACTION_setUsbDeviceConnectionHandler = 42;
        static final int TRANSACTION_setUsbHiddenMenuState = 49;
        static final int TRANSACTION_unregisterForDisplayPortEvents = 44;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUsbManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IUsbManager)) {
                return (IUsbManager) iin;
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
                    return "getDeviceList";
                case 2:
                    return "openDevice";
                case 3:
                    return "getCurrentAccessory";
                case 4:
                    return "openAccessory";
                case 5:
                    return "setDevicePackage";
                case 6:
                    return "setAccessoryPackage";
                case 7:
                    return "addDevicePackagesToPreferenceDenied";
                case 8:
                    return "addAccessoryPackagesToPreferenceDenied";
                case 9:
                    return "removeDevicePackagesFromPreferenceDenied";
                case 10:
                    return "removeAccessoryPackagesFromPreferenceDenied";
                case 11:
                    return "setDevicePersistentPermission";
                case 12:
                    return "setAccessoryPersistentPermission";
                case 13:
                    return "hasDevicePermission";
                case 14:
                    return "hasDevicePermissionWithIdentity";
                case 15:
                    return "hasAccessoryPermission";
                case 16:
                    return "hasAccessoryPermissionWithIdentity";
                case 17:
                    return "requestDevicePermission";
                case 18:
                    return "requestAccessoryPermission";
                case 19:
                    return "grantDevicePermission";
                case 20:
                    return "grantAccessoryPermission";
                case 21:
                    return "hasDefaults";
                case 22:
                    return "clearDefaults";
                case 23:
                    return "isFunctionEnabled";
                case 24:
                    return "setCurrentFunctions";
                case 25:
                    return "setCurrentFunction";
                case 26:
                    return "getCurrentFunctions";
                case 27:
                    return "getCurrentUsbSpeed";
                case 28:
                    return "getGadgetHalVersion";
                case 29:
                    return "setScreenUnlockedFunctions";
                case 30:
                    return "getScreenUnlockedFunctions";
                case 31:
                    return "resetUsbGadget";
                case 32:
                    return "resetUsbPort";
                case 33:
                    return "enableUsbData";
                case 34:
                    return "enableUsbDataWhileDocked";
                case 35:
                    return "getUsbHalVersion";
                case 36:
                    return "getControlFd";
                case 37:
                    return "getPorts";
                case 38:
                    return "getPortStatus";
                case 39:
                    return "setPortRoles";
                case 40:
                    return "enableLimitPowerTransfer";
                case 41:
                    return "enableContaminantDetection";
                case 42:
                    return "setUsbDeviceConnectionHandler";
                case 43:
                    return "registerForDisplayPortEvents";
                case 44:
                    return "unregisterForDisplayPortEvents";
                case 45:
                    return "isUsbBlocked";
                case 46:
                    return "isSupportScrLckBlk";
                case 47:
                    return "isSupportDexRestrict";
                case 48:
                    return "restrictUsbHostInterface";
                case 49:
                    return "setUsbHiddenMenuState";
                case 50:
                    return "semSetMode";
                case 51:
                    return "semSetDevicePackage";
                case 52:
                    return "semGrantDevicePermission";
                case 53:
                    return "semGetPowerRoleStatus";
                case 54:
                    return "semGetDataRoleStatus";
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
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            Bundle _arg0 = new Bundle();
                            data.enforceNoDataAvail();
                            getDeviceList(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_arg0, 1);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            String _arg1 = data.readString();
                            data.enforceNoDataAvail();
                            ParcelFileDescriptor _result = openDevice(_arg02, _arg1);
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 3:
                            UsbAccessory _result2 = getCurrentAccessory();
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 4:
                            UsbAccessory _arg03 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            data.enforceNoDataAvail();
                            ParcelFileDescriptor _result3 = openAccessory(_arg03);
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 5:
                            UsbDevice _arg04 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String _arg12 = data.readString();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            setDevicePackage(_arg04, _arg12, _arg2);
                            reply.writeNoException();
                            return true;
                        case 6:
                            UsbAccessory _arg05 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            String _arg13 = data.readString();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            setAccessoryPackage(_arg05, _arg13, _arg22);
                            reply.writeNoException();
                            return true;
                        case 7:
                            UsbDevice _arg06 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String[] _arg14 = data.createStringArray();
                            UserHandle _arg23 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                            data.enforceNoDataAvail();
                            addDevicePackagesToPreferenceDenied(_arg06, _arg14, _arg23);
                            reply.writeNoException();
                            return true;
                        case 8:
                            UsbAccessory _arg07 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            String[] _arg15 = data.createStringArray();
                            UserHandle _arg24 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                            data.enforceNoDataAvail();
                            addAccessoryPackagesToPreferenceDenied(_arg07, _arg15, _arg24);
                            reply.writeNoException();
                            return true;
                        case 9:
                            UsbDevice _arg08 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String[] _arg16 = data.createStringArray();
                            UserHandle _arg25 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                            data.enforceNoDataAvail();
                            removeDevicePackagesFromPreferenceDenied(_arg08, _arg16, _arg25);
                            reply.writeNoException();
                            return true;
                        case 10:
                            UsbAccessory _arg09 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            String[] _arg17 = data.createStringArray();
                            UserHandle _arg26 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                            data.enforceNoDataAvail();
                            removeAccessoryPackagesFromPreferenceDenied(_arg09, _arg17, _arg26);
                            reply.writeNoException();
                            return true;
                        case 11:
                            UsbDevice _arg010 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            int _arg18 = data.readInt();
                            UserHandle _arg27 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                            boolean _arg3 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setDevicePersistentPermission(_arg010, _arg18, _arg27, _arg3);
                            reply.writeNoException();
                            return true;
                        case 12:
                            UsbAccessory _arg011 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            int _arg19 = data.readInt();
                            UserHandle _arg28 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                            boolean _arg32 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setAccessoryPersistentPermission(_arg011, _arg19, _arg28, _arg32);
                            reply.writeNoException();
                            return true;
                        case 13:
                            UsbDevice _arg012 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String _arg110 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result4 = hasDevicePermission(_arg012, _arg110);
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 14:
                            UsbDevice _arg013 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String _arg111 = data.readString();
                            int _arg29 = data.readInt();
                            int _arg33 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result5 = hasDevicePermissionWithIdentity(_arg013, _arg111, _arg29, _arg33);
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        case 15:
                            UsbAccessory _arg014 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result6 = hasAccessoryPermission(_arg014);
                            reply.writeNoException();
                            reply.writeBoolean(_result6);
                            return true;
                        case 16:
                            UsbAccessory _arg015 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            int _arg112 = data.readInt();
                            int _arg210 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result7 = hasAccessoryPermissionWithIdentity(_arg015, _arg112, _arg210);
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 17:
                            UsbDevice _arg016 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String _arg113 = data.readString();
                            PendingIntent _arg211 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                            data.enforceNoDataAvail();
                            requestDevicePermission(_arg016, _arg113, _arg211);
                            reply.writeNoException();
                            return true;
                        case 18:
                            UsbAccessory _arg017 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            String _arg114 = data.readString();
                            PendingIntent _arg212 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                            data.enforceNoDataAvail();
                            requestAccessoryPermission(_arg017, _arg114, _arg212);
                            reply.writeNoException();
                            return true;
                        case 19:
                            UsbDevice _arg018 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            int _arg115 = data.readInt();
                            data.enforceNoDataAvail();
                            grantDevicePermission(_arg018, _arg115);
                            reply.writeNoException();
                            return true;
                        case 20:
                            UsbAccessory _arg019 = (UsbAccessory) data.readTypedObject(UsbAccessory.CREATOR);
                            int _arg116 = data.readInt();
                            data.enforceNoDataAvail();
                            grantAccessoryPermission(_arg019, _arg116);
                            reply.writeNoException();
                            return true;
                        case 21:
                            String _arg020 = data.readString();
                            int _arg117 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result8 = hasDefaults(_arg020, _arg117);
                            reply.writeNoException();
                            reply.writeBoolean(_result8);
                            return true;
                        case 22:
                            String _arg021 = data.readString();
                            int _arg118 = data.readInt();
                            data.enforceNoDataAvail();
                            clearDefaults(_arg021, _arg118);
                            reply.writeNoException();
                            return true;
                        case 23:
                            String _arg022 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result9 = isFunctionEnabled(_arg022);
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 24:
                            long _arg023 = data.readLong();
                            int _arg119 = data.readInt();
                            data.enforceNoDataAvail();
                            setCurrentFunctions(_arg023, _arg119);
                            reply.writeNoException();
                            return true;
                        case 25:
                            String _arg024 = data.readString();
                            boolean _arg120 = data.readBoolean();
                            int _arg213 = data.readInt();
                            data.enforceNoDataAvail();
                            setCurrentFunction(_arg024, _arg120, _arg213);
                            reply.writeNoException();
                            return true;
                        case 26:
                            long _result10 = getCurrentFunctions();
                            reply.writeNoException();
                            reply.writeLong(_result10);
                            return true;
                        case 27:
                            int _result11 = getCurrentUsbSpeed();
                            reply.writeNoException();
                            reply.writeInt(_result11);
                            return true;
                        case 28:
                            int _result12 = getGadgetHalVersion();
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            return true;
                        case 29:
                            long _arg025 = data.readLong();
                            data.enforceNoDataAvail();
                            setScreenUnlockedFunctions(_arg025);
                            reply.writeNoException();
                            return true;
                        case 30:
                            long _result13 = getScreenUnlockedFunctions();
                            reply.writeNoException();
                            reply.writeLong(_result13);
                            return true;
                        case 31:
                            resetUsbGadget();
                            reply.writeNoException();
                            return true;
                        case 32:
                            String _arg026 = data.readString();
                            int _arg121 = data.readInt();
                            IUsbOperationInternal _arg214 = IUsbOperationInternal.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            resetUsbPort(_arg026, _arg121, _arg214);
                            reply.writeNoException();
                            return true;
                        case 33:
                            String _arg027 = data.readString();
                            boolean _arg122 = data.readBoolean();
                            int _arg215 = data.readInt();
                            IUsbOperationInternal _arg34 = IUsbOperationInternal.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            boolean _result14 = enableUsbData(_arg027, _arg122, _arg215, _arg34);
                            reply.writeNoException();
                            reply.writeBoolean(_result14);
                            return true;
                        case 34:
                            String _arg028 = data.readString();
                            int _arg123 = data.readInt();
                            IUsbOperationInternal _arg216 = IUsbOperationInternal.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            enableUsbDataWhileDocked(_arg028, _arg123, _arg216);
                            reply.writeNoException();
                            return true;
                        case 35:
                            int _result15 = getUsbHalVersion();
                            reply.writeNoException();
                            reply.writeInt(_result15);
                            return true;
                        case 36:
                            long _arg029 = data.readLong();
                            data.enforceNoDataAvail();
                            ParcelFileDescriptor _result16 = getControlFd(_arg029);
                            reply.writeNoException();
                            reply.writeTypedObject(_result16, 1);
                            return true;
                        case 37:
                            List<ParcelableUsbPort> _result17 = getPorts();
                            reply.writeNoException();
                            reply.writeTypedList(_result17, 1);
                            return true;
                        case 38:
                            String _arg030 = data.readString();
                            data.enforceNoDataAvail();
                            UsbPortStatus _result18 = getPortStatus(_arg030);
                            reply.writeNoException();
                            reply.writeTypedObject(_result18, 1);
                            return true;
                        case 39:
                            String _arg031 = data.readString();
                            int _arg124 = data.readInt();
                            int _arg217 = data.readInt();
                            data.enforceNoDataAvail();
                            setPortRoles(_arg031, _arg124, _arg217);
                            reply.writeNoException();
                            return true;
                        case 40:
                            String _arg032 = data.readString();
                            boolean _arg125 = data.readBoolean();
                            int _arg218 = data.readInt();
                            IUsbOperationInternal _arg35 = IUsbOperationInternal.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            enableLimitPowerTransfer(_arg032, _arg125, _arg218, _arg35);
                            reply.writeNoException();
                            return true;
                        case 41:
                            String _arg033 = data.readString();
                            boolean _arg126 = data.readBoolean();
                            data.enforceNoDataAvail();
                            enableContaminantDetection(_arg033, _arg126);
                            reply.writeNoException();
                            return true;
                        case 42:
                            ComponentName _arg034 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            setUsbDeviceConnectionHandler(_arg034);
                            reply.writeNoException();
                            return true;
                        case 43:
                            IDisplayPortAltModeInfoListener _arg035 = IDisplayPortAltModeInfoListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            boolean _result19 = registerForDisplayPortEvents(_arg035);
                            reply.writeNoException();
                            reply.writeBoolean(_result19);
                            return true;
                        case 44:
                            IDisplayPortAltModeInfoListener _arg036 = IDisplayPortAltModeInfoListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            unregisterForDisplayPortEvents(_arg036);
                            reply.writeNoException();
                            return true;
                        case 45:
                            boolean _result20 = isUsbBlocked();
                            reply.writeNoException();
                            reply.writeBoolean(_result20);
                            return true;
                        case 46:
                            boolean _result21 = isSupportScrLckBlk();
                            reply.writeNoException();
                            reply.writeBoolean(_result21);
                            return true;
                        case 47:
                            boolean _result22 = isSupportDexRestrict();
                            reply.writeNoException();
                            reply.writeBoolean(_result22);
                            return true;
                        case 48:
                            boolean _arg037 = data.readBoolean();
                            String _arg127 = data.readString();
                            data.enforceNoDataAvail();
                            int _result23 = restrictUsbHostInterface(_arg037, _arg127);
                            reply.writeNoException();
                            reply.writeInt(_result23);
                            return true;
                        case 49:
                            boolean _arg038 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setUsbHiddenMenuState(_arg038);
                            reply.writeNoException();
                            return true;
                        case 50:
                            int _arg039 = data.readInt();
                            data.enforceNoDataAvail();
                            semSetMode(_arg039);
                            reply.writeNoException();
                            return true;
                        case 51:
                            UsbDevice _arg040 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            String _arg128 = data.readString();
                            int _arg219 = data.readInt();
                            data.enforceNoDataAvail();
                            semSetDevicePackage(_arg040, _arg128, _arg219);
                            reply.writeNoException();
                            return true;
                        case 52:
                            UsbDevice _arg041 = (UsbDevice) data.readTypedObject(UsbDevice.CREATOR);
                            int _arg129 = data.readInt();
                            data.enforceNoDataAvail();
                            semGrantDevicePermission(_arg041, _arg129);
                            reply.writeNoException();
                            return true;
                        case 53:
                            int _result24 = semGetPowerRoleStatus();
                            reply.writeNoException();
                            reply.writeInt(_result24);
                            return true;
                        case 54:
                            int _result25 = semGetDataRoleStatus();
                            reply.writeNoException();
                            reply.writeInt(_result25);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUsbManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbManager
            public void getDeviceList(Bundle devices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        devices.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor openDevice(String deviceName, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceName);
                    _data.writeString(packageName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public UsbAccessory getCurrentAccessory() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    UsbAccessory _result = (UsbAccessory) _reply.readTypedObject(UsbAccessory.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor openAccessory(UsbAccessory accessory) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePackage(UsbDevice device, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPackage(UsbAccessory accessory, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addDevicePackagesToPreferenceDenied(UsbDevice device, String[] packageNames, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeStringArray(packageNames);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addAccessoryPackagesToPreferenceDenied(UsbAccessory accessory, String[] packageNames, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    _data.writeStringArray(packageNames);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeDevicePackagesFromPreferenceDenied(UsbDevice device, String[] packageNames, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeStringArray(packageNames);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory device, String[] packageNames, UserHandle user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeStringArray(packageNames);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePersistentPermission(UsbDevice device, int uid, UserHandle user, boolean shouldBeGranted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(uid);
                    _data.writeTypedObject(user, 0);
                    _data.writeBoolean(shouldBeGranted);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPersistentPermission(UsbAccessory accessory, int uid, UserHandle user, boolean shouldBeGranted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    _data.writeInt(uid);
                    _data.writeTypedObject(user, 0);
                    _data.writeBoolean(shouldBeGranted);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermission(UsbDevice device, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermissionWithIdentity(UsbDevice device, String packageName, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermission(UsbAccessory accessory) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermissionWithIdentity(UsbAccessory accessory, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestDevicePermission(UsbDevice device, String packageName, PendingIntent pi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeString(packageName);
                    _data.writeTypedObject(pi, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestAccessoryPermission(UsbAccessory accessory, String packageName, PendingIntent pi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    _data.writeString(packageName);
                    _data.writeTypedObject(pi, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantDevicePermission(UsbDevice device, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(uid);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantAccessoryPermission(UsbAccessory accessory, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accessory, 0);
                    _data.writeInt(uid);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDefaults(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void clearDefaults(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isFunctionEnabled(String function) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(function);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunctions(long functions, int operationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(functions);
                    _data.writeInt(operationId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunction(String function, boolean usbDataUnlocked, int operationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(function);
                    _data.writeBoolean(usbDataUnlocked);
                    _data.writeInt(operationId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getCurrentFunctions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getCurrentUsbSpeed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getGadgetHalVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setScreenUnlockedFunctions(long functions) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(functions);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getScreenUnlockedFunctions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbGadget() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbPort(String portId, int operationId, IUsbOperationInternal callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeInt(operationId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean enableUsbData(String portId, boolean enable, int operationId, IUsbOperationInternal callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeBoolean(enable);
                    _data.writeInt(operationId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableUsbDataWhileDocked(String portId, int operationId, IUsbOperationInternal callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeInt(operationId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getUsbHalVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor getControlFd(long function) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(function);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public List<ParcelableUsbPort> getPorts() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    List<ParcelableUsbPort> _result = _reply.createTypedArrayList(ParcelableUsbPort.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public UsbPortStatus getPortStatus(String portId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    UsbPortStatus _result = (UsbPortStatus) _reply.readTypedObject(UsbPortStatus.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setPortRoles(String portId, int powerRole, int dataRole) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeInt(powerRole);
                    _data.writeInt(dataRole);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableLimitPowerTransfer(String portId, boolean limit, int operationId, IUsbOperationInternal callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeBoolean(limit);
                    _data.writeInt(operationId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableContaminantDetection(String portId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(portId);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbDeviceConnectionHandler(ComponentName usbDeviceConnectionHandler) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(usbDeviceConnectionHandler, 0);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isUsbBlocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isSupportScrLckBlk() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isSupportDexRestrict() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int restrictUsbHostInterface(boolean enable, String strWhiteList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeString(strWhiteList);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbHiddenMenuState(boolean HiddenMenuEnable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(HiddenMenuEnable);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semSetMode(int usbMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(usbMode);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semSetDevicePackage(UsbDevice device, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semGrantDevicePermission(UsbDevice device, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(device, 0);
                    _data.writeInt(uid);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int semGetPowerRoleStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int semGetDataRoleStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 53;
        }
    }
}
