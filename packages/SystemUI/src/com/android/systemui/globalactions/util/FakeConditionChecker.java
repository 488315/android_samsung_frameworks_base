package com.android.systemui.globalactions.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FakeConditionChecker implements ConditionChecker {
    public static final HashMap sConditionMap = new HashMap();
    public static FakeConditionChecker sInstance;
    public final ConditionChecker mDefaultSystemCondition;
    public final LogWrapper mLogWrapper;
    public final AnonymousClass1 mReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.content.BroadcastReceiver, com.android.systemui.globalactions.util.FakeConditionChecker$1] */
    public FakeConditionChecker(Context context, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mDefaultSystemCondition = conditionChecker;
        this.mLogWrapper = logWrapper;
        if (this.mReceiver == null) {
            IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_CONDITION", "com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_CONDITION");
            ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.util.FakeConditionChecker.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    String stringExtra = intent.getStringExtra("key");
                    Boolean valueOf = Boolean.valueOf(intent.getBooleanExtra("enabled", false));
                    LogWrapper logWrapper2 = FakeConditionChecker.this.mLogWrapper;
                    StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onReceive: ", action, ",", stringExtra, ",");
                    m2.append(valueOf);
                    logWrapper2.v("FakeConditionChecker", m2.toString());
                    if ("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_CONDITION".equals(action)) {
                        FakeConditionChecker.this.updateCondition(stringExtra, valueOf);
                    } else if ("com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_CONDITION".equals(action)) {
                        FakeConditionChecker fakeConditionChecker = FakeConditionChecker.this;
                        fakeConditionChecker.getClass();
                        FakeConditionChecker.sConditionMap.remove(stringExtra);
                        fakeConditionChecker.mLogWrapper.v("FakeConditionChecker", "removed");
                    }
                }
            };
            this.mReceiver = r0;
            context.registerReceiver(r0, m, 2);
            logWrapper.v("FakeConditionChecker", "initialized");
        }
        sInstance = this;
    }

    public final boolean isEnabled(Object obj) {
        String obj2 = obj.toString();
        HashMap hashMap = sConditionMap;
        if (!hashMap.containsKey(obj2)) {
            return this.mDefaultSystemCondition.isEnabled(obj);
        }
        boolean booleanValue = ((Boolean) hashMap.get(obj2)).booleanValue();
        this.mLogWrapper.v("FakeConditionChecker", "[Fake : " + obj2.toLowerCase() + "] " + booleanValue);
        return booleanValue;
    }

    public final void updateCondition(String str, Boolean bool) {
        HashMap hashMap = sConditionMap;
        if (hashMap.containsKey(str)) {
            hashMap.replace(str, bool);
            this.mLogWrapper.v("FakeConditionChecker", "updated");
        } else {
            hashMap.put(str, bool);
            this.mLogWrapper.v("FakeConditionChecker", "added");
        }
    }
}
