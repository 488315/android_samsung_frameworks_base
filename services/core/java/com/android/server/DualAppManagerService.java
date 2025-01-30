package com.android.server;

import android.app.ActivityManagerNative;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.app.ISemDualAppManager;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class DualAppManagerService extends ISemDualAppManager.Stub {
  public static int inOpsCallback;
  public static Context mContext;
  public OpChangeListener mOpChangeListener = new OpChangeListener();
  public static final String[] DEFAULT_PACKAGES_NOT_FORWARDING = {
    "com.google.android.gms",
    "com.google.android.gsf",
    "com.google.android.gsf.login",
    "com.android.chrome",
    "com.google.android.webview",
    "com.android.nfc",
    "com.google.android.permissioncontroller",
    "com.android.permissioncontroller",
    "com.samsung.android.permissioncontroller",
    "com.google.android.overlay.modules.permissioncontroller",
    "com.google.android.overlay.modules.permissioncontroller.forframework",
    "com.google.android.overlay.modules.modulemetadata.forframework"
  };
  public static final String[] DUALAPP_DEFAULT_PACKAGES = {
    "com.android.settings",
    "com.android.providers.settings",
    "android",
    "com.android.keychain",
    "com.google.android.webview",
    "com.sec.android.provider.badge",
    "com.bst.floatingmsgproxy",
    "com.bst.airmessage",
    "com.android.server.telecom",
    "com.android.intentresolver",
    "com.facebook.appmanager",
    "com.google.android.apps.restore"
  };
  public static HashMap mInstalledWhitelistedPkgMap = new HashMap();
  public static HashMap mWhitelistedPkgMap = new HashMap();
  public static List mDaWeeklyUsageCount = new ArrayList();
  public static List mDaMonthlyUsageCount = new ArrayList();
  public static int thisWeek = -1;
  public static String DUAL_APP_NOTIFICATION_URI = null;
  public static DualAppManagerService sDAMSInstance = null;
  public static InternalHandler mHandler = null;
  public static String lastResumedActivity = null;
  public static boolean isNotNullInputContextNotified = false;
  public static boolean isNullInputContextNotified = false;

  public final void addOpChangeListener(String str) {}

  public class InternalHandler extends Handler {
    public InternalHandler() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Log.d("DualAppManagerService", "handleMessage() START " + message);
      try {
        int i = message.what;
        if (i == 1) {
          Intent intent = new Intent();
          intent.setClassName(
              "com.samsung.android.da.daagent",
              "com.samsung.android.da.daagent.service.SwitchLauncherService");
          intent.putExtra("defaultLauncher", (String) message.obj);
          DualAppManagerService.mContext.startServiceAsUser(intent, UserHandle.SEM_OWNER);
        } else if (i == 2) {
          DualAppManagerService.this.updateWhitelistPackages();
          DualAppManagerService.this.updateInstalledWhitelistPackages();
        } else if (i == 3) {
          DualAppManagerService.this.updateDAUsage();
        } else if (i == 4) {
          DualAppManagerService.updateWedgeAboutActivity((String) message.obj);
        } else if (i == 5) {
          DualAppManagerService.updateWedgeAboutInputContext((String) message.obj, message.arg1);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      Log.d("DualAppManagerService", "handleMessage() END" + message);
    }
  }

  public static void sendInternalMsg(int i, int i2, int i3, Object obj) {
    sendInternalMsg(i, i2, i3, obj, 0L);
  }

  public static void sendInternalMsg(int i, int i2, int i3, Object obj, long j) {
    Log.d("DualAppManagerService", "sendInternalMsg() " + i + "/" + i2 + "/" + i3);
    InternalHandler internalHandler = mHandler;
    if (internalHandler == null) {
      Log.d("DualAppManagerService", "sendInternalMsg() failed, handler is null");
    } else if (j > 0) {
      internalHandler.sendMessageDelayed(internalHandler.obtainMessage(i, i2, i3, obj), j);
    } else {
      internalHandler.sendMessage(internalHandler.obtainMessage(i, i2, i3, obj));
    }
  }

  public class OpChangeListener extends AppOpsManager.OnOpChangedInternalListener {
    public OpChangeListener() {}

    public void onOpChanged(String str, String str2) {
      Log.d("DualAppManagerService", "onOpChanged() " + str + "/" + str2);
    }

    public void onOpChanged(int i, String str) {
      AppOpsManager appOpsManager;
      Integer num;
      if (i == 24) {
        return;
      }
      if (DualAppManagerService.inOpsCallback != 0) {
        Log.d("DualAppManagerService", "onOpChanged() is in progress");
        return;
      }
      DualAppManagerService.inOpsCallback = 1;
      try {
        appOpsManager =
            (AppOpsManager) DualAppManagerService.mContext.getSystemService(AppOpsManager.class);
        num = (Integer) DualAppManagerService.mInstalledWhitelistedPkgMap.get(str);
      } catch (Exception unused) {
      } catch (Throwable th) {
        DualAppManagerService.inOpsCallback = 0;
        throw th;
      }
      if (num != null) {
        appOpsManager.setMode(
            i,
            num.intValue(),
            str,
            appOpsManager.checkOpNoThrow(i, UserHandle.getAppId(num.intValue()), str));
        ActivityManagerNative.getDefault()
            .killUid(
                UserHandle.getAppId(num.intValue()),
                UserHandle.getUserId(num.intValue()),
                "Permission related app op changed");
        DualAppManagerService.inOpsCallback = 0;
        return;
      }
      DualAppManagerService.inOpsCallback = 0;
    }
  }

  public DualAppManagerService() {
    mHandler = new InternalHandler();
  }

  public static DualAppManagerService getInstance(Context context) {
    if (sDAMSInstance == null) {
      synchronized (DualAppManagerService.class) {
        if (sDAMSInstance == null) {
          sDAMSInstance = new DualAppManagerService();
          mContext = context;
        }
      }
    }
    return sDAMSInstance;
  }

  public static void notifyActivityResumedLocked(int i, String str) {
    if (SystemProperties.getInt("sys.datawedge.prop", 0) == 1) {
      Log.d("DualAppManagerService", "DW::notifyActivityResumedLocked " + str + " " + i);
      lastResumedActivity = str;
      isNullInputContextNotified = false;
      isNotNullInputContextNotified = false;
      sendInternalMsg(4, 0, 0, str);
    }
  }

  public static void notifyInputContextChanged(int i, String str, boolean z) {
    if (SystemProperties.getInt("sys.datawedge.prop", 0) == 1) {
      if (!z) {
        if (isNullInputContextNotified) {
          return;
        }
        sendInternalMsg(5, 0, 0, lastResumedActivity + "|" + i);
        isNullInputContextNotified = true;
        return;
      }
      if (isNotNullInputContextNotified) {
        return;
      }
      sendInternalMsg(5, 1, 0, lastResumedActivity + "|" + i);
      isNotNullInputContextNotified = true;
      isNullInputContextNotified = true;
    }
  }

  public static void updateWedgeAboutActivity(String str) {
    Uri parse = Uri.parse("content://com.samsung.android.bbc.bbcagent/updateForegroundActivity");
    ContentValues contentValues = new ContentValues();
    contentValues.put("component", str);
    mContext.getContentResolver().update(parse, contentValues, null, null);
  }

  public static void updateWedgeAboutInputContext(String str, int i) {
    Uri parse = Uri.parse("content://com.samsung.android.bbc.bbcagent/updateInputContext");
    ContentValues contentValues = new ContentValues();
    contentValues.put("newInputContextNotNull", Integer.valueOf(i));
    contentValues.put("component", str);
    mContext.getContentResolver().update(parse, contentValues, null, null);
  }

  public static void sendBroadcastCustomIntent(Context context, int i, Intent intent) {
    String str;
    boolean z;
    if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
      str = "com.samsung.android.da.daagent.PACKAGE_REMOVED";
      z = true;
    } else {
      str = "com.samsung.android.da.daagent.PACKAGE_ADDED";
      z = false;
    }
    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
    if (schemeSpecificPart == null) {
      Log.d("DualAppManagerService", " can not sendBroadcast intent, becuase pkgName is null");
      return;
    }
    if (i == 0 && SemDualAppManager.getInstance(context).isWhitelistedPackage(schemeSpecificPart)) {
      Intent intent2 = (Intent) intent.clone();
      intent2.setAction(str);
      intent2.setPackage("com.samsung.android.da.daagent");
      context.sendBroadcastAsUser(intent2, UserHandle.OWNER);
    }
    if (z || isFilteredPackage(context, schemeSpecificPart, i)) {
      Intent intent3 = (Intent) intent.clone();
      intent3.setAction(str);
      intent3.setPackage("com.samsung.android.bbc.bbcagent");
      context.sendBroadcastAsUser(intent3, UserHandle.OWNER);
    }
  }

  public static boolean isFilteredPackage(Context context, String str, int i) {
    Bundle bundle;
    String[] strArr;
    PackageManager packageManager = context.getPackageManager();
    try {
      PackageInfo packageInfo = packageManager.getPackageInfo(str, IInstalld.FLAG_USE_QUOTA);
      if (packageInfo != null && (strArr = packageInfo.requestedPermissions) != null) {
        for (String str2 : strArr) {
          if ("com.samsung.android.teelicense".equals(str2)) {
            Log.d("DualAppManagerService", "this is DDC app " + str2);
            return true;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (!"com.samsung.android.app.smartscan".equals(str)) {
      return false;
    }
    try {
      PackageInfo packageInfoAsUser = packageManager.getPackageInfoAsUser(str, 128, i);
      if (packageInfoAsUser != null
          && (bundle = packageInfoAsUser.applicationInfo.metaData) != null) {
        if (bundle.getBoolean("com.samsung.android.bbcagent.notify_install", false)) {
          return true;
        }
      }
    } catch (Exception e2) {
      e2.printStackTrace();
    }
    return false;
  }

  public void systemReady() {
    Log.d("DualAppManagerService", "DualAppManagerService ready");
    try {
      if (!writeDualAppProfileId(mContext)
          && SystemProperties.getInt("persist.sys.dualapp.directory.revision", 0) == 0) {
        SystemProperties.set("persist.sys.dualapp.directory.revision", "1");
      }
    } catch (Exception e) {
      Log.e("DualAppManagerService", "Failed to write dual app profile id");
      e.printStackTrace();
    }
    Log.d("DualAppManagerService", "systemReady done.");
  }

  public synchronized Bundle updateDualAppData(String str, int i, Bundle bundle) {
    Bundle bundle2 = null;
    if ("com.samsung.android.da.daagent".equals(str) && Binder.getCallingUid() == 1000) {
      Log.d("DualAppManagerService", "updateDualAppData " + str + "/" + i + "/" + bundle);
      if (bundle == null) {
        Log.d("DualAppManagerService", "updateDualAppData. Bundle is null");
        return null;
      }
      String string = bundle.getString(KnoxVpnFirewallHelper.CMD);
      try {
        if ("renewInstalledWhitelistedPkgs".equals(string)) {
          bundle2 = renewWhitelistedPkg(bundle);
        } else if ("addInstalledWhitelistedPkg".equals(string)) {
          bundle2 = addWhitelistedPkg(bundle);
        } else if ("removeInstalledWhitelistedPkg".equals(string)) {
          bundle2 = removeWhitelistedPkg(bundle);
        } else if ("removeAllInstalledWhitelistedPkgs".equals(string)) {
          bundle2 = removeAllWhitelistedPkgs(bundle);
        } else if ("printInstalledWhitelistedPkg".equals(string)) {
          printInstalledWhitelistedPkg();
        } else if ("setDualAppNotificationSound".equals(string)) {
          bundle2 = setDualAppNotificationSound(bundle);
        } else if ("updateWhitelistPkgs".equals(string)) {
          bundle2 = updateWhitelistPkg(bundle);
        } else {
          Bundle bundle3 = new Bundle();
          try {
            bundle3.putInt("result_code", 0);
            bundle3.putString("result_desc", "not defined command");
            bundle2 = bundle3;
          } catch (Exception e) {
            bundle2 = bundle3;
            e = e;
            e.printStackTrace();
            return bundle2;
          }
        }
      } catch (Exception e2) {
        e = e2;
      }
      return bundle2;
    }
    Log.d("DualAppManagerService", "updateDualAppData is called from unauthorized app");
    return null;
  }

  public List getAllInstalledWhitelistedPackages() {
    try {
      Set keySet = mInstalledWhitelistedPkgMap.keySet();
      if (keySet != null) {
        return new ArrayList(keySet);
      }
      return null;
    } catch (Exception unused) {
      Log.e(
          "DualAppManagerService",
          "Exception occured in getAllInstalledWhitelistedPackages. retrun null");
      return null;
    }
  }

  public static boolean isSelfContainedAction(int i, String str) {
    List allPkgNameByUid;
    if (str != null && (allPkgNameByUid = getAllPkgNameByUid(i)) != null) {
      Iterator it = allPkgNameByUid.iterator();
      while (it.hasNext()) {
        if (str.startsWith((String) it.next())) {
          return true;
        }
      }
    }
    return false;
  }

  public static final List getAllPkgNameByUid(int i) {
    ArrayList arrayList = new ArrayList();
    Integer valueOf = Integer.valueOf(i);
    try {
      for (Map.Entry entry : mInstalledWhitelistedPkgMap.entrySet()) {
        if (valueOf.equals(entry.getValue())) {
          arrayList.add((String) entry.getKey());
        }
      }
    } catch (Exception e) {
      Log.e("DualAppManagerService", "Exception occured in getAllPkgNameByUid. retrun null");
      e.printStackTrace();
    }
    return arrayList;
  }

  public boolean isInstalledWhitelistedPackage(String str) {
    try {
      if (!mInstalledWhitelistedPkgMap.containsKey(str)) {
        return false;
      }
      Log.d("DualAppManagerService", "Found!");
      return true;
    } catch (Exception e) {
      Log.e(
          "DualAppManagerService",
          "Exception occured in isInstalledWhitelistedPackage. retrun false");
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isInstalledWhitelistedPackageInternal(String str) {
    try {
      return mInstalledWhitelistedPkgMap.containsKey(str);
    } catch (Exception e) {
      Log.e(
          "DualAppManagerService",
          "Exception occured in isInstalledWhitelistedPackageInternal. retrun false");
      e.printStackTrace();
      return false;
    }
  }

  public static ActivityInfo changeInfoIfDeletingDualApp(
      Context context, ActivityInfo activityInfo, Intent intent, int i, String str) {
    if (activityInfo == null) {
      return null;
    }
    if ("com.samsung.android.da.daagent".equals(str)
        || "com.android.settings".equals(str)
        || SemDualAppManager.isSamsungLauncher(str)) {
      return activityInfo;
    }
    String schemeSpecificPart =
        intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;
    if (!SemDualAppManager.getInstance(context).isWhitelistedPackage(schemeSpecificPart)) {
      return activityInfo;
    }
    UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
    if (userHandle != null) {
      i = userHandle.getIdentifier();
    }
    if (!SemDualAppManager.isDualAppId(i) && i != 0) {
      return activityInfo;
    }
    PackageManager packageManager = context.getPackageManager();
    Intent intent2 = new Intent();
    intent2.setClassName(
        "com.samsung.android.da.daagent", "com.samsung.android.da.daagent.RemoveDualIM");
    ResolveInfo resolveActivityAsUser = packageManager.resolveActivityAsUser(intent2, 66688, 0);
    ActivityInfo activityInfo2 =
        resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
    if (activityInfo2 == null) {
      return activityInfo;
    }
    intent.setClassName(
        "com.samsung.android.da.daagent", "com.samsung.android.da.daagent.RemoveDualIM");
    intent.putExtra("com.samsung.android.da.original_intent", new Intent(intent));
    intent.putExtra("packageName", schemeSpecificPart);
    intent.putExtra("userId", i);
    return activityInfo2;
  }

  public final Bundle renewWhitelistedPkg(Bundle bundle) {
    Bundle bundle2 = new Bundle();
    mInstalledWhitelistedPkgMap.clear();
    mInstalledWhitelistedPkgMap.putAll(
        (HashMap) bundle.getSerializable("allInstalledWhitelistedPkgs"));
    Iterator it = mInstalledWhitelistedPkgMap.keySet().iterator();
    while (it.hasNext()) {
      addOpChangeListener((String) it.next());
    }
    bundle2.putInt("result_code", 1);
    bundle2.putString("result_desc", "success");
    return bundle2;
  }

  public final Bundle updateWhitelistPkg(Bundle bundle) {
    Bundle bundle2 = new Bundle();
    HashMap hashMap = (HashMap) bundle.getSerializable("packageList");
    if (hashMap != null) {
      mWhitelistedPkgMap = hashMap;
    }
    bundle2.putInt("result_code", 1);
    bundle2.putString("result_desc", "success");
    return bundle2;
  }

  public final Bundle addWhitelistedPkg(Bundle bundle) {
    Bundle bundle2 = new Bundle();
    String string = bundle.getString("pkgName");
    int i = bundle.getInt("pkgUid");
    if (string == null) {
      bundle2.putInt("result_code", 0);
      bundle2.putString("result_desc", "package name is null");
    } else if (mInstalledWhitelistedPkgMap.get(string) != null) {
      bundle2.putInt("result_code", 1);
      bundle2.putString("result_desc", "package is already added");
    } else {
      try {
        mInstalledWhitelistedPkgMap.put(string, Integer.valueOf(i));
        bundle2.putInt("result_code", 1);
        bundle2.putString("result_desc", "success");
      } catch (Exception e) {
        bundle2.putInt("result_code", 0);
        bundle2.putString("result_desc", e.getMessage());
      }
      addOpChangeListener(string);
    }
    return bundle2;
  }

  public final Bundle removeWhitelistedPkg(Bundle bundle) {
    Bundle bundle2 = new Bundle();
    String string = bundle.getString("pkgName");
    if (string == null) {
      bundle2.putInt("result_code", 0);
      bundle2.putString("result_desc", "package name is null");
    } else if (mInstalledWhitelistedPkgMap.get(string) == null) {
      bundle2.putInt("result_code", 1);
      bundle2.putString("result_desc", "package doesn't exist");
    } else {
      try {
        mInstalledWhitelistedPkgMap.remove(string);
        bundle2.putInt("result_code", 1);
        bundle2.putString("result_desc", "success");
      } catch (Exception e) {
        bundle2.putInt("result_code", 0);
        bundle2.putString("result_desc", e.getMessage());
      }
    }
    return bundle2;
  }

  public final Bundle removeAllWhitelistedPkgs(Bundle bundle) {
    Bundle bundle2 = new Bundle();
    mInstalledWhitelistedPkgMap.clear();
    bundle2.putInt("result_code", 1);
    bundle2.putString("result_desc", "success");
    return bundle2;
  }

  public final void printInstalledWhitelistedPkg() {
    Log.d("DualAppManagerService", "printInstalledWhitelistedPkg called!");
    for (String str : mInstalledWhitelistedPkgMap.keySet()) {
      Log.d(
          "DualAppManagerService",
          "installed whitelisted dual app ["
              + str
              + "/"
              + String.valueOf(mInstalledWhitelistedPkgMap.get(str))
              + "]");
    }
  }

  public final Bundle setDualAppNotificationSound(Bundle bundle) {
    Bundle bundle2 = new Bundle();
    String string = bundle.getString("sound_uri");
    Log.d("DualAppManagerService", "setDualAppNotificationSound : " + string);
    if (string == null) {
      bundle2.putInt("result_code", 0);
      bundle2.putString("result_desc", "uri is null");
    } else {
      try {
        DUAL_APP_NOTIFICATION_URI = string;
        bundle2.putInt("result_code", 1);
        bundle2.putString("result_desc", "success");
      } catch (Exception e) {
        bundle2.putInt("result_code", 0);
        bundle2.putString("result_desc", e.getMessage());
      }
    }
    return bundle2;
  }

  public static void changeUriForDualApp(Intent intent, int i) {
    Uri uri;
    try {
      Uri uri2 = (Uri) intent.getExtra("output");
      if (uri2 != null && SemDualAppManager.shouldAddUserId(uri2, i)) {
        intent.putExtra("output", ContentProvider.maybeAddUserId(uri2, i));
      }
      if (intent.getClipData() != null) {
        intent.getClipData().fixUris(i);
      }
      if (intent.getData() != null && SemDualAppManager.shouldAddUserId(intent.getData(), i)) {
        intent.setDataAndType(
            ContentProvider.maybeAddUserId(intent.getData(), i), intent.getType());
      }
      if ("android.intent.action.SEND_MULTIPLE".equals(intent.getAction())) {
        ArrayList arrayList = new ArrayList();
        ArrayList parcelableArrayListExtra =
            intent.getParcelableArrayListExtra("android.intent.extra.STREAM");
        if (parcelableArrayListExtra == null || parcelableArrayListExtra.size() <= 0) {
          return;
        }
        Iterator it = parcelableArrayListExtra.iterator();
        while (it.hasNext()) {
          Uri uri3 = (Uri) it.next();
          if (SemDualAppManager.shouldAddUserId(uri3, i)) {
            arrayList.add(ContentProvider.maybeAddUserId(uri3, i));
          } else {
            arrayList.add(uri3);
          }
        }
        intent.putExtra("android.intent.extra.STREAM", arrayList);
        return;
      }
      if (!"android.intent.action.SEND".equals(intent.getAction())
          || intent.getExtras() == null
          || (uri = (Uri) intent.getExtras().getParcelable("android.intent.extra.STREAM")) == null
          || !SemDualAppManager.shouldAddUserId(uri, i)) {
        return;
      }
      intent.putExtra("android.intent.extra.STREAM", ContentProvider.maybeAddUserId(uri, i));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static boolean isDefalutAppPackage(String str) {
    if (str != null && !"".equalsIgnoreCase(str)) {
      for (String str2 : DUALAPP_DEFAULT_PACKAGES) {
        if (str2.equals(str)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean shouldForwardToOwner(String str) {
    if (str == null || "".equalsIgnoreCase(str)) {
      return true;
    }
    Context context = mContext;
    if (context != null
        && SemDualAppManager.getInstance(context).isWhitelistedPackage(str)
        && (SemDualAppManager.isInstalledWhitelistedPackage(str)
            || str.equals(mContext.getPackageManager().getNameForUid(Binder.getCallingUid())))) {
      return false;
    }
    for (String str2 : DEFAULT_PACKAGES_NOT_FORWARDING) {
      if (str2.equals(str)) {
        return false;
      }
    }
    return ("com.bst.floatingmsgproxy".equals(str) || "com.bst.airmessage".equals(str))
        ? false
        : true;
  }

  public static boolean mayForwardIntent(Intent intent) {
    if (intent == null) {
      return true;
    }
    String action = intent.getAction();
    if ("android.settings.action.MANAGE_OVERLAY_PERMISSION".equals(action)
        || "android.settings.CHANNEL_NOTIFICATION_SETTINGS".equals(action)) {
      return false;
    }
    return (("android.intent.action.MAIN".equals(action)
                && intent.hasCategory("android.intent.category.NOTIFICATION_PREFERENCES"))
            || "android.intent.action.MANAGE_APP_PERMISSIONS".equals(action)
            || "android.content.pm.action.REQUEST_PERMISSIONS".equals(action)
            || "android.settings.APPLICATION_DETAILS_SETTINGS".equals(action)
            || "android.settings.APP_NOTIFICATION_SETTINGS".equals(action))
        ? false
        : true;
  }

  public static boolean hasExternalAppDirPath(Intent intent, String str) {
    String path;
    String path2;
    String path3;
    String path4;
    String path5;
    String path6;
    String str2 = "/storage/emulated/0/Android/data/" + str;
    if ("android.intent.action.SEND_MULTIPLE".equals(intent.getAction())) {
      ClipData clipData = intent.getClipData();
      if (clipData != null) {
        ArrayList<Uri> arrayList = new ArrayList();
        clipData.collectUris(arrayList);
        for (Uri uri : arrayList) {
          if (uri != null
              && "file".equals(uri.getScheme())
              && (path6 = uri.getPath()) != null
              && path6.startsWith(str2)) {
            return true;
          }
        }
      }
      Uri data = intent.getData();
      if (data != null
          && "file".equals(data.getScheme())
          && (path5 = data.getPath()) != null
          && path5.startsWith(str2)) {
        return true;
      }
      ArrayList parcelableArrayListExtra =
          intent.getParcelableArrayListExtra("android.intent.extra.STREAM");
      if (parcelableArrayListExtra == null) {
        return false;
      }
      Iterator it = parcelableArrayListExtra.iterator();
      while (it.hasNext()) {
        Uri uri2 = (Uri) it.next();
        if (uri2 != null
            && "file".equals(uri2.getScheme())
            && (path4 = uri2.getPath()) != null
            && path4.startsWith(str2)) {
          return true;
        }
      }
      return false;
    }
    if (!"android.intent.action.SEND".equals(intent.getAction())) {
      return false;
    }
    ClipData clipData2 = intent.getClipData();
    if (clipData2 != null) {
      ArrayList<Uri> arrayList2 = new ArrayList();
      clipData2.collectUris(arrayList2);
      for (Uri uri3 : arrayList2) {
        if (uri3 != null
            && "file".equals(uri3.getScheme())
            && (path3 = uri3.getPath()) != null
            && path3.startsWith(str2)) {
          return true;
        }
      }
    }
    Uri data2 = intent.getData();
    if (data2 != null
        && "file".equals(data2.getScheme())
        && (path2 = data2.getPath()) != null
        && path2.startsWith(str2)) {
      return true;
    }
    Uri uri4 = (Uri) intent.getExtras().getParcelable("android.intent.extra.STREAM");
    return uri4 != null
        && "file".equals(uri4.getScheme())
        && (path = uri4.getPath()) != null
        && path.startsWith(str2);
  }

  public static boolean mayForwardShare(Intent intent, String str, int i, int i2) {
    ComponentName component;
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if (intent != null
        && "com.samsung.android.da.daagent.FORWARD_SHARE_FROM_OWNER".equals(intent.getAction())) {
      Log.d("DualAppManagerService", "illegal action. this action doens't use other app.");
      throw new SecurityException("not allow this action");
    }
    try {
      if (SemDualAppManager.isDualAppId(i2)) {
        Intent intent2 =
            (intent == null || !"android.intent.action.CHOOSER".equals(intent.getAction()))
                ? intent
                : (Intent) intent.getExtras().getParcelable("android.intent.extra.INTENT");
        if (intent2 != null
            && intent2.getComponent() == null
            && intent2.getPackage() == null
            && !"com.samsung.android.da.daagent".equals(str)
            && hasExternalAppDirPath(intent2, str)) {
          intent.setClassName(
              "com.samsung.android.da.daagent",
              "com.samsung.android.da.daagent.activity.ForwardShareActivity");
          return true;
        }
      } else if (i2 == 0) {
        String str2 = intent.getPackage();
        if (str2 == null && (component = intent.getComponent()) != null) {
          str2 = component.getPackageName();
        }
        if (!"com.samsung.android.da.daagent".equals(str)
            && SemDualAppManager.isDualAppId(i)
            && str.equals(str2)
            && hasExternalAppDirPath(intent, str)) {
          intent.putExtra("android.intent.extra.INTENT", (Parcelable) intent.clone());
          intent.setAction("com.samsung.android.da.daagent.FORWARD_SHARE_FROM_OWNER");
          intent.setClassName(
              "com.samsung.android.da.daagent",
              "com.samsung.android.da.daagent.activity.ForwardShareActivity");
          return true;
        }
      }
    } catch (Exception unused) {
    }
    return false;
  }

  public static Intent startDAChooserActivity(Intent intent, int i, int i2, String str) {
    return startDAChooserActivity(intent, i, i2, str, Binder.getCallingUid());
  }

  /* JADX WARN: Removed duplicated region for block: B:46:0x02d8 A[Catch: Exception -> 0x0335, TryCatch #0 {Exception -> 0x0335, blocks: (B:10:0x002d, B:12:0x0042, B:16:0x004f, B:19:0x0056, B:21:0x0066, B:25:0x007f, B:27:0x00d2, B:30:0x00dc, B:32:0x00ed, B:34:0x00f3, B:36:0x00f9, B:38:0x00ff, B:40:0x010e, B:43:0x0115, B:46:0x02d8, B:48:0x02df, B:49:0x02e2, B:51:0x02e8, B:52:0x0301, B:54:0x0307, B:55:0x0320, B:57:0x0108, B:58:0x0126, B:61:0x012e, B:63:0x013f, B:65:0x0145, B:67:0x015c, B:69:0x0164, B:71:0x016c, B:72:0x0178, B:74:0x017e, B:76:0x018f, B:78:0x0195, B:80:0x019b, B:82:0x01af, B:84:0x01b5, B:86:0x01bd, B:88:0x01c5, B:90:0x01cd, B:92:0x01d3, B:95:0x01e4, B:97:0x01ea, B:99:0x01f0, B:101:0x01f6, B:103:0x01dc, B:104:0x0206, B:106:0x0213, B:108:0x0220, B:110:0x022d, B:113:0x023b, B:115:0x0241, B:117:0x024e, B:119:0x0256, B:120:0x027f, B:122:0x0285, B:124:0x028b, B:125:0x0085, B:129:0x008f, B:131:0x009d, B:133:0x00ab, B:135:0x00b3, B:137:0x00c1, B:141:0x02a2, B:143:0x02a8, B:146:0x02b9, B:148:0x02bf, B:150:0x02c5, B:151:0x02b1), top: B:9:0x002d }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static Intent startDAChooserActivity(Intent intent, int i, int i2, String str, int i3) {
    String str2;
    Intent createChooser;
    boolean z;
    String str3;
    String str4;
    if (!"com.bst.floatingmsgproxy".equals(str)
        && !"com.bst.airmessage".equals(str)
        && !"com.samsung.android.da.daagent".equals(str)
        && !"com.samsung.android.spay".equals(str)) {
      try {
        boolean isDualAppId = SemDualAppManager.isDualAppId(i);
        if (new ComponentName(
                "com.samsung.android.da.daagent",
                "com.samsung.android.da.daagent.activity.ForwardShareActivity")
            .equals(intent.getComponent())) {
          intent.setClassName(
              "com.samsung.android.da.daagent",
              "com.samsung.android.da.daagent.activity.EmptyActivity");
          Log.d("DualAppManagerService", "Invalid call to share");
          return null;
        }
        if (isDualAppId && mayForwardShare(intent, str, i2, i)) {
          return null;
        }
        if ((!"android.intent.action.SEND".equals(intent.getAction())
                && !"android.intent.action.SEND_MULTIPLE".equals(intent.getAction()))
            || (!isDualAppId && i != 0)) {
          if ((i2 == 0 || SemDualAppManager.isDualAppId(i2))
              && intent.getComponent() != null
              && i3 != 1000
              && !intent.getComponent().getPackageName().equals(str)
              && isInstalledWhitelistedPackageInternal(intent.getComponent().getPackageName())
              && !"com.sina.weibo".equals(str)
              && SemDualAppManager.isChooserRequired(intent.getComponent().getClassName())) {
            createChooser = Intent.createChooser(new Intent(), mContext.getString(17043293));
          } else if (intent.getData() != null
              && "mqqapi".equals(intent.getData().getScheme())
              && (isInstalledWhitelistedPackageInternal("com.tencent.mobileqq")
                  || isInstalledWhitelistedPackageInternal("com.tencent.mobileqqi"))) {
            if (intent.getComponent() != null) {
              str4 = intent.getComponent().getPackageName();
            } else {
              str4 = intent.getPackage();
            }
            if (str != null && str.equals(str4)) {
              return null;
            }
            createChooser = Intent.createChooser(new Intent(), mContext.getString(17043310));
          } else if (intent.getData() != null
              && "weixin".equals(intent.getData().getScheme())
              && isInstalledWhitelistedPackageInternal("com.tencent.mm")) {
            createChooser = Intent.createChooser(new Intent(), mContext.getString(17043293));
            if ((KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME.equals(str)
                    || "com.android.chrome".equals(str))
                && intent.hasCategory("android.intent.category.BROWSABLE")) {
              createChooser.addFlags(335544320);
              Log.d("DualAppManagerService", "startDAChooserActivity addFlags for chooserIntent");
            }
          } else if (intent.getData() != null
              && "mqqwallet".equals(intent.getData().getScheme())
              && (isInstalledWhitelistedPackageInternal("com.tencent.mobileqq")
                  || isInstalledWhitelistedPackageInternal("com.tencent.mobileqqi"))) {
            createChooser = Intent.createChooser(new Intent(), mContext.getString(17043293));
          } else {
            if (i2 == 0
                && Binder.getCallingUid() != 1000
                && ("com.taobao.taobao".equals(str)
                    || "com.eg.android.AlipayGphone".equals(str)
                    || "com.tmall.wireless".equals(str))) {
              if (intent.getComponent() != null) {
                str3 = intent.getComponent().getPackageName();
              } else {
                str3 = intent.getPackage();
              }
              if (str != null
                  && str3 != null
                  && !str.equals(str3)
                  && isInstalledWhitelistedPackageInternal(str3)
                  && SemDualAppManager.isChinaDualApp(str3)) {
                createChooser = Intent.createChooser(new Intent(), mContext.getString(17043310));
              }
              createChooser = null;
              z = false;
            } else {
              if (!"vnd.android.cursor.item/vnd.com.tencent.mm.chatting.profile"
                      .equals(intent.getType())
                  && !"vnd.android.cursor.item/vnd.com.tencent.mm.chatting.voip"
                      .equals(intent.getType())
                  && !"vnd.android.cursor.item/vnd.com.tencent.mm.plugin.sns.timeline"
                      .equals(intent.getType())
                  && !"vnd.android.cursor.item/vnd.com.tencent.mm.chatting.voiceaction"
                      .equals(intent.getType())) {
                if (intent.getDataString() != null
                    && intent.getDataString().contains("kakaotalk://settings/theme/")
                    && isInstalledWhitelistedPackageInternal("com.kakao.talk")) {
                  Log.d("DualAppManagerService", "intent : " + intent.getData());
                  createChooser = Intent.createChooser(new Intent(), mContext.getString(17043293));
                }
                createChooser = null;
                z = false;
              }
              if (isInstalledWhitelistedPackageInternal("com.tencent.mm")
                  && !isInstalledWhitelistedPackageInternal(str)) {
                createChooser = Intent.createChooser(new Intent(), mContext.getString(17043316));
              }
              createChooser = null;
              z = false;
            }
            if (z) {
              ArrayList arrayList = new ArrayList();
              if (isDualAppId) {
                changeUriForDualApp(intent, i);
              }
              if (intent.getData() != null) {
                Log.d("DualAppManagerService", "ret1 : " + intent.getData());
              }
              if (intent.getComponent() != null) {
                Log.d("DualAppManagerService", "ret2 : " + intent.getComponent());
              }
              arrayList.add(intent);
              createChooser.putExtra(
                  "android.intent.extra.INITIAL_INTENTS",
                  (Intent[]) arrayList.toArray(new Intent[arrayList.size()]));
              return createChooser;
            }
          }
          z = true;
          if (z) {}
        }
        if (intent.getComponent() != null) {
          str2 = intent.getComponent().getPackageName();
        } else {
          str2 = intent.getPackage();
        }
        if (str != null
            && str2 != null
            && !str.equals(str2)
            && isInstalledWhitelistedPackageInternal(str2)) {
          createChooser = Intent.createChooser(new Intent(), mContext.getString(17043310));
          z = true;
          if (z) {}
        }
        createChooser = null;
        z = false;
        if (z) {}
      } catch (Exception e) {
        Log.d("DualAppManagerService", "fail startDAChooserActivity");
        e.printStackTrace();
      }
    }
    return null;
  }

  public final boolean writeDualAppProfileId(Context context) {
    int i;
    boolean z;
    Iterator it = UserManager.get(context).getUsers().iterator();
    while (true) {
      if (!it.hasNext()) {
        i = -10000;
        z = false;
        break;
      }
      UserInfo userInfo = (UserInfo) it.next();
      if (SemDualAppManager.isDualAppId(userInfo.id)) {
        i = userInfo.id;
        z = true;
        break;
      }
    }
    String str = SystemProperties.get("persist.sys.dualapp.prop");
    if (str != null && str.length() > 0) {
      Log.d("DualAppManagerService", "dualapp_prop " + str);
    } else if (z) {
      SystemProperties.set("persist.sys.dualapp.prop", "0");
    } else {
      SystemProperties.set("persist.sys.dualapp.prop", "1");
    }
    if (z) {
      Log.d("DualAppManagerService", "Found DA Profile. Id = " + i);
      SystemProperties.set("sys.dualapp.profile_id", String.valueOf(i));
      return true;
    }
    Log.d("DualAppManagerService", "Can not found DA Profile. Id");
    SystemProperties.set("sys.dualapp.profile_id", "");
    return false;
  }

  public void updateWhitelistPackages() {
    Cursor query =
        mContext
            .getContentResolver()
            .query(
                Uri.parse("content://com.samsung.android.da.daagent.provider/getWhitelistApps"),
                null,
                null,
                null,
                null);
    mWhitelistedPkgMap.clear();
    if (query != null) {
      while (query.moveToNext()) {
        Log.d("DualAppManagerService", "updateWhitelistPackages : " + query.getString(0));
        mWhitelistedPkgMap.put(query.getString(0), 0);
      }
      query.close();
    }
  }

  public void updateInstalledWhitelistPackages() {
    Cursor query =
        mContext
            .getContentResolver()
            .query(
                Uri.parse("content://com.samsung.android.da.daagent.provider/getInstalledApps"),
                null,
                null,
                null,
                null);
    mInstalledWhitelistedPkgMap.clear();
    if (query != null) {
      while (query.moveToNext()) {
        try {
          Log.d(
              "DualAppManagerService",
              "updateInstalledWhitelistPackages : " + query.getString(0) + "/" + query.getInt(1));
          mInstalledWhitelistedPkgMap.put(query.getString(0), Integer.valueOf(query.getInt(1)));
        } finally {
          query.close();
        }
      }
    }
  }

  public static void recordDaUsageCount(Context context, Intent intent, int i, int i2, String str) {
    String str2;
    if (!SemDualAppManager.isDualAppId(i2) || intent == null) {
      return;
    }
    if (intent.getComponent() != null) {
      str2 = intent.getComponent().getPackageName();
    } else {
      str2 = intent.getPackage();
    }
    if (isInstalledWhitelistedPackageInternal(str2)) {
      sendInternalMsg(3, 0, 0, null);
    }
  }

  public void updateDAUsage() {
    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(7);
    int i2 = calendar.get(3);
    int i3 = calendar.get(5);
    if (thisWeek != i2) {
      mDaWeeklyUsageCount.clear();
      thisWeek = i2;
    }
    if (!mDaWeeklyUsageCount.contains(Integer.valueOf(i))) {
      mDaWeeklyUsageCount.add(Integer.valueOf(i));
      try {
        mContext
            .getContentResolver()
            .update(
                Uri.parse("content://com.samsung.android.da.daagent.provider/recordUsage"),
                new ContentValues(),
                null,
                null);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (mDaMonthlyUsageCount.contains(Integer.valueOf(i3))) {
      return;
    }
    mDaMonthlyUsageCount.add(Integer.valueOf(i3));
  }

  public String[] getAllWhitelistedPackages() {
    HashMap hashMap = mWhitelistedPkgMap;
    if (hashMap == null) {
      return null;
    }
    if (hashMap.isEmpty()) {
      Log.e("DualAppManagerService", "getAllWhitelistedPackages : empty");
      return null;
    }
    return (String[]) mWhitelistedPkgMap.keySet().toArray(new String[mWhitelistedPkgMap.size()]);
  }

  public static boolean isCrossAccessAllowed(int i, int i2) {
    if (SemDualAppManager.isDualAppId(i2) && i == 0) {
      return true;
    }
    return SemDualAppManager.isDualAppId(i) && i2 == 0;
  }
}
