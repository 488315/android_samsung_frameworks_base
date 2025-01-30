package com.android.server.enterprise.browser;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Browser;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.common.EnterprisePermissionChecker;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.browser.IBrowserPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class BrowserPolicy extends IBrowserPolicy.Stub implements EnterpriseServiceCallback {
  public Context mContext;
  public EdmStorageProvider mEdmStorageProvider;
  public EnterpriseDumpHelper mEnterpriseDumpHelper;
  public static final Uri SBROWSER_BOOKMARKS_URI =
      Uri.parse("content://com.sec.android.app.sbrowser.browser/bookmarks");
  public static final Uri CHROME_BOOKMARKS_URI =
      Uri.parse("content://com.android.partnerbookmarks/bookmarks");
  public static final String[] SBROWSER_PROJECTION = {
    KnoxCustomManagerService.f1773ID,
    "url",
    KnoxCustomManagerService.SHORTCUT_TITLE,
    "favicon",
    "editable"
  };
  public HashMap mCache = new HashMap();
  public HashMap mUserCache = new HashMap();
  public final int NUM_OF_CONTAINER = 2;
  public final String FIREWALL_POLICY_SERVICE = "FirewallPolicy";
  public EnterpriseDeviceManager mEDM = null;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  public class BrowserProxyCache {
    public final int mContainerId;
    public final int mUserId;
    public int mAdminUid = -1;
    public String mProxySetting = null;

    public BrowserProxyCache(int i, int i2) {
      this.mUserId = i;
      this.mContainerId = i2;
    }

    public void setProxy(int i, String str) {
      if (str != null) {
        this.mAdminUid = i;
        this.mProxySetting = str;
      }
    }

    public void clear() {
      if (this.mAdminUid != -1) {
        this.mAdminUid = -1;
        this.mProxySetting = null;
      }
    }

    public boolean isAlreadySet() {
      return this.mAdminUid != -1;
    }

    public boolean isOwner(int i) {
      return this.mAdminUid == i;
    }
  }

  public class WebFilteringCache {
    public final int mContainerId;
    public List mUrlBlacklistAllAdmin;
    public boolean mIsUrlBlacklistUpdated = false;
    public boolean mUrlFilterStateCache = false;
    public boolean mIsUrlFilterStateUpdated = false;
    public boolean mUrlFilterReportState = false;
    public boolean mIsUrlFilterReportUpdated = false;

    public WebFilteringCache(int i) {
      this.mUrlBlacklistAllAdmin = null;
      this.mContainerId = i;
      this.mUrlBlacklistAllAdmin = new ArrayList();
    }
  }

  public BrowserPolicy(Context context) {
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
    this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
    loadProxySettings();
  }

  public void loadProxySettings() {
    try {
      for (ContentValues contentValues :
          this.mEdmStorageProvider.getDataByFields(
              "BROWSER_PROXY", null, null, new String[] {"adminUid", "proxyServer"})) {
        String asString = contentValues.getAsString("proxyServer");
        long longValue =
            contentValues.getAsLong("adminUid") != null
                ? contentValues.getAsLong("adminUid").longValue()
                : 0L;
        int containerIdFromLUID = EdmStorageProviderBase.getContainerIdFromLUID(longValue);
        int adminUidFromLUID = EdmStorageProviderBase.getAdminUidFromLUID(longValue);
        int userId = UserHandle.getUserId(adminUidFromLUID);
        if (!this.mCache.containsKey(Integer.valueOf(userId))) {
          this.mCache.put(Integer.valueOf(userId), new HashMap());
        }
        if (!((HashMap) this.mCache.get(Integer.valueOf(userId)))
            .containsKey(Integer.valueOf(containerIdFromLUID))) {
          ((HashMap) this.mCache.get(Integer.valueOf(userId)))
              .put(
                  Integer.valueOf(containerIdFromLUID),
                  new BrowserProxyCache(userId, containerIdFromLUID));
        }
        ((BrowserProxyCache)
                ((HashMap) this.mCache.get(Integer.valueOf(userId)))
                    .get(Integer.valueOf(containerIdFromLUID)))
            .setProxy(adminUidFromLUID, asString);
      }
    } catch (Exception e) {
      Log.e("BrowserPolicy", "loadProxySettings() : failed", e);
    }
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  public final ContextInfo enforceBrowserPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS")));
  }

  public final ContextInfo enforceFirewallPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
  }

  public final ContextInfo enforceFirewallPermissionByContext(ContextInfo contextInfo) {
    return getEDM()
        .enforcePermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
  }

  public final ContextInfo enforceBrowserPermissionByContext(ContextInfo contextInfo) {
    return getEDM()
        .enforcePermissionByContext(
            contextInfo,
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS")));
  }

  public final ContextInfo enforceBrowserProxyPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BROWSER_PROXY")));
  }

  public boolean setBrowserSettingStatus(ContextInfo contextInfo, boolean z, int i) {
    int i2;
    boolean z2;
    int i3 = enforceBrowserPermission(contextInfo).mCallerUid;
    int userId = UserHandle.getUserId(i3);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        i2 = this.mEdmStorageProvider.getInt(i3, "BROWSER", "browserSettings");
      } catch (Exception e) {
        try {
          Log.e(
              "BrowserPolicy",
              "setBrowserSettingStatus(" + i + ") : EdmStorageProvider failed to read Data. ",
              e);
          i2 = 31;
        } catch (Exception e2) {
          Log.e("BrowserPolicy", "setBrowserSettingStatus(" + i + ") : failed. ", e2);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          z2 = false;
        }
      }
      z2 =
          this.mEdmStorageProvider.putInt(
              i3, "BROWSER", "browserSettings", true == z ? i2 | i : i2 & (~i));
      Slog.d(
          "BrowserPolicy",
          "setBrowserSettingStatus() : = " + z2 + ", enable = " + z + ",  setting = " + i);
      if (z2) {
        if (i == 1) {
          SecContentProviderUtil.notifyPolicyChangesAsUser(
              this.mContext, "BrowserPolicy/getPopupsSetting", userId);
        } else if (i == 2) {
          SecContentProviderUtil.notifyPolicyChangesAsUser(
              this.mContext, "BrowserPolicy/getCookiesSetting", userId);
        } else if (i == 4) {
          SecContentProviderUtil.notifyPolicyChangesAsUser(
              this.mContext, "BrowserPolicy/getAutoFillSetting", userId);
          clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            AuditLog.logAsUser(
                5,
                1,
                true,
                Process.myPid(),
                "BrowserPolicy",
                String.format(
                    z
                        ? "Admin %d has enabled Auto Fill Setting"
                        : "Admin %d has disabled Auto Fill Setting",
                    Integer.valueOf(i3)),
                userId);
          } finally {
          }
        } else if (i == 16) {
          SecContentProviderUtil.notifyPolicyChangesAsUser(
              this.mContext, "BrowserPolicy/getJavaScriptSetting", userId);
        }
        Slog.d("BrowserPolicy", "setBrowserSettingStatus() : SecContentProvider updated.");
      }
      return z2;
    } finally {
    }
  }

  public boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) {
    boolean z = true;
    try {
      ArrayList intListAsUser =
          this.mEdmStorageProvider.getIntListAsUser(
              "BROWSER", "browserSettings", Utils.getCallingOrCurrentUserId(contextInfo));
      if (!intListAsUser.isEmpty()) {
        Iterator it = intListAsUser.iterator();
        while (it.hasNext()) {
          int intValue = ((Integer) it.next()).intValue();
          if (intValue >= 0 && i != (intValue & i)) {
            z = false;
            break;
          }
        }
      } else {
        Slog.d("BrowserPolicy", "getBrowserSettingStatus() : No Policy in BrowserPolicy.");
      }
    } catch (Exception e) {
      Log.e("BrowserPolicy", "getBrowserSettingStatus() : failed. ", e);
    }
    Log.i("BrowserPolicy", "getBrowserSettingStatus(" + i + ") : " + z);
    return z;
  }

  /* JADX WARN: Removed duplicated region for block: B:28:0x00c0 A[Catch: Exception -> 0x00cf, TRY_LEAVE, TryCatch #0 {Exception -> 0x00cf, blocks: (B:21:0x0081, B:23:0x009d, B:28:0x00c0, B:33:0x00a6, B:35:0x00bb), top: B:20:0x0081 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean setHttpProxy(ContextInfo contextInfo, String str) {
    String str2;
    String str3;
    BrowserProxyCache browserProxyCache;
    boolean putValues;
    ContextInfo enforceBrowserProxyPermission = enforceBrowserProxyPermission(contextInfo);
    int i = enforceBrowserProxyPermission.mCallerUid;
    int i2 = enforceBrowserProxyPermission.mContainerId;
    int userId = UserHandle.getUserId(i);
    boolean z = false;
    if (str == null) {
      return false;
    }
    if (str.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
      String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
      str3 = split[0];
      str2 = split[1];
    } else {
      str2 = "80";
      str3 = str;
    }
    if (!validateProxyParameters(str3, str2)) {
      return false;
    }
    String trim = str.trim();
    if (trim.isEmpty()) {
      return false;
    }
    if (!this.mCache.containsKey(Integer.valueOf(userId))) {
      this.mCache.put(Integer.valueOf(userId), new HashMap());
    }
    if (!((HashMap) this.mCache.get(Integer.valueOf(userId))).containsKey(Integer.valueOf(i2))) {
      ((HashMap) this.mCache.get(Integer.valueOf(userId)))
          .put(Integer.valueOf(i2), new BrowserProxyCache(userId, i2));
    }
    try {
      browserProxyCache =
          (BrowserProxyCache)
              ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(Integer.valueOf(i2));
    } catch (Exception e) {
      Log.e("BrowserPolicy", "setHttpProxy() : failed.", e);
    }
    if (browserProxyCache.isAlreadySet() && !browserProxyCache.isOwner(i)) {
      putValues = false;
      if (putValues) {
        SecContentProviderUtil.notifyPolicyChangesAsUser(
            this.mContext, "BrowserPolicy/getHttpProxy", userId);
        Slog.d("BrowserPolicy", "setHttpProxy() : SecContentProvider updated.");
      }
      z = putValues;
      Log.i("BrowserPolicy", "setHttpProxy(" + str + ") : " + z);
      return z;
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("proxyServer", trim);
    putValues = this.mEdmStorageProvider.putValues(i, i2, "BROWSER_PROXY", contentValues);
    if (putValues) {
      browserProxyCache.setProxy(i, trim);
    }
    if (putValues) {}
    z = putValues;
    Log.i("BrowserPolicy", "setHttpProxy(" + str + ") : " + z);
    return z;
  }

  public boolean clearHttpProxy(ContextInfo contextInfo) {
    boolean z;
    ContextInfo enforceBrowserProxyPermission = enforceBrowserProxyPermission(contextInfo);
    int i = enforceBrowserProxyPermission.mCallerUid;
    int i2 = enforceBrowserProxyPermission.mContainerId;
    int userId = UserHandle.getUserId(i);
    if (this.mCache.containsKey(Integer.valueOf(userId))
        && ((HashMap) this.mCache.get(Integer.valueOf(userId))).containsKey(Integer.valueOf(i2))
        && ((BrowserProxyCache)
                ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(Integer.valueOf(i2)))
            .isOwner(i)) {
      z = this.mEdmStorageProvider.removeByAdmin("BROWSER_PROXY", i, i2);
      if (z) {
        ((BrowserProxyCache)
                ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(Integer.valueOf(i2)))
            .clear();
        ((HashMap) this.mCache.get(Integer.valueOf(userId))).remove(Integer.valueOf(i2));
        if (((HashMap) this.mCache.get(Integer.valueOf(userId))).isEmpty()) {
          this.mCache.remove(Integer.valueOf(userId));
        }
        SecContentProviderUtil.notifyPolicyChangesAsUser(
            this.mContext, "BrowserPolicy/getHttpProxy", userId);
        Slog.d("BrowserPolicy", "clearHttpProxy() : SecContentProvider updated.");
      }
    } else {
      z = false;
    }
    Log.i("BrowserPolicy", "clearHttpProxy() : " + z);
    return z;
  }

  public String getHttpProxy(ContextInfo contextInfo) {
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    int i = contextInfo.mContainerId;
    String str =
        (this.mCache.containsKey(Integer.valueOf(callingOrCurrentUserId))
                && ((HashMap) this.mCache.get(Integer.valueOf(callingOrCurrentUserId)))
                    .containsKey(Integer.valueOf(i)))
            ? ((BrowserProxyCache)
                    ((HashMap) this.mCache.get(Integer.valueOf(callingOrCurrentUserId)))
                        .get(Integer.valueOf(i)))
                .mProxySetting
            : null;
    Slog.d("BrowserPolicy", "getHttpProxy() : " + str);
    return str;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    int userId = UserHandle.getUserId(i);
    if (this.mCache.containsKey(Integer.valueOf(userId))
        && ((HashMap) this.mCache.get(Integer.valueOf(userId))).containsKey(0)
        && ((BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(0))
            .isOwner(i)) {
      ((BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(0)).clear();
      ((HashMap) this.mCache.get(Integer.valueOf(userId))).remove(0);
      if (((HashMap) this.mCache.get(Integer.valueOf(userId))).isEmpty()) {
        this.mCache.remove(Integer.valueOf(userId));
      }
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {
    removeAdmin(new ContextInfo(i, 0));
  }

  public static boolean validateProxyParameters(String str, String str2) {
    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
      try {
        int parseInt = Integer.parseInt(str2);
        if (parseInt > 0 && parseInt <= 65535) {
          StringBuilder sb = new StringBuilder();
          sb.append("^$|^[");
          sb.append("a-zA-Z0-9\\_");
          sb.append("]+(\\-[");
          sb.append("a-zA-Z0-9\\_");
          sb.append("]+)*(\\.[");
          sb.append("a-zA-Z0-9\\_");
          sb.append("]+(\\-[");
          sb.append("a-zA-Z0-9\\_");
          sb.append("]+)*)*$");
          return Pattern.compile(sb.toString()).matcher(str).matches();
        }
      } catch (NumberFormatException unused) {
      }
    }
    return false;
  }

  public int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
    return setURLFilterEnabled(enforceFirewallPermission(contextInfo), z);
  }

  public int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
    return setURLFilterEnabled(enforceBrowserPermission(contextInfo), z);
  }

  public final int setURLFilterEnabled(ContextInfo contextInfo, boolean z) {
    Log.d("BrowserPolicy", "setURLFilterEnabled(" + z + ")");
    int i = contextInfo.mContainerId;
    int i2 = contextInfo.mCallerUid;
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    ContentValues contentValues = new ContentValues();
    contentValues.put("filtering", String.valueOf(z));
    boolean putValues =
        this.mEdmStorageProvider.putValues(i2, i, "WebFilterSettingsTable", contentValues);
    if (putValues) {
      WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
      synchronized (webFilteringCache.mUrlBlacklistAllAdmin) {
        webFilteringCache.mUrlBlacklistAllAdmin.clear();
        webFilteringCache.mIsUrlFilterStateUpdated = false;
        webFilteringCache.mIsUrlBlacklistUpdated = false;
        refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
        if (!z) {
          this.mEdmStorageProvider.removeByAdmin("WebFilterTable", i2);
        }
      }
      SecContentProviderUtil.notifyPolicyChangesAsUser(
          this.mContext, "FirewallPolicy/getURLFilterEnabled", callingOrCurrentUserId);
    }
    Slog.d("BrowserPolicy", "setURLFilterEnabled : " + z);
    return putValues ? 1 : 0;
  }

  public boolean getURLFilterEnabledEnforcingFirewallPermission(
      ContextInfo contextInfo, boolean z, boolean z2) {
    try {
      if (z2) {
        contextInfo = enforceFirewallPermission(contextInfo);
      } else {
        contextInfo = enforceFirewallPermissionByContext(contextInfo);
      }
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "getURLFilterEnabled");
    }
    return getURLFilterEnabled(contextInfo, z, z2);
  }

  public boolean getURLFilterEnabledEnforcingBrowserPermission(
      ContextInfo contextInfo, boolean z, boolean z2) {
    try {
      if (z2) {
        contextInfo = enforceBrowserPermission(contextInfo);
      } else {
        contextInfo = enforceBrowserPermissionByContext(contextInfo);
      }
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "getURLFilterEnabled");
    }
    return getURLFilterEnabled(contextInfo, z, z2);
  }

  public final boolean getURLFilterEnabled(ContextInfo contextInfo, boolean z, boolean z2) {
    int i = contextInfo.mContainerId;
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    if (z) {
      contextInfo = new ContextInfo(-1, i);
    }
    WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
    if (!webFilteringCache.mIsUrlFilterStateUpdated) {
      webFilteringCache.mUrlFilterStateCache = getUrlFilterState(contextInfo, "filtering");
      webFilteringCache.mIsUrlFilterStateUpdated = true;
      refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
    }
    return webFilteringCache.mUrlFilterStateCache;
  }

  public int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List list) {
    return setURLFilterList(enforceFirewallPermission(contextInfo), list);
  }

  public int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List list) {
    return setURLFilterList(enforceBrowserPermission(contextInfo), list);
  }

  public final int setURLFilterList(ContextInfo contextInfo, List list) {
    int i = contextInfo.mContainerId;
    int i2 = contextInfo.mCallerUid;
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    if (list == null) {
      return 0;
    }
    boolean saveUrlBlackList = saveUrlBlackList(i, i2, list);
    if (saveUrlBlackList) {
      WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
      synchronized (webFilteringCache.mUrlBlacklistAllAdmin) {
        webFilteringCache.mUrlBlacklistAllAdmin.clear();
        webFilteringCache.mIsUrlBlacklistUpdated = false;
        refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
      }
      SecContentProviderUtil.notifyPolicyChangesAsUser(
          this.mContext, "FirewallPolicy/getURLFilterList", callingOrCurrentUserId);
    }
    return saveUrlBlackList ? 1 : 0;
  }

  public List getURLFilterListEnforcingFirewallPermission(
      ContextInfo contextInfo, boolean z, boolean z2) {
    try {
      if (z2) {
        contextInfo = enforceFirewallPermission(contextInfo);
      } else {
        contextInfo = enforceFirewallPermissionByContext(contextInfo);
      }
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "getURLFilterList");
    }
    return getURLFilterList(contextInfo, z, z2);
  }

  public List getURLFilterListEnforcingBrowserPermission(
      ContextInfo contextInfo, boolean z, boolean z2) {
    try {
      if (z2) {
        contextInfo = enforceBrowserPermission(contextInfo);
      } else {
        contextInfo = enforceBrowserPermissionByContext(contextInfo);
      }
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "getURLFilterList");
    }
    return getURLFilterList(contextInfo, z, z2);
  }

  public final List getURLFilterList(ContextInfo contextInfo, boolean z, boolean z2) {
    List list;
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    Log.d(
        "BrowserPolicy",
        "getURLFilterList => userId "
            + callingOrCurrentUserId
            + " callerUid "
            + contextInfo.mCallerUid
            + " allAdmins "
            + z);
    WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
    if (z) {
      synchronized (webFilteringCache.mUrlBlacklistAllAdmin) {
        if (!webFilteringCache.mIsUrlBlacklistUpdated) {
          list = getUrlBlackList(contextInfo, true);
          if (list != null && !list.isEmpty()) {
            Log.d("BrowserPolicy", "getUrlBlackList - synchronized");
            webFilteringCache.mUrlBlacklistAllAdmin.clear();
            webFilteringCache.mUrlBlacklistAllAdmin.addAll(list);
            webFilteringCache.mIsUrlBlacklistUpdated = true;
          }
        } else {
          list = webFilteringCache.mUrlBlacklistAllAdmin;
        }
      }
      return list;
    }
    return getUrlBlackList(contextInfo, false);
  }

  public boolean isUrlBlocked(ContextInfo contextInfo, String str) {
    boolean z = false;
    if (!getURLFilterEnabled(contextInfo, true, false)) {
      Log.d("BrowserPolicy", "isUrlBlocked - Policy disabled");
      return false;
    }
    List uRLFilterList = getURLFilterList(contextInfo, true, false);
    Log.d("BrowserPolicy", "urlBlacklist: " + uRLFilterList);
    if (uRLFilterList != null && !uRLFilterList.isEmpty()) {
      Iterator it = uRLFilterList.iterator();
      boolean z2 = false;
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        String replace = ((String) it.next()).replace("*", ".*");
        String trim = str.trim();
        String trim2 = replace.trim();
        if (trim2.endsWith("/")) {
          trim2 = trim2.substring(0, trim2.length() - 1);
        }
        if (trim.endsWith("/")) {
          trim = trim.substring(0, trim.length() - 1);
        }
        if (trim.startsWith("http://")) {
          trim = trim.substring(7);
        } else if (trim.startsWith("https://")) {
          trim = trim.substring(8);
        }
        if (trim2.startsWith("https://")) {
          trim2 = trim2.substring(8);
        } else if (trim2.startsWith("http://")) {
          trim2 = trim2.substring(7);
        }
        try {
          Pattern compile = Pattern.compile(trim2);
          z2 = compile.matcher(trim).matches();
          if (!z2 && trim.contains("/")) {
            z2 = compile.matcher(trim.substring(0, trim.indexOf(47))).matches();
          }
        } catch (ArrayIndexOutOfBoundsException unused) {
          Log.e("BrowserPolicy", "isUrlBlocked - Regex is not valid");
        }
        if (z2) {
          if (getURLFilterReportEnabled(contextInfo, true, false)) {
            saveURLBlockedReport(contextInfo, str);
          }
        }
      }
      z = z2;
    }
    Log.d("BrowserPolicy", "isUrlBlocked: " + z);
    return z;
  }

  public void saveURLBlockedReportEnforcingFirewallPermission(ContextInfo contextInfo, String str) {
    try {
      enforceFirewallPermissionByContext(contextInfo);
    } catch (SecurityException unused) {
      Log.d("BrowserPolicy", "Enforcing Chrome permission");
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "saveURLBlockedReport");
    }
    saveURLBlockedReport(contextInfo, str);
  }

  public final void saveURLBlockedReport(ContextInfo contextInfo, String str) {
    Log.d("BrowserPolicy", "saveURLBlockedReport");
    Calendar calendar = Calendar.getInstance();
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    Log.d("BrowserPolicy", "saveURLBlockedReport > userId = " + callingOrCurrentUserId);
    long timeInMillis = calendar.getTimeInMillis();
    ContentValues contentValues = new ContentValues();
    contentValues.put("url", str);
    contentValues.put("time", String.valueOf(timeInMillis));
    contentValues.put("containerID", (Integer) 0);
    contentValues.put("userID", Integer.valueOf(callingOrCurrentUserId));
    if (this.mEdmStorageProvider.putValuesNoUpdate("WebFilterLogTable", contentValues)) {
      return;
    }
    Log.d("BrowserPolicy", "isUrlBlocked - Failed on inserting log");
  }

  public int setURLFilterReportEnabledEnforcingFirewallPermission(
      ContextInfo contextInfo, boolean z) {
    return setURLFilterReportEnabled(enforceFirewallPermission(contextInfo), z);
  }

  public int setURLFilterReportEnabledEnforcingBrowserPermission(
      ContextInfo contextInfo, boolean z) {
    return setURLFilterReportEnabled(enforceBrowserPermission(contextInfo), z);
  }

  public final int setURLFilterReportEnabled(ContextInfo contextInfo, boolean z) {
    int i = contextInfo.mContainerId;
    int i2 = contextInfo.mCallerUid;
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    ContentValues contentValues = new ContentValues();
    contentValues.put("logging", String.valueOf(z));
    boolean putValues =
        this.mEdmStorageProvider.putValues(i2, i, "WebFilterSettingsTable", contentValues);
    if (putValues) {
      Log.d(
          "BrowserPolicy",
          "setURLFilterReportEnabled - Added to database, refreshing cache userId= "
              + callingOrCurrentUserId);
      WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
      webFilteringCache.mIsUrlFilterReportUpdated = false;
      webFilteringCache.mIsUrlBlacklistUpdated = false;
      refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
      SecContentProviderUtil.notifyPolicyChangesAsUser(
          this.mContext, "FirewallPolicy/getURLFilterReportEnabled", callingOrCurrentUserId);
    }
    boolean uRLFilterReportEnabled = getURLFilterReportEnabled(contextInfo, true, false);
    if (!z || !uRLFilterReportEnabled) {
      Log.d("BrowserPolicy", "setURLFilterReportEnabled - Clean url report");
      this.mEdmStorageProvider.deleteDataByFields(
          "WebFilterLogTable",
          new String[] {"containerID", "userID"},
          new String[] {String.valueOf(0), String.valueOf(callingOrCurrentUserId)});
    }
    Log.d("BrowserPolicy", "setURLFilterReportEnabled - return = " + putValues);
    return putValues ? 1 : 0;
  }

  public boolean getURLFilterReportEnabledEnforcingFirewallPermission(
      ContextInfo contextInfo, boolean z, boolean z2) {
    try {
      if (z2) {
        contextInfo = enforceFirewallPermission(contextInfo);
      } else {
        contextInfo = enforceFirewallPermissionByContext(contextInfo);
      }
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "getURLFilterReportEnabled");
    }
    return getURLFilterReportEnabled(contextInfo, z, z2);
  }

  public boolean getURLFilterReportEnabledEnforcingBrowserPermission(
      ContextInfo contextInfo, boolean z, boolean z2) {
    try {
      if (z2) {
        contextInfo = enforceBrowserPermission(contextInfo);
      } else {
        contextInfo = enforceBrowserPermissionByContext(contextInfo);
      }
    } catch (SecurityException unused) {
      EnterprisePermissionChecker.getInstance(this.mContext)
          .enforceAuthorization("FirewallPolicy", "getURLFilterReportEnabled");
    }
    return getURLFilterReportEnabled(contextInfo, z, z2);
  }

  public final boolean getURLFilterReportEnabled(ContextInfo contextInfo, boolean z, boolean z2) {
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
    if (!webFilteringCache.mIsUrlFilterReportUpdated) {
      webFilteringCache.mUrlFilterReportState = getUrlFilterState(contextInfo, "logging");
      webFilteringCache.mIsUrlFilterReportUpdated = true;
      Log.d(
          "WebFilteringCache",
          "cache.mUrlFilterReportState=> " + webFilteringCache.mUrlFilterReportState);
      refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
    }
    return webFilteringCache.mUrlFilterReportState;
  }

  public List getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) {
    return getURLFilterReport(enforceFirewallPermission(contextInfo));
  }

  public List getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) {
    return getURLFilterReport(enforceBrowserPermission(contextInfo));
  }

  public final List getURLFilterReport(ContextInfo contextInfo) {
    Log.d("BrowserPolicy", "getURLFilterReport()");
    ArrayList arrayList = new ArrayList();
    String[] strArr = {
      String.valueOf(0), String.valueOf(Utils.getCallingOrCurrentUserId(contextInfo))
    };
    EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
    ArrayList<ContentValues> dataByFields =
        edmStorageProvider.getDataByFields(
            "WebFilterLogTable",
            new String[] {"containerID", "userID"},
            strArr,
            new String[] {"url", "time"});
    if (dataByFields == null) {
      Log.d("BrowserPolicy", "getURLFilterReport - cvList is null");
      return null;
    }
    for (ContentValues contentValues : dataByFields) {
      String asString = contentValues.getAsString("time");
      String asString2 = contentValues.getAsString("url");
      if (asString != null && asString2 != null) {
        arrayList.add(asString.concat(XmlUtils.STRING_ARRAY_SEPARATOR).concat(asString2));
      }
    }
    return arrayList;
  }

  public final boolean getUrlFilterState(ContextInfo contextInfo, String str) {
    boolean z;
    Log.d(
        "BrowserPolicy",
        "getUrlFilterState - uid:"
            + contextInfo.mCallerUid
            + " containerId:"
            + contextInfo.mContainerId
            + " column:"
            + str);
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
    StringBuilder sb = new StringBuilder();
    sb.append("getUrlFilterState - userId: ");
    sb.append(callingOrCurrentUserId);
    Log.d("BrowserPolicy", sb.toString());
    List valuesListAsUser =
        this.mEdmStorageProvider.getValuesListAsUser(
            contextInfo.mContainerId,
            "WebFilterSettingsTable",
            new String[] {str},
            callingOrCurrentUserId);
    if (valuesListAsUser != null) {
      Iterator it = valuesListAsUser.iterator();
      while (it.hasNext()) {
        String asString = ((ContentValues) it.next()).getAsString(str);
        if (asString != null && asString.equals("true")) {
          z = true;
          break;
        }
      }
    }
    z = false;
    Log.d("BrowserPolicy", "getUrlFilterState - ret: " + z);
    return z;
  }

  public final boolean saveUrlBlackList(int i, int i2, List list) {
    Iterator it = list.iterator();
    ContentValues contentValues = new ContentValues();
    this.mEdmStorageProvider.removeByAdmin("WebFilterTable", i2, i);
    while (it.hasNext()) {
      contentValues.put(
          "adminUid", String.valueOf(EdmStorageProviderBase.translateToAdminLUID(i2, i)));
      contentValues.put("url", (String) it.next());
      Log.d("BrowserPolicy", "saveUrlBlackList - cv: " + contentValues);
      if (!this.mEdmStorageProvider.putValuesNoUpdate("WebFilterTable", contentValues)) {
        return false;
      }
      contentValues.clear();
    }
    return true;
  }

  public final List getUrlBlackList(ContextInfo contextInfo, boolean z) {
    int i = contextInfo.mCallerUid;
    int i2 = contextInfo.mContainerId;
    String[] strArr = {"url"};
    ArrayList arrayList = new ArrayList();
    Log.d("BrowserPolicy", "getUrlBlackList - uid " + i);
    if (!z) {
      Cursor cursorByAdmin =
          this.mEdmStorageProvider.getCursorByAdmin("WebFilterTable", i, i2, strArr);
      try {
        if (cursorByAdmin == null) {
          Log.d("BrowserPolicy", "getUrlBlackList - Cursor is null");
          return null;
        }
        try {
          if (cursorByAdmin.moveToFirst()) {
            do {
              arrayList.add(cursorByAdmin.getString(cursorByAdmin.getColumnIndexOrThrow("url")));
            } while (cursorByAdmin.moveToNext());
          }
        } catch (SQLException e) {
          Log.e("BrowserPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
        } catch (IllegalArgumentException unused) {
          Log.e("BrowserPolicy", "getUrlBlackList - IllegalArgumentException");
          cursorByAdmin.close();
          return null;
        }
        cursorByAdmin.close();
      } catch (Throwable th) {
        cursorByAdmin.close();
        throw th;
      }
    } else {
      int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
      Log.d("BrowserPolicy", "Getting URLList called by server for user " + callingOrCurrentUserId);
      new ArrayList();
      List valuesListAsUser =
          this.mEdmStorageProvider.getValuesListAsUser(
              i2, "WebFilterTable", strArr, callingOrCurrentUserId);
      if (valuesListAsUser == null) {
        Log.d("BrowserPolicy", "getUrlBlackList - cv is null");
        return null;
      }
      Iterator it = valuesListAsUser.iterator();
      while (it.hasNext()) {
        arrayList.add(((ContentValues) it.next()).getAsString("url"));
      }
    }
    return arrayList;
  }

  public final WebFilteringCache getWebFilteringCache(int i) {
    WebFilteringCache[] webFilteringCacheArr =
        (WebFilteringCache[]) this.mUserCache.get(Integer.valueOf(i));
    if (webFilteringCacheArr == null) {
      WebFilteringCache[] webFilteringCacheArr2 = new WebFilteringCache[2];
      for (int i2 = 0; i2 < 2; i2++) {
        webFilteringCacheArr2[i2] = new WebFilteringCache(i2);
      }
      this.mUserCache.put(Integer.valueOf(i), webFilteringCacheArr2);
      webFilteringCacheArr = webFilteringCacheArr2;
    }
    return webFilteringCacheArr[0];
  }

  public final void refreshWebFiltering(WebFilteringCache webFilteringCache, int i) {
    WebFilteringCache[] webFilteringCacheArr;
    HashMap hashMap = this.mUserCache;
    if (hashMap == null
        || !hashMap.containsKey(Integer.valueOf(i))
        || (webFilteringCacheArr = (WebFilteringCache[]) this.mUserCache.get(Integer.valueOf(i)))
            == null) {
      return;
    }
    this.mUserCache.remove(Integer.valueOf(i));
    webFilteringCacheArr[0] = webFilteringCache;
    this.mUserCache.put(Integer.valueOf(i), webFilteringCacheArr);
  }

  public boolean addWebBookmarkByteBuffer(
      ContextInfo contextInfo, Uri uri, String str, byte[] bArr) {
    ContextInfo enforceBrowserPermission = enforceBrowserPermission(contextInfo);
    if (uri == null || str == null) {
      return false;
    }
    return addBookmark(enforceBrowserPermission, uri.toString(), str, null);
  }

  public boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) {
    ContextInfo enforceBrowserPermission = enforceBrowserPermission(contextInfo);
    if (uri == null || str == null) {
      return false;
    }
    return addBookmark(enforceBrowserPermission, uri.toString(), str, null);
  }

  public boolean deleteWebBookmark(ContextInfo contextInfo, Uri uri, String str) {
    ContextInfo enforceBrowserPermission = enforceBrowserPermission(contextInfo);
    if (uri == null) {
      return false;
    }
    return removeFromBookmarks(enforceBrowserPermission, uri.toString(), str);
  }

  public static final void addOrUrlEquals(StringBuilder sb) {
    sb.append(" OR url = ");
  }

  public static final Cursor getVisitedLike(
      ContentResolver contentResolver, String str, Uri uri, String[] strArr) {
    StringBuilder sb;
    boolean z = false;
    if (str.startsWith("http://")) {
      str = str.substring(7);
    } else if (str.startsWith("https://")) {
      str = str.substring(8);
      z = true;
    }
    if (str.startsWith("www.")) {
      str = str.substring(4);
    }
    if (z) {
      sb = new StringBuilder("url = ");
      DatabaseUtils.appendEscapedSQLString(sb, "https://" + str);
      addOrUrlEquals(sb);
      DatabaseUtils.appendEscapedSQLString(sb, "https://www." + str);
    } else {
      StringBuilder sb2 = new StringBuilder("url = ");
      DatabaseUtils.appendEscapedSQLString(sb2, str);
      addOrUrlEquals(sb2);
      String str2 = "www." + str;
      DatabaseUtils.appendEscapedSQLString(sb2, str2);
      addOrUrlEquals(sb2);
      DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str);
      addOrUrlEquals(sb2);
      DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str2);
      sb = sb2;
    }
    return contentResolver.query(uri, strArr, sb.toString(), null, null);
  }

  public final boolean addBookmarkToSBrowser(
      ContentResolver contentResolver, String str, String str2) {
    ContentValues contentValues = new ContentValues();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z = false;
    try {
      try {
        contentValues.put(KnoxCustomManagerService.SHORTCUT_TITLE, str2);
        contentValues.put("url", str);
        contentValues.put("editable", (Integer) 1);
        Uri insert = contentResolver.insert(SBROWSER_BOOKMARKS_URI, contentValues);
        if (insert != null) {
          Log.d("BrowserPolicy", "addBookmarkToSBrowser() - uri: " + insert.toString());
          z = true;
        } else {
          Log.d("BrowserPolicy", "addBookmarkToSBrowser() - uri is null!");
        }
      } catch (IllegalArgumentException unused) {
        Log.d("BrowserPolicy", "Sbrowser provider error - unknown uri");
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x00b8 A[Catch: all -> 0x00dc, IllegalArgumentException -> 0x00de, TryCatch #0 {IllegalArgumentException -> 0x00de, blocks: (B:4:0x000d, B:6:0x0016, B:10:0x001f, B:13:0x003a, B:19:0x006e, B:23:0x00b8, B:26:0x00d2, B:30:0x009b, B:33:0x0063), top: B:3:0x000d, outer: #2 }] */
  /* JADX WARN: Removed duplicated region for block: B:26:0x00d2 A[Catch: all -> 0x00dc, IllegalArgumentException -> 0x00de, TRY_LEAVE, TryCatch #0 {IllegalArgumentException -> 0x00de, blocks: (B:4:0x000d, B:6:0x0016, B:10:0x001f, B:13:0x003a, B:19:0x006e, B:23:0x00b8, B:26:0x00d2, B:30:0x009b, B:33:0x0063), top: B:3:0x000d, outer: #2 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean addBookmarkToChrome(
      ContentResolver contentResolver, String str, String str2) {
    Uri uri;
    Uri uri2;
    boolean z;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        Uri uri3 = CHROME_BOOKMARKS_URI;
        Cursor query = contentResolver.query(uri3, null, null, null);
        if (query == null) {
          Log.d("BrowserPolicy", "addBookmarkToChrome cursor is null");
          return false;
        }
        long count = query.getCount();
        query.close();
        if (count == 0) {
          ContentValues contentValues = new ContentValues();
          count++;
          contentValues.put(KnoxCustomManagerService.f1773ID, Long.valueOf(count));
          contentValues.put(KnoxCustomManagerService.SHORTCUT_TITLE, "Samsung Mobile");
          contentValues.put("parent", (Long) 0L);
          contentValues.put("type", (Integer) 2);
          try {
            uri = contentResolver.insert(uri3, contentValues);
          } catch (Exception unused) {
            Log.d("BrowserPolicy", "Exception creating parent folder");
          }
          if (str != null || str2 == null) {
            uri2 = uri;
          } else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(KnoxCustomManagerService.f1773ID, Long.valueOf(count + 1));
            contentValues2.put(KnoxCustomManagerService.SHORTCUT_TITLE, str2);
            contentValues2.put("url", str);
            contentValues2.put("parent", (Long) 1L);
            contentValues2.put("type", (Integer) 1);
            try {
              uri2 = contentResolver.insert(CHROME_BOOKMARKS_URI, contentValues2);
            } catch (Exception e) {
              Log.e(
                  "BrowserPolicy",
                  " updateBookmarks : insert bookmark items to db. Exception - " + e.getMessage());
              uri2 = null;
            }
          }
          if (uri2 == null) {
            Log.d("BrowserPolicy", "addBookmarkToChrome() - uri: " + uri2.toString());
            z = true;
          } else {
            Log.d("BrowserPolicy", "addBookmarkToChrome() - uri is null!");
            z = false;
          }
          return z;
        }
        uri = null;
        if (str != null) {}
        uri2 = uri;
        if (uri2 == null) {}
        return z;
      } catch (IllegalArgumentException unused2) {
        Log.d("BrowserPolicy", "Chrome provider error - unknown uri");
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:43:0x00f4, code lost:

     if (0 == 0) goto L33;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean addBookmarkToAndroidBrowser(
      ContentResolver contentResolver, String str, String str2, Bitmap bitmap) {
    Uri uri = Browser.BOOKMARKS_URI;
    long time = new Date().getTime();
    ContentValues contentValues = new ContentValues();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z = false;
    Cursor cursor = null;
    try {
      try {
        try {
          cursor = getVisitedLike(contentResolver, str, uri, Browser.HISTORY_PROJECTION);
        } catch (IllegalArgumentException unused) {
          Log.d("BrowserPolicy", "Android provider error - unknown uri");
        }
      } catch (IllegalStateException e) {
        Log.e("BrowserPolicy", "Android provider error: " + e.getMessage());
        if (0 != 0) {}
      }
      if (cursor == null) {
        Log.w("BrowserPolicy", "addBookmarkToAndroidBrowser() - No provider found!!!");
        return false;
      }
      int count = cursor.getCount();
      for (int i = 0; i < count; i++) {
        cursor.moveToPosition(i);
        if (cursor.getString(5).equals(str2)) {
          break;
        }
      }
      contentValues.put(KnoxCustomManagerService.SHORTCUT_TITLE, str2);
      contentValues.put("url", str);
      contentValues.put("created", Long.valueOf(time));
      contentValues.put("bookmark", (Integer) 1);
      contentValues.put("date", (Integer) 0);
      if (Browser.BOOKMARKS_URI.equals(uri)) {
        contentValues.put("thumbnail", bitmapToBytes(bitmap));
      }
      int i2 = count > 0 ? cursor.getInt(2) : 0;
      if (count == 0) {
        contentValues.put("visits", Integer.valueOf(i2 + 3));
      } else {
        contentValues.put("visits", (Integer) 0);
      }
      Log.d("BrowserPolicy", "addBookmarkToAndroidBrowser() - Inserting bookmark into database");
      Uri insert = contentResolver.insert(uri, contentValues);
      if (insert != null) {
        Log.d("BrowserPolicy", "addBookmarkToAndroidBrowser() - uri: " + insert.toString());
        z = true;
      } else {
        Log.d("BrowserPolicy", "addBookmarkToAndroidBrowser() - uri is null!");
      }
      cursor.close();
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return z;
    } finally {
      if (0 != 0) {
        cursor.close();
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean addBookmark(
      ContextInfo contextInfo, String str, String str2, Bitmap bitmap) {
    Context createContextAsUser =
        Utils.createContextAsUser(
            this.mContext, "android", 0, Utils.getCallingOrCurrentUserId(contextInfo));
    if (createContextAsUser == null) {
      Log.e("BrowserPolicy", "addBookmark() - Could not create context for current user!");
      return false;
    }
    if (str.length() == 0 || str2.length() == 0) {
      Log.e("BrowserPolicy", "addBookmark() - uri or title cannot be empty");
      return false;
    }
    ContentResolver contentResolver = createContextAsUser.getContentResolver();
    boolean addBookmarkToChrome = addBookmarkToChrome(contentResolver, str, str2);
    if (addBookmarkToSBrowser(contentResolver, str, str2)) {
      return true;
    }
    return addBookmarkToAndroidBrowser(contentResolver, str, str2, bitmap) || addBookmarkToChrome;
  }

  public static byte[] bitmapToBytes(Bitmap bitmap) {
    if (bitmap == null) {
      return null;
    }
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  public final boolean removeBookmarkFromSBrowser(
      ContentResolver contentResolver, String str, String str2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z = false;
    try {
      try {
        int delete =
            contentResolver.delete(
                SBROWSER_BOOKMARKS_URI, "url = ? AND title = ?", new String[] {str, str2});
        Log.d("BrowserPolicy", "removeBookmarkFromSBrowser() - rows: " + delete);
        if (delete > 0) {
          z = true;
        }
      } catch (IllegalArgumentException unused) {
        Log.d("BrowserPolicy", "Sbrowser provider error - unknown uri");
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean removeBookmarkFromChrome(
      ContentResolver contentResolver, String str, String str2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z = false;
    try {
      try {
        Uri uri = CHROME_BOOKMARKS_URI;
        int delete = contentResolver.delete(uri, "url = ? AND title = ?", new String[] {str, str2});
        Log.d("BrowserPolicy", "removeBookmarkFromChrome() - rows: " + delete);
        if (delete == 0) {
          delete = contentResolver.delete(uri, "url = ?", new String[] {str});
        }
        if (delete > 0) {
          z = true;
        }
      } catch (IllegalArgumentException unused) {
        Log.d("BrowserPolicy", "Chrome provider error - unknown uri");
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:38:0x00af, code lost:

     if (r2 == null) goto L38;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean removeBookmarkFromAndroidBrowser(
      ContentResolver contentResolver, String str, String str2) {
    Cursor query;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    Cursor cursor = null;
    try {
      try {
        query =
            contentResolver.query(
                Browser.BOOKMARKS_URI,
                Browser.HISTORY_PROJECTION,
                "url = ? AND title = ?",
                new String[] {str, str2},
                null);
      } catch (Throwable th) {
        th = th;
      }
    } catch (IllegalArgumentException unused) {
    } catch (IllegalStateException e) {
      e = e;
    }
    try {
      try {
        if (query == null) {
          Log.d("BrowserPolicy", "removeBookmarkFromAndroidBrowser() - No provider found!!!");
          if (query != null) {
            query.close();
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return false;
        }
        if (!query.moveToFirst()) {
          Log.w("BrowserPolicy", "removeBookmarkFromAndroidBrowser() - Empty cursor!!!");
          query.close();
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return false;
        }
        Uri withAppendedId = ContentUris.withAppendedId(Browser.BOOKMARKS_URI, query.getInt(0));
        if (query.getInt(2) != 0 || withAppendedId == null) {
          ContentValues contentValues = new ContentValues();
          contentValues.put("bookmark", (Integer) 0);
          try {
            Log.d("BrowserPolicy", "removeBookmarkFromAndroidBrowser() - Updating database");
            contentResolver.update(withAppendedId, contentValues, null, null);
          } catch (IllegalStateException unused2) {
            Log.e("removeFromBookmarks", "no database!");
          }
        } else {
          Log.d("BrowserPolicy", "removeBookmarkFromAndroidBrowser() - Deleting bookmark");
          contentResolver.delete(withAppendedId, null, null);
        }
        query.close();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return true;
      } catch (IllegalStateException e2) {
        e = e2;
        cursor = query;
        Log.e("BrowserPolicy", "Android provider error: " + e.getMessage());
        if (cursor != null) {
          cursor.close();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } catch (IllegalArgumentException unused3) {
      cursor = query;
      Log.d("BrowserPolicy", "Android provider error - unknown uri");
    } catch (Throwable th2) {
      th = th2;
      cursor = query;
      if (cursor != null) {
        cursor.close();
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public final boolean removeFromBookmarks(ContextInfo contextInfo, String str, String str2) {
    Context createContextAsUser =
        Utils.createContextAsUser(
            this.mContext, "android", 0, Utils.getCallingOrCurrentUserId(contextInfo));
    if (createContextAsUser == null) {
      Log.e("BrowserPolicy", "removeFromBookmarks() - Could not create context for current user!");
      return false;
    }
    ContentResolver contentResolver = createContextAsUser.getContentResolver();
    boolean removeBookmarkFromChrome = removeBookmarkFromChrome(contentResolver, str, str2);
    if (removeBookmarkFromSBrowser(contentResolver, str, str2)) {
      return true;
    }
    return removeBookmarkFromAndroidBrowser(contentResolver, str, str2) || removeBookmarkFromChrome;
  }

  public final void removeAdmin(ContextInfo contextInfo) {
    WebFilteringCache webFilteringCache =
        getWebFilteringCache(UserHandle.getUserId(contextInfo.mCallerUid));
    webFilteringCache.mUrlBlacklistAllAdmin.clear();
    webFilteringCache.mIsUrlBlacklistUpdated = false;
    webFilteringCache.mIsUrlFilterStateUpdated = false;
    webFilteringCache.mIsUrlFilterReportUpdated = false;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println("Permission Denial: can't dump SecurityPolicy");
    } else {
      this.mEnterpriseDumpHelper.dumpTable(
          printWriter, "BROWSER", new String[] {"browserSettings"});
    }
  }
}
