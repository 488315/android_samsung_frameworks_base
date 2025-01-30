package android.sec.clipboard.util;

import android.app.ActivityManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IUserManager;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.sec.clipboard.data.ClipboardConstants;
import android.text.TextUtils;
import com.samsung.android.knox.SemPersonaManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes3.dex */
public class ClipboardPolicyObserver extends ContentObserver {
  private static final String ALL_PACKAGES = "*";
  private static final String AUTHORITY = "com.sec.knox.rcppolicyprovider";
  private static final String SAMSUNG_COCKTAILBAR_PKGNAME =
      "com.samsung.android.app.cocktailbarservice";
  private static final String SAMSUNG_HONEYBOARD_PKGNAME = "com.samsung.android.honeyboard";
  private static final String SAMSUNG_KEYBOARD_PKGNAME = "com.sec.android.inputmethod";
  private static final String TABLE_NAME = "RCP_DATA";
  private static final String URL = "content://com.sec.knox.rcppolicyprovider/RCP_DATA";
  private static ClipboardPolicyObserver instance;
  private String TAG;
  private Map<Integer, Map<Long, List<String>>> mClipboardAllowListPolicy;
  private ReentrantReadWriteLock mClipboardAllowListPolicyLock;
  private HashMap<Integer, Boolean> mClipboardAllowedPolicy;
  private Map<Integer, Map<Long, List<String>>> mClipboardDenyListPolicy;
  private ReentrantReadWriteLock mClipboardDenyListPolicyLock;
  private ClipboardPolicyChangeListener mClipboardPolicyChangeListener;
  private HashMap<Integer, Boolean> mClipboardSharedAllowedKnoxToPersonalPolicy;
  private HashMap<Integer, Boolean> mClipboardSharedAllowedPersonalToKnoxPolicy;
  private HashMap<Integer, Boolean> mClipboardSharedAllowedPolicy;
  private Context mContext;
  private boolean mIsInitialized;
  private SemPersonaManager mPersonaManager;
  private IUserManager mUm;
  private static final Uri CONTENT_URI =
      Uri.parse("content://com.sec.knox.rcppolicyprovider/RCP_DATA");
  private static final Uri CLIPBOARD_ALLOWED_URI = ClipboardConstants.CLIPBOARD_ALLOWED_URI;
  private static final Uri CLIPBOARD_SHARED_ALLOWED_URI =
      ClipboardConstants.CLIPBOARD_SHARED_ALLOWED_URI;
  private static final Uri CLIPBOARD_RESCTRICTION_URI =
      Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1");
  private static final Uri CLIPBOARD_APPLICATION_URI =
      Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy");
  private static final Uri CLIPBOARD_ALLOWED_DENYLIST_APP_URI =
      ClipboardConstants.CLIPBOARD_ALLOWED_DENYLIST_APP_URI;
  private static final Uri CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI =
      ClipboardConstants.CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI;

  public interface ClipboardPolicyChangeListener {
    void onChanged();
  }

  private ClipboardPolicyObserver(Context context, Handler handler) {
    super(handler);
    this.TAG = "ClipboardPolicyObserver";
    this.mClipboardAllowedPolicy = null;
    this.mClipboardSharedAllowedPolicy = null;
    this.mClipboardAllowListPolicy = null;
    this.mClipboardDenyListPolicy = null;
    this.mClipboardSharedAllowedKnoxToPersonalPolicy = null;
    this.mClipboardSharedAllowedPersonalToKnoxPolicy = null;
    this.mPersonaManager = null;
    this.mClipboardAllowListPolicyLock = new ReentrantReadWriteLock();
    this.mClipboardDenyListPolicyLock = new ReentrantReadWriteLock();
    this.mIsInitialized = false;
    this.mClipboardPolicyChangeListener = null;
    this.mContext = context;
    this.mUm = (IUserManager) ServiceManager.getService("user");
    initHashMap();
    updateRCPMap();
    updateClipboardAllowedMap(getPersonaId());
    updateClipboardSharedAllowedMap(getPersonaId());
  }

  private void initHashMap() {
    this.mClipboardAllowedPolicy = new HashMap<>();
    this.mClipboardSharedAllowedPolicy = new HashMap<>();
    this.mClipboardSharedAllowedKnoxToPersonalPolicy = new HashMap<>();
    this.mClipboardSharedAllowedPersonalToKnoxPolicy = new HashMap<>();
    this.mClipboardAllowListPolicy = new HashMap();
    this.mClipboardDenyListPolicy = new HashMap();
    this.mIsInitialized = true;
  }

  private boolean isInitialized() {
    return this.mIsInitialized;
  }

  public static ClipboardPolicyObserver getInstance(Context context) {
    if (instance == null) {
      synchronized (ClipboardPolicyObserver.class) {
        if (instance == null) {
          instance = new ClipboardPolicyObserver(context, new Handler(context.getMainLooper()));
        }
      }
    }
    return instance;
  }

  @Override // android.database.ContentObserver
  public void onChange(boolean selfChange) {
    super.onChange(selfChange);
  }

  @Override // android.database.ContentObserver
  public void onChange(boolean selfChange, Uri uri) {
    super.onChange(selfChange, uri);
    Log.secD(this.TAG, "onChage is calledm uri : " + uri.toString());
  }

  @Override // android.database.ContentObserver
  public void onChange(boolean selfChange, Uri uri, int userId) {
    Log.secD(this.TAG, "onChage is calledm uri : " + uri.toString() + ", userId : " + userId);
    if (!isInitialized()) {
      initHashMap();
    }
    if (uri.compareTo(CLIPBOARD_ALLOWED_URI) == 0) {
      updateClipboardAllowedMap(userId);
    } else if (uri.compareTo(CLIPBOARD_SHARED_ALLOWED_URI) == 0) {
      updateClipboardSharedAllowedMap(userId);
    } else if (uri.compareTo(CONTENT_URI) == 0) {
      updateRCPMap();
    } else if (uri.compareTo(CLIPBOARD_ALLOWED_DENYLIST_APP_URI) == 0) {
      updateClipboardDenyListMap(userId);
    } else if (uri.compareTo(CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI) == 0) {
      updateClipboardAllowListMap(userId);
    }
    ClipboardPolicyChangeListener clipboardPolicyChangeListener =
        this.mClipboardPolicyChangeListener;
    if (clipboardPolicyChangeListener != null) {
      clipboardPolicyChangeListener.onChanged();
    } else {
      Log.secD(this.TAG, "onChage - ClipboardPolicyChangeListener is null");
    }
  }

  private void updateRCPMap() {
    Log.secD(this.TAG, "updateRCPMap is called");
    if (getPersonaManager() != null) {
      List<Integer> idList = getPersonaManager().getKnoxIds(false);
      for (int i = 0; idList != null && i < idList.size(); i++) {
        int userId = idList.get(i).intValue();
        if (userId > -1) {
          this.mClipboardSharedAllowedKnoxToPersonalPolicy.put(
              Integer.valueOf(userId),
              Boolean.valueOf(getPersonaManager().isShareClipboardDataToOwnerAllowed(userId)));
          this.mClipboardSharedAllowedPersonalToKnoxPolicy.put(
              Integer.valueOf(userId),
              Boolean.valueOf(getPersonaManager().isShareClipboardDataToContainerAllowed(userId)));
        } else {
          Log.secD(this.TAG, "Wrong user : " + userId);
        }
      }
      return;
    }
    Log.secD(this.TAG, "PersonaManager is null");
  }

  private void updateClipboardAllowedMap(int userId) {
    String[] selectionArgs = {"false", Integer.toString(userId)};
    Cursor cursor =
        this.mContext
            .getContentResolver()
            .query(
                CLIPBOARD_RESCTRICTION_URI, null, "isClipboardAllowedAsUser", selectionArgs, null);
    try {
      if (cursor != null) {
        try {
          cursor.moveToFirst();
          String result = cursor.getString(cursor.getColumnIndex("isClipboardAllowedAsUser"));
          this.mClipboardAllowedPolicy.put(Integer.valueOf(userId), Boolean.valueOf(result));
          Log.secD(
              this.TAG, "updateClipboardAllowedMap - userId : " + userId + ", result : " + result);
        } catch (Exception e) {
          e.printStackTrace();
          Log.secD(this.TAG, "updateClipboardAllowedMap, exception is occured hence set true");
          this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(userId), true);
        }
        return;
      }
      Log.secD(this.TAG, "updateClipboardAllowedMap, cursor is null hence set true");
      this.mClipboardAllowedPolicy.put(Integer.valueOf(userId), true);
    } finally {
      cursor.close();
    }
  }

  private void updateClipboardSharedAllowedMap(int userId) {
    String[] selectionArgs = {Integer.toString(userId)};
    Cursor cursor =
        this.mContext
            .getContentResolver()
            .query(
                CLIPBOARD_RESCTRICTION_URI,
                null,
                "isClipboardShareAllowedAsUser",
                selectionArgs,
                null);
    try {
      if (cursor != null) {
        try {
          cursor.moveToFirst();
          String result = cursor.getString(cursor.getColumnIndex("isClipboardShareAllowedAsUser"));
          this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(userId), Boolean.valueOf(result));
          Log.secD(
              this.TAG,
              "updateClipboardSharedAllowedMap - userId : " + userId + ", result : " + result);
        } catch (Exception e) {
          e.printStackTrace();
          Log.secD(
              this.TAG, "updateClipboardSharedAllowedMap, exception is occured hence set true");
          this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(userId), true);
        }
        return;
      }
      Log.secD(this.TAG, "updateClipboardSharedAllowedMap, cursor is null hence set true");
      this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(userId), true);
    } finally {
      cursor.close();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:11:0x004e, code lost:

     if (r0.isClosed() == false) goto L21;
  */
  /* JADX WARN: Code restructure failed: missing block: B:12:0x0065, code lost:

     r0.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:28:0x0063, code lost:

     if (r0.isClosed() == false) goto L21;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void updateClipboardDenyListMap(int userId) {
    String[] selectionArgs = {Integer.toString(userId)};
    Cursor cursor = null;
    this.mClipboardDenyListPolicyLock.writeLock().lock();
    try {
      try {
        cursor =
            this.mContext
                .getContentResolver()
                .query(
                    CLIPBOARD_APPLICATION_URI,
                    null,
                    "getPackagesFromDisableClipboardBlackListPerUidInternal",
                    selectionArgs,
                    null);
        if (cursor != null) {
          Bundle bundle = cursor.getExtras();
          if (bundle != null) {
            Serializable serial = bundle.getSerializable("clipboard_blacklist_perUid");
            Map<Long, List<String>> resultMap = (HashMap) serial;
            this.mClipboardDenyListPolicy.put(Integer.valueOf(userId), resultMap);
          }
        } else {
          this.mClipboardDenyListPolicy.remove(Integer.valueOf(userId));
        }
        if (cursor != null) {}
      } catch (Exception e) {
        Log.secD(this.TAG, "updateClipboardDenyListMap - exception occured!.");
        if (0 != 0) {}
      }
      this.mClipboardDenyListPolicyLock.writeLock().unlock();
    } catch (Throwable th) {
      if (0 != 0 && !cursor.isClosed()) {
        cursor.close();
      }
      this.mClipboardDenyListPolicyLock.writeLock().unlock();
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:11:0x004e, code lost:

     if (r0.isClosed() == false) goto L21;
  */
  /* JADX WARN: Code restructure failed: missing block: B:12:0x0065, code lost:

     r0.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:28:0x0063, code lost:

     if (r0.isClosed() == false) goto L21;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void updateClipboardAllowListMap(int userId) {
    String[] selectionArgs = {Integer.toString(userId)};
    Cursor cursor = null;
    this.mClipboardAllowListPolicyLock.writeLock().lock();
    try {
      try {
        cursor =
            this.mContext
                .getContentResolver()
                .query(
                    CLIPBOARD_APPLICATION_URI,
                    null,
                    "getPackagesFromDisableClipboardWhiteListPerUidInternal",
                    selectionArgs,
                    null);
        if (cursor != null) {
          Bundle bundle = cursor.getExtras();
          if (bundle != null) {
            Serializable serial = bundle.getSerializable("clipboard_whitelist_perUid");
            Map<Long, List<String>> resultMap = (HashMap) serial;
            this.mClipboardAllowListPolicy.put(Integer.valueOf(userId), resultMap);
          }
        } else {
          this.mClipboardAllowListPolicy.remove(Integer.valueOf(userId));
        }
        if (cursor != null) {}
      } catch (Exception e) {
        Log.secD(this.TAG, "updateClipboardAllowListMap - exception occured!.");
        if (0 != 0) {}
      }
      this.mClipboardAllowListPolicyLock.writeLock().unlock();
    } catch (Throwable th) {
      if (0 != 0 && !cursor.isClosed()) {
        cursor.close();
      }
      this.mClipboardAllowListPolicyLock.writeLock().unlock();
      throw th;
    }
  }

  public int getPersonaId() {
    if (getPersonaManager() != null) {
      int userId = getPersonaManager().getFocusedKnoxId();
      if (userId == 0) {
        return getUserId();
      }
      return userId;
    }
    return getUserId();
  }

  private int getUserId() {
    int userId = UserHandle.getCallingUserId();
    return userId;
  }

  private SemPersonaManager getPersonaManager() {
    if (!ClipboardConstants.KNOX_V2_ENABLED) {
      return null;
    }
    if (this.mPersonaManager == null) {
      this.mPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
    }
    return this.mPersonaManager;
  }

  public boolean isClipboardAllowed(int userId) {
    if (this.mClipboardAllowedPolicy.get(Integer.valueOf(userId)) == null) {
      return true;
    }
    boolean result = this.mClipboardAllowedPolicy.get(Integer.valueOf(userId)).booleanValue();
    return result;
  }

  public boolean isClipboardSharedAllowed(int userId) {
    if (this.mClipboardSharedAllowedPolicy.get(Integer.valueOf(userId)) == null) {
      return true;
    }
    boolean result = this.mClipboardSharedAllowedPolicy.get(Integer.valueOf(userId)).booleanValue();
    return result;
  }

  public boolean isAllowedSharingKnoxDataToPersonal(int userId) {
    boolean canCrossCopyPaste = isAllowCrossProfileCopyPaste(userId);
    boolean canClipboardSharedAllowed = isClipboardSharedAllowed(userId);
    Log.secD(
        this.TAG,
        "isAllowedSharingKnoxDataToPersonal: "
            + canCrossCopyPaste
            + ", canClipboardSharedAllowed: "
            + canClipboardSharedAllowed
            + ", userId="
            + userId);
    return canCrossCopyPaste && canClipboardSharedAllowed;
  }

  private boolean isAllowCrossProfileCopyPaste(int userId) {
    boolean canCrossCopyPaste = false;
    try {
      Bundle b = this.mUm.getUserRestrictions(userId);
      canCrossCopyPaste = !b.getBoolean(UserManager.DISALLOW_CROSS_PROFILE_COPY_PASTE);
    } catch (RemoteException e) {
      Log.secD(
          this.TAG,
          "get DISALLOW_CROSS_PROFILE_COPY_PASTE value failed: RemoteException occured " + e);
    } catch (SecurityException e2) {
      Log.secD(
          this.TAG, "getUserRestrictions failed : SecurityException occured " + e2.getMessage());
    }
    Log.secD(this.TAG, "AllowCrossProfileCopyPaste =" + canCrossCopyPaste + " userId=" + userId);
    return canCrossCopyPaste;
  }

  public boolean isAllowedSharingPersonalDataToKnox(int userId) {
    boolean result;
    Log.secD(this.TAG, "isAllowedSharingPersonalDataToKnox, userId = " + userId);
    if (this.mClipboardSharedAllowedPersonalToKnoxPolicy.get(Integer.valueOf(userId)) == null) {
      result = true;
    } else {
      result =
          this.mClipboardSharedAllowedPersonalToKnoxPolicy
              .get(Integer.valueOf(userId))
              .booleanValue();
    }
    boolean canClipboardSharedAllowed = isClipboardSharedAllowed(0);
    return result && canClipboardSharedAllowed;
  }

  public boolean isPackageAllowed(int userId) {
    Map<Long, List<String>> clipboardDenyListMap;
    boolean isAllowed = true;
    String packageName = getTopActivityPackageName();
    if (TextUtils.isEmpty(packageName)) {
      Log.secD(this.TAG, "package name is empty.");
      return false;
    }
    if (isKnoxVersion1(packageName)) {
      Log.secD(this.TAG, "KNOX 1.0 not supported so blocking it.");
      return true;
    }
    this.mClipboardDenyListPolicyLock.readLock().lock();
    this.mClipboardAllowListPolicyLock.readLock().lock();
    try {
      try {
        clipboardDenyListMap = this.mClipboardDenyListPolicy.get(Integer.valueOf(userId));
      } catch (Exception e) {
        Log.secD(this.TAG, "isPackageAllowed, Exception occure. isAllowed : " + isAllowed);
      }
      if (clipboardDenyListMap == null) {
        return true;
      }
      Set<Long> mdmAdminUids = clipboardDenyListMap.keySet();
      if (mdmAdminUids == null) {
        return true;
      }
      Iterator<Long> it = mdmAdminUids.iterator();
      while (it.hasNext()) {
        long uid = it.next().longValue();
        Map<Long, List<String>> denyListMap =
            this.mClipboardDenyListPolicy.get(Integer.valueOf(userId));
        Map<Long, List<String>> allowListMap =
            this.mClipboardAllowListPolicy.get(Integer.valueOf(userId));
        List<String> denyList = denyListMap != null ? denyListMap.get(Long.valueOf(uid)) : null;
        List<String> allowList = allowListMap != null ? allowListMap.get(Long.valueOf(uid)) : null;
        boolean isDenyListIncludePackage = isListIncludePackage(denyList, packageName);
        if (isDenyListIncludePackage
            && !(isAllowed = isListIncludePackage(allowList, packageName))) {
          break;
        }
      }
      this.mClipboardDenyListPolicyLock.readLock().unlock();
      this.mClipboardAllowListPolicyLock.readLock().unlock();
      Log.secD(
          this.TAG,
          "isPackageAllowed, userId : "
              + userId
              + ", packageName : "
              + packageName
              + ", isAllowed : "
              + isAllowed);
      return isAllowed;
    } finally {
      this.mClipboardDenyListPolicyLock.readLock().unlock();
      this.mClipboardAllowListPolicyLock.readLock().unlock();
    }
  }

  private boolean isKnoxVersion1(String packageName) {
    return (TextUtils.isEmpty(packageName)
            || !packageName.startsWith("sec_container_")
            || packageName.contains("com.sec.knox.containeragent")
            || packageName.contains("com.sec.android.app.knoxlauncher"))
        ? false
        : true;
  }

  private boolean isListIncludePackage(List<String> list, String packageName) {
    if (list == null || TextUtils.isEmpty(packageName)) {
      return false;
    }
    for (String name : list) {
      if ("*".equals(name) || packageName.equals(name)) {
        return true;
      }
    }
    return false;
  }

  private String getTopActivityPackageName() {
    int callerUid = Binder.getCallingUid();
    String callerPackageName = "";
    String[] packageList = this.mContext.getPackageManager().getPackagesForUid(callerUid);
    if (packageList != null
        && packageList.length == 1
        && !SAMSUNG_KEYBOARD_PKGNAME.equals(packageList[0])
        && !"com.samsung.android.honeyboard".equals(packageList[0])
        && !"com.samsung.android.app.cocktailbarservice".equals(packageList[0])) {
      return packageList[0];
    }
    long identity = Binder.clearCallingIdentity();
    ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
    List<ActivityManager.RunningTaskInfo> runningTaskInfo = activityManager.getRunningTasks(1);
    if (runningTaskInfo != null && runningTaskInfo.size() > 0) {
      ActivityManager.RunningTaskInfo foregroundTaskInfo = runningTaskInfo.get(0);
      callerPackageName = foregroundTaskInfo.topActivity.getPackageName();
    }
    Binder.restoreCallingIdentity(identity);
    return callerPackageName;
  }
}
