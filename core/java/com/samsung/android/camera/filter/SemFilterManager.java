package com.samsung.android.camera.filter;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes5.dex */
public class SemFilterManager {
    private static final String AUTHORITY = "com.samsung.android.provider.filterprovider/filters";
    private static final String FILTER_AUTHORITY =
            "com.samsung.android.provider.filterprovider/filters";
    public static final int FILTER_EVENT_ADD = 0;
    public static final int FILTER_EVENT_DELETE = 1;
    public static final int FILTER_EVENT_LOCALE_CHANGE = 2;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int FILTER_EVENT_RESET = 3;

    private static final String FILTER_NAME = "name";
    private static final String FILTER_PACKAGE = "com.samsung.android.provider.filterprovider";
    private static final String FILTER_PACKAGE_NAME = "package_name";
    private static final String FILTER_TITLE = "title";
    private static final String FILTER_VENDOR = "vendor";
    private static final String FILTER_VERSION = "version";
    private static final int INDEX_FILTER_CATEGORY = 4;
    private static final int INDEX_FILTER_FILE_NAME = 1;
    private static final int INDEX_FILTER_NAME = 0;
    private static final int INDEX_FILTER_PACKAGE_NAME = 2;
    private static final int INDEX_FILTER_TITLE_ID = 6;
    private static final int INDEX_FILTER_VENDOR = 3;
    private static final int INDEX_FILTER_VERSION = 5;
    private static final String MYFILTER_AUTHORITY =
            "com.samsung.android.provider.filterprovider/myfilter";
    private static final String MYFILTER_SEPERATOR = "[MYFILTER]";
    private static final int SI_KEY_FILTER_VALUE_GS_NO_EFFECT = 400;
    private static final String TAG = "SemFilterManager";
    private static final int TYPE_EFFECT_CUSTOMCOLOR = 425;
    private static final int TYPE_EFFECT_DISTORTION_CORRECTION = 447;
    private static final int TYPE_EFFECT_FOOD = 450;
    public static final int TYPE_FILTER_BASIC = 100;

    @Deprecated(forRemoval = true, since = "15.5")
    public static final int TYPE_FILTER_EXTENDED = 101;

    public static final int TYPE_FILTER_USER_GENERATED = 102;
    private Context mContext;
    private ContentObserver mFilterAddObserver;
    private ContentObserver mFilterDeleteObserver;
    private ContentObserver mLocaleChangeObserver;
    private Handler mObserverHandler;
    private HandlerThread mObserverHandlerThread;
    private static final Uri BASE_URI =
            Uri.parse("content://com.samsung.android.provider.filterprovider/filters");
    private static final Uri FILTER_URI =
            Uri.parse("content://com.samsung.android.provider.filterprovider/filters");
    private static final Uri MYFILTER_URI =
            Uri.parse("content://com.samsung.android.provider.filterprovider/myfilter");
    private static final Uri notiAddUri =
            Uri.parse("content://com.samsung.android.provider.filterprovider/notifyAdd");
    private static final Uri notiDeleteUri =
            Uri.parse("content://com.samsung.android.provider.filterprovider/notifyDelete");
    private static final Uri notiLocaleChangeUri =
            Uri.parse("content://com.samsung.android.provider.filterprovider/notifyLocaleChange");
    private static final String FILTER_FILE_NAME = "filename";
    private static final String FILTER_CATEGORY = "category";
    private static final String FILTER_TITLE_ID = "title_id";
    private static final String[] FILTER_PROJECTION = {
        "name",
        FILTER_FILE_NAME,
        "package_name",
        "vendor",
        FILTER_CATEGORY,
        "version",
        FILTER_TITLE_ID
    };
    private Handler mCallbackHandler = null;
    SemFilterManagerCallback mSemFilterManagerCallback = null;

    public interface SemFilterManagerCallback {
        void onFilterChanged(int i);
    }

    public SemFilterManager(Context context) {
        this.mContext = null;
        this.mFilterAddObserver = null;
        this.mFilterDeleteObserver = null;
        this.mLocaleChangeObserver = null;
        this.mObserverHandlerThread = null;
        this.mObserverHandler = null;
        this.mContext = context;
        if (this.mObserverHandlerThread == null) {
            this.mObserverHandlerThread = new HandlerThread("SemFilter ContentObserver");
            this.mObserverHandlerThread.start();
            this.mObserverHandler = new Handler(this.mObserverHandlerThread.getLooper());
        }
        this.mFilterAddObserver =
                new ContentObserver(
                        this
                                .mObserverHandler) { // from class:
                                                     // com.samsung.android.camera.filter.SemFilterManager.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean selfChange) {
                        if (SemFilterManager.this.mCallbackHandler != null) {
                            SemFilterManager.this.mCallbackHandler.post(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.camera.filter.SemFilterManager.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (SemFilterManager.this.mSemFilterManagerCallback
                                                    != null) {
                                                SemFilterManager.this.mSemFilterManagerCallback
                                                        .onFilterChanged(0);
                                            }
                                        }
                                    });
                        } else if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                            SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(0);
                        }
                    }
                };
        this.mFilterDeleteObserver =
                new ContentObserver(
                        this
                                .mObserverHandler) { // from class:
                                                     // com.samsung.android.camera.filter.SemFilterManager.2
                    @Override // android.database.ContentObserver
                    public void onChange(boolean selfChange) {
                        if (SemFilterManager.this.mCallbackHandler != null) {
                            SemFilterManager.this.mCallbackHandler.post(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.camera.filter.SemFilterManager.2.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (SemFilterManager.this.mSemFilterManagerCallback
                                                    != null) {
                                                SemFilterManager.this.mSemFilterManagerCallback
                                                        .onFilterChanged(1);
                                            }
                                        }
                                    });
                        } else if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                            SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(1);
                        }
                    }
                };
        this.mLocaleChangeObserver =
                new ContentObserver(
                        this
                                .mObserverHandler) { // from class:
                                                     // com.samsung.android.camera.filter.SemFilterManager.3
                    @Override // android.database.ContentObserver
                    public void onChange(boolean selfChange) {
                        if (SemFilterManager.this.mCallbackHandler != null) {
                            SemFilterManager.this.mCallbackHandler.post(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.camera.filter.SemFilterManager.3.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (SemFilterManager.this.mSemFilterManagerCallback
                                                    != null) {
                                                SemFilterManager.this.mSemFilterManagerCallback
                                                        .onFilterChanged(2);
                                            }
                                        }
                                    });
                        } else if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                            SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(2);
                        }
                    }
                };
        registerObserver();
    }

    public void close() {
        if (this.mObserverHandler != null) {
            this.mObserverHandler.removeCallbacksAndMessages(null);
            if (this.mObserverHandlerThread != null) {
                this.mObserverHandlerThread.quitSafely();
                try {
                    try {
                        this.mObserverHandlerThread.join();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "stopHandler : interrupted - " + e.getMessage());
                    }
                } finally {
                    this.mObserverHandlerThread = null;
                    this.mObserverHandler = null;
                }
            }
        }
        unRegisterObserver();
    }

    protected void finalize() {
        close();
    }

    private void registerObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(notiAddUri, true, this.mFilterAddObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(notiDeleteUri, true, this.mFilterDeleteObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(notiLocaleChangeUri, true, this.mLocaleChangeObserver);
    }

    private void unRegisterObserver() {
        if (this.mFilterAddObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mFilterAddObserver);
        }
        if (this.mFilterDeleteObserver != null) {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mFilterDeleteObserver);
        }
        if (this.mLocaleChangeObserver != null) {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mLocaleChangeObserver);
        }
        this.mFilterAddObserver = null;
        this.mFilterDeleteObserver = null;
        this.mLocaleChangeObserver = null;
    }

    public SemFilter getFilter(
            int type, String filterName, String filterFileName, String filterPackageName) {
        Log.i(
                TAG,
                "getFilter : type : "
                        + type
                        + ",  filterName : "
                        + filterName
                        + ",  filterFileName"
                        + filterFileName
                        + ", filterPackageName : "
                        + filterPackageName);
        if (type != 450 && type != 425 && (filterFileName == null || filterFileName.isEmpty())) {
            Log.e(TAG, "There's no filter file");
            return null;
        }
        switch (type) {
            case 100:
                String filterIdentifier =
                        filterPackageName
                                + ","
                                + filterFileName.substring(filterPackageName.length() + 1);
                return new SemFilterImpl(
                        filterPackageName, filterName, filterIdentifier, filterName, "", 0, 0);
            case 102:
                return new SemFilterImpl(
                        "", filterName, MYFILTER_SEPERATOR + filterFileName, filterName, "", 0, 0);
            case 425:
                return new SemFilterImpl(
                        "com.samsung.android.provider",
                        "CustomColor",
                        425,
                        "Custom Color",
                        "SAMSUNG_MOBILE",
                        0,
                        0);
            case 450:
                return new SemFilterImpl(
                        "com.samsung.android.provider",
                        "Food",
                        450,
                        "Food",
                        "SAMSUNG_MOBILE",
                        0,
                        0);
            default:
                return new SemFilterImpl("", "", filterFileName, "", "", 0, 0);
        }
    }

    public List<SemFilter> getAvailableFilters(int type) {
        ArrayList<SemFilter> FilterList = new ArrayList<>();
        switch (type) {
            case 100:
                return getAvailableFilters();
            case 101:
                SemFilter selfieFaceCorrection =
                        new SemFilterImpl(
                                "com.samsung.android.provider",
                                "SelfieFaceCorrection",
                                447,
                                "Selfie Face Correction",
                                "SAMSUNG_MOBILE",
                                0,
                                0);
                FilterList.add(selfieFaceCorrection);
                SemFilter customcolorFilter1 =
                        new SemFilterImpl(
                                "com.samsung.android.provider",
                                "CustomColor",
                                425,
                                "Custom Color",
                                "SAMSUNG_MOBILE",
                                0,
                                0);
                FilterList.add(customcolorFilter1);
                SemFilter foodFilter1 =
                        new SemFilterImpl(
                                "com.samsung.android.provider",
                                "Food",
                                450,
                                "Food",
                                "SAMSUNG_MOBILE",
                                0,
                                0);
                FilterList.add(foodFilter1);
                break;
            case 102:
                return getAvailableMyFilters();
            case 425:
                SemFilter customcolorFilter2 =
                        new SemFilterImpl(
                                "com.samsung.android.provider",
                                "CustomColor",
                                425,
                                "Custom Color",
                                "SAMSUNG_MOBILE",
                                0,
                                0);
                FilterList.add(customcolorFilter2);
                break;
            case 450:
                SemFilter foodFilter2 =
                        new SemFilterImpl(
                                "com.samsung.android.provider",
                                "Food",
                                450,
                                "Food",
                                "SAMSUNG_MOBILE",
                                0,
                                0);
                FilterList.add(foodFilter2);
                break;
        }
        return Collections.unmodifiableList(FilterList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a6, code lost:

       if (r3 != null) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a8, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b9, code lost:

       return java.util.Collections.unmodifiableList(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b2, code lost:

       if (0 == 0) goto L34;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.samsung.android.camera.filter.SemFilter> getAvailableMyFilters() {
        /*
            r15 = this;
            java.lang.String r0 = "Unnamed filter"
            java.lang.String r1 = "[SemFilterManager] getAvailableMyFilters()"
            java.lang.String r2 = "SemFilterManager"
            android.util.Log.i(r2, r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = 0
            android.content.Context r4 = r15.mContext     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            android.content.ContentResolver r5 = r4.getContentResolver()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            android.net.Uri r6 = com.samsung.android.camera.filter.SemFilterManager.MYFILTER_URI     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r10 = "filter_order"
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r4 = r5.query(r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r3 = r4
            if (r3 != 0) goto L33
            java.lang.String r0 = "[SemFilterManager] getAvailableMyFilters() cursor is null"
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.util.List r0 = java.util.Collections.unmodifiableList(r1)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r3 == 0) goto L32
            r3.close()
        L32:
            return r0
        L33:
            if (r3 == 0) goto La6
            int r4 = r3.getCount()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r4 <= 0) goto La6
        L3b:
            boolean r4 = r3.moveToNext()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r4 == 0) goto La6
            r4 = 1
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r4 == 0) goto L3b
            boolean r5 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r5 == 0) goto L4f
            goto L3b
        L4f:
            r5 = 2
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r13 = r5
            if (r13 == 0) goto L3b
            boolean r5 = r13.equals(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r5 == 0) goto L5e
            goto L3b
        L5e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r5.<init>()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r6 = "myFilterName : "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.StringBuilder r5 = r5.append(r4)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r6 = ", myFilterFileName : "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.StringBuilder r5 = r5.append(r13)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            android.util.Log.i(r2, r5)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            com.samsung.android.camera.filter.SemFilterManager$SemFilterImpl r14 = new com.samsung.android.camera.filter.SemFilterManager$SemFilterImpl     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r6 = ""
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r5.<init>()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r7 = "[MYFILTER]"
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.StringBuilder r5 = r5.append(r13)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r8 = r5.toString()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r10 = ""
            r11 = 0
            r12 = 0
            r5 = r14
            r7 = r4
            r9 = r4
            r5.<init>(r6, r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r5 = r14
            r1.add(r5)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            goto L3b
        La6:
            if (r3 == 0) goto Lb5
        La8:
            r3.close()
            goto Lb5
        Lac:
            r0 = move-exception
            goto Lba
        Lae:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Lac
            if (r3 == 0) goto Lb5
            goto La8
        Lb5:
            java.util.List r0 = java.util.Collections.unmodifiableList(r1)
            return r0
        Lba:
            if (r3 == 0) goto Lbf
            r3.close()
        Lbf:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.camera.filter.SemFilterManager.getAvailableMyFilters():java.util.List");
    }

    public List<SemFilter> getAvailableFilters() {
        return Collections.unmodifiableList(loadFilter());
    }

    private List<SemFilter> loadFilter() {
        String filterFullName;
        String packageName;
        String filterTitle;
        String filterVendor;
        Log.i(TAG, "[SemFilterManager] loadFilter()");
        ArrayList<SemFilter> FilterList = new ArrayList<>();
        Cursor cursor =
                this.mContext
                        .getContentResolver()
                        .query(FILTER_URI, FILTER_PROJECTION, null, null, null);
        try {
            if (cursor == null) {
                Log.i(TAG, "[SemFilterManager] loadFilter() cursor is null");
                if (cursor != null) {
                    cursor.close();
                }
                return FilterList;
            }
            HashMap<String, Resources> resourceMap = new HashMap<>();
            while (cursor.moveToNext()) {
                String filterName = cursor.getString(0);
                if (filterName != null
                        && !filterName.isEmpty()
                        && (filterFullName = cursor.getString(1)) != null
                        && !filterFullName.isEmpty()
                        && (packageName = cursor.getString(2)) != null
                        && !packageName.isEmpty()) {
                    try {
                        Resources resources = resourceMap.get(packageName);
                        if (resources == null) {
                            resources =
                                    this.mContext
                                            .getPackageManager()
                                            .getResourcesForApplication(packageName);
                            resourceMap.put(packageName, resources);
                        }
                        int resId = cursor.getInt(6);
                        String filterTitle2 = resources.getString(resId);
                        filterTitle = filterTitle2;
                    } catch (Exception e) {
                        Log.e(TAG, "getResourcesForApplication or getString encounter exception");
                        e.printStackTrace();
                        filterTitle = filterName;
                    }
                    String filterIdentifier =
                            packageName + "," + filterFullName.substring(packageName.length() + 1);
                    Log.i(TAG, "packageName : " + packageName);
                    Log.i(TAG, "filterFullName : " + filterFullName);
                    Log.i(TAG, "filterIdentifier : " + filterIdentifier);
                    Log.i(TAG, "filterName : " + filterName);
                    if (filterIdentifier != null
                            && !filterIdentifier.isEmpty()
                            && (filterVendor = cursor.getString(3)) != null
                            && !filterVendor.isEmpty()) {
                        int filterCategory = cursor.getInt(4);
                        int filterVersion = cursor.getInt(5);
                        SemFilter filter =
                                new SemFilterImpl(
                                        packageName,
                                        filterName,
                                        filterIdentifier,
                                        filterTitle,
                                        filterVendor,
                                        filterCategory,
                                        filterVersion);
                        FilterList.add(filter);
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return FilterList;
        } finally {
        }
    }

    public void setFilterCallback(SemFilterManagerCallback cb, Handler handler) {
        this.mSemFilterManagerCallback = cb;
        this.mCallbackHandler = handler;
    }

    public static class SemFilterImpl extends SemFilter {
        private String mFilterIdentifier;
        private int mFilterIdentifierIdx;

        SemFilterImpl(
                String packageName,
                String filterName,
                String filterIdentifier,
                String title,
                String vendor2,
                int category,
                int version) {
            super(packageName, filterName, title, vendor2, category, version);
            this.mFilterIdentifier = "";
            this.mFilterIdentifierIdx = -1;
            this.mFilterIdentifier = filterIdentifier;
        }

        SemFilterImpl(
                String packageName,
                String filterName,
                int filterIdentifierIdx,
                String title,
                String vendor2,
                int category,
                int version) {
            super(packageName, filterName, title, vendor2, category, version);
            this.mFilterIdentifier = "";
            this.mFilterIdentifierIdx = -1;
            this.mFilterIdentifierIdx = filterIdentifierIdx;
        }

        public String getFilterIdentifier() {
            if (this.mFilterIdentifierIdx == -1) {
                return this.mFilterIdentifier;
            }
            return Integer.toString(this.mFilterIdentifierIdx);
        }

        public int getFilterIdentifierIdx() {
            return this.mFilterIdentifierIdx;
        }
    }
}
