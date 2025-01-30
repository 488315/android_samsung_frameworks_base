package com.android.systemui.statusbar.p030tv.notifications;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import androidx.leanback.widget.VerticalGridView;
import com.android.systemui.R;
import com.android.systemui.statusbar.p030tv.notifications.TvNotificationHandler;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvNotificationPanelActivity extends Activity implements TvNotificationHandler.Listener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public VerticalGridView mNotificationListView;
    public View mNotificationPlaceholder;
    public TvNotificationAdapter mTvNotificationAdapter;
    public final TvNotificationHandler mTvNotificationHandler;
    public boolean mPanelAlreadyOpen = false;
    public final TvNotificationPanelActivity$$ExternalSyntheticLambda0 mBlurConsumer = new Consumer() { // from class: com.android.systemui.statusbar.tv.notifications.TvNotificationPanelActivity$$ExternalSyntheticLambda0
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            TvNotificationPanelActivity tvNotificationPanelActivity = TvNotificationPanelActivity.this;
            boolean booleanValue = ((Boolean) obj).booleanValue();
            int i = TvNotificationPanelActivity.$r8$clinit;
            if (!booleanValue) {
                tvNotificationPanelActivity.getWindow().setBackgroundDrawable(new ColorDrawable(tvNotificationPanelActivity.getColor(R.color.tv_notification_default_background_color)));
                tvNotificationPanelActivity.getWindow().setBackgroundBlurRadius(0);
            } else {
                int dimensionPixelSize = tvNotificationPanelActivity.getResources().getDimensionPixelSize(R.dimen.tv_notification_blur_radius);
                tvNotificationPanelActivity.getWindow().setBackgroundDrawable(new ColorDrawable(tvNotificationPanelActivity.getColor(R.color.tv_notification_blur_background_color)));
                tvNotificationPanelActivity.getWindow().setBackgroundBlurRadius(dimensionPixelSize);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.tv.notifications.TvNotificationPanelActivity$$ExternalSyntheticLambda0] */
    public TvNotificationPanelActivity(TvNotificationHandler tvNotificationHandler) {
        this.mTvNotificationHandler = tvNotificationHandler;
    }

    public final boolean maybeClosePanel(Intent intent) {
        if (!"android.app.action.CLOSE_NOTIFICATION_HANDLER_PANEL".equals(intent.getAction()) && (!this.mPanelAlreadyOpen || !"android.app.action.TOGGLE_NOTIFICATION_HANDLER_PANEL".equals(intent.getAction()))) {
            return false;
        }
        finish();
        return true;
    }

    public final void notificationsUpdated(SparseArray sparseArray) {
        TvNotificationAdapter tvNotificationAdapter = this.mTvNotificationAdapter;
        tvNotificationAdapter.mNotifications = sparseArray;
        tvNotificationAdapter.notifyDataSetChanged();
        boolean z = sparseArray.size() == 0;
        this.mNotificationListView.setVisibility(z ? 8 : 0);
        this.mNotificationPlaceholder.setVisibility(z ? 0 : 8);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setGravity(8388613);
        getWindowManager().addCrossWindowBlurEnabledListener(this.mBlurConsumer);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (maybeClosePanel(getIntent())) {
            return;
        }
        this.mPanelAlreadyOpen = true;
        setContentView(R.layout.tv_notification_panel);
        this.mNotificationPlaceholder = findViewById(R.id.no_tv_notifications);
        this.mTvNotificationAdapter = new TvNotificationAdapter();
        VerticalGridView verticalGridView = (VerticalGridView) findViewById(R.id.notifications_list);
        this.mNotificationListView = verticalGridView;
        verticalGridView.setAdapter(this.mTvNotificationAdapter);
        VerticalGridView verticalGridView2 = this.mNotificationListView;
        verticalGridView2.mLayoutManager.setRowHeight(R.dimen.tv_notification_panel_width);
        verticalGridView2.requestLayout();
        TvNotificationHandler tvNotificationHandler = this.mTvNotificationHandler;
        tvNotificationHandler.mUpdateListener = this;
        notificationsUpdated(tvNotificationHandler.mNotifications);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.mTvNotificationHandler.mUpdateListener = null;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getWindowManager().removeCrossWindowBlurEnabledListener(this.mBlurConsumer);
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        maybeClosePanel(intent);
    }
}
