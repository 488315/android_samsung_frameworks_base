package com.android.server.input;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.hardware.input.KeyboardLayoutSelectionResult;
import android.icu.lang.UScript;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InputDevice;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import com.android.internal.R;
import com.android.internal.inputmethod.InputMethodSubtypeHandle;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.inputmethod.InputMethodManagerInternal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeyboardLayoutManager implements InputManager.InputDeviceListener {
    public static final boolean DEBUG = Log.isLoggable("KeyboardLayoutManager", 3);
    public final Context mContext;
    public ImeInfo mCurrentImeInfo;
    public final PersistentDataStore mDataStore;
    public final Handler mHandler;
    public final NativeInputManagerService mNative;
    public final SparseArray mConfiguredKeyboards = new SparseArray();
    public final Map mKeyboardLayoutCache = new ArrayMap();
    public HashSet mAvailableLayouts = new HashSet();
    public final Object mImeInfoLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ImeInfo {
        public final InputMethodSubtype mImeSubtype;
        public final InputMethodSubtypeHandle mImeSubtypeHandle;
        public final int mUserId;

        public ImeInfo(
                int i, InputMethodInfo inputMethodInfo, InputMethodSubtype inputMethodSubtype) {
            this(
                    i,
                    InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodSubtype),
                    inputMethodSubtype);
        }

        public ImeInfo(
                int i,
                InputMethodSubtypeHandle inputMethodSubtypeHandle,
                InputMethodSubtype inputMethodSubtype) {
            this.mUserId = i;
            this.mImeSubtypeHandle = inputMethodSubtypeHandle;
            this.mImeSubtype = inputMethodSubtype;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyboardConfiguration {
        public Set mConfiguredLayouts;
        public final int mDeviceId;

        public KeyboardConfiguration(int i) {
            this.mDeviceId = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyboardIdentifier {
        public final /* synthetic */ int $r8$classId = 1;
        public Object mIdentifier;
        public String mLanguageTag;
        public String mLayoutType;

        public /* synthetic */ KeyboardIdentifier() {}

        public KeyboardIdentifier(
                InputDeviceIdentifier inputDeviceIdentifier, String str, String str2) {
            Objects.requireNonNull(inputDeviceIdentifier, "identifier must not be null");
            Objects.requireNonNull(
                    inputDeviceIdentifier.getDescriptor(), "descriptor must not be null");
            this.mIdentifier = inputDeviceIdentifier;
            this.mLanguageTag = str;
            this.mLayoutType = str2;
        }

        public KeyboardIdentifier(InputDevice inputDevice) {
            this(
                    inputDevice.getIdentifier(),
                    inputDevice.getKeyboardLanguageTag(),
                    inputDevice.getKeyboardLayoutType());
        }

        public static KeyboardIdentifier parse(String str) {
            int i;
            int indexOf;
            int i2;
            int indexOf2 = str.indexOf(47);
            if (indexOf2 < 0
                    || (i = indexOf2 + 1) == str.length()
                    || (indexOf = str.indexOf(47, i)) < indexOf2 + 2
                    || (i2 = indexOf + 1) == str.length()) {
                return null;
            }
            KeyboardIdentifier keyboardIdentifier = new KeyboardIdentifier();
            keyboardIdentifier.mLanguageTag = str.substring(0, indexOf2);
            keyboardIdentifier.mLayoutType = str.substring(i, indexOf);
            keyboardIdentifier.mIdentifier = str.substring(i2);
            return keyboardIdentifier;
        }

        public int hashCode() {
            switch (this.$r8$classId) {
                case 0:
                    return Objects.hashCode(toString());
                default:
                    return super.hashCode();
            }
        }

        public String toString() {
            switch (this.$r8$classId) {
                case 0:
                    if (((InputDeviceIdentifier) this.mIdentifier).getVendorId() == 0
                            && ((InputDeviceIdentifier) this.mIdentifier).getProductId() == 0) {
                        return ((InputDeviceIdentifier) this.mIdentifier).getDescriptor();
                    }
                    StringBuilder sb = new StringBuilder("vendor:");
                    sb.append(((InputDeviceIdentifier) this.mIdentifier).getVendorId());
                    sb.append(",product:");
                    sb.append(((InputDeviceIdentifier) this.mIdentifier).getProductId());
                    String str = this.mLanguageTag;
                    if (!TextUtils.isEmpty(str)) {
                        sb.append(",languageTag:");
                        sb.append(str);
                    }
                    String str2 = this.mLayoutType;
                    if (!TextUtils.isEmpty(str2)) {
                        sb.append(",layoutType:");
                        sb.append(str2);
                    }
                    return sb.toString();
                default:
                    return super.toString();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface KeyboardLayoutVisitor {
        void visitKeyboardLayout(Resources resources, int i, KeyboardLayout keyboardLayout);
    }

    public KeyboardLayoutManager(
            Context context,
            NativeInputManagerService.NativeImpl nativeImpl,
            PersistentDataStore persistentDataStore,
            Looper looper) {
        this.mContext = context;
        this.mNative = nativeImpl;
        this.mDataStore = persistentDataStore;
        this.mHandler =
                new Handler(
                        looper,
                        new Handler
                                .Callback() { // from class:
                                              // com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda5
                            @Override // android.os.Handler.Callback
                            public final boolean handleMessage(Message message) {
                                KeyboardLayoutManager keyboardLayoutManager =
                                        KeyboardLayoutManager.this;
                                keyboardLayoutManager.getClass();
                                int i = message.what;
                                if (i == 1) {
                                    for (int i2 : (int[]) message.obj) {
                                        keyboardLayoutManager.onInputDeviceChangedInternal(
                                                i2, true);
                                    }
                                    return true;
                                }
                                if (i != 2) {
                                    if (i != 3) {
                                        return false;
                                    }
                                    keyboardLayoutManager.updateKeyboardLayouts();
                                    return true;
                                }
                                if (KeyboardLayoutManager.DEBUG) {
                                    Slog.d("KeyboardLayoutManager", "Reloading keyboard layouts.");
                                }
                                keyboardLayoutManager.mNative.reloadKeyboardLayouts();
                                return true;
                            }
                        },
                        true);
    }

    public static KeyboardLayoutSelectionResult getDefaultKeyboardLayoutBasedOnImeInfo(
            KeyboardIdentifier keyboardIdentifier,
            ImeInfo imeInfo,
            KeyboardLayout[] keyboardLayoutArr) {
        InputMethodSubtype inputMethodSubtype;
        String matchingLayoutForProvidedLanguageTagAndLayoutType;
        Arrays.sort(keyboardLayoutArr);
        for (KeyboardLayout keyboardLayout : keyboardLayoutArr) {
            if (keyboardLayout.getVendorId()
                            == ((InputDeviceIdentifier) keyboardIdentifier.mIdentifier)
                                    .getVendorId()
                    && keyboardLayout.getProductId()
                            == ((InputDeviceIdentifier) keyboardIdentifier.mIdentifier)
                                    .getProductId()) {
                if (DEBUG) {
                    Slog.d(
                            "KeyboardLayoutManager",
                            "getDefaultKeyboardLayoutBasedOnImeInfo() : Layout found based on"
                                + " vendor and product Ids. "
                                    + keyboardIdentifier
                                    + " : "
                                    + keyboardLayout.getDescriptor());
                }
                return new KeyboardLayoutSelectionResult(keyboardLayout.getDescriptor(), 2);
            }
        }
        String str =
                (((InputDeviceIdentifier) keyboardIdentifier.mIdentifier).getVendorId() == 1
                                && ((InputDeviceIdentifier) keyboardIdentifier.mIdentifier)
                                                .getProductId()
                                        == 1)
                        ? keyboardIdentifier.mLanguageTag
                        : null;
        if (str != null
                && (matchingLayoutForProvidedLanguageTagAndLayoutType =
                                getMatchingLayoutForProvidedLanguageTagAndLayoutType(
                                        keyboardLayoutArr, str, keyboardIdentifier.mLayoutType))
                        != null) {
            Slog.d(
                    "KeyboardLayoutManager",
                    "getDefaultKeyboardLayoutBasedOnImeInfo() : Layout found based on HW"
                        + " information (Language tag and Layout type). "
                            + keyboardIdentifier
                            + " : "
                            + matchingLayoutForProvidedLanguageTagAndLayoutType);
            return new KeyboardLayoutSelectionResult(
                    matchingLayoutForProvidedLanguageTagAndLayoutType, 2);
        }
        if (imeInfo == null
                || imeInfo.mImeSubtypeHandle == null
                || (inputMethodSubtype = imeInfo.mImeSubtype) == null) {
            return KeyboardLayoutSelectionResult.FAILED;
        }
        ULocale physicalKeyboardHintLanguageTag =
                inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
        String languageTag =
                physicalKeyboardHintLanguageTag != null
                        ? physicalKeyboardHintLanguageTag.toLanguageTag()
                        : inputMethodSubtype.getCanonicalizedLanguageTag();
        String matchingLayoutForProvidedLanguageTagAndLayoutType2 =
                getMatchingLayoutForProvidedLanguageTagAndLayoutType(
                        keyboardLayoutArr,
                        languageTag,
                        inputMethodSubtype.getPhysicalKeyboardHintLayoutType());
        StringBuilder sb =
                new StringBuilder(
                        "getDefaultKeyboardLayoutBasedOnImeInfo() : Layout found based on IME"
                            + " locale matching. ");
        sb.append(keyboardIdentifier);
        sb.append(" : ");
        sb.append(matchingLayoutForProvidedLanguageTagAndLayoutType2);
        sb.append(", LanguageTag : ");
        BootReceiver$$ExternalSyntheticOutline0.m(sb, languageTag, "KeyboardLayoutManager");
        return matchingLayoutForProvidedLanguageTagAndLayoutType2 != null
                ? new KeyboardLayoutSelectionResult(
                        matchingLayoutForProvidedLanguageTagAndLayoutType2, 3)
                : KeyboardLayoutSelectionResult.FAILED;
    }

    public static String getMatchingLayoutForProvidedLanguageTag(String str, List list) {
        Locale forLanguageTag = Locale.forLanguageTag(str);
        Iterator it = list.iterator();
        String str2 = null;
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        while (it.hasNext()) {
            KeyboardLayout keyboardLayout = (KeyboardLayout) it.next();
            LocaleList locales = keyboardLayout.getLocales();
            for (int i = 0; i < locales.size(); i++) {
                Locale locale = locales.get(i);
                if (locale != null && locale.getLanguage().equals(forLanguageTag.getLanguage())) {
                    float f2 =
                            locale.getCountry().equals(forLanguageTag.getCountry())
                                    ? 2.0f
                                    : TextUtils.isEmpty(locale.getCountry())
                                            ? (float) (1.0f + 0.5d)
                                            : 1.0f;
                    if (locale.getVariant().equals(forLanguageTag.getVariant())) {
                        f2 += 1.0f;
                    } else if (TextUtils.isEmpty(locale.getVariant())) {
                        f2 = (float) (f2 + 0.5d);
                    }
                    if (f2 > f) {
                        str2 = keyboardLayout.getDescriptor();
                        f = f2;
                    }
                }
            }
        }
        return str2;
    }

    public static String getMatchingLayoutForProvidedLanguageTagAndLayoutType(
            KeyboardLayout[] keyboardLayoutArr, String str, String str2) {
        if (str2 == null || !KeyboardLayout.isLayoutTypeValid(str2)) {
            str2 = "undefined";
        }
        ArrayList arrayList = new ArrayList();
        for (KeyboardLayout keyboardLayout : keyboardLayoutArr) {
            if (keyboardLayout.getLayoutType().equals(str2)) {
                arrayList.add(keyboardLayout);
            }
        }
        String matchingLayoutForProvidedLanguageTag =
                getMatchingLayoutForProvidedLanguageTag(str, arrayList);
        return matchingLayoutForProvidedLanguageTag != null
                ? matchingLayoutForProvidedLanguageTag
                : getMatchingLayoutForProvidedLanguageTag(str, Arrays.asList(keyboardLayoutArr));
    }

    public static int[] getScriptCodes(Locale locale) {
        int codeFromName;
        if (locale == null) {
            return new int[0];
        }
        if (!TextUtils.isEmpty(locale.getScript())
                && (codeFromName = UScript.getCodeFromName(locale.getScript())) != -1) {
            return new int[] {codeFromName};
        }
        int[] code = UScript.getCode(locale);
        return code != null ? code : new int[0];
    }

    public static void logKeyboardConfigurationEvent(
            InputDevice inputDevice, List list, List list2, boolean z) {
        List list3;
        int i;
        if (list.isEmpty()) {
            return;
        }
        ArrayList arrayList = (ArrayList) list2;
        if (arrayList.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            KeyboardLayoutSelectionResult keyboardLayoutSelectionResult =
                    (KeyboardLayoutSelectionResult) arrayList.get(i2);
            if (keyboardLayoutSelectionResult == null
                    || keyboardLayoutSelectionResult.getLayoutDescriptor() == null) {
                list3 = list;
                i = 4;
            } else {
                i = keyboardLayoutSelectionResult.getSelectionCriteria();
                KeyboardIdentifier parse =
                        KeyboardIdentifier.parse(
                                keyboardLayoutSelectionResult.getLayoutDescriptor());
                r8 = parse != null ? (String) parse.mIdentifier : null;
                list3 = list;
            }
            InputMethodSubtype inputMethodSubtype = ((ImeInfo) list3.get(i2)).mImeSubtype;
            Objects.requireNonNull(inputMethodSubtype, "IME subtype provided should not be null");
            boolean z2 = KeyboardMetricsCollector.DEBUG;
            if (i != 1 && i != 2 && i != 3 && i != 4) {
                throw new IllegalStateException("Invalid layout selection criteria");
            }
            arrayList2.add(inputMethodSubtype);
            arrayList3.add(r8);
            arrayList4.add(Integer.valueOf(i));
        }
        int size = arrayList2.size();
        if (size == 0) {
            throw new IllegalStateException("Should have at least one configuration");
        }
        ArrayList arrayList5 = new ArrayList();
        for (int i3 = 0; i3 < size; i3++) {
            int intValue = ((Integer) arrayList4.get(i3)).intValue();
            InputMethodSubtype inputMethodSubtype2 = (InputMethodSubtype) arrayList2.get(i3);
            String keyboardLanguageTag = inputDevice.getKeyboardLanguageTag();
            String str =
                    TextUtils.isEmpty(keyboardLanguageTag)
                            ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG
                            : keyboardLanguageTag;
            int layoutTypeEnumValue =
                    KeyboardLayout.LayoutType.getLayoutTypeEnumValue(
                            inputDevice.getKeyboardLayoutType());
            ULocale physicalKeyboardHintLanguageTag =
                    inputMethodSubtype2.getPhysicalKeyboardHintLanguageTag();
            String languageTag =
                    physicalKeyboardHintLanguageTag != null
                            ? physicalKeyboardHintLanguageTag.toLanguageTag()
                            : inputMethodSubtype2.getCanonicalizedLanguageTag();
            arrayList5.add(
                    new KeyboardMetricsCollector.LayoutConfiguration(
                            layoutTypeEnumValue,
                            intValue,
                            str,
                            arrayList3.get(i3) == null ? "Default" : (String) arrayList3.get(i3),
                            TextUtils.isEmpty(languageTag)
                                    ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG
                                    : languageTag,
                            KeyboardLayout.LayoutType.getLayoutTypeEnumValue(
                                    inputMethodSubtype2.getPhysicalKeyboardHintLayoutType())));
        }
        KeyboardMetricsCollector.KeyboardConfigurationEvent keyboardConfigurationEvent =
                new KeyboardMetricsCollector.KeyboardConfigurationEvent(inputDevice, z, arrayList5);
        boolean z3 = KeyboardMetricsCollector.DEBUG;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        Iterator it = arrayList5.iterator();
        while (it.hasNext()) {
            KeyboardMetricsCollector.LayoutConfiguration layoutConfiguration =
                    (KeyboardMetricsCollector.LayoutConfiguration) it.next();
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1138166333442L, layoutConfiguration.keyboardLanguageTag);
            protoOutputStream.write(1120986464257L, layoutConfiguration.keyboardLayoutType);
            protoOutputStream.write(1138166333443L, layoutConfiguration.keyboardLayoutName);
            protoOutputStream.write(1120986464260L, layoutConfiguration.layoutSelectionCriteria);
            protoOutputStream.write(1138166333446L, layoutConfiguration.imeLanguageTag);
            protoOutputStream.write(1120986464261L, layoutConfiguration.imeLayoutType);
            protoOutputStream.end(start);
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.KEYBOARD_CONFIGURED,
                keyboardConfigurationEvent.mIsFirstConfiguration,
                keyboardConfigurationEvent.mInputDevice.getVendorId(),
                keyboardConfigurationEvent.mInputDevice.getProductId(),
                protoOutputStream.getBytes(),
                keyboardConfigurationEvent.mInputDevice.getDeviceBus());
        if (KeyboardMetricsCollector.DEBUG) {
            Slog.d(
                    "KeyboardMetricCollector",
                    "Logging Keyboard configuration event: " + keyboardConfigurationEvent);
        }
    }

    public static void visitKeyboardLayoutsInPackage(
            PackageManager packageManager,
            ActivityInfo activityInfo,
            String str,
            int i,
            KeyboardLayoutVisitor keyboardLayoutVisitor) {
        String str2;
        int i2;
        int i3;
        TypedArray typedArray;
        Object obj = str;
        Bundle bundle = activityInfo.metaData;
        if (bundle == null) {
            return;
        }
        int i4 = bundle.getInt("android.hardware.input.metadata.KEYBOARD_LAYOUTS");
        if (i4 == 0) {
            StringBuilder sb =
                    new StringBuilder(
                            "Missing meta-data 'android.hardware.input.metadata.KEYBOARD_LAYOUTS'"
                                + " on receiver ");
            sb.append(activityInfo.packageName);
            sb.append("/");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, activityInfo.name, "KeyboardLayoutManager");
            return;
        }
        CharSequence loadLabel = activityInfo.loadLabel(packageManager);
        String charSequence = loadLabel != null ? loadLabel.toString() : "";
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        int i5 = 1;
        int i6 = 0;
        int i7 = (applicationInfo.flags & 1) != 0 ? i : 0;
        try {
            Resources resourcesForApplication =
                    packageManager.getResourcesForApplication(applicationInfo);
            XmlResourceParser xml = resourcesForApplication.getXml(i4);
            try {
                XmlUtils.beginDocument(xml, "keyboard-layouts");
                while (true) {
                    XmlUtils.nextElement(xml);
                    String name = xml.getName();
                    if (name == null) {
                        xml.close();
                        return;
                    }
                    if (name.equals("keyboard-layout")) {
                        TypedArray obtainAttributes =
                                resourcesForApplication.obtainAttributes(
                                        xml, R.styleable.KeyboardLayout);
                        try {
                            String string = obtainAttributes.getString(i5);
                            String string2 = obtainAttributes.getString(i6);
                            int attributeResourceValue =
                                    xml.getAttributeResourceValue(
                                            "http://schemas.android.com/apk/res-auto", "style", i6);
                            String attributeValue =
                                    attributeResourceValue == 0
                                            ? xml.getAttributeValue(
                                                    "http://schemas.android.com/apk/res-auto",
                                                    "style")
                                            : resourcesForApplication.getString(
                                                    attributeResourceValue);
                            if (!TextUtils.isEmpty(attributeValue)) {
                                String str3 = ",";
                                try {
                                    str3 =
                                            resourcesForApplication.getString(
                                                    resourcesForApplication.getIdentifier(
                                                            "comma",
                                                            "string",
                                                            "com.android.inputdevices"));
                                } catch (Exception unused) {
                                }
                                string2 = string2 + str3 + " " + attributeValue;
                            }
                            String str4 = string2;
                            int resourceId = obtainAttributes.getResourceId(2, i6);
                            String string3 = obtainAttributes.getString(3);
                            LocaleList emptyLocaleList =
                                    TextUtils.isEmpty(string3)
                                            ? LocaleList.getEmptyLocaleList()
                                            : LocaleList.forLanguageTags(string3.replace('|', ','));
                            int i8 = obtainAttributes.getInt(4, i6);
                            int i9 = obtainAttributes.getInt(6, -1);
                            int i10 = obtainAttributes.getInt(5, -1);
                            if (string == null || str4 == null || resourceId == 0) {
                                str2 = charSequence;
                                typedArray = obtainAttributes;
                                i2 = i6;
                                i3 = 1;
                                Slog.w(
                                        "KeyboardLayoutManager",
                                        "Missing required 'name', 'label' or 'keyboardLayout'"
                                            + " attributes in keyboard layout resource from"
                                            + " receiver "
                                                + activityInfo.packageName
                                                + "/"
                                                + activityInfo.name);
                            } else {
                                String str5 =
                                        activityInfo.packageName
                                                + "/"
                                                + activityInfo.name
                                                + "/"
                                                + string;
                                if (obj != null && !string.equals(obj)) {
                                    str2 = charSequence;
                                    typedArray = obtainAttributes;
                                    i2 = i6;
                                    i3 = 1;
                                }
                                typedArray = obtainAttributes;
                                i2 = i6;
                                str2 = charSequence;
                                i3 = 1;
                                try {
                                    keyboardLayoutVisitor.visitKeyboardLayout(
                                            resourcesForApplication,
                                            resourceId,
                                            new KeyboardLayout(
                                                    str5,
                                                    str4,
                                                    charSequence,
                                                    i7,
                                                    emptyLocaleList,
                                                    i8,
                                                    i9,
                                                    i10));
                                } catch (Throwable th) {
                                    th = th;
                                    typedArray.recycle();
                                    throw th;
                                }
                            }
                            typedArray.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            typedArray = obtainAttributes;
                        }
                    } else {
                        str2 = charSequence;
                        i2 = i6;
                        i3 = i5;
                        Slog.w(
                                "KeyboardLayoutManager",
                                "Skipping unrecognized element '"
                                        + name
                                        + "' in keyboard layout resource from receiver "
                                        + activityInfo.packageName
                                        + "/"
                                        + activityInfo.name);
                    }
                    obj = str;
                    i5 = i3;
                    i6 = i2;
                    charSequence = str2;
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.w(
                    "KeyboardLayoutManager",
                    "Could not parse keyboard layout resource from receiver "
                            + activityInfo.packageName
                            + "/"
                            + activityInfo.name,
                    e);
        }
    }

    public final void dump(final IndentingPrintWriter indentingPrintWriter) {
        String str;
        indentingPrintWriter.println("KeyboardLayoutManager:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mImeInfoLock) {
            try {
                ImeInfo imeInfo = this.mCurrentImeInfo;
                if (imeInfo != null) {
                    InputMethodSubtype inputMethodSubtype = imeInfo.mImeSubtype;
                    if (inputMethodSubtype != null) {
                        ULocale physicalKeyboardHintLanguageTag =
                                inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
                        str =
                                physicalKeyboardHintLanguageTag != null
                                        ? physicalKeyboardHintLanguageTag.toLanguageTag()
                                        : inputMethodSubtype.getCanonicalizedLanguageTag();
                    } else {
                        str = null;
                    }
                    indentingPrintWriter.println(
                            "mCurrentImeInfo: userId="
                                    + this.mCurrentImeInfo.mUserId
                                    + ", subtypeHandle="
                                    + this.mCurrentImeInfo.mImeSubtypeHandle
                                    + ", subtype="
                                    + this.mCurrentImeInfo.mImeSubtype
                                    + ", languageTag="
                                    + str);
                } else {
                    indentingPrintWriter.println("mCurrentImeInfo: null");
                }
            } finally {
            }
        }
        indentingPrintWriter.println("mConfiguredKeyboards:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mConfiguredKeyboards.size(); i++) {
            try {
                indentingPrintWriter.println("Device " + this.mConfiguredKeyboards.keyAt(i) + ": ");
                indentingPrintWriter.increaseIndent();
                KeyboardConfiguration keyboardConfiguration =
                        (KeyboardConfiguration) this.mConfiguredKeyboards.valueAt(i);
                indentingPrintWriter.println("Configured layouts:");
                indentingPrintWriter.increaseIndent();
                Iterator it = keyboardConfiguration.mConfiguredLayouts.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println((String) it.next());
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            } catch (Exception unused) {
                indentingPrintWriter.println("failed to dump");
            }
        }
        indentingPrintWriter.decreaseIndent();
        synchronized (this.mKeyboardLayoutCache) {
            try {
                if (!((ArrayMap) this.mKeyboardLayoutCache).isEmpty()) {
                    indentingPrintWriter.println("Keyboard layout cache:");
                    ((ArrayMap) this.mKeyboardLayoutCache)
                            .forEach(
                                    new BiConsumer() { // from class:
                                                       // com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda2
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            IndentingPrintWriter indentingPrintWriter2 =
                                                    indentingPrintWriter;
                                            indentingPrintWriter2.print("  key: " + ((String) obj));
                                            indentingPrintWriter2.println(
                                                    "  layout: "
                                                            + ((KeyboardLayoutSelectionResult)
                                                                    obj2));
                                        }
                                    });
                }
            } finally {
            }
        }
        indentingPrintWriter.decreaseIndent();
        synchronized (this.mDataStore) {
            PersistentDataStore persistentDataStore = this.mDataStore;
            persistentDataStore.getClass();
            indentingPrintWriter.println("Data store:");
            indentingPrintWriter.increaseIndent();
            if (!persistentDataStore.mInputDevices.isEmpty()) {
                persistentDataStore.mInputDevices.forEach(
                        new PersistentDataStore$$ExternalSyntheticLambda0(indentingPrintWriter, 0));
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public List getImeInfoListForLayoutMapping() {
        ArrayList arrayList = new ArrayList();
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        Objects.requireNonNull(userManager);
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        Objects.requireNonNull(inputMethodManager);
        InputMethodManagerInternal inputMethodManagerInternal = InputMethodManagerInternal.get();
        Iterator it = userManager.getUserHandles(true).iterator();
        while (it.hasNext()) {
            int identifier = ((UserHandle) it.next()).getIdentifier();
            for (InputMethodInfo inputMethodInfo :
                    inputMethodManagerInternal.getEnabledInputMethodListAsUser(identifier)) {
                for (InputMethodSubtype inputMethodSubtype :
                        inputMethodManager.getEnabledInputMethodSubtypeList(
                                inputMethodInfo, true)) {
                    if (inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
                        arrayList.add(new ImeInfo(identifier, inputMethodInfo, inputMethodSubtype));
                    }
                }
            }
        }
        return arrayList;
    }

    public final InputDevice getInputDevice(int i) {
        InputManager inputManager =
                (InputManager) this.mContext.getSystemService(InputManager.class);
        if (inputManager != null) {
            return inputManager.getInputDevice(i);
        }
        return null;
    }

    public final InputDevice getInputDevice(InputDeviceIdentifier inputDeviceIdentifier) {
        InputManager inputManager =
                (InputManager) this.mContext.getSystemService(InputManager.class);
        if (inputManager != null) {
            return inputManager.getInputDeviceByDescriptor(inputDeviceIdentifier.getDescriptor());
        }
        return null;
    }

    public final KeyboardLayout getKeyboardLayout(String str) {
        Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        KeyboardLayout[] keyboardLayoutArr = new KeyboardLayout[1];
        visitKeyboardLayout(
                str, new KeyboardLayoutManager$$ExternalSyntheticLambda0(0, keyboardLayoutArr));
        if (keyboardLayoutArr[0] == null) {
            PinnerService$$ExternalSyntheticOutline0.m(
                    "Could not get keyboard layout with descriptor '",
                    str,
                    "'.",
                    "KeyboardLayoutManager");
        }
        return keyboardLayoutArr[0];
    }

    public final KeyboardLayoutSelectionResult getKeyboardLayoutForInputDeviceInternal(
            KeyboardIdentifier keyboardIdentifier, ImeInfo imeInfo) {
        String str;
        if (imeInfo == null) {
            str = keyboardIdentifier.toString();
        } else {
            Objects.requireNonNull(imeInfo.mImeSubtypeHandle, "subtypeHandle must not be null");
            str =
                    "layoutDescriptor:"
                            + keyboardIdentifier
                            + ",userId:"
                            + imeInfo.mUserId
                            + ",subtypeHandle:"
                            + imeInfo.mImeSubtypeHandle.toStringHandle();
        }
        synchronized (this.mDataStore) {
            try {
                PersistentDataStore persistentDataStore = this.mDataStore;
                String keyboardIdentifier2 = keyboardIdentifier.toString();
                persistentDataStore.loadIfNeeded();
                PersistentDataStore.InputDeviceState inputDeviceState =
                        (PersistentDataStore.InputDeviceState)
                                persistentDataStore.mInputDevices.get(keyboardIdentifier2);
                String str2 =
                        inputDeviceState != null
                                ? (String) ((ArrayMap) inputDeviceState.mKeyboardLayoutMap).get(str)
                                : null;
                if (str2 != null) {
                    return new KeyboardLayoutSelectionResult(str2, 1);
                }
                synchronized (this.mKeyboardLayoutCache) {
                    try {
                        if (((ArrayMap) this.mKeyboardLayoutCache).containsKey(str)) {
                            return (KeyboardLayoutSelectionResult)
                                    ((ArrayMap) this.mKeyboardLayoutCache).get(str);
                        }
                        KeyboardLayoutSelectionResult defaultKeyboardLayoutBasedOnImeInfo =
                                getDefaultKeyboardLayoutBasedOnImeInfo(
                                        keyboardIdentifier,
                                        imeInfo,
                                        getKeyboardLayoutListForInputDeviceInternal(
                                                keyboardIdentifier, imeInfo));
                        ((ArrayMap) this.mKeyboardLayoutCache)
                                .put(str, defaultKeyboardLayoutBasedOnImeInfo);
                        return defaultKeyboardLayoutBasedOnImeInfo;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final KeyboardLayout[] getKeyboardLayoutListForInputDeviceInternal(
            final KeyboardIdentifier keyboardIdentifier, ImeInfo imeInfo) {
        String str;
        final String str2;
        final String str3;
        InputMethodSubtype inputMethodSubtype;
        if (imeInfo == null) {
            str = keyboardIdentifier.toString();
        } else {
            Objects.requireNonNull(imeInfo.mImeSubtypeHandle, "subtypeHandle must not be null");
            str =
                    "layoutDescriptor:"
                            + keyboardIdentifier
                            + ",userId:"
                            + imeInfo.mUserId
                            + ",subtypeHandle:"
                            + imeInfo.mImeSubtypeHandle.toStringHandle();
        }
        synchronized (this.mDataStore) {
            PersistentDataStore persistentDataStore = this.mDataStore;
            String keyboardIdentifier2 = keyboardIdentifier.toString();
            persistentDataStore.loadIfNeeded();
            PersistentDataStore.InputDeviceState inputDeviceState =
                    (PersistentDataStore.InputDeviceState)
                            persistentDataStore.mInputDevices.get(keyboardIdentifier2);
            str2 =
                    inputDeviceState != null
                            ? (String) ((ArrayMap) inputDeviceState.mKeyboardLayoutMap).get(str)
                            : null;
        }
        final ArrayList arrayList = new ArrayList();
        if (imeInfo == null || (inputMethodSubtype = imeInfo.mImeSubtype) == null) {
            str3 = "";
        } else {
            ULocale physicalKeyboardHintLanguageTag =
                    inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
            str3 =
                    physicalKeyboardHintLanguageTag != null
                            ? physicalKeyboardHintLanguageTag.toLanguageTag()
                            : imeInfo.mImeSubtype.getCanonicalizedLanguageTag();
        }
        visitAllKeyboardLayouts(
                new KeyboardLayoutVisitor() { // from class:
                                              // com.android.server.input.KeyboardLayoutManager.2
                    public boolean mDeviceSpecificLayoutAvailable;

                    @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
                    public final void visitKeyboardLayout(
                            Resources resources, int i, KeyboardLayout keyboardLayout) {
                        int vendorId = keyboardLayout.getVendorId();
                        KeyboardIdentifier keyboardIdentifier3 = KeyboardIdentifier.this;
                        if (vendorId
                                        == ((InputDeviceIdentifier) keyboardIdentifier3.mIdentifier)
                                                .getVendorId()
                                && keyboardLayout.getProductId()
                                        == ((InputDeviceIdentifier) keyboardIdentifier3.mIdentifier)
                                                .getProductId()) {
                            if (!this.mDeviceSpecificLayoutAvailable) {
                                this.mDeviceSpecificLayoutAvailable = true;
                                arrayList.clear();
                            }
                            arrayList.add(keyboardLayout);
                            return;
                        }
                        if (keyboardLayout.getVendorId() == -1
                                && keyboardLayout.getProductId() == -1
                                && !this.mDeviceSpecificLayoutAvailable) {
                            LocaleList locales = keyboardLayout.getLocales();
                            if (!locales.isEmpty()) {
                                String str4 = str3;
                                if (!TextUtils.isEmpty(str4)) {
                                    int[] scriptCodes =
                                            KeyboardLayoutManager.getScriptCodes(
                                                    Locale.forLanguageTag(str4));
                                    if (scriptCodes.length != 0) {
                                        for (int i2 = 0; i2 < locales.size(); i2++) {
                                            for (int i3 :
                                                    KeyboardLayoutManager.getScriptCodes(
                                                            locales.get(i2))) {
                                                for (int i4 : scriptCodes) {
                                                    if (i3 != i4) {}
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            arrayList.add(keyboardLayout);
                            return;
                        }
                        if (keyboardLayout.getDescriptor().equals(str2)) {
                            arrayList.add(keyboardLayout);
                        }
                    }
                });
        Collections.sort(arrayList);
        return (KeyboardLayout[]) arrayList.toArray(new KeyboardLayout[0]);
    }

    public boolean isVirtualDevice(int i) {
        VirtualDeviceManagerInternal virtualDeviceManagerInternal =
                (VirtualDeviceManagerInternal)
                        LocalServices.getService(VirtualDeviceManagerInternal.class);
        return virtualDeviceManagerInternal != null
                && virtualDeviceManagerInternal.isInputDeviceOwnedByVirtualDevice(i);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceAdded(int i) {
        onInputDeviceChangedInternal(i, true);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceChanged(int i) {
        onInputDeviceChangedInternal(i, false);
    }

    public final void onInputDeviceChangedInternal(int i, boolean z) {
        boolean z2;
        Set set;
        InputDevice inputDevice = getInputDevice(i);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return;
        }
        KeyboardIdentifier keyboardIdentifier = new KeyboardIdentifier(inputDevice);
        KeyboardConfiguration keyboardConfiguration =
                (KeyboardConfiguration) this.mConfiguredKeyboards.get(i);
        if (keyboardConfiguration == null) {
            keyboardConfiguration = new KeyboardConfiguration(i);
            this.mConfiguredKeyboards.put(i, keyboardConfiguration);
        }
        HashSet hashSet = new HashSet();
        List imeInfoListForLayoutMapping = getImeInfoListForLayoutMapping();
        ArrayList arrayList = new ArrayList();
        Iterator it = imeInfoListForLayoutMapping.iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            KeyboardLayoutSelectionResult keyboardLayoutForInputDeviceInternal =
                    getKeyboardLayoutForInputDeviceInternal(
                            keyboardIdentifier, (ImeInfo) it.next());
            if (keyboardLayoutForInputDeviceInternal.getLayoutDescriptor() != null) {
                hashSet.add(keyboardLayoutForInputDeviceInternal.getLayoutDescriptor());
            } else {
                z3 = true;
            }
            arrayList.add(keyboardLayoutForInputDeviceInternal);
        }
        if (DEBUG) {
            Slog.d(
                    "KeyboardLayoutManager",
                    "Layouts selected for input device: "
                            + keyboardIdentifier
                            + " -> selectedLayouts: "
                            + hashSet);
        }
        if (z3) {
            hashSet.clear();
        }
        keyboardConfiguration.mConfiguredLayouts = hashSet;
        synchronized (this.mDataStore) {
            try {
                String keyboardIdentifier2 = keyboardIdentifier.toString();
                PersistentDataStore persistentDataStore = this.mDataStore;
                PersistentDataStore.InputDeviceState orCreateInputDeviceState =
                        persistentDataStore.getOrCreateInputDeviceState(keyboardIdentifier2);
                if (Objects.equals(orCreateInputDeviceState.mSelectedKeyboardLayouts, hashSet)) {
                    z2 = false;
                } else {
                    orCreateInputDeviceState.mSelectedKeyboardLayouts = new HashSet(hashSet);
                    persistentDataStore.mDirty = true;
                    z2 = true;
                }
                if (z) {
                    PersistentDataStore persistentDataStore2 = this.mDataStore;
                    persistentDataStore2.loadIfNeeded();
                    logKeyboardConfigurationEvent(
                            inputDevice,
                            imeInfoListForLayoutMapping,
                            arrayList,
                            ((PersistentDataStore.InputDeviceState)
                                            persistentDataStore2.mInputDevices.get(
                                                    keyboardIdentifier2))
                                    == null);
                }
            } finally {
                this.mDataStore.saveIfNeeded();
            }
        }
        if (z2) {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < this.mConfiguredKeyboards.size(); i2++) {
                int keyAt = this.mConfiguredKeyboards.keyAt(i2);
                KeyboardConfiguration keyboardConfiguration2 =
                        (KeyboardConfiguration) this.mConfiguredKeyboards.valueAt(i2);
                if (!isVirtualDevice(keyAt)) {
                    Set set2 = keyboardConfiguration2.mConfiguredLayouts;
                    if (set2 == null || set2.isEmpty()) {
                        Resources resources = this.mContext.getResources();
                        String string = resources.getString(17042793);
                        if (this.mConfiguredKeyboards.size() != 1) {
                            showKeyboardLayoutNotification(
                                    resources.getString(17042796), string, null);
                            return;
                        }
                        InputDevice inputDevice2 =
                                getInputDevice(this.mConfiguredKeyboards.keyAt(0));
                        if (inputDevice2 == null) {
                            return;
                        }
                        showKeyboardLayoutNotification(
                                resources.getString(17042794, inputDevice2.getName()),
                                string,
                                inputDevice2);
                        return;
                    }
                    arrayList2.add(keyboardConfiguration2);
                }
            }
            if (arrayList2.size() == 0) {
                NotificationManager notificationManager =
                        (NotificationManager)
                                this.mContext.getSystemService(NotificationManager.class);
                if (notificationManager == null) {
                    return;
                }
                notificationManager.cancelAsUser(null, 19, UserHandle.ALL);
                return;
            }
            Resources resources2 = this.mContext.getResources();
            if (arrayList2.size() != 1) {
                showKeyboardLayoutNotification(
                        resources2.getString(android.R.string.permdesc_accessBackgroundLocation),
                        resources2.getString(android.R.string.permdesc_acceptHandovers),
                        null);
                return;
            }
            KeyboardConfiguration keyboardConfiguration3 =
                    (KeyboardConfiguration) arrayList2.get(0);
            InputDevice inputDevice3 = getInputDevice(keyboardConfiguration3.mDeviceId);
            if (inputDevice3 == null
                    || (set = keyboardConfiguration3.mConfiguredLayouts) == null
                    || set.isEmpty()) {
                return;
            }
            String string2 =
                    resources2.getString(
                            android.R.string.permdesc_accessDrmCertificates,
                            inputDevice3.getName());
            Context context = this.mContext;
            Set set3 = keyboardConfiguration3.mConfiguredLayouts;
            Resources resources3 = context.getResources();
            final ArrayList arrayList3 = new ArrayList();
            set3.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            arrayList3.add(
                                    KeyboardLayoutManager.this
                                            .getKeyboardLayout((String) obj)
                                            .getLabel());
                        }
                    });
            Collections.sort(arrayList3);
            int size = arrayList3.size();
            showKeyboardLayoutNotification(
                    string2,
                    size != 1
                            ? size != 2
                                    ? size != 3
                                            ? resources3.getString(
                                                    android.R.string.perm_costs_money,
                                                    arrayList3.get(0),
                                                    arrayList3.get(1),
                                                    arrayList3.get(2))
                                            : resources3.getString(
                                                    android.R.string.permdesc_accessFineLocation,
                                                    arrayList3.get(0),
                                                    arrayList3.get(1),
                                                    arrayList3.get(2))
                                    : resources3.getString(
                                            android.R.string.permdesc_accessHiddenProfile,
                                            arrayList3.get(0),
                                            arrayList3.get(1))
                            : resources3.getString(
                                    android.R.string.permdesc_accessCoarseLocation,
                                    arrayList3.get(0)),
                    inputDevice3);
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public final void onInputDeviceRemoved(int i) {
        NotificationManager notificationManager;
        this.mConfiguredKeyboards.remove(i);
        if (this.mConfiguredKeyboards.size() != 0
                || (notificationManager =
                                (NotificationManager)
                                        this.mContext.getSystemService(NotificationManager.class))
                        == null) {
            return;
        }
        notificationManager.cancelAsUser(null, 19, UserHandle.ALL);
    }

    public final void showKeyboardLayoutNotification(
            String str, String str2, InputDevice inputDevice) {
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        if (notificationManager == null) {
            return;
        }
        Intent intent = new Intent("android.settings.HARD_KEYBOARD_SETTINGS");
        if (inputDevice != null) {
            intent.putExtra("input_device_identifier", (Parcelable) inputDevice.getIdentifier());
            intent.putExtra("com.android.settings.inputmethod.EXTRA_ENTRYPOINT", 0);
        }
        intent.setFlags(337641472);
        notificationManager.notifyAsUser(
                null,
                19,
                new Notification.Builder(
                                this.mContext, SystemNotificationChannels.PHYSICAL_KEYBOARD)
                        .setContentTitle(str)
                        .setContentText(str2)
                        .setContentIntent(
                                PendingIntent.getActivityAsUser(
                                        this.mContext,
                                        0,
                                        intent,
                                        67108864,
                                        null,
                                        UserHandle.CURRENT))
                        .setSmallIcon(android.R.drawable.ic_voice_search)
                        .setColor(
                                this.mContext.getColor(
                                        android.R.color.system_notification_accent_color))
                        .setAutoCancel(true)
                        .build(),
                UserHandle.ALL);
    }

    public final void updateKeyboardLayouts() {
        HashSet hashSet = new HashSet();
        visitAllKeyboardLayouts(new KeyboardLayoutManager$$ExternalSyntheticLambda0(2, hashSet));
        if (this.mAvailableLayouts.equals(hashSet)) {
            return;
        }
        this.mAvailableLayouts = hashSet;
        synchronized (this.mDataStore) {
            try {
                this.mDataStore.removeUninstalledKeyboardLayouts(hashSet);
            } finally {
                this.mDataStore.saveIfNeeded();
            }
        }
        synchronized (this.mKeyboardLayoutCache) {
            ((ArrayMap) this.mKeyboardLayoutCache).clear();
        }
        if (DEBUG) {
            Slog.d("KeyboardLayoutManager", "Reloading keyboard layouts.");
        }
        this.mNative.reloadKeyboardLayouts();
    }

    public final void visitAllKeyboardLayouts(KeyboardLayoutVisitor keyboardLayoutVisitor) {
        ActivityInfo activityInfo;
        PackageManager packageManager = this.mContext.getPackageManager();
        for (ResolveInfo resolveInfo :
                packageManager.queryBroadcastReceiversAsUser(
                        new Intent("android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS"),
                        786560,
                        0)) {
            if (resolveInfo != null && (activityInfo = resolveInfo.activityInfo) != null) {
                visitKeyboardLayoutsInPackage(
                        packageManager,
                        activityInfo,
                        null,
                        resolveInfo.priority,
                        keyboardLayoutVisitor);
            }
        }
    }

    public final void visitKeyboardLayout(String str, KeyboardLayoutVisitor keyboardLayoutVisitor) {
        KeyboardIdentifier parse = KeyboardIdentifier.parse(str);
        if (parse != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                visitKeyboardLayoutsInPackage(
                        packageManager,
                        packageManager.getReceiverInfo(
                                new ComponentName(parse.mLanguageTag, parse.mLayoutType), 786560),
                        (String) parse.mIdentifier,
                        0,
                        keyboardLayoutVisitor);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }
}
