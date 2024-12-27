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
    public static final int DESKTOP_MODE_UNDEFINED = -1;
    public static final int DEX_COMPAT_DISABLED = 1;
    public static final int DEX_COMPAT_ENABLED = 2;
    public static final int DEX_COMPAT_UI_CUSTOM = 2;
    public static final int DEX_COMPAT_UI_DEFAULT = 1;
    public static final int DEX_COMPAT_UI_FULLSCREEN = 3;
    public static final int DEX_COMPAT_UI_UNDEFINED = 0;
    public static final int DEX_COMPAT_UNDEFINED = 0;
    public static final int DEX_MODE_DUAL = 2;
    public static final int DEX_MODE_NEXT_GEN = 3;
    public static final int DEX_MODE_NONE = 0;
    public static final int DEX_MODE_STANDALONE = 1;
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
    public int dexCompatEnabled;
    public int dexCompatUiMode;
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
        public Configuration createFromParcel(Parcel source) {
            return new Configuration(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

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

    public static int resetScreenLayout(int curLayout) {
        return ((-268435520) & curLayout) | 36;
    }

    public static int reduceScreenLayout(int curLayout, int longSizeDp, int shortSizeDp) {
        int screenLayoutSize;
        boolean screenLayoutCompatNeeded;
        boolean screenLayoutLong;
        if (longSizeDp < 470) {
            screenLayoutSize = 1;
            screenLayoutLong = false;
            screenLayoutCompatNeeded = false;
        } else {
            if (longSizeDp >= 960 && shortSizeDp >= 720) {
                screenLayoutSize = 4;
            } else if (longSizeDp >= 640 && shortSizeDp >= 480) {
                screenLayoutSize = 3;
            } else {
                screenLayoutSize = 2;
            }
            if (shortSizeDp > 321 || longSizeDp > 570) {
                screenLayoutCompatNeeded = true;
            } else {
                screenLayoutCompatNeeded = false;
            }
            if ((longSizeDp * 3) / 5 >= shortSizeDp - 1) {
                screenLayoutLong = true;
            } else {
                screenLayoutLong = false;
            }
        }
        if (!screenLayoutLong) {
            curLayout = (curLayout & (-49)) | 16;
        }
        if (screenLayoutCompatNeeded) {
            curLayout |= 268435456;
        }
        int curSize = curLayout & 15;
        if (screenLayoutSize < curSize) {
            return (curLayout & (-16)) | screenLayoutSize;
        }
        return curLayout;
    }

    public static String configurationDiffToString(int diff) {
        ArrayList<String> list = new ArrayList<>();
        if ((diff & 1) != 0) {
            list.add("CONFIG_MCC");
        }
        if ((diff & 2) != 0) {
            list.add("CONFIG_MNC");
        }
        if ((diff & 4) != 0) {
            list.add("CONFIG_LOCALE");
        }
        if ((diff & 8) != 0) {
            list.add("CONFIG_TOUCHSCREEN");
        }
        if ((diff & 16) != 0) {
            list.add("CONFIG_KEYBOARD");
        }
        if ((diff & 32) != 0) {
            list.add("CONFIG_KEYBOARD_HIDDEN");
        }
        if ((diff & 64) != 0) {
            list.add("CONFIG_NAVIGATION");
        }
        if ((diff & 128) != 0) {
            list.add("CONFIG_ORIENTATION");
        }
        if ((diff & 256) != 0) {
            list.add("CONFIG_SCREEN_LAYOUT");
        }
        if ((diff & 16384) != 0) {
            list.add("CONFIG_COLOR_MODE");
        }
        if ((diff & 512) != 0) {
            list.add("CONFIG_UI_MODE");
        }
        if ((diff & 1024) != 0) {
            list.add("CONFIG_SCREEN_SIZE");
        }
        if ((diff & 2048) != 0) {
            list.add("CONFIG_SMALLEST_SCREEN_SIZE");
        }
        if ((diff & 4096) != 0) {
            list.add("CONFIG_DENSITY");
        }
        if ((diff & 8192) != 0) {
            list.add("CONFIG_LAYOUT_DIRECTION");
        }
        if ((1073741824 & diff) != 0) {
            list.add("CONFIG_FONT_SCALE");
        }
        if ((Integer.MIN_VALUE & diff) != 0) {
            list.add("CONFIG_ASSETS_PATHS");
        }
        if ((536870912 & diff) != 0) {
            list.add("CONFIG_WINDOW_CONFIGURATION");
        }
        if ((268435456 & diff) != 0) {
            list.add("CONFIG_AUTO_BOLD_TEXT");
        }
        if ((32768 & diff) != 0) {
            list.add("CONFIG_GRAMMATICAL_GENDER");
        }
        return "{" + TextUtils.join(", ", list) + "}";
    }

    public boolean isLayoutSizeAtLeast(int size) {
        int cur = this.screenLayout & 15;
        return cur != 0 && cur >= size;
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
        this.dexCompatUiMode = 0;
        this.dexCompatEnabled = 0;
        unset();
    }

    public Configuration(Configuration o) {
        this.windowConfiguration = new WindowConfiguration();
        this.semMobileKeyboardCovered = -1;
        this.semDesktopModeEnabled = -1;
        this.semDisplayDeviceType = -1;
        this.themeSeq = 0;
        this.dexMode = -1;
        this.dexCompatUiMode = 0;
        this.dexCompatEnabled = 0;
        setTo(o);
    }

    private void fixUpLocaleList() {
        if ((this.locale == null && !this.mLocaleList.isEmpty()) || (this.locale != null && !this.locale.equals(this.mLocaleList.get(0)))) {
            this.mLocaleList = this.locale == null ? LocaleList.getEmptyLocaleList() : new LocaleList(this.locale);
        }
    }

    public void setTo(Configuration o) {
        this.fontScale = o.fontScale;
        this.mcc = o.mcc;
        this.mnc = o.mnc;
        if (o.locale == null) {
            this.locale = null;
        } else if (!o.locale.equals(this.locale)) {
            this.locale = (Locale) o.locale.clone();
        }
        o.fixUpLocaleList();
        this.mLocaleList = o.mLocaleList;
        this.mGrammaticalGender = o.mGrammaticalGender;
        this.userSetLocale = o.userSetLocale;
        this.touchscreen = o.touchscreen;
        this.keyboard = o.keyboard;
        this.keyboardHidden = o.keyboardHidden;
        this.hardKeyboardHidden = o.hardKeyboardHidden;
        this.navigation = o.navigation;
        this.navigationHidden = o.navigationHidden;
        this.orientation = o.orientation;
        this.screenLayout = o.screenLayout;
        this.colorMode = o.colorMode;
        this.uiMode = o.uiMode;
        this.screenWidthDp = o.screenWidthDp;
        this.screenHeightDp = o.screenHeightDp;
        this.smallestScreenWidthDp = o.smallestScreenWidthDp;
        this.densityDpi = o.densityDpi;
        this.compatScreenWidthDp = o.compatScreenWidthDp;
        this.compatScreenHeightDp = o.compatScreenHeightDp;
        this.compatSmallestScreenWidthDp = o.compatSmallestScreenWidthDp;
        this.assetsSeq = o.assetsSeq;
        this.seq = o.seq;
        this.windowConfiguration.setTo(o.windowConfiguration);
        this.fontWeightAdjustment = o.fontWeightAdjustment;
        this.rilSetLocale = o.rilSetLocale;
        this.FlipFont = o.FlipFont;
        this.boldFont = o.boldFont;
        this.semButtonShapeEnabled = o.semButtonShapeEnabled;
        this.semCursorThicknessScale = o.semCursorThicknessScale;
        this.nightDim = o.nightDim;
        this.semDesktopModeEnabled = o.semDesktopModeEnabled;
        this.dexMode = o.dexMode;
        this.dexCompatEnabled = o.dexCompatEnabled;
        this.dexCompatUiMode = o.dexCompatUiMode;
        this.themeSeq = o.themeSeq;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");
        sb.append(this.fontScale);
        sb.append(" ");
        if (this.mcc != 0) {
            sb.append(this.mcc);
            sb.append("mcc");
        } else {
            sb.append("?mcc");
        }
        if (this.mnc != 65535) {
            sb.append(this.mnc);
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
        if (this.mGrammaticalGender > 0) {
            switch (this.mGrammaticalGender) {
                case 1:
                    sb.append(" neuter");
                    break;
                case 2:
                    sb.append(" feminine");
                    break;
                case 3:
                    sb.append(" masculine");
                    break;
                default:
                    sb.append(" ?grgend");
                    break;
            }
        }
        int layoutDir = this.screenLayout & 192;
        switch (layoutDir) {
            case 0:
                sb.append(" ?layoutDir");
                break;
            case 64:
                sb.append(" ldltr");
                break;
            case 128:
                sb.append(" ldrtl");
                break;
            default:
                sb.append(" layoutDir=");
                sb.append(layoutDir >> 6);
                break;
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
        switch (this.screenLayout & 15) {
            case 0:
                sb.append(" ?lsize");
                break;
            case 1:
                sb.append(" smll");
                break;
            case 2:
                sb.append(" nrml");
                break;
            case 3:
                sb.append(" lrg");
                break;
            case 4:
                sb.append(" xlrg");
                break;
            default:
                sb.append(" layoutSize=");
                sb.append(this.screenLayout & 15);
                break;
        }
        switch (this.screenLayout & 48) {
            case 0:
                sb.append(" ?long");
                break;
            case 16:
                break;
            case 32:
                sb.append(" long");
                break;
            default:
                sb.append(" layoutLong=");
                sb.append(this.screenLayout & 48);
                break;
        }
        switch (this.colorMode & 12) {
            case 0:
                sb.append(" ?ldr");
                break;
            case 4:
                break;
            case 8:
                sb.append(" hdr");
                break;
            default:
                sb.append(" dynamicRange=");
                sb.append(this.colorMode & 12);
                break;
        }
        switch (this.colorMode & 3) {
            case 0:
                sb.append(" ?wideColorGamut");
                break;
            case 1:
                break;
            case 2:
                sb.append(" widecg");
                break;
            default:
                sb.append(" wideColorGamut=");
                sb.append(this.colorMode & 3);
                break;
        }
        switch (this.orientation) {
            case 0:
                sb.append(" ?orien");
                break;
            case 1:
                sb.append(" port");
                break;
            case 2:
                sb.append(" land");
                break;
            default:
                sb.append(" orien=");
                sb.append(this.orientation);
                break;
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
        switch (this.uiMode & 48) {
            case 0:
                sb.append(" ?night");
                break;
            case 16:
                break;
            case 32:
                sb.append(" night");
                break;
            default:
                sb.append(" night=");
                sb.append(this.uiMode & 48);
                break;
        }
        switch (this.touchscreen) {
            case 0:
                sb.append(" ?touch");
                break;
            case 1:
                sb.append(" -touch");
                break;
            case 2:
                sb.append(" stylus");
                break;
            case 3:
                sb.append(" finger");
                break;
            default:
                sb.append(" touch=");
                sb.append(this.touchscreen);
                break;
        }
        switch (this.keyboard) {
            case 0:
                sb.append(" ?keyb");
                break;
            case 1:
                sb.append(" -keyb");
                break;
            case 2:
                sb.append(" qwerty");
                break;
            case 3:
                sb.append(" 12key");
                break;
            default:
                sb.append(" keys=");
                sb.append(this.keyboard);
                break;
        }
        switch (this.keyboardHidden) {
            case 0:
                sb.append("/?");
                break;
            case 1:
                sb.append("/v");
                break;
            case 2:
                sb.append("/h");
                break;
            case 3:
                sb.append("/s");
                break;
            default:
                sb.append("/");
                sb.append(this.keyboardHidden);
                break;
        }
        switch (this.hardKeyboardHidden) {
            case 0:
                sb.append("/?");
                break;
            case 1:
                sb.append("/v");
                break;
            case 2:
                sb.append("/h");
                break;
            default:
                sb.append("/");
                sb.append(this.hardKeyboardHidden);
                break;
        }
        switch (this.navigation) {
            case 0:
                sb.append(" ?nav");
                break;
            case 1:
                sb.append(" -nav");
                break;
            case 2:
                sb.append(" dpad");
                break;
            case 3:
                sb.append(" tball");
                break;
            case 4:
                sb.append(" wheel");
                break;
            default:
                sb.append(" nav=");
                sb.append(this.navigation);
                break;
        }
        switch (this.navigationHidden) {
            case 0:
                sb.append("/?");
                break;
            case 1:
                sb.append("/v");
                break;
            case 2:
                sb.append("/h");
                break;
            default:
                sb.append("/");
                sb.append(this.navigationHidden);
                break;
        }
        sb.append(" winConfig=");
        sb.append(this.windowConfiguration);
        if (this.assetsSeq != 0) {
            sb.append(" as.").append(this.assetsSeq);
        }
        if (this.seq != 0) {
            sb.append(" s.").append(this.seq);
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
        switch (this.semDesktopModeEnabled) {
            case -1:
                sb.append(" desktop/?");
                break;
            case 0:
                sb.append(" desktop/d");
                break;
            case 1:
                sb.append(" desktop/e");
                break;
        }
        switch (this.dexMode) {
            case -1:
                sb.append(" dm/?");
                break;
            case 0:
                sb.append(" dm/n");
                break;
            case 1:
                sb.append(" dm/stand");
                break;
            case 2:
                sb.append(" dm/dual");
                break;
            case 3:
                sb.append(" dm/nextgen");
                break;
        }
        switch (this.dexCompatEnabled) {
            case 0:
                sb.append(" ?dc");
                break;
            case 1:
                sb.append(" dc/d");
                break;
            case 2:
                sb.append(" dc/e");
                break;
        }
        switch (this.dexCompatUiMode) {
            case 0:
                sb.append(" ?dcui");
                break;
            case 1:
                sb.append(" dcui/d");
                break;
            case 2:
                sb.append(" dcui/c");
                break;
            case 3:
                sb.append(" dcui/f");
                break;
        }
        sb.append(" themeSeq=");
        sb.append(this.themeSeq);
        sb.append('}');
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long fieldId, boolean persisted, boolean critical) {
        long token = protoOutputStream.start(fieldId);
        if (!critical) {
            protoOutputStream.write(1108101562369L, this.fontScale);
            protoOutputStream.write(1155346202626L, this.mcc);
            protoOutputStream.write(1155346202627L, this.mnc);
            if (this.mLocaleList != null) {
                protoOutputStream.write(1138166333460L, this.mLocaleList.toLanguageTags());
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
            if (!persisted && this.windowConfiguration != null) {
                this.windowConfiguration.dumpDebug(protoOutputStream, 1146756268051L);
            }
            protoOutputStream.write(ConfigurationProto.FONT_WEIGHT_ADJUSTMENT, this.fontWeightAdjustment);
        }
        protoOutputStream.write(1155346202637L, this.orientation);
        protoOutputStream.write(ConfigurationProto.SCREEN_WIDTH_DP, this.screenWidthDp);
        protoOutputStream.write(ConfigurationProto.SCREEN_HEIGHT_DP, this.screenHeightDp);
        protoOutputStream.write(1155346202646L, this.mGrammaticalGender);
        protoOutputStream.end(token);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long fieldId) {
        dumpDebug(protoOutputStream, fieldId, false, false);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long fieldId, boolean critical) {
        dumpDebug(protoOutputStream, fieldId, false, critical);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x034d A[Catch: IllformedLocaleException -> 0x0378, all -> 0x03ab, TRY_ENTER, TryCatch #5 {all -> 0x03ab, blocks: (B:97:0x0324, B:99:0x0327, B:103:0x034d, B:105:0x03aa, B:111:0x0374, B:113:0x037d, B:148:0x023f, B:150:0x0252, B:153:0x025d, B:156:0x0277, B:161:0x02c7, B:166:0x027d), top: B:147:0x023f }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0374 A[Catch: IllformedLocaleException -> 0x0378, all -> 0x03ab, TRY_LEAVE, TryCatch #5 {all -> 0x03ab, blocks: (B:97:0x0324, B:99:0x0327, B:103:0x034d, B:105:0x03aa, B:111:0x0374, B:113:0x037d, B:148:0x023f, B:150:0x0252, B:153:0x025d, B:156:0x0277, B:161:0x02c7, B:166:0x027d), top: B:147:0x023f }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0430  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readFromProto(android.util.proto.ProtoInputStream r27, long r28) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1156
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.Configuration.readFromProto(android.util.proto.ProtoInputStream, long):void");
    }

    public void writeResConfigToProto(ProtoOutputStream protoOutputStream, long fieldId, DisplayMetrics metrics) {
        int width;
        int height;
        if (metrics.widthPixels >= metrics.heightPixels) {
            width = metrics.widthPixels;
            height = metrics.heightPixels;
        } else {
            width = metrics.heightPixels;
            height = metrics.widthPixels;
        }
        long token = protoOutputStream.start(fieldId);
        dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1155346202626L, Build.VERSION.RESOURCES_SDK_INT);
        protoOutputStream.write(1155346202627L, width);
        protoOutputStream.write(1155346202628L, height);
        protoOutputStream.end(token);
    }

    public static String uiModeToString(int uiMode) {
        switch (uiMode) {
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
                return Integer.toString(uiMode);
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
        this.semDesktopModeEnabled = -1;
        this.dexMode = -1;
        this.dexCompatEnabled = 0;
        this.dexCompatUiMode = 0;
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

    public int updateFrom(Configuration delta) {
        return updateFrom(delta, false);
    }

    public int updateFrom(Configuration delta, boolean publicOnly) {
        int changed = 0;
        if (delta.fontScale > 0.0f && this.fontScale != delta.fontScale) {
            changed = 0 | 1073741824;
            this.fontScale = delta.fontScale;
        }
        if (delta.mcc != 0 && this.mcc != delta.mcc) {
            changed |= 1;
            this.mcc = delta.mcc;
        }
        if (delta.mnc != 0 && this.mnc != delta.mnc) {
            changed |= 2;
            this.mnc = delta.mnc;
        }
        fixUpLocaleList();
        delta.fixUpLocaleList();
        if (!delta.mLocaleList.isEmpty() && !this.mLocaleList.equals(delta.mLocaleList)) {
            changed |= 4;
            this.mLocaleList = delta.mLocaleList;
            if (!delta.locale.equals(this.locale)) {
                this.locale = (Locale) delta.locale.clone();
                changed |= 8192;
                setLayoutDirection(this.locale);
            }
        }
        int deltaScreenLayoutDir = delta.screenLayout & 192;
        if (deltaScreenLayoutDir != 0 && deltaScreenLayoutDir != (this.screenLayout & 192)) {
            this.screenLayout = (this.screenLayout & (-193)) | deltaScreenLayoutDir;
            changed |= 8192;
        }
        if (delta.userSetLocale && (!this.userSetLocale || (changed & 4) != 0)) {
            changed |= 4;
            this.userSetLocale = true;
        }
        if (delta.touchscreen != 0 && this.touchscreen != delta.touchscreen) {
            changed |= 8;
            this.touchscreen = delta.touchscreen;
        }
        if (delta.keyboard != 0 && this.keyboard != delta.keyboard) {
            changed |= 16;
            this.keyboard = delta.keyboard;
        }
        if (delta.keyboardHidden != 0 && this.keyboardHidden != delta.keyboardHidden) {
            changed |= 32;
            this.keyboardHidden = delta.keyboardHidden;
        }
        if (delta.hardKeyboardHidden != 0 && this.hardKeyboardHidden != delta.hardKeyboardHidden) {
            changed |= 32;
            this.hardKeyboardHidden = delta.hardKeyboardHidden;
        }
        if (delta.navigation != 0 && this.navigation != delta.navigation) {
            changed |= 64;
            this.navigation = delta.navigation;
        }
        if (delta.navigationHidden != 0 && this.navigationHidden != delta.navigationHidden) {
            changed |= 32;
            this.navigationHidden = delta.navigationHidden;
        }
        if (delta.orientation != 0 && this.orientation != delta.orientation) {
            changed |= 128;
            this.orientation = delta.orientation;
        }
        if ((delta.screenLayout & 15) != 0 && (delta.screenLayout & 15) != (this.screenLayout & 15)) {
            changed |= 256;
            this.screenLayout = (this.screenLayout & (-16)) | (delta.screenLayout & 15);
        }
        if ((delta.screenLayout & 48) != 0 && (delta.screenLayout & 48) != (this.screenLayout & 48)) {
            changed |= 256;
            this.screenLayout = (this.screenLayout & (-49)) | (delta.screenLayout & 48);
        }
        if ((delta.screenLayout & 768) != 0 && (delta.screenLayout & 768) != (this.screenLayout & 768)) {
            changed |= 256;
            this.screenLayout = (this.screenLayout & (-769)) | (delta.screenLayout & 768);
        }
        if ((delta.screenLayout & 268435456) != (this.screenLayout & 268435456) && delta.screenLayout != 0) {
            changed |= 256;
            this.screenLayout = (this.screenLayout & (-268435457)) | (delta.screenLayout & 268435456);
        }
        if ((delta.colorMode & 3) != 0 && (delta.colorMode & 3) != (this.colorMode & 3)) {
            changed |= 16384;
            this.colorMode = (this.colorMode & (-4)) | (delta.colorMode & 3);
        }
        if ((delta.colorMode & 12) != 0 && (delta.colorMode & 12) != (this.colorMode & 12)) {
            changed |= 16384;
            this.colorMode = (this.colorMode & (-13)) | (delta.colorMode & 12);
        }
        if (delta.uiMode != 0 && this.uiMode != delta.uiMode) {
            changed |= 512;
            if ((delta.uiMode & 15) != 0) {
                this.uiMode = (this.uiMode & (-16)) | (delta.uiMode & 15);
            }
            if ((delta.uiMode & 48) != 0) {
                this.uiMode = (this.uiMode & (-49)) | (delta.uiMode & 48);
            }
        }
        if (delta.screenWidthDp != 0 && this.screenWidthDp != delta.screenWidthDp) {
            changed |= 1024;
            this.screenWidthDp = delta.screenWidthDp;
        }
        if (delta.screenHeightDp != 0 && this.screenHeightDp != delta.screenHeightDp) {
            changed |= 1024;
            this.screenHeightDp = delta.screenHeightDp;
        }
        if (delta.smallestScreenWidthDp != 0 && this.smallestScreenWidthDp != delta.smallestScreenWidthDp) {
            changed |= 2048;
            this.smallestScreenWidthDp = delta.smallestScreenWidthDp;
        }
        if (delta.densityDpi != 0 && this.densityDpi != delta.densityDpi) {
            changed |= 4096;
            this.densityDpi = delta.densityDpi;
        }
        if (delta.compatScreenWidthDp != 0) {
            this.compatScreenWidthDp = delta.compatScreenWidthDp;
        }
        if (delta.compatScreenHeightDp != 0) {
            this.compatScreenHeightDp = delta.compatScreenHeightDp;
        }
        if (delta.compatSmallestScreenWidthDp != 0) {
            this.compatSmallestScreenWidthDp = delta.compatSmallestScreenWidthDp;
        }
        if (delta.assetsSeq != 0 && delta.assetsSeq != this.assetsSeq) {
            changed |= Integer.MIN_VALUE;
            this.assetsSeq = delta.assetsSeq;
        }
        if (delta.seq != 0) {
            this.seq = delta.seq;
        }
        if (!publicOnly && this.windowConfiguration.updateFrom(delta.windowConfiguration) != 0) {
            changed |= 536870912;
        }
        if (delta.fontWeightAdjustment != Integer.MAX_VALUE && delta.fontWeightAdjustment != this.fontWeightAdjustment) {
            changed |= 268435456;
            this.fontWeightAdjustment = delta.fontWeightAdjustment;
        }
        if (delta.mGrammaticalGender != -1 && delta.mGrammaticalGender != this.mGrammaticalGender) {
            changed |= 32768;
            this.mGrammaticalGender = delta.mGrammaticalGender;
        }
        if (delta.FlipFont > 0 && this.FlipFont != delta.FlipFont) {
            changed |= 268435456;
            this.FlipFont = delta.FlipFont;
        }
        if (delta.boldFont != -1 && this.boldFont != delta.boldFont) {
            changed |= 16777216;
            this.boldFont = delta.boldFont;
        }
        if (delta.semButtonShapeEnabled != -1 && this.semButtonShapeEnabled != delta.semButtonShapeEnabled) {
            changed |= 2097152;
            this.semButtonShapeEnabled = delta.semButtonShapeEnabled;
        }
        if (delta.semCursorThicknessScale > 0.0f && this.semCursorThicknessScale != delta.semCursorThicknessScale) {
            changed |= 8388608;
            this.semCursorThicknessScale = delta.semCursorThicknessScale;
        }
        if (delta.nightDim != -1 && this.nightDim != delta.nightDim) {
            changed |= 4194304;
            this.nightDim = delta.nightDim;
        }
        if (delta.semDesktopModeEnabled != -1 && this.semDesktopModeEnabled != delta.semDesktopModeEnabled) {
            this.semDesktopModeEnabled = delta.semDesktopModeEnabled;
        }
        if (delta.dexMode != -1 && this.dexMode != delta.dexMode) {
            changed |= 1048576;
            this.dexMode = delta.dexMode;
        }
        if (delta.dexCompatEnabled != 0 && this.dexCompatEnabled != delta.dexCompatEnabled) {
            this.dexCompatEnabled = delta.dexCompatEnabled;
        }
        if (delta.dexCompatUiMode != 0 && this.dexCompatUiMode != delta.dexCompatUiMode) {
            this.dexCompatUiMode = delta.dexCompatUiMode;
        }
        if (delta.themeSeq > 0 && this.themeSeq != delta.themeSeq) {
            int changed2 = changed | 65536;
            this.themeSeq = delta.themeSeq;
            return changed2;
        }
        return changed;
    }

    public void setTo(Configuration delta, int mask, int windowMask) {
        if ((1073741824 & mask) != 0) {
            this.fontScale = delta.fontScale;
        }
        if ((mask & 1) != 0) {
            this.mcc = delta.mcc;
        }
        if ((mask & 2) != 0) {
            this.mnc = delta.mnc;
        }
        if ((mask & 4) != 0) {
            this.mLocaleList = delta.mLocaleList;
            if (!this.mLocaleList.isEmpty() && !delta.locale.equals(this.locale)) {
                this.locale = (Locale) delta.locale.clone();
            }
        }
        if ((mask & 8192) != 0) {
            int deltaScreenLayoutDir = delta.screenLayout & 192;
            this.screenLayout = (this.screenLayout & (-193)) | deltaScreenLayoutDir;
        }
        int deltaScreenLayoutDir2 = mask & 4;
        if (deltaScreenLayoutDir2 != 0) {
            this.userSetLocale = delta.userSetLocale;
        }
        if ((mask & 8) != 0) {
            this.touchscreen = delta.touchscreen;
        }
        if ((mask & 16) != 0) {
            this.keyboard = delta.keyboard;
        }
        if ((mask & 32) != 0) {
            this.keyboardHidden = delta.keyboardHidden;
            this.hardKeyboardHidden = delta.hardKeyboardHidden;
            this.navigationHidden = delta.navigationHidden;
        }
        if ((mask & 64) != 0) {
            this.navigation = delta.navigation;
        }
        if ((mask & 128) != 0) {
            this.orientation = delta.orientation;
        }
        if ((mask & 256) != 0) {
            this.screenLayout |= delta.screenLayout & (-193);
        }
        if ((mask & 16384) != 0) {
            this.colorMode = delta.colorMode;
        }
        if ((mask & 512) != 0) {
            this.uiMode = delta.uiMode;
        }
        if ((mask & 1024) != 0) {
            this.screenWidthDp = delta.screenWidthDp;
            this.screenHeightDp = delta.screenHeightDp;
        }
        if ((mask & 2048) != 0) {
            this.smallestScreenWidthDp = delta.smallestScreenWidthDp;
        }
        if ((mask & 4096) != 0) {
            this.densityDpi = delta.densityDpi;
        }
        if ((Integer.MIN_VALUE & mask) != 0) {
            this.assetsSeq = delta.assetsSeq;
        }
        if ((536870912 & mask) != 0) {
            this.windowConfiguration.setTo(delta.windowConfiguration, windowMask);
        }
        if ((mask & 268435456) != 0) {
            this.fontWeightAdjustment = delta.fontWeightAdjustment;
        }
        if ((32768 & mask) != 0) {
            this.mGrammaticalGender = delta.mGrammaticalGender;
        }
        if ((268435456 & mask) != 0) {
            this.FlipFont = delta.FlipFont;
        }
        if ((16777216 & mask) != 0) {
            this.boldFont = delta.boldFont;
        }
        if ((2097152 & mask) != 0) {
            this.semButtonShapeEnabled = delta.semButtonShapeEnabled;
        }
        if ((8388608 & mask) != 0) {
            this.semCursorThicknessScale = delta.semCursorThicknessScale;
        }
        if ((4194304 & mask) != 0) {
            this.nightDim = delta.nightDim;
        }
        if ((1048576 & mask) != 0) {
            this.dexMode = delta.dexMode;
        }
    }

    public int diff(Configuration delta) {
        return diff(delta, false, false);
    }

    public int diffPublicOnly(Configuration delta) {
        return diff(delta, false, true);
    }

    public int diff(Configuration delta, boolean compareUndefined, boolean publicOnly) {
        int changed = 0;
        if ((compareUndefined || delta.fontScale > 0.0f) && this.fontScale != delta.fontScale) {
            changed = 0 | 1073741824;
        }
        if ((compareUndefined || delta.mcc != 0) && this.mcc != delta.mcc) {
            changed |= 1;
        }
        if ((compareUndefined || delta.mnc != 0) && this.mnc != delta.mnc) {
            changed |= 2;
        }
        fixUpLocaleList();
        delta.fixUpLocaleList();
        if ((compareUndefined || !delta.mLocaleList.isEmpty()) && !this.mLocaleList.equals(delta.mLocaleList)) {
            changed = changed | 4 | 8192;
        }
        int deltaScreenLayoutDir = delta.screenLayout & 192;
        if ((compareUndefined || deltaScreenLayoutDir != 0) && deltaScreenLayoutDir != (this.screenLayout & 192)) {
            changed |= 8192;
        }
        if ((compareUndefined || delta.touchscreen != 0) && this.touchscreen != delta.touchscreen) {
            changed |= 8;
        }
        if ((compareUndefined || delta.keyboard != 0) && this.keyboard != delta.keyboard) {
            changed |= 16;
        }
        if ((compareUndefined || delta.keyboardHidden != 0) && this.keyboardHidden != delta.keyboardHidden) {
            changed |= 32;
        }
        if ((compareUndefined || delta.hardKeyboardHidden != 0) && this.hardKeyboardHidden != delta.hardKeyboardHidden) {
            changed |= 32;
        }
        if ((compareUndefined || delta.navigation != 0) && this.navigation != delta.navigation) {
            changed |= 64;
        }
        if ((compareUndefined || delta.navigationHidden != 0) && this.navigationHidden != delta.navigationHidden) {
            changed |= 32;
        }
        if ((compareUndefined || delta.orientation != 0) && this.orientation != delta.orientation) {
            changed |= 128;
        }
        if ((compareUndefined || getScreenLayoutNoDirection(delta.screenLayout) != 0) && getScreenLayoutNoDirection(this.screenLayout) != getScreenLayoutNoDirection(delta.screenLayout)) {
            changed |= 256;
        }
        if ((compareUndefined || (delta.colorMode & 12) != 0) && (this.colorMode & 12) != (delta.colorMode & 12)) {
            changed |= 16384;
        }
        if ((compareUndefined || (delta.colorMode & 3) != 0) && (this.colorMode & 3) != (delta.colorMode & 3)) {
            changed |= 16384;
        }
        if ((compareUndefined || delta.uiMode != 0) && this.uiMode != delta.uiMode) {
            changed |= 512;
        }
        if ((compareUndefined || delta.screenWidthDp != 0) && this.screenWidthDp != delta.screenWidthDp) {
            changed |= 1024;
        }
        if ((compareUndefined || delta.screenHeightDp != 0) && this.screenHeightDp != delta.screenHeightDp) {
            changed |= 1024;
        }
        if ((compareUndefined || delta.smallestScreenWidthDp != 0) && this.smallestScreenWidthDp != delta.smallestScreenWidthDp) {
            changed |= 2048;
        }
        if ((compareUndefined || delta.densityDpi != 0) && this.densityDpi != delta.densityDpi) {
            changed |= 4096;
        }
        if ((compareUndefined || delta.assetsSeq != 0) && this.assetsSeq != delta.assetsSeq) {
            changed |= Integer.MIN_VALUE;
        }
        if (!publicOnly && this.windowConfiguration.diff(delta.windowConfiguration, compareUndefined) != 0) {
            changed |= 536870912;
        }
        if ((compareUndefined || delta.fontWeightAdjustment != Integer.MAX_VALUE) && this.fontWeightAdjustment != delta.fontWeightAdjustment) {
            changed |= 268435456;
        }
        if ((compareUndefined || delta.mGrammaticalGender != -1) && this.mGrammaticalGender != delta.mGrammaticalGender) {
            changed |= 32768;
        }
        if (delta.FlipFont > 0 && this.FlipFont != delta.FlipFont) {
            changed |= 268435456;
        }
        if (delta.boldFont != -1 && this.boldFont != delta.boldFont) {
            changed |= 16777216;
        }
        if (delta.semButtonShapeEnabled != -1 && this.semButtonShapeEnabled != delta.semButtonShapeEnabled) {
            changed |= 2097152;
        }
        if (delta.semCursorThicknessScale > 0.0f && this.semCursorThicknessScale != delta.semCursorThicknessScale) {
            changed |= 8388608;
        }
        if (delta.dexMode != -1 && this.dexMode != delta.dexMode) {
            changed |= 1048576;
        }
        if (delta.nightDim != -1 && this.nightDim != delta.nightDim) {
            changed |= 4194304;
        }
        if (delta.themeSeq > 0 && this.themeSeq != delta.themeSeq) {
            return changed | 65536;
        }
        return changed;
    }

    public static boolean needNewResources(int configChanges, int interestingChanges) {
        return (configChanges & ((((Integer.MIN_VALUE | interestingChanges) | 1073741824) | 16777216) | 268435456)) != 0;
    }

    public boolean isOtherSeqNewer(Configuration other) {
        if (other == null) {
            return false;
        }
        if (other.seq == 0 || this.seq == 0) {
            return true;
        }
        int diff = other.seq - this.seq;
        if (Math.abs(diff) > 268435456) {
            return diff < 0;
        }
        int themeDiff = other.themeSeq - this.themeSeq;
        if (themeDiff > 65536) {
            return false;
        }
        return diff > 0 || themeDiff > 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
        parcel.writeInt(this.semDesktopModeEnabled);
        parcel.writeInt(this.dexMode);
        parcel.writeInt(this.dexCompatEnabled);
        parcel.writeInt(this.dexCompatUiMode);
        parcel.writeInt(this.themeSeq);
    }

    public void readFromParcel(Parcel source) {
        this.fontScale = source.readFloat();
        this.mcc = source.readInt();
        this.mnc = source.readInt();
        this.mLocaleList = (LocaleList) source.readTypedObject(LocaleList.CREATOR);
        this.locale = this.mLocaleList.get(0);
        int localeSetFrom = source.readInt();
        this.userSetLocale = (localeSetFrom & 1) != 0;
        this.rilSetLocale = (localeSetFrom & 2) != 0;
        this.userSetLocale = source.readInt() == 1;
        this.touchscreen = source.readInt();
        this.keyboard = source.readInt();
        this.keyboardHidden = source.readInt();
        this.hardKeyboardHidden = source.readInt();
        this.navigation = source.readInt();
        this.navigationHidden = source.readInt();
        this.orientation = source.readInt();
        this.screenLayout = source.readInt();
        this.colorMode = source.readInt();
        this.uiMode = source.readInt();
        this.screenWidthDp = source.readInt();
        this.screenHeightDp = source.readInt();
        this.smallestScreenWidthDp = source.readInt();
        this.densityDpi = source.readInt();
        this.compatScreenWidthDp = source.readInt();
        this.compatScreenHeightDp = source.readInt();
        this.compatSmallestScreenWidthDp = source.readInt();
        this.windowConfiguration.readFromParcel(source);
        this.assetsSeq = source.readInt();
        this.seq = source.readInt();
        this.fontWeightAdjustment = source.readInt();
        this.mGrammaticalGender = source.readInt();
        this.FlipFont = source.readInt();
        this.boldFont = source.readInt();
        this.semButtonShapeEnabled = source.readInt();
        this.semCursorThicknessScale = source.readFloat();
        this.nightDim = source.readInt();
        this.semDesktopModeEnabled = source.readInt();
        this.dexMode = source.readInt();
        this.dexCompatEnabled = source.readInt();
        this.dexCompatUiMode = source.readInt();
        this.themeSeq = source.readInt();
    }

    private Configuration(Parcel source) {
        this.windowConfiguration = new WindowConfiguration();
        this.semMobileKeyboardCovered = -1;
        this.semDesktopModeEnabled = -1;
        this.semDisplayDeviceType = -1;
        this.themeSeq = 0;
        this.dexMode = -1;
        this.dexCompatUiMode = 0;
        this.dexCompatEnabled = 0;
        readFromParcel(source);
    }

    public boolean isNightModeActive() {
        return (this.uiMode & 48) == 32;
    }

    @Override // java.lang.Comparable
    public int compareTo(Configuration that) {
        float a = this.fontScale;
        float b = that.fontScale;
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        int n = this.mcc - that.mcc;
        if (n != 0) {
            return n;
        }
        int n2 = this.mnc - that.mnc;
        if (n2 != 0) {
            return n2;
        }
        fixUpLocaleList();
        that.fixUpLocaleList();
        if (this.mLocaleList.isEmpty()) {
            if (!that.mLocaleList.isEmpty()) {
                return 1;
            }
        } else {
            if (that.mLocaleList.isEmpty()) {
                return -1;
            }
            int minSize = Math.min(this.mLocaleList.size(), that.mLocaleList.size());
            for (int i = 0; i < minSize; i++) {
                Locale thisLocale = this.mLocaleList.get(i);
                Locale thatLocale = that.mLocaleList.get(i);
                int n3 = thisLocale.getLanguage().compareTo(thatLocale.getLanguage());
                if (n3 != 0) {
                    return n3;
                }
                int n4 = thisLocale.getCountry().compareTo(thatLocale.getCountry());
                if (n4 != 0) {
                    return n4;
                }
                int n5 = thisLocale.getVariant().compareTo(thatLocale.getVariant());
                if (n5 != 0) {
                    return n5;
                }
                int n6 = thisLocale.toLanguageTag().compareTo(thatLocale.toLanguageTag());
                if (n6 != 0) {
                    return n6;
                }
            }
            int n7 = this.mLocaleList.size() - that.mLocaleList.size();
            if (n7 != 0) {
                return n7;
            }
        }
        int minSize2 = this.mGrammaticalGender;
        int n8 = minSize2 - that.mGrammaticalGender;
        if (n8 != 0) {
            return n8;
        }
        int n9 = this.touchscreen - that.touchscreen;
        if (n9 != 0) {
            return n9;
        }
        int n10 = this.keyboard - that.keyboard;
        if (n10 != 0) {
            return n10;
        }
        int n11 = this.keyboardHidden - that.keyboardHidden;
        if (n11 != 0) {
            return n11;
        }
        int n12 = this.hardKeyboardHidden - that.hardKeyboardHidden;
        if (n12 != 0) {
            return n12;
        }
        int n13 = this.navigation - that.navigation;
        if (n13 != 0) {
            return n13;
        }
        int n14 = this.navigationHidden - that.navigationHidden;
        if (n14 != 0) {
            return n14;
        }
        int n15 = this.orientation - that.orientation;
        if (n15 != 0) {
            return n15;
        }
        int n16 = this.colorMode - that.colorMode;
        if (n16 != 0) {
            return n16;
        }
        int n17 = this.screenLayout - that.screenLayout;
        if (n17 != 0) {
            return n17;
        }
        int n18 = this.uiMode - that.uiMode;
        if (n18 != 0) {
            return n18;
        }
        int n19 = this.screenWidthDp - that.screenWidthDp;
        if (n19 != 0) {
            return n19;
        }
        int n20 = this.screenHeightDp - that.screenHeightDp;
        if (n20 != 0) {
            return n20;
        }
        int n21 = this.smallestScreenWidthDp - that.smallestScreenWidthDp;
        if (n21 != 0) {
            return n21;
        }
        int n22 = this.densityDpi - that.densityDpi;
        if (n22 != 0) {
            return n22;
        }
        int n23 = this.assetsSeq - that.assetsSeq;
        if (n23 != 0) {
            return n23;
        }
        int n24 = this.windowConfiguration.compareTo(that.windowConfiguration);
        if (n24 != 0) {
            return n24;
        }
        int n25 = this.fontWeightAdjustment - that.fontWeightAdjustment;
        if (n25 != 0) {
            return n25;
        }
        float a2 = this.FlipFont;
        float b2 = that.FlipFont;
        if (a2 < b2) {
            return -1;
        }
        if (a2 > b2) {
            return 1;
        }
        float a3 = this.boldFont;
        float b3 = that.boldFont;
        if (a3 < b3) {
            return -1;
        }
        if (a3 > b3) {
            return 1;
        }
        int n26 = this.semButtonShapeEnabled - that.semButtonShapeEnabled;
        if (n26 != 0) {
            return n26;
        }
        float a4 = this.semCursorThicknessScale;
        float b4 = that.semCursorThicknessScale;
        if (a4 < b4) {
            return -1;
        }
        if (a4 > b4) {
            return 1;
        }
        float a5 = this.nightDim;
        float b5 = that.nightDim;
        if (a5 < b5) {
            return -1;
        }
        if (a5 > b5) {
            return 1;
        }
        int n27 = this.semDesktopModeEnabled - that.semDesktopModeEnabled;
        if (n27 != 0) {
            return n27;
        }
        int n28 = this.dexMode - that.dexMode;
        if (n28 != 0) {
            return n28;
        }
        int n29 = this.dexCompatEnabled - that.dexCompatEnabled;
        if (n29 != 0) {
            return n29;
        }
        int n30 = this.dexCompatUiMode - that.dexCompatUiMode;
        if (n30 != 0) {
            return n30;
        }
        int x = this.themeSeq;
        int y = that.themeSeq;
        if (x < y) {
            return -1;
        }
        if (x > y) {
            return 1;
        }
        return n30;
    }

    public boolean equals(Configuration that) {
        if (that == null) {
            return false;
        }
        if (that != this && compareTo(that) != 0) {
            return false;
        }
        return true;
    }

    public boolean equals(Object that) {
        try {
            return equals((Configuration) that);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        int result = (17 * 31) + Float.floatToIntBits(this.fontScale);
        return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((result * 31) + this.mcc) * 31) + this.mnc) * 31) + this.mLocaleList.hashCode()) * 31) + this.touchscreen) * 31) + this.keyboard) * 31) + this.keyboardHidden) * 31) + this.hardKeyboardHidden) * 31) + this.navigation) * 31) + this.navigationHidden) * 31) + this.orientation) * 31) + this.screenLayout) * 31) + this.colorMode) * 31) + this.uiMode) * 31) + this.screenWidthDp) * 31) + this.screenHeightDp) * 31) + this.smallestScreenWidthDp) * 31) + this.densityDpi) * 31) + this.assetsSeq) * 31) + this.fontWeightAdjustment) * 31) + this.mGrammaticalGender) * 31) + this.FlipFont) * 31) + this.boldFont) * 31) + this.semButtonShapeEnabled) * 31) + Float.floatToIntBits(this.semCursorThicknessScale)) * 31) + this.nightDim) * 31) + this.semDesktopModeEnabled) * 31) + this.dexMode) * 31) + this.dexCompatEnabled) * 31) + this.dexCompatUiMode) * 31) + this.themeSeq;
    }

    public int getGrammaticalGender() {
        if (this.mGrammaticalGender == -1) {
            return 0;
        }
        return this.mGrammaticalGender;
    }

    public int getGrammaticalGenderRaw() {
        return this.mGrammaticalGender;
    }

    public void setGrammaticalGender(int grammaticalGender) {
        this.mGrammaticalGender = grammaticalGender;
    }

    public LocaleList getLocales() {
        fixUpLocaleList();
        return this.mLocaleList;
    }

    public void setLocales(LocaleList locales) {
        this.mLocaleList = locales == null ? LocaleList.getEmptyLocaleList() : locales;
        this.locale = this.mLocaleList.get(0);
        setLayoutDirection(this.locale);
    }

    public void setLocale(Locale loc) {
        setLocales(loc == null ? LocaleList.getEmptyLocaleList() : new LocaleList(loc));
    }

    public void clearLocales() {
        this.mLocaleList = LocaleList.getEmptyLocaleList();
        this.locale = null;
    }

    public int getLayoutDirection() {
        return (this.screenLayout & 192) == 128 ? 1 : 0;
    }

    public void setLayoutDirection(Locale loc) {
        int layoutDirection = TextUtils.getLayoutDirectionFromLocale(loc) + 1;
        this.screenLayout = (this.screenLayout & (-193)) | (layoutDirection << 6);
    }

    private static int getScreenLayoutNoDirection(int screenLayout) {
        return screenLayout & (-193);
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

    public static String localesToResourceQualifier(LocaleList locs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < locs.size(); i++) {
            Locale loc = locs.get(i);
            int l = loc.getLanguage().length();
            if (l != 0) {
                int s = loc.getScript().length();
                int c = loc.getCountry().length();
                int v = loc.getVariant().length();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                if (l == 2 && s == 0 && ((c == 0 || c == 2) && v == 0)) {
                    sb.append(loc.getLanguage());
                    if (c == 2) {
                        sb.append("-r").append(loc.getCountry());
                    }
                } else {
                    sb.append("b+");
                    sb.append(loc.getLanguage());
                    if (s != 0) {
                        sb.append("+");
                        sb.append(loc.getScript());
                    }
                    if (c != 0) {
                        sb.append("+");
                        sb.append(loc.getCountry());
                    }
                    if (v != 0) {
                        sb.append("+");
                        sb.append(loc.getVariant());
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String resourceQualifierString(Configuration config) {
        return resourceQualifierString(config, null);
    }

    public static String resourceQualifierString(Configuration config, DisplayMetrics metrics) {
        int width;
        int height;
        ArrayList<String> parts = new ArrayList<>();
        if (config.mcc != 0) {
            parts.add("mcc" + config.mcc);
            if (config.mnc != 0) {
                parts.add("mnc" + config.mnc);
            }
        }
        if (!config.mLocaleList.isEmpty()) {
            String resourceQualifier = localesToResourceQualifier(config.mLocaleList);
            if (!resourceQualifier.isEmpty()) {
                parts.add(resourceQualifier);
            }
        }
        switch (config.mGrammaticalGender) {
            case 1:
                parts.add("neuter");
                break;
            case 2:
                parts.add("feminine");
                break;
            case 3:
                parts.add("masculine");
                break;
        }
        switch (config.screenLayout & 192) {
            case 64:
                parts.add("ldltr");
                break;
            case 128:
                parts.add("ldrtl");
                break;
        }
        if (config.smallestScreenWidthDp != 0) {
            parts.add(XML_ATTR_SMALLEST_WIDTH + config.smallestScreenWidthDp + "dp");
        }
        if (config.screenWidthDp != 0) {
            parts.add("w" + config.screenWidthDp + "dp");
        }
        if (config.screenHeightDp != 0) {
            parts.add("h" + config.screenHeightDp + "dp");
        }
        switch (config.screenLayout & 15) {
            case 1:
                parts.add("small");
                break;
            case 2:
                parts.add("normal");
                break;
            case 3:
                parts.add(Slice.HINT_LARGE);
                break;
            case 4:
                parts.add("xlarge");
                break;
        }
        switch (config.screenLayout & 48) {
            case 16:
                parts.add("notlong");
                break;
            case 32:
                parts.add(SliceItem.FORMAT_LONG);
                break;
        }
        switch (config.screenLayout & 768) {
            case 256:
                parts.add("notround");
                break;
            case 512:
                parts.add("round");
                break;
        }
        switch (config.colorMode & 3) {
            case 1:
                parts.add("nowidecg");
                break;
            case 2:
                parts.add("widecg");
                break;
        }
        switch (config.colorMode & 12) {
            case 4:
                parts.add("lowdr");
                break;
            case 8:
                parts.add("highdr");
                break;
        }
        switch (config.orientation) {
            case 1:
                parts.add("port");
                break;
            case 2:
                parts.add("land");
                break;
        }
        String uiModeTypeString = getUiModeTypeString(config.uiMode & 15);
        if (uiModeTypeString != null) {
            parts.add(uiModeTypeString);
        }
        switch (config.uiMode & 48) {
            case 16:
                parts.add("notnight");
                break;
            case 32:
                parts.add(Camera.Parameters.SCENE_MODE_NIGHT);
                break;
        }
        switch (config.densityDpi) {
            case 0:
                break;
            case 120:
                parts.add("ldpi");
                break;
            case 160:
                parts.add("mdpi");
                break;
            case 213:
                parts.add("tvdpi");
                break;
            case 240:
                parts.add("hdpi");
                break;
            case 320:
                parts.add("xhdpi");
                break;
            case 480:
                parts.add("xxhdpi");
                break;
            case 640:
                parts.add("xxxhdpi");
                break;
            case DENSITY_DPI_ANY /* 65534 */:
                parts.add("anydpi");
                break;
            case 65535:
                parts.add("nodpi");
                break;
            default:
                parts.add(config.densityDpi + "dpi");
                break;
        }
        switch (config.touchscreen) {
            case 1:
                parts.add("notouch");
                break;
            case 3:
                parts.add("finger");
                break;
        }
        switch (config.keyboardHidden) {
            case 1:
                parts.add("keysexposed");
                break;
            case 2:
                parts.add("keyshidden");
                break;
            case 3:
                parts.add("keyssoft");
                break;
        }
        switch (config.keyboard) {
            case 1:
                parts.add("nokeys");
                break;
            case 2:
                parts.add("qwerty");
                break;
            case 3:
                parts.add("12key");
                break;
        }
        switch (config.navigationHidden) {
            case 1:
                parts.add("navexposed");
                break;
            case 2:
                parts.add("navhidden");
                break;
        }
        switch (config.navigation) {
            case 1:
                parts.add("nonav");
                break;
            case 2:
                parts.add("dpad");
                break;
            case 3:
                parts.add("trackball");
                break;
            case 4:
                parts.add("wheel");
                break;
        }
        if (metrics != null) {
            if (metrics.widthPixels >= metrics.heightPixels) {
                width = metrics.widthPixels;
                height = metrics.heightPixels;
            } else {
                width = metrics.heightPixels;
                height = metrics.widthPixels;
            }
            parts.add(width + "x" + height);
        }
        parts.add("v" + Build.VERSION.RESOURCES_SDK_INT);
        return TextUtils.join(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, parts);
    }

    public static String getUiModeTypeString(int uiModeType) {
        switch (uiModeType) {
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

    public static Configuration generateDelta(Configuration base, Configuration change) {
        Configuration delta = new Configuration();
        if (base.fontScale != change.fontScale) {
            delta.fontScale = change.fontScale;
        }
        if (base.mcc != change.mcc) {
            delta.mcc = change.mcc;
        }
        if (base.mnc != change.mnc) {
            delta.mnc = change.mnc;
        }
        base.fixUpLocaleList();
        change.fixUpLocaleList();
        if (!base.mLocaleList.equals(change.mLocaleList)) {
            delta.mLocaleList = change.mLocaleList;
            delta.locale = change.locale;
        }
        if (base.mGrammaticalGender != change.mGrammaticalGender) {
            delta.mGrammaticalGender = change.mGrammaticalGender;
        }
        if (base.touchscreen != change.touchscreen) {
            delta.touchscreen = change.touchscreen;
        }
        if (base.keyboard != change.keyboard) {
            delta.keyboard = change.keyboard;
        }
        if (base.keyboardHidden != change.keyboardHidden) {
            delta.keyboardHidden = change.keyboardHidden;
        }
        if (base.navigation != change.navigation) {
            delta.navigation = change.navigation;
        }
        if (base.navigationHidden != change.navigationHidden) {
            delta.navigationHidden = change.navigationHidden;
        }
        if (base.orientation != change.orientation) {
            delta.orientation = change.orientation;
        }
        if ((base.screenLayout & 15) != (change.screenLayout & 15)) {
            delta.screenLayout |= change.screenLayout & 15;
        }
        if ((base.screenLayout & 192) != (change.screenLayout & 192)) {
            delta.screenLayout |= change.screenLayout & 192;
        }
        if ((base.screenLayout & 48) != (change.screenLayout & 48)) {
            delta.screenLayout |= change.screenLayout & 48;
        }
        if ((base.screenLayout & 768) != (change.screenLayout & 768)) {
            delta.screenLayout |= change.screenLayout & 768;
        }
        if ((base.colorMode & 3) != (change.colorMode & 3)) {
            delta.colorMode |= change.colorMode & 3;
        }
        if ((base.colorMode & 12) != (change.colorMode & 12)) {
            delta.colorMode |= change.colorMode & 12;
        }
        if ((base.uiMode & 15) != (change.uiMode & 15)) {
            delta.uiMode |= change.uiMode & 15;
        }
        if ((base.uiMode & 48) != (change.uiMode & 48)) {
            delta.uiMode |= change.uiMode & 48;
        }
        if (base.screenWidthDp != change.screenWidthDp) {
            delta.screenWidthDp = change.screenWidthDp;
        }
        if (base.screenHeightDp != change.screenHeightDp) {
            delta.screenHeightDp = change.screenHeightDp;
        }
        if (base.smallestScreenWidthDp != change.smallestScreenWidthDp) {
            delta.smallestScreenWidthDp = change.smallestScreenWidthDp;
        }
        if (base.densityDpi != change.densityDpi) {
            delta.densityDpi = change.densityDpi;
        }
        if (base.assetsSeq != change.assetsSeq) {
            delta.assetsSeq = change.assetsSeq;
        }
        if (!base.windowConfiguration.equals(change.windowConfiguration)) {
            delta.windowConfiguration.setTo(change.windowConfiguration);
        }
        if (base.fontWeightAdjustment != change.fontWeightAdjustment) {
            delta.fontWeightAdjustment = change.fontWeightAdjustment;
        }
        if (base.boldFont != change.boldFont) {
            delta.boldFont = change.boldFont;
        }
        if (base.semButtonShapeEnabled != change.semButtonShapeEnabled) {
            delta.semButtonShapeEnabled = change.semButtonShapeEnabled;
        }
        if (base.semCursorThicknessScale != change.semCursorThicknessScale) {
            delta.semCursorThicknessScale = change.semCursorThicknessScale;
        }
        if (base.nightDim != change.nightDim) {
            delta.nightDim = change.nightDim;
        }
        if (base.semDesktopModeEnabled != change.semDesktopModeEnabled) {
            delta.semDesktopModeEnabled = change.semDesktopModeEnabled;
        }
        if (base.dexMode != change.dexMode) {
            delta.dexMode = change.dexMode;
        }
        if (base.dexCompatEnabled != change.dexCompatEnabled) {
            delta.dexCompatEnabled = change.dexCompatEnabled;
        }
        if (base.dexCompatUiMode != change.dexCompatUiMode) {
            delta.dexCompatUiMode = change.dexCompatUiMode;
        }
        if (base.themeSeq != change.themeSeq) {
            delta.themeSeq = change.themeSeq;
        }
        return delta;
    }

    public static void readXmlAttrs(XmlPullParser parser, Configuration configOut) throws XmlPullParserException, IOException {
        configOut.fontScale = Float.intBitsToFloat(XmlUtils.readIntAttribute(parser, XML_ATTR_FONT_SCALE, 0));
        configOut.mcc = XmlUtils.readIntAttribute(parser, "mcc", 0);
        configOut.mnc = XmlUtils.readIntAttribute(parser, "mnc", 0);
        String localesStr = XmlUtils.readStringAttribute(parser, XML_ATTR_LOCALES);
        configOut.mLocaleList = LocaleList.forLanguageTags(localesStr);
        configOut.locale = configOut.mLocaleList.get(0);
        configOut.touchscreen = XmlUtils.readIntAttribute(parser, XML_ATTR_TOUCHSCREEN, 0);
        configOut.keyboard = XmlUtils.readIntAttribute(parser, "key", 0);
        configOut.keyboardHidden = XmlUtils.readIntAttribute(parser, XML_ATTR_KEYBOARD_HIDDEN, 0);
        configOut.hardKeyboardHidden = XmlUtils.readIntAttribute(parser, XML_ATTR_HARD_KEYBOARD_HIDDEN, 0);
        configOut.navigation = XmlUtils.readIntAttribute(parser, XML_ATTR_NAVIGATION, 0);
        configOut.navigationHidden = XmlUtils.readIntAttribute(parser, XML_ATTR_NAVIGATION_HIDDEN, 0);
        configOut.orientation = XmlUtils.readIntAttribute(parser, XML_ATTR_ORIENTATION, 0);
        configOut.screenLayout = XmlUtils.readIntAttribute(parser, XML_ATTR_SCREEN_LAYOUT, 0);
        configOut.colorMode = XmlUtils.readIntAttribute(parser, XML_ATTR_COLOR_MODE, 0);
        configOut.uiMode = XmlUtils.readIntAttribute(parser, XML_ATTR_UI_MODE, 0);
        configOut.screenWidthDp = XmlUtils.readIntAttribute(parser, "width", 0);
        configOut.screenHeightDp = XmlUtils.readIntAttribute(parser, "height", 0);
        configOut.smallestScreenWidthDp = XmlUtils.readIntAttribute(parser, XML_ATTR_SMALLEST_WIDTH, 0);
        configOut.densityDpi = XmlUtils.readIntAttribute(parser, XML_ATTR_DENSITY, 0);
        configOut.fontWeightAdjustment = XmlUtils.readIntAttribute(parser, XML_ATTR_FONT_WEIGHT_ADJUSTMENT, Integer.MAX_VALUE);
        configOut.mGrammaticalGender = XmlUtils.readIntAttribute(parser, XML_ATTR_GRAMMATICAL_GENDER, -1);
    }

    public boolean isDexMode() {
        return this.dexMode == 2 || this.dexMode == 1;
    }

    public boolean isDesktopModeEnabled() {
        return this.semDesktopModeEnabled == 1;
    }

    public boolean isNewDexMode() {
        return this.dexMode == 3;
    }

    public int updateFromDexCompatTaskConfig(Configuration delta) {
        int changed = updateFromScreenConfiguration(delta);
        if (delta.dexCompatEnabled != 0 && this.dexCompatEnabled != delta.dexCompatEnabled) {
            this.dexCompatEnabled = delta.dexCompatEnabled;
        }
        if (delta.dexCompatUiMode != 0 && this.dexCompatUiMode != delta.dexCompatUiMode) {
            this.dexCompatUiMode = delta.dexCompatUiMode;
        }
        if (this.windowConfiguration.updateFrom(delta.windowConfiguration) != 0) {
            return changed | 536870912;
        }
        return changed;
    }

    private int updateFromScreenConfiguration(Configuration delta) {
        int changed = 0;
        int screenLayoutNoDir = getScreenLayoutNoDirection(this.screenLayout);
        int deltaScreenLayoutNoDir = getScreenLayoutNoDirection(delta.screenLayout);
        if (deltaScreenLayoutNoDir != 0 && screenLayoutNoDir != deltaScreenLayoutNoDir) {
            changed = 0 | 256;
            this.screenLayout = (this.screenLayout & 192) | deltaScreenLayoutNoDir;
        }
        if (delta.orientation != 0 && this.orientation != delta.orientation) {
            changed |= 128;
            this.orientation = delta.orientation;
        }
        if (delta.screenWidthDp != 0 && this.screenWidthDp != delta.screenWidthDp) {
            changed |= 1024;
            this.screenWidthDp = delta.screenWidthDp;
        }
        if (delta.screenHeightDp != 0 && this.screenHeightDp != delta.screenHeightDp) {
            changed |= 1024;
            this.screenHeightDp = delta.screenHeightDp;
        }
        if (delta.smallestScreenWidthDp != 0 && this.smallestScreenWidthDp != delta.smallestScreenWidthDp) {
            int changed2 = changed | 2048;
            this.smallestScreenWidthDp = delta.smallestScreenWidthDp;
            return changed2;
        }
        return changed;
    }

    public void overrideUndefinedFrom(Configuration delta) {
        if (this.dexCompatEnabled == 0 && delta.dexCompatEnabled == 1) {
            this.dexCompatEnabled = delta.dexCompatEnabled;
        }
        this.windowConfiguration.overrideUndefinedFrom(delta.windowConfiguration);
    }

    private int hidden_semDesktopModeEnabled() {
        return this.semDesktopModeEnabled;
    }

    private static final int hidden_SEM_DESKTOP_MODE_ENABLED() {
        return 1;
    }
}
