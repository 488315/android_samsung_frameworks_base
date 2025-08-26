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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUsbManager)) {
                return (IUsbManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenDevice = openDevice(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenDevice, 1);
                    return true;
                case 3:
                    UsbAccessory currentAccessory = getCurrentAccessory();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentAccessory, 1);
                    return true;
                case 4:
                    UsbAccessory usbAccessory = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenAccessory = openAccessory(usbAccessory);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenAccessory, 1);
                    return true;
                case 5:
                    UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String string3 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePackage(usbDevice, string3, i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    UsbAccessory usbAccessory2 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String string4 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAccessoryPackage(usbAccessory2, string4, i4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    UsbDevice usbDevice2 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDevicePackagesToPreferenceDenied(usbDevice2, strArrCreateStringArray, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    UsbAccessory usbAccessory3 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAccessoryPackagesToPreferenceDenied(usbAccessory3, strArrCreateStringArray2, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    UsbDevice usbDevice3 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicePackagesFromPreferenceDenied(usbDevice3, strArrCreateStringArray3, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    UsbAccessory usbAccessory4 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeAccessoryPackagesFromPreferenceDenied(usbAccessory4, strArrCreateStringArray4, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    UsbDevice usbDevice4 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    int i5 = parcel.readInt();
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDevicePersistentPermission(usbDevice4, i5, userHandle5, z);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    UsbAccessory usbAccessory5 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    int i6 = parcel.readInt();
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAccessoryPersistentPermission(usbAccessory5, i6, userHandle6, z2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    UsbDevice usbDevice5 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasDevicePermission = hasDevicePermission(usbDevice5, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDevicePermission);
                    return true;
                case 14:
                    UsbDevice usbDevice6 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String string6 = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasDevicePermissionWithIdentity = hasDevicePermissionWithIdentity(usbDevice6, string6, i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDevicePermissionWithIdentity);
                    return true;
                case 15:
                    UsbAccessory usbAccessory6 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zHasAccessoryPermission = hasAccessoryPermission(usbAccessory6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasAccessoryPermission);
                    return true;
                case 16:
                    UsbAccessory usbAccessory7 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasAccessoryPermissionWithIdentity = hasAccessoryPermissionWithIdentity(usbAccessory7, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasAccessoryPermissionWithIdentity);
                    return true;
                case 17:
                    UsbDevice usbDevice7 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String string7 = parcel.readString();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestDevicePermission(usbDevice7, string7, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    UsbAccessory usbAccessory8 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    String string8 = parcel.readString();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAccessoryPermission(usbAccessory8, string8, pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    UsbDevice usbDevice8 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDevicePermission(usbDevice8, i11);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    UsbAccessory usbAccessory9 = (UsbAccessory) parcel.readTypedObject(UsbAccessory.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantAccessoryPermission(usbAccessory9, i12);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string9 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasDefaults = hasDefaults(string9, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDefaults);
                    return true;
                case 22:
                    String string10 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDefaults(string10, i14);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsFunctionEnabled = isFunctionEnabled(string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFunctionEnabled);
                    return true;
                case 24:
                    boolean zIsUvcGadgetSupportEnabled = isUvcGadgetSupportEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUvcGadgetSupportEnabled);
                    return true;
                case 25:
                    long j = parcel.readLong();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentFunctions(j, i15);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string12 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentFunction(string12, z3, i16);
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
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setScreenUnlockedFunctions(j2);
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
                    String string13 = parcel.readString();
                    int i17 = parcel.readInt();
                    IUsbOperationInternal iUsbOperationInternalAsInterface = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    resetUsbPort(string13, i17, iUsbOperationInternalAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string14 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    int i18 = parcel.readInt();
                    IUsbOperationInternal iUsbOperationInternalAsInterface2 = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zEnableUsbData = enableUsbData(string14, z4, i18, iUsbOperationInternalAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableUsbData);
                    return true;
                case 35:
                    String string15 = parcel.readString();
                    int i19 = parcel.readInt();
                    IUsbOperationInternal iUsbOperationInternalAsInterface3 = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableUsbDataWhileDocked(string15, i19, iUsbOperationInternalAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int usbHalVersion = getUsbHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbHalVersion);
                    return true;
                case 37:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor controlFd = getControlFd(j3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(controlFd, 1);
                    return true;
                case 38:
                    List<ParcelableUsbPort> ports = getPorts();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(ports, 1);
                    return true;
                case 39:
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsbPortStatus portStatus = getPortStatus(string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(portStatus, 1);
                    return true;
                case 40:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsModeChangeSupported = isModeChangeSupported(string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsModeChangeSupported);
                    return true;
                case 41:
                    String string18 = parcel.readString();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPortRoles(string18, i20, i21);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String string19 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    int i22 = parcel.readInt();
                    IUsbOperationInternal iUsbOperationInternalAsInterface4 = IUsbOperationInternal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableLimitPowerTransfer(string19, z5, i22, iUsbOperationInternalAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String string20 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableContaminantDetection(string20, z6);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUsbDeviceConnectionHandler(componentName);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListenerAsInterface = IDisplayPortAltModeInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterForDisplayPortEvents = registerForDisplayPortEvents(iDisplayPortAltModeInfoListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterForDisplayPortEvents);
                    return true;
                case 46:
                    IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListenerAsInterface2 = IDisplayPortAltModeInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForDisplayPortEvents(iDisplayPortAltModeInfoListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    boolean zIsUsbBlocked = isUsbBlocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUsbBlocked);
                    return true;
                case 48:
                    boolean zIsSupportDexRestrict = isSupportDexRestrict();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportDexRestrict);
                    return true;
                case 49:
                    boolean z7 = parcel.readBoolean();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRestrictUsbHostInterface = restrictUsbHostInterface(z7, string21);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRestrictUsbHostInterface);
                    return true;
                case 50:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUsbHiddenMenuState(z8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSetMode(i23);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    UsbDevice usbDevice9 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    String string22 = parcel.readString();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semSetDevicePackage(usbDevice9, string22, i24);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    UsbDevice usbDevice10 = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semGrantDevicePermission(usbDevice10, i25);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int iSemGetPowerRoleStatus = semGetPowerRoleStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetPowerRoleStatus);
                    return true;
                case 55:
                    int iSemGetDataRoleStatus = semGetDataRoleStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetDataRoleStatus);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor openDevice(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public UsbAccessory getCurrentAccessory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsbAccessory) parcelObtain2.readTypedObject(UsbAccessory.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPackage(UsbAccessory usbAccessory, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addDevicePackagesToPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void addAccessoryPackagesToPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeDevicePackagesFromPreferenceDenied(UsbDevice usbDevice, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void removeAccessoryPackagesFromPreferenceDenied(UsbAccessory usbAccessory, String[] strArr, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setDevicePersistentPermission(UsbDevice usbDevice, int i, UserHandle userHandle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setAccessoryPersistentPermission(UsbAccessory usbAccessory, int i, UserHandle userHandle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermission(UsbDevice usbDevice, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDevicePermissionWithIdentity(UsbDevice usbDevice, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasAccessoryPermissionWithIdentity(UsbAccessory usbAccessory, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestDevicePermission(UsbDevice usbDevice, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void requestAccessoryPermission(UsbAccessory usbAccessory, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void grantAccessoryPermission(UsbAccessory usbAccessory, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbAccessory, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean hasDefaults(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void clearDefaults(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isFunctionEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isUvcGadgetSupportEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunctions(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setCurrentFunction(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getCurrentFunctions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getCurrentUsbSpeed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getGadgetHalVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setScreenUnlockedFunctions(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public long getScreenUnlockedFunctions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbGadget() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void resetUsbPort(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean enableUsbData(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableUsbDataWhileDocked(String str, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int getUsbHalVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public ParcelFileDescriptor getControlFd(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public List<ParcelableUsbPort> getPorts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ParcelableUsbPort.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public UsbPortStatus getPortStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsbPortStatus) parcelObtain2.readTypedObject(UsbPortStatus.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isModeChangeSupported(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setPortRoles(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableLimitPowerTransfer(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iUsbOperationInternal);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void enableContaminantDetection(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayPortAltModeInfoListener);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void unregisterForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayPortAltModeInfoListener);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isUsbBlocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public boolean isSupportDexRestrict() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int restrictUsbHostInterface(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void setUsbHiddenMenuState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semSetMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semSetDevicePackage(UsbDevice usbDevice, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public void semGrantDevicePermission(UsbDevice usbDevice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int semGetPowerRoleStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbManager
            public int semGetDataRoleStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
