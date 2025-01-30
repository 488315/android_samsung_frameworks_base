package com.android.systemui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsResetSettingsManager {
    public final Context mContext;
    public final C35821 mSoftResetReceiver;
    public final ArrayList mAppliers = new ArrayList();
    public final ArrayList mDemoAppliers = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DemoResetSettingsApplier {
        void applyDemoResetSetting();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ResetSettingsApplier {
        void applyResetSetting();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.content.BroadcastReceiver, com.android.systemui.util.QsResetSettingsManager$1] */
    public QsResetSettingsManager(Context context, BroadcastDispatcher broadcastDispatcher) {
        this.mSoftResetReceiver = null;
        this.mContext = context;
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.util.QsResetSettingsManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                int i = 0;
                if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)) {
                    Log.d("QsResetSettingsManager", "Soft reset Started");
                    QsResetSettingsManager qsResetSettingsManager = QsResetSettingsManager.this;
                    qsResetSettingsManager.getClass();
                    Log.d("QsResetSettingsManager", "runReset");
                    while (true) {
                        ArrayList arrayList = qsResetSettingsManager.mAppliers;
                        if (i >= arrayList.size()) {
                            return;
                        }
                        ResetSettingsApplier resetSettingsApplier = (ResetSettingsApplier) arrayList.get(i);
                        if (resetSettingsApplier != null) {
                            resetSettingsApplier.applyResetSetting();
                        }
                        i++;
                    }
                } else {
                    if (!"com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action)) {
                        return;
                    }
                    Log.d("QsResetSettingsManager", "Demo reset Started");
                    if (!DeviceState.isShopDemo(QsResetSettingsManager.this.mContext)) {
                        return;
                    }
                    QsResetSettingsManager qsResetSettingsManager2 = QsResetSettingsManager.this;
                    qsResetSettingsManager2.getClass();
                    Log.d("QsResetSettingsManager", "runDemoReset");
                    while (true) {
                        ArrayList arrayList2 = qsResetSettingsManager2.mDemoAppliers;
                        if (i >= arrayList2.size()) {
                            return;
                        }
                        DemoResetSettingsApplier demoResetSettingsApplier = (DemoResetSettingsApplier) arrayList2.get(i);
                        if (demoResetSettingsApplier != null) {
                            demoResetSettingsApplier.applyDemoResetSetting();
                        }
                        i++;
                    }
                }
            }
        };
        this.mSoftResetReceiver = r2;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.SETTINGS_SOFT_RESET");
        intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
        broadcastDispatcher.registerReceiver(intentFilter, r2);
    }

    public final void registerApplier(ResetSettingsApplier resetSettingsApplier) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mAppliers;
            if (i >= arrayList.size()) {
                arrayList.add(resetSettingsApplier);
                return;
            } else {
                if (arrayList.get(i) == resetSettingsApplier) {
                    Log.d("QsResetSettingsManager", "registerApplier() mAppliers has same applier");
                    return;
                }
                i++;
            }
        }
    }

    public final void registerDemoApplier(DemoResetSettingsApplier demoResetSettingsApplier) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mDemoAppliers;
            if (i >= arrayList.size()) {
                arrayList.add(demoResetSettingsApplier);
                return;
            } else {
                if (arrayList.get(i) == demoResetSettingsApplier) {
                    Log.d("QsResetSettingsManager", "registerDemoApplier() mAppliers has same applier");
                    return;
                }
                i++;
            }
        }
    }
}
