package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.shared.condition.Condition;
import com.android.systemui.shared.condition.Monitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: classes3.dex */
public class Monitor {
    public final AnonymousClass1 mConditionCallback;
    public final HashMap mConditions;
    public final Executor mExecutor;
    public final TableLogBufferBase mLogBuffer;
    public final Set mPreconditions;
    public final HashMap mSubscriptions;
    public final String mTag;

    /* renamed from: com.android.systemui.shared.condition.Monitor$1, reason: invalid class name */
    public class AnonymousClass1 implements Condition.Callback {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.shared.condition.Condition.Callback
        public final void onConditionChanged(Condition condition) {
            Monitor.this.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(1, this, condition));
        }
    }

    public interface Callback {
        void onConditionsChanged(boolean z);
    }

    public class Subscription {
        public final Callback mCallback;
        public final Set mConditions;
        public final Subscription mNestedSubscription;

        public class Builder {
            public final Callback mCallback;
            public final ArraySet mConditions;
            public final Subscription mNestedSubscription;

            public Builder(Callback callback) {
                this(null, callback);
            }

            public final Subscription build() {
                return new Subscription(this.mConditions, this.mCallback, this.mNestedSubscription, 0);
            }

            public Builder(Subscription subscription) {
                this(subscription, null);
            }

            private Builder(Subscription subscription, Callback callback) {
                this.mNestedSubscription = subscription;
                this.mCallback = callback;
                this.mConditions = new ArraySet();
            }
        }

        public class Token {
        }

        public /* synthetic */ Subscription(Set set, Callback callback, Subscription subscription, int i) {
            this(set, callback, subscription);
        }

        private Subscription(Set<Condition> set, Callback callback, Subscription subscription) {
            this.mConditions = Collections.unmodifiableSet(set);
            this.mCallback = callback;
            this.mNestedSubscription = subscription;
        }
    }

    public class SubscriptionState {
        public boolean mActive;
        public Boolean mAllConditionsMet;
        public Subscription.Token mNestedSubscriptionToken;
        public final Subscription mSubscription;

        public SubscriptionState(Subscription subscription) {
            this.mSubscription = subscription;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:4:0x0011  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void update(Monitor monitor) {
            Boolean boolThreeValuedAndOrOr;
            Subscription.Token token;
            Evaluator evaluator = Evaluator.INSTANCE;
            Subscription subscription = this.mSubscription;
            Set set = subscription.mConditions;
            evaluator.getClass();
            if (set.isEmpty()) {
                boolThreeValuedAndOrOr = null;
            } else {
                ArrayList arrayList = new ArrayList();
                for (Object obj : set) {
                    Condition condition = (Condition) obj;
                    if (condition._isConditionMet != null && condition.isOverridingCondition) {
                        arrayList.add(obj);
                    }
                }
                if (!arrayList.isEmpty()) {
                    set = arrayList;
                }
                Set set2 = set;
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
                Iterator it = set2.iterator();
                while (it.hasNext()) {
                    Boolean bool = ((Condition) it.next())._isConditionMet;
                    arrayList2.add(bool != null ? Boolean.valueOf(Boolean.TRUE.equals(bool)) : null);
                }
                if (!arrayList2.isEmpty()) {
                    boolThreeValuedAndOrOr = Evaluator.threeValuedAndOrOr(arrayList2, false);
                }
            }
            boolean z = boolThreeValuedAndOrOr == null || boolThreeValuedAndOrOr.booleanValue();
            Boolean bool2 = this.mAllConditionsMet;
            if (bool2 == null || z != bool2.booleanValue()) {
                Boolean boolValueOf = Boolean.valueOf(z);
                this.mAllConditionsMet = boolValueOf;
                Subscription subscription2 = subscription.mNestedSubscription;
                if (subscription2 == null) {
                    subscription.mCallback.onConditionsChanged(z);
                    return;
                }
                if (z && this.mNestedSubscriptionToken == null) {
                    this.mNestedSubscriptionToken = monitor.addSubscription(subscription2, null);
                } else {
                    if (boolValueOf.booleanValue() || (token = this.mNestedSubscriptionToken) == null || token == null) {
                        return;
                    }
                    monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(0, monitor, token));
                    this.mNestedSubscriptionToken = null;
                }
            }
        }
    }

    public Monitor(Executor executor) {
        this(executor, Collections.EMPTY_SET);
    }

    public final Subscription.Token addSubscription(final Subscription subscription, Set set) {
        if (set != null) {
            Subscription.Builder builder = new Subscription.Builder(subscription);
            builder.mConditions.addAll(set);
            subscription = builder.build();
        }
        final Subscription.Token token = new Subscription.Token();
        final SubscriptionState subscriptionState = new SubscriptionState(subscription);
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                Monitor monitor = this.f$0;
                Monitor.Subscription.Token token2 = token;
                Monitor.SubscriptionState subscriptionState2 = subscriptionState;
                Monitor.Subscription subscription2 = subscription;
                if (Log.isLoggable(monitor.mTag, 3)) {
                    Log.d(monitor.mTag, "adding subscription");
                }
                monitor.mSubscriptions.put(token2, subscriptionState2);
                subscription2.mConditions.forEach(new Monitor$$ExternalSyntheticLambda1(monitor, token2, 1));
                if (!subscriptionState2.mActive) {
                    subscriptionState2.mActive = true;
                    Monitor.Callback callback = subscriptionState2.mSubscription.mCallback;
                }
                subscriptionState2.update(monitor);
            }
        });
        return token;
    }

    public Monitor(Executor executor, Set<Condition> set) {
        this(executor, set, null);
    }

    public Monitor(Executor executor, Set<Condition> set, TableLogBufferBase tableLogBufferBase) {
        this.mTag = getClass().getSimpleName();
        this.mConditions = new HashMap();
        this.mSubscriptions = new HashMap();
        this.mConditionCallback = new AnonymousClass1();
        this.mExecutor = executor;
        this.mPreconditions = set;
        this.mLogBuffer = tableLogBufferBase;
    }
}
