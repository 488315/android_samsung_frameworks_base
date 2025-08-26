package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.os.Process;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.DragEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.InstanceIdSequence;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$Notification;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$NotificationList;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.google.protobuf.nano.MessageNano;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class ExpandableNotificationRowDragController {
    public final Context mContext;
    public final HeadsUpManager mHeadsUpManager;
    public final NotificationPanelLogger mNotificationPanelLogger;
    public final ShadeController mShadeController;

    public ExpandableNotificationRowDragController(Context context, HeadsUpManager headsUpManager, ShadeController shadeController, NotificationPanelLogger notificationPanelLogger) throws Resources.NotFoundException {
        this.mContext = context;
        this.mHeadsUpManager = headsUpManager;
        this.mShadeController = shadeController;
        this.mNotificationPanelLogger = notificationPanelLogger;
        context.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v6, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r3v7, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.content.pm.PackageManager] */
    public void startDragAndDrop(View view) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        ExpandableNotificationRow expandableNotificationRow = view instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view : null;
        int i = NotificationBundleUi.$r8$clinit;
        StatusBarNotification statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
        Notification notification2 = statusBarNotification.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        ShadeController shadeController = this.mShadeController;
        if (pendingIntent == null || !pendingIntent.isActivity()) {
            if (!expandableNotificationRow.mPinnedStatus.isPinned()) {
                shadeController.animateCollapseShade(1.1f, 0, true, false);
            }
            Toast.makeText(this.mContext, R.string.drag_split_not_supported, 0).show();
            return;
        }
        ?? packageName = statusBarNotification.getPackageName();
        ?? packageManager = this.mContext.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 795136);
            if (applicationInfo != null) {
                packageName = packageManager.getApplicationIcon(applicationInfo);
            } else {
                Log.d("ExpandableNotificationRowDragController", " application info is null ");
                packageName = packageManager.getDefaultActivityIcon();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("ExpandableNotificationRowDragController", "can not find package with : " + packageName);
            packageName = packageManager.getDefaultActivityIcon();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(packageName.getIntrinsicWidth(), packageName.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        packageName.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        packageName.draw(canvas);
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageBitmap(bitmapCreateBitmap);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size);
        imageView.layout(0, 0, dimensionPixelSize, dimensionPixelSize);
        ClipDescription clipDescription = new ClipDescription("Drag And Drop", new String[]{"application/vnd.android.activity"});
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.PENDING_INTENT", pendingIntent);
        intent.putExtra("android.intent.extra.USER", Process.myUserHandle());
        ClipData.Item item = new ClipData.Item(intent);
        item.getIntent().putExtra("android.intent.extra.LOGGING_INSTANCE_ID", (Parcelable) new InstanceIdSequence(Integer.MAX_VALUE).newInstanceId());
        ClipData clipData = new ClipData(clipDescription, item);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(imageView);
        view.setOnDragListener(new View.OnDragListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view2, DragEvent dragEvent) {
                ExpandableNotificationRow expandableNotificationRow2;
                NotificationClicker.AnonymousClass1 anonymousClass1;
                ExpandableNotificationRowDragController expandableNotificationRowDragController = this.f$0;
                int action = dragEvent.getAction();
                if (action != 1) {
                    if (action != 4) {
                        return false;
                    }
                    if (!dragEvent.getResult()) {
                        final SurfaceControl dragSurface = dragEvent.getDragSurface();
                        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                        valueAnimatorOfFloat.setDuration(200L);
                        valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                SurfaceControl surfaceControl = dragSurface;
                                SurfaceControl.Transaction transaction2 = transaction;
                                if (surfaceControl.isValid()) {
                                    transaction2.setAlpha(surfaceControl, 1.0f - valueAnimator.getAnimatedFraction());
                                    transaction2.apply();
                                }
                            }
                        });
                        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter(expandableNotificationRowDragController, dragSurface, transaction) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController.1
                            public boolean mCanceled = false;
                            public final /* synthetic */ SurfaceControl val$dragSurface;
                            public final /* synthetic */ SurfaceControl.Transaction val$tx;

                            {
                                this.val$dragSurface = dragSurface;
                                this.val$tx = transaction;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                                if (this.val$dragSurface.isValid()) {
                                    this.val$tx.remove(this.val$dragSurface);
                                    this.val$tx.apply();
                                    this.val$tx.close();
                                }
                                this.mCanceled = true;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                if (!this.mCanceled && this.val$dragSurface.isValid()) {
                                    this.val$tx.remove(this.val$dragSurface);
                                    this.val$tx.apply();
                                    this.val$tx.close();
                                }
                            }
                        });
                        valueAnimatorOfFloat.start();
                    } else if ((view2 instanceof ExpandableNotificationRow) && (anonymousClass1 = (expandableNotificationRow2 = (ExpandableNotificationRow) view2).mOnDragSuccessListener) != null) {
                        int i2 = NotificationBundleUi.$r8$clinit;
                        NotificationEntry entryLegacy = expandableNotificationRow2.getEntryLegacy();
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        ((StatusBarNotificationActivityStarter) NotificationClicker.this.mNotificationActivityStarter).onDragSuccess(entryLegacy);
                    }
                    view2.setOnDragListener(null);
                }
                return true;
            }
        });
        if (view.startDragAndDrop(clipData, dragShadowBuilder, null, VolteConstants.ErrorCode.CALL_FORBIDDEN_RSN_TEMPORARY_DISABILITY)) {
            int i2 = NotificationBundleUi.$r8$clinit;
            NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
            ((NotificationPanelLoggerImpl) this.mNotificationPanelLogger).getClass();
            List<NotificationEntry> listSingletonList = Collections.singletonList(entryLegacy);
            Notifications$NotificationList notifications$NotificationList = new Notifications$NotificationList();
            if (listSingletonList != null) {
                Notifications$Notification[] notifications$NotificationArr = new Notifications$Notification[listSingletonList.size()];
                int i3 = 0;
                for (NotificationEntry notificationEntry : listSingletonList) {
                    StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
                    if (statusBarNotification2 != null) {
                        Notifications$Notification notifications$Notification = new Notifications$Notification();
                        notifications$Notification.uid = statusBarNotification2.getUid();
                        notifications$Notification.packageName = statusBarNotification2.getPackageName();
                        if (statusBarNotification2.getInstanceId() != null) {
                            notifications$Notification.instanceId = statusBarNotification2.getInstanceId().getId();
                        }
                        if (statusBarNotification2.getNotification() != null) {
                            notifications$Notification.isGroupSummary = statusBarNotification2.getNotification().isGroupSummary();
                        }
                        notifications$Notification.section = NotificationPanelLogger.toNotificationSection(notificationEntry.mBucket);
                        notifications$NotificationArr[i3] = notifications$Notification;
                    }
                    i3++;
                }
                notifications$NotificationList.notifications = notifications$NotificationArr;
            }
            SysUiStatsLog.write(NotificationPanelLogger.NotificationPanelEvent.NOTIFICATION_DRAG.getId(), notifications$NotificationList.notifications.length, MessageNano.toByteArray(notifications$NotificationList));
            view.performHapticFeedback(0);
            if (expandableNotificationRow.mPinnedStatus.isPinned()) {
                ((HeadsUpManagerImpl) this.mHeadsUpManager).releaseAllImmediately();
            } else {
                shadeController.animateCollapseShade(1.1f, 0, true, false);
            }
        }
    }
}
