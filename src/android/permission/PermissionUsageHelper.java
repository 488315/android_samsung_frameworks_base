package android.permission;

import android.Manifest;
import android.app.AppOpsManager;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.Attribution;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.icu.text.ListFormatter;
import android.location.LocationManager;
import android.os.Process;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.zt.internal.KnoxZtInternalConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class PermissionUsageHelper implements AppOpsManager.OnOpActiveChangedListener, AppOpsManager.OnOpStartedListener {
    private static final long DEFAULT_RECENT_TIME_MS = 15000;
    private static final long DEFAULT_RUNNING_TIME_MS = 5000;
    private static final String LOG_TAG = "android.permission.PermissionUsageHelper";
    private static final String PROPERTY_CAMERA_MIC_ICONS_ENABLED = "camera_mic_icons_enabled";
    private static final String PROPERTY_LOCATION_INDICATORS_ENABLED = "location_indicators_enabled";
    private static final String RECENT_ACCESS_TIME_MS = "recent_access_time_ms";
    private static final String RUNNING_ACCESS_TIME_MS = "running_access_time_ms";
    private static final String SYSTEM_PKG = "android";
    private AppOpsManager mAppOpsManager;
    private final ArrayMap<Integer, ArrayList<AccessChainLink>> mAttributionChains = new ArrayMap<>();
    private Context mContext;
    private PackageManager mPkgManager;
    private ArrayMap<UserHandle, Context> mUserContexts;
    private VirtualDeviceManager mVirtualDeviceManager;
    private static final List<String> LOCATION_OPS = List.of(AppOpsManager.OPSTR_COARSE_LOCATION, AppOpsManager.OPSTR_FINE_LOCATION);
    private static final List<String> MIC_OPS = List.of(AppOpsManager.OPSTR_PHONE_CALL_MICROPHONE, AppOpsManager.OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO, AppOpsManager.OPSTR_RECORD_AUDIO);
    private static final List<String> CAMERA_OPS = List.of(AppOpsManager.OPSTR_PHONE_CALL_CAMERA, AppOpsManager.OPSTR_CAMERA);

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public void onOpActiveChanged(String str, int i, String str2, boolean z) {
    }

    @Override // android.app.AppOpsManager.OnOpStartedListener
    public void onOpStarted(int i, int i2, String str, String str2, int i3, int i4) {
    }

    private static boolean shouldShowIndicators() {
        return DeviceConfig.getBoolean(KnoxZtInternalConst.Event.LogKeys.PRIVACY, "camera_mic_icons_enabled", true);
    }

    private static boolean shouldShowLocationIndicator() {
        return DeviceConfig.getBoolean(KnoxZtInternalConst.Event.LogKeys.PRIVACY, "location_indicators_enabled", false) || isSupportSamsungLocationChip();
    }

    private static boolean isSupportSamsungLocationChip() {
        return "US".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO", ""));
    }

    private static long getRecentThreshold(Long l) {
        return l.longValue() - DeviceConfig.getLong(KnoxZtInternalConst.Event.LogKeys.PRIVACY, RECENT_ACCESS_TIME_MS, DEFAULT_RECENT_TIME_MS);
    }

    private static long getRunningThreshold(Long l) {
        return l.longValue() - DeviceConfig.getLong(KnoxZtInternalConst.Event.LogKeys.PRIVACY, RUNNING_ACCESS_TIME_MS, 5000L);
    }

    private static String getGroupForOp(String str) {
        str.hashCode();
        switch (str) {
            case "android:coarse_location":
            case "android:fine_location":
                return Manifest.permission_group.LOCATION;
            case "android:phone_call_microphone":
            case "android:phone_call_camera":
                return str;
            case "android:receive_ambient_trigger_audio":
            case "android:record_audio":
                return Manifest.permission_group.MICROPHONE;
            case "android:camera":
                return Manifest.permission_group.CAMERA;
            default:
                throw new IllegalArgumentException("Unknown app op: " + str);
        }
    }

    public PermissionUsageHelper(Context context) {
        this.mContext = context;
        this.mPkgManager = context.getPackageManager();
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mVirtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class);
        ArrayMap<UserHandle, Context> arrayMap = new ArrayMap<>();
        this.mUserContexts = arrayMap;
        arrayMap.put(Process.myUserHandle(), this.mContext);
        this.mAppOpsManager.startWatchingActive(new String[]{AppOpsManager.OPSTR_CAMERA, AppOpsManager.OPSTR_RECORD_AUDIO}, context.getMainExecutor(), this);
        this.mAppOpsManager.startWatchingStarted(new int[]{26, 27}, this);
    }

    private Context getUserContext(UserHandle userHandle) {
        if (!this.mUserContexts.containsKey(userHandle)) {
            this.mUserContexts.put(userHandle, this.mContext.createContextAsUser(userHandle, 0));
        }
        return this.mUserContexts.get(userHandle);
    }

    public void tearDown() {
        this.mAppOpsManager.stopWatchingActive(this);
        this.mAppOpsManager.stopWatchingStarted(this);
    }

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public void onOpActiveChanged(String str, int i, String str2, String str3, boolean z, int i2, int i3) {
        if (z) {
            return;
        }
        synchronized (this.mAttributionChains) {
            this.mAttributionChains.remove(Integer.valueOf(i3));
            int size = this.mAttributionChains.size();
            ArrayList arrayList = new ArrayList();
            for (int i4 = 0; i4 < size; i4++) {
                Integer keyAt = this.mAttributionChains.keyAt(i4);
                keyAt.intValue();
                ArrayList<AccessChainLink> valueAt = this.mAttributionChains.valueAt(i4);
                int size2 = valueAt.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size2) {
                        break;
                    }
                    if (valueAt.get(i5).packageAndOpEquals(str, str2, str3, i)) {
                        arrayList.add(keyAt);
                        break;
                    }
                    i5++;
                }
            }
            this.mAttributionChains.removeAll(arrayList);
        }
    }

    @Override // android.app.AppOpsManager.OnOpStartedListener
    public void onOpStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7) {
        if (i5 == 0 || i7 == -1 || i6 == 0 || (i6 & 8) == 0) {
            return;
        }
        synchronized (this.mAttributionChains) {
            addLinkToChainIfNotPresentLocked(AppOpsManager.opToPublicName(i), str, i2, str2, i6, i7);
        }
    }

    private void addLinkToChainIfNotPresentLocked(String str, String str2, int i, String str3, int i2, int i3) {
        ArrayList<AccessChainLink> computeIfAbsent = this.mAttributionChains.computeIfAbsent(Integer.valueOf(i3), new Function() { // from class: android.permission.PermissionUsageHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PermissionUsageHelper.lambda$addLinkToChainIfNotPresentLocked$0((Integer) obj);
            }
        });
        AccessChainLink accessChainLink = new AccessChainLink(str, str2, str3, i, i2);
        if (computeIfAbsent.contains(accessChainLink)) {
            return;
        }
        int size = computeIfAbsent.size();
        if (size != 0 && !accessChainLink.isEnd()) {
            int i4 = size - 1;
            if (computeIfAbsent.get(i4).isEnd()) {
                if (accessChainLink.isStart()) {
                    computeIfAbsent.add(0, accessChainLink);
                    return;
                } else {
                    if (computeIfAbsent.get(computeIfAbsent.size() - 1).isEnd()) {
                        computeIfAbsent.add(i4, accessChainLink);
                        return;
                    }
                    return;
                }
            }
        }
        computeIfAbsent.add(accessChainLink);
    }

    static /* synthetic */ ArrayList lambda$addLinkToChainIfNotPresentLocked$0(Integer num) {
        return new ArrayList();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00d4 A[LOOP:1: B:20:0x00ce->B:22:0x00d4, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<android.permission.PermissionGroupUsage> getOpUsageDataByDevice(boolean r25, java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.permission.PermissionUsageHelper.getOpUsageDataByDevice(boolean, java.lang.String):java.util.List");
    }

    public List<PermissionGroupUsage> getOpUsageDataForAllDevices(boolean z) {
        ArrayList arrayList = new ArrayList();
        VirtualDeviceManager virtualDeviceManager = this.mVirtualDeviceManager;
        if (virtualDeviceManager != null) {
            List<VirtualDevice> virtualDevices = virtualDeviceManager.getVirtualDevices();
            ArraySet arraySet = new ArraySet();
            for (int i = 0; i < virtualDevices.size(); i++) {
                arraySet.add(virtualDevices.get(i).getPersistentDeviceId());
            }
            arraySet.add(VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                arrayList.addAll(getOpUsageDataByDevice(z, (String) arraySet.valueAt(i2)));
            }
        }
        return arrayList;
    }

    private void updateSubattributionLabelsMap(List<OpUsage> list, ArrayMap<String, Map<String, String>> arrayMap) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (OpUsage opUsage : list) {
            if (opUsage.attributionTag != null && !arrayMap.containsKey(opUsage.packageName)) {
                arrayMap.put(opUsage.packageName, getSubattributionLabelsForPackage(opUsage.packageName, opUsage.uid));
            }
        }
    }

    private ArrayMap<String, String> getSubattributionLabelsForPackage(String str, int i) {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        try {
            if (isSubattributionSupported(str, i)) {
                Context userContext = getUserContext(userHandleForUid);
                PackageInfo packageInfo = userContext.getPackageManager().getPackageInfo(str, PackageManager.PackageInfoFlags.of(2147487744L));
                Context createPackageContext = userContext.createPackageContext(packageInfo.packageName, 0);
                for (Attribution attribution : packageInfo.attributions) {
                    try {
                        arrayMap.put(attribution.getTag(), createPackageContext.getString(attribution.getLabel()));
                    } catch (Resources.NotFoundException unused) {
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException unused2) {
        }
        return arrayMap;
    }

    private boolean isSubattributionSupported(String str, int i) {
        ApplicationInfo applicationInfoAsUser;
        try {
            if (isLocationProvider(str) && (applicationInfoAsUser = getUserContext(UserHandle.getUserHandleForUid(i)).getPackageManager().getApplicationInfoAsUser(str, PackageManager.ApplicationInfoFlags.of(0L), UserHandle.getUserId(i))) != null) {
                return applicationInfoAsUser.areAttributionsUserVisible();
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    private boolean isLocationProvider(String str) {
        return ((LocationManager) Objects.requireNonNull((LocationManager) this.mContext.getSystemService(LocationManager.class))).isProviderPackage(str);
    }

    private Map<String, List<OpUsage>> getOpUsagesByDevice(List<String> list, String str) {
        List<AppOpsManager.PackageOps> packagesForOps;
        int i;
        int i2;
        try {
            if (Flags.deviceAwarePermissionApisEnabled()) {
                packagesForOps = this.mAppOpsManager.getPackagesForOps((String[]) list.toArray(new String[list.size()]), str);
            } else {
                if (!Objects.equals(str, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT)) {
                    Slog.w(LOG_TAG, "device_aware_permission_apis_enabled flag not enabled when deviceId is not default");
                    return Collections.EMPTY_MAP;
                }
                packagesForOps = this.mAppOpsManager.getPackagesForOps((String[]) list.toArray(new String[list.size()]));
            }
            long currentTimeMillis = System.currentTimeMillis();
            long recentThreshold = getRecentThreshold(Long.valueOf(currentTimeMillis));
            long runningThreshold = getRunningThreshold(Long.valueOf(currentTimeMillis));
            ArrayMap arrayMap = new ArrayMap();
            int size = packagesForOps.size();
            for (int i3 = 0; i3 < size; i3++) {
                AppOpsManager.PackageOps packageOps = packagesForOps.get(i3);
                int uid = packageOps.getUid();
                UserHandle.getUserHandleForUid(uid);
                String packageName = packageOps.getPackageName();
                int size2 = packageOps.getOps().size();
                int i4 = 0;
                while (i4 < size2) {
                    AppOpsManager.OpEntry opEntry = packageOps.getOps().get(i4);
                    String opStr = opEntry.getOpStr();
                    List<AppOpsManager.PackageOps> list2 = packagesForOps;
                    ArrayList arrayList = new ArrayList(opEntry.getAttributedOpEntries().keySet());
                    int size3 = opEntry.getAttributedOpEntries().size();
                    long j = currentTimeMillis;
                    int i5 = 0;
                    while (i5 < size3) {
                        String str2 = (String) arrayList.get(i5);
                        int i6 = size3;
                        AppOpsManager.AttributedOpEntry attributedOpEntry = opEntry.getAttributedOpEntries().get(str2);
                        int i7 = i5;
                        long lastAccessTime = attributedOpEntry.getLastAccessTime(13);
                        if (attributedOpEntry.isRunning()) {
                            lastAccessTime = j;
                        }
                        if (lastAccessTime >= recentThreshold || attributedOpEntry.isRunning()) {
                            boolean z = attributedOpEntry.isRunning() || lastAccessTime >= runningThreshold;
                            AppOpsManager.OpEventProxyInfo lastProxyInfo = attributedOpEntry.getLastProxyInfo(13);
                            OpUsage opUsage = (lastProxyInfo == null || lastProxyInfo.getPackageName() == null) ? null : new OpUsage(lastProxyInfo.getPackageName(), lastProxyInfo.getAttributionTag(), opStr, lastProxyInfo.getUid(), lastAccessTime, z, null);
                            String groupForOp = getGroupForOp(opStr);
                            int i8 = size2;
                            int i9 = i4;
                            OpUsage opUsage2 = new OpUsage(packageName, str2, opStr, uid, lastAccessTime, z, opUsage);
                            Integer valueOf = Integer.valueOf(opUsage2.getPackageIdHash());
                            if (!arrayMap.containsKey(groupForOp)) {
                                i = i8;
                                ArrayMap arrayMap2 = new ArrayMap();
                                arrayMap2.put(valueOf, opUsage2);
                                arrayMap.put(groupForOp, arrayMap2);
                            } else {
                                i = i8;
                                Map map = (Map) arrayMap.get(groupForOp);
                                if (map.containsKey(valueOf)) {
                                    i2 = i9;
                                    if (opUsage2.lastAccessTime > ((OpUsage) map.get(valueOf)).lastAccessTime) {
                                        map.put(valueOf, opUsage2);
                                    }
                                } else {
                                    map.put(valueOf, opUsage2);
                                }
                            }
                            i2 = i9;
                        } else {
                            i = size2;
                            i2 = i4;
                        }
                        i5 = i7 + 1;
                        size3 = i6;
                        size2 = i;
                        i4 = i2;
                    }
                    i4++;
                    packagesForOps = list2;
                    currentTimeMillis = j;
                }
            }
            ArrayMap arrayMap3 = new ArrayMap();
            ArrayList arrayList2 = new ArrayList(arrayMap.keySet());
            for (int i10 = 0; i10 < arrayList2.size(); i10++) {
                String str3 = (String) arrayList2.get(i10);
                arrayMap3.put(str3, new ArrayList(((Map) arrayMap.get(str3)).values()));
            }
            return arrayMap3;
        } catch (NullPointerException unused) {
            return Collections.EMPTY_MAP;
        }
    }

    private CharSequence formatLabelList(List<CharSequence> list) {
        return ListFormatter.getInstance().format(list);
    }

    private ArrayMap<OpUsage, CharSequence> getUniqueUsagesWithLabels(String str, List<OpUsage> list) {
        int i;
        String charSequence;
        int i2;
        ArrayMap<OpUsage, CharSequence> arrayMap = new ArrayMap<>();
        if (list != null && !list.isEmpty()) {
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayMap arrayMap3 = new ArrayMap();
            ArraySet arraySet = new ArraySet();
            ArrayMap arrayMap4 = new ArrayMap();
            ArrayMap arrayMap5 = new ArrayMap();
            for (int i3 = 0; i3 < list.size(); i3++) {
                OpUsage opUsage = list.get(i3);
                arrayMap2.put(Integer.valueOf(opUsage.getPackageIdHash()), opUsage);
                if (opUsage.proxy != null) {
                    arrayMap5.put(Integer.valueOf(opUsage.proxy.getPackageIdHash()), opUsage);
                }
            }
            while (i < list.size()) {
                OpUsage opUsage2 = list.get(i);
                if (opUsage2 != null) {
                    if (!arrayMap5.containsKey(Integer.valueOf(opUsage2.getPackageIdHash())) && opUsage2.proxy != null && !Manifest.permission_group.MICROPHONE.equals(str)) {
                        arrayMap4.put(opUsage2, new ArrayList());
                        arraySet.add(Integer.valueOf(opUsage2.getPackageIdHash()));
                    }
                    int packageIdHash = opUsage2.getPackageIdHash();
                    OpUsage opUsage3 = (OpUsage) arrayMap3.get(Integer.valueOf(packageIdHash));
                    if (shouldShowPackage(opUsage2.packageName)) {
                        if (opUsage3 != null) {
                            i2 = i;
                            i = opUsage2.lastAccessTime <= opUsage3.lastAccessTime ? i2 + 1 : 0;
                        } else {
                            i2 = i;
                        }
                        arrayMap3.put(Integer.valueOf(packageIdHash), opUsage2);
                    }
                }
                i2 = i;
            }
            for (int i4 = 0; i4 < arrayMap4.size(); i4++) {
                OpUsage opUsage4 = (OpUsage) arrayMap4.keyAt(i4);
                arrayMap3.remove(Integer.valueOf(opUsage4.getPackageIdHash()));
                OpUsage opUsage5 = (OpUsage) arrayMap4.keyAt(i4);
                ArrayList arrayList = (ArrayList) arrayMap4.get(opUsage5);
                if (opUsage5 != null && arrayList != null) {
                    int size = arrayMap2.size();
                    int i5 = 0;
                    while (opUsage5.proxy != null) {
                        if (arrayMap2.containsKey(Integer.valueOf(opUsage5.proxy.getPackageIdHash()))) {
                            opUsage5 = (OpUsage) arrayMap2.get(Integer.valueOf(opUsage5.proxy.getPackageIdHash()));
                        } else {
                            opUsage5 = opUsage5.proxy;
                            if (!shouldShowPackage(opUsage5.packageName)) {
                                break;
                            }
                            size++;
                        }
                        if (opUsage5 == null || i5 == size || opUsage5.getPackageIdHash() == opUsage4.getPackageIdHash()) {
                            break;
                        }
                        arraySet.add(Integer.valueOf(opUsage5.getPackageIdHash()));
                        if (!opUsage5.packageName.equals(opUsage4.packageName) && shouldShowPackage(opUsage5.packageName)) {
                            try {
                                PackageManager packageManager = getUserContext(opUsage5.getUser()).getPackageManager();
                                CharSequence loadLabel = packageManager.getApplicationInfo(opUsage5.packageName, 0).loadLabel(packageManager);
                                if (!arrayList.contains(loadLabel)) {
                                    arrayList.add(loadLabel);
                                }
                            } catch (PackageManager.NameNotFoundException unused) {
                            }
                        }
                        i5++;
                    }
                    if (!Manifest.permission_group.MICROPHONE.equals(str)) {
                        arrayMap.put(opUsage4, arrayList.isEmpty() ? null : formatLabelList(arrayList));
                    }
                }
            }
            synchronized (this.mAttributionChains) {
                for (int i6 = 0; i6 < this.mAttributionChains.size(); i6++) {
                    ArrayList<AccessChainLink> valueAt = this.mAttributionChains.valueAt(i6);
                    int size2 = valueAt.size() - 1;
                    if (!valueAt.isEmpty() && valueAt.get(size2).isEnd()) {
                        if (valueAt.get(0).isStart()) {
                            if (str.equals(getGroupForOp(valueAt.get(0).usage.op)) && Manifest.permission_group.MICROPHONE.equals(str)) {
                                Iterator<AccessChainLink> it = valueAt.iterator();
                                while (it.hasNext()) {
                                    arraySet.add(Integer.valueOf(it.next().usage.getPackageIdHash()));
                                }
                                AccessChainLink accessChainLink = valueAt.get(0);
                                AccessChainLink accessChainLink2 = valueAt.get(size2);
                                while (size2 > 0 && !shouldShowPackage(accessChainLink2.usage.packageName)) {
                                    size2--;
                                    accessChainLink2 = valueAt.get(size2);
                                }
                                if (!accessChainLink2.usage.packageName.equals(accessChainLink.usage.packageName)) {
                                    try {
                                        PackageManager packageManager2 = getUserContext(accessChainLink2.usage.getUser()).getPackageManager();
                                        try {
                                            charSequence = packageManager2.getApplicationInfo(accessChainLink2.usage.packageName, 0).loadLabel(packageManager2).toString();
                                        } catch (PackageManager.NameNotFoundException unused2) {
                                        }
                                    } catch (PackageManager.NameNotFoundException unused3) {
                                    }
                                    arrayMap.put(accessChainLink.usage, charSequence);
                                }
                                charSequence = null;
                                arrayMap.put(accessChainLink.usage, charSequence);
                            }
                        }
                    }
                }
            }
            for (Integer num : arrayMap3.keySet()) {
                num.intValue();
                if (!arraySet.contains(num)) {
                    arrayMap.put((OpUsage) arrayMap3.get(num), null);
                }
            }
        }
        return arrayMap;
    }

    private boolean shouldShowPackage(String str) {
        return PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, str);
    }

    private static class OpUsage {
        public final String attributionTag;
        public final boolean isRunning;
        public final long lastAccessTime;
        public final String op;
        public final String packageName;
        public final OpUsage proxy;
        public final int uid;

        OpUsage(String str, String str2, String str3, int i, long j, boolean z, OpUsage opUsage) {
            this.packageName = str;
            this.attributionTag = str2;
            this.op = str3;
            this.uid = i;
            this.lastAccessTime = j;
            this.isRunning = z;
            this.proxy = opUsage;
        }

        public UserHandle getUser() {
            return UserHandle.getUserHandleForUid(this.uid);
        }

        public int getPackageIdHash() {
            return Objects.hash(this.packageName, Integer.valueOf(this.uid));
        }

        public int hashCode() {
            return Objects.hash(this.packageName, this.attributionTag, this.op, Integer.valueOf(this.uid), Long.valueOf(this.lastAccessTime), Boolean.valueOf(this.isRunning));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof OpUsage)) {
                return false;
            }
            OpUsage opUsage = (OpUsage) obj;
            return Objects.equals(this.packageName, opUsage.packageName) && Objects.equals(this.attributionTag, opUsage.attributionTag) && Objects.equals(this.op, opUsage.op) && this.uid == opUsage.uid && this.lastAccessTime == opUsage.lastAccessTime && this.isRunning == opUsage.isRunning;
        }
    }

    private static class AccessChainLink {
        public final int flags;
        public final OpUsage usage;

        AccessChainLink(String str, String str2, String str3, int i, int i2) {
            this.usage = new OpUsage(str2, str3, str, i, System.currentTimeMillis(), true, null);
            this.flags = i2;
        }

        public boolean isEnd() {
            return (this.flags & 1) != 0;
        }

        public boolean isStart() {
            return (this.flags & 4) != 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof AccessChainLink)) {
                return false;
            }
            AccessChainLink accessChainLink = (AccessChainLink) obj;
            return accessChainLink.flags == this.flags && packageAndOpEquals(accessChainLink.usage.op, accessChainLink.usage.packageName, accessChainLink.usage.attributionTag, accessChainLink.usage.uid);
        }

        public boolean packageAndOpEquals(String str, String str2, String str3, int i) {
            return Objects.equals(str, this.usage.op) && Objects.equals(str2, this.usage.packageName) && Objects.equals(str3, this.usage.attributionTag) && i == this.usage.uid;
        }
    }
}
