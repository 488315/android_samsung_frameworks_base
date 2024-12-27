package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.shared.condition.Monitor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executor;

public final class Monitor {
    public final AnonymousClass1 mConditionCallback;
    public final HashMap mConditions;
    public final Executor mExecutor;
    public final TableLogBufferBase mLogBuffer;
    public final Set mPreconditions;
    public final HashMap mSubscriptions;
    public final String mTag;

    /* renamed from: com.android.systemui.shared.condition.Monitor$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public interface Callback {
        void onConditionsChanged(boolean z);
    }

    public final class Subscription {
        public final Callback mCallback;
        public final Set mConditions;
        public final Subscription mNestedSubscription;

        public final class Builder {
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

        public final class Token {
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

    public final class SubscriptionState {
        public boolean mActive;
        public Boolean mAllConditionsMet;
        public Subscription.Token mNestedSubscriptionToken;
        public final Subscription mSubscription;

        public SubscriptionState(Subscription subscription) {
            this.mSubscription = subscription;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void update(com.android.systemui.shared.condition.Monitor r11) {
            /*
                Method dump skipped, instructions count: 237
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.condition.Monitor.SubscriptionState.update(com.android.systemui.shared.condition.Monitor):void");
        }
    }

    public Monitor(Executor executor) {
        this(executor, Collections.emptySet());
    }

    public final Subscription.Token addSubscription(final Subscription subscription, Set set) {
        if (set != null) {
            Subscription.Builder builder = new Subscription.Builder(subscription);
            builder.mConditions.addAll(set);
            subscription = builder.build();
        }
        final Subscription.Token token = new Subscription.Token();
        final SubscriptionState subscriptionState = new SubscriptionState(subscription);
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Monitor monitor = Monitor.this;
                Monitor.Subscription.Token token2 = token;
                Monitor.SubscriptionState subscriptionState2 = subscriptionState;
                Monitor.Subscription subscription2 = subscription;
                if (Log.isLoggable(monitor.mTag, 3)) {
                    Log.d(monitor.mTag, "adding subscription");
                }
                monitor.mSubscriptions.put(token2, subscriptionState2);
                subscription2.mConditions.forEach(new Monitor$$ExternalSyntheticLambda2(monitor, token2, 1));
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
        this.mTag = "Monitor";
        this.mConditions = new HashMap();
        this.mSubscriptions = new HashMap();
        this.mConditionCallback = new AnonymousClass1();
        this.mExecutor = executor;
        this.mPreconditions = set;
        this.mLogBuffer = tableLogBufferBase;
    }
}
