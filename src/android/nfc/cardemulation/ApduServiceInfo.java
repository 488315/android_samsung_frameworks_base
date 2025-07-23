package android.nfc.cardemulation;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@SystemApi
/* loaded from: classes3.dex */
public final class ApduServiceInfo implements Parcelable {

    @SystemApi
    public static final String PROPERTY_WALLET_PREFERRED_BANNER_AND_LABEL = "android.nfc.cardemulation.PROPERTY_WALLET_PREFERRED_BANNER_AND_LABEL";
    private static final String TAG = "ApduServiceInfo";
    private final Map<String, Boolean> mAutoTransact;
    private final Map<Pattern, Boolean> mAutoTransactPatterns;
    private final int mBannerResourceId;
    private boolean mCategoryOtherServiceEnabled;
    private final String mDescription;
    private final HashMap<String, AidGroup> mDynamicAidGroups;
    private String mOffHostName;
    private final boolean mOnHost;
    private final boolean mRequiresDeviceScreenOn;
    private final boolean mRequiresDeviceUnlock;
    private final ResolveInfo mService;
    private final String mSettingsActivityName;
    private boolean mShouldDefaultToObserveMode;
    private final HashMap<String, AidGroup> mStaticAidGroups;
    private final String mStaticOffHostName;
    private final int mUid;
    private boolean mWantsRoleHolderPriority;
    private static final Pattern PLPF_PATTERN = Pattern.compile("[0-9A-Fa-f]{2,}[0-9A-Fa-f,\\?,\\*\\.]*");
    private static final Pattern PLF_PATTERN = Pattern.compile("[0-9A-Fa-f]{2,}");
    public static final Parcelable.Creator<ApduServiceInfo> CREATOR = new Parcelable.Creator<ApduServiceInfo>() { // from class: android.nfc.cardemulation.ApduServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduServiceInfo createFromParcel(Parcel parcel) {
            boolean z;
            ResolveInfo createFromParcel = ResolveInfo.CREATOR.createFromParcel(parcel);
            String readString = parcel.readString();
            boolean z2 = false;
            if (parcel.readInt() != 0) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            ArrayList arrayList = new ArrayList();
            if (parcel.readInt() > 0) {
                parcel.readTypedList(arrayList, AidGroup.CREATOR);
            }
            ArrayList arrayList2 = new ArrayList();
            if (parcel.readInt() > 0) {
                parcel.readTypedList(arrayList2, AidGroup.CREATOR);
            }
            boolean z3 = parcel.readInt() != 0 ? true : z;
            boolean z4 = parcel.readInt() != 0 ? true : z;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString4 = parcel.readString();
            boolean z5 = parcel.readInt() != 0 ? true : z;
            int readInt3 = parcel.readInt();
            HashMap hashMap = new HashMap(readInt3);
            parcel.readMap(hashMap, getClass().getClassLoader(), String.class, Boolean.class);
            parcel.readInt();
            HashMap hashMap2 = new HashMap(readInt3);
            parcel.readMap(hashMap2, getClass().getClassLoader(), Pattern.class, Boolean.class);
            return new ApduServiceInfo(createFromParcel, z2, readString, arrayList, arrayList2, z3, z4, readInt, readInt2, readString4, readString2, readString3, z5, hashMap, hashMap2);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduServiceInfo[] newArray(int i) {
            return new ApduServiceInfo[i];
        }
    };
    private static final Pattern AID_PATTERN = Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public ApduServiceInfo(ResolveInfo resolveInfo, boolean z, String str, List<AidGroup> list, List<AidGroup> list2, boolean z2, int i, int i2, String str2, String str3, String str4) {
        this(resolveInfo, z, str, list, list2, z2, i, i2, str2, str3, str4, false);
    }

    public ApduServiceInfo(ResolveInfo resolveInfo, boolean z, String str, List<AidGroup> list, List<AidGroup> list2, boolean z2, int i, int i2, String str2, String str3, String str4, boolean z3) {
        this(resolveInfo, z, str, list, list2, z2, z, i, i2, str2, str3, str4, z3);
    }

    public ApduServiceInfo(ResolveInfo resolveInfo, boolean z, String str, List<AidGroup> list, List<AidGroup> list2, boolean z2, boolean z3, int i, int i2, String str2, String str3, String str4, boolean z4) {
        this(resolveInfo, z, str, list, list2, z2, z3, i, i2, str2, str3, str4, z4, new HashMap(), new TreeMap(Comparator.comparing(new Function() { // from class: android.nfc.cardemulation.ApduServiceInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Pattern) obj).toString();
            }
        })));
    }

    public ApduServiceInfo(ResolveInfo resolveInfo, boolean z, String str, List<AidGroup> list, List<AidGroup> list2, boolean z2, boolean z3, int i, int i2, String str2, String str3, String str4, boolean z4, Map<String, Boolean> map, Map<Pattern, Boolean> map2) {
        this.mService = resolveInfo;
        this.mDescription = str;
        this.mStaticAidGroups = new HashMap<>();
        this.mDynamicAidGroups = new HashMap<>();
        this.mAutoTransact = map;
        this.mAutoTransactPatterns = map2;
        this.mOffHostName = str3;
        this.mStaticOffHostName = str4;
        this.mOnHost = z;
        this.mRequiresDeviceUnlock = z2;
        this.mRequiresDeviceScreenOn = z3;
        for (AidGroup aidGroup : list) {
            this.mStaticAidGroups.put(aidGroup.getCategory(), aidGroup);
        }
        for (AidGroup aidGroup2 : list2) {
            this.mDynamicAidGroups.put(aidGroup2.getCategory(), aidGroup2);
        }
        this.mBannerResourceId = i;
        this.mUid = i2;
        this.mSettingsActivityName = str2;
        this.mCategoryOtherServiceEnabled = z4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x005b, code lost:
    
        if ("offhost-apdu-service".equals(r7) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with <offhost-apdu-service> tag");
     */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x03da: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:183:0x03da */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x03dc: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:181:0x03dc */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0405  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ApduServiceInfo(android.content.pm.PackageManager r18, android.content.pm.ResolveInfo r19, boolean r20) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1033
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.cardemulation.ApduServiceInfo.<init>(android.content.pm.PackageManager, android.content.pm.ResolveInfo, boolean):void");
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getOffHostSecureElement() {
        return this.mOffHostName;
    }

    public List<String> getAids() {
        ArrayList arrayList = new ArrayList();
        Iterator<AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getAids());
        }
        return arrayList;
    }

    public List<String> getPollingLoopFilters() {
        return new ArrayList(this.mAutoTransact.keySet());
    }

    public boolean getShouldAutoTransact(final String str) {
        if (this.mAutoTransact.getOrDefault(str.toUpperCase(Locale.ROOT), false).booleanValue()) {
            return true;
        }
        final boolean z = str.contains("?") || str.contains("*");
        List<Pattern> list = this.mAutoTransactPatterns.keySet().stream().filter(new Predicate() { // from class: android.nfc.cardemulation.ApduServiceInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ApduServiceInfo.lambda$getShouldAutoTransact$0(z, str, (Pattern) obj);
            }
        }).toList();
        if (list != null && list.size() != 0) {
            Iterator<Pattern> it = list.iterator();
            while (it.hasNext()) {
                if (this.mAutoTransactPatterns.get(it.next()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    static /* synthetic */ boolean lambda$getShouldAutoTransact$0(boolean z, String str, Pattern pattern) {
        return z ? pattern.toString().equals(str) : pattern.matcher(str).matches();
    }

    public List<Pattern> getPollingLoopPatternFilters() {
        return new ArrayList(this.mAutoTransactPatterns.keySet());
    }

    public List<String> getPrefixAids() {
        ArrayList arrayList = new ArrayList();
        Iterator<AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            for (String str : it.next().getAids()) {
                if (str.endsWith("*")) {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    public List<String> getSubsetAids() {
        ArrayList arrayList = new ArrayList();
        Iterator<AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            for (String str : it.next().getAids()) {
                if (str.endsWith("#")) {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    public AidGroup getDynamicAidGroupForCategory(String str) {
        return this.mDynamicAidGroups.get(str);
    }

    public boolean removeDynamicAidGroupForCategory(String str) {
        return this.mDynamicAidGroups.remove(str) != null;
    }

    public List<AidGroup> getAidGroups() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, AidGroup>> it = this.mDynamicAidGroups.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        for (Map.Entry<String, AidGroup> entry : this.mStaticAidGroups.entrySet()) {
            if (!this.mDynamicAidGroups.containsKey(entry.getKey())) {
                arrayList.add(entry.getValue());
            }
        }
        return arrayList;
    }

    public String getCategoryForAid(String str) {
        for (AidGroup aidGroup : getAidGroups()) {
            if (aidGroup.getAids().contains(str.toUpperCase())) {
                return aidGroup.getCategory();
            }
        }
        return null;
    }

    public boolean hasCategory(String str) {
        return this.mStaticAidGroups.containsKey(str) || this.mDynamicAidGroups.containsKey(str);
    }

    public boolean isOnHost() {
        return this.mOnHost;
    }

    public boolean requiresUnlock() {
        return this.mRequiresDeviceUnlock;
    }

    public boolean requiresScreenOn() {
        return this.mRequiresDeviceScreenOn;
    }

    public boolean shouldDefaultToObserveMode() {
        return this.mShouldDefaultToObserveMode;
    }

    public void setShouldDefaultToObserveMode(boolean z) {
        this.mShouldDefaultToObserveMode = z;
    }

    public boolean wantsRoleHolderPriority() {
        return this.mWantsRoleHolderPriority;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setDynamicAidGroup(AidGroup aidGroup) {
        this.mDynamicAidGroups.put(aidGroup.getCategory(), aidGroup);
    }

    public void addPollingLoopFilter(String str, boolean z) {
        if (!PLF_PATTERN.matcher(str).matches() || str.length() % 2 != 0) {
            throw new IllegalArgumentException("Polling loop filter must contain an even number of characters 0-9 or A-F");
        }
        if (this.mOnHost || z) {
            this.mAutoTransact.put(str.toUpperCase(Locale.ROOT), Boolean.valueOf(z));
        }
    }

    public void removePollingLoopFilter(String str) {
        this.mAutoTransact.remove(str.toUpperCase(Locale.ROOT));
    }

    public void addPollingLoopPatternFilter(String str, boolean z) {
        if (!PLPF_PATTERN.matcher(str).matches()) {
            throw new IllegalArgumentException("Polling loop pattern filter is invalid");
        }
        if (this.mOnHost || z) {
            this.mAutoTransactPatterns.put(Pattern.compile(str.toUpperCase(Locale.ROOT)), Boolean.valueOf(z));
        }
    }

    public void removePollingLoopPatternFilter(String str) {
        this.mAutoTransactPatterns.remove(Pattern.compile(str.toUpperCase(Locale.ROOT)));
    }

    public void setOffHostSecureElement(String str) {
        this.mOffHostName = str;
    }

    public void resetOffHostSecureElement() {
        this.mOffHostName = this.mStaticOffHostName;
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public CharSequence loadAppLabel(PackageManager packageManager) {
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mService.resolvePackageName, 128));
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public Drawable loadBanner(PackageManager packageManager) {
        try {
            return packageManager.getResourcesForApplication(this.mService.serviceInfo.packageName).getDrawable(this.mBannerResourceId);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Could not load banner.");
            return null;
        } catch (Resources.NotFoundException unused2) {
            Log.e(TAG, "Could not load banner.");
            return null;
        }
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ApduService: ");
        sb.append(getComponent());
        sb.append(", UID: " + this.mUid);
        sb.append(", description: " + this.mDescription);
        sb.append(", Static AID Groups: ");
        Iterator<AidGroup> it = this.mStaticAidGroups.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        sb.append(", Dynamic AID Groups: ");
        Iterator<AidGroup> it2 = this.mDynamicAidGroups.values().iterator();
        while (it2.hasNext()) {
            sb.append(it2.next().toString());
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ApduServiceInfo)) {
            return false;
        }
        ApduServiceInfo apduServiceInfo = (ApduServiceInfo) obj;
        return apduServiceInfo.getComponent().equals(getComponent()) && apduServiceInfo.getUid() == getUid();
    }

    public int hashCode() {
        return getComponent().hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mDescription);
        parcel.writeInt(this.mOnHost ? 1 : 0);
        parcel.writeString(this.mOffHostName);
        parcel.writeString(this.mStaticOffHostName);
        parcel.writeInt(this.mStaticAidGroups.size());
        if (this.mStaticAidGroups.size() > 0) {
            parcel.writeTypedList(new ArrayList(this.mStaticAidGroups.values()));
        }
        parcel.writeInt(this.mDynamicAidGroups.size());
        if (this.mDynamicAidGroups.size() > 0) {
            parcel.writeTypedList(new ArrayList(this.mDynamicAidGroups.values()));
        }
        parcel.writeInt(this.mRequiresDeviceUnlock ? 1 : 0);
        parcel.writeInt(this.mRequiresDeviceScreenOn ? 1 : 0);
        parcel.writeInt(this.mBannerResourceId);
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mCategoryOtherServiceEnabled ? 1 : 0);
        parcel.writeInt(this.mAutoTransact.size());
        parcel.writeMap(this.mAutoTransact);
        parcel.writeInt(this.mAutoTransactPatterns.size());
        parcel.writeMap(this.mAutoTransactPatterns);
    }

    public void dump(ParcelFileDescriptor parcelFileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("    " + getComponent() + " (Description: " + getDescription() + ") (UID: " + getUid() + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mOnHost) {
            printWriter.println("    On Host Service");
        } else {
            printWriter.println("    Off-host Service");
            printWriter.println("        Current off-host SE:" + this.mOffHostName + " static off-host SE:" + this.mStaticOffHostName);
        }
        printWriter.println("    Static AID groups:");
        for (AidGroup aidGroup : this.mStaticAidGroups.values()) {
            printWriter.println("        Category: " + aidGroup.getCategory() + "(enabled: " + this.mCategoryOtherServiceEnabled + NavigationBarInflaterView.KEY_CODE_END);
            Iterator<String> it = aidGroup.getAids().iterator();
            while (it.hasNext()) {
                printWriter.println("            AID: " + it.next());
            }
        }
        printWriter.println("    Dynamic AID groups:");
        for (AidGroup aidGroup2 : this.mDynamicAidGroups.values()) {
            printWriter.println("        Category: " + aidGroup2.getCategory() + "(enabled: " + this.mCategoryOtherServiceEnabled + NavigationBarInflaterView.KEY_CODE_END);
            Iterator<String> it2 = aidGroup2.getAids().iterator();
            while (it2.hasNext()) {
                printWriter.println("            AID: " + it2.next());
            }
        }
        printWriter.println("    Settings Activity: " + this.mSettingsActivityName);
        printWriter.println("    Requires Device Unlock: " + this.mRequiresDeviceUnlock);
        printWriter.println("    Requires Device ScreenOn: " + this.mRequiresDeviceScreenOn);
        printWriter.println("    Should Default to Observe Mode: " + this.mShouldDefaultToObserveMode);
        printWriter.println("    Auto-Transact Mapping: " + this.mAutoTransact);
        printWriter.println("    Auto-Transact Patterns: " + this.mAutoTransactPatterns);
    }

    public void setCategoryOtherServiceEnabled(boolean z) {
        this.mCategoryOtherServiceEnabled = z;
    }

    public boolean isCategoryOtherServiceEnabled() {
        return this.mCategoryOtherServiceEnabled;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream) {
        getComponent().dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1138166333442L, getDescription());
        protoOutputStream.write(1133871366147L, this.mOnHost);
        if (!this.mOnHost) {
            protoOutputStream.write(1138166333444L, this.mOffHostName);
            protoOutputStream.write(1138166333445L, this.mStaticOffHostName);
        }
        for (AidGroup aidGroup : this.mStaticAidGroups.values()) {
            long start = protoOutputStream.start(2246267895814L);
            aidGroup.dump(protoOutputStream);
            protoOutputStream.end(start);
        }
        for (AidGroup aidGroup2 : this.mDynamicAidGroups.values()) {
            long start2 = protoOutputStream.start(2246267895814L);
            aidGroup2.dump(protoOutputStream);
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1138166333448L, this.mSettingsActivityName);
        protoOutputStream.write(1133871366153L, this.mShouldDefaultToObserveMode);
        long start3 = protoOutputStream.start(2246267895818L);
        for (Map.Entry<String, Boolean> entry : this.mAutoTransact.entrySet()) {
            protoOutputStream.write(1138166333441L, entry.getKey());
            protoOutputStream.write(1133871366146L, entry.getValue().booleanValue());
        }
        protoOutputStream.end(start3);
        long start4 = protoOutputStream.start(2246267895819L);
        for (Map.Entry<Pattern, Boolean> entry2 : this.mAutoTransactPatterns.entrySet()) {
            protoOutputStream.write(1138166333441L, entry2.getKey().pattern());
            protoOutputStream.write(1133871366146L, entry2.getValue().booleanValue());
        }
        protoOutputStream.end(start4);
    }

    private static boolean isValidAid(String str) {
        if (str == null) {
            return false;
        }
        if ((str.endsWith("*") || str.endsWith("#")) && str.length() % 2 == 0) {
            Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (!str.endsWith("*") && !str.endsWith("#") && str.length() % 2 != 0) {
            Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (AID_PATTERN.matcher(str).matches()) {
            return true;
        }
        Log.e(TAG, "AID " + str + " is not a valid AID.");
        return false;
    }
}
