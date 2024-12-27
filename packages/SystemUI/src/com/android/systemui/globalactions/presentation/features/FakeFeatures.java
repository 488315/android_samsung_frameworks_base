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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FakeFeatures implements Features {
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
            IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_FEATURE", "com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_FEATURE");
            ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.presentation.features.FakeFeatures.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    String stringExtra = intent.getStringExtra("key");
                    Boolean valueOf = Boolean.valueOf(intent.getBooleanExtra("enabled", false));
                    LogWrapper logWrapper2 = FakeFeatures.this.mLogWrapper;
                    StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onReceive: ", action, ",", stringExtra, ",");
                    m2.append(valueOf);
                    logWrapper2.v("FakeFeatures", m2.toString());
                    if ("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_FEATURE".equals(action)) {
                        FakeFeatures.this.updateFeature(stringExtra, valueOf);
                    } else if ("com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_FEATURE".equals(action)) {
                        FakeFeatures fakeFeatures = FakeFeatures.this;
                        fakeFeatures.getClass();
                        FakeFeatures.sConditionMap.remove(stringExtra);
                        fakeFeatures.mLogWrapper.v("FakeFeatures", "removed");
                    }
                }
            };
            this.mReceiver = r0;
            context.registerReceiver(r0, m, 2);
            logWrapper.v("FakeFeatures", "initialized");
        }
        sInstance = this;
    }

    public final boolean isEnabled(String str) {
        HashMap hashMap = sConditionMap;
        if (!hashMap.containsKey(str)) {
            return this.mDefaultFeatures.isEnabled(str);
        }
        boolean booleanValue = ((Boolean) hashMap.get(str)).booleanValue();
        this.mLogWrapper.v("FakeFeatures", "[Fake : " + str + "] " + booleanValue);
        return booleanValue;
    }

    public final void updateFeature(String str, Boolean bool) {
        HashMap hashMap = sConditionMap;
        if (hashMap.containsKey(str)) {
            hashMap.replace(str, bool);
            this.mLogWrapper.v("FakeFeatures", "updated");
        } else {
            hashMap.put(str, bool);
            this.mLogWrapper.v("FakeFeatures", "added");
        }
    }
}
