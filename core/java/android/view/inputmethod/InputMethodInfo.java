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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InputMethodInfo implements Parcelable {
    public static final String ACTION_IME_LANGUAGE_SETTINGS =
            "android.view.inputmethod.action.IME_LANGUAGE_SETTINGS";
    public static final String ACTION_STYLUS_HANDWRITING_SETTINGS =
            "android.view.inputmethod.action.STYLUS_HANDWRITING_SETTINGS";
    public static final int COMPONENT_NAME_MAX_LENGTH = 1000;
    public static final Parcelable.Creator<InputMethodInfo> CREATOR =
            new Parcelable.Creator<
                    InputMethodInfo>() { // from class: android.view.inputmethod.InputMethodInfo.1
                @Override // android.os.Parcelable.Creator
                public InputMethodInfo createFromParcel(Parcel source) {
                    return new InputMethodInfo(source);
                }

                @Override // android.os.Parcelable.Creator
                public InputMethodInfo[] newArray(int size) {
                    return new InputMethodInfo[size];
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

    public static String computeId(ResolveInfo service) {
        ServiceInfo si = service.serviceInfo;
        return new ComponentName(si.packageName, si.name).flattenToShortString();
    }

    public InputMethodInfo(Context context, ResolveInfo service)
            throws XmlPullParserException, IOException {
        this(context, service, null);
    }

    /*  JADX ERROR: Types fix failed
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
    */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    public InputMethodInfo(
            android.content.Context r34,
            android.content.pm.ResolveInfo r35,
            java.util.List<android.view.inputmethod.InputMethodSubtype> r36)
            throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1031
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.inputmethod.InputMethodInfo.<init>(android.content.Context,"
                    + " android.content.pm.ResolveInfo, java.util.List):void");
    }

    public InputMethodInfo(InputMethodInfo source) {
        this(source, (List<InputMethodSubtype>) Collections.emptyList());
    }

    public InputMethodInfo(InputMethodInfo source, List<InputMethodSubtype> additionalSubtypes) {
        this.mId = source.mId;
        this.mSettingsActivityName = source.mSettingsActivityName;
        this.mLanguageSettingsActivityName = source.mLanguageSettingsActivityName;
        this.mIsDefaultResId = source.mIsDefaultResId;
        this.mIsAuxIme = source.mIsAuxIme;
        this.mSupportsSwitchingToNextInputMethod = source.mSupportsSwitchingToNextInputMethod;
        this.mInlineSuggestionsEnabled = source.mInlineSuggestionsEnabled;
        this.mSupportsInlineSuggestionsWithTouchExploration =
                source.mSupportsInlineSuggestionsWithTouchExploration;
        this.mSuppressesSpellChecker = source.mSuppressesSpellChecker;
        this.mShowInInputMethodPicker = source.mShowInInputMethodPicker;
        this.mIsVrOnly = source.mIsVrOnly;
        this.mIsVirtualDeviceOnly = source.mIsVirtualDeviceOnly;
        this.mService = source.mService;
        if (additionalSubtypes.isEmpty()) {
            this.mSubtypes = source.mSubtypes;
        } else {
            ArrayList<InputMethodSubtype> subtypes = source.mSubtypes.toList();
            int additionalSubtypeCount = additionalSubtypes.size();
            for (int i = 0; i < additionalSubtypeCount; i++) {
                InputMethodSubtype additionalSubtype = additionalSubtypes.get(i);
                if (!subtypes.contains(additionalSubtype)) {
                    subtypes.add(additionalSubtype);
                }
            }
            this.mSubtypes = new InputMethodSubtypeArray(subtypes);
        }
        this.mHandledConfigChanges = source.mHandledConfigChanges;
        this.mSupportsStylusHandwriting = source.mSupportsStylusHandwriting;
        this.mSupportsConnectionlessStylusHandwriting =
                source.mSupportsConnectionlessStylusHandwriting;
        this.mForceDefault = source.mForceDefault;
        this.mStylusHandwritingSettingsActivityAttr = source.mStylusHandwritingSettingsActivityAttr;
    }

    InputMethodInfo(Parcel source) {
        this.mId = source.readString();
        this.mSettingsActivityName = source.readString();
        this.mLanguageSettingsActivityName = source.readString8();
        this.mIsDefaultResId = source.readInt();
        this.mIsAuxIme = source.readInt() == 1;
        this.mSupportsSwitchingToNextInputMethod = source.readInt() == 1;
        this.mInlineSuggestionsEnabled = source.readInt() == 1;
        this.mSupportsInlineSuggestionsWithTouchExploration = source.readInt() == 1;
        this.mSuppressesSpellChecker = source.readBoolean();
        this.mShowInInputMethodPicker = source.readBoolean();
        this.mIsVrOnly = source.readBoolean();
        this.mIsVirtualDeviceOnly = source.readBoolean();
        this.mService = ResolveInfo.CREATOR.createFromParcel(source);
        this.mSubtypes = new InputMethodSubtypeArray(source);
        this.mHandledConfigChanges = source.readInt();
        this.mSupportsStylusHandwriting = source.readBoolean();
        this.mSupportsConnectionlessStylusHandwriting = source.readBoolean();
        this.mStylusHandwritingSettingsActivityAttr = source.readString8();
        this.mForceDefault = false;
    }

    public InputMethodInfo(
            String packageName, String className, CharSequence label, String settingsActivity) {
        this(
                buildFakeResolveInfo(packageName, className, label),
                false,
                settingsActivity,
                null,
                null,
                0,
                false,
                true,
                false,
                false,
                false,
                0,
                false,
                false,
                null,
                false);
    }

    public InputMethodInfo(
            String packageName,
            String className,
            CharSequence label,
            String settingsActivity,
            boolean supportStylusHandwriting,
            String stylusHandwritingSettingsActivityAttr) {
        this(
                buildFakeResolveInfo(packageName, className, label),
                false,
                settingsActivity,
                null,
                null,
                0,
                false,
                true,
                false,
                false,
                false,
                0,
                supportStylusHandwriting,
                false,
                stylusHandwritingSettingsActivityAttr,
                false);
    }

    public InputMethodInfo(
            String packageName,
            String className,
            CharSequence label,
            String settingsActivity,
            String languageSettingsActivity,
            boolean supportStylusHandwriting,
            String stylusHandwritingSettingsActivityAttr) {
        this(
                buildFakeResolveInfo(packageName, className, label),
                false,
                settingsActivity,
                languageSettingsActivity,
                null,
                0,
                false,
                true,
                false,
                false,
                false,
                0,
                supportStylusHandwriting,
                false,
                stylusHandwritingSettingsActivityAttr,
                false);
    }

    public InputMethodInfo(
            String packageName,
            String className,
            CharSequence label,
            String settingsActivity,
            String languageSettingsActivity,
            boolean supportStylusHandwriting,
            boolean supportConnectionlessStylusHandwriting,
            String stylusHandwritingSettingsActivityAttr) {
        this(
                buildFakeResolveInfo(packageName, className, label),
                false,
                settingsActivity,
                languageSettingsActivity,
                null,
                0,
                false,
                true,
                false,
                false,
                false,
                0,
                supportStylusHandwriting,
                supportConnectionlessStylusHandwriting,
                stylusHandwritingSettingsActivityAttr,
                false);
    }

    public InputMethodInfo(
            String packageName,
            String className,
            CharSequence label,
            String settingsActivity,
            int handledConfigChanges) {
        this(
                buildFakeResolveInfo(packageName, className, label),
                false,
                settingsActivity,
                null,
                null,
                0,
                false,
                true,
                false,
                false,
                false,
                handledConfigChanges,
                false,
                false,
                null,
                false);
    }

    public InputMethodInfo(
            ResolveInfo ri,
            boolean isAuxIme,
            String settingsActivity,
            List<InputMethodSubtype> subtypes,
            int isDefaultResId,
            boolean forceDefault) {
        this(
                ri,
                isAuxIme,
                settingsActivity,
                null,
                subtypes,
                isDefaultResId,
                forceDefault,
                true,
                false,
                false,
                false,
                0,
                false,
                false,
                null,
                false);
    }

    public InputMethodInfo(
            ResolveInfo ri,
            boolean isAuxIme,
            String settingsActivity,
            List<InputMethodSubtype> subtypes,
            int isDefaultResId,
            boolean forceDefault,
            boolean supportsSwitchingToNextInputMethod,
            boolean isVrOnly) {
        this(
                ri,
                isAuxIme,
                settingsActivity,
                null,
                subtypes,
                isDefaultResId,
                forceDefault,
                supportsSwitchingToNextInputMethod,
                false,
                isVrOnly,
                false,
                0,
                false,
                false,
                null,
                false);
    }

    public InputMethodInfo(
            ResolveInfo ri,
            boolean isAuxIme,
            String settingsActivity,
            String languageSettingsActivity,
            List<InputMethodSubtype> subtypes,
            int isDefaultResId,
            boolean forceDefault,
            boolean supportsSwitchingToNextInputMethod,
            boolean inlineSuggestionsEnabled,
            boolean isVrOnly,
            boolean isVirtualDeviceOnly,
            int handledConfigChanges,
            boolean supportsStylusHandwriting,
            boolean supportsConnectionlessStylusHandwriting,
            String stylusHandwritingSettingsActivityAttr,
            boolean supportsInlineSuggestionsWithTouchExploration) {
        ServiceInfo si = ri.serviceInfo;
        this.mService = ri;
        this.mId = new ComponentName(si.packageName, si.name).flattenToShortString();
        this.mSettingsActivityName = settingsActivity;
        this.mLanguageSettingsActivityName = languageSettingsActivity;
        this.mIsDefaultResId = isDefaultResId;
        this.mIsAuxIme = isAuxIme;
        this.mSubtypes = new InputMethodSubtypeArray(subtypes);
        this.mForceDefault = forceDefault;
        this.mSupportsSwitchingToNextInputMethod = supportsSwitchingToNextInputMethod;
        this.mInlineSuggestionsEnabled = inlineSuggestionsEnabled;
        this.mSupportsInlineSuggestionsWithTouchExploration =
                supportsInlineSuggestionsWithTouchExploration;
        this.mSuppressesSpellChecker = false;
        this.mShowInInputMethodPicker = true;
        this.mIsVrOnly = isVrOnly;
        this.mIsVirtualDeviceOnly = isVirtualDeviceOnly;
        this.mHandledConfigChanges = handledConfigChanges;
        this.mSupportsStylusHandwriting = supportsStylusHandwriting;
        this.mSupportsConnectionlessStylusHandwriting = supportsConnectionlessStylusHandwriting;
        this.mStylusHandwritingSettingsActivityAttr = stylusHandwritingSettingsActivityAttr;
    }

    private static ResolveInfo buildFakeResolveInfo(
            String packageName, String className, CharSequence label) {
        ResolveInfo ri = new ResolveInfo();
        ServiceInfo si = new ServiceInfo();
        ApplicationInfo ai = new ApplicationInfo();
        ai.packageName = packageName;
        ai.enabled = true;
        si.applicationInfo = ai;
        si.enabled = true;
        si.packageName = packageName;
        si.name = className;
        si.exported = true;
        si.nonLocalizedLabel = label;
        ri.serviceInfo = si;
        return ri;
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
        return new ComponentName(
                this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public CharSequence loadLabel(PackageManager pm) {
        return this.mService.loadLabel(pm);
    }

    public Drawable loadIcon(PackageManager pm) {
        return this.mService.loadIcon(pm);
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

    public InputMethodSubtype getSubtypeAt(int index) {
        return this.mSubtypes.get(index);
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
            Resources res = context.createPackageContext(getPackageName(), 0).getResources();
            return res.getBoolean(getIsDefaultResourceId());
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
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
        if (TextUtils.isEmpty(this.mStylusHandwritingSettingsActivityAttr)
                || !this.mSupportsStylusHandwriting) {
            return null;
        }
        return new Intent(ACTION_STYLUS_HANDWRITING_SETTINGS)
                .setComponent(
                        new ComponentName(
                                getServiceInfo().packageName,
                                this.mStylusHandwritingSettingsActivityAttr));
    }

    public Intent createImeLanguageSettingsActivityIntent() {
        if (TextUtils.isEmpty(this.mLanguageSettingsActivityName)) {
            return null;
        }
        return new Intent(ACTION_IME_LANGUAGE_SETTINGS)
                .setComponent(
                        new ComponentName(
                                getServiceInfo().packageName, this.mLanguageSettingsActivityName));
    }

    public void dump(Printer pw, String prefix) {
        pw.println(
                prefix
                        + "mId="
                        + this.mId
                        + " mSettingsActivityName="
                        + this.mSettingsActivityName
                        + " mLanguageSettingsActivityName="
                        + this.mLanguageSettingsActivityName
                        + " mIsVrOnly="
                        + this.mIsVrOnly
                        + " mIsVirtualDeviceOnly="
                        + this.mIsVirtualDeviceOnly
                        + " mSupportsSwitchingToNextInputMethod="
                        + this.mSupportsSwitchingToNextInputMethod
                        + " mInlineSuggestionsEnabled="
                        + this.mInlineSuggestionsEnabled
                        + " mSupportsInlineSuggestionsWithTouchExploration="
                        + this.mSupportsInlineSuggestionsWithTouchExploration
                        + " mSuppressesSpellChecker="
                        + this.mSuppressesSpellChecker
                        + " mShowInInputMethodPicker="
                        + this.mShowInInputMethodPicker
                        + " mSupportsStylusHandwriting="
                        + this.mSupportsStylusHandwriting
                        + " mSupportsConnectionlessStylusHandwriting="
                        + this.mSupportsConnectionlessStylusHandwriting
                        + " mStylusHandwritingSettingsActivityAttr="
                        + this.mStylusHandwritingSettingsActivityAttr);
        pw.println(prefix + "mIsDefaultResId=0x" + Integer.toHexString(this.mIsDefaultResId));
        pw.println(prefix + "Service:");
        this.mService.dump(pw, prefix + "  ");
        pw.println(prefix + "InputMethodSubtype array: count=" + this.mSubtypes.getCount());
        this.mSubtypes.dump(pw, prefix + "  ");
    }

    public String toString() {
        return "InputMethodInfo{"
                + this.mId
                + ", settings: "
                + this.mSettingsActivityName
                + ", languageSettings: "
                + this.mLanguageSettingsActivityName
                + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof InputMethodInfo)) {
            return false;
        }
        InputMethodInfo obj = (InputMethodInfo) o;
        return this.mId.equals(obj.mId);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
