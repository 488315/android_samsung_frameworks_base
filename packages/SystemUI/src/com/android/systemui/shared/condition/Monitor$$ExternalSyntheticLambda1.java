package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.shared.condition.Monitor;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class Monitor$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Monitor f$0;
    public final /* synthetic */ Monitor.Subscription.Token f$1;

    public /* synthetic */ Monitor$$ExternalSyntheticLambda1(Monitor monitor, Monitor.Subscription.Token token, int i) {
        this.$r8$classId = i;
        this.f$0 = monitor;
        this.f$1 = token;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Monitor monitor = this.f$0;
                Monitor.Subscription.Token token = this.f$1;
                Condition condition = (Condition) obj;
                if (!monitor.mConditions.containsKey(condition)) {
                    Log.e(monitor.mTag, "condition not present:" + condition);
                    break;
                } else {
                    Set set = (Set) monitor.mConditions.get(condition);
                    set.remove(token);
                    if (set.isEmpty()) {
                        condition.removeCallback(monitor.mConditionCallback);
                        monitor.mConditions.remove(condition);
                        break;
                    }
                }
                break;
            default:
                Monitor monitor2 = this.f$0;
                Monitor.Subscription.Token token2 = this.f$1;
                Condition condition2 = (Condition) obj;
                if (!monitor2.mConditions.containsKey(condition2)) {
                    monitor2.mConditions.put(condition2, new ArraySet());
                    condition2.addCallback(monitor2.mConditionCallback);
                }
                ((ArraySet) monitor2.mConditions.get(condition2)).add(token2);
                break;
        }
    }
}
