package com.android.systemui.statusbar.phone.datausage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
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

/* loaded from: classes3.dex */
public class DataUsageLabelView extends DataUsageLabelCommonView {
    public static final boolean DEBUG = DataUsageLabelManager.DEBUG;
    public String mDataUsage;
    public boolean mDataUsageVisibility;
    public Handler mHandler;
    public boolean mPreDataUsageVisibility;
    public final AnonymousClass1 mReceiver;
    public NotificationShelfManager mShelfManager;
    public AnonymousClass2 mThread;
    public final AnonymousClass3 mUpdateRunnable;
    public DataUsageBar mVisibilityChangedListener;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* renamed from: -$$Nest$mgetDataUsageResult, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String m3095$$Nest$mgetDataUsageResult(DataUsageLabelView dataUsageLabelView) throws Throwable {
        Exception exc;
        String str;
        Throwable th;
        dataUsageLabelView.getClass();
        try {
            ?? contentResolver = ((TextView) dataUsageLabelView).mContext.getContentResolver();
            ?? r4 = Uri.parse("content://com.samsung.android.sm.dcapi");
            Cursor cursorQuery = contentResolver.query(r4, null, "getUsageLabel", null, null);
            try {
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            String string = cursorQuery.getString(0);
                            try {
                                Log.d("DataUsageLabelView", "query result: " + cursorQuery.getString(0));
                                str = string;
                            } catch (Throwable th2) {
                                th = th2;
                                r4 = string;
                                try {
                                    cursorQuery.close();
                                    throw th;
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                    throw th;
                                }
                            }
                        } else {
                            str = "";
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        r4 = "";
                    }
                }
            } catch (Exception e) {
                exc = e;
                str = r4;
                Log.e("DataUsageLabelView", "query Data Usage fail: " + exc.toString());
                if (str != null) {
                }
            }
        } catch (Exception e2) {
            exc = e2;
            str = "";
        }
        return str != null ? "" : str.trim();
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
            /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                boolean z = DataUsageLabelView.DEBUG;
                if (z) {
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
                DataUsageLabelView dataUsageLabelView3 = DataUsageLabelView.this;
                DataUsageBar dataUsageBar = dataUsageLabelView3.mVisibilityChangedListener;
                if (dataUsageBar != null) {
                    if (dataUsageLabelView3.getVisibility() == 0) {
                        dataUsageBar.getClass();
                        boolean z2 = SecPanelSplitHelper.isEnabled();
                        dataUsageBar.showBar(z2);
                    }
                }
                DataUsageLabelView.this.updateUsageInfo();
                DataUsageLabelView dataUsageLabelView4 = DataUsageLabelView.this;
                boolean z3 = dataUsageLabelView4.mPreDataUsageVisibility;
                boolean z4 = dataUsageLabelView4.mDataUsageVisibility;
                if (z3 != z4) {
                    dataUsageLabelView4.mPreDataUsageVisibility = z4;
                    NotificationShelfManager notificationShelfManager = dataUsageLabelView4.mShelfManager;
                    if (notificationShelfManager != null) {
                        notificationShelfManager.updateShelfLayout();
                    }
                }
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((TextView) this).mContext.getString(R.string.quick_panel_data_usage), ": ");
            sbM.append(this.mDataUsage);
            this.mDataUsage = sbM.toString();
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
                    dataUsageLabelView.mDataUsage = DataUsageLabelView.m3095$$Nest$mgetDataUsageResult(dataUsageLabelView);
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
            /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                boolean z = DataUsageLabelView.DEBUG;
                if (z) {
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
                DataUsageLabelView dataUsageLabelView3 = DataUsageLabelView.this;
                DataUsageBar dataUsageBar = dataUsageLabelView3.mVisibilityChangedListener;
                if (dataUsageBar != null) {
                    if (dataUsageLabelView3.getVisibility() == 0) {
                        dataUsageBar.getClass();
                        boolean z2 = SecPanelSplitHelper.isEnabled();
                        dataUsageBar.showBar(z2);
                    }
                }
                DataUsageLabelView.this.updateUsageInfo();
                DataUsageLabelView dataUsageLabelView4 = DataUsageLabelView.this;
                boolean z3 = dataUsageLabelView4.mPreDataUsageVisibility;
                boolean z4 = dataUsageLabelView4.mDataUsageVisibility;
                if (z3 != z4) {
                    dataUsageLabelView4.mPreDataUsageVisibility = z4;
                    NotificationShelfManager notificationShelfManager = dataUsageLabelView4.mShelfManager;
                    if (notificationShelfManager != null) {
                        notificationShelfManager.updateShelfLayout();
                    }
                }
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
        this.mPreDataUsageVisibility = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.datausage.DataUsageLabelView.1
            /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                boolean z = DataUsageLabelView.DEBUG;
                if (z) {
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
                DataUsageLabelView dataUsageLabelView3 = DataUsageLabelView.this;
                DataUsageBar dataUsageBar = dataUsageLabelView3.mVisibilityChangedListener;
                if (dataUsageBar != null) {
                    if (dataUsageLabelView3.getVisibility() == 0) {
                        dataUsageBar.getClass();
                        boolean z2 = SecPanelSplitHelper.isEnabled();
                        dataUsageBar.showBar(z2);
                    }
                }
                DataUsageLabelView.this.updateUsageInfo();
                DataUsageLabelView dataUsageLabelView4 = DataUsageLabelView.this;
                boolean z3 = dataUsageLabelView4.mPreDataUsageVisibility;
                boolean z4 = dataUsageLabelView4.mDataUsageVisibility;
                if (z3 != z4) {
                    dataUsageLabelView4.mPreDataUsageVisibility = z4;
                    NotificationShelfManager notificationShelfManager = dataUsageLabelView4.mShelfManager;
                    if (notificationShelfManager != null) {
                        notificationShelfManager.updateShelfLayout();
                    }
                }
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
