package android.view.inputmethod;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Printer;
import android.util.Xml;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class InputMethodInfo implements Parcelable {
    public static final String ACTION_IME_LANGUAGE_SETTINGS = "android.view.inputmethod.action.IME_LANGUAGE_SETTINGS";
    public static final String ACTION_STYLUS_HANDWRITING_SETTINGS = "android.view.inputmethod.action.STYLUS_HANDWRITING_SETTINGS";
    public static final int COMPONENT_NAME_MAX_LENGTH = 1000;
    public static final Parcelable.Creator<InputMethodInfo> CREATOR = new Parcelable.Creator<InputMethodInfo>() { // from class: android.view.inputmethod.InputMethodInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodInfo createFromParcel(Parcel parcel) {
            return new InputMethodInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodInfo[] newArray(int i) {
            return new InputMethodInfo[i];
        }
    };
    public static final int MAX_IMES_PER_PACKAGE = 20;
    static final String TAG = "InputMethodInfo";
    private final boolean mForceDefault;
    private final int mHandledConfigChanges;
    final String mId;
    private final boolean mInlineSuggestionsEnabled;
    private final boolean mIsAuxIme;
    final int mIsDefaultResId;
    final boolean mIsVirtualDeviceOnly;
    final boolean mIsVrOnly;
    private final String mLanguageSettingsActivityName;
    final ResolveInfo mService;
    final String mSettingsActivityName;
    private final boolean mShowInInputMethodPicker;
    private final String mStylusHandwritingSettingsActivityAttr;
    private final InputMethodSubtypeArray mSubtypes;
    private final boolean mSupportsConnectionlessStylusHandwriting;
    private final boolean mSupportsInlineSuggestionsWithTouchExploration;
    private final boolean mSupportsStylusHandwriting;
    private final boolean mSupportsSwitchingToNextInputMethod;
    private final boolean mSuppressesSpellChecker;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String computeId(ResolveInfo resolveInfo) {
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
    }

    public InputMethodInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        this(context, resolveInfo, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x01c2, code lost:
    
        r17 = r14;
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01c5, code lost:
    
        if (r18 == null) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01c7, code lost:
    
        r18.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01ce, code lost:
    
        if (r5.size() != 0) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01d0, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01d2, code lost:
    
        r1 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01d4, code lost:
    
        if (r30 == null) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01d6, code lost:
    
        r4 = r30.size();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01da, code lost:
    
        if (r9 >= r4) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01dc, code lost:
    
        r8 = r30.get(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01e6, code lost:
    
        if (r5.contains(r8) != false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01e8, code lost:
    
        r5.add(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01ec, code lost:
    
        android.util.Slog.w(android.view.inputmethod.InputMethodInfo.TAG, "Duplicated subtype definition found: " + r8.getLocale() + ", " + r8.getMode());
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x020f, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0212, code lost:
    
        r27.mSubtypes = new android.view.inputmethod.InputMethodSubtypeArray(r5);
        r27.mSettingsActivityName = r12;
        r27.mLanguageSettingsActivityName = r13;
        r27.mStylusHandwritingSettingsActivityAttr = r15;
        r27.mIsDefaultResId = r6;
        r27.mIsAuxIme = r1;
        r27.mSupportsSwitchingToNextInputMethod = r11;
        r27.mInlineSuggestionsEnabled = r7;
        r27.mSupportsInlineSuggestionsWithTouchExploration = r3;
        r27.mSuppressesSpellChecker = r17;
        r27.mShowInInputMethodPicker = r15;
        r27.mIsVrOnly = r15;
        r27.mIsVirtualDeviceOnly = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0239, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0288  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public InputMethodInfo(Context context, ResolveInfo resolveInfo, List<InputMethodSubtype> list) throws Throwable {
        XmlResourceParser xmlResourceParser;
        ServiceInfo serviceInfo;
        XmlResourceParser xmlResourceParser2;
        int next;
        String string;
        this.mService = resolveInfo;
        ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
        this.mId = computeId(resolveInfo);
        this.mForceDefault = false;
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        try {
            XmlResourceParser xmlResourceParserLoadXmlMetaData = serviceInfo2.loadXmlMetaData(packageManager, InputMethod.SERVICE_META_DATA);
            try {
                try {
                    if (xmlResourceParserLoadXmlMetaData == null) {
                        throw new XmlPullParserException("No android.view.im meta-data");
                    }
                    try {
                        try {
                            Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo2.applicationInfo);
                            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData);
                            do {
                                next = xmlResourceParserLoadXmlMetaData.next();
                                if (next == 1) {
                                    break;
                                }
                            } while (next != 2);
                            if (!"input-method".equals(xmlResourceParserLoadXmlMetaData.getName())) {
                                throw new XmlPullParserException("Meta-data does not start with input-method tag");
                            }
                            TypedArray typedArrayObtainAttributes = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.InputMethod);
                            String string2 = typedArrayObtainAttributes.getString(2);
                            if (Flags.imeSwitcherRevampApi()) {
                                try {
                                    try {
                                        string = typedArrayObtainAttributes.getString(13);
                                    } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException unused) {
                                        serviceInfo = serviceInfo2;
                                        xmlResourceParser = xmlResourceParserLoadXmlMetaData;
                                        try {
                                            throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
                                        } catch (Throwable th) {
                                            th = th;
                                            if (xmlResourceParser != null) {
                                                xmlResourceParser.close();
                                            }
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    xmlResourceParser = xmlResourceParserLoadXmlMetaData;
                                    if (xmlResourceParser != null) {
                                    }
                                    throw th;
                                }
                            } else {
                                string = null;
                            }
                            if ((serviceInfo2.name != null && serviceInfo2.name.length() > 1000) || ((string2 != null && string2.length() > 1000) || (string != null && string.length() > 1000))) {
                                throw new XmlPullParserException("Activity name exceeds maximum of 1000 characters");
                            }
                            boolean z = typedArrayObtainAttributes.getBoolean(4, false);
                            boolean z2 = typedArrayObtainAttributes.getBoolean(11, false);
                            int resourceId = typedArrayObtainAttributes.getResourceId(1, 0);
                            boolean z3 = typedArrayObtainAttributes.getBoolean(3, false);
                            xmlResourceParser2 = xmlResourceParserLoadXmlMetaData;
                            try {
                                boolean z4 = typedArrayObtainAttributes.getBoolean(5, false);
                                boolean z5 = typedArrayObtainAttributes.getBoolean(9, false);
                                boolean z6 = typedArrayObtainAttributes.getBoolean(6, false);
                                boolean z7 = typedArrayObtainAttributes.getBoolean(7, true);
                                this.mHandledConfigChanges = typedArrayObtainAttributes.getInt(0, 0);
                                this.mSupportsStylusHandwriting = typedArrayObtainAttributes.getBoolean(8, false);
                                this.mSupportsConnectionlessStylusHandwriting = typedArrayObtainAttributes.getBoolean(12, false);
                                String string3 = typedArrayObtainAttributes.getString(10);
                                typedArrayObtainAttributes.recycle();
                                int depth = xmlResourceParser2.getDepth();
                                boolean z8 = true;
                                while (true) {
                                    int next2 = xmlResourceParser2.next();
                                    if ((next2 == 3 && xmlResourceParser2.getDepth() <= depth) || next2 == 1) {
                                        break;
                                    }
                                    if (next2 == 2) {
                                        if (!"subtype".equals(xmlResourceParser2.getName())) {
                                            throw new XmlPullParserException("Meta-data in input-method does not start with subtype tag");
                                        }
                                        TypedArray typedArrayObtainAttributes2 = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.InputMethod_Subtype);
                                        Resources resources = resourcesForApplication;
                                        String string4 = typedArrayObtainAttributes2.getString(10);
                                        String string5 = typedArrayObtainAttributes2.getString(11);
                                        AttributeSet attributeSet = attributeSetAsAttributeSet;
                                        int i = depth;
                                        boolean z9 = z6;
                                        InputMethodSubtype inputMethodSubtypeBuild = new InputMethodSubtype.InputMethodSubtypeBuilder().setSubtypeNameResId(typedArrayObtainAttributes2.getResourceId(0, 0)).setSubtypeIconResId(typedArrayObtainAttributes2.getResourceId(1, 0)).setPhysicalKeyboardHint(string4 == null ? null : new ULocale(string4), string5 == null ? "" : string5).setLanguageTag(typedArrayObtainAttributes2.getString(9)).setSubtypeLocale(typedArrayObtainAttributes2.getString(2)).setSubtypeMode(typedArrayObtainAttributes2.getString(3)).setSubtypeExtraValue(typedArrayObtainAttributes2.getString(4)).setIsAuxiliary(typedArrayObtainAttributes2.getBoolean(5, false)).setOverridesImplicitlyEnabledSubtype(typedArrayObtainAttributes2.getBoolean(6, false)).setSubtypeId(typedArrayObtainAttributes2.getInt(7, 0)).setIsAsciiCapable(typedArrayObtainAttributes2.getBoolean(8, false)).build();
                                        typedArrayObtainAttributes2.recycle();
                                        z8 = inputMethodSubtypeBuild.isAuxiliary() ? z8 : false;
                                        arrayList.add(inputMethodSubtypeBuild);
                                        z6 = z9;
                                        resourcesForApplication = resources;
                                        attributeSetAsAttributeSet = attributeSet;
                                        depth = i;
                                    }
                                }
                            } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException unused2) {
                                serviceInfo = serviceInfo2;
                                xmlResourceParser = xmlResourceParser2;
                                throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            xmlResourceParser = xmlResourceParserLoadXmlMetaData;
                            if (xmlResourceParser != null) {
                            }
                            throw th;
                        }
                    } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException unused3) {
                        serviceInfo = serviceInfo2;
                        xmlResourceParser2 = xmlResourceParserLoadXmlMetaData;
                    }
                } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException unused4) {
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException unused5) {
            serviceInfo = serviceInfo2;
            xmlResourceParser = null;
        } catch (Throwable th5) {
            th = th5;
            xmlResourceParser = null;
        }
    }

    public InputMethodInfo(InputMethodInfo inputMethodInfo) {
        this(inputMethodInfo, (List<InputMethodSubtype>) Collections.EMPTY_LIST);
    }

    public InputMethodInfo(InputMethodInfo inputMethodInfo, List<InputMethodSubtype> list) {
        this.mId = inputMethodInfo.mId;
        this.mSettingsActivityName = inputMethodInfo.mSettingsActivityName;
        this.mLanguageSettingsActivityName = inputMethodInfo.mLanguageSettingsActivityName;
        this.mIsDefaultResId = inputMethodInfo.mIsDefaultResId;
        this.mIsAuxIme = inputMethodInfo.mIsAuxIme;
        this.mSupportsSwitchingToNextInputMethod = inputMethodInfo.mSupportsSwitchingToNextInputMethod;
        this.mInlineSuggestionsEnabled = inputMethodInfo.mInlineSuggestionsEnabled;
        this.mSupportsInlineSuggestionsWithTouchExploration = inputMethodInfo.mSupportsInlineSuggestionsWithTouchExploration;
        this.mSuppressesSpellChecker = inputMethodInfo.mSuppressesSpellChecker;
        this.mShowInInputMethodPicker = inputMethodInfo.mShowInInputMethodPicker;
        this.mIsVrOnly = inputMethodInfo.mIsVrOnly;
        this.mIsVirtualDeviceOnly = inputMethodInfo.mIsVirtualDeviceOnly;
        this.mService = inputMethodInfo.mService;
        if (list.isEmpty()) {
            this.mSubtypes = inputMethodInfo.mSubtypes;
        } else {
            ArrayList<InputMethodSubtype> list2 = inputMethodInfo.mSubtypes.toList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                InputMethodSubtype inputMethodSubtype = list.get(i);
                if (!list2.contains(inputMethodSubtype)) {
                    list2.add(inputMethodSubtype);
                }
            }
            this.mSubtypes = new InputMethodSubtypeArray(list2);
        }
        this.mHandledConfigChanges = inputMethodInfo.mHandledConfigChanges;
        this.mSupportsStylusHandwriting = inputMethodInfo.mSupportsStylusHandwriting;
        this.mSupportsConnectionlessStylusHandwriting = inputMethodInfo.mSupportsConnectionlessStylusHandwriting;
        this.mForceDefault = inputMethodInfo.mForceDefault;
        this.mStylusHandwritingSettingsActivityAttr = inputMethodInfo.mStylusHandwritingSettingsActivityAttr;
    }

    InputMethodInfo(Parcel parcel) {
        this.mId = parcel.readString();
        this.mSettingsActivityName = parcel.readString();
        this.mLanguageSettingsActivityName = parcel.readString8();
        this.mIsDefaultResId = parcel.readInt();
        this.mIsAuxIme = parcel.readInt() == 1;
        this.mSupportsSwitchingToNextInputMethod = parcel.readInt() == 1;
        this.mInlineSuggestionsEnabled = parcel.readInt() == 1;
        this.mSupportsInlineSuggestionsWithTouchExploration = parcel.readInt() == 1;
        this.mSuppressesSpellChecker = parcel.readBoolean();
        this.mShowInInputMethodPicker = parcel.readBoolean();
        this.mIsVrOnly = parcel.readBoolean();
        this.mIsVirtualDeviceOnly = parcel.readBoolean();
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mSubtypes = new InputMethodSubtypeArray(parcel);
        this.mHandledConfigChanges = parcel.readInt();
        this.mSupportsStylusHandwriting = parcel.readBoolean();
        this.mSupportsConnectionlessStylusHandwriting = parcel.readBoolean();
        this.mStylusHandwritingSettingsActivityAttr = parcel.readString8();
        this.mForceDefault = false;
    }

    public InputMethodInfo(String str, String str2, CharSequence charSequence, String str3) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, null, null, 0, false, true, false, false, false, 0, false, false, null, false);
    }

    public InputMethodInfo(String str, String str2, CharSequence charSequence, String str3, boolean z, String str4) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, null, null, 0, false, true, false, false, false, 0, z, false, str4, false);
    }

    public InputMethodInfo(String str, String str2, CharSequence charSequence, String str3, String str4, boolean z, String str5) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, str4, null, 0, false, true, false, false, false, 0, z, false, str5, false);
    }

    public InputMethodInfo(String str, String str2, CharSequence charSequence, String str3, String str4, boolean z, boolean z2, String str5) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, str4, null, 0, false, true, false, false, false, 0, z, z2, str5, false);
    }

    public InputMethodInfo(String str, String str2, CharSequence charSequence, String str3, int i) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, null, null, 0, false, true, false, false, false, i, false, false, null, false);
    }

    public InputMethodInfo(ResolveInfo resolveInfo, boolean z, String str, List<InputMethodSubtype> list, int i, boolean z2) {
        this(resolveInfo, z, str, null, list, i, z2, true, false, false, false, 0, false, false, null, false);
    }

    public InputMethodInfo(ResolveInfo resolveInfo, boolean z, String str, List<InputMethodSubtype> list, int i, boolean z2, boolean z3, boolean z4) {
        this(resolveInfo, z, str, null, list, i, z2, z3, false, z4, false, 0, false, false, null, false);
    }

    public InputMethodInfo(ResolveInfo resolveInfo, boolean z, String str, String str2, List<InputMethodSubtype> list, int i, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i2, boolean z7, boolean z8, String str3, boolean z9) {
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mService = resolveInfo;
        this.mId = new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
        this.mSettingsActivityName = str;
        this.mLanguageSettingsActivityName = str2;
        this.mIsDefaultResId = i;
        this.mIsAuxIme = z;
        this.mSubtypes = new InputMethodSubtypeArray(list);
        this.mForceDefault = z2;
        this.mSupportsSwitchingToNextInputMethod = z3;
        this.mInlineSuggestionsEnabled = z4;
        this.mSupportsInlineSuggestionsWithTouchExploration = z9;
        this.mSuppressesSpellChecker = false;
        this.mShowInInputMethodPicker = true;
        this.mIsVrOnly = z5;
        this.mIsVirtualDeviceOnly = z6;
        this.mHandledConfigChanges = i2;
        this.mSupportsStylusHandwriting = z7;
        this.mSupportsConnectionlessStylusHandwriting = z8;
        this.mStylusHandwritingSettingsActivityAttr = str3;
    }

    private static ResolveInfo buildFakeResolveInfo(String str, String str2, CharSequence charSequence) {
        ResolveInfo resolveInfo = new ResolveInfo();
        ServiceInfo serviceInfo = new ServiceInfo();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.packageName = str;
        applicationInfo.enabled = true;
        serviceInfo.applicationInfo = applicationInfo;
        serviceInfo.enabled = true;
        serviceInfo.packageName = str;
        serviceInfo.name = str2;
        serviceInfo.exported = true;
        serviceInfo.nonLocalizedLabel = charSequence;
        resolveInfo.serviceInfo = serviceInfo;
        return resolveInfo;
    }

    public String getId() {
        return this.mId;
    }

    public String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public String getServiceName() {
        return this.mService.serviceInfo.name;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public boolean isVrOnly() {
        return this.mIsVrOnly;
    }

    @SystemApi
    public boolean isVirtualDeviceOnly() {
        return this.mIsVirtualDeviceOnly;
    }

    public int getSubtypeCount() {
        return this.mSubtypes.getCount();
    }

    public InputMethodSubtype getSubtypeAt(int i) {
        return this.mSubtypes.get(i);
    }

    public int getIsDefaultResourceId() {
        return this.mIsDefaultResId;
    }

    public boolean isDefault(Context context) {
        if (this.mForceDefault) {
            return true;
        }
        try {
            if (getIsDefaultResourceId() == 0) {
                return false;
            }
            return context.createPackageContext(getPackageName(), 0).getResources().getBoolean(getIsDefaultResourceId());
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            return false;
        }
    }

    public int getConfigChanges() {
        return this.mHandledConfigChanges;
    }

    public boolean supportsStylusHandwriting() {
        return this.mSupportsStylusHandwriting;
    }

    public boolean supportsConnectionlessStylusHandwriting() {
        return this.mSupportsConnectionlessStylusHandwriting;
    }

    public Intent createStylusHandwritingSettingsActivityIntent() {
        if (TextUtils.isEmpty(this.mStylusHandwritingSettingsActivityAttr) || !this.mSupportsStylusHandwriting) {
            return null;
        }
        return new Intent(ACTION_STYLUS_HANDWRITING_SETTINGS).setComponent(new ComponentName(getServiceInfo().packageName, this.mStylusHandwritingSettingsActivityAttr));
    }

    public Intent createImeLanguageSettingsActivityIntent() {
        String str = !TextUtils.isEmpty(this.mLanguageSettingsActivityName) ? this.mLanguageSettingsActivityName : this.mSettingsActivityName;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return new Intent(ACTION_IME_LANGUAGE_SETTINGS).setComponent(new ComponentName(getServiceInfo().packageName, str));
    }

    public void dump(Printer printer, String str) {
        printer.println(str + "mId=" + this.mId + " mSettingsActivityName=" + this.mSettingsActivityName + " mLanguageSettingsActivityName=" + this.mLanguageSettingsActivityName + " mIsVrOnly=" + this.mIsVrOnly + " mIsVirtualDeviceOnly=" + this.mIsVirtualDeviceOnly + " mSupportsSwitchingToNextInputMethod=" + this.mSupportsSwitchingToNextInputMethod + " mInlineSuggestionsEnabled=" + this.mInlineSuggestionsEnabled + " mSupportsInlineSuggestionsWithTouchExploration=" + this.mSupportsInlineSuggestionsWithTouchExploration + " mSuppressesSpellChecker=" + this.mSuppressesSpellChecker + " mShowInInputMethodPicker=" + this.mShowInInputMethodPicker + " mSupportsStylusHandwriting=" + this.mSupportsStylusHandwriting + " mSupportsConnectionlessStylusHandwriting=" + this.mSupportsConnectionlessStylusHandwriting + " mStylusHandwritingSettingsActivityAttr=" + this.mStylusHandwritingSettingsActivityAttr);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("mIsDefaultResId=0x");
        sb.append(Integer.toHexString(this.mIsDefaultResId));
        printer.println(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("Service:");
        printer.println(sb2.toString());
        ResolveInfo resolveInfo = this.mService;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("  ");
        resolveInfo.dump(printer, sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append("InputMethodSubtype array: count=");
        sb4.append(this.mSubtypes.getCount());
        printer.println(sb4.toString());
        InputMethodSubtypeArray inputMethodSubtypeArray = this.mSubtypes;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(str);
        sb5.append("  ");
        inputMethodSubtypeArray.dump(printer, sb5.toString());
    }

    public String toString() {
        return "InputMethodInfo{" + this.mId + ", settings: " + this.mSettingsActivityName + ", languageSettings: " + this.mLanguageSettingsActivityName + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && (obj instanceof InputMethodInfo)) {
            return this.mId.equals(((InputMethodInfo) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean isSystem() {
        return (this.mService.serviceInfo.applicationInfo.flags & 1) != 0;
    }

    public boolean isAuxiliaryIme() {
        return this.mIsAuxIme;
    }

    public boolean supportsSwitchingToNextInputMethod() {
        return this.mSupportsSwitchingToNextInputMethod;
    }

    public boolean isInlineSuggestionsEnabled() {
        return this.mInlineSuggestionsEnabled;
    }

    public boolean supportsInlineSuggestionsWithTouchExploration() {
        return this.mSupportsInlineSuggestionsWithTouchExploration;
    }

    public boolean suppressesSpellChecker() {
        return this.mSuppressesSpellChecker;
    }

    public boolean shouldShowInInputMethodPicker() {
        return this.mShowInInputMethodPicker;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString8(this.mLanguageSettingsActivityName);
        parcel.writeInt(this.mIsDefaultResId);
        parcel.writeInt(this.mIsAuxIme ? 1 : 0);
        parcel.writeInt(this.mSupportsSwitchingToNextInputMethod ? 1 : 0);
        parcel.writeInt(this.mInlineSuggestionsEnabled ? 1 : 0);
        parcel.writeInt(this.mSupportsInlineSuggestionsWithTouchExploration ? 1 : 0);
        parcel.writeBoolean(this.mSuppressesSpellChecker);
        parcel.writeBoolean(this.mShowInInputMethodPicker);
        parcel.writeBoolean(this.mIsVrOnly);
        parcel.writeBoolean(this.mIsVirtualDeviceOnly);
        this.mService.writeToParcel(parcel, i);
        this.mSubtypes.writeToParcel(parcel);
        parcel.writeInt(this.mHandledConfigChanges);
        parcel.writeBoolean(this.mSupportsStylusHandwriting);
        parcel.writeBoolean(this.mSupportsConnectionlessStylusHandwriting);
        parcel.writeString8(this.mStylusHandwritingSettingsActivityAttr);
    }
}
