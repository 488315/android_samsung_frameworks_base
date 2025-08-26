package android.nfc.cardemulation;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.nfc.Flags;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import java.io.IOException;
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
import org.xmlpull.v1.XmlPullParserException;

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
        public ApduServiceInfo createFromParcel(Parcel parcel) throws ClassNotFoundException, IOException {
            boolean z;
            ResolveInfo resolveInfoCreateFromParcel = ResolveInfo.CREATOR.createFromParcel(parcel);
            String string = parcel.readString();
            boolean z2 = false;
            if (parcel.readInt() != 0) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
            String string2 = parcel.readString();
            String string3 = parcel.readString();
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
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string4 = parcel.readString();
            boolean z5 = parcel.readInt() != 0 ? true : z;
            int i3 = parcel.readInt();
            HashMap map = new HashMap(i3);
            parcel.readMap(map, getClass().getClassLoader(), String.class, Boolean.class);
            parcel.readInt();
            HashMap map2 = new HashMap(i3);
            parcel.readMap(map2, getClass().getClassLoader(), Pattern.class, Boolean.class);
            return new ApduServiceInfo(resolveInfoCreateFromParcel, z2, string, arrayList, arrayList2, z3, z4, i, i2, string4, string2, string3, z5, map, map2);
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

    /* JADX WARN: Not initialized variable reg: 6, insn: 0x03da: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:156:0x03da */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x03dc: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:157:0x03dc */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0381 A[Catch: all -> 0x03d9, NameNotFoundException -> 0x03dc, TRY_ENTER, TryCatch #5 {NameNotFoundException -> 0x03dc, all -> 0x03d9, blocks: (B:7:0x0019, B:8:0x0020, B:13:0x002c, B:17:0x0036, B:18:0x003b, B:20:0x0041, B:23:0x004a, B:24:0x0051, B:26:0x0054, B:29:0x005e, B:30:0x0065, B:31:0x0066, B:33:0x0077, B:35:0x00ad, B:36:0x00b4, B:49:0x011e, B:50:0x014a, B:52:0x0150, B:55:0x0158, B:58:0x0162, B:61:0x016a, B:65:0x0185, B:67:0x018f, B:69:0x0195, B:71:0x01b6, B:70:0x01b0, B:73:0x01bd, B:76:0x01c5, B:78:0x01cf, B:80:0x01db, B:81:0x01e5, B:86:0x01f2, B:89:0x01fc, B:91:0x0210, B:93:0x021a, B:95:0x0234, B:94:0x0222, B:97:0x023b, B:100:0x0245, B:102:0x025f, B:104:0x0269, B:106:0x0283, B:105:0x0271, B:108:0x028a, B:111:0x0294, B:113:0x02ae, B:115:0x02b8, B:117:0x02d2, B:116:0x02c0, B:121:0x02db, B:124:0x02e6, B:127:0x0308, B:129:0x030f, B:132:0x0315, B:135:0x034c, B:133:0x032b, B:134:0x0335, B:137:0x0353, B:140:0x035e, B:143:0x0381, B:149:0x03c2, B:144:0x0399, B:147:0x039f, B:148:0x03b5, B:37:0x00b8, B:39:0x00ef, B:41:0x00f7, B:42:0x00fc, B:44:0x0106, B:45:0x010a, B:47:0x0114, B:48:0x011b, B:158:0x03de, B:159:0x03e5), top: B:174:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0399 A[Catch: all -> 0x03d9, NameNotFoundException -> 0x03dc, TryCatch #5 {NameNotFoundException -> 0x03dc, all -> 0x03d9, blocks: (B:7:0x0019, B:8:0x0020, B:13:0x002c, B:17:0x0036, B:18:0x003b, B:20:0x0041, B:23:0x004a, B:24:0x0051, B:26:0x0054, B:29:0x005e, B:30:0x0065, B:31:0x0066, B:33:0x0077, B:35:0x00ad, B:36:0x00b4, B:49:0x011e, B:50:0x014a, B:52:0x0150, B:55:0x0158, B:58:0x0162, B:61:0x016a, B:65:0x0185, B:67:0x018f, B:69:0x0195, B:71:0x01b6, B:70:0x01b0, B:73:0x01bd, B:76:0x01c5, B:78:0x01cf, B:80:0x01db, B:81:0x01e5, B:86:0x01f2, B:89:0x01fc, B:91:0x0210, B:93:0x021a, B:95:0x0234, B:94:0x0222, B:97:0x023b, B:100:0x0245, B:102:0x025f, B:104:0x0269, B:106:0x0283, B:105:0x0271, B:108:0x028a, B:111:0x0294, B:113:0x02ae, B:115:0x02b8, B:117:0x02d2, B:116:0x02c0, B:121:0x02db, B:124:0x02e6, B:127:0x0308, B:129:0x030f, B:132:0x0315, B:135:0x034c, B:133:0x032b, B:134:0x0335, B:137:0x0353, B:140:0x035e, B:143:0x0381, B:149:0x03c2, B:144:0x0399, B:147:0x039f, B:148:0x03b5, B:37:0x00b8, B:39:0x00ef, B:41:0x00f7, B:42:0x00fc, B:44:0x0106, B:45:0x010a, B:47:0x0114, B:48:0x011b, B:158:0x03de, B:159:0x03e5), top: B:174:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0077 A[Catch: all -> 0x03d9, NameNotFoundException -> 0x03dc, TryCatch #5 {NameNotFoundException -> 0x03dc, all -> 0x03d9, blocks: (B:7:0x0019, B:8:0x0020, B:13:0x002c, B:17:0x0036, B:18:0x003b, B:20:0x0041, B:23:0x004a, B:24:0x0051, B:26:0x0054, B:29:0x005e, B:30:0x0065, B:31:0x0066, B:33:0x0077, B:35:0x00ad, B:36:0x00b4, B:49:0x011e, B:50:0x014a, B:52:0x0150, B:55:0x0158, B:58:0x0162, B:61:0x016a, B:65:0x0185, B:67:0x018f, B:69:0x0195, B:71:0x01b6, B:70:0x01b0, B:73:0x01bd, B:76:0x01c5, B:78:0x01cf, B:80:0x01db, B:81:0x01e5, B:86:0x01f2, B:89:0x01fc, B:91:0x0210, B:93:0x021a, B:95:0x0234, B:94:0x0222, B:97:0x023b, B:100:0x0245, B:102:0x025f, B:104:0x0269, B:106:0x0283, B:105:0x0271, B:108:0x028a, B:111:0x0294, B:113:0x02ae, B:115:0x02b8, B:117:0x02d2, B:116:0x02c0, B:121:0x02db, B:124:0x02e6, B:127:0x0308, B:129:0x030f, B:132:0x0315, B:135:0x034c, B:133:0x032b, B:134:0x0335, B:137:0x0353, B:140:0x035e, B:143:0x0381, B:149:0x03c2, B:144:0x0399, B:147:0x039f, B:148:0x03b5, B:37:0x00b8, B:39:0x00ef, B:41:0x00f7, B:42:0x00fc, B:44:0x0106, B:45:0x010a, B:47:0x0114, B:48:0x011b, B:158:0x03de, B:159:0x03e5), top: B:174:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b8 A[Catch: all -> 0x03d9, NameNotFoundException -> 0x03dc, TryCatch #5 {NameNotFoundException -> 0x03dc, all -> 0x03d9, blocks: (B:7:0x0019, B:8:0x0020, B:13:0x002c, B:17:0x0036, B:18:0x003b, B:20:0x0041, B:23:0x004a, B:24:0x0051, B:26:0x0054, B:29:0x005e, B:30:0x0065, B:31:0x0066, B:33:0x0077, B:35:0x00ad, B:36:0x00b4, B:49:0x011e, B:50:0x014a, B:52:0x0150, B:55:0x0158, B:58:0x0162, B:61:0x016a, B:65:0x0185, B:67:0x018f, B:69:0x0195, B:71:0x01b6, B:70:0x01b0, B:73:0x01bd, B:76:0x01c5, B:78:0x01cf, B:80:0x01db, B:81:0x01e5, B:86:0x01f2, B:89:0x01fc, B:91:0x0210, B:93:0x021a, B:95:0x0234, B:94:0x0222, B:97:0x023b, B:100:0x0245, B:102:0x025f, B:104:0x0269, B:106:0x0283, B:105:0x0271, B:108:0x028a, B:111:0x0294, B:113:0x02ae, B:115:0x02b8, B:117:0x02d2, B:116:0x02c0, B:121:0x02db, B:124:0x02e6, B:127:0x0308, B:129:0x030f, B:132:0x0315, B:135:0x034c, B:133:0x032b, B:134:0x0335, B:137:0x0353, B:140:0x035e, B:143:0x0381, B:149:0x03c2, B:144:0x0399, B:147:0x039f, B:148:0x03b5, B:37:0x00b8, B:39:0x00ef, B:41:0x00f7, B:42:0x00fc, B:44:0x0106, B:45:0x010a, B:47:0x0114, B:48:0x011b, B:158:0x03de, B:159:0x03e5), top: B:174:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0162 A[Catch: all -> 0x03d9, NameNotFoundException -> 0x03dc, TRY_ENTER, TryCatch #5 {NameNotFoundException -> 0x03dc, all -> 0x03d9, blocks: (B:7:0x0019, B:8:0x0020, B:13:0x002c, B:17:0x0036, B:18:0x003b, B:20:0x0041, B:23:0x004a, B:24:0x0051, B:26:0x0054, B:29:0x005e, B:30:0x0065, B:31:0x0066, B:33:0x0077, B:35:0x00ad, B:36:0x00b4, B:49:0x011e, B:50:0x014a, B:52:0x0150, B:55:0x0158, B:58:0x0162, B:61:0x016a, B:65:0x0185, B:67:0x018f, B:69:0x0195, B:71:0x01b6, B:70:0x01b0, B:73:0x01bd, B:76:0x01c5, B:78:0x01cf, B:80:0x01db, B:81:0x01e5, B:86:0x01f2, B:89:0x01fc, B:91:0x0210, B:93:0x021a, B:95:0x0234, B:94:0x0222, B:97:0x023b, B:100:0x0245, B:102:0x025f, B:104:0x0269, B:106:0x0283, B:105:0x0271, B:108:0x028a, B:111:0x0294, B:113:0x02ae, B:115:0x02b8, B:117:0x02d2, B:116:0x02c0, B:121:0x02db, B:124:0x02e6, B:127:0x0308, B:129:0x030f, B:132:0x0315, B:135:0x034c, B:133:0x032b, B:134:0x0335, B:137:0x0353, B:140:0x035e, B:143:0x0381, B:149:0x03c2, B:144:0x0399, B:147:0x039f, B:148:0x03b5, B:37:0x00b8, B:39:0x00ef, B:41:0x00f7, B:42:0x00fc, B:44:0x0106, B:45:0x010a, B:47:0x0114, B:48:0x011b, B:158:0x03de, B:159:0x03e5), top: B:174:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0239  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ApduServiceInfo(PackageManager packageManager, ResolveInfo resolveInfo, boolean z) throws Throwable {
        XmlResourceParser xmlResourceParser;
        XmlResourceParser xmlResourceParser2;
        XmlResourceParser xmlResourceParserLoadXmlMetaData;
        int eventType;
        int i;
        Resources resourcesForApplication;
        AttributeSet attributeSetAsAttributeSet;
        int i2;
        int next;
        String upperCase;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        XmlResourceParser xmlResourceParser3 = null;
        try {
            if (z) {
                try {
                    try {
                        xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, "android.nfc.cardemulation.host_apdu_service");
                        if (xmlResourceParserLoadXmlMetaData == null) {
                            throw new XmlPullParserException("No android.nfc.cardemulation.host_apdu_service meta-data");
                        }
                        eventType = xmlResourceParserLoadXmlMetaData.getEventType();
                        while (true) {
                            i = 1;
                            if (eventType == 2 || eventType == 1) {
                                break;
                            } else {
                                eventType = xmlResourceParserLoadXmlMetaData.next();
                            }
                        }
                        String name = xmlResourceParserLoadXmlMetaData.getName();
                        if (z && !"host-apdu-service".equals(name)) {
                            throw new XmlPullParserException("Meta-data does not start with <host-apdu-service> tag");
                        }
                        if (!z && !"offhost-apdu-service".equals(name)) {
                            throw new XmlPullParserException("Meta-data does not start with <offhost-apdu-service> tag");
                        }
                        resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                        attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData);
                        i2 = 3;
                        if (!z) {
                            TypedArray typedArrayObtainAttributes = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.HostApduService);
                            this.mService = resolveInfo;
                            this.mDescription = typedArrayObtainAttributes.getString(0);
                            this.mRequiresDeviceUnlock = typedArrayObtainAttributes.getBoolean(2, false);
                            this.mRequiresDeviceScreenOn = typedArrayObtainAttributes.getBoolean(4, true);
                            this.mBannerResourceId = typedArrayObtainAttributes.getResourceId(3, -1);
                            this.mSettingsActivityName = typedArrayObtainAttributes.getString(1);
                            this.mOffHostName = null;
                            this.mStaticOffHostName = null;
                            this.mShouldDefaultToObserveMode = typedArrayObtainAttributes.getBoolean(5, false);
                            if (Flags.nfcAssociatedRoleServices()) {
                                this.mWantsRoleHolderPriority = typedArrayObtainAttributes.getBoolean(6, false);
                            }
                            typedArrayObtainAttributes.recycle();
                        } else {
                            TypedArray typedArrayObtainAttributes2 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.OffHostApduService);
                            this.mService = resolveInfo;
                            this.mDescription = typedArrayObtainAttributes2.getString(0);
                            this.mRequiresDeviceUnlock = typedArrayObtainAttributes2.getBoolean(2, false);
                            this.mRequiresDeviceScreenOn = typedArrayObtainAttributes2.getBoolean(5, false);
                            this.mBannerResourceId = typedArrayObtainAttributes2.getResourceId(3, -1);
                            this.mSettingsActivityName = typedArrayObtainAttributes2.getString(1);
                            this.mOffHostName = typedArrayObtainAttributes2.getString(4);
                            this.mShouldDefaultToObserveMode = typedArrayObtainAttributes2.getBoolean(6, false);
                            String str = this.mOffHostName;
                            if (str != null) {
                                if (str.equals("eSE")) {
                                    this.mOffHostName = "eSE1";
                                } else if (this.mOffHostName.equals("SIM")) {
                                    this.mOffHostName = "SIM1";
                                }
                            }
                            this.mStaticOffHostName = this.mOffHostName;
                            if (Flags.nfcAssociatedRoleServices()) {
                                this.mWantsRoleHolderPriority = typedArrayObtainAttributes2.getBoolean(7, false);
                            }
                            typedArrayObtainAttributes2.recycle();
                        }
                        this.mStaticAidGroups = new HashMap<>();
                        this.mDynamicAidGroups = new HashMap<>();
                        this.mAutoTransact = new HashMap();
                        this.mAutoTransactPatterns = new TreeMap(Comparator.comparing(new Function() { // from class: android.nfc.cardemulation.ApduServiceInfo$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((Pattern) obj).toString();
                            }
                        }));
                        this.mOnHost = z;
                        int depth = xmlResourceParserLoadXmlMetaData.getDepth();
                        AidGroup aidGroup = null;
                        while (true) {
                            next = xmlResourceParserLoadXmlMetaData.next();
                            if ((next != i2 && xmlResourceParserLoadXmlMetaData.getDepth() <= depth) || next == i) {
                                break;
                            }
                            String name2 = xmlResourceParserLoadXmlMetaData.getName();
                            if (next == 2 && "aid-group".equals(name2) && aidGroup == null) {
                                TypedArray typedArrayObtainAttributes3 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.AidGroup);
                                String string = typedArrayObtainAttributes3.getString(i);
                                String string2 = typedArrayObtainAttributes3.getString(0);
                                string = "payment".equals(string) ? string : "other";
                                AidGroup aidGroup2 = this.mStaticAidGroups.get(string);
                                if (aidGroup2 != null) {
                                    if (!"other".equals(string)) {
                                        Log.e(TAG, "Not allowing multiple aid-groups in the " + string + " category");
                                        aidGroup2 = null;
                                    }
                                } else {
                                    aidGroup2 = new AidGroup(string, string2);
                                }
                                typedArrayObtainAttributes3.recycle();
                                aidGroup = aidGroup2;
                            } else if (next == i2 || !"aid-group".equals(name2) || aidGroup == null) {
                                if (next != 2 && "aid-filter".equals(name2) && aidGroup != null) {
                                    TypedArray typedArrayObtainAttributes4 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.AidFilter);
                                    String upperCase2 = typedArrayObtainAttributes4.getString(0).toUpperCase();
                                    if (isValidAid(upperCase2) && !aidGroup.getAids().contains(upperCase2)) {
                                        aidGroup.getAids().add(upperCase2);
                                    } else {
                                        Log.e(TAG, "Ignoring invalid or duplicate aid: " + upperCase2);
                                    }
                                    typedArrayObtainAttributes4.recycle();
                                } else if (next != 2 && "aid-prefix-filter".equals(name2) && aidGroup != null) {
                                    TypedArray typedArrayObtainAttributes5 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.AidFilter);
                                    String strConcat = typedArrayObtainAttributes5.getString(0).toUpperCase().concat("*");
                                    if (isValidAid(strConcat) && !aidGroup.getAids().contains(strConcat)) {
                                        aidGroup.getAids().add(strConcat);
                                    } else {
                                        Log.e(TAG, "Ignoring invalid or duplicate aid: " + strConcat);
                                    }
                                    typedArrayObtainAttributes5.recycle();
                                } else if (next != 2 && name2.equals("aid-suffix-filter") && aidGroup != null) {
                                    TypedArray typedArrayObtainAttributes6 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.AidFilter);
                                    String strConcat2 = typedArrayObtainAttributes6.getString(0).toUpperCase().concat("#");
                                    if (isValidAid(strConcat2) && !aidGroup.getAids().contains(strConcat2)) {
                                        aidGroup.getAids().add(strConcat2);
                                    } else {
                                        Log.e(TAG, "Ignoring invalid or duplicate aid: " + strConcat2);
                                    }
                                    typedArrayObtainAttributes6.recycle();
                                } else if (next != 2 && "polling-loop-filter".equals(name2) && aidGroup == null) {
                                    TypedArray typedArrayObtainAttributes7 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.PollingLoopFilter);
                                    String upperCase3 = typedArrayObtainAttributes7.getString(0).toUpperCase(Locale.ROOT);
                                    boolean z2 = typedArrayObtainAttributes7.getBoolean(i, false);
                                    if (!PLF_PATTERN.matcher(upperCase3).matches() || upperCase3.length() % 2 != 0) {
                                        Log.e(TAG, "Ignoring polling-loop-filter " + upperCase3 + " it is not a valid filter");
                                    } else if (!this.mOnHost && !z2) {
                                        Log.e(TAG, "Ignoring polling-loop-filter " + upperCase3 + " for offhost service that isn't autoTransact");
                                    } else {
                                        this.mAutoTransact.put(upperCase3, Boolean.valueOf(z2));
                                    }
                                    typedArrayObtainAttributes7.recycle();
                                } else if (next == 2 && "polling-loop-pattern-filter".equals(name2) && aidGroup == null) {
                                    TypedArray typedArrayObtainAttributes8 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.PollingLoopPatternFilter);
                                    upperCase = typedArrayObtainAttributes8.getString(0).toUpperCase(Locale.ROOT);
                                    boolean z3 = typedArrayObtainAttributes8.getBoolean(1, false);
                                    if (PLPF_PATTERN.matcher(upperCase).matches()) {
                                        Log.e(TAG, "Ignoring polling-loop-pattern-filter " + upperCase + " it is not a valid pattern filter");
                                    } else if (!this.mOnHost && !z3) {
                                        Log.e(TAG, "Ignoring polling-loop-pattern-filter " + upperCase + " for offhost service that isn't autoTransact");
                                    } else {
                                        this.mAutoTransactPatterns.put(Pattern.compile(upperCase), Boolean.valueOf(z3));
                                    }
                                    typedArrayObtainAttributes8.recycle();
                                }
                                i = 1;
                                i2 = 3;
                            } else {
                                if (aidGroup.getAids().size() > 0) {
                                    if (!this.mStaticAidGroups.containsKey(aidGroup.getCategory())) {
                                        this.mStaticAidGroups.put(aidGroup.getCategory(), aidGroup);
                                    }
                                } else {
                                    Log.e(TAG, "Not adding <aid-group> with empty or invalid AIDs");
                                }
                                aidGroup = null;
                            }
                        }
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                        this.mUid = serviceInfo.applicationInfo.uid;
                        this.mCategoryOtherServiceEnabled = true;
                    } catch (PackageManager.NameNotFoundException unused) {
                        throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
                    }
                } catch (Throwable th) {
                    th = th;
                    if (xmlResourceParser3 != null) {
                        xmlResourceParser3.close();
                    }
                    throw th;
                }
            }
            try {
                xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, "android.nfc.cardemulation.off_host_apdu_service");
                if (xmlResourceParserLoadXmlMetaData == null) {
                    throw new XmlPullParserException("No android.nfc.cardemulation.off_host_apdu_service meta-data");
                }
                eventType = xmlResourceParserLoadXmlMetaData.getEventType();
                while (true) {
                    i = 1;
                    if (eventType == 2) {
                        break;
                    }
                    break;
                    break;
                    eventType = xmlResourceParserLoadXmlMetaData.next();
                }
                String name3 = xmlResourceParserLoadXmlMetaData.getName();
                if (z) {
                    throw new XmlPullParserException("Meta-data does not start with <host-apdu-service> tag");
                }
                if (!z) {
                    throw new XmlPullParserException("Meta-data does not start with <offhost-apdu-service> tag");
                }
                resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData);
                i2 = 3;
                if (!z) {
                }
                this.mStaticAidGroups = new HashMap<>();
                this.mDynamicAidGroups = new HashMap<>();
                this.mAutoTransact = new HashMap();
                this.mAutoTransactPatterns = new TreeMap(Comparator.comparing(new Function() { // from class: android.nfc.cardemulation.ApduServiceInfo$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((Pattern) obj).toString();
                    }
                }));
                this.mOnHost = z;
                int depth2 = xmlResourceParserLoadXmlMetaData.getDepth();
                AidGroup aidGroup3 = null;
                while (true) {
                    next = xmlResourceParserLoadXmlMetaData.next();
                    if (next != i2) {
                        String name22 = xmlResourceParserLoadXmlMetaData.getName();
                        if (next == 2) {
                            if (next == i2) {
                            }
                            if (next != 2) {
                                if (next != 2) {
                                    if (next != 2) {
                                        if (next != 2) {
                                            if (next == 2) {
                                                TypedArray typedArrayObtainAttributes82 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.PollingLoopPatternFilter);
                                                upperCase = typedArrayObtainAttributes82.getString(0).toUpperCase(Locale.ROOT);
                                                boolean z32 = typedArrayObtainAttributes82.getBoolean(1, false);
                                                if (PLPF_PATTERN.matcher(upperCase).matches()) {
                                                }
                                                typedArrayObtainAttributes82.recycle();
                                            }
                                            i = 1;
                                            i2 = 3;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        String name222 = xmlResourceParserLoadXmlMetaData.getName();
                        if (next == 2) {
                        }
                    }
                }
                if (xmlResourceParserLoadXmlMetaData != null) {
                }
                this.mUid = serviceInfo.applicationInfo.uid;
                this.mCategoryOtherServiceEnabled = true;
            } catch (PackageManager.NameNotFoundException unused2) {
                xmlResourceParser3 = null;
                throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
            } catch (Throwable th2) {
                th = th2;
                xmlResourceParser3 = null;
                if (xmlResourceParser3 != null) {
                }
                throw th;
            }
        } catch (PackageManager.NameNotFoundException unused3) {
            xmlResourceParser3 = xmlResourceParser2;
        } catch (Throwable th3) {
            th = th3;
            xmlResourceParser3 = xmlResourceParser;
        }
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
            long jStart = protoOutputStream.start(2246267895814L);
            aidGroup.dump(protoOutputStream);
            protoOutputStream.end(jStart);
        }
        for (AidGroup aidGroup2 : this.mDynamicAidGroups.values()) {
            long jStart2 = protoOutputStream.start(2246267895814L);
            aidGroup2.dump(protoOutputStream);
            protoOutputStream.end(jStart2);
        }
        protoOutputStream.write(1138166333448L, this.mSettingsActivityName);
        protoOutputStream.write(1133871366153L, this.mShouldDefaultToObserveMode);
        long jStart3 = protoOutputStream.start(2246267895818L);
        for (Map.Entry<String, Boolean> entry : this.mAutoTransact.entrySet()) {
            protoOutputStream.write(1138166333441L, entry.getKey());
            protoOutputStream.write(1133871366146L, entry.getValue().booleanValue());
        }
        protoOutputStream.end(jStart3);
        long jStart4 = protoOutputStream.start(2246267895819L);
        for (Map.Entry<Pattern, Boolean> entry2 : this.mAutoTransactPatterns.entrySet()) {
            protoOutputStream.write(1138166333441L, entry2.getKey().pattern());
            protoOutputStream.write(1133871366146L, entry2.getValue().booleanValue());
        }
        protoOutputStream.end(jStart4);
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
