package com.android.server.enterprise.browser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.browser.IBrowserPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrowserPolicy extends IBrowserPolicy.Stub implements EnterpriseServiceCallback {
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public static final Uri SBROWSER_BOOKMARKS_URI = Uri.parse("content://com.sec.android.app.sbrowser.browser/bookmarks");
    public static final Uri CHROME_BOOKMARKS_URI = Uri.parse("content://com.android.partnerbookmarks/bookmarks");
    public final HashMap mCache = new HashMap();
    public final HashMap mUserCache = new HashMap();
    public EnterpriseDeviceManager mEDM = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrowserProxyCache {
        public int mAdminUid = -1;
        public String mProxySetting = null;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WebFilteringCache {
        public boolean mIsUrlBlacklistUpdated;
        public boolean mIsUrlFilterReportUpdated;
        public boolean mIsUrlFilterStateUpdated;
        public List mUrlBlacklistAllAdmin;
        public boolean mUrlFilterReportState;
        public boolean mUrlFilterStateCache;
    }

    public BrowserPolicy(Context context) {
        this.mContext = context;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(context);
        try {
            Iterator it = edmStorageProvider.getDataByFields("BROWSER_PROXY", null, null, new String[]{"adminUid", "proxyServer"}).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("proxyServer");
                long longValue = contentValues.getAsLong("adminUid") != null ? contentValues.getAsLong("adminUid").longValue() : 0L;
                int i = (int) (longValue >>> 32);
                int i2 = (int) longValue;
                int userId = UserHandle.getUserId(i2);
                if (!this.mCache.containsKey(Integer.valueOf(userId))) {
                    this.mCache.put(Integer.valueOf(userId), new HashMap());
                }
                if (!((HashMap) this.mCache.get(Integer.valueOf(userId))).containsKey(Integer.valueOf(i))) {
                    ((HashMap) this.mCache.get(Integer.valueOf(userId))).put(Integer.valueOf(i), new BrowserProxyCache());
                }
                BrowserProxyCache browserProxyCache = (BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(Integer.valueOf(i));
                if (asString != null) {
                    browserProxyCache.mAdminUid = i2;
                    browserProxyCache.mProxySetting = asString;
                } else {
                    browserProxyCache.getClass();
                }
            }
        } catch (Exception e) {
            Log.e("BrowserPolicy", "loadProxySettings() : failed", e);
        }
    }

    public static final Cursor getVisitedLike(ContentResolver contentResolver, String str, Uri uri, String[] strArr) {
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
            sb.append(" OR url = ");
            DatabaseUtils.appendEscapedSQLString(sb, "https://www." + str);
        } else {
            StringBuilder sb2 = new StringBuilder("url = ");
            DatabaseUtils.appendEscapedSQLString(sb2, str);
            sb2.append(" OR url = ");
            String str2 = "www." + str;
            DatabaseUtils.appendEscapedSQLString(sb2, str2);
            sb2.append(" OR url = ");
            DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str);
            sb2.append(" OR url = ");
            DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str2);
            sb = sb2;
        }
        return contentResolver.query(uri, strArr, sb.toString(), null, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x027f, code lost:
    
        if (r14 == null) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0273, code lost:
    
        android.os.Binder.restoreCallingIdentity(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0270, code lost:
    
        r14.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x026e, code lost:
    
        if (r14 == null) goto L106;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x013d A[Catch: all -> 0x0152, IllegalArgumentException -> 0x015f, TryCatch #9 {IllegalArgumentException -> 0x015f, blocks: (B:18:0x0124, B:20:0x013d, B:94:0x0155), top: B:17:0x0124, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0294 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0155 A[Catch: all -> 0x0152, IllegalArgumentException -> 0x015f, TRY_LEAVE, TryCatch #9 {IllegalArgumentException -> 0x015f, blocks: (B:18:0x0124, B:20:0x013d, B:94:0x0155), top: B:17:0x0124, outer: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addBookmark(com.samsung.android.knox.ContextInfo r25, java.lang.String r26, java.lang.String r27) {
        /*
            Method dump skipped, instructions count: 677
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.browser.BrowserPolicy.addBookmark(com.samsung.android.knox.ContextInfo, java.lang.String, java.lang.String):boolean");
    }

    public final boolean addWebBookmarkBitmap(ContextInfo contextInfo, Uri uri, String str, Bitmap bitmap) {
        ContextInfo enforceBrowserPermission = enforceBrowserPermission(contextInfo);
        if (uri == null || str == null) {
            return false;
        }
        return addBookmark(enforceBrowserPermission, uri.toString(), str);
    }

    public final boolean addWebBookmarkByteBuffer(ContextInfo contextInfo, Uri uri, String str, byte[] bArr) {
        ContextInfo enforceBrowserPermission = enforceBrowserPermission(contextInfo);
        if (uri == null || str == null) {
            return false;
        }
        return addBookmark(enforceBrowserPermission, uri.toString(), str);
    }

    public final boolean clearHttpProxy(ContextInfo contextInfo) {
        boolean z;
        ContextInfo enforceActiveAdminPermissionByContext = getEDM$3().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BROWSER_PROXY")));
        int i = enforceActiveAdminPermissionByContext.mCallerUid;
        int i2 = enforceActiveAdminPermissionByContext.mContainerId;
        int userId = UserHandle.getUserId(i);
        if (this.mCache.containsKey(Integer.valueOf(userId)) && ((HashMap) this.mCache.get(Integer.valueOf(userId))).containsKey(Integer.valueOf(i2)) && ((BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(Integer.valueOf(i2))).mAdminUid == i) {
            z = this.mEdmStorageProvider.removeByAdmin(i, i2, "BROWSER_PROXY");
            if (z) {
                BrowserProxyCache browserProxyCache = (BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(Integer.valueOf(i2));
                if (browserProxyCache.mAdminUid != -1) {
                    browserProxyCache.mAdminUid = -1;
                    browserProxyCache.mProxySetting = null;
                }
                ((HashMap) this.mCache.get(Integer.valueOf(userId))).remove(Integer.valueOf(i2));
                if (((HashMap) this.mCache.get(Integer.valueOf(userId))).isEmpty()) {
                    this.mCache.remove(Integer.valueOf(userId));
                }
                SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, "BrowserPolicy/getHttpProxy", userId);
                Slog.d("BrowserPolicy", "clearHttpProxy() : SecContentProvider updated.");
            }
        } else {
            z = false;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("clearHttpProxy() : ", "BrowserPolicy", z);
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ce, code lost:
    
        if (r11 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x015a, code lost:
    
        if (r11 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x015c, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0166, code lost:
    
        if (r11 == null) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean deleteWebBookmark(com.samsung.android.knox.ContextInfo r17, android.net.Uri r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.browser.BrowserPolicy.deleteWebBookmark(com.samsung.android.knox.ContextInfo, android.net.Uri, java.lang.String):boolean");
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump SecurityPolicy");
        } else {
            this.mEnterpriseDumpHelper.dumpTable(printWriter, "BROWSER", new String[]{"browserSettings"}, null);
        }
    }

    public final ContextInfo enforceBrowserPermission(ContextInfo contextInfo) {
        return getEDM$3().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS")));
    }

    public final ContextInfo enforceBrowserPermissionByContext(ContextInfo contextInfo) {
        return getEDM$3().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS")));
    }

    public final ContextInfo enforceFirewallPermission(ContextInfo contextInfo) {
        return getEDM$3().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
    }

    public final ContextInfo enforceFirewallPermissionByContext(ContextInfo contextInfo) {
        return getEDM$3().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
    }

    public final boolean getBrowserSettingStatus(ContextInfo contextInfo, int i) {
        boolean z = true;
        try {
            ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser(0, Utils.getCallingOrCurrentUserId(contextInfo), "BROWSER", "browserSettings");
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

    public final EnterpriseDeviceManager getEDM$3() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final String getHttpProxy(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i = contextInfo.mContainerId;
        String str = (this.mCache.containsKey(Integer.valueOf(callingOrCurrentUserId)) && ((HashMap) this.mCache.get(Integer.valueOf(callingOrCurrentUserId))).containsKey(Integer.valueOf(i))) ? ((BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(callingOrCurrentUserId))).get(Integer.valueOf(i))).mProxySetting : null;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m("getHttpProxy() : ", str, "BrowserPolicy");
        return str;
    }

    public final boolean getURLFilterEnabled(ContextInfo contextInfo, boolean z) {
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

    public final boolean getURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
        return getURLFilterEnabled(z2 ? enforceBrowserPermission(contextInfo) : enforceBrowserPermissionByContext(contextInfo), z);
    }

    public final boolean getURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
        return getURLFilterEnabled(z2 ? enforceFirewallPermission(contextInfo) : enforceFirewallPermissionByContext(contextInfo), z);
    }

    public final List getURLFilterList(ContextInfo contextInfo, boolean z) {
        List list;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "getURLFilterList => userId ", " callerUid ");
        m.append(contextInfo.mCallerUid);
        m.append(" allAdmins ");
        m.append(z);
        Log.d("BrowserPolicy", m.toString());
        WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
        if (!z) {
            return getUrlBlackList(contextInfo, false);
        }
        synchronized (webFilteringCache.mUrlBlacklistAllAdmin) {
            try {
                if (webFilteringCache.mIsUrlBlacklistUpdated) {
                    list = webFilteringCache.mUrlBlacklistAllAdmin;
                } else {
                    list = getUrlBlackList(contextInfo, true);
                    if (list != null && !((ArrayList) list).isEmpty()) {
                        Log.d("BrowserPolicy", "getUrlBlackList - synchronized");
                        ((ArrayList) webFilteringCache.mUrlBlacklistAllAdmin).clear();
                        ((ArrayList) webFilteringCache.mUrlBlacklistAllAdmin).addAll(list);
                        webFilteringCache.mIsUrlBlacklistUpdated = true;
                    }
                }
            } finally {
            }
        }
        return list;
    }

    public final List getURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
        return getURLFilterList(z2 ? enforceBrowserPermission(contextInfo) : enforceBrowserPermissionByContext(contextInfo), z);
    }

    public final List getURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
        return getURLFilterList(z2 ? enforceFirewallPermission(contextInfo) : enforceFirewallPermissionByContext(contextInfo), z);
    }

    public final List getURLFilterReport(ContextInfo contextInfo) {
        Log.d("BrowserPolicy", "getURLFilterReport()");
        ArrayList arrayList = new ArrayList();
        String[] strArr = {String.valueOf(0), String.valueOf(Utils.getCallingOrCurrentUserId(contextInfo))};
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        Iterator it = edmStorageProvider.getDataByFields("WebFilterLogTable", new String[]{"containerID", "userID"}, strArr, new String[]{"url", "time"}).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("time");
            String asString2 = contentValues.getAsString("url");
            if (asString != null && asString2 != null) {
                arrayList.add(asString.concat(":").concat(asString2));
            }
        }
        return arrayList;
    }

    public final boolean getURLFilterReportEnabled(ContextInfo contextInfo) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
        if (!webFilteringCache.mIsUrlFilterReportUpdated) {
            webFilteringCache.mUrlFilterReportState = getUrlFilterState(contextInfo, "logging");
            webFilteringCache.mIsUrlFilterReportUpdated = true;
            RCPManagerService$$ExternalSyntheticOutline0.m("WebFilteringCache", new StringBuilder("cache.mUrlFilterReportState=> "), webFilteringCache.mUrlFilterReportState);
            refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
        }
        return webFilteringCache.mUrlFilterReportState;
    }

    public final boolean getURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z, boolean z2) {
        return getURLFilterReportEnabled(z2 ? enforceBrowserPermission(contextInfo) : enforceBrowserPermissionByContext(contextInfo));
    }

    public final boolean getURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z, boolean z2) {
        return getURLFilterReportEnabled(z2 ? enforceFirewallPermission(contextInfo) : enforceFirewallPermissionByContext(contextInfo));
    }

    public final List getURLFilterReportEnforcingBrowserPermission(ContextInfo contextInfo) {
        return getURLFilterReport(enforceBrowserPermission(contextInfo));
    }

    public final List getURLFilterReportEnforcingFirewallPermission(ContextInfo contextInfo) {
        return getURLFilterReport(enforceFirewallPermission(contextInfo));
    }

    public final List getUrlBlackList(ContextInfo contextInfo, boolean z) {
        int i = contextInfo.mCallerUid;
        int i2 = contextInfo.mContainerId;
        String[] strArr = {"url"};
        ArrayList arrayList = new ArrayList();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "getUrlBlackList - uid ", "BrowserPolicy");
        if (z) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
            Log.d("BrowserPolicy", "Getting URLList called by server for user " + callingOrCurrentUserId);
            new ArrayList();
            Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(i2, callingOrCurrentUserId, "WebFilterTable", strArr)).iterator();
            while (it.hasNext()) {
                arrayList.add(((ContentValues) it.next()).getAsString("url"));
            }
        } else {
            Cursor cursorByAdmin = this.mEdmStorageProvider.getCursorByAdmin(i, i2, "WebFilterTable", strArr);
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
        }
        return arrayList;
    }

    public final boolean getUrlFilterState(ContextInfo contextInfo, String str) {
        boolean z;
        Log.d("BrowserPolicy", "getUrlFilterState - uid:" + contextInfo.mCallerUid + " containerId:" + contextInfo.mContainerId + " column:" + str);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(callingOrCurrentUserId, "getUrlFilterState - userId: ", "BrowserPolicy");
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(contextInfo.mContainerId, callingOrCurrentUserId, "WebFilterSettingsTable", new String[]{str})).iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            String asString = ((ContentValues) it.next()).getAsString(str);
            if (asString != null && asString.equals("true")) {
                z = true;
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("getUrlFilterState - ret: ", "BrowserPolicy", z);
        return z;
    }

    public final WebFilteringCache getWebFilteringCache(int i) {
        WebFilteringCache[] webFilteringCacheArr = (WebFilteringCache[]) this.mUserCache.get(Integer.valueOf(i));
        if (webFilteringCacheArr == null) {
            WebFilteringCache[] webFilteringCacheArr2 = new WebFilteringCache[2];
            for (int i2 = 0; i2 < 2; i2++) {
                WebFilteringCache webFilteringCache = new WebFilteringCache();
                webFilteringCache.mUrlBlacklistAllAdmin = null;
                webFilteringCache.mIsUrlBlacklistUpdated = false;
                webFilteringCache.mUrlFilterStateCache = false;
                webFilteringCache.mIsUrlFilterStateUpdated = false;
                webFilteringCache.mUrlFilterReportState = false;
                webFilteringCache.mIsUrlFilterReportUpdated = false;
                webFilteringCache.mUrlBlacklistAllAdmin = new ArrayList();
                webFilteringCacheArr2[i2] = webFilteringCache;
            }
            this.mUserCache.put(Integer.valueOf(i), webFilteringCacheArr2);
            webFilteringCacheArr = webFilteringCacheArr2;
        }
        return webFilteringCacheArr[0];
    }

    public final boolean isUrlBlocked(ContextInfo contextInfo, String str) {
        boolean z = false;
        if (!getURLFilterEnabled(contextInfo, true)) {
            Log.d("BrowserPolicy", "isUrlBlocked - Policy disabled");
            return false;
        }
        List uRLFilterList = getURLFilterList(contextInfo, true);
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
                    trim2 = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 0, trim2);
                }
                if (trim.endsWith("/")) {
                    trim = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 0, trim);
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
                    if (getURLFilterReportEnabled(contextInfo)) {
                        Log.d("BrowserPolicy", "saveURLBlockedReport");
                        Calendar calendar = Calendar.getInstance();
                        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
                        Log.d("BrowserPolicy", "saveURLBlockedReport > userId = " + callingOrCurrentUserId);
                        long timeInMillis = calendar.getTimeInMillis();
                        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("url", str);
                        m.put("time", String.valueOf(timeInMillis));
                        m.put("containerID", (Integer) 0);
                        m.put("userID", Integer.valueOf(callingOrCurrentUserId));
                        if (!this.mEdmStorageProvider.putValuesNoUpdate("WebFilterLogTable", m)) {
                            Log.d("BrowserPolicy", "isUrlBlocked - Failed on inserting log");
                        }
                    }
                }
            }
            z = z2;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isUrlBlocked: ", "BrowserPolicy", z);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int userId = UserHandle.getUserId(i);
        if (this.mCache.containsKey(Integer.valueOf(userId)) && ((HashMap) this.mCache.get(Integer.valueOf(userId))).containsKey(0) && ((BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(0)).mAdminUid == i) {
            BrowserProxyCache browserProxyCache = (BrowserProxyCache) ((HashMap) this.mCache.get(Integer.valueOf(userId))).get(0);
            if (browserProxyCache.mAdminUid != -1) {
                browserProxyCache.mAdminUid = -1;
                browserProxyCache.mProxySetting = null;
            }
            ((HashMap) this.mCache.get(Integer.valueOf(userId))).remove(0);
            if (((HashMap) this.mCache.get(Integer.valueOf(userId))).isEmpty()) {
                this.mCache.remove(Integer.valueOf(userId));
            }
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        WebFilteringCache webFilteringCache = getWebFilteringCache(UserHandle.getUserId(new ContextInfo(i, 0).mCallerUid));
        ((ArrayList) webFilteringCache.mUrlBlacklistAllAdmin).clear();
        webFilteringCache.mIsUrlBlacklistUpdated = false;
        webFilteringCache.mIsUrlFilterStateUpdated = false;
        webFilteringCache.mIsUrlFilterReportUpdated = false;
    }

    public final void refreshWebFiltering(WebFilteringCache webFilteringCache, int i) {
        WebFilteringCache[] webFilteringCacheArr;
        HashMap hashMap = this.mUserCache;
        if (hashMap == null || !hashMap.containsKey(Integer.valueOf(i)) || (webFilteringCacheArr = (WebFilteringCache[]) this.mUserCache.get(Integer.valueOf(i))) == null) {
            return;
        }
        this.mUserCache.remove(Integer.valueOf(i));
        webFilteringCacheArr[0] = webFilteringCache;
        this.mUserCache.put(Integer.valueOf(i), webFilteringCacheArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setBrowserSettingStatus(com.samsung.android.knox.ContextInfo r21, boolean r22, int r23) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.browser.BrowserPolicy.setBrowserSettingStatus(com.samsung.android.knox.ContextInfo, boolean, int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f6 A[Catch: Exception -> 0x0104, TRY_LEAVE, TryCatch #1 {Exception -> 0x0104, blocks: (B:29:0x00bb, B:35:0x00f6, B:39:0x00db, B:41:0x00f0), top: B:28:0x00bb }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setHttpProxy(com.samsung.android.knox.ContextInfo r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.browser.BrowserPolicy.setHttpProxy(com.samsung.android.knox.ContextInfo, java.lang.String):boolean");
    }

    public final int setURLFilterEnabled(ContextInfo contextInfo, boolean z) {
        Log.d("BrowserPolicy", "setURLFilterEnabled(" + z + ")");
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ContentValues contentValues = new ContentValues();
        contentValues.put("filtering", String.valueOf(z));
        boolean putValues = this.mEdmStorageProvider.putValues(i2, i, "WebFilterSettingsTable", contentValues);
        if (putValues) {
            WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
            synchronized (webFilteringCache.mUrlBlacklistAllAdmin) {
                try {
                    ((ArrayList) webFilteringCache.mUrlBlacklistAllAdmin).clear();
                    webFilteringCache.mIsUrlFilterStateUpdated = false;
                    webFilteringCache.mIsUrlBlacklistUpdated = false;
                    refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
                    if (!z) {
                        this.mEdmStorageProvider.removeByAdmin(i2, 0, "WebFilterTable");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, "FirewallPolicy/getURLFilterEnabled", callingOrCurrentUserId);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m("setURLFilterEnabled : ", "BrowserPolicy", z);
        return putValues ? 1 : 0;
    }

    public final int setURLFilterEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
        return setURLFilterEnabled(enforceBrowserPermission(contextInfo), z);
    }

    public final int setURLFilterEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
        return setURLFilterEnabled(enforceFirewallPermission(contextInfo), z);
    }

    public final int setURLFilterList(ContextInfo contextInfo, List list) {
        int i;
        int i2 = contextInfo.mContainerId;
        int i3 = contextInfo.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        if (list == null) {
            return 0;
        }
        Iterator it = list.iterator();
        ContentValues contentValues = new ContentValues();
        this.mEdmStorageProvider.removeByAdmin(i3, i2, "WebFilterTable");
        while (true) {
            if (!it.hasNext()) {
                i = 1;
                break;
            }
            contentValues.put("adminUid", String.valueOf(EdmStorageProviderBase.translateToAdminLUID(i3, i2)));
            contentValues.put("url", (String) it.next());
            Log.d("BrowserPolicy", "saveUrlBlackList - cv: " + contentValues);
            if (!this.mEdmStorageProvider.putValuesNoUpdate("WebFilterTable", contentValues)) {
                i = 0;
                break;
            }
            contentValues.clear();
        }
        if (i == 1) {
            WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
            synchronized (webFilteringCache.mUrlBlacklistAllAdmin) {
                ((ArrayList) webFilteringCache.mUrlBlacklistAllAdmin).clear();
                webFilteringCache.mIsUrlBlacklistUpdated = false;
                refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
            }
            SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, "FirewallPolicy/getURLFilterList", callingOrCurrentUserId);
        }
        return i;
    }

    public final int setURLFilterListEnforcingBrowserPermission(ContextInfo contextInfo, List list) {
        return setURLFilterList(enforceBrowserPermission(contextInfo), list);
    }

    public final int setURLFilterListEnforcingFirewallPermission(ContextInfo contextInfo, List list) {
        return setURLFilterList(enforceFirewallPermission(contextInfo), list);
    }

    public final int setURLFilterReportEnabled(ContextInfo contextInfo, boolean z) {
        int i = contextInfo.mContainerId;
        int i2 = contextInfo.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        ContentValues contentValues = new ContentValues();
        contentValues.put("logging", String.valueOf(z));
        boolean putValues = this.mEdmStorageProvider.putValues(i2, i, "WebFilterSettingsTable", contentValues);
        if (putValues) {
            Log.d("BrowserPolicy", "setURLFilterReportEnabled - Added to database, refreshing cache userId= " + callingOrCurrentUserId);
            WebFilteringCache webFilteringCache = getWebFilteringCache(callingOrCurrentUserId);
            webFilteringCache.mIsUrlFilterReportUpdated = false;
            webFilteringCache.mIsUrlBlacklistUpdated = false;
            refreshWebFiltering(webFilteringCache, callingOrCurrentUserId);
            SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, "FirewallPolicy/getURLFilterReportEnabled", callingOrCurrentUserId);
        }
        boolean uRLFilterReportEnabled = getURLFilterReportEnabled(contextInfo);
        if (!z || !uRLFilterReportEnabled) {
            Log.d("BrowserPolicy", "setURLFilterReportEnabled - Clean url report");
            this.mEdmStorageProvider.deleteDataByFields("WebFilterLogTable", new String[]{"containerID", "userID"}, new String[]{String.valueOf(0), String.valueOf(callingOrCurrentUserId)});
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("setURLFilterReportEnabled - return = ", "BrowserPolicy", putValues);
        return putValues ? 1 : 0;
    }

    public final int setURLFilterReportEnabledEnforcingBrowserPermission(ContextInfo contextInfo, boolean z) {
        return setURLFilterReportEnabled(enforceBrowserPermission(contextInfo), z);
    }

    public final int setURLFilterReportEnabledEnforcingFirewallPermission(ContextInfo contextInfo, boolean z) {
        return setURLFilterReportEnabled(enforceFirewallPermission(contextInfo), z);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
    }
}
