package android.content.res;

import android.app.WindowConfiguration;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.content.ConfigurationProto;
import android.hardware.Camera;
import android.os.Build;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class Configuration implements Parcelable, Comparable<Configuration> {
    public static final int ASSETS_SEQ_UNDEFINED = 0;
    public static final int COLOR_MODE_HDR_MASK = 12;
    public static final int COLOR_MODE_HDR_NO = 4;
    public static final int COLOR_MODE_HDR_SHIFT = 2;
    public static final int COLOR_MODE_HDR_UNDEFINED = 0;
    public static final int COLOR_MODE_HDR_YES = 8;
    public static final int COLOR_MODE_UNDEFINED = 0;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_MASK = 3;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_NO = 1;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_UNDEFINED = 0;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_YES = 2;
    public static final int DENSITY_DPI_ANY = 65534;
    public static final int DENSITY_DPI_NONE = 65535;
    public static final int DENSITY_DPI_UNDEFINED = 0;

    @Deprecated
    public static final int DESKTOP_MODE_UNDEFINED = -1;

    @Deprecated
    public static final int DEX_MODE_DUAL = 2;

    @Deprecated
    public static final int DEX_MODE_NEXT_GEN = 3;

    @Deprecated
    public static final int DEX_MODE_NONE = 0;

    @Deprecated
    public static final int DEX_MODE_STANDALONE = 1;

    @Deprecated
    public static final int DEX_MODE_UNDEFINED = -1;
    public static final int DISPLAY_DEVICE_TYPE_DUAL = 4;
    public static final int DISPLAY_DEVICE_TYPE_HDMI = 1;
    public static final int DISPLAY_DEVICE_TYPE_SUB_DUAL = 6;
    public static final int DISPLAY_DEVICE_TYPE_SUB_TENT = 7;
    public static final int DISPLAY_DEVICE_TYPE_UNDEFINED = -1;
    public static final int FONT_WEIGHT_ADJUSTMENT_UNDEFINED = Integer.MAX_VALUE;
    public static final int GRAMMATICAL_GENDER_FEMININE = 2;
    public static final int GRAMMATICAL_GENDER_MASCULINE = 3;
    public static final int GRAMMATICAL_GENDER_NEUTRAL = 1;
    public static final int GRAMMATICAL_GENDER_NOT_SPECIFIED = 0;
    public static final int GRAMMATICAL_GENDER_UNDEFINED = -1;
    public static final int HARDKEYBOARDHIDDEN_NO = 1;
    public static final int HARDKEYBOARDHIDDEN_UNDEFINED = 0;
    public static final int HARDKEYBOARDHIDDEN_YES = 2;
    public static final int KEYBOARDHIDDEN_NO = 1;
    public static final int KEYBOARDHIDDEN_SOFT = 3;
    public static final int KEYBOARDHIDDEN_UNDEFINED = 0;
    public static final int KEYBOARDHIDDEN_YES = 2;
    public static final int KEYBOARD_12KEY = 3;
    public static final int KEYBOARD_NOKEYS = 1;
    public static final int KEYBOARD_QWERTY = 2;
    public static final int KEYBOARD_UNDEFINED = 0;
    public static final int MNC_ZERO = 65535;
    public static final int MOBILEKEYBOARD_COVERED_UNDEFINED = -1;
    public static final int NATIVE_CONFIG_BOLD_TEXT = 262144;
    public static final int NATIVE_CONFIG_COLOR_MODE = 65536;
    public static final int NATIVE_CONFIG_CURSOR_THICKNESS = 2097152;
    public static final int NATIVE_CONFIG_DENSITY = 256;
    public static final int NATIVE_CONFIG_FLIPFONT = 32768;
    public static final int NATIVE_CONFIG_GRAMMATICAL_GENDER = 131072;
    public static final int NATIVE_CONFIG_KEYBOARD = 16;
    public static final int NATIVE_CONFIG_KEYBOARD_HIDDEN = 32;
    public static final int NATIVE_CONFIG_LAYOUTDIR = 16384;
    public static final int NATIVE_CONFIG_LOCALE = 4;
    public static final int NATIVE_CONFIG_MCC = 1;
    public static final int NATIVE_CONFIG_MNC = 2;
    public static final int NATIVE_CONFIG_NAVIGATION = 64;
    public static final int NATIVE_CONFIG_NIGHT_DIM = 1048576;
    public static final int NATIVE_CONFIG_ORIENTATION = 128;
    public static final int NATIVE_CONFIG_SCREEN_LAYOUT = 2048;
    public static final int NATIVE_CONFIG_SCREEN_SIZE = 512;
    public static final int NATIVE_CONFIG_SHOW_BUTTON_SHAPE = 524288;
    public static final int NATIVE_CONFIG_SMALLEST_SCREEN_SIZE = 8192;
    public static final int NATIVE_CONFIG_THEMESEQ = 131072;
    public static final int NATIVE_CONFIG_TOUCHSCREEN = 8;
    public static final int NATIVE_CONFIG_UI_MODE = 4096;
    public static final int NATIVE_CONFIG_VERSION = 1024;
    public static final int NAVIGATIONHIDDEN_NO = 1;
    public static final int NAVIGATIONHIDDEN_UNDEFINED = 0;
    public static final int NAVIGATIONHIDDEN_YES = 2;
    public static final int NAVIGATION_DPAD = 2;
    public static final int NAVIGATION_NONAV = 1;
    public static final int NAVIGATION_TRACKBALL = 3;
    public static final int NAVIGATION_UNDEFINED = 0;
    public static final int NAVIGATION_WHEEL = 4;
    public static final int ORIENTATION_LANDSCAPE = 2;
    public static final int ORIENTATION_PORTRAIT = 1;

    @Deprecated
    public static final int ORIENTATION_SQUARE = 3;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int SCREENLAYOUT_COMPAT_NEEDED = 268435456;
    public static final int SCREENLAYOUT_LAYOUTDIR_LTR = 64;
    public static final int SCREENLAYOUT_LAYOUTDIR_MASK = 192;
    public static final int SCREENLAYOUT_LAYOUTDIR_RTL = 128;
    public static final int SCREENLAYOUT_LAYOUTDIR_SHIFT = 6;
    public static final int SCREENLAYOUT_LAYOUTDIR_UNDEFINED = 0;
    public static final int SCREENLAYOUT_LONG_MASK = 48;
    public static final int SCREENLAYOUT_LONG_NO = 16;
    public static final int SCREENLAYOUT_LONG_UNDEFINED = 0;
    public static final int SCREENLAYOUT_LONG_YES = 32;
    public static final int SCREENLAYOUT_ROUND_MASK = 768;
    public static final int SCREENLAYOUT_ROUND_NO = 256;
    public static final int SCREENLAYOUT_ROUND_SHIFT = 8;
    public static final int SCREENLAYOUT_ROUND_UNDEFINED = 0;
    public static final int SCREENLAYOUT_ROUND_YES = 512;
    public static final int SCREENLAYOUT_SIZE_LARGE = 3;
    public static final int SCREENLAYOUT_SIZE_MASK = 15;
    public static final int SCREENLAYOUT_SIZE_NORMAL = 2;
    public static final int SCREENLAYOUT_SIZE_SMALL = 1;
    public static final int SCREENLAYOUT_SIZE_UNDEFINED = 0;
    public static final int SCREENLAYOUT_SIZE_XLARGE = 4;
    public static final int SCREENLAYOUT_UNDEFINED = 0;
    public static final int SCREEN_HEIGHT_DP_UNDEFINED = 0;
    public static final int SCREEN_WIDTH_DP_UNDEFINED = 0;
    public static final int SEM_BOLD_FONT_DISABLED = 0;
    public static final int SEM_BOLD_FONT_ENABLED = 1;
    public static final int SEM_BOLD_FONT_UNDEFINED = -1;
    public static final int SEM_BUTTON_SHAPE_DISABLED = 0;
    public static final int SEM_BUTTON_SHAPE_ENABLED = 1;
    public static final int SEM_BUTTON_SHAPE_UNDEFINED = -1;
    public static final float SEM_CURSOR_THICKNESS_SCALE_UNDEFINED = 0.0f;
    public static final int SEM_DESKTOP_MODE_DISABLED = 0;
    public static final int SEM_DESKTOP_MODE_ENABLED = 1;
    public static final int SEM_DISPLAY_DEVICE_TYPE_MAIN = 0;
    public static final int SEM_DISPLAY_DEVICE_TYPE_SUB = 5;
    public static final int SEM_MOBILE_KEYBOARD_COVERED_NO = 0;
    public static final int SEM_MOBILE_KEYBOARD_COVERED_YES = 1;
    public static final int SMALLEST_SCREEN_WIDTH_DP_UNDEFINED = 0;
    private static final String TAG = "Configuration";
    public static final int TOUCHSCREEN_FINGER = 3;
    public static final int TOUCHSCREEN_NOTOUCH = 1;

    @Deprecated
    public static final int TOUCHSCREEN_STYLUS = 2;
    public static final int TOUCHSCREEN_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_MASK = 48;
    public static final int UI_MODE_NIGHT_NO = 16;
    public static final int UI_MODE_NIGHT_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_YES = 32;
    public static final int UI_MODE_TYPE_APPLIANCE = 5;
    public static final int UI_MODE_TYPE_CAR = 3;
    public static final int UI_MODE_TYPE_DESK = 2;
    public static final int UI_MODE_TYPE_MASK = 15;
    public static final int UI_MODE_TYPE_NORMAL = 1;
    public static final int UI_MODE_TYPE_TELEVISION = 4;
    public static final int UI_MODE_TYPE_UNDEFINED = 0;
    public static final int UI_MODE_TYPE_VR_HEADSET = 7;
    public static final int UI_MODE_TYPE_WATCH = 6;
    private static final String XML_ATTR_APP_BOUNDS = "app_bounds";
    private static final String XML_ATTR_COLOR_MODE = "clrMod";
    private static final String XML_ATTR_DENSITY = "density";
    private static final String XML_ATTR_FONT_SCALE = "fs";
    private static final String XML_ATTR_FONT_WEIGHT_ADJUSTMENT = "fontWeightAdjustment";
    private static final String XML_ATTR_GRAMMATICAL_GENDER = "grammaticalGender";
    private static final String XML_ATTR_HARD_KEYBOARD_HIDDEN = "hardKeyHid";
    private static final String XML_ATTR_KEYBOARD = "key";
    private static final String XML_ATTR_KEYBOARD_HIDDEN = "keyHid";
    private static final String XML_ATTR_LOCALES = "locales";
    private static final String XML_ATTR_MCC = "mcc";
    private static final String XML_ATTR_MNC = "mnc";
    private static final String XML_ATTR_NAVIGATION = "nav";
    private static final String XML_ATTR_NAVIGATION_HIDDEN = "navHid";
    private static final String XML_ATTR_ORIENTATION = "ori";
    private static final String XML_ATTR_ROTATION = "rot";
    private static final String XML_ATTR_SCREEN_HEIGHT = "height";
    private static final String XML_ATTR_SCREEN_LAYOUT = "scrLay";
    private static final String XML_ATTR_SCREEN_WIDTH = "width";
    private static final String XML_ATTR_SMALLEST_WIDTH = "sw";
    private static final String XML_ATTR_TOUCHSCREEN = "touch";
    private static final String XML_ATTR_UI_MODE = "ui";
    public int FlipFont;
    public int assetsSeq;
    public int boldFont;
    public int colorMode;
    public int compatScreenHeightDp;
    public int compatScreenWidthDp;
    public int compatSmallestScreenWidthDp;
    public int densityDpi;

    @Deprecated
    public int dexMode;
    public float fontScale;
    public int fontWeightAdjustment;
    public int hardKeyboardHidden;
    public int keyboard;
    public int keyboardHidden;

    @Deprecated
    public Locale locale;
    private int mGrammaticalGender;
    private LocaleList mLocaleList;
    public int mcc;
    public int mnc;
    public int navigation;
    public int navigationHidden;
    public int nightDim;
    public int orientation;
    public boolean rilSetLocale;
    public int screenHeightDp;
    public int screenLayout;
    public int screenWidthDp;
    public int semButtonShapeEnabled;
    public float semCursorThicknessScale;
    public int semDesktopModeEnabled;
    public int semDisplayDeviceType;
    public int semMobileKeyboardCovered;
    public int seq;
    public int smallestScreenWidthDp;
    public int themeSeq;
    public int touchscreen;
    public int uiMode;
    public boolean userSetLocale;
    public final WindowConfiguration windowConfiguration;
    public static final Configuration EMPTY = new Configuration();
    public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() { // from class: android.content.res.Configuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Configuration createFromParcel(Parcel parcel) {
            return new Configuration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Configuration[] newArray(int i) {
            return new Configuration[i];
        }
    };

    @Deprecated
    public @interface DexMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GrammaticalGender {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NativeConfig {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    private static int getScreenLayoutNoDirection(int i) {
        return i & (-193);
    }

    @Deprecated
    private static final int hidden_SEM_DESKTOP_MODE_ENABLED() {
        return 1;
    }

    public static boolean needNewResources(int i, int i2) {
        return (i & (i2 | (-1023410176))) != 0;
    }

    public static int resetScreenLayout(int i) {
        return (i & (-268435520)) | 36;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static int reduceScreenLayout(int i, int i2, int i3) {
        boolean z;
        int i4 = 1;
        if (i2 < 470) {
            z = false;
        } else {
            int i5 = (i2 < 960 || i3 < 720) ? (i2 < 640 || i3 < 480) ? 2 : 3 : 4;
            z = i3 > 321 || i2 > 570;
            r1 = (i2 * 3) / 5 >= i3 - 1;
            i4 = i5;
        }
        if (!r1) {
            i = (i & (-49)) | 16;
        }
        if (z) {
            i |= 268435456;
        }
        return i4 < (i & 15) ? (i & (-16)) | i4 : i;
    }

    public static String configurationDiffToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("CONFIG_MCC");
        }
        if ((i & 2) != 0) {
            arrayList.add("CONFIG_MNC");
        }
        if ((i & 4) != 0) {
            arrayList.add("CONFIG_LOCALE");
        }
        if ((i & 8) != 0) {
            arrayList.add("CONFIG_TOUCHSCREEN");
        }
        if ((i & 16) != 0) {
            arrayList.add("CONFIG_KEYBOARD");
        }
        if ((i & 32) != 0) {
            arrayList.add("CONFIG_KEYBOARD_HIDDEN");
        }
        if ((i & 64) != 0) {
            arrayList.add("CONFIG_NAVIGATION");
        }
        if ((i & 128) != 0) {
            arrayList.add("CONFIG_ORIENTATION");
        }
        if ((i & 256) != 0) {
            arrayList.add("CONFIG_SCREEN_LAYOUT");
        }
        if ((i & 16384) != 0) {
            arrayList.add("CONFIG_COLOR_MODE");
        }
        if ((i & 512) != 0) {
            arrayList.add("CONFIG_UI_MODE");
        }
        if ((i & 1024) != 0) {
            arrayList.add("CONFIG_SCREEN_SIZE");
        }
        if ((i & 2048) != 0) {
            arrayList.add("CONFIG_SMALLEST_SCREEN_SIZE");
        }
        if ((i & 4096) != 0) {
            arrayList.add("CONFIG_DENSITY");
        }
        if ((i & 8192) != 0) {
            arrayList.add("CONFIG_LAYOUT_DIRECTION");
        }
        if ((1073741824 & i) != 0) {
            arrayList.add("CONFIG_FONT_SCALE");
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            arrayList.add("CONFIG_ASSETS_PATHS");
        }
        if ((536870912 & i) != 0) {
            arrayList.add("CONFIG_WINDOW_CONFIGURATION");
        }
        if ((268435456 & i) != 0) {
            arrayList.add("CONFIG_AUTO_BOLD_TEXT");
        }
        if ((i & 32768) != 0) {
            arrayList.add("CONFIG_GRAMMATICAL_GENDER");
        }
        return "{" + TextUtils.join(", ", arrayList) + "}";
    }

    public boolean isLayoutSizeAtLeast(int i) {
        int i2 = this.screenLayout & 15;
        return i2 != 0 && i2 >= i;
    }

    public boolean semIsPopOver() {
        return this.windowConfiguration.isPopOver();
    }

    public Configuration() {
        this.windowConfiguration = new WindowConfiguration();
        this.semMobileKeyboardCovered = -1;
        this.semDesktopModeEnabled = -1;
        this.semDisplayDeviceType = -1;
        this.themeSeq = 0;
        this.dexMode = -1;
        unset();
    }

    public Configuration(Configuration configuration) {
        this.windowConfiguration = new WindowConfiguration();
        this.semMobileKeyboardCovered = -1;
        this.semDesktopModeEnabled = -1;
        this.semDisplayDeviceType = -1;
        this.themeSeq = 0;
        this.dexMode = -1;
        setTo(configuration);
    }

    private void fixUpLocaleList() {
        Locale locale;
        if ((this.locale != null || this.mLocaleList.isEmpty()) && ((locale = this.locale) == null || locale.equals(this.mLocaleList.get(0)))) {
            return;
        }
        this.mLocaleList = this.locale == null ? LocaleList.getEmptyLocaleList() : new LocaleList(this.locale);
    }

    public void setTo(Configuration configuration) {
        this.fontScale = configuration.fontScale;
        this.mcc = configuration.mcc;
        this.mnc = configuration.mnc;
        Locale locale = configuration.locale;
        if (locale == null) {
            this.locale = null;
        } else if (!locale.equals(this.locale)) {
            this.locale = (Locale) configuration.locale.clone();
        }
        configuration.fixUpLocaleList();
        this.mLocaleList = configuration.mLocaleList;
        this.mGrammaticalGender = configuration.mGrammaticalGender;
        this.userSetLocale = configuration.userSetLocale;
        this.touchscreen = configuration.touchscreen;
        this.keyboard = configuration.keyboard;
        this.keyboardHidden = configuration.keyboardHidden;
        this.hardKeyboardHidden = configuration.hardKeyboardHidden;
        this.navigation = configuration.navigation;
        this.navigationHidden = configuration.navigationHidden;
        this.orientation = configuration.orientation;
        this.screenLayout = configuration.screenLayout;
        this.colorMode = configuration.colorMode;
        this.uiMode = configuration.uiMode;
        this.screenWidthDp = configuration.screenWidthDp;
        this.screenHeightDp = configuration.screenHeightDp;
        this.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        this.densityDpi = configuration.densityDpi;
        this.compatScreenWidthDp = configuration.compatScreenWidthDp;
        this.compatScreenHeightDp = configuration.compatScreenHeightDp;
        this.compatSmallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        this.assetsSeq = configuration.assetsSeq;
        this.seq = configuration.seq;
        this.windowConfiguration.setTo(configuration.windowConfiguration);
        this.fontWeightAdjustment = configuration.fontWeightAdjustment;
        this.rilSetLocale = configuration.rilSetLocale;
        this.FlipFont = configuration.FlipFont;
        this.boldFont = configuration.boldFont;
        this.semButtonShapeEnabled = configuration.semButtonShapeEnabled;
        this.semCursorThicknessScale = configuration.semCursorThicknessScale;
        this.nightDim = configuration.nightDim;
        this.themeSeq = configuration.themeSeq;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");
        sb.append(this.fontScale);
        sb.append(" ");
        int i = this.mcc;
        if (i != 0) {
            sb.append(i);
            sb.append("mcc");
        } else {
            sb.append("?mcc");
        }
        int i2 = this.mnc;
        if (i2 != 65535) {
            sb.append(i2);
            sb.append("mnc");
        } else {
            sb.append("?mnc");
        }
        fixUpLocaleList();
        if (!this.mLocaleList.isEmpty()) {
            sb.append(" ");
            sb.append(this.mLocaleList);
        } else {
            sb.append(" ?localeList");
        }
        int i3 = this.mGrammaticalGender;
        if (i3 > 0) {
            if (i3 == 1) {
                sb.append(" neuter");
            } else if (i3 == 2) {
                sb.append(" feminine");
            } else if (i3 == 3) {
                sb.append(" masculine");
            } else {
                sb.append(" ?grgend");
            }
        }
        int i4 = this.screenLayout & 192;
        if (i4 == 0) {
            sb.append(" ?layoutDir");
        } else if (i4 == 64) {
            sb.append(" ldltr");
        } else if (i4 == 128) {
            sb.append(" ldrtl");
        } else {
            sb.append(" layoutDir=");
            sb.append(i4 >> 6);
        }
        if (this.smallestScreenWidthDp != 0) {
            sb.append(" sw");
            sb.append(this.smallestScreenWidthDp);
            sb.append("dp");
        } else {
            sb.append(" ?swdp");
        }
        if (this.screenWidthDp != 0) {
            sb.append(" w");
            sb.append(this.screenWidthDp);
            sb.append("dp");
        } else {
            sb.append(" ?wdp");
        }
        if (this.screenHeightDp != 0) {
            sb.append(" h");
            sb.append(this.screenHeightDp);
            sb.append("dp");
        } else {
            sb.append(" ?hdp");
        }
        if (this.densityDpi != 0) {
            sb.append(" ");
            sb.append(this.densityDpi);
            sb.append("dpi");
        } else {
            sb.append(" ?density");
        }
        int i5 = this.screenLayout & 15;
        if (i5 == 0) {
            sb.append(" ?lsize");
        } else if (i5 == 1) {
            sb.append(" smll");
        } else if (i5 == 2) {
            sb.append(" nrml");
        } else if (i5 == 3) {
            sb.append(" lrg");
        } else if (i5 == 4) {
            sb.append(" xlrg");
        } else {
            sb.append(" layoutSize=");
            sb.append(this.screenLayout & 15);
        }
        int i6 = this.screenLayout & 48;
        if (i6 == 0) {
            sb.append(" ?long");
        } else if (i6 != 16) {
            if (i6 == 32) {
                sb.append(" long");
            } else {
                sb.append(" layoutLong=");
                sb.append(this.screenLayout & 48);
            }
        }
        int i7 = this.colorMode & 12;
        if (i7 == 0) {
            sb.append(" ?ldr");
        } else if (i7 != 4) {
            if (i7 == 8) {
                sb.append(" hdr");
            } else {
                sb.append(" dynamicRange=");
                sb.append(this.colorMode & 12);
            }
        }
        int i8 = this.colorMode & 3;
        if (i8 == 0) {
            sb.append(" ?wideColorGamut");
        } else if (i8 != 1) {
            if (i8 == 2) {
                sb.append(" widecg");
            } else {
                sb.append(" wideColorGamut=");
                sb.append(this.colorMode & 3);
            }
        }
        int i9 = this.orientation;
        if (i9 == 0) {
            sb.append(" ?orien");
        } else if (i9 == 1) {
            sb.append(" port");
        } else if (i9 == 2) {
            sb.append(" land");
        } else {
            sb.append(" orien=");
            sb.append(this.orientation);
        }
        switch (this.uiMode & 15) {
            case 0:
                sb.append(" ?uimode");
                break;
            case 1:
                break;
            case 2:
                sb.append(" desk");
                break;
            case 3:
                sb.append(" car");
                break;
            case 4:
                sb.append(" television");
                break;
            case 5:
                sb.append(" appliance");
                break;
            case 6:
                sb.append(" watch");
                break;
            case 7:
                sb.append(" vrheadset");
                break;
            default:
                sb.append(" uimode=");
                sb.append(this.uiMode & 15);
                break;
        }
        int i10 = this.uiMode & 48;
        if (i10 == 0) {
            sb.append(" ?night");
        } else if (i10 != 16) {
            if (i10 == 32) {
                sb.append(" night");
            } else {
                sb.append(" night=");
                sb.append(this.uiMode & 48);
            }
        }
        int i11 = this.touchscreen;
        if (i11 == 0) {
            sb.append(" ?touch");
        } else if (i11 == 1) {
            sb.append(" -touch");
        } else if (i11 == 2) {
            sb.append(" stylus");
        } else if (i11 == 3) {
            sb.append(" finger");
        } else {
            sb.append(" touch=");
            sb.append(this.touchscreen);
        }
        int i12 = this.keyboard;
        if (i12 == 0) {
            sb.append(" ?keyb");
        } else if (i12 == 1) {
            sb.append(" -keyb");
        } else if (i12 == 2) {
            sb.append(" qwerty");
        } else if (i12 == 3) {
            sb.append(" 12key");
        } else {
            sb.append(" keys=");
            sb.append(this.keyboard);
        }
        int i13 = this.keyboardHidden;
        if (i13 == 0) {
            sb.append("/?");
        } else if (i13 == 1) {
            sb.append("/v");
        } else if (i13 == 2) {
            sb.append("/h");
        } else if (i13 == 3) {
            sb.append("/s");
        } else {
            sb.append("/");
            sb.append(this.keyboardHidden);
        }
        int i14 = this.hardKeyboardHidden;
        if (i14 == 0) {
            sb.append("/?");
        } else if (i14 == 1) {
            sb.append("/v");
        } else if (i14 == 2) {
            sb.append("/h");
        } else {
            sb.append("/");
            sb.append(this.hardKeyboardHidden);
        }
        int i15 = this.navigation;
        if (i15 == 0) {
            sb.append(" ?nav");
        } else if (i15 == 1) {
            sb.append(" -nav");
        } else if (i15 == 2) {
            sb.append(" dpad");
        } else if (i15 == 3) {
            sb.append(" tball");
        } else if (i15 == 4) {
            sb.append(" wheel");
        } else {
            sb.append(" nav=");
            sb.append(this.navigation);
        }
        int i16 = this.navigationHidden;
        if (i16 == 0) {
            sb.append("/?");
        } else if (i16 == 1) {
            sb.append("/v");
        } else if (i16 == 2) {
            sb.append("/h");
        } else {
            sb.append("/");
            sb.append(this.navigationHidden);
        }
        sb.append(" winConfig=");
        sb.append(this.windowConfiguration);
        if (this.assetsSeq != 0) {
            sb.append(" as.");
            sb.append(this.assetsSeq);
        }
        if (this.seq != 0) {
            sb.append(" s.");
            sb.append(this.seq);
        }
        if (this.fontWeightAdjustment != Integer.MAX_VALUE) {
            sb.append(" fontWeightAdjustment=");
            sb.append(this.fontWeightAdjustment);
        } else {
            sb.append(" ?fontWeightAdjustment");
        }
        sb.append(" ff=");
        sb.append(this.FlipFont);
        sb.append(" bf=");
        sb.append(this.boldFont);
        sb.append(" bts=");
        sb.append(this.semButtonShapeEnabled);
        sb.append(" cst=");
        sb.append(this.semCursorThicknessScale);
        sb.append(" nightDim=");
        sb.append(this.nightDim);
        sb.append(" themeSeq=");
        sb.append(this.themeSeq);
        sb.append('}');
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, boolean z, boolean z2) {
        WindowConfiguration windowConfiguration;
        long start = protoOutputStream.start(j);
        if (!z2) {
            protoOutputStream.write(1108101562369L, this.fontScale);
            protoOutputStream.write(1155346202626L, this.mcc);
            protoOutputStream.write(1155346202627L, this.mnc);
            LocaleList localeList = this.mLocaleList;
            if (localeList != null) {
                protoOutputStream.write(1138166333460L, localeList.toLanguageTags());
            }
            protoOutputStream.write(1155346202629L, this.screenLayout);
            protoOutputStream.write(1155346202630L, this.colorMode);
            protoOutputStream.write(1155346202631L, this.touchscreen);
            protoOutputStream.write(1155346202632L, this.keyboard);
            protoOutputStream.write(ConfigurationProto.KEYBOARD_HIDDEN, this.keyboardHidden);
            protoOutputStream.write(1155346202634L, this.hardKeyboardHidden);
            protoOutputStream.write(ConfigurationProto.NAVIGATION, this.navigation);
            protoOutputStream.write(ConfigurationProto.NAVIGATION_HIDDEN, this.navigationHidden);
            protoOutputStream.write(ConfigurationProto.UI_MODE, this.uiMode);
            protoOutputStream.write(ConfigurationProto.SMALLEST_SCREEN_WIDTH_DP, this.smallestScreenWidthDp);
            protoOutputStream.write(ConfigurationProto.DENSITY_DPI, this.densityDpi);
            if (!z && (windowConfiguration = this.windowConfiguration) != null) {
                windowConfiguration.dumpDebug(protoOutputStream, 1146756268051L);
            }
            protoOutputStream.write(ConfigurationProto.FONT_WEIGHT_ADJUSTMENT, this.fontWeightAdjustment);
        }
        protoOutputStream.write(1155346202637L, this.orientation);
        protoOutputStream.write(ConfigurationProto.SCREEN_WIDTH_DP, this.screenWidthDp);
        protoOutputStream.write(ConfigurationProto.SCREEN_HEIGHT_DP, this.screenHeightDp);
        protoOutputStream.write(1155346202646L, this.mGrammaticalGender);
        protoOutputStream.end(start);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        dumpDebug(protoOutputStream, j, false, false);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, boolean z) {
        dumpDebug(protoOutputStream, j, false, z);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0245 A[Catch: IllformedLocaleException -> 0x026a, all -> 0x028f, TryCatch #7 {IllformedLocaleException -> 0x026a, blocks: (B:71:0x0225, B:73:0x0245, B:78:0x0266), top: B:70:0x0225, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0266 A[Catch: IllformedLocaleException -> 0x026a, all -> 0x028f, TRY_LEAVE, TryCatch #7 {IllformedLocaleException -> 0x026a, blocks: (B:71:0x0225, B:73:0x0245, B:78:0x0266), top: B:70:0x0225, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0301  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readFromProto(android.util.proto.ProtoInputStream r23, long r24) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 842
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.Configuration.readFromProto(android.util.proto.ProtoInputStream, long):void");
    }

    public void writeResConfigToProto(ProtoOutputStream protoOutputStream, long j, DisplayMetrics displayMetrics) {
        int i;
        int i2;
        if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
            i = displayMetrics.widthPixels;
            i2 = displayMetrics.heightPixels;
        } else {
            i = displayMetrics.heightPixels;
            i2 = displayMetrics.widthPixels;
        }
        long start = protoOutputStream.start(j);
        dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1155346202626L, Build.VERSION.RESOURCES_SDK_INT);
        protoOutputStream.write(1155346202627L, i);
        protoOutputStream.write(1155346202628L, i2);
        protoOutputStream.end(start);
    }

    public static String uiModeToString(int i) {
        switch (i) {
            case 0:
                return "UI_MODE_TYPE_UNDEFINED";
            case 1:
                return "UI_MODE_TYPE_NORMAL";
            case 2:
                return "UI_MODE_TYPE_DESK";
            case 3:
                return "UI_MODE_TYPE_CAR";
            case 4:
                return "UI_MODE_TYPE_TELEVISION";
            case 5:
                return "UI_MODE_TYPE_APPLIANCE";
            case 6:
                return "UI_MODE_TYPE_WATCH";
            case 7:
                return "UI_MODE_TYPE_VR_HEADSET";
            default:
                return Integer.toString(i);
        }
    }

    public void setToDefaults() {
        this.fontScale = 1.0f;
        this.mnc = 0;
        this.mcc = 0;
        this.mLocaleList = LocaleList.getEmptyLocaleList();
        this.locale = null;
        this.userSetLocale = false;
        this.touchscreen = 0;
        this.keyboard = 0;
        this.keyboardHidden = 0;
        this.hardKeyboardHidden = 0;
        this.navigation = 0;
        this.navigationHidden = 0;
        this.orientation = 0;
        this.screenLayout = 0;
        this.colorMode = 0;
        this.uiMode = 0;
        this.compatScreenWidthDp = 0;
        this.screenWidthDp = 0;
        this.compatScreenHeightDp = 0;
        this.screenHeightDp = 0;
        this.compatSmallestScreenWidthDp = 0;
        this.smallestScreenWidthDp = 0;
        this.densityDpi = 0;
        this.assetsSeq = 0;
        this.seq = 0;
        this.windowConfiguration.setToDefaults();
        this.fontWeightAdjustment = Integer.MAX_VALUE;
        this.mGrammaticalGender = -1;
        this.rilSetLocale = false;
        this.FlipFont = 0;
        this.boldFont = -1;
        this.semButtonShapeEnabled = -1;
        this.semCursorThicknessScale = 0.0f;
        this.nightDim = -1;
        this.themeSeq = 0;
    }

    public void unset() {
        setToDefaults();
        this.fontScale = 0.0f;
    }

    @Deprecated
    public void makeDefault() {
        setToDefaults();
    }

    public int updateFrom(Configuration configuration) {
        return updateFrom(configuration, false);
    }

    public int updateFrom(Configuration configuration, boolean z) {
        int i;
        int i2;
        float f = configuration.fontScale;
        if (f <= 0.0f || this.fontScale == f) {
            i = 0;
        } else {
            this.fontScale = f;
            i = 1073741824;
        }
        int i3 = configuration.mcc;
        if (i3 != 0 && this.mcc != i3) {
            i |= 1;
            this.mcc = i3;
        }
        int i4 = configuration.mnc;
        if (i4 != 0 && this.mnc != i4) {
            i |= 2;
            this.mnc = i4;
        }
        fixUpLocaleList();
        configuration.fixUpLocaleList();
        if (!configuration.mLocaleList.isEmpty() && !this.mLocaleList.equals(configuration.mLocaleList)) {
            int i5 = i | 4;
            this.mLocaleList = configuration.mLocaleList;
            if (configuration.locale.equals(this.locale)) {
                i = i5;
            } else {
                Locale locale = (Locale) configuration.locale.clone();
                this.locale = locale;
                i |= 8196;
                setLayoutDirection(locale);
            }
        }
        int i6 = configuration.screenLayout & 192;
        if (i6 != 0) {
            int i7 = this.screenLayout;
            if (i6 != (i7 & 192)) {
                this.screenLayout = i6 | (i7 & (-193));
                i |= 8192;
            }
        }
        if (configuration.userSetLocale && (!this.userSetLocale || (i & 4) != 0)) {
            i |= 4;
            this.userSetLocale = true;
        }
        int i8 = configuration.touchscreen;
        if (i8 != 0 && this.touchscreen != i8) {
            i |= 8;
            this.touchscreen = i8;
        }
        int i9 = configuration.keyboard;
        if (i9 != 0 && this.keyboard != i9) {
            i |= 16;
            this.keyboard = i9;
        }
        int i10 = configuration.keyboardHidden;
        if (i10 != 0 && this.keyboardHidden != i10) {
            i |= 32;
            this.keyboardHidden = i10;
        }
        int i11 = configuration.hardKeyboardHidden;
        if (i11 != 0 && this.hardKeyboardHidden != i11) {
            i |= 32;
            this.hardKeyboardHidden = i11;
        }
        int i12 = configuration.navigation;
        if (i12 != 0 && this.navigation != i12) {
            i |= 64;
            this.navigation = i12;
        }
        int i13 = configuration.navigationHidden;
        if (i13 != 0 && this.navigationHidden != i13) {
            i |= 32;
            this.navigationHidden = i13;
        }
        int i14 = configuration.orientation;
        if (i14 != 0 && this.orientation != i14) {
            i |= 128;
            this.orientation = i14;
        }
        int i15 = configuration.screenLayout;
        if ((i15 & 15) != 0) {
            int i16 = i15 & 15;
            int i17 = this.screenLayout;
            if (i16 != (i17 & 15)) {
                i |= 256;
                this.screenLayout = (i15 & 15) | (i17 & (-16));
            }
        }
        int i18 = configuration.screenLayout;
        if ((i18 & 48) != 0) {
            int i19 = i18 & 48;
            int i20 = this.screenLayout;
            if (i19 != (i20 & 48)) {
                i |= 256;
                this.screenLayout = (i18 & 48) | (i20 & (-49));
            }
        }
        int i21 = configuration.screenLayout;
        if ((i21 & 768) != 0) {
            int i22 = i21 & 768;
            int i23 = this.screenLayout;
            if (i22 != (i23 & 768)) {
                i |= 256;
                this.screenLayout = (i21 & 768) | (i23 & (-769));
            }
        }
        int i24 = configuration.screenLayout;
        int i25 = i24 & 268435456;
        int i26 = this.screenLayout;
        if (i25 != (i26 & 268435456) && i24 != 0) {
            i |= 256;
            this.screenLayout = (i24 & 268435456) | ((-268435457) & i26);
        }
        int i27 = configuration.colorMode;
        if ((i27 & 3) != 0) {
            int i28 = i27 & 3;
            int i29 = this.colorMode;
            if (i28 != (i29 & 3)) {
                i |= 16384;
                this.colorMode = (i27 & 3) | (i29 & (-4));
            }
        }
        int i30 = configuration.colorMode;
        if ((i30 & 12) != 0) {
            int i31 = i30 & 12;
            int i32 = this.colorMode;
            if (i31 != (i32 & 12)) {
                i |= 16384;
                this.colorMode = (i30 & 12) | (i32 & (-13));
            }
        }
        int i33 = configuration.uiMode;
        if (i33 != 0 && (i2 = this.uiMode) != i33) {
            i |= 512;
            if ((i33 & 15) != 0) {
                this.uiMode = (i33 & 15) | (i2 & (-16));
            }
            int i34 = configuration.uiMode;
            if ((i34 & 48) != 0) {
                this.uiMode = (i34 & 48) | (this.uiMode & (-49));
            }
        }
        int i35 = configuration.screenWidthDp;
        if (i35 != 0 && this.screenWidthDp != i35) {
            i |= 1024;
            this.screenWidthDp = i35;
        }
        int i36 = configuration.screenHeightDp;
        if (i36 != 0 && this.screenHeightDp != i36) {
            i |= 1024;
            this.screenHeightDp = i36;
        }
        int i37 = configuration.smallestScreenWidthDp;
        if (i37 != 0 && this.smallestScreenWidthDp != i37) {
            i |= 2048;
            this.smallestScreenWidthDp = i37;
        }
        int i38 = configuration.densityDpi;
        if (i38 != 0 && this.densityDpi != i38) {
            i |= 4096;
            this.densityDpi = i38;
        }
        int i39 = configuration.compatScreenWidthDp;
        if (i39 != 0) {
            this.compatScreenWidthDp = i39;
        }
        int i40 = configuration.compatScreenHeightDp;
        if (i40 != 0) {
            this.compatScreenHeightDp = i40;
        }
        int i41 = configuration.compatSmallestScreenWidthDp;
        if (i41 != 0) {
            this.compatSmallestScreenWidthDp = i41;
        }
        int i42 = configuration.assetsSeq;
        if (i42 != 0 && i42 != this.assetsSeq) {
            i |= Integer.MIN_VALUE;
            this.assetsSeq = i42;
        }
        int i43 = configuration.seq;
        if (i43 != 0) {
            this.seq = i43;
        }
        if (!z && this.windowConfiguration.updateFrom(configuration.windowConfiguration) != 0) {
            i |= 536870912;
        }
        int i44 = configuration.fontWeightAdjustment;
        if (i44 != Integer.MAX_VALUE && i44 != this.fontWeightAdjustment) {
            i |= 268435456;
            this.fontWeightAdjustment = i44;
        }
        int i45 = configuration.mGrammaticalGender;
        if (i45 != -1 && i45 != this.mGrammaticalGender) {
            i |= 32768;
            this.mGrammaticalGender = i45;
        }
        int i46 = configuration.FlipFont;
        if (i46 > 0 && this.FlipFont != i46) {
            i |= 33554432;
            this.FlipFont = i46;
        }
        int i47 = configuration.boldFont;
        if (i47 != -1 && this.boldFont != i47) {
            i |= 16777216;
            this.boldFont = i47;
        }
        int i48 = configuration.semButtonShapeEnabled;
        if (i48 != -1 && this.semButtonShapeEnabled != i48) {
            i |= 2097152;
            this.semButtonShapeEnabled = i48;
        }
        float f2 = configuration.semCursorThicknessScale;
        if (f2 > 0.0f && this.semCursorThicknessScale != f2) {
            i |= 8388608;
            this.semCursorThicknessScale = f2;
        }
        int i49 = configuration.nightDim;
        if (i49 != -1 && this.nightDim != i49) {
            i |= 4194304;
            this.nightDim = i49;
        }
        int i50 = configuration.themeSeq;
        if (i50 <= 0 || this.themeSeq == i50) {
            return i;
        }
        int i51 = 65536 | i;
        this.themeSeq = i50;
        return i51;
    }

    public void setTo(Configuration configuration, int i, int i2) {
        if ((1073741824 & i) != 0) {
            this.fontScale = configuration.fontScale;
        }
        if ((i & 1) != 0) {
            this.mcc = configuration.mcc;
        }
        if ((i & 2) != 0) {
            this.mnc = configuration.mnc;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            LocaleList localeList = configuration.mLocaleList;
            this.mLocaleList = localeList;
            if (!localeList.isEmpty() && !configuration.locale.equals(this.locale)) {
                this.locale = (Locale) configuration.locale.clone();
            }
        }
        if ((i & 8192) != 0) {
            this.screenLayout = (configuration.screenLayout & 192) | (this.screenLayout & (-193));
        }
        if (i3 != 0) {
            this.userSetLocale = configuration.userSetLocale;
        }
        if ((i & 8) != 0) {
            this.touchscreen = configuration.touchscreen;
        }
        if ((i & 16) != 0) {
            this.keyboard = configuration.keyboard;
        }
        if ((i & 32) != 0) {
            this.keyboardHidden = configuration.keyboardHidden;
            this.hardKeyboardHidden = configuration.hardKeyboardHidden;
            this.navigationHidden = configuration.navigationHidden;
        }
        if ((i & 64) != 0) {
            this.navigation = configuration.navigation;
        }
        if ((i & 128) != 0) {
            this.orientation = configuration.orientation;
        }
        if ((i & 256) != 0) {
            this.screenLayout |= configuration.screenLayout & (-193);
        }
        if ((i & 16384) != 0) {
            this.colorMode = configuration.colorMode;
        }
        if ((i & 512) != 0) {
            this.uiMode = configuration.uiMode;
        }
        if ((i & 1024) != 0) {
            this.screenWidthDp = configuration.screenWidthDp;
            this.screenHeightDp = configuration.screenHeightDp;
        }
        if ((i & 2048) != 0) {
            this.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        }
        if ((i & 4096) != 0) {
            this.densityDpi = configuration.densityDpi;
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            this.assetsSeq = configuration.assetsSeq;
        }
        if ((536870912 & i) != 0) {
            this.windowConfiguration.setTo(configuration.windowConfiguration, i2);
        }
        if ((268435456 & i) != 0) {
            this.fontWeightAdjustment = configuration.fontWeightAdjustment;
        }
        if ((32768 & i) != 0) {
            this.mGrammaticalGender = configuration.mGrammaticalGender;
        }
        if ((33554432 & i) != 0) {
            this.FlipFont = configuration.FlipFont;
        }
        if ((16777216 & i) != 0) {
            this.boldFont = configuration.boldFont;
        }
        if ((2097152 & i) != 0) {
            this.semButtonShapeEnabled = configuration.semButtonShapeEnabled;
        }
        if ((8388608 & i) != 0) {
            this.semCursorThicknessScale = configuration.semCursorThicknessScale;
        }
        if ((i & 4194304) != 0) {
            this.nightDim = configuration.nightDim;
        }
    }

    public int diff(Configuration configuration) {
        return diff(configuration, false, false);
    }

    public int diffPublicOnly(Configuration configuration) {
        return diff(configuration, false, true);
    }

    public int diff(Configuration configuration, boolean z, boolean z2) {
        int i = ((z || configuration.fontScale > 0.0f) && this.fontScale != configuration.fontScale) ? 1073741824 : 0;
        if ((z || configuration.mcc != 0) && this.mcc != configuration.mcc) {
            i |= 1;
        }
        if ((z || configuration.mnc != 0) && this.mnc != configuration.mnc) {
            i |= 2;
        }
        fixUpLocaleList();
        configuration.fixUpLocaleList();
        if ((z || !configuration.mLocaleList.isEmpty()) && !this.mLocaleList.equals(configuration.mLocaleList)) {
            i |= 8196;
        }
        int i2 = configuration.screenLayout;
        int i3 = i2 & 192;
        if ((z || i3 != 0) && i3 != (this.screenLayout & 192)) {
            i |= 8192;
        }
        if ((z || configuration.touchscreen != 0) && this.touchscreen != configuration.touchscreen) {
            i |= 8;
        }
        if ((z || configuration.keyboard != 0) && this.keyboard != configuration.keyboard) {
            i |= 16;
        }
        if ((z || configuration.keyboardHidden != 0) && this.keyboardHidden != configuration.keyboardHidden) {
            i |= 32;
        }
        if ((z || configuration.hardKeyboardHidden != 0) && this.hardKeyboardHidden != configuration.hardKeyboardHidden) {
            i |= 32;
        }
        if ((z || configuration.navigation != 0) && this.navigation != configuration.navigation) {
            i |= 64;
        }
        if ((z || configuration.navigationHidden != 0) && this.navigationHidden != configuration.navigationHidden) {
            i |= 32;
        }
        if ((z || configuration.orientation != 0) && this.orientation != configuration.orientation) {
            i |= 128;
        }
        if ((z || getScreenLayoutNoDirection(i2) != 0) && getScreenLayoutNoDirection(this.screenLayout) != getScreenLayoutNoDirection(configuration.screenLayout)) {
            i |= 256;
        }
        if ((z || (configuration.colorMode & 12) != 0) && (this.colorMode & 12) != (configuration.colorMode & 12)) {
            i |= 16384;
        }
        if ((z || (configuration.colorMode & 3) != 0) && (this.colorMode & 3) != (configuration.colorMode & 3)) {
            i |= 16384;
        }
        if ((z || configuration.uiMode != 0) && this.uiMode != configuration.uiMode) {
            i |= 512;
        }
        if ((z || configuration.screenWidthDp != 0) && this.screenWidthDp != configuration.screenWidthDp) {
            i |= 1024;
        }
        if ((z || configuration.screenHeightDp != 0) && this.screenHeightDp != configuration.screenHeightDp) {
            i |= 1024;
        }
        if ((z || configuration.smallestScreenWidthDp != 0) && this.smallestScreenWidthDp != configuration.smallestScreenWidthDp) {
            i |= 2048;
        }
        if ((z || configuration.densityDpi != 0) && this.densityDpi != configuration.densityDpi) {
            i |= 4096;
        }
        if ((z || configuration.assetsSeq != 0) && this.assetsSeq != configuration.assetsSeq) {
            i |= Integer.MIN_VALUE;
        }
        if (!z2 && this.windowConfiguration.diff(configuration.windowConfiguration, z) != 0) {
            i |= 536870912;
        }
        if ((z || configuration.fontWeightAdjustment != Integer.MAX_VALUE) && this.fontWeightAdjustment != configuration.fontWeightAdjustment) {
            i |= 268435456;
        }
        if ((z || configuration.mGrammaticalGender != -1) && this.mGrammaticalGender != configuration.mGrammaticalGender) {
            i |= 32768;
        }
        int i4 = configuration.FlipFont;
        if (i4 > 0 && this.FlipFont != i4) {
            i |= 33554432;
        }
        int i5 = configuration.boldFont;
        if (i5 != -1 && this.boldFont != i5) {
            i |= 16777216;
        }
        int i6 = configuration.semButtonShapeEnabled;
        if (i6 != -1 && this.semButtonShapeEnabled != i6) {
            i |= 2097152;
        }
        float f = configuration.semCursorThicknessScale;
        if (f > 0.0f && this.semCursorThicknessScale != f) {
            i |= 8388608;
        }
        int i7 = configuration.nightDim;
        if (i7 != -1 && this.nightDim != i7) {
            i |= 4194304;
        }
        int i8 = configuration.themeSeq;
        return (i8 <= 0 || this.themeSeq == i8) ? i : 65536 | i;
    }

    public boolean isOtherSeqNewer(Configuration configuration) {
        int i;
        if (configuration == null) {
            return false;
        }
        int i2 = configuration.seq;
        if (i2 == 0 || (i = this.seq) == 0) {
            return true;
        }
        int i3 = i2 - i;
        if (Math.abs(i3) > 268435456) {
            return i3 < 0;
        }
        int i4 = configuration.themeSeq - this.themeSeq;
        if (i4 > 65536) {
            return false;
        }
        return i3 > 0 || i4 > 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.fontScale);
        parcel.writeInt(this.mcc);
        parcel.writeInt(this.mnc);
        fixUpLocaleList();
        parcel.writeTypedObject(this.mLocaleList, i);
        parcel.writeInt((this.userSetLocale ? 1 : 0) | (this.rilSetLocale ? 2 : 0));
        if (this.userSetLocale) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.touchscreen);
        parcel.writeInt(this.keyboard);
        parcel.writeInt(this.keyboardHidden);
        parcel.writeInt(this.hardKeyboardHidden);
        parcel.writeInt(this.navigation);
        parcel.writeInt(this.navigationHidden);
        parcel.writeInt(this.orientation);
        parcel.writeInt(this.screenLayout);
        parcel.writeInt(this.colorMode);
        parcel.writeInt(this.uiMode);
        parcel.writeInt(this.screenWidthDp);
        parcel.writeInt(this.screenHeightDp);
        parcel.writeInt(this.smallestScreenWidthDp);
        parcel.writeInt(this.densityDpi);
        parcel.writeInt(this.compatScreenWidthDp);
        parcel.writeInt(this.compatScreenHeightDp);
        parcel.writeInt(this.compatSmallestScreenWidthDp);
        this.windowConfiguration.writeToParcel(parcel, i);
        parcel.writeInt(this.assetsSeq);
        parcel.writeInt(this.seq);
        parcel.writeInt(this.fontWeightAdjustment);
        parcel.writeInt(this.mGrammaticalGender);
        parcel.writeInt(this.FlipFont);
        parcel.writeInt(this.boldFont);
        parcel.writeInt(this.semButtonShapeEnabled);
        parcel.writeFloat(this.semCursorThicknessScale);
        parcel.writeInt(this.nightDim);
        parcel.writeInt(this.themeSeq);
    }

    public void readFromParcel(Parcel parcel) {
        this.fontScale = parcel.readFloat();
        this.mcc = parcel.readInt();
        this.mnc = parcel.readInt();
        LocaleList localeList = (LocaleList) parcel.readTypedObject(LocaleList.CREATOR);
        this.mLocaleList = localeList;
        this.locale = localeList.get(0);
        int readInt = parcel.readInt();
        this.userSetLocale = (readInt & 1) != 0;
        this.rilSetLocale = (readInt & 2) != 0;
        this.userSetLocale = parcel.readInt() == 1;
        this.touchscreen = parcel.readInt();
        this.keyboard = parcel.readInt();
        this.keyboardHidden = parcel.readInt();
        this.hardKeyboardHidden = parcel.readInt();
        this.navigation = parcel.readInt();
        this.navigationHidden = parcel.readInt();
        this.orientation = parcel.readInt();
        this.screenLayout = parcel.readInt();
        this.colorMode = parcel.readInt();
        this.uiMode = parcel.readInt();
        this.screenWidthDp = parcel.readInt();
        this.screenHeightDp = parcel.readInt();
        this.smallestScreenWidthDp = parcel.readInt();
        this.densityDpi = parcel.readInt();
        this.compatScreenWidthDp = parcel.readInt();
        this.compatScreenHeightDp = parcel.readInt();
        this.compatSmallestScreenWidthDp = parcel.readInt();
        this.windowConfiguration.readFromParcel(parcel);
        this.assetsSeq = parcel.readInt();
        this.seq = parcel.readInt();
        this.fontWeightAdjustment = parcel.readInt();
        this.mGrammaticalGender = parcel.readInt();
        this.FlipFont = parcel.readInt();
        this.boldFont = parcel.readInt();
        this.semButtonShapeEnabled = parcel.readInt();
        this.semCursorThicknessScale = parcel.readFloat();
        this.nightDim = parcel.readInt();
        this.themeSeq = parcel.readInt();
    }

    private Configuration(Parcel parcel) {
        this.windowConfiguration = new WindowConfiguration();
        this.semMobileKeyboardCovered = -1;
        this.semDesktopModeEnabled = -1;
        this.semDisplayDeviceType = -1;
        this.themeSeq = 0;
        this.dexMode = -1;
        readFromParcel(parcel);
    }

    public boolean isNightModeActive() {
        return (this.uiMode & 48) == 32;
    }

    @Override // java.lang.Comparable
    public int compareTo(Configuration configuration) {
        float f = this.fontScale;
        float f2 = configuration.fontScale;
        if (f < f2) {
            return -1;
        }
        if (f > f2) {
            return 1;
        }
        int i = this.mcc - configuration.mcc;
        if (i != 0) {
            return i;
        }
        int i2 = this.mnc - configuration.mnc;
        if (i2 != 0) {
            return i2;
        }
        fixUpLocaleList();
        configuration.fixUpLocaleList();
        if (this.mLocaleList.isEmpty()) {
            if (!configuration.mLocaleList.isEmpty()) {
                return 1;
            }
        } else {
            if (configuration.mLocaleList.isEmpty()) {
                return -1;
            }
            int min = Math.min(this.mLocaleList.size(), configuration.mLocaleList.size());
            for (int i3 = 0; i3 < min; i3++) {
                Locale locale = this.mLocaleList.get(i3);
                Locale locale2 = configuration.mLocaleList.get(i3);
                int compareTo = locale.getLanguage().compareTo(locale2.getLanguage());
                if (compareTo != 0) {
                    return compareTo;
                }
                int compareTo2 = locale.getCountry().compareTo(locale2.getCountry());
                if (compareTo2 != 0) {
                    return compareTo2;
                }
                int compareTo3 = locale.getVariant().compareTo(locale2.getVariant());
                if (compareTo3 != 0) {
                    return compareTo3;
                }
                int compareTo4 = locale.toLanguageTag().compareTo(locale2.toLanguageTag());
                if (compareTo4 != 0) {
                    return compareTo4;
                }
            }
            int size = this.mLocaleList.size() - configuration.mLocaleList.size();
            if (size != 0) {
                return size;
            }
        }
        int i4 = this.mGrammaticalGender - configuration.mGrammaticalGender;
        if (i4 != 0) {
            return i4;
        }
        int i5 = this.touchscreen - configuration.touchscreen;
        if (i5 != 0) {
            return i5;
        }
        int i6 = this.keyboard - configuration.keyboard;
        if (i6 != 0) {
            return i6;
        }
        int i7 = this.keyboardHidden - configuration.keyboardHidden;
        if (i7 != 0) {
            return i7;
        }
        int i8 = this.hardKeyboardHidden - configuration.hardKeyboardHidden;
        if (i8 != 0) {
            return i8;
        }
        int i9 = this.navigation - configuration.navigation;
        if (i9 != 0) {
            return i9;
        }
        int i10 = this.navigationHidden - configuration.navigationHidden;
        if (i10 != 0) {
            return i10;
        }
        int i11 = this.orientation - configuration.orientation;
        if (i11 != 0) {
            return i11;
        }
        int i12 = this.colorMode - configuration.colorMode;
        if (i12 != 0) {
            return i12;
        }
        int i13 = this.screenLayout - configuration.screenLayout;
        if (i13 != 0) {
            return i13;
        }
        int i14 = this.uiMode - configuration.uiMode;
        if (i14 != 0) {
            return i14;
        }
        int i15 = this.screenWidthDp - configuration.screenWidthDp;
        if (i15 != 0) {
            return i15;
        }
        int i16 = this.screenHeightDp - configuration.screenHeightDp;
        if (i16 != 0) {
            return i16;
        }
        int i17 = this.smallestScreenWidthDp - configuration.smallestScreenWidthDp;
        if (i17 != 0) {
            return i17;
        }
        int i18 = this.densityDpi - configuration.densityDpi;
        if (i18 != 0) {
            return i18;
        }
        int i19 = this.assetsSeq - configuration.assetsSeq;
        if (i19 != 0) {
            return i19;
        }
        int compareTo5 = this.windowConfiguration.compareTo(configuration.windowConfiguration);
        if (compareTo5 != 0) {
            return compareTo5;
        }
        int i20 = this.fontWeightAdjustment - configuration.fontWeightAdjustment;
        if (i20 != 0) {
            return i20;
        }
        float f3 = this.FlipFont;
        float f4 = configuration.FlipFont;
        if (f3 < f4) {
            return -1;
        }
        if (f3 > f4) {
            return 1;
        }
        float f5 = this.boldFont;
        float f6 = configuration.boldFont;
        if (f5 < f6) {
            return -1;
        }
        if (f5 > f6) {
            return 1;
        }
        int i21 = this.semButtonShapeEnabled - configuration.semButtonShapeEnabled;
        if (i21 == 0) {
            float f7 = this.semCursorThicknessScale;
            float f8 = configuration.semCursorThicknessScale;
            if (f7 < f8) {
                return -1;
            }
            if (f7 > f8) {
                return 1;
            }
            float f9 = this.nightDim;
            float f10 = configuration.nightDim;
            if (f9 < f10) {
                return -1;
            }
            if (f9 > f10) {
                return 1;
            }
            int i22 = this.themeSeq;
            int i23 = configuration.themeSeq;
            if (i22 < i23) {
                return -1;
            }
            if (i22 > i23) {
                return 1;
            }
        }
        return i21;
    }

    public boolean equals(Configuration configuration) {
        if (configuration == null) {
            return false;
        }
        return configuration == this || compareTo(configuration) == 0;
    }

    public boolean equals(Object obj) {
        try {
            return equals((Configuration) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((((((((((((((((527 + Float.floatToIntBits(this.fontScale)) * 31) + this.mcc) * 31) + this.mnc) * 31) + this.mLocaleList.hashCode()) * 31) + this.touchscreen) * 31) + this.keyboard) * 31) + this.keyboardHidden) * 31) + this.hardKeyboardHidden) * 31) + this.navigation) * 31) + this.navigationHidden) * 31) + this.orientation) * 31) + this.screenLayout) * 31) + this.colorMode) * 31) + this.uiMode) * 31) + this.screenWidthDp) * 31) + this.screenHeightDp) * 31) + this.smallestScreenWidthDp) * 31) + this.densityDpi) * 31) + this.assetsSeq) * 31) + this.fontWeightAdjustment) * 31) + this.mGrammaticalGender) * 31) + this.FlipFont) * 31) + this.boldFont) * 31) + this.semButtonShapeEnabled) * 31) + Float.floatToIntBits(this.semCursorThicknessScale)) * 31) + this.nightDim) * 31) + this.themeSeq;
    }

    public int getGrammaticalGender() {
        int i = this.mGrammaticalGender;
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public int getGrammaticalGenderRaw() {
        return this.mGrammaticalGender;
    }

    public void setGrammaticalGender(int i) {
        this.mGrammaticalGender = i;
    }

    public LocaleList getLocales() {
        fixUpLocaleList();
        return this.mLocaleList;
    }

    public void setLocales(LocaleList localeList) {
        LocaleList localeList2 = this.mLocaleList;
        if (localeList == null) {
            localeList = LocaleList.getEmptyLocaleList();
        }
        this.mLocaleList = localeList;
        this.locale = localeList.get(0);
        if (!this.mLocaleList.equals(localeList2)) {
            Slog.v(TAG, "Updating configuration, locales updated from " + localeList2 + " to " + this.mLocaleList);
        }
        setLayoutDirection(this.locale);
    }

    public void setLocale(Locale locale) {
        setLocales(locale == null ? LocaleList.getEmptyLocaleList() : new LocaleList(locale));
    }

    public void clearLocales() {
        this.mLocaleList = LocaleList.getEmptyLocaleList();
        this.locale = null;
    }

    public int getLayoutDirection() {
        return (this.screenLayout & 192) == 128 ? 1 : 0;
    }

    public void setLayoutDirection(Locale locale) {
        this.screenLayout = ((TextUtils.getLayoutDirectionFromLocale(locale) + 1) << 6) | (this.screenLayout & (-193));
    }

    public boolean isScreenRound() {
        return (this.screenLayout & 768) == 512;
    }

    public boolean isScreenWideColorGamut() {
        return (this.colorMode & 3) == 2;
    }

    public boolean isScreenHdr() {
        return (this.colorMode & 12) == 8;
    }

    public static String localesToResourceQualifier(LocaleList localeList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            int length = locale.getLanguage().length();
            if (length != 0) {
                int length2 = locale.getScript().length();
                int length3 = locale.getCountry().length();
                int length4 = locale.getVariant().length();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                if (length == 2 && length2 == 0 && ((length3 == 0 || length3 == 2) && length4 == 0)) {
                    sb.append(locale.getLanguage());
                    if (length3 == 2) {
                        sb.append("-r");
                        sb.append(locale.getCountry());
                    }
                } else {
                    sb.append("b+");
                    sb.append(locale.getLanguage());
                    if (length2 != 0) {
                        sb.append("+");
                        sb.append(locale.getScript());
                    }
                    if (length3 != 0) {
                        sb.append("+");
                        sb.append(locale.getCountry());
                    }
                    if (length4 != 0) {
                        sb.append("+");
                        sb.append(locale.getVariant());
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String resourceQualifierString(Configuration configuration) {
        return resourceQualifierString(configuration, null);
    }

    public static String resourceQualifierString(Configuration configuration, DisplayMetrics displayMetrics) {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        if (configuration.mcc != 0) {
            arrayList.add("mcc" + configuration.mcc);
            if (configuration.mnc != 0) {
                arrayList.add("mnc" + configuration.mnc);
            }
        }
        if (!configuration.mLocaleList.isEmpty()) {
            String localesToResourceQualifier = localesToResourceQualifier(configuration.mLocaleList);
            if (!localesToResourceQualifier.isEmpty()) {
                arrayList.add(localesToResourceQualifier);
            }
        }
        int i3 = configuration.mGrammaticalGender;
        if (i3 == 1) {
            arrayList.add("neuter");
        } else if (i3 == 2) {
            arrayList.add("feminine");
        } else if (i3 == 3) {
            arrayList.add("masculine");
        }
        int i4 = configuration.screenLayout & 192;
        if (i4 == 64) {
            arrayList.add("ldltr");
        } else if (i4 == 128) {
            arrayList.add("ldrtl");
        }
        if (configuration.smallestScreenWidthDp != 0) {
            arrayList.add(XML_ATTR_SMALLEST_WIDTH + configuration.smallestScreenWidthDp + "dp");
        }
        if (configuration.screenWidthDp != 0) {
            arrayList.add("w" + configuration.screenWidthDp + "dp");
        }
        if (configuration.screenHeightDp != 0) {
            arrayList.add("h" + configuration.screenHeightDp + "dp");
        }
        int i5 = configuration.screenLayout & 15;
        if (i5 == 1) {
            arrayList.add("small");
        } else if (i5 == 2) {
            arrayList.add("normal");
        } else if (i5 == 3) {
            arrayList.add(Slice.HINT_LARGE);
        } else if (i5 == 4) {
            arrayList.add("xlarge");
        }
        int i6 = configuration.screenLayout & 48;
        if (i6 == 16) {
            arrayList.add("notlong");
        } else if (i6 == 32) {
            arrayList.add(SliceItem.FORMAT_LONG);
        }
        int i7 = configuration.screenLayout & 768;
        if (i7 == 256) {
            arrayList.add("notround");
        } else if (i7 == 512) {
            arrayList.add("round");
        }
        int i8 = configuration.colorMode & 3;
        if (i8 == 1) {
            arrayList.add("nowidecg");
        } else if (i8 == 2) {
            arrayList.add("widecg");
        }
        int i9 = configuration.colorMode & 12;
        if (i9 == 4) {
            arrayList.add("lowdr");
        } else if (i9 == 8) {
            arrayList.add("highdr");
        }
        int i10 = configuration.orientation;
        if (i10 == 1) {
            arrayList.add("port");
        } else if (i10 == 2) {
            arrayList.add("land");
        }
        String uiModeTypeString = getUiModeTypeString(configuration.uiMode & 15);
        if (uiModeTypeString != null) {
            arrayList.add(uiModeTypeString);
        }
        int i11 = configuration.uiMode & 48;
        if (i11 == 16) {
            arrayList.add("notnight");
        } else if (i11 == 32) {
            arrayList.add(Camera.Parameters.SCENE_MODE_NIGHT);
        }
        int i12 = configuration.densityDpi;
        if (i12 != 0) {
            if (i12 == 120) {
                arrayList.add("ldpi");
            } else if (i12 == 160) {
                arrayList.add("mdpi");
            } else if (i12 == 213) {
                arrayList.add("tvdpi");
            } else if (i12 == 240) {
                arrayList.add("hdpi");
            } else if (i12 == 320) {
                arrayList.add("xhdpi");
            } else if (i12 == 480) {
                arrayList.add("xxhdpi");
            } else if (i12 == 640) {
                arrayList.add("xxxhdpi");
            } else {
                switch (i12) {
                    case DENSITY_DPI_ANY /* 65534 */:
                        arrayList.add("anydpi");
                        break;
                    case 65535:
                        arrayList.add("nodpi");
                        break;
                    default:
                        arrayList.add(configuration.densityDpi + "dpi");
                        break;
                }
            }
        }
        int i13 = configuration.touchscreen;
        if (i13 == 1) {
            arrayList.add("notouch");
        } else if (i13 == 3) {
            arrayList.add("finger");
        }
        int i14 = configuration.keyboardHidden;
        if (i14 == 1) {
            arrayList.add("keysexposed");
        } else if (i14 == 2) {
            arrayList.add("keyshidden");
        } else if (i14 == 3) {
            arrayList.add("keyssoft");
        }
        int i15 = configuration.keyboard;
        if (i15 == 1) {
            arrayList.add("nokeys");
        } else if (i15 == 2) {
            arrayList.add("qwerty");
        } else if (i15 == 3) {
            arrayList.add("12key");
        }
        int i16 = configuration.navigationHidden;
        if (i16 == 1) {
            arrayList.add("navexposed");
        } else if (i16 == 2) {
            arrayList.add("navhidden");
        }
        int i17 = configuration.navigation;
        if (i17 == 1) {
            arrayList.add("nonav");
        } else if (i17 == 2) {
            arrayList.add("dpad");
        } else if (i17 == 3) {
            arrayList.add("trackball");
        } else if (i17 == 4) {
            arrayList.add("wheel");
        }
        if (displayMetrics != null) {
            if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
                i = displayMetrics.widthPixels;
                i2 = displayMetrics.heightPixels;
            } else {
                i = displayMetrics.heightPixels;
                i2 = displayMetrics.widthPixels;
            }
            arrayList.add(i + "x" + i2);
        }
        arrayList.add("v" + Build.VERSION.RESOURCES_SDK_INT);
        return TextUtils.join(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, arrayList);
    }

    public static String getUiModeTypeString(int i) {
        switch (i) {
            case 2:
                return "desk";
            case 3:
                return "car";
            case 4:
                return "television";
            case 5:
                return "appliance";
            case 6:
                return "watch";
            case 7:
                return "vrheadset";
            default:
                return null;
        }
    }

    public static Configuration generateDelta(Configuration configuration, Configuration configuration2) {
        Configuration configuration3 = new Configuration();
        float f = configuration.fontScale;
        float f2 = configuration2.fontScale;
        if (f != f2) {
            configuration3.fontScale = f2;
        }
        int i = configuration.mcc;
        int i2 = configuration2.mcc;
        if (i != i2) {
            configuration3.mcc = i2;
        }
        int i3 = configuration.mnc;
        int i4 = configuration2.mnc;
        if (i3 != i4) {
            configuration3.mnc = i4;
        }
        configuration.fixUpLocaleList();
        configuration2.fixUpLocaleList();
        if (!configuration.mLocaleList.equals(configuration2.mLocaleList)) {
            configuration3.mLocaleList = configuration2.mLocaleList;
            configuration3.locale = configuration2.locale;
        }
        int i5 = configuration.mGrammaticalGender;
        int i6 = configuration2.mGrammaticalGender;
        if (i5 != i6) {
            configuration3.mGrammaticalGender = i6;
        }
        int i7 = configuration.touchscreen;
        int i8 = configuration2.touchscreen;
        if (i7 != i8) {
            configuration3.touchscreen = i8;
        }
        int i9 = configuration.keyboard;
        int i10 = configuration2.keyboard;
        if (i9 != i10) {
            configuration3.keyboard = i10;
        }
        int i11 = configuration.keyboardHidden;
        int i12 = configuration2.keyboardHidden;
        if (i11 != i12) {
            configuration3.keyboardHidden = i12;
        }
        int i13 = configuration.navigation;
        int i14 = configuration2.navigation;
        if (i13 != i14) {
            configuration3.navigation = i14;
        }
        int i15 = configuration.navigationHidden;
        int i16 = configuration2.navigationHidden;
        if (i15 != i16) {
            configuration3.navigationHidden = i16;
        }
        int i17 = configuration.orientation;
        int i18 = configuration2.orientation;
        if (i17 != i18) {
            configuration3.orientation = i18;
        }
        int i19 = configuration.screenLayout & 15;
        int i20 = configuration2.screenLayout;
        if (i19 != (i20 & 15)) {
            configuration3.screenLayout |= i20 & 15;
        }
        int i21 = configuration.screenLayout & 192;
        int i22 = configuration2.screenLayout;
        if (i21 != (i22 & 192)) {
            configuration3.screenLayout |= i22 & 192;
        }
        int i23 = configuration.screenLayout & 48;
        int i24 = configuration2.screenLayout;
        if (i23 != (i24 & 48)) {
            configuration3.screenLayout |= i24 & 48;
        }
        int i25 = configuration.screenLayout & 768;
        int i26 = configuration2.screenLayout;
        if (i25 != (i26 & 768)) {
            configuration3.screenLayout |= i26 & 768;
        }
        int i27 = configuration.colorMode & 3;
        int i28 = configuration2.colorMode;
        if (i27 != (i28 & 3)) {
            configuration3.colorMode |= i28 & 3;
        }
        int i29 = configuration.colorMode & 12;
        int i30 = configuration2.colorMode;
        if (i29 != (i30 & 12)) {
            configuration3.colorMode |= i30 & 12;
        }
        int i31 = configuration.uiMode & 15;
        int i32 = configuration2.uiMode;
        if (i31 != (i32 & 15)) {
            configuration3.uiMode |= i32 & 15;
        }
        int i33 = configuration.uiMode & 48;
        int i34 = configuration2.uiMode;
        if (i33 != (i34 & 48)) {
            configuration3.uiMode |= i34 & 48;
        }
        int i35 = configuration.screenWidthDp;
        int i36 = configuration2.screenWidthDp;
        if (i35 != i36) {
            configuration3.screenWidthDp = i36;
        }
        int i37 = configuration.screenHeightDp;
        int i38 = configuration2.screenHeightDp;
        if (i37 != i38) {
            configuration3.screenHeightDp = i38;
        }
        int i39 = configuration.smallestScreenWidthDp;
        int i40 = configuration2.smallestScreenWidthDp;
        if (i39 != i40) {
            configuration3.smallestScreenWidthDp = i40;
        }
        int i41 = configuration.densityDpi;
        int i42 = configuration2.densityDpi;
        if (i41 != i42) {
            configuration3.densityDpi = i42;
        }
        int i43 = configuration.assetsSeq;
        int i44 = configuration2.assetsSeq;
        if (i43 != i44) {
            configuration3.assetsSeq = i44;
        }
        if (!configuration.windowConfiguration.equals(configuration2.windowConfiguration)) {
            configuration3.windowConfiguration.setTo(configuration2.windowConfiguration);
        }
        int i45 = configuration.fontWeightAdjustment;
        int i46 = configuration2.fontWeightAdjustment;
        if (i45 != i46) {
            configuration3.fontWeightAdjustment = i46;
        }
        int i47 = configuration.boldFont;
        int i48 = configuration2.boldFont;
        if (i47 != i48) {
            configuration3.boldFont = i48;
        }
        int i49 = configuration.semButtonShapeEnabled;
        int i50 = configuration2.semButtonShapeEnabled;
        if (i49 != i50) {
            configuration3.semButtonShapeEnabled = i50;
        }
        float f3 = configuration.semCursorThicknessScale;
        float f4 = configuration2.semCursorThicknessScale;
        if (f3 != f4) {
            configuration3.semCursorThicknessScale = f4;
        }
        int i51 = configuration.nightDim;
        int i52 = configuration2.nightDim;
        if (i51 != i52) {
            configuration3.nightDim = i52;
        }
        int i53 = configuration.themeSeq;
        int i54 = configuration2.themeSeq;
        if (i53 != i54) {
            configuration3.themeSeq = i54;
        }
        return configuration3;
    }

    public static void readXmlAttrs(XmlPullParser xmlPullParser, Configuration configuration) throws XmlPullParserException, IOException {
        configuration.fontScale = Float.intBitsToFloat(XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_FONT_SCALE, 0));
        configuration.mcc = XmlUtils.readIntAttribute(xmlPullParser, "mcc", 0);
        configuration.mnc = XmlUtils.readIntAttribute(xmlPullParser, "mnc", 0);
        LocaleList forLanguageTags = LocaleList.forLanguageTags(XmlUtils.readStringAttribute(xmlPullParser, XML_ATTR_LOCALES));
        configuration.mLocaleList = forLanguageTags;
        configuration.locale = forLanguageTags.get(0);
        configuration.touchscreen = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_TOUCHSCREEN, 0);
        configuration.keyboard = XmlUtils.readIntAttribute(xmlPullParser, "key", 0);
        configuration.keyboardHidden = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_KEYBOARD_HIDDEN, 0);
        configuration.hardKeyboardHidden = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_HARD_KEYBOARD_HIDDEN, 0);
        configuration.navigation = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_NAVIGATION, 0);
        configuration.navigationHidden = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_NAVIGATION_HIDDEN, 0);
        configuration.orientation = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_ORIENTATION, 0);
        configuration.screenLayout = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SCREEN_LAYOUT, 0);
        configuration.colorMode = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_COLOR_MODE, 0);
        configuration.uiMode = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_UI_MODE, 0);
        configuration.screenWidthDp = XmlUtils.readIntAttribute(xmlPullParser, "width", 0);
        configuration.screenHeightDp = XmlUtils.readIntAttribute(xmlPullParser, "height", 0);
        configuration.smallestScreenWidthDp = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SMALLEST_WIDTH, 0);
        configuration.densityDpi = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_DENSITY, 0);
        configuration.fontWeightAdjustment = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_FONT_WEIGHT_ADJUSTMENT, Integer.MAX_VALUE);
        configuration.mGrammaticalGender = XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_GRAMMATICAL_GENDER, -1);
    }

    @Deprecated
    public boolean isDexMode() {
        int i = this.dexMode;
        return i == 2 || i == 1;
    }

    @Deprecated
    public boolean isDesktopModeEnabled() {
        return this.semDesktopModeEnabled == 1;
    }

    @Deprecated
    public boolean isNewDexMode() {
        return this.dexMode == 3;
    }

    public void overrideUndefinedFrom(Configuration configuration) {
        this.windowConfiguration.overrideUndefinedFrom(configuration.windowConfiguration);
    }

    @Deprecated
    private int hidden_semDesktopModeEnabled() {
        return this.semDesktopModeEnabled;
    }
}
