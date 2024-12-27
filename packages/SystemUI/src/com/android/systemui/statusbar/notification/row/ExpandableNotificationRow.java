package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.CallLayout;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.SecPanelTouchBlockHelper;
import com.android.systemui.statusbar.NotificationGroupingUtil;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.ConversationNotificationManager$onEntryViewBound$1;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfoManager;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationGroupHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.SwipeableView;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.DrawableDumpKt;
import com.android.systemui.util.DumpUtilsKt;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import noticolorpicker.NotificationColorPicker;

public class ExpandableNotificationRow extends ActivatableNotificationView implements PluginListener<NotificationMenuRowPlugin>, SwipeableView, NotificationFadeAware.FadeOptimizedNotification, PanelScreenShotLogger.LogProvider {
    public static final AnonymousClass2 TRANSLATE_CONTENT;
    public boolean mAboveShelf;
    public AboveShelfObserver mAboveShelfChangedListener;
    public boolean mAnimationRunning;
    public String mAppName;
    public View.OnClickListener mBubbleClickListener;
    public KeyguardBypassController mBypassController;
    public View mChildAfterViewWhenDismissed;
    public boolean mChildIsExpanding;
    public NotificationChildrenContainer mChildrenContainer;
    public NotificationChildrenContainerLogger mChildrenContainerLogger;
    public ViewStub mChildrenContainerStub;
    public boolean mChildrenExpanded;
    public ColorUpdateLogger mColorUpdateLogger;
    public NotificationDismissibilityProvider mDismissibilityProvider;
    public ExpandableNotificationRowDragController mDragController;
    public boolean mEnableNonGroupedNotificationExpand;
    public NotificationEntry mEntry;
    public boolean mExpandAnimationRunning;
    public final AnonymousClass1 mExpandClickListener;
    public boolean mExpandable;
    public boolean mExpandedWhenPinned;
    public Path mExpandingClipPath;
    public ConversationNotificationManager$onEntryViewBound$1 mExpansionChangedListener;
    public final ExpandableNotificationRow$$ExternalSyntheticLambda0 mExpireRecentlyAlertedFlag;
    public FalsingManager mFalsingManager;
    public boolean mGroupExpansionChanging;
    public GroupExpansionManager mGroupExpansionManager;
    public GroupMembershipManager mGroupMembershipManager;
    public ExpandableNotificationRow mGroupParentWhenDismissed;
    public NotificationGuts mGuts;
    public ViewStub mGutsStub;
    public boolean mHasUserChangedExpansion;
    public float mHeaderVisibleAmount;
    public Consumer mHeadsUpAnimatingAwayListener;
    public HeadsUpManager mHeadsUpManager;
    public boolean mHeadsupDisappearRunning;
    public boolean mHideSensitiveForIntrinsicHeight;
    public int mIconTransformContentShift;
    public boolean mIgnoreLockscreenConstraints;
    public final NotificationInlineImageResolver mImageResolver;
    public boolean mIsCustomBigNotification;
    public boolean mIsCustomHeadsUpNotification;
    public boolean mIsCustomNotification;
    public boolean mIsCustomPublicNotification;
    public boolean mIsFaded;
    public boolean mIsGroupHeaderContainAtMark;
    public boolean mIsHeadsUp;
    public boolean mIsMinimized;
    public boolean mIsPinned;
    public boolean mIsSnoozed;
    public boolean mIsSummaryWithChildren;
    public boolean mIsSystemChildExpanded;
    public boolean mIsSystemExpanded;
    public boolean mJustClicked;
    public boolean mKeepInParentForDismissAnimation;
    public boolean mLastChronometerRunning;
    public NotificationMenuRow mLayoutListener;
    public NotificationContentView[] mLayouts;
    public ExpandableNotificationRowController.AnonymousClass2 mLogger;
    public String mLoggingKey;
    public ExpandableNotificationRowController$$ExternalSyntheticLambda0 mLongPressListener;
    public int mMaxExpandedHeight;
    public int mMaxHeadsUpHeight;
    public int mMaxHeadsUpHeightBeforeN;
    public int mMaxHeadsUpHeightBeforeP;
    public int mMaxHeadsUpHeightBeforeS;
    public int mMaxHeadsUpHeightIncreased;
    public int mMaxSmallHeight;
    public int mMaxSmallHeightBeforeN;
    public int mMaxSmallHeightBeforeP;
    public int mMaxSmallHeightBeforeS;
    public int mMaxSmallHeightLarge;
    public NotificationMenuRowPlugin mMenuRow;
    public MetricsLogger mMetricsLogger;
    public boolean mMustStayOnScreen;
    public int mNotificationColor;
    public NotificationGutsManager mNotificationGutsManager;
    public int mNotificationLaunchHeight;
    public ExpandableNotificationRow mNotificationParent;
    public View.OnClickListener mOnClickListener;
    public NotificationClicker.AnonymousClass1 mOnDragSuccessListener;
    public OnExpandClickListener mOnExpandClickListener;
    public ExpandableNotificationRow$$ExternalSyntheticLambda4 mOnFeedbackClickListener;
    public Runnable mOnIntrinsicHeightReachedRunnable;
    public boolean mOnKeyguard;
    public OnUserInteractionCallback mOnUserInteractionCallback;
    public PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    public NotificationContentView mPrivateLayout;
    public NotificationContentView mPublicLayout;
    public RowContentBindStage mRowContentBindStage;
    public boolean mSaveSpaceOnLockscreen;
    public BooleanSupplier mSecureStateProvider;
    public boolean mSensitive;
    public boolean mSensitiveHiddenInGeneral;
    public boolean mShowNoBackground;
    public boolean mShowPublicExpander;
    public boolean mShowingPublic;
    public boolean mShowingPublicInitialized;
    public boolean mSkipRemovalAnim;
    public final float mSmallRoundness;
    public StatusBarStateController mStatusBarStateController;
    public Animator mTranslateAnim;
    public ArrayList mTranslateableViews;
    public boolean mUseIncreasedCollapsedHeight;
    public boolean mUseIncreasedHeadsUpHeight;
    public boolean mUserExpanded;
    public boolean mUserLocked;
    public static final long RECENTLY_ALERTED_THRESHOLD_MS = TimeUnit.SECONDS.toMillis(30);
    public static final SourceType$Companion$from$1 BASE_VALUE = SourceType.from("BaseValue");
    public static final SourceType$Companion$from$1 FROM_PARENT = SourceType.from("FromParent(ENR)");

    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            boolean z;
            NotificationChildrenContainer notificationChildrenContainer;
            ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
            if (expandableNotificationRow.mIsMinimized) {
                if (((GroupMembershipManagerImpl) expandableNotificationRow.mGroupMembershipManager).isGroupSummary(expandableNotificationRow.mEntry) && (notificationChildrenContainer = ExpandableNotificationRow.this.mChildrenContainer) != null) {
                    notificationChildrenContainer.mWasLowPriorityShowing = false;
                }
            }
            if (!ExpandableNotificationRow.this.shouldShowPublic()) {
                ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                if (expandableNotificationRow2.mIsMinimized) {
                    expandableNotificationRow2.isExpanded(false);
                }
                ExpandableNotificationRow expandableNotificationRow3 = ExpandableNotificationRow.this;
                if (((GroupMembershipManagerImpl) expandableNotificationRow3.mGroupMembershipManager).isGroupSummary(expandableNotificationRow3.mEntry)) {
                    ExpandableNotificationRow expandableNotificationRow4 = ExpandableNotificationRow.this;
                    expandableNotificationRow4.mGroupExpansionChanging = true;
                    boolean isGroupExpanded = ((GroupExpansionManagerImpl) expandableNotificationRow4.mGroupExpansionManager).isGroupExpanded(expandableNotificationRow4.mEntry);
                    ExpandableNotificationRow expandableNotificationRow5 = ExpandableNotificationRow.this;
                    GroupExpansionManager groupExpansionManager = expandableNotificationRow5.mGroupExpansionManager;
                    NotificationEntry notificationEntry = expandableNotificationRow5.mEntry;
                    GroupExpansionManagerImpl groupExpansionManagerImpl = (GroupExpansionManagerImpl) groupExpansionManager;
                    groupExpansionManagerImpl.setGroupExpanded(notificationEntry, !groupExpansionManagerImpl.isGroupExpanded(notificationEntry));
                    boolean isGroupExpanded2 = groupExpansionManagerImpl.isGroupExpanded(notificationEntry);
                    ExpandableNotificationRow expandableNotificationRow6 = ExpandableNotificationRow.this;
                    ((StatusBarNotificationPresenter) expandableNotificationRow6.mOnExpandClickListener).onExpandClicked(expandableNotificationRow6.mEntry, isGroupExpanded2);
                    ExpandableNotificationRow.this.mMetricsLogger.action(VolteConstants.ErrorCode.REQUEST_TIMEOUT, isGroupExpanded2);
                    ExpandableNotificationRow.this.onExpansionChanged(true, isGroupExpanded);
                    return;
                }
            }
            if (ExpandableNotificationRow.this.mEnableNonGroupedNotificationExpand) {
                if (view.isAccessibilityFocused()) {
                    ExpandableNotificationRow.this.mPrivateLayout.mFocusOnVisibilityChange = true;
                }
                ExpandableNotificationRow expandableNotificationRow7 = ExpandableNotificationRow.this;
                if (expandableNotificationRow7.mIsPinned) {
                    z = !expandableNotificationRow7.mExpandedWhenPinned;
                    expandableNotificationRow7.mExpandedWhenPinned = z;
                    ConversationNotificationManager$onEntryViewBound$1 conversationNotificationManager$onEntryViewBound$1 = expandableNotificationRow7.mExpansionChangedListener;
                    if (conversationNotificationManager$onEntryViewBound$1 != null) {
                        conversationNotificationManager$onEntryViewBound$1.onExpansionChanged(z);
                    }
                } else {
                    z = !expandableNotificationRow7.isExpanded(false);
                    ExpandableNotificationRow.this.setUserExpanded(z, false);
                }
                ExpandableNotificationRow.this.notifyHeightChanged(true);
                ExpandableNotificationRow expandableNotificationRow8 = ExpandableNotificationRow.this;
                ((StatusBarNotificationPresenter) expandableNotificationRow8.mOnExpandClickListener).onExpandClicked(expandableNotificationRow8.mEntry, z);
                ExpandableNotificationRow.this.mMetricsLogger.action(407, z);
            }
        }
    }

    public final class NotificationViewState extends ExpandableViewState {
        public /* synthetic */ NotificationViewState(int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.mExpandAnimationRunning) {
                    return;
                }
                if (expandableNotificationRow.mChildIsExpanding) {
                    setZTranslation(expandableNotificationRow.getTranslationZ());
                    this.clipTopAmount = expandableNotificationRow.mClipTopAmount;
                }
                super.animateTo(view, animationProperties);
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    final NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                    int size = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
                    ViewState viewState = new ViewState();
                    float groupExpandFraction = notificationChildrenContainer.getGroupExpandFraction();
                    if (notificationChildrenContainer.isSkipAnim()) {
                        notificationChildrenContainer.applyState$1();
                        return;
                    }
                    boolean z = (notificationChildrenContainer.mChildrenExpanded && notificationChildrenContainer.mShowDividersWhenExpanded) || ((notificationChildrenContainer.mUserLocked || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) && !notificationChildrenContainer.mHideDividersDuringExpand);
                    for (int i = size - 1; i >= 0; i--) {
                        final ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i);
                        ExpandableViewState expandableViewState = expandableNotificationRow2.mViewState;
                        animationProperties.mAnimationEndAction = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                NotificationChildrenContainer notificationChildrenContainer2 = NotificationChildrenContainer.this;
                                ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                                if (notificationChildrenContainer2.mContainingNotification.areGutsExposed()) {
                                    return;
                                }
                                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow3.mBackgroundNormal;
                                notificationBackgroundView.mBottomClipRounded = false;
                                notificationBackgroundView.invalidate();
                            }
                        };
                        expandableNotificationRow2.setTag(R.id.group_children_clip_top_duration_value_tag, 340);
                        expandableViewState.animateTo(expandableNotificationRow2, animationProperties);
                        expandableNotificationRow2.setTag(R.id.group_children_clip_top_duration_value_tag, null);
                        View view2 = (View) ((ArrayList) notificationChildrenContainer.mDividers).get(i);
                        viewState.initFrom(view2);
                        viewState.setYTranslation(expandableViewState.mYTranslation - notificationChildrenContainer.mDividerHeight);
                        float f = (!notificationChildrenContainer.mChildrenExpanded || expandableViewState.mAlpha == 0.0f) ? 0.0f : notificationChildrenContainer.mDividerAlpha;
                        if (notificationChildrenContainer.mUserLocked) {
                            float f2 = expandableViewState.mAlpha;
                            if (f2 != 0.0f) {
                                f = NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mDividerAlpha, Math.min(f2, groupExpandFraction));
                            }
                        }
                        viewState.hidden = !z;
                        viewState.setAlpha(f);
                        if (!z) {
                            viewState.setAlpha(0.0f);
                            view2.setAlpha(0.0f);
                        }
                        viewState.animateTo(view2, animationProperties);
                        expandableNotificationRow2.setFakeShadowIntensity(0, 0.0f, 0.0f, 0);
                    }
                    if (notificationChildrenContainer.getFirstChild() != null) {
                        for (int i2 = 0; i2 < notificationChildrenContainer.mExpanderViewStates.size() && i2 < notificationChildrenContainer.getFirstChild().getExpandButtons().size(); i2++) {
                            ((ViewState) notificationChildrenContainer.mExpanderViewStates.get(i2)).animateTo((View) notificationChildrenContainer.getFirstChild().getExpandButtons().get(i2), animationProperties);
                        }
                        for (int i3 = 0; i3 < notificationChildrenContainer.mChildrenCountViewStates.size() && i3 < notificationChildrenContainer.getFirstChild().getChildrenCountText().size(); i3++) {
                            ((ViewState) notificationChildrenContainer.mChildrenCountViewStates.get(i3)).animateTo((View) notificationChildrenContainer.getFirstChild().getChildrenCountText().get(i3), animationProperties);
                        }
                    }
                    if (notificationChildrenContainer.mOverflowNumber != null) {
                        if (notificationChildrenContainer.mNeverAppliedGroupState) {
                            ViewState viewState2 = notificationChildrenContainer.mGroupOverFlowState;
                            float f3 = viewState2.mAlpha;
                            viewState2.setAlpha(0.0f);
                            notificationChildrenContainer.mGroupOverFlowState.applyToView(notificationChildrenContainer.mOverflowNumber);
                            notificationChildrenContainer.mGroupOverFlowState.setAlpha(f3);
                            notificationChildrenContainer.mNeverAppliedGroupState = false;
                        }
                        notificationChildrenContainer.mGroupOverFlowState.animateTo(notificationChildrenContainer.mOverflowNumber, animationProperties);
                    }
                    if (notificationChildrenContainer.mGroupHeader != null) {
                        if (notificationChildrenContainer.mContainingNotification.isGroupExpanded()) {
                            notificationChildrenContainer.mHeaderViewState.applyToView(notificationChildrenContainer.mNotificationHeaderExpanded);
                        } else {
                            notificationChildrenContainer.mHeaderViewState.applyToView(notificationChildrenContainer.mGroupHeader);
                        }
                    }
                    notificationChildrenContainer.updateChildrenClipping();
                }
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.mExpandAnimationRunning) {
                    return;
                }
                if (expandableNotificationRow.mChildIsExpanding) {
                    setZTranslation(expandableNotificationRow.getTranslationZ());
                    this.clipTopAmount = expandableNotificationRow.mClipTopAmount;
                }
                super.applyToView(view);
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    expandableNotificationRow.mChildrenContainer.applyState$1();
                }
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        public final void onYTranslationAnimationFinished(View view) {
            super.onYTranslationAnimationFinished(view);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.mHeadsupDisappearRunning) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(false);
                }
            }
        }

        private NotificationViewState() {
        }
    }

    public interface OnExpandClickListener {
    }

    /* renamed from: $r8$lambda$-m9ShXoKTwBLcNnosIeqOTeZZvE, reason: not valid java name */
    public static void m2221$r8$lambda$m9ShXoKTwBLcNnosIeqOTeZZvE(ExpandableNotificationRow expandableNotificationRow, ExpandableNotificationRowController$$ExternalSyntheticLambda0 expandableNotificationRowController$$ExternalSyntheticLambda0, View view) {
        NotificationMenuRowPlugin.MenuItem feedbackMenuItem;
        expandableNotificationRow.createMenu();
        NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
        if (notificationMenuRowPlugin == null || (feedbackMenuItem = notificationMenuRowPlugin.getFeedbackMenuItem(((FrameLayout) expandableNotificationRow).mContext)) == null) {
            return;
        }
        ((NotificationGutsManager) expandableNotificationRowController$$ExternalSyntheticLambda0.f$0).openGuts(expandableNotificationRow, view.getWidth() / 2, view.getHeight() / 2, feedbackMenuItem);
    }

    static {
        SystemProperties.getInt("persist.notifications.extra_measure_delay_ms", 150);
        TRANSLATE_CONTENT = new FloatProperty("translate") { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.2
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((ExpandableNotificationRow) obj).getTranslation());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                ((ExpandableNotificationRow) obj).setTranslation(f);
            }
        };
    }

    public ExpandableNotificationRow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, context);
        throw new UnsupportedOperationException("Insecure constructor");
    }

    public static void setChronometerRunningForChild(View view, boolean z) {
        if (view != null) {
            View findViewById = view.findViewById(android.R.id.colorMode);
            if (findViewById instanceof Chronometer) {
                ((Chronometer) findViewById).setStarted(z);
            }
        }
    }

    public static void setIconAnimationRunningForChild(View view, boolean z) {
        if (view != null) {
            setImageViewAnimationRunning((ImageView) view.findViewById(android.R.id.icon), z);
            setImageViewAnimationRunning((ImageView) view.findViewById(android.R.id.splashscreen_branding_view), z);
        }
    }

    public static void setImageViewAnimationRunning(ImageView imageView, boolean z) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                if (z) {
                    animationDrawable.start();
                    return;
                } else {
                    animationDrawable.stop();
                    return;
                }
            }
            if (drawable instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                if (z) {
                    animatedVectorDrawable.start();
                } else {
                    animatedVectorDrawable.stop();
                }
            }
        }
    }

    public final void addChildNotification(ExpandableNotificationRow expandableNotificationRow, int i) {
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        ArrayList arrayList;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager2;
        ArrayList arrayList2;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager3;
        StatusBarNotification statusBarNotification;
        StatusBarNotification statusBarNotification2;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager4;
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter2;
        if (this.mChildrenContainer == null) {
            this.mChildrenContainerStub.inflate();
        }
        if (expandableNotificationRow.mKeepInParentForDismissAnimation) {
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                NotificationEntry notificationEntry2 = this.mEntry;
                NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                NotificationRowLogger$logSkipAttachingKeepInParentChild$2 notificationRowLogger$logSkipAttachingKeepInParentChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logSkipAttachingKeepInParentChild$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return MotionLayout$$ExternalSyntheticOutline0.m("Skipping to attach ", logMessage.getStr1(), " to ", logMessage.getStr2(), ", because it still flagged to keep in parent");
                    }
                };
                LogBuffer logBuffer = notificationRowLogger.buffer;
                LogMessage obtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$logSkipAttachingKeepInParentChild$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logBuffer.commit(obtain);
                return;
            }
            return;
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        notificationChildrenContainer.getClass();
        if (expandableNotificationRow.getParent() != null) {
            expandableNotificationRow.removeFromTransientContainerForAdditionTo(notificationChildrenContainer);
        }
        int size = i < 0 ? ((ArrayList) notificationChildrenContainer.mAttachedChildren).size() : i;
        ((ArrayList) notificationChildrenContainer.mAttachedChildren).add(size, expandableNotificationRow);
        notificationChildrenContainer.addView(expandableNotificationRow);
        expandableNotificationRow.setUserLocked(notificationChildrenContainer.mUserLocked);
        View inflateDivider = notificationChildrenContainer.inflateDivider();
        notificationChildrenContainer.addView(inflateDivider);
        ((ArrayList) notificationChildrenContainer.mDividers).add(size, inflateDivider);
        boolean z = expandableNotificationRow.mIsLastChild | (expandableNotificationRow.mContentTransformationAmount != 0.0f);
        expandableNotificationRow.mIsLastChild = false;
        expandableNotificationRow.mContentTransformationAmount = 0.0f;
        if (z && !expandableNotificationRow.mExpandAnimationRunning) {
            float f = expandableNotificationRow.mIconTransformContentShift * (-expandableNotificationRow.mContentTransformationAmount);
            float interpolation = ((PathInterpolator) Interpolators.ALPHA_OUT).getInterpolation(Math.min((1.0f - expandableNotificationRow.mContentTransformationAmount) / 0.5f, 1.0f));
            if (expandableNotificationRow.mIsLastChild) {
                f *= 0.4f;
            }
            expandableNotificationRow.mContentTranslation = f;
            if (!expandableNotificationRow.mIsLastChild) {
                interpolation = 1.0f;
            }
            for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                notificationContentView.setAlpha(interpolation);
                notificationContentView.setTranslationY(f);
            }
            NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
            if (notificationChildrenContainer2 != null) {
                notificationChildrenContainer2.setAlpha(interpolation);
                expandableNotificationRow.mChildrenContainer.setTranslationY(f);
            }
        }
        expandableNotificationRow.setNotificationFaded(notificationChildrenContainer.mContainingNotificationIsFaded);
        ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
        if (expandableViewState != null) {
            expandableViewState.cancelAnimations(expandableNotificationRow);
            ValueAnimator valueAnimator = expandableNotificationRow.mAppearAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                expandableNotificationRow.mAppearAnimator = null;
            }
            expandableNotificationRow.enableAppearDrawing(false);
            expandableNotificationRow.setHeadsUpAnimatingAway(false);
        }
        if (i == 0 && !notificationChildrenContainer.mExpanderViewStates.isEmpty()) {
            Iterator it = notificationChildrenContainer.mExpanderViewStates.iterator();
            while (it.hasNext()) {
                ((ViewState) it.next()).setAlpha(1.0f);
            }
        }
        notificationChildrenContainer.applyRoundnessAndInvalidate();
        onAttachedChildrenCountChanged();
        expandableNotificationRow.setIsChildInGroup(this, true);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
            SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(expandableNotificationRow.mEntry.mKey);
            NotificationEntry notificationEntry3 = (NotificationEntry) subscreenDeviceModelParent.mMainListAddEntryHashMap.get(expandableNotificationRow.mEntry.mKey);
            if (subscreenDeviceModelParent.isShownGroup() && mainListHashMapItem == null && notificationEntry3 == null) {
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification;
                SubscreenNotificationInfo subscreenNotificationInfo = (subscreenSubRoomNotification == null || (subscreenNotificationGroupAdapter2 = subscreenSubRoomNotification.mNotificationGroupAdapter) == null) ? null : subscreenNotificationGroupAdapter2.mSummaryInfo;
                SubscreenNotificationInfo createItemsData = (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager4 = subscreenSubRoomNotification.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager4.createItemsData(expandableNotificationRow);
                if (StringsKt__StringsJVMKt.equals((subscreenNotificationInfo == null || (statusBarNotification2 = subscreenNotificationInfo.mSbn) == null) ? null : statusBarNotification2.getGroupKey(), (createItemsData == null || (statusBarNotification = createItemsData.mSbn) == null) ? null : statusBarNotification.getGroupKey(), false)) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelParent.mSubRoomNotification;
                    Integer valueOf = (subscreenSubRoomNotification2 == null || (subscreenNotificationInfoManager3 = subscreenSubRoomNotification2.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager3.mGroupDataArray.size());
                    Intrinsics.checkNotNull(valueOf);
                    int intValue = valueOf.intValue();
                    for (int i2 = 0; i2 < intValue; i2++) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent.mSubRoomNotification;
                        SubscreenNotificationInfo subscreenNotificationInfo2 = (subscreenSubRoomNotification3 == null || (subscreenNotificationInfoManager2 = subscreenSubRoomNotification3.mNotificationInfoManager) == null || (arrayList2 = subscreenNotificationInfoManager2.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList2.get(i2);
                        if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo2 != null ? subscreenNotificationInfo2.mKey : null, createItemsData != null ? createItemsData.mKey : null, false)) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("addChildNotification parent - already Item  : ", createItemsData != null ? createItemsData.mKey : null, "S.S.N.");
                        }
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification4 = subscreenDeviceModelParent.mSubRoomNotification;
                    if (subscreenSubRoomNotification4 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification4.mNotificationInfoManager) != null && (arrayList = subscreenNotificationInfoManager.mGroupDataArray) != null) {
                        arrayList.add(intValue, createItemsData);
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification5 = subscreenDeviceModelParent.mSubRoomNotification;
                    if (subscreenSubRoomNotification5 != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification5.mNotificationGroupAdapter) != null) {
                        subscreenNotificationGroupAdapter.mObservable.notifyItemRangeInserted(intValue, 1);
                    }
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("addChildNotification parent - add Item  : ", createItemsData != null ? createItemsData.mKey : null, "S.S.N.");
                }
                SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent, expandableNotificationRow.mEntry);
            }
        }
        initGroupHeaderContainAtMark();
    }

    public final void addTransientView(View view, int i) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                NotificationEntry notificationEntry2 = this.mEntry;
                NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                NotificationRowLogger$logAddTransientRow$2 notificationRowLogger$logAddTransientRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logAddTransientRow$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        int int1 = logMessage.getInt1();
                        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("addTransientRow to row: childKey: ", str1, " -- containerKey: ", str2, " -- index: ");
                        m.append(int1);
                        return m.toString();
                    }
                };
                LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
                LogMessage obtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$logAddTransientRow$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logMessageImpl.int1 = i;
                logBuffer.commit(obtain);
            }
        }
        super.addTransientView(view, i);
    }

    public final String appendTraceStyleTag(String str) {
        if (!Trace.isEnabled()) {
            return str;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "(");
        m.append(this.mEntry.getNotificationStyle());
        m.append(")");
        return m.toString();
    }

    public final void applyAudiblyAlertedRecently(boolean z) {
        if (this.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = notificationChildrenContainer.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper != null) {
                notificationHeaderViewWrapper.setRecentlyAudiblyAlerted(z);
            }
            NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = notificationChildrenContainer.mMinimizedGroupHeaderWrapper;
            if (notificationHeaderViewWrapper2 != null) {
                notificationHeaderViewWrapper2.setRecentlyAudiblyAlerted(z);
            }
            NotificationGroupHeaderViewWrapper notificationGroupHeaderViewWrapper = notificationChildrenContainer.mNotificationHeaderWrapperExpanded;
            if (notificationGroupHeaderViewWrapper != null) {
                notificationGroupHeaderViewWrapper.setRecentlyAudiblyAlerted(z);
            }
        }
        NotificationContentView notificationContentView = this.mPrivateLayout;
        if (notificationContentView.mContractedChild != null) {
            notificationContentView.mContractedWrapper.setRecentlyAudiblyAlerted(z);
        }
        if (notificationContentView.mExpandedChild != null) {
            notificationContentView.mExpandedWrapper.setRecentlyAudiblyAlerted(z);
        }
        if (notificationContentView.mHeadsUpChild != null) {
            notificationContentView.mHeadsUpWrapper.setRecentlyAudiblyAlerted(z);
        }
        NotificationContentView notificationContentView2 = this.mPublicLayout;
        if (notificationContentView2.mContractedChild != null) {
            notificationContentView2.mContractedWrapper.setRecentlyAudiblyAlerted(z);
        }
        if (notificationContentView2.mExpandedChild != null) {
            notificationContentView2.mExpandedWrapper.setRecentlyAudiblyAlerted(z);
        }
        if (notificationContentView2.mHeadsUpChild != null) {
            notificationContentView2.mHeadsUpWrapper.setRecentlyAudiblyAlerted(z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.Roundable
    public final void applyRoundnessAndInvalidate() {
        if (this.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            RoundableState roundableState = ((ExpandableOutlineView) this).mRoundableState;
            notificationChildrenContainer.requestRoundness(roundableState.topRoundness, roundableState.bottomRoundness, FROM_PARENT, false);
        }
        super.applyRoundnessAndInvalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean areChildrenExpanded() {
        return this.mChildrenExpanded;
    }

    public final boolean areGutsExposed() {
        NotificationGuts notificationGuts = this.mGuts;
        return notificationGuts != null && notificationGuts.mExposed;
    }

    public final boolean canShowHeadsUp() {
        KeyguardBypassController keyguardBypassController;
        if (!this.mOnKeyguard) {
            return true;
        }
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if ((statusBarStateController != null && statusBarStateController.isDozing()) || (keyguardBypassController = this.mBypassController) == null || keyguardBypassController.getBypassEnabled()) {
            return true;
        }
        if (this.mEntry.isStickyAndNotDemoted()) {
            return this.mIgnoreLockscreenConstraints || !this.mSaveSpaceOnLockscreen;
        }
        return false;
    }

    public final boolean canViewBeCleared() {
        return this.mEntry.isClearable() && !(shouldShowPublic() && this.mSensitiveHiddenInGeneral);
    }

    public final boolean canViewBeDismissed$1() {
        return (((NotificationDismissibilityProviderImpl) this.mDismissibilityProvider).nonDismissableEntryKeys.contains(this.mEntry.mKey) ^ true) && !(shouldShowPublic() && this.mSensitiveHiddenInGeneral);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public final boolean childNeedsClipping(View view) {
        if (view instanceof NotificationContentView) {
            NotificationContentView notificationContentView = (NotificationContentView) view;
            if (isClippingNeeded()) {
                return true;
            }
            if (hasRoundedCorner()) {
                RoundableState roundableState = ((ExpandableOutlineView) this).mRoundableState;
                float f = roundableState.topRoundness;
                boolean z = roundableState.bottomRoundness != 0.0f;
                NotificationViewWrapper visibleWrapper = notificationContentView.getVisibleWrapper(notificationContentView.mVisibleType);
                boolean shouldClipToRounding = visibleWrapper == null ? false : visibleWrapper.shouldClipToRounding(z);
                if (notificationContentView.mUserExpanding) {
                    NotificationViewWrapper visibleWrapper2 = notificationContentView.getVisibleWrapper(notificationContentView.mTransformationStartVisibleType);
                    shouldClipToRounding |= visibleWrapper2 != null ? visibleWrapper2.shouldClipToRounding(z) : false;
                }
                if (shouldClipToRounding) {
                    return true;
                }
            }
        } else if (view == this.mChildrenContainer) {
            if (isClippingNeeded() || hasRoundedCorner()) {
                return false;
            }
        } else if (view instanceof NotificationGuts) {
            return hasRoundedCorner();
        }
        return super.childNeedsClipping(view);
    }

    public final boolean childrenRequireOverlappingRendering() {
        if (this.mEntry.mSbn.getNotification().isColorized()) {
            return true;
        }
        NotificationContentView showingLayout = getShowingLayout();
        if (showingLayout != null) {
            RemoteInputView remoteInputView = showingLayout.mHeadsUpRemoteInput;
            if (remoteInputView != null && remoteInputView.isActive()) {
                return true;
            }
            RemoteInputView remoteInputView2 = showingLayout.mExpandedRemoteInput;
            if (remoteInputView2 != null && remoteInputView2.isActive()) {
                return true;
            }
        }
        return false;
    }

    public final void collectVisibleLocations(Map map) {
        if (getVisibility() != 0) {
            return;
        }
        ((HashMap) map).put(this.mEntry.mKey, Integer.valueOf(this.mViewState.location));
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null) {
            return;
        }
        List list = notificationChildrenContainer.mAttachedChildren;
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ((ExpandableNotificationRow) arrayList.get(i)).collectVisibleLocations(map);
            i++;
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final ExpandableViewState createExpandableViewState() {
        return new NotificationViewState(0);
    }

    public final NotificationMenuRowPlugin createMenu() {
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin == null) {
            return null;
        }
        if (notificationMenuRowPlugin.getMenuView() == null) {
            this.mMenuRow.createMenu(this, this.mEntry.mSbn);
            this.mMenuRow.setAppName(this.mAppName);
            addView(this.mMenuRow.getMenuView(), 0, new FrameLayout.LayoutParams(-1, -1));
        }
        return this.mMenuRow;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        canvas.save();
        Path path = this.mExpandingClipPath;
        if (path != null && (this.mExpandAnimationRunning || this.mChildIsExpanding)) {
            canvas.clipPath(path);
        }
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public final void doLongClickCallback(int i, int i2) {
        createMenu();
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        doLongClickCallback(i, i2, notificationMenuRowPlugin != null ? notificationMenuRowPlugin.getLongpressMenuItem(((FrameLayout) this).mContext) : null);
    }

    public final void doSmartActionClick(int i, int i2) {
        createMenu();
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        NotificationMenuRowPlugin.MenuItem longpressMenuItem = notificationMenuRowPlugin != null ? notificationMenuRowPlugin.getLongpressMenuItem(((FrameLayout) this).mContext) : null;
        if (longpressMenuItem.getGutsView() instanceof NotificationConversationInfo) {
            ((NotificationConversationInfo) longpressMenuItem.getGutsView()).setSelectedAction(2);
        }
        doLongClickCallback(i, i2, longpressMenuItem);
    }

    public final void dragAndDropSuccess() {
        RemoteInputCoordinator remoteInputCoordinator;
        NotificationClicker.AnonymousClass1 anonymousClass1 = this.mOnDragSuccessListener;
        if (anonymousClass1 != null) {
            NotificationEntry notificationEntry = this.mEntry;
            StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) NotificationClicker.this.mNotificationActivityStarter;
            NotificationVisibility obtain = ((NotificationVisibilityProviderImpl) statusBarNotificationActivityStarter.mVisibilityProvider).obtain(notificationEntry);
            boolean shouldAutoCancel = StatusBarNotificationActivityStarter.shouldAutoCancel(notificationEntry.mSbn);
            String str = notificationEntry.mKey;
            if (shouldAutoCancel || ((remoteInputCoordinator = statusBarNotificationActivityStarter.mRemoteInputManager.mRemoteInputListener) != null && remoteInputCoordinator.isNotificationKeptForRemoteInputHistory(str))) {
                statusBarNotificationActivityStarter.mMainThreadHandler.post(new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda6(statusBarNotificationActivityStarter, ((OnUserInteractionCallbackImpl) statusBarNotificationActivityStarter.mOnUserInteractionCallback).registerFutureDismissal(notificationEntry, 1), 1));
            }
            statusBarNotificationActivityStarter.mClickNotifier.onNotificationClick(str, obtain);
            statusBarNotificationActivityStarter.mIsCollapsingToShowActivityOverLockscreen = false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, final String[] strArr) {
        final IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("Notification: " + this.mEntry.mKey);
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
                IndentingPrintWriter indentingPrintWriter = asIndenting;
                String[] strArr2 = strArr;
                SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                expandableNotificationRow.getClass();
                indentingPrintWriter.println(expandableNotificationRow);
                indentingPrintWriter.print("visibility: " + expandableNotificationRow.getVisibility());
                indentingPrintWriter.print(", alpha: " + expandableNotificationRow.getAlpha());
                indentingPrintWriter.print(", translation: " + expandableNotificationRow.getTranslation());
                indentingPrintWriter.print(", entry dismissable: " + (((NotificationDismissibilityProviderImpl) expandableNotificationRow.mDismissibilityProvider).nonDismissableEntryKeys.contains(expandableNotificationRow.mEntry.mKey) ^ true));
                StringBuilder sb = new StringBuilder(", mOnUserInteractionCallback null: ");
                sb.append(expandableNotificationRow.mOnUserInteractionCallback == null);
                indentingPrintWriter.print(sb.toString());
                indentingPrintWriter.print(", removed: false");
                indentingPrintWriter.print(", expandAnimationRunning: " + expandableNotificationRow.mExpandAnimationRunning);
                indentingPrintWriter.print(", mShowingPublic: " + expandableNotificationRow.mShowingPublic);
                indentingPrintWriter.print(", mShowingPublicInitialized: " + expandableNotificationRow.mShowingPublicInitialized);
                NotificationContentView showingLayout = expandableNotificationRow.getShowingLayout();
                StringBuilder sb2 = new StringBuilder(", privateShowing: ");
                sb2.append(showingLayout == expandableNotificationRow.mPrivateLayout);
                indentingPrintWriter.print(sb2.toString());
                indentingPrintWriter.print(", mShowNoBackground: " + expandableNotificationRow.mShowNoBackground);
                if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
                    indentingPrintWriter.print(", inflationWakelock: " + expandableNotificationRow.mEntry.mInflationWakeLock);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.print("Heights: ");
                indentingPrintWriter.print("intrinsic", Integer.valueOf(expandableNotificationRow.getIntrinsicHeight()));
                indentingPrintWriter.print("actual", Integer.valueOf(expandableNotificationRow.mActualHeight));
                indentingPrintWriter.print("maxContent", Integer.valueOf(expandableNotificationRow.getMaxContentHeight()));
                indentingPrintWriter.print("maxExpanded", Integer.valueOf(expandableNotificationRow.getMaxExpandHeight()));
                indentingPrintWriter.print("collapsed", Integer.valueOf(expandableNotificationRow.getCollapsedHeight()));
                indentingPrintWriter.print("headsup", Integer.valueOf(expandableNotificationRow.getHeadsUpHeight()));
                indentingPrintWriter.print("headsup  without header", Integer.valueOf(expandableNotificationRow.getHeadsUpHeightWithoutHeader()));
                indentingPrintWriter.print("minHeight", Integer.valueOf(expandableNotificationRow.getMinHeight(false)));
                indentingPrintWriter.print("pinned headsup", Integer.valueOf(expandableNotificationRow.getPinnedHeadsUpHeight(true)));
                indentingPrintWriter.println();
                indentingPrintWriter.print("Intrinsic Height Factors: ");
                indentingPrintWriter.print("isUserLocked()", Boolean.valueOf(expandableNotificationRow.mUserLocked));
                indentingPrintWriter.print("isChildInGroup()", Boolean.valueOf(expandableNotificationRow.isChildInGroup()));
                indentingPrintWriter.print("isGroupExpanded()", Boolean.valueOf(expandableNotificationRow.isGroupExpanded()));
                indentingPrintWriter.print("sensitive", Boolean.valueOf(expandableNotificationRow.mSensitive));
                indentingPrintWriter.print("hideSensitiveForIntrinsicHeight", Boolean.valueOf(expandableNotificationRow.mHideSensitiveForIntrinsicHeight));
                indentingPrintWriter.print("isSummaryWithChildren", Boolean.valueOf(expandableNotificationRow.mIsSummaryWithChildren));
                indentingPrintWriter.print("canShowHeadsUp()", Boolean.valueOf(expandableNotificationRow.canShowHeadsUp()));
                indentingPrintWriter.print("isHeadsUpState()", Boolean.valueOf(expandableNotificationRow.isHeadsUpState()));
                indentingPrintWriter.print("isPinned()", Boolean.valueOf(expandableNotificationRow.mIsPinned));
                indentingPrintWriter.print("headsupDisappearRunning", Boolean.valueOf(expandableNotificationRow.mHeadsupDisappearRunning));
                indentingPrintWriter.print("isExpanded()", Boolean.valueOf(expandableNotificationRow.isExpanded(false)));
                indentingPrintWriter.println();
                indentingPrintWriter.print("contentView visibility: " + showingLayout.getVisibility());
                indentingPrintWriter.print(", alpha: " + showingLayout.getAlpha());
                indentingPrintWriter.print(", clipBounds: " + showingLayout.getClipBounds());
                indentingPrintWriter.print(", contentHeight: " + showingLayout.mContentHeight);
                indentingPrintWriter.print(", visibleType: " + showingLayout.mVisibleType);
                View viewForVisibleType = showingLayout.getViewForVisibleType(showingLayout.mVisibleType);
                indentingPrintWriter.print(", visibleView ");
                if (viewForVisibleType != null) {
                    indentingPrintWriter.print(" visibility: " + viewForVisibleType.getVisibility());
                    indentingPrintWriter.print(", alpha: " + viewForVisibleType.getAlpha());
                    indentingPrintWriter.print(", clipBounds: " + viewForVisibleType.getClipBounds());
                } else {
                    indentingPrintWriter.print("null");
                }
                indentingPrintWriter.println();
                IndentingPrintWriter asIndenting2 = DumpUtilsKt.asIndenting(indentingPrintWriter);
                asIndenting2.print("ContentDimensions: ");
                int i = showingLayout.mVisibleType;
                asIndenting2.print("visibleType(String)", i != 0 ? i != 1 ? i != 2 ? i != 3 ? PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE : "SINGLELINE" : "HEADSUP" : "EXPANDED" : "CONTRACTED");
                asIndenting2.print("measured width", Integer.valueOf(showingLayout.getMeasuredWidth()));
                asIndenting2.print("measured height", Integer.valueOf(showingLayout.getMeasuredHeight()));
                asIndenting2.print("maxHeight", Integer.valueOf(showingLayout.getMaxHeight()));
                asIndenting2.print("minHeight", Integer.valueOf(showingLayout.getMinHeight(true)));
                asIndenting2.println();
                asIndenting2.println("ChildViews:");
                DumpUtilsKt.withIncreasedIndent(asIndenting2, new NotificationContentView$$ExternalSyntheticLambda3(showingLayout, asIndenting2));
                int extraRemoteInputHeight = showingLayout.getExtraRemoteInputHeight(showingLayout.mExpandedRemoteInput);
                int extraRemoteInputHeight2 = showingLayout.getExtraRemoteInputHeight(showingLayout.mHeadsUpRemoteInput);
                asIndenting2.print("expandedRemoteInputHeight", Integer.valueOf(extraRemoteInputHeight));
                asIndenting2.print("headsUpRemoteInputHeight", Integer.valueOf(extraRemoteInputHeight2));
                asIndenting2.println();
                indentingPrintWriter.println("mBubblesEnabledForUser: " + showingLayout.mBubblesEnabledForUser);
                indentingPrintWriter.print("RemoteInputViews { ");
                indentingPrintWriter.print(" visibleType: " + showingLayout.mVisibleType);
                if (showingLayout.mHeadsUpRemoteInputController != null) {
                    indentingPrintWriter.print(", headsUpRemoteInputController.isActive: " + ((RemoteInputViewControllerImpl) showingLayout.mHeadsUpRemoteInputController).view.isActive());
                } else {
                    indentingPrintWriter.print(", headsUpRemoteInputController: null");
                }
                if (showingLayout.mExpandedRemoteInputController != null) {
                    indentingPrintWriter.print(", expandedRemoteInputController.isActive: " + ((RemoteInputViewControllerImpl) showingLayout.mExpandedRemoteInputController).view.isActive());
                } else {
                    indentingPrintWriter.print(", expandedRemoteInputController: null");
                }
                indentingPrintWriter.println(" }");
                ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                if (expandableViewState != null) {
                    expandableViewState.dump(indentingPrintWriter, strArr2);
                    indentingPrintWriter.println();
                } else {
                    indentingPrintWriter.println("no viewState!!!");
                }
                indentingPrintWriter.println(((ExpandableOutlineView) expandableNotificationRow).mRoundableState.debugString());
                indentingPrintWriter.println("Background View: " + expandableNotificationRow.mBackgroundNormal);
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                int transientViewCount = notificationChildrenContainer == null ? 0 : notificationChildrenContainer.getTransientViewCount();
                if (!expandableNotificationRow.mIsSummaryWithChildren && transientViewCount <= 0) {
                    NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                    if (notificationContentView != null) {
                        if (notificationContentView.mHeadsUpSmartReplyView != null) {
                            indentingPrintWriter.println("HeadsUp SmartReplyView:");
                            indentingPrintWriter.increaseIndent();
                            notificationContentView.mHeadsUpSmartReplyView.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                        }
                        if (notificationContentView.mExpandedSmartReplyView != null) {
                            indentingPrintWriter.println("Expanded SmartReplyView:");
                            indentingPrintWriter.increaseIndent();
                            notificationContentView.mExpandedSmartReplyView.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                            return;
                        }
                        return;
                    }
                    return;
                }
                NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
                indentingPrintWriter.println("NotificationChildrenContainer { visibility: " + notificationChildrenContainer2.getVisibility() + ", alpha: " + notificationChildrenContainer2.getAlpha() + ", translationY: " + notificationChildrenContainer2.getTranslationY() + ", roundableState: " + notificationChildrenContainer2.mRoundableState.debugString() + "}");
                indentingPrintWriter.println();
                List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                StringBuilder sb3 = new StringBuilder("Children: ");
                sb3.append(attachedChildren.size());
                sb3.append(" {");
                indentingPrintWriter.print(sb3.toString());
                indentingPrintWriter.increaseIndent();
                for (ExpandableNotificationRow expandableNotificationRow2 : attachedChildren) {
                    indentingPrintWriter.println();
                    expandableNotificationRow2.dump(indentingPrintWriter, strArr2);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
                indentingPrintWriter.print("Transient Views: " + transientViewCount + " {");
                indentingPrintWriter.increaseIndent();
                for (int i2 = 0; i2 < transientViewCount; i2++) {
                    indentingPrintWriter.println();
                    ((ExpandableView) expandableNotificationRow.mChildrenContainer.getTransientView(i2)).dump(indentingPrintWriter, strArr2);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
            }
        });
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        Boolean valueOf = Boolean.valueOf(this.mSensitive);
        panelScreenShotLogger.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "mSensitive", valueOf);
        PanelScreenShotLogger.addLogItem(arrayList, "mNeedsRedaction", Boolean.valueOf(needsRedaction()));
        PanelScreenShotLogger.addLogItem(arrayList, "shouldShowPublic", Boolean.valueOf(shouldShowPublic()));
        PanelScreenShotLogger.addLogItem(arrayList, "mHideSensitiveForIntrinsicHeight", Boolean.valueOf(this.mHideSensitiveForIntrinsicHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "isExpanded", Boolean.valueOf(isExpanded(false)));
        PanelScreenShotLogger.addLogItem(arrayList, "getMaxExpandHeight", Integer.valueOf(getMaxExpandHeight()));
        PanelScreenShotLogger.addLogItem(arrayList, "getCollapsedHeight", Integer.valueOf(getCollapsedHeight()));
        PanelScreenShotLogger.addLogItem(arrayList, "getMinHeight", Integer.valueOf(getMinHeight(false)));
        PanelScreenShotLogger.addLogItem(arrayList, "mUseIncreasedCollapsedHeight", Boolean.valueOf(this.mUseIncreasedCollapsedHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "hasInterrupted", Boolean.valueOf(this.mEntry.interruption));
        PanelScreenShotLogger.addLogItem(arrayList, "mDismissState", this.mEntry.mDismissState);
        PanelScreenShotLogger.addLogItem(arrayList, "isDismissable", Boolean.valueOf(!((NotificationDismissibilityProviderImpl) this.mDismissibilityProvider).nonDismissableEntryKeys.contains(this.mEntry.mKey)));
        PanelScreenShotLogger.addLogItem(arrayList, "mIsHeadsUp", Boolean.valueOf(this.mIsHeadsUp));
        PanelScreenShotLogger.addLogItem(arrayList, "mHeadsupDisappearRunning", Boolean.valueOf(this.mHeadsupDisappearRunning));
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        PanelScreenShotLogger.addLogItem(arrayList, "mBackgroundNormal", "<NotificationBackgroundView tintColor=" + ColorUtilKt.hexColorString(Integer.valueOf(notificationBackgroundView.mTintColor)) + " rippleColor=" + ColorUtilKt.hexColorString(notificationBackgroundView.mRippleColor) + " bgColor=" + DrawableDumpKt.getSolidColor(notificationBackgroundView.mBackground) + ">");
        StatusBarIconView statusBarIconView = this.mEntry.mIcons.mStatusBarIcon;
        if (statusBarIconView != null) {
            PanelScreenShotLogger.addLogItem(arrayList, "isGrayScale", Boolean.valueOf(NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(getContext()))));
        }
        arrayList.addAll(getShowingLayout().gatherState());
        if (getAttachedChildren() != null) {
            PanelScreenShotLogger.addHeaderLine("Attache Children : ", arrayList);
            Iterator it = getAttachedChildren().iterator();
            while (it.hasNext()) {
                arrayList.addAll(((ExpandableNotificationRow) it.next()).gatherState());
            }
        }
        return arrayList;
    }

    public final List getAttachedChildren() {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null) {
            return null;
        }
        return notificationChildrenContainer.mAttachedChildren;
    }

    public final ArrayList getChildrenCountText() {
        ArrayList arrayList = new ArrayList();
        if (getShowingLayout().getContractedWrapper() != null && getShowingLayout().getContractedWrapper().getChildrenCountText() != null) {
            TextView childrenCountText = getShowingLayout().getContractedWrapper().getChildrenCountText();
            int textColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(2, this.mDimmed, true);
            childrenCountText.setTextColor(Color.argb(102, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
            arrayList.add(childrenCountText);
        }
        if (getShowingLayout().getHeadsUpWrapper() != null && getShowingLayout().getHeadsUpWrapper().getChildrenCountText() != null) {
            TextView childrenCountText2 = getShowingLayout().getHeadsUpWrapper().getChildrenCountText();
            int textColor2 = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(2, this.mDimmed, true);
            childrenCountText2.setTextColor(Color.argb(102, Color.red(textColor2), Color.green(textColor2), Color.blue(textColor2)));
            arrayList.add(childrenCountText2);
        }
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getCollapsedHeight() {
        if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
            return getMinHeight(false);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        return notificationChildrenContainer.getMinHeight(notificationChildrenContainer.getMaxAllowedVisibleChildren(true));
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final View getContentView() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? getShowingLayout() : this.mChildrenContainer;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public final Path getCustomClipPath(View view) {
        if (view instanceof NotificationGuts) {
            return getClipPath(true);
        }
        return null;
    }

    public final ArrayList getExpandButtons() {
        View expandButton;
        View expandButton2;
        View expandButton3;
        ArrayList arrayList = new ArrayList();
        if (getShowingLayout().getContractedWrapper() != null && (expandButton3 = getShowingLayout().getContractedWrapper().getExpandButton()) != null) {
            arrayList.add(expandButton3);
        }
        if (getShowingLayout().getExpandedWrapper() != null && (expandButton2 = getShowingLayout().getExpandedWrapper().getExpandButton()) != null) {
            arrayList.add(expandButton2);
        }
        if (getShowingLayout().getHeadsUpWrapper() != null && (expandButton = getShowingLayout().getHeadsUpWrapper().getExpandButton()) != null) {
            arrayList.add(expandButton);
        }
        return arrayList;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final float getHeaderVisibleAmount() {
        return this.mHeaderVisibleAmount;
    }

    public final int getHeadsUpHeight() {
        return getShowingLayout().getHeadsUpHeight(false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getHeadsUpHeightWithoutHeader() {
        if (!canShowHeadsUp() || !this.mIsHeadsUp) {
            return getCollapsedHeight();
        }
        if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
            return getShowingLayout().getHeadsUpHeight(true);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        return notificationChildrenContainer.getMinHeight(notificationChildrenContainer.getMaxAllowedVisibleChildren(true));
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getHeightWithoutLockscreenConstraints() {
        this.mIgnoreLockscreenConstraints = true;
        int intrinsicHeight = getIntrinsicHeight();
        this.mIgnoreLockscreenConstraints = false;
        return intrinsicHeight;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getIntrinsicHeight() {
        boolean z;
        if (this.mUserLocked) {
            return this.mActualHeight;
        }
        NotificationGuts notificationGuts = this.mGuts;
        if (notificationGuts == null || !(z = notificationGuts.mExposed)) {
            return (!isChildInGroup() || isGroupExpanded()) ? (this.mSensitive && this.mHideSensitiveForIntrinsicHeight) ? getMinHeight(false) : this.mIsSummaryWithChildren ? this.mChildrenContainer.getIntrinsicHeight() : (canShowHeadsUp() && isHeadsUpState()) ? (this.mIsPinned || this.mHeadsupDisappearRunning) ? getPinnedHeadsUpHeight(true) : isExpanded(false) ? Math.max(getMaxExpandHeight(), getHeadsUpHeight()) : Math.max(getCollapsedHeight(), getHeadsUpHeight()) : isExpanded(false) ? getMaxExpandHeight() : getCollapsedHeight() : this.mPrivateLayout.getMinHeight(true);
        }
        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
        return (gutsContent == null || !z) ? notificationGuts.getHeight() : gutsContent.getActualHeight();
    }

    public final boolean getIsNonPackageBlockable() {
        return !SecNotificationBlockManager.isBlockablePackage(((FrameLayout) this).mContext, this.mEntry.mSbn.getPackageName());
    }

    public final boolean getIsNonblockable() {
        int checkSystemAppAndMetaData;
        Context context = ((FrameLayout) this).mContext;
        String packageName = this.mEntry.mSbn.getPackageName();
        NotificationChannel channel = this.mEntry.mRanking.getChannel();
        boolean z = false;
        if (SecNotificationBlockManager.checkConfigCSC(context, packageName, channel) != 2 && ((checkSystemAppAndMetaData = SecNotificationBlockManager.checkSystemAppAndMetaData(context, packageName)) == 4 || channel.isBlockable() || checkSystemAppAndMetaData != 2)) {
            z = true;
        }
        return !z;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getMaxContentHeight() {
        /*
            r7 = this;
            boolean r0 = r7.mIsSummaryWithChildren
            if (r0 == 0) goto L6d
            boolean r0 = r7.shouldShowPublic()
            if (r0 != 0) goto L6d
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r7 = r7.mChildrenContainer
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r0 = r7.mContainingNotification
            boolean r0 = r0.isGroupExpanded()
            if (r0 == 0) goto L17
            int r0 = r7.mHeaderExpandedHeight
            goto L1c
        L17:
            int r0 = r7.mHeaderExpandedHeight
            int r1 = r7.mNotificationTopPadding
            int r0 = r0 + r1
        L1c:
            java.util.List r1 = r7.mAttachedChildren
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            int r1 = r1.size()
            r2 = 0
            r3 = r2
        L26:
            if (r2 >= r1) goto L66
            boolean r4 = com.android.systemui.NotiRune.NOTI_INSIGNIFICANT
            if (r4 == 0) goto L39
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = r7.mContainingNotification
            boolean r4 = r4.isInsignificant()
            if (r4 == 0) goto L39
            r4 = 50
            if (r3 < r4) goto L3e
            goto L66
        L39:
            r4 = 8
            if (r3 < r4) goto L3e
            goto L66
        L3e:
            java.util.List r4 = r7.mAttachedChildren
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            java.lang.Object r4 = r4.get(r2)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r4
            r5 = 1
            boolean r6 = r4.isExpanded(r5)
            if (r6 == 0) goto L55
            int r4 = r4.getMaxExpandHeight()
        L53:
            float r4 = (float) r4
            goto L5e
        L55:
            com.android.systemui.statusbar.notification.row.NotificationContentView r4 = r4.getShowingLayout()
            int r4 = r4.getMinHeight(r5)
            goto L53
        L5e:
            float r0 = (float) r0
            float r0 = r0 + r4
            int r0 = (int) r0
            int r3 = r3 + 1
            int r2 = r2 + 1
            goto L26
        L66:
            if (r3 <= 0) goto L6c
            int r7 = r7.mDividerHeight
            int r3 = r3 * r7
            int r0 = r0 + r3
        L6c:
            return r0
        L6d:
            com.android.systemui.statusbar.notification.row.NotificationContentView r7 = r7.getShowingLayout()
            int r7 = r7.getMaxHeight()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.getMaxContentHeight():int");
    }

    public final int getMaxExpandHeight() {
        NotificationContentView notificationContentView = this.mPrivateLayout;
        int i = 1;
        if (notificationContentView.mExpandedChild == null) {
            if (notificationContentView.mContractedChild == null) {
                return notificationContentView.getMinHeight(true);
            }
            i = 0;
        }
        return notificationContentView.getExtraRemoteInputHeight(notificationContentView.mExpandedRemoteInput) + notificationContentView.getViewHeight(i, false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getMinHeight(boolean z) {
        NotificationGuts notificationGuts;
        boolean z2;
        if (z || (notificationGuts = this.mGuts) == null || !(z2 = notificationGuts.mExposed)) {
            return (!z && canShowHeadsUp() && this.mIsHeadsUp && this.mHeadsUpManager.isTrackingHeadsUp()) ? getPinnedHeadsUpHeight(false) : (!this.mIsSummaryWithChildren || isGroupExpanded() || shouldShowPublic()) ? (!z && canShowHeadsUp() && this.mIsHeadsUp) ? getHeadsUpHeight() : getShowingLayout().getMinHeight(true) : this.mChildrenContainer.getMinHeight(1);
        }
        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
        return (gutsContent == null || !z2) ? notificationGuts.getHeight() : gutsContent.getActualHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getPinnedHeadsUpHeight() {
        return getPinnedHeadsUpHeight(true);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final StatusBarIconView getShelfIcon() {
        return this.mEntry.mIcons.mShelfIcon;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final View getShelfTransformationTarget() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            NotificationViewWrapper visibleWrapper = this.mChildrenContainer.getVisibleWrapper();
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            Flags.notificationAsyncGroupHeaderInflation();
            return visibleWrapper.getShelfTransformationTarget();
        }
        NotificationContentView showingLayout = getShowingLayout();
        NotificationViewWrapper visibleWrapper2 = showingLayout.getVisibleWrapper(showingLayout.mVisibleType);
        if (visibleWrapper2 != null) {
            return visibleWrapper2.getShelfTransformationTarget();
        }
        return null;
    }

    public final NotificationContentView getShowingLayout() {
        return shouldShowPublic() ? this.mPublicLayout : this.mPrivateLayout;
    }

    public final Animator getTranslateViewAnimator(final float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, TRANSLATE_CONTENT, f);
        if (animatorUpdateListener != null) {
            ofFloat.addUpdateListener(animatorUpdateListener);
        }
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.3
            public boolean cancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
                this.cancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                NotificationMenuRowPlugin notificationMenuRowPlugin;
                if (!this.cancelled && f == 0.0f && (notificationMenuRowPlugin = ExpandableNotificationRow.this.mMenuRow) != null) {
                    notificationMenuRowPlugin.resetMenu();
                }
                ExpandableNotificationRow.this.mTranslateAnim = null;
            }
        });
        this.mTranslateAnim = ofFloat;
        return ofFloat;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final float getTranslation() {
        if (this.mDismissUsingRowTranslationX) {
            return getTranslationX();
        }
        ArrayList arrayList = this.mTranslateableViews;
        if (arrayList == null || arrayList.size() <= 0) {
            return 0.0f;
        }
        return ((View) this.mTranslateableViews.get(0)).getTranslationX();
    }

    public final ExpandableNotificationRow getViewAtPosition(float f) {
        ExpandableNotificationRow expandableNotificationRow;
        if (!this.mIsSummaryWithChildren || !this.mChildrenExpanded) {
            return this;
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        int size = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
        int i = 0;
        while (true) {
            if (i >= size) {
                expandableNotificationRow = null;
                break;
            }
            expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i);
            float translationY = expandableNotificationRow.getTranslationY();
            float max = Math.max(0, expandableNotificationRow.mClipTopAmount) + translationY;
            float f2 = translationY + expandableNotificationRow.mActualHeight;
            if (f >= max && f <= f2) {
                break;
            }
            i++;
        }
        return expandableNotificationRow == null ? this : expandableNotificationRow;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean hasExpandingChild() {
        return this.mChildIsExpanding;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final boolean hasOverlappingRendering() {
        return super.hasOverlappingRendering() && childrenRequireOverlappingRendering();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final boolean hideBackground() {
        return this.mShowNoBackground;
    }

    public final void initDimens$1() {
        this.mMaxSmallHeightBeforeN = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_legacy, ((FrameLayout) this).mContext);
        this.mMaxSmallHeightBeforeP = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_before_p, ((FrameLayout) this).mContext);
        this.mMaxSmallHeightBeforeS = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_before_s, ((FrameLayout) this).mContext);
        this.mMaxSmallHeight = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height, ((FrameLayout) this).mContext);
        this.mMaxSmallHeightLarge = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_increased, ((FrameLayout) this).mContext);
        this.mMaxExpandedHeight = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_height, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightBeforeN = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_legacy, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightBeforeP = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_before_p, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightBeforeS = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_before_s, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeight = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightIncreased = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_increased, ((FrameLayout) this).mContext);
        Resources resources = getResources();
        this.mEnableNonGroupedNotificationExpand = resources.getBoolean(R.bool.config_enableNonGroupedNotificationExpand);
        resources.getBoolean(R.bool.config_showGroupNotificationBgWhenExpanded);
    }

    public final void initGroupHeaderContainAtMark() {
        TextView textView;
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null || (textView = (TextView) notificationChildrenContainer.mGroupHeader.findViewById(android.R.id.inbox_text0)) == null || !textView.getText().toString().contains("@")) {
            return;
        }
        this.mIsGroupHeaderContainAtMark = true;
    }

    public final void initialize(NotificationEntry notificationEntry, RemoteInputViewSubcomponent.Factory factory, String str, String str2, ExpandableNotificationRowController.AnonymousClass2 anonymousClass2, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, HeadsUpManager headsUpManager, RowContentBindStage rowContentBindStage, OnExpandClickListener onExpandClickListener, ExpandableNotificationRowController$$ExternalSyntheticLambda0 expandableNotificationRowController$$ExternalSyntheticLambda0, FalsingManager falsingManager, StatusBarStateController statusBarStateController, PeopleNotificationIdentifier peopleNotificationIdentifier, OnUserInteractionCallback onUserInteractionCallback, NotificationGutsManager notificationGutsManager, NotificationDismissibilityProvider notificationDismissibilityProvider, MetricsLogger metricsLogger, NotificationChildrenContainerLogger notificationChildrenContainerLogger, ColorUpdateLogger colorUpdateLogger, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, IStatusBarService iStatusBarService) {
        this.mEntry = notificationEntry;
        this.mAppName = str;
        if (this.mMenuRow == null) {
            this.mMenuRow = new NotificationMenuRow(((FrameLayout) this).mContext, peopleNotificationIdentifier);
        }
        if (this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.setAppName(this.mAppName);
        }
        this.mLogger = anonymousClass2;
        this.mLoggingKey = str2;
        this.mBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mPrivateLayout.getClass();
        this.mHeadsUpManager = headsUpManager;
        this.mRowContentBindStage = rowContentBindStage;
        this.mOnExpandClickListener = onExpandClickListener;
        this.mOnFeedbackClickListener = new ExpandableNotificationRow$$ExternalSyntheticLambda4(this, expandableNotificationRowController$$ExternalSyntheticLambda0, 1);
        this.mFalsingManager = falsingManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mPeopleIdentifier = this.mPeopleNotificationIdentifier;
            notificationContentView.mRemoteInputSubcomponentFactory = factory;
            notificationContentView.mSmartReplyConstants = smartReplyConstants;
            notificationContentView.mSmartReplyController = smartReplyController;
            notificationContentView.mStatusBarService = iStatusBarService;
            notificationContentView.setIsRootNamespace(true);
        }
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mMetricsLogger = metricsLogger;
        this.mChildrenContainerLogger = notificationChildrenContainerLogger;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isAboveShelf() {
        return canShowHeadsUp() && (this.mIsPinned || this.mHeadsupDisappearRunning || ((this.mIsHeadsUp && this.mAboveShelf) || this.mExpandAnimationRunning || this.mChildIsExpanding));
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isChildInGroup() {
        return this.mNotificationParent != null;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isContentExpandable() {
        if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
            return getShowingLayout().mIsContentExpandable;
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isExpandAnimationRunning() {
        return this.mExpandAnimationRunning;
    }

    public final boolean isExpandable$1() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? this.mEnableNonGroupedNotificationExpand && this.mExpandable : !this.mChildrenExpanded;
    }

    public final boolean isExpanded(boolean z) {
        return !shouldShowPublic() && (!this.mOnKeyguard || z || (this.mEntry.isOngoingAcitivty() && this.mEntry.isPromotedState())) && ((!this.mHasUserChangedExpansion && (this.mIsSystemExpanded || this.mIsSystemChildExpanded)) || this.mUserExpanded);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isGroupExpanded() {
        return ((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(this.mEntry);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isGroupExpansionChanging() {
        return isChildInGroup() ? this.mNotificationParent.isGroupExpansionChanging() : this.mGroupExpansionChanging;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isHeadsUpAnimatingAway() {
        return this.mHeadsupDisappearRunning;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isHeadsUpState() {
        return this.mIsHeadsUp || this.mHeadsupDisappearRunning;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isInsignificant() {
        NotificationEntry notificationEntry = this.mEntry;
        if (notificationEntry != null) {
            return notificationEntry.isInsignificant();
        }
        return false;
    }

    public final boolean isInsignificantSummary() {
        return isInsignificant() && this.mEntry.mSbn.getGroupKey().contains("INSIGNIFICANT");
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isPinned() {
        return this.mIsPinned;
    }

    @Override // android.view.View
    public final boolean isSoundEffectsEnabled() {
        BooleanSupplier booleanSupplier;
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        return (statusBarStateController == null || !statusBarStateController.isDozing() || (booleanSupplier = this.mSecureStateProvider) == null || booleanSupplier.getAsBoolean()) && super.isSoundEffectsEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isSummaryWithChildren() {
        return this.mIsSummaryWithChildren;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean mustStayOnScreen() {
        return this.mIsHeadsUp && this.mMustStayOnScreen;
    }

    public final boolean needsRedaction() {
        return ((NotificationLockscreenUserManagerImpl) ((NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class))).needsRedaction(this.mEntry);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void notifyHeightChanged(boolean z) {
        super.notifyHeightChanged(z);
        getShowingLayout().selectLayout(z || this.mUserLocked, false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void onAppearAnimationFinished(boolean z) {
        if (!z) {
            setHeadsUpAnimatingAway(false);
        } else {
            resetAllContentAlphas();
            setNotificationFaded(false);
        }
    }

    public final void onAttachedChildrenCountChanged() {
        NotificationViewWrapper notificationViewWrapper;
        boolean z = this.mIsSummaryWithChildren;
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        boolean z2 = notificationChildrenContainer != null && notificationChildrenContainer.getNotificationChildCount() > 0;
        this.mIsSummaryWithChildren = z2;
        if (z2) {
            Trace.beginSection("ExpNotRow#onChildCountChanged (summary)");
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            Flags.notificationAsyncGroupHeaderInflation();
            NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = notificationChildrenContainer2.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper == null || notificationHeaderViewWrapper.mNotificationHeader == null) {
                notificationChildrenContainer2.recreateNotificationHeader(this.mExpandClickListener, ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(this.mEntry) != 0);
            }
        }
        if (!this.mIsSummaryWithChildren && z) {
            NotificationContentView notificationContentView = this.mPublicLayout;
            long when = this.mEntry.mSbn.getNotification().getWhen();
            if ((notificationContentView.mContractedChild == null || (notificationViewWrapper = notificationContentView.mContractedWrapper) == null) && ((notificationContentView.mExpandedChild == null || (notificationViewWrapper = notificationContentView.mExpandedWrapper) == null) && (notificationContentView.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView.mHeadsUpWrapper) == null))) {
                notificationViewWrapper = null;
            }
            if (notificationViewWrapper instanceof NotificationHeaderViewWrapper) {
                ((NotificationHeaderViewWrapper) notificationViewWrapper).setNotificationWhen(when);
            }
        }
        getShowingLayout().updateBackgroundColor(false);
        this.mPrivateLayout.updateExpandButtonsDuringLayout(isExpandable$1(), false);
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateChildrenAppearance();
        }
        updateChildrenVisibility();
        if (this.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer3 = this.mChildrenContainer;
            RoundableState roundableState = ((ExpandableOutlineView) this).mRoundableState;
            notificationChildrenContainer3.requestRoundness(roundableState.topRoundness, roundableState.bottomRoundness, FROM_PARENT, false);
        }
        if (this.mIsSummaryWithChildren) {
            Trace.endSection();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        float f = this.mSmallRoundness;
        requestRoundness(f, f, BASE_VALUE, false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin != null && notificationMenuRowPlugin.getMenuView() != null) {
            this.mMenuRow.onConfigurationChanged();
        }
        NotificationInlineImageResolver notificationInlineImageResolver = this.mImageResolver;
        if (notificationInlineImageResolver != null) {
            notificationInlineImageResolver.mMaxImageWidth = notificationInlineImageResolver.getMaxImageWidth();
            notificationInlineImageResolver.mMaxImageHeight = notificationInlineImageResolver.getMaxImageHeight();
        }
    }

    public final void onExpandedByGesture(boolean z) {
        this.mMetricsLogger.action(((GroupMembershipManagerImpl) this.mGroupMembershipManager).isGroupSummary(this.mEntry) ? 410 : 409, z);
    }

    public final void onExpansionChanged(boolean z, boolean z2) {
        boolean isExpanded = isExpanded(false);
        if (this.mIsSummaryWithChildren) {
            isExpanded = ((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(this.mEntry);
        }
        if (isExpanded != z2) {
            updateShelfIconColor();
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                ExpandableNotificationRowController.this.mStatsLogger.onNotificationExpansionChanged(this.mLoggingKey, isExpanded, this.mViewState.location, z);
            }
            if (this.mIsSummaryWithChildren) {
                NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
                if (notificationChildrenContainer.mIsMinimized) {
                    boolean z3 = notificationChildrenContainer.mUserLocked;
                    if (z3) {
                        notificationChildrenContainer.setUserLocked(z3);
                    }
                    notificationChildrenContainer.updateHeaderVisibility(true, false);
                }
                if (!notificationChildrenContainer.mUserLocked) {
                    notificationChildrenContainer.updateHeaderVisibility(true, false);
                }
            }
            ConversationNotificationManager$onEntryViewBound$1 conversationNotificationManager$onEntryViewBound$1 = this.mExpansionChangedListener;
            if (conversationNotificationManager$onEntryViewBound$1 != null) {
                conversationNotificationManager$onEntryViewBound$1.onExpansionChanged(isExpanded);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPublicLayout = (NotificationContentView) findViewById(R.id.expandedPublic);
        NotificationContentView notificationContentView = (NotificationContentView) findViewById(R.id.expanded);
        this.mPrivateLayout = notificationContentView;
        NotificationContentView[] notificationContentViewArr = {notificationContentView, this.mPublicLayout};
        this.mLayouts = notificationContentViewArr;
        for (NotificationContentView notificationContentView2 : notificationContentViewArr) {
            notificationContentView2.mExpandClickListener = this.mExpandClickListener;
            notificationContentView2.mContainingNotification = this;
        }
        ViewStub viewStub = (ViewStub) findViewById(R.id.notification_guts_stub);
        this.mGutsStub = viewStub;
        final int i = 0;
        viewStub.setOnInflateListener(new ViewStub.OnInflateListener(this) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda1
            public final /* synthetic */ ExpandableNotificationRow f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub2, View view) {
                int i2 = i;
                ExpandableNotificationRow expandableNotificationRow = this.f$0;
                switch (i2) {
                    case 0:
                        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                        expandableNotificationRow.getClass();
                        NotificationGuts notificationGuts = (NotificationGuts) view;
                        expandableNotificationRow.mGuts = notificationGuts;
                        notificationGuts.mClipTopAmount = expandableNotificationRow.mClipTopAmount;
                        notificationGuts.invalidate();
                        NotificationGuts notificationGuts2 = expandableNotificationRow.mGuts;
                        notificationGuts2.mActualHeight = expandableNotificationRow.mActualHeight;
                        notificationGuts2.invalidate();
                        expandableNotificationRow.mGutsStub = null;
                        break;
                    default:
                        SourceType$Companion$from$1 sourceType$Companion$from$12 = ExpandableNotificationRow.BASE_VALUE;
                        expandableNotificationRow.getClass();
                        NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
                        expandableNotificationRow.mChildrenContainer = notificationChildrenContainer;
                        notificationChildrenContainer.setIsMinimized(expandableNotificationRow.mIsMinimized);
                        NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
                        notificationChildrenContainer2.mContainingNotification = expandableNotificationRow;
                        notificationChildrenContainer2.mGroupingUtil = new NotificationGroupingUtil(notificationChildrenContainer2.mContainingNotification);
                        expandableNotificationRow.mChildrenContainer.onNotificationUpdated();
                        NotificationChildrenContainer notificationChildrenContainer3 = expandableNotificationRow.mChildrenContainer;
                        notificationChildrenContainer3.mLogger = expandableNotificationRow.mChildrenContainerLogger;
                        expandableNotificationRow.mTranslateableViews.add(notificationChildrenContainer3);
                        break;
                }
            }
        });
        ViewStub viewStub2 = (ViewStub) findViewById(R.id.child_container_stub);
        this.mChildrenContainerStub = viewStub2;
        final int i2 = 1;
        viewStub2.setOnInflateListener(new ViewStub.OnInflateListener(this) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda1
            public final /* synthetic */ ExpandableNotificationRow f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub22, View view) {
                int i22 = i2;
                ExpandableNotificationRow expandableNotificationRow = this.f$0;
                switch (i22) {
                    case 0:
                        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                        expandableNotificationRow.getClass();
                        NotificationGuts notificationGuts = (NotificationGuts) view;
                        expandableNotificationRow.mGuts = notificationGuts;
                        notificationGuts.mClipTopAmount = expandableNotificationRow.mClipTopAmount;
                        notificationGuts.invalidate();
                        NotificationGuts notificationGuts2 = expandableNotificationRow.mGuts;
                        notificationGuts2.mActualHeight = expandableNotificationRow.mActualHeight;
                        notificationGuts2.invalidate();
                        expandableNotificationRow.mGutsStub = null;
                        break;
                    default:
                        SourceType$Companion$from$1 sourceType$Companion$from$12 = ExpandableNotificationRow.BASE_VALUE;
                        expandableNotificationRow.getClass();
                        NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
                        expandableNotificationRow.mChildrenContainer = notificationChildrenContainer;
                        notificationChildrenContainer.setIsMinimized(expandableNotificationRow.mIsMinimized);
                        NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
                        notificationChildrenContainer2.mContainingNotification = expandableNotificationRow;
                        notificationChildrenContainer2.mGroupingUtil = new NotificationGroupingUtil(notificationChildrenContainer2.mContainingNotification);
                        expandableNotificationRow.mChildrenContainer.onNotificationUpdated();
                        NotificationChildrenContainer notificationChildrenContainer3 = expandableNotificationRow.mChildrenContainer;
                        notificationChildrenContainer3.mLogger = expandableNotificationRow.mChildrenContainerLogger;
                        expandableNotificationRow.mTranslateableViews.add(notificationChildrenContainer3);
                        break;
                }
            }
        });
        this.mTranslateableViews = new ArrayList();
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            this.mTranslateableViews.add(getChildAt(i3));
        }
        this.mTranslateableViews.remove(this.mChildrenContainerStub);
        this.mTranslateableViews.remove(this.mGutsStub);
        setDefaultFocusHighlightEnabled(false);
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        NotificationGuts.GutsContent gutsContent;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        boolean z2 = true;
        boolean z3 = false;
        if (this.mLongPressListener == null) {
            z = false;
        } else if (areGutsExposed()) {
            NotificationGuts notificationGuts = this.mGuts;
            z = !((notificationGuts == null || (gutsContent = notificationGuts.mGutsContent) == null || !gutsContent.isLeavebehind()) ? false : true);
        } else {
            z = true;
        }
        if (z) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
        }
        accessibilityNodeInfo.setLongClickable(z);
        if (canViewBeDismissed$1() && !this.mIsSnoozed) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        }
        boolean shouldShowPublic = shouldShowPublic();
        if (shouldShowPublic) {
            z2 = shouldShowPublic;
        } else if (this.mIsSummaryWithChildren) {
            if (this.mIsMinimized) {
                isExpanded(false);
            }
            z3 = isGroupExpanded();
        } else {
            z2 = this.mPrivateLayout.mIsContentExpandable;
            z3 = isExpanded(false);
        }
        if (z2 && !this.mIsSnoozed) {
            if (z3) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            } else {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            }
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin == null || notificationMenuRowPlugin.getSnoozeMenuItem(getContext()) == null) {
            return;
        }
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snooze, getContext().getResources().getString(R.string.notification_menu_snooze_action)));
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != 0) {
            this.mFalsingManager.isFalseTap(2);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!KeyEvent.isConfirmKey(i)) {
            return super.onKeyDown(i, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        if (!KeyEvent.isConfirmKey(i)) {
            return false;
        }
        doLongClickCallback(getWidth() / 2, getHeight() / 2);
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!KeyEvent.isConfirmKey(i)) {
            return super.onKeyUp(i, keyEvent);
        }
        if (keyEvent.isCanceled()) {
            return true;
        }
        performClick();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        NotificationViewWrapper visibleWrapper;
        Trace.beginSection(appendTraceStyleTag("ExpNotRow#onLayout"));
        int intrinsicHeight = getIntrinsicHeight();
        super.onLayout(z, i, i2, i3, i4);
        if (intrinsicHeight != getIntrinsicHeight() && (intrinsicHeight != 0 || this.mActualHeight > 0)) {
            notifyHeightChanged(true);
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin != null && notificationMenuRowPlugin.getMenuView() != null) {
            this.mMenuRow.onParentHeightUpdate();
        }
        if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
            NotificationContentView showingLayout = getShowingLayout();
            visibleWrapper = showingLayout.getVisibleWrapper(showingLayout.mVisibleType);
        } else {
            visibleWrapper = this.mChildrenContainer.getVisibleWrapper();
        }
        View icon = visibleWrapper == null ? null : visibleWrapper.getIcon();
        if (icon != null) {
            this.mIconTransformContentShift = icon.getHeight() + getRelativeTopPadding(icon);
        } else {
            this.mIconTransformContentShift = this.mContentShift;
        }
        NotificationMenuRow notificationMenuRow = this.mLayoutListener;
        if (notificationMenuRow != null) {
            notificationMenuRow.mIconsPlaced = false;
            notificationMenuRow.setMenuLocation();
            notificationMenuRow.mParent.mLayoutListener = null;
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection(appendTraceStyleTag("ExpNotRow#onMeasure"));
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    public final void onNotificationUpdated() {
        TextView textView;
        TextView textView2;
        boolean z;
        RemoteInputView remoteInputView;
        RemoteInputView remoteInputView2;
        if (this.mIsSummaryWithChildren) {
            Trace.beginSection("ExpNotRow#onNotifUpdated (summary)");
        } else {
            Trace.beginSection("ExpNotRow#onNotifUpdated (leaf)");
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            NotificationEntry notificationEntry = this.mEntry;
            notificationContentView.mNotificationEntry = notificationEntry;
            notificationContentView.mBeforeN = notificationEntry.targetSdk < 24;
            notificationContentView.updateAllSingleLineViews();
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (notificationContentView.mContractedChild != null) {
                notificationContentView.mContractedWrapper.onContentUpdated(expandableNotificationRow);
            }
            if (notificationContentView.mExpandedChild != null) {
                notificationContentView.mExpandedWrapper.onContentUpdated(expandableNotificationRow);
            }
            if (notificationContentView.mHeadsUpChild != null) {
                notificationContentView.mHeadsUpWrapper.onContentUpdated(expandableNotificationRow);
            }
            if (notificationContentView.mRemoteInputController != null) {
                boolean z2 = notificationContentView.mNotificationEntry.mSbn.getNotification().findRemoteInputActionPair(true) != null;
                View view = notificationContentView.mExpandedChild;
                if (view != null && (remoteInputView2 = (RemoteInputView) view.findViewWithTag(RemoteInputView.VIEW_TAG)) != null) {
                    remoteInputView2.mOnVisibilityChangedListeners.clear();
                }
                View view2 = notificationContentView.mHeadsUpChild;
                if (view2 != null && (remoteInputView = (RemoteInputView) view2.findViewWithTag(RemoteInputView.VIEW_TAG)) != null) {
                    remoteInputView.mOnVisibilityChangedListeners.clear();
                }
                View view3 = notificationContentView.mExpandedChild;
                if (view3 != null) {
                    NotificationContentView.RemoteInputViewData applyRemoteInput = notificationContentView.applyRemoteInput(view3, notificationContentView.mNotificationEntry, z2, notificationContentView.mPreviousExpandedRemoteInputIntent, notificationContentView.mExpandedWrapper, false);
                    notificationContentView.mExpandedRemoteInput = applyRemoteInput.mView;
                    RemoteInputViewController remoteInputViewController = applyRemoteInput.mController;
                    notificationContentView.mExpandedRemoteInputController = remoteInputViewController;
                    if (remoteInputViewController != null) {
                        ((RemoteInputViewControllerImpl) remoteInputViewController).bind();
                    }
                } else {
                    notificationContentView.mExpandedRemoteInput = null;
                    RemoteInputViewController remoteInputViewController2 = notificationContentView.mExpandedRemoteInputController;
                    if (remoteInputViewController2 != null) {
                        ((RemoteInputViewControllerImpl) remoteInputViewController2).unbind();
                    }
                    notificationContentView.mExpandedRemoteInputController = null;
                }
                RemoteInputView remoteInputView3 = notificationContentView.mCachedExpandedRemoteInput;
                if (remoteInputView3 != null && remoteInputView3 != notificationContentView.mExpandedRemoteInput) {
                    remoteInputView3.dispatchFinishTemporaryDetach();
                }
                notificationContentView.mCachedExpandedRemoteInput = null;
                notificationContentView.mCachedExpandedRemoteInputViewController = null;
                View view4 = notificationContentView.mHeadsUpChild;
                if (view4 != null) {
                    NotificationContentView.RemoteInputViewData applyRemoteInput2 = notificationContentView.applyRemoteInput(view4, notificationContentView.mNotificationEntry, z2, notificationContentView.mPreviousHeadsUpRemoteInputIntent, notificationContentView.mHeadsUpWrapper, true);
                    notificationContentView.mHeadsUpRemoteInput = applyRemoteInput2.mView;
                    RemoteInputViewController remoteInputViewController3 = applyRemoteInput2.mController;
                    notificationContentView.mHeadsUpRemoteInputController = remoteInputViewController3;
                    if (remoteInputViewController3 != null) {
                        ((RemoteInputViewControllerImpl) remoteInputViewController3).bind();
                    }
                } else {
                    notificationContentView.mHeadsUpRemoteInput = null;
                    RemoteInputViewController remoteInputViewController4 = notificationContentView.mHeadsUpRemoteInputController;
                    if (remoteInputViewController4 != null) {
                        ((RemoteInputViewControllerImpl) remoteInputViewController4).unbind();
                    }
                    notificationContentView.mHeadsUpRemoteInputController = null;
                }
                RemoteInputView remoteInputView4 = notificationContentView.mCachedHeadsUpRemoteInput;
                if (remoteInputView4 != null && remoteInputView4 != notificationContentView.mHeadsUpRemoteInput) {
                    remoteInputView4.dispatchFinishTemporaryDetach();
                }
                notificationContentView.mCachedHeadsUpRemoteInput = null;
                notificationContentView.mCachedHeadsUpRemoteInputViewController = null;
            }
            InflatedSmartReplyState inflatedSmartReplyState = notificationContentView.mCurrentSmartReplyState;
            if (inflatedSmartReplyState != null) {
                View view5 = notificationContentView.mContractedChild;
                if (view5 != null) {
                    NotificationContentView.applyExternalSmartReplyState(view5, inflatedSmartReplyState);
                }
                View view6 = notificationContentView.mExpandedChild;
                if (view6 != null) {
                    NotificationContentView.applyExternalSmartReplyState(view6, notificationContentView.mCurrentSmartReplyState);
                    SmartReplyView applySmartReplyView = NotificationContentView.applySmartReplyView(notificationContentView.mExpandedChild, notificationContentView.mCurrentSmartReplyState, notificationContentView.mNotificationEntry, notificationContentView.mExpandedInflatedSmartReplies, false);
                    notificationContentView.mExpandedSmartReplyView = applySmartReplyView;
                    if (applySmartReplyView != null) {
                        InflatedSmartReplyState inflatedSmartReplyState2 = notificationContentView.mCurrentSmartReplyState;
                        SmartReplyView.SmartReplies smartReplies = inflatedSmartReplyState2.smartReplies;
                        SmartReplyView.SmartActions smartActions = inflatedSmartReplyState2.smartActions;
                        if (smartReplies != null || smartActions != null) {
                            int size = smartReplies == null ? 0 : smartReplies.choices.size();
                            int size2 = smartActions == null ? 0 : smartActions.actions.size();
                            boolean z3 = smartReplies == null ? smartActions.fromAssistant : smartReplies.fromAssistant;
                            try {
                                if (smartReplies != null) {
                                    SmartReplyConstants smartReplyConstants = notificationContentView.mSmartReplyConstants;
                                    int editChoicesBeforeSending = smartReplies.remoteInput.getEditChoicesBeforeSending();
                                    smartReplyConstants.getClass();
                                    if (editChoicesBeforeSending != 1 ? editChoicesBeforeSending != 2 ? smartReplyConstants.mEditChoicesBeforeSending : true : false) {
                                        z = true;
                                        SmartReplyController smartReplyController = notificationContentView.mSmartReplyController;
                                        NotificationEntry notificationEntry2 = notificationContentView.mNotificationEntry;
                                        smartReplyController.getClass();
                                        smartReplyController.mBarService.onNotificationSmartSuggestionsAdded(notificationEntry2.mSbn.getKey(), size, size2, z3, z);
                                    }
                                }
                                smartReplyController.mBarService.onNotificationSmartSuggestionsAdded(notificationEntry2.mSbn.getKey(), size, size2, z3, z);
                            } catch (RemoteException unused) {
                            }
                            z = false;
                            SmartReplyController smartReplyController2 = notificationContentView.mSmartReplyController;
                            NotificationEntry notificationEntry22 = notificationContentView.mNotificationEntry;
                            smartReplyController2.getClass();
                        }
                    }
                }
                View view7 = notificationContentView.mHeadsUpChild;
                if (view7 != null) {
                    NotificationContentView.applyExternalSmartReplyState(view7, notificationContentView.mCurrentSmartReplyState);
                    if (notificationContentView.mSmartReplyConstants.mShowInHeadsUp) {
                        notificationContentView.mHeadsUpSmartReplyView = NotificationContentView.applySmartReplyView(notificationContentView.mHeadsUpChild, notificationContentView.mCurrentSmartReplyState, notificationContentView.mNotificationEntry, notificationContentView.mHeadsUpInflatedSmartReplies, true);
                    }
                }
            }
            notificationContentView.updateLegacy();
            notificationContentView.mForceSelectNextLayout = true;
            notificationContentView.mPreviousExpandedRemoteInputIntent = null;
            notificationContentView.mPreviousHeadsUpRemoteInputIntent = null;
            View view8 = notificationContentView.mExpandedChild;
            notificationContentView.applySnoozeAction(view8);
            notificationContentView.applyBubbleAction(view8, notificationEntry);
            View view9 = notificationContentView.mHeadsUpChild;
            notificationContentView.applySnoozeAction(view9);
            notificationContentView.applyBubbleAction(view9, notificationEntry);
            if (notificationContentView.mContainingNotification != null) {
                View view10 = notificationContentView.mContractedChild;
                if (view10 != null) {
                    notificationContentView.updateContentViewMarginBottom(view10, false);
                }
                View view11 = notificationContentView.mExpandedChild;
                if (view11 != null) {
                    notificationContentView.updateContentViewMarginBottom(view11, true);
                }
                View view12 = notificationContentView.mHeadsUpChild;
                if (view12 != null) {
                    notificationContentView.updateContentViewMarginBottom(view12, false);
                }
            }
        }
        this.mShowingPublicInitialized = false;
        int i = getResources().getConfiguration().uiMode;
        this.mNotificationColor = ContrastColorUtil.resolveContrastColor(((FrameLayout) this).mContext, this.mEntry.mSbn.getNotification().color, calculateBgColor(false, false), false);
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin != null) {
            notificationMenuRowPlugin.onNotificationUpdated(this.mEntry.mSbn);
            this.mMenuRow.setAppName(this.mAppName);
        }
        if (this.mIsSummaryWithChildren) {
            int i2 = AsyncGroupHeaderViewInflation.$r8$clinit;
            Flags.notificationAsyncGroupHeaderInflation();
            this.mChildrenContainer.recreateNotificationHeader(this.mExpandClickListener, ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(this.mEntry) != 0);
            this.mChildrenContainer.onNotificationUpdated();
        }
        if (this.mAnimationRunning) {
            setAnimationRunning(true);
        }
        if (this.mLastChronometerRunning) {
            setChronometerRunning(true);
        }
        ExpandableNotificationRow expandableNotificationRow2 = this.mNotificationParent;
        if (expandableNotificationRow2 != null && expandableNotificationRow2.mIsSummaryWithChildren) {
            expandableNotificationRow2.mChildrenContainer.updateChildrenAppearance();
        }
        onAttachedChildrenCountChanged();
        this.mPublicLayout.updateExpandButtonsDuringLayout(this.mShowPublicExpander, false);
        updateLimits();
        updateShelfIconColor();
        updateBackgroundColors();
        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateAllTextViewColors(this, this.mDimmed);
        initGroupHeaderContainAtMark();
        for (NotificationContentView notificationContentView2 : this.mLayouts) {
            View view13 = notificationContentView2.mContractedChild;
            if (view13 != null && (textView2 = (TextView) view13.findViewById(android.R.id.inbox_text0)) != null && textView2.getText().toString().contains("@")) {
                notificationContentView2.mIsContractedHeaderContainAtMark = true;
            }
            View view14 = notificationContentView2.mExpandedChild;
            if (view14 != null && (textView = (TextView) view14.findViewById(android.R.id.inbox_text0)) != null && textView.getText().toString().contains("@")) {
                notificationContentView2.mIsExpandedHeaderContainAtMark = true;
            }
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(NotificationMenuRowPlugin notificationMenuRowPlugin, Context context) {
        NotificationMenuRowPlugin notificationMenuRowPlugin2 = notificationMenuRowPlugin;
        NotificationMenuRowPlugin notificationMenuRowPlugin3 = this.mMenuRow;
        boolean z = (notificationMenuRowPlugin3 == null || notificationMenuRowPlugin3.getMenuView() == null) ? false : true;
        if (z) {
            removeView(this.mMenuRow.getMenuView());
        }
        if (notificationMenuRowPlugin2 == null) {
            return;
        }
        this.mMenuRow = notificationMenuRowPlugin2;
        if (notificationMenuRowPlugin2.shouldUseDefaultMenuItems()) {
            ArrayList<NotificationMenuRowPlugin.MenuItem> arrayList = new ArrayList<>();
            Context context2 = ((FrameLayout) this).mContext;
            arrayList.add(new NotificationMenuRow.NotificationMenuItem(context2, context2.getResources().getString(R.string.notification_menu_gear_description), (NotificationConversationInfo) LayoutInflater.from(context2).inflate(R.layout.notification_conversation_info, (ViewGroup) null, false), R.drawable.ic_settings));
            Context context3 = ((FrameLayout) this).mContext;
            arrayList.add(new NotificationMenuRow.NotificationMenuItem(context3, context3.getResources().getString(R.string.notification_menu_gear_description), (PartialConversationInfo) LayoutInflater.from(context3).inflate(R.layout.partial_conversation_info, (ViewGroup) null, false), R.drawable.ic_settings));
            Context context4 = ((FrameLayout) this).mContext;
            arrayList.add(new NotificationMenuRow.NotificationMenuItem(context4, context4.getResources().getString(R.string.notification_menu_gear_description), (NotificationInfo) LayoutInflater.from(context4).inflate(R.layout.notification_info, (ViewGroup) null, false), R.drawable.ic_settings));
            arrayList.add(SecGutInflater.createNotificationMenuItem(((FrameLayout) this).mContext, R.string.notification_menu_snooze_description, R.layout.sec_notification_snooze));
            this.mMenuRow.setMenuItems(arrayList);
        }
        if (z) {
            createMenu();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(NotificationMenuRowPlugin notificationMenuRowPlugin) {
        boolean z = this.mMenuRow.getMenuView() != null;
        this.mMenuRow = new NotificationMenuRow(((FrameLayout) this).mContext, this.mPeopleNotificationIdentifier);
        if (z) {
            createMenu();
        }
    }

    public final boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(obtain);
        dispatchPopulateAccessibilityEvent(obtain);
        accessibilityEvent.appendRecord(obtain);
        return true;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0 && isChildInGroup() && !isGroupExpanded()) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void onUiModeChanged() {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.onNotificationUpdated();
        }
        if (this.mIsCustomNotification || this.mIsCustomBigNotification || this.mIsCustomHeadsUpNotification || this.mIsCustomPublicNotification || this.mEntry.mSbn.getNotification().isMediaNotification() || (this.mEntry.mSbn.getNotification().isColorized() && this.mBgTint != 0)) {
            reInflateViews$1();
        } else {
            NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
            updateBackgroundColors();
            notificationColorPicker.updateAllTextViewColors(this, this.mDimmed);
            NotificationGuts notificationGuts = this.mGuts;
            if (notificationGuts != null) {
                notificationGuts.invalidate();
            }
            NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
            View menuView = notificationMenuRowPlugin == null ? null : notificationMenuRowPlugin.getMenuView();
            if (menuView != null) {
                int indexOfChild = indexOfChild(menuView);
                removeView(menuView);
                this.mMenuRow.createMenu(this, this.mEntry.mSbn);
                this.mMenuRow.setAppName(this.mAppName);
                addView(this.mMenuRow.getMenuView(), indexOfChild);
            }
            Arrays.stream(this.mLayouts).forEach(new ExpandableNotificationRow$$ExternalSyntheticLambda5());
        }
        NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
        if (notificationChildrenContainer2 != null) {
            Iterator it = ((ArrayList) notificationChildrenContainer2.mAttachedChildren).iterator();
            while (it.hasNext()) {
                ((ExpandableNotificationRow) it.next()).onUiModeChanged();
            }
        }
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        NotificationMenuRowPlugin notificationMenuRowPlugin;
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (i == 32) {
            doLongClickCallback(getWidth() / 2, getHeight() / 2);
            return true;
        }
        if (i == 262144 || i == 524288) {
            this.mExpandClickListener.onClick(this);
            return true;
        }
        if (i == 1048576) {
            performDismiss(true);
            return true;
        }
        if (i != R.id.action_snooze || (notificationMenuRowPlugin = this.mMenuRow) == null) {
            return false;
        }
        NotificationMenuRowPlugin.MenuItem snoozeMenuItem = notificationMenuRowPlugin.getSnoozeMenuItem(getContext());
        if (snoozeMenuItem != null) {
            doLongClickCallback(getWidth() / 2, getHeight() / 2, snoozeMenuItem);
        }
        return true;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (((GroupMembershipManagerImpl) this.mGroupMembershipManager).isGroupSummary(this.mEntry)) {
            if (!((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(this.mEntry)) {
                if ((this.mShowingPublic && this.mStatusBarStateController.getState() == 0) || ((SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class)).isKeyguardPanelDisabled()) {
                    return super.performClick();
                }
                this.mExpandClickListener.onClick(this);
                return true;
            }
        }
        return super.performClick();
    }

    public final void performDismiss(boolean z) {
        OnUserInteractionCallback onUserInteractionCallback;
        List attachedChildren;
        int indexOf;
        this.mMetricsLogger.count("notification_dismissed", 1);
        this.mDismissed = true;
        this.mRefocusOnDismiss = z;
        this.mLongPressListener = null;
        this.mDragController = null;
        this.mGroupParentWhenDismissed = this.mNotificationParent;
        this.mChildAfterViewWhenDismissed = null;
        Runnable runnable = this.mEntry.mIcons.mStatusBarIcon.mOnDismissListener;
        if (runnable != null) {
            runnable.run();
        }
        if (isChildInGroup() && (indexOf = (attachedChildren = this.mNotificationParent.getAttachedChildren()).indexOf(this)) != -1 && indexOf < attachedChildren.size() - 1) {
            this.mChildAfterViewWhenDismissed = (View) attachedChildren.get(indexOf + 1);
        }
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("try performDismiss : "), this.mEntry.mKey, "ExpandableNotifRow");
        if (!(!((NotificationDismissibilityProviderImpl) this.mDismissibilityProvider).nonDismissableEntryKeys.contains(this.mEntry.mKey)) || (onUserInteractionCallback = this.mOnUserInteractionCallback) == null) {
            return;
        }
        ((OnUserInteractionCallbackImpl) onUserInteractionCallback).registerFutureDismissal(this.mEntry, 2).run();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final long performRemoveAnimation(final float f, final long j, long j2, final AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide, final Runnable runnable, final Runnable runnable2, final boolean z) {
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        final long j3 = 0;
        if (notificationMenuRowPlugin == null || !notificationMenuRowPlugin.isMenuVisible()) {
            super.performRemoveAnimation(f, j, 0L, animatorListenerAdapter, clipSide, runnable, runnable2, z);
            return 0L;
        }
        Animator translateViewAnimator = getTranslateViewAnimator(0.0f, null);
        translateViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
                long j4 = j;
                long j5 = j3;
                float f2 = f;
                boolean z2 = z;
                ExpandableNotificationRow.super.performRemoveAnimation(f2, j4, j5, animatorListenerAdapter, ExpandableView.ClipSide.BOTTOM, null, runnable2, z2);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        });
        translateViewAnimator.start();
        return translateViewAnimator.getDuration();
    }

    public final void reInflateViews$1() {
        HybridNotificationView hybridNotificationView;
        Trace.beginSection("ExpandableNotificationRow#reInflateViews");
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            AnonymousClass1 anonymousClass1 = this.mExpandClickListener;
            StatusBarNotification statusBarNotification = this.mEntry.mSbn;
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            Flags.notificationAsyncGroupHeaderInflation();
            NotificationHeaderView notificationHeaderView = notificationChildrenContainer.mGroupHeader;
            if (notificationHeaderView != null) {
                notificationChildrenContainer.removeView(notificationHeaderView);
                notificationChildrenContainer.mGroupHeader = null;
            }
            NotificationHeaderView notificationHeaderView2 = notificationChildrenContainer.mMinimizedGroupHeader;
            if (notificationHeaderView2 != null) {
                notificationChildrenContainer.removeView(notificationHeaderView2);
                notificationChildrenContainer.mMinimizedGroupHeader = null;
            }
            NotificationHeaderView notificationHeaderView3 = notificationChildrenContainer.mNotificationHeaderExpanded;
            if (notificationHeaderView3 != null) {
                notificationChildrenContainer.removeView(notificationHeaderView3);
                notificationChildrenContainer.mNotificationHeaderExpanded = null;
            }
            notificationChildrenContainer.recreateNotificationHeader(anonymousClass1, notificationChildrenContainer.mIsConversation);
            notificationChildrenContainer.initDimens$2();
            for (int i2 = 0; i2 < ((ArrayList) notificationChildrenContainer.mDividers).size(); i2++) {
                View view = (View) ((ArrayList) notificationChildrenContainer.mDividers).get(i2);
                int indexOfChild = notificationChildrenContainer.indexOfChild(view);
                notificationChildrenContainer.removeView(view);
                View inflateDivider = notificationChildrenContainer.inflateDivider();
                notificationChildrenContainer.addView(inflateDivider, indexOfChild);
                ((ArrayList) notificationChildrenContainer.mDividers).set(i2, inflateDivider);
            }
            notificationChildrenContainer.removeView(notificationChildrenContainer.mOverflowNumber);
            notificationChildrenContainer.mOverflowNumber = null;
            notificationChildrenContainer.mGroupOverFlowState = null;
            notificationChildrenContainer.updateGroupOverflow();
        }
        NotificationGuts notificationGuts = this.mGuts;
        if (notificationGuts != null) {
            int indexOfChild2 = indexOfChild(notificationGuts);
            removeView(notificationGuts);
            NotificationGuts notificationGuts2 = (NotificationGuts) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.notification_guts, (ViewGroup) this, false);
            this.mGuts = notificationGuts2;
            notificationGuts2.setVisibility(notificationGuts.mExposed ? 0 : 8);
            addView(this.mGuts, indexOfChild2);
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        View menuView = notificationMenuRowPlugin == null ? null : notificationMenuRowPlugin.getMenuView();
        if (menuView != null) {
            int indexOfChild3 = indexOfChild(menuView);
            removeView(menuView);
            this.mMenuRow.createMenu(this, this.mEntry.mSbn);
            this.mMenuRow.setAppName(this.mAppName);
            addView(this.mMenuRow.getMenuView(), indexOfChild3);
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mMinContractedHeight = notificationContentView.getResources().getDimensionPixelSize(R.dimen.min_notification_layout_height);
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            Flags.notificationAsyncHybridViewInflation();
            if (notificationContentView.mIsChildInGroup && (hybridNotificationView = notificationContentView.mSingleLineView) != null) {
                notificationContentView.removeView(hybridNotificationView);
                notificationContentView.mSingleLineView = null;
                notificationContentView.updateAllSingleLineViews();
            }
        }
        this.mEntry.mSbn.clearPackageContext();
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mRowContentBindStage.getStageParams(this.mEntry);
        rowContentBindParams.mViewsNeedReinflation = true;
        rowContentBindParams.mDirtyContentViews = rowContentBindParams.mContentViews | rowContentBindParams.mDirtyContentViews;
        this.mRowContentBindStage.requestRebind(this.mEntry, null);
        Trace.endSection();
    }

    public final void removeChildNotification(ExpandableNotificationRow expandableNotificationRow) {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.removeNotification(expandableNotificationRow);
            expandableNotificationRow.mKeepInParentForDismissAnimation = false;
        }
        onAttachedChildrenCountChanged();
        expandableNotificationRow.setIsChildInGroup(null, false);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
            if (subscreenDeviceModelParent.isSubScreen()) {
                if (subscreenDeviceModelParent.isShownDetail()) {
                    Log.d("S.S.N.", "removeChildNotification parent -  Detail State");
                    return;
                }
                if (expandableNotificationRow.mEntry.mRanking.getChannel().isImportantConversation()) {
                    Log.d("S.S.N.", "removeChildNotification parent -  isImportantConversation");
                    return;
                }
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("removeChildNotification parent - remove Item  : ", expandableNotificationRow.mEntry.mKey, "S.S.N.");
                NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
                Integer valueOf = notificationChildrenContainer2 != null ? Integer.valueOf(notificationChildrenContainer2.getNotificationChildCount()) : null;
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(expandableNotificationRow.mEntry.mKey);
                if (valueOf == null || valueOf.intValue() != 0) {
                    return;
                }
                subscreenDeviceModelParent.removeMainHashItem(this.mEntry);
                subscreenDeviceModelParent.notifyListAdapterItemRemoved(this.mEntry);
            }
        }
    }

    public final void removeChildrenWithKeepInParent() {
        if (this.mChildrenContainer == null) {
            return;
        }
        Iterator it = new ArrayList(this.mChildrenContainer.mAttachedChildren).iterator();
        boolean z = false;
        while (it.hasNext()) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) it.next();
            if (expandableNotificationRow.mKeepInParentForDismissAnimation) {
                this.mChildrenContainer.removeNotification(expandableNotificationRow);
                expandableNotificationRow.setIsChildInGroup(null, false);
                expandableNotificationRow.mKeepInParentForDismissAnimation = false;
                ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
                if (anonymousClass2 != null) {
                    NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                    NotificationEntry notificationEntry2 = this.mEntry;
                    NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                    notificationRowLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    NotificationRowLogger$logKeepInParentChildDetached$2 notificationRowLogger$logKeepInParentChildDetached$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logKeepInParentChildDetached$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return FontProvider$$ExternalSyntheticOutline0.m("Detach child ", logMessage.getStr1(), " kept in parent ", logMessage.getStr2());
                        }
                    };
                    LogBuffer logBuffer = notificationRowLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$logKeepInParentChildDetached$2, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                    logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
                    logBuffer.commit(obtain);
                }
                z = true;
            }
        }
        if (z) {
            onAttachedChildrenCountChanged();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void removeFromTransientContainer() {
        ViewGroup viewGroup = this.mTransientContainer;
        ViewParent parent = getParent();
        if (viewGroup == null || viewGroup != parent) {
            super.removeFromTransientContainer();
            return;
        }
        ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
        if (anonymousClass2 != null) {
            boolean z = viewGroup instanceof NotificationChildrenContainer;
            ExpandableNotificationRowController expandableNotificationRowController = ExpandableNotificationRowController.this;
            if (z) {
                NotificationEntry notificationEntry = this.mEntry;
                NotificationEntry notificationEntry2 = ((NotificationChildrenContainer) viewGroup).mContainingNotification.mEntry;
                NotificationRowLogger notificationRowLogger = expandableNotificationRowController.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotificationRowLogger$logRemoveTransientFromContainer$2 notificationRowLogger$logRemoveTransientFromContainer$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logRemoveTransientFromContainer$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return FontProvider$$ExternalSyntheticOutline0.m("RemoveTransientRow from ChildrenContainer: childKey: ", logMessage.getStr1(), " -- containerKey: ", logMessage.getStr2());
                    }
                };
                LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
                LogMessage obtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$logRemoveTransientFromContainer$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logBuffer.commit(obtain);
            } else if (viewGroup instanceof NotificationStackScrollLayout) {
                NotificationEntry notificationEntry3 = this.mEntry;
                NotificationRowLogger notificationRowLogger2 = expandableNotificationRowController.mLogBufferLogger;
                notificationRowLogger2.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                NotificationRowLogger$logRemoveTransientFromNssl$2 notificationRowLogger$logRemoveTransientFromNssl$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logRemoveTransientFromNssl$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RemoveTransientRow from Nssl: childKey: ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer2 = notificationRowLogger2.notificationRenderBuffer;
                LogMessage obtain2 = logBuffer2.obtain("NotifRow", logLevel2, notificationRowLogger$logRemoveTransientFromNssl$2, null);
                ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry3);
                logBuffer2.commit(obtain2);
            } else {
                NotificationEntry notificationEntry4 = this.mEntry;
                NotificationRowLogger notificationRowLogger3 = expandableNotificationRowController.mLogBufferLogger;
                notificationRowLogger3.getClass();
                LogLevel logLevel3 = LogLevel.WARNING;
                NotificationRowLogger$logRemoveTransientFromViewGroup$2 notificationRowLogger$logRemoveTransientFromViewGroup$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logRemoveTransientFromViewGroup$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return FontProvider$$ExternalSyntheticOutline0.m("RemoveTransientRow from other ViewGroup: childKey: ", logMessage.getStr1(), " -- ViewGroup: ", logMessage.getStr2());
                    }
                };
                LogBuffer logBuffer3 = notificationRowLogger3.notificationRenderBuffer;
                LogMessage obtain3 = logBuffer3.obtain("NotifRow", logLevel3, notificationRowLogger$logRemoveTransientFromViewGroup$2, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
                logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry4);
                logMessageImpl2.str2 = viewGroup.toString();
                logBuffer3.commit(obtain3);
            }
        }
        super.removeFromTransientContainer();
    }

    public final void removeTransientView(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                NotificationEntry notificationEntry2 = this.mEntry;
                NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                NotificationRowLogger$logRemoveTransientRow$2 notificationRowLogger$logRemoveTransientRow$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowLogger$logRemoveTransientRow$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return FontProvider$$ExternalSyntheticOutline0.m("removeTransientRow from row: childKey: ", logMessage.getStr1(), " -- containerKey: ", logMessage.getStr2());
                    }
                };
                LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
                LogMessage obtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$logRemoveTransientRow$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logBuffer.commit(obtain);
            }
        }
        super.removeTransientView(view);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void resetAllContentAlphas() {
        this.mPrivateLayout.setAlpha(1.0f);
        this.mPrivateLayout.setLayerType(0, null);
        this.mPublicLayout.setAlpha(1.0f);
        this.mPublicLayout.setLayerType(0, null);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setAlpha(1.0f);
            this.mChildrenContainer.setLayerType(0, null);
        }
    }

    public final void resetTranslation() {
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
        if (this.mDismissUsingRowTranslationX) {
            setTranslationX(0.0f);
        } else if (this.mTranslateableViews != null) {
            for (int i = 0; i < this.mTranslateableViews.size(); i++) {
                ((View) this.mTranslateableViews.get(i)).setTranslationX(0.0f);
            }
            invalidateOutline();
            this.mEntry.mIcons.mShelfIcon.setScrollX(0);
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin != null) {
            notificationMenuRowPlugin.resetMenu();
        }
    }

    public final void setAboveShelf(boolean z) {
        boolean isAboveShelf = isAboveShelf();
        this.mAboveShelf = z;
        if (isAboveShelf() != isAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!isAboveShelf);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setActualHeight(int i, boolean z) {
        super.setActualHeight(i, z);
        NotificationGuts notificationGuts = this.mGuts;
        if (notificationGuts != null && notificationGuts.mExposed) {
            notificationGuts.mActualHeight = i;
            notificationGuts.invalidate();
            return;
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mUnrestrictedContentHeight = Math.max(i, notificationContentView.getMinHeight(true));
            notificationContentView.mContentHeight = Math.min(notificationContentView.mUnrestrictedContentHeight, (notificationContentView.mContainingNotification.getIntrinsicHeight() - notificationContentView.getExtraRemoteInputHeight(notificationContentView.mExpandedRemoteInput)) - notificationContentView.getExtraRemoteInputHeight(notificationContentView.mHeadsUpRemoteInput));
            notificationContentView.selectLayout(notificationContentView.mAnimate, false);
            if (notificationContentView.mContractedChild != null) {
                int minContentHeightHint = notificationContentView.getMinContentHeightHint();
                NotificationViewWrapper visibleWrapper = notificationContentView.getVisibleWrapper(notificationContentView.mVisibleType);
                if (visibleWrapper != null) {
                    visibleWrapper.setContentHeight(notificationContentView.mUnrestrictedContentHeight, minContentHeightHint);
                }
                NotificationViewWrapper visibleWrapper2 = notificationContentView.getVisibleWrapper(notificationContentView.mTransformationStartVisibleType);
                if (visibleWrapper2 != null) {
                    visibleWrapper2.setContentHeight(notificationContentView.mUnrestrictedContentHeight, minContentHeightHint);
                }
                notificationContentView.updateClipping();
                notificationContentView.invalidateOutline();
            }
        }
        if (this.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            if (notificationChildrenContainer.mUserLocked) {
                notificationChildrenContainer.mActualHeight = i;
                float groupExpandFraction = notificationChildrenContainer.getGroupExpandFraction();
                if (notificationChildrenContainer.mUserLocked && notificationChildrenContainer.showingLowPriorityGroupHeader()) {
                    float groupExpandFraction2 = notificationChildrenContainer.getGroupExpandFraction();
                    notificationChildrenContainer.mNotificationHeaderWrapperExpanded.transformFrom(groupExpandFraction2, notificationChildrenContainer.mMinimizedGroupHeaderWrapper);
                    notificationChildrenContainer.mNotificationHeaderExpanded.setVisibility(0);
                    notificationChildrenContainer.mMinimizedGroupHeaderWrapper.transformTo(groupExpandFraction2, notificationChildrenContainer.mNotificationHeaderWrapperExpanded);
                } else {
                    float groupExpandFraction3 = notificationChildrenContainer.getGroupExpandFraction();
                    if (!notificationChildrenContainer.mContainingNotification.isGroupExpanded()) {
                        notificationChildrenContainer.mNotificationHeaderWrapperExpanded.transformFrom(groupExpandFraction3, notificationChildrenContainer.mGroupHeaderWrapper);
                        notificationChildrenContainer.mNotificationHeaderExpanded.setVisibility(0);
                        notificationChildrenContainer.mGroupHeaderWrapper.transformTo(groupExpandFraction3, notificationChildrenContainer.mNotificationHeaderWrapperExpanded);
                    }
                }
                int maxAllowedVisibleChildren = notificationChildrenContainer.getMaxAllowedVisibleChildren(true);
                int size = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
                for (int i2 = 0; i2 < size; i2++) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i2);
                    float maxExpandHeight = expandableNotificationRow.isExpanded(true) ? expandableNotificationRow.getMaxExpandHeight() : expandableNotificationRow.getShowingLayout().getMinHeight(true);
                    if (i2 < maxAllowedVisibleChildren) {
                        expandableNotificationRow.setActualHeight((int) NotificationUtils.interpolate(expandableNotificationRow.getShowingLayout().getMinHeight(true), maxExpandHeight, groupExpandFraction), false);
                    } else {
                        expandableNotificationRow.setActualHeight((int) maxExpandHeight, false);
                    }
                }
            }
        }
        NotificationGuts notificationGuts2 = this.mGuts;
        if (notificationGuts2 != null) {
            notificationGuts2.mActualHeight = i;
            notificationGuts2.invalidate();
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin != null && notificationMenuRowPlugin.getMenuView() != null) {
            this.mMenuRow.onParentHeightUpdate();
        }
        if (this.mOnIntrinsicHeightReachedRunnable == null || this.mActualHeight != getIntrinsicHeight()) {
            return;
        }
        this.mOnIntrinsicHeightReachedRunnable.run();
        this.mOnIntrinsicHeightReachedRunnable = null;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setActualHeightAnimating(boolean z) {
        NotificationContentView notificationContentView = this.mPrivateLayout;
        if (notificationContentView == null || z) {
            return;
        }
        notificationContentView.mContentHeightAtAnimationStart = -1;
    }

    public final void setAnimationRunning(boolean z) {
        int i = 0;
        for (NotificationContentView notificationContentView : this.mLayouts) {
            if (notificationContentView != null) {
                if (z != notificationContentView.mContentAnimating) {
                    NotificationViewWrapper notificationViewWrapper = notificationContentView.mContractedWrapper;
                    if (notificationViewWrapper != null) {
                        notificationViewWrapper.setAnimationsRunning(z);
                    }
                    NotificationViewWrapper notificationViewWrapper2 = notificationContentView.mExpandedWrapper;
                    if (notificationViewWrapper2 != null) {
                        notificationViewWrapper2.setAnimationsRunning(z);
                    }
                    NotificationViewWrapper notificationViewWrapper3 = notificationContentView.mHeadsUpWrapper;
                    if (notificationViewWrapper3 != null) {
                        notificationViewWrapper3.setAnimationsRunning(z);
                    }
                    notificationContentView.mContentAnimating = z;
                }
                View view = notificationContentView.mContractedChild;
                View view2 = notificationContentView.mExpandedChild;
                View view3 = notificationContentView.mHeadsUpChild;
                setIconAnimationRunningForChild(view, z);
                setIconAnimationRunningForChild(view2, z);
                setIconAnimationRunningForChild(view3, z);
            }
        }
        if (this.mIsSummaryWithChildren) {
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mChildrenContainer.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper != null) {
                setIconAnimationRunningForChild(notificationHeaderViewWrapper.mIcon, z);
            }
            NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mChildrenContainer.mMinimizedGroupHeaderWrapper;
            if (notificationHeaderViewWrapper2 != null) {
                setIconAnimationRunningForChild(notificationHeaderViewWrapper2.mIcon, z);
            }
            List list = this.mChildrenContainer.mAttachedChildren;
            while (true) {
                ArrayList arrayList = (ArrayList) list;
                if (i >= arrayList.size()) {
                    break;
                }
                ((ExpandableNotificationRow) arrayList.get(i)).setAnimationRunning(z);
                i++;
            }
        }
        this.mAnimationRunning = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void setBackgroundTintColor(final int i) {
        super.setBackgroundTintColor(i);
        final NotificationContentView showingLayout = getShowingLayout();
        if (showingLayout != null) {
            final int i2 = 0;
            Optional map = Optional.ofNullable(showingLayout.mNotificationEntry).map(new Function() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    switch (i2) {
                        case 0:
                            return ((NotificationEntry) obj).mSbn;
                        default:
                            return ((StatusBarNotification) obj).getNotification();
                    }
                }
            });
            final int i3 = 1;
            map.map(new Function() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    switch (i3) {
                        case 0:
                            return ((NotificationEntry) obj).mSbn;
                        default:
                            return ((StatusBarNotification) obj).getNotification();
                    }
                }
            }).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationContentView notificationContentView = NotificationContentView.this;
                    int i4 = i;
                    Notification notification2 = (Notification) obj;
                    int i5 = NotificationContentView.$r8$clinit;
                    notificationContentView.getClass();
                    boolean isColorized = notification2.isColorized();
                    RemoteInputView remoteInputView = notificationContentView.mExpandedRemoteInput;
                    if (remoteInputView != null) {
                        remoteInputView.setBackgroundTintColor(i4, notification2.color, isColorized);
                    }
                    RemoteInputView remoteInputView2 = notificationContentView.mHeadsUpRemoteInput;
                    if (remoteInputView2 != null) {
                        remoteInputView2.setBackgroundTintColor(i4, notification2.color, isColorized);
                    }
                }
            });
        }
    }

    public void setChildrenContainer(NotificationChildrenContainer notificationChildrenContainer) {
        this.mChildrenContainer = notificationChildrenContainer;
    }

    public final void setChronometerRunning(boolean z, NotificationContentView notificationContentView) {
        if (notificationContentView != null) {
            boolean z2 = z || this.mIsPinned;
            View view = notificationContentView.mContractedChild;
            View view2 = notificationContentView.mExpandedChild;
            View view3 = notificationContentView.mHeadsUpChild;
            setChronometerRunningForChild(view, z2);
            setChronometerRunningForChild(view2, z2);
            setChronometerRunningForChild(view3, z2);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setClipBottomAmount(int i) {
        if (this.mExpandAnimationRunning) {
            return;
        }
        if (i != this.mClipBottomAmount) {
            super.setClipBottomAmount(i);
            for (NotificationContentView notificationContentView : this.mLayouts) {
                notificationContentView.mClipBottomAmount = i;
                notificationContentView.updateClipping();
            }
            NotificationGuts notificationGuts = this.mGuts;
            if (notificationGuts != null) {
                notificationGuts.mClipBottomAmount = i;
                notificationGuts.invalidate();
            }
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null || this.mChildIsExpanding) {
            return;
        }
        notificationChildrenContainer.mClipBottomAmount = i;
        notificationChildrenContainer.updateChildrenClipping();
    }

    public final void setClipToActualHeight(boolean z) {
        boolean z2 = true;
        this.mClipToActualHeight = z || this.mUserLocked || isExpanded(false);
        updateClipping$1();
        NotificationContentView showingLayout = getShowingLayout();
        if (!z && !this.mUserLocked && !isExpanded(false)) {
            z2 = false;
        }
        showingLayout.mClipToActualHeight = z2;
        showingLayout.updateClipping();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setClipTopAmount(int i) {
        super.setClipTopAmount(i);
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mClipTopAmount = i;
            notificationContentView.updateClipping();
        }
        NotificationGuts notificationGuts = this.mGuts;
        if (notificationGuts != null) {
            notificationGuts.mClipTopAmount = i;
            notificationGuts.invalidate();
        }
    }

    public final void setContentAlpha(float f) {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setAlpha(f);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            if (notificationChildrenContainer.mGroupHeader != null) {
                for (int i = 0; i < notificationChildrenContainer.mGroupHeader.getChildCount(); i++) {
                    notificationChildrenContainer.mGroupHeader.getChildAt(i).setAlpha(f);
                }
            }
            Iterator it = ((ArrayList) notificationChildrenContainer.mAttachedChildren).iterator();
            while (it.hasNext()) {
                ((ExpandableNotificationRow) it.next()).setContentAlpha(f);
            }
        }
    }

    public final void setContentAlphaLocked(boolean z) {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mIsLockedAlpha = z;
        }
    }

    public final void setContentClipTopAmount(int i) {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mClipTopAmount = i;
            notificationContentView.updateClipping();
        }
    }

    public final void setDismissUsingRowTranslationX(boolean z) {
        if (z == this.mDismissUsingRowTranslationX) {
            return;
        }
        float translation = getTranslation();
        if (translation != 0.0f) {
            setTranslation(0.0f);
        }
        this.mDismissUsingRowTranslationX = z;
        if (translation != 0.0f) {
            setTranslation(translation);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null) {
            return;
        }
        List list = notificationChildrenContainer.mAttachedChildren;
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ((ExpandableNotificationRow) arrayList.get(i)).setDismissUsingRowTranslationX(z);
            i++;
        }
    }

    public void setEntry(NotificationEntry notificationEntry) {
        this.mEntry = notificationEntry;
    }

    public final void setExpandAnimationRunning(boolean z) {
        if (z) {
            setAboveShelf(true);
            this.mExpandAnimationRunning = true;
            this.mViewState.cancelAnimations(this);
            this.mNotificationLaunchHeight = Math.max(1, getContext().getResources().getDimensionPixelSize(R.dimen.z_distance_between_notifications)) * 4;
        } else {
            this.mExpandAnimationRunning = false;
            setAboveShelf(isAboveShelf());
            setVisibility(0);
            NotificationGuts notificationGuts = this.mGuts;
            if (notificationGuts != null) {
                notificationGuts.setAlpha(1.0f);
            }
            resetAllContentAlphas();
            this.mExtraWidthForClipping = 0.0f;
            invalidate();
            ExpandableNotificationRow expandableNotificationRow = this.mNotificationParent;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.mExtraWidthForClipping = 0.0f;
                expandableNotificationRow.invalidate();
                ExpandableNotificationRow expandableNotificationRow2 = this.mNotificationParent;
                expandableNotificationRow2.mMinimumHeightForClipping = 0;
                expandableNotificationRow2.updateClipping$1();
                expandableNotificationRow2.invalidate();
            }
        }
        ExpandableNotificationRow expandableNotificationRow3 = this.mNotificationParent;
        if (expandableNotificationRow3 != null) {
            expandableNotificationRow3.mChildIsExpanding = this.mExpandAnimationRunning;
            expandableNotificationRow3.updateClipping$1();
            expandableNotificationRow3.invalidate();
        }
        updateChildrenVisibility();
        updateClipping$1();
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        notificationBackgroundView.mExpandAnimationRunning = z;
        Drawable drawable = notificationBackgroundView.mBackground;
        if (drawable instanceof LayerDrawable) {
            ((GradientDrawable) ((LayerDrawable) drawable).getDrawable(0)).setAntiAlias(!z);
        }
        boolean z2 = notificationBackgroundView.mExpandAnimationRunning;
        if (!z2) {
            int i = notificationBackgroundView.mDrawableAlpha;
            notificationBackgroundView.mDrawableAlpha = i;
            if (!z2) {
                notificationBackgroundView.mBackground.setAlpha(i);
            }
        }
        notificationBackgroundView.invalidate();
    }

    public final void setHeadsUpAnimatingAway(boolean z) {
        Consumer consumer;
        boolean isAboveShelf = isAboveShelf();
        boolean z2 = z != this.mHeadsupDisappearRunning;
        this.mHeadsupDisappearRunning = z;
        NotificationContentView notificationContentView = this.mPrivateLayout;
        notificationContentView.mHeadsUpAnimatingAway = z;
        notificationContentView.selectLayout(false, true);
        if (z2 && (consumer = this.mHeadsUpAnimatingAwayListener) != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        if (isAboveShelf() != isAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!isAboveShelf);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setHeadsUpIsVisible() {
        this.mMustStayOnScreen = false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setHideSensitive(boolean z, boolean z2) {
        NotificationChildrenContainer notificationChildrenContainer;
        if (getVisibility() == 8) {
            return;
        }
        boolean z3 = this.mShowingPublic;
        boolean z4 = this.mSensitive && z;
        this.mShowingPublic = z4;
        if (this.mShowingPublicInitialized && z4 == z3) {
            return;
        }
        getContentView().getAlpha();
        this.mPublicLayout.animate().cancel();
        this.mPrivateLayout.animate().cancel();
        NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
        if (notificationChildrenContainer2 != null) {
            notificationChildrenContainer2.animate().cancel();
        }
        resetAllContentAlphas();
        this.mPublicLayout.setVisibility(this.mShowingPublic ? 0 : 4);
        updateChildrenVisibility();
        int i = NotificationContentAlphaOptimization.$r8$clinit;
        Flags.FEATURE_FLAGS.getClass();
        getShowingLayout().updateBackgroundColor(z2);
        this.mPrivateLayout.updateExpandButtonsDuringLayout(isExpandable$1(), false);
        updateShelfIconColor();
        this.mShowingPublicInitialized = true;
        if (!this.mIsSummaryWithChildren || (notificationChildrenContainer = this.mChildrenContainer) == null || this.mShowingPublic) {
            return;
        }
        notificationChildrenContainer.updateHeaderVisibility(false, true);
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateChildrenAppearance();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setHideSensitiveForIntrinsicHeight(boolean z) {
        this.mHideSensitiveForIntrinsicHeight = z;
        if (!this.mIsSummaryWithChildren) {
            return;
        }
        List list = this.mChildrenContainer.mAttachedChildren;
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ((ExpandableNotificationRow) arrayList.get(i)).setHideSensitiveForIntrinsicHeight(z);
            i++;
        }
    }

    public final void setIsChildInGroup(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        ExpandableNotificationRow expandableNotificationRow2;
        if (this.mExpandAnimationRunning && !z && (expandableNotificationRow2 = this.mNotificationParent) != null) {
            expandableNotificationRow2.mChildIsExpanding = false;
            expandableNotificationRow2.updateClipping$1();
            expandableNotificationRow2.invalidate();
            ExpandableNotificationRow expandableNotificationRow3 = this.mNotificationParent;
            expandableNotificationRow3.mExpandingClipPath = null;
            expandableNotificationRow3.invalidate();
            ExpandableNotificationRow expandableNotificationRow4 = this.mNotificationParent;
            expandableNotificationRow4.mExtraWidthForClipping = 0.0f;
            expandableNotificationRow4.invalidate();
            ExpandableNotificationRow expandableNotificationRow5 = this.mNotificationParent;
            expandableNotificationRow5.mMinimumHeightForClipping = 0;
            expandableNotificationRow5.updateClipping$1();
            expandableNotificationRow5.invalidate();
        }
        if (!z) {
            expandableNotificationRow = null;
        }
        this.mNotificationParent = expandableNotificationRow;
        NotificationContentView notificationContentView = this.mPrivateLayout;
        notificationContentView.mIsChildInGroup = z;
        if (notificationContentView.mContractedChild != null) {
            notificationContentView.mContractedWrapper.setIsChildInGroup(z);
        }
        if (notificationContentView.mExpandedChild != null) {
            notificationContentView.mExpandedWrapper.setIsChildInGroup(notificationContentView.mIsChildInGroup);
        }
        if (notificationContentView.mHeadsUpChild != null) {
            notificationContentView.mHeadsUpWrapper.setIsChildInGroup(notificationContentView.mIsChildInGroup);
        }
        notificationContentView.updateAllSingleLineViews();
        updateBackgroundForGroupState();
        updateClickAndFocus();
        if (this.mNotificationParent != null) {
            this.mOverrideTint = 0;
            this.mOverrideAmount = 0.0f;
            setBackgroundTintColor(calculateBgColor(true, true));
            this.mNotificationParent.updateBackgroundForGroupState();
        }
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        boolean isChildInGroup = true ^ isChildInGroup();
        if (isChildInGroup != notificationBackgroundView.mBottomAmountClips) {
            notificationBackgroundView.mBottomAmountClips = isChildInGroup;
            notificationBackgroundView.invalidate();
        }
        float f = this.mSmallRoundness;
        requestRoundness(f, f, BASE_VALUE, false);
    }

    public final void setNotificationFaded(boolean z) {
        this.mIsFaded = z;
        if (!childrenRequireOverlappingRendering()) {
            NotificationFadeAware.setLayerTypeForFaded(this, false);
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            if (notificationChildrenContainer instanceof NotificationFadeAware) {
                notificationChildrenContainer.setNotificationFaded(z);
            } else {
                NotificationFadeAware.setLayerTypeForFaded(notificationChildrenContainer, z);
            }
            for (NotificationContentView notificationContentView : this.mLayouts) {
                if (notificationContentView instanceof NotificationFadeAware) {
                    notificationContentView.setNotificationFaded(z);
                } else {
                    NotificationFadeAware.setLayerTypeForFaded(notificationContentView, z);
                }
            }
            return;
        }
        NotificationFadeAware.setLayerTypeForFaded(this, z);
        NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
        if (notificationChildrenContainer2 instanceof NotificationFadeAware) {
            notificationChildrenContainer2.setNotificationFaded(false);
        } else {
            NotificationFadeAware.setLayerTypeForFaded(notificationChildrenContainer2, false);
        }
        for (NotificationContentView notificationContentView2 : this.mLayouts) {
            if (notificationContentView2 instanceof NotificationFadeAware) {
                notificationContentView2.setNotificationFaded(false);
            } else {
                NotificationFadeAware.setLayerTypeForFaded(notificationContentView2, false);
            }
        }
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
        updateClickAndFocus();
    }

    public final void setOnKeyguard(boolean z) {
        if (z != this.mOnKeyguard) {
            boolean isAboveShelf = isAboveShelf();
            boolean isExpanded = isExpanded(false);
            this.mOnKeyguard = z;
            onExpansionChanged(false, isExpanded);
            if (isExpanded != isExpanded(false)) {
                if (this.mIsSummaryWithChildren) {
                    this.mChildrenContainer.updateGroupOverflow();
                }
                notifyHeightChanged(false);
            }
            if (isAboveShelf() != isAboveShelf) {
                this.mAboveShelfChangedListener.onAboveShelfStateChanged(!isAboveShelf);
            }
        }
    }

    public void setPrivateLayout(NotificationContentView notificationContentView) {
        this.mPrivateLayout = notificationContentView;
        this.mLayouts = new NotificationContentView[]{notificationContentView, this.mPublicLayout};
    }

    public void setPublicLayout(NotificationContentView notificationContentView) {
        this.mPublicLayout = notificationContentView;
        this.mLayouts = new NotificationContentView[]{this.mPrivateLayout, notificationContentView};
    }

    public final void setTranslation(float f) {
        invalidate();
        if (this.mDismissUsingRowTranslationX) {
            setTranslationX(f);
        } else if (this.mTranslateableViews != null) {
            for (int i = 0; i < this.mTranslateableViews.size(); i++) {
                if (this.mTranslateableViews.get(i) != null) {
                    ((View) this.mTranslateableViews.get(i)).setTranslationX(f);
                }
            }
            invalidateOutline();
            this.mEntry.mIcons.mShelfIcon.setScrollX((int) (-f));
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin == null || notificationMenuRowPlugin.getMenuView() == null) {
            return;
        }
        this.mMenuRow.onParentTranslationUpdate(f);
    }

    public final void setUserExpanded(boolean z, boolean z2) {
        if (this.mIsSummaryWithChildren && !shouldShowPublic() && z2) {
            this.mChildrenContainer.getClass();
            boolean isGroupExpanded = ((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(this.mEntry);
            ((GroupExpansionManagerImpl) this.mGroupExpansionManager).setGroupExpanded(this.mEntry, z);
            onExpansionChanged(true, isGroupExpanded);
            return;
        }
        if (!z || this.mExpandable) {
            boolean isExpanded = isExpanded(false);
            this.mHasUserChangedExpansion = true;
            this.mUserExpanded = z;
            onExpansionChanged(true, isExpanded);
            if (isExpanded || !isExpanded(false) || this.mActualHeight == getIntrinsicHeight()) {
                return;
            }
            notifyHeightChanged(true);
        }
    }

    public final void setUserLocked(boolean z) {
        this.mUserLocked = z;
        NotificationContentView notificationContentView = this.mPrivateLayout;
        notificationContentView.mUserExpanding = z;
        if (z) {
            notificationContentView.mTransformationStartVisibleType = notificationContentView.mVisibleType;
        } else {
            notificationContentView.mTransformationStartVisibleType = -1;
            int calculateVisibleType = notificationContentView.calculateVisibleType();
            notificationContentView.mVisibleType = calculateVisibleType;
            notificationContentView.updateViewVisibilities(calculateVisibleType);
            notificationContentView.updateBackgroundColor(false);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setUserLocked(z);
            if (this.mIsSummaryWithChildren) {
                if (z || !isGroupExpanded()) {
                    updateBackgroundForGroupState();
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean shouldClipToActualHeight() {
        return !this.mExpandAnimationRunning;
    }

    public final boolean shouldShowPublic() {
        return this.mSensitive && this.mHideSensitiveForIntrinsicHeight;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean showingPulsing() {
        StatusBarStateController statusBarStateController;
        KeyguardBypassController keyguardBypassController;
        return isHeadsUpState() && (((statusBarStateController = this.mStatusBarStateController) != null && statusBarStateController.isDozing()) || (this.mOnKeyguard && ((keyguardBypassController = this.mBypassController) == null || keyguardBypassController.getBypassEnabled())));
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void updateBackgroundColors() {
        super.updateBackgroundColors();
        this.mColorUpdateLogger.getClass();
        if (this.mIsSummaryWithChildren) {
            Iterator it = ((ArrayList) this.mChildrenContainer.mAttachedChildren).iterator();
            while (it.hasNext()) {
                ((ExpandableNotificationRow) it.next()).updateBackgroundColors();
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBackgroundForGroupState() {
        /*
            r4 = this;
            boolean r0 = r4.mIsSummaryWithChildren
            r1 = 0
            if (r0 == 0) goto L5c
            boolean r0 = r4.isGroupExpanded()
            r2 = 1
            if (r0 != 0) goto L18
            boolean r0 = r4.mUserLocked
            if (r0 == 0) goto L16
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r0 = r4.mChildrenContainer
            r0.getClass()
            goto L18
        L16:
            r0 = r1
            goto L19
        L18:
            r0 = r2
        L19:
            r4.mShowNoBackground = r0
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r0 = r4.mChildrenContainer
            r0.getClass()
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r0 = r4.mChildrenContainer
            boolean r3 = r4.mShowNoBackground
            r3 = r3 ^ r2
            com.android.internal.widget.CachingIconView r0 = r0.mGroupIconView
            if (r0 == 0) goto L31
            if (r3 == 0) goto L2d
            r3 = r1
            goto L2e
        L2d:
            r3 = 4
        L2e:
            r0.setVisibility(r3)
        L31:
            boolean r0 = r4.shouldShowPublic()
            if (r0 == 0) goto L3a
            r4.mShowNoBackground = r1
            goto L3c
        L3a:
            r4.mShowNoBackground = r2
        L3c:
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r0 = r4.mChildrenContainer
            boolean r2 = r4.mShowNoBackground
            r0.updateHeaderForExpansion(r2)
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r0 = r4.mChildrenContainer
            java.util.List r0 = r0.mAttachedChildren
        L47:
            r2 = r0
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            int r3 = r2.size()
            if (r1 >= r3) goto L9c
            java.lang.Object r2 = r2.get(r1)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
            r2.updateBackgroundForGroupState()
            int r1 = r1 + 1
            goto L47
        L5c:
            boolean r0 = r4.isChildInGroup()
            if (r0 == 0) goto L9a
            com.android.systemui.statusbar.notification.row.NotificationContentView r0 = r4.getShowingLayout()
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = r0.mContainingNotification
            boolean r2 = r2.isGroupExpanded()
            if (r2 != 0) goto L78
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = r0.mContainingNotification
            boolean r2 = r2.mUserLocked
            if (r2 == 0) goto L75
            goto L78
        L75:
            int r2 = r0.mVisibleType
            goto L7c
        L78:
            int r2 = r0.calculateVisibleType()
        L7c:
            com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper r0 = r0.getVisibleWrapper(r2)
            if (r0 == 0) goto L85
            r0.getCustomBackgroundColor()
        L85:
            boolean r0 = r4.isGroupExpanded()
            if (r0 != 0) goto L97
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r0 = r4.mNotificationParent
            boolean r0 = r0.isGroupExpansionChanging()
            if (r0 != 0) goto L97
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r0 = r4.mNotificationParent
            boolean r0 = r0.mUserLocked
        L97:
            r4.mShowNoBackground = r1
            goto L9c
        L9a:
            r4.mShowNoBackground = r1
        L9c:
            boolean r0 = r4.mCustomOutline
            if (r0 == 0) goto La1
            goto Lae
        La1:
            boolean r0 = r4.needsOutline()
            if (r0 == 0) goto Laa
            com.android.systemui.statusbar.notification.row.ExpandableOutlineView$1 r0 = r4.mProvider
            goto Lab
        Laa:
            r0 = 0
        Lab:
            r4.setOutlineProvider(r0)
        Lae:
            r4.updateBackground()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.updateBackgroundForGroupState():void");
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void updateBackgroundTint() {
        int i = 0;
        updateBackgroundTint(false);
        updateBackgroundForGroupState();
        if (!this.mIsSummaryWithChildren) {
            return;
        }
        List list = this.mChildrenContainer.mAttachedChildren;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ((ExpandableNotificationRow) arrayList.get(i)).updateBackgroundForGroupState();
            i++;
        }
    }

    public final void updateBubbleButton() {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.applyBubbleAction(notificationContentView.mExpandedChild, this.mEntry);
        }
    }

    public final void updateChildrenVisibility() {
        NotificationGuts notificationGuts;
        boolean z = this.mExpandAnimationRunning && (notificationGuts = this.mGuts) != null && notificationGuts.mExposed;
        this.mPrivateLayout.setVisibility((this.mShowingPublic || this.mIsSummaryWithChildren || z) ? 4 : 0);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setVisibility((this.mShowingPublic || !this.mIsSummaryWithChildren || z) ? 4 : 0);
        }
        updateLimits();
    }

    public final void updateClickAndFocus() {
        boolean z = !isChildInGroup() || isGroupExpanded();
        boolean z2 = this.mOnClickListener != null && z;
        if (isFocusable() != z) {
            setFocusable(z);
        }
        if (isClickable() != z2) {
            setClickable(z2);
        }
    }

    public final void updateContentAccessibilityImportanceForGuts(boolean z) {
        setImportantForAccessibility(z ? 0 : 2);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setImportantForAccessibility(z ? 0 : 4);
        }
        NotificationContentView[] notificationContentViewArr = this.mLayouts;
        if (notificationContentViewArr != null) {
            for (NotificationContentView notificationContentView : notificationContentViewArr) {
                notificationContentView.setImportantForAccessibility(z ? 0 : 4);
            }
        }
        if (z) {
            requestAccessibilityFocus();
        }
    }

    public final void updateLimits() {
        NotificationContentView[] notificationContentViewArr = this.mLayouts;
        int length = notificationContentViewArr.length;
        for (int i = 0; i < length; i++) {
            NotificationContentView notificationContentView = notificationContentViewArr[i];
            View view = notificationContentView.mContractedChild;
            boolean z = (view == null || view.getId() == 16909822) ? false : true;
            int i2 = this.mEntry.targetSdk;
            boolean z2 = i2 < 24;
            boolean z3 = i2 < 28;
            boolean z4 = i2 < 31;
            int i3 = (z && z4 && !this.mIsSummaryWithChildren) ? z2 ? this.mMaxSmallHeightBeforeN : z3 ? this.mMaxSmallHeightBeforeP : this.mMaxSmallHeightBeforeS : view instanceof CallLayout ? this.mMaxExpandedHeight : (this.mUseIncreasedCollapsedHeight && notificationContentView == this.mPrivateLayout) ? this.mMaxSmallHeightLarge : this.mMaxSmallHeight;
            View view2 = notificationContentView.mHeadsUpChild;
            int i4 = (view2 == null || view2.getId() == 16909822 || !z4) ? (this.mUseIncreasedHeadsUpHeight && notificationContentView == this.mPrivateLayout) ? this.mMaxHeadsUpHeightIncreased : this.mMaxHeadsUpHeight : z2 ? this.mMaxHeadsUpHeightBeforeN : z3 ? this.mMaxHeadsUpHeightBeforeP : this.mMaxHeadsUpHeightBeforeS;
            NotificationViewWrapper visibleWrapper = notificationContentView.getVisibleWrapper(2);
            if (visibleWrapper != null) {
                i4 = Math.max(i4, visibleWrapper.getMinLayoutHeight());
            }
            int i5 = this.mMaxExpandedHeight;
            notificationContentView.mSmallHeight = i3;
            notificationContentView.mHeadsUpHeight = i4;
            notificationContentView.mNotificationMaxHeight = i5;
        }
    }

    public void updateShelfIconColor() {
        int resolveContrastColor;
        StatusBarIconView statusBarIconView = this.mEntry.mIcons.mShelfIcon;
        Boolean.TRUE.equals(statusBarIconView.getTag(R.id.icon_is_pre_L));
        if (NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(((FrameLayout) this).mContext))) {
            if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
                NotificationContentView showingLayout = getShowingLayout();
                NotificationViewWrapper visibleWrapper = showingLayout.getVisibleWrapper(showingLayout.mVisibleType);
                int originalIconColor = visibleWrapper != null ? visibleWrapper.getOriginalIconColor() : 1;
                if (originalIconColor != 1) {
                    r2 = originalIconColor;
                } else {
                    NotificationEntry notificationEntry = this.mEntry;
                    Context context = ((FrameLayout) this).mContext;
                    boolean z = this.mIsMinimized && !isExpanded(false);
                    int calculateBgColor = calculateBgColor(false, false);
                    r2 = z ? 0 : notificationEntry.mSbn.getNotification().color;
                    if (notificationEntry.mCachedContrastColorIsFor != r2 || (resolveContrastColor = notificationEntry.mCachedContrastColor) == 1) {
                        resolveContrastColor = ContrastColorUtil.resolveContrastColor(context, r2, calculateBgColor);
                        notificationEntry.mCachedContrastColorIsFor = r2;
                        notificationEntry.mCachedContrastColor = resolveContrastColor;
                    }
                    r2 = resolveContrastColor;
                }
            } else {
                int i = AsyncGroupHeaderViewInflation.$r8$clinit;
                Flags.notificationAsyncGroupHeaderInflation();
                r2 = this.mChildrenContainer.getVisibleWrapper().getOriginalIconColor();
            }
        }
        statusBarIconView.setStaticDrawableColor(r2);
    }

    public final void updateSummarize(String str, boolean z) {
        this.mEntry.getClass();
        NotificationContentView notificationContentView = this.mPrivateLayout;
        if (notificationContentView != null) {
            if (notificationContentView.getContractedWrapper() != null) {
                this.mPrivateLayout.getContractedWrapper().getClass();
            }
            if (this.mPrivateLayout.getHeadsUpWrapper() != null) {
                this.mPrivateLayout.getHeadsUpWrapper().getClass();
            }
            if (this.mPrivateLayout.getExpandedWrapper() != null) {
                this.mPrivateLayout.getExpandedWrapper().getClass();
            }
            NotificationContentView notificationContentView2 = this.mPrivateLayout;
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationContentView2.mContractedChild == null) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(" updateSummarizeView mContractedChild is null "), notificationEntry.mKey, "Summarize");
                return;
            }
            if (notificationContentView2.mContainingNotification == null) {
                Log.d("Summarize", " updateSummarizeView : mContainingNotification is null");
                return;
            }
            NotificationViewWrapper contractedWrapper = notificationContentView2.getContractedWrapper();
            if (contractedWrapper == null) {
                Log.d("Summarize", " updateSummarizeView : contractedWrapper is null");
                return;
            }
            contractedWrapper.updateSummarize(notificationEntry.row);
            NotificationViewWrapper headsUpWrapper = notificationContentView2.getHeadsUpWrapper();
            if (headsUpWrapper == null) {
                Log.d("Summarize", " updateSummarizeView : headsUpWrapper is null");
            } else {
                headsUpWrapper.updateSummarize(notificationEntry.row);
            }
        }
    }

    public final int getPinnedHeadsUpHeight(boolean z) {
        return this.mIsSummaryWithChildren ? this.mChildrenContainer.getIntrinsicHeight() : this.mExpandedWhenPinned ? Math.max(getMaxExpandHeight(), getHeadsUpHeight()) : z ? Math.max(getCollapsedHeight(), getHeadsUpHeight()) : getHeadsUpHeight();
    }

    public ExpandableNotificationRow(Context context, AttributeSet attributeSet, NotificationEntry notificationEntry) {
        this(context, attributeSet, context.getUserId() == notificationEntry.mSbn.getNormalizedUserId() ? context : context.createContextAsUser(UserHandle.of(notificationEntry.mSbn.getNormalizedUserId()), 0));
    }

    public final void doLongClickCallback(int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
        ExpandableNotificationRowController$$ExternalSyntheticLambda0 expandableNotificationRowController$$ExternalSyntheticLambda0 = this.mLongPressListener;
        if (expandableNotificationRowController$$ExternalSyntheticLambda0 == null || menuItem == null) {
            return;
        }
        ExpandableNotificationRowController expandableNotificationRowController = (ExpandableNotificationRowController) expandableNotificationRowController$$ExternalSyntheticLambda0.f$0;
        ExpandableNotificationRow expandableNotificationRow = expandableNotificationRowController.mView;
        boolean z = expandableNotificationRow.mIsSummaryWithChildren;
        NotificationGutsManager notificationGutsManager = expandableNotificationRowController.mNotificationGutsManager;
        if (z && expandableNotificationRow.isGroupExpanded()) {
            ExpandableNotificationRow viewAtPosition = expandableNotificationRow.getViewAtPosition(i2);
            if (viewAtPosition.equals(expandableNotificationRow)) {
                return;
            }
            notificationGutsManager.openGuts(viewAtPosition, i, i2, menuItem);
            return;
        }
        notificationGutsManager.openGuts(this, i, i2, menuItem);
    }

    public final void setChronometerRunning(boolean z) {
        this.mLastChronometerRunning = z;
        setChronometerRunning(z, this.mPrivateLayout);
        setChronometerRunning(z, this.mPublicLayout);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null) {
            return;
        }
        List list = notificationChildrenContainer.mAttachedChildren;
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ((ExpandableNotificationRow) arrayList.get(i)).setChronometerRunning(z);
            i++;
        }
    }

    private ExpandableNotificationRow(Context context, AttributeSet attributeSet, Context context2) {
        super(context, attributeSet);
        this.mShowPublicExpander = true;
        this.mHeaderVisibleAmount = 1.0f;
        this.mLastChronometerRunning = true;
        this.mExpandClickListener = new AnonymousClass1();
        this.mExpireRecentlyAlertedFlag = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
                SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                expandableNotificationRow.applyAudiblyAlertedRecently(false);
            }
        };
        this.mIsGroupHeaderContainAtMark = false;
        this.mImageResolver = new NotificationInlineImageResolver(context2, new NotificationInlineImageCache());
        getResources().getDimension(R.dimen.notification_corner_radius_small);
        this.mSmallRoundness = 1.0f;
        initDimens$1();
    }
}
