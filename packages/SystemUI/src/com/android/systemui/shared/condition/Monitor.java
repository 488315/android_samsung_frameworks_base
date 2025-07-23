package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.shared.condition.Condition;
import com.android.systemui.shared.condition.Monitor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Monitor {
    public final AnonymousClass1 mConditionCallback;
    public final HashMap mConditions;
    public final Executor mExecutor;
    public final TableLogBufferBase mLogBuffer;
    public final Set mPreconditions;
    public final HashMap mSubscriptions;
    public final String mTag;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shared.condition.Monitor$1, reason: invalid class name */
    public class AnonymousClass1 implements Condition.Callback {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.shared.condition.Condition.Callback
        public final void onConditionChanged(Condition condition) {
            Monitor.this.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(1, this, condition));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onConditionsChanged(boolean z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Subscription {
        public final Callback mCallback;
        public final Set mConditions;
        public final Subscription mNestedSubscription;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SubscriptionState {
        public boolean mActive;
        public Boolean mAllConditionsMet;
        public Subscription.Token mNestedSubscriptionToken;
        public final Subscription mSubscription;

        public SubscriptionState(Subscription subscription) {
            this.mSubscription = subscription;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:15:0x009b  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00c3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void update(com.android.systemui.shared.condition.Monitor r10) {
            /*
                r9 = this;
                com.android.systemui.shared.condition.Evaluator r0 = com.android.systemui.shared.condition.Evaluator.INSTANCE
                com.android.systemui.shared.condition.Monitor$Subscription r1 = r9.mSubscription
                java.util.Set r2 = r1.mConditions
                r0.getClass()
                boolean r0 = r2.isEmpty()
                r3 = 0
                r4 = 0
                if (r0 == 0) goto L14
            L11:
                r0 = r4
                goto L7d
            L14:
                r0 = r2
                java.lang.Iterable r0 = (java.lang.Iterable) r0
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>()
                java.util.Iterator r0 = r0.iterator()
            L20:
                boolean r6 = r0.hasNext()
                if (r6 == 0) goto L39
                java.lang.Object r6 = r0.next()
                r7 = r6
                com.android.systemui.shared.condition.Condition r7 = (com.android.systemui.shared.condition.Condition) r7
                java.lang.Boolean r8 = r7._isConditionMet
                if (r8 == 0) goto L20
                boolean r7 = r7.isOverridingCondition
                if (r7 == 0) goto L20
                r5.add(r6)
                goto L20
            L39:
                boolean r0 = r5.isEmpty()
                if (r0 == 0) goto L40
                goto L41
            L40:
                r2 = r5
            L41:
                java.lang.Iterable r2 = (java.lang.Iterable) r2
                java.util.ArrayList r0 = new java.util.ArrayList
                r5 = 10
                int r5 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r2, r5)
                r0.<init>(r5)
                java.util.Iterator r2 = r2.iterator()
            L52:
                boolean r5 = r2.hasNext()
                if (r5 == 0) goto L72
                java.lang.Object r5 = r2.next()
                com.android.systemui.shared.condition.Condition r5 = (com.android.systemui.shared.condition.Condition) r5
                java.lang.Boolean r5 = r5._isConditionMet
                if (r5 == 0) goto L6d
                java.lang.Boolean r6 = java.lang.Boolean.TRUE
                boolean r5 = r6.equals(r5)
                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                goto L6e
            L6d:
                r5 = r4
            L6e:
                r0.add(r5)
                goto L52
            L72:
                boolean r2 = r0.isEmpty()
                if (r2 == 0) goto L79
                goto L11
            L79:
                java.lang.Boolean r0 = com.android.systemui.shared.condition.Evaluator.threeValuedAndOrOr(r0, r3)
            L7d:
                if (r0 == 0) goto L85
                boolean r0 = r0.booleanValue()
                if (r0 == 0) goto L86
            L85:
                r3 = 1
            L86:
                java.lang.Boolean r0 = r9.mAllConditionsMet
                if (r0 == 0) goto L91
                boolean r0 = r0.booleanValue()
                if (r3 != r0) goto L91
                goto Lc2
            L91:
                java.lang.Boolean r0 = java.lang.Boolean.valueOf(r3)
                r9.mAllConditionsMet = r0
                com.android.systemui.shared.condition.Monitor$Subscription r2 = r1.mNestedSubscription
                if (r2 == 0) goto Lc3
                if (r3 == 0) goto La8
                com.android.systemui.shared.condition.Monitor$Subscription$Token r1 = r9.mNestedSubscriptionToken
                if (r1 != 0) goto La8
                com.android.systemui.shared.condition.Monitor$Subscription$Token r10 = r10.addSubscription(r2, r4)
                r9.mNestedSubscriptionToken = r10
                return
            La8:
                boolean r0 = r0.booleanValue()
                if (r0 != 0) goto Lc2
                com.android.systemui.shared.condition.Monitor$Subscription$Token r0 = r9.mNestedSubscriptionToken
                if (r0 == 0) goto Lc2
                if (r0 != 0) goto Lb5
                goto Lc2
            Lb5:
                java.util.concurrent.Executor r1 = r10.mExecutor
                com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda0 r2 = new com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda0
                r3 = 0
                r2.<init>(r3, r10, r0)
                r1.execute(r2)
                r9.mNestedSubscriptionToken = r4
            Lc2:
                return
            Lc3:
                com.android.systemui.shared.condition.Monitor$Callback r9 = r1.mCallback
                r9.onConditionsChanged(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.condition.Monitor.SubscriptionState.update(com.android.systemui.shared.condition.Monitor):void");
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
                Monitor monitor = Monitor.this;
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
