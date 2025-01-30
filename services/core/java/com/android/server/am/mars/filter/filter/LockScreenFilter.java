package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Slog;
import com.android.server.am.mars.filter.IFilter;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class LockScreenFilter implements IFilter {
    public static String TAG = "MARs:" + LockScreenFilter.class.getSimpleName();
    public boolean isLockTypeClockFace;
    public boolean isLockTypeClockFaceSub;
    public Context mContext;
    public String mKeyguardPkg;
    public Map mKeyguardPkgMap;
    public int mKeyguardPkgUid;
    public ContentObserver mLockClockFaceObserver;
    public ContentObserver mLockClockFaceSubObserver;

    public abstract class LockScreenFilterHolder {
        public static final LockScreenFilter INSTANCE = new LockScreenFilter();
    }

    public LockScreenFilter() {
        this.mContext = null;
        this.mKeyguardPkg = null;
        this.mKeyguardPkgUid = -1;
        this.mKeyguardPkgMap = new LinkedHashMap();
    }

    public static LockScreenFilter getInstance() {
        return LockScreenFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        this.mContext = context;
        registerContentObserver();
        getLockClockFace();
        getLockClockFaceSub();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        unregisterContentObserver();
    }

    public final void registerContentObserver() {
        if (this.mContext != null) {
            if (this.mLockClockFaceObserver == null) {
                this.mLockClockFaceObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.LockScreenFilter.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        LockScreenFilter.this.getLockClockFace();
                    }
                };
            }
            if (this.mLockClockFaceSubObserver == null) {
                this.mLockClockFaceSubObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.LockScreenFilter.2
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        LockScreenFilter.this.getLockClockFaceSub();
                    }
                };
            }
            try {
                this.mContext.getContentResolver().registerContentObserver(Uri.parse("content://com.samsung.android.app.clockpack.provider/lock_settings/lock_clock_type"), false, this.mLockClockFaceObserver);
                this.mContext.getContentResolver().registerContentObserver(Uri.parse("content://com.samsung.android.app.clockpack.provider/lock_settings/lock_sub_clock_type"), false, this.mLockClockFaceSubObserver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void unregisterContentObserver() {
        try {
            Context context = this.mContext;
            if (context != null) {
                if (this.mLockClockFaceObserver != null) {
                    context.getContentResolver().unregisterContentObserver(this.mLockClockFaceObserver);
                    this.mLockClockFaceObserver = null;
                }
                if (this.mLockClockFaceSubObserver != null) {
                    this.mContext.getContentResolver().unregisterContentObserver(this.mLockClockFaceSubObserver);
                    this.mLockClockFaceSubObserver = null;
                }
            }
        } catch (IllegalArgumentException unused) {
            Slog.e(TAG, "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        String str2;
        String str3;
        if ((this.isLockTypeClockFace || this.isLockTypeClockFaceSub) && "com.samsung.android.app.clockface".equals(str)) {
            return 13;
        }
        if (this.mKeyguardPkgUid == i2 && (str3 = this.mKeyguardPkg) != null && str3.equals(str)) {
            return 13;
        }
        return (i3 == 17 && (str2 = (String) this.mKeyguardPkgMap.get(Integer.valueOf(i2))) != null && str2.equals(str)) ? 13 : 0;
    }

    public void setKeyguardInfo(String str, int i) {
        this.mKeyguardPkg = str;
        this.mKeyguardPkgUid = i;
        if (str != null) {
            this.mKeyguardPkgMap.remove(Integer.valueOf(i));
            if (this.mKeyguardPkgMap.size() >= 5) {
                Map map = this.mKeyguardPkgMap;
                map.remove(((Map.Entry) map.entrySet().iterator().next()).getKey());
            }
            this.mKeyguardPkgMap.put(Integer.valueOf(i), str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getLockClockFace() {
        int i;
        Cursor query;
        boolean z = false;
        try {
            query = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.app.clockpack.provider/lock_settings/lock_clock_type"), null, null, null, null);
        } catch (Exception e) {
            e = e;
            i = 0;
            e.printStackTrace();
            if (i >= 40000) {
            }
            this.isLockTypeClockFace = z;
        }
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    query.moveToFirst();
                    i = query.getInt(0);
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            if (i >= 40000) {
                            }
                            this.isLockTypeClockFace = z;
                        }
                    }
                    if (i >= 40000 && i <= 80000) {
                        z = true;
                    }
                    this.isLockTypeClockFace = z;
                }
            } finally {
            }
        }
        i = 0;
        if (query != null) {
        }
        if (i >= 40000) {
            z = true;
        }
        this.isLockTypeClockFace = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getLockClockFaceSub() {
        int i;
        Cursor query;
        boolean z = false;
        try {
            query = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.app.clockpack.provider/lock_settings/lock_sub_clock_type"), null, null, null, null);
        } catch (Exception e) {
            e = e;
            i = 0;
            e.printStackTrace();
            if (i >= 40000) {
            }
            this.isLockTypeClockFaceSub = z;
        }
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    query.moveToFirst();
                    i = query.getInt(0);
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            if (i >= 40000) {
                            }
                            this.isLockTypeClockFaceSub = z;
                        }
                    }
                    if (i >= 40000 && i <= 80000) {
                        z = true;
                    }
                    this.isLockTypeClockFaceSub = z;
                }
            } finally {
            }
        }
        i = 0;
        if (query != null) {
        }
        if (i >= 40000) {
            z = true;
        }
        this.isLockTypeClockFaceSub = z;
    }
}
