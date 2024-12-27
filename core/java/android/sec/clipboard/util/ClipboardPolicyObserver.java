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
                    instance =
                            new ClipboardPolicyObserver(
                                    context, new Handler(context.getMainLooper()));
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
        if (this.mClipboardPolicyChangeListener != null) {
            this.mClipboardPolicyChangeListener.onChanged();
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
                            Boolean.valueOf(
                                    getPersonaManager()
                                            .isShareClipboardDataToOwnerAllowed(userId)));
                    this.mClipboardSharedAllowedPersonalToKnoxPolicy.put(
                            Integer.valueOf(userId),
                            Boolean.valueOf(
                                    getPersonaManager()
                                            .isShareClipboardDataToContainerAllowed(userId)));
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
                                CLIPBOARD_RESCTRICTION_URI,
                                null,
                                SemKnoxPolicyContract.RestrictionPolicy.CLIPBOARD_ALLOWED_AS_USER,
                                selectionArgs,
                                null);
        try {
            if (cursor != null) {
                try {
                    cursor.moveToFirst();
                    String result =
                            cursor.getString(
                                    cursor.getColumnIndex(
                                            SemKnoxPolicyContract.RestrictionPolicy
                                                    .CLIPBOARD_ALLOWED_AS_USER));
                    this.mClipboardAllowedPolicy.put(
                            Integer.valueOf(userId), Boolean.valueOf(result));
                    Log.secD(
                            this.TAG,
                            "updateClipboardAllowedMap - userId : "
                                    + userId
                                    + ", result : "
                                    + result);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.secD(
                            this.TAG,
                            "updateClipboardAllowedMap, exception is occured hence set true");
                    this.mClipboardAllowedPolicy.put(Integer.valueOf(userId), true);
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
                                SemKnoxPolicyContract.RestrictionPolicy
                                        .CLIPBOARD_SHARE_ALLOWED_AS_USER,
                                selectionArgs,
                                null);
        try {
            if (cursor != null) {
                try {
                    cursor.moveToFirst();
                    String result =
                            cursor.getString(
                                    cursor.getColumnIndex(
                                            SemKnoxPolicyContract.RestrictionPolicy
                                                    .CLIPBOARD_SHARE_ALLOWED_AS_USER));
                    this.mClipboardSharedAllowedPolicy.put(
                            Integer.valueOf(userId), Boolean.valueOf(result));
                    Log.secD(
                            this.TAG,
                            "updateClipboardSharedAllowedMap - userId : "
                                    + userId
                                    + ", result : "
                                    + result);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.secD(
                            this.TAG,
                            "updateClipboardSharedAllowedMap, exception is occured hence set true");
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004d, code lost:

       if (r0.isClosed() == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0064, code lost:

       r0.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0062, code lost:

       if (r0.isClosed() == false) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateClipboardDenyListMap(int r8) {
        /*
            r7 = this;
            java.lang.String r0 = java.lang.Integer.toString(r8)
            java.lang.String[] r5 = new java.lang.String[]{r0}
            r0 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r7.mClipboardDenyListPolicyLock
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.lock()
            android.content.Context r1 = r7.mContext     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            android.net.Uri r2 = android.sec.clipboard.util.ClipboardPolicyObserver.CLIPBOARD_APPLICATION_URI     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.String r4 = "getPackagesFromDisableClipboardBlackListPerUidInternal"
            r6 = 0
            r3 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r0 = r1
            if (r0 == 0) goto L3e
            android.os.Bundle r1 = r0.getExtras()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            if (r1 == 0) goto L3d
            java.lang.String r2 = "clipboard_blacklist_perUid"
            java.io.Serializable r2 = r1.getSerializable(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r3 = r2
            java.util.HashMap r3 = (java.util.HashMap) r3     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, java.util.List<java.lang.String>>> r4 = r7.mClipboardDenyListPolicy     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r4.put(r6, r3)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
        L3d:
            goto L47
        L3e:
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, java.util.List<java.lang.String>>> r1 = r7.mClipboardDenyListPolicy     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r1.remove(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
        L47:
            if (r0 == 0) goto L67
            boolean r1 = r0.isClosed()
            if (r1 != 0) goto L67
            goto L64
        L50:
            r1 = move-exception
            goto L72
        L52:
            r1 = move-exception
            java.lang.String r2 = r7.TAG     // Catch: java.lang.Throwable -> L50
            java.lang.String r3 = "updateClipboardDenyListMap - exception occured!."
            android.sec.clipboard.util.Log.secD(r2, r3)     // Catch: java.lang.Throwable -> L50
            if (r0 == 0) goto L67
            boolean r1 = r0.isClosed()
            if (r1 != 0) goto L67
        L64:
            r0.close()
        L67:
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r7.mClipboardDenyListPolicyLock
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.unlock()
            return
        L72:
            if (r0 == 0) goto L7d
            boolean r2 = r0.isClosed()
            if (r2 != 0) goto L7d
            r0.close()
        L7d:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = r7.mClipboardDenyListPolicyLock
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r2 = r2.writeLock()
            r2.unlock()
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.sec.clipboard.util.ClipboardPolicyObserver.updateClipboardDenyListMap(int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004d, code lost:

       if (r0.isClosed() == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0064, code lost:

       r0.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0062, code lost:

       if (r0.isClosed() == false) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateClipboardAllowListMap(int r8) {
        /*
            r7 = this;
            java.lang.String r0 = java.lang.Integer.toString(r8)
            java.lang.String[] r5 = new java.lang.String[]{r0}
            r0 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r7.mClipboardAllowListPolicyLock
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.lock()
            android.content.Context r1 = r7.mContext     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            android.net.Uri r2 = android.sec.clipboard.util.ClipboardPolicyObserver.CLIPBOARD_APPLICATION_URI     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.String r4 = "getPackagesFromDisableClipboardWhiteListPerUidInternal"
            r6 = 0
            r3 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r0 = r1
            if (r0 == 0) goto L3e
            android.os.Bundle r1 = r0.getExtras()     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            if (r1 == 0) goto L3d
            java.lang.String r2 = "clipboard_whitelist_perUid"
            java.io.Serializable r2 = r1.getSerializable(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r3 = r2
            java.util.HashMap r3 = (java.util.HashMap) r3     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, java.util.List<java.lang.String>>> r4 = r7.mClipboardAllowListPolicy     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r4.put(r6, r3)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
        L3d:
            goto L47
        L3e:
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.Long, java.util.List<java.lang.String>>> r1 = r7.mClipboardAllowListPolicy     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
            r1.remove(r2)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L52
        L47:
            if (r0 == 0) goto L67
            boolean r1 = r0.isClosed()
            if (r1 != 0) goto L67
            goto L64
        L50:
            r1 = move-exception
            goto L72
        L52:
            r1 = move-exception
            java.lang.String r2 = r7.TAG     // Catch: java.lang.Throwable -> L50
            java.lang.String r3 = "updateClipboardAllowListMap - exception occured!."
            android.sec.clipboard.util.Log.secD(r2, r3)     // Catch: java.lang.Throwable -> L50
            if (r0 == 0) goto L67
            boolean r1 = r0.isClosed()
            if (r1 != 0) goto L67
        L64:
            r0.close()
        L67:
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r7.mClipboardAllowListPolicyLock
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.unlock()
            return
        L72:
            if (r0 == 0) goto L7d
            boolean r2 = r0.isClosed()
            if (r2 != 0) goto L7d
            r0.close()
        L7d:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = r7.mClipboardAllowListPolicyLock
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r2 = r2.writeLock()
            r2.unlock()
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.sec.clipboard.util.ClipboardPolicyObserver.updateClipboardAllowListMap(int):void");
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
        boolean result =
                this.mClipboardSharedAllowedPolicy.get(Integer.valueOf(userId)).booleanValue();
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
                    "get DISALLOW_CROSS_PROFILE_COPY_PASTE value failed: RemoteException occured "
                            + e);
        } catch (SecurityException e2) {
            Log.secD(
                    this.TAG,
                    "getUserRestrictions failed : SecurityException occured " + e2.getMessage());
        }
        Log.secD(
                this.TAG, "AllowCrossProfileCopyPaste =" + canCrossCopyPaste + " userId=" + userId);
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
                List<String> denyList =
                        denyListMap != null ? denyListMap.get(Long.valueOf(uid)) : null;
                List<String> allowList =
                        allowListMap != null ? allowListMap.get(Long.valueOf(uid)) : null;
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
        ActivityManager activityManager =
                (ActivityManager) this.mContext.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = activityManager.getRunningTasks(1);
        if (runningTaskInfo != null && runningTaskInfo.size() > 0) {
            ActivityManager.RunningTaskInfo foregroundTaskInfo = runningTaskInfo.get(0);
            callerPackageName = foregroundTaskInfo.topActivity.getPackageName();
        }
        Binder.restoreCallingIdentity(identity);
        return callerPackageName;
    }
}
