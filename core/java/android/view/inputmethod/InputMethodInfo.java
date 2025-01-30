package android.view.inputmethod;

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
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public final class InputMethodInfo implements Parcelable {
  public static final String ACTION_STYLUS_HANDWRITING_SETTINGS =
      "android.view.inputmethod.action.STYLUS_HANDWRITING_SETTINGS";
  public static final int COMPONENT_NAME_MAX_LENGTH = 1000;
  public static final Parcelable.Creator<InputMethodInfo> CREATOR =
      new Parcelable.Creator<
          InputMethodInfo>() { // from class: android.view.inputmethod.InputMethodInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodInfo createFromParcel(Parcel source) {
          return new InputMethodInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
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
  final boolean mIsVrOnly;
  final ResolveInfo mService;
  final String mSettingsActivityName;
  private final boolean mShowInInputMethodPicker;
  private final String mStylusHandwritingSettingsActivityAttr;
  private final InputMethodSubtypeArray mSubtypes;
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
  	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
  	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
  	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
   */
  /* JADX WARN: Not initialized variable reg: 19, insn: 0x0389: MOVE (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r19 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('supportsSwitchingToNextInputMethod' boolean)]), block:B:165:0x0389 */
  /* JADX WARN: Not initialized variable reg: 19, insn: 0x038f: MOVE (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r19 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('supportsSwitchingToNextInputMethod' boolean)]), block:B:167:0x038f */
  /* JADX WARN: Not initialized variable reg: 20, insn: 0x038b: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r20 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isAuxIme' boolean)]), block:B:165:0x0389 */
  /* JADX WARN: Not initialized variable reg: 20, insn: 0x0391: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r20 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY] A[D('isAuxIme' boolean)]), block:B:167:0x038f */
  public InputMethodInfo(
      android.content.Context r32,
      android.content.pm.ResolveInfo r33,
      java.util.List<android.view.inputmethod.InputMethodSubtype> r34)
      throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
    /*
        Method dump skipped, instructions count: 975
        To view this dump add '--comments-level debug' option
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " android.view.inputmethod.InputMethodInfo.<init>(android.content.Context,"
            + " android.content.pm.ResolveInfo, java.util.List):void");
  }

  public InputMethodInfo(InputMethodInfo source) {
    this.mId = source.mId;
    this.mSettingsActivityName = source.mSettingsActivityName;
    this.mIsDefaultResId = source.mIsDefaultResId;
    this.mIsAuxIme = source.mIsAuxIme;
    this.mSupportsSwitchingToNextInputMethod = source.mSupportsSwitchingToNextInputMethod;
    this.mInlineSuggestionsEnabled = source.mInlineSuggestionsEnabled;
    this.mSupportsInlineSuggestionsWithTouchExploration =
        source.mSupportsInlineSuggestionsWithTouchExploration;
    this.mSuppressesSpellChecker = source.mSuppressesSpellChecker;
    this.mShowInInputMethodPicker = source.mShowInInputMethodPicker;
    this.mIsVrOnly = source.mIsVrOnly;
    this.mService = source.mService;
    this.mSubtypes = source.mSubtypes;
    this.mHandledConfigChanges = source.mHandledConfigChanges;
    this.mSupportsStylusHandwriting = source.mSupportsStylusHandwriting;
    this.mForceDefault = source.mForceDefault;
    this.mStylusHandwritingSettingsActivityAttr = source.mStylusHandwritingSettingsActivityAttr;
  }

  InputMethodInfo(Parcel source) {
    this.mId = source.readString();
    this.mSettingsActivityName = source.readString();
    this.mIsDefaultResId = source.readInt();
    this.mIsAuxIme = source.readInt() == 1;
    this.mSupportsSwitchingToNextInputMethod = source.readInt() == 1;
    this.mInlineSuggestionsEnabled = source.readInt() == 1;
    this.mSupportsInlineSuggestionsWithTouchExploration = source.readInt() == 1;
    this.mSuppressesSpellChecker = source.readBoolean();
    this.mShowInInputMethodPicker = source.readBoolean();
    this.mIsVrOnly = source.readBoolean();
    this.mService = ResolveInfo.CREATOR.createFromParcel(source);
    this.mSubtypes = new InputMethodSubtypeArray(source);
    this.mHandledConfigChanges = source.readInt();
    this.mSupportsStylusHandwriting = source.readBoolean();
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
        0,
        false,
        true,
        false,
        false,
        0,
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
        0,
        false,
        true,
        false,
        false,
        0,
        supportStylusHandwriting,
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
        0,
        false,
        true,
        false,
        false,
        handledConfigChanges,
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
        subtypes,
        isDefaultResId,
        forceDefault,
        true,
        false,
        false,
        0,
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
        subtypes,
        isDefaultResId,
        forceDefault,
        supportsSwitchingToNextInputMethod,
        false,
        isVrOnly,
        0,
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
      boolean inlineSuggestionsEnabled,
      boolean isVrOnly,
      int handledConfigChanges,
      boolean supportsStylusHandwriting,
      String stylusHandwritingSettingsActivityAttr,
      boolean supportsInlineSuggestionsWithTouchExploration) {
    ServiceInfo si = ri.serviceInfo;
    this.mService = ri;
    this.mId = new ComponentName(si.packageName, si.name).flattenToShortString();
    this.mSettingsActivityName = settingsActivity;
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
    this.mHandledConfigChanges = handledConfigChanges;
    this.mSupportsStylusHandwriting = supportsStylusHandwriting;
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
    return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
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

  public Intent createStylusHandwritingSettingsActivityIntent() {
    if (TextUtils.isEmpty(this.mStylusHandwritingSettingsActivityAttr)
        || !this.mSupportsStylusHandwriting) {
      return null;
    }
    return new Intent(ACTION_STYLUS_HANDWRITING_SETTINGS)
        .setComponent(
            new ComponentName(
                getServiceInfo().packageName, this.mStylusHandwritingSettingsActivityAttr));
  }

  public void dump(Printer pw, String prefix) {
    pw.println(
        prefix
            + "mId="
            + this.mId
            + " mSettingsActivityName="
            + this.mSettingsActivityName
            + " mIsVrOnly="
            + this.mIsVrOnly
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
            + " mStylusHandwritingSettingsActivityAttr="
            + this.mStylusHandwritingSettingsActivityAttr);
    pw.println(prefix + "mIsDefaultResId=0x" + Integer.toHexString(this.mIsDefaultResId));
    pw.println(prefix + "Service:");
    this.mService.dump(pw, prefix + "  ");
  }

  public String toString() {
    return "InputMethodInfo{" + this.mId + ", settings: " + this.mSettingsActivityName + "}";
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
    parcel.writeInt(this.mIsDefaultResId);
    parcel.writeInt(this.mIsAuxIme ? 1 : 0);
    parcel.writeInt(this.mSupportsSwitchingToNextInputMethod ? 1 : 0);
    parcel.writeInt(this.mInlineSuggestionsEnabled ? 1 : 0);
    parcel.writeInt(this.mSupportsInlineSuggestionsWithTouchExploration ? 1 : 0);
    parcel.writeBoolean(this.mSuppressesSpellChecker);
    parcel.writeBoolean(this.mShowInInputMethodPicker);
    parcel.writeBoolean(this.mIsVrOnly);
    this.mService.writeToParcel(parcel, i);
    this.mSubtypes.writeToParcel(parcel);
    parcel.writeInt(this.mHandledConfigChanges);
    parcel.writeBoolean(this.mSupportsStylusHandwriting);
    parcel.writeString8(this.mStylusHandwritingSettingsActivityAttr);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
