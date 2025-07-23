package android.sec.clipboard.util;

import android.app.ActivityManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IUserManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.sec.clipboard.data.ClipboardConstants;
import android.text.TextUtils;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.provider.SemKnoxPolicyContract;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes3.dex */
public class ClipboardPolicyObserver extends ContentObserver {
    private static final String ALL_PACKAGES = "*";
    private static final String AUTHORITY = "com.sec.knox.rcppolicyprovider";
    private static final String SAMSUNG_COCKTAILBAR_PKGNAME = "com.samsung.android.app.cocktailbarservice";
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
    private static final Uri CONTENT_URI = Uri.parse("content://com.sec.knox.rcppolicyprovider/RCP_DATA");
    private static final Uri CLIPBOARD_ALLOWED_URI = ClipboardConstants.CLIPBOARD_ALLOWED_URI;
    private static final Uri CLIPBOARD_SHARED_ALLOWED_URI = ClipboardConstants.CLIPBOARD_SHARED_ALLOWED_URI;
    private static final Uri CLIPBOARD_RESCTRICTION_URI = Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1");
    private static final Uri CLIPBOARD_APPLICATION_URI = Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy");
    private static final Uri CLIPBOARD_ALLOWED_DENYLIST_APP_URI = ClipboardConstants.CLIPBOARD_ALLOWED_DENYLIST_APP_URI;
    private static final Uri CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI = ClipboardConstants.CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI;

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
    public void onChange(boolean z) {
        super.onChange(z);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        super.onChange(z, uri);
        Log.secD(this.TAG, "onChage is calledm uri : " + uri.toString());
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
        Log.secD(this.TAG, "onChage is calledm uri : " + uri.toString() + ", userId : " + i);
        if (!isInitialized()) {
            initHashMap();
        }
        if (uri.compareTo(CLIPBOARD_ALLOWED_URI) == 0) {
            updateClipboardAllowedMap(i);
        } else if (uri.compareTo(CLIPBOARD_SHARED_ALLOWED_URI) == 0) {
            updateClipboardSharedAllowedMap(i);
        } else if (uri.compareTo(CONTENT_URI) == 0) {
            updateRCPMap();
        } else if (uri.compareTo(CLIPBOARD_ALLOWED_DENYLIST_APP_URI) == 0) {
            updateClipboardDenyListMap(i);
        } else if (uri.compareTo(CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI) == 0) {
            updateClipboardAllowListMap(i);
        }
        ClipboardPolicyChangeListener clipboardPolicyChangeListener = this.mClipboardPolicyChangeListener;
        if (clipboardPolicyChangeListener != null) {
            clipboardPolicyChangeListener.onChanged();
        } else {
            Log.secD(this.TAG, "onChage - ClipboardPolicyChangeListener is null");
        }
    }

    private void updateRCPMap() {
        Log.secD(this.TAG, "updateRCPMap is called");
        if (getPersonaManager() != null) {
            List<Integer> knoxIds = getPersonaManager().getKnoxIds(false);
            for (int i = 0; knoxIds != null && i < knoxIds.size(); i++) {
                Integer num = knoxIds.get(i);
                int intValue = num.intValue();
                if (intValue > -1) {
                    this.mClipboardSharedAllowedKnoxToPersonalPolicy.put(num, Boolean.valueOf(getPersonaManager().isShareClipboardDataToOwnerAllowed(intValue)));
                    this.mClipboardSharedAllowedPersonalToKnoxPolicy.put(num, Boolean.valueOf(getPersonaManager().isShareClipboardDataToContainerAllowed(intValue)));
                } else {
                    Log.secD(this.TAG, "Wrong user : " + intValue);
                }
            }
            return;
        }
        Log.secD(this.TAG, "PersonaManager is null");
    }

    private void updateClipboardAllowedMap(int i) {
        Cursor query = this.mContext.getContentResolver().query(CLIPBOARD_RESCTRICTION_URI, null, SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_ALLOWED_AS_USER, new String[]{"false", Integer.toString(i)}, null);
        if (query != null) {
            try {
                try {
                    query.moveToFirst();
                    String string = query.getString(query.getColumnIndex(SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_ALLOWED_AS_USER));
                    this.mClipboardAllowedPolicy.put(Integer.valueOf(i), Boolean.valueOf(string));
                    Log.secD(this.TAG, "updateClipboardAllowedMap - userId : " + i + ", result : " + string);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.secD(this.TAG, "updateClipboardAllowedMap, exception is occured hence set true");
                    this.mClipboardAllowedPolicy.put(Integer.valueOf(i), true);
                }
                query.close();
                return;
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        Log.secD(this.TAG, "updateClipboardAllowedMap, cursor is null hence set true");
        this.mClipboardAllowedPolicy.put(Integer.valueOf(i), true);
    }

    private void updateClipboardSharedAllowedMap(int i) {
        Cursor query = this.mContext.getContentResolver().query(CLIPBOARD_RESCTRICTION_URI, null, SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_SHARE_ALLOWED_AS_USER, new String[]{Integer.toString(i)}, null);
        try {
            if (query != null) {
                try {
                    query.moveToFirst();
                    String string = query.getString(query.getColumnIndex(SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_SHARE_ALLOWED_AS_USER));
                    this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(i), Boolean.valueOf(string));
                    Log.secD(this.TAG, "updateClipboardSharedAllowedMap - userId : " + i + ", result : " + string);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.secD(this.TAG, "updateClipboardSharedAllowedMap, exception is occured hence set true");
                    this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(i), true);
                }
                query.close();
                return;
            }
            Log.secD(this.TAG, "updateClipboardSharedAllowedMap, cursor is null hence set true");
            this.mClipboardSharedAllowedPolicy.put(Integer.valueOf(i), true);
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }

    private void updateClipboardDenyListMap(int i) {
        String[] strArr = {Integer.toString(i)};
        this.mClipboardDenyListPolicyLock.writeLock().lock();
        Cursor cursor = null;
        try {
            try {
                Cursor query = this.mContext.getContentResolver().query(CLIPBOARD_APPLICATION_URI, null, "getPackagesFromDisableClipboardBlackListPerUidInternal", strArr, null);
                if (query != null) {
                    Bundle extras = query.getExtras();
                    if (extras != null) {
                        this.mClipboardDenyListPolicy.put(Integer.valueOf(i), (HashMap) extras.getSerializable("clipboard_blacklist_perUid"));
                    }
                } else {
                    this.mClipboardDenyListPolicy.remove(Integer.valueOf(i));
                }
                if (query != null && !query.isClosed()) {
                    query.close();
                }
                this.mClipboardDenyListPolicyLock.writeLock().unlock();
            } catch (Exception unused) {
                Log.secD(this.TAG, "updateClipboardDenyListMap - exception occured!.");
                if (0 != 0 && !cursor.isClosed()) {
                    cursor.close();
                }
                this.mClipboardDenyListPolicyLock.writeLock().unlock();
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            this.mClipboardDenyListPolicyLock.writeLock().unlock();
            throw th;
        }
    }

    private void updateClipboardAllowListMap(int i) {
        String[] strArr = {Integer.toString(i)};
        this.mClipboardAllowListPolicyLock.writeLock().lock();
        Cursor cursor = null;
        try {
            try {
                Cursor query = this.mContext.getContentResolver().query(CLIPBOARD_APPLICATION_URI, null, "getPackagesFromDisableClipboardWhiteListPerUidInternal", strArr, null);
                if (query != null) {
                    Bundle extras = query.getExtras();
                    if (extras != null) {
                        this.mClipboardAllowListPolicy.put(Integer.valueOf(i), (HashMap) extras.getSerializable("clipboard_whitelist_perUid"));
                    }
                } else {
                    this.mClipboardAllowListPolicy.remove(Integer.valueOf(i));
                }
                if (query != null && !query.isClosed()) {
                    query.close();
                }
                this.mClipboardAllowListPolicyLock.writeLock().unlock();
            } catch (Exception unused) {
                Log.secD(this.TAG, "updateClipboardAllowListMap - exception occured!.");
                if (0 != 0 && !cursor.isClosed()) {
                    cursor.close();
                }
                this.mClipboardAllowListPolicyLock.writeLock().unlock();
            }
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
            int focusedKnoxId = getPersonaManager().getFocusedKnoxId();
            return focusedKnoxId == 0 ? getUserId() : focusedKnoxId;
        }
        return getUserId();
    }

    private int getUserId() {
        return UserHandle.getCallingUserId();
    }

    private SemPersonaManager getPersonaManager() {
        if (this.mPersonaManager == null) {
            this.mPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
        }
        return this.mPersonaManager;
    }

    public boolean isClipboardAllowed(int i) {
        if (this.mClipboardAllowedPolicy.get(Integer.valueOf(i)) == null) {
            return true;
        }
        return this.mClipboardAllowedPolicy.get(Integer.valueOf(i)).booleanValue();
    }

    public boolean isClipboardSharedAllowed(int i) {
        if (this.mClipboardSharedAllowedPolicy.get(Integer.valueOf(i)) == null) {
            return true;
        }
        return this.mClipboardSharedAllowedPolicy.get(Integer.valueOf(i)).booleanValue();
    }

    public boolean isAllowedSharingKnoxDataToPersonal(int i) {
        boolean isAllowCrossProfileCopyPaste = isAllowCrossProfileCopyPaste(i);
        boolean isClipboardSharedAllowed = isClipboardSharedAllowed(i);
        Log.secD(this.TAG, "isAllowedSharingKnoxDataToPersonal: " + isAllowCrossProfileCopyPaste + ", canClipboardSharedAllowed: " + isClipboardSharedAllowed + ", userId=" + i);
        return isAllowCrossProfileCopyPaste && isClipboardSharedAllowed;
    }

    private boolean isAllowCrossProfileCopyPaste(int i) {
        boolean z;
        try {
            z = !this.mUm.getUserRestrictions(i).getBoolean(UserManager.DISALLOW_CROSS_PROFILE_COPY_PASTE);
        } catch (RemoteException e) {
            Log.secD(this.TAG, "get DISALLOW_CROSS_PROFILE_COPY_PASTE value failed: RemoteException occured " + e);
            z = false;
            Log.secD(this.TAG, "AllowCrossProfileCopyPaste =" + z + " userId=" + i);
            return z;
        } catch (SecurityException e2) {
            Log.secD(this.TAG, "getUserRestrictions failed : SecurityException occured " + e2.getMessage());
            z = false;
            Log.secD(this.TAG, "AllowCrossProfileCopyPaste =" + z + " userId=" + i);
            return z;
        }
        Log.secD(this.TAG, "AllowCrossProfileCopyPaste =" + z + " userId=" + i);
        return z;
    }

    public boolean isAllowedSharingPersonalDataToKnox(int i) {
        Log.secD(this.TAG, "isAllowedSharingPersonalDataToKnox, userId = " + i);
        return (this.mClipboardSharedAllowedPersonalToKnoxPolicy.get(Integer.valueOf(i)) == null ? true : this.mClipboardSharedAllowedPersonalToKnoxPolicy.get(Integer.valueOf(i)).booleanValue()) && isClipboardSharedAllowed(0);
    }

    public boolean isPackageAllowed(int i) {
        Map<Long, List<String>> map;
        String topActivityPackageName = getTopActivityPackageName();
        if (TextUtils.isEmpty(topActivityPackageName)) {
            Log.secD(this.TAG, "package name is empty.");
            return false;
        }
        boolean z = true;
        if (isKnoxVersion1(topActivityPackageName)) {
            Log.secD(this.TAG, "KNOX 1.0 not supported so blocking it.");
            return true;
        }
        this.mClipboardDenyListPolicyLock.readLock().lock();
        this.mClipboardAllowListPolicyLock.readLock().lock();
        try {
            try {
                map = this.mClipboardDenyListPolicy.get(Integer.valueOf(i));
            } catch (Exception unused) {
                Log.secD(this.TAG, "isPackageAllowed, Exception occure. isAllowed : " + z);
            }
            if (map == null) {
                return true;
            }
            Set<Long> keySet = map.keySet();
            if (keySet == null) {
                return true;
            }
            for (Long l : keySet) {
                l.longValue();
                Map<Long, List<String>> map2 = this.mClipboardDenyListPolicy.get(Integer.valueOf(i));
                Map<Long, List<String>> map3 = this.mClipboardAllowListPolicy.get(Integer.valueOf(i));
                List<String> list = map2 != null ? map2.get(l) : null;
                List<String> list2 = map3 != null ? map3.get(l) : null;
                if (isListIncludePackage(list, topActivityPackageName) && !(z = isListIncludePackage(list2, topActivityPackageName))) {
                    break;
                }
            }
            this.mClipboardDenyListPolicyLock.readLock().unlock();
            this.mClipboardAllowListPolicyLock.readLock().unlock();
            Log.secD(this.TAG, "isPackageAllowed, userId : " + i + ", packageName : " + topActivityPackageName + ", isAllowed : " + z);
            return z;
        } finally {
            this.mClipboardDenyListPolicyLock.readLock().unlock();
            this.mClipboardAllowListPolicyLock.readLock().unlock();
        }
    }

    private boolean isKnoxVersion1(String str) {
        return (TextUtils.isEmpty(str) || !str.startsWith("sec_container_") || str.contains("com.sec.knox.containeragent") || str.contains("com.sec.android.app.knoxlauncher")) ? false : true;
    }

    private boolean isListIncludePackage(List<String> list, String str) {
        if (list == null || TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : list) {
            if ("*".equals(str2) || str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    private String getTopActivityPackageName() {
        String str;
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid != null && packagesForUid.length == 1 && !SAMSUNG_KEYBOARD_PKGNAME.equals(packagesForUid[0]) && !"com.samsung.android.honeyboard".equals(packagesForUid[0]) && !"com.samsung.android.app.cocktailbarservice".equals(packagesForUid[0])) {
            return packagesForUid[0];
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            str = runningTasks.get(0).topActivity.getPackageName();
        } else {
            str = "";
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return str;
    }
}
