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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@SystemApi
/* loaded from: classes3.dex */
public final class ApduServiceInfo implements Parcelable {
    static final String SE_PREFIX_ESE = "eSE";
    static final String SE_PREFIX_SIM = "SIM";
    static final String SE_PREFIX_UICC = "UICC";
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
    private boolean mSamsungExt;
    private final ResolveInfo mService;
    private final String mSettingsActivityName;
    private boolean mShouldDefaultToObserveMode;
    private final HashMap<String, AidGroup> mStaticAidGroups;
    private String mStaticOffHostName;
    private final int mUid;
    public static final Parcelable.Creator<ApduServiceInfo> CREATOR = new Parcelable.Creator<ApduServiceInfo>() { // from class: android.nfc.cardemulation.ApduServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduServiceInfo createFromParcel(Parcel source) {
            ResolveInfo info = ResolveInfo.CREATOR.createFromParcel(source);
            String description = source.readString();
            boolean onHost = source.readInt() != 0;
            String offHostName = source.readString();
            String staticOffHostName = source.readString();
            ArrayList<AidGroup> staticAidGroups = new ArrayList<>();
            int numStaticGroups = source.readInt();
            if (numStaticGroups > 0) {
                source.readTypedList(staticAidGroups, AidGroup.CREATOR);
            }
            ArrayList<AidGroup> dynamicAidGroups = new ArrayList<>();
            int numDynamicGroups = source.readInt();
            if (numDynamicGroups > 0) {
                source.readTypedList(dynamicAidGroups, AidGroup.CREATOR);
            }
            boolean requiresUnlock = source.readInt() != 0;
            boolean requiresScreenOn = source.readInt() != 0;
            int bannerResource = source.readInt();
            int uid = source.readInt();
            String settingsActivityName = source.readString();
            boolean isEnabled = source.readInt() != 0;
            int autoTransactSize = source.readInt();
            HashMap<String, Boolean> autoTransact = new HashMap<>(autoTransactSize);
            source.readMap(autoTransact, getClass().getClassLoader(), String.class, Boolean.class);
            source.readInt();
            HashMap<Pattern, Boolean> autoTransactPatterns = new HashMap<>(autoTransactSize);
            source.readMap(autoTransactPatterns, getClass().getClassLoader(), Pattern.class, Boolean.class);
            return new ApduServiceInfo(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, requiresScreenOn, bannerResource, uid, settingsActivityName, offHostName, staticOffHostName, isEnabled, autoTransact, autoTransactPatterns);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduServiceInfo[] newArray(int size) {
            return new ApduServiceInfo[size];
        }
    };
    private static final Pattern AID_PATTERN = Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, ArrayList<AidGroup> staticAidGroups, ArrayList<AidGroup> dynamicAidGroups, boolean requiresUnlock, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost) {
        this(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, bannerResource, uid, settingsActivityName, offHost, staticOffHost, false, false);
    }

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, ArrayList<AidGroup> staticAidGroups, ArrayList<AidGroup> dynamicAidGroups, boolean requiresUnlock, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost, boolean isEnabled, boolean samsungExt) {
        this(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, onHost, bannerResource, uid, settingsActivityName, offHost, staticOffHost, isEnabled);
        this.mSamsungExt = samsungExt;
    }

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, List<AidGroup> staticAidGroups, List<AidGroup> dynamicAidGroups, boolean requiresUnlock, boolean requiresScreenOn, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost, boolean isEnabled) {
        this(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, requiresScreenOn, bannerResource, uid, settingsActivityName, offHost, staticOffHost, isEnabled, new HashMap(), new HashMap());
    }

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, List<AidGroup> staticAidGroups, List<AidGroup> dynamicAidGroups, boolean requiresUnlock, boolean requiresScreenOn, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost, boolean isEnabled, Map<String, Boolean> autoTransact, Map<Pattern, Boolean> autoTransactPatterns) {
        this.mSamsungExt = false;
        this.mService = info;
        this.mDescription = description;
        this.mStaticAidGroups = new HashMap<>();
        this.mDynamicAidGroups = new HashMap<>();
        this.mAutoTransact = autoTransact;
        this.mAutoTransactPatterns = autoTransactPatterns;
        this.mOffHostName = offHost;
        this.mStaticOffHostName = staticOffHost;
        this.mOnHost = onHost;
        this.mRequiresDeviceUnlock = requiresUnlock;
        this.mRequiresDeviceScreenOn = requiresScreenOn;
        for (AidGroup aidGroup : staticAidGroups) {
            this.mStaticAidGroups.put(aidGroup.getCategory(), aidGroup);
        }
        for (AidGroup aidGroup2 : dynamicAidGroups) {
            this.mDynamicAidGroups.put(aidGroup2.getCategory(), aidGroup2);
        }
        this.mBannerResourceId = bannerResource;
        this.mUid = uid;
        this.mSettingsActivityName = settingsActivityName;
        this.mCategoryOtherServiceEnabled = isEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0079, code lost:
    
        if ("offhost-apdu-service".equals(r12) == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0083, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with <offhost-apdu-service> tag");
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0581  */
    /* JADX WARN: Removed duplicated region for block: B:245:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7, types: [android.content.res.XmlResourceParser] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ApduServiceInfo(android.content.pm.PackageManager r24, android.content.pm.ResolveInfo r25, boolean r26) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1413
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
        ArrayList<String> aids = new ArrayList<>();
        for (AidGroup group : getAidGroups()) {
            aids.addAll(group.getAids());
        }
        return aids;
    }

    public List<String> getPollingLoopFilters() {
        return new ArrayList(this.mAutoTransact.keySet());
    }

    public boolean getShouldAutoTransact(final String plf) {
        if (this.mAutoTransact.getOrDefault(plf.toUpperCase(Locale.ROOT), false).booleanValue()) {
            return true;
        }
        List<Pattern> patternMatches = this.mAutoTransactPatterns.keySet().stream().filter(new Predicate() { // from class: android.nfc.cardemulation.ApduServiceInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean matches;
                matches = ((Pattern) obj).matcher(plf).matches();
                return matches;
            }
        }).toList();
        if (patternMatches == null || patternMatches.size() == 0) {
            return false;
        }
        for (Pattern patternMatch : patternMatches) {
            if (this.mAutoTransactPatterns.get(patternMatch).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public List<Pattern> getPollingLoopPatternFilters() {
        return new ArrayList(this.mAutoTransactPatterns.keySet());
    }

    public List<String> getPrefixAids() {
        ArrayList<String> prefixAids = new ArrayList<>();
        for (AidGroup group : getAidGroups()) {
            for (String aid : group.getAids()) {
                if (aid.endsWith("*")) {
                    prefixAids.add(aid);
                }
            }
        }
        return prefixAids;
    }

    public List<String> getSubsetAids() {
        ArrayList<String> subsetAids = new ArrayList<>();
        for (AidGroup group : getAidGroups()) {
            for (String aid : group.getAids()) {
                if (aid.endsWith("#")) {
                    subsetAids.add(aid);
                }
            }
        }
        return subsetAids;
    }

    public AidGroup getDynamicAidGroupForCategory(String category) {
        return this.mDynamicAidGroups.get(category);
    }

    public boolean removeDynamicAidGroupForCategory(String category) {
        return this.mDynamicAidGroups.remove(category) != null;
    }

    public List<AidGroup> getAidGroups() {
        ArrayList<AidGroup> groups = new ArrayList<>();
        Iterator<Map.Entry<String, AidGroup>> it = this.mDynamicAidGroups.entrySet().iterator();
        while (it.hasNext()) {
            groups.add(it.next().getValue());
        }
        for (Map.Entry<String, AidGroup> entry : this.mStaticAidGroups.entrySet()) {
            if (!this.mDynamicAidGroups.containsKey(entry.getKey())) {
                groups.add(entry.getValue());
            }
        }
        return groups;
    }

    public String getCategoryForAid(String aid) {
        List<AidGroup> groups = getAidGroups();
        for (AidGroup group : groups) {
            if (group.getAids().contains(aid.toUpperCase())) {
                return group.getCategory();
            }
        }
        return null;
    }

    public boolean hasCategory(String category) {
        return this.mStaticAidGroups.containsKey(category) || this.mDynamicAidGroups.containsKey(category);
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

    public void setShouldDefaultToObserveMode(boolean shouldDefaultToObserveMode) {
        this.mShouldDefaultToObserveMode = shouldDefaultToObserveMode;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setOrReplaceDynamicAidGroup(AidGroup aidGroup) {
        this.mDynamicAidGroups.put(aidGroup.getCategory(), aidGroup);
    }

    public void setDynamicAidGroup(AidGroup aidGroup) {
        this.mDynamicAidGroups.put(aidGroup.getCategory(), aidGroup);
    }

    public void addPollingLoopFilter(String pollingLoopFilter, boolean autoTransact) {
        if (!this.mOnHost && !autoTransact) {
            return;
        }
        this.mAutoTransact.put(pollingLoopFilter, Boolean.valueOf(autoTransact));
    }

    public void removePollingLoopFilter(String pollingLoopFilter) {
        this.mAutoTransact.remove(pollingLoopFilter.toUpperCase(Locale.ROOT));
    }

    public void addPollingLoopPatternFilter(String pollingLoopPatternFilter, boolean autoTransact) {
        if (!this.mOnHost && !autoTransact) {
            return;
        }
        this.mAutoTransactPatterns.put(Pattern.compile(pollingLoopPatternFilter), Boolean.valueOf(autoTransact));
    }

    public void removePollingLoopPatternFilter(String pollingLoopPatternFilter) {
        this.mAutoTransactPatterns.remove(Pattern.compile(pollingLoopPatternFilter.toUpperCase(Locale.ROOT)));
    }

    public void setOffHostSecureElement(String offHost) {
        this.mOffHostName = offHost;
    }

    public void resetOffHostSecureElement() {
        this.mOffHostName = this.mStaticOffHostName;
    }

    public CharSequence loadLabel(PackageManager pm) {
        return this.mService.loadLabel(pm);
    }

    public CharSequence loadAppLabel(PackageManager pm) {
        try {
            return pm.getApplicationLabel(pm.getApplicationInfo(this.mService.resolvePackageName, 128));
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public Drawable loadIcon(PackageManager pm) {
        return this.mService.loadIcon(pm);
    }

    public Drawable loadBanner(PackageManager pm) {
        try {
            Resources res = pm.getResourcesForApplication(this.mService.serviceInfo.packageName);
            Drawable banner = res.getDrawable(this.mBannerResourceId);
            return banner;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not load banner.");
            return null;
        } catch (Resources.NotFoundException e2) {
            Log.e(TAG, "Could not load banner.");
            return null;
        }
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("ApduService: ");
        out.append(getComponent());
        out.append(", UID: " + this.mUid);
        out.append(", description: " + this.mDescription);
        out.append(", Static AID Groups: ");
        for (AidGroup aidGroup : this.mStaticAidGroups.values()) {
            out.append(aidGroup.toString());
        }
        out.append(", Dynamic AID Groups: ");
        for (AidGroup aidGroup2 : this.mDynamicAidGroups.values()) {
            out.append(aidGroup2.toString());
        }
        return out.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApduServiceInfo)) {
            return false;
        }
        ApduServiceInfo thatService = (ApduServiceInfo) o;
        return thatService.getComponent().equals(getComponent()) && thatService.getUid() == getUid();
    }

    public int hashCode() {
        return getComponent().hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public void dump(ParcelFileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("    " + getComponent() + " (Description: " + getDescription() + ") (UID: " + getUid() + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mOnHost) {
            pw.println("    On Host Service");
        } else {
            pw.println("    Off-host Service");
            pw.println("        Current off-host SE:" + this.mOffHostName + " static off-host SE:" + this.mStaticOffHostName);
        }
        pw.println("    Static AID groups:");
        for (AidGroup group : this.mStaticAidGroups.values()) {
            pw.println("        Category: " + group.getCategory() + "(enabled: " + this.mCategoryOtherServiceEnabled + NavigationBarInflaterView.KEY_CODE_END);
            for (String aid : group.getAids()) {
                pw.println("            AID: " + aid);
            }
        }
        pw.println("    Dynamic AID groups:");
        for (AidGroup group2 : this.mDynamicAidGroups.values()) {
            pw.println("        Category: " + group2.getCategory() + "(enabled: " + this.mCategoryOtherServiceEnabled + NavigationBarInflaterView.KEY_CODE_END);
            for (String aid2 : group2.getAids()) {
                pw.println("            AID: " + aid2);
            }
        }
        pw.println("    Settings Activity: " + this.mSettingsActivityName);
        pw.println("    Requires Device Unlock: " + this.mRequiresDeviceUnlock);
        pw.println("    Requires Device ScreenOn: " + this.mRequiresDeviceScreenOn);
        pw.println("    Should Default to Observe Mode: " + this.mShouldDefaultToObserveMode);
        pw.println("    Auto-Transact Mapping: " + this.mAutoTransact);
        pw.println("    Auto-Transact Patterns: " + this.mAutoTransactPatterns);
        pw.println("    EXT: " + this.mSamsungExt);
    }

    public void setCategoryOtherServiceEnabled(boolean enabled) {
        this.mCategoryOtherServiceEnabled = enabled;
    }

    public boolean isSamsungExtensionService() {
        return this.mSamsungExt;
    }

    public boolean isCategoryOtherServiceEnabled() {
        return this.mCategoryOtherServiceEnabled;
    }

    public void dumpDebug(ProtoOutputStream proto) {
        getComponent().dumpDebug(proto, 1146756268033L);
        proto.write(1138166333442L, getDescription());
        proto.write(1133871366147L, this.mOnHost);
        if (!this.mOnHost) {
            proto.write(1138166333444L, this.mOffHostName);
            proto.write(1138166333445L, this.mStaticOffHostName);
        }
        for (AidGroup group : this.mStaticAidGroups.values()) {
            long token = proto.start(2246267895814L);
            group.dump(proto);
            proto.end(token);
        }
        for (AidGroup group2 : this.mDynamicAidGroups.values()) {
            long token2 = proto.start(2246267895814L);
            group2.dump(proto);
            proto.end(token2);
        }
        proto.write(1138166333448L, this.mSettingsActivityName);
        proto.write(1133871366153L, this.mShouldDefaultToObserveMode);
        long token3 = proto.start(2246267895818L);
        for (Map.Entry<String, Boolean> entry : this.mAutoTransact.entrySet()) {
            proto.write(1138166333441L, entry.getKey());
            proto.write(1133871366146L, entry.getValue().booleanValue());
        }
        proto.end(token3);
        long token4 = proto.start(2246267895819L);
        for (Map.Entry<Pattern, Boolean> entry2 : this.mAutoTransactPatterns.entrySet()) {
            proto.write(1138166333441L, entry2.getKey().pattern());
            proto.write(1133871366146L, entry2.getValue().booleanValue());
        }
        proto.end(token4);
    }

    private static boolean isValidAid(String aid) {
        if (aid == null) {
            return false;
        }
        if ((aid.endsWith("*") || aid.endsWith("#")) && aid.length() % 2 == 0) {
            Log.e(TAG, "AID " + aid + " is not a valid AID.");
            return false;
        }
        if (!aid.endsWith("*") && !aid.endsWith("#") && aid.length() % 2 != 0) {
            Log.e(TAG, "AID " + aid + " is not a valid AID.");
            return false;
        }
        if (!AID_PATTERN.matcher(aid).matches()) {
            Log.e(TAG, "AID " + aid + " is not a valid AID.");
            return false;
        }
        return true;
    }
}
