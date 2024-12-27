package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Pair;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.server.IntentResolver;
import com.android.server.pm.Computer;
import com.android.server.pm.DumpState;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.utils.WatchableImpl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComponentResolverBase extends WatchableImpl implements ComponentResolverApi {
    public ComponentResolver.ActivityIntentResolver mActivities;
    public ComponentResolver.ServiceIntentResolver mProviders;
    public ArrayMap mProvidersByAuthority;
    public ComponentResolver.ReceiverIntentResolver mReceivers;
    public ComponentResolver.ServiceIntentResolver mServices;
    public final UserManagerService mUserManager;

    public ComponentResolverBase(UserManagerService userManagerService) {
        this.mUserManager = userManagerService;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public boolean componentExists(ComponentName componentName) {
        return (((ParsedMainComponent) this.mActivities.mActivities.get(componentName)) == null
                        && ((ParsedMainComponent) this.mReceivers.mActivities.get(componentName))
                                == null
                        && ((ParsedMainComponent)
                                        ((HashMap) this.mServices.mServices).get(componentName))
                                == null
                        && ((ArrayMap) this.mProviders.mServices).get(componentName) == null)
                ? false
                : true;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpActivityResolvers(DumpState dumpState, PrintWriter printWriter, String str) {
        if (this.mActivities.dump(
                printWriter,
                dumpState.mTitlePrinted ? "\nActivity Resolver Table:" : "Activity Resolver Table:",
                "  ",
                str,
                dumpState.isOptionEnabled(1),
                true)) {
            dumpState.mTitlePrinted = true;
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpContentProviders(
            Computer computer, PrintWriter printWriter, DumpState dumpState, String str) {
        boolean z = false;
        boolean z2 = false;
        for (ParsedProvider parsedProvider : ((ArrayMap) this.mProviders.mServices).values()) {
            if (str == null || str.equals(parsedProvider.getPackageName())) {
                if (!z2) {
                    boolean z3 = dumpState.mTitlePrinted;
                    dumpState.mTitlePrinted = true;
                    if (z3) {
                        printWriter.println();
                    }
                    printWriter.println("Registered ContentProviders:");
                    z2 = true;
                }
                printWriter.print("  ");
                ComponentName.printShortString(
                        printWriter, parsedProvider.getPackageName(), parsedProvider.getName());
                printWriter.println(":");
                printWriter.print("    ");
                printWriter.println(parsedProvider.toString());
            }
        }
        for (Map.Entry entry : this.mProvidersByAuthority.entrySet()) {
            ParsedProvider parsedProvider2 = (ParsedProvider) entry.getValue();
            if (str == null || str.equals(parsedProvider2.getPackageName())) {
                if (!z) {
                    boolean z4 = dumpState.mTitlePrinted;
                    dumpState.mTitlePrinted = true;
                    if (z4) {
                        printWriter.println();
                    }
                    printWriter.println("ContentProvider Authorities:");
                    z = true;
                }
                printWriter.print("  [");
                printWriter.print((String) entry.getKey());
                printWriter.println("]:");
                printWriter.print("    ");
                printWriter.println(parsedProvider2.toString());
                AndroidPackageHidden androidPackageHidden =
                        computer.getPackage(parsedProvider2.getPackageName());
                if (androidPackageHidden != null) {
                    printWriter.print("      applicationInfo=");
                    printWriter.println(androidPackageHidden.toAppInfoWithoutState());
                }
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpProviderResolvers(DumpState dumpState, PrintWriter printWriter, String str) {
        if (this.mProviders.dump(
                printWriter,
                dumpState.mTitlePrinted ? "\nProvider Resolver Table:" : "Provider Resolver Table:",
                "  ",
                str,
                dumpState.isOptionEnabled(1),
                true)) {
            dumpState.mTitlePrinted = true;
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpReceiverResolvers(DumpState dumpState, PrintWriter printWriter, String str) {
        if (this.mReceivers.dump(
                printWriter,
                dumpState.mTitlePrinted ? "\nReceiver Resolver Table:" : "Receiver Resolver Table:",
                "  ",
                str,
                dumpState.isOptionEnabled(1),
                true)) {
            dumpState.mTitlePrinted = true;
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServicePermissions(PrintWriter printWriter, DumpState dumpState) {
        boolean z = dumpState.mTitlePrinted;
        dumpState.mTitlePrinted = true;
        if (z) {
            printWriter.println();
        }
        printWriter.println("Service permissions:");
        IntentResolver.IteratorWrapper filterIterator = this.mServices.filterIterator();
        while (filterIterator.mI.hasNext()) {
            ParsedService parsedService = (ParsedService) ((Pair) filterIterator.next()).first;
            String permission = parsedService.getPermission();
            if (permission != null) {
                printWriter.print("    ");
                printWriter.print(parsedService.getComponentName().flattenToShortString());
                printWriter.print(": ");
                printWriter.println(permission);
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServiceResolvers(DumpState dumpState, PrintWriter printWriter, String str) {
        if (this.mServices.dump(
                printWriter,
                dumpState.mTitlePrinted ? "\nService Resolver Table:" : "Service Resolver Table:",
                "  ",
                str,
                dumpState.isOptionEnabled(1),
                true)) {
            dumpState.mTitlePrinted = true;
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedActivity getActivity(ComponentName componentName) {
        return (ParsedActivity) this.mActivities.mActivities.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedProvider getProvider(ComponentName componentName) {
        return (ParsedProvider) ((ArrayMap) this.mProviders.mServices).get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedActivity getReceiver(ComponentName componentName) {
        return (ParsedActivity) this.mReceivers.mActivities.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedService getService(ComponentName componentName) {
        return (ParsedService) ((HashMap) this.mServices.mServices).get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryActivities(Computer computer, Intent intent, String str, long j, int i) {
        return this.mActivities.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryActivities(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        return this.mActivities.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ProviderInfo queryProvider(Computer computer, String str, long j, int i) {
        PackageSetting packageStateInternal;
        AndroidPackageInternal androidPackageInternal;
        PackageUserStateInternal userStateOrDefault;
        ApplicationInfo generateApplicationInfo;
        ParsedProvider parsedProvider = (ParsedProvider) this.mProvidersByAuthority.get(str);
        if (parsedProvider == null
                || (packageStateInternal =
                                computer.getPackageStateInternal(parsedProvider.getPackageName()))
                        == null
                || (androidPackageInternal = packageStateInternal.pkg) == null
                || (generateApplicationInfo =
                                PackageInfoUtils.generateApplicationInfo(
                                        androidPackageInternal,
                                        j,
                                        (userStateOrDefault =
                                                packageStateInternal.getUserStateOrDefault(i)),
                                        i,
                                        packageStateInternal))
                        == null) {
            return null;
        }
        return PackageInfoUtils.generateProviderInfo(
                androidPackageInternal,
                parsedProvider,
                j,
                userStateOrDefault,
                generateApplicationInfo,
                i,
                packageStateInternal);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryProviders(Computer computer, Intent intent, String str, long j, int i) {
        ComponentResolver.ServiceIntentResolver serviceIntentResolver = this.mProviders;
        if (serviceIntentResolver.mUserManager.mLocalService.exists(i)) {
            return serviceIntentResolver.queryIntent(computer, intent, str, (65536 & j) != 0, i, j);
        }
        return null;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryProviders(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        ComponentResolver.ServiceIntentResolver serviceIntentResolver = this.mProviders;
        if (!serviceIntentResolver.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        if (list == null) {
            return Collections.emptyList();
        }
        boolean z = (j & 65536) != 0;
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            ParsedProvider parsedProvider = (ParsedProvider) list.get(i2);
            List intents = parsedProvider.getIntents();
            if (!intents.isEmpty()) {
                Pair[] pairArr = new Pair[intents.size()];
                for (int i3 = 0; i3 < intents.size(); i3++) {
                    pairArr[i3] = Pair.create(parsedProvider, (ParsedIntentInfo) intents.get(i3));
                }
                arrayList.add(pairArr);
            }
        }
        return serviceIntentResolver.queryIntentFromList(computer, intent, str, z, arrayList, i, j);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryProviders(Computer computer, String str, String str2, int i, long j, int i2) {
        PackageSetting packageStateInternal;
        AndroidPackageInternal androidPackageInternal;
        PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator;
        ApplicationInfo applicationInfo;
        ProviderInfo generateProviderInfo;
        ComponentResolverBase componentResolverBase = this;
        PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator2 = null;
        if (!componentResolverBase.mUserManager.mLocalService.exists(i2)) {
            return null;
        }
        int size = ((ArrayMap) componentResolverBase.mProviders.mServices).size() - 1;
        ArrayList arrayList = null;
        while (size >= 0) {
            ParsedProvider parsedProvider =
                    (ParsedProvider)
                            ((ArrayMap) componentResolverBase.mProviders.mServices).valueAt(size);
            if (parsedProvider.getAuthority() != null
                    && (packageStateInternal =
                                    computer.getPackageStateInternal(
                                            parsedProvider.getPackageName()))
                            != null
                    && (androidPackageInternal = packageStateInternal.pkg) != null) {
                if (str != null) {
                    if (parsedProvider.getProcessName().equals(str)) {
                        if (!UserHandle.isSameApp(androidPackageInternal.getUid(), i)) {
                            size--;
                            componentResolverBase = this;
                        }
                    }
                }
                if (str2 == null || parsedProvider.getMetaData().containsKey(str2)) {
                    if (cachedApplicationInfoGenerator2 == null) {
                        cachedApplicationInfoGenerator2 =
                                new PackageInfoUtils.CachedApplicationInfoGenerator();
                    }
                    PackageInfoUtils.CachedApplicationInfoGenerator
                            cachedApplicationInfoGenerator3 = cachedApplicationInfoGenerator2;
                    PackageUserStateInternal userStateOrDefault =
                            packageStateInternal.getUserStateOrDefault(i2);
                    ApplicationInfo applicationInfo2 =
                            (ApplicationInfo)
                                    cachedApplicationInfoGenerator3.mCache.get(
                                            androidPackageInternal.getPackageName());
                    if (applicationInfo2 != null) {
                        applicationInfo = applicationInfo2;
                        cachedApplicationInfoGenerator = cachedApplicationInfoGenerator3;
                    } else {
                        cachedApplicationInfoGenerator = cachedApplicationInfoGenerator3;
                        ApplicationInfo generateApplicationInfo =
                                PackageInfoUtils.generateApplicationInfo(
                                        androidPackageInternal,
                                        j,
                                        userStateOrDefault,
                                        i2,
                                        packageStateInternal);
                        cachedApplicationInfoGenerator.mCache.put(
                                androidPackageInternal.getPackageName(), generateApplicationInfo);
                        applicationInfo = generateApplicationInfo;
                    }
                    if (applicationInfo != null
                            && (generateProviderInfo =
                                            PackageInfoUtils.generateProviderInfo(
                                                    androidPackageInternal,
                                                    parsedProvider,
                                                    j,
                                                    userStateOrDefault,
                                                    applicationInfo,
                                                    i2,
                                                    packageStateInternal))
                                    != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList(size + 1);
                        }
                        arrayList.add(generateProviderInfo);
                    }
                    cachedApplicationInfoGenerator2 = cachedApplicationInfoGenerator;
                }
                size--;
                componentResolverBase = this;
            }
            size--;
            componentResolverBase = this;
        }
        return arrayList;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryReceivers(Computer computer, Intent intent, String str, long j, int i) {
        return this.mReceivers.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryReceivers(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        return this.mReceivers.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryServices(Computer computer, Intent intent, String str, long j, int i) {
        ComponentResolver.ServiceIntentResolver serviceIntentResolver = this.mServices;
        if (serviceIntentResolver.mUserManager.mLocalService.exists(i)) {
            return serviceIntentResolver.queryIntent(computer, intent, str, (65536 & j) != 0, i, j);
        }
        return null;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryServices(
            Computer computer, Intent intent, String str, long j, List list, int i) {
        ComponentResolver.ServiceIntentResolver serviceIntentResolver = this.mServices;
        if (!serviceIntentResolver.mUserManager.mLocalService.exists(i)) {
            return null;
        }
        if (list == null) {
            return Collections.emptyList();
        }
        boolean z = (j & 65536) != 0;
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            ParsedService parsedService = (ParsedService) list.get(i2);
            List intents = parsedService.getIntents();
            if (intents.size() > 0) {
                Pair[] pairArr = new Pair[intents.size()];
                for (int i3 = 0; i3 < intents.size(); i3++) {
                    pairArr[i3] = Pair.create(parsedService, (ParsedIntentInfo) intents.get(i3));
                }
                arrayList.add(pairArr);
            }
        }
        return serviceIntentResolver.queryIntentFromList(computer, intent, str, z, arrayList, i, j);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void querySyncProviders(Computer computer, List list, List list2, boolean z, int i) {
        PackageSetting packageStateInternal;
        AndroidPackageInternal androidPackageInternal;
        ProviderInfo generateProviderInfo;
        PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator = null;
        for (int size = this.mProvidersByAuthority.size() - 1; size >= 0; size--) {
            ParsedProvider parsedProvider =
                    (ParsedProvider) this.mProvidersByAuthority.valueAt(size);
            if (parsedProvider.isSyncable()
                    && (packageStateInternal =
                                    computer.getPackageStateInternal(
                                            parsedProvider.getPackageName()))
                            != null
                    && (androidPackageInternal = packageStateInternal.pkg) != null
                    && (!z || packageStateInternal.isSystem())) {
                if (cachedApplicationInfoGenerator == null) {
                    cachedApplicationInfoGenerator =
                            new PackageInfoUtils.CachedApplicationInfoGenerator();
                }
                PackageUserStateInternal userStateOrDefault =
                        packageStateInternal.getUserStateOrDefault(i);
                ApplicationInfo applicationInfo =
                        (ApplicationInfo)
                                cachedApplicationInfoGenerator.mCache.get(
                                        androidPackageInternal.getPackageName());
                if (applicationInfo == null) {
                    applicationInfo =
                            PackageInfoUtils.generateApplicationInfo(
                                    androidPackageInternal,
                                    0L,
                                    userStateOrDefault,
                                    i,
                                    packageStateInternal);
                    cachedApplicationInfoGenerator.mCache.put(
                            androidPackageInternal.getPackageName(), applicationInfo);
                }
                ApplicationInfo applicationInfo2 = applicationInfo;
                if (applicationInfo2 != null
                        && (generateProviderInfo =
                                        PackageInfoUtils.generateProviderInfo(
                                                androidPackageInternal,
                                                parsedProvider,
                                                0L,
                                                userStateOrDefault,
                                                applicationInfo2,
                                                i,
                                                packageStateInternal))
                                != null) {
                    ((ArrayList) list).add((String) this.mProvidersByAuthority.keyAt(size));
                    ((ArrayList) list2).add(generateProviderInfo);
                }
            }
        }
    }
}
