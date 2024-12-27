package com.android.server.inputmethod;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InputMethodSubtypeSwitchingController {
    public final Context mContext;
    public ControllerImpl mController;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ControllerImpl {
        public final DynamicRotationList mSwitchingAwareRotationList;
        public final StaticRotationList mSwitchingUnawareRotationList;

        public ControllerImpl(
                DynamicRotationList dynamicRotationList, StaticRotationList staticRotationList) {
            this.mSwitchingAwareRotationList = dynamicRotationList;
            this.mSwitchingUnawareRotationList = staticRotationList;
        }

        public static ControllerImpl createFrom(ControllerImpl controllerImpl, List list) {
            DynamicRotationList dynamicRotationList;
            StaticRotationList staticRotationList;
            List filterImeSubtypeList = filterImeSubtypeList(list, true);
            StaticRotationList staticRotationList2 = null;
            if (controllerImpl == null
                    || (dynamicRotationList = controllerImpl.mSwitchingAwareRotationList) == null
                    || !Objects.equals(dynamicRotationList.mImeSubtypeList, filterImeSubtypeList)) {
                dynamicRotationList = null;
            }
            if (dynamicRotationList == null) {
                dynamicRotationList = new DynamicRotationList(filterImeSubtypeList);
            }
            List filterImeSubtypeList2 = filterImeSubtypeList(list, false);
            if (controllerImpl != null
                    && (staticRotationList = controllerImpl.mSwitchingUnawareRotationList) != null
                    && Objects.equals(staticRotationList.mImeSubtypeList, filterImeSubtypeList2)) {
                staticRotationList2 = staticRotationList;
            }
            if (staticRotationList2 == null) {
                staticRotationList2 = new StaticRotationList(filterImeSubtypeList2);
            }
            return new ControllerImpl(dynamicRotationList, staticRotationList2);
        }

        public static List filterImeSubtypeList(List list, boolean z) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list;
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ImeSubtypeListItem imeSubtypeListItem = (ImeSubtypeListItem) arrayList2.get(i);
                if (imeSubtypeListItem.mImi.supportsSwitchingToNextInputMethod() == z) {
                    arrayList.add(imeSubtypeListItem);
                }
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DynamicRotationList {
        public final List mImeSubtypeList;
        public final int[] mUsageHistoryOfSubtypeListItemIndex;

        public DynamicRotationList(List list) {
            this.mImeSubtypeList = list;
            ArrayList arrayList = (ArrayList) list;
            this.mUsageHistoryOfSubtypeListItemIndex = new int[arrayList.size()];
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mUsageHistoryOfSubtypeListItemIndex[i] = i;
            }
        }

        public final int getUsageRank(
                InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
            int subtypeIdFromHashCode =
                    inputMethodSubtype != null
                            ? SubtypeUtils.getSubtypeIdFromHashCode(
                                    inputMethodInfo, inputMethodSubtype.hashCode())
                            : -1;
            int[] iArr = this.mUsageHistoryOfSubtypeListItemIndex;
            int length = iArr.length;
            for (int i = 0; i < length; i++) {
                ImeSubtypeListItem imeSubtypeListItem =
                        (ImeSubtypeListItem) this.mImeSubtypeList.get(iArr[i]);
                if (imeSubtypeListItem.mImi.equals(inputMethodInfo)
                        && imeSubtypeListItem.mSubtypeId == subtypeIdFromHashCode) {
                    return i;
                }
            }
            return -1;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImeSubtypeListItem implements Comparable {
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
            String languageFromLocaleString = LocaleUtils.getLanguageFromLocaleString(str2);
            String languageFromLocaleString2 = LocaleUtils.getLanguageFromLocaleString(str);
            if (languageFromLocaleString.length() >= 2
                    && languageFromLocaleString.equals(languageFromLocaleString2)) {
                z = true;
            }
            this.mIsSystemLanguage = z;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            ImeSubtypeListItem imeSubtypeListItem = (ImeSubtypeListItem) obj;
            CharSequence charSequence = this.mImeName;
            CharSequence charSequence2 = imeSubtypeListItem.mImeName;
            boolean isEmpty = TextUtils.isEmpty(charSequence);
            boolean isEmpty2 = TextUtils.isEmpty(charSequence2);
            int compareTo =
                    (isEmpty || isEmpty2)
                            ? (isEmpty ? 1 : 0) - (isEmpty2 ? 1 : 0)
                            : charSequence.toString().compareTo(charSequence2.toString());
            if (compareTo != 0) {
                return compareTo;
            }
            int i = (this.mIsSystemLocale ? -1 : 0) - (imeSubtypeListItem.mIsSystemLocale ? -1 : 0);
            if (i != 0) {
                return i;
            }
            int i2 =
                    (this.mIsSystemLanguage ? -1 : 0)
                            - (imeSubtypeListItem.mIsSystemLanguage ? -1 : 0);
            if (i2 != 0) {
                return i2;
            }
            CharSequence charSequence3 = this.mSubtypeName;
            CharSequence charSequence4 = imeSubtypeListItem.mSubtypeName;
            boolean isEmpty3 = TextUtils.isEmpty(charSequence3);
            boolean isEmpty4 = TextUtils.isEmpty(charSequence4);
            int compareTo2 =
                    (isEmpty3 || isEmpty4)
                            ? (isEmpty3 ? 1 : 0) - (isEmpty4 ? 1 : 0)
                            : charSequence3.toString().compareTo(charSequence4.toString());
            return compareTo2 != 0
                    ? compareTo2
                    : this.mImi.getId().compareTo(imeSubtypeListItem.mImi.getId());
        }

        public final boolean equals(Object obj) {
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

        public final String toString() {
            StringBuilder sb = new StringBuilder("ImeSubtypeListItem{mImeName=");
            sb.append((Object) this.mImeName);
            sb.append(" mSubtypeName=");
            sb.append((Object) this.mSubtypeName);
            sb.append(" mSubtypeId=");
            sb.append(this.mSubtypeId);
            sb.append(" mIsSystemLocale=");
            sb.append(this.mIsSystemLocale);
            sb.append(" mIsSystemLanguage=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.mIsSystemLanguage);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StaticRotationList {
        public final List mImeSubtypeList;

        public StaticRotationList(List list) {
            this.mImeSubtypeList = list;
        }
    }

    public InputMethodSubtypeSwitchingController(
            Context context, InputMethodMap inputMethodMap, int i) {
        this.mContext = context;
        this.mUserId = i;
        this.mController =
                ControllerImpl.createFrom(
                        null,
                        getSortedInputMethodAndSubtypeList(
                                false, false, false, context, inputMethodMap, i));
    }

    public static List getSortedInputMethodAndSubtypeList(
            boolean z,
            boolean z2,
            boolean z3,
            Context context,
            InputMethodMap inputMethodMap,
            int i) {
        Context context2;
        int i2;
        Context context3;
        int i3;
        int i4;
        ArraySet arraySet;
        InputMethodInfo inputMethodInfo;
        int i5;
        int i6 = 0;
        Context createContextAsUser =
                context.getUserId() == i
                        ? context
                        : context.createContextAsUser(UserHandle.of(i), 0);
        String languageTag = SystemLocaleWrapper.get().get(0).toLanguageTag();
        InputMethodSettings inputMethodSettings = new InputMethodSettings(inputMethodMap, i);
        CharSequence charSequence = null;
        ArrayList enabledInputMethodListWithFilter =
                inputMethodSettings.getEnabledInputMethodListWithFilter(null);
        if (enabledInputMethodListWithFilter.isEmpty()) {
            Slog.w("InputMethodSubtypeSwitchingController", "Enabled input method list is empty.");
            return new ArrayList();
        }
        boolean z4 = (z2 && z) ? false : z;
        ArrayList arrayList = new ArrayList();
        int size = enabledInputMethodListWithFilter.size();
        int i7 = 0;
        while (i7 < size) {
            InputMethodInfo inputMethodInfo2 =
                    (InputMethodInfo) enabledInputMethodListWithFilter.get(i7);
            if (!z3 || inputMethodInfo2.shouldShowInInputMethodPicker()) {
                List enabledInputMethodSubtypeList =
                        inputMethodSettings.getEnabledInputMethodSubtypeList(
                                inputMethodInfo2, true);
                ArraySet arraySet2 = new ArraySet();
                Iterator it = enabledInputMethodSubtypeList.iterator();
                while (it.hasNext()) {
                    arraySet2.add(String.valueOf(((InputMethodSubtype) it.next()).hashCode()));
                }
                CharSequence loadLabel =
                        inputMethodInfo2.loadLabel(createContextAsUser.getPackageManager());
                if (arraySet2.size() > 0) {
                    int subtypeCount = inputMethodInfo2.getSubtypeCount();
                    int i8 = i6;
                    while (i8 < subtypeCount) {
                        InputMethodSubtype subtypeAt = inputMethodInfo2.getSubtypeAt(i8);
                        String valueOf = String.valueOf(subtypeAt.hashCode());
                        if (!arraySet2.contains(valueOf) || (!z4 && subtypeAt.isAuxiliary())) {
                            context3 = createContextAsUser;
                            i3 = i8;
                            i4 = subtypeCount;
                            arraySet = arraySet2;
                            inputMethodInfo = inputMethodInfo2;
                            i5 = i7;
                        } else {
                            if (!subtypeAt.overridesImplicitlyEnabledSubtype()) {
                                charSequence =
                                        subtypeAt.getDisplayName(
                                                createContextAsUser,
                                                inputMethodInfo2.getPackageName(),
                                                inputMethodInfo2.getServiceInfo().applicationInfo);
                            }
                            context3 = createContextAsUser;
                            i3 = i8;
                            CharSequence charSequence2 = charSequence;
                            i4 = subtypeCount;
                            arraySet = arraySet2;
                            inputMethodInfo = inputMethodInfo2;
                            i5 = i7;
                            arrayList.add(
                                    new ImeSubtypeListItem(
                                            loadLabel,
                                            charSequence2,
                                            inputMethodInfo2,
                                            i3,
                                            subtypeAt.getLocale(),
                                            languageTag));
                            arraySet.remove(valueOf);
                        }
                        i8 = i3 + 1;
                        subtypeCount = i4;
                        arraySet2 = arraySet;
                        i7 = i5;
                        createContextAsUser = context3;
                        inputMethodInfo2 = inputMethodInfo;
                        charSequence = null;
                    }
                } else {
                    context2 = createContextAsUser;
                    i2 = i7;
                    arrayList.add(
                            new ImeSubtypeListItem(
                                    loadLabel, null, inputMethodInfo2, -1, null, languageTag));
                    i7 = i2 + 1;
                    createContextAsUser = context2;
                    charSequence = null;
                    i6 = 0;
                }
            }
            context2 = createContextAsUser;
            i2 = i7;
            i7 = i2 + 1;
            createContextAsUser = context2;
            charSequence = null;
            i6 = 0;
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final void dump(Printer printer) {
        ControllerImpl controllerImpl = this.mController;
        if (controllerImpl == null) {
            ((PrintWriterPrinter) printer).println("    mController=null");
            return;
        }
        PrintWriterPrinter printWriterPrinter = (PrintWriterPrinter) printer;
        printWriterPrinter.println("    mSwitchingAwareRotationList:");
        int i = 0;
        while (true) {
            DynamicRotationList dynamicRotationList = controllerImpl.mSwitchingAwareRotationList;
            int[] iArr = dynamicRotationList.mUsageHistoryOfSubtypeListItemIndex;
            if (i >= iArr.length) {
                break;
            }
            printWriterPrinter.println(
                    "      rank="
                            + i
                            + " item="
                            + ((ImeSubtypeListItem)
                                    dynamicRotationList.mImeSubtypeList.get(iArr[i])));
            i++;
        }
        printWriterPrinter.println("    mSwitchingUnawareRotationList:");
        StaticRotationList staticRotationList = controllerImpl.mSwitchingUnawareRotationList;
        int size = staticRotationList.mImeSubtypeList.size();
        for (int i2 = 0; i2 < size; i2++) {
            printWriterPrinter.println(
                    "      rank="
                            + i2
                            + " item="
                            + ((ImeSubtypeListItem) staticRotationList.mImeSubtypeList.get(i2)));
        }
    }

    public final ImeSubtypeListItem getNextInputMethodLocked(
            boolean z, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
        ControllerImpl controllerImpl = this.mController;
        if (controllerImpl == null || inputMethodInfo == null) {
            return null;
        }
        int i = 1;
        if (inputMethodInfo.supportsSwitchingToNextInputMethod()) {
            DynamicRotationList dynamicRotationList = controllerImpl.mSwitchingAwareRotationList;
            int usageRank = dynamicRotationList.getUsageRank(inputMethodInfo, inputMethodSubtype);
            if (usageRank < 0) {
                return null;
            }
            int[] iArr = dynamicRotationList.mUsageHistoryOfSubtypeListItemIndex;
            int length = iArr.length;
            while (i < length) {
                ImeSubtypeListItem imeSubtypeListItem =
                        (ImeSubtypeListItem)
                                dynamicRotationList.mImeSubtypeList.get(
                                        iArr[(usageRank + i) % length]);
                if (!z || inputMethodInfo.equals(imeSubtypeListItem.mImi)) {
                    return imeSubtypeListItem;
                }
                i++;
            }
            return null;
        }
        StaticRotationList staticRotationList = controllerImpl.mSwitchingUnawareRotationList;
        if (staticRotationList.mImeSubtypeList.size() <= 1) {
            return null;
        }
        int i2 = -1;
        int subtypeIdFromHashCode =
                inputMethodSubtype != null
                        ? SubtypeUtils.getSubtypeIdFromHashCode(
                                inputMethodInfo, inputMethodSubtype.hashCode())
                        : -1;
        int size = staticRotationList.mImeSubtypeList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            ImeSubtypeListItem imeSubtypeListItem2 =
                    (ImeSubtypeListItem) staticRotationList.mImeSubtypeList.get(i3);
            if (inputMethodInfo.equals(imeSubtypeListItem2.mImi)
                    && imeSubtypeListItem2.mSubtypeId == subtypeIdFromHashCode) {
                i2 = i3;
                break;
            }
            i3++;
        }
        if (i2 < 0) {
            return null;
        }
        int size2 = staticRotationList.mImeSubtypeList.size();
        while (i < size2) {
            ImeSubtypeListItem imeSubtypeListItem3 =
                    (ImeSubtypeListItem) staticRotationList.mImeSubtypeList.get((i2 + i) % size2);
            if (!z || inputMethodInfo.equals(imeSubtypeListItem3.mImi)) {
                return imeSubtypeListItem3;
            }
            i++;
        }
        return null;
    }
}
