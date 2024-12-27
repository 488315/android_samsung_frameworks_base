package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.InstantAppResolveInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.ApplicationPolicy;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedMainComponent;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedProviderImpl;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.util.ArrayUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.IntentResolver;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerException;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.UserNeedsBadgingCache;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateUtils;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public final class ComponentResolver extends ComponentResolverLocked implements Snappable {
    public static final Set PROTECTED_ACTIONS;
    public static final ComponentResolver$$ExternalSyntheticLambda0 RESOLVE_PRIORITY_SORTER;
    public boolean mDeferProtectedFilters;
    public List mProtectedFilters;
    public final AnonymousClass1 mSnapshot;

    public class ActivityIntentResolver extends MimeGroupsAwareIntentResolver {
        public final HashMap mActivities;
        public final UserNeedsBadgingCache mUserNeedsBadging;

        public ActivityIntentResolver(
                UserManagerService userManagerService,
                UserNeedsBadgingCache userNeedsBadgingCache) {
            super(userManagerService);
            this.mActivities = new HashMap();
            this.mUserNeedsBadging = userNeedsBadgingCache;
        }

        public ActivityIntentResolver(
                ActivityIntentResolver activityIntentResolver,
                UserManagerService userManagerService,
                UserNeedsBadgingCache userNeedsBadgingCache) {
            super(activityIntentResolver, userManagerService);
            HashMap hashMap = new HashMap();
            this.mActivities = hashMap;
            hashMap.putAll(activityIntentResolver.mActivities);
            this.mUserNeedsBadging = userNeedsBadgingCache;
        }

        public final void addActivity(
                Computer computer, ParsedActivity parsedActivity, String str, List list) {
            this.mActivities.put(parsedActivity.getComponentName(), parsedActivity);
            int size = parsedActivity.getIntents().size();
            for (int i = 0; i < size; i++) {
                ParsedIntentInfo parsedIntentInfo =
                        (ParsedIntentInfo) parsedActivity.getIntents().get(i);
                IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
                if (list != null && "activity".equals(str)) {
                    ((ArrayList) list).add(Pair.create(parsedActivity, parsedIntentInfo));
                }
                if (!intentFilter.debugCheck()) {
                    Log.w("PackageManager", "==> For Activity " + parsedActivity.getName());
                }
                addFilter(
                        (PackageDataSnapshot) computer,
                        Pair.create(parsedActivity, parsedIntentInfo));
            }
        }

        @Override // com.android.server.IntentResolver
        public final boolean allowFilterResult(List list, Object obj) {
            Pair pair = (Pair) obj;
            ArrayList arrayList = (ArrayList) list;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ActivityInfo activityInfo = ((ResolveInfo) arrayList.get(size)).activityInfo;
                if (Objects.equals(activityInfo.name, ((ParsedActivity) pair.first).getName())
                        && Objects.equals(
                                activityInfo.packageName,
                                ((ParsedActivity) pair.first).getPackageName())) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.IntentResolver
        public final void dumpFilter(PrintWriter printWriter, String str, Object obj) {
            Pair pair = (Pair) obj;
            ParsedActivity parsedActivity = (ParsedActivity) pair.first;
            ParsedIntentInfo parsedIntentInfo = (ParsedIntentInfo) pair.second;
            printWriter.print(str);
            printWriter.print(Integer.toHexString(System.identityHashCode(parsedActivity)));
            printWriter.print(' ');
            ComponentName.printShortString(
                    printWriter, parsedActivity.getPackageName(), parsedActivity.getClassName());
            printWriter.print(" filter ");
            printWriter.println(Integer.toHexString(System.identityHashCode(parsedIntentInfo)));
        }

        @Override // com.android.server.IntentResolver
        public final void dumpFilterLabel(PrintWriter printWriter, String str, Object obj, int i) {
            Pair pair = (Pair) obj;
            printWriter.print(str);
            printWriter.print(Integer.toHexString(System.identityHashCode(pair.first)));
            printWriter.print(' ');
            ComponentName.printShortString(
                    printWriter,
                    ((ParsedActivity) pair.first).getPackageName(),
                    ((ParsedActivity) pair.first).getClassName());
            if (i > 1) {
                printWriter.print(" (");
                printWriter.print(i);
                printWriter.print(" filters)");
            }
            printWriter.println();
        }

        @Override // com.android.server.IntentResolver
        public final Object filterToLabel(Object obj) {
            return (Pair) obj;
        }

        @Override // com.android.server.IntentResolver
        public final IntentFilter getIntentFilter(Object obj) {
            return ((ParsedIntentInfo) ((Pair) obj).second).getIntentFilter();
        }

        @Override // com.android.server.IntentResolver
        public final boolean isPackageForFilter(String str, Object obj) {
            return str.equals(((ParsedActivity) ((Pair) obj).first).getPackageName());
        }

        @Override // com.android.server.IntentResolver
        public final Object[] newArray(int i) {
            return new Pair[i];
        }

        @Override // com.android.server.IntentResolver
        public final Object newResult(Computer computer, Object obj, int i, int i2, long j) {
            PackageSetting packageStateInternal;
            ApplicationPolicy applicationPolicy;
            Pair pair = (Pair) obj;
            ParsedActivity parsedActivity = (ParsedActivity) pair.first;
            ParsedIntentInfo parsedIntentInfo = (ParsedIntentInfo) pair.second;
            IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
            ResolveInfo resolveInfo = null;
            if (this.mUserManager.mLocalService.exists(i2)
                    && (packageStateInternal =
                                    computer.getPackageStateInternal(
                                            parsedActivity.getPackageName()))
                            != null
                    && packageStateInternal.pkg != null
                    && PackageStateUtils.isEnabledAndMatches(
                            packageStateInternal, parsedActivity, j, i2)) {
                PackageUserStateInternal userStateOrDefault =
                        packageStateInternal.getUserStateOrDefault(i2);
                ActivityInfo generateActivityInfo =
                        PackageInfoUtils.generateActivityInfo(
                                packageStateInternal.pkg,
                                parsedActivity,
                                j,
                                userStateOrDefault,
                                i2,
                                packageStateInternal);
                if (generateActivityInfo != null) {
                    boolean z = (33554432 & j) != 0;
                    boolean z2 = (j & 16777216) != 0;
                    boolean z3 =
                            z2
                                    && intentFilter.isVisibleToInstantApp()
                                    && (!z || intentFilter.isExplicitlyVisibleToInstantApp());
                    boolean z4 = (j & 8388608) != 0;
                    if ((!z2 || z3 || userStateOrDefault.isInstantApp())
                            && ((z4 || !userStateOrDefault.isInstantApp())
                                    && (!userStateOrDefault.isInstantApp()
                                            || !packageStateInternal.getBoolean(2)))) {
                        resolveInfo =
                                new ResolveInfo(
                                        intentFilter.hasCategory(
                                                "android.intent.category.BROWSABLE"));
                        resolveInfo.activityInfo = generateActivityInfo;
                        if ((j & 64) != 0) {
                            resolveInfo.filter = intentFilter;
                        }
                        resolveInfo.handleAllWebDataURI = intentFilter.handleAllWebDataURI();
                        resolveInfo.priority = intentFilter.getPriority();
                        resolveInfo.match = i;
                        resolveInfo.isDefault = parsedIntentInfo.isHasDefault();
                        resolveInfo.labelRes = parsedIntentInfo.getLabelRes();
                        resolveInfo.nonLocalizedLabel = parsedIntentInfo.getNonLocalizedLabel();
                        if (SystemProperties.getBoolean("sys.knox.app_name_change", false)
                                && (applicationPolicy =
                                                EnterpriseDeviceManager.getInstance()
                                                        .getApplicationPolicy())
                                        != null) {
                            String applicationNameForComponent =
                                    applicationPolicy.getApplicationNameForComponent(
                                            parsedActivity.getPackageName()
                                                    + "/"
                                                    + generateActivityInfo.name,
                                            parsedActivity.getPackageName(),
                                            i2);
                            if (applicationNameForComponent != null) {
                                Log.e(
                                        "PackageManager",
                                        "replace res.nonLocalizedLabel("
                                                + ((Object) resolveInfo.nonLocalizedLabel)
                                                + ") to newName("
                                                + applicationNameForComponent
                                                + ") and activity.getPackageName() () UId("
                                                + Process.myUid()
                                                + ")");
                                resolveInfo.nonLocalizedLabel = applicationNameForComponent;
                            }
                        }
                        if (this.mUserNeedsBadging.get(i2)) {
                            resolveInfo.noResourceId = true;
                        } else {
                            resolveInfo.icon = parsedIntentInfo.getIcon();
                        }
                        resolveInfo.iconResourceId = parsedIntentInfo.getIcon();
                        resolveInfo.system = resolveInfo.activityInfo.applicationInfo.isSystemApp();
                        resolveInfo.isInstantAppAvailable = userStateOrDefault.isInstantApp();
                        resolveInfo.userHandle = UserHandle.of(i2);
                    }
                }
            }
            return resolveInfo;
        }

        public final List queryIntent(Computer computer, Intent intent, String str, long j, int i) {
            if (this.mUserManager.mLocalService.exists(i)) {
                return queryIntent(computer, intent, str, (65536 & j) != 0, i, j);
            }
            return null;
        }

        public final List queryIntentForPackage(
                Computer computer, Intent intent, String str, long j, List list, int i) {
            if (!this.mUserManager.mLocalService.exists(i)) {
                return null;
            }
            if (list == null) {
                return Collections.emptyList();
            }
            boolean z = (j & 65536) != 0;
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                ParsedActivity parsedActivity = (ParsedActivity) list.get(i2);
                List intents = parsedActivity.getIntents();
                if (!intents.isEmpty()) {
                    Pair[] pairArr = new Pair[intents.size()];
                    for (int i3 = 0; i3 < intents.size(); i3++) {
                        pairArr[i3] =
                                Pair.create(parsedActivity, (ParsedIntentInfo) intents.get(i3));
                    }
                    arrayList.add(pairArr);
                }
            }
            return queryIntentFromList(computer, intent, str, z, arrayList, i, j);
        }

        public final void removeActivity(ParsedActivity parsedActivity) {
            this.mActivities.remove(parsedActivity.getComponentName());
            int size = parsedActivity.getIntents().size();
            for (int i = 0; i < size; i++) {
                ParsedIntentInfo parsedIntentInfo =
                        (ParsedIntentInfo) parsedActivity.getIntents().get(i);
                parsedIntentInfo.getIntentFilter();
                removeFilter(Pair.create(parsedActivity, parsedIntentInfo));
            }
        }

        @Override // com.android.server.IntentResolver
        public final void sortResults(List list) {
            ((ArrayList) list).sort(ComponentResolver.RESOLVE_PRIORITY_SORTER);
        }
    }

    public final class InstantAppIntentResolver extends IntentResolver {
        public final ArrayMap mOrderResult = new ArrayMap();
        public final UserManagerService mUserManager;

        public InstantAppIntentResolver(UserManagerService userManagerService) {
            this.mUserManager = userManagerService;
        }

        @Override // com.android.server.IntentResolver
        public final void filterResults(List list) {
            if (this.mOrderResult.size() == 0) {
                return;
            }
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                InstantAppResolveInfo instantAppResolveInfo =
                        ((AuxiliaryResolveInfo.AuxiliaryFilter) arrayList.get(i)).resolveInfo;
                String packageName = instantAppResolveInfo.getPackageName();
                Pair pair = (Pair) this.mOrderResult.get(packageName);
                if (pair != null) {
                    if (pair.second == instantAppResolveInfo) {
                        this.mOrderResult.remove(packageName);
                        if (this.mOrderResult.size() == 0) {
                            return;
                        }
                    } else {
                        arrayList.remove(i);
                        size--;
                        i--;
                    }
                }
                i++;
            }
        }

        @Override // com.android.server.IntentResolver
        public final IntentFilter getIntentFilter(Object obj) {
            return (AuxiliaryResolveInfo.AuxiliaryFilter) obj;
        }

        @Override // com.android.server.IntentResolver
        public final /* bridge */ /* synthetic */ boolean isPackageForFilter(
                String str, Object obj) {
            return true;
        }

        @Override // com.android.server.IntentResolver
        public final Object[] newArray(int i) {
            return new AuxiliaryResolveInfo.AuxiliaryFilter[i];
        }

        @Override // com.android.server.IntentResolver
        public final Object newResult(Computer computer, Object obj, int i, int i2, long j) {
            AuxiliaryResolveInfo.AuxiliaryFilter auxiliaryFilter =
                    (AuxiliaryResolveInfo.AuxiliaryFilter) obj;
            if (this.mUserManager.mLocalService.exists(i2)) {
                String packageName = auxiliaryFilter.resolveInfo.getPackageName();
                int order = auxiliaryFilter.getOrder();
                Integer valueOf = Integer.valueOf(order);
                Pair pair = (Pair) this.mOrderResult.get(packageName);
                if (pair == null || ((Integer) pair.first).intValue() < order) {
                    InstantAppResolveInfo instantAppResolveInfo = auxiliaryFilter.resolveInfo;
                    if (order <= 0) {
                        return auxiliaryFilter;
                    }
                    this.mOrderResult.put(packageName, new Pair(valueOf, instantAppResolveInfo));
                    return auxiliaryFilter;
                }
            }
            return null;
        }
    }

    public abstract class MimeGroupsAwareIntentResolver extends IntentResolver {
        public boolean mIsUpdatingMimeGroup;
        public final ArrayMap mMimeGroupToFilter;
        public final UserManagerService mUserManager;

        public MimeGroupsAwareIntentResolver(UserManagerService userManagerService) {
            this.mMimeGroupToFilter = new ArrayMap();
            this.mIsUpdatingMimeGroup = false;
            this.mUserManager = userManagerService;
        }

        public MimeGroupsAwareIntentResolver(
                MimeGroupsAwareIntentResolver mimeGroupsAwareIntentResolver,
                UserManagerService userManagerService) {
            ArrayMap arrayMap = new ArrayMap();
            this.mMimeGroupToFilter = arrayMap;
            this.mIsUpdatingMimeGroup = false;
            this.mUserManager = userManagerService;
            copyFrom(mimeGroupsAwareIntentResolver);
            copyInto(arrayMap, mimeGroupsAwareIntentResolver.mMimeGroupToFilter);
            this.mIsUpdatingMimeGroup = mimeGroupsAwareIntentResolver.mIsUpdatingMimeGroup;
        }

        @Override // com.android.server.IntentResolver
        public final void addFilter(PackageDataSnapshot packageDataSnapshot, Pair pair) {
            IntentFilter intentFilter = getIntentFilter(pair);
            Computer computer = (Computer) packageDataSnapshot;
            IntentFilter intentFilter2 = getIntentFilter(pair);
            for (int countMimeGroups = intentFilter2.countMimeGroups() - 1;
                    countMimeGroups >= 0;
                    countMimeGroups--) {
                PackageSetting packageStateInternal =
                        computer.getPackageStateInternal(
                                ((ParsedComponent) pair.first).getPackageName());
                Iterator it =
                        (packageStateInternal == null
                                        ? Collections.emptyList()
                                        : (Collection)
                                                packageStateInternal
                                                        .getMimeGroups()
                                                        .get(
                                                                intentFilter2.getMimeGroup(
                                                                        countMimeGroups)))
                                .iterator();
                while (it.hasNext()) {
                    try {
                        intentFilter2.addDynamicDataType((String) it.next());
                    } catch (IntentFilter.MalformedMimeTypeException unused) {
                    }
                }
            }
            super.addFilter(packageDataSnapshot, (Object) pair);
            if (this.mIsUpdatingMimeGroup) {
                return;
            }
            register_intent_filter(
                    pair, intentFilter.mimeGroupsIterator(), this.mMimeGroupToFilter);
        }

        @Override // com.android.server.IntentResolver
        public final boolean isFilterStopped(Computer computer, Object obj, int i) {
            Pair pair = (Pair) obj;
            if (!this.mUserManager.mLocalService.exists(i)) {
                return true;
            }
            PackageSetting packageStateInternal =
                    computer.getPackageStateInternal(
                            ((ParsedComponent) pair.first).getPackageName());
            if (packageStateInternal != null && packageStateInternal.pkg != null) {
                if (!packageStateInternal.isSystem()) {
                    return packageStateInternal.getUserStateOrDefault(i).isStopped();
                }
                if (packageStateInternal.getBoolean(8)
                        && packageStateInternal.getUserStateOrDefault(i).isStopped()) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.IntentResolver
        public final void removeFilterInternal(Object obj) {
            Pair pair = (Pair) obj;
            IntentFilter intentFilter = getIntentFilter(pair);
            if (!this.mIsUpdatingMimeGroup) {
                unregister_intent_filter(
                        pair, intentFilter.mimeGroupsIterator(), this.mMimeGroupToFilter);
            }
            super.removeFilterInternal(pair);
            intentFilter.clearDynamicDataTypes();
        }

        public final boolean updateMimeGroup(Computer computer, String str, String str2) {
            boolean equals;
            Pair[] pairArr = (Pair[]) this.mMimeGroupToFilter.get(str2);
            int length = pairArr != null ? pairArr.length : 0;
            this.mIsUpdatingMimeGroup = true;
            boolean z = false;
            for (int i = 0; i < length; i++) {
                Pair pair = pairArr[i];
                if (pair == null) {
                    break;
                }
                if (isPackageForFilter(str, pair)) {
                    IntentFilter intentFilter = getIntentFilter(pair);
                    List dataTypes = intentFilter.dataTypes();
                    removeFilter(pair);
                    addFilter((PackageDataSnapshot) computer, pair);
                    List dataTypes2 = intentFilter.dataTypes();
                    if (dataTypes == null) {
                        if (dataTypes2 == null) {
                            equals = true;
                            z |= !equals;
                        }
                        equals = false;
                        z |= !equals;
                    } else {
                        if (dataTypes2 != null && dataTypes.size() == dataTypes2.size()) {
                            Collections.sort(dataTypes);
                            Collections.sort(dataTypes2);
                            equals = dataTypes.equals(dataTypes2);
                            z |= !equals;
                        }
                        equals = false;
                        z |= !equals;
                    }
                }
            }
            this.mIsUpdatingMimeGroup = false;
            return z;
        }
    }

    public final class ReceiverIntentResolver extends ActivityIntentResolver {}

    public final class ServiceIntentResolver extends MimeGroupsAwareIntentResolver {
        public final /* synthetic */ int $r8$classId;
        public final Object mServices;

        public ServiceIntentResolver(UserManagerService userManagerService, int i) {
            super(userManagerService);
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    super(userManagerService);
                    this.mServices = new ArrayMap();
                    break;
                default:
                    this.mServices = new HashMap();
                    break;
            }
        }

        public ServiceIntentResolver(
                ServiceIntentResolver serviceIntentResolver,
                UserManagerService userManagerService) {
            super(serviceIntentResolver, userManagerService);
            this.$r8$classId = 0;
            HashMap hashMap = new HashMap();
            this.mServices = hashMap;
            hashMap.putAll((HashMap) serviceIntentResolver.mServices);
        }

        public ServiceIntentResolver(
                ServiceIntentResolver serviceIntentResolver,
                UserManagerService userManagerService,
                byte b) {
            super(serviceIntentResolver, userManagerService);
            this.$r8$classId = 1;
            ArrayMap arrayMap = new ArrayMap();
            this.mServices = arrayMap;
            arrayMap.putAll((ArrayMap) serviceIntentResolver.mServices);
        }

        @Override // com.android.server.IntentResolver
        public final boolean allowFilterResult(List list, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    Pair pair = (Pair) obj;
                    ArrayList arrayList = (ArrayList) list;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        ServiceInfo serviceInfo = ((ResolveInfo) arrayList.get(size)).serviceInfo;
                        if (Objects.equals(
                                        serviceInfo.name,
                                        ((ParsedService) pair.first).getClassName())
                                && Objects.equals(
                                        serviceInfo.packageName,
                                        ((ParsedService) pair.first).getPackageName())) {
                            break;
                        }
                    }
                    break;
                default:
                    Pair pair2 = (Pair) obj;
                    ArrayList arrayList2 = (ArrayList) list;
                    for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                        ProviderInfo providerInfo =
                                ((ResolveInfo) arrayList2.get(size2)).providerInfo;
                        if (Objects.equals(
                                        providerInfo.name,
                                        ((ParsedProvider) pair2.first).getClassName())
                                && Objects.equals(
                                        providerInfo.packageName,
                                        ((ParsedProvider) pair2.first).getPackageName())) {
                            break;
                        }
                    }
                    break;
            }
            return true;
        }

        @Override // com.android.server.IntentResolver
        public final void dumpFilter(PrintWriter printWriter, String str, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    Pair pair = (Pair) obj;
                    ParsedService parsedService = (ParsedService) pair.first;
                    ParsedIntentInfo parsedIntentInfo = (ParsedIntentInfo) pair.second;
                    printWriter.print(str);
                    printWriter.print(Integer.toHexString(System.identityHashCode(parsedService)));
                    printWriter.print(' ');
                    ComponentName.printShortString(
                            printWriter,
                            parsedService.getPackageName(),
                            parsedService.getClassName());
                    printWriter.print(" filter ");
                    printWriter.print(
                            Integer.toHexString(System.identityHashCode(parsedIntentInfo)));
                    if (parsedService.getPermission() == null) {
                        printWriter.println();
                        break;
                    } else {
                        printWriter.print(" permission ");
                        printWriter.println(parsedService.getPermission());
                        break;
                    }
                default:
                    Pair pair2 = (Pair) obj;
                    ParsedProvider parsedProvider = (ParsedProvider) pair2.first;
                    ParsedIntentInfo parsedIntentInfo2 = (ParsedIntentInfo) pair2.second;
                    printWriter.print(str);
                    printWriter.print(Integer.toHexString(System.identityHashCode(parsedProvider)));
                    printWriter.print(' ');
                    ComponentName.printShortString(
                            printWriter,
                            parsedProvider.getPackageName(),
                            parsedProvider.getClassName());
                    printWriter.print(" filter ");
                    printWriter.println(
                            Integer.toHexString(System.identityHashCode(parsedIntentInfo2)));
                    break;
            }
        }

        @Override // com.android.server.IntentResolver
        public final void dumpFilterLabel(PrintWriter printWriter, String str, Object obj, int i) {
            switch (this.$r8$classId) {
                case 0:
                    Pair pair = (Pair) obj;
                    printWriter.print(str);
                    printWriter.print(Integer.toHexString(System.identityHashCode(pair.first)));
                    printWriter.print(' ');
                    ComponentName.printShortString(
                            printWriter,
                            ((ParsedService) pair.first).getPackageName(),
                            ((ParsedService) pair.first).getClassName());
                    if (i > 1) {
                        printWriter.print(" (");
                        printWriter.print(i);
                        printWriter.print(" filters)");
                    }
                    printWriter.println();
                    break;
                default:
                    Pair pair2 = (Pair) obj;
                    printWriter.print(str);
                    printWriter.print(Integer.toHexString(System.identityHashCode(pair2.first)));
                    printWriter.print(' ');
                    ComponentName.printShortString(
                            printWriter,
                            ((ParsedProvider) pair2.first).getPackageName(),
                            ((ParsedProvider) pair2.first).getClassName());
                    if (i > 1) {
                        printWriter.print(" (");
                        printWriter.print(i);
                        printWriter.print(" filters)");
                    }
                    printWriter.println();
                    break;
            }
        }

        @Override // com.android.server.IntentResolver
        public final Object filterToLabel(Object obj) {
            switch (this.$r8$classId) {
            }
            return (Pair) obj;
        }

        @Override // com.android.server.IntentResolver
        public final IntentFilter getIntentFilter(Object obj) {
            switch (this.$r8$classId) {
            }
            return ((ParsedIntentInfo) ((Pair) obj).second).getIntentFilter();
        }

        @Override // com.android.server.IntentResolver
        public final boolean isPackageForFilter(String str, Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    return str.equals(((ParsedService) ((Pair) obj).first).getPackageName());
                default:
                    return str.equals(((ParsedProvider) ((Pair) obj).first).getPackageName());
            }
        }

        @Override // com.android.server.IntentResolver
        public final Object[] newArray(int i) {
            switch (this.$r8$classId) {
                case 0:
                    return new Pair[i];
                default:
                    return new Pair[i];
            }
        }

        @Override // com.android.server.IntentResolver
        public final Object newResult(Computer computer, Object obj, int i, int i2, long j) {
            ApplicationInfo generateApplicationInfo;
            ProviderInfo generateProviderInfo;
            switch (this.$r8$classId) {
                case 0:
                    Pair pair = (Pair) obj;
                    ResolveInfo resolveInfo = null;
                    if (this.mUserManager.mLocalService.exists(i2)) {
                        ParsedService parsedService = (ParsedService) pair.first;
                        ParsedIntentInfo parsedIntentInfo = (ParsedIntentInfo) pair.second;
                        IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
                        PackageSetting packageStateInternal =
                                computer.getPackageStateInternal(parsedService.getPackageName());
                        if (packageStateInternal != null
                                && packageStateInternal.pkg != null
                                && PackageStateUtils.isEnabledAndMatches(
                                        packageStateInternal, parsedService, j, i2)) {
                            PackageUserStateInternal userStateOrDefault =
                                    packageStateInternal.getUserStateOrDefault(i2);
                            ServiceInfo generateServiceInfo =
                                    PackageInfoUtils.generateServiceInfo(
                                            packageStateInternal.pkg,
                                            parsedService,
                                            j,
                                            userStateOrDefault,
                                            null,
                                            i2,
                                            packageStateInternal);
                            if (generateServiceInfo != null) {
                                boolean z = (16777216 & j) != 0;
                                boolean z2 = (8388608 & j) != 0;
                                if ((!z
                                                || intentFilter.isVisibleToInstantApp()
                                                || userStateOrDefault.isInstantApp())
                                        && ((z2 || !userStateOrDefault.isInstantApp())
                                                && (!userStateOrDefault.isInstantApp()
                                                        || !packageStateInternal.getBoolean(2)))) {
                                    resolveInfo = new ResolveInfo();
                                    resolveInfo.serviceInfo = generateServiceInfo;
                                    if ((64 & j) != 0) {
                                        resolveInfo.filter = intentFilter;
                                    }
                                    resolveInfo.priority = intentFilter.getPriority();
                                    resolveInfo.match = i;
                                    resolveInfo.isDefault = parsedIntentInfo.isHasDefault();
                                    resolveInfo.labelRes = parsedIntentInfo.getLabelRes();
                                    resolveInfo.nonLocalizedLabel =
                                            parsedIntentInfo.getNonLocalizedLabel();
                                    resolveInfo.icon = parsedIntentInfo.getIcon();
                                    resolveInfo.system =
                                            resolveInfo.serviceInfo.applicationInfo.isSystemApp();
                                }
                            }
                        }
                    }
                    return resolveInfo;
                default:
                    Pair pair2 = (Pair) obj;
                    ResolveInfo resolveInfo2 = null;
                    if (this.mUserManager.mLocalService.exists(i2)) {
                        ParsedProvider parsedProvider = (ParsedProvider) pair2.first;
                        ParsedIntentInfo parsedIntentInfo2 = (ParsedIntentInfo) pair2.second;
                        IntentFilter intentFilter2 = parsedIntentInfo2.getIntentFilter();
                        PackageSetting packageStateInternal2 =
                                computer.getPackageStateInternal(parsedProvider.getPackageName());
                        if (packageStateInternal2 != null
                                && packageStateInternal2.pkg != null
                                && PackageStateUtils.isEnabledAndMatches(
                                        packageStateInternal2, parsedProvider, j, i2)) {
                            PackageUserStateInternal userStateOrDefault2 =
                                    packageStateInternal2.getUserStateOrDefault(i2);
                            boolean z3 = (16777216 & j) != 0;
                            boolean z4 = (8388608 & j) != 0;
                            if ((!z3
                                            || intentFilter2.isVisibleToInstantApp()
                                            || userStateOrDefault2.isInstantApp())
                                    && ((z4 || !userStateOrDefault2.isInstantApp())
                                            && ((!userStateOrDefault2.isInstantApp()
                                                            || !packageStateInternal2.getBoolean(2))
                                                    && (generateApplicationInfo =
                                                                    PackageInfoUtils
                                                                            .generateApplicationInfo(
                                                                                    packageStateInternal2
                                                                                            .pkg,
                                                                                    j,
                                                                                    userStateOrDefault2,
                                                                                    i2,
                                                                                    packageStateInternal2))
                                                            != null
                                                    && (generateProviderInfo =
                                                                    PackageInfoUtils
                                                                            .generateProviderInfo(
                                                                                    packageStateInternal2
                                                                                            .pkg,
                                                                                    parsedProvider,
                                                                                    j,
                                                                                    userStateOrDefault2,
                                                                                    generateApplicationInfo,
                                                                                    i2,
                                                                                    packageStateInternal2))
                                                            != null))) {
                                resolveInfo2 = new ResolveInfo();
                                resolveInfo2.providerInfo = generateProviderInfo;
                                if ((64 & j) != 0) {
                                    resolveInfo2.filter = intentFilter2;
                                }
                                resolveInfo2.priority = intentFilter2.getPriority();
                                resolveInfo2.match = i;
                                resolveInfo2.isDefault = parsedIntentInfo2.isHasDefault();
                                resolveInfo2.labelRes = parsedIntentInfo2.getLabelRes();
                                resolveInfo2.nonLocalizedLabel =
                                        parsedIntentInfo2.getNonLocalizedLabel();
                                resolveInfo2.icon = parsedIntentInfo2.getIcon();
                                resolveInfo2.system =
                                        resolveInfo2.providerInfo.applicationInfo.isSystemApp();
                            }
                        }
                    }
                    return resolveInfo2;
            }
        }

        @Override // com.android.server.IntentResolver
        public final void sortResults(List list) {
            switch (this.$r8$classId) {
                case 0:
                    ((ArrayList) list).sort(ComponentResolver.RESOLVE_PRIORITY_SORTER);
                    break;
                default:
                    ((ArrayList) list).sort(ComponentResolver.RESOLVE_PRIORITY_SORTER);
                    break;
            }
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        PROTECTED_ACTIONS = arraySet;
        arraySet.add("android.intent.action.SEND");
        arraySet.add("android.intent.action.SENDTO");
        arraySet.add("android.intent.action.SEND_MULTIPLE");
        arraySet.add("android.intent.action.VIEW");
        RESOLVE_PRIORITY_SORTER = new ComponentResolver$$ExternalSyntheticLambda0();
    }

    public ComponentResolver(
            UserManagerService userManagerService,
            final UserNeedsBadgingCache userNeedsBadgingCache) {
        super(userManagerService);
        this.mDeferProtectedFilters = true;
        this.mActivities = new ActivityIntentResolver(userManagerService, userNeedsBadgingCache);
        this.mProviders = new ServiceIntentResolver(userManagerService, 1);
        this.mReceivers = new ReceiverIntentResolver(userManagerService, userNeedsBadgingCache);
        this.mServices = new ServiceIntentResolver(userManagerService, 0);
        this.mProvidersByAuthority = new ArrayMap();
        this.mDeferProtectedFilters = true;
        this.mSnapshot =
                new SnapshotCache(
                        this,
                        this) { // from class: com.android.server.pm.resolution.ComponentResolver.1
                    @Override // com.android.server.utils.SnapshotCache
                    public final Object createSnapshot() {
                        ComponentResolverSnapshot componentResolverSnapshot;
                        PackageManagerTracedLock packageManagerTracedLock =
                                ComponentResolver.this.mLock;
                        boolean z = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock) {
                            try {
                                componentResolverSnapshot =
                                        new ComponentResolverSnapshot(
                                                ComponentResolver.this, userNeedsBadgingCache);
                            } catch (Throwable th) {
                                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                                throw th;
                            }
                        }
                        return componentResolverSnapshot;
                    }
                };
    }

    public static void getIntentListSubset(List list, Function function, Iterator it) {
        while (it.hasNext()) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() == 0) {
                return;
            }
            Object next = it.next();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Iterator it3 =
                        (Iterator)
                                function.apply(((ParsedIntentInfo) it2.next()).getIntentFilter());
                while (it3 != null && it3.hasNext()) {
                    Object next2 = it3.next();
                    if (next2 == null || !next2.equals(next)) {}
                }
                it2.remove();
            }
        }
    }

    public final void addAllComponents(
            AndroidPackage androidPackage, boolean z, String str, Computer computer) {
        ParsedActivity parsedActivity;
        final int i = 0;
        final int i2 = 1;
        ArrayList arrayList = new ArrayList();
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                int size = ArrayUtils.size(androidPackage.getActivities());
                for (int i3 = 0; i3 < size; i3++) {
                    this.mActivities.addActivity(
                            computer,
                            (ParsedActivity) androidPackage.getActivities().get(i3),
                            "activity",
                            arrayList);
                }
                int size2 = ArrayUtils.size(androidPackage.getReceivers());
                for (int i4 = 0; i4 < size2; i4++) {
                    this.mReceivers.addActivity(
                            computer,
                            (ParsedActivity) androidPackage.getReceivers().get(i4),
                            "receiver",
                            null);
                }
                addProvidersLocked(computer, androidPackage);
                addServicesLocked(computer, androidPackage);
                dispatchChange(this);
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
        for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
            Pair pair = (Pair) arrayList.get(size3);
            PackageSetting disabledSystemPackage =
                    computer.getDisabledSystemPackage(
                            ((ParsedActivity) pair.first).getPackageName());
            AndroidPackage androidPackage2 =
                    disabledSystemPackage == null ? null : disabledSystemPackage.pkg;
            List activities = androidPackage2 != null ? androidPackage2.getActivities() : null;
            ParsedActivity parsedActivity2 = (ParsedActivity) pair.first;
            ParsedIntentInfo parsedIntentInfo = (ParsedIntentInfo) pair.second;
            IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
            if (intentFilter.getPriority() > 0) {
                String packageName = parsedActivity2.getPackageName();
                boolean isPrivileged = computer.getPackageStateInternal(packageName).isPrivileged();
                parsedActivity2.getClassName();
                if (isPrivileged) {
                    Iterator<String> actionsIterator = intentFilter.actionsIterator();
                    while (actionsIterator != null && actionsIterator.hasNext()) {
                        if (((ArraySet) PROTECTED_ACTIONS).contains(actionsIterator.next())) {
                            if (this.mDeferProtectedFilters) {
                                if (this.mProtectedFilters == null) {
                                    this.mProtectedFilters = new ArrayList();
                                }
                                ((ArrayList) this.mProtectedFilters)
                                        .add(Pair.create(parsedActivity2, parsedIntentInfo));
                            } else if (!packageName.equals(str)) {
                                intentFilter.setPriority(0);
                            }
                        }
                    }
                    if (activities != null) {
                        Iterator it = activities.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                parsedActivity = null;
                                break;
                            }
                            parsedActivity = (ParsedActivity) it.next();
                            if (parsedActivity.getName().equals(parsedActivity2.getName())
                                    || parsedActivity
                                            .getName()
                                            .equals(parsedActivity2.getTargetActivity())
                                    || (parsedActivity.getTargetActivity() != null
                                            && (parsedActivity
                                                            .getTargetActivity()
                                                            .equals(parsedActivity2.getName())
                                                    || parsedActivity
                                                            .getTargetActivity()
                                                            .equals(
                                                                    parsedActivity2
                                                                            .getTargetActivity())))) {
                                break;
                            }
                        }
                        if (parsedActivity == null) {
                            intentFilter.setPriority(0);
                        } else {
                            ArrayList arrayList2 = new ArrayList(parsedActivity.getIntents());
                            Iterator<String> actionsIterator2 = intentFilter.actionsIterator();
                            if (actionsIterator2 != null) {
                                getIntentListSubset(
                                        arrayList2,
                                        new Function() { // from class:
                                                         // com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                IntentFilter intentFilter2 = (IntentFilter) obj;
                                                switch (i) {
                                                    case 0:
                                                        return intentFilter2.actionsIterator();
                                                    case 1:
                                                        return intentFilter2.categoriesIterator();
                                                    case 2:
                                                        return intentFilter2.schemesIterator();
                                                    default:
                                                        return intentFilter2.authoritiesIterator();
                                                }
                                            }
                                        },
                                        actionsIterator2);
                                if (arrayList2.size() == 0) {
                                    intentFilter.setPriority(0);
                                }
                            }
                            Iterator<String> categoriesIterator = intentFilter.categoriesIterator();
                            if (categoriesIterator != null) {
                                getIntentListSubset(
                                        arrayList2,
                                        new Function() { // from class:
                                                         // com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                IntentFilter intentFilter2 = (IntentFilter) obj;
                                                switch (i2) {
                                                    case 0:
                                                        return intentFilter2.actionsIterator();
                                                    case 1:
                                                        return intentFilter2.categoriesIterator();
                                                    case 2:
                                                        return intentFilter2.schemesIterator();
                                                    default:
                                                        return intentFilter2.authoritiesIterator();
                                                }
                                            }
                                        },
                                        categoriesIterator);
                                if (arrayList2.size() == 0) {
                                    intentFilter.setPriority(0);
                                }
                            }
                            Iterator<String> schemesIterator = intentFilter.schemesIterator();
                            if (schemesIterator != null) {
                                final int i5 = 2;
                                getIntentListSubset(
                                        arrayList2,
                                        new Function() { // from class:
                                                         // com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                IntentFilter intentFilter2 = (IntentFilter) obj;
                                                switch (i5) {
                                                    case 0:
                                                        return intentFilter2.actionsIterator();
                                                    case 1:
                                                        return intentFilter2.categoriesIterator();
                                                    case 2:
                                                        return intentFilter2.schemesIterator();
                                                    default:
                                                        return intentFilter2.authoritiesIterator();
                                                }
                                            }
                                        },
                                        schemesIterator);
                                if (arrayList2.size() == 0) {
                                    intentFilter.setPriority(0);
                                }
                            }
                            Iterator<IntentFilter.AuthorityEntry> authoritiesIterator =
                                    intentFilter.authoritiesIterator();
                            if (authoritiesIterator != null) {
                                final int i6 = 3;
                                getIntentListSubset(
                                        arrayList2,
                                        new Function() { // from class:
                                                         // com.android.server.pm.resolution.ComponentResolver$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                IntentFilter intentFilter2 = (IntentFilter) obj;
                                                switch (i6) {
                                                    case 0:
                                                        return intentFilter2.actionsIterator();
                                                    case 1:
                                                        return intentFilter2.categoriesIterator();
                                                    case 2:
                                                        return intentFilter2.schemesIterator();
                                                    default:
                                                        return intentFilter2.authoritiesIterator();
                                                }
                                            }
                                        },
                                        authoritiesIterator);
                                if (arrayList2.size() == 0) {
                                    intentFilter.setPriority(0);
                                }
                            }
                            int i7 = 0;
                            for (int size4 = arrayList2.size() - 1; size4 >= 0; size4--) {
                                i7 =
                                        Math.max(
                                                i7,
                                                ((ParsedIntentInfo) arrayList2.get(size4))
                                                        .getIntentFilter()
                                                        .getPriority());
                            }
                            if (intentFilter.getPriority() > i7) {
                                intentFilter.setPriority(i7);
                            }
                        }
                    }
                } else {
                    intentFilter.setPriority(0);
                }
            }
            dispatchChange(this);
        }
    }

    public final void addProvidersLocked(Computer computer, AndroidPackage androidPackage) {
        int size = ArrayUtils.size(androidPackage.getProviders());
        for (int i = 0; i < size; i++) {
            ParsedProviderImpl parsedProviderImpl =
                    (ParsedProvider) androidPackage.getProviders().get(i);
            ServiceIntentResolver serviceIntentResolver = this.mProviders;
            if (((ArrayMap) serviceIntentResolver.mServices)
                    .containsKey(parsedProviderImpl.getComponentName())) {
                Slog.w(
                        "PackageManager",
                        "Provider "
                                + parsedProviderImpl.getComponentName()
                                + " already defined; ignoring");
            } else {
                ((ArrayMap) serviceIntentResolver.mServices)
                        .put(parsedProviderImpl.getComponentName(), parsedProviderImpl);
                int size2 = parsedProviderImpl.getIntents().size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ParsedIntentInfo parsedIntentInfo =
                            (ParsedIntentInfo) parsedProviderImpl.getIntents().get(i2);
                    if (!parsedIntentInfo.getIntentFilter().debugCheck()) {
                        Log.w("PackageManager", "==> For Provider " + parsedProviderImpl.getName());
                    }
                    serviceIntentResolver.addFilter(
                            (PackageDataSnapshot) computer,
                            Pair.create(parsedProviderImpl, parsedIntentInfo));
                }
            }
            if (parsedProviderImpl.getAuthority() != null) {
                String[] split = parsedProviderImpl.getAuthority().split(";");
                ComponentMutateUtils.setAuthority(parsedProviderImpl, (String) null);
                for (int i3 = 0; i3 < split.length; i3++) {
                    if (i3 == 1 && parsedProviderImpl.isSyncable()) {
                        ParsedProviderImpl parsedProviderImpl2 =
                                new ParsedProviderImpl(parsedProviderImpl);
                        ComponentMutateUtils.setSyncable(parsedProviderImpl2, false);
                        parsedProviderImpl = parsedProviderImpl2;
                    }
                    if (this.mProvidersByAuthority.containsKey(split[i3])) {
                        ParsedProvider parsedProvider =
                                (ParsedProvider) this.mProvidersByAuthority.get(split[i3]);
                        ComponentName componentName =
                                (parsedProvider == null
                                                || parsedProvider.getComponentName() == null)
                                        ? null
                                        : parsedProvider.getComponentName();
                        String packageName =
                                componentName != null ? componentName.getPackageName() : "?";
                        StringBuilder sb = new StringBuilder("Skipping provider name ");
                        sb.append(split[i3]);
                        sb.append(" (in package ");
                        sb.append(androidPackage.getPackageName());
                        sb.append("): name already used by ");
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                sb, packageName, "PackageManager");
                    } else {
                        this.mProvidersByAuthority.put(split[i3], parsedProviderImpl);
                        if (parsedProviderImpl.getAuthority() == null) {
                            ComponentMutateUtils.setAuthority(parsedProviderImpl, split[i3]);
                        } else {
                            ComponentMutateUtils.setAuthority(
                                    parsedProviderImpl,
                                    parsedProviderImpl.getAuthority() + ";" + split[i3]);
                        }
                    }
                }
            }
        }
    }

    public final void addServicesLocked(Computer computer, AndroidPackage androidPackage) {
        int size = ArrayUtils.size(androidPackage.getServices());
        for (int i = 0; i < size; i++) {
            ParsedService parsedService = (ParsedService) androidPackage.getServices().get(i);
            ServiceIntentResolver serviceIntentResolver = this.mServices;
            ((HashMap) serviceIntentResolver.mServices)
                    .put(parsedService.getComponentName(), parsedService);
            int size2 = parsedService.getIntents().size();
            for (int i2 = 0; i2 < size2; i2++) {
                ParsedIntentInfo parsedIntentInfo =
                        (ParsedIntentInfo) parsedService.getIntents().get(i2);
                if (!parsedIntentInfo.getIntentFilter().debugCheck()) {
                    Log.w("PackageManager", "==> For Service " + parsedService.getName());
                }
                serviceIntentResolver.addFilter(
                        (PackageDataSnapshot) computer,
                        Pair.create(parsedService, parsedIntentInfo));
            }
        }
    }

    public final void assertProvidersNotDefined(AndroidPackage androidPackage) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                int size = ArrayUtils.size(androidPackage.getProviders());
                for (int i = 0; i < size; i++) {
                    ParsedProvider parsedProvider =
                            (ParsedProvider) androidPackage.getProviders().get(i);
                    if (parsedProvider.getAuthority() != null) {
                        String[] split = parsedProvider.getAuthority().split(";");
                        for (int i2 = 0; i2 < split.length; i2++) {
                            if (this.mProvidersByAuthority.containsKey(split[i2])) {
                                ParsedProvider parsedProvider2 =
                                        (ParsedProvider) this.mProvidersByAuthority.get(split[i2]);
                                String packageName =
                                        (parsedProvider2 == null
                                                        || parsedProvider2.getComponentName()
                                                                == null)
                                                ? "?"
                                                : parsedProvider2
                                                        .getComponentName()
                                                        .getPackageName();
                                if (!packageName.equals(androidPackage.getPackageName())) {
                                    throw new PackageManagerException(
                                            -13,
                                            "Can't install because provider name "
                                                    + split[i2]
                                                    + " (in package "
                                                    + androidPackage.getPackageName()
                                                    + ") is already used by "
                                                    + packageName);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void fixProtectedFilterPriorities(String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                if (this.mDeferProtectedFilters) {
                    this.mDeferProtectedFilters = false;
                    List list = this.mProtectedFilters;
                    if (list != null && ((ArrayList) list).size() != 0) {
                        List list2 = this.mProtectedFilters;
                        this.mProtectedFilters = null;
                        ArrayList arrayList = (ArrayList) list2;
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            Pair pair = (Pair) arrayList.get(size);
                            ParsedMainComponent parsedMainComponent =
                                    (ParsedMainComponent) pair.first;
                            IntentFilter intentFilter =
                                    ((ParsedIntentInfo) pair.second).getIntentFilter();
                            String packageName = parsedMainComponent.getPackageName();
                            parsedMainComponent.getClassName();
                            if (!packageName.equals(str)) {
                                intentFilter.setPriority(0);
                            }
                        }
                        dispatchChange(this);
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    }
                }
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public final void removeAllComponentsLocked(AndroidPackage androidPackage) {
        int size = ArrayUtils.size(androidPackage.getActivities());
        for (int i = 0; i < size; i++) {
            this.mActivities.removeActivity((ParsedActivity) androidPackage.getActivities().get(i));
        }
        int size2 = ArrayUtils.size(androidPackage.getProviders());
        for (int i2 = 0; i2 < size2; i2++) {
            ParsedProvider parsedProvider = (ParsedProvider) androidPackage.getProviders().get(i2);
            ServiceIntentResolver serviceIntentResolver = this.mProviders;
            ((ArrayMap) serviceIntentResolver.mServices).remove(parsedProvider.getComponentName());
            int size3 = parsedProvider.getIntents().size();
            for (int i3 = 0; i3 < size3; i3++) {
                ParsedIntentInfo parsedIntentInfo =
                        (ParsedIntentInfo) parsedProvider.getIntents().get(i3);
                parsedIntentInfo.getIntentFilter();
                serviceIntentResolver.removeFilter(Pair.create(parsedProvider, parsedIntentInfo));
            }
            if (parsedProvider.getAuthority() != null) {
                String[] split = parsedProvider.getAuthority().split(";");
                for (int i4 = 0; i4 < split.length; i4++) {
                    if (this.mProvidersByAuthority.get(split[i4]) == parsedProvider) {
                        this.mProvidersByAuthority.remove(split[i4]);
                    }
                }
            }
        }
        int size4 = ArrayUtils.size(androidPackage.getReceivers());
        for (int i5 = 0; i5 < size4; i5++) {
            this.mReceivers.removeActivity((ParsedActivity) androidPackage.getReceivers().get(i5));
        }
        int size5 = ArrayUtils.size(androidPackage.getServices());
        for (int i6 = 0; i6 < size5; i6++) {
            ParsedService parsedService = (ParsedService) androidPackage.getServices().get(i6);
            ServiceIntentResolver serviceIntentResolver2 = this.mServices;
            ((HashMap) serviceIntentResolver2.mServices).remove(parsedService.getComponentName());
            int size6 = parsedService.getIntents().size();
            for (int i7 = 0; i7 < size6; i7++) {
                ParsedIntentInfo parsedIntentInfo2 =
                        (ParsedIntentInfo) parsedService.getIntents().get(i7);
                parsedIntentInfo2.getIntentFilter();
                serviceIntentResolver2.removeFilter(Pair.create(parsedService, parsedIntentInfo2));
            }
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (ComponentResolverApi) snapshot();
    }
}
