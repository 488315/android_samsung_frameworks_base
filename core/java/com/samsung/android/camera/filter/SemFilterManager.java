package com.samsung.android.camera.filter;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes5.dex */
public class SemFilterManager {
    private static final String AUTHORITY = "com.samsung.android.provider.filterprovider/filters";
    private static final String FILTER_AUTHORITY = "com.samsung.android.provider.filterprovider/filters";
    private static final String FILTER_CATEGORY = "category";
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
    private static final String MYFILTER_AUTHORITY = "com.samsung.android.provider.filterprovider/myfilter";
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
    private static final Uri BASE_URI = Uri.parse("content://com.samsung.android.provider.filterprovider/filters");
    private static final Uri FILTER_URI = Uri.parse("content://com.samsung.android.provider.filterprovider/filters");
    private static final Uri MYFILTER_URI = Uri.parse("content://com.samsung.android.provider.filterprovider/myfilter");
    private static final Uri notiAddUri = Uri.parse("content://com.samsung.android.provider.filterprovider/notifyAdd");
    private static final Uri notiDeleteUri = Uri.parse("content://com.samsung.android.provider.filterprovider/notifyDelete");
    private static final Uri notiLocaleChangeUri = Uri.parse("content://com.samsung.android.provider.filterprovider/notifyLocaleChange");
    private static final String FILTER_FILE_NAME = "filename";
    private static final String FILTER_TITLE_ID = "title_id";
    private static final String[] FILTER_PROJECTION = {"name", FILTER_FILE_NAME, "package_name", "vendor", "category", "version", FILTER_TITLE_ID};
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
        HandlerThread handlerThread = new HandlerThread("SemFilter ContentObserver");
        this.mObserverHandlerThread = handlerThread;
        handlerThread.start();
        this.mObserverHandler = new Handler(this.mObserverHandlerThread.getLooper());
        this.mFilterAddObserver = new ContentObserver(this.mObserverHandler) { // from class: com.samsung.android.camera.filter.SemFilterManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                if (SemFilterManager.this.mCallbackHandler != null) {
                    SemFilterManager.this.mCallbackHandler.post(new Runnable() { // from class: com.samsung.android.camera.filter.SemFilterManager.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                                SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(0);
                            }
                        }
                    });
                } else if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                    SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(0);
                }
            }
        };
        this.mFilterDeleteObserver = new ContentObserver(this.mObserverHandler) { // from class: com.samsung.android.camera.filter.SemFilterManager.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                if (SemFilterManager.this.mCallbackHandler != null) {
                    SemFilterManager.this.mCallbackHandler.post(new Runnable() { // from class: com.samsung.android.camera.filter.SemFilterManager.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                                SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(1);
                            }
                        }
                    });
                } else if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                    SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(1);
                }
            }
        };
        this.mLocaleChangeObserver = new ContentObserver(this.mObserverHandler) { // from class: com.samsung.android.camera.filter.SemFilterManager.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                if (SemFilterManager.this.mCallbackHandler != null) {
                    SemFilterManager.this.mCallbackHandler.post(new Runnable() { // from class: com.samsung.android.camera.filter.SemFilterManager.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (SemFilterManager.this.mSemFilterManagerCallback != null) {
                                SemFilterManager.this.mSemFilterManagerCallback.onFilterChanged(2);
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

    /* JADX WARN: Multi-variable type inference failed */
    public void close() {
        Handler handler = this.mObserverHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            HandlerThread handlerThread = this.mObserverHandlerThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                try {
                    try {
                        this.mObserverHandlerThread.join();
                    } catch (InterruptedException e) {
                        Log.m96e(TAG, "stopHandler : interrupted - " + e.getMessage());
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
        this.mContext.getContentResolver().registerContentObserver(notiAddUri, true, this.mFilterAddObserver);
        this.mContext.getContentResolver().registerContentObserver(notiDeleteUri, true, this.mFilterDeleteObserver);
        this.mContext.getContentResolver().registerContentObserver(notiLocaleChangeUri, true, this.mLocaleChangeObserver);
    }

    private void unRegisterObserver() {
        if (this.mFilterAddObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mFilterAddObserver);
        }
        if (this.mFilterDeleteObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mFilterDeleteObserver);
        }
        if (this.mLocaleChangeObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mLocaleChangeObserver);
        }
        this.mFilterAddObserver = null;
        this.mFilterDeleteObserver = null;
        this.mLocaleChangeObserver = null;
    }

    public SemFilter getFilter(int type, String filterName, String filterFileName, String filterPackageName) {
        Log.m98i(TAG, "getFilter : type : " + type + ",  filterName : " + filterName + ",  filterFileName" + filterFileName + ", filterPackageName : " + filterPackageName);
        if (filterFileName == null || filterFileName.isEmpty()) {
            Log.m96e(TAG, "There's no filer file");
            return null;
        }
        switch (type) {
            case 100:
                String filterIdentifier = filterPackageName + "," + filterFileName.substring(filterPackageName.length() + 1);
                return new SemFilterImpl(filterPackageName, filterName, filterIdentifier, filterName, "", 0, 0);
            case 101:
            default:
                return null;
            case 102:
                return new SemFilterImpl("", filterName, MYFILTER_SEPERATOR + filterFileName, filterName, "", 0, 0);
        }
    }

    public List<SemFilter> getAvailableFilters(int type) {
        ArrayList<SemFilter> FilterList = new ArrayList<>();
        switch (type) {
            case 100:
                return getAvailableFilters();
            case 101:
                SemFilter selfieFaceCorrection = new SemFilterImpl("com.samsung.android.provider", "SelfieFaceCorrection", TYPE_EFFECT_DISTORTION_CORRECTION, "Selfie Face Correction", "SAMSUNG_MOBILE", 0, 0);
                FilterList.add(selfieFaceCorrection);
                SemFilter customcolorFilter = new SemFilterImpl("com.samsung.android.provider", "CustomColor", TYPE_EFFECT_CUSTOMCOLOR, "Custom Color", "SAMSUNG_MOBILE", 0, 0);
                FilterList.add(customcolorFilter);
                SemFilter foodFilter = new SemFilterImpl("com.samsung.android.provider", "Food", 450, "Food", "SAMSUNG_MOBILE", 0, 0);
                FilterList.add(foodFilter);
                break;
            case 102:
                return getAvailableMyFilters();
        }
        return Collections.unmodifiableList(FilterList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a5, code lost:
    
        if (r3 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a7, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b8, code lost:
    
        return java.util.Collections.unmodifiableList(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b1, code lost:
    
        if (0 == 0) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<SemFilter> getAvailableMyFilters() {
        String myFilterFileName;
        Log.m98i(TAG, "[SemFilterManager] getAvailableMyFilters()");
        ArrayList<SemFilter> MyFilterList = new ArrayList<>();
        Cursor cursor = null;
        try {
            try {
                cursor = this.mContext.getContentResolver().query(MYFILTER_URI, null, null, null, "filter_order");
                if (cursor == null) {
                    Log.m96e(TAG, "[SemFilterManager] getAvailableMyFilters() cursor is null");
                    return Collections.unmodifiableList(MyFilterList);
                }
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String myFilterName = cursor.getString(1);
                        if (myFilterName != null && !myFilterName.equals("Unnamed filter") && (myFilterFileName = cursor.getString(2)) != null && !myFilterFileName.equals("Unnamed filter")) {
                            Log.m98i(TAG, "myFilterName : " + myFilterName + ", myFilterFileName : " + myFilterFileName);
                            SemFilter Myfilter = new SemFilterImpl("", myFilterName, MYFILTER_SEPERATOR + myFilterFileName, myFilterName, "", 0, 0);
                            MyFilterList.add(Myfilter);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (0 != 0) {
                cursor.close();
            }
        }
    }

    public List<SemFilter> getAvailableFilters() {
        return Collections.unmodifiableList(loadFilter());
    }

    private List<SemFilter> loadFilter() {
        String filterFullName;
        String packageName;
        String filterTitle;
        String filterVendor;
        Log.m98i(TAG, "[SemFilterManager] loadFilter()");
        ArrayList<SemFilter> FilterList = new ArrayList<>();
        Cursor cursor = this.mContext.getContentResolver().query(FILTER_URI, FILTER_PROJECTION, null, null, null);
        try {
            if (cursor == null) {
                Log.m98i(TAG, "[SemFilterManager] loadFilter() cursor is null");
                if (cursor != null) {
                    cursor.close();
                }
                return FilterList;
            }
            HashMap<String, Resources> resourceMap = new HashMap<>();
            while (cursor.moveToNext()) {
                String filterName = cursor.getString(0);
                if (filterName != null && !filterName.isEmpty() && (filterFullName = cursor.getString(1)) != null && !filterFullName.isEmpty() && (packageName = cursor.getString(2)) != null && !packageName.isEmpty()) {
                    try {
                        Resources resources = resourceMap.get(packageName);
                        if (resources == null) {
                            resources = this.mContext.getPackageManager().getResourcesForApplication(packageName);
                            resourceMap.put(packageName, resources);
                        }
                        int resId = cursor.getInt(6);
                        String filterTitle2 = resources.getString(resId);
                        filterTitle = filterTitle2;
                    } catch (Exception e) {
                        Log.m96e(TAG, "getResourcesForApplication or getString encounter exception");
                        e.printStackTrace();
                        filterTitle = filterName;
                    }
                    String filterIdentifier = packageName + "," + filterFullName.substring(packageName.length() + 1);
                    Log.m98i(TAG, "packageName : " + packageName);
                    Log.m98i(TAG, "filterFullName : " + filterFullName);
                    Log.m98i(TAG, "filterIdentifier : " + filterIdentifier);
                    Log.m98i(TAG, "filterName : " + filterName);
                    if (filterIdentifier != null && !filterIdentifier.isEmpty() && (filterVendor = cursor.getString(3)) != null && !filterVendor.isEmpty()) {
                        int filterCategory = cursor.getInt(4);
                        int filterVersion = cursor.getInt(5);
                        SemFilter filter = new SemFilterImpl(packageName, filterName, filterIdentifier, filterTitle, filterVendor, filterCategory, filterVersion);
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

        SemFilterImpl(String packageName, String filterName, String filterIdentifier, String title, String vendor2, int category, int version) {
            super(packageName, filterName, title, vendor2, category, version);
            this.mFilterIdentifier = "";
            this.mFilterIdentifierIdx = -1;
            this.mFilterIdentifier = filterIdentifier;
        }

        SemFilterImpl(String packageName, String filterName, int filterIdentifierIdx, String title, String vendor2, int category, int version) {
            super(packageName, filterName, title, vendor2, category, version);
            this.mFilterIdentifier = "";
            this.mFilterIdentifierIdx = -1;
            this.mFilterIdentifierIdx = filterIdentifierIdx;
        }

        public String getFilterIdentifier() {
            int i = this.mFilterIdentifierIdx;
            if (i == -1) {
                return this.mFilterIdentifier;
            }
            return Integer.toString(i);
        }

        public int getFilterIdentifierIdx() {
            return this.mFilterIdentifierIdx;
        }
    }
}
