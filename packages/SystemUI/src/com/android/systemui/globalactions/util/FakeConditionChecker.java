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

/* loaded from: classes2.dex */
public class FakeConditionChecker implements ConditionChecker {
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
            IntentFilter intentFilterM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_CONDITION", "com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_CONDITION");
            ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.util.FakeConditionChecker.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    String stringExtra = intent.getStringExtra("key");
                    Boolean boolValueOf = Boolean.valueOf(intent.getBooleanExtra("enabled", false));
                    LogWrapper logWrapper2 = FakeConditionChecker.this.mLogWrapper;
                    StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onReceive: ", action, ",", stringExtra, ",");
                    sbM.append(boolValueOf);
                    logWrapper2.v("FakeConditionChecker", sbM.toString());
                    if ("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_CONDITION".equals(action)) {
                        FakeConditionChecker.this.updateCondition(stringExtra, boolValueOf);
                    } else if ("com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_CONDITION".equals(action)) {
                        FakeConditionChecker fakeConditionChecker = FakeConditionChecker.this;
                        fakeConditionChecker.getClass();
                        FakeConditionChecker.sConditionMap.remove(stringExtra);
                        fakeConditionChecker.mLogWrapper.v("FakeConditionChecker", "removed");
                    }
                }
            };
            this.mReceiver = r0;
            context.registerReceiver(r0, intentFilterM, 2);
            logWrapper.v("FakeConditionChecker", "initialized");
        }
        sInstance = this;
    }

    public final boolean isEnabled(Object obj) {
        String string = obj.toString();
        HashMap map = sConditionMap;
        if (!map.containsKey(string)) {
            return this.mDefaultSystemCondition.isEnabled(obj);
        }
        boolean zBooleanValue = ((Boolean) map.get(string)).booleanValue();
        this.mLogWrapper.v("FakeConditionChecker", "[Fake : " + string.toLowerCase() + "] " + zBooleanValue);
        return zBooleanValue;
    }

    public final void updateCondition(String str, Boolean bool) {
        HashMap map = sConditionMap;
        if (map.containsKey(str)) {
            map.replace(str, bool);
            this.mLogWrapper.v("FakeConditionChecker", "updated");
        } else {
            map.put(str, bool);
            this.mLogWrapper.v("FakeConditionChecker", "added");
        }
    }
}
