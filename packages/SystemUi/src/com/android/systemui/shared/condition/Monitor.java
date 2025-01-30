package com.android.systemui.shared.condition;

import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.shared.condition.Monitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Monitor {
    public final C24781 mConditionCallback;
    public final HashMap mConditions;
    public final Executor mExecutor;
    public final TableLogBufferBase mLogBuffer;
    public final Set mPreconditions;
    public final HashMap mSubscriptions;
    public final String mTag;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shared.condition.Monitor$1 */
    public final class C24781 {
        public C24781() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onConditionsChanged(boolean z);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Subscription {
        public final Callback mCallback;
        public final Set mConditions;
        public final Subscription mNestedSubscription;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Builder {
            public final Callback mCallback;
            public final ArraySet mConditions;
            public final Subscription mNestedSubscription;
            public final ArraySet mPreconditions;

            public Builder(Callback callback) {
                this(null, callback);
            }

            public final Subscription build() {
                int i = 0;
                Subscription subscription = new Subscription(this.mConditions, this.mCallback, this.mNestedSubscription, i);
                ArraySet arraySet = this.mPreconditions;
                return !arraySet.isEmpty() ? new Subscription(arraySet, null, subscription, i) : subscription;
            }

            public Builder(Subscription subscription) {
                this(subscription, null);
            }

            private Builder(Subscription subscription, Callback callback) {
                this.mNestedSubscription = subscription;
                this.mCallback = callback;
                this.mConditions = new ArraySet();
                this.mPreconditions = new ArraySet();
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubscriptionState {
        public boolean mActive;
        public Boolean mAllConditionsMet;
        public Subscription.Token mNestedSubscriptionToken;
        public final Subscription mSubscription;

        public SubscriptionState(Subscription subscription) {
            this.mSubscription = subscription;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:78:0x00f6  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void update(Monitor monitor) {
            Boolean bool;
            Boolean bool2;
            Subscription subscription;
            Subscription.Token token;
            Evaluator evaluator = Evaluator.INSTANCE;
            Subscription subscription2 = this.mSubscription;
            Set set = subscription2.mConditions;
            evaluator.getClass();
            boolean z = true;
            if (!set.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : set) {
                    Condition condition = (Condition) obj;
                    if ((condition.mIsConditionMet != null) && condition.mOverriding) {
                        arrayList.add(obj);
                    }
                }
                if (!arrayList.isEmpty()) {
                    set = arrayList;
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Boolean bool3 = ((Condition) it.next()).mIsConditionMet;
                    arrayList2.add(bool3 != null ? Boolean.valueOf(Boolean.TRUE.equals(bool3)) : null);
                }
                if (!arrayList2.isEmpty()) {
                    Iterator it2 = arrayList2.iterator();
                    boolean z2 = false;
                    while (true) {
                        if (it2.hasNext()) {
                            Boolean bool4 = (Boolean) it2.next();
                            if (bool4 != null) {
                                bool = Boolean.FALSE;
                                if (Intrinsics.areEqual(bool4, bool)) {
                                    break;
                                }
                            } else {
                                z2 = true;
                            }
                        } else if (!z2) {
                            bool = true;
                        }
                    }
                    if (bool != null && !bool.booleanValue()) {
                        z = false;
                    }
                    bool2 = this.mAllConditionsMet;
                    if (bool2 == null && z == bool2.booleanValue()) {
                        return;
                    }
                    Boolean valueOf = Boolean.valueOf(z);
                    this.mAllConditionsMet = valueOf;
                    subscription = subscription2.mNestedSubscription;
                    if (subscription != null) {
                        subscription2.mCallback.onConditionsChanged(valueOf.booleanValue());
                        return;
                    }
                    if (valueOf.booleanValue() && this.mNestedSubscriptionToken == null) {
                        this.mNestedSubscriptionToken = monitor.addSubscription(subscription, null);
                        return;
                    } else {
                        if (this.mAllConditionsMet.booleanValue() || (token = this.mNestedSubscriptionToken) == null || token == null) {
                            return;
                        }
                        monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda2(0, monitor, token));
                        this.mNestedSubscriptionToken = null;
                        return;
                    }
                }
            }
            bool = null;
            if (bool != null) {
                z = false;
            }
            bool2 = this.mAllConditionsMet;
            if (bool2 == null) {
            }
            Boolean valueOf2 = Boolean.valueOf(z);
            this.mAllConditionsMet = valueOf2;
            subscription = subscription2.mNestedSubscription;
            if (subscription != null) {
            }
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
                subscription2.mConditions.stream().forEach(new Monitor$$ExternalSyntheticLambda3(monitor, token2, 0));
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
        this.mConditionCallback = new C24781();
        this.mExecutor = executor;
        this.mPreconditions = set;
        this.mLogBuffer = tableLogBufferBase;
    }
}
