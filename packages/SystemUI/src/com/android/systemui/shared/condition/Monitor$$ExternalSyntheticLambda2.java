package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.shared.condition.Monitor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public final /* synthetic */ class Monitor$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Monitor f$0;
    public final /* synthetic */ Monitor.Subscription.Token f$1;

    public /* synthetic */ Monitor$$ExternalSyntheticLambda2(Monitor monitor, Monitor.Subscription.Token token, int i) {
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
                        if (Log.isLoggable(condition.mTag, 3)) {
                            Log.d(condition.mTag, "removing callback");
                        }
                        Iterator it = condition.mCallbacks.iterator();
                        while (it.hasNext()) {
                            Monitor.AnonymousClass1 anonymousClass1 = (Monitor.AnonymousClass1) ((WeakReference) it.next()).get();
                            if (anonymousClass1 == null || anonymousClass1 == monitor.mConditionCallback) {
                                it.remove();
                            }
                        }
                        if (condition.mCallbacks.isEmpty() && condition.mStarted) {
                            condition.stop();
                            condition.mStarted = false;
                        }
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
                    if (Log.isLoggable(condition2.mTag, 3)) {
                        Log.d(condition2.mTag, "adding callback");
                    }
                    ArrayList arrayList = condition2.mCallbacks;
                    Monitor.AnonymousClass1 anonymousClass12 = monitor2.mConditionCallback;
                    arrayList.add(new WeakReference(anonymousClass12));
                    if (condition2.mStarted) {
                        Monitor.this.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(1, anonymousClass12, condition2));
                    } else {
                        condition2.start();
                        condition2.mStarted = true;
                    }
                }
                ((ArraySet) monitor2.mConditions.get(condition2)).add(token2);
                break;
        }
    }
}
