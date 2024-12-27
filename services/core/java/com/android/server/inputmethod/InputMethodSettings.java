package com.android.server.inputmethod;

import android.content.pm.PackageManagerInternal;
import android.os.Debug;
import android.os.LocaleList;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InputMethodSettings {
    public static final String INVALID_SUBTYPE_HASHCODE_STR = String.valueOf(-1);
    public final List mMethodList;
    public final InputMethodMap mMethodMap;
    public final int mUserId;

    public InputMethodSettings(InputMethodMap inputMethodMap, int i) {
        this.mMethodMap = inputMethodMap;
        this.mMethodList = List.copyOf(inputMethodMap.mMap.values());
        this.mUserId = i;
    }

    public static String updateEnabledImeString(String str, String str2, IntArray intArray) {
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
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

    public final boolean buildAndPutEnabledInputMethodsStrRemovingId(
            StringBuilder sb, List list, String str) {
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                break;
            }
            Pair pair = (Pair) arrayList.get(i);
            if (((String) pair.first).equals(str)) {
                z = true;
            } else {
                if (z2) {
                    sb.append(':');
                } else {
                    z2 = true;
                }
                sb.append((String) pair.first);
                for (int i2 = 0; i2 < ((ArrayList) pair.second).size(); i2++) {
                    String str2 = (String) ((ArrayList) pair.second).get(i2);
                    sb.append(';');
                    sb.append(str2);
                }
            }
            i++;
        }
        if (z) {
            putEnabledInputMethodsStr(sb.toString());
        }
        return z;
    }

    public final InputMethodSubtype getCurrentInputMethodSubtypeForNonCurrentUsers() {
        InputMethodInfo inputMethodInfo;
        int subtypeIdFromHashCode;
        String selectedInputMethod = getSelectedInputMethod();
        if (selectedInputMethod == null
                || (inputMethodInfo = this.mMethodMap.get(selectedInputMethod)) == null
                || inputMethodInfo.getSubtypeCount() == 0) {
            return null;
        }
        int i = SecureSettingsWrapper.getInt(-1, this.mUserId, "selected_input_method_subtype");
        if (i != -1
                && (subtypeIdFromHashCode =
                                SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, i))
                        >= 0) {
            return inputMethodInfo.getSubtypeAt(subtypeIdFromHashCode);
        }
        List enabledInputMethodSubtypeList =
                getEnabledInputMethodSubtypeList(inputMethodInfo, true);
        if (enabledInputMethodSubtypeList.isEmpty()) {
            return null;
        }
        if (enabledInputMethodSubtypeList.size() == 1) {
            return (InputMethodSubtype) enabledInputMethodSubtypeList.get(0);
        }
        String locale = SystemLocaleWrapper.get().get(0).toString();
        InputMethodSubtype findLastResortApplicableSubtype =
                SubtypeUtils.findLastResortApplicableSubtype(
                        "keyboard", locale, enabledInputMethodSubtypeList);
        return findLastResortApplicableSubtype != null
                ? findLastResortApplicableSubtype
                : SubtypeUtils.findLastResortApplicableSubtype(
                        null, locale, enabledInputMethodSubtypeList);
    }

    public final ArrayList getEnabledInputMethodListWithFilter(Predicate predicate) {
        List enabledInputMethodsAndSubtypeList = getEnabledInputMethodsAndSubtypeList();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            ArrayList arrayList2 = (ArrayList) enabledInputMethodsAndSubtypeList;
            if (i >= arrayList2.size()) {
                return arrayList;
            }
            InputMethodInfo inputMethodInfo =
                    this.mMethodMap.get((String) ((Pair) arrayList2.get(i)).first);
            if (inputMethodInfo != null
                    && !inputMethodInfo.isVrOnly()
                    && (predicate == null || predicate.test(inputMethodInfo))) {
                arrayList.add(inputMethodInfo);
            }
            i++;
        }
    }

    public final List getEnabledInputMethodSubtypeList(InputMethodInfo inputMethodInfo, boolean z) {
        List enabledInputMethodsAndSubtypeList = getEnabledInputMethodsAndSubtypeList();
        ArrayList arrayList = new ArrayList();
        if (inputMethodInfo != null) {
            int i = 0;
            while (true) {
                ArrayList arrayList2 = (ArrayList) enabledInputMethodsAndSubtypeList;
                if (i >= arrayList2.size()) {
                    break;
                }
                Pair pair = (Pair) arrayList2.get(i);
                InputMethodInfo inputMethodInfo2 = this.mMethodMap.get((String) pair.first);
                if (inputMethodInfo2 == null
                        || !inputMethodInfo2.getId().equals(inputMethodInfo.getId())) {
                    i++;
                } else {
                    int subtypeCount = inputMethodInfo2.getSubtypeCount();
                    for (int i2 = 0; i2 < subtypeCount; i2++) {
                        InputMethodSubtype subtypeAt = inputMethodInfo2.getSubtypeAt(i2);
                        for (int i3 = 0; i3 < ((ArrayList) pair.second).size(); i3++) {
                            if (String.valueOf(subtypeAt.hashCode())
                                    .equals((String) ((ArrayList) pair.second).get(i3))) {
                                arrayList.add(subtypeAt);
                            }
                        }
                    }
                }
            }
        }
        if (z && arrayList.isEmpty()) {
            arrayList =
                    SubtypeUtils.getImplicitlyApplicableSubtypes(
                            SystemLocaleWrapper.get(), inputMethodInfo);
        }
        return InputMethodSubtype.sort(inputMethodInfo, arrayList);
    }

    public final List getEnabledInputMethodsAndSubtypeList() {
        String enabledInputMethodsStr = getEnabledInputMethodsStr();
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        TextUtils.SimpleStringSplitter simpleStringSplitter2 =
                new TextUtils.SimpleStringSplitter(';');
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(enabledInputMethodsStr)) {
            return arrayList;
        }
        simpleStringSplitter.setString(enabledInputMethodsStr);
        while (simpleStringSplitter.hasNext()) {
            simpleStringSplitter2.setString(simpleStringSplitter.next());
            if (simpleStringSplitter2.hasNext()) {
                ArrayList arrayList2 = new ArrayList();
                String next = simpleStringSplitter2.next();
                while (simpleStringSplitter2.hasNext()) {
                    arrayList2.add(simpleStringSplitter2.next());
                }
                arrayList.add(new Pair(next, arrayList2));
            }
        }
        return arrayList;
    }

    public final String getEnabledInputMethodsStr() {
        return SecureSettingsWrapper.getString("enabled_input_methods", "", this.mUserId);
    }

    public final InputMethodSubtype getLastInputMethodSubtype() {
        Pair lastSubtypeForInputMethodInternal = getLastSubtypeForInputMethodInternal(null);
        if (lastSubtypeForInputMethodInternal != null
                && !TextUtils.isEmpty((CharSequence) lastSubtypeForInputMethodInternal.first)
                && !TextUtils.isEmpty((CharSequence) lastSubtypeForInputMethodInternal.second)) {
            InputMethodInfo inputMethodInfo =
                    this.mMethodMap.get((String) lastSubtypeForInputMethodInternal.first);
            if (inputMethodInfo == null) {
                return null;
            }
            try {
                int subtypeIdFromHashCode =
                        SubtypeUtils.getSubtypeIdFromHashCode(
                                inputMethodInfo,
                                Integer.parseInt(
                                        (String) lastSubtypeForInputMethodInternal.second));
                if (subtypeIdFromHashCode >= 0
                        && subtypeIdFromHashCode < inputMethodInfo.getSubtypeCount()) {
                    return inputMethodInfo.getSubtypeAt(subtypeIdFromHashCode);
                }
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public final Pair getLastSubtypeForInputMethodInternal(String str) {
        List enabledInputMethodsAndSubtypeList = getEnabledInputMethodsAndSubtypeList();
        List loadInputMethodAndSubtypeHistory = loadInputMethodAndSubtypeHistory();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) loadInputMethodAndSubtypeHistory;
            String str2 = null;
            if (i >= arrayList.size()) {
                return null;
            }
            Pair pair = (Pair) arrayList.get(i);
            String str3 = (String) pair.first;
            if (TextUtils.isEmpty(str) || str3.equals(str)) {
                String str4 = (String) pair.second;
                LocaleList localeList = SystemLocaleWrapper.get();
                int i2 = 0;
                while (true) {
                    ArrayList arrayList2 = (ArrayList) enabledInputMethodsAndSubtypeList;
                    if (i2 >= arrayList2.size()) {
                        break;
                    }
                    Pair pair2 = (Pair) arrayList2.get(i2);
                    if (((String) pair2.first).equals(str3)) {
                        ArrayList arrayList3 = (ArrayList) pair2.second;
                        InputMethodInfo inputMethodInfo = this.mMethodMap.get(str3);
                        boolean isEmpty = arrayList3.isEmpty();
                        String str5 = INVALID_SUBTYPE_HASHCODE_STR;
                        if (isEmpty) {
                            if (inputMethodInfo != null && inputMethodInfo.getSubtypeCount() > 0) {
                                ArrayList implicitlyApplicableSubtypes =
                                        SubtypeUtils.getImplicitlyApplicableSubtypes(
                                                localeList, inputMethodInfo);
                                int size = implicitlyApplicableSubtypes.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    if (String.valueOf(
                                                    ((InputMethodSubtype)
                                                                    implicitlyApplicableSubtypes
                                                                            .get(i3))
                                                            .hashCode())
                                            .equals(str4)) {
                                        str2 = str4;
                                        break;
                                    }
                                }
                            }
                            str2 = str5;
                        } else {
                            int i4 = 0;
                            while (true) {
                                if (i4 >= arrayList3.size()) {
                                    break;
                                }
                                String str6 = (String) arrayList3.get(i4);
                                if (str6.equals(str4)) {
                                    try {
                                        if (SubtypeUtils.getSubtypeIdFromHashCode(
                                                        inputMethodInfo, Integer.parseInt(str4))
                                                != -1) {
                                            str2 = str6;
                                        }
                                    } catch (NumberFormatException unused) {
                                    }
                                } else {
                                    i4++;
                                }
                            }
                            str2 = str5;
                        }
                    } else {
                        i2++;
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    return new Pair(str3, str2);
                }
            }
            i++;
        }
    }

    public final AdditionalSubtypeMap getNewAdditionalSubtypeMap(
            String str,
            ArrayList arrayList,
            AdditionalSubtypeMap additionalSubtypeMap,
            PackageManagerInternal packageManagerInternal,
            int i) {
        InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            return additionalSubtypeMap;
        }
        if (!packageManagerInternal.isSameApp(
                i, UserHandle.getUserId(i), 0L, inputMethodInfo.getPackageName())) {
            return additionalSubtypeMap;
        }
        if (!arrayList.isEmpty()) {
            String id = inputMethodInfo.getId();
            additionalSubtypeMap.getClass();
            ArrayMap arrayMap = new ArrayMap(additionalSubtypeMap.mMap);
            arrayMap.put(id, arrayList);
            return new AdditionalSubtypeMap(arrayMap);
        }
        String id2 = inputMethodInfo.getId();
        if (additionalSubtypeMap.mMap.isEmpty() || !additionalSubtypeMap.mMap.containsKey(id2)) {
            return additionalSubtypeMap;
        }
        ArrayMap arrayMap2 = new ArrayMap(additionalSubtypeMap.mMap);
        arrayMap2.remove(id2);
        return arrayMap2.isEmpty()
                ? AdditionalSubtypeMap.EMPTY_MAP
                : new AdditionalSubtypeMap(arrayMap2);
    }

    public final String getSelectedInputMethod() {
        return SecureSettingsWrapper.getString("default_input_method", null, this.mUserId);
    }

    public final int getSelectedInputMethodSubtypeId(String str) {
        InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            return -1;
        }
        return SubtypeUtils.getSubtypeIdFromHashCode(
                inputMethodInfo,
                SecureSettingsWrapper.getInt(-1, this.mUserId, "selected_input_method_subtype"));
    }

    public final boolean isShowImeWithHardKeyboardEnabled() {
        String str = SystemProperties.get("ro.build.characteristics");
        int i = (str == null || !str.contains("tablet")) ? 1 : 0;
        int i2 = this.mUserId;
        return i2 == 0
                ? SecureSettingsWrapper.getInt(i, 0, "show_ime_with_hard_keyboard") == 1
                : SecureSettingsWrapper.getInt(0, i2, "show_ime_with_hard_keyboard") == 1;
    }

    public final boolean isShowKeyboardButton() {
        int i = this.mUserId;
        return i == 0
                ? SecureSettingsWrapper.getInt(1, 0, "show_keyboard_button") == 1
                : SecureSettingsWrapper.getInt(1, i, "show_keyboard_button") == 1;
    }

    public final List loadInputMethodAndSubtypeHistory() {
        ArrayList arrayList = new ArrayList();
        String string =
                SecureSettingsWrapper.getString("input_methods_subtype_history", "", this.mUserId);
        if (TextUtils.isEmpty(string)) {
            return arrayList;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        TextUtils.SimpleStringSplitter simpleStringSplitter2 =
                new TextUtils.SimpleStringSplitter(';');
        simpleStringSplitter.setString(string);
        while (simpleStringSplitter.hasNext()) {
            simpleStringSplitter2.setString(simpleStringSplitter.next());
            if (simpleStringSplitter2.hasNext()) {
                arrayList.add(
                        new Pair(
                                simpleStringSplitter2.next(),
                                simpleStringSplitter2.hasNext()
                                        ? simpleStringSplitter2.next()
                                        : INVALID_SUBTYPE_HASHCODE_STR));
            }
        }
        return arrayList;
    }

    public final void putEnabledInputMethodsStr(String str) {
        if (TextUtils.isEmpty(str)) {
            putString("enabled_input_methods", null);
        } else {
            putString("enabled_input_methods", str);
        }
    }

    public final void putSelectedDefaultDeviceInputMethod(String str) {
        putString("default_device_input_method", str);
    }

    public final void putSelectedInputMethod(String str) {
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "putSelectedInputMethodStr: ", str, ", userId : ");
        m.append(this.mUserId);
        m.append(", callers : ");
        m.append(Debug.getCallers(10));
        Slog.d("InputMethodSettings", m.toString());
        putString("default_input_method", str);
    }

    public final void putString(String str, String str2) {
        SecureSettingsWrapper.get(this.mUserId).putString(str, str2);
    }

    public final boolean setEnabledInputMethodSubtypes(String str, int[] iArr) {
        InputMethodInfo inputMethodInfo = this.mMethodMap.get(str);
        if (inputMethodInfo == null) {
            return false;
        }
        IntArray intArray = new IntArray(iArr.length);
        for (int i : iArr) {
            if (i != -1
                    && SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, i) != -1
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

    public final void setShowImeWithHardKeyboard(boolean z) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "setShowImeWithHardKeyboard show = ", ", callers= ", z);
        m.append(Debug.getCallers(3));
        Slog.i("InputMethodSettings", m.toString());
        SecureSettingsWrapper.get(this.mUserId).putInt(z ? 1 : 0, "show_ime_with_hard_keyboard");
    }
}
