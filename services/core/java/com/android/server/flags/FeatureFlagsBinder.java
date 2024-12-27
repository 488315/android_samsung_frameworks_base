package com.android.server.flags;

import android.annotation.SystemApi;
import android.flags.IFeatureFlags;
import android.flags.IFeatureFlagsCallback;
import android.flags.SyncableFlag;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.util.Slog;

import com.android.internal.flags.CoreFlags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FastPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.flags.DynamicFlagBinderDelegate.BinderGriever;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

class FeatureFlagsBinder extends IFeatureFlags.Stub {
    public final DynamicFlagBinderDelegate mDynamicFlagDelegate;
    public final FlagCache mFlagCache = new FlagCache();
    public final FlagOverrideStore mFlagStore;
    public final FeatureFlagsService.PermissionsChecker mPermissionsChecker;
    public final FlagsShellCommand mShellCommand;

    public FeatureFlagsBinder(
            FlagOverrideStore flagOverrideStore,
            FlagsShellCommand flagsShellCommand,
            FeatureFlagsService.PermissionsChecker permissionsChecker) {
        this.mFlagStore = flagOverrideStore;
        this.mShellCommand = flagsShellCommand;
        this.mDynamicFlagDelegate = new DynamicFlagBinderDelegate(flagOverrideStore);
        this.mPermissionsChecker = permissionsChecker;
    }

    @SystemApi
    public int handleShellCommand(
            ParcelFileDescriptor parcelFileDescriptor,
            ParcelFileDescriptor parcelFileDescriptor2,
            ParcelFileDescriptor parcelFileDescriptor3,
            String[] strArr) {
        boolean z;
        int i = 4;
        FileOutputStream fileOutputStream =
                new FileOutputStream(parcelFileDescriptor2.getFileDescriptor());
        FileOutputStream fileOutputStream2 =
                new FileOutputStream(parcelFileDescriptor3.getFileDescriptor());
        FlagsShellCommand flagsShellCommand = this.mShellCommand;
        flagsShellCommand.getClass();
        FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
        FastPrintWriter fastPrintWriter2 = new FastPrintWriter(fileOutputStream2);
        if (strArr.length == 0) {
            FlagsShellCommand.printHelp(fastPrintWriter);
        } else {
            String lowerCase = strArr[0].toLowerCase(Locale.ROOT);
            lowerCase.getClass();
            FlagOverrideStore flagOverrideStore = flagsShellCommand.mFlagStore;
            switch (lowerCase.hashCode()) {
                case 102230:
                    if (lowerCase.equals("get")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 113762:
                    if (lowerCase.equals("set")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 3198785:
                    if (lowerCase.equals("help")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case 3322014:
                    if (lowerCase.equals("list")) {
                        z = 3;
                        break;
                    }
                    z = -1;
                    break;
                case 96768678:
                    if (lowerCase.equals("erase")) {
                        z = 4;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    if (!FlagsShellCommand.validateNumArguments(
                            strArr, 2, 2, strArr[0], fastPrintWriter2)) {
                        fastPrintWriter2.println("Expected `" + strArr[0] + " <namespace> <name>`");
                        fastPrintWriter2.flush();
                        return -1;
                    }
                    String str = flagOverrideStore.get(strArr[1], strArr[2]);
                    fastPrintWriter.print(strArr[1] + "." + strArr[2] + " is ");
                    if (str == null || str.isEmpty()) {
                        fastPrintWriter.println("<unset>");
                    } else {
                        fastPrintWriter.println("\"" + str.translateEscapes() + "\"");
                    }
                    fastPrintWriter.flush();
                    break;
                    break;
                case true:
                    if (!FlagsShellCommand.validateNumArguments(
                            strArr, 3, 3, strArr[0], fastPrintWriter2)) {
                        fastPrintWriter2.println(
                                "Expected `" + strArr[0] + " <namespace> <name> <value>`");
                        fastPrintWriter2.flush();
                        return -1;
                    }
                    flagOverrideStore.set(strArr[1], strArr[2], strArr[3]);
                    fastPrintWriter.println(
                            "Flag " + strArr[1] + "." + strArr[2] + " is now " + strArr[3]);
                    fastPrintWriter.flush();
                    break;
                case true:
                    FlagsShellCommand.printHelp(fastPrintWriter);
                    break;
                case true:
                    if (!FlagsShellCommand.validateNumArguments(
                            strArr, 0, 1, strArr[0], fastPrintWriter2)) {
                        fastPrintWriter2.println("Expected `" + strArr[0] + " [namespace]`");
                        fastPrintWriter2.flush();
                        return -1;
                    }
                    Map flagsForNamespace =
                            strArr.length == 2
                                    ? flagOverrideStore.getFlagsForNamespace(strArr[1])
                                    : flagOverrideStore.getFlagsForNamespace(null);
                    if (flagsForNamespace.isEmpty()) {
                        fastPrintWriter.println("No overrides set");
                    } else {
                        int i2 = 9;
                        int i3 = 5;
                        for (Map.Entry entry : flagsForNamespace.entrySet()) {
                            i2 = Math.max(i2, ((String) entry.getKey()).length());
                            for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                                i = Math.max(i, ((String) entry2.getKey()).length());
                                i3 = Math.max(i3, ((String) entry2.getValue()).length());
                            }
                        }
                        fastPrintWriter.print(
                                String.format(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                i2, "%-", "s"),
                                        "namespace"));
                        fastPrintWriter.print(' ');
                        fastPrintWriter.print(
                                String.format(
                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                i, new StringBuilder("%-"), "s"),
                                        "flag"));
                        fastPrintWriter.print(' ');
                        fastPrintWriter.println("value");
                        for (int i4 = 0; i4 < i2; i4++) {
                            fastPrintWriter.print('=');
                        }
                        fastPrintWriter.print(' ');
                        for (int i5 = 0; i5 < i; i5++) {
                            fastPrintWriter.print('=');
                        }
                        fastPrintWriter.print(' ');
                        for (int i6 = 0; i6 < i3; i6++) {
                            fastPrintWriter.print('=');
                        }
                        fastPrintWriter.println();
                        for (Map.Entry entry3 : flagsForNamespace.entrySet()) {
                            for (Map.Entry entry4 : ((Map) entry3.getValue()).entrySet()) {
                                fastPrintWriter.print(
                                        String.format(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(i2, "%-", "s"),
                                                entry3.getKey()));
                                fastPrintWriter.print(' ');
                                fastPrintWriter.print(
                                        String.format(
                                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                        i, new StringBuilder("%-"), "s"),
                                                entry4.getKey()));
                                fastPrintWriter.print(' ');
                                fastPrintWriter.println((String) entry4.getValue());
                            }
                        }
                    }
                    fastPrintWriter.flush();
                    break;
                case true:
                    if (!FlagsShellCommand.validateNumArguments(
                            strArr, 2, 2, strArr[0], fastPrintWriter2)) {
                        fastPrintWriter2.println("Expected `" + strArr[0] + " <namespace> <name>`");
                        fastPrintWriter2.flush();
                        return -1;
                    }
                    flagOverrideStore.erase(strArr[1], strArr[2]);
                    fastPrintWriter.println("Erased " + strArr[1] + "." + strArr[2]);
                    break;
                default:
                    fastPrintWriter.println("This command is unknown.");
                    FlagsShellCommand.printHelp(fastPrintWriter);
                    fastPrintWriter.flush();
                    return -1;
            }
        }
        return 0;
    }

    public final void overrideFlag(SyncableFlag syncableFlag) {
        this.mPermissionsChecker.assertWritePermission();
        IFeatureFlags.Stub.clearCallingIdentity();
        this.mFlagStore.set(
                syncableFlag.getNamespace(), syncableFlag.getName(), syncableFlag.getValue());
    }

    public final List queryFlags(List list) {
        String str;
        String str2;
        this.mPermissionsChecker.assertSyncPermission();
        IFeatureFlags.Stub.clearCallingIdentity();
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SyncableFlag syncableFlag = (SyncableFlag) it.next();
            String namespace = syncableFlag.getNamespace();
            String name = syncableFlag.getName();
            String str3 = this.mFlagStore.get(namespace, name);
            boolean z = str3 != null;
            if (syncableFlag.isDynamic()) {
                str =
                        this.mDynamicFlagDelegate.getFlagValue(
                                namespace, name, syncableFlag.getValue());
            } else {
                str = (String) this.mFlagCache.getOrNull(namespace, name);
                if (str == null) {
                    if (Build.IS_USER) {
                        str3 = null;
                    }
                    if (str3 == null) {
                        str = syncableFlag.getValue();
                    } else {
                        str2 = str3;
                        arrayList.add(
                                new SyncableFlag(
                                        syncableFlag.getNamespace(),
                                        syncableFlag.getName(),
                                        str2,
                                        syncableFlag.isDynamic(),
                                        z));
                    }
                }
            }
            str2 = str;
            arrayList.add(
                    new SyncableFlag(
                            syncableFlag.getNamespace(),
                            syncableFlag.getName(),
                            str2,
                            syncableFlag.isDynamic(),
                            z));
        }
        return arrayList;
    }

    public final void registerCallback(IFeatureFlagsCallback iFeatureFlagsCallback) {
        Set set;
        DynamicFlagBinderDelegate dynamicFlagBinderDelegate = this.mDynamicFlagDelegate;
        int callingPid = IFeatureFlags.Stub.getCallingPid();
        synchronized (dynamicFlagBinderDelegate.mCallbacks) {
            set =
                    (Set)
                            ((HashMap) dynamicFlagBinderDelegate.mCallbacks)
                                    .computeIfAbsent(
                                            Integer.valueOf(callingPid),
                                            DynamicFlagBinderDelegate.NEW_CALLBACK_SET);
            set.add(iFeatureFlagsCallback);
        }
        try {
            iFeatureFlagsCallback
                    .asBinder()
                    .linkToDeath(dynamicFlagBinderDelegate.new BinderGriever(callingPid), 0);
        } catch (RemoteException unused) {
            Slog.e(
                    "FeatureFlagsService",
                    "Failed to link to binder death. Callback not registered.");
            synchronized (dynamicFlagBinderDelegate.mCallbacks) {
                set.remove(iFeatureFlagsCallback);
            }
        }
    }

    public final void resetFlag(SyncableFlag syncableFlag) {
        this.mPermissionsChecker.assertWritePermission();
        IFeatureFlags.Stub.clearCallingIdentity();
        this.mFlagStore.erase(syncableFlag.getNamespace(), syncableFlag.getName());
    }

    public final List syncFlags(List list) {
        boolean z;
        SyncableFlag syncableFlag;
        boolean containsKey;
        int callingPid = IFeatureFlags.Stub.getCallingPid();
        ArrayList arrayList = new ArrayList();
        try {
            this.mPermissionsChecker.assertSyncPermission();
            IFeatureFlags.Stub.clearCallingIdentity();
            z = true;
            e = null;
        } catch (SecurityException e) {
            e = e;
            z = false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SyncableFlag syncableFlag2 = (SyncableFlag) it.next();
            if (!z && !CoreFlags.isCoreFlag(syncableFlag2)) {
                throw e;
            }
            String namespace = syncableFlag2.getNamespace();
            String name = syncableFlag2.getName();
            if (syncableFlag2.isDynamic()) {
                DynamicFlagBinderDelegate dynamicFlagBinderDelegate = this.mDynamicFlagDelegate;
                dynamicFlagBinderDelegate.getClass();
                if (syncableFlag2.isDynamic()) {
                    String namespace2 = syncableFlag2.getNamespace();
                    String name2 = syncableFlag2.getName();
                    DynamicFlagBinderDelegate.DynamicFlagData dynamicFlagData =
                            (DynamicFlagBinderDelegate.DynamicFlagData)
                                    dynamicFlagBinderDelegate.mDynamicFlags.getOrNull(
                                            namespace2, name2);
                    String flagValue =
                            dynamicFlagBinderDelegate.getFlagValue(
                                    namespace2, name2, syncableFlag2.getValue());
                    FlagCache flagCache = dynamicFlagBinderDelegate.mDynamicFlags;
                    synchronized (flagCache.mCache) {
                        containsKey = ((HashMap) flagCache.mCache).containsKey(namespace2);
                    }
                    if (!containsKey) {
                        DeviceConfig.addOnPropertiesChangedListener(
                                namespace2,
                                BackgroundThread.getExecutor(),
                                dynamicFlagBinderDelegate.mDeviceConfigListener);
                    }
                    ((HashSet) dynamicFlagData.mPids).add(Integer.valueOf(callingPid));
                    dynamicFlagData.mValue = flagValue;
                    dynamicFlagData.mDefaultValue = syncableFlag2.getValue();
                    syncableFlag =
                            new SyncableFlag(
                                    syncableFlag2.getNamespace(),
                                    syncableFlag2.getName(),
                                    flagValue,
                                    true);
                } else {
                    continue;
                    arrayList.add(syncableFlag2);
                }
            } else {
                synchronized (this.mFlagCache) {
                    try {
                        String str = (String) this.mFlagCache.getOrNull(namespace, name);
                        if (str == null) {
                            str = Build.IS_USER ? null : this.mFlagStore.get(namespace, name);
                            if (str == null) {
                                str = syncableFlag2.getValue();
                            }
                            this.mFlagCache.setIfChanged(namespace, name, str);
                        }
                        syncableFlag =
                                new SyncableFlag(
                                        syncableFlag2.getNamespace(),
                                        syncableFlag2.getName(),
                                        str,
                                        false);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            syncableFlag2 = syncableFlag;
            arrayList.add(syncableFlag2);
        }
        return arrayList;
    }

    public final void unregisterCallback(IFeatureFlagsCallback iFeatureFlagsCallback) {
        DynamicFlagBinderDelegate dynamicFlagBinderDelegate = this.mDynamicFlagDelegate;
        int callingPid = IFeatureFlags.Stub.getCallingPid();
        synchronized (dynamicFlagBinderDelegate.mCallbacks) {
            ((Set)
                            ((HashMap) dynamicFlagBinderDelegate.mCallbacks)
                                    .computeIfAbsent(
                                            Integer.valueOf(callingPid),
                                            DynamicFlagBinderDelegate.NEW_CALLBACK_SET))
                    .remove(iFeatureFlagsCallback);
        }
    }
}
