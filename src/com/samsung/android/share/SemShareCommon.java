package com.samsung.android.share;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import com.android.internal.R;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemShareCommon {
    private static final boolean DEBUG = false;
    private static final String TAG = "SemShareCommon";
    private Context mContext;
    private boolean mDeviceDefault;
    private List<Intent> mExtraIntentList;
    private int mIconChangePlayer;
    private int mIconPrint;
    private int mIconQuickConnect;
    private int mIconScreenMirroring;
    private int mIconScreenSharing;
    private int mLaunchedFromUid;
    private PackageManager mPm;
    private boolean mSCSupport;
    private int mSCVersion;
    private int mSupportedFeatures;
    private static final String sSalesCode = SemSystemProperties.getSalesCode();
    private static final String[] CHINA_SALES_CODES = {"CHN", "CHM", "CBK", "CTC", "CHU", "CHC"};

    private boolean getResolverGuideSupportState(Intent intent) {
        return false;
    }

    public boolean isShowSwipeUpGuide() {
        return false;
    }

    public SemShareCommon(Context context, Intent intent, boolean z, int i, List<Intent> list) {
        this(context, intent, z, false, false, i, list);
    }

    public SemShareCommon(Context context, Intent intent, boolean z, boolean z2, boolean z3, int i, List<Intent> list) {
        this.mSupportedFeatures = 0;
        this.mIconChangePlayer = 0;
        this.mIconScreenMirroring = 0;
        this.mIconScreenSharing = 0;
        this.mIconQuickConnect = 0;
        this.mIconPrint = 0;
        this.mContext = context;
        this.mLaunchedFromUid = i;
        this.mExtraIntentList = list;
        this.mDeviceDefault = z;
        this.mPm = context.getPackageManager();
        if (z2 || z3) {
            checkButtonsFeature();
            checkResolverGuideFeature(intent);
        }
        checkLoggingFeature();
        Log.d(TAG, "SShare Support Feature: " + this.mSupportedFeatures);
    }

    private void checkButtonsFeature() {
        setSupportedFeature(SemShareConstants.SUPPORT_BUTTONS);
        if (getButtonShapeSupportState()) {
            setSupportedFeature(SemShareConstants.SUPPORT_SHOW_BUTTON_SHAPES);
        }
    }

    private void checkResolverGuideFeature(Intent intent) {
        if (getResolverGuideSupportState(intent)) {
            setSupportedFeature(SemShareConstants.SUPPORT_RESOLVER_GUIDE);
        }
    }

    private void checkShareLinkFeature(Intent intent) {
        if (getShareLinkSupportState(intent)) {
            setSupportedFeature(SemShareConstants.SUPPORT_SHARE_LINK);
        }
    }

    private void checkLoggingFeature() {
        setSupportedFeature(SemShareConstants.SUPPORT_LOGGING);
    }

    private void checkDeviceShareFeature() {
        setSupportedFeature(SemShareConstants.SUPPORT_DEVICE_SHARE);
    }

    private void checkBixbyFeature() {
        if (SemShareConstants.ENABLE_BIXBY) {
            setSupportedFeature(SemShareConstants.SUPPORT_BIXBY);
        }
    }

    private int getSupportedFeatures() {
        return this.mSupportedFeatures;
    }

    private void setSupportedFeature(int i) {
        this.mSupportedFeatures = i | this.mSupportedFeatures;
    }

    public boolean isFeatureSupported(int i) {
        return (this.mSupportedFeatures & i) != 0;
    }

    public boolean isDeviceDefaultTheme() {
        return this.mDeviceDefault;
    }

    public int getChangePlayerEnable() {
        return this.mIconChangePlayer;
    }

    public int getScreenMirroringEnable() {
        return this.mIconScreenMirroring;
    }

    public int getScreenSharingEnable() {
        return this.mIconScreenSharing;
    }

    public int getQuickConnectEnable() {
        return this.mIconQuickConnect;
    }

    public int getPrintEnable() {
        return this.mIconPrint;
    }

    public boolean isKnoxModeEnabled() {
        return UserHandle.getUserId(this.mLaunchedFromUid) >= 100;
    }

    private boolean isForceSimpleSharingDisable(Intent intent) {
        return intent.getIntExtra(SemShareConstants.SIMPLE_SHARING_FORCE_DISABLE, 0) == 1;
    }

    private boolean isEmergencyOrUPSModeEnabled() {
        boolean z;
        boolean z2;
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        SemEmergencyManager semEmergencyManager = SemEmergencyManager.getInstance(context);
        if (semEmergencyManager != null) {
            z2 = semEmergencyManager.isEmergencyMode() && !semEmergencyManager.checkModeType(512);
            z = semEmergencyManager.isEmergencyMode() && semEmergencyManager.checkModeType(512);
        } else {
            z = false;
            z2 = false;
        }
        return z2 || z;
    }

    private boolean isIntentTypeSupportRemoteShare(Intent intent) {
        String action = intent.getAction();
        return (Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action)) && isIntentUriDataIValidCheck(intent);
    }

    private boolean hasExtraIntentUriInfo() {
        if (this.mExtraIntentList != null) {
            for (int i = 0; i < this.mExtraIntentList.size(); i++) {
                Bundle extras = this.mExtraIntentList.get(i).getExtras();
                if (extras != null && ((Uri) extras.getParcelable(Intent.EXTRA_STREAM)) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isIntentUriDataIValidCheck(Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            Bundle extras = intent.getExtras();
            Uri uri = extras != null ? (Uri) extras.getParcelable(Intent.EXTRA_STREAM) : null;
            if (uri == null) {
                return hasExtraIntentUriInfo();
            }
            "com.android.contacts".equals(uri.getEncodedAuthority());
            return true;
        }
        if (!Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            return true;
        }
        new ArrayList();
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (parcelableArrayListExtra == null) {
            return false;
        }
        int size = parcelableArrayListExtra.size();
        for (int i = 0; i < size; i++) {
            if (parcelableArrayListExtra.get(i) != null) {
                "com.android.contacts".equals(((Uri) parcelableArrayListExtra.get(i)).getEncodedAuthority());
                return true;
            }
        }
        return false;
    }

    private boolean getButtonsSupportState() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        return contentResolver != null && Settings.System.getInt(contentResolver, "default_app_selection_option", 0) == 1;
    }

    private boolean getButtonShapeSupportState() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        return contentResolver != null && Settings.System.getInt(contentResolver, "show_button_background", 0) == 1;
    }

    private boolean getQuickConnectSupportState() {
        try {
            ApplicationInfo applicationInfo = this.mPm.getApplicationInfo("com.samsung.android.oneconnect", 0);
            if (applicationInfo == null || (applicationInfo.flags & 1) != 0 || SemShareConstants.ENABLE_QUICKCONNECT_D2D) {
                return true;
            }
            Log.w(TAG, "getQuickConnectSupportState - oneconnect isn't preload app");
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "getQuickConnectSupportState - oneconnect isn't installed");
            return false;
        }
    }

    private boolean getShareLinkSupportState(Intent intent) {
        boolean zIsIntentTypeSupportRemoteShare = isIntentTypeSupportRemoteShare(intent);
        boolean zIsKnoxModeEnabled = isKnoxModeEnabled();
        boolean zIsEmergencyOrUPSModeEnabled = isEmergencyOrUPSModeEnabled();
        boolean zIsForceSimpleSharingDisable = isForceSimpleSharingDisable(intent);
        if (zIsIntentTypeSupportRemoteShare && !zIsKnoxModeEnabled && !zIsEmergencyOrUPSModeEnabled && !zIsForceSimpleSharingDisable) {
            return true;
        }
        Log.d(TAG, " intentSupport = " + zIsIntentTypeSupportRemoteShare + " knoxMode = " + zIsKnoxModeEnabled + " emergencyMode = " + zIsEmergencyOrUPSModeEnabled + " forceDisable = " + zIsForceSimpleSharingDisable);
        return false;
    }

    private void checkSamsungConnectInfo() {
        this.mSCSupport = getQuickConnectSupportState();
        this.mSCVersion = getSamsungConnectVersion();
    }

    public int getSamsungConnectVersion() {
        PackageInfo packageInfo;
        try {
            packageInfo = this.mPm.getPackageInfo("com.samsung.android.oneconnect", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return -1;
    }

    private boolean getShareToDeviceSupportState() {
        if (Build.VERSION.SEM_PLATFORM_INT >= 80500) {
            return !this.mSCSupport || this.mSCVersion >= 150000000;
        }
        return false;
    }

    public int getFileIconTypeFromExtension(String str) {
        HashMap<String, Integer> fileIconExtensionMap = getFileIconExtensionMap();
        if (fileIconExtensionMap.containsKey(str)) {
            return fileIconExtensionMap.get(str).intValue();
        }
        return fileIconExtensionMap.get("etc").intValue();
    }

    private HashMap<String, Integer> getFileIconExtensionMap() {
        HashMap<String, Integer> map = new HashMap<>();
        Integer numValueOf = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_amr);
        map.put("m4a", numValueOf);
        map.put("amr", numValueOf);
        map.put("awb", numValueOf);
        map.put("3ga", numValueOf);
        map.put("apk", Integer.valueOf(R.drawable.sem_chooser_ic_filetype_apk));
        map.put("vcf", Integer.valueOf(R.drawable.sem_chooser_ic_filetype_contact));
        Integer numValueOf2 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_gallery);
        map.put("jpg", numValueOf2);
        map.put("jpeg", numValueOf2);
        map.put("mv5", numValueOf2);
        map.put("gif", numValueOf2);
        map.put("png", numValueOf2);
        map.put("bmp", numValueOf2);
        map.put("wbmp", numValueOf2);
        map.put("webp", numValueOf2);
        map.put("golf", numValueOf2);
        Integer numValueOf3 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_hwp);
        map.put("hwp", numValueOf3);
        map.put("hwpx", numValueOf3);
        map.put("hwt", numValueOf3);
        Integer numValueOf4 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_music);
        map.put("mp3", numValueOf4);
        map.put("wav", numValueOf4);
        map.put("wma", numValueOf4);
        map.put("ogg", numValueOf4);
        map.put("oga", numValueOf4);
        map.put("aac", numValueOf4);
        map.put("flac", numValueOf4);
        map.put("mp4_a", numValueOf4);
        map.put("mpga", numValueOf4);
        map.put("3gp_a", numValueOf4);
        map.put("asf_a", numValueOf4);
        map.put(Telephony.Mms.Part.MSG_ID, numValueOf4);
        map.put("mid_a", numValueOf4);
        map.put("midi", numValueOf4);
        map.put("rtx", numValueOf4);
        map.put("ota", numValueOf4);
        map.put("xmf", numValueOf4);
        map.put("mxmf", numValueOf4);
        map.put("rtttl", numValueOf4);
        map.put("smf", numValueOf4);
        map.put("spmid", numValueOf4);
        map.put("imv", numValueOf4);
        map.put("pva", numValueOf4);
        map.put("qcp", numValueOf4);
        map.put("mka", numValueOf4);
        map.put("pdf", Integer.valueOf(R.drawable.sem_chooser_ic_filetype_pdf));
        Integer numValueOf5 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_ppt);
        map.put("pps", numValueOf5);
        map.put("ppt", numValueOf5);
        map.put("pptx", numValueOf5);
        map.put("ppsx", numValueOf5);
        map.put("application/vnd.google-apps.presentation", numValueOf5);
        Integer numValueOf6 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_calendar);
        map.put("vcs", numValueOf6);
        map.put("ics", numValueOf6);
        Integer numValueOf7 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_txt);
        map.put("asc", numValueOf7);
        map.put("txt", numValueOf7);
        map.put("epub", numValueOf7);
        map.put("acsm", numValueOf7);
        Integer numValueOf8 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_video);
        map.put("mpeg", numValueOf8);
        map.put("mpg", numValueOf8);
        map.put(BnRConstants.VIDEO_FILE_EXTENSION, numValueOf8);
        map.put("m4v", numValueOf8);
        map.put("3gp", numValueOf8);
        map.put("3gpp", numValueOf8);
        map.put("3g2", numValueOf8);
        map.put("wmv", numValueOf8);
        map.put("asf", numValueOf8);
        map.put("avi", numValueOf8);
        map.put("divx", numValueOf8);
        map.put("flv", numValueOf8);
        map.put("mkv", numValueOf8);
        map.put("sdp", numValueOf8);
        map.put("ts", numValueOf8);
        map.put("pvv", numValueOf8);
        map.put("mov", numValueOf8);
        map.put("skm", numValueOf8);
        map.put("k3g", numValueOf8);
        map.put("ak3g", numValueOf8);
        map.put("webm", numValueOf8);
        map.put("mts", numValueOf8);
        map.put("m2ts", numValueOf8);
        map.put("m2t", numValueOf8);
        map.put("trp", numValueOf8);
        map.put("tp", numValueOf8);
        Integer numValueOf9 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_word);
        map.put("rtf", numValueOf9);
        map.put("doc", numValueOf9);
        map.put("docx", numValueOf9);
        map.put("dot", numValueOf9);
        map.put("dox", numValueOf9);
        map.put("hwdt", numValueOf9);
        map.put("application/vnd.google-apps.document", numValueOf9);
        Integer numValueOf10 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_xls);
        map.put("csv", numValueOf10);
        map.put("xls", numValueOf10);
        map.put("xlsx", numValueOf10);
        map.put("xlt", numValueOf10);
        map.put("xltx", numValueOf10);
        map.put("applicatoin/vnd.google-apps.spreadsheet", numValueOf10);
        map.put("zip", Integer.valueOf(R.drawable.sem_chooser_ic_filetype_zip));
        Integer numValueOf11 = Integer.valueOf(R.drawable.sem_chooser_ic_filetype_notes);
        map.put("sdoc", numValueOf11);
        map.put("sdocx", numValueOf11);
        map.put("etc", Integer.valueOf(R.drawable.sem_chooser_ic_filetype_etc));
        return map;
    }

    public Map<String, String> getHtmlCharMap() {
        return new HashMap<String, String>() { // from class: com.samsung.android.share.SemShareCommon.1
            {
                put("&nbsp;", " ");
                put("&iexcl;", "¡");
                put("&cent;", "¢");
                put("&pound;", "£");
                put("&curren;", "¤");
                put("&yen;", "¥");
                put("&brvbar;", "¦");
                put("&sect;", "§");
                put("&uml;", "¨");
                put("&copy;", "©");
                put("&ordf;", "ª");
                put("&laquo;", "«");
                put("&not;", "¬");
                put("&shy;", "\u00ad");
                put("&reg;", "®");
                put("&macr;", "¯");
                put("&deg;", "°");
                put("&plusmn;", "±");
                put("&sup2;", "²");
                put("&sup3;", "³");
                put("&acute;", "´");
                put("&micro;", "µ");
                put("&para;", "¶");
                put("&middot;", "·");
                put("&cedil;", "¸");
                put("&sup1;", "¹");
                put("&ordm;", "º");
                put("&raquo;", "»");
                put("&frac14;", "¼");
                put("&frac12;", "½");
                put("&frac34;", "¾");
                put("&iquest;", "¿");
                put("&Agrave;", "À");
                put("&Aacute;", "Á");
                put("&Acirc;", "Â");
                put("&Atilde;", "Ã");
                put("&Auml;", "Ä");
                put("&Aring;", "Å");
                put("&AElig;", "Æ");
                put("&Ccedil;", "Ç");
                put("&Egrave;", "È");
                put("&Eacute;", "É");
                put("&Ecirc;", "Ê");
                put("&Euml;", "Ë");
                put("&Igrave;", "Ì");
                put("&Iacute;", "Í");
                put("&Icirc;", "Î");
                put("&Iuml;", "Ï");
                put("&ETH;", "Ð");
                put("&Ntilde;", "Ñ");
                put("&Ograve;", "Ò");
                put("&Oacute;", "Ó");
                put("&Ocirc;", "Ô");
                put("&Otilde;", "Õ");
                put("&Ouml;", "Ö");
                put("&times;", "×");
                put("&Oslash;", "Ø");
                put("&Ugrave;", "Ù");
                put("&Uacute;", "Ú");
                put("&Ucirc;", "Û");
                put("&Uuml;", "Ü");
                put("&Yacute;", "Ý");
                put("&THORN;", "Þ");
                put("&szlig;", "ß");
                put("&agrave;", "à");
                put("&aacute;", "á");
                put("&acirc;", "â");
                put("&atilde;", "ã");
                put("&auml;", "ä");
                put("&aring;", "å");
                put("&aelig;", "æ");
                put("&ccedil;", "ç");
                put("&egrave;", "è");
                put("&eacute;", "é");
                put("&ecirc;", "ê");
                put("&euml;", "ë");
                put("&igrave;", "ì");
                put("&iacute;", "í");
                put("&icirc;", "î");
                put("&iuml;", "ï");
                put("&eth;", "ð");
                put("&ntilde;", "ñ");
                put("&ograve;", "ò");
                put("&oacute;", "ó");
                put("&ocirc;", "ô");
                put("&otilde;", "õ");
                put("&ouml;", "ö");
                put("&divide;", "÷");
                put("&oslash;", "ø");
                put("&ugrave;", "ù");
                put("&uacute;", "ú");
                put("&ucirc;", "û");
                put("&uuml;", "ü");
                put("&yacute;", "ý");
                put("&thorn;", "þ");
                put("&yuml;", "ÿ");
                put("&fnof;", "ƒ");
                put("&Alpha;", "Α");
                put("&Beta;", "Β");
                put("&Gamma;", "Γ");
                put("&Delta;", "Δ");
                put("&Epsilon;", "Ε");
                put("&Zeta;", "Ζ");
                put("&Eta;", "Η");
                put("&Theta;", "Θ");
                put("&Iota;", "Ι");
                put("&Kappa;", "Κ");
                put("&Lambda;", "Λ");
                put("&Mu;", "Μ");
                put("&Nu;", "Ν");
                put("&Xi;", "Ξ");
                put("&Omicron;", "Ο");
                put("&Pi;", "Π");
                put("&Rho;", "Ρ");
                put("&Sigma;", "Σ");
                put("&Tau;", "Τ");
                put("&Upsilon;", "Υ");
                put("&Phi;", "Φ");
                put("&Chi;", "Χ");
                put("&Psi;", "Ψ");
                put("&Omega;", "Ω");
                put("&alpha;", "α");
                put("&beta;", "β");
                put("&gamma;", "γ");
                put("&delta;", "δ");
                put("&epsilon;", "ε");
                put("&zeta;", "ζ");
                put("&eta;", "η");
                put("&theta;", "θ");
                put("&iota;", "ι");
                put("&kappa;", "κ");
                put("&lambda;", "λ");
                put("&mu;", "μ");
                put("&nu;", "ν");
                put("&xi;", "ξ");
                put("&omicron;", "ο");
                put("&pi;", "π");
                put("&rho;", "ρ");
                put("&sigmaf;", "ς");
                put("&sigma;", "σ");
                put("&tau;", "τ");
                put("&upsilon;", "υ");
                put("&phi;", "φ");
                put("&chi;", "χ");
                put("&psi;", "ψ");
                put("&omega;", "ω");
                put("&thetasym;", "ϑ");
                put("&upsih;", "ϒ");
                put("&piv;", "ϖ");
                put("&bull;", "•");
                put("&hellip;", "…");
                put("&prime;", "′");
                put("&Prime;", "″");
                put("&oline;", "‾");
                put("&frasl;", "⁄");
                put("&weierp;", "℘");
                put("&image;", "ℑ");
                put("&real;", "ℜ");
                put("&trade;", "™");
                put("&alefsym;", "ℵ");
                put("&larr;", "←");
                put("&uarr;", "↑");
                put("&rarr;", "→");
                put("&darr;", "↓");
                put("&harr;", "↔");
                put("&crarr;", "↵");
                put("&lArr;", "⇐");
                put("&uArr;", "⇑");
                put("&rArr;", "⇒");
                put("&dArr;", "⇓");
                put("&hArr;", "⇔");
                put("&forall;", "∀");
                put("&part;", "∂");
                put("&exist;", "∃");
                put("&empty;", "∅");
                put("&nabla;", "∇");
                put("&isin;", "∈");
                put("&notin;", "∉");
                put("&ni;", "∋");
                put("&prod;", "∏");
                put("&sum;", "∑");
                put("&minus;", "−");
                put("&lowast;", "∗");
                put("&radic;", "√");
                put("&prop;", "∝");
                put("&infin;", "∞");
                put("&ang;", "∠");
                put("&and;", "∧");
                put("&or;", "∨");
                put("&cap;", "∩");
                put("&cup;", "∪");
                put("&int;", "∫");
                put("&there4;", "∴");
                put("&sim;", "∼");
                put("&cong;", "≅");
                put("&asymp;", "≈");
                put("&ne;", "≠");
                put("&equiv;", "≡");
                put("&le;", "≤");
                put("&ge;", "≥");
                put("&sub;", "⊂");
                put("&sup;", "⊃");
                put("&nsub;", "⊄");
                put("&sube;", "⊆");
                put("&supe;", "⊇");
                put("&oplus;", "⊕");
                put("&otimes;", "⊗");
                put("&perp;", "⊥");
                put("&sdot;", "⋅");
                put("&lceil;", "⌈");
                put("&rceil;", "⌉");
                put("&lfloor;", "⌊");
                put("&rfloor;", "⌋");
                put("&lang;", "〈");
                put("&rang;", "〉");
                put("&loz;", "◊");
                put("&spades;", "♠");
                put("&clubs;", "♣");
                put("&hearts;", "♥");
                put("&diams;", "♦");
                put("&quot;", "\"");
                put("&#39;", "'");
                put("&amp;", "&");
                put("&lt;", "<");
                put("&gt;", ">");
                put("&OElig;", "Œ");
                put("&oelig;", "œ");
                put("&Scaron;", "Š");
                put("&scaron;", "š");
                put("&Yuml;", "Ÿ");
                put("&circ;", "ˆ");
                put("&tilde;", "˜");
                put("&ensp;", "\u2002");
                put("&emsp;", "\u2003");
                put("&thinsp;", "\u2009");
                put("&zwnj;", "\u200c");
                put("&zwj;", "\u200d");
                put("&lrm;", "\u200e");
                put("&rlm;", "\u200f");
                put("&ndash;", "–");
                put("&mdash;", "—");
                put("&lsquo;", "‘");
                put("&rsquo;", "’");
                put("&sbquo;", "‚");
                put("&ldquo;", "“");
                put("&rdquo;", "”");
                put("&bdquo;", "„");
                put("&dagger;", "†");
                put("&Dagger;", "‡");
                put("&permil;", "‰");
                put("&lsaquo;", "‹");
                put("&rsaquo;", "›");
                put("&euro;", "€");
            }
        };
    }

    public static boolean isChinaModel() {
        if (sSalesCode != null) {
            for (String str : CHINA_SALES_CODES) {
                if (str.equals(sSalesCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}
