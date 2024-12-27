package com.android.systemui.plugins;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@Dependencies({@DependsOn(target = Callbacks.class), @DependsOn(target = PanelViewController.class)})
@ProvidesInterface(action = GlobalActionsPanelPlugin.ACTION, version = 0)
public interface GlobalActionsPanelPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_GLOBAL_ACTIONS_PANEL";
    public static final int VERSION = 0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 0)
    public interface Callbacks {
        public static final int VERSION = 0;

        void dismissGlobalActionsMenu();

        default void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
            try {
                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                makeBasic.setInteractive(true);
                pendingIntent.send(makeBasic.toBundle());
            } catch (PendingIntent.CanceledException unused) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 0)
    public interface PanelViewController {
        public static final int VERSION = 0;

        default Drawable getBackgroundDrawable() {
            return null;
        }

        View getPanelContent();

        void onDeviceLockStateChanged(boolean z);

        void onDismissed();
    }

    PanelViewController onPanelShown(Callbacks callbacks, boolean z);
}
