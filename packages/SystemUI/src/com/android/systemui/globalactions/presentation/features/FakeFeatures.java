package com.android.systemui.globalactions.presentation.features;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.util.LogWrapper;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class FakeFeatures implements Features {
    public static final HashMap sConditionMap = new HashMap();
    public static FakeFeatures sInstance;
    public final Features mDefaultFeatures;
    public final LogWrapper mLogWrapper;
    public final AnonymousClass1 mReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.content.BroadcastReceiver, com.android.systemui.globalactions.presentation.features.FakeFeatures$1] */
    public FakeFeatures(Context context, Features features, LogWrapper logWrapper) {
        this.mDefaultFeatures = features;
        this.mLogWrapper = logWrapper;
        if (this.mReceiver == null) {
            IntentFilter intentFilterM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_FEATURE", "com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_FEATURE");
            ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.presentation.features.FakeFeatures.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    String stringExtra = intent.getStringExtra("key");
                    Boolean boolValueOf = Boolean.valueOf(intent.getBooleanExtra("enabled", false));
                    LogWrapper logWrapper2 = FakeFeatures.this.mLogWrapper;
                    StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onReceive: ", action, ",", stringExtra, ",");
                    sbM.append(boolValueOf);
                    logWrapper2.v("FakeFeatures", sbM.toString());
                    if ("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_FEATURE".equals(action)) {
                        FakeFeatures.this.updateFeature(stringExtra, boolValueOf);
                    } else if ("com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_FEATURE".equals(action)) {
                        FakeFeatures fakeFeatures = FakeFeatures.this;
                        fakeFeatures.getClass();
                        FakeFeatures.sConditionMap.remove(stringExtra);
                        fakeFeatures.mLogWrapper.v("FakeFeatures", "removed");
                    }
                }
            };
            this.mReceiver = r0;
            context.registerReceiver(r0, intentFilterM, 2);
            logWrapper.v("FakeFeatures", "initialized");
        }
        sInstance = this;
    }

    public final boolean isEnabled(String str) {
        HashMap map = sConditionMap;
        if (!map.containsKey(str)) {
            return this.mDefaultFeatures.isEnabled(str);
        }
        boolean zBooleanValue = ((Boolean) map.get(str)).booleanValue();
        this.mLogWrapper.v("FakeFeatures", FakeFeatures$$ExternalSyntheticOutline0.m("[Fake : ", str, "] ", zBooleanValue));
        return zBooleanValue;
    }

    public final void updateFeature(String str, Boolean bool) {
        HashMap map = sConditionMap;
        if (map.containsKey(str)) {
            map.replace(str, bool);
            this.mLogWrapper.v("FakeFeatures", "updated");
        } else {
            map.put(str, bool);
            this.mLogWrapper.v("FakeFeatures", "added");
        }
    }
}
