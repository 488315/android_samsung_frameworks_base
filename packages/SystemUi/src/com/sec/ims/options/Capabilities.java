package com.sec.ims.options;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SemSystemProperties;
import android.util.Log;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.ImsUri;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Capabilities implements Parcelable, Cloneable {
    public static final Parcelable.Creator<Capabilities> CREATOR;
    public static final String FEATURE_TAG_CHATBOT_CHAT_SESSION = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot\"";

    @Deprecated
    public static final String FEATURE_TAG_CHATBOT_COMMUNICATION = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot\"";
    public static final String FEATURE_TAG_CHATBOT_ROLE = "+g.gsma.rcs.isbot";
    public static final String FEATURE_TAG_CHATBOT_STANDALONE_MSG = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot.sa\"";
    public static final String FEATURE_TAG_ENRICHED_CALL_COMPOSER = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.callcomposer\"";
    public static final String FEATURE_TAG_ENRICHED_POST_CALL = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.callunanswered\"";
    public static final String FEATURE_TAG_ENRICHED_SHARED_MAP = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.sharedmap\"";
    public static final String FEATURE_TAG_ENRICHED_SHARED_SKETCH = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.sharedsketch\"";
    public static final String FEATURE_TAG_EXTENDED_BOT_MSG = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.xbotmessage\"";
    public static final String FEATURE_TAG_FT_VIA_SMS = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftsms\"";
    public static final String FEATURE_TAG_GEO_VIA_SMS = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geosms\"";
    public static final String FEATURE_TAG_PLUG_IN = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.plugin\"";
    private static final String LOG_TAG = "CapexInfo";
    private long mAvailableFeatures;
    private String mBotServiceId;
    private String mContactId;
    private String mDisplayName;
    private final Object mExtFeatureLock;
    private List<String> mExtFeatures;
    private long mFeatures;
    private long mId;
    private boolean mIsAvailable;
    private boolean mIsExpired;
    private boolean mIsLegacyLatching;
    private long mLastSeen;
    private String mNumber;
    private final Object mPAssertedIdLock;
    private List<ImsUri> mPAssertedIdSet;
    private int mPhoneId;
    private String mPidf;
    private boolean mSupportPresence;
    private Date mTimestamp;
    private ImsUri mUri;
    private static final boolean SHIP_BUILD = "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
    public static int FEATURE_NON_RCS_USER = 16777216;
    public static int FEATURE_NOT_UPDATED = QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING;
    public static int FEATURE_OFFLINE_RCS_USER = 0;
    public static int FEATURE_ISH = 1;
    public static int FEATURE_VSH = 2;
    public static int FEATURE_CHAT_CPM = 4;
    public static int FEATURE_SF_GROUP_CHAT = 8;
    public static int FEATURE_FT = 16;
    public static int FEATURE_FT_THUMBNAIL = 32;
    public static int FEATURE_FT_STORE = 64;
    public static int FEATURE_FT_HTTP = 128;
    public static int FEATURE_STANDALONE_MSG = 256;
    public static int FEATURE_VSH_OUTSIDE_CALL = 512;
    public static int FEATURE_SOCIAL_PRESENCE = 1024;
    public static int FEATURE_PRESENCE_DISCOVERY = 2048;
    public static int FEATURE_MMTEL = 4096;
    public static int FEATURE_MMTEL_VIDEO = 8192;
    public static int FEATURE_IPCALL = 16384;
    public static int FEATURE_IPCALL_VIDEO = 32768;
    public static int FEATURE_IPCALL_VIDEO_ONLY = 65536;
    public static int FEATURE_GEOLOCATION_PULL = 131072;
    public static int FEATURE_GEOLOCATION_PULL_FT = 262144;
    public static int FEATURE_GEOLOCATION_PUSH = 524288;
    public static int FEATURE_INTEGRATED_MSG = QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING;
    public static int FEATURE_CHAT_SIMPLE_IM = QuickStepContract.SYSUI_STATE_DEVICE_DOZING;
    public static int FEATURE_FT_VIA_SMS = QuickStepContract.SYSUI_STATE_BACK_DISABLED;
    public static int FEATURE_GEO_VIA_SMS = QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED;
    public static int FEATURE_STICKER = 134217728;
    public static int FEATURE_FT_HTTP_EXTRA = QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE;
    public static int FEATURE_STANDALONE_MSG_V1 = QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT;
    public static int FEATURE_FT_THUMBNAIL_V1 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;

    @Deprecated
    public static long FEATURE_VEMOTICON = SemWallpaperColorsWrapper.LOCKSCREEN_STATUS_BAR;

    @Deprecated
    public static long FEATURE_CARD_MSG = SemWallpaperColorsWrapper.LOCKSCREEN_LOCK_ICON;

    @Deprecated
    public static long FEATURE_BURN_MSG = SemWallpaperColorsWrapper.LOCKSCREEN_CLOCK;

    @Deprecated
    public static long FEATURE_CLOUD_FILE = SemWallpaperColorsWrapper.LOCKSCREEN_NIO;
    public static long FEATURE_ENRICHED_CALL_COMPOSER = SemWallpaperColorsWrapper.LOCKSCREEN_NIO_TEXT;
    public static long FEATURE_ENRICHED_SHARED_MAP = SemWallpaperColorsWrapper.LOCKSCREEN_MUSIC;
    public static long FEATURE_ENRICHED_SHARED_SKETCH = SemWallpaperColorsWrapper.LOCKSCREEN_HELP_TEXT;
    public static long FEATURE_ENRICHED_POST_CALL = SemWallpaperColorsWrapper.LOCKSCREEN_SHORTCUT;

    @Deprecated
    public static long FEATURE_CHATBOT_COMMUNICATION = SemWallpaperColorsWrapper.LOCKSCREEN_NAVI_BAR;
    public static long FEATURE_CHATBOT_CHAT_SESSION = SemWallpaperColorsWrapper.LOCKSCREEN_NAVI_BAR;
    public static long FEATURE_CHATBOT_ROLE = SemWallpaperColorsWrapper.LOCKSCREEN_SECURE_TEXT;
    public static long FEATURE_PLUG_IN = SemWallpaperColorsWrapper.LOCKSCREEN_FINGERPRINT;
    public static long FEATURE_PUBLIC_MSG = SemWallpaperColorsWrapper.LOCKSCREEN_BOUNCER;
    public static long FEATURE_LAST_SEEN_ACTIVE = 17592186044416L;
    public static long FEATURE_CHATBOT_STANDALONE_MSG = 35184372088832L;
    public static long FEATURE_CHATBOT_EXTENDED_MSG = 70368744177664L;
    public static long FEATURE_MMTEL_CALL_COMPOSER = 140737488355328L;
    public static long FEATURE_CANCEL_MESSAGE = 281474976710656L;
    public static long FEATURE_IM_SERVICE = SemWallpaperColorsWrapper.LOCKSCREEN_NAVI_BAR | (4 | QuickStepContract.SYSUI_STATE_DEVICE_DOZING);
    public static int FEATURE_FT_SERVICE = 16 | 64;
    public static long FEATURE_CALL_SERVICE = (1 | 2) | SemWallpaperColorsWrapper.LOCKSCREEN_HELP_TEXT;
    public static long FEATURE_ALL = 1152921504606846975L;
    public static String FEATURE_TAG_ISH = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.gsma-is\"";
    public static String FEATURE_TAG_VSH = "+g.3gpp.cs-voice";
    public static String FEATURE_TAG_STANDALONE_MSG = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.msg,urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.largemsg\"";
    public static String FEATURE_TAG_CHAT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.im\"";
    public static String FEATURE_TAG_SF_GROUP_CHAT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fullsfgroupchat\"";
    public static String FEATURE_TAG_FT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.ft\"";
    public static String FEATURE_TAG_FT_THUMBNAIL = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftthumb\"";
    public static String FEATURE_TAG_FT_STORE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftstandfw\"";
    public static String FEATURE_TAG_FT_HTTP = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fthttp\"";
    public static String FEATURE_TAG_VSH_OUTSIDE_CALL = "+g.3gpp.iari-ref=\"urn:urn-7:3gpp-application.ims.iari.gsma-vs\"";
    public static String FEATURE_TAG_SOCIAL_PRESENCE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.sp\"";
    public static String FEATURE_TAG_PRESENCE_DISCOVERY = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.dp\"";
    public static String FEATURE_TAG_MMTEL = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\"";
    public static String FEATURE_TAG_MMTEL_VIDEO = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\";video";
    public static String FEATURE_TAG_MMTEL_CALL_COMPOSER = "+g.gsma.callcomposer";
    public static String FEATURE_TAG_IPCALL = "+g.gsma.rcs.ipcall";
    public static String FEATURE_TAG_IPCALL_VIDEO = "+g.gsma.rcs.ipcall;video";
    public static String FEATURE_TAG_IPCALL_VIDEO_ONLY = "+g.gsma.rcs.ipvideocallonly";
    public static String FEATURE_TAG_GEOLOCATION_PULL = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopull\"";
    public static String FEATURE_TAG_GEOLOCATION_PULL_FT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopullft\"";
    public static String FEATURE_TAG_GEOLOCATION_PUSH = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopush\"";
    public static String FEATURE_TAG_INTEGRATED_MSG = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.joyn.intmsg\"";
    public static String FEATURE_TAG_NULL = "null";
    public static String FEATURE_TAG_STICKER = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.sticker\"";
    public static String FEATURE_TAG_FT_HTTP_EXTRA = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fthttpextra\"";
    public static String FEATURE_TAG_CANCEL_MESSAGE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.cancelmessage\"";
    public static String FEATURE_TAG_PUBLIC_MSG = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs .mnc000.mcc460.publicmsg\"";
    public static String FEATURE_TAG_NOT_UPDATED = "not_updated";
    public static String FEATURE_TAG_LAST_SEEN_ACTIVE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.lastseenactive\"";
    private static Map<Long, String> sFeatureTags = new HashMap();
    private static Map<String, Long> sTagFeatures = new HashMap();
    private static Map<Long, String> sFeatures = new HashMap();

    static {
        sFeatureTags.put(Long.valueOf(FEATURE_ISH), FEATURE_TAG_ISH);
        sFeatureTags.put(Long.valueOf(FEATURE_VSH), FEATURE_TAG_VSH);
        sFeatureTags.put(Long.valueOf(FEATURE_CHAT_CPM), FEATURE_TAG_CHAT);
        sFeatureTags.put(Long.valueOf(FEATURE_SF_GROUP_CHAT), FEATURE_TAG_SF_GROUP_CHAT);
        sFeatureTags.put(Long.valueOf(FEATURE_FT), FEATURE_TAG_FT);
        sFeatureTags.put(Long.valueOf(FEATURE_FT_THUMBNAIL), FEATURE_TAG_FT_THUMBNAIL);
        sFeatureTags.put(Long.valueOf(FEATURE_FT_STORE), FEATURE_TAG_FT_STORE);
        sFeatureTags.put(Long.valueOf(FEATURE_FT_HTTP), FEATURE_TAG_FT_HTTP);
        sFeatureTags.put(Long.valueOf(FEATURE_STANDALONE_MSG), FEATURE_TAG_STANDALONE_MSG);
        sFeatureTags.put(Long.valueOf(FEATURE_STANDALONE_MSG_V1), FEATURE_TAG_STANDALONE_MSG);
        sFeatureTags.put(Long.valueOf(FEATURE_VSH_OUTSIDE_CALL), FEATURE_TAG_VSH_OUTSIDE_CALL);
        sFeatureTags.put(Long.valueOf(FEATURE_SOCIAL_PRESENCE), FEATURE_TAG_SOCIAL_PRESENCE);
        sFeatureTags.put(Long.valueOf(FEATURE_PRESENCE_DISCOVERY), FEATURE_TAG_PRESENCE_DISCOVERY);
        sFeatureTags.put(Long.valueOf(FEATURE_MMTEL), FEATURE_TAG_MMTEL);
        sFeatureTags.put(Long.valueOf(FEATURE_MMTEL_VIDEO), FEATURE_TAG_MMTEL_VIDEO);
        sFeatureTags.put(Long.valueOf(FEATURE_MMTEL_CALL_COMPOSER), FEATURE_TAG_MMTEL_CALL_COMPOSER);
        sFeatureTags.put(Long.valueOf(FEATURE_IPCALL), FEATURE_TAG_IPCALL);
        sFeatureTags.put(Long.valueOf(FEATURE_IPCALL_VIDEO), FEATURE_TAG_IPCALL_VIDEO);
        sFeatureTags.put(Long.valueOf(FEATURE_IPCALL_VIDEO_ONLY), FEATURE_TAG_IPCALL_VIDEO_ONLY);
        sFeatureTags.put(Long.valueOf(FEATURE_GEOLOCATION_PULL), FEATURE_TAG_GEOLOCATION_PULL);
        sFeatureTags.put(Long.valueOf(FEATURE_GEOLOCATION_PULL_FT), FEATURE_TAG_GEOLOCATION_PULL_FT);
        sFeatureTags.put(Long.valueOf(FEATURE_GEOLOCATION_PUSH), FEATURE_TAG_GEOLOCATION_PUSH);
        sFeatureTags.put(Long.valueOf(FEATURE_INTEGRATED_MSG), FEATURE_TAG_INTEGRATED_MSG);
        sFeatureTags.put(Long.valueOf(FEATURE_CHAT_SIMPLE_IM), FEATURE_TAG_CHAT);
        sFeatureTags.put(Long.valueOf(FEATURE_NON_RCS_USER), FEATURE_TAG_NULL);
        sFeatureTags.put(Long.valueOf(FEATURE_NOT_UPDATED), FEATURE_TAG_NOT_UPDATED);
        sFeatureTags.put(Long.valueOf(FEATURE_STICKER), FEATURE_TAG_STICKER);
        sFeatureTags.put(Long.valueOf(FEATURE_FT_THUMBNAIL_V1), FEATURE_TAG_FT_THUMBNAIL);
        sFeatureTags.put(Long.valueOf(FEATURE_FT_HTTP_EXTRA), FEATURE_TAG_FT_HTTP_EXTRA);
        sFeatureTags.put(Long.valueOf(FEATURE_FT_VIA_SMS), FEATURE_TAG_FT_VIA_SMS);
        sFeatureTags.put(Long.valueOf(FEATURE_GEO_VIA_SMS), FEATURE_TAG_GEO_VIA_SMS);
        sFeatureTags.put(Long.valueOf(FEATURE_ENRICHED_CALL_COMPOSER), FEATURE_TAG_ENRICHED_CALL_COMPOSER);
        sFeatureTags.put(Long.valueOf(FEATURE_ENRICHED_SHARED_MAP), FEATURE_TAG_ENRICHED_SHARED_MAP);
        sFeatureTags.put(Long.valueOf(FEATURE_ENRICHED_SHARED_SKETCH), FEATURE_TAG_ENRICHED_SHARED_SKETCH);
        sFeatureTags.put(Long.valueOf(FEATURE_ENRICHED_POST_CALL), FEATURE_TAG_ENRICHED_POST_CALL);
        sFeatureTags.put(Long.valueOf(FEATURE_LAST_SEEN_ACTIVE), FEATURE_TAG_LAST_SEEN_ACTIVE);
        sFeatureTags.put(Long.valueOf(FEATURE_CHATBOT_CHAT_SESSION), "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot\"");
        sFeatureTags.put(Long.valueOf(FEATURE_CANCEL_MESSAGE), FEATURE_TAG_CANCEL_MESSAGE);
        sFeatureTags.put(Long.valueOf(FEATURE_CHATBOT_STANDALONE_MSG), FEATURE_TAG_CHATBOT_STANDALONE_MSG);
        sFeatureTags.put(Long.valueOf(FEATURE_CHATBOT_EXTENDED_MSG), FEATURE_TAG_EXTENDED_BOT_MSG);
        sFeatureTags.put(Long.valueOf(FEATURE_CHATBOT_ROLE), FEATURE_TAG_CHATBOT_ROLE);
        sFeatureTags.put(Long.valueOf(FEATURE_PLUG_IN), FEATURE_TAG_PLUG_IN);
        sFeatureTags.put(Long.valueOf(FEATURE_PUBLIC_MSG), FEATURE_TAG_PUBLIC_MSG);
        sTagFeatures.put(FEATURE_TAG_ISH, Long.valueOf(FEATURE_ISH));
        sTagFeatures.put(FEATURE_TAG_VSH, Long.valueOf(FEATURE_VSH));
        sTagFeatures.put(FEATURE_TAG_CHAT, Long.valueOf(FEATURE_CHAT_CPM));
        sTagFeatures.put(FEATURE_TAG_SF_GROUP_CHAT, Long.valueOf(FEATURE_SF_GROUP_CHAT));
        sTagFeatures.put(FEATURE_TAG_FT, Long.valueOf(FEATURE_FT));
        sTagFeatures.put(FEATURE_TAG_FT_THUMBNAIL, Long.valueOf(FEATURE_FT_THUMBNAIL));
        sTagFeatures.put(FEATURE_TAG_FT_STORE, Long.valueOf(FEATURE_FT_STORE));
        sTagFeatures.put(FEATURE_TAG_FT_HTTP, Long.valueOf(FEATURE_FT_HTTP));
        sTagFeatures.put(FEATURE_TAG_STANDALONE_MSG, Long.valueOf(FEATURE_STANDALONE_MSG));
        sTagFeatures.put(FEATURE_TAG_STANDALONE_MSG, Long.valueOf(FEATURE_STANDALONE_MSG_V1));
        sTagFeatures.put(FEATURE_TAG_VSH_OUTSIDE_CALL, Long.valueOf(FEATURE_VSH_OUTSIDE_CALL));
        sTagFeatures.put(FEATURE_TAG_SOCIAL_PRESENCE, Long.valueOf(FEATURE_SOCIAL_PRESENCE));
        sTagFeatures.put(FEATURE_TAG_PRESENCE_DISCOVERY, Long.valueOf(FEATURE_PRESENCE_DISCOVERY));
        sTagFeatures.put(FEATURE_TAG_MMTEL, Long.valueOf(FEATURE_MMTEL));
        sTagFeatures.put(FEATURE_TAG_MMTEL_VIDEO, Long.valueOf(FEATURE_MMTEL_VIDEO));
        sTagFeatures.put(FEATURE_TAG_MMTEL_CALL_COMPOSER, Long.valueOf(FEATURE_MMTEL_CALL_COMPOSER));
        sTagFeatures.put(FEATURE_TAG_IPCALL, Long.valueOf(FEATURE_IPCALL));
        sTagFeatures.put(FEATURE_TAG_IPCALL_VIDEO, Long.valueOf(FEATURE_IPCALL_VIDEO));
        sTagFeatures.put(FEATURE_TAG_IPCALL_VIDEO_ONLY, Long.valueOf(FEATURE_IPCALL_VIDEO_ONLY));
        sTagFeatures.put(FEATURE_TAG_GEOLOCATION_PULL, Long.valueOf(FEATURE_GEOLOCATION_PULL));
        sTagFeatures.put(FEATURE_TAG_GEOLOCATION_PULL_FT, Long.valueOf(FEATURE_GEOLOCATION_PULL_FT));
        sTagFeatures.put(FEATURE_TAG_GEOLOCATION_PUSH, Long.valueOf(FEATURE_GEOLOCATION_PUSH));
        sTagFeatures.put(FEATURE_TAG_INTEGRATED_MSG, Long.valueOf(FEATURE_INTEGRATED_MSG));
        sTagFeatures.put(FEATURE_TAG_CHAT, Long.valueOf(FEATURE_CHAT_SIMPLE_IM));
        sTagFeatures.put(FEATURE_TAG_NULL, Long.valueOf(FEATURE_NON_RCS_USER));
        sTagFeatures.put(FEATURE_TAG_NOT_UPDATED, Long.valueOf(FEATURE_NOT_UPDATED));
        sTagFeatures.put(FEATURE_TAG_STICKER, Long.valueOf(FEATURE_STICKER));
        sTagFeatures.put(FEATURE_TAG_FT_HTTP_EXTRA, Long.valueOf(FEATURE_FT_HTTP_EXTRA));
        sTagFeatures.put(FEATURE_TAG_FT_VIA_SMS, Long.valueOf(FEATURE_FT_VIA_SMS));
        sTagFeatures.put(FEATURE_TAG_GEO_VIA_SMS, Long.valueOf(FEATURE_GEO_VIA_SMS));
        sTagFeatures.put(FEATURE_TAG_ENRICHED_CALL_COMPOSER, Long.valueOf(FEATURE_ENRICHED_CALL_COMPOSER));
        sTagFeatures.put(FEATURE_TAG_ENRICHED_SHARED_MAP, Long.valueOf(FEATURE_ENRICHED_SHARED_MAP));
        sTagFeatures.put(FEATURE_TAG_ENRICHED_SHARED_SKETCH, Long.valueOf(FEATURE_ENRICHED_SHARED_SKETCH));
        sTagFeatures.put(FEATURE_TAG_ENRICHED_POST_CALL, Long.valueOf(FEATURE_ENRICHED_POST_CALL));
        sTagFeatures.put(FEATURE_TAG_LAST_SEEN_ACTIVE, Long.valueOf(FEATURE_LAST_SEEN_ACTIVE));
        sTagFeatures.put("+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot\"", Long.valueOf(FEATURE_CHATBOT_CHAT_SESSION));
        sTagFeatures.put(FEATURE_TAG_CANCEL_MESSAGE, Long.valueOf(FEATURE_CANCEL_MESSAGE));
        sTagFeatures.put(FEATURE_TAG_CHATBOT_STANDALONE_MSG, Long.valueOf(FEATURE_CHATBOT_STANDALONE_MSG));
        sTagFeatures.put(FEATURE_TAG_EXTENDED_BOT_MSG, Long.valueOf(FEATURE_CHATBOT_EXTENDED_MSG));
        sTagFeatures.put(FEATURE_TAG_CHATBOT_ROLE, Long.valueOf(FEATURE_CHATBOT_ROLE));
        sTagFeatures.put(FEATURE_TAG_PLUG_IN, Long.valueOf(FEATURE_PLUG_IN));
        sFeatures.put(Long.valueOf(FEATURE_ISH), "ish");
        sFeatures.put(Long.valueOf(FEATURE_VSH), "vsh");
        sFeatures.put(Long.valueOf(FEATURE_CHAT_CPM), ImsProfile.SERVICE_IM);
        sFeatures.put(Long.valueOf(FEATURE_SF_GROUP_CHAT), "fullsf_groupchat");
        sFeatures.put(Long.valueOf(FEATURE_FT), ImsProfile.SERVICE_FT);
        sFeatures.put(Long.valueOf(FEATURE_FT_THUMBNAIL), "ftthumb");
        sFeatures.put(Long.valueOf(FEATURE_FT_STORE), "ftstandfw");
        sFeatures.put(Long.valueOf(FEATURE_FT_HTTP), "fthttp");
        sFeatures.put(Long.valueOf(FEATURE_STANDALONE_MSG), "standalone_msg");
        sFeatures.put(Long.valueOf(FEATURE_STANDALONE_MSG_V1), "standalone_msg_v1");
        sFeatures.put(Long.valueOf(FEATURE_VSH_OUTSIDE_CALL), "vsh_outside_call");
        sFeatures.put(Long.valueOf(FEATURE_SOCIAL_PRESENCE), "sp");
        sFeatures.put(Long.valueOf(FEATURE_PRESENCE_DISCOVERY), "dp");
        sFeatures.put(Long.valueOf(FEATURE_MMTEL), "mmtel");
        sFeatures.put(Long.valueOf(FEATURE_MMTEL_VIDEO), "mmtel_video");
        sFeatures.put(Long.valueOf(FEATURE_MMTEL_CALL_COMPOSER), "mmtel_call_composer");
        sFeatures.put(Long.valueOf(FEATURE_IPCALL), "ipcall");
        sFeatures.put(Long.valueOf(FEATURE_IPCALL_VIDEO), "ipcall_video");
        sFeatures.put(Long.valueOf(FEATURE_IPCALL_VIDEO_ONLY), "ipcall_video_only");
        sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PULL), "geopush");
        sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PULL_FT), "geopullft");
        sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PUSH), "geopush");
        sFeatures.put(Long.valueOf(FEATURE_INTEGRATED_MSG), "intergrated_msg");
        sFeatures.put(Long.valueOf(FEATURE_CHAT_SIMPLE_IM), "session_mode_msg");
        sFeatures.put(Long.valueOf(FEATURE_NON_RCS_USER), "null");
        sFeatures.put(Long.valueOf(FEATURE_NOT_UPDATED), "not_updated");
        sFeatures.put(Long.valueOf(FEATURE_STICKER), "sticker");
        sFeatures.put(Long.valueOf(FEATURE_FT_THUMBNAIL_V1), "ftthumb_v1");
        sFeatures.put(Long.valueOf(FEATURE_FT_HTTP_EXTRA), "fthttp_extra");
        sFeatures.put(Long.valueOf(FEATURE_FT_VIA_SMS), "ftsms");
        sFeatures.put(Long.valueOf(FEATURE_GEO_VIA_SMS), "geosms");
        sFeatures.put(Long.valueOf(FEATURE_ENRICHED_CALL_COMPOSER), "callcomposer");
        sFeatures.put(Long.valueOf(FEATURE_ENRICHED_SHARED_MAP), "sharedmap");
        sFeatures.put(Long.valueOf(FEATURE_ENRICHED_SHARED_SKETCH), "sharedsketch");
        sFeatures.put(Long.valueOf(FEATURE_ENRICHED_POST_CALL), "callunanswered");
        sFeatures.put(Long.valueOf(FEATURE_LAST_SEEN_ACTIVE), "lastseenactive");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_CHAT_SESSION), "chatbot");
        sFeatures.put(Long.valueOf(FEATURE_CANCEL_MESSAGE), "cancelmessage");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_STANDALONE_MSG), "chatbot_standalone_msg");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_EXTENDED_MSG), "extended_bot_msg");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_ROLE), "isbot");
        sFeatures.put(Long.valueOf(FEATURE_PLUG_IN), "plugin");
        sFeatures.put(Long.valueOf(FEATURE_PUBLIC_MSG), "publicmsg");
        CREATOR = new Parcelable.Creator<Capabilities>() { // from class: com.sec.ims.options.Capabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Capabilities createFromParcel(Parcel parcel) {
                return new Capabilities(parcel, 0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Capabilities[] newArray(int i) {
                return new Capabilities[i];
            }
        };
    }

    public /* synthetic */ Capabilities(Parcel parcel, int i) {
        this(parcel);
    }

    public static String dumpFeature(long j) {
        String[] featureTag = getFeatureTag(j);
        StringBuilder sb = new StringBuilder();
        for (String str : featureTag) {
            sb.append(str + ",");
        }
        return sb.toString();
    }

    private static String dumpServices(long j) {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = sFeatures.keySet().iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            if ((longValue & j) != 0) {
                arrayList.add(sFeatures.get(Long.valueOf(longValue)));
            }
        }
        return arrayList.toString();
    }

    public static long getTagFeature(String str) {
        return sTagFeatures.containsKey(str) ? sTagFeatures.get(str).longValue() : FEATURE_OFFLINE_RCS_USER;
    }

    private String toStringLimit(String str) {
        return (str == null || str.length() <= 2) ? "" : str.substring(str.length() - 2);
    }

    public void addExtFeature(String str) {
        synchronized (this.mExtFeatureLock) {
            this.mExtFeatures.add(str);
        }
    }

    public void addFeature(long j) {
        this.mFeatures |= j;
        this.mAvailableFeatures = j | this.mAvailableFeatures;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getAvailableFeatures() {
        return this.mAvailableFeatures;
    }

    public String getBotServiceId() {
        return this.mBotServiceId;
    }

    public String getContactId() {
        return this.mContactId;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public boolean getExpired() {
        return this.mIsExpired;
    }

    public List<String> getExtFeature() {
        ArrayList arrayList;
        synchronized (this.mExtFeatureLock) {
            arrayList = new ArrayList(this.mExtFeatures);
        }
        return arrayList;
    }

    public String getExtFeatureAsJoinedString() {
        String join;
        synchronized (this.mExtFeatureLock) {
            List<String> list = this.mExtFeatures;
            join = (list == null || list.isEmpty()) ? "" : String.join(",", this.mExtFeatures);
        }
        return join;
    }

    public long getFeature() {
        return this.mFeatures;
    }

    public String[] getFeatureTag() {
        return getFeatureTag(this.mFeatures);
    }

    public long getId() {
        return this.mId;
    }

    public long getLastSeen() {
        return this.mLastSeen;
    }

    public boolean getLegacyLatching() {
        return this.mIsLegacyLatching;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public List<ImsUri> getPAssertedId() {
        ArrayList arrayList;
        synchronized (this.mPAssertedIdLock) {
            arrayList = new ArrayList(this.mPAssertedIdSet);
        }
        return arrayList;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public String getPidf() {
        return this.mPidf;
    }

    public Date getTimestamp() {
        return this.mTimestamp;
    }

    public ImsUri getUri() {
        return this.mUri;
    }

    public boolean hasAnyFeature(long j) {
        return (this.mFeatures & j) != 0;
    }

    public boolean hasFeature(int i) {
        long j = this.mFeatures;
        long j2 = i;
        return (j & j2) == j2;
    }

    public boolean hasNoRcsFeatures() {
        return hasFeature(FEATURE_NON_RCS_USER) || hasFeature(FEATURE_NOT_UPDATED) || ((this.mFeatures & ((long) (~FEATURE_MMTEL))) & ((long) (~FEATURE_MMTEL_VIDEO))) == ((long) FEATURE_OFFLINE_RCS_USER);
    }

    public boolean hasPresenceSupport() {
        return this.mSupportPresence;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    public boolean isExpired(long j) {
        Date date = new Date();
        setExpired(date.getTime() - this.mTimestamp.getTime() >= 1000 * j);
        Log.i(LOG_TAG, "isExpired: " + this.mIsExpired + ", capInfoExpiry = " + j + " sec, elapsed time = " + (date.getTime() - this.mTimestamp.getTime()) + " ms");
        return this.mIsExpired;
    }

    public boolean isFeatureAvailable(int i) {
        return isFeatureAvailable(Long.valueOf(i).longValue());
    }

    public void removeFeature(long j) {
        long j2 = this.mFeatures;
        long j3 = ~j;
        this.mFeatures = j2 & j3;
        this.mAvailableFeatures = j3 & this.mAvailableFeatures;
    }

    public void resetFeatures() {
        int i = FEATURE_NOT_UPDATED;
        this.mFeatures = i;
        this.mAvailableFeatures = i;
        this.mIsAvailable = false;
        this.mIsLegacyLatching = false;
        this.mTimestamp = new Date();
    }

    public void setAvailableFeatures(long j) {
        this.mAvailableFeatures = j;
    }

    public void setAvailiable(boolean z) {
        this.mIsAvailable = z;
    }

    public void setBotServiceId(String str) {
        this.mBotServiceId = str;
    }

    public void setExpired(boolean z) {
        this.mIsExpired = z;
    }

    public void setExtFeature(List<String> list) {
        synchronized (this.mExtFeatureLock) {
            this.mExtFeatures.clear();
            this.mExtFeatures.addAll(list);
        }
    }

    public void setFeatures(long j) {
        this.mFeatures = j;
    }

    public void setId(long j) {
        this.mId = j;
    }

    public void setLastSeen(long j) {
        this.mLastSeen = j;
    }

    public void setLegacyLatching(boolean z) {
        this.mIsLegacyLatching = z;
    }

    public void setNumber(String str) {
        this.mNumber = str;
    }

    public void setPAssertedId(List<ImsUri> list) {
        synchronized (this.mPAssertedIdLock) {
            this.mPAssertedIdSet.clear();
            this.mPAssertedIdSet.addAll(list);
        }
    }

    public void setPhoneId(int i) {
        this.mPhoneId = i;
    }

    public void setPidf(String str) {
        this.mPidf = str;
    }

    public void setPresenceSupport(boolean z) {
        this.mSupportPresence = z;
    }

    public void setTimestamp(Date date) {
        this.mTimestamp = date;
    }

    public void setUri(ImsUri imsUri) {
        if (imsUri != null) {
            this.mUri = imsUri;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Capabilities [mId=");
        sb.append(this.mId);
        sb.append(", mUri=");
        boolean z = SHIP_BUILD;
        sb.append(z ? "xxxxx" : this.mUri);
        sb.append(", mContactId=");
        sb.append(this.mContactId);
        sb.append(", mNumber=");
        String str = this.mNumber;
        if (z) {
            str = toStringLimit(str);
        }
        sb.append(str);
        sb.append(", mIsAvailable=");
        sb.append(this.mIsAvailable);
        sb.append(", mFeatures=");
        sb.append(Long.toHexString(this.mFeatures));
        sb.append(", mAvailableFeatures=");
        sb.append(Long.toHexString(this.mAvailableFeatures));
        sb.append(", mSupportPresence=");
        sb.append(this.mSupportPresence);
        sb.append(", mIsLegacyLatching=");
        sb.append(this.mIsLegacyLatching);
        sb.append(", mPhoneId=");
        sb.append(this.mPhoneId);
        sb.append(", mBotServiceId=");
        sb.append(z ? "xxxxx" : this.mBotServiceId);
        sb.append(", mTimestamp=");
        sb.append(this.mTimestamp);
        sb.append("], mAvailableFeatures=");
        sb.append(dumpServices(this.mAvailableFeatures));
        sb.append(", mFeatures=");
        sb.append(dumpServices(this.mFeatures));
        sb.append(", mLastSeen=");
        sb.append(this.mLastSeen);
        sb.append(", mExtFeatures=");
        sb.append(this.mExtFeatures);
        sb.append("]");
        return sb.toString();
    }

    public void updateCapabilities(String str, String str2, String str3) {
        this.mNumber = str;
        this.mContactId = str2;
        this.mDisplayName = str3;
    }

    public void updateTimestamp() {
        this.mTimestamp = new Date();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mContactId);
        String str = this.mDisplayName;
        if (str == null) {
            str = "";
        }
        parcel.writeString(str);
        parcel.writeLong(this.mId);
        ImsUri imsUri = this.mUri;
        parcel.writeString(imsUri != null ? imsUri.toString() : "");
        String str2 = this.mNumber;
        if (str2 == null) {
            str2 = "";
        }
        parcel.writeString(str2);
        parcel.writeInt(this.mIsAvailable ? 1 : 0);
        parcel.writeLong(this.mFeatures);
        parcel.writeLong(this.mAvailableFeatures);
        synchronized (this.mExtFeatureLock) {
            parcel.writeStringList(this.mExtFeatures);
        }
        parcel.writeLong(this.mTimestamp.getTime());
        parcel.writeInt(this.mSupportPresence ? 1 : 0);
        parcel.writeInt(this.mIsLegacyLatching ? 1 : 0);
        parcel.writeInt(this.mPhoneId);
        if (this.mPidf != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mPidf);
        } else {
            parcel.writeInt(0);
        }
        String str3 = this.mBotServiceId;
        if (str3 == null) {
            str3 = "";
        }
        parcel.writeString(str3);
        parcel.writeLong(this.mLastSeen);
    }

    public Capabilities() {
        this.mId = -1L;
        this.mNumber = null;
        this.mPhoneId = 0;
        this.mIsAvailable = false;
        this.mFeatures = 0L;
        this.mAvailableFeatures = 0L;
        this.mExtFeatureLock = new Object();
        this.mExtFeatures = new ArrayList();
        this.mSupportPresence = false;
        this.mIsExpired = false;
        this.mIsLegacyLatching = false;
        this.mLastSeen = -1L;
        this.mPidf = null;
        this.mBotServiceId = null;
        this.mPAssertedIdLock = new Object();
        this.mPAssertedIdSet = new ArrayList(2);
        this.mUri = ImsUri.parse("sip:foo@examcple.com");
        this.mContactId = null;
        this.mDisplayName = null;
        int i = FEATURE_OFFLINE_RCS_USER;
        this.mFeatures = i;
        this.mAvailableFeatures = i;
        this.mTimestamp = new Date(0L);
    }

    public static String[] getFeatureTag(long j) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<Map.Entry<Long, String>> it = sFeatureTags.entrySet().iterator();
        while (it.hasNext()) {
            long longValue = it.next().getKey().longValue();
            if ((longValue & j) != 0) {
                arrayList.add(sFeatureTags.get(Long.valueOf(longValue)));
                arrayList2.add(Long.valueOf(longValue));
            }
        }
        Log.i(LOG_TAG, "getFeatureTag: features = " + j + " " + arrayList2);
        return (String[]) arrayList.toArray(new String[0]);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Capabilities m2849clone() {
        return (Capabilities) super.clone();
    }

    public boolean hasFeature(long j) {
        return (this.mFeatures & j) == j;
    }

    public boolean isFeatureAvailable(long j) {
        if (j == Long.valueOf(FEATURE_OFFLINE_RCS_USER).longValue()) {
            return true;
        }
        boolean z = (this.mAvailableFeatures & j) != 0;
        Log.i(LOG_TAG, "isFeatureAvailable: feature " + dumpFeature(j) + " " + z);
        return z;
    }

    public Capabilities(ImsUri imsUri, String str, String str2, long j, String str3) {
        this.mId = -1L;
        this.mNumber = null;
        this.mPhoneId = 0;
        this.mIsAvailable = false;
        this.mFeatures = 0L;
        this.mAvailableFeatures = 0L;
        this.mExtFeatureLock = new Object();
        this.mExtFeatures = new ArrayList();
        this.mSupportPresence = false;
        this.mIsExpired = false;
        this.mIsLegacyLatching = false;
        this.mLastSeen = -1L;
        this.mPidf = null;
        this.mBotServiceId = null;
        this.mPAssertedIdLock = new Object();
        this.mPAssertedIdSet = new ArrayList(2);
        this.mUri = imsUri;
        this.mNumber = str;
        this.mContactId = str2;
        this.mId = j;
        this.mDisplayName = str3;
        this.mTimestamp = new Date(0L);
    }

    private Capabilities(Parcel parcel) {
        this.mId = -1L;
        this.mNumber = null;
        this.mPhoneId = 0;
        this.mIsAvailable = false;
        this.mFeatures = 0L;
        this.mAvailableFeatures = 0L;
        this.mExtFeatureLock = new Object();
        this.mExtFeatures = new ArrayList();
        this.mSupportPresence = false;
        this.mIsExpired = false;
        this.mIsLegacyLatching = false;
        this.mLastSeen = -1L;
        this.mPidf = null;
        this.mBotServiceId = null;
        this.mPAssertedIdLock = new Object();
        this.mPAssertedIdSet = new ArrayList(2);
        this.mContactId = parcel.readString();
        this.mDisplayName = parcel.readString();
        this.mId = parcel.readLong();
        this.mUri = ImsUri.parse(parcel.readString());
        this.mNumber = parcel.readString();
        this.mIsAvailable = parcel.readInt() == 1;
        this.mFeatures = parcel.readLong();
        this.mAvailableFeatures = parcel.readLong();
        parcel.readStringList(this.mExtFeatures);
        this.mTimestamp = new Date(parcel.readLong());
        this.mSupportPresence = parcel.readInt() == 1;
        this.mIsLegacyLatching = parcel.readInt() == 1;
        this.mPhoneId = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mPidf = parcel.readString();
        }
        this.mBotServiceId = parcel.readString();
        this.mLastSeen = parcel.readLong();
    }
}
