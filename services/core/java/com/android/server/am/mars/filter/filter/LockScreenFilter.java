package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Slog;
import com.android.server.am.mars.filter.IFilter;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LockScreenFilter implements IFilter {
    public boolean isLockTypeClockFace;
    public boolean isLockTypeClockFaceSub;
    public Context mContext;
    public String mKeyguardPkg;
    public Map mKeyguardPkgMap;
    public int mKeyguardPkgUid;
    public AnonymousClass1 mLockClockFaceObserver;
    public AnonymousClass1 mLockClockFaceSubObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LockScreenFilterHolder {
        public static final LockScreenFilter INSTANCE;

        static {
            LockScreenFilter lockScreenFilter = new LockScreenFilter();
            lockScreenFilter.mContext = null;
            lockScreenFilter.mKeyguardPkg = null;
            lockScreenFilter.mKeyguardPkgUid = -1;
            lockScreenFilter.mKeyguardPkgMap = new LinkedHashMap();
            INSTANCE = lockScreenFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
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
            Slog.e("MARs:LockScreenFilter", "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        String str2;
        if ((this.isLockTypeClockFace || this.isLockTypeClockFaceSub) && "com.samsung.android.app.clockface".equals(str)) {
            return 13;
        }
        if (this.mKeyguardPkgUid == i2 && (str2 = this.mKeyguardPkg) != null && str2.equals(str)) {
            return 13;
        }
        if (i3 != 17) {
            return 0;
        }
        String str3 = (String) ((LinkedHashMap) this.mKeyguardPkgMap).get(Integer.valueOf(i2));
        return (str3 == null || !str3.equals(str)) ? 0 : 13;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getLockClockFace() {
        /*
            r8 = this;
            r0 = 0
            android.content.Context r1 = r8.mContext     // Catch: java.lang.Exception -> L30
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch: java.lang.Exception -> L30
            java.lang.String r1 = "content://com.samsung.android.app.clockpack.provider/lock_settings/lock_clock_type"
            android.net.Uri r3 = android.net.Uri.parse(r1)     // Catch: java.lang.Exception -> L30
            r6 = 0
            r7 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L30
            if (r1 == 0) goto L33
            int r2 = r1.getCount()     // Catch: java.lang.Throwable -> L26
            if (r2 <= 0) goto L33
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L26
            int r2 = r1.getInt(r0)     // Catch: java.lang.Throwable -> L26
            goto L34
        L26:
            r2 = move-exception
            r1.close()     // Catch: java.lang.Throwable -> L2b
            goto L2f
        L2b:
            r1 = move-exception
            r2.addSuppressed(r1)     // Catch: java.lang.Exception -> L30
        L2f:
            throw r2     // Catch: java.lang.Exception -> L30
        L30:
            r1 = move-exception
            r2 = r0
            goto L3b
        L33:
            r2 = r0
        L34:
            if (r1 == 0) goto L3e
            r1.close()     // Catch: java.lang.Exception -> L3a
            goto L3e
        L3a:
            r1 = move-exception
        L3b:
            r1.printStackTrace()
        L3e:
            r1 = 40000(0x9c40, float:5.6052E-41)
            if (r2 < r1) goto L49
            r1 = 80000(0x13880, float:1.12104E-40)
            if (r2 > r1) goto L49
            r0 = 1
        L49:
            r8.isLockTypeClockFace = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.mars.filter.filter.LockScreenFilter.getLockClockFace():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getLockClockFaceSub() {
        /*
            r8 = this;
            r0 = 0
            android.content.Context r1 = r8.mContext     // Catch: java.lang.Exception -> L30
            android.content.ContentResolver r2 = r1.getContentResolver()     // Catch: java.lang.Exception -> L30
            java.lang.String r1 = "content://com.samsung.android.app.clockpack.provider/lock_settings/lock_sub_clock_type"
            android.net.Uri r3 = android.net.Uri.parse(r1)     // Catch: java.lang.Exception -> L30
            r6 = 0
            r7 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L30
            if (r1 == 0) goto L33
            int r2 = r1.getCount()     // Catch: java.lang.Throwable -> L26
            if (r2 <= 0) goto L33
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L26
            int r2 = r1.getInt(r0)     // Catch: java.lang.Throwable -> L26
            goto L34
        L26:
            r2 = move-exception
            r1.close()     // Catch: java.lang.Throwable -> L2b
            goto L2f
        L2b:
            r1 = move-exception
            r2.addSuppressed(r1)     // Catch: java.lang.Exception -> L30
        L2f:
            throw r2     // Catch: java.lang.Exception -> L30
        L30:
            r1 = move-exception
            r2 = r0
            goto L3b
        L33:
            r2 = r0
        L34:
            if (r1 == 0) goto L3e
            r1.close()     // Catch: java.lang.Exception -> L3a
            goto L3e
        L3a:
            r1 = move-exception
        L3b:
            r1.printStackTrace()
        L3e:
            r1 = 40000(0x9c40, float:5.6052E-41)
            if (r2 < r1) goto L49
            r1 = 80000(0x13880, float:1.12104E-40)
            if (r2 > r1) goto L49
            r0 = 1
        L49:
            r8.isLockTypeClockFaceSub = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.mars.filter.filter.LockScreenFilter.getLockClockFaceSub():void");
    }

    /* JADX WARN: Type inference failed for: r4v8, types: [com.android.server.am.mars.filter.filter.LockScreenFilter$1] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.server.am.mars.filter.filter.LockScreenFilter$1] */
    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        if (context != null) {
            if (this.mLockClockFaceObserver == null) {
                final int i = 0;
                this.mLockClockFaceObserver = new ContentObserver(this, new Handler()) { // from class: com.android.server.am.mars.filter.filter.LockScreenFilter.1
                    public final /* synthetic */ LockScreenFilter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        switch (i) {
                            case 0:
                                this.this$0.getLockClockFace();
                                break;
                            default:
                                this.this$0.getLockClockFaceSub();
                                break;
                        }
                    }
                };
            }
            if (this.mLockClockFaceSubObserver == null) {
                final int i2 = 1;
                this.mLockClockFaceSubObserver = new ContentObserver(this, new Handler()) { // from class: com.android.server.am.mars.filter.filter.LockScreenFilter.1
                    public final /* synthetic */ LockScreenFilter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        switch (i2) {
                            case 0:
                                this.this$0.getLockClockFace();
                                break;
                            default:
                                this.this$0.getLockClockFaceSub();
                                break;
                        }
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
        getLockClockFace();
        getLockClockFaceSub();
    }
}
