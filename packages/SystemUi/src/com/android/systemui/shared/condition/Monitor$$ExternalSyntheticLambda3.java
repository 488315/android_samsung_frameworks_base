package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.shared.condition.Monitor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class Monitor$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Monitor f$0;
    public final /* synthetic */ Monitor.Subscription.Token f$1;

    public /* synthetic */ Monitor$$ExternalSyntheticLambda3(Monitor monitor, Monitor.Subscription.Token token, int i) {
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
                HashMap hashMap = monitor.mConditions;
                if (!hashMap.containsKey(condition)) {
                    hashMap.put(condition, new ArraySet());
                    if (Log.isLoggable(condition.mTag, 3)) {
                        Log.d(condition.mTag, "adding callback");
                    }
                    ArrayList arrayList = condition.mCallbacks;
                    Monitor.C24781 c24781 = monitor.mConditionCallback;
                    arrayList.add(new WeakReference(c24781));
                    int i = 1;
                    if (condition.mStarted) {
                        Monitor.this.mExecutor.execute(new Monitor$$ExternalSyntheticLambda2(i, c24781, condition));
                    } else {
                        condition.start();
                        condition.mStarted = true;
                    }
                }
                ((ArraySet) hashMap.get(condition)).add(token);
                break;
            default:
                Monitor monitor2 = this.f$0;
                Monitor.Subscription.Token token2 = this.f$1;
                Condition condition2 = (Condition) obj;
                HashMap hashMap2 = monitor2.mConditions;
                if (!hashMap2.containsKey(condition2)) {
                    Log.e(monitor2.mTag, "condition not present:" + condition2);
                    break;
                } else {
                    Set set = (Set) hashMap2.get(condition2);
                    set.remove(token2);
                    if (set.isEmpty()) {
                        if (Log.isLoggable(condition2.mTag, 3)) {
                            Log.d(condition2.mTag, "removing callback");
                        }
                        ArrayList arrayList2 = condition2.mCallbacks;
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            Monitor.C24781 c247812 = (Monitor.C24781) ((WeakReference) it.next()).get();
                            if (c247812 == null || c247812 == monitor2.mConditionCallback) {
                                it.remove();
                            }
                        }
                        if (arrayList2.isEmpty() && condition2.mStarted) {
                            condition2.stop();
                            condition2.mStarted = false;
                        }
                        hashMap2.remove(condition2);
                        break;
                    }
                }
                break;
        }
    }
}
