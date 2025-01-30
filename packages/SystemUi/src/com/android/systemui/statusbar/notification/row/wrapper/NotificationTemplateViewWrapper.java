package com.android.systemui.statusbar.notification.row.wrapper;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Pools;
import android.view.NotificationHeaderView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationTemplateViewWrapper extends NotificationHeaderViewWrapper {
    public NotificationActionListLayout mActions;
    public View mActionsContainer;
    public final boolean mAllowHideHeader;
    public boolean mCanHideHeader;
    public final ArraySet mCancelledPendingIntents;
    public final int mFullHeaderTranslation;
    public float mHeaderTranslation;
    public ImageView mLeftIcon;
    public ProgressBar mProgressBar;
    public View mRemoteInputHistory;
    public ImageView mRightIcon;
    public View mSmartReplyContainer;
    public TextView mText;
    public TextView mTitle;
    public UiOffloadThread mUiOffloadThread;

    public NotificationTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mCancelledPendingIntents = new ArraySet();
        this.mAllowHideHeader = context.getResources().getBoolean(R.bool.heads_up_notification_hides_header);
        this.mTransformationHelper.setCustomTransformation(new ViewTransformationHelper.CustomTransformation(this) { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper.1
            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean customTransformTarget(TransformState transformState, TransformState transformState2) {
                int[] laidOutLocationOnScreen = transformState2.getLaidOutLocationOnScreen();
                int[] laidOutLocationOnScreen2 = transformState.getLaidOutLocationOnScreen();
                transformState.mTransformationEndY = ((transformState2.mTransformedView.getHeight() + laidOutLocationOnScreen[1]) - laidOutLocationOnScreen2[1]) * 0.33f;
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean initTransformation(TransformState transformState, TransformState transformState2) {
                int[] laidOutLocationOnScreen = transformState2.getLaidOutLocationOnScreen();
                int[] laidOutLocationOnScreen2 = transformState.getLaidOutLocationOnScreen();
                transformState.setTransformationStartY(((transformState2.mTransformedView.getHeight() + laidOutLocationOnScreen[1]) - laidOutLocationOnScreen2[1]) * 0.33f);
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = transformableView.getCurrentState(1);
                CrossFadeHelper.fadeIn(transformState.mTransformedView, f, true);
                if (currentState != null) {
                    transformState.transformViewFrom(currentState, 16, this, f);
                    currentState.recycle();
                }
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = transformableView.getCurrentState(1);
                CrossFadeHelper.fadeOut(transformState.mTransformedView, f, true);
                if (currentState != null) {
                    transformState.transformViewTo(currentState, 16, this, f);
                    currentState.recycle();
                }
                return true;
            }
        }, 2);
        this.mFullHeaderTranslation = context.getResources().getDimensionPixelSize(android.R.dimen.notification_left_icon_size) - context.getResources().getDimensionPixelSize(android.R.dimen.notification_messaging_spacing_conversation_group);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getExtraMeasureHeight() {
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        int extraMeasureHeight = notificationActionListLayout != null ? notificationActionListLayout.getExtraMeasureHeight() : 0;
        View view = this.mRemoteInputHistory;
        if (view != null && view.getVisibility() != 8) {
            extraMeasureHeight += this.mRow.getContext().getResources().getDimensionPixelSize(R.dimen.remote_input_history_extra_height);
        }
        return extraMeasureHeight + 0;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getHeaderTranslation(boolean z) {
        return (z && this.mCanHideHeader) ? this.mFullHeaderTranslation : (int) this.mHeaderTranslation;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        ImageView imageView;
        Bitmap bitmap;
        Icon largeIcon;
        Bitmap bitmap2;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        View view = this.mView;
        ImageView imageView2 = (ImageView) view.findViewById(android.R.id.snooze_button);
        this.mRightIcon = imageView2;
        if (imageView2 != null) {
            Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
            Notification notification2 = statusBarNotification.getNotification();
            if ((!notification2.extras.getBoolean("android.showBigPictureWhenCollapsed") || !notification2.isStyle(Notification.BigPictureStyle.class) || (largeIcon = Notification.BigPictureStyle.getPictureIcon(notification2.extras)) == null) && (largeIcon = notification2.getLargeIcon()) == null && (bitmap2 = notification2.largeIcon) != null) {
                largeIcon = Icon.createWithBitmap(bitmap2);
            }
            imageView2.setTag(R.id.image_icon_tag, largeIcon);
            ImageView imageView3 = this.mRightIcon;
            Pools.SimplePool simplePool2 = TransformState.sInstancePool;
            imageView3.setTag(R.id.align_transform_end_tag, Boolean.TRUE);
        }
        ImageView imageView4 = (ImageView) view.findViewById(android.R.id.mediaProjection);
        this.mLeftIcon = imageView4;
        if (imageView4 != null) {
            Pools.SimplePool simplePool3 = ImageTransformState.sInstancePool;
            Notification notification3 = statusBarNotification.getNotification();
            Icon largeIcon2 = notification3.getLargeIcon();
            if (largeIcon2 == null && (bitmap = notification3.largeIcon) != null) {
                largeIcon2 = Icon.createWithBitmap(bitmap);
            }
            imageView4.setTag(R.id.image_icon_tag, largeIcon2);
        }
        this.mTitle = (TextView) view.findViewById(android.R.id.title);
        this.mText = (TextView) view.findViewById(16909857);
        View findViewById = view.findViewById(android.R.id.progress);
        if (findViewById instanceof ProgressBar) {
            this.mProgressBar = (ProgressBar) findViewById;
        } else {
            this.mProgressBar = null;
        }
        this.mSmartReplyContainer = view.findViewById(android.R.id.xhdpi);
        this.mActionsContainer = view.findViewById(android.R.id.actions_container_layout);
        this.mActions = view.findViewById(android.R.id.actions_container);
        this.mRemoteInputHistory = view.findViewById(android.R.id.pin_text);
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        boolean z = false;
        z = false;
        z = false;
        if (notificationActionListLayout != null) {
            int childCount = notificationActionListLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                Button button = (Button) this.mActions.getChildAt(i);
                final NotificationTemplateViewWrapper$$ExternalSyntheticLambda1 notificationTemplateViewWrapper$$ExternalSyntheticLambda1 = new NotificationTemplateViewWrapper$$ExternalSyntheticLambda1(3, this, button);
                final PendingIntent pendingIntent = (PendingIntent) button.getTag(android.R.id.reply_action_container);
                if (pendingIntent != null) {
                    if (this.mCancelledPendingIntents.contains(pendingIntent)) {
                        notificationTemplateViewWrapper$$ExternalSyntheticLambda1.run();
                    } else {
                        final PendingIntent.CancelListener cancelListener = new PendingIntent.CancelListener() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$$ExternalSyntheticLambda0
                            public final void onCanceled(PendingIntent pendingIntent2) {
                                final NotificationTemplateViewWrapper notificationTemplateViewWrapper = NotificationTemplateViewWrapper.this;
                                final PendingIntent pendingIntent3 = pendingIntent;
                                final Runnable runnable = notificationTemplateViewWrapper$$ExternalSyntheticLambda1;
                                notificationTemplateViewWrapper.getClass();
                                notificationTemplateViewWrapper.mView.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        NotificationTemplateViewWrapper notificationTemplateViewWrapper2 = NotificationTemplateViewWrapper.this;
                                        PendingIntent pendingIntent4 = pendingIntent3;
                                        Runnable runnable2 = runnable;
                                        notificationTemplateViewWrapper2.mCancelledPendingIntents.add(pendingIntent4);
                                        runnable2.run();
                                    }
                                });
                            }
                        };
                        if (this.mUiOffloadThread == null) {
                            this.mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
                        }
                        if (button.isAttachedToWindow()) {
                            this.mUiOffloadThread.execute(new NotificationTemplateViewWrapper$$ExternalSyntheticLambda1(z ? 1 : 0, pendingIntent, cancelListener));
                        }
                        button.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper.2
                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewAttachedToWindow(View view2) {
                                NotificationTemplateViewWrapper.this.mUiOffloadThread.execute(new NotificationTemplateViewWrapper$$ExternalSyntheticLambda1(2, pendingIntent, cancelListener));
                            }

                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewDetachedFromWindow(View view2) {
                                NotificationTemplateViewWrapper.this.mUiOffloadThread.execute(new NotificationTemplateViewWrapper$$ExternalSyntheticLambda1(1, pendingIntent, cancelListener));
                            }
                        });
                    }
                }
            }
        }
        NotificationActionListLayout notificationActionListLayout2 = this.mActions;
        if (notificationActionListLayout2 != null) {
            int childCount2 = notificationActionListLayout2.getChildCount();
            for (int i2 = 0; i2 < childCount2; i2++) {
                Button button2 = (Button) this.mActions.getChildAt(i2);
                button2.setPadding(button2.getPaddingLeft(), 0, button2.getPaddingRight(), 0);
                button2.semSetButtonShapeEnabled(true);
            }
        }
        super.onContentUpdated(expandableNotificationRow);
        if (this.mAllowHideHeader && this.mNotificationHeader != null && ((imageView = this.mRightIcon) == null || imageView.getVisibility() != 0)) {
            z = true;
        }
        this.mCanHideHeader = z;
        float f = expandableNotificationRow.mHeaderVisibleAmount;
        if (f != 1.0f) {
            setHeaderVisibleAmount(f);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setContentHeight(int i, int i2) {
        if (this.mActionsContainer != null) {
            this.mActionsContainer.setTranslationY((Math.max(i, i2) - this.mView.getHeight()) - getHeaderTranslation(false));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setHeaderVisibleAmount(float f) {
        float f2;
        NotificationHeaderView notificationHeaderView;
        if (!this.mCanHideHeader || (notificationHeaderView = this.mNotificationHeader) == null) {
            f2 = 0.0f;
        } else {
            notificationHeaderView.setAlpha(f);
            f2 = (1.0f - f) * this.mFullHeaderTranslation;
        }
        this.mHeaderTranslation = f2;
        this.mView.setTranslationY(f2);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public boolean shouldClipToRounding(boolean z) {
        View view;
        if (this instanceof NotificationCustomViewWrapper) {
            return true;
        }
        return (!z || (view = this.mActionsContainer) == null || view.getVisibility() == 8) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public void updateTransformedTypes() {
        super.updateTransformedTypes();
        TextView textView = this.mTitle;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        if (textView != null) {
            viewTransformationHelper.addTransformedView(textView, 1);
        }
        TextView textView2 = this.mText;
        if (textView2 != null) {
            viewTransformationHelper.addTransformedView(textView2, 2);
        }
        ImageView imageView = this.mRightIcon;
        if (imageView != null) {
            viewTransformationHelper.addTransformedView(imageView, 3);
        }
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            viewTransformationHelper.addTransformedView(progressBar, 4);
        }
        addViewsTransformingToSimilar(this.mLeftIcon);
        addTransformedViews(this.mSmartReplyContainer);
    }
}
