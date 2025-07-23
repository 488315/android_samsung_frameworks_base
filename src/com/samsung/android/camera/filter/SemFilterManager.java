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

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0097, code lost:
    
        if (r3 != null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ab, code lost:
    
        return java.util.Collections.unmodifiableList(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a4, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a2, code lost:
    
        if (0 == 0) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.samsung.android.camera.filter.SemFilter> getAvailableMyFilters() {
        /*
            r12 = this;
            java.lang.String r0 = "Unnamed filter"
            java.lang.String r1 = "[SemFilterManager] getAvailableMyFilters()"
            java.lang.String r2 = "SemFilterManager"
            android.util.Log.i(r2, r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = 0
            android.content.Context r12 = r12.mContext     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            android.content.ContentResolver r4 = r12.getContentResolver()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            android.net.Uri r5 = com.samsung.android.camera.filter.SemFilterManager.MYFILTER_URI     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r9 = "filter_order"
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r3 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r3 != 0) goto L31
            java.lang.String r12 = "[SemFilterManager] getAvailableMyFilters() cursor is null"
            android.util.Log.e(r2, r12)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.util.List r12 = java.util.Collections.unmodifiableList(r1)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r3 == 0) goto L30
            r3.close()
        L30:
            return r12
        L31:
            int r12 = r3.getCount()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r12 <= 0) goto L97
        L37:
            boolean r12 = r3.moveToNext()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r12 == 0) goto L97
            r12 = 1
            java.lang.String r6 = r3.getString(r12)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r6 == 0) goto L37
            boolean r12 = r6.equals(r0)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r12 == 0) goto L4b
            goto L37
        L4b:
            r12 = 2
            java.lang.String r12 = r3.getString(r12)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r12 == 0) goto L37
            boolean r4 = r12.equals(r0)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            if (r4 == 0) goto L59
            goto L37
        L59:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            r4.<init>()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r5 = "myFilterName : "
            r4.append(r5)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            r4.append(r6)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r5 = ", myFilterFileName : "
            r4.append(r5)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            r4.append(r12)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            android.util.Log.i(r2, r4)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            com.samsung.android.camera.filter.SemFilterManager$SemFilterImpl r4 = new com.samsung.android.camera.filter.SemFilterManager$SemFilterImpl     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r5 = ""
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            r7.<init>()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r8 = "[MYFILTER]"
            r7.append(r8)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            r7.append(r12)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            java.lang.String r9 = ""
            r10 = 0
            r11 = 0
            r8 = r6
            r4.<init>(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            r1.add(r4)     // Catch: java.lang.Throwable -> L9a java.lang.Exception -> L9d
            goto L37
        L97:
            if (r3 == 0) goto La7
            goto La4
        L9a:
            r0 = move-exception
            r12 = r0
            goto Lac
        L9d:
            r0 = move-exception
            r12 = r0
            r12.printStackTrace()     // Catch: java.lang.Throwable -> L9a
            if (r3 == 0) goto La7
        La4:
            r3.close()
        La7:
            java.util.List r12 = java.util.Collections.unmodifiableList(r1)
            return r12
        Lac:
            if (r3 == 0) goto Lb1
            r3.close()
        Lb1:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.camera.filter.SemFilterManager.getAvailableMyFilters():java.util.List");
    }

    public List<SemFilter> getAvailableFilters() {
        return Collections.unmodifiableList(loadFilter());
    }

    private List<SemFilter> loadFilter() {
        String string;
        String string2;
        String str;
        String string3;
        Log.i(TAG, "[SemFilterManager] loadFilter()");
        ArrayList arrayList = new ArrayList();
        Cursor query = this.mContext.getContentResolver().query(FILTER_URI, FILTER_PROJECTION, null, null, null);
        try {
            if (query == null) {
                Log.i(TAG, "[SemFilterManager] loadFilter() cursor is null");
                if (query != null) {
                    query.close();
                    return arrayList;
                }
            } else {
                HashMap hashMap = new HashMap();
                while (query.moveToNext()) {
                    String string4 = query.getString(0);
                    if (string4 != null && !string4.isEmpty() && (string = query.getString(1)) != null && !string.isEmpty() && (string2 = query.getString(2)) != null && !string2.isEmpty()) {
                        try {
                            Resources resources = (Resources) hashMap.get(string2);
                            if (resources == null) {
                                resources = this.mContext.getPackageManager().getResourcesForApplication(string2);
                                hashMap.put(string2, resources);
                            }
                            str = resources.getString(query.getInt(6));
                        } catch (Exception e) {
                            Log.e(TAG, "getResourcesForApplication or getString encounter exception");
                            e.printStackTrace();
                            str = string4;
                        }
                        String str2 = string2 + "," + string.substring(string2.length() + 1);
                        Log.i(TAG, "packageName : " + string2);
                        Log.i(TAG, "filterFullName : " + string);
                        Log.i(TAG, "filterIdentifier : " + str2);
                        Log.i(TAG, "filterName : " + string4);
                        if (str2 != null && !str2.isEmpty() && (string3 = query.getString(3)) != null && !string3.isEmpty()) {
                            arrayList.add(new SemFilterImpl(string2, string4, str2, str, string3, query.getInt(4), query.getInt(5)));
                        }
                    }
                }
                if (query != null) {
                    query.close();
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
