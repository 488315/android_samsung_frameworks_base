package com.android.server.inputmethod;

import android.content.Context;
import android.content.res.Resources;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public abstract class InputMethodInfoUtils {
    public static final Locale[] SEARCH_ORDER_OF_FALLBACK_LOCALES = {Locale.ENGLISH, Locale.US, Locale.UK};
    public static final Locale ENGLISH_LOCALE = new Locale("en");

    public final class InputMethodListBuilder {
        public final LinkedHashSet mInputMethodSet = new LinkedHashSet();

        public final void fillImes(List list, Context context, boolean z, Locale locale, boolean z2, String str) {
            for (int i = 0; i < list.size(); i++) {
                InputMethodInfo inputMethodInfo = (InputMethodInfo) list.get(i);
                if (InputMethodInfoUtils.isSystemImeThatHasSubtypeOf(inputMethodInfo, context, z, locale, z2, str)) {
                    this.mInputMethodSet.add(inputMethodInfo);
                }
            }
        }
    }

    /* renamed from: -$$Nest$smisSystemAuxilialyImeThatHasAutomaticSubtype, reason: not valid java name */
    public static boolean m589$$Nest$smisSystemAuxilialyImeThatHasAutomaticSubtype(InputMethodInfo inputMethodInfo, Context context, boolean z) {
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

    public static void getAuxilaryRemoveList(List list) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            String id = ((InputMethodInfo) arrayList.get(i)).getId();
            if ("com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco".equals(id) || "com.samsung.android.svoiceime/.BixbyDictVoiceReco".equals(id) || "com.samsung.android.bixby.service/.dictation.DictationInputMethodService".equals(id) || "com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService".equals(id)) {
                arrayList.remove(i);
                i--;
            }
            i++;
        }
    }

    public static ArrayList getDefaultEnabledImes(Context context, List list, boolean z) {
        Locale locale;
        Locale locale2;
        int i;
        Locale[] localeArr = SEARCH_ORDER_OF_FALLBACK_LOCALES;
        int length = localeArr.length;
        int i2 = 0;
        while (true) {
            locale = null;
            if (i2 < length) {
                locale2 = localeArr[i2];
                for (0; i < list.size(); i + 1) {
                    i = isSystemImeThatHasSubtypeOf((InputMethodInfo) list.get(i), context, true, locale2, true, "keyboard") ? 0 : i + 1;
                }
                i2++;
            } else {
                Locale[] localeArr2 = SEARCH_ORDER_OF_FALLBACK_LOCALES;
                int length2 = localeArr2.length;
                loop5: for (int i3 = 0; i3 < length2; i3++) {
                    locale2 = localeArr2[i3];
                    for (int i4 = 0; i4 < list.size(); i4++) {
                        if (!isSystemImeThatHasSubtypeOf((InputMethodInfo) list.get(i4), context, false, locale2, true, "keyboard")) {
                        }
                    }
                }
                Slog.w("InputMethodInfoUtils", "Found no fallback locale. imis=" + Arrays.toString(list.toArray()));
                locale2 = null;
            }
        }
        try {
            locale = context.getResources().getConfiguration().locale;
        } catch (Resources.NotFoundException unused) {
        }
        InputMethodListBuilder inputMethodListBuilder = new InputMethodListBuilder();
        inputMethodListBuilder.fillImes(list, context, true, locale, true, "keyboard");
        if (inputMethodListBuilder.mInputMethodSet.isEmpty()) {
            inputMethodListBuilder.fillImes(list, context, true, locale, false, "keyboard");
            if (inputMethodListBuilder.mInputMethodSet.isEmpty()) {
                inputMethodListBuilder.fillImes(list, context, true, locale2, true, "keyboard");
                if (inputMethodListBuilder.mInputMethodSet.isEmpty()) {
                    inputMethodListBuilder.fillImes(list, context, true, locale2, false, "keyboard");
                    if (inputMethodListBuilder.mInputMethodSet.isEmpty()) {
                        inputMethodListBuilder.fillImes(list, context, false, locale2, true, "keyboard");
                        if (inputMethodListBuilder.mInputMethodSet.isEmpty()) {
                            inputMethodListBuilder.fillImes(list, context, false, locale2, false, "keyboard");
                            if (inputMethodListBuilder.mInputMethodSet.isEmpty()) {
                                Slog.w("InputMethodInfoUtils", "No software keyboard is found. imis=" + Arrays.toString(list.toArray()) + " systemLocale=" + locale + " fallbackLocale=" + locale2);
                            }
                        }
                    }
                }
            }
        }
        if (!z) {
            inputMethodListBuilder.fillImes(list, context, true, locale, true, null);
            Iterator it = inputMethodListBuilder.mInputMethodSet.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((InputMethodInfo) it.next()).isAuxiliaryIme()) {
                        break;
                    }
                } else {
                    boolean z2 = false;
                    for (int i5 = 0; i5 < list.size(); i5++) {
                        InputMethodInfo inputMethodInfo = (InputMethodInfo) list.get(i5);
                        if (m589$$Nest$smisSystemAuxilialyImeThatHasAutomaticSubtype(inputMethodInfo, context, true)) {
                            inputMethodListBuilder.mInputMethodSet.add(inputMethodInfo);
                            z2 = true;
                        }
                    }
                    if (!z2) {
                        for (int i6 = 0; i6 < list.size(); i6++) {
                            InputMethodInfo inputMethodInfo2 = (InputMethodInfo) list.get(i6);
                            if (m589$$Nest$smisSystemAuxilialyImeThatHasAutomaticSubtype(inputMethodInfo2, context, false)) {
                                inputMethodListBuilder.mInputMethodSet.add(inputMethodInfo2);
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList(inputMethodListBuilder.mInputMethodSet);
    }

    public static InputMethodInfo getMostApplicableDefaultIME(List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return null;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) arrayList.get(i);
            if ("com.samsung.android.honeyboard/.service.HoneyBoardService".equals(inputMethodInfo.getId())) {
                StringBuilder sb = new StringBuilder("getMostApplicableDefaultIME(): Set the default IME to Honeyboard");
                sb.append(inputMethodInfo);
                ActivityManagerService$$ExternalSyntheticOutline0.m(10, sb, "InputMethodInfoUtils");
                return inputMethodInfo;
            }
        }
        int i2 = -1;
        while (size > 0) {
            size--;
            InputMethodInfo inputMethodInfo2 = (InputMethodInfo) arrayList.get(size);
            if (!inputMethodInfo2.isAuxiliaryIme()) {
                if (inputMethodInfo2.isSystem() && SubtypeUtils.containsSubtypeOf(inputMethodInfo2, ENGLISH_LOCALE, false, "keyboard")) {
                    return inputMethodInfo2;
                }
                if (i2 < 0 && inputMethodInfo2.isSystem()) {
                    i2 = size;
                }
            }
        }
        return (InputMethodInfo) arrayList.get(Math.max(i2, 0));
    }

    public static boolean isSystemImeThatHasSubtypeOf(InputMethodInfo inputMethodInfo, Context context, boolean z, Locale locale, boolean z2, String str) {
        if (!inputMethodInfo.isSystem()) {
            return false;
        }
        if (!z || inputMethodInfo.isDefault(context)) {
            return SubtypeUtils.containsSubtypeOf(inputMethodInfo, locale, z2, str);
        }
        return false;
    }
}
