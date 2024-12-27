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
    private static final Uri RCC_APP_CONTENT_URI =
            Uri.parse("content://com.samsung.android.sm.rcc/SmRccApps");
    private static final Uri RCC_APP_AUTHORITY_URI =
            Uri.parse("content://com.samsung.android.sm.rcc");
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
        HandlerThread thread = new HandlerThread("RccAppThread");
        thread.start();
        this.mHandler = new MyHandler(thread.getLooper());
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.internal.app.SmRccPolicy$$ExternalSyntheticLambda0
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
            RccAppDBObserver rccDBObserver = new RccAppDBObserver(this.mHandler);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(RCC_APP_CONTENT_URI, true, rccDBObserver);
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
        public void onChange(boolean selfChange, Uri uri) {
            if (uri == null) {
                return;
            }
            String pkgName = uri.getQueryParameter("package_name");
            String action = uri.getQueryParameter("action");
            String show = uri.getQueryParameter("show");
            String open = uri.getQueryParameter("open");
            String restrict = uri.getQueryParameter(SmRccPolicy.RESTRICT);
            if (SmRccPolicy.ACTION_INSERT.equals(action)) {
                Message msgInsert = Message.obtain();
                msgInsert.what = 20;
                msgInsert.obj = new RccApp(pkgName, show, open, restrict);
                SmRccPolicy.this.mHandler.sendMessage(msgInsert);
                return;
            }
            if (SmRccPolicy.ACTION_DELETE.equals(action)) {
                Message msgDelete = Message.obtain();
                msgDelete.what = 30;
                msgDelete.obj = pkgName;
                SmRccPolicy.this.mHandler.sendMessage(msgDelete);
                return;
            }
            if (SmRccPolicy.ACTION_UPDATE_SHOW.equals(action)) {
                Message msgShow = Message.obtain();
                msgShow.what = 40;
                msgShow.obj = pkgName;
                if (!TextUtils.isEmpty(show)) {
                    msgShow.arg1 = Integer.parseInt(show);
                }
                SmRccPolicy.this.mHandler.sendMessage(msgShow);
                return;
            }
            if (SmRccPolicy.ACTION_UPDATE_OPEN.equals(action)) {
                Message msgOpen = Message.obtain();
                msgOpen.what = 50;
                msgOpen.obj = pkgName;
                if (!TextUtils.isEmpty(open)) {
                    msgOpen.arg1 = Integer.parseInt(open);
                }
                SmRccPolicy.this.mHandler.sendMessage(msgOpen);
                return;
            }
            if (SmRccPolicy.ACTION_UPDATE_RESTRICT.equals(action)) {
                Message msgRestrict = Message.obtain();
                msgRestrict.what = 60;
                msgRestrict.obj = pkgName;
                if (!TextUtils.isEmpty(restrict)) {
                    msgRestrict.arg1 = Integer.parseInt(restrict);
                }
                SmRccPolicy.this.mHandler.sendMessage(msgRestrict);
            }
        }
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    SmRccPolicy.this.loadRccAppFromSm();
                    break;
                case 20:
                    SmRccPolicy.this.addRccPackage((RccApp) msg.obj);
                    break;
                case 30:
                    SmRccPolicy.this.removeRccPackage((String) msg.obj);
                    break;
                case 40:
                    SmRccPolicy.this.updateRccShow((String) msg.obj, String.valueOf(msg.arg1));
                    break;
                case 50:
                    SmRccPolicy.this.updateRccOpen((String) msg.obj, String.valueOf(msg.arg1));
                    break;
                case 60:
                    SmRccPolicy.this.updateRccRestrict((String) msg.obj, String.valueOf(msg.arg1));
                    break;
                case 70:
                    SmRccPolicy.this.callResetSmRccOpen((String) msg.obj);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadRccAppFromSm() {
        Slog.i(TAG, "loadRccAppFromSm: ");
        mRccPkgMap.clear();
        try {
            Cursor cursor =
                    this.mContext
                            .getContentResolver()
                            .query(RCC_APP_CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                try {
                    if (!cursor.isClosed()) {
                        while (cursor.moveToNext()) {
                            String pkgName =
                                    cursor.getString(cursor.getColumnIndex("package_name"));
                            String show = cursor.getString(cursor.getColumnIndex("show"));
                            String open = cursor.getString(cursor.getColumnIndex("open"));
                            String restrict = cursor.getString(cursor.getColumnIndex(RESTRICT));
                            mRccPkgMap.put(pkgName, new RccApp(pkgName, show, open, restrict));
                            Slog.i(TAG, "put=" + pkgName);
                        }
                    }
                } finally {
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Slog.e(TAG, "loadRccAppFromSm error", e);
        }
    }

    public void addRccPackage(RccApp rccApp) {
        String pkgName;
        if (rccApp != null && (pkgName = rccApp.getPkgName()) != null) {
            mRccPkgMap.put(pkgName, rccApp);
            Slog.i(TAG, "addRccPackage: " + pkgName);
        }
    }

    public void removeRccPackage(String packageName) {
        if (packageName != null) {
            mRccPkgMap.remove(packageName);
        }
        Slog.i(TAG, "removeRccPackage: " + packageName);
    }

    public void updateRccShow(String packageName, String show) {
        RccApp rccApp = mRccPkgMap.get(packageName);
        if (rccApp != null) {
            rccApp.setShow(show);
        }
        Slog.i(TAG, "update rcc show pkg=" + packageName + " show=" + show);
    }

    public void updateRccOpen(String packageName, String open) {
        RccApp rccApp = mRccPkgMap.get(packageName);
        if (rccApp != null) {
            rccApp.setOpen(open);
        }
        Slog.i(TAG, "update rcc open pkg=" + packageName + " open=" + open);
    }

    public void updateRccRestrict(String packageName, String restrict) {
        RccApp rccApp = mRccPkgMap.get(packageName);
        if (rccApp != null) {
            rccApp.setRestrict(restrict);
        }
        Slog.i(TAG, "update rcc restrict pkg=" + packageName + " restrict=" + restrict);
    }

    public boolean isSmRccPkg(String packageName) {
        RccApp rccApp;
        if (packageName == null || (rccApp = mRccPkgMap.get(packageName)) == null) {
            return false;
        }
        String show = rccApp.getShow();
        String restrict = rccApp.getRestrict();
        Slog.i(TAG, "isSmRccPkg=" + packageName + ",show=" + show + ",restrict=" + restrict);
        if (!"1".equals(show) || !"1".equals(restrict)) {
            return false;
        }
        return true;
    }

    public boolean isSmRccOpen(String packageName) {
        RccApp rccApp;
        if (packageName == null || (rccApp = mRccPkgMap.get(packageName)) == null) {
            return false;
        }
        String open = rccApp.getOpen();
        Slog.i(TAG, "isSmRccOpen=" + packageName + ",open=" + open);
        if (!"1".equals(open)) {
            return false;
        }
        return true;
    }

    public void resetSmRccOpen(String packageName) {
        Message message = Message.obtain();
        message.what = 70;
        message.obj = packageName;
        this.mHandler.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callResetSmRccOpen(String packageName) {
        if (packageName != null && mRccPkgMap.containsKey(packageName)) {
            Bundle bundle = new Bundle();
            bundle.putString("package_name", packageName);
            try {
                this.mContext
                        .getContentResolver()
                        .call(RCC_APP_AUTHORITY_URI, RESET_SM_RCC_OPEN, (String) null, bundle);
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
        if (this.mHandler == null) {
            Slog.i(TAG, "SmRcc handler is null, return");
        } else {
            this.mHandler.sendEmptyMessage(10);
        }
    }

    private static class RccApp {
        private String open;
        private String pkgName;
        private String restrict;
        private String show;

        public RccApp(String pkgName, String show, String open, String restrict) {
            this.pkgName = pkgName;
            this.show = show;
            this.open = open;
            this.restrict = restrict;
        }

        public String getPkgName() {
            return this.pkgName;
        }

        public void setPkgName(String pkgName) {
            this.pkgName = pkgName;
        }

        public String getShow() {
            return this.show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getOpen() {
            return this.open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getRestrict() {
            return this.restrict;
        }

        public void setRestrict(String restrict) {
            this.restrict = restrict;
        }
    }
}
