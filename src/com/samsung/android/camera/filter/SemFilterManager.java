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

/* loaded from: classes6.dex */
public class SemFilterManager {
    private static final String AUTHORITY = "com.samsung.android.provider.filterprovider/filters";
    private static final String FILTER_AUTHORITY = "com.samsung.android.provider.filterprovider/filters";
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
    private static final String FILTER_CATEGORY = "category";
    private static final String FILTER_TITLE_ID = "title_id";
    private static final String[] FILTER_PROJECTION = {"name", FILTER_FILE_NAME, "package_name", "vendor", FILTER_CATEGORY, "version", FILTER_TITLE_ID};
    private Handler mCallbackHandler = null;
    SemFilterManagerCallback mSemFilterManagerCallback = null;

    public interface SemFilterManagerCallback {
        void onFilterChanged(int i);
    }

    public SemFilterManager(Context context) {
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
            public void onChange(boolean z) {
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
            public void onChange(boolean z) {
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
            public void onChange(boolean z) {
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

    public SemFilter getFilter(int i, String str, String str2, String str3) {
        Log.i(TAG, "getFilter : type : " + i + ",  filterName : " + str + ",  filterFileName" + str2 + ", filterPackageName : " + str3);
        if (i != 450 && i != 425 && (str2 == null || str2.isEmpty())) {
            Log.e(TAG, "There's no filter file");
            return null;
        }
        if (i == 100) {
            return new SemFilterImpl(str3, str, str3 + "," + str2.substring(str3.length() + 1), str, "", 0, 0);
        }
        if (i == 102) {
            return new SemFilterImpl("", str, MYFILTER_SEPERATOR + str2, str, "", 0, 0);
        }
        if (i == 425) {
            return new SemFilterImpl("com.samsung.android.provider", "CustomColor", 425, "Custom Color", "SAMSUNG_MOBILE", 0, 0);
        }
        if (i == 450) {
            return new SemFilterImpl("com.samsung.android.provider", "Food", 450, "Food", "SAMSUNG_MOBILE", 0, 0);
        }
        return new SemFilterImpl("", "", str2, "", "", 0, 0);
    }

    public List<SemFilter> getAvailableFilters(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == 425) {
            arrayList.add(new SemFilterImpl("com.samsung.android.provider", "CustomColor", 425, "Custom Color", "SAMSUNG_MOBILE", 0, 0));
        } else if (i == 450) {
            arrayList.add(new SemFilterImpl("com.samsung.android.provider", "Food", 450, "Food", "SAMSUNG_MOBILE", 0, 0));
        } else {
            switch (i) {
                case 100:
                    return getAvailableFilters();
                case 101:
                    arrayList.add(new SemFilterImpl("com.samsung.android.provider", "SelfieFaceCorrection", 447, "Selfie Face Correction", "SAMSUNG_MOBILE", 0, 0));
                    arrayList.add(new SemFilterImpl("com.samsung.android.provider", "CustomColor", 425, "Custom Color", "SAMSUNG_MOBILE", 0, 0));
                    arrayList.add(new SemFilterImpl("com.samsung.android.provider", "Food", 450, "Food", "SAMSUNG_MOBILE", 0, 0));
                    break;
                case 102:
                    return getAvailableMyFilters();
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<SemFilter> getAvailableMyFilters() {
        String string;
        Log.i(TAG, "[SemFilterManager] getAvailableMyFilters()");
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.mContext.getContentResolver().query(MYFILTER_URI, null, null, null, "filter_order");
            } catch (Exception e) {
                e.printStackTrace();
                if (0 != 0) {
                }
            }
            if (cursorQuery == null) {
                Log.e(TAG, "[SemFilterManager] getAvailableMyFilters() cursor is null");
                List<SemFilter> listUnmodifiableList = Collections.unmodifiableList(arrayList);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return listUnmodifiableList;
            }
            if (cursorQuery.getCount() > 0) {
                while (cursorQuery.moveToNext()) {
                    String string2 = cursorQuery.getString(1);
                    if (string2 != null && !string2.equals("Unnamed filter") && (string = cursorQuery.getString(2)) != null && !string.equals("Unnamed filter")) {
                        Log.i(TAG, "myFilterName : " + string2 + ", myFilterFileName : " + string);
                        StringBuilder sb = new StringBuilder();
                        sb.append(MYFILTER_SEPERATOR);
                        sb.append(string);
                        arrayList.add(new SemFilterImpl("", string2, sb.toString(), string2, "", 0, 0));
                    }
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return Collections.unmodifiableList(arrayList);
        } finally {
        }
    }

    public List<SemFilter> getAvailableFilters() {
        return Collections.unmodifiableList(loadFilter());
    }

    private List<SemFilter> loadFilter() throws Resources.NotFoundException {
        String string;
        String string2;
        String string3;
        String string4;
        Log.i(TAG, "[SemFilterManager] loadFilter()");
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = this.mContext.getContentResolver().query(FILTER_URI, FILTER_PROJECTION, null, null, null);
        try {
            if (cursorQuery == null) {
                Log.i(TAG, "[SemFilterManager] loadFilter() cursor is null");
                if (cursorQuery != null) {
                    cursorQuery.close();
                    return arrayList;
                }
            } else {
                HashMap map = new HashMap();
                while (cursorQuery.moveToNext()) {
                    String string5 = cursorQuery.getString(0);
                    if (string5 != null && !string5.isEmpty() && (string = cursorQuery.getString(1)) != null && !string.isEmpty() && (string2 = cursorQuery.getString(2)) != null && !string2.isEmpty()) {
                        try {
                            Resources resourcesForApplication = (Resources) map.get(string2);
                            if (resourcesForApplication == null) {
                                resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(string2);
                                map.put(string2, resourcesForApplication);
                            }
                            string3 = resourcesForApplication.getString(cursorQuery.getInt(6));
                        } catch (Exception e) {
                            Log.e(TAG, "getResourcesForApplication or getString encounter exception");
                            e.printStackTrace();
                            string3 = string5;
                        }
                        String str = string2 + "," + string.substring(string2.length() + 1);
                        Log.i(TAG, "packageName : " + string2);
                        Log.i(TAG, "filterFullName : " + string);
                        Log.i(TAG, "filterIdentifier : " + str);
                        Log.i(TAG, "filterName : " + string5);
                        if (str != null && !str.isEmpty() && (string4 = cursorQuery.getString(3)) != null && !string4.isEmpty()) {
                            arrayList.add(new SemFilterImpl(string2, string5, str, string3, string4, cursorQuery.getInt(4), cursorQuery.getInt(5)));
                        }
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
            return arrayList;
        } finally {
        }
    }

    public void setFilterCallback(SemFilterManagerCallback semFilterManagerCallback, Handler handler) {
        this.mSemFilterManagerCallback = semFilterManagerCallback;
        this.mCallbackHandler = handler;
    }

    public static class SemFilterImpl extends SemFilter {
        private String mFilterIdentifier;
        private int mFilterIdentifierIdx;

        SemFilterImpl(String str, String str2, String str3, String str4, String str5, int i, int i2) {
            super(str, str2, str4, str5, i, i2);
            this.mFilterIdentifierIdx = -1;
            this.mFilterIdentifier = str3;
        }

        SemFilterImpl(String str, String str2, int i, String str3, String str4, int i2, int i3) {
            super(str, str2, str3, str4, i2, i3);
            this.mFilterIdentifier = "";
            this.mFilterIdentifierIdx = i;
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
