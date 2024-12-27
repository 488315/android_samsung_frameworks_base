package com.android.internal.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Message;
import android.os.UserHandle;

import com.android.internal.app.chooser.TargetInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

/* loaded from: classes5.dex */
public class NoOpResolverComparator extends AbstractResolverComparator {
    private List<ResolveInfo> mOriginalTargetOrder;

    public NoOpResolverComparator(
            Context launchedFromContext, Intent intent, List<UserHandle> targetUserSpaceList) {
        super(launchedFromContext, intent, targetUserSpaceList);
        this.mOriginalTargetOrder = null;
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void doCompute(List<ResolverActivity.ResolvedComponentInfo> targets) {
        this.mOriginalTargetOrder = new ArrayList();
        for (ResolverActivity.ResolvedComponentInfo target : targets) {
            this.mOriginalTargetOrder.add(target.getResolveInfoAt(0));
        }
        afterCompute();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public int compare(ResolveInfo lhs, ResolveInfo rhs) {
        Comparator<ResolveInfo> c =
                Comparator.comparingDouble(
                        new ToDoubleFunction() { // from class:
                                                 // com.android.internal.app.NoOpResolverComparator$$ExternalSyntheticLambda0
                            @Override // java.util.function.ToDoubleFunction
                            public final double applyAsDouble(Object obj) {
                                double lambda$compare$0;
                                lambda$compare$0 =
                                        NoOpResolverComparator.this.lambda$compare$0(
                                                (ResolveInfo) obj);
                                return lambda$compare$0;
                            }
                        });
        return c.reversed().compare(lhs, rhs);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public float getScore(TargetInfo targetInfo) {
        return lambda$compare$0(targetInfo.getResolveInfo());
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void handleResultMessage(Message message) {}

    /* renamed from: getScore, reason: merged with bridge method [inline-methods] */
    public float lambda$compare$0(ResolveInfo resolveInfo) {
        if (!this.mOriginalTargetOrder.contains(resolveInfo)) {
            return 0.0f;
        }
        float rank = this.mOriginalTargetOrder.indexOf(resolveInfo);
        return 1.0f - (rank / (this.mOriginalTargetOrder.size() + 1));
    }
}
