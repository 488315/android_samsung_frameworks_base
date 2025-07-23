package com.android.systemui.statusbar.phone.datausage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.bar.DataUsageBar;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DataUsageLabelView extends DataUsageLabelCommonView {
    public static final boolean DEBUG = DataUsageLabelManager.DEBUG;
    public static DataUsageBar mVisibilityChangedListener;
    public String mDataUsage;
    public boolean mDataUsageVisibility;
    public Handler mHandler;
    public boolean mPreDataUsageVisibility;
    public final AnonymousClass1 mReceiver;
    public NotificationShelfManager mShelfManager;
    public AnonymousClass2 mThread;
    public final AnonymousClass3 mUpdateRunnable;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0056 A[Catch: Exception -> 0x0050, TRY_LEAVE, TryCatch #0 {Exception -> 0x0050, blocks: (B:8:0x0056, B:30:0x004f, B:29:0x004c, B:24:0x0046), top: B:4:0x001f, inners: #4 }] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* renamed from: -$$Nest$mgetDataUsageResult, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String m3080$$Nest$mgetDataUsageResult(com.android.systemui.statusbar.phone.datausage.DataUsageLabelView r9) {
        /*
            r9.getClass()
            java.lang.String r1 = "DataUsageLabelView"
            java.lang.String r0 = "query result: "
            java.lang.String r2 = ""
            android.content.Context r9 = r9.mContext     // Catch: java.lang.Exception -> L5a
            android.content.ContentResolver r3 = r9.getContentResolver()     // Catch: java.lang.Exception -> L5a
            java.lang.String r9 = "content://com.samsung.android.sm.dcapi"
            android.net.Uri r4 = android.net.Uri.parse(r9)     // Catch: java.lang.Exception -> L5a
            java.lang.String r6 = "getUsageLabel"
            r8 = 0
            r5 = 0
            r7 = 0
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Exception -> L5a
            if (r9 == 0) goto L53
            boolean r3 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L43
            if (r3 == 0) goto L53
            r3 = 0
            java.lang.String r4 = r9.getString(r3)     // Catch: java.lang.Throwable -> L43
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L40
            java.lang.String r0 = r9.getString(r3)     // Catch: java.lang.Throwable -> L40
            r5.append(r0)     // Catch: java.lang.Throwable -> L40
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L40
            android.util.Log.d(r1, r0)     // Catch: java.lang.Throwable -> L40
            goto L54
        L40:
            r0 = move-exception
            r3 = r0
            goto L46
        L43:
            r0 = move-exception
            r3 = r0
            r4 = r2
        L46:
            r9.close()     // Catch: java.lang.Throwable -> L4a
            goto L4f
        L4a:
            r0 = move-exception
            r9 = r0
            r3.addSuppressed(r9)     // Catch: java.lang.Exception -> L50
        L4f:
            throw r3     // Catch: java.lang.Exception -> L50
        L50:
            r0 = move-exception
            r9 = r0
            goto L5d
        L53:
            r4 = r2
        L54:
            if (r9 == 0) goto L73
            r9.close()     // Catch: java.lang.Exception -> L50
            goto L73
        L5a:
            r0 = move-exception
            r9 = r0
            r4 = r2
        L5d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "query Data Usage fail: "
            r0.<init>(r3)
            java.lang.String r9 = r9.toString()
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.e(r1, r9)
        L73:
            if (r4 != 0) goto L76
            goto L7a
        L76:
            java.lang.String r2 = r4.trim()
        L7a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.m3080$$Nest$mgetDataUsageResult(com.android.systemui.statusbar.phone.datausage.DataUsageLabelView):java.lang.String");
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$3] */
    public DataUsageLabelView(Context context) {
        super(context);
        this.mHandler = null;
        this.mDataUsage = "";
        this.mThread = null;
        this.mDataUsageVisibility = false;
        this.mPreDataUsageVisibility = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean z;
                String action = intent.getAction();
                boolean z2 = DataUsageLabelView.DEBUG;
                if (z2) {
                    ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action) || "com.samsung.systemui.statusbar.ANIMATING".equals(action)) {
                        DataUsageLabelView.this.updateUsageInfo();
                        return;
                    }
                    return;
                }
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                dataUsageLabelView.mDataUsageVisibility = DeviceState.getActiveSimCount(((TextView) dataUsageLabelView).mContext) > 0;
                DataUsageLabelView dataUsageLabelView2 = DataUsageLabelView.this;
                dataUsageLabelView2.setVisibility(dataUsageLabelView2.mDataUsageVisibility ? 0 : 8);
                DataUsageBar dataUsageBar = DataUsageLabelView.mVisibilityChangedListener;
                if (dataUsageBar != null) {
                    if (DataUsageLabelView.this.getVisibility() == 0) {
                        dataUsageBar.getClass();
                        if (SecPanelSplitHelper.isEnabled()) {
                            z = true;
                            dataUsageBar.showBar(z);
                        }
                    }
                    z = false;
                    dataUsageBar.showBar(z);
                }
                DataUsageLabelView.this.updateUsageInfo();
                DataUsageLabelView dataUsageLabelView3 = DataUsageLabelView.this;
                boolean z3 = dataUsageLabelView3.mPreDataUsageVisibility;
                boolean z4 = dataUsageLabelView3.mDataUsageVisibility;
                if (z3 != z4) {
                    dataUsageLabelView3.mPreDataUsageVisibility = z4;
                    NotificationShelfManager notificationShelfManager = dataUsageLabelView3.mShelfManager;
                    if (notificationShelfManager != null) {
                        notificationShelfManager.updateShelfLayout();
                    }
                }
                if (z2) {
                    StringBuilder sb = new StringBuilder("ACTION_SIM_STATE_CHANGED: visibility=");
                    sb.append(DataUsageLabelView.this.mDataUsageVisibility);
                    sb.append(" rewrite String to ");
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
            }
        };
        this.mUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.3
            @Override // java.lang.Runnable
            public final void run() {
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                boolean z = DataUsageLabelView.DEBUG;
                dataUsageLabelView.updateDataText();
                DataUsageLabelView.this.mThread = null;
            }
        };
        initView();
    }

    public final void initView() {
        this.mHandler = new Handler();
        boolean z = DeviceState.getActiveSimCount(((TextView) this).mContext) > 0;
        this.mDataUsageVisibility = z;
        this.mPreDataUsageVisibility = z;
        this.mShelfManager = (NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class);
    }

    @Override // com.android.systemui.statusbar.phone.datausage.DataUsageLabelCommonView, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mReceiver);
    }

    @Override // com.android.systemui.statusbar.phone.datausage.DataUsageLabelCommonView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mReceiver);
    }

    public final void updateDataText() {
        if (!this.mDataUsageVisibility) {
            this.mDataUsage = "";
        } else if (!TextUtils.isEmpty(this.mDataUsage)) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((TextView) this).mContext.getString(R.string.quick_panel_data_usage), ": ");
            m.append(this.mDataUsage);
            this.mDataUsage = m.toString();
        }
        if (!this.mDataUsage.equals(getText().toString())) {
            setText(this.mDataUsage);
        }
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Data Usage:"), this.mDataUsage, "DataUsageLabelView");
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$2, java.lang.Thread] */
    public final void updateUsageInfo() {
        if (!this.mDataUsageVisibility) {
            updateDataText();
        } else {
            if (this.mThread != null) {
                Log.d("DataUsageLabelView", "Last Thread still running");
                return;
            }
            ?? r0 = new Thread("updateUsageInfo") { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.2
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                    dataUsageLabelView.mDataUsage = DataUsageLabelView.m3080$$Nest$mgetDataUsageResult(dataUsageLabelView);
                    DataUsageLabelView dataUsageLabelView2 = DataUsageLabelView.this;
                    dataUsageLabelView2.mHandler.post(dataUsageLabelView2.mUpdateRunnable);
                }
            };
            this.mThread = r0;
            r0.start();
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$3] */
    public DataUsageLabelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = null;
        this.mDataUsage = "";
        this.mThread = null;
        this.mDataUsageVisibility = false;
        this.mPreDataUsageVisibility = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean z;
                String action = intent.getAction();
                boolean z2 = DataUsageLabelView.DEBUG;
                if (z2) {
                    ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action) || "com.samsung.systemui.statusbar.ANIMATING".equals(action)) {
                        DataUsageLabelView.this.updateUsageInfo();
                        return;
                    }
                    return;
                }
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                dataUsageLabelView.mDataUsageVisibility = DeviceState.getActiveSimCount(((TextView) dataUsageLabelView).mContext) > 0;
                DataUsageLabelView dataUsageLabelView2 = DataUsageLabelView.this;
                dataUsageLabelView2.setVisibility(dataUsageLabelView2.mDataUsageVisibility ? 0 : 8);
                DataUsageBar dataUsageBar = DataUsageLabelView.mVisibilityChangedListener;
                if (dataUsageBar != null) {
                    if (DataUsageLabelView.this.getVisibility() == 0) {
                        dataUsageBar.getClass();
                        if (SecPanelSplitHelper.isEnabled()) {
                            z = true;
                            dataUsageBar.showBar(z);
                        }
                    }
                    z = false;
                    dataUsageBar.showBar(z);
                }
                DataUsageLabelView.this.updateUsageInfo();
                DataUsageLabelView dataUsageLabelView3 = DataUsageLabelView.this;
                boolean z3 = dataUsageLabelView3.mPreDataUsageVisibility;
                boolean z4 = dataUsageLabelView3.mDataUsageVisibility;
                if (z3 != z4) {
                    dataUsageLabelView3.mPreDataUsageVisibility = z4;
                    NotificationShelfManager notificationShelfManager = dataUsageLabelView3.mShelfManager;
                    if (notificationShelfManager != null) {
                        notificationShelfManager.updateShelfLayout();
                    }
                }
                if (z2) {
                    StringBuilder sb = new StringBuilder("ACTION_SIM_STATE_CHANGED: visibility=");
                    sb.append(DataUsageLabelView.this.mDataUsageVisibility);
                    sb.append(" rewrite String to ");
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
            }
        };
        this.mUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.3
            @Override // java.lang.Runnable
            public final void run() {
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                boolean z = DataUsageLabelView.DEBUG;
                dataUsageLabelView.updateDataText();
                DataUsageLabelView.this.mThread = null;
            }
        };
        initView();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$3] */
    public DataUsageLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = null;
        this.mDataUsage = "";
        this.mThread = null;
        this.mDataUsageVisibility = false;
        this.mPreDataUsageVisibility = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean z;
                String action = intent.getAction();
                boolean z2 = DataUsageLabelView.DEBUG;
                if (z2) {
                    ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action) || "com.samsung.systemui.statusbar.ANIMATING".equals(action)) {
                        DataUsageLabelView.this.updateUsageInfo();
                        return;
                    }
                    return;
                }
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                dataUsageLabelView.mDataUsageVisibility = DeviceState.getActiveSimCount(((TextView) dataUsageLabelView).mContext) > 0;
                DataUsageLabelView dataUsageLabelView2 = DataUsageLabelView.this;
                dataUsageLabelView2.setVisibility(dataUsageLabelView2.mDataUsageVisibility ? 0 : 8);
                DataUsageBar dataUsageBar = DataUsageLabelView.mVisibilityChangedListener;
                if (dataUsageBar != null) {
                    if (DataUsageLabelView.this.getVisibility() == 0) {
                        dataUsageBar.getClass();
                        if (SecPanelSplitHelper.isEnabled()) {
                            z = true;
                            dataUsageBar.showBar(z);
                        }
                    }
                    z = false;
                    dataUsageBar.showBar(z);
                }
                DataUsageLabelView.this.updateUsageInfo();
                DataUsageLabelView dataUsageLabelView3 = DataUsageLabelView.this;
                boolean z3 = dataUsageLabelView3.mPreDataUsageVisibility;
                boolean z4 = dataUsageLabelView3.mDataUsageVisibility;
                if (z3 != z4) {
                    dataUsageLabelView3.mPreDataUsageVisibility = z4;
                    NotificationShelfManager notificationShelfManager = dataUsageLabelView3.mShelfManager;
                    if (notificationShelfManager != null) {
                        notificationShelfManager.updateShelfLayout();
                    }
                }
                if (z2) {
                    StringBuilder sb = new StringBuilder("ACTION_SIM_STATE_CHANGED: visibility=");
                    sb.append(DataUsageLabelView.this.mDataUsageVisibility);
                    sb.append(" rewrite String to ");
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
            }
        };
        this.mUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.3
            @Override // java.lang.Runnable
            public final void run() {
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                boolean z = DataUsageLabelView.DEBUG;
                dataUsageLabelView.updateDataText();
                DataUsageLabelView.this.mThread = null;
            }
        };
        initView();
    }
}
