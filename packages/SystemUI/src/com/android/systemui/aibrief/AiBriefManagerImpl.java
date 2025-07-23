package com.android.systemui.aibrief;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.aibrief.control.BriefNotificationController;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.systemui.aibrief.data.NowBarData;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    private final JSONObject conventBundleToJson(Bundle bundle) {
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
        NowBarData convertToNowBarData = convertToNowBarData(bundle);
        View createBriefNowBarView$default = BriefViewController.createBriefNowBarView$default(this.viewController, convertToNowBarData, false, 2, null);
        createBriefNowBarView$default.getClass();
        showNowBar(createBriefNowBarView$default, this.viewController.createFullView(), this.viewController.createBriefNowBarView(convertToNowBarData, true));
    }

    @Override // com.android.systemui.aibrief.AiBriefManager
    public void createRemoteNowBar(Bundle bundle) {
        showNowBarRemoteView(this.viewController.createNormalRemoteView(bundle), this.viewController.createExpandRemoteView(bundle), this.viewController.createNormalCoverRemoteView(bundle), this.viewController.createPendingIntent(bundle));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
    @Override // com.android.systemui.aibrief.AiBriefManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.os.Bundle findSportsScoreRemoteViews(android.os.Bundle r4) {
        /*
            r3 = this;
            com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper r3 = r3.faceWidgetNotificationControllerWrapper
            r3.getClass()
            r0 = 0
            com.android.systemui.plugins.keyguardstatusview.PluginNotificationController r3 = r3.mNotificationController     // Catch: java.lang.Exception -> Lf
            if (r3 == 0) goto L2b
            java.util.ArrayList r3 = r3.findNowBarItems(r4)     // Catch: java.lang.Exception -> Lf
            goto L2c
        Lf:
            r3 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getNowBarItem "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r4 = " / "
            r1.append(r4)
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            java.lang.String r4 = "FaceWidgetNotificationControllerWrapper"
            android.util.Log.e(r4, r3)
        L2b:
            r3 = r0
        L2c:
            if (r3 == 0) goto L36
            r4 = 0
            java.lang.Object r3 = kotlin.collections.CollectionsKt___CollectionsKt.getOrNull(r4, r3)
            r0 = r3
            android.os.Bundle r0 = (android.os.Bundle) r0
        L36:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.aibrief.AiBriefManagerImpl.findSportsScoreRemoteViews(android.os.Bundle):android.os.Bundle");
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
