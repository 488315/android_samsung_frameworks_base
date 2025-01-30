package com.android.server.inputmethod;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Printer;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class InputMethodSubtypeSwitchingController {
  public ControllerImpl mController;
  public final InputMethodUtils.InputMethodSettings mSettings;
  public InputMethodAndSubtypeList mSubtypeList;

  public class ImeSubtypeListItem implements Comparable {
    public final CharSequence mImeName;
    public final InputMethodInfo mImi;
    public final boolean mIsSystemLanguage;
    public final boolean mIsSystemLocale;
    public final int mSubtypeId;
    public final CharSequence mSubtypeName;

    public ImeSubtypeListItem(
        CharSequence charSequence,
        CharSequence charSequence2,
        InputMethodInfo inputMethodInfo,
        int i,
        String str,
        String str2) {
      this.mImeName = charSequence;
      this.mSubtypeName = charSequence2;
      this.mImi = inputMethodInfo;
      this.mSubtypeId = i;
      boolean z = false;
      if (TextUtils.isEmpty(str)) {
        this.mIsSystemLocale = false;
        this.mIsSystemLanguage = false;
        return;
      }
      boolean equals = str.equals(str2);
      this.mIsSystemLocale = equals;
      if (equals) {
        this.mIsSystemLanguage = true;
        return;
      }
      String parseLanguageFromLocaleString = parseLanguageFromLocaleString(str2);
      String parseLanguageFromLocaleString2 = parseLanguageFromLocaleString(str);
      if (parseLanguageFromLocaleString.length() >= 2
          && parseLanguageFromLocaleString.equals(parseLanguageFromLocaleString2)) {
        z = true;
      }
      this.mIsSystemLanguage = z;
    }

    public static String parseLanguageFromLocaleString(String str) {
      int indexOf = str.indexOf(95);
      return indexOf < 0 ? str : str.substring(0, indexOf);
    }

    public static int compareNullableCharSequences(
        CharSequence charSequence, CharSequence charSequence2) {
      boolean isEmpty = TextUtils.isEmpty(charSequence);
      boolean isEmpty2 = TextUtils.isEmpty(charSequence2);
      if (isEmpty || isEmpty2) {
        return (isEmpty ? 1 : 0) - (isEmpty2 ? 1 : 0);
      }
      return charSequence.toString().compareTo(charSequence2.toString());
    }

    @Override // java.lang.Comparable
    public int compareTo(ImeSubtypeListItem imeSubtypeListItem) {
      int compareNullableCharSequences =
          compareNullableCharSequences(this.mImeName, imeSubtypeListItem.mImeName);
      if (compareNullableCharSequences != 0) {
        return compareNullableCharSequences;
      }
      int i = (this.mIsSystemLocale ? -1 : 0) - (imeSubtypeListItem.mIsSystemLocale ? -1 : 0);
      if (i != 0) {
        return i;
      }
      int i2 = (this.mIsSystemLanguage ? -1 : 0) - (imeSubtypeListItem.mIsSystemLanguage ? -1 : 0);
      if (i2 != 0) {
        return i2;
      }
      int compareNullableCharSequences2 =
          compareNullableCharSequences(this.mSubtypeName, imeSubtypeListItem.mSubtypeName);
      return compareNullableCharSequences2 != 0
          ? compareNullableCharSequences2
          : this.mImi.getId().compareTo(imeSubtypeListItem.mImi.getId());
    }

    public String toString() {
      return "ImeSubtypeListItem{mImeName="
          + ((Object) this.mImeName)
          + " mSubtypeName="
          + ((Object) this.mSubtypeName)
          + " mSubtypeId="
          + this.mSubtypeId
          + " mIsSystemLocale="
          + this.mIsSystemLocale
          + " mIsSystemLanguage="
          + this.mIsSystemLanguage
          + "}";
    }

    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof ImeSubtypeListItem)) {
        return false;
      }
      ImeSubtypeListItem imeSubtypeListItem = (ImeSubtypeListItem) obj;
      return Objects.equals(this.mImi, imeSubtypeListItem.mImi)
          && this.mSubtypeId == imeSubtypeListItem.mSubtypeId;
    }
  }

  public class InputMethodAndSubtypeList {
    public final Context mContext;
    public final PackageManager mPm;
    public final InputMethodUtils.InputMethodSettings mSettings;
    public final String mSystemLocaleStr;

    public InputMethodAndSubtypeList(
        Context context, InputMethodUtils.InputMethodSettings inputMethodSettings) {
      this.mContext = context;
      this.mSettings = inputMethodSettings;
      this.mPm = context.getPackageManager();
      Locale locale = context.getResources().getConfiguration().locale;
      this.mSystemLocaleStr = locale != null ? locale.toString() : "";
    }

    public List getSortedInputMethodAndSubtypeList(boolean z, boolean z2, boolean z3) {
      ArrayList arrayList;
      ArrayList arrayList2;
      int i;
      int i2;
      ArrayList enabledInputMethodListLocked = this.mSettings.getEnabledInputMethodListLocked();
      if (enabledInputMethodListLocked.isEmpty()) {
        return Collections.emptyList();
      }
      boolean z4 = (z2 && z) ? false : z;
      ArrayList arrayList3 = new ArrayList();
      int size = enabledInputMethodListLocked.size();
      int i3 = 0;
      while (i3 < size) {
        InputMethodInfo inputMethodInfo = (InputMethodInfo) enabledInputMethodListLocked.get(i3);
        if (!z3 || inputMethodInfo.shouldShowInInputMethodPicker()) {
          List enabledInputMethodSubtypeListLocked =
              this.mSettings.getEnabledInputMethodSubtypeListLocked(inputMethodInfo, true);
          ArraySet arraySet = new ArraySet();
          Iterator it = enabledInputMethodSubtypeListLocked.iterator();
          while (it.hasNext()) {
            arraySet.add(String.valueOf(((InputMethodSubtype) it.next()).hashCode()));
          }
          CharSequence loadLabel = inputMethodInfo.loadLabel(this.mPm);
          if (arraySet.size() > 0) {
            int subtypeCount = inputMethodInfo.getSubtypeCount();
            int i4 = 0;
            while (i4 < subtypeCount) {
              InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i4);
              String valueOf = String.valueOf(subtypeAt.hashCode());
              if (!arraySet.contains(valueOf) || (!z4 && subtypeAt.isAuxiliary())) {
                arrayList2 = enabledInputMethodListLocked;
                i = i4;
                i2 = subtypeCount;
              } else {
                arrayList2 = enabledInputMethodListLocked;
                i = i4;
                i2 = subtypeCount;
                arrayList3.add(
                    new ImeSubtypeListItem(
                        loadLabel,
                        subtypeAt.overridesImplicitlyEnabledSubtype()
                            ? null
                            : subtypeAt.getDisplayName(
                                this.mContext,
                                inputMethodInfo.getPackageName(),
                                inputMethodInfo.getServiceInfo().applicationInfo),
                        inputMethodInfo,
                        i4,
                        subtypeAt.getLocale(),
                        this.mSystemLocaleStr));
                arraySet.remove(valueOf);
              }
              i4 = i + 1;
              subtypeCount = i2;
              enabledInputMethodListLocked = arrayList2;
            }
          } else {
            arrayList = enabledInputMethodListLocked;
            arrayList3.add(
                new ImeSubtypeListItem(
                    loadLabel, null, inputMethodInfo, -1, null, this.mSystemLocaleStr));
            i3++;
            enabledInputMethodListLocked = arrayList;
          }
        }
        arrayList = enabledInputMethodListLocked;
        i3++;
        enabledInputMethodListLocked = arrayList;
      }
      Collections.sort(arrayList3);
      return arrayList3;
    }
  }

  public static int calculateSubtypeId(
      InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
    if (inputMethodSubtype != null) {
      return SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, inputMethodSubtype.hashCode());
    }
    return -1;
  }

  public class StaticRotationList {
    public final List mImeSubtypeList;

    public StaticRotationList(List list) {
      this.mImeSubtypeList = list;
    }

    public final int getIndex(
        InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      int calculateSubtypeId =
          InputMethodSubtypeSwitchingController.calculateSubtypeId(
              inputMethodInfo, inputMethodSubtype);
      int size = this.mImeSubtypeList.size();
      for (int i = 0; i < size; i++) {
        ImeSubtypeListItem imeSubtypeListItem = (ImeSubtypeListItem) this.mImeSubtypeList.get(i);
        if (inputMethodInfo.equals(imeSubtypeListItem.mImi)
            && imeSubtypeListItem.mSubtypeId == calculateSubtypeId) {
          return i;
        }
      }
      return -1;
    }

    public ImeSubtypeListItem getNextInputMethodLocked(
        boolean z, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      int index;
      if (inputMethodInfo == null) {
        return null;
      }
      if (this.mImeSubtypeList.size() <= 1
          || (index = getIndex(inputMethodInfo, inputMethodSubtype)) < 0) {
        return null;
      }
      int size = this.mImeSubtypeList.size();
      for (int i = 1; i < size; i++) {
        ImeSubtypeListItem imeSubtypeListItem =
            (ImeSubtypeListItem) this.mImeSubtypeList.get((index + i) % size);
        if (!z || inputMethodInfo.equals(imeSubtypeListItem.mImi)) {
          return imeSubtypeListItem;
        }
      }
      return null;
    }

    public void dump(Printer printer, String str) {
      int size = this.mImeSubtypeList.size();
      for (int i = 0; i < size; i++) {
        printer.println(
            str + "rank=" + i + " item=" + ((ImeSubtypeListItem) this.mImeSubtypeList.get(i)));
      }
    }
  }

  public class DynamicRotationList {
    public final List mImeSubtypeList;
    public final int[] mUsageHistoryOfSubtypeListItemIndex;

    public DynamicRotationList(List list) {
      this.mImeSubtypeList = list;
      this.mUsageHistoryOfSubtypeListItemIndex = new int[list.size()];
      int size = list.size();
      for (int i = 0; i < size; i++) {
        this.mUsageHistoryOfSubtypeListItemIndex[i] = i;
      }
    }

    public final int getUsageRank(
        InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      int calculateSubtypeId =
          InputMethodSubtypeSwitchingController.calculateSubtypeId(
              inputMethodInfo, inputMethodSubtype);
      int length = this.mUsageHistoryOfSubtypeListItemIndex.length;
      for (int i = 0; i < length; i++) {
        ImeSubtypeListItem imeSubtypeListItem =
            (ImeSubtypeListItem)
                this.mImeSubtypeList.get(this.mUsageHistoryOfSubtypeListItemIndex[i]);
        if (imeSubtypeListItem.mImi.equals(inputMethodInfo)
            && imeSubtypeListItem.mSubtypeId == calculateSubtypeId) {
          return i;
        }
      }
      return -1;
    }

    public void onUserAction(
        InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      int usageRank = getUsageRank(inputMethodInfo, inputMethodSubtype);
      if (usageRank <= 0) {
        return;
      }
      int[] iArr = this.mUsageHistoryOfSubtypeListItemIndex;
      int i = iArr[usageRank];
      System.arraycopy(iArr, 0, iArr, 1, usageRank);
      this.mUsageHistoryOfSubtypeListItemIndex[0] = i;
    }

    public ImeSubtypeListItem getNextInputMethodLocked(
        boolean z, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      int usageRank = getUsageRank(inputMethodInfo, inputMethodSubtype);
      if (usageRank < 0) {
        return null;
      }
      int length = this.mUsageHistoryOfSubtypeListItemIndex.length;
      for (int i = 1; i < length; i++) {
        ImeSubtypeListItem imeSubtypeListItem =
            (ImeSubtypeListItem)
                this.mImeSubtypeList.get(
                    this.mUsageHistoryOfSubtypeListItemIndex[(usageRank + i) % length]);
        if (!z || inputMethodInfo.equals(imeSubtypeListItem.mImi)) {
          return imeSubtypeListItem;
        }
      }
      return null;
    }

    public void dump(Printer printer, String str) {
      int i = 0;
      while (true) {
        int[] iArr = this.mUsageHistoryOfSubtypeListItemIndex;
        if (i >= iArr.length) {
          return;
        }
        printer.println(
            str
                + "rank="
                + iArr[i]
                + " item="
                + ((ImeSubtypeListItem) this.mImeSubtypeList.get(i)));
        i++;
      }
    }
  }

  public class ControllerImpl {
    public final DynamicRotationList mSwitchingAwareRotationList;
    public final StaticRotationList mSwitchingUnawareRotationList;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    public static ControllerImpl createFrom(ControllerImpl controllerImpl, List list) {
      StaticRotationList staticRotationList;
      DynamicRotationList dynamicRotationList;
      List filterImeSubtypeList = filterImeSubtypeList(list, true);
      StaticRotationList staticRotationList2 = 0;
      staticRotationList2 = 0;
      staticRotationList2 = 0;
      DynamicRotationList dynamicRotationList2 =
          (controllerImpl == null
                  || (dynamicRotationList = controllerImpl.mSwitchingAwareRotationList) == null
                  || !Objects.equals(dynamicRotationList.mImeSubtypeList, filterImeSubtypeList))
              ? null
              : controllerImpl.mSwitchingAwareRotationList;
      if (dynamicRotationList2 == null) {
        dynamicRotationList2 = new DynamicRotationList(filterImeSubtypeList);
      }
      List filterImeSubtypeList2 = filterImeSubtypeList(list, false);
      if (controllerImpl != null
          && (staticRotationList = controllerImpl.mSwitchingUnawareRotationList) != null
          && Objects.equals(staticRotationList.mImeSubtypeList, filterImeSubtypeList2)) {
        staticRotationList2 = controllerImpl.mSwitchingUnawareRotationList;
      }
      if (staticRotationList2 == 0) {
        staticRotationList2 = new StaticRotationList(filterImeSubtypeList2);
      }
      return new ControllerImpl(dynamicRotationList2, staticRotationList2);
    }

    public ControllerImpl(
        DynamicRotationList dynamicRotationList, StaticRotationList staticRotationList) {
      this.mSwitchingAwareRotationList = dynamicRotationList;
      this.mSwitchingUnawareRotationList = staticRotationList;
    }

    public ImeSubtypeListItem getNextInputMethod(
        boolean z, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      if (inputMethodInfo == null) {
        return null;
      }
      if (inputMethodInfo.supportsSwitchingToNextInputMethod()) {
        return this.mSwitchingAwareRotationList.getNextInputMethodLocked(
            z, inputMethodInfo, inputMethodSubtype);
      }
      return this.mSwitchingUnawareRotationList.getNextInputMethodLocked(
          z, inputMethodInfo, inputMethodSubtype);
    }

    public void onUserActionLocked(
        InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
      if (inputMethodInfo != null && inputMethodInfo.supportsSwitchingToNextInputMethod()) {
        this.mSwitchingAwareRotationList.onUserAction(inputMethodInfo, inputMethodSubtype);
      }
    }

    public static List filterImeSubtypeList(List list, boolean z) {
      ArrayList arrayList = new ArrayList();
      int size = list.size();
      for (int i = 0; i < size; i++) {
        ImeSubtypeListItem imeSubtypeListItem = (ImeSubtypeListItem) list.get(i);
        if (imeSubtypeListItem.mImi.supportsSwitchingToNextInputMethod() == z) {
          arrayList.add(imeSubtypeListItem);
        }
      }
      return arrayList;
    }

    public void dump(Printer printer) {
      printer.println("    mSwitchingAwareRotationList:");
      this.mSwitchingAwareRotationList.dump(printer, "      ");
      printer.println("    mSwitchingUnawareRotationList:");
      this.mSwitchingUnawareRotationList.dump(printer, "      ");
    }
  }

  public InputMethodSubtypeSwitchingController(
      InputMethodUtils.InputMethodSettings inputMethodSettings, Context context) {
    this.mSettings = inputMethodSettings;
    resetCircularListLocked(context);
  }

  public static InputMethodSubtypeSwitchingController createInstanceLocked(
      InputMethodUtils.InputMethodSettings inputMethodSettings, Context context) {
    return new InputMethodSubtypeSwitchingController(inputMethodSettings, context);
  }

  public void onUserActionLocked(
      InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
    ControllerImpl controllerImpl = this.mController;
    if (controllerImpl == null) {
      return;
    }
    controllerImpl.onUserActionLocked(inputMethodInfo, inputMethodSubtype);
  }

  public void resetCircularListLocked(Context context) {
    InputMethodAndSubtypeList inputMethodAndSubtypeList =
        new InputMethodAndSubtypeList(context, this.mSettings);
    this.mSubtypeList = inputMethodAndSubtypeList;
    this.mController =
        ControllerImpl.createFrom(
            this.mController,
            inputMethodAndSubtypeList.getSortedInputMethodAndSubtypeList(false, false, false));
  }

  public ImeSubtypeListItem getNextInputMethodLocked(
      boolean z, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
    ControllerImpl controllerImpl = this.mController;
    if (controllerImpl == null) {
      return null;
    }
    return controllerImpl.getNextInputMethod(z, inputMethodInfo, inputMethodSubtype);
  }

  public List getSortedInputMethodAndSubtypeListForImeMenuLocked(boolean z, boolean z2) {
    return this.mSubtypeList.getSortedInputMethodAndSubtypeList(z, z2, true);
  }

  public void dump(Printer printer) {
    ControllerImpl controllerImpl = this.mController;
    if (controllerImpl != null) {
      controllerImpl.dump(printer);
    } else {
      printer.println("    mController=null");
    }
  }
}
