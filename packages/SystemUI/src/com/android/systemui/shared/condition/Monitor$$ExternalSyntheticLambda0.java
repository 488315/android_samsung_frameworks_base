package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.shared.condition.Monitor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class Monitor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ Monitor$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Monitor monitor = (Monitor) this.f$0;
                Monitor.Subscription.Token token = (Monitor.Subscription.Token) this.f$1;
                boolean isLoggable = Log.isLoggable(monitor.mTag, 3);
                String str = monitor.mTag;
                if (isLoggable) {
                    Log.d(str, "removing subscription");
                }
                if (!monitor.mSubscriptions.containsKey(token)) {
                    Log.e(str, "subscription not present:" + token);
                    break;
                } else {
                    Monitor.SubscriptionState subscriptionState = (Monitor.SubscriptionState) monitor.mSubscriptions.remove(token);
                    subscriptionState.mSubscription.mConditions.forEach(new Monitor$$ExternalSyntheticLambda2(monitor, token, 0));
                    if (subscriptionState.mActive) {
                        subscriptionState.mActive = false;
                        Monitor.Callback callback = subscriptionState.mSubscription.mCallback;
                    }
                    Monitor.Subscription.Token token2 = subscriptionState.mNestedSubscriptionToken;
                    if (token2 != null) {
                        monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(0, monitor, token2));
                        subscriptionState.mNestedSubscriptionToken = null;
                        break;
                    }
                }
                break;
            default:
                Monitor.AnonymousClass1 anonymousClass1 = (Monitor.AnonymousClass1) this.f$0;
                Condition condition = (Condition) this.f$1;
                final Monitor monitor2 = anonymousClass1.this$0;
                TableLogBufferBase tableLogBufferBase = monitor2.mLogBuffer;
                if (tableLogBufferBase != null) {
                    String str2 = condition.mTag;
                    if (condition.mOverriding) {
                        str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "[OVRD]");
                    }
                    Boolean bool = condition.mIsConditionMet;
                    tableLogBufferBase.logChange("", str2, bool != null ? Boolean.TRUE.equals(bool) ? "True" : "False" : "Invalid");
                }
                ArraySet arraySet = (ArraySet) monitor2.mConditions.get(condition);
                if (arraySet != null) {
                    arraySet.stream().forEach(new Consumer() { // from class: com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Monitor monitor3 = Monitor.this;
                            ((Monitor.SubscriptionState) monitor3.mSubscriptions.get((Monitor.Subscription.Token) obj)).update(monitor3);
                        }
                    });
                    break;
                }
                break;
        }
    }
}
