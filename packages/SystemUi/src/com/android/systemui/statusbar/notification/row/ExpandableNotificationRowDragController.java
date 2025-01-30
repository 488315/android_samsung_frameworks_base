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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;
import android.view.DragEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.InstanceIdSequence;
import com.android.systemui.R;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import com.android.systemui.statusbar.notification.logging.nano.Notifications$NotificationList;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.google.protobuf.nano.MessageNano;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExpandableNotificationRowDragController {
    public final Context mContext;
    public final HeadsUpManager mHeadsUpManager;
    public final NotificationPanelLogger mNotificationPanelLogger;
    public final ShadeController mShadeController;

    public ExpandableNotificationRowDragController(Context context, HeadsUpManager headsUpManager, ShadeController shadeController, NotificationPanelLogger notificationPanelLogger) {
        this.mContext = context;
        this.mHeadsUpManager = headsUpManager;
        this.mShadeController = shadeController;
        this.mNotificationPanelLogger = notificationPanelLogger;
        context.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v6, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r8v7, types: [android.graphics.drawable.Drawable] */
    public void startDragAndDrop(View view) {
        ExpandableNotificationRow expandableNotificationRow = view instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view : null;
        Notification notification2 = expandableNotificationRow.mEntry.mSbn.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        ShadeController shadeController = this.mShadeController;
        Context context = this.mContext;
        if (pendingIntent == null || !pendingIntent.isActivity()) {
            if (!expandableNotificationRow.mIsPinned) {
                ((ShadeControllerImpl) shadeController).animateCollapsePanels(1.1f, 0, true, false);
            }
            Toast.makeText(context, R.string.drag_split_not_supported, 0).show();
            return;
        }
        ?? packageName = expandableNotificationRow.mEntry.mSbn.getPackageName();
        ?? packageManager = context.getPackageManager();
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
        Bitmap createBitmap = Bitmap.createBitmap(packageName.getIntrinsicWidth(), packageName.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        packageName.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        packageName.draw(canvas);
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(createBitmap);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.drag_and_drop_icon_size);
        imageView.layout(0, 0, dimensionPixelSize, dimensionPixelSize);
        ClipDescription clipDescription = new ClipDescription("Drag And Drop", new String[]{"application/vnd.android.activity"});
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.PENDING_INTENT", pendingIntent);
        intent.putExtra("android.intent.extra.USER", Process.myUserHandle());
        intent.putExtra("com.samsung.android.intent.extra.DRAG_AND_DROP_REQUESTER", expandableNotificationRow.mEntry.isRowPinned() ? "hun" : "noti");
        ClipData.Item item = new ClipData.Item(intent);
        item.getIntent().putExtra("android.intent.extra.LOGGING_INSTANCE_ID", (Parcelable) new InstanceIdSequence(Integer.MAX_VALUE).newInstanceId());
        ClipData clipData = new ClipData(clipDescription, item);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(imageView);
        view.setOnDragListener(new View.OnDragListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view2, DragEvent dragEvent) {
                ExpandableNotificationRowDragController expandableNotificationRowDragController = ExpandableNotificationRowDragController.this;
                expandableNotificationRowDragController.getClass();
                int action = dragEvent.getAction();
                if (action == 1) {
                    return true;
                }
                if (action != 4) {
                    return false;
                }
                if (!dragEvent.getResult()) {
                    final SurfaceControl dragSurface = dragEvent.getDragSurface();
                    final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    ofFloat.setDuration(200L);
                    ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda1
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
                    ofFloat.addListener(new AnimatorListenerAdapter(expandableNotificationRowDragController, dragSurface, transaction) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController.1
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
                    ofFloat.start();
                } else if (view2 instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) view2).dragAndDropSuccess();
                }
                view2.setOnDragListener(null);
                return true;
            }
        });
        if (view.startDragAndDrop(clipData, dragShadowBuilder, null, VolteConstants.ErrorCode.CALL_FORBIDDEN_RSN_TEMPORARY_DISABILITY)) {
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            ((NotificationPanelLoggerImpl) this.mNotificationPanelLogger).getClass();
            Notifications$NotificationList notificationProto = NotificationPanelLogger.toNotificationProto(Collections.singletonList(notificationEntry));
            SysUiStatsLog.write(NotificationPanelLogger.NotificationPanelEvent.NOTIFICATION_DRAG.getId(), notificationProto.notifications.length, MessageNano.toByteArray(notificationProto));
            view.performHapticFeedback(0);
            if (expandableNotificationRow.mIsPinned) {
                this.mHeadsUpManager.releaseAllImmediately();
            } else {
                ((ShadeControllerImpl) shadeController).animateCollapsePanels(1.1f, 0, true, false);
            }
        }
    }
}
