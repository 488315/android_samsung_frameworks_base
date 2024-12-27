package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        notifCollection.mNotifCollectionListeners.addIfAbsent(notifCollectionListener);
    }

    public final void addFinalizeFilter(NotifFilter notifFilter) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mNotifFinalizeFilters).add(notifFilter);
        notifFilter.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 1));
    }

    public final void addNotificationLifetimeExtender(NotifLifetimeExtender notifLifetimeExtender) {
        NotifCollection notifCollection = this.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        if (!((ArrayList) notifCollection.mLifetimeExtenders).contains(notifLifetimeExtender)) {
            ((ArrayList) notifCollection.mLifetimeExtenders).add(notifLifetimeExtender);
            notifLifetimeExtender.setCallback(new NotifCollection$$ExternalSyntheticLambda4(notifCollection));
        } else {
            throw new IllegalArgumentException("Extender " + notifLifetimeExtender + " already added.");
        }
    }

    public final void addOnAfterRenderListListener(OnAfterRenderListListener onAfterRenderListListener) {
        ((ArrayList) this.mRenderStageManager.onAfterRenderListListeners).add(onAfterRenderListListener);
    }

    public final void addOnBeforeFinalizeFilterListener(OnBeforeFinalizeFilterListener onBeforeFinalizeFilterListener) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnBeforeFinalizeFilterListeners.addIfAbsent(onBeforeFinalizeFilterListener);
    }

    public final void addOnBeforeRenderListListener(OnBeforeRenderListListener onBeforeRenderListListener) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnBeforeRenderListListeners.addIfAbsent(onBeforeRenderListListener);
    }

    public final void addPreGroupFilter(NotifFilter notifFilter) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mNotifPreGroupFilters).add(notifFilter);
        notifFilter.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 7));
    }

    public final void addPreRenderInvalidator(Invalidator invalidator) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        invalidator.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 0));
    }

    public final void addPromoter(NotifPromoter notifPromoter) {
        ShadeListBuilder shadeListBuilder = this.mShadeListBuilder;
        shadeListBuilder.getClass();
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        ((ArrayList) shadeListBuilder.mNotifPromoters).add(notifPromoter);
        notifPromoter.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(shadeListBuilder, 2));
    }

    public final Collection getAllNotifs() {
        NotifCollection notifCollection = this.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        return notifCollection.mReadOnlyNotificationSet;
    }
}
