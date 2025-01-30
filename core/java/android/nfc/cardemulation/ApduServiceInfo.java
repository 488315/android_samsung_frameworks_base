package android.nfc.cardemulation;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Contacts;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.internal.C4337R;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class ApduServiceInfo implements Parcelable {
    static final String AID_BASED_FALSE = "false";
    static final String AID_BASED_TRUE = "true";
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
            boolean isSelected = source.readInt() != 0;
            boolean aidBased = source.readInt() != 0;
            boolean samsungExt = source.readInt() != 0;
            return new ApduServiceInfo(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, requiresScreenOn, bannerResource, uid, settingsActivityName, offHostName, staticOffHostName, isSelected, aidBased, samsungExt);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduServiceInfo[] newArray(int size) {
            return new ApduServiceInfo[size];
        }
    };
    static final String SE_PREFIX_ESE = "eSE";
    static final String SE_PREFIX_SIM = "SIM";
    static final String SE_PREFIX_UICC = "UICC";
    static final String TAG = "ApduServiceInfo";
    private boolean mAidBased;
    final int mBannerResourceId;
    final String mDescription;
    final HashMap<String, AidGroup> mDynamicAidGroups;
    String mOffHostName;
    final boolean mOnHost;
    private boolean mOtherServiceSelectionState;
    boolean mRequiresDeviceScreenOn;
    final boolean mRequiresDeviceUnlock;
    private boolean mSamsungExt;
    final ResolveInfo mService;
    final String mSettingsActivityName;
    final HashMap<String, AidGroup> mStaticAidGroups;
    String mStaticOffHostName;
    final int mUid;

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, ArrayList<AidGroup> staticAidGroups, ArrayList<AidGroup> dynamicAidGroups, boolean requiresUnlock, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost) {
        this(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, onHost, bannerResource, uid, settingsActivityName, offHost, staticOffHost);
    }

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, ArrayList<AidGroup> staticAidGroups, ArrayList<AidGroup> dynamicAidGroups, boolean requiresUnlock, boolean requiresScreenOn, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost, boolean isSelected, boolean aidBased, boolean samsungExt) {
        this(info, onHost, description, staticAidGroups, dynamicAidGroups, requiresUnlock, requiresScreenOn, bannerResource, uid, settingsActivityName, offHost, staticOffHost);
        this.mOtherServiceSelectionState = isSelected;
        this.mAidBased = aidBased;
        this.mSamsungExt = samsungExt;
        if (isExceptionalSPay()) {
            this.mRequiresDeviceScreenOn = false;
        } else {
            this.mRequiresDeviceScreenOn = requiresScreenOn;
        }
    }

    public ApduServiceInfo(ResolveInfo info, boolean onHost, String description, ArrayList<AidGroup> staticAidGroups, ArrayList<AidGroup> dynamicAidGroups, boolean requiresUnlock, boolean requiresScreenOn, int bannerResource, int uid, String settingsActivityName, String offHost, String staticOffHost) {
        this.mAidBased = true;
        this.mSamsungExt = false;
        this.mService = info;
        this.mDescription = description;
        this.mStaticAidGroups = new HashMap<>();
        this.mDynamicAidGroups = new HashMap<>();
        this.mOffHostName = offHost;
        this.mStaticOffHostName = staticOffHost;
        this.mOnHost = onHost;
        this.mRequiresDeviceUnlock = requiresUnlock;
        this.mRequiresDeviceScreenOn = requiresScreenOn;
        Iterator<AidGroup> it = staticAidGroups.iterator();
        while (it.hasNext()) {
            AidGroup aidGroup = it.next();
            this.mStaticAidGroups.put(aidGroup.category, aidGroup);
        }
        Iterator<AidGroup> it2 = dynamicAidGroups.iterator();
        while (it2.hasNext()) {
            AidGroup aidGroup2 = it2.next();
            this.mDynamicAidGroups.put(aidGroup2.category, aidGroup2);
        }
        this.mBannerResourceId = bannerResource;
        this.mUid = uid;
        this.mSettingsActivityName = settingsActivityName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0075, code lost:
    
        if ("offhost-apdu-service".equals(r12) == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007f, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with <offhost-apdu-service> tag");
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x044a, code lost:
    
        r23.mSamsungExt = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x044d, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:?, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:236:0x04b3  */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v16, types: [android.content.res.XmlResourceParser] */
    /* JADX WARN: Type inference failed for: r15v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r15v23 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ApduServiceInfo(PackageManager pm, ResolveInfo info, boolean onHost) throws XmlPullParserException, IOException {
        String str;
        XmlResourceParser extParser;
        int eventType;
        String tagName;
        String str2;
        String str3;
        XmlResourceParser extParser2;
        String str4;
        String aidBased;
        this.mAidBased = true;
        this.mSamsungExt = false;
        ServiceInfo si = info.serviceInfo;
        XmlResourceParser parser = null;
        XmlResourceParser extParser3 = null;
        try {
            try {
                if (onHost) {
                    parser = si.loadXmlMetaData(pm, HostApduService.SERVICE_META_DATA);
                    if (parser == null) {
                        throw new XmlPullParserException("No android.nfc.cardemulation.host_apdu_service meta-data");
                    }
                } else {
                    parser = si.loadXmlMetaData(pm, OffHostApduService.SERVICE_META_DATA);
                    if (parser == null) {
                        throw new XmlPullParserException("No android.nfc.cardemulation.off_host_apdu_service meta-data");
                    }
                    extParser3 = si.loadXmlMetaData(pm, OffHostApduService.SERVICE_EXTRA_META_DATA);
                    if (extParser3 == null) {
                        extParser3 = si.loadXmlMetaData(pm, OffHostApduService.SE_EXT_META_DATA);
                    }
                }
                try {
                    int eventType2 = parser.getEventType();
                    while (eventType2 != 2 && eventType2 != 1) {
                        eventType2 = parser.next();
                    }
                    String tagName2 = parser.getName();
                    if (onHost && !"host-apdu-service".equals(tagName2)) {
                        throw new XmlPullParserException("Meta-data does not start with <host-apdu-service> tag");
                    }
                    Resources res = pm.getResourcesForApplication(si.applicationInfo);
                    AttributeSet attrs = Xml.asAttributeSet(parser);
                    ?? r15 = SE_PREFIX_SIM;
                    String str5 = "SIM1";
                    try {
                        if (onHost) {
                            eventType = eventType2;
                            try {
                                TypedArray sa = res.obtainAttributes(attrs, C4337R.styleable.HostApduService);
                                this.mService = info;
                                str = "true";
                                tagName = tagName2;
                                this.mDescription = sa.getString(0);
                                extParser = extParser3;
                                this.mRequiresDeviceUnlock = sa.getBoolean(2, false);
                                if (isExceptionalSPay()) {
                                    this.mRequiresDeviceScreenOn = false;
                                } else {
                                    this.mRequiresDeviceScreenOn = sa.getBoolean(4, true);
                                }
                                this.mBannerResourceId = sa.getResourceId(3, -1);
                                this.mSettingsActivityName = sa.getString(1);
                                this.mOffHostName = null;
                                this.mStaticOffHostName = null;
                                sa.recycle();
                            } catch (PackageManager.NameNotFoundException e) {
                                throw new XmlPullParserException("Unable to create context for: " + si.packageName);
                            } catch (Throwable th) {
                                e = th;
                                if (parser != null) {
                                    parser.close();
                                }
                                throw e;
                            }
                        } else {
                            str = "true";
                            extParser = extParser3;
                            eventType = eventType2;
                            tagName = tagName2;
                            try {
                                TypedArray sa2 = res.obtainAttributes(attrs, C4337R.styleable.OffHostApduService);
                                this.mService = info;
                                this.mDescription = sa2.getString(0);
                                this.mRequiresDeviceUnlock = sa2.getBoolean(2, false);
                                this.mRequiresDeviceScreenOn = sa2.getBoolean(5, false);
                                this.mBannerResourceId = sa2.getResourceId(3, -1);
                                this.mSettingsActivityName = sa2.getString(1);
                                String string = sa2.getString(4);
                                this.mOffHostName = string;
                                if (string == null) {
                                    Log.m96e(TAG, "###---");
                                    this.mOffHostName = "SIM1";
                                } else if (string.equals(SE_PREFIX_ESE)) {
                                    this.mOffHostName = "eSE1";
                                } else if (this.mOffHostName.equals(SE_PREFIX_SIM)) {
                                    this.mOffHostName = "SIM1";
                                }
                                this.mStaticOffHostName = this.mOffHostName;
                                sa2.recycle();
                            } catch (PackageManager.NameNotFoundException e2) {
                                throw new XmlPullParserException("Unable to create context for: " + si.packageName);
                            } catch (Throwable th2) {
                                e = th2;
                                if (parser != null) {
                                }
                                throw e;
                            }
                        }
                        this.mStaticAidGroups = new HashMap<>();
                        this.mDynamicAidGroups = new HashMap<>();
                        this.mOnHost = onHost;
                        int depth = parser.getDepth();
                        AidGroup currentGroup = null;
                        while (true) {
                            int eventType3 = parser.next();
                            if (eventType3 == 3) {
                                if (parser.getDepth() <= depth) {
                                    str2 = str5;
                                    str3 = r15;
                                    break;
                                }
                            }
                            if (eventType3 == 1) {
                                str2 = str5;
                                str3 = r15;
                                break;
                            }
                            String tagName3 = parser.getName();
                            if (eventType3 == 2 && "aid-group".equals(tagName3) && currentGroup == null) {
                                TypedArray groupAttrs = res.obtainAttributes(attrs, C4337R.styleable.AidGroup);
                                String groupCategory = groupAttrs.getString(1);
                                int depth2 = depth;
                                String groupDescription = groupAttrs.getString(0);
                                String str6 = str5;
                                String groupCategory2 = !CardEmulation.CATEGORY_PAYMENT.equals(groupCategory) ? "other" : groupCategory;
                                String groupCategory3 = r15;
                                currentGroup = this.mStaticAidGroups.get(groupCategory2);
                                if (currentGroup == null) {
                                    AidGroup currentGroup2 = new AidGroup(groupCategory2, groupDescription);
                                    currentGroup = currentGroup2;
                                } else if (!"other".equals(groupCategory2)) {
                                    Log.m96e(TAG, "Not allowing multiple aid-groups in the " + groupCategory2 + " category");
                                    currentGroup = null;
                                }
                                groupAttrs.recycle();
                                depth = depth2;
                                str5 = str6;
                                r15 = groupCategory3;
                            } else {
                                int depth3 = depth;
                                String str7 = str5;
                                Object obj = r15;
                                if (eventType3 == 3 && "aid-group".equals(tagName3) && currentGroup != null) {
                                    if (currentGroup.aids.size() <= 0) {
                                        Log.m96e(TAG, "Not adding <aid-group> with empty or invalid AIDs");
                                    } else if (!this.mStaticAidGroups.containsKey(currentGroup.category)) {
                                        this.mStaticAidGroups.put(currentGroup.category, currentGroup);
                                    }
                                    currentGroup = null;
                                    depth = depth3;
                                    str5 = str7;
                                    r15 = obj;
                                } else {
                                    if (eventType3 == 2 && "aid-filter".equals(tagName3) && currentGroup != null) {
                                        TypedArray a = res.obtainAttributes(attrs, C4337R.styleable.AidFilter);
                                        String aid = a.getString(0).toUpperCase();
                                        if (!CardEmulation.isValidAid(aid) || currentGroup.aids.contains(aid)) {
                                            Log.m96e(TAG, "Ignoring invalid or duplicate aid: " + aid);
                                        } else {
                                            currentGroup.aids.add(aid);
                                        }
                                        a.recycle();
                                    } else if (eventType3 == 2 && "aid-prefix-filter".equals(tagName3) && currentGroup != null) {
                                        TypedArray a2 = res.obtainAttributes(attrs, C4337R.styleable.AidFilter);
                                        String aid2 = a2.getString(0).toUpperCase().concat("*");
                                        if (!CardEmulation.isValidAid(aid2) || currentGroup.aids.contains(aid2)) {
                                            Log.m96e(TAG, "Ignoring invalid or duplicate aid: " + aid2);
                                        } else {
                                            currentGroup.aids.add(aid2);
                                        }
                                        a2.recycle();
                                    } else if (eventType3 == 2 && tagName3.equals("aid-suffix-filter") && currentGroup != null) {
                                        TypedArray a3 = res.obtainAttributes(attrs, C4337R.styleable.AidFilter);
                                        String aid3 = a3.getString(0).toUpperCase().concat("#");
                                        if (!CardEmulation.isValidAid(aid3) || currentGroup.aids.contains(aid3)) {
                                            Log.m96e(TAG, "Ignoring invalid or duplicate aid: " + aid3);
                                        } else {
                                            currentGroup.aids.add(aid3);
                                        }
                                        a3.recycle();
                                    }
                                    depth = depth3;
                                    str5 = str7;
                                    r15 = obj;
                                }
                            }
                        }
                        if (parser != null) {
                            parser.close();
                        }
                        this.mUid = si.applicationInfo.uid;
                        this.mOtherServiceSelectionState = true;
                        if (extParser == null) {
                            this.mSamsungExt = false;
                            return;
                        }
                        try {
                            int depth4 = extParser.getDepth();
                            for (int eventType4 = extParser.getEventType(); eventType4 != 2 && eventType4 != 1; eventType4 = extParser.next()) {
                                try {
                                } catch (Throwable th3) {
                                    th = th3;
                                    r15 = extParser;
                                    r15.close();
                                    throw th;
                                }
                            }
                            String tagName4 = extParser.getName();
                            try {
                                if (!Contacts.People.Extensions.CONTENT_DIRECTORY.equals(tagName4)) {
                                    throw new XmlPullParserException("Meta-data does not start with <extensions> tag " + tagName4);
                                }
                                while (true) {
                                    int eventType5 = extParser.next();
                                    if (eventType5 == 3 && extParser.getDepth() <= depth4) {
                                        extParser2 = extParser;
                                        break;
                                    }
                                    if (eventType5 == 1) {
                                        extParser2 = extParser;
                                        break;
                                    }
                                    String tagName5 = extParser.getName();
                                    if (eventType5 == 2 && "AID-based".equals(tagName5)) {
                                        aidBased = extParser.nextText();
                                        if (aidBased == null) {
                                            break;
                                        }
                                        String str8 = str;
                                        if (!aidBased.equalsIgnoreCase(str8) && !aidBased.equalsIgnoreCase("false")) {
                                            break;
                                        }
                                        this.mAidBased = aidBased.equalsIgnoreCase(str8);
                                        Log.m94d(TAG, "XYZ-" + this.mAidBased);
                                        str = str8;
                                    } else {
                                        String str9 = str;
                                        if (eventType5 == 2 && "se-id".equals(tagName5)) {
                                            XmlResourceParser extParser4 = extParser;
                                            String extOffHostName = extParser4.getAttributeValue(null, "name");
                                            if (extOffHostName == null) {
                                                str4 = str3;
                                                Log.m96e(TAG, "###+++");
                                                extOffHostName = str2;
                                            } else if (extOffHostName.equalsIgnoreCase(SE_PREFIX_ESE)) {
                                                extOffHostName = "eSE1";
                                                str4 = str3;
                                            } else {
                                                if (extOffHostName.equalsIgnoreCase(SE_PREFIX_UICC)) {
                                                    str4 = str3;
                                                } else {
                                                    str4 = str3;
                                                    if (extOffHostName.equalsIgnoreCase(str4)) {
                                                    }
                                                }
                                                extOffHostName = str2;
                                            }
                                            this.mOffHostName = extOffHostName;
                                            this.mStaticOffHostName = extOffHostName;
                                            str = str9;
                                            str3 = str4;
                                            extParser = extParser4;
                                        } else {
                                            str = str9;
                                            str3 = str3;
                                            extParser = extParser;
                                        }
                                    }
                                }
                                throw new XmlPullParserException("Unsupported AID-based value: " + aidBased);
                            } catch (Throwable th4) {
                                th = th4;
                                r15.close();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            r15 = extParser;
                        }
                    } catch (PackageManager.NameNotFoundException e3) {
                    } catch (Throwable th6) {
                        e = th6;
                    }
                } catch (PackageManager.NameNotFoundException e4) {
                } catch (Throwable th7) {
                    e = th7;
                }
            } catch (Throwable th8) {
                e = th8;
            }
        } catch (PackageManager.NameNotFoundException e5) {
        }
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getOffHostSecureElement() {
        return this.mOffHostName;
    }

    public List<String> getAids() {
        ArrayList<String> aids = new ArrayList<>();
        Iterator<AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            AidGroup group = it.next();
            aids.addAll(group.aids);
        }
        return aids;
    }

    public List<String> getPrefixAids() {
        ArrayList<String> prefixAids = new ArrayList<>();
        Iterator<AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            AidGroup group = it.next();
            for (String aid : group.aids) {
                if (aid.endsWith("*")) {
                    prefixAids.add(aid);
                }
            }
        }
        return prefixAids;
    }

    public List<String> getSubsetAids() {
        ArrayList<String> subsetAids = new ArrayList<>();
        Iterator<AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            AidGroup group = it.next();
            for (String aid : group.aids) {
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

    public ArrayList<AidGroup> getAidGroups() {
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
        ArrayList<AidGroup> groups = getAidGroups();
        Iterator<AidGroup> it = groups.iterator();
        while (it.hasNext()) {
            AidGroup group = it.next();
            if (group.aids.contains(aid.toUpperCase())) {
                return group.category;
            }
        }
        return null;
    }

    public String getCategoryForPrefixAid(String aid) {
        ArrayList<AidGroup> groups = getAidGroups();
        Iterator<AidGroup> it = groups.iterator();
        while (it.hasNext()) {
            AidGroup group = it.next();
            for (String a : group.aids) {
                if (a.endsWith("*")) {
                    String newAid = a.substring(0, a.length() - 1);
                    if (aid.toUpperCase().startsWith(newAid.toUpperCase())) {
                        return group.category;
                    }
                }
            }
        }
        return null;
    }

    public boolean hasCategory(String category) {
        return this.mStaticAidGroups.containsKey(category) || this.mDynamicAidGroups.containsKey(category) || (!isAidBased() && "other".equals(category));
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

    public String getDescription() {
        return this.mDescription;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setOrReplaceDynamicAidGroup(AidGroup aidGroup) {
        this.mDynamicAidGroups.put(aidGroup.getCategory(), aidGroup);
    }

    public void setOffHostSecureElement(String offHost) {
        this.mOffHostName = offHost;
    }

    public void unsetOffHostSecureElement() {
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
        } catch (Exception | OutOfMemoryError e) {
            Log.m96e(TAG, "Could not load banner.");
            return null;
        }
    }

    public boolean isAidBased() {
        return this.mAidBased;
    }

    public void setAidBased(boolean aidBased) {
        this.mAidBased = aidBased;
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
        parcel.writeInt(this.mOtherServiceSelectionState ? 1 : 0);
        parcel.writeInt(this.mAidBased ? 1 : 0);
        parcel.writeInt(this.mSamsungExt ? 1 : 0);
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("    " + getComponent() + " (Description: " + getDescription() + ") (UID: " + getUid() + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mOnHost) {
            pw.println("    On Host Service");
        } else {
            pw.println("    Off-host Service");
            pw.println("        Current off-host SE:" + this.mOffHostName + " static off-host SE:" + this.mStaticOffHostName);
        }
        pw.println("    Static AID groups:");
        for (AidGroup group : this.mStaticAidGroups.values()) {
            pw.println("        Category: " + group.category + "(selected: " + this.mOtherServiceSelectionState + NavigationBarInflaterView.KEY_CODE_END);
            for (String aid : group.aids) {
                pw.println("            AID: " + aid);
            }
        }
        pw.println("    Dynamic AID groups:");
        for (AidGroup group2 : this.mDynamicAidGroups.values()) {
            pw.println("        Category: " + group2.category + "(selected: " + this.mOtherServiceSelectionState + NavigationBarInflaterView.KEY_CODE_END);
            for (String aid2 : group2.aids) {
                pw.println("            AID: " + aid2);
            }
        }
        pw.println("    Settings Activity: " + this.mSettingsActivityName);
        pw.println("    Requires Device Unlock: " + this.mRequiresDeviceUnlock);
        pw.println("    Requires Device ScreenOn: " + this.mRequiresDeviceScreenOn);
        pw.println("    AID-based: " + this.mAidBased);
        pw.println("    EXT: " + this.mSamsungExt);
    }

    public void setOtherServiceState(boolean selected) {
        this.mOtherServiceSelectionState = selected;
    }

    public boolean isSelectedOtherService() {
        return this.mOtherServiceSelectionState;
    }

    public boolean isSamsungExtensionService() {
        return this.mSamsungExt;
    }

    public boolean isExceptionalSPay() {
        if (this.mService.serviceInfo.applicationInfo.targetSdkVersion >= 31) {
            return false;
        }
        String[] exceptionPackages = {"com.samsung.android.spayfw.core.hce.SPayHCEService"};
        for (String serviceName : exceptionPackages) {
            if (serviceName.equals(this.mService.serviceInfo.name)) {
                return true;
            }
        }
        return false;
    }

    public void dumpDebug(ProtoOutputStream proto) {
        Utils.dumpDebugComponentName(getComponent(), proto, 1146756268033L);
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
    }
}
