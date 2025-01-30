package com.android.systemui.statusbar.phone.datausage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DataUsageLabelView extends DataUsageLabelCommonView {
    public static final boolean DEBUG = DataUsageLabelManager.DEBUG;
    public String mDataUsage;
    public boolean mDataUsageVisibility;
    public Handler mHandler;
    public final C31771 mReceiver;
    public Thread mThread;
    public final RunnableC31793 mUpdateRunnable;

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
                    ExifInterface$$ExternalSyntheticOutline0.m35m(ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action) || "com.samsung.systemui.statusbar.ANIMATING".equals(action)) {
                        DataUsageLabelView.this.updateUsageInfo();
                        return;
                    }
                    return;
                }
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                dataUsageLabelView.mDataUsageVisibility = DeviceState.getActiveSimCount(dataUsageLabelView.mContext) > 0;
                DataUsageLabelView.this.updateUsageInfo();
                if (z) {
                    StringBuilder sb = new StringBuilder("ACTION_SIM_STATE_CHANGED: visibility=");
                    sb.append(DataUsageLabelView.this.mDataUsageVisibility);
                    sb.append(" rewrite String to ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
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
        this.mDataUsageVisibility = DeviceState.getActiveSimCount(this.mContext) > 0;
    }

    @Override // com.android.systemui.statusbar.phone.datausage.DataUsageLabelCommonView, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mReceiver);
    }

    @Override // com.android.systemui.statusbar.phone.datausage.DataUsageLabelCommonView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mReceiver);
    }

    public final void updateDataText() {
        if (!this.mDataUsageVisibility) {
            this.mDataUsage = "";
        } else if (!TextUtils.isEmpty(this.mDataUsage)) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(this.mContext.getString(R.string.quick_panel_data_usage), ": ");
            m2m.append(this.mDataUsage);
            this.mDataUsage = m2m.toString();
        }
        if (!this.mDataUsage.equals(getText().toString())) {
            setText(this.mDataUsage);
        }
        ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Data Usage:"), this.mDataUsage, "DataUsageLabelView");
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
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:11:0x0075  */
                /* JADX WARN: Removed duplicated region for block: B:9:0x0054 A[Catch: Exception -> 0x0058, TRY_LEAVE, TryCatch #3 {Exception -> 0x0058, blocks: (B:9:0x0054, B:25:0x0050, B:28:0x004d, B:24:0x0048), top: B:5:0x0023, inners: #4 }] */
                /* JADX WARN: Type inference failed for: r5v1, types: [android.content.ContentResolver] */
                /* JADX WARN: Type inference failed for: r6v0 */
                /* JADX WARN: Type inference failed for: r6v1 */
                /* JADX WARN: Type inference failed for: r6v10 */
                /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.String] */
                /* JADX WARN: Type inference failed for: r6v3, types: [android.net.Uri] */
                /* JADX WARN: Type inference failed for: r6v4 */
                /* JADX WARN: Type inference failed for: r6v5 */
                /* JADX WARN: Type inference failed for: r6v6 */
                /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.String] */
                /* JADX WARN: Type inference failed for: r6v8 */
                /* JADX WARN: Type inference failed for: r6v9 */
                @Override // java.lang.Thread, java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    ?? r6;
                    Cursor query;
                    DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                    boolean z = DataUsageLabelView.DEBUG;
                    dataUsageLabelView.getClass();
                    try {
                        ?? contentResolver = dataUsageLabelView.mContext.getContentResolver();
                        r6 = Uri.parse("content://com.samsung.android.sm.dcapi");
                        query = contentResolver.query(r6, null, "getUsageLabel", null, null);
                    } catch (Exception e) {
                        e = e;
                        r6 = "";
                    }
                    try {
                    } catch (Exception e2) {
                        e = e2;
                        Log.e("DataUsageLabelView", "query Data Usage fail: " + e.toString());
                        dataUsageLabelView.mDataUsage = r6 != 0 ? r6.trim() : "";
                        DataUsageLabelView dataUsageLabelView2 = DataUsageLabelView.this;
                        dataUsageLabelView2.mHandler.post(dataUsageLabelView2.mUpdateRunnable);
                    }
                    if (query != null) {
                        try {
                            if (query.moveToFirst()) {
                                r6 = query.getString(0);
                                try {
                                    Log.d("DataUsageLabelView", "query result: " + query.getString(0));
                                    r6 = r6;
                                    if (query != null) {
                                        query.close();
                                    }
                                    dataUsageLabelView.mDataUsage = r6 != 0 ? r6.trim() : "";
                                    DataUsageLabelView dataUsageLabelView22 = DataUsageLabelView.this;
                                    dataUsageLabelView22.mHandler.post(dataUsageLabelView22.mUpdateRunnable);
                                } catch (Throwable th) {
                                    th = th;
                                    try {
                                        query.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            r6 = "";
                        }
                    }
                    r6 = "";
                    if (query != null) {
                    }
                    dataUsageLabelView.mDataUsage = r6 != 0 ? r6.trim() : "";
                    DataUsageLabelView dataUsageLabelView222 = DataUsageLabelView.this;
                    dataUsageLabelView222.mHandler.post(dataUsageLabelView222.mUpdateRunnable);
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
                    ExifInterface$$ExternalSyntheticOutline0.m35m(ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action) || "com.samsung.systemui.statusbar.ANIMATING".equals(action)) {
                        DataUsageLabelView.this.updateUsageInfo();
                        return;
                    }
                    return;
                }
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                dataUsageLabelView.mDataUsageVisibility = DeviceState.getActiveSimCount(dataUsageLabelView.mContext) > 0;
                DataUsageLabelView.this.updateUsageInfo();
                if (z) {
                    StringBuilder sb = new StringBuilder("ACTION_SIM_STATE_CHANGED: visibility=");
                    sb.append(DataUsageLabelView.this.mDataUsageVisibility);
                    sb.append(" rewrite String to ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
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
                    ExifInterface$$ExternalSyntheticOutline0.m35m(ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("onReceive: ", action, " DataUsage String: "), DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action) || "com.samsung.systemui.statusbar.ANIMATING".equals(action)) {
                        DataUsageLabelView.this.updateUsageInfo();
                        return;
                    }
                    return;
                }
                DataUsageLabelView dataUsageLabelView = DataUsageLabelView.this;
                dataUsageLabelView.mDataUsageVisibility = DeviceState.getActiveSimCount(dataUsageLabelView.mContext) > 0;
                DataUsageLabelView.this.updateUsageInfo();
                if (z) {
                    StringBuilder sb = new StringBuilder("ACTION_SIM_STATE_CHANGED: visibility=");
                    sb.append(DataUsageLabelView.this.mDataUsageVisibility);
                    sb.append(" rewrite String to ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, DataUsageLabelView.this.mDataUsage, "DataUsageLabelView");
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
