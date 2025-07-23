package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifBundler;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class BundleCoordinator implements Coordinator {
    private final NodeController newsHeaderController;
    private final NodeController promoHeaderController;
    private final NodeController recsHeaderController;
    private final NodeController socialHeaderController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    public static boolean debugBundleUi = true;
    private final NotifSectioner newsSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BundleCoordinator$newsSectioner$1
        {
            super("News", 16);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            NodeController nodeController;
            nodeController = BundleCoordinator.this.newsHeaderController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationChannel channel;
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            return Intrinsics.areEqual((representativeEntry == null || (channel = representativeEntry.mRanking.getChannel()) == null) ? null : channel.getId(), "android.app.news");
        }
    };
    private final NotifSectioner socialSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BundleCoordinator$socialSectioner$1
        {
            super("Social", 17);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            NodeController nodeController;
            nodeController = BundleCoordinator.this.socialHeaderController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationChannel channel;
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            return Intrinsics.areEqual((representativeEntry == null || (channel = representativeEntry.mRanking.getChannel()) == null) ? null : channel.getId(), "android.app.social");
        }
    };
    private final NotifSectioner recsSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BundleCoordinator$recsSectioner$1
        {
            super("Recommendations", 18);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            NodeController nodeController;
            nodeController = BundleCoordinator.this.recsHeaderController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationChannel channel;
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            return Intrinsics.areEqual((representativeEntry == null || (channel = representativeEntry.mRanking.getChannel()) == null) ? null : channel.getId(), "android.app.recs");
        }
    };
    private final NotifSectioner promoSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BundleCoordinator$promoSectioner$1
        {
            super("Promotions", 19);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            NodeController nodeController;
            nodeController = BundleCoordinator.this.promoHeaderController;
            return nodeController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationChannel channel;
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            return Intrinsics.areEqual((representativeEntry == null || (channel = representativeEntry.mRanking.getChannel()) == null) ? null : channel.getId(), "android.app.promotions");
        }
    };
    private final NotifBundler bundler = new NotifBundler() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BundleCoordinator$bundler$1
        private final List<String> bundleIds;

        {
            this.bundleIds = BundleCoordinator.debugBundleUi ? CollectionsKt___CollectionsKt.plus(NotificationChannel.SYSTEM_RESERVED_IDS, "notify") : NotificationChannel.SYSTEM_RESERVED_IDS;
        }

        public String getBundleIdOrNull(NotificationEntry notificationEntry) {
            NotificationChannel channel;
            String id;
            String str;
            if (BundleCoordinator.debugBundleUi && notificationEntry != null && (str = notificationEntry.mKey) != null && StringsKt__StringsKt.contains(str, "notify", false)) {
                return "notify";
            }
            if (notificationEntry == null || (channel = notificationEntry.mRanking.getChannel()) == null || (id = channel.getId()) == null || !getBundleIds().contains(id)) {
                return null;
            }
            return id;
        }

        public List<String> getBundleIds() {
            return this.bundleIds;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public BundleCoordinator(NodeController nodeController, NodeController nodeController2, NodeController nodeController3, NodeController nodeController4) {
        this.newsHeaderController = nodeController;
        this.socialHeaderController = nodeController2;
        this.recsHeaderController = nodeController3;
        this.promoHeaderController = nodeController4;
    }

    public final NotifBundler getBundler() {
        return this.bundler;
    }

    public final NotifSectioner getNewsSectioner() {
        return this.newsSectioner;
    }

    public final NotifSectioner getPromoSectioner() {
        return this.promoSectioner;
    }

    public final NotifSectioner getRecsSectioner() {
        return this.recsSectioner;
    }

    public final NotifSectioner getSocialSectioner() {
        return this.socialSectioner;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
    }
}
