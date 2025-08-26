package com.android.internal.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Message;
import android.os.UserHandle;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToDoubleFunction;

/* loaded from: classes5.dex */
public class NoOpResolverComparator extends AbstractResolverComparator {
    private List<ResolveInfo> mOriginalTargetOrder;

    @Override // com.android.internal.app.AbstractResolverComparator
    public void handleResultMessage(Message message) {
    }

    public NoOpResolverComparator(Context context, Intent intent, List<UserHandle> list) {
        super(context, intent, list);
        this.mOriginalTargetOrder = null;
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public void doCompute(List<ResolverActivity.ResolvedComponentInfo> list) {
        this.mOriginalTargetOrder = new ArrayList();
        Iterator<ResolverActivity.ResolvedComponentInfo> it = list.iterator();
        while (it.hasNext()) {
            this.mOriginalTargetOrder.add(it.next().getResolveInfoAt(0));
        }
        afterCompute();
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        return Comparator.comparingDouble(new ToDoubleFunction() { // from class: com.android.internal.app.NoOpResolverComparator$$ExternalSyntheticLambda0
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return this.f$0.lambda$compare$0((ResolveInfo) obj);
            }
        }).reversed().compare(resolveInfo, resolveInfo2);
    }

    @Override // com.android.internal.app.AbstractResolverComparator
    public float getScore(TargetInfo targetInfo) {
        return lambda$compare$0(targetInfo.getResolveInfo());
    }

    /* renamed from: getScore, reason: merged with bridge method [inline-methods] */
    public float lambda$compare$0(ResolveInfo resolveInfo) {
        if (this.mOriginalTargetOrder.contains(resolveInfo)) {
            return 1.0f - (this.mOriginalTargetOrder.indexOf(resolveInfo) / (this.mOriginalTargetOrder.size() + 1));
        }
        return 0.0f;
    }
}
