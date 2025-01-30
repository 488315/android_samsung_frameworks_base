package com.samsung.android.share;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.net.Uri;
import android.p009os.Build;
import android.p009os.Bundle;
import android.p009os.SemSystemProperties;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import com.android.internal.C4337R;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
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

  public SemShareCommon(
      Context context,
      Intent intent,
      boolean isDeviceDefault,
      int launchedFromUid,
      List<Intent> extraIntentList) {
    this(context, intent, isDeviceDefault, false, false, launchedFromUid, extraIntentList);
  }

  public SemShareCommon(
      Context context,
      Intent intent,
      boolean isDeviceDefault,
      boolean alwaysUseOption,
      boolean hasFilteredItem,
      int launchedFromUid,
      List<Intent> extraIntentList) {
    this.mSupportedFeatures = 0;
    this.mIconChangePlayer = 0;
    this.mIconScreenMirroring = 0;
    this.mIconScreenSharing = 0;
    this.mIconQuickConnect = 0;
    this.mIconPrint = 0;
    this.mContext = context;
    this.mLaunchedFromUid = launchedFromUid;
    this.mExtraIntentList = extraIntentList;
    this.mDeviceDefault = isDeviceDefault;
    this.mPm = context.getPackageManager();
    if (alwaysUseOption || hasFilteredItem) {
      checkButtonsFeature();
      checkResolverGuideFeature(intent);
    }
    checkLoggingFeature();
    Log.m94d(TAG, "SShare Support Feature: " + this.mSupportedFeatures);
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

  private void setSupportedFeature(int item) {
    this.mSupportedFeatures |= item;
  }

  public boolean isFeatureSupported(int item) {
    return (this.mSupportedFeatures & item) != 0;
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
    int forceDisalbe = intent.getIntExtra(SemShareConstants.SIMPLE_SHARING_FORCE_DISABLE, 0);
    return forceDisalbe == 1;
  }

  private boolean isEmergencyOrUPSModeEnabled() {
    Context context = this.mContext;
    if (context == null) {
      return false;
    }
    SemEmergencyManager em = SemEmergencyManager.getInstance(context);
    boolean isEmergencyMode = false;
    boolean isUltraPowerSavingMode = false;
    if (em != null) {
      isEmergencyMode = em.isEmergencyMode() && !em.checkModeType(512);
      isUltraPowerSavingMode = em.isEmergencyMode() && em.checkModeType(512);
    }
    return isEmergencyMode || isUltraPowerSavingMode;
  }

  private boolean isIntentTypeSupportRemoteShare(Intent intent) {
    String action = intent.getAction();
    if ((Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action))
        && isIntentUriDataIValidCheck(intent)) {
      return true;
    }
    return false;
  }

  private boolean hasExtraIntentUriInfo() {
    if (this.mExtraIntentList != null) {
      for (int i = 0; i < this.mExtraIntentList.size(); i++) {
        Bundle extraBundle = this.mExtraIntentList.get(i).getExtras();
        if (extraBundle != null) {
          Uri uri = (Uri) extraBundle.getParcelable(Intent.EXTRA_STREAM);
          if (uri != null) {
            return true;
          }
        }
      }
      return false;
    }
    return false;
  }

  private boolean isIntentUriDataIValidCheck(Intent intent) {
    String action = intent.getAction();
    if (Intent.ACTION_SEND.equals(action)) {
      Uri uri = null;
      Bundle extraBundle = intent.getExtras();
      if (extraBundle != null) {
        uri = (Uri) extraBundle.getParcelable(Intent.EXTRA_STREAM);
      }
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
    ArrayList<Uri> uriArray = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
    if (uriArray == null) {
      return false;
    }
    int numOfUri = uriArray.size();
    for (int i = 0; i < numOfUri; i++) {
      if (uriArray.get(i) != null) {
        "com.android.contacts".equals(uriArray.get(i).getEncodedAuthority());
        return true;
      }
    }
    return false;
  }

  private boolean getResolverGuideSupportState(Intent intent) {
    return false;
  }

  private boolean getButtonsSupportState() {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (contentResolver == null
        || Settings.System.getInt(contentResolver, "default_app_selection_option", 0) != 1) {
      return false;
    }
    return true;
  }

  private boolean getButtonShapeSupportState() {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    if (contentResolver == null
        || Settings.System.getInt(contentResolver, "show_button_background", 0) != 1) {
      return false;
    }
    return true;
  }

  private boolean getQuickConnectSupportState() {
    try {
      ApplicationInfo ai = this.mPm.getApplicationInfo("com.samsung.android.oneconnect", 0);
      if (ai != null && (ai.flags & 1) == 0 && !SemShareConstants.ENABLE_QUICKCONNECT_D2D) {
        Log.m102w(TAG, "getQuickConnectSupportState - oneconnect isn't preload app");
        return false;
      }
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      Log.m102w(TAG, "getQuickConnectSupportState - oneconnect isn't installed");
      return false;
    }
  }

  private boolean getShareLinkSupportState(Intent intent) {
    boolean intentSupport = isIntentTypeSupportRemoteShare(intent);
    boolean knoxMode = isKnoxModeEnabled();
    boolean emergencyMode = isEmergencyOrUPSModeEnabled();
    boolean forceDisable = isForceSimpleSharingDisable(intent);
    if (intentSupport && !knoxMode && !emergencyMode && !forceDisable) {
      return true;
    }
    Log.m94d(
        TAG,
        " intentSupport = "
            + intentSupport
            + " knoxMode = "
            + knoxMode
            + " emergencyMode = "
            + emergencyMode
            + " forceDisable = "
            + forceDisable);
    return false;
  }

  private void checkSamsungConnectInfo() {
    this.mSCSupport = getQuickConnectSupportState();
    this.mSCVersion = getSamsungConnectVersion();
  }

  public int getSamsungConnectVersion() {
    PackageInfo pi = null;
    try {
      pi = this.mPm.getPackageInfo("com.samsung.android.oneconnect", 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    if (pi != null) {
      return pi.versionCode;
    }
    return -1;
  }

  public boolean isShowSwipeUpGuide() {
    return false;
  }

  private boolean getShareToDeviceSupportState() {
    if (Build.VERSION.SEM_PLATFORM_INT >= 80500) {
      return !this.mSCSupport || this.mSCVersion >= 150000000;
    }
    return false;
  }

  public int getFileIconTypeFromExtension(String extension) {
    HashMap<String, Integer> map = getFileIconExtensionMap();
    if (map.containsKey(extension)) {
      return map.get(extension).intValue();
    }
    return map.get("etc").intValue();
  }

  private HashMap<String, Integer> getFileIconExtensionMap() {
    HashMap<String, Integer> map = new HashMap<>();
    Integer valueOf = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_amr);
    map.put("m4a", valueOf);
    map.put("amr", valueOf);
    map.put("awb", valueOf);
    map.put("3ga", valueOf);
    map.put("apk", Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_apk));
    map.put("vcf", Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_contact));
    Integer valueOf2 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_gallery);
    map.put("jpg", valueOf2);
    map.put("jpeg", valueOf2);
    map.put("mv5", valueOf2);
    map.put("gif", valueOf2);
    map.put("png", valueOf2);
    map.put("bmp", valueOf2);
    map.put("wbmp", valueOf2);
    map.put("webp", valueOf2);
    map.put("golf", valueOf2);
    Integer valueOf3 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_hwp);
    map.put("hwp", valueOf3);
    map.put("hwpx", valueOf3);
    map.put("hwt", valueOf3);
    Integer valueOf4 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_music);
    map.put("mp3", valueOf4);
    map.put("wav", valueOf4);
    map.put("wma", valueOf4);
    map.put("ogg", valueOf4);
    map.put("oga", valueOf4);
    map.put("aac", valueOf4);
    map.put("flac", valueOf4);
    map.put("mp4_a", valueOf4);
    map.put("mpga", valueOf4);
    map.put("3gp_a", valueOf4);
    map.put("asf_a", valueOf4);
    map.put(Telephony.Mms.Part.MSG_ID, valueOf4);
    map.put("mid_a", valueOf4);
    map.put("midi", valueOf4);
    map.put("rtx", valueOf4);
    map.put("ota", valueOf4);
    map.put("xmf", valueOf4);
    map.put("mxmf", valueOf4);
    map.put("rtttl", valueOf4);
    map.put("smf", valueOf4);
    map.put("spmid", valueOf4);
    map.put("imv", valueOf4);
    map.put("pva", valueOf4);
    map.put("qcp", valueOf4);
    map.put("mka", valueOf4);
    map.put("pdf", Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_pdf));
    Integer valueOf5 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_ppt);
    map.put("pps", valueOf5);
    map.put("ppt", valueOf5);
    map.put("pptx", valueOf5);
    map.put("ppsx", valueOf5);
    map.put("application/vnd.google-apps.presentation", valueOf5);
    Integer valueOf6 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_calendar);
    map.put("vcs", valueOf6);
    map.put("ics", valueOf6);
    Integer valueOf7 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_txt);
    map.put("asc", valueOf7);
    map.put("txt", valueOf7);
    map.put("epub", valueOf7);
    map.put("acsm", valueOf7);
    Integer valueOf8 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_video);
    map.put("mpeg", valueOf8);
    map.put("mpg", valueOf8);
    map.put(BnRConstants.VIDEO_FILE_EXTENSION, valueOf8);
    map.put("m4v", valueOf8);
    map.put("3gp", valueOf8);
    map.put("3gpp", valueOf8);
    map.put("3g2", valueOf8);
    map.put("wmv", valueOf8);
    map.put("asf", valueOf8);
    map.put("avi", valueOf8);
    map.put("divx", valueOf8);
    map.put("flv", valueOf8);
    map.put("mkv", valueOf8);
    map.put("sdp", valueOf8);
    map.put("ts", valueOf8);
    map.put("pvv", valueOf8);
    map.put("mov", valueOf8);
    map.put("skm", valueOf8);
    map.put("k3g", valueOf8);
    map.put("ak3g", valueOf8);
    map.put("webm", valueOf8);
    map.put("mts", valueOf8);
    map.put("m2ts", valueOf8);
    map.put("m2t", valueOf8);
    map.put("trp", valueOf8);
    map.put("tp", valueOf8);
    Integer valueOf9 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_word);
    map.put("rtf", valueOf9);
    map.put("doc", valueOf9);
    map.put("docx", valueOf9);
    map.put("dot", valueOf9);
    map.put("dox", valueOf9);
    map.put("hwdt", valueOf9);
    map.put("application/vnd.google-apps.document", valueOf9);
    Integer valueOf10 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_xls);
    map.put("csv", valueOf10);
    map.put("xls", valueOf10);
    map.put("xlsx", valueOf10);
    map.put("xlt", valueOf10);
    map.put("xltx", valueOf10);
    map.put("applicatoin/vnd.google-apps.spreadsheet", valueOf10);
    map.put("zip", Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_zip));
    Integer valueOf11 = Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_notes);
    map.put("sdoc", valueOf11);
    map.put("sdocx", valueOf11);
    map.put("etc", Integer.valueOf(C4337R.drawable.sem_chooser_ic_filetype_etc));
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
      for (String code : CHINA_SALES_CODES) {
        if (code.equals(sSalesCode)) {
          return true;
        }
      }
    }
    return false;
  }
}
