package com.android.systemui.aibrief;

import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.aibrief.control.BriefNotificationController;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aibrief.data.NowBarData;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class AiBriefManagerImpl implements AiBriefManager {
    public static final String TAG = "BriefManager";
    private final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    private final Gson gsonBuilder = new GsonBuilder().create();
    private final BriefLogger logger;
    private final BriefNotificationController notificationController;
    private final BriefNowBarController nowBarController;
    private final BriefViewController viewController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public AiBriefManagerImpl(BriefLogger briefLogger, BriefViewController briefViewController, BriefNowBarController briefNowBarController, BriefNotificationController briefNotificationController, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        this.logger = briefLogger;
        this.viewController = briefViewController;
        this.nowBarController = briefNowBarController;
        this.notificationController = briefNotificationController;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        briefLogger.d(TAG, "init");
    }

    private final JSONObject conventBundleToJson(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            try {
                jSONObject.put(str, JSONObject.wrap(bundle.get(str)));
            } catch (JSONException e) {
                this.logger.e(BriefViewController.TAG, "conventBundleToJson() " + e);
            }
        }
        return jSONObject;
    }

    private final NowBarData convertToNowBarData(Bundle bundle) {
        try {
            return (NowBarData) this.gsonBuilder.fromJson(conventBundleToJson(bundle).getString("data"), NowBarData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final void showNowBarRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2, RemoteViews remoteViews3, PendingIntent pendingIntent) {
        this.logger.d(TAG, "showNowBarRemoteView");
        this.nowBarController.showNowBarRemoteView(remoteViews, remoteViews2, remoteViews3, pendingIntent);
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void createNowBar(Bundle bundle) {
        NowBarData nowBarDataConvertToNowBarData = convertToNowBarData(bundle);
        View viewCreateBriefNowBarView$default = BriefViewController.createBriefNowBarView$default(this.viewController, nowBarDataConvertToNowBarData, false, 2, null);
        viewCreateBriefNowBarView$default.getClass();
        showNowBar(viewCreateBriefNowBarView$default, this.viewController.createFullView(), this.viewController.createBriefNowBarView(nowBarDataConvertToNowBarData, true));
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void createRemoteNowBar(Bundle bundle) {
        showNowBarRemoteView(this.viewController.createNormalRemoteView(bundle), this.viewController.createExpandRemoteView(bundle), this.viewController.createNormalCoverRemoteView(bundle), this.viewController.createPendingIntent(bundle));
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public Bundle findSportsScoreRemoteViews(Bundle bundle) {
        PluginNotificationController pluginNotificationController;
        FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = this.faceWidgetNotificationControllerWrapper;
        faceWidgetNotificationControllerWrapper.getClass();
        try {
            pluginNotificationController = faceWidgetNotificationControllerWrapper.mNotificationController;
        } catch (Exception e) {
            Log.e("FaceWidgetNotificationControllerWrapper", "getNowBarItem " + bundle + " / " + e);
        }
        ArrayList<Bundle> arrayListFindNowBarItems = pluginNotificationController != null ? pluginNotificationController.findNowBarItems(bundle) : null;
        if (arrayListFindNowBarItems != null) {
            return (Bundle) CollectionsKt___CollectionsKt.getOrNull(0, arrayListFindNowBarItems);
        }
        return null;
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
    public void showNowBar(View view, View view2, View view3) {
        this.logger.d(TAG, "showNowBar");
        this.nowBarController.showNowBar(view, view2, view3);
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void showReport() {
        this.logger.d(TAG, "showReport");
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void updateNowBarNeedToUnlock(Bundle bundle) {
        this.logger.d(TAG, "updateNowBarNeedToUnlock");
        this.nowBarController.updateNowBarNeedToUnlock(bundle.getBoolean("needToUnlock", false));
    }
}
