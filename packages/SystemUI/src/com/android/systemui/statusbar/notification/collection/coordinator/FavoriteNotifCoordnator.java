package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import java.util.Collection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class FavoriteNotifCoordnator extends Invalidator implements Coordinator {
    public static final int $stable = 8;
    private final Context context;
    private final NodeController favoriteHeaderController;
    private final String favoritePkgList;
    private final FavoriteNotifCoordnator$mFavoriteNotifSectioner$1 mFavoriteNotifSectioner;
    private final NotifLiveDataStoreImpl notifLiveDataStoreImpl;
    private final SharedPreferences sp;
    private final String spName;
    private final NotifTimeSortCoordnator timeSortCoordnator;
    private final VisualStabilityCoordinator visualStabilityCoordinator;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.FavoriteNotifCoordnator$mFavoriteNotifSectioner$1] */
    public FavoriteNotifCoordnator(Context context, VisualStabilityCoordinator visualStabilityCoordinator, NotifLiveDataStoreImpl notifLiveDataStoreImpl, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController) {
        super("FavoriteNotifCoordinator");
        this.context = context;
        this.visualStabilityCoordinator = visualStabilityCoordinator;
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
        this.timeSortCoordnator = notifTimeSortCoordnator;
        this.favoriteHeaderController = nodeController;
        this.spName = "favorite_notif";
        this.favoritePkgList = "favorite_list";
        this.sp = context.getSharedPreferences("favorite_notif", 0);
        this.mFavoriteNotifSectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.FavoriteNotifCoordnator$mFavoriteNotifSectioner$1
            {
                super("Favorite", 2);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NotifComparator getComparator() {
                return FavoriteNotifCoordnator.this.getTimeSortCoordnator().getTimeComparator();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return FavoriteNotifCoordnator.this.getFavoriteHeaderController();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                return false;
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        ((NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class)).mInvalidateListener = this;
    }

    public final Context getContext() {
        return this.context;
    }

    public final NodeController getFavoriteHeaderController() {
        return this.favoriteHeaderController;
    }

    public final String getFavoritePkgList() {
        return this.favoritePkgList;
    }

    public final NotifSectioner getFavoriteSectioner() {
        return this.mFavoriteNotifSectioner;
    }

    public final NotifLiveDataStoreImpl getNotifLiveDataStoreImpl() {
        return this.notifLiveDataStoreImpl;
    }

    public final SharedPreferences getSp() {
        return this.sp;
    }

    public final String getSpName() {
        return this.spName;
    }

    public final NotifTimeSortCoordnator getTimeSortCoordnator() {
        return this.timeSortCoordnator;
    }

    public final VisualStabilityCoordinator getVisualStabilityCoordinator() {
        return this.visualStabilityCoordinator;
    }

    public void onUpdateNotifStack() {
        this.visualStabilityCoordinator.temporarilyAllowSectionChanges((Collection<NotificationEntry>) this.notifLiveDataStoreImpl.activeNotifList.atomicValue.get(), SystemClock.uptimeMillis());
    }
}
