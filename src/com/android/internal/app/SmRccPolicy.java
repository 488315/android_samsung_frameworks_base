package com.android.internal.app;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Slog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes5.dex */
public final class SmRccPolicy {
    private static final String ACTION = "action";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_INSERT = "insert";
    private static final String ACTION_UPDATE_OPEN = "update_open";
    private static final String ACTION_UPDATE_RESTRICT = "update_restrict";
    private static final String ACTION_UPDATE_SHOW = "update_show";
    private static final int MSG_LOAD_RCC_APP_LIST = 10;
    private static final int MSG_RCC_APP_DELETE = 30;
    private static final int MSG_RCC_APP_INSERT = 20;
    private static final int MSG_RCC_APP_RESET_OPEN = 70;
    private static final int MSG_RCC_APP_UPDATE_OPEN = 50;
    private static final int MSG_RCC_APP_UPDATE_RESTRICT = 60;
    private static final int MSG_RCC_APP_UPDATE_SHOW = 40;
    private static final String OFF = "0";
    private static final String ON = "1";
    private static final String OPEN = "open";
    private static final String PACKAGE_NAME = "package_name";
    private static final String RESET_SM_RCC_OPEN = "reset_sm_rcc_open";
    private static final String RESTRICT = "restrict";
    private static final String SHOW = "show";
    public static final String SM_RCC_ACTIVITY_OPTIONS = "SM_RCC_PACKAGE_OPTIONS";
    public static final String SM_RCC_EXTRA_RESULT_NEEDED = "SM_RCC_EXTRA_RESULT_NEEDED";
    public static final String SM_RCC_PACKAGE_INTENT = "SM_RCC_PACKAGE_INTENT";
    public static final String SM_RCC_PACKAGE_USERID = "SM_RCC_PACKAGE_USERID";
    private static final String TAG = "SmRccPolicy";
    private static volatile SmRccPolicy sInstance;
    private Context mContext;
    private Handler mHandler;
    private static final Uri RCC_APP_CONTENT_URI = Uri.parse("content://com.samsung.android.sm.rcc/SmRccApps");
    private static final Uri RCC_APP_AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.rcc");
    private static Map<String, RccApp> mRccPkgMap = new ConcurrentHashMap();

    public static SmRccPolicy getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SmRccPolicy.class) {
                if (sInstance == null) {
                    sInstance = new SmRccPolicy(context);
                }
            }
        }
        return sInstance;
    }

    private SmRccPolicy(Context context) {
        Slog.i(TAG, "RccAppPolicy init");
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("RccAppThread");
        handlerThread.start();
        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        this.mHandler = myHandler;
        myHandler.post(new Runnable() { // from class: com.android.internal.app.SmRccPolicy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SmRccPolicy.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: registerRccDBObserver, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        try {
            this.mContext.getContentResolver().registerContentObserver(RCC_APP_CONTENT_URI, true, new RccAppDBObserver(this.mHandler));
            Slog.i(TAG, "registerRccDBObserver");
        } catch (Exception e) {
            Slog.e(TAG, "registerRccDBObserver error", e);
        }
    }

    private class RccAppDBObserver extends ContentObserver {
        public RccAppDBObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            String queryParameter = uri.getQueryParameter("package_name");
            String queryParameter2 = uri.getQueryParameter("action");
            String queryParameter3 = uri.getQueryParameter("show");
            String queryParameter4 = uri.getQueryParameter("open");
            String queryParameter5 = uri.getQueryParameter(SmRccPolicy.RESTRICT);
            if (SmRccPolicy.ACTION_INSERT.equals(queryParameter2)) {
                Message obtain = Message.obtain();
                obtain.what = 20;
                obtain.obj = new RccApp(queryParameter, queryParameter3, queryParameter4, queryParameter5);
                SmRccPolicy.this.mHandler.sendMessage(obtain);
                return;
            }
            if (SmRccPolicy.ACTION_DELETE.equals(queryParameter2)) {
                Message obtain2 = Message.obtain();
                obtain2.what = 30;
                obtain2.obj = queryParameter;
                SmRccPolicy.this.mHandler.sendMessage(obtain2);
                return;
            }
            if (SmRccPolicy.ACTION_UPDATE_SHOW.equals(queryParameter2)) {
                Message obtain3 = Message.obtain();
                obtain3.what = 40;
                obtain3.obj = queryParameter;
                if (!TextUtils.isEmpty(queryParameter3)) {
                    obtain3.arg1 = Integer.parseInt(queryParameter3);
                }
                SmRccPolicy.this.mHandler.sendMessage(obtain3);
                return;
            }
            if (SmRccPolicy.ACTION_UPDATE_OPEN.equals(queryParameter2)) {
                Message obtain4 = Message.obtain();
                obtain4.what = 50;
                obtain4.obj = queryParameter;
                if (!TextUtils.isEmpty(queryParameter4)) {
                    obtain4.arg1 = Integer.parseInt(queryParameter4);
                }
                SmRccPolicy.this.mHandler.sendMessage(obtain4);
                return;
            }
            if (SmRccPolicy.ACTION_UPDATE_RESTRICT.equals(queryParameter2)) {
                Message obtain5 = Message.obtain();
                obtain5.what = 60;
                obtain5.obj = queryParameter;
                if (!TextUtils.isEmpty(queryParameter5)) {
                    obtain5.arg1 = Integer.parseInt(queryParameter5);
                }
                SmRccPolicy.this.mHandler.sendMessage(obtain5);
            }
        }
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 10) {
                SmRccPolicy.this.loadRccAppFromSm();
                return;
            }
            if (i == 20) {
                SmRccPolicy.this.addRccPackage((RccApp) message.obj);
                return;
            }
            if (i == 30) {
                SmRccPolicy.this.removeRccPackage((String) message.obj);
                return;
            }
            if (i == 40) {
                SmRccPolicy.this.updateRccShow((String) message.obj, String.valueOf(message.arg1));
                return;
            }
            if (i == 50) {
                SmRccPolicy.this.updateRccOpen((String) message.obj, String.valueOf(message.arg1));
            } else if (i == 60) {
                SmRccPolicy.this.updateRccRestrict((String) message.obj, String.valueOf(message.arg1));
            } else {
                if (i != 70) {
                    return;
                }
                SmRccPolicy.this.callResetSmRccOpen((String) message.obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadRccAppFromSm() {
        Slog.i(TAG, "loadRccAppFromSm: ");
        mRccPkgMap.clear();
        try {
            Cursor query = this.mContext.getContentResolver().query(RCC_APP_CONTENT_URI, null, null, null, null);
            if (query != null) {
                try {
                    if (!query.isClosed()) {
                        while (query.moveToNext()) {
                            String string = query.getString(query.getColumnIndex("package_name"));
                            mRccPkgMap.put(string, new RccApp(string, query.getString(query.getColumnIndex("show")), query.getString(query.getColumnIndex("open")), query.getString(query.getColumnIndex(RESTRICT))));
                            Slog.i(TAG, "put=" + string);
                        }
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Slog.e(TAG, "loadRccAppFromSm error", e);
        }
    }

    public void addRccPackage(RccApp rccApp) {
        String pkgName;
        if (rccApp == null || (pkgName = rccApp.getPkgName()) == null) {
            return;
        }
        mRccPkgMap.put(pkgName, rccApp);
        Slog.i(TAG, "addRccPackage: " + pkgName);
    }

    public void removeRccPackage(String str) {
        if (str != null) {
            mRccPkgMap.remove(str);
        }
        Slog.i(TAG, "removeRccPackage: " + str);
    }

    public void updateRccShow(String str, String str2) {
        RccApp rccApp = mRccPkgMap.get(str);
        if (rccApp != null) {
            rccApp.setShow(str2);
        }
        Slog.i(TAG, "update rcc show pkg=" + str + " show=" + str2);
    }

    public void updateRccOpen(String str, String str2) {
        RccApp rccApp = mRccPkgMap.get(str);
        if (rccApp != null) {
            rccApp.setOpen(str2);
        }
        Slog.i(TAG, "update rcc open pkg=" + str + " open=" + str2);
    }

    public void updateRccRestrict(String str, String str2) {
        RccApp rccApp = mRccPkgMap.get(str);
        if (rccApp != null) {
            rccApp.setRestrict(str2);
        }
        Slog.i(TAG, "update rcc restrict pkg=" + str + " restrict=" + str2);
    }

    public boolean isSmRccPkg(String str) {
        RccApp rccApp;
        if (str == null || (rccApp = mRccPkgMap.get(str)) == null) {
            return false;
        }
        String show = rccApp.getShow();
        String restrict = rccApp.getRestrict();
        Slog.i(TAG, "isSmRccPkg=" + str + ",show=" + show + ",restrict=" + restrict);
        return "1".equals(show) && "1".equals(restrict);
    }

    public boolean isSmRccOpen(String str) {
        RccApp rccApp;
        if (str == null || (rccApp = mRccPkgMap.get(str)) == null) {
            return false;
        }
        String open = rccApp.getOpen();
        Slog.i(TAG, "isSmRccOpen=" + str + ",open=" + open);
        return "1".equals(open);
    }

    public void resetSmRccOpen(String str) {
        Message obtain = Message.obtain();
        obtain.what = 70;
        obtain.obj = str;
        this.mHandler.sendMessage(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callResetSmRccOpen(String str) {
        if (str != null && mRccPkgMap.containsKey(str)) {
            Bundle bundle = new Bundle();
            bundle.putString("package_name", str);
            try {
                this.mContext.getContentResolver().call(RCC_APP_AUTHORITY_URI, RESET_SM_RCC_OPEN, (String) null, bundle);
                Slog.d(TAG, "call sm reset rcc open status");
            } catch (Exception e) {
                Slog.e(TAG, "call sm reset rcc open status error", e);
            }
        }
    }

    public String getSmRccAction() {
        return "com.samsung.android.sm.security.RCC_START_APP";
    }

    public void loadData() {
        Handler handler = this.mHandler;
        if (handler == null) {
            Slog.i(TAG, "SmRcc handler is null, return");
        } else {
            handler.sendEmptyMessage(10);
        }
    }

    private static class RccApp {
        private String open;
        private String pkgName;
        private String restrict;
        private String show;

        public RccApp(String str, String str2, String str3, String str4) {
            this.pkgName = str;
            this.show = str2;
            this.open = str3;
            this.restrict = str4;
        }

        public String getPkgName() {
            return this.pkgName;
        }

        public void setPkgName(String str) {
            this.pkgName = str;
        }

        public String getShow() {
            return this.show;
        }

        public void setShow(String str) {
            this.show = str;
        }

        public String getOpen() {
            return this.open;
        }

        public void setOpen(String str) {
            this.open = str;
        }

        public String getRestrict() {
            return this.restrict;
        }

        public void setRestrict(String str) {
            this.restrict = str;
        }
    }
}
