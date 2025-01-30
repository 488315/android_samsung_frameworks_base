package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Pair;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.pm.Computer;
import com.android.server.pm.DumpState;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedMainComponent;
import com.android.server.pm.pkg.component.ParsedProvider;
import com.android.server.pm.pkg.component.ParsedService;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.utils.WatchableImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class ComponentResolverBase extends WatchableImpl implements ComponentResolverApi {
    public ComponentResolver.ActivityIntentResolver mActivities;
    public ComponentResolver.ProviderIntentResolver mProviders;
    public ArrayMap mProvidersByAuthority;
    public ComponentResolver.ReceiverIntentResolver mReceivers;
    public ComponentResolver.ServiceIntentResolver mServices;
    public UserManagerService mUserManager;

    public ComponentResolverBase(UserManagerService userManagerService) {
        this.mUserManager = userManagerService;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public boolean componentExists(ComponentName componentName) {
        return (((ParsedMainComponent) this.mActivities.mActivities.get(componentName)) == null && ((ParsedMainComponent) this.mReceivers.mActivities.get(componentName)) == null && ((ParsedMainComponent) this.mServices.mServices.get(componentName)) == null && this.mProviders.mProviders.get(componentName) == null) ? false : true;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedActivity getActivity(ComponentName componentName) {
        return (ParsedActivity) this.mActivities.mActivities.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedProvider getProvider(ComponentName componentName) {
        return (ParsedProvider) this.mProviders.mProviders.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedActivity getReceiver(ComponentName componentName) {
        return (ParsedActivity) this.mReceivers.mActivities.get(componentName);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ParsedService getService(ComponentName componentName) {
        return (ParsedService) this.mServices.mServices.get(componentName);
    }

    public boolean isActivityDefined(ComponentName componentName) {
        return this.mActivities.mActivities.get(componentName) != null;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryActivities(Computer computer, Intent intent, String str, long j, int i) {
        return this.mActivities.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryActivities(Computer computer, Intent intent, String str, long j, List list, int i) {
        return this.mActivities.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public ProviderInfo queryProvider(Computer computer, String str, long j, int i) {
        PackageStateInternal packageStateInternal;
        AndroidPackageInternal pkg;
        PackageUserStateInternal userStateOrDefault;
        ApplicationInfo generateApplicationInfo;
        ParsedProvider parsedProvider = (ParsedProvider) this.mProvidersByAuthority.get(str);
        if (parsedProvider == null || (packageStateInternal = computer.getPackageStateInternal(parsedProvider.getPackageName())) == null || (pkg = packageStateInternal.getPkg()) == null || (generateApplicationInfo = PackageInfoUtils.generateApplicationInfo(pkg, j, (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)), i, packageStateInternal)) == null) {
            return null;
        }
        return PackageInfoUtils.generateProviderInfo(pkg, parsedProvider, j, userStateOrDefault, generateApplicationInfo, i, packageStateInternal);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryProviders(Computer computer, Intent intent, String str, long j, int i) {
        return this.mProviders.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryProviders(Computer computer, Intent intent, String str, long j, List list, int i) {
        return this.mProviders.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryProviders(Computer computer, String str, String str2, int i, long j, int i2) {
        PackageStateInternal packageStateInternal;
        AndroidPackageInternal pkg;
        ProviderInfo generateProviderInfo;
        PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator = null;
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        ArrayList arrayList = null;
        for (int size = this.mProviders.mProviders.size() - 1; size >= 0; size--) {
            ParsedProvider parsedProvider = (ParsedProvider) this.mProviders.mProviders.valueAt(size);
            if (parsedProvider.getAuthority() != null && (packageStateInternal = computer.getPackageStateInternal(parsedProvider.getPackageName())) != null && (pkg = packageStateInternal.getPkg()) != null) {
                if (str != null) {
                    if (parsedProvider.getProcessName().equals(str)) {
                        if (!UserHandle.isSameApp(pkg.getUid(), i)) {
                        }
                    }
                }
                if (str2 == null || parsedProvider.getMetaData().containsKey(str2)) {
                    if (cachedApplicationInfoGenerator == null) {
                        cachedApplicationInfoGenerator = new PackageInfoUtils.CachedApplicationInfoGenerator();
                    }
                    PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator2 = cachedApplicationInfoGenerator;
                    PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i2);
                    ApplicationInfo generate = cachedApplicationInfoGenerator2.generate(pkg, j, userStateOrDefault, i2, packageStateInternal);
                    if (generate != null && (generateProviderInfo = PackageInfoUtils.generateProviderInfo(pkg, parsedProvider, j, userStateOrDefault, generate, i2, packageStateInternal)) != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList(size + 1);
                        }
                        arrayList.add(generateProviderInfo);
                    }
                    cachedApplicationInfoGenerator = cachedApplicationInfoGenerator2;
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryReceivers(Computer computer, Intent intent, String str, long j, int i) {
        return this.mReceivers.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryReceivers(Computer computer, Intent intent, String str, long j, List list, int i) {
        return this.mReceivers.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryServices(Computer computer, Intent intent, String str, long j, int i) {
        return this.mServices.queryIntent(computer, intent, str, j, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public List queryServices(Computer computer, Intent intent, String str, long j, List list, int i) {
        return this.mServices.queryIntentForPackage(computer, intent, str, j, list, i);
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void querySyncProviders(Computer computer, List list, List list2, boolean z, int i) {
        PackageStateInternal packageStateInternal;
        AndroidPackageInternal pkg;
        ProviderInfo generateProviderInfo;
        PackageInfoUtils.CachedApplicationInfoGenerator cachedApplicationInfoGenerator = null;
        for (int size = this.mProvidersByAuthority.size() - 1; size >= 0; size--) {
            ParsedProvider parsedProvider = (ParsedProvider) this.mProvidersByAuthority.valueAt(size);
            if (parsedProvider.isSyncable() && (packageStateInternal = computer.getPackageStateInternal(parsedProvider.getPackageName())) != null && (pkg = packageStateInternal.getPkg()) != null && (!z || packageStateInternal.isSystem())) {
                if (cachedApplicationInfoGenerator == null) {
                    cachedApplicationInfoGenerator = new PackageInfoUtils.CachedApplicationInfoGenerator();
                }
                PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                ApplicationInfo generate = cachedApplicationInfoGenerator.generate(pkg, 0L, userStateOrDefault, i, packageStateInternal);
                if (generate != null && (generateProviderInfo = PackageInfoUtils.generateProviderInfo(pkg, parsedProvider, 0L, userStateOrDefault, generate, i, packageStateInternal)) != null) {
                    list.add((String) this.mProvidersByAuthority.keyAt(size));
                    list2.add(generateProviderInfo);
                }
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpActivityResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
        if (this.mActivities.dump(printWriter, dumpState.getTitlePrinted() ? "\nActivity Resolver Table:" : "Activity Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpProviderResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
        if (this.mProviders.dump(printWriter, dumpState.getTitlePrinted() ? "\nProvider Resolver Table:" : "Provider Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpReceiverResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
        if (this.mReceivers.dump(printWriter, dumpState.getTitlePrinted() ? "\nReceiver Resolver Table:" : "Receiver Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServiceResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
        if (this.mServices.dump(printWriter, dumpState.getTitlePrinted() ? "\nService Resolver Table:" : "Service Resolver Table:", "  ", str, dumpState.isOptionEnabled(1), true)) {
            dumpState.setTitlePrinted(true);
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpContentProviders(Computer computer, PrintWriter printWriter, DumpState dumpState, String str) {
        boolean z = false;
        boolean z2 = false;
        for (ParsedProvider parsedProvider : this.mProviders.mProviders.values()) {
            if (str == null || str.equals(parsedProvider.getPackageName())) {
                if (!z2) {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("Registered ContentProviders:");
                    z2 = true;
                }
                printWriter.print("  ");
                ComponentName.printShortString(printWriter, parsedProvider.getPackageName(), parsedProvider.getName());
                printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                printWriter.print("    ");
                printWriter.println(parsedProvider.toString());
            }
        }
        for (Map.Entry entry : this.mProvidersByAuthority.entrySet()) {
            ParsedProvider parsedProvider2 = (ParsedProvider) entry.getValue();
            if (str == null || str.equals(parsedProvider2.getPackageName())) {
                if (!z) {
                    if (dumpState.onTitlePrinted()) {
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
                AndroidPackage androidPackage = computer.getPackage(parsedProvider2.getPackageName());
                if (androidPackage != null) {
                    printWriter.print("      applicationInfo=");
                    printWriter.println(AndroidPackageUtils.generateAppInfoWithoutState(androidPackage));
                }
            }
        }
    }

    @Override // com.android.server.pm.resolution.ComponentResolverApi
    public void dumpServicePermissions(PrintWriter printWriter, DumpState dumpState) {
        if (dumpState.onTitlePrinted()) {
            printWriter.println();
        }
        printWriter.println("Service permissions:");
        Iterator filterIterator = this.mServices.filterIterator();
        while (filterIterator.hasNext()) {
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
}
