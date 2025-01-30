package com.android.systemui.globalactions.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FakeConditionChecker implements ConditionChecker {
    public static final HashMap sConditionMap = new HashMap();
    public static FakeConditionChecker sInstance;
    public final ConditionChecker mDefaultSystemCondition;
    public final LogWrapper mLogWrapper;
    public C14381 mReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.BroadcastReceiver, com.android.systemui.globalactions.util.FakeConditionChecker$1] */
    public FakeConditionChecker(Context context, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mDefaultSystemCondition = conditionChecker;
        this.mLogWrapper = logWrapper;
        if (this.mReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.android.systemui.globalactions.ACTION_UPDATE_SYSTEM_CONDITION");
            intentFilter.addAction("com.android.systemui.globalactions.ACTION_REMOVE_SYSTEM_CONDITION");
            ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.util.FakeConditionChecker.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    String stringExtra = intent.getStringExtra("key");
                    Boolean valueOf = Boolean.valueOf(intent.getBooleanExtra("enabled", false));
                    LogWrapper logWrapper2 = FakeConditionChecker.this.mLogWrapper;
                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("onReceive: ", action, ",", stringExtra, ",");
                    m87m.append(valueOf);
                    logWrapper2.v("FakeConditionChecker", m87m.toString());
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
            context.registerReceiver(r0, intentFilter, 2);
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
