package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifPipeline implements CommonNotifCollection {
    public final NotifCollection mNotifCollection;
    public final RenderStageManager mRenderStageManager;
    public final ShadeListBuilder mShadeListBuilder;

    public NotifPipeline(NotifCollection notifCollection, ShadeListBuilder shadeListBuilder, RenderStageManager renderStageManager) {
        this.mNotifCollection = notifCollection;
        this.mShadeListBuilder = shadeListBuilder;
        this.mRenderStageManager = renderStageManager;
    }

    public final void addCollectionListener(NotifCollectionListener notifCollectionListener) {
        NotifCollection notifCollection = this.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        ((ArrayList) notifCollection.mNotifCollectionListeners).add(notifCollectionListener);
    }

    public final void addFinalizeFilter(NotifFilter notifFilter) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mNotifFinalizeFilters).add(notifFilter);
        notifFilter.mListener = new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 4);
    }

    public final void addNotificationLifetimeExtender(NotifLifetimeExtender notifLifetimeExtender) {
        NotifCollection notifCollection = this.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        ArrayList arrayList = (ArrayList) notifCollection.mLifetimeExtenders;
        if (!arrayList.contains(notifLifetimeExtender)) {
            arrayList.add(notifLifetimeExtender);
            notifLifetimeExtender.setCallback(new NotifCollection$$ExternalSyntheticLambda8(notifCollection));
        } else {
            throw new IllegalArgumentException("Extender " + notifLifetimeExtender + " already added.");
        }
    }

    public final void addOnBeforeFinalizeFilterListener(OnBeforeFinalizeFilterListener onBeforeFinalizeFilterListener) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mOnBeforeFinalizeFilterListeners).add(onBeforeFinalizeFilterListener);
    }

    public final void addOnBeforeRenderListListener(OnBeforeRenderListListener onBeforeRenderListListener) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mOnBeforeRenderListListeners).add(onBeforeRenderListListener);
    }

    public final void addPreGroupFilter(NotifFilter notifFilter) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mNotifPreGroupFilters).add(notifFilter);
        notifFilter.mListener = new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 7);
    }

    public final void addPreRenderInvalidator(Invalidator invalidator) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        invalidator.mListener = new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 5);
    }

    public final void addPromoter(NotifPromoter notifPromoter) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mNotifPromoters).add(notifPromoter);
        notifPromoter.mListener = new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 6);
    }

    public final Collection getAllNotifs() {
        NotifCollection notifCollection = this.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        return notifCollection.mReadOnlyNotificationSet;
    }

    public final NotificationEntry getEntry(String str) {
        return this.mNotifCollection.getEntry(str);
    }
}
