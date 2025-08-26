package com.android.systemui.statusbar.notification;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.service.notification.NotificationListenerService;
import android.util.SparseArray;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class AssistantFeedbackController {
    public volatile boolean mFeedbackEnabled;
    public final Handler mHandler;
    public final SparseArray mIcons;
    public final AnonymousClass1 mPropertiesChangedListener;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.systemui.statusbar.notification.AssistantFeedbackController$1] */
    public AssistantFeedbackController(Handler handler, Context context, DeviceConfigProxy deviceConfigProxy) {
        ?? r4 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.statusbar.notification.AssistantFeedbackController.1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("enable_nas_feedback")) {
                    AssistantFeedbackController.this.mFeedbackEnabled = properties.getBoolean("enable_nas_feedback", false);
                }
            }
        };
        this.mPropertiesChangedListener = r4;
        this.mHandler = handler;
        this.mFeedbackEnabled = deviceConfigProxy.getBoolean("systemui", "enable_nas_feedback", false);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.statusbar.notification.AssistantFeedbackController$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                this.f$0.mHandler.post(runnable);
            }
        }, r4);
        SparseArray sparseArray = new SparseArray(4);
        this.mIcons = sparseArray;
        sparseArray.set(1, new FeedbackIcon(R.drawable.ic_maps_indicator_current_position_anim3, R.string.status_bar_sensors_off));
        sparseArray.set(2, new FeedbackIcon(R.drawable.ic_media_route_connected_dark_00_mtrl, R.string.status_bar_sync_failing));
        sparseArray.set(3, new FeedbackIcon(R.drawable.ic_media_route_connected_dark_01_mtrl, R.string.status_bar_sync_active));
        sparseArray.set(4, new FeedbackIcon(R.drawable.ic_media_embed_play, R.string.status_bar_speakerphone));
    }

    public final int getFeedbackStatus(NotificationListenerService.Ranking ranking) {
        if (!this.mFeedbackEnabled) {
            return 0;
        }
        int importance = ranking.getChannel().getImportance();
        int importance2 = ranking.getImportance();
        if (importance < 3 && importance2 >= 3) {
            return 1;
        }
        if (importance >= 3 && importance2 < 3) {
            return 2;
        }
        if (importance < importance2 || ranking.getRankingAdjustment() == 1) {
            return 3;
        }
        return (importance > importance2 || ranking.getRankingAdjustment() == -1) ? 4 : 0;
    }
}
