package com.android.systemui.statusbar.notification.row.wrapper;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.android.internal.util.ContrastColorUtil;
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
import com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NotificationTemplateViewWrapper extends NotificationHeaderViewWrapper {
    public NotificationActionListLayout mActions;
    public View mActionsContainer;
    public final boolean mAllowHideHeader;
    public boolean mCanHideHeader;
    final ArraySet<Integer> mCancelledPendingIntents;
    public final int mFullHeaderTranslation;
    public float mHeaderTranslation;
    public ImageView mLeftIcon;
    public TextView mNotificationChildrenCount;
    public ProgressBar mProgressBar;
    public View mRemoteInputHistory;
    public ImageView mRightIcon;
    public View mSmartReplyContainer;
    public TextView mText;
    public TextView mTitle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class ActionPendingIntentCancellationHandler implements View.OnAttachStateChangeListener {
        public static UiOffloadThread sUiOffloadThread;
        public final AnonymousClass1 mCancelListener = new AnonymousClass1();
        public final Consumer mOnCancelledCallback;
        public final PendingIntent mPendingIntent;
        public final View mView;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$1, reason: invalid class name */
        public final class AnonymousClass1 implements PendingIntent.CancelListener {
            public AnonymousClass1() {
            }

            public final void onCanceled(final PendingIntent pendingIntent) {
                ActionPendingIntentCancellationHandler.this.mView.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler.AnonymousClass1 anonymousClass1 = NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler.AnonymousClass1.this;
                        NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler.this.mOnCancelledCallback.accept(pendingIntent);
                        NotificationTemplateViewWrapper.ActionPendingIntentCancellationHandler.this.remove();
                    }
                });
            }
        }

        public ActionPendingIntentCancellationHandler(PendingIntent pendingIntent, View view, Consumer<PendingIntent> consumer) {
            this.mPendingIntent = pendingIntent;
            this.mView = view;
            this.mOnCancelledCallback = consumer;
        }

        public static UiOffloadThread getUiOffloadThread() {
            if (sUiOffloadThread == null) {
                sUiOffloadThread = (UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class);
            }
            return sUiOffloadThread;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            getUiOffloadThread().execute(new NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            getUiOffloadThread().execute(new NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda0(this, 1));
        }

        public final void remove() {
            this.mView.removeOnAttachStateChangeListener(this);
            if (this.mView.getTag(R.id.pending_intent_listener_tag) == this) {
                this.mView.setTag(R.id.pending_intent_listener_tag, null);
            }
            getUiOffloadThread().execute(new NotificationTemplateViewWrapper$ActionPendingIntentCancellationHandler$$ExternalSyntheticLambda0(this, 2));
        }
    }

    public NotificationTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mCancelledPendingIntents = new ArraySet<>();
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
                transformState.mTransformedView.setTag(R.id.transformation_start_y_tag, Float.valueOf(((transformState2.mTransformedView.getHeight() + laidOutLocationOnScreen[1]) - laidOutLocationOnScreen2[1]) * 0.33f));
                return true;
            }

            @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
            public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
                if (!(transformableView instanceof HybridNotificationView)) {
                    return false;
                }
                TransformState currentState = ((HybridNotificationView) transformableView).mTransformationHelper.getCurrentState(1);
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
                TransformState currentState = ((HybridNotificationView) transformableView).mTransformationHelper.getCurrentState(1);
                CrossFadeHelper.fadeOut(transformState.mTransformedView, f, true);
                if (currentState != null) {
                    transformState.transformViewTo(currentState, 16, this, f);
                    currentState.recycle();
                }
                return true;
            }
        }, 2);
        this.mFullHeaderTranslation = context.getResources().getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_bottom_edge_size) - context.getResources().getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_top_edge_size);
    }

    public final void disableActionView(Button button) {
        if (button.isEnabled()) {
            button.setEnabled(false);
            ColorStateList textColors = button.getTextColors();
            int[] colors = textColors.getColors();
            int[] iArr = new int[colors.length];
            float f = this.mView.getResources().getFloat(android.R.dimen.restricted_icon_size_material);
            for (int i = 0; i < colors.length; i++) {
                int i2 = colors[i];
                iArr[i] = ContrastColorUtil.compositeColors(Color.argb((int) (255.0f * f), Color.red(i2), Color.green(i2), Color.blue(i2)), resolveBackgroundColor());
            }
            button.setTextColor(new ColorStateList(textColors.getStates(), iArr));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final TextView getChildrenCountText() {
        return this.mNotificationChildrenCount;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getExtraMeasureHeight() {
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        int extraMeasureHeight = notificationActionListLayout != null ? notificationActionListLayout.getExtraMeasureHeight() : 0;
        View view = this.mRemoteInputHistory;
        return (view == null || view.getVisibility() == 8) ? extraMeasureHeight : extraMeasureHeight + this.mRow.getContext().getResources().getDimensionPixelSize(R.dimen.remote_input_history_extra_height);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getHeaderTranslation(boolean z) {
        return (z && this.mCanHideHeader) ? this.mFullHeaderTranslation : (int) this.mHeaderTranslation;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public int isEllipsis() {
        TextView textView = this.mText;
        if (textView == null || textView.getLayout() == null) {
            return 0;
        }
        return this.mText.getLayout().getEllipsisCount(this.mText.getLayout().getLineCount() - 1) > 0 ? 1 : 2;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        ImageView imageView;
        resolveTemplateViews(expandableNotificationRow.mEntry.mSbn);
        super.onContentUpdated(expandableNotificationRow);
        this.mCanHideHeader = this.mAllowHideHeader && this.mNotificationHeader != null && ((imageView = this.mRightIcon) == null || imageView.getVisibility() != 0);
        float f = expandableNotificationRow.mHeaderVisibleAmount;
        if (f != 1.0f) {
            setHeaderVisibleAmount(f);
        }
    }

    public final void resolveTemplateViews(StatusBarNotification statusBarNotification) {
        ActionPendingIntentCancellationHandler actionPendingIntentCancellationHandler;
        Bitmap bitmap;
        Icon largeIcon;
        Bitmap bitmap2;
        ImageView imageView = (ImageView) this.mView.findViewById(android.R.id.splashscreen_branding_view);
        this.mRightIcon = imageView;
        if (imageView != null) {
            Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
            Notification notification2 = statusBarNotification.getNotification();
            if ((!notification2.extras.getBoolean("android.showBigPictureWhenCollapsed") || !notification2.isStyle(Notification.BigPictureStyle.class) || (largeIcon = Notification.BigPictureStyle.getPictureIcon(notification2.extras)) == null) && (largeIcon = notification2.getLargeIcon()) == null && (bitmap2 = notification2.largeIcon) != null) {
                largeIcon = Icon.createWithBitmap(bitmap2);
            }
            imageView.setTag(R.id.image_icon_tag, largeIcon);
            ImageView imageView2 = this.mRightIcon;
            Pools.SimplePool simplePool2 = TransformState.sInstancePool;
            imageView2.setTag(R.id.align_transform_end_tag, Boolean.TRUE);
        }
        ImageView imageView3 = (ImageView) this.mView.findViewById(android.R.id.media_route_list);
        this.mLeftIcon = imageView3;
        if (imageView3 != null) {
            Pools.SimplePool simplePool3 = ImageTransformState.sInstancePool;
            Notification notification3 = statusBarNotification.getNotification();
            Icon largeIcon2 = notification3.getLargeIcon();
            if (largeIcon2 == null && (bitmap = notification3.largeIcon) != null) {
                largeIcon2 = Icon.createWithBitmap(bitmap);
            }
            imageView3.setTag(R.id.image_icon_tag, largeIcon2);
        }
        this.mTitle = (TextView) this.mView.findViewById(android.R.id.title);
        this.mText = (TextView) this.mView.findViewById(16909869);
        View findViewById = this.mView.findViewById(android.R.id.progress);
        if (findViewById instanceof ProgressBar) {
            this.mProgressBar = (ProgressBar) findViewById;
        } else {
            this.mProgressBar = null;
        }
        this.mSmartReplyContainer = this.mView.findViewById(android.R.id.zoom_fit_page);
        this.mActionsContainer = this.mView.findViewById(android.R.id.activity_chooser_view_content);
        this.mActions = this.mView.findViewById(android.R.id.actions_container_layout);
        this.mRemoteInputHistory = this.mView.findViewById(android.R.id.portrait);
        this.mNotificationChildrenCount = (TextView) this.mView.findViewById(android.R.id.icon_menu_presenter);
        NotificationActionListLayout notificationActionListLayout = this.mActions;
        if (notificationActionListLayout != null) {
            int childCount = notificationActionListLayout.getChildCount();
            ArraySet arraySet = new ArraySet(childCount);
            for (int i = 0; i < childCount; i++) {
                Button button = (Button) this.mActions.getChildAt(i);
                PendingIntent pendingIntent = (PendingIntent) button.getTag(android.R.id.resolver_empty_state_icon);
                if (pendingIntent != null) {
                    int identityHashCode = System.identityHashCode(pendingIntent.getTarget().asBinder());
                    arraySet.add(Integer.valueOf(identityHashCode));
                    if (this.mCancelledPendingIntents.contains(Integer.valueOf(identityHashCode))) {
                        disableActionView(button);
                    }
                }
                if (pendingIntent != null) {
                    ActionPendingIntentCancellationHandler actionPendingIntentCancellationHandler2 = new ActionPendingIntentCancellationHandler(pendingIntent, button, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            NotificationTemplateViewWrapper notificationTemplateViewWrapper = NotificationTemplateViewWrapper.this;
                            PendingIntent pendingIntent2 = (PendingIntent) obj;
                            notificationTemplateViewWrapper.mCancelledPendingIntents.add(Integer.valueOf(System.identityHashCode(pendingIntent2.getTarget().asBinder())));
                            NotificationActionListLayout notificationActionListLayout2 = notificationTemplateViewWrapper.mActions;
                            if (notificationActionListLayout2 != null) {
                                int childCount2 = notificationActionListLayout2.getChildCount();
                                for (int i2 = 0; i2 < childCount2; i2++) {
                                    Button button2 = (Button) notificationTemplateViewWrapper.mActions.getChildAt(i2);
                                    if (pendingIntent2.equals((PendingIntent) button2.getTag(android.R.id.resolver_empty_state_icon))) {
                                        notificationTemplateViewWrapper.disableActionView(button2);
                                    }
                                }
                            }
                        }
                    });
                    button.addOnAttachStateChangeListener(actionPendingIntentCancellationHandler2);
                    actionPendingIntentCancellationHandler = actionPendingIntentCancellationHandler2;
                    if (button.isAttachedToWindow()) {
                        actionPendingIntentCancellationHandler2.onViewAttachedToWindow(button);
                        actionPendingIntentCancellationHandler = actionPendingIntentCancellationHandler2;
                    }
                } else {
                    actionPendingIntentCancellationHandler = null;
                }
                ActionPendingIntentCancellationHandler actionPendingIntentCancellationHandler3 = (ActionPendingIntentCancellationHandler) button.getTag(R.id.pending_intent_listener_tag);
                if (actionPendingIntentCancellationHandler3 != null) {
                    actionPendingIntentCancellationHandler3.remove();
                }
                button.setTag(R.id.pending_intent_listener_tag, actionPendingIntentCancellationHandler);
            }
            this.mCancelledPendingIntents.retainAll(arraySet);
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

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void updateSummarize(ExpandableNotificationRow expandableNotificationRow) {
        resolveTemplateViews(expandableNotificationRow.mEntry.mSbn);
        super.onContentUpdated(expandableNotificationRow);
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
