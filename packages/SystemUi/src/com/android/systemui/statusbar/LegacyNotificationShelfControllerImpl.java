package com.android.systemui.statusbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LegacyNotificationShelfControllerImpl implements NotificationShelfController {
    public final ActivatableNotificationViewController mActivatableNotificationViewController;
    public AmbientState mAmbientState;
    public final KeyguardBypassController mKeyguardBypassController;
    public final ViewOnAttachStateChangeListenerC25621 mOnAttachStateChangeListener;
    public final NotificationShelfManager mShelfManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final NotificationShelf mView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.LegacyNotificationShelfControllerImpl$1 */
    public final class ViewOnAttachStateChangeListenerC25621 implements View.OnAttachStateChangeListener {
        public ViewOnAttachStateChangeListenerC25621() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            LegacyNotificationShelfControllerImpl legacyNotificationShelfControllerImpl = LegacyNotificationShelfControllerImpl.this;
            SysuiStatusBarStateController sysuiStatusBarStateController = legacyNotificationShelfControllerImpl.mStatusBarStateController;
            NotificationShelf notificationShelf = legacyNotificationShelfControllerImpl.mView;
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
            synchronized (statusBarStateControllerImpl.mListeners) {
                statusBarStateControllerImpl.addListenerInternalLocked(notificationShelf, 3);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            LegacyNotificationShelfControllerImpl legacyNotificationShelfControllerImpl = LegacyNotificationShelfControllerImpl.this;
            ((StatusBarStateControllerImpl) legacyNotificationShelfControllerImpl.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) legacyNotificationShelfControllerImpl.mView);
        }
    }

    public LegacyNotificationShelfControllerImpl(NotificationShelf notificationShelf, ActivatableNotificationViewController activatableNotificationViewController, KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController, FeatureFlags featureFlags, NotificationShelfManager notificationShelfManager) {
        this.mView = notificationShelf;
        this.mActivatableNotificationViewController = activatableNotificationViewController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mShelfManager = notificationShelfManager;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        notificationShelf.mSensitiveRevealAnimEndabled = false;
        this.mOnAttachStateChangeListener = new ViewOnAttachStateChangeListenerC25621();
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final void bind(AmbientState ambientState, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        final NotificationShelf notificationShelf = this.mView;
        if (notificationShelf.mShelfRefactorFlagEnabled) {
            NotificationShelfController.Companion.getClass();
            throw new IllegalStateException("Code path not supported when Flags.NOTIFICATION_SHELF_REFACTOR is ".concat("enabled").toString());
        }
        notificationShelf.mAmbientState = ambientState;
        notificationShelf.mHostLayoutController = notificationStackScrollLayoutController;
        ViewCompat$$ExternalSyntheticLambda0 viewCompat$$ExternalSyntheticLambda0 = new ViewCompat$$ExternalSyntheticLambda0();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        FeatureFlags featureFlags = notificationStackScrollLayout.mAmbientState.mFeatureFlags;
        NotificationShelfController.assertRefactorFlagDisabled();
        notificationStackScrollLayout.mOnNotificationRemovedListener = viewCompat$$ExternalSyntheticLambda0;
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        notificationShelf.mShelfManager = notificationShelfManager;
        notificationShelfManager.shelf = notificationShelf;
        notificationShelfManager.mNotiSettingContainer = (LinearLayout) notificationShelf.findViewById(R.id.noti_setting_container);
        notificationShelfManager.mNotiSettingIcon = (ImageView) notificationShelf.findViewById(R.id.noti_setting_icon);
        notificationShelfManager.mNotiSettingText = (SwitchableDoubleShadowTextView) notificationShelf.findViewById(R.id.noti_setting);
        notificationShelfManager.mClearAllButton = (TextView) notificationShelf.findViewById(R.id.clear_all);
        notificationShelfManager.mShelfTextArea = (LinearLayout) notificationShelf.findViewById(R.id.notification_shelf_text_area);
        notificationShelfManager.mNotificationIconContainer = (SecShelfNotificationIconContainer) notificationShelf.findViewById(R.id.content);
        notificationShelfManager.updateTextColor();
        int i = QpRune.QUICK_TABLET ? R.dimen.bottom_bar_button_text_size_for_tablet : R.dimen.bottom_bar_button_text_size;
        FontSizeUtils.updateFontSize(notificationShelfManager.mNotiSettingText, i, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(notificationShelfManager.mClearAllButton, i, 0.8f, 1.3f);
        notificationShelfManager.updateShelfButtonBackground();
        notificationShelfManager.statusBarState = notificationShelfManager.statusBarStateController.getState();
        notificationShelfManager.updateShelfHeight();
        notificationShelfManager.updateShelfTextAreaVisibility();
        TextView textView = notificationShelfManager.mClearAllButton;
        if (textView != null) {
            textView.setVisibility(4);
        }
        LinearLayout linearLayout = notificationShelfManager.mNotiSettingContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(4);
        }
        LinearLayout linearLayout2 = notificationShelfManager.mShelfTextArea;
        if (linearLayout2 != null) {
            linearLayout2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager$shelf$1$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    NotificationShelf.this.updateIconsPaddingEnd();
                }
            });
        }
        notificationShelf.updateResources();
        ContrastColorUtil.getInstance(notificationShelf.getContext());
        this.mAmbientState = ambientState;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final boolean canModifyColorOfNotifications() {
        AmbientState ambientState = this.mAmbientState;
        return ambientState.mShadeExpanded && !(ambientState.isOnKeyguard() && this.mKeyguardBypassController.getBypassEnabled());
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final int getIntrinsicHeight() {
        return this.mView.getHeight();
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final NotificationIconContainer getShelfIcons() {
        return this.mView.mShelfIcons;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final NotificationShelf getView() {
        return this.mView;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final void setOnClickListener(LockscreenShadeTransitionController$bindController$1 lockscreenShadeTransitionController$bindController$1) {
        this.mView.mShelfIcons.setOnClickListener(lockscreenShadeTransitionController$bindController$1);
    }
}
