package com.android.server.chimera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.util.Slog;
import java.io.PrintWriter;

/* compiled from: QuickSwap.java */
/* loaded from: classes.dex */
public class GenAIReclaimMode {
    public static final String TAG = "GenAIReclaimMode";
    public boolean mIsRbinRefillModeAvailable;
    public ModelEventReceiver mModelEventReceiver;
    public QuickSwap mQuickSwap;
    public SystemRepository mSystemRepository;
    public static final boolean IS_SHIP_BUILD = "true".equals(SystemProperties.get("ro.product_ship", "false"));
    public static boolean FEATURE_ENABLED = SystemProperties.getBoolean("ro.slmk.genai_reclaim_mode", false);
    public boolean mIsModeOn = false;
    public int mModeCount = 0;

    public GenAIReclaimMode(QuickSwap quickSwap, SystemRepository systemRepository) {
        this.mIsRbinRefillModeAvailable = false;
        this.mQuickSwap = quickSwap;
        this.mSystemRepository = systemRepository;
        boolean __setRbinRefillModePath = QuickSwap.__setRbinRefillModePath();
        this.mIsRbinRefillModeAvailable = __setRbinRefillModePath;
        if (!__setRbinRefillModePath) {
            Slog.i(TAG, "__setRbinBlockModePath failed");
        }
        if (FEATURE_ENABLED) {
            this.mModelEventReceiver = new ModelEventReceiver();
        }
    }

    public boolean isModeOn() {
        return this.mIsModeOn;
    }

    public void setModeOn(boolean z) {
        if ((FEATURE_ENABLED || this.mIsModeOn) && this.mIsModeOn != z) {
            this.mIsModeOn = z;
            if (z) {
                handleModeOn();
            } else {
                handleModeOff();
            }
        }
    }

    public final void handleModeOn() {
        Slog.i(TAG, "Mode ON");
        this.mModeCount++;
        allowRbinRefill(false);
        this.mQuickSwap.tryQuickSwap(2);
        this.mQuickSwap.setGenAIReclaimModeTimeout(5000L);
    }

    public final void handleModeOff() {
        Slog.i(TAG, "Mode OFF");
        allowRbinRefill(true);
    }

    public final void allowRbinRefill(boolean z) {
        if (this.mIsRbinRefillModeAvailable) {
            QuickSwap.__writeRbinRefillMode(!z ? 1 : 0);
        }
    }

    /* compiled from: QuickSwap.java */
    public class ModelEventReceiver extends BroadcastReceiver {
        public ModelEventReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.GEN_AI_RECLAIM");
            intentFilter.addAction("com.samsung.GEN_AI_RECLAIM_END");
            intentFilter.addAction("AICORE_BROADCAST_ACTION_MODEL_LOADING");
            intentFilter.addAction("AICORE_BROADCAST_ACTION_MODEL_UNLOADED");
            GenAIReclaimMode.this.mSystemRepository.registerBroadcastReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                Slog.d(GenAIReclaimMode.TAG, "Receive broadcast: " + action);
            }
            if ("com.samsung.GEN_AI_RECLAIM".equals(action) || "AICORE_BROADCAST_ACTION_MODEL_LOADING".equals(action)) {
                GenAIReclaimMode.this.setModeOn(true);
            } else if ("com.samsung.GEN_AI_RECLAIM_END".equals(action) || "AICORE_BROADCAST_ACTION_MODEL_UNLOADED".equals(action)) {
                GenAIReclaimMode.this.setModeOn(false);
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        try {
            printWriter.println("  gen_ai_reclaim_mode enable: " + FEATURE_ENABLED);
            printWriter.println("    control_rbin_refill: " + this.mIsRbinRefillModeAvailable);
            printWriter.println("    on: " + isModeOn());
            printWriter.println("    count: " + this.mModeCount);
        } catch (Exception e) {
            printWriter.println(e.toString());
            e.printStackTrace();
        }
    }
}
