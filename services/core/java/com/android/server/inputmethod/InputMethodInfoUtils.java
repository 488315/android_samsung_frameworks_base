package com.android.server.inputmethod;

import android.content.Context;
import android.os.Debug;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class InputMethodInfoUtils {
  public static final Locale[] SEARCH_ORDER_OF_FALLBACK_LOCALES = {
    Locale.ENGLISH, Locale.US, Locale.UK
  };
  public static final Locale ENGLISH_LOCALE = new Locale("en");

  public final class InputMethodListBuilder {
    public final LinkedHashSet mInputMethodSet;

    public InputMethodListBuilder() {
      this.mInputMethodSet = new LinkedHashSet();
    }

    public InputMethodListBuilder fillImes(
        ArrayList arrayList, Context context, boolean z, Locale locale, boolean z2, String str) {
      for (int i = 0; i < arrayList.size(); i++) {
        InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayList.get(i);
        if (InputMethodInfoUtils.isSystemImeThatHasSubtypeOf(
            inputMethodInfo, context, z, locale, z2, str)) {
          this.mInputMethodSet.add(inputMethodInfo);
        }
      }
      return this;
    }

    public InputMethodListBuilder fillAuxiliaryImes(ArrayList arrayList, Context context) {
      Iterator it = this.mInputMethodSet.iterator();
      while (it.hasNext()) {
        if (((InputMethodInfo) it.next()).isAuxiliaryIme()) {
          return this;
        }
      }
      boolean z = false;
      for (int i = 0; i < arrayList.size(); i++) {
        InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayList.get(i);
        if (InputMethodInfoUtils.isSystemAuxilialyImeThatHasAutomaticSubtype(
            inputMethodInfo, context, true)) {
          this.mInputMethodSet.add(inputMethodInfo);
          z = true;
        }
      }
      if (z) {
        return this;
      }
      for (int i2 = 0; i2 < arrayList.size(); i2++) {
        InputMethodInfo inputMethodInfo2 = (InputMethodInfo) arrayList.get(i2);
        if (InputMethodInfoUtils.isSystemAuxilialyImeThatHasAutomaticSubtype(
            inputMethodInfo2, context, false)) {
          this.mInputMethodSet.add(inputMethodInfo2);
        }
      }
      return this;
    }

    public boolean isEmpty() {
      return this.mInputMethodSet.isEmpty();
    }

    public ArrayList build() {
      return new ArrayList(this.mInputMethodSet);
    }
  }

  public static InputMethodListBuilder getMinimumKeyboardSetWithSystemLocale(
      ArrayList arrayList, Context context, Locale locale, Locale locale2) {
    InputMethodListBuilder inputMethodListBuilder = new InputMethodListBuilder();
    inputMethodListBuilder.fillImes(arrayList, context, true, locale, true, "keyboard");
    if (!inputMethodListBuilder.isEmpty()) {
      return inputMethodListBuilder;
    }
    inputMethodListBuilder.fillImes(arrayList, context, true, locale, false, "keyboard");
    if (!inputMethodListBuilder.isEmpty()) {
      return inputMethodListBuilder;
    }
    inputMethodListBuilder.fillImes(arrayList, context, true, locale2, true, "keyboard");
    if (!inputMethodListBuilder.isEmpty()) {
      return inputMethodListBuilder;
    }
    inputMethodListBuilder.fillImes(arrayList, context, true, locale2, false, "keyboard");
    if (!inputMethodListBuilder.isEmpty()) {
      return inputMethodListBuilder;
    }
    inputMethodListBuilder.fillImes(arrayList, context, false, locale2, true, "keyboard");
    if (!inputMethodListBuilder.isEmpty()) {
      return inputMethodListBuilder;
    }
    inputMethodListBuilder.fillImes(arrayList, context, false, locale2, false, "keyboard");
    if (!inputMethodListBuilder.isEmpty()) {
      return inputMethodListBuilder;
    }
    Slog.w(
        "InputMethodInfoUtils",
        "No software keyboard is found. imis="
            + Arrays.toString(arrayList.toArray())
            + " systemLocale="
            + locale
            + " fallbackLocale="
            + locale2);
    return inputMethodListBuilder;
  }

  public static ArrayList getDefaultEnabledImes(Context context, ArrayList arrayList, boolean z) {
    Locale fallbackLocaleForDefaultIme = getFallbackLocaleForDefaultIme(arrayList, context);
    Locale systemLocaleFromContext = LocaleUtils.getSystemLocaleFromContext(context);
    InputMethodListBuilder minimumKeyboardSetWithSystemLocale =
        getMinimumKeyboardSetWithSystemLocale(
            arrayList, context, systemLocaleFromContext, fallbackLocaleForDefaultIme);
    if (!z) {
      minimumKeyboardSetWithSystemLocale
          .fillImes(
              arrayList,
              context,
              true,
              systemLocaleFromContext,
              true,
              SubtypeUtils.SUBTYPE_MODE_ANY)
          .fillAuxiliaryImes(arrayList, context);
    }
    return minimumKeyboardSetWithSystemLocale.build();
  }

  public static ArrayList getDefaultEnabledImes(Context context, ArrayList arrayList) {
    return getDefaultEnabledImes(context, arrayList, false);
  }

  public static InputMethodInfo chooseSystemVoiceIme(ArrayMap arrayMap, String str, String str2) {
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayMap.get(str2);
    if (inputMethodInfo != null
        && inputMethodInfo.isSystem()
        && inputMethodInfo.getPackageName().equals(str)) {
      return inputMethodInfo;
    }
    int size = arrayMap.size();
    InputMethodInfo inputMethodInfo2 = null;
    for (int i = 0; i < size; i++) {
      InputMethodInfo inputMethodInfo3 = (InputMethodInfo) arrayMap.valueAt(i);
      if (inputMethodInfo3.isSystem() && TextUtils.equals(inputMethodInfo3.getPackageName(), str)) {
        if (inputMethodInfo2 != null) {
          Slog.e(
              "InputMethodInfoUtils",
              "At most one InputMethodService can be published in systemSpeechRecognizer: "
                  + str
                  + ". Ignoring all of them.");
          return null;
        }
        inputMethodInfo2 = inputMethodInfo3;
      }
    }
    return inputMethodInfo2;
  }

  public static InputMethodInfo getMostApplicableDefaultIME(List list) {
    if (list == null || list.isEmpty()) {
      return null;
    }
    int size = list.size();
    for (int i = 0; i < size; i++) {
      InputMethodInfo inputMethodInfo = (InputMethodInfo) list.get(i);
      if ("com.samsung.android.honeyboard/.service.HoneyBoardService"
          .equals(inputMethodInfo.getId())) {
        Slog.d(
            "InputMethodInfoUtils",
            "getMostApplicableDefaultIME(): Set the default IME to Honeyboard"
                + inputMethodInfo
                + Debug.getCallers(10));
        return inputMethodInfo;
      }
    }
    int i2 = -1;
    while (size > 0) {
      size--;
      InputMethodInfo inputMethodInfo2 = (InputMethodInfo) list.get(size);
      if (!inputMethodInfo2.isAuxiliaryIme()) {
        if (inputMethodInfo2.isSystem()
            && SubtypeUtils.containsSubtypeOf(
                inputMethodInfo2, ENGLISH_LOCALE, false, "keyboard")) {
          return inputMethodInfo2;
        }
        if (i2 < 0 && inputMethodInfo2.isSystem()) {
          i2 = size;
        }
      }
    }
    return (InputMethodInfo) list.get(Math.max(i2, 0));
  }

  public static boolean isSystemAuxilialyImeThatHasAutomaticSubtype(
      InputMethodInfo inputMethodInfo, Context context, boolean z) {
    if (!inputMethodInfo.isSystem()) {
      return false;
    }
    if ((z && !inputMethodInfo.isDefault(context)) || !inputMethodInfo.isAuxiliaryIme()) {
      return false;
    }
    int subtypeCount = inputMethodInfo.getSubtypeCount();
    for (int i = 0; i < subtypeCount; i++) {
      if (inputMethodInfo.getSubtypeAt(i).overridesImplicitlyEnabledSubtype()) {
        return true;
      }
    }
    return false;
  }

  public static Locale getFallbackLocaleForDefaultIme(ArrayList arrayList, Context context) {
    for (Locale locale : SEARCH_ORDER_OF_FALLBACK_LOCALES) {
      for (int i = 0; i < arrayList.size(); i++) {
        if (isSystemImeThatHasSubtypeOf(
            (InputMethodInfo) arrayList.get(i), context, true, locale, true, "keyboard")) {
          return locale;
        }
      }
    }
    for (Locale locale2 : SEARCH_ORDER_OF_FALLBACK_LOCALES) {
      for (int i2 = 0; i2 < arrayList.size(); i2++) {
        if (isSystemImeThatHasSubtypeOf(
            (InputMethodInfo) arrayList.get(i2), context, false, locale2, true, "keyboard")) {
          return locale2;
        }
      }
    }
    Slog.w(
        "InputMethodInfoUtils",
        "Found no fallback locale. imis=" + Arrays.toString(arrayList.toArray()));
    return null;
  }

  public static boolean isSystemImeThatHasSubtypeOf(
      InputMethodInfo inputMethodInfo,
      Context context,
      boolean z,
      Locale locale,
      boolean z2,
      String str) {
    if (!inputMethodInfo.isSystem()) {
      return false;
    }
    if (!z || inputMethodInfo.isDefault(context)) {
      return SubtypeUtils.containsSubtypeOf(inputMethodInfo, locale, z2, str);
    }
    return false;
  }

  public static List getAuxilaryRemoveList(List list) {
    int i = 0;
    while (i < list.size()) {
      String id = ((InputMethodInfo) list.get(i)).getId();
      if ("com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco"
              .equals(id)
          || "com.samsung.android.svoiceime/.BixbyDictVoiceReco".equals(id)
          || "com.samsung.android.bixby.service/.dictation.DictationInputMethodService"
              .equals(id)) {
        list.remove(i);
        i--;
      }
      i++;
    }
    return list;
  }
}
