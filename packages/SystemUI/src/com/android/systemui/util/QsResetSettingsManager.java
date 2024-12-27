package com.android.systemui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class QsResetSettingsManager {
    private static final String TAG = "QsResetSettingsManager";
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final Context mContext;
    private BroadcastReceiver mSoftResetReceiver;
    private ArrayList<ResetSettingsApplier> mAppliers = new ArrayList<>();
    private ArrayList<DemoResetSettingsApplier> mDemoAppliers = new ArrayList<>();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface DemoResetSettingsApplier {
        void applyDemoResetSetting();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ResetSettingsApplier {
        void applyResetSetting();
    }

    public QsResetSettingsManager(Context context, BroadcastDispatcher broadcastDispatcher) {
        this.mSoftResetReceiver = null;
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mSoftResetReceiver = new BroadcastReceiver() { // from class: com.android.systemui.util.QsResetSettingsManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)) {
                    Log.d(QsResetSettingsManager.TAG, "Soft reset Started");
                    QsResetSettingsManager.this.runReset();
                } else if ("com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action)) {
                    Log.d(QsResetSettingsManager.TAG, "Demo reset Started");
                    if (DeviceState.isShopDemo(QsResetSettingsManager.this.mContext)) {
                        QsResetSettingsManager.this.runDemoReset();
                    }
                }
            }
        };
        broadcastDispatcher.registerReceiver(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.samsung.intent.action.SETTINGS_SOFT_RESET", "com.samsung.sea.rm.DEMO_RESET_STARTED"), this.mSoftResetReceiver);
    }

    public void destroy() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mSoftResetReceiver);
        this.mSoftResetReceiver = null;
    }

    public void registerApplier(ResetSettingsApplier resetSettingsApplier) {
        for (int i = 0; i < this.mAppliers.size(); i++) {
            if (this.mAppliers.get(i) == resetSettingsApplier) {
                Log.d(TAG, "registerApplier() mAppliers has same applier");
                return;
            }
        }
        this.mAppliers.add(resetSettingsApplier);
    }

    public void registerDemoApplier(DemoResetSettingsApplier demoResetSettingsApplier) {
        for (int i = 0; i < this.mDemoAppliers.size(); i++) {
            if (this.mDemoAppliers.get(i) == demoResetSettingsApplier) {
                Log.d(TAG, "registerDemoApplier() mAppliers has same applier");
                return;
            }
        }
        this.mDemoAppliers.add(demoResetSettingsApplier);
    }

    public void runDemoReset() {
        Log.d(TAG, "runDemoReset");
        for (int i = 0; i < this.mDemoAppliers.size(); i++) {
            DemoResetSettingsApplier demoResetSettingsApplier = this.mDemoAppliers.get(i);
            if (demoResetSettingsApplier != null) {
                demoResetSettingsApplier.applyDemoResetSetting();
            }
        }
    }

    public void runReset() {
        Log.d(TAG, "runReset");
        for (int i = 0; i < this.mAppliers.size(); i++) {
            ResetSettingsApplier resetSettingsApplier = this.mAppliers.get(i);
            if (resetSettingsApplier != null) {
                resetSettingsApplier.applyResetSetting();
            }
        }
    }

    public void unregisterApplier(ResetSettingsApplier resetSettingsApplier) {
        Log.d(TAG, "unregisterApplier() applier=" + resetSettingsApplier);
        for (int size = this.mAppliers.size() + (-1); size >= 0; size--) {
            if (this.mAppliers.get(size) == resetSettingsApplier) {
                this.mAppliers.remove(size);
            }
        }
    }

    public void unregisterDemoApplier(DemoResetSettingsApplier demoResetSettingsApplier) {
        Log.d(TAG, "unregisterDemoApplier() applier=" + demoResetSettingsApplier);
        for (int size = this.mDemoAppliers.size() + (-1); size >= 0; size--) {
            if (this.mDemoAppliers.get(size) == demoResetSettingsApplier) {
                this.mDemoAppliers.remove(size);
            }
        }
    }
}
