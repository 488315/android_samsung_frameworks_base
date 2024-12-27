package com.android.systemui.util.condition;

import com.android.systemui.CoreStartable;
import com.android.systemui.shared.condition.Condition;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.shared.condition.Monitor$$ExternalSyntheticLambda0;
import java.io.PrintWriter;
import java.util.Set;

public abstract class ConditionalCoreStartable implements CoreStartable {
    private Monitor.Subscription.Token mBootCompletedToken;
    private final Set<Condition> mConditionSet;
    private final Monitor mMonitor;
    private Monitor.Subscription.Token mStartToken;

    public ConditionalCoreStartable(Monitor monitor) {
        this(monitor, null);
    }

    public void lambda$onBootCompleted$1(boolean z) {
        if (z) {
            Monitor monitor = this.mMonitor;
            monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(0, monitor, this.mBootCompletedToken));
            this.mBootCompletedToken = null;
            bootCompleted();
        }
    }

    public void lambda$start$0(boolean z) {
        if (z) {
            Monitor monitor = this.mMonitor;
            monitor.mExecutor.execute(new Monitor$$ExternalSyntheticLambda0(0, monitor, this.mStartToken));
            this.mStartToken = null;
            onStart();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ boolean isDumpCritical() {
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        Monitor monitor = this.mMonitor;
        Monitor.Subscription.Builder builder = new Monitor.Subscription.Builder(new ConditionalCoreStartable$$ExternalSyntheticLambda0(this, 0));
        Set<Condition> set = this.mConditionSet;
        if (set != null) {
            builder.mConditions.addAll(set);
        }
        this.mBootCompletedToken = monitor.addSubscription(builder.build(), monitor.mPreconditions);
    }

    public abstract void onStart();

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Monitor monitor = this.mMonitor;
        Monitor.Subscription.Builder builder = new Monitor.Subscription.Builder(new ConditionalCoreStartable$$ExternalSyntheticLambda0(this, 1));
        Set<Condition> set = this.mConditionSet;
        if (set != null) {
            builder.mConditions.addAll(set);
        }
        this.mStartToken = monitor.addSubscription(builder.build(), monitor.mPreconditions);
    }

    public ConditionalCoreStartable(Monitor monitor, Set<Condition> set) {
        this.mMonitor = monitor;
        this.mConditionSet = set;
    }

    public void bootCompleted() {
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public /* bridge */ /* synthetic */ void dump(PrintWriter printWriter, String[] strArr) {
    }
}
