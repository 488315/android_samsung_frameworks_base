package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.ImageUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PolicyWarningUIController {
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final ArraySet mEnabledA11yServices = new ArraySet();
    public final Handler mMainHandler;
    public final NotificationController mNotificationController;
    protected static final String ACTION_SEND_NOTIFICATION = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1("PolicyWarningUIController", ".ACTION_SEND_NOTIFICATION");
    protected static final String ACTION_A11Y_SETTINGS = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1("PolicyWarningUIController", ".ACTION_A11Y_SETTINGS");
    protected static final String ACTION_DISMISS_NOTIFICATION = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1("PolicyWarningUIController", ".ACTION_DISMISS_NOTIFICATION");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationController extends BroadcastReceiver {
        public final Context mContext;
        public int mCurrentUserId;
        public final NotificationManager mNotificationManager;
        public boolean mSendNotification;
        public final ArraySet mNotifiedA11yServices = new ArraySet();
        public final List mSentA11yServiceNotification = new ArrayList();

        public NotificationController(Context context) {
            this.mContext = context;
            this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        }

        public List getEnabledServiceInfos() {
            return AccessibilityManager.getInstance(this.mContext).getEnabledAccessibilityServiceList(-1);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            ComponentName componentName = (ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME", ComponentName.class);
            if (TextUtils.isEmpty(action) || componentName == null) {
                return;
            }
            long longExtra = intent.getLongExtra("android.intent.extra.TIME", 0L);
            long elapsedRealtime = longExtra > 0 ? SystemClock.elapsedRealtime() - longExtra : 0L;
            int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", 0);
            if (!PolicyWarningUIController.ACTION_SEND_NOTIFICATION.equals(action)) {
                if (!PolicyWarningUIController.ACTION_A11Y_SETTINGS.equals(action)) {
                    if (PolicyWarningUIController.ACTION_DISMISS_NOTIFICATION.equals(action)) {
                        ((ArrayList) this.mSentA11yServiceNotification).remove(componentName);
                        if (intExtra == this.mCurrentUserId && this.mNotifiedA11yServices.add(componentName)) {
                            writeNotifiedServiceList(intExtra, this.mNotifiedA11yServices);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (intExtra == this.mCurrentUserId) {
                    Intent m = BatteryService$$ExternalSyntheticOutline0.m(268468224, "android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
                    m.putExtra("android.intent.extra.COMPONENT_NAME", componentName.flattenToShortString());
                    m.putExtra("start_time_to_log_a11y_tool", SystemClock.elapsedRealtime());
                    this.mContext.startActivityAsUser(m, ActivityOptions.makeBasic().setLaunchDisplayId(this.mContext.getDisplayId()).toBundle(), UserHandle.of(intExtra));
                    ((StatusBarManager) this.mContext.getSystemService(StatusBarManager.class)).collapsePanels();
                    AccessibilityStatsLogUtils.logNonA11yToolServiceWarningReported(componentName.getPackageName(), AccessibilityStatsLogUtils.ACCESSIBILITY_PRIVACY_WARNING_STATUS_CLICKED, elapsedRealtime);
                }
                this.mNotificationManager.cancel(componentName.flattenToShortString(), 1005);
                ((ArrayList) this.mSentA11yServiceNotification).remove(componentName);
                if (intExtra == this.mCurrentUserId && this.mNotifiedA11yServices.add(componentName)) {
                    writeNotifiedServiceList(intExtra, this.mNotifiedA11yServices);
                    return;
                }
                return;
            }
            if (intExtra == this.mCurrentUserId && this.mSendNotification) {
                List enabledServiceInfos = getEnabledServiceInfos();
                for (int i = 0; i < enabledServiceInfos.size(); i++) {
                    AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) enabledServiceInfos.get(i);
                    if (componentName.flattenToShortString().equals(accessibilityServiceInfo.getComponentName().flattenToShortString())) {
                        if (accessibilityServiceInfo.isAccessibilityTool() || this.mNotifiedA11yServices.contains(componentName)) {
                            return;
                        }
                        CharSequence loadLabel = accessibilityServiceInfo.getResolveInfo().serviceInfo.loadLabel(this.mContext.getPackageManager());
                        Drawable loadIcon = accessibilityServiceInfo.getResolveInfo().loadIcon(this.mContext.getPackageManager());
                        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
                        Bitmap buildScaledBitmap = ImageUtils.buildScaledBitmap(loadIcon, dimensionPixelSize, dimensionPixelSize);
                        Notification.Builder builder = new Notification.Builder(this.mContext, SystemNotificationChannels.ACCESSIBILITY_SECURITY_POLICY);
                        builder.setSmallIcon(R.drawable.ic_battery).setSmallIcon(R.drawable.scrubber_progress_horizontal_holo_dark).setColor(this.mContext.getResources().getColor(R.color.accessibility_magnification_thumbnail_container_stroke_color)).setCategory("recommendation").setContentTitle(this.mContext.getString(17043442)).setContentText(this.mContext.getString(17043441, loadLabel)).setStyle(new Notification.BigTextStyle().bigText(this.mContext.getString(17043441, loadLabel))).setTicker(this.mContext.getString(17043442)).setOnlyAlertOnce(true).setDeleteIntent(PolicyWarningUIController.createPendingIntent(this.mContext, intExtra, PolicyWarningUIController.ACTION_DISMISS_NOTIFICATION, componentName)).setContentIntent(PolicyWarningUIController.createPendingIntent(this.mContext, intExtra, PolicyWarningUIController.ACTION_A11Y_SETTINGS, componentName));
                        if (buildScaledBitmap != null) {
                            builder.setLargeIcon(buildScaledBitmap);
                        }
                        this.mNotificationManager.notify(componentName.flattenToShortString(), 1005, builder.build());
                        ((ArrayList) this.mSentA11yServiceNotification).add(componentName);
                        AccessibilityStatsLogUtils.logNonA11yToolServiceWarningReported(componentName.getPackageName(), AccessibilityStatsLogUtils.ACCESSIBILITY_PRIVACY_WARNING_STATUS_SHOWN, elapsedRealtime);
                        return;
                    }
                }
            }
        }

        public final void writeNotifiedServiceList(int i, ArraySet arraySet) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                if (i2 > 0) {
                    sb.append(':');
                }
                sb.append(((ComponentName) arraySet.valueAt(i2)).flattenToShortString());
            }
            Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "notified_non_accessibility_category_services", sb.toString(), i);
        }
    }

    public PolicyWarningUIController(AccessibilityManagerService.MainHandler mainHandler, Context context, NotificationController notificationController) {
        this.mMainHandler = mainHandler;
        this.mContext = context;
        this.mNotificationController = notificationController;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SEND_NOTIFICATION);
        intentFilter.addAction(ACTION_A11Y_SETTINGS);
        intentFilter.addAction(ACTION_DISMISS_NOTIFICATION);
        context.registerReceiver(notificationController, intentFilter, "android.permission.MANAGE_ACCESSIBILITY", mainHandler, 2);
    }

    public static PendingIntent createPendingIntent(Context context, int i, String str, ComponentName componentName) {
        Intent intent = new Intent(str);
        intent.setPackage(context.getPackageName()).setIdentifier(componentName.flattenToShortString()).putExtra("android.intent.extra.COMPONENT_NAME", componentName).putExtra("android.intent.extra.USER_ID", i).putExtra("android.intent.extra.TIME", SystemClock.elapsedRealtime());
        return PendingIntent.getBroadcast(context, 0, intent, 67108864);
    }
}
