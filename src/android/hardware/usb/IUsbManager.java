package android.hardware.usb;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.IUsbOperationInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.List;

/* loaded from: classes2.dex */
public interface IUsbManager extends IInterface {

    public static class Default implements IUsbManager {
        @Override // android.hardware.usb.IUsbManager
        public void addAccessoryPackagesToPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void addDevicePackagesToPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public void clearDefaults(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableContaminantDetection(String str, boolean z) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableLimitPowerTransfer(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean enableUsbData(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void enableUsbDataWhileDocked(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public ParcelFileDescriptor getControlFd(long j) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public UsbAccessory getCurrentAccessory() throws RemoteException {
            return null;
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
        public void getDeviceList(Bundle bundle) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public int getGadgetHalVersion() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public UsbPortStatus getPortStatus(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public List<ParcelableUsbPort> getPorts() throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public long getScreenUnlockedFunctions() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.usb.IUsbManager
        public int getUsbHalVersion() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public void grantAccessoryPermission(UsbAccessory usbAccessory, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void grantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasAccessoryPermissionWithIdentity(UsbAccessory usbAccessory, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDefaults(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDevicePermission(UsbDevice usbDevice, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean hasDevicePermissionWithIdentity(UsbDevice usbDevice, String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isFunctionEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isModeChangeSupported(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isSupportDexRestrict() throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isUsbBlocked() throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean isUvcGadgetSupportEnabled() throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public ParcelFileDescriptor openDevice(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.usb.IUsbManager
        public boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException {
            return false;
        }

        @Override // android.hardware.usb.IUsbManager
        public void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void removeDevicePackagesFromPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void requestAccessoryPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void requestDevicePermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void resetUsbGadget() throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void resetUsbPort(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public int restrictUsbHostInterface(boolean z, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public int semGetDataRoleStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public int semGetPowerRoleStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.usb.IUsbManager
        public void semGrantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void semSetDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void semSetMode(int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setAccessoryPackage(UsbAccessory usbAccessory, String str, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setAccessoryPersistentPermission(UsbAccessory usbAccessory, int i, UserHandle userHandle, boolean z) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setCurrentFunction(String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setCurrentFunctions(long j, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setDevicePersistentPermission(UsbDevice usbDevice, int i, UserHandle userHandle, boolean z) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setPortRoles(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setScreenUnlockedFunctions(long j) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void setUsbHiddenMenuState(boolean z) throws RemoteException {
        }

        @Override // android.hardware.usb.IUsbManager
        public void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException {
        }
    }

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

    boolean isModeChangeSupported(String str) throws RemoteException;

    boolean isSupportDexRestrict() throws RemoteException;

    boolean isUsbBlocked() throws RemoteException;

    boolean isUvcGadgetSupportEnabled() throws RemoteException;

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

    public static abstract class Stub extends Binder implements IUsbManager {
        public static final String DESCRIPTOR = "android.hardware.usb.IUsbManager";
        static final int TRANSACTION_addAccessoryPackagesToPreferenceDenied = 8;
        static final int TRANSACTION_addDevicePackagesToPreferenceDenied = 7;
        static final int TRANSACTION_clearDefaults = 22;
        static final int TRANSACTION_enableContaminantDetection = 43;
        static final int TRANSACTION_enableLimitPowerTransfer = 42;
        static final int TRANSACTION_enableUsbData = 34;
        static final int TRANSACTION_enableUsbDataWhileDocked = 35;
        static final int TRANSACTION_getControlFd = 37;
        static final int TRANSACTION_getCurrentAccessory = 3;
        static final int TRANSACTION_getCurrentFunctions = 27;
        static final int TRANSACTION_getCurrentUsbSpeed = 28;
        static final int TRANSACTION_getDeviceList = 1;
        static final int TRANSACTION_getGadgetHalVersion = 29;
        static final int TRANSACTION_getPortStatus = 39;
        static final int TRANSACTION_getPorts = 38;
        static final int TRANSACTION_getScreenUnlockedFunctions = 31;
        static final int TRANSACTION_getUsbHalVersion = 36;
        static final int TRANSACTION_grantAccessoryPermission = 20;
        static final int TRANSACTION_grantDevicePermission = 19;
        static final int TRANSACTION_hasAccessoryPermission = 15;
        static final int TRANSACTION_hasAccessoryPermissionWithIdentity = 16;
        static final int TRANSACTION_hasDefaults = 21;
        static final int TRANSACTION_hasDevicePermission = 13;
        static final int TRANSACTION_hasDevicePermissionWithIdentity = 14;
        static final int TRANSACTION_isFunctionEnabled = 23;
        static final int TRANSACTION_isModeChangeSupported = 40;
        static final int TRANSACTION_isSupportDexRestrict = 48;
        static final int TRANSACTION_isUsbBlocked = 47;
        static final int TRANSACTION_isUvcGadgetSupportEnabled = 24;
        static final int TRANSACTION_openAccessory = 4;
        static final int TRANSACTION_openDevice = 2;
        static final int TRANSACTION_registerForDisplayPortEvents = 45;
        static final int TRANSACTION_removeAccessoryPackagesFromPreferenceDenied = 10;
        static final int TRANSACTION_removeDevicePackagesFromPreferenceDenied = 9;
        static final int TRANSACTION_requestAccessoryPermission = 18;
        static final int TRANSACTION_requestDevicePermission = 17;
        static final int TRANSACTION_resetUsbGadget = 32;
        static final int TRANSACTION_resetUsbPort = 33;
        static final int TRANSACTION_restrictUsbHostInterface = 49;
        static final int TRANSACTION_semGetDataRoleStatus = 55;
        static final int TRANSACTION_semGetPowerRoleStatus = 54;
        static final int TRANSACTION_semGrantDevicePermission = 53;
        static final int TRANSACTION_semSetDevicePackage = 52;
        static final int TRANSACTION_semSetMode = 51;
        static final int TRANSACTION_setAccessoryPackage = 6;
        static final int TRANSACTION_setAccessoryPersistentPermission = 12;
        static final int TRANSACTION_setCurrentFunction = 26;
        static final int TRANSACTION_setCurrentFunctions = 25;
        static final int TRANSACTION_setDevicePackage = 5;
        static final int TRANSACTION_setDevicePersistentPermission = 11;
        static final int TRANSACTION_setPortRoles = 41;
        static final int TRANSACTION_setScreenUnlockedFunctions = 30;
        static final int TRANSACTION_setUsbDeviceConnectionHandler = 44;
        static final int TRANSACTION_setUsbHiddenMenuState = 50;
        static final int TRANSACTION_unregisterForDisplayPortEvents = 46;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 54;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IUsbManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsbManager)) {
                return (IUsbManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "isUvcGadgetSupportEnabled";
                case 25:
                    return "setCurrentFunctions";
                case 26:
                    return "setCurrentFunction";
                case 27:
                    return "getCurrentFunctions";
                case 28:
                    return "getCurrentUsbSpeed";
                case 29:
                    return "getGadgetHalVersion";
                case 30:
                    return "setScreenUnlockedFunctions";
                case 31:
                    return "getScreenUnlockedFunctions";
                case 32:
                    return "resetUsbGadget";
                case 33:
                    return "resetUsbPort";
                case 34:
                    return "enableUsbData";
                case 35:
                    return "enableUsbDataWhileDocked";
                case 36:
                    return "getUsbHalVersion";
                case 37:
                    return "getControlFd";
                case 38:
                    return "getPorts";
                case 39:
                    return "getPortStatus";
                case 40:
                    return "isModeChangeSupported";
                case 41:
                    return "setPortRoles";
                case 42:
                    return "enableLimitPowerTransfer";
                case 43:
                    return "enableContaminantDetection";
                case 44:
                    return "setUsbDeviceConnectionHandler";
                case 45:
                    return "registerForDisplayPortEvents";
                case 46:
                    return "unregisterForDisplayPortEvents";
                case 47:
                    return "isUsbBlocked";
                case 48:
                    return "isSupportDexRestrict";
                case 49:
                    return "restrictUsbHostInterface";
                case 50:
                    return "setUsbHiddenMenuState";
                case 51:
                    return "semSetMode";
                case 52:
                    return "semSetDevicePackage";
                case 53:
                    return "semGrantDevicePermission";
                case 54:
                    return "semGetPowerRoleStatus";
                case 55:
                    return "semGetDataRoleStatus";
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
                    Bundle bundle = new Bundle();
                    parcel.enforceNoDataAvail();
                    getDeviceList(bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openDevice = openDevice(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openDevice, 1);
                    return true;
                case 3:
                    UsbAccessory currentAccessory = getCurrentAccessory();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentAccessory, 1);
                    return true;
                case 4:
                    UsbAccessory usbAccessory = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openAccessory = openAccessory(usbAccessory);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openAccessory, 1);
                    return true;
                case 5:
                    UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePackage(usbDevice, readString3, readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    UsbAccessory usbAccessory2 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String readString4 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAccessoryPackage(usbAccessory2, readString4, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    UsbDevice usbDevice2 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDevicePackagesToPreferenceDenied(usbDevice2, createStringArray, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    UsbAccessory usbAccessory3 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String[] createStringArray2 = parcel.createStringArray();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccessoryPackagesToPreferenceDenied(usbAccessory3, createStringArray2, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    UsbDevice usbDevice3 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String[] createStringArray3 = parcel.createStringArray();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicePackagesFromPreferenceDenied(usbDevice3, createStringArray3, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    UsbAccessory usbAccessory4 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String[] createStringArray4 = parcel.createStringArray();
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeAccessoryPackagesFromPreferenceDenied(usbAccessory4, createStringArray4, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    UsbDevice usbDevice4 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    int readInt3 = parcel.readInt();
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDevicePersistentPermission(usbDevice4, readInt3, userHandle5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    UsbAccessory usbAccessory5 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    int readInt4 = parcel.readInt();
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAccessoryPersistentPermission(usbAccessory5, readInt4, userHandle6, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    UsbDevice usbDevice5 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDevicePermission = hasDevicePermission(usbDevice5, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDevicePermission);
                    return true;
                case 14:
                    UsbDevice usbDevice6 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String readString6 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasDevicePermissionWithIdentity = hasDevicePermissionWithIdentity(usbDevice6, readString6, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDevicePermissionWithIdentity);
                    return true;
                case 15:
                    UsbAccessory usbAccessory6 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasAccessoryPermission = hasAccessoryPermission(usbAccessory6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAccessoryPermission);
                    return true;
                case 16:
                    UsbAccessory usbAccessory7 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasAccessoryPermissionWithIdentity = hasAccessoryPermissionWithIdentity(usbAccessory7, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAccessoryPermissionWithIdentity);
                    return true;
                case 17:
                    UsbDevice usbDevice7 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String readString7 = parcel.readString();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDevicePermission(usbDevice7, readString7, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    UsbAccessory usbAccessory8 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String readString8 = parcel.readString();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAccessoryPermission(usbAccessory8, readString8, pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    UsbDevice usbDevice8 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDevicePermission(usbDevice8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    UsbAccessory usbAccessory9 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantAccessoryPermission(usbAccessory9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString9 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasDefaults = hasDefaults(readString9, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDefaults);
                    return true;
                case 22:
                    String readString10 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDefaults(readString10, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isFunctionEnabled = isFunctionEnabled(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFunctionEnabled);
                    return true;
                case 24:
                    boolean isUvcGadgetSupportEnabled = isUvcGadgetSupportEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUvcGadgetSupportEnabled);
                    return true;
                case 25:
                    long readLong = parcel.readLong();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentFunctions(readLong, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString12 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentFunction(readString12, readBoolean3, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    long currentFunctions = getCurrentFunctions();
                    parcel2.writeNoException();
                    parcel2.writeLong(currentFunctions);
                    return true;
                case 28:
                    int currentUsbSpeed = getCurrentUsbSpeed();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentUsbSpeed);
                    return true;
                case 29:
                    int gadgetHalVersion = getGadgetHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(gadgetHalVersion);
                    return true;
                case 30:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setScreenUnlockedFunctions(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    long screenUnlockedFunctions = getScreenUnlockedFunctions();
                    parcel2.writeNoException();
                    parcel2.writeLong(screenUnlockedFunctions);
                    return true;
                case 32:
                    resetUsbGadget();
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString13 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    IUsbOperationInternal asInterface = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    resetUsbPort(readString13, readInt15, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString14 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    IUsbOperationInternal asInterface2 = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean enableUsbData = enableUsbData(readString14, readBoolean4, readInt16, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableUsbData);
                    return true;
                case 35:
                    String readString15 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    IUsbOperationInternal asInterface3 = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableUsbDataWhileDocked(readString15, readInt17, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int usbHalVersion = getUsbHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbHalVersion);
                    return true;
                case 37:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor controlFd = getControlFd(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(controlFd, 1);
                    return true;
                case 38:
                    List<ParcelableUsbPort> ports = getPorts();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(ports, 1);
                    return true;
                case 39:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsbPortStatus portStatus = getPortStatus(readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(portStatus, 1);
                    return true;
                case 40:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isModeChangeSupported = isModeChangeSupported(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isModeChangeSupported);
                    return true;
                case 41:
                    String readString18 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPortRoles(readString18, readInt18, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String readString19 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    IUsbOperationInternal asInterface4 = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableLimitPowerTransfer(readString19, readBoolean5, readInt20, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String readString20 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableContaminantDetection(readString20, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUsbDeviceConnectionHandler(componentName);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IDisplayPortAltModeInfoListener asInterface5 = IDisplayPortAltModeInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerForDisplayPortEvents = registerForDisplayPortEvents(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerForDisplayPortEvents);
                    return true;
                case 46:
                    IDisplayPortAltModeInfoListener asInterface6 = IDisplayPortAltModeInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForDisplayPortEvents(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    boolean isUsbBlocked = isUsbBlocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbBlocked);
                    return true;
                case 48:
                    boolean isSupportDexRestrict = isSupportDexRestrict();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportDexRestrict);
                    return true;
                case 49:
                    boolean readBoolean7 = parcel.readBoolean();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int restrictUsbHostInterface = restrictUsbHostInterface(readBoolean7, readString21);
                    parcel2.writeNoException();
                    parcel2.writeInt(restrictUsbHostInterface);
                    return true;
                case 50:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUsbHiddenMenuState(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSetMode(readInt21);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    UsbDevice usbDevice9 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String readString22 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSetDevicePackage(usbDevice9, readString22, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    UsbDevice usbDevice10 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semGrantDevicePermission(usbDevice10, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int semGetPowerRoleStatus = semGetPowerRoleStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetPowerRoleStatus);
                    return true;
                case 55:
                    int semGetDataRoleStatus = semGetDataRoleStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetDataRoleStatus);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUsbManager {
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

            @Override // android.hardware.usb.IUsbManager
            public void getDeviceList(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor openDevice(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public UsbAccessory getCurrentAccessory() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsbAccessory) obtain2.readTypedObject(UsbAccessory.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPackage(UsbAccessory usbAccessory, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addDevicePackagesToPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addAccessoryPackagesToPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeDevicePackagesFromPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePersistentPermission(UsbDevice usbDevice, int i, UserHandle userHandle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPersistentPermission(UsbAccessory usbAccessory, int i, UserHandle userHandle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermission(UsbDevice usbDevice, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermissionWithIdentity(UsbDevice usbDevice, String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermissionWithIdentity(UsbAccessory usbAccessory, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestDevicePermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestAccessoryPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantAccessoryPermission(UsbAccessory usbAccessory, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbAccessory, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDefaults(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void clearDefaults(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isFunctionEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isUvcGadgetSupportEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunctions(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunction(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getCurrentFunctions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getCurrentUsbSpeed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getGadgetHalVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setScreenUnlockedFunctions(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getScreenUnlockedFunctions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbGadget() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbPort(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean enableUsbData(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableUsbDataWhileDocked(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getUsbHalVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor getControlFd(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public List<ParcelableUsbPort> getPorts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ParcelableUsbPort.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public UsbPortStatus getPortStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsbPortStatus) obtain2.readTypedObject(UsbPortStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isModeChangeSupported(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setPortRoles(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableLimitPowerTransfer(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableContaminantDetection(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayPortAltModeInfoListener);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayPortAltModeInfoListener);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isUsbBlocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isSupportDexRestrict() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int restrictUsbHostInterface(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbHiddenMenuState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semSetMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semSetDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semGrantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int semGetPowerRoleStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int semGetDataRoleStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void hasDevicePermissionWithIdentity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void hasAccessoryPermissionWithIdentity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void grantDevicePermission_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void grantAccessoryPermission_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void setCurrentFunctions_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getCurrentFunctions_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getCurrentUsbSpeed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getGadgetHalVersion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void setScreenUnlockedFunctions_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getScreenUnlockedFunctions_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void resetUsbGadget_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getUsbHalVersion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void getControlFd_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_MTP, getCallingPid(), getCallingUid());
        }

        protected void getPorts_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void isModeChangeSupported_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }

        protected void setUsbDeviceConnectionHandler_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USB, getCallingPid(), getCallingUid());
        }
    }
}
