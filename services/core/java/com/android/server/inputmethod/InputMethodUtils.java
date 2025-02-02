package com.android.server.inputmethod;

import android.R;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Debug;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Printer;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.view.textservice.SpellCheckerInfo;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import com.android.server.textservices.TextServicesManagerInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public abstract class InputMethodUtils {
  public static final String NOT_A_SUBTYPE_ID_STR = String.valueOf(-1);

  public static boolean isSoftInputModeStateVisibleAllowed(int i, int i2) {
    if (i < 28) {
      return true;
    }
    return ((i2 & 1) == 0 || (i2 & 2) == 0) ? false : true;
  }

  public static boolean canAddToLastInputMethod(InputMethodSubtype inputMethodSubtype) {
    if (inputMethodSubtype == null) {
      return true;
    }
    return !inputMethodSubtype.isAuxiliary();
  }

  public static void setNonSelectedSystemImesDisabledUntilUsed(
      PackageManager packageManager, List list) {
    boolean z;
    String[] stringArray =
        Resources.getSystem().getStringArray(R.array.config_tether_bluetooth_regexs);
    if (stringArray == null || stringArray.length == 0) {
      return;
    }
    SpellCheckerInfo currentSpellCheckerForUser =
        TextServicesManagerInternal.get().getCurrentSpellCheckerForUser(packageManager.getUserId());
    for (String str : stringArray) {
      int i = 0;
      while (true) {
        if (i >= list.size()) {
          z = false;
          break;
        } else {
          if (str.equals(((InputMethodInfo) list.get(i)).getPackageName())) {
            z = true;
            break;
          }
          i++;
        }
      }
      if (!z
          && (currentSpellCheckerForUser == null
              || !str.equals(currentSpellCheckerForUser.getPackageName()))) {
        try {
          ApplicationInfo applicationInfo =
              packageManager.getApplicationInfo(
                  str, PackageManager.ApplicationInfoFlags.of(32768L));
          if (applicationInfo != null) {
            if ((applicationInfo.flags & 1) != 0) {
              setDisabledUntilUsed(packageManager, str);
            }
          }
        } catch (PackageManager.NameNotFoundException unused) {
        }
      }
    }
  }

  public static void setDisabledUntilUsed(PackageManager packageManager, String str) {
    try {
      int applicationEnabledSetting = packageManager.getApplicationEnabledSetting(str);
      if (applicationEnabledSetting == 0 || applicationEnabledSetting == 1) {
        try {
          packageManager.setApplicationEnabledSetting(str, 4, 0);
        } catch (IllegalArgumentException e) {
          Slog.w(
              "InputMethodUtils",
              "setApplicationEnabledSetting failed. packageName="
                  + str
                  + " userId="
                  + packageManager.getUserId(),
              e);
        }
      }
    } catch (IllegalArgumentException e2) {
      Slog.w(
          "InputMethodUtils",
          "getApplicationEnabledSetting failed. packageName="
              + str
              + " userId="
              + packageManager.getUserId(),
          e2);
    }
  }

  public static boolean checkIfPackageBelongsToUid(
      PackageManagerInternal packageManagerInternal, int i, String str) {
    return packageManagerInternal.isSameApp(str, 0L, i, UserHandle.getUserId(i));
  }

  public class InputMethodSettings {
    public static final ArraySet CLONE_TO_MANAGED_PROFILE;
    public static final UserManagerInternal sUserManagerInternal;
    public int mCurrentUserId;
    public final ArrayMap mMethodMap;
    public Resources mRes;
    public ContentResolver mResolver;
    public Context mUserAwareContext;
    public final TextUtils.SimpleStringSplitter mInputMethodSplitter =
        new TextUtils.SimpleStringSplitter(':');
    public final TextUtils.SimpleStringSplitter mSubtypeSplitter =
        new TextUtils.SimpleStringSplitter(';');
    public final ArrayMap mCopyOnWriteDataStore = new ArrayMap();
    public boolean mCopyOnWrite = false;
    public String mEnabledInputMethodsStrCache = "";
    public int[] mCurrentProfileIds = new int[0];

    static {
      ArraySet arraySet = new ArraySet();
      CLONE_TO_MANAGED_PROFILE = arraySet;
      Settings.Secure.getCloneToManagedProfileSettings(arraySet);
      sUserManagerInternal =
          (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    public static void buildEnabledInputMethodsSettingString(StringBuilder sb, Pair pair) {
      sb.append((String) pair.first);
      Iterator it = ((ArrayList) pair.second).iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        sb.append(';');
        sb.append(str);
      }
    }

    public static List buildInputMethodsAndSubtypeList(
        String str,
        TextUtils.SimpleStringSplitter simpleStringSplitter,
        TextUtils.SimpleStringSplitter simpleStringSplitter2) {
      ArrayList arrayList = new ArrayList();
      if (TextUtils.isEmpty(str)) {
        return arrayList;
      }
      simpleStringSplitter.setString(str);
      while (simpleStringSplitter.hasNext()) {
        try {
          simpleStringSplitter2.setString(simpleStringSplitter.next());
          if (simpleStringSplitter2.hasNext()) {
            ArrayList arrayList2 = new ArrayList();
            String next = simpleStringSplitter2.next();
            while (simpleStringSplitter2.hasNext()) {
              arrayList2.add(simpleStringSplitter2.next());
            }
            arrayList.add(new Pair(next, arrayList2));
          }
        } catch (StringIndexOutOfBoundsException e) {
          Log.w("InputMethodUtils", "StringIndexOutOfBoundsException : " + e);
        }
      }
      return arrayList;
    }

    public final void initContentWithUserContext(Context context, int i) {
      if (context.getUserId() != i) {
        context = context.createContextAsUser(UserHandle.of(i), 0);
      }
      this.mUserAwareContext = context;
      this.mRes = context.getResources();
      this.mResolver = this.mUserAwareContext.getContentResolver();
    }

    public InputMethodSettings(Context context, ArrayMap arrayMap, int i, boolean z) {
      this.mMethodMap = arrayMap;
      initContentWithUserContext(context, i);
      switchCurrentUser(i, z);
    }

    public void switchCurrentUser(int i, boolean z) {
      if (this.mCurrentUserId != i || this.mCopyOnWrite != z) {
        this.mCopyOnWriteDataStore.clear();
        this.mEnabledInputMethodsStrCache = "";
      }
      if (this.mUserAwareContext.getUserId() != i) {
        initContentWithUserContext(this.mUserAwareContext, i);
      }
      this.mCurrentUserId = i;
      this.mCopyOnWrite = z;
    }

    public final void putString(String str, String str2) {
      if (this.mCopyOnWrite) {
        this.mCopyOnWriteDataStore.put(str, str2);
      } else {
        Settings.Secure.putStringForUser(
            this.mResolver,
            str,
            str2,
            CLONE_TO_MANAGED_PROFILE.contains(str)
                ? sUserManagerInternal.getProfileParentId(this.mCurrentUserId)
                : this.mCurrentUserId);
      }
    }

    public final String getString(String str, String str2) {
      return getStringForUser(str, str2, this.mCurrentUserId);
    }

    public final String getStringForUser(String str, String str2, int i) {
      String stringForUser;
      if (this.mCopyOnWrite && this.mCopyOnWriteDataStore.containsKey(str)) {
        stringForUser = (String) this.mCopyOnWriteDataStore.get(str);
      } else {
        stringForUser = Settings.Secure.getStringForUser(this.mResolver, str, i);
      }
      return stringForUser != null ? stringForUser : str2;
    }

    public final void putInt(String str, int i) {
      if (this.mCopyOnWrite) {
        this.mCopyOnWriteDataStore.put(str, String.valueOf(i));
      } else {
        Settings.Secure.putIntForUser(
            this.mResolver,
            str,
            i,
            CLONE_TO_MANAGED_PROFILE.contains(str)
                ? sUserManagerInternal.getProfileParentId(this.mCurrentUserId)
                : this.mCurrentUserId);
      }
    }

    public final int getInt(String str, int i) {
      if (this.mCopyOnWrite && this.mCopyOnWriteDataStore.containsKey(str)) {
        String str2 = (String) this.mCopyOnWriteDataStore.get(str);
        return str2 != null ? Integer.parseInt(str2) : i;
      }
      return Settings.Secure.getIntForUser(this.mResolver, str, i, this.mCurrentUserId);
    }

    public final void putBoolean(String str, boolean z) {
      putInt(str, z ? 1 : 0);
    }

    public final boolean getBoolean(String str, boolean z) {
      return getInt(str, z ? 1 : 0) == 1;
    }

    public void setCurrentProfileIds(int[] iArr) {
      synchronized (this) {
        this.mCurrentProfileIds = iArr;
      }
    }

    public boolean isCurrentProfile(int i) {
      synchronized (this) {
        if (i == this.mCurrentUserId) {
          return true;
        }
        int i2 = 0;
        while (true) {
          int[] iArr = this.mCurrentProfileIds;
          if (i2 >= iArr.length) {
            return false;
          }
          if (i == iArr[i2]) {
            return true;
          }
          i2++;
        }
      }
    }

    public ArrayList getEnabledInputMethodListLocked() {
      return getEnabledInputMethodListWithFilterLocked(null);
    }

    public ArrayList getEnabledInputMethodListWithFilterLocked(Predicate predicate) {
      return createEnabledInputMethodListLocked(
          getEnabledInputMethodsAndSubtypeListLocked(), predicate);
    }

    public List getEnabledInputMethodSubtypeListLocked(InputMethodInfo inputMethodInfo, boolean z) {
      List enabledInputMethodSubtypeListLocked =
          getEnabledInputMethodSubtypeListLocked(inputMethodInfo);
      if (z && enabledInputMethodSubtypeListLocked.isEmpty()) {
        enabledInputMethodSubtypeListLocked =
            SubtypeUtils.getImplicitlyApplicableSubtypesLocked(this.mRes, inputMethodInfo);
      }
      return InputMethodSubtype.sort(inputMethodInfo, enabledInputMethodSubtypeListLocked);
    }

    public List getEnabledInputMethodSubtypeListLocked(InputMethodInfo inputMethodInfo) {
      List enabledInputMethodsAndSubtypeListLocked = getEnabledInputMethodsAndSubtypeListLocked();
      ArrayList arrayList = new ArrayList();
      if (inputMethodInfo != null) {
        Iterator it = enabledInputMethodsAndSubtypeListLocked.iterator();
        while (true) {
          if (!it.hasNext()) {
            break;
          }
          Pair pair = (Pair) it.next();
          InputMethodInfo inputMethodInfo2 = (InputMethodInfo) this.mMethodMap.get(pair.first);
          if (inputMethodInfo2 != null
              && inputMethodInfo2.getId().equals(inputMethodInfo.getId())) {
            int subtypeCount = inputMethodInfo2.getSubtypeCount();
            for (int i = 0; i < subtypeCount; i++) {
              InputMethodSubtype subtypeAt = inputMethodInfo2.getSubtypeAt(i);
              Iterator it2 = ((ArrayList) pair.second).iterator();
              while (it2.hasNext()) {
                if (String.valueOf(subtypeAt.hashCode()).equals((String) it2.next())) {
                  arrayList.add(subtypeAt);
                }
              }
            }
          }
        }
      }
      return arrayList;
    }

    public List getEnabledInputMethodsAndSubtypeListLocked() {
      return buildInputMethodsAndSubtypeList(
          getEnabledInputMethodsStr(), this.mInputMethodSplitter, this.mSubtypeSplitter);
    }

    public List getEnabledInputMethodNames() {
      ArrayList arrayList = new ArrayList();
      Iterator it = getEnabledInputMethodsAndSubtypeListLocked().iterator();
      while (it.hasNext()) {
        arrayList.add((String) ((Pair) it.next()).first);
      }
      return arrayList;
    }

    public void appendAndPutEnabledInputMethodLocked(String str, boolean z) {
      if (z) {
        getEnabledInputMethodsStr();
      }
      if (TextUtils.isEmpty(this.mEnabledInputMethodsStrCache)) {
        putEnabledInputMethodsStr(str);
        return;
      }
      putEnabledInputMethodsStr(this.mEnabledInputMethodsStrCache + ':' + str);
    }

    public boolean buildAndPutEnabledInputMethodsStrRemovingIdLocked(
        StringBuilder sb, List list, String str) {
      Iterator it = list.iterator();
      boolean z = false;
      boolean z2 = false;
      while (it.hasNext()) {
        Pair pair = (Pair) it.next();
        if (((String) pair.first).equals(str)) {
          z = true;
        } else {
          if (z2) {
            sb.append(':');
          } else {
            z2 = true;
          }
          buildEnabledInputMethodsSettingString(sb, pair);
        }
      }
      if (z) {
        putEnabledInputMethodsStr(sb.toString());
      }
      return z;
    }

    public final ArrayList createEnabledInputMethodListLocked(List list, Predicate predicate) {
      ArrayList arrayList = new ArrayList();
      Iterator it = list.iterator();
      while (it.hasNext()) {
        InputMethodInfo inputMethodInfo =
            (InputMethodInfo) this.mMethodMap.get(((Pair) it.next()).first);
        if (inputMethodInfo != null
            && !inputMethodInfo.isVrOnly()
            && (predicate == null || predicate.test(inputMethodInfo))) {
          arrayList.add(inputMethodInfo);
        }
      }
      return arrayList;
    }

    public void putEnabledInputMethodsStr(String str) {
      if (TextUtils.isEmpty(str)) {
        putString("enabled_input_methods", null);
      } else {
        putString("enabled_input_methods", str);
      }
      if (str == null) {
        str = "";
      }
      this.mEnabledInputMethodsStrCache = str;
    }

    public String getEnabledInputMethodsStr() {
      String string = getString("enabled_input_methods", "");
      this.mEnabledInputMethodsStrCache = string;
      return string;
    }

    public final void saveSubtypeHistory(List list, String str, String str2) {
      boolean z;
      StringBuilder sb = new StringBuilder();
      if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
        z = false;
      } else {
        sb.append(str);
        sb.append(';');
        sb.append(str2);
        z = true;
      }
      Iterator it = list.iterator();
      while (it.hasNext()) {
        Pair pair = (Pair) it.next();
        String str3 = (String) pair.first;
        String str4 = (String) pair.second;
        if (TextUtils.isEmpty(str4)) {
          str4 = InputMethodUtils.NOT_A_SUBTYPE_ID_STR;
        }
        if (z) {
          sb.append(':');
        } else {
          z = true;
        }
        sb.append(str3);
        sb.append(';');
        sb.append(str4);
      }
      putSubtypeHistoryStr(sb.toString());
    }

    public final void addSubtypeToHistory(String str, String str2) {
      List loadInputMethodAndSubtypeHistoryLocked = loadInputMethodAndSubtypeHistoryLocked();
      Iterator it = loadInputMethodAndSubtypeHistoryLocked.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        Pair pair = (Pair) it.next();
        if (((String) pair.first).equals(str)) {
          loadInputMethodAndSubtypeHistoryLocked.remove(pair);
          break;
        }
      }
      saveSubtypeHistory(loadInputMethodAndSubtypeHistoryLocked, str, str2);
    }

    public final void putSubtypeHistoryStr(String str) {
      if (TextUtils.isEmpty(str)) {
        putString("input_methods_subtype_history", null);
      } else {
        putString("input_methods_subtype_history", str);
      }
    }

    public Pair getLastInputMethodAndSubtypeLocked() {
      return getLastSubtypeForInputMethodLockedInternal(null);
    }

    public InputMethodSubtype getLastInputMethodSubtypeLocked() {
      InputMethodInfo inputMethodInfo;
      Pair lastInputMethodAndSubtypeLocked = getLastInputMethodAndSubtypeLocked();
      if (lastInputMethodAndSubtypeLocked == null
          || TextUtils.isEmpty((CharSequence) lastInputMethodAndSubtypeLocked.first)
          || TextUtils.isEmpty((CharSequence) lastInputMethodAndSubtypeLocked.second)
          || (inputMethodInfo =
                  (InputMethodInfo) this.mMethodMap.get(lastInputMethodAndSubtypeLocked.first))
              == null) {
        return null;
      }
      try {
        int subtypeIdFromHashCode =
            SubtypeUtils.getSubtypeIdFromHashCode(
                inputMethodInfo, Integer.parseInt((String) lastInputMethodAndSubtypeLocked.second));
        if (subtypeIdFromHashCode >= 0
            && subtypeIdFromHashCode < inputMethodInfo.getSubtypeCount()) {
          return inputMethodInfo.getSubtypeAt(subtypeIdFromHashCode);
        }
      } catch (NumberFormatException unused) {
      }
      return null;
    }

    public String getLastSubtypeForInputMethodLocked(String str) {
      Pair lastSubtypeForInputMethodLockedInternal =
          getLastSubtypeForInputMethodLockedInternal(str);
      if (lastSubtypeForInputMethodLockedInternal != null) {
        return (String) lastSubtypeForInputMethodLockedInternal.second;
      }
      return null;
    }

    public final Pair getLastSubtypeForInputMethodLockedInternal(String str) {
      List enabledInputMethodsAndSubtypeListLocked = getEnabledInputMethodsAndSubtypeListLocked();
      for (Pair pair : loadInputMethodAndSubtypeHistoryLocked()) {
        String str2 = (String) pair.first;
        if (TextUtils.isEmpty(str) || str2.equals(str)) {
          String enabledSubtypeHashCodeForInputMethodAndSubtypeLocked =
              getEnabledSubtypeHashCodeForInputMethodAndSubtypeLocked(
                  enabledInputMethodsAndSubtypeListLocked, str2, (String) pair.second);
          if (!TextUtils.isEmpty(enabledSubtypeHashCodeForInputMethodAndSubtypeLocked)) {
            return new Pair(str2, enabledSubtypeHashCodeForInputMethodAndSubtypeLocked);
          }
        }
      }
      return null;
    }

    public final String getEnabledSubtypeHashCodeForInputMethodAndSubtypeLocked(
        List list, String str, String str2) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        Pair pair = (Pair) it.next();
        if (((String) pair.first).equals(str)) {
          ArrayList arrayList = (ArrayList) pair.second;
          InputMethodInfo inputMethodInfo = (InputMethodInfo) this.mMethodMap.get(str);
          if (arrayList.size() == 0) {
            if (inputMethodInfo != null && inputMethodInfo.getSubtypeCount() > 0) {
              ArrayList implicitlyApplicableSubtypesLocked =
                  SubtypeUtils.getImplicitlyApplicableSubtypesLocked(this.mRes, inputMethodInfo);
              int size = implicitlyApplicableSubtypesLocked.size();
              for (int i = 0; i < size; i++) {
                if (String.valueOf(
                        ((InputMethodSubtype) implicitlyApplicableSubtypesLocked.get(i)).hashCode())
                    .equals(str2)) {
                  return str2;
                }
              }
            }
          } else {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
              String str3 = (String) it2.next();
              if (str3.equals(str2)) {
                try {
                  return SubtypeUtils.isValidSubtypeId(inputMethodInfo, Integer.parseInt(str2))
                      ? str3
                      : InputMethodUtils.NOT_A_SUBTYPE_ID_STR;
                } catch (NumberFormatException unused) {
                  return InputMethodUtils.NOT_A_SUBTYPE_ID_STR;
                }
              }
            }
          }
          return InputMethodUtils.NOT_A_SUBTYPE_ID_STR;
        }
      }
      return null;
    }

    public final List loadInputMethodAndSubtypeHistoryLocked() {
      ArrayList arrayList = new ArrayList();
      String subtypeHistoryStr = getSubtypeHistoryStr();
      if (TextUtils.isEmpty(subtypeHistoryStr)) {
        return arrayList;
      }
      try {
        this.mInputMethodSplitter.setString(subtypeHistoryStr);
        while (this.mInputMethodSplitter.hasNext()) {
          this.mSubtypeSplitter.setString(this.mInputMethodSplitter.next());
          if (this.mSubtypeSplitter.hasNext()) {
            String str = InputMethodUtils.NOT_A_SUBTYPE_ID_STR;
            String next = this.mSubtypeSplitter.next();
            if (this.mSubtypeSplitter.hasNext()) {
              str = this.mSubtypeSplitter.next();
            }
            arrayList.add(new Pair(next, str));
          }
        }
      } catch (StringIndexOutOfBoundsException e) {
        Log.w("InputMethodUtils", "StringIndexOutOfBoundsException : " + e);
      }
      return arrayList;
    }

    public final String getSubtypeHistoryStr() {
      return getString("input_methods_subtype_history", "");
    }

    public void putSelectedInputMethod(String str) {
      Slog.d(
          "InputMethodUtils",
          "putSelectedInputMethodStr: "
              + str
              + ", userId : "
              + this.mCurrentUserId
              + ", callers : "
              + Debug.getCallers(10));
      putString("default_input_method", str);
    }

    public void putSelectedSubtype(int i) {
      putInt("selected_input_method_subtype", i);
    }

    public String getSelectedInputMethod() {
      return getString("default_input_method", null);
    }

    public String getSelectedInputMethodForUser(int i) {
      return getStringForUser("default_input_method", null, i);
    }

    public void putDefaultVoiceInputMethod(String str) {
      putString("default_voice_input_method", str);
    }

    public String getDefaultVoiceInputMethod() {
      return getString("default_voice_input_method", null);
    }

    public boolean isSubtypeSelected() {
      return getSelectedInputMethodSubtypeHashCode() != -1;
    }

    public final int getSelectedInputMethodSubtypeHashCode() {
      return getInt("selected_input_method_subtype", -1);
    }

    public boolean isShowImeWithHardKeyboardEnabled() {
      String str = SystemProperties.get("ro.build.characteristics");
      int i = (str == null || !str.contains("tablet")) ? 1 : 0;
      if (this.mCurrentUserId == 0) {
        return Settings.Secure.getIntForUser(this.mResolver, "show_ime_with_hard_keyboard", i, 0)
            == 1;
      }
      return getBoolean("show_ime_with_hard_keyboard", false);
    }

    public void setShowImeWithHardKeyboard(boolean z) {
      Slog.i(
          "InputMethodUtils",
          "setShowImeWithHardKeyboard show = " + z + ", callers= " + Debug.getCallers(3));
      putBoolean("show_ime_with_hard_keyboard", z);
    }

    public int getCurrentUserId() {
      return this.mCurrentUserId;
    }

    public int getSelectedInputMethodSubtypeId(String str) {
      InputMethodInfo inputMethodInfo = (InputMethodInfo) this.mMethodMap.get(str);
      if (inputMethodInfo == null) {
        return -1;
      }
      return SubtypeUtils.getSubtypeIdFromHashCode(
          inputMethodInfo, getSelectedInputMethodSubtypeHashCode());
    }

    public void saveCurrentInputMethodAndSubtypeToHistory(
        String str, InputMethodSubtype inputMethodSubtype) {
      String str2 = InputMethodUtils.NOT_A_SUBTYPE_ID_STR;
      if (inputMethodSubtype != null) {
        str2 = String.valueOf(inputMethodSubtype.hashCode());
      }
      if (InputMethodUtils.canAddToLastInputMethod(inputMethodSubtype)) {
        addSubtypeToHistory(str, str2);
      }
    }

    public InputMethodSubtype getCurrentInputMethodSubtypeForNonCurrentUsers() {
      InputMethodInfo inputMethodInfo;
      int subtypeIdFromHashCode;
      String selectedInputMethod = getSelectedInputMethod();
      if (selectedInputMethod == null
          || (inputMethodInfo = (InputMethodInfo) this.mMethodMap.get(selectedInputMethod)) == null
          || inputMethodInfo.getSubtypeCount() == 0) {
        return null;
      }
      int selectedInputMethodSubtypeHashCode = getSelectedInputMethodSubtypeHashCode();
      if (selectedInputMethodSubtypeHashCode != -1
          && (subtypeIdFromHashCode =
                  SubtypeUtils.getSubtypeIdFromHashCode(
                      inputMethodInfo, selectedInputMethodSubtypeHashCode))
              >= 0) {
        return inputMethodInfo.getSubtypeAt(subtypeIdFromHashCode);
      }
      List enabledInputMethodSubtypeListLocked =
          getEnabledInputMethodSubtypeListLocked(inputMethodInfo, true);
      if (enabledInputMethodSubtypeListLocked.isEmpty()) {
        return null;
      }
      if (enabledInputMethodSubtypeListLocked.size() == 1) {
        return (InputMethodSubtype) enabledInputMethodSubtypeListLocked.get(0);
      }
      InputMethodSubtype findLastResortApplicableSubtypeLocked =
          SubtypeUtils.findLastResortApplicableSubtypeLocked(
              this.mRes, enabledInputMethodSubtypeListLocked, "keyboard", null, true);
      return findLastResortApplicableSubtypeLocked != null
          ? findLastResortApplicableSubtypeLocked
          : SubtypeUtils.findLastResortApplicableSubtypeLocked(
              this.mRes, enabledInputMethodSubtypeListLocked, null, null, true);
    }

    public boolean setAdditionalInputMethodSubtypes(
        String str,
        ArrayList arrayList,
        ArrayMap arrayMap,
        PackageManagerInternal packageManagerInternal,
        int i) {
      InputMethodInfo inputMethodInfo = (InputMethodInfo) this.mMethodMap.get(str);
      if (inputMethodInfo == null
          || !InputMethodUtils.checkIfPackageBelongsToUid(
              packageManagerInternal, i, inputMethodInfo.getPackageName())) {
        return false;
      }
      if (arrayList.isEmpty()) {
        arrayMap.remove(inputMethodInfo.getId());
      } else {
        arrayMap.put(inputMethodInfo.getId(), arrayList);
      }
      AdditionalSubtypeUtils.save(arrayMap, this.mMethodMap, getCurrentUserId());
      return true;
    }

    public boolean setEnabledInputMethodSubtypes(String str, int[] iArr) {
      InputMethodInfo inputMethodInfo = (InputMethodInfo) this.mMethodMap.get(str);
      if (inputMethodInfo == null) {
        return false;
      }
      IntArray intArray = new IntArray(iArr.length);
      for (int i : iArr) {
        if (i != -1
            && SubtypeUtils.isValidSubtypeId(inputMethodInfo, i)
            && intArray.indexOf(i) < 0) {
          intArray.add(i);
        }
      }
      String enabledInputMethodsStr = getEnabledInputMethodsStr();
      String updateEnabledImeString =
          updateEnabledImeString(enabledInputMethodsStr, inputMethodInfo.getId(), intArray);
      if (TextUtils.equals(enabledInputMethodsStr, updateEnabledImeString)) {
        return false;
      }
      putEnabledInputMethodsStr(updateEnabledImeString);
      return true;
    }

    public static String updateEnabledImeString(String str, String str2, IntArray intArray) {
      TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
      TextUtils.SimpleStringSplitter simpleStringSplitter2 =
          new TextUtils.SimpleStringSplitter(';');
      StringBuilder sb = new StringBuilder();
      simpleStringSplitter.setString(str);
      boolean z = false;
      while (simpleStringSplitter.hasNext()) {
        String next = simpleStringSplitter.next();
        simpleStringSplitter2.setString(next);
        if (simpleStringSplitter2.hasNext()) {
          if (z) {
            sb.append(':');
          }
          if (TextUtils.equals(str2, simpleStringSplitter2.next())) {
            sb.append(str2);
            for (int i = 0; i < intArray.size(); i++) {
              sb.append(';');
              sb.append(intArray.get(i));
            }
          } else {
            sb.append(next);
          }
          z = true;
        }
      }
      return sb.toString();
    }

    public void dumpLocked(Printer printer, String str) {
      printer.println(str + "mCurrentUserId=" + this.mCurrentUserId);
      printer.println(str + "mCurrentProfileIds=" + Arrays.toString(this.mCurrentProfileIds));
      printer.println(str + "mCopyOnWrite=" + this.mCopyOnWrite);
      printer.println(str + "mEnabledInputMethodsStrCache=" + this.mEnabledInputMethodsStrCache);
    }

    public boolean isShowKeyboardButton() {
      if (this.mCurrentUserId == 0) {
        return Settings.Secure.getIntForUser(this.mResolver, "show_keyboard_button", 1, 0) == 1;
      }
      return getBoolean("show_keyboard_button", true);
    }

    public void setShowKeyboardButton(boolean z) {
      putBoolean("show_keyboard_button", z);
    }
  }

  public static int[] resolveUserId(int i, int i2, PrintWriter printWriter) {
    UserManagerInternal userManagerInternal =
        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    if (i == -1) {
      return userManagerInternal.getUserIds();
    }
    if (i == -2) {
      i = i2;
    } else {
      if (i < 0) {
        if (printWriter != null) {
          printWriter.print("Pseudo user ID ");
          printWriter.print(i);
          printWriter.println(" is not supported.");
        }
        return new int[0];
      }
      if (!userManagerInternal.exists(i)) {
        if (printWriter != null) {
          printWriter.print("User #");
          printWriter.print(i);
          printWriter.println(" does not exit.");
        }
        return new int[0];
      }
    }
    return new int[] {i};
  }

  public static ComponentName convertIdToComponentName(String str) {
    return ComponentName.unflattenFromString(str);
  }
}
