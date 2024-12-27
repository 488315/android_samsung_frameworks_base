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
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.DeviceState;

public final class DataUsageLabelView extends DataUsageLabelCommonView {
    public static final boolean DEBUG = DataUsageLabelManager.DEBUG;
    public String mDataUsage;
    public boolean mDataUsageVisibility;
    public Handler mHandler;
    public final AnonymousClass1 mReceiver;
    public Thread mThread;
    public final AnonymousClass3 mUpdateRunnable;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0052 A[Catch: Exception -> 0x004d, TRY_LEAVE, TryCatch #2 {Exception -> 0x004d, blocks: (B:8:0x0052, B:24:0x004c, B:27:0x0049, B:23:0x0044), top: B:4:0x001f, inners: #3 }] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.lang.String] */
    /* renamed from: -$$Nest$mgetDataUsageResult, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String m2236$$Nest$mgetDataUsageResult(com.android.systemui.statusbar.phone.datausage.DataUsageLabelView r9) {
        /*
            r9.getClass()
            java.lang.String r0 = "DataUsageLabelView"
            java.lang.String r1 = "query result: "
            java.lang.String r2 = ""
            android.content.Context r9 = r9.mContext     // Catch: java.lang.Exception -> L56
            android.content.ContentResolver r3 = r9.getContentResolver()     // Catch: java.lang.Exception -> L56
            java.lang.String r9 = "content://com.samsung.android.sm.dcapi"
            android.net.Uri r4 = android.net.Uri.parse(r9)     // Catch: java.lang.Exception -> L56
            java.lang.String r6 = "getUsageLabel"
            r8 = 0
            r5 = 0
            r7 = 0
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Exception -> L56
            if (r9 == 0) goto L4f
            boolean r3 = r9.moveToFirst()     // Catch: java.lang.Throwable -> L42
            if (r3 == 0) goto L4f
            r3 = 0
            java.lang.String r4 = r9.getString(r3)     // Catch: java.lang.Throwable -> L42
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L40
            java.lang.String r1 = r9.getString(r3)     // Catch: java.lang.Throwable -> L40
            r5.append(r1)     // Catch: java.lang.Throwable -> L40
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L40
            android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L40
            goto L50
        L40:
            r1 = move-exception
            goto L44
        L42:
            r1 = move-exception
            r4 = r2
        L44:
            r9.close()     // Catch: java.lang.Throwable -> L48
            goto L4c
        L48:
            r9 = move-exception
            r1.addSuppressed(r9)     // Catch: java.lang.Exception -> L4d
        L4c:
            throw r1     // Catch: java.lang.Exception -> L4d
        L4d:
            r9 = move-exception
            goto L58
        L4f:
            r4 = r2
        L50:
            if (r9 == 0) goto L6e
            r9.close()     // Catch: java.lang.Exception -> L4d
            goto L6e
        L56:
            r9 = move-exception
            r4 = r2
        L58:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "query Data Usage fail: "
            r1.<init>(r3)
            java.lang.String r9 = r9.toString()
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            android.util.Log.e(r0, r9)
        L6e:
            if (r4 != 0) goto L71
            goto L75
        L71:
            java.lang.String r2 = r4.trim()
        L75:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.m2236$$Nest$mgetDataUsageResult(com.android.systemui.statusbar.phone.datausage.DataUsageLabelView):java.lang.String");
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.phone.datausage.DataUsageLabelView$3] */
    public DataUsageLabelView(Context context) {
        super(context);
        this.mHandler = null;
        this.mDataUsage = "";
        this.mThread = null;
        this.mDataUsageVisibility = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                boolean z = DataUsageLabelView.DEBUG;
                if (z) {
                    ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
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
                DataUsageLabelView.this.updateUsageInfo();
                if (z) {
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
        this.mDataUsageVisibility = DeviceState.getActiveSimCount(((TextView) this).mContext) > 0;
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

    public final void updateUsageInfo() {
        if (!this.mDataUsageVisibility) {
            updateDataText();
        } else {
            if (this.mThread != null) {
                Log.d("DataUsageLabelView", "Last Thread still running");
                return;
            }
            Thread thread = new Thread("updateUsageInfo") { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.2
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                    dataUsageLabelView.mDataUsage = DataUsageLabelView.m2236$$Nest$mgetDataUsageResult(dataUsageLabelView);
                    DataUsageLabelView dataUsageLabelView2 = DataUsageLabelView.this;
                    dataUsageLabelView2.mHandler.post(dataUsageLabelView2.mUpdateRunnable);
                }
            };
            this.mThread = thread;
            thread.start();
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
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                boolean z = DataUsageLabelView.DEBUG;
                if (z) {
                    ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
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
                DataUsageLabelView.this.updateUsageInfo();
                if (z) {
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
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                boolean z = DataUsageLabelView.DEBUG;
                if (z) {
                    ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
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
                DataUsageLabelView.this.updateUsageInfo();
                if (z) {
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
