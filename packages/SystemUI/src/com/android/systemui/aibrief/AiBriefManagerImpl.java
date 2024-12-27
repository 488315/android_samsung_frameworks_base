package com.android.systemui.aibrief;

import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.aibrief.control.BriefNotificationController;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aibrief.data.NowBarData;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.json.JSONException;
import org.json.JSONObject;

public final class AiBriefManagerImpl implements AiBriefManager {
    public static final String TAG = "BriefManager";
    private final Gson gsonBuilder = new GsonBuilder().create();
    private final BriefLogger logger;
    private final BriefNotificationController notificationController;
    private final BriefNowBarController nowBarController;
    private final BriefViewController viewController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AiBriefManagerImpl(BriefLogger briefLogger, BriefViewController briefViewController, BriefNowBarController briefNowBarController, BriefNotificationController briefNotificationController) {
        this.logger = briefLogger;
        this.viewController = briefViewController;
        this.nowBarController = briefNowBarController;
        this.notificationController = briefNotificationController;
        briefLogger.d(TAG, "init");
    }

    private final JSONObject conventBundleToJson(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            try {
                jSONObject.put(str, JSONObject.wrap(bundle.get(str)));
            } catch (JSONException e) {
                this.logger.e("BriefViewController", "conventBundleToJson() " + e);
            }
        }
        return jSONObject;
    }

    private final NowBarData convertToNowBarData(Bundle bundle) {
        try {
            return (NowBarData) this.gsonBuilder.fromJson(NowBarData.class, conventBundleToJson(bundle).getString("data"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final void showNowBarRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        this.logger.d(TAG, "showNowBarRemoteView");
        this.nowBarController.showNowBarRemoteView(remoteViews, remoteViews2);
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void createNowBar(Bundle bundle) {
        NowBarData convertToNowBarData = convertToNowBarData(bundle);
        showNowBar(this.viewController.createBriefNowBarView(convertToNowBarData), this.viewController.createFullView(), convertToNowBarData);
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void createRemoteNowBar(Bundle bundle) {
        showNowBarRemoteView(this.viewController.createNormalRemoteView(bundle), this.viewController.createExpandRemoteView(bundle));
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void hideNotification() {
        this.logger.d(TAG, "hideNotification");
        this.notificationController.hideNotification();
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void hideNowBar() {
        this.logger.d(TAG, "hideNowBar");
        this.nowBarController.hideNowBar();
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void hideRemoteNowBar() {
        this.logger.d(TAG, "hideRemoteNowBar");
        this.nowBarController.hideRemoteNowBar();
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void showNotification() {
        this.logger.d(TAG, "showNotification");
        this.notificationController.showNotification();
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void showNowBar(View view, View view2, NowBarData nowBarData) {
        this.logger.d(TAG, "showNowBar");
        this.nowBarController.showNowBar(view, view2, nowBarData);
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void showReport() {
        this.logger.d(TAG, "showReport");
    }
}
