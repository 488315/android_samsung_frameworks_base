package com.samsung.android.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.appwidget.AppWidgetHostView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.IPackageManager;
import android.content.p002pm.LabeledIntent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.RemoteException;
import android.p009os.SemSystemProperties;
import android.p009os.ServiceManager;
import android.p009os.SystemProperties;
import android.p009os.UserHandle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.android.internal.app.chooser.DisplayResolveInfo;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class SemDualAppManager {
  private static final String ACTION3_PACKAGE_NAME;
  private static final String ADW_PACKAGE_NAME;
  private static final String[] AFW_CAPABLE_LAUNCHER_APPS;
  private static final String BLACKBERRYMESSENGER_PACKAGE_NAME;
  private static final String[] CHINA_SALES_CODES;
  public static final String DA_PROFILE_ID_PROPERTY_NAME = "sys.dualapp.profile_id";
  private static final String DCM_LIVEUX_PACKAGE_NAME;
  static final String[] DUAL_APP_WHITELIST_PACKAGES;
  static final String[] DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA;
  public static final String DUAL_CALLER_PACKAGE_NAME = "callerPackage";
  public static final String DUAL_ORI_SHORTCUT_COMPONENT = "dual_shortcut_component";
  private static final String FACEBOOKMESSENGER_PACKAGE_NAME;
  private static final String FACEBOOK_PACKAGE_NAME;
  private static final String GOOGLE_QUICKSEARCHBOX_PACKGE_NAME;
  private static final String HIKE_PACKAGE_NAME;
  private static final String HOLO_PACKAGE_NAME;
  private static final String ICQ_PACKAGE_NAME;
  private static final String KAKAOTALK_PACKAGE_NAME;
  private static final String KAKAOTALK_SETTINGS_THEME_URI = "kakaotalk://settings/theme/";
  private static final String KIK_PACKAGE_NAME;
  private static final String LINE_PACKAGE_NAME;
  public static final int MAX_DUALAPP_ID = 99;
  private static final String MICROSOFT_PACKAGE_NAME;
  public static final int MIN_DUALAPP_ID = 95;
  private static final String NOUGAT_PACKAGE_NAME;
  private static final String NOVA_PACKAGE_NAME;
  private static final String QQMOBILECHINA_PACKAGE_NAME;
  private static final String QQMOBILEINTERNATIONAL_PACKAGE_NAME;
  private static final String[] SAMSUNG_LAUNCHER_APPS;
  private static final String SEC_DESKTOP_LAUNCHER_PACKGE_NAME;
  private static final String SEC_EASY_LAUNCHER_PACKGE_NAME;
  private static final String SEC_EMERGENCY_LAUNCHER_PACKGE_NAME;
  private static final String SEC_LAUNCHER_PACKGE_NAME;
  private static final boolean SEC_PRODUCT_FEATURE_KNOX_SUPPORT_DUAL_APP = true;
  private static final String SKYPE_PACKAGE_NAME;
  private static final String SMART3_PACKAGE_NAME;
  private static final String SNAPCHAT_PACKAGE_NAME;
  private static final String TAG = "SemDualAppManager";
  private static final String TELEGRAM_PACKAGE_NAME;
  private static final String VIBER_PACKAGE_NAME;
  private static final String WECHAT_PACKAGE_NAME;
  private static final String WEIBO_PACKAGE_NAME;
  private static final String WHATSAPP_PACKAGE_NAME;
  private static final String YAHOOMESSENGER_PACKAGE_NAME;
  private static final String YANDEX_PACKAGE_NAME;
  private static final String ZALO_PACKAGE_NAME;
  private static Context mContext;
  private static boolean mIsChinaModel;
  private static String mSalesCode;
  private Map<ComponentName, Integer> mDuplicateInitialIntents = new HashMap();
  private static SemDualAppManager sDAInstance = null;
  private static ISemDualAppManager mService = null;

  public interface DualAppVersion {
    public static final int DUAL_APP_VERSION_1_0_0 = 100;
    public static final int DUAL_APP_VERSION_1_1_0 = 110;
    public static final int DUAL_APP_VERSION_2_0_0 = 200;
    public static final int DUAL_APP_VERSION_3_0_0 = 300;
    public static final int DUAL_APP_VERSION_3_1_0 = 310;
    public static final int DUAL_APP_VERSION_3_2_0 = 320;
    public static final int DUAL_APP_VERSION_3_3_0 = 330;
    public static final int DUAL_APP_VERSION_3_4_0 = 340;
    public static final int DUAL_APP_VERSION_NONE = 0;
  }

  private interface SepVersionInt {
    public static final int SEP_VER_10_0_INT = 100000;
    public static final int SEP_VER_11_0_INT = 110000;
    public static final int SEP_VER_12_0_INT = 120000;
    public static final int SEP_VER_13_0_INT = 130000;
    public static final int SEP_VER_14_0_INT = 140000;
    public static final int SEP_VER_15_0_INT = 150000;
    public static final int SEP_VER_8_1_INT = 80100;
    public static final int SEP_VER_8_5_INT = 80500;
    public static final int SEP_VER_9_0_INT = 90000;
  }

  static {
    String decodeString = decodeString("Y29tLmZhY2Vib29rLmthdGFuYQ==");
    FACEBOOK_PACKAGE_NAME = decodeString;
    String decodeString2 = decodeString("Y29tLndoYXRzYXBw");
    WHATSAPP_PACKAGE_NAME = decodeString2;
    String decodeString3 = decodeString("Y29tLmZhY2Vib29rLm9yY2E=");
    FACEBOOKMESSENGER_PACKAGE_NAME = decodeString3;
    String decodeString4 = decodeString("Y29tLnRlbmNlbnQubW9iaWxlcXE=");
    QQMOBILECHINA_PACKAGE_NAME = decodeString4;
    String decodeString5 = decodeString("Y29tLnRlbmNlbnQubW9iaWxlcXFp");
    QQMOBILEINTERNATIONAL_PACKAGE_NAME = decodeString5;
    String decodeString6 = decodeString("Y29tLnRlbmNlbnQubW0=");
    WECHAT_PACKAGE_NAME = decodeString6;
    String decodeString7 = decodeString("Y29tLnNreXBlLnJhaWRlcg==");
    SKYPE_PACKAGE_NAME = decodeString7;
    String decodeString8 = decodeString("Y29tLnZpYmVyLnZvaXA=");
    VIBER_PACKAGE_NAME = decodeString8;
    String decodeString9 = decodeString("anAubmF2ZXIubGluZS5hbmRyb2lk");
    LINE_PACKAGE_NAME = decodeString9;
    String decodeString10 = decodeString("Y29tLmJibQ==");
    BLACKBERRYMESSENGER_PACKAGE_NAME = decodeString10;
    String decodeString11 = decodeString("b3JnLnRlbGVncmFtLm1lc3Nlbmdlcg==");
    TELEGRAM_PACKAGE_NAME = decodeString11;
    String decodeString12 = decodeString("Y29tLmtha2FvLnRhbGs=");
    KAKAOTALK_PACKAGE_NAME = decodeString12;
    String decodeString13 = decodeString("Y29tLmJzYi5oaWtl");
    HIKE_PACKAGE_NAME = decodeString13;
    String decodeString14 = decodeString("Y29tLmljcS5tb2JpbGUuY2xpZW50");
    ICQ_PACKAGE_NAME = decodeString14;
    String decodeString15 = decodeString("Y29tLnlhaG9vLm1vYmlsZS5jbGllbnQuYW5kcm9pZC5pbQ==");
    YAHOOMESSENGER_PACKAGE_NAME = decodeString15;
    String decodeString16 = decodeString("Y29tLnppbmcuemFsbw==");
    ZALO_PACKAGE_NAME = decodeString16;
    String decodeString17 = decodeString("Y29tLnNuYXBjaGF0LmFuZHJvaWQ=");
    SNAPCHAT_PACKAGE_NAME = decodeString17;
    String decodeString18 = decodeString("Y29tLnNpbmEud2VpYm8=");
    WEIBO_PACKAGE_NAME = decodeString18;
    String decodeString19 = decodeString("a2lrLmFuZHJvaWQ=");
    KIK_PACKAGE_NAME = decodeString19;
    String decodeString20 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5sYXVuY2hlcg==");
    SEC_LAUNCHER_PACKGE_NAME = decodeString20;
    String decodeString21 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5lYXN5bGF1bmNoZXI=");
    SEC_EASY_LAUNCHER_PACKGE_NAME = decodeString21;
    String decodeString22 = decodeString("Y29tLnNlYy5hbmRyb2lkLmVtZXJnZW5jeWxhdW5jaGVy");
    SEC_EMERGENCY_LAUNCHER_PACKGE_NAME = decodeString22;
    String decodeString23 = decodeString("Y29tLnNlYy5hbmRyb2lkLmFwcC5kZXNrdG9wbGF1bmNoZXI=");
    SEC_DESKTOP_LAUNCHER_PACKGE_NAME = decodeString23;
    String decodeString24 = decodeString("Y29tLmdvb2dsZS5hbmRyb2lkLmdvb2dsZXF1aWNrc2VhcmNoYm94");
    GOOGLE_QUICKSEARCHBOX_PACKGE_NAME = decodeString24;
    String decodeString25 = decodeString("Y29tLnRlc2xhY29pbHN3LmxhdW5jaGVy");
    NOVA_PACKAGE_NAME = decodeString25;
    String decodeString26 = decodeString("Y29tLm1pY3Jvc29mdC5sYXVuY2hlcg==");
    MICROSOFT_PACKAGE_NAME = decodeString26;
    String decodeString27 = decodeString("b3JnLmFkdy5sYXVuY2hlcg==");
    ADW_PACKAGE_NAME = decodeString27;
    String decodeString28 = decodeString("Y29tLmFjdGlvbmxhdW5jaGVyLnBsYXlzdG9yZQ==");
    ACTION3_PACKAGE_NAME = decodeString28;
    String decodeString29 = decodeString("Y29tLm1vYmludC5ob2xvbGF1bmNoZXI=");
    HOLO_PACKAGE_NAME = decodeString29;
    String decodeString30 = decodeString("Z2lubGVtb24uZmxvd2VyZnJlZQ==");
    SMART3_PACKAGE_NAME = decodeString30;
    String decodeString31 = decodeString("Y29tLmNtbmxhdW5jaGVy");
    NOUGAT_PACKAGE_NAME = decodeString31;
    String decodeString32 = decodeString("Y29tLnlhbmRleC5sYXVuY2hlcg==");
    YANDEX_PACKAGE_NAME = decodeString32;
    String decodeString33 = decodeString("Y29tLm50dGRvY29tby5hbmRyb2lkLmRob21l");
    DCM_LIVEUX_PACKAGE_NAME = decodeString33;
    mSalesCode = SemSystemProperties.getSalesCode();
    CHINA_SALES_CODES = new String[] {"CHN", "CHM", "CBK", "CTC", "CHU", "CHC"};
    mIsChinaModel = isChinaModel();
    DUAL_APP_WHITELIST_PACKAGES =
        new String[] {
          decodeString,
          decodeString2,
          decodeString3,
          decodeString4,
          decodeString5,
          decodeString6,
          decodeString18,
          decodeString7,
          decodeString8,
          decodeString9,
          decodeString10,
          decodeString11,
          decodeString12,
          decodeString13,
          decodeString14,
          decodeString15,
          decodeString16,
          decodeString17,
          decodeString19
        };
    DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA =
        new String[] {decodeString6, decodeString4, decodeString18};
    AFW_CAPABLE_LAUNCHER_APPS =
        new String[] {
          decodeString20,
          decodeString21,
          decodeString22,
          decodeString23,
          decodeString24,
          decodeString25,
          decodeString28,
          decodeString29,
          decodeString30,
          decodeString31,
          decodeString32,
          decodeString33,
          decodeString26,
          decodeString27
        };
    SAMSUNG_LAUNCHER_APPS =
        new String[] {decodeString20, decodeString21, decodeString22, decodeString23};
  }

  private SemDualAppManager() {}

  private static ISemDualAppManager getDualAppService() {
    if (mService == null) {
      mService = ISemDualAppManager.Stub.asInterface(ServiceManager.getService("dual_app"));
    }
    return mService;
  }

  public static SemDualAppManager getInstance(Context context) {
    if (sDAInstance == null) {
      synchronized (SemDualAppManager.class) {
        if (sDAInstance == null) {
          sDAInstance = new SemDualAppManager();
          mContext = context;
        }
      }
    }
    return sDAInstance;
  }

  public boolean isWhitelistedPackage(String pkgName) {
    String[] apps;
    int currentUserId = UserHandle.myUserId();
    if ((currentUserId == 0 || isDualAppIdInternal(currentUserId))
        && pkgName != null
        && !"".equalsIgnoreCase(pkgName)
        && (apps = getAllWhitelistedPackages()) != null) {
      for (String pkg : apps) {
        if (pkg.equals(pkgName)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isSupported() {
    int currentUserId = UserHandle.myUserId();
    if (currentUserId == 0 || isDualAppIdInternal(currentUserId)) {
      return true;
    }
    return false;
  }

  public static boolean isDualAppId(int userId) {
    return userId >= 95 && userId <= 99;
  }

  public static String[] getAllWhitelistedPackages() {
    String[] apps = null;
    ISemDualAppManager sdam = getDualAppService();
    if (sdam != null) {
      try {
        apps = sdam.getAllWhitelistedPackages();
      } catch (RemoteException e) {
        Log.m96e(TAG, "getAllWhitelistedPackages : RemoteException occured");
      }
    }
    if (apps == null) {
      Log.m96e(TAG, "getAllWhitelistedPackages : null returned. Return default");
      if (mIsChinaModel) {
        return DUAL_APP_WHITELIST_PACKAGES_FOR_CHINA;
      }
      return DUAL_APP_WHITELIST_PACKAGES;
    }
    return apps;
  }

  public static int getDualAppProfileId() {
    String value = SystemProperties.get(DA_PROFILE_ID_PROPERTY_NAME, null);
    if (value == null) {
      return -10000;
    }
    try {
      if (value.isEmpty()) {
        return -10000;
      }
      int id = Integer.valueOf(value).intValue();
      return id;
    } catch (Exception e) {
      e.printStackTrace();
      return -10000;
    }
  }

  public static List<String> getAllInstalledWhitelistedPackages() {
    ISemDualAppManager sdam = getDualAppService();
    if (sdam != null) {
      try {
        return sdam.getAllInstalledWhitelistedPackages();
      } catch (RemoteException e) {
        Log.m96e(TAG, "getAllInstalledWhitelistedPackages : RemoteException occured");
      }
    }
    Log.m96e(TAG, "getAllInstalledWhitelistedPackages : Can not connect to DualAppManagerService");
    return null;
  }

  public static boolean isInstalledWhitelistedPackage(String pkgName) {
    int currentUserId = UserHandle.myUserId();
    if (currentUserId != 0 && !isDualAppIdInternal(currentUserId)) {
      return false;
    }
    ISemDualAppManager sdam = getDualAppService();
    if (sdam != null) {
      try {
        return sdam.isInstalledWhitelistedPackage(pkgName);
      } catch (RemoteException e) {
        Log.m96e(TAG, "isInstalledWhitelistedPackage : RemoteException occured");
      }
    }
    Log.m96e(TAG, "isInstalledWhitelistedPackage : Can not connect to DualAppManagerService");
    return false;
  }

  public static Bundle updateDualAppData(Context ctx, int userId, Bundle bundle) {
    ISemDualAppManager sdam = getDualAppService();
    if (sdam != null) {
      try {
        String pkgName = ctx.getPackageName();
        return sdam.updateDualAppData(pkgName, userId, bundle);
      } catch (RemoteException e) {
        Log.m96e(TAG, "updateDualAppData : RemoteException occured");
      }
    }
    Log.m96e(TAG, "updateDualAppData : Can not connect to DualAppManagerService");
    return null;
  }

  private static boolean isDualAppIdInternal(int userId) {
    return userId >= 95 && userId <= 99;
  }

  public static boolean shouldAddUserId(Uri uri, int userId) {
    if (uri == null) {
      return false;
    }
    String uriScheme = uri.getScheme();
    String uriAuthority = uri.getAuthority();
    if (!"content".equals(uriScheme)
        || "com.android.contacts".equals(uriAuthority)
        || "com.android.calendar".equals(uriAuthority)
        || "com.android.providers.downloads.documents".equals(uriAuthority)) {
      return false;
    }
    return true;
  }

  /* JADX WARN: Removed duplicated region for block: B:38:0x00f8 A[Catch: Exception -> 0x0139, TryCatch #4 {Exception -> 0x0139, blocks: (B:3:0x0004, B:7:0x0010, B:10:0x0027, B:12:0x0044, B:14:0x004b, B:18:0x0057, B:26:0x006b, B:28:0x006f, B:31:0x0076, B:33:0x0088, B:36:0x0091, B:38:0x00f8, B:39:0x0103, B:52:0x00bf, B:50:0x00dd, B:53:0x009b), top: B:2:0x0004 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void addDualAppAccounts(LinearLayout contents, int resId, int resHeight) {
    AuthenticatorDescription desc;
    int dualAppId;
    AccountManager mgr;
    try {
      int dualAppId2 = getDualAppProfileId();
      if (!isDualAppId(dualAppId2)) {
        return;
      }
      AccountManager mgr2 = AccountManager.get(mContext);
      UserHandle userHandle = new UserHandle(dualAppId2);
      Account[] dualAppAccounts = mgr2.getAccountsAsUser(dualAppId2);
      int N = dualAppAccounts.length;
      if (N == 0) {
        return;
      }
      AuthenticatorDescription[] dualAppDescs =
          AccountManager.get(mContext).getAuthenticatorTypesAsUser(dualAppId2);
      int M = dualAppDescs.length;
      LayoutInflater inflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      int i = 0;
      while (i < N) {
        Account account = dualAppAccounts[i];
        int j = 0;
        while (true) {
          if (j >= M) {
            desc = null;
            break;
          } else if (!account.type.equals(dualAppDescs[j].type)) {
            j++;
          } else {
            AuthenticatorDescription desc2 = dualAppDescs[j];
            desc = desc2;
            break;
          }
        }
        if (desc == null) {
          dualAppId = dualAppId2;
          mgr = mgr2;
        } else {
          Drawable icon = null;
          try {
            if (desc.iconId == 0) {
              dualAppId = dualAppId2;
              mgr = mgr2;
            } else {
              dualAppId = dualAppId2;
              try {
                Context authContext =
                    mContext.createPackageContextAsUser(desc.packageName, 0, userHandle);
                PackageManager pm = mContext.getPackageManager();
                if (pm.semShouldPackIntoIconTray(desc.packageName)) {
                  mgr = mgr2;
                  try {
                    icon =
                        pm.getUserBadgedIcon(
                            pm.semGetDrawableForIconTray(authContext.getDrawable(desc.iconId), 1),
                            userHandle);
                  } catch (PackageManager.NameNotFoundException e) {
                    Log.m102w(TAG, "Bad package name for account type " + desc.type);
                    if (icon == null) {}
                    TextView child =
                        (TextView) inflater.inflate(resId, (ViewGroup) contents, false);
                    child.setText(account.name);
                    int height = mContext.getResources().getDimensionPixelSize(resHeight);
                    icon.setBounds(0, 0, height, height);
                    child.setCompoundDrawablesRelative(icon, null, null, null);
                    contents.addView(child);
                    i++;
                    dualAppId2 = dualAppId;
                    mgr2 = mgr;
                  } catch (Resources.NotFoundException e2) {
                    e = e2;
                    Log.m103w(TAG, "Invalid icon id for account type " + desc.type, e);
                    if (icon == null) {}
                    TextView child2 =
                        (TextView) inflater.inflate(resId, (ViewGroup) contents, false);
                    child2.setText(account.name);
                    int height2 = mContext.getResources().getDimensionPixelSize(resHeight);
                    icon.setBounds(0, 0, height2, height2);
                    child2.setCompoundDrawablesRelative(icon, null, null, null);
                    contents.addView(child2);
                    i++;
                    dualAppId2 = dualAppId;
                    mgr2 = mgr;
                  }
                } else {
                  mgr = mgr2;
                  icon = pm.getUserBadgedIcon(authContext.getDrawable(desc.iconId), userHandle);
                }
              } catch (PackageManager.NameNotFoundException e3) {
                mgr = mgr2;
              } catch (Resources.NotFoundException e4) {
                e = e4;
                mgr = mgr2;
              }
            }
          } catch (PackageManager.NameNotFoundException e5) {
            dualAppId = dualAppId2;
            mgr = mgr2;
          } catch (Resources.NotFoundException e6) {
            e = e6;
            dualAppId = dualAppId2;
            mgr = mgr2;
          }
          if (icon == null) {
            icon = mContext.getPackageManager().getDefaultActivityIcon();
          }
          TextView child22 = (TextView) inflater.inflate(resId, (ViewGroup) contents, false);
          child22.setText(account.name);
          try {
            int height22 = mContext.getResources().getDimensionPixelSize(resHeight);
            icon.setBounds(0, 0, height22, height22);
            child22.setCompoundDrawablesRelative(icon, null, null, null);
            contents.addView(child22);
          } catch (Exception e7) {
            e = e7;
            Log.m102w(TAG, "Exception in addDualAppAccounts " + e);
            return;
          }
        }
        i++;
        dualAppId2 = dualAppId;
        mgr2 = mgr;
      }
    } catch (Exception e8) {
      e = e8;
    }
  }

  public static int getDualAppVersion() {
    return 340;
  }

  public static boolean isDualAppVersionSupported(int dualAppVer) {
    int currentVersion = getDualAppVersion();
    if (currentVersion >= dualAppVer) {
      return true;
    }
    return false;
  }

  public boolean isNeedAddResolveInfoForOtherUser(ActivityInfo ai, Intent ii) {
    int appUserId;
    if (ai == null
        || (((appUserId = UserHandle.getUserId(ai.applicationInfo.uid)) != 0
                && !isDualAppIdInternal(appUserId))
            || ((ii instanceof LabeledIntent) && !isInstalledWhitelistedPackage(ai.packageName)))) {
      return false;
    }
    if (this.mDuplicateInitialIntents.containsKey(ai.getComponentName())) {
      Log.m102w(TAG, "Duplicate activity found for " + ii);
      return false;
    }
    if (isInstalledWhitelistedPackage(ai.packageName)
        && (Intent.ACTION_SEND.equals(ii.getAction())
            || Intent.ACTION_SEND_MULTIPLE.equals(ii.getAction())
            || ((ii.getComponent() != null && isChooserRequired(ii.getComponent().getClassName()))
                || isChinaDualApp(ai.packageName)
                || ((ii.getData() != null && "mqqapi".equals(ii.getData().getScheme()))
                    || isKakaoThemeIntent(ai.packageName, ii))))) {
      this.mDuplicateInitialIntents.put(ai.getComponentName(), Integer.valueOf(appUserId));
      return true;
    }
    return false;
  }

  public boolean isDuplicateEntry(
      PackageManager pm, List<DisplayResolveInfo> target, ActivityInfo ai, Intent ii) {
    int appUserId;
    if (ai == null
        || (((appUserId = UserHandle.getUserId(ai.applicationInfo.uid)) != 0
                && !isDualAppIdInternal(appUserId))
            || ((ii instanceof LabeledIntent) && !isInstalledWhitelistedPackage(ai.packageName)))) {
      return false;
    }
    if (this.mDuplicateInitialIntents.containsKey(ai.getComponentName())) {
      return true;
    }
    if (isInstalledWhitelistedPackage(ai.packageName)
        && (Intent.ACTION_SEND.equals(ii.getAction())
            || Intent.ACTION_SEND_MULTIPLE.equals(ii.getAction())
            || ((ii.getComponent() != null && isChooserRequired(ii.getComponent().getClassName()))
                || isChinaDualApp(ai.packageName)
                || ((ii.getData() != null && "mqqapi".equals(ii.getData().getScheme()))
                    || isKakaoThemeIntent(ai.packageName, ii))))) {
      this.mDuplicateInitialIntents.put(ai.getComponentName(), Integer.valueOf(appUserId));
      addResolveInfoFromOtherUser(pm, target, ai, ii);
    }
    return false;
  }

  public void clearDuplicateMaps() {
    this.mDuplicateInitialIntents.clear();
  }

  /* JADX WARN: Removed duplicated region for block: B:24:0x0053  */
  /* JADX WARN: Removed duplicated region for block: B:26:0x0074  */
  /* JADX WARN: Removed duplicated region for block: B:28:0x008d  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void addResolveInfoFromOtherUser(
      PackageManager pm, List<DisplayResolveInfo> target, ActivityInfo ai, Intent ii) {
    int userId;
    ActivityInfo ai2;
    ResolveInfo ri;
    ActivityInfo ai3 = ai;
    int dualAppUserId = getDualAppProfileId();
    IPackageManager iPackageManager =
        IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    if (ai3 != null && isDualAppId(dualAppUserId)) {
      if (UserHandle.getUserId(ai3.applicationInfo.uid) != 0) {
        userId = 0;
      } else {
        userId = dualAppUserId;
      }
      ResolveInfo ri2 = null;
      ComponentName cn = ai.getComponentName();
      if (cn != null) {
        try {
          ai3 = iPackageManager.getActivityInfo(cn, 0L, userId);
        } catch (RemoteException e) {
        }
        try {
          ri2 = new ResolveInfo();
          ri2.activityInfo = ai3;
        } catch (RemoteException e2) {
          ai2 = ai3;
          ri = ri2;
          if (ii instanceof LabeledIntent) {}
          if (ai2 == null) {}
        }
      }
      ai2 = ai3;
      ri = ri2;
      if (ii instanceof LabeledIntent) {
        LabeledIntent li = (LabeledIntent) ii;
        ri.resolvePackageName = li.getSourcePackage();
        ri.labelRes = li.getLabelResource();
        ri.nonLocalizedLabel = li.getNonLocalizedLabel();
        ri.icon = li.getIconResource();
        ri.iconResourceId = ri.icon;
      }
      if (ai2 == null) {
        target.add(new DisplayResolveInfo(ii, ri, ri.loadLabel(pm), null, ii, null));
      }
    }
  }

  public static void drawDualAppBadge(
      final Context context, final AppWidgetHostView view, UserHandle user) {
    new Handler(Looper.getMainLooper())
        .postDelayed(
            new Runnable() { // from class: com.samsung.android.app.SemDualAppManager.1
              @Override // java.lang.Runnable
              public void run() {
                try {
                  ImageView dualAppBadge = new ImageView(Context.this);
                  int density = Context.this.getResources().getDisplayMetrics().densityDpi;
                  Drawable badgeicon =
                      Resources.getSystem()
                          .getDrawableForDensity(C4337R.drawable.ic_dualapp_widget_badge, density);
                  if (badgeicon != null) {
                    dualAppBadge.setImageDrawable(badgeicon);
                    FrameLayout.LayoutParams params =
                        new FrameLayout.LayoutParams(
                            badgeicon.getIntrinsicWidth(), badgeicon.getIntrinsicHeight());
                    params.gravity = 85;
                    view.addView(dualAppBadge, params);
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            },
            1000L);
  }

  public static boolean isAfwSupportLauncher(String launcherPkgName) {
    if (launcherPkgName != null) {
      for (String pkg : AFW_CAPABLE_LAUNCHER_APPS) {
        if (pkg.equals(launcherPkgName)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isSamsungLauncher(String launcherPkgName) {
    if (launcherPkgName != null) {
      for (String pkg : SAMSUNG_LAUNCHER_APPS) {
        if (pkg.equals(launcherPkgName)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isChooserRequired(String clsName) {
    if ("com.tencent.mm.plugin.base.stub.WXEntryActivity".equals(clsName)
        || "com.tencent.open.agent.AgentActivity".equals(clsName)
        || "com.tencent.mm.plugin.base.stub.WXPayEntryActivity".equals(clsName)
        || "com.sina.weibo.SSOActivity".equals(clsName)) {
      return true;
    }
    return false;
  }

  public static boolean isChinaDualApp(String packageName) {
    if (QQMOBILECHINA_PACKAGE_NAME.equals(packageName)
        || QQMOBILEINTERNATIONAL_PACKAGE_NAME.equals(packageName)
        || WEIBO_PACKAGE_NAME.equals(packageName)
        || WECHAT_PACKAGE_NAME.equals(packageName)) {
      return true;
    }
    return false;
  }

  private static boolean isKakaoThemeIntent(String packageName, Intent intent) {
    if (KAKAOTALK_PACKAGE_NAME.equals(packageName)
        && intent.getDataString() != null
        && intent.getDataString().contains(KAKAOTALK_SETTINGS_THEME_URI)) {
      return true;
    }
    return false;
  }

  public static boolean isChinaModel() {
    if (mSalesCode != null) {
      for (String code : CHINA_SALES_CODES) {
        if (code.equals(mSalesCode)) {
          return true;
        }
      }
    }
    return false;
  }

  private static String decodeString(String in) {
    byte[] out = Base64.decode(in, 0);
    return new String(out, StandardCharsets.UTF_8);
  }

  public static boolean shouldRemove(ResolveInfo resolveInfo) {
    if (isDualAppId(resolveInfo.userHandle.getIdentifier())) {
      if (resolveInfo.activityInfo.packageName.equals("com.android.settings")
          || resolveInfo.activityInfo.packageName.equals("com.android.chrome")) {
        return true;
      }
      return false;
    }
    return false;
  }
}
