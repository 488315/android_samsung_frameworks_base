package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import android.view.KeyCharacterMap;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class KeyEvent extends InputEvent implements Parcelable {
    public static final int ACTION_DOWN = 0;

    @Deprecated
    public static final int ACTION_MULTIPLE = 2;
    public static final int ACTION_UP = 1;
    static final boolean DEBUG = false;
    public static final int FLAG_CANCELED = 32;
    public static final int FLAG_CANCELED_LONG_PRESS = 256;
    public static final int FLAG_EDITOR_ACTION = 16;
    public static final int FLAG_FALLBACK = 1024;
    public static final int FLAG_FROM_KEY_CUSTOMIZATION = 268435456;
    public static final int FLAG_FROM_SYSTEM = 8;
    public static final int FLAG_FROM_WFD = 134217728;
    public static final int FLAG_IS_ACCESSIBILITY_EVENT = 2048;
    public static final int FLAG_KEEP_TOUCH_MODE = 4;
    public static final int FLAG_LONG_PRESS = 128;
    public static final int FLAG_NOT_RESET_USER_ACTIVITY_TIMEOUT = 16777216;
    public static final int FLAG_PREDISPATCH = 536870912;
    public static final int FLAG_SOFT_KEYBOARD = 2;
    public static final int FLAG_START_TRACKING = 1073741824;
    public static final int FLAG_TAINTED = Integer.MIN_VALUE;
    public static final int FLAG_TRACKING = 512;
    public static final int FLAG_VIRTUAL_HARD_KEY = 64;

    @Deprecated
    public static final int FLAG_WOKE_HERE = 1;
    public static final int KEYCODE_0 = 7;
    public static final int KEYCODE_1 = 8;
    public static final int KEYCODE_11 = 227;
    public static final int KEYCODE_12 = 228;
    public static final int KEYCODE_2 = 9;
    public static final int KEYCODE_3 = 10;
    public static final int KEYCODE_3D_MODE = 206;
    public static final int KEYCODE_3FINGER_BOTTOM = 1088;
    public static final int KEYCODE_3FINGER_LEFT = 1085;
    public static final int KEYCODE_3FINGER_RELEASE = 1089;
    public static final int KEYCODE_3FINGER_RIGHT = 1086;
    public static final int KEYCODE_3FINGER_TOP = 1087;
    public static final int KEYCODE_3G = 1036;
    public static final int KEYCODE_4 = 11;
    public static final int KEYCODE_5 = 12;
    public static final int KEYCODE_6 = 13;
    public static final int KEYCODE_7 = 14;
    public static final int KEYCODE_8 = 15;
    public static final int KEYCODE_9 = 16;
    public static final int KEYCODE_A = 29;
    public static final int KEYCODE_AI_HOT = 1104;
    public static final int KEYCODE_ALL_APPS = 284;
    public static final int KEYCODE_ALT_LEFT = 57;
    public static final int KEYCODE_ALT_RIGHT = 58;
    public static final int KEYCODE_ANC = 1080;
    public static final int KEYCODE_APOSTROPHE = 75;
    public static final int KEYCODE_APP_SHORTCUT_1 = 1090;
    public static final int KEYCODE_APP_SHORTCUT_2 = 1091;
    public static final int KEYCODE_APP_SHORTCUT_3 = 1092;
    public static final int KEYCODE_APP_SWITCH = 187;
    public static final int KEYCODE_ASSIST = 219;
    public static final int KEYCODE_AT = 77;
    public static final int KEYCODE_AVR_INPUT = 182;
    public static final int KEYCODE_AVR_POWER = 181;
    public static final int KEYCODE_B = 30;
    public static final int KEYCODE_BACK = 4;
    public static final int KEYCODE_BACKSLASH = 73;
    public static final int KEYCODE_BLUETOOTH = 1037;
    public static final int KEYCODE_BOOKMARK = 174;
    public static final int KEYCODE_BREAK = 121;
    public static final int KEYCODE_BRIGHTNESS_DOWN = 220;
    public static final int KEYCODE_BRIGHTNESS_UP = 221;
    public static final int KEYCODE_BUTTON_1 = 188;
    public static final int KEYCODE_BUTTON_10 = 197;
    public static final int KEYCODE_BUTTON_11 = 198;
    public static final int KEYCODE_BUTTON_12 = 199;
    public static final int KEYCODE_BUTTON_13 = 200;
    public static final int KEYCODE_BUTTON_14 = 201;
    public static final int KEYCODE_BUTTON_15 = 202;
    public static final int KEYCODE_BUTTON_16 = 203;
    public static final int KEYCODE_BUTTON_2 = 189;
    public static final int KEYCODE_BUTTON_3 = 190;
    public static final int KEYCODE_BUTTON_4 = 191;
    public static final int KEYCODE_BUTTON_5 = 192;
    public static final int KEYCODE_BUTTON_6 = 193;
    public static final int KEYCODE_BUTTON_7 = 194;
    public static final int KEYCODE_BUTTON_8 = 195;
    public static final int KEYCODE_BUTTON_9 = 196;
    public static final int KEYCODE_BUTTON_A = 96;
    public static final int KEYCODE_BUTTON_B = 97;
    public static final int KEYCODE_BUTTON_C = 98;
    public static final int KEYCODE_BUTTON_GAME = 1050;
    public static final int KEYCODE_BUTTON_L1 = 102;
    public static final int KEYCODE_BUTTON_L2 = 104;
    public static final int KEYCODE_BUTTON_MODE = 110;
    public static final int KEYCODE_BUTTON_R1 = 103;
    public static final int KEYCODE_BUTTON_R2 = 105;
    public static final int KEYCODE_BUTTON_SELECT = 109;
    public static final int KEYCODE_BUTTON_START = 108;
    public static final int KEYCODE_BUTTON_THUMBL = 106;
    public static final int KEYCODE_BUTTON_THUMBR = 107;
    public static final int KEYCODE_BUTTON_X = 99;
    public static final int KEYCODE_BUTTON_Y = 100;
    public static final int KEYCODE_BUTTON_Z = 101;
    public static final int KEYCODE_C = 31;
    public static final int KEYCODE_CALCULATOR = 210;
    public static final int KEYCODE_CALENDAR = 208;
    public static final int KEYCODE_CALL = 5;
    public static final int KEYCODE_CAMERA = 27;
    public static final int KEYCODE_CAPS_LOCK = 115;
    public static final int KEYCODE_CAPTIONS = 175;
    public static final int KEYCODE_CHANNEL_DOWN = 167;
    public static final int KEYCODE_CHANNEL_UP = 166;
    public static final int KEYCODE_CLEAR = 28;
    public static final int KEYCODE_CLOSE = 321;
    public static final int KEYCODE_CLOSE_CNP_FROM_IME = 1102;
    public static final int KEYCODE_COMMA = 55;
    public static final int KEYCODE_CONTACTS = 207;
    public static final int KEYCODE_COPY = 278;
    public static final int KEYCODE_CTRL_LEFT = 113;
    public static final int KEYCODE_CTRL_RIGHT = 114;
    public static final int KEYCODE_CUT = 277;
    public static final int KEYCODE_D = 32;
    public static final int KEYCODE_DEL = 67;
    public static final int KEYCODE_DEMO_APP_1 = 301;
    public static final int KEYCODE_DEMO_APP_2 = 302;
    public static final int KEYCODE_DEMO_APP_3 = 303;
    public static final int KEYCODE_DEMO_APP_4 = 304;
    public static final int KEYCODE_DEX_ON_OFF = 1084;
    public static final int KEYCODE_DICTATE = 319;
    public static final int KEYCODE_DOUBLE_TAP = 1078;
    public static final int KEYCODE_DO_NOT_DISTURB = 322;
    public static final int KEYCODE_DPAD_CENTER = 23;
    public static final int KEYCODE_DPAD_DOWN = 20;
    public static final int KEYCODE_DPAD_DOWN_LEFT = 269;
    public static final int KEYCODE_DPAD_DOWN_RIGHT = 271;
    public static final int KEYCODE_DPAD_LEFT = 21;
    public static final int KEYCODE_DPAD_RIGHT = 22;
    public static final int KEYCODE_DPAD_UP = 19;
    public static final int KEYCODE_DPAD_UP_LEFT = 268;
    public static final int KEYCODE_DPAD_UP_RIGHT = 270;
    public static final int KEYCODE_DVR = 173;
    public static final int KEYCODE_E = 33;
    public static final int KEYCODE_EISU = 212;
    public static final int KEYCODE_EMOJI_PICKER = 317;
    public static final int KEYCODE_END = 1035;
    public static final int KEYCODE_ENDCALL = 6;
    public static final int KEYCODE_ENTER = 66;
    public static final int KEYCODE_ENVELOPE = 65;
    public static final int KEYCODE_EQUALS = 70;
    public static final int KEYCODE_ESCAPE = 111;
    public static final int KEYCODE_EXPLORER = 64;
    public static final int KEYCODE_F = 34;
    public static final int KEYCODE_F1 = 131;
    public static final int KEYCODE_F10 = 140;
    public static final int KEYCODE_F11 = 141;
    public static final int KEYCODE_F12 = 142;
    public static final int KEYCODE_F13 = 326;
    public static final int KEYCODE_F14 = 327;
    public static final int KEYCODE_F15 = 328;
    public static final int KEYCODE_F16 = 329;
    public static final int KEYCODE_F17 = 330;
    public static final int KEYCODE_F18 = 331;
    public static final int KEYCODE_F19 = 332;
    public static final int KEYCODE_F2 = 132;
    public static final int KEYCODE_F20 = 333;
    public static final int KEYCODE_F21 = 334;
    public static final int KEYCODE_F22 = 335;
    public static final int KEYCODE_F23 = 336;
    public static final int KEYCODE_F24 = 337;
    public static final int KEYCODE_F3 = 133;
    public static final int KEYCODE_F4 = 134;
    public static final int KEYCODE_F5 = 135;
    public static final int KEYCODE_F6 = 136;
    public static final int KEYCODE_F7 = 137;
    public static final int KEYCODE_F8 = 138;
    public static final int KEYCODE_F9 = 139;
    public static final int KEYCODE_FEATURED_APP_1 = 297;
    public static final int KEYCODE_FEATURED_APP_2 = 298;
    public static final int KEYCODE_FEATURED_APP_3 = 299;
    public static final int KEYCODE_FEATURED_APP_4 = 300;
    public static final int KEYCODE_FN_LOCK = 1100;
    public static final int KEYCODE_FN_UNLOCK = 1101;
    public static final int KEYCODE_FOCUS = 80;
    public static final int KEYCODE_FORWARD = 125;
    public static final int KEYCODE_FORWARD_DEL = 112;
    public static final int KEYCODE_FULLSCREEN = 325;
    public static final int KEYCODE_FUNCTION = 119;
    public static final int KEYCODE_G = 35;
    public static final int KEYCODE_GRAVE = 68;
    public static final int KEYCODE_GUIDE = 172;
    public static final int KEYCODE_H = 36;
    public static final int KEYCODE_HEADSETHOOK = 79;
    public static final int KEYCODE_HELP = 259;
    public static final int KEYCODE_HENKAN = 214;
    public static final int KEYCODE_HOME = 3;
    public static final int KEYCODE_I = 37;
    public static final int KEYCODE_INFO = 165;
    public static final int KEYCODE_INSERT = 124;
    public static final int KEYCODE_J = 38;
    public static final int KEYCODE_K = 39;
    public static final int KEYCODE_KANA = 218;
    public static final int KEYCODE_KATAKANA_HIRAGANA = 215;
    public static final int KEYCODE_KEYBOARD_BACKLIGHT_DOWN = 305;
    public static final int KEYCODE_KEYBOARD_BACKLIGHT_TOGGLE = 307;
    public static final int KEYCODE_KEYBOARD_BACKLIGHT_UP = 306;
    public static final int KEYCODE_L = 40;
    public static final int KEYCODE_LANGUAGE_SWITCH = 204;
    public static final int KEYCODE_LAST_CHANNEL = 229;
    public static final int KEYCODE_LEFT_BRACKET = 71;
    public static final int KEYCODE_LOCK = 324;
    public static final int KEYCODE_M = 41;
    public static final int KEYCODE_MACRO_1 = 313;
    public static final int KEYCODE_MACRO_2 = 314;
    public static final int KEYCODE_MACRO_3 = 315;
    public static final int KEYCODE_MACRO_4 = 316;
    public static final int KEYCODE_MANNER_MODE = 205;
    public static final int KEYCODE_MEDIA_AUDIO_TRACK = 222;
    public static final int KEYCODE_MEDIA_CLOSE = 128;
    public static final int KEYCODE_MEDIA_EJECT = 129;
    public static final int KEYCODE_MEDIA_FAST_FORWARD = 90;
    public static final int KEYCODE_MEDIA_NEXT = 87;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_PLAY_PAUSE = 85;
    public static final int KEYCODE_MEDIA_PREVIOUS = 88;
    public static final int KEYCODE_MEDIA_RECORD = 130;
    public static final int KEYCODE_MEDIA_REWIND = 89;
    public static final int KEYCODE_MEDIA_SKIP_BACKWARD = 273;
    public static final int KEYCODE_MEDIA_SKIP_FORWARD = 272;
    public static final int KEYCODE_MEDIA_STEP_BACKWARD = 275;
    public static final int KEYCODE_MEDIA_STEP_FORWARD = 274;
    public static final int KEYCODE_MEDIA_STOP = 86;
    public static final int KEYCODE_MEDIA_TOP_MENU = 226;
    public static final int KEYCODE_MENU = 82;
    public static final int KEYCODE_META_LEFT = 117;
    public static final int KEYCODE_META_RIGHT = 118;
    public static final int KEYCODE_MINUS = 69;
    public static final int KEYCODE_MOUSE_ACTION = 1060;
    public static final int KEYCODE_MOUSE_APP_SHORTCUT_1 = 1093;
    public static final int KEYCODE_MOUSE_APP_SHORTCUT_2 = 1094;
    public static final int KEYCODE_MOUSE_APP_SHORTCUT_3 = 1095;
    public static final int KEYCODE_MOUSE_APP_SHORTCUT_4 = 1096;
    public static final int KEYCODE_MOVE_END = 123;
    public static final int KEYCODE_MOVE_HOME = 122;
    public static final int KEYCODE_MUHENKAN = 213;
    public static final int KEYCODE_MULTI_WINDOW = 1061;
    public static final int KEYCODE_MUSIC = 209;
    public static final int KEYCODE_MUTE = 91;
    public static final int KEYCODE_N = 42;
    public static final int KEYCODE_NAVIGATE_IN = 262;
    public static final int KEYCODE_NAVIGATE_NEXT = 261;
    public static final int KEYCODE_NAVIGATE_OUT = 263;
    public static final int KEYCODE_NAVIGATE_PREVIOUS = 260;
    public static final int KEYCODE_NETWORK_SEL = 1047;
    public static final int KEYCODE_NEW = 320;
    public static final int KEYCODE_NOTIFICATION = 83;
    public static final int KEYCODE_NUM = 78;
    public static final int KEYCODE_NUMPAD_0 = 144;
    public static final int KEYCODE_NUMPAD_1 = 145;
    public static final int KEYCODE_NUMPAD_2 = 146;
    public static final int KEYCODE_NUMPAD_3 = 147;
    public static final int KEYCODE_NUMPAD_4 = 148;
    public static final int KEYCODE_NUMPAD_5 = 149;
    public static final int KEYCODE_NUMPAD_6 = 150;
    public static final int KEYCODE_NUMPAD_7 = 151;
    public static final int KEYCODE_NUMPAD_8 = 152;
    public static final int KEYCODE_NUMPAD_9 = 153;
    public static final int KEYCODE_NUMPAD_ADD = 157;
    public static final int KEYCODE_NUMPAD_COMMA = 159;
    public static final int KEYCODE_NUMPAD_DIVIDE = 154;
    public static final int KEYCODE_NUMPAD_DOT = 158;
    public static final int KEYCODE_NUMPAD_ENTER = 160;
    public static final int KEYCODE_NUMPAD_EQUALS = 161;
    public static final int KEYCODE_NUMPAD_LEFT_PAREN = 162;
    public static final int KEYCODE_NUMPAD_MULTIPLY = 155;
    public static final int KEYCODE_NUMPAD_RIGHT_PAREN = 163;
    public static final int KEYCODE_NUMPAD_SUBTRACT = 156;
    public static final int KEYCODE_NUM_LOCK = 143;
    public static final int KEYCODE_O = 43;
    public static final int KEYCODE_P = 44;
    public static final int KEYCODE_PAGE_DOWN = 93;
    public static final int KEYCODE_PAGE_UP = 92;
    public static final int KEYCODE_PAIRING = 225;
    public static final int KEYCODE_PASTE = 279;
    public static final int KEYCODE_PERIOD = 56;
    public static final int KEYCODE_PICTSYMBOLS = 94;
    public static final int KEYCODE_PLUS = 81;
    public static final int KEYCODE_POUND = 18;
    public static final int KEYCODE_POWER = 26;
    public static final int KEYCODE_PRINT = 323;
    public static final int KEYCODE_PROFILE_SWITCH = 288;
    public static final int KEYCODE_PROG_BLUE = 186;
    public static final int KEYCODE_PROG_GREEN = 184;
    public static final int KEYCODE_PROG_RED = 183;
    public static final int KEYCODE_PROG_YELLOW = 185;
    public static final int KEYCODE_Q = 45;
    public static final int KEYCODE_QUICK_SETTINGS = 1098;
    public static final int KEYCODE_R = 46;
    public static final int KEYCODE_RECENT_APPS = 312;
    public static final int KEYCODE_REFRESH = 285;
    public static final int KEYCODE_RESET = 1083;
    public static final int KEYCODE_RIGHT_BRACKET = 72;
    public static final int KEYCODE_RO = 217;
    public static final int KEYCODE_S = 47;
    public static final int KEYCODE_SCREENSHOT = 318;
    public static final int KEYCODE_SCROLL_LOCK = 116;
    public static final int KEYCODE_SEARCH = 84;
    public static final int KEYCODE_SEMICOLON = 74;
    public static final int KEYCODE_SETTINGS = 176;
    public static final int KEYCODE_SFINDER = 1064;
    public static final int KEYCODE_SHIFT_LEFT = 59;
    public static final int KEYCODE_SHIFT_RIGHT = 60;
    public static final int KEYCODE_SIM = 1048;
    public static final int KEYCODE_SLASH = 76;
    public static final int KEYCODE_SLEEP = 223;
    public static final int KEYCODE_SOFT_LEFT = 1;
    public static final int KEYCODE_SOFT_RIGHT = 2;
    public static final int KEYCODE_SOFT_SLEEP = 276;
    public static final int KEYCODE_SPACE = 62;
    public static final int KEYCODE_STAR = 17;
    public static final int KEYCODE_STB_INPUT = 180;
    public static final int KEYCODE_STB_POWER = 179;
    public static final int KEYCODE_STEM_1 = 265;
    public static final int KEYCODE_STEM_2 = 266;
    public static final int KEYCODE_STEM_3 = 267;
    public static final int KEYCODE_STEM_PRIMARY = 264;
    public static final int KEYCODE_STYLUS_BUTTON_PRIMARY = 308;
    public static final int KEYCODE_STYLUS_BUTTON_SECONDARY = 309;
    public static final int KEYCODE_STYLUS_BUTTON_TAIL = 311;
    public static final int KEYCODE_STYLUS_BUTTON_TERTIARY = 310;
    public static final int KEYCODE_SWITCH_CHARSET = 95;
    public static final int KEYCODE_SYM = 63;
    public static final int KEYCODE_SYSRQ = 120;
    public static final int KEYCODE_SYSTEM_NAVIGATION_DOWN = 281;
    public static final int KEYCODE_SYSTEM_NAVIGATION_LEFT = 282;
    public static final int KEYCODE_SYSTEM_NAVIGATION_RIGHT = 283;
    public static final int KEYCODE_SYSTEM_NAVIGATION_UP = 280;
    public static final int KEYCODE_T = 48;
    public static final int KEYCODE_TAB = 61;
    public static final int KEYCODE_TA_CHARGING = 1077;
    public static final int KEYCODE_THUMBS_DOWN = 287;
    public static final int KEYCODE_THUMBS_UP = 286;
    public static final int KEYCODE_TOUCHPAD_OFF = 1076;
    public static final int KEYCODE_TOUCHPAD_ON = 1075;
    public static final int KEYCODE_TV = 170;
    public static final int KEYCODE_TV_ANTENNA_CABLE = 242;
    public static final int KEYCODE_TV_AUDIO_DESCRIPTION = 252;
    public static final int KEYCODE_TV_AUDIO_DESCRIPTION_MIX_DOWN = 254;
    public static final int KEYCODE_TV_AUDIO_DESCRIPTION_MIX_UP = 253;
    public static final int KEYCODE_TV_CONTENTS_MENU = 256;
    public static final int KEYCODE_TV_DATA_SERVICE = 230;
    public static final int KEYCODE_TV_INPUT = 178;
    public static final int KEYCODE_TV_INPUT_COMPONENT_1 = 249;
    public static final int KEYCODE_TV_INPUT_COMPONENT_2 = 250;
    public static final int KEYCODE_TV_INPUT_COMPOSITE_1 = 247;
    public static final int KEYCODE_TV_INPUT_COMPOSITE_2 = 248;
    public static final int KEYCODE_TV_INPUT_HDMI_1 = 243;
    public static final int KEYCODE_TV_INPUT_HDMI_2 = 244;
    public static final int KEYCODE_TV_INPUT_HDMI_3 = 245;
    public static final int KEYCODE_TV_INPUT_HDMI_4 = 246;
    public static final int KEYCODE_TV_INPUT_VGA_1 = 251;
    public static final int KEYCODE_TV_MEDIA_CONTEXT_MENU = 257;
    public static final int KEYCODE_TV_NETWORK = 241;
    public static final int KEYCODE_TV_NUMBER_ENTRY = 234;
    public static final int KEYCODE_TV_POWER = 177;
    public static final int KEYCODE_TV_RADIO_SERVICE = 232;
    public static final int KEYCODE_TV_SATELLITE = 237;
    public static final int KEYCODE_TV_SATELLITE_BS = 238;
    public static final int KEYCODE_TV_SATELLITE_CS = 239;
    public static final int KEYCODE_TV_SATELLITE_SERVICE = 240;
    public static final int KEYCODE_TV_TELETEXT = 233;
    public static final int KEYCODE_TV_TERRESTRIAL_ANALOG = 235;
    public static final int KEYCODE_TV_TERRESTRIAL_DIGITAL = 236;
    public static final int KEYCODE_TV_TIMER_PROGRAMMING = 258;
    public static final int KEYCODE_TV_ZOOM_MODE = 255;
    public static final int KEYCODE_U = 49;
    public static final int KEYCODE_UNKNOWN = 0;
    public static final int KEYCODE_V = 50;
    public static final int KEYCODE_VIDEO_APP_1 = 289;
    public static final int KEYCODE_VIDEO_APP_2 = 290;
    public static final int KEYCODE_VIDEO_APP_3 = 291;
    public static final int KEYCODE_VIDEO_APP_4 = 292;
    public static final int KEYCODE_VIDEO_APP_5 = 293;
    public static final int KEYCODE_VIDEO_APP_6 = 294;
    public static final int KEYCODE_VIDEO_APP_7 = 295;
    public static final int KEYCODE_VIDEO_APP_8 = 296;
    public static final int KEYCODE_VIEW_NOTIFICATION = 1097;
    public static final int KEYCODE_VIRTUALEYE_BUTTON = 1043;
    public static final int KEYCODE_VOICEMEMO_BUTTON = 1044;
    public static final int KEYCODE_VOICE_ASSIST = 231;
    public static final int KEYCODE_VOICE_WAKEUP = 1062;
    public static final int KEYCODE_VOICE_WAKEUP_LPSD = 1063;
    public static final int KEYCODE_VOLUME_DOWN = 25;
    public static final int KEYCODE_VOLUME_MUTE = 164;
    public static final int KEYCODE_VOLUME_UP = 24;
    public static final int KEYCODE_W = 51;
    public static final int KEYCODE_WAKEUP = 224;
    public static final int KEYCODE_WATCH = 1049;
    public static final int KEYCODE_WATCH_BUTTON_1 = 1099;
    public static final int KEYCODE_WINDOW = 171;
    public static final int KEYCODE_X = 52;
    public static final int KEYCODE_Y = 53;
    public static final int KEYCODE_YEN = 216;
    public static final int KEYCODE_Z = 54;
    public static final int KEYCODE_ZENKAKU_HANKAKU = 211;
    public static final int KEYCODE_ZOOM_IN = 168;
    public static final int KEYCODE_ZOOM_OUT = 169;
    private static final String LABEL_PREFIX = "KEYCODE_";
    public static final int LAST_KEYCODE = 337;

    @Deprecated
    public static final int MAX_KEYCODE = 84;
    private static final int MAX_RECYCLED = 10;
    private static final int META_ALL_MASK = 7827711;
    public static final int META_ALT_LEFT_ON = 16;
    public static final int META_ALT_LOCKED = 512;
    public static final int META_ALT_MASK = 50;
    public static final int META_ALT_ON = 2;
    public static final int META_ALT_RIGHT_ON = 32;
    public static final int META_CAPS_LOCK_ON = 1048576;
    public static final int META_CAP_LOCKED = 256;
    public static final int META_CTRL_LEFT_ON = 8192;
    public static final int META_CTRL_MASK = 28672;
    public static final int META_CTRL_ON = 4096;
    public static final int META_CTRL_RIGHT_ON = 16384;
    public static final int META_FUNCTION_ON = 8;
    private static final int META_INVALID_MODIFIER_MASK = 7343872;
    private static final int META_LOCK_MASK = 7340032;
    public static final int META_META_LEFT_ON = 131072;
    public static final int META_META_MASK = 458752;
    public static final int META_META_ON = 65536;
    public static final int META_META_RIGHT_ON = 262144;
    private static final int META_MODIFIER_MASK = 487679;
    public static final int META_NUM_LOCK_ON = 2097152;
    public static final int META_SCROLL_LOCK_ON = 4194304;
    public static final int META_SELECTING = 2048;
    public static final int META_SHIFT_LEFT_ON = 64;
    public static final int META_SHIFT_MASK = 193;
    public static final int META_SHIFT_ON = 1;
    public static final int META_SHIFT_RIGHT_ON = 128;
    public static final int META_SYM_LOCKED = 1024;
    public static final int META_SYM_ON = 4;
    private static final int META_SYNTHETIC_MASK = 3840;
    private static final int SAMSUNG_INPUTKEYCODE = 1000;
    public static final int SAMSUNG_LAST_KEYCODE = 1104;
    public static final int SAMSUNG_START_KEYCODE = 1000;
    public static final int SEM_FLAG_IGNORE_FAKE_FOCUS = 67108864;
    public static final int SEM_FLAG_SKIP_IME_STAGE = 33554432;
    public static final int SEM_KEYCODE_ALT_GR = 1010;
    public static final int SEM_KEYCODE_APPLICATION = 1002;
    public static final int SEM_KEYCODE_CLIPBOARD = 1009;
    public static final int SEM_KEYCODE_EMAIL = 1008;
    public static final int SEM_KEYCODE_LANG = 1005;
    public static final int SEM_KEYCODE_LAUNCH_HONEY_VOICE = 1103;
    public static final int SEM_KEYCODE_LOCK = 1011;
    public static final int SEM_KEYCODE_MINIMODE_ON_OFF = 1000;
    public static final int SEM_KEYCODE_QPANEL_ON_OFF = 1003;
    public static final int SEM_KEYCODE_QUESTION = 1014;
    public static final int SEM_KEYCODE_RECENT_APPS = 1001;
    public static final int SEM_KEYCODE_SCREEN_CAPTURE = 1004;
    public static final int SEM_KEYCODE_SIP_ON_OFF = 1006;
    public static final int SEM_KEYCODE_SPEAKER = 1012;
    public static final int SEM_KEYCODE_TEXT = 1013;
    public static final int SEM_KEYCODE_USER = 1015;
    public static final int SEM_KEYCODE_USER_TOP = 1079;
    public static final int SEM_KEYCODE_VOICE_SEARCH = 1007;
    public static final int SEM_KEYCODE_WINK = 1082;
    public static final int SEM_KEYCODE_WPS_BUTTON = 1042;
    public static final int SEM_META_ALT_GR_ON = 32768;
    static final String TAG = "KeyEvent";
    private static KeyEvent gRecyclerTop;
    private static int gRecyclerUsed;
    private int mAction;
    private String mCharacters;
    private int mDeviceId;
    private int mDisplayId;
    private long mDownTime;
    private long mEventTime;
    private int mFlags;
    private byte[] mHmac;
    private int mId;
    private int mKeyCode;
    private int mMetaState;
    private KeyEvent mNext;
    private int mRepeatCount;
    private int mScanCode;
    private int mSource;
    private static final String[] META_SYMBOLIC_NAMES = {"META_SHIFT_ON", "META_ALT_ON", "META_SYM_ON", "META_FUNCTION_ON", "META_ALT_LEFT_ON", "META_ALT_RIGHT_ON", "META_SHIFT_LEFT_ON", "META_SHIFT_RIGHT_ON", "META_CAP_LOCKED", "META_ALT_LOCKED", "META_SYM_LOCKED", "0x00000800", "META_CTRL_ON", "META_CTRL_LEFT_ON", "META_CTRL_RIGHT_ON", "0x00008000", "META_META_ON", "META_META_LEFT_ON", "META_META_RIGHT_ON", "0x00080000", "META_CAPS_LOCK_ON", "META_NUM_LOCK_ON", "META_SCROLL_LOCK_ON", "0x00800000", "0x01000000", "0x02000000", "0x04000000", "0x08000000", "0x10000000", "0x20000000", "0x40000000", "0x80000000"};
    private static final Object gRecyclerLock = new Object();
    public static final Parcelable.Creator<KeyEvent> CREATOR = new Parcelable.Creator<KeyEvent>() { // from class: android.view.KeyEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyEvent createFromParcel(Parcel parcel) {
            parcel.readInt();
            return KeyEvent.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyEvent[] newArray(int i) {
            return new KeyEvent[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @interface Action {
    }

    public interface Callback {
        boolean onKeyDown(int i, KeyEvent keyEvent);

        boolean onKeyLongPress(int i, KeyEvent keyEvent);

        boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent);

        boolean onKeyUp(int i, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Flag {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface KeyCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface MetaState {
    }

    public static int getMaxKeyCode() {
        return 337;
    }

    public static int getModifierMetaStateMask() {
        return META_MODIFIER_MASK;
    }

    public static int getSamsungLastKeyCode() {
        return 1104;
    }

    public static int getSamsungStartKeyCode() {
        return 1000;
    }

    public static final boolean isAltKey(int i) {
        return i == 57 || i == 58;
    }

    public static final boolean isConfirmKey(int i) {
        return i == 23 || i == 62 || i == 66 || i == 160;
    }

    public static final boolean isGamepadButton(int i) {
        switch (i) {
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
                return true;
            default:
                switch (i) {
                    case 188:
                    case 189:
                    case 190:
                    case 191:
                    case 192:
                    case 193:
                    case 194:
                    case 195:
                    case 196:
                    case 197:
                    case 198:
                    case 199:
                    case 200:
                    case 201:
                    case 202:
                    case 203:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static final boolean isMediaSessionKey(int i) {
        if (i == 79 || i == 130 || i == 126 || i == 127) {
            return true;
        }
        switch (i) {
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
                return true;
            default:
                return false;
        }
    }

    public static final boolean isMetaKey(int i) {
        return i == 117 || i == 118;
    }

    public static boolean isModifierKey(int i) {
        if (i == 63 || i == 78 || i == 113 || i == 114) {
            return true;
        }
        switch (i) {
            case 57:
            case 58:
            case 59:
            case 60:
                return true;
            default:
                switch (i) {
                    case 117:
                    case 118:
                    case 119:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static final boolean isSystemKey(int i) {
        if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 79 || i == 80 || i == 82 || i == 130 || i == 164 || i == 264 || i == 312 || i == 126 || i == 127) {
            return true;
        }
        switch (i) {
            case 24:
            case 25:
            case 26:
            case 27:
                return true;
            default:
                switch (i) {
                    case 84:
                    case 85:
                    case 86:
                    case 87:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                        return true;
                    default:
                        switch (i) {
                            case 220:
                            case 221:
                            case 222:
                                return true;
                            default:
                                switch (i) {
                                    case 280:
                                    case 281:
                                    case 282:
                                    case 283:
                                        return true;
                                    default:
                                        switch (i) {
                                            case 305:
                                            case 306:
                                            case 307:
                                                return true;
                                            default:
                                                return false;
                                        }
                                }
                        }
                }
        }
    }

    public static boolean isVisibleBackgroundUserAllowedKey(int i) {
        return (i == 5 || i == 6 || i == 26 || i == 83 || i == 91 || i == 164 || i == 187 || i == 219 || i == 231 || i == 312 || i == 223 || i == 224) ? false : true;
    }

    public static final boolean isWakeKey(int i) {
        if (i != 3 && i != 6 && i != 82 && i != 1049 && i != 1083 && i != 26 && i != 27 && i != 224 && i != 225) {
            switch (i) {
                case 264:
                case 265:
                case 266:
                case 267:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private static boolean keyCodeIsValid(int i) {
        return ((i >= 0 && i <= 337) || (i >= 1000 && i <= 1104)) && i != 1001;
    }

    private static native int nativeKeyCodeFromString(String str);

    private static native String nativeKeyCodeToString(int i);

    private static native int nativeNextId();

    public static int normalizeMetaState(int i) {
        if ((i & 192) != 0) {
            i |= 1;
        }
        if ((i & 48) != 0) {
            i |= 2;
        }
        if ((i & 24576) != 0) {
            i |= 4096;
        }
        if ((393216 & i) != 0) {
            i |= 65536;
        }
        if ((i & 256) != 0) {
            i |= 1048576;
        }
        if ((i & 512) != 0) {
            i |= 2;
        }
        if ((i & 1024) != 0) {
            i |= 4;
        }
        return i & META_ALL_MASK;
    }

    @Override // android.view.InputEvent
    public final void recycleIfNeededAfterDispatch() {
    }

    public static int getDeadChar(int i, int i2) {
        return KeyCharacterMap.getDeadChar(i, i2);
    }

    private KeyEvent() {
        this(0L, 0L, 0, 0, 0, 0, 0, 0, 0, 0, (String) null);
    }

    public KeyEvent(int i, int i2) {
        this(0L, 0L, i, i2, 0, 0, -1, 0, 0, 0, (String) null);
    }

    public KeyEvent(long j, long j2, int i, int i2, int i3) {
        this(j, j2, i, i2, i3, 0, -1, 0, 0, 0, (String) null);
    }

    public KeyEvent(long j, long j2, int i, int i2, int i3, int i4) {
        this(j, j2, i, i2, i3, i4, -1, 0, 0, 0, (String) null);
    }

    public KeyEvent(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6) {
        this(j, j2, i, i2, i3, i4, i5, i6, 0, 0, (String) null);
    }

    public KeyEvent(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(j, j2, i, i2, i3, i4, i5, i6, i7, 0, (String) null);
    }

    public KeyEvent(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this(j, j2, i, i2, i3, i4, i5, i6, i7, i8, (String) null);
    }

    private KeyEvent(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String str) {
        this.mDisplayId = -1;
        this.mId = nativeNextId();
        this.mDownTime = TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS);
        this.mEventTime = TimeUnit.NANOSECONDS.convert(j2, TimeUnit.MILLISECONDS);
        this.mAction = i;
        this.mKeyCode = i2;
        this.mRepeatCount = i3;
        this.mMetaState = i4;
        this.mDeviceId = i5;
        this.mScanCode = i6;
        this.mFlags = i7;
        this.mSource = i8;
        this.mCharacters = str;
    }

    public KeyEvent(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.mDisplayId = -1;
        this.mId = nativeNextId();
        this.mDownTime = j;
        this.mEventTime = j2;
        this.mAction = i;
        this.mKeyCode = i2;
        this.mRepeatCount = i3;
        this.mMetaState = i4;
        this.mDeviceId = i5;
        this.mScanCode = i6;
        this.mFlags = i7;
        this.mSource = i8;
        this.mDisplayId = i9;
    }

    public KeyEvent(long j, String str, int i, int i2) {
        this(j, j, 2, 0, 0, 0, i, 0, i2, 257, str);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KeyEvent(KeyEvent keyEvent) {
        int i = keyEvent.mId;
        long j = keyEvent.mEventTime;
        int i2 = keyEvent.mAction;
        int i3 = keyEvent.mRepeatCount;
        byte[] bArr = keyEvent.mHmac;
        this(keyEvent, i, j, i2, i3, bArr == null ? null : (byte[]) bArr.clone(), keyEvent.mCharacters);
    }

    @Deprecated
    public KeyEvent(KeyEvent keyEvent, long j, int i) {
        this(keyEvent, nativeNextId(), TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS), keyEvent.mAction, i, null, keyEvent.mCharacters);
    }

    private KeyEvent(KeyEvent keyEvent, int i, long j, int i2, int i3, byte[] bArr, String str) {
        this.mDisplayId = -1;
        this.mId = i;
        this.mDownTime = keyEvent.mDownTime;
        this.mEventTime = j;
        this.mAction = i2;
        this.mKeyCode = keyEvent.mKeyCode;
        this.mRepeatCount = i3;
        this.mMetaState = keyEvent.mMetaState;
        this.mDeviceId = keyEvent.mDeviceId;
        this.mSource = keyEvent.mSource;
        this.mDisplayId = keyEvent.mDisplayId;
        this.mHmac = bArr;
        this.mScanCode = keyEvent.mScanCode;
        this.mFlags = keyEvent.mFlags;
        this.mCharacters = str;
    }

    private static KeyEvent obtain() {
        synchronized (gRecyclerLock) {
            KeyEvent keyEvent = gRecyclerTop;
            if (keyEvent == null) {
                return new KeyEvent();
            }
            gRecyclerTop = keyEvent.mNext;
            gRecyclerUsed--;
            keyEvent.mNext = null;
            keyEvent.prepareForReuse();
            return keyEvent;
        }
    }

    private static KeyEvent obtain(int i, long j, long j2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, byte[] bArr, String str) {
        KeyEvent keyEventObtain = obtain();
        keyEventObtain.mId = i;
        keyEventObtain.mDownTime = j;
        keyEventObtain.mEventTime = j2;
        keyEventObtain.mAction = i2;
        keyEventObtain.mKeyCode = i3;
        keyEventObtain.mRepeatCount = i4;
        keyEventObtain.mMetaState = i5;
        keyEventObtain.mDeviceId = i6;
        keyEventObtain.mScanCode = i7;
        keyEventObtain.mFlags = i8;
        keyEventObtain.mSource = i9;
        keyEventObtain.mDisplayId = i10;
        keyEventObtain.mHmac = bArr;
        keyEventObtain.mCharacters = str;
        return keyEventObtain;
    }

    public static KeyEvent obtain(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str) {
        return obtain(nativeNextId(), TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS), TimeUnit.NANOSECONDS.convert(j2, TimeUnit.MILLISECONDS), i, i2, i3, i4, i5, i6, i7, i8, i9, null, str);
    }

    public static KeyEvent obtain(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String str) {
        return obtain(j, j2, i, i2, i3, i4, i5, i6, i7, i8, -1, str);
    }

    public static KeyEvent obtain(KeyEvent keyEvent) {
        KeyEvent keyEventObtain = obtain();
        keyEventObtain.mId = keyEvent.mId;
        keyEventObtain.mDownTime = keyEvent.mDownTime;
        keyEventObtain.mEventTime = keyEvent.mEventTime;
        keyEventObtain.mAction = keyEvent.mAction;
        keyEventObtain.mKeyCode = keyEvent.mKeyCode;
        keyEventObtain.mRepeatCount = keyEvent.mRepeatCount;
        keyEventObtain.mMetaState = keyEvent.mMetaState;
        keyEventObtain.mDeviceId = keyEvent.mDeviceId;
        keyEventObtain.mScanCode = keyEvent.mScanCode;
        keyEventObtain.mFlags = keyEvent.mFlags;
        keyEventObtain.mSource = keyEvent.mSource;
        keyEventObtain.mDisplayId = keyEvent.mDisplayId;
        byte[] bArr = keyEvent.mHmac;
        keyEventObtain.mHmac = bArr == null ? null : (byte[]) bArr.clone();
        keyEventObtain.mCharacters = keyEvent.mCharacters;
        return keyEventObtain;
    }

    @Override // android.view.InputEvent
    public KeyEvent copy() {
        return obtain(this);
    }

    @Override // android.view.InputEvent
    public final void recycle() {
        super.recycle();
        this.mCharacters = null;
        synchronized (gRecyclerLock) {
            int i = gRecyclerUsed;
            if (i < 10) {
                gRecyclerUsed = i + 1;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
        }
    }

    @Override // android.view.InputEvent
    public int getId() {
        return this.mId;
    }

    public static KeyEvent changeTimeRepeat(KeyEvent keyEvent, long j, int i) {
        return new KeyEvent(keyEvent, j, i);
    }

    public static KeyEvent changeTimeRepeat(KeyEvent keyEvent, long j, int i, int i2) {
        KeyEvent keyEvent2 = new KeyEvent(keyEvent);
        keyEvent2.mId = nativeNextId();
        keyEvent2.mEventTime = TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS);
        keyEvent2.mRepeatCount = i;
        keyEvent2.mFlags = i2;
        return keyEvent2;
    }

    private KeyEvent(KeyEvent keyEvent, int i) {
        this(keyEvent, nativeNextId(), keyEvent.mEventTime, i, keyEvent.mRepeatCount, null, null);
    }

    public static KeyEvent changeAction(KeyEvent keyEvent, int i) {
        return new KeyEvent(keyEvent, i);
    }

    public static KeyEvent changeFlags(KeyEvent keyEvent, int i) {
        KeyEvent keyEvent2 = new KeyEvent(keyEvent);
        keyEvent2.mId = nativeNextId();
        keyEvent2.mFlags = i;
        return keyEvent2;
    }

    @Override // android.view.InputEvent
    public final boolean isTainted() {
        return (this.mFlags & Integer.MIN_VALUE) != 0;
    }

    @Override // android.view.InputEvent
    public final void setTainted(boolean z) {
        this.mFlags = z ? this.mFlags | Integer.MIN_VALUE : this.mFlags & Integer.MAX_VALUE;
    }

    public void semSetNonResetUserActivityTimeout() {
        this.mFlags |= 16777216;
    }

    @Deprecated
    public final boolean isDown() {
        return this.mAction == 0;
    }

    public final boolean isSystem() {
        return isSystemKey(this.mKeyCode);
    }

    public final boolean isWakeKey() {
        return isWakeKey(this.mKeyCode);
    }

    @Override // android.view.InputEvent
    public final int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.view.InputEvent
    public final int getSource() {
        return this.mSource;
    }

    @Override // android.view.InputEvent
    public final void setSource(int i) {
        this.mSource = i;
    }

    @Override // android.view.InputEvent
    public final int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.view.InputEvent
    public final void setDisplayId(int i) {
        this.mDisplayId = i;
    }

    public final void semSetDisplayId(int i) {
        setDisplayId(i);
    }

    public final int getMetaState() {
        return this.mMetaState;
    }

    public final int getModifiers() {
        return normalizeMetaState(this.mMetaState) & META_MODIFIER_MASK;
    }

    public final void setFlags(int i) {
        this.mFlags = i;
    }

    public final int getFlags() {
        return this.mFlags;
    }

    public static boolean metaStateHasNoModifiers(int i) {
        return (normalizeMetaState(i) & META_MODIFIER_MASK) == 0;
    }

    public static boolean metaStateHasModifiers(int i, int i2) {
        if ((META_INVALID_MODIFIER_MASK & i2) == 0) {
            return metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(metaStateFilterDirectionalModifiers(normalizeMetaState(i) & META_MODIFIER_MASK, i2, 1, 64, 128), i2, 2, 16, 32), i2, 4096, 8192, 16384), i2, 65536, 131072, 262144) == i2;
        }
        throw new IllegalArgumentException("modifiers must not contain META_CAPS_LOCK_ON, META_NUM_LOCK_ON, META_SCROLL_LOCK_ON, META_CAP_LOCKED, META_ALT_LOCKED, META_SYM_LOCKED, or META_SELECTING");
    }

    private static int metaStateFilterDirectionalModifiers(int i, int i2, int i3, int i4, int i5) {
        int i6;
        boolean z = (i2 & i3) != 0;
        int i7 = i4 | i5;
        boolean z2 = (i2 & i7) != 0;
        if (z) {
            if (z2) {
                throw new IllegalArgumentException("modifiers must not contain " + metaStateToString(i3) + " combined with " + metaStateToString(i4) + " or " + metaStateToString(i5));
            }
            i6 = ~i7;
        } else {
            if (!z2) {
                return i;
            }
            i6 = ~i3;
        }
        return i & i6;
    }

    public final boolean hasNoModifiers() {
        return metaStateHasNoModifiers(this.mMetaState);
    }

    public final boolean hasModifiers(int i) {
        return metaStateHasModifiers(this.mMetaState, i);
    }

    public final boolean isAltPressed() {
        return (this.mMetaState & 2) != 0;
    }

    public final boolean isShiftPressed() {
        return (this.mMetaState & 1) != 0;
    }

    public final boolean isSymPressed() {
        return (this.mMetaState & 4) != 0;
    }

    public final boolean isCtrlPressed() {
        return (this.mMetaState & 4096) != 0;
    }

    public final boolean isMetaPressed() {
        return (this.mMetaState & 65536) != 0;
    }

    public final boolean isFunctionPressed() {
        return (this.mMetaState & 8) != 0;
    }

    public final void semSetShiftPressed(boolean z) {
        if (z) {
            this.mMetaState |= 1;
        } else {
            this.mMetaState &= -2;
        }
    }

    public final void semSetAltPressed(boolean z) {
        if (z) {
            this.mMetaState |= 2;
        } else {
            this.mMetaState &= -3;
        }
    }

    public final void semSetCtrlPressed(boolean z) {
        if (z) {
            this.mMetaState |= 4096;
        } else {
            this.mMetaState &= -4097;
        }
    }

    public final void semSetMetaPressed(boolean z) {
        if (z) {
            this.mMetaState |= 65536;
        } else {
            this.mMetaState &= -65537;
        }
    }

    public final boolean isCapsLockOn() {
        return (this.mMetaState & 1048576) != 0;
    }

    public final boolean isNumLockOn() {
        return (this.mMetaState & 2097152) != 0;
    }

    public final boolean isScrollLockOn() {
        return (this.mMetaState & 4194304) != 0;
    }

    public final int getAction() {
        return this.mAction;
    }

    public final boolean isCanceled() {
        return (this.mFlags & 32) != 0;
    }

    @Override // android.view.InputEvent
    public final void cancel() {
        this.mFlags |= 32;
    }

    public final void startTracking() {
        this.mFlags |= 1073741824;
    }

    public final boolean isTracking() {
        return (this.mFlags & 512) != 0;
    }

    public final boolean isLongPress() {
        return (this.mFlags & 128) != 0;
    }

    public final int getKeyCode() {
        return this.mKeyCode;
    }

    @Deprecated
    public final String getCharacters() {
        return this.mCharacters;
    }

    public final int getScanCode() {
        return this.mScanCode;
    }

    public final int getRepeatCount() {
        return this.mRepeatCount;
    }

    public final void setTime(long j, long j2) {
        this.mDownTime = TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS);
        this.mEventTime = TimeUnit.NANOSECONDS.convert(j2, TimeUnit.MILLISECONDS);
    }

    public final long getDownTime() {
        return TimeUnit.MILLISECONDS.convert(this.mDownTime, TimeUnit.NANOSECONDS);
    }

    @Override // android.view.InputEvent
    public final long getEventTime() {
        return TimeUnit.MILLISECONDS.convert(this.mEventTime, TimeUnit.NANOSECONDS);
    }

    @Override // android.view.InputEvent
    public final long getEventTimeNanos() {
        return this.mEventTime;
    }

    @Deprecated
    public final int getKeyboardDevice() {
        return this.mDeviceId;
    }

    public final KeyCharacterMap getKeyCharacterMap() {
        return KeyCharacterMap.load(this.mDeviceId);
    }

    public char getDisplayLabel() {
        return getKeyCharacterMap().getDisplayLabel(this.mKeyCode);
    }

    public int getUnicodeChar() {
        return getUnicodeChar(this.mMetaState);
    }

    public int getUnicodeChar(int i) {
        return getKeyCharacterMap().get(this.mKeyCode, i);
    }

    @Deprecated
    public boolean getKeyData(KeyCharacterMap.KeyData keyData) {
        return getKeyCharacterMap().getKeyData(this.mKeyCode, keyData);
    }

    public char getMatch(char[] cArr) {
        return getMatch(cArr, 0);
    }

    public char getMatch(char[] cArr, int i) {
        return getKeyCharacterMap().getMatch(this.mKeyCode, cArr, i);
    }

    public char getNumber() {
        return getKeyCharacterMap().getNumber(this.mKeyCode);
    }

    public boolean isPrintingKey() {
        return getKeyCharacterMap().isPrintingKey(this.mKeyCode);
    }

    @Deprecated
    public final boolean dispatch(Callback callback) {
        return dispatch(callback, null, null);
    }

    public final boolean dispatch(Callback callback, DispatcherState dispatcherState, Object obj) {
        int i = this.mAction;
        if (i == 0) {
            this.mFlags &= -1073741825;
            boolean zOnKeyDown = callback.onKeyDown(this.mKeyCode, this);
            if (dispatcherState != null) {
                if (zOnKeyDown && this.mRepeatCount == 0 && (this.mFlags & 1073741824) != 0) {
                    dispatcherState.startTracking(this, obj);
                } else if (isLongPress() && dispatcherState.isTracking(this)) {
                    try {
                        if (callback.onKeyLongPress(this.mKeyCode, this)) {
                            dispatcherState.performedLongPress(this);
                            return true;
                        }
                    } catch (AbstractMethodError unused) {
                    }
                }
            }
            return zOnKeyDown;
        }
        if (i == 1) {
            if (dispatcherState != null) {
                dispatcherState.handleUpEvent(this);
            }
            return callback.onKeyUp(this.mKeyCode, this);
        }
        boolean zOnKeyDown2 = false;
        if (i != 2) {
            return false;
        }
        int i2 = this.mRepeatCount;
        int i3 = this.mKeyCode;
        if (callback.onKeyMultiple(i3, i2, this)) {
            return true;
        }
        if (i3 != 0) {
            this.mAction = 0;
            this.mRepeatCount = 0;
            zOnKeyDown2 = callback.onKeyDown(i3, this);
            if (zOnKeyDown2) {
                this.mAction = 1;
                callback.onKeyUp(i3, this);
            }
            this.mAction = 2;
            this.mRepeatCount = i2;
        }
        return zOnKeyDown2;
    }

    public static class DispatcherState {
        SparseIntArray mActiveLongPresses = new SparseIntArray();
        int mDownKeyCode;
        Object mDownTarget;

        public void reset() {
            this.mDownKeyCode = 0;
            this.mDownTarget = null;
            this.mActiveLongPresses.clear();
        }

        public void reset(Object obj) {
            if (this.mDownTarget == obj) {
                this.mDownKeyCode = 0;
                this.mDownTarget = null;
            }
        }

        public void startTracking(KeyEvent keyEvent, Object obj) {
            if (keyEvent.getAction() != 0) {
                throw new IllegalArgumentException("Can only start tracking on a down event");
            }
            this.mDownKeyCode = keyEvent.getKeyCode();
            this.mDownTarget = obj;
        }

        public boolean isTracking(KeyEvent keyEvent) {
            return this.mDownKeyCode == keyEvent.getKeyCode();
        }

        public void performedLongPress(KeyEvent keyEvent) {
            this.mActiveLongPresses.put(keyEvent.getKeyCode(), 1);
        }

        public void handleUpEvent(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            int iIndexOfKey = this.mActiveLongPresses.indexOfKey(keyCode);
            if (iIndexOfKey >= 0) {
                keyEvent.mFlags |= 288;
                this.mActiveLongPresses.removeAt(iIndexOfKey);
            }
            if (this.mDownKeyCode == keyCode) {
                keyEvent.mFlags |= 512;
                this.mDownKeyCode = 0;
                this.mDownTarget = null;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("KeyEvent { action=");
        sb.append(actionToString(this.mAction));
        sb.append(", keyCode=");
        sb.append(keyCodeToString(this.mKeyCode));
        sb.append(", scanCode=");
        sb.append(this.mScanCode);
        if (this.mCharacters != null) {
            sb.append(", characters=\"");
            sb.append(this.mCharacters);
            sb.append("\"");
        }
        sb.append(", metaState=");
        sb.append(metaStateToString(this.mMetaState));
        sb.append(", flags=0x");
        sb.append(Integer.toHexString(this.mFlags));
        sb.append(", repeatCount=");
        sb.append(this.mRepeatCount);
        sb.append(", eventTime=");
        sb.append(this.mEventTime);
        sb.append(", downTime=");
        sb.append(this.mDownTime);
        sb.append(", deviceId=");
        sb.append(this.mDeviceId);
        sb.append(", source=0x");
        sb.append(Integer.toHexString(this.mSource));
        sb.append(", displayId=");
        sb.append(this.mDisplayId);
        sb.append(" }");
        return sb.toString();
    }

    public static String actionToString(int i) {
        if (i == 0) {
            return "ACTION_DOWN";
        }
        if (i == 1) {
            return "ACTION_UP";
        }
        if (i == 2) {
            return "ACTION_MULTIPLE";
        }
        return Integer.toString(i);
    }

    public static String keyCodeToString(int i) {
        String strNativeKeyCodeToString = nativeKeyCodeToString(i);
        if (strNativeKeyCodeToString == null) {
            return Integer.toString(i);
        }
        return LABEL_PREFIX + strNativeKeyCodeToString;
    }

    public static int keyCodeFromString(String str) throws NumberFormatException {
        try {
            int i = Integer.parseInt(str);
            if (keyCodeIsValid(i)) {
                return i;
            }
        } catch (NumberFormatException unused) {
        }
        if (str.startsWith(LABEL_PREFIX)) {
            str = str.substring(8);
        }
        int iNativeKeyCodeFromString = nativeKeyCodeFromString(str);
        if (keyCodeIsValid(iNativeKeyCodeFromString)) {
            return iNativeKeyCodeFromString;
        }
        return 0;
    }

    public static String metaStateToString(int i) {
        if (i == 0) {
            return "0";
        }
        StringBuilder sb = null;
        int i2 = 0;
        while (i != 0) {
            boolean z = (i & 1) != 0;
            i >>>= 1;
            if (z) {
                String str = META_SYMBOLIC_NAMES[i2];
                if (sb != null) {
                    sb.append('|');
                    sb.append(str);
                } else {
                    if (i == 0) {
                        return str;
                    }
                    sb = new StringBuilder(str);
                }
            }
            i2++;
        }
        return sb.toString();
    }

    public static KeyEvent createFromParcelBody(Parcel parcel) {
        return new KeyEvent(parcel);
    }

    private KeyEvent(Parcel parcel) {
        this.mDisplayId = -1;
        this.mId = parcel.readInt();
        this.mDeviceId = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mDisplayId = parcel.readInt();
        this.mHmac = parcel.createByteArray();
        this.mAction = parcel.readInt();
        this.mKeyCode = parcel.readInt();
        this.mRepeatCount = parcel.readInt();
        this.mMetaState = parcel.readInt();
        this.mScanCode = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mDownTime = parcel.readLong();
        this.mEventTime = parcel.readLong();
        this.mCharacters = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(2);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mDeviceId);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mDisplayId);
        parcel.writeByteArray(this.mHmac);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mKeyCode);
        parcel.writeInt(this.mRepeatCount);
        parcel.writeInt(this.mMetaState);
        parcel.writeInt(this.mScanCode);
        parcel.writeInt(this.mFlags);
        parcel.writeLong(this.mDownTime);
        parcel.writeLong(this.mEventTime);
        parcel.writeString(this.mCharacters);
    }
}
