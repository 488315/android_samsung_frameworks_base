package android.content.pm;

import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.IntentSender;
import android.content.pm.ILauncherApps;
import android.content.pm.IOnAppsChangedListener;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IShortcutChangeCallback;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.window.IDumpCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface ILauncherApps extends IInterface {

    public static class Default implements ILauncherApps {
        @Override // android.content.pm.ILauncherApps
        public void addOnAppsChangedListener(String str, IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void cacheShortcuts(String str, String str2, List<String> list, UserHandle userHandle, int i) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void changePackageIcon(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public PendingIntent getActivityLaunchIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public Map<String, LauncherActivityInfoInternal> getActivityOverrides(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public ParceledListSlice getAllSessions(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public IntentSender getAppMarketActivityIntent(String str, String str2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public LauncherApps.AppUsageLimit getAppUsageLimit(String str, String str2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public ParceledListSlice getLauncherActivities(String str, String str2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public LauncherUserInfo getLauncherUserInfo(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public List<String> getPreInstalledSystemPackages(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public IntentSender getPrivateSpaceSettingsIntent() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public ParceledListSlice getShortcutConfigActivities(String str, String str2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public IntentSender getShortcutConfigActivityIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public int getShortcutIconResId(String str, String str2, String str3, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.ILauncherApps
        public String getShortcutIconUri(String str, String str2, String str3, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public PendingIntent getShortcutIntent(String str, String str2, String str3, Bundle bundle, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public ParceledListSlice getShortcuts(String str, ShortcutQueryWrapper shortcutQueryWrapper, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public Bundle getSuspendedPackageLauncherExtras(String str, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public List<UserHandle> getUserProfiles() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public boolean hasShortcutHostPermission(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public boolean isActivityEnabled(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public boolean isPackageEnabled(String str, String str2, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public void pinShortcuts(String str, String str2, List<String> list, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void registerDumpCallback(IDumpCallback iDumpCallback) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void registerPackageInstallerCallback(String str, IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void registerShortcutChangeCallback(String str, ShortcutQueryWrapper shortcutQueryWrapper, IShortcutChangeCallback iShortcutChangeCallback) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public LauncherActivityInfoInternal resolveLauncherActivityInternal(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void saveViewCaptureData() throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void setArchiveCompatibilityOptions(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public boolean shouldHideFromSuggestions(String str, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public void showAppDetailsAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public void unRegisterDumpCallback(IDumpCallback iDumpCallback) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void uncacheShortcuts(String str, String str2, List<String> list, UserHandle userHandle, int i) throws RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void unregisterShortcutChangeCallback(String str, IShortcutChangeCallback iShortcutChangeCallback) throws RemoteException {
        }
    }

    void addOnAppsChangedListener(String str, IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException;

    void cacheShortcuts(String str, String str2, List<String> list, UserHandle userHandle, int i) throws RemoteException;

    void changePackageIcon(String str, int i) throws RemoteException;

    PendingIntent getActivityLaunchIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    Map<String, LauncherActivityInfoInternal> getActivityOverrides(String str, int i) throws RemoteException;

    ParceledListSlice getAllSessions(String str) throws RemoteException;

    IntentSender getAppMarketActivityIntent(String str, String str2, UserHandle userHandle) throws RemoteException;

    LauncherApps.AppUsageLimit getAppUsageLimit(String str, String str2, UserHandle userHandle) throws RemoteException;

    ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException;

    ParceledListSlice getLauncherActivities(String str, String str2, UserHandle userHandle) throws RemoteException;

    LauncherUserInfo getLauncherUserInfo(UserHandle userHandle) throws RemoteException;

    List<String> getPreInstalledSystemPackages(UserHandle userHandle) throws RemoteException;

    IntentSender getPrivateSpaceSettingsIntent() throws RemoteException;

    ParceledListSlice getShortcutConfigActivities(String str, String str2, UserHandle userHandle) throws RemoteException;

    IntentSender getShortcutConfigActivityIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, int i) throws RemoteException;

    int getShortcutIconResId(String str, String str2, String str3, int i) throws RemoteException;

    String getShortcutIconUri(String str, String str2, String str3, int i) throws RemoteException;

    PendingIntent getShortcutIntent(String str, String str2, String str3, Bundle bundle, UserHandle userHandle) throws RemoteException;

    ParceledListSlice getShortcuts(String str, ShortcutQueryWrapper shortcutQueryWrapper, UserHandle userHandle) throws RemoteException;

    Bundle getSuspendedPackageLauncherExtras(String str, UserHandle userHandle) throws RemoteException;

    List<UserHandle> getUserProfiles() throws RemoteException;

    boolean hasShortcutHostPermission(String str) throws RemoteException;

    boolean isActivityEnabled(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    boolean isPackageEnabled(String str, String str2, UserHandle userHandle) throws RemoteException;

    void pinShortcuts(String str, String str2, List<String> list, UserHandle userHandle) throws RemoteException;

    void registerDumpCallback(IDumpCallback iDumpCallback) throws RemoteException;

    void registerPackageInstallerCallback(String str, IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException;

    void registerShortcutChangeCallback(String str, ShortcutQueryWrapper shortcutQueryWrapper, IShortcutChangeCallback iShortcutChangeCallback) throws RemoteException;

    void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException;

    LauncherActivityInfoInternal resolveLauncherActivityInternal(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException;

    void saveViewCaptureData() throws RemoteException;

    void setArchiveCompatibilityOptions(boolean z, boolean z2) throws RemoteException;

    boolean shouldHideFromSuggestions(String str, UserHandle userHandle) throws RemoteException;

    void showAppDetailsAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException;

    void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException;

    void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException;

    boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException;

    void unRegisterDumpCallback(IDumpCallback iDumpCallback) throws RemoteException;

    void uncacheShortcuts(String str, String str2, List<String> list, UserHandle userHandle, int i) throws RemoteException;

    void unregisterShortcutChangeCallback(String str, IShortcutChangeCallback iShortcutChangeCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ILauncherApps {
        public static final String DESCRIPTOR = "android.content.pm.ILauncherApps";
        static final int TRANSACTION_addOnAppsChangedListener = 1;
        static final int TRANSACTION_cacheShortcuts = 32;
        static final int TRANSACTION_changePackageIcon = 41;
        static final int TRANSACTION_getActivityLaunchIntent = 7;
        static final int TRANSACTION_getActivityOverrides = 35;
        static final int TRANSACTION_getAllSessions = 29;
        static final int TRANSACTION_getAppMarketActivityIntent = 10;
        static final int TRANSACTION_getAppUsageLimit = 17;
        static final int TRANSACTION_getApplicationInfo = 16;
        static final int TRANSACTION_getLauncherActivities = 3;
        static final int TRANSACTION_getLauncherUserInfo = 8;
        static final int TRANSACTION_getPreInstalledSystemPackages = 9;
        static final int TRANSACTION_getPrivateSpaceSettingsIntent = 11;
        static final int TRANSACTION_getShortcutConfigActivities = 25;
        static final int TRANSACTION_getShortcutConfigActivityIntent = 26;
        static final int TRANSACTION_getShortcutIconFd = 22;
        static final int TRANSACTION_getShortcutIconResId = 21;
        static final int TRANSACTION_getShortcutIconUri = 34;
        static final int TRANSACTION_getShortcutIntent = 27;
        static final int TRANSACTION_getShortcuts = 18;
        static final int TRANSACTION_getSuspendedPackageLauncherExtras = 14;
        static final int TRANSACTION_getUserProfiles = 39;
        static final int TRANSACTION_hasShortcutHostPermission = 23;
        static final int TRANSACTION_isActivityEnabled = 15;
        static final int TRANSACTION_isPackageEnabled = 13;
        static final int TRANSACTION_pinShortcuts = 19;
        static final int TRANSACTION_registerDumpCallback = 36;
        static final int TRANSACTION_registerPackageInstallerCallback = 28;
        static final int TRANSACTION_registerShortcutChangeCallback = 30;
        static final int TRANSACTION_removeOnAppsChangedListener = 2;
        static final int TRANSACTION_resolveLauncherActivityInternal = 4;
        static final int TRANSACTION_saveViewCaptureData = 40;
        static final int TRANSACTION_setArchiveCompatibilityOptions = 38;
        static final int TRANSACTION_shouldHideFromSuggestions = 24;
        static final int TRANSACTION_showAppDetailsAsUser = 12;
        static final int TRANSACTION_startActivityAsUser = 6;
        static final int TRANSACTION_startSessionDetailsActivityAsUser = 5;
        static final int TRANSACTION_startShortcut = 20;
        static final int TRANSACTION_unRegisterDumpCallback = 37;
        static final int TRANSACTION_uncacheShortcuts = 33;
        static final int TRANSACTION_unregisterShortcutChangeCallback = 31;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILauncherApps asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILauncherApps)) {
                return (ILauncherApps) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addOnAppsChangedListener";
                case 2:
                    return "removeOnAppsChangedListener";
                case 3:
                    return "getLauncherActivities";
                case 4:
                    return "resolveLauncherActivityInternal";
                case 5:
                    return "startSessionDetailsActivityAsUser";
                case 6:
                    return "startActivityAsUser";
                case 7:
                    return "getActivityLaunchIntent";
                case 8:
                    return "getLauncherUserInfo";
                case 9:
                    return "getPreInstalledSystemPackages";
                case 10:
                    return "getAppMarketActivityIntent";
                case 11:
                    return "getPrivateSpaceSettingsIntent";
                case 12:
                    return "showAppDetailsAsUser";
                case 13:
                    return "isPackageEnabled";
                case 14:
                    return "getSuspendedPackageLauncherExtras";
                case 15:
                    return "isActivityEnabled";
                case 16:
                    return "getApplicationInfo";
                case 17:
                    return "getAppUsageLimit";
                case 18:
                    return "getShortcuts";
                case 19:
                    return "pinShortcuts";
                case 20:
                    return "startShortcut";
                case 21:
                    return "getShortcutIconResId";
                case 22:
                    return "getShortcutIconFd";
                case 23:
                    return "hasShortcutHostPermission";
                case 24:
                    return "shouldHideFromSuggestions";
                case 25:
                    return "getShortcutConfigActivities";
                case 26:
                    return "getShortcutConfigActivityIntent";
                case 27:
                    return "getShortcutIntent";
                case 28:
                    return "registerPackageInstallerCallback";
                case 29:
                    return "getAllSessions";
                case 30:
                    return "registerShortcutChangeCallback";
                case 31:
                    return "unregisterShortcutChangeCallback";
                case 32:
                    return "cacheShortcuts";
                case 33:
                    return "uncacheShortcuts";
                case 34:
                    return "getShortcutIconUri";
                case 35:
                    return "getActivityOverrides";
                case 36:
                    return "registerDumpCallback";
                case 37:
                    return "unRegisterDumpCallback";
                case 38:
                    return "setArchiveCompatibilityOptions";
                case 39:
                    return "getUserProfiles";
                case 40:
                    return "saveViewCaptureData";
                case 41:
                    return "changePackageIcon";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    IOnAppsChangedListener asInterface = IOnAppsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnAppsChangedListener(readString, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IOnAppsChangedListener asInterface2 = IOnAppsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnAppsChangedListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice launcherActivities = getLauncherActivities(readString2, readString3, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launcherActivities, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    LauncherActivityInfoInternal resolveLauncherActivityInternal = resolveLauncherActivityInternal(readString4, componentName, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveLauncherActivityInternal, 1);
                    return true;
                case 5:
                    IApplicationThread asInterface3 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    PackageInstaller.SessionInfo sessionInfo = (PackageInstaller.SessionInfo) parcel.readTypedObject(PackageInstaller.SessionInfo.CREATOR);
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startSessionDetailsActivityAsUser(asInterface3, readString5, readString6, sessionInfo, rect, bundle, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IApplicationThread asInterface4 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUser(asInterface4, readString7, readString8, componentName2, rect2, bundle2, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString9 = parcel.readString();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    PendingIntent activityLaunchIntent = getActivityLaunchIntent(readString9, componentName3, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activityLaunchIntent, 1);
                    return true;
                case 8:
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    LauncherUserInfo launcherUserInfo = getLauncherUserInfo(userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launcherUserInfo, 1);
                    return true;
                case 9:
                    UserHandle userHandle7 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> preInstalledSystemPackages = getPreInstalledSystemPackages(userHandle7);
                    parcel2.writeNoException();
                    parcel2.writeStringList(preInstalledSystemPackages);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    UserHandle userHandle8 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IntentSender appMarketActivityIntent = getAppMarketActivityIntent(readString10, readString11, userHandle8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMarketActivityIntent, 1);
                    return true;
                case 11:
                    IntentSender privateSpaceSettingsIntent = getPrivateSpaceSettingsIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(privateSpaceSettingsIntent, 1);
                    return true;
                case 12:
                    IApplicationThread asInterface5 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    UserHandle userHandle9 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    showAppDetailsAsUser(asInterface5, readString12, readString13, componentName4, rect3, bundle3, userHandle9);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    UserHandle userHandle10 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPackageEnabled = isPackageEnabled(readString14, readString15, userHandle10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageEnabled);
                    return true;
                case 14:
                    String readString16 = parcel.readString();
                    UserHandle userHandle11 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle suspendedPackageLauncherExtras = getSuspendedPackageLauncherExtras(readString16, userHandle11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendedPackageLauncherExtras, 1);
                    return true;
                case 15:
                    String readString17 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle12 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isActivityEnabled = isActivityEnabled(readString17, componentName5, userHandle12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivityEnabled);
                    return true;
                case 16:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt = parcel.readInt();
                    UserHandle userHandle13 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ApplicationInfo applicationInfo = getApplicationInfo(readString18, readString19, readInt, userHandle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 17:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    UserHandle userHandle14 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    LauncherApps.AppUsageLimit appUsageLimit = getAppUsageLimit(readString20, readString21, userHandle14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appUsageLimit, 1);
                    return true;
                case 18:
                    String readString22 = parcel.readString();
                    ShortcutQueryWrapper shortcutQueryWrapper = (ShortcutQueryWrapper) parcel.readTypedObject(ShortcutQueryWrapper.CREATOR);
                    UserHandle userHandle15 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shortcuts = getShortcuts(readString22, shortcutQueryWrapper, userHandle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 19:
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    UserHandle userHandle16 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    pinShortcuts(readString23, readString24, createStringArrayList, userHandle16);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    Rect rect4 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startShortcut = startShortcut(readString25, readString26, readString27, readString28, rect4, bundle4, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startShortcut);
                    return true;
                case 21:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int shortcutIconResId = getShortcutIconResId(readString29, readString30, readString31, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(shortcutIconResId);
                    return true;
                case 22:
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(readString32, readString33, readString34, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutIconFd, 1);
                    return true;
                case 23:
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasShortcutHostPermission = hasShortcutHostPermission(readString35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasShortcutHostPermission);
                    return true;
                case 24:
                    String readString36 = parcel.readString();
                    UserHandle userHandle17 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean shouldHideFromSuggestions = shouldHideFromSuggestions(readString36, userHandle17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldHideFromSuggestions);
                    return true;
                case 25:
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    UserHandle userHandle18 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shortcutConfigActivities = getShortcutConfigActivities(readString37, readString38, userHandle18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutConfigActivities, 1);
                    return true;
                case 26:
                    String readString39 = parcel.readString();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle19 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IntentSender shortcutConfigActivityIntent = getShortcutConfigActivityIntent(readString39, componentName6, userHandle19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutConfigActivityIntent, 1);
                    return true;
                case 27:
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    UserHandle userHandle20 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    PendingIntent shortcutIntent = getShortcutIntent(readString40, readString41, readString42, bundle5, userHandle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutIntent, 1);
                    return true;
                case 28:
                    String readString43 = parcel.readString();
                    IPackageInstallerCallback asInterface6 = IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPackageInstallerCallback(readString43, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allSessions = getAllSessions(readString44);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allSessions, 1);
                    return true;
                case 30:
                    String readString45 = parcel.readString();
                    ShortcutQueryWrapper shortcutQueryWrapper2 = (ShortcutQueryWrapper) parcel.readTypedObject(ShortcutQueryWrapper.CREATOR);
                    IShortcutChangeCallback asInterface7 = IShortcutChangeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerShortcutChangeCallback(readString45, shortcutQueryWrapper2, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString46 = parcel.readString();
                    IShortcutChangeCallback asInterface8 = IShortcutChangeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterShortcutChangeCallback(readString46, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    UserHandle userHandle21 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cacheShortcuts(readString47, readString48, createStringArrayList2, userHandle21, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString49 = parcel.readString();
                    String readString50 = parcel.readString();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    UserHandle userHandle22 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uncacheShortcuts(readString49, readString50, createStringArrayList3, userHandle22, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    String readString53 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String shortcutIconUri = getShortcutIconUri(readString51, readString52, readString53, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(shortcutIconUri);
                    return true;
                case 35:
                    String readString54 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map<String, LauncherActivityInfoInternal> activityOverrides = getActivityOverrides(readString54, readInt8);
                    parcel2.writeNoException();
                    if (activityOverrides == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(activityOverrides.size());
                        activityOverrides.forEach(new BiConsumer() { // from class: android.content.pm.ILauncherApps$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ILauncherApps.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (LauncherActivityInfoInternal) obj2);
                            }
                        });
                    }
                    return true;
                case 36:
                    IDumpCallback asInterface9 = IDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDumpCallback(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IDumpCallback asInterface10 = IDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unRegisterDumpCallback(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setArchiveCompatibilityOptions(readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    List<UserHandle> userProfiles = getUserProfiles();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(userProfiles, 1);
                    return true;
                case 40:
                    saveViewCaptureData();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString55 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changePackageIcon(readString55, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, LauncherActivityInfoInternal launcherActivityInfoInternal) {
            parcel.writeString(str);
            parcel.writeTypedObject(launcherActivityInfoInternal, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements ILauncherApps {
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

            @Override // android.content.pm.ILauncherApps
            public void addOnAppsChangedListener(String str, IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iOnAppsChangedListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnAppsChangedListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public ParceledListSlice getLauncherActivities(String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public LauncherActivityInfoInternal resolveLauncherActivityInternal(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LauncherActivityInfoInternal) obtain2.readTypedObject(LauncherActivityInfoInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(sessionInfo, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public PendingIntent getActivityLaunchIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public LauncherUserInfo getLauncherUserInfo(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LauncherUserInfo) obtain2.readTypedObject(LauncherUserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public List<String> getPreInstalledSystemPackages(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public IntentSender getAppMarketActivityIntent(String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public IntentSender getPrivateSpaceSettingsIntent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void showAppDetailsAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean isPackageEnabled(String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public Bundle getSuspendedPackageLauncherExtras(String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean isActivityEnabled(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApplicationInfo) obtain2.readTypedObject(ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public LauncherApps.AppUsageLimit getAppUsageLimit(String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LauncherApps.AppUsageLimit) obtain2.readTypedObject(LauncherApps.AppUsageLimit.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public ParceledListSlice getShortcuts(String str, ShortcutQueryWrapper shortcutQueryWrapper, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutQueryWrapper, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void pinShortcuts(String str, String str2, List<String> list, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public int getShortcutIconResId(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean hasShortcutHostPermission(String str) throws RemoteException {
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

            @Override // android.content.pm.ILauncherApps
            public boolean shouldHideFromSuggestions(String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public ParceledListSlice getShortcutConfigActivities(String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public IntentSender getShortcutConfigActivityIntent(String str, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public PendingIntent getShortcutIntent(String str, String str2, String str3, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void registerPackageInstallerCallback(String str, IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageInstallerCallback);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public ParceledListSlice getAllSessions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void registerShortcutChangeCallback(String str, ShortcutQueryWrapper shortcutQueryWrapper, IShortcutChangeCallback iShortcutChangeCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutQueryWrapper, 0);
                    obtain.writeStrongInterface(iShortcutChangeCallback);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void unregisterShortcutChangeCallback(String str, IShortcutChangeCallback iShortcutChangeCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iShortcutChangeCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void cacheShortcuts(String str, String str2, List<String> list, UserHandle userHandle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void uncacheShortcuts(String str, String str2, List<String> list, UserHandle userHandle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public String getShortcutIconUri(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public Map<String, LauncherActivityInfoInternal> getActivityOverrides(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.content.pm.ILauncherApps$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), (LauncherActivityInfoInternal) Parcel.this.readTypedObject(LauncherActivityInfoInternal.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void registerDumpCallback(IDumpCallback iDumpCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDumpCallback);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void unRegisterDumpCallback(IDumpCallback iDumpCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDumpCallback);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void setArchiveCompatibilityOptions(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public List<UserHandle> getUserProfiles() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void saveViewCaptureData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void changePackageIcon(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
