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
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Printer;
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

    /* JADX WARN: Removed duplicated region for block: B:103:0x0288  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public InputMethodInfo(android.content.Context r28, android.content.pm.ResolveInfo r29, java.util.List<android.view.inputmethod.InputMethodSubtype> r30) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 652
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodInfo.<init>(android.content.Context, android.content.pm.ResolveInfo, java.util.List):void");
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
