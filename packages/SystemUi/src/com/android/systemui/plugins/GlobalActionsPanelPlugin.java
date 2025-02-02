package com.android.systemui.plugins;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Dependencies({@DependsOn(target = Callbacks.class), @DependsOn(target = PanelViewController.class)})
@ProvidesInterface(action = GlobalActionsPanelPlugin.ACTION, version = 0)
/* loaded from: classes2.dex */
public interface GlobalActionsPanelPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_GLOBAL_ACTIONS_PANEL";
    public static final int VERSION = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
