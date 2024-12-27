package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SingleShadeLockScreenOverScroller {
    public final Context context;
    public float expansionDragDownAmount;
    public int maxOverScrollAmount;
    public final NotificationStackScrollLayoutController nsslController;
    public final SysuiStatusBarStateController statusBarStateController;
    public int totalDistanceForFullShadeTransition;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        SingleShadeLockScreenOverScroller create(NotificationStackScrollLayoutController notificationStackScrollLayoutController);
    }

    public SingleShadeLockScreenOverScroller(ConfigurationController configurationController, Context context, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.context = context;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.nsslController = notificationStackScrollLayoutController;
        Resources resources = context.getResources();
        this.totalDistanceForFullShadeTransition = resources.getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_distance);
        this.maxOverScrollAmount = resources.getDimensionPixelSize(R.dimen.lockscreen_shade_max_over_scroll_amount);
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.SingleShadeLockScreenOverScroller.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                SingleShadeLockScreenOverScroller singleShadeLockScreenOverScroller = SingleShadeLockScreenOverScroller.this;
                Resources resources2 = singleShadeLockScreenOverScroller.context.getResources();
                singleShadeLockScreenOverScroller.totalDistanceForFullShadeTransition = resources2.getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_distance);
                singleShadeLockScreenOverScroller.maxOverScrollAmount = resources2.getDimensionPixelSize(R.dimen.lockscreen_shade_max_over_scroll_amount);
            }
        });
    }
}
