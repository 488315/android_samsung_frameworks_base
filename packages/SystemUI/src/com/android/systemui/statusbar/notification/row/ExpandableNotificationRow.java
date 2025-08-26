package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.TrackGroupUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.CallLayout;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingLayout;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelTouchBlockHelper;
import com.android.systemui.statusbar.NotificationGroupingUtil;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.ConversationNotificationManager$onEntryViewBound$1;
import com.android.systemui.statusbar.notification.NmSummarizationUiFlag;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfoManager;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
import com.android.systemui.statusbar.notification.collection.EntryAdapter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationUi;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.LockscreenOtpRedaction;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationGroupHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationAddXOnHoverToDismiss;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.MagneticRowListener;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.SwipeableView;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingType;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.animation.PhysicsAnimatorKt;
import com.samsung.android.knox.container.RCPPolicy;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public class ExpandableNotificationRow extends ActivatableNotificationView implements PluginListener<NotificationMenuRowPlugin>, SwipeableView, NotificationFadeAware.FadeOptimizedNotification, PanelScreenShotLogger.LogProvider {
    public static final AnonymousClass2 TRANSLATE_CONTENT;
    public boolean mAboveShelf;
    public AboveShelfObserver mAboveShelfChangedListener;
    public boolean mAnimationRunning;
    public String mAppName;
    public List mBubbleButtonViews;
    public NotificationClicker$$ExternalSyntheticLambda0 mBubbleClickListener;
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
    public NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0 mHeadsUpAnimatingAwayListener;
    public HeadsUpManager mHeadsUpManager;
    public boolean mHeadsupDisappearRunning;
    public boolean mHideSensitiveForIntrinsicHeight;
    public boolean mIgnoreLockscreenConstraints;
    public ImageModelIndex mImageModelIndex;
    public final NotificationInlineImageResolver mImageResolver;
    public final List mInsigificantChildrenList;
    public boolean mIsCustomBigNotification;
    public boolean mIsCustomHeadsUpNotification;
    public boolean mIsCustomNotification;
    public boolean mIsCustomPublicNotification;
    public boolean mIsFaded;
    public boolean mIsHeadsUp;
    public boolean mIsMinimized;
    public boolean mIsSnoozed;
    public boolean mIsSummaryWithChildren;
    public boolean mIsSystemExpanded;
    public boolean mJustClicked;
    public boolean mKeepInParentForDismissAnimation;
    public boolean mLastChronometerRunning;
    public NotificationMenuRow mLayoutListener;
    public NotificationContentView[] mLayouts;
    public ExpandableNotificationRowController.AnonymousClass2 mLogger;
    public String mLoggingKey;
    public ExpandableNotificationRowController$$ExternalSyntheticLambda1 mLongPressListener;
    public int mMaxExpandedHeight;
    public int mMaxHeadsUpHeight;
    public int mMaxHeadsUpHeightBeforeN;
    public int mMaxHeadsUpHeightBeforeP;
    public int mMaxHeadsUpHeightBeforeS;
    public int mMaxSmallHeight;
    public int mMaxSmallHeightBeforeN;
    public int mMaxSmallHeightBeforeP;
    public int mMaxSmallHeightBeforeS;
    public MediaDataManager mMediaDataManager;
    public SecMediaHost mMediaHost;
    public NotificationMenuRowPlugin mMenuRow;
    public MetricsLogger mMetricsLogger;
    public boolean mMustStayOnScreen;
    public NotificationGutsManager mNotificationGutsManager;
    public int mNotificationLaunchHeight;
    public ExpandableNotificationRow mNotificationParent;
    public NotificationRemoteInputManager mNotificationRemoteInputManager;
    public View.OnClickListener mOnClickListener;
    public NotificationClicker.AnonymousClass1 mOnDragSuccessListener;
    public OnExpandClickListener mOnExpandClickListener;
    public ConversationNotificationManager$onEntryViewBound$1.AnonymousClass1 mOnIntrinsicHeightReachedRunnable;
    public boolean mOnKeyguard;
    public OnUserInteractionCallback mOnUserInteractionCallback;
    public PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    public PinnedStatus mPinnedStatus;
    public NotificationContentView mPrivateLayout;
    public NotificationContentView mPublicLayout;
    public NotificationRebindingTracker mRebindingTracker;
    public int mRedactionType;
    public RowContentBindStage mRowContentBindStage;
    public boolean mSaveSpaceOnLockscreen;
    public StatusBarNotificationPresenter$$ExternalSyntheticLambda4 mSecureStateProvider;
    public boolean mSensitive;
    public boolean mSensitiveHiddenInGeneral;
    public boolean mShowNoBackground;
    public boolean mShowPublicExpander;
    public boolean mShowingPublic;
    public boolean mShowingPublicInitialized;
    public boolean mSkipRemovalAnim;
    public final float mSmallRoundness;
    public ImageView mSnoozeButtonView;
    public StatusBarStateController mStatusBarStateController;
    public int mTempSmallHeight;
    public Animator mTranslateAnim;
    public ArrayList mTranslateableViews;
    public boolean mUserExpanded;
    public boolean mUserLocked;
    public static final long RECENTLY_ALERTED_THRESHOLD_MS = TimeUnit.SECONDS.toMillis(30);
    public static final SourceType$Companion$from$1 BASE_VALUE = SourceType.from("BaseValue");
    public static final SourceType$Companion$from$1 FROM_PARENT = SourceType.from("FromParent(ENR)");

    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRow.this;
            SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
            expandableNotificationRow.toggleExpansionState(view, true);
        }
    }

    public class NotificationViewState extends ExpandableViewState {
        public /* synthetic */ NotificationViewState(int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:51:0x00bb  */
        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
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
                    boolean z = (notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging() || notificationChildrenContainer.mUserLocked) ? false : true;
                    boolean z2 = (notificationChildrenContainer.mChildrenExpanded && notificationChildrenContainer.mShowDividersWhenExpanded) || ((notificationChildrenContainer.mUserLocked || notificationChildrenContainer.mContainingNotification.isGroupExpansionChanging()) && !notificationChildrenContainer.mHideDividersDuringExpand);
                    for (int i = size - 1; i >= 0; i--) {
                        final ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i);
                        ExpandableViewState expandableViewState = expandableNotificationRow2.mViewState;
                        if (!z || expandableViewState.hidden) {
                            animationProperties.mAnimationEndAction = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    NotificationChildrenContainer notificationChildrenContainer2 = notificationChildrenContainer;
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
                            float fInterpolate = (!notificationChildrenContainer.mChildrenExpanded || expandableViewState.mAlpha == 0.0f) ? 0.0f : notificationChildrenContainer.mDividerAlpha;
                            if (notificationChildrenContainer.mUserLocked) {
                                float f = expandableViewState.mAlpha;
                                if (f != 0.0f) {
                                    fInterpolate = NotificationUtils.interpolate(0.0f, notificationChildrenContainer.mDividerAlpha, Math.min(f, groupExpandFraction));
                                }
                            }
                            viewState.hidden = !z2;
                            viewState.setAlpha(fInterpolate);
                            if (!z2) {
                                viewState.setAlpha(0.0f);
                                view2.setAlpha(0.0f);
                            }
                            viewState.animateTo(view2, animationProperties);
                            expandableNotificationRow2.setFakeShadowIntensity(0, 0.0f, 0.0f, 0);
                        } else if (animationProperties.wasAdded(expandableNotificationRow2)) {
                            NotificationContentView showingLayout = expandableNotificationRow2.getShowingLayout();
                            SpringAnimation springAnimation = new SpringAnimation(showingLayout, DynamicAnimation.TRANSLATION_Y);
                            springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(1000.0f, 0.84f);
                            springAnimation.addEndListener(new NotificationChildrenContainer$$ExternalSyntheticLambda2(showingLayout, 0));
                            springAnimation.animateToFinalPosition(-notificationChildrenContainer.mTranslationYForAdd);
                        } else if (i != 1 && i != 2) {
                        }
                    }
                    if (z) {
                        notificationChildrenContainer.applyState();
                        return;
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
                            float f2 = viewState2.mAlpha;
                            viewState2.setAlpha(0.0f);
                            notificationChildrenContainer.mGroupOverFlowState.applyToView(notificationChildrenContainer.mOverflowNumber);
                            notificationChildrenContainer.mGroupOverFlowState.setAlpha(f2);
                            notificationChildrenContainer.mNeverAppliedGroupState = false;
                        }
                        notificationChildrenContainer.mGroupOverFlowState.animateTo(notificationChildrenContainer.mOverflowNumber, animationProperties);
                    }
                    if (notificationChildrenContainer.mGroupHeader != null) {
                        if (notificationChildrenContainer.mContainingNotification.isGroupExpanded$1()) {
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
                    expandableNotificationRow.mChildrenContainer.applyState();
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

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$2] */
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

    public static Rect getButtonViewRect(View view, View view2) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr);
        view2.getLocationOnScreen(iArr2);
        int i = iArr[0] - iArr2[0];
        int i2 = iArr[1] - iArr2[1];
        return new Rect(i, i2, view.getWidth() + i, view.getHeight() + i2);
    }

    public static void setChronometerRunningForChild(View view, boolean z) {
        if (view != null) {
            View viewFindViewById = view.findViewById(android.R.id.conversation_face_pile_bottom);
            if (viewFindViewById instanceof Chronometer) {
                ((Chronometer) viewFindViewById).setStarted(z);
            }
        }
    }

    public static void setIconAnimationRunningForChild(View view, boolean z) {
        if (view != null) {
            setImageViewAnimationRunning((ImageView) view.findViewById(android.R.id.icon), z);
            setImageViewAnimationRunning((ImageView) view.findViewById(android.R.id.tag_top_override), z);
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

    public final void addChildNotification(ExpandableNotificationRow expandableNotificationRow, int i) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
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
                String str = expandableNotificationRow.mLoggingKey;
                String str2 = this.mLoggingKey;
                NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(5);
                LogBuffer logBuffer = notificationRowLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = str2;
                logBuffer.commit(logMessageObtain);
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
        View viewInflateDivider = notificationChildrenContainer.inflateDivider();
        notificationChildrenContainer.addView(viewInflateDivider);
        ((ArrayList) notificationChildrenContainer.mDividers).add(size, viewInflateDivider);
        expandableNotificationRow.setNotificationFaded(notificationChildrenContainer.mContainingNotificationIsFaded);
        ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
        if (expandableViewState != null) {
            expandableViewState.cancelAnimations(expandableNotificationRow);
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass22 = expandableNotificationRow.mLogger;
            String str3 = expandableNotificationRow.mLoggingKey;
            boolean z = expandableNotificationRow.mDrawingAppearAnimation;
            NotificationRowLogger notificationRowLogger2 = ExpandableNotificationRowController.this.mLogBufferLogger;
            notificationRowLogger2.getClass();
            LogLevel logLevel2 = LogLevel.WARNING;
            NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda02 = new NotificationRowLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer2 = notificationRowLogger2.notificationRenderBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("NotifRow", logLevel2, notificationRowLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.str1 = str3;
            logMessageImpl2.bool1 = z;
            logBuffer2.commit(logMessageObtain2);
            ValueAnimator valueAnimator = expandableNotificationRow.mAppearAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                expandableNotificationRow.mAppearAnimator = null;
            }
            expandableNotificationRow.enableAppearDrawing(false);
            expandableNotificationRow.setHeadsUpAnimatingAway(false);
        }
        if (i == 0 && !notificationChildrenContainer.mExpanderViewStates.isEmpty()) {
            ArrayList arrayList3 = notificationChildrenContainer.mExpanderViewStates;
            int size2 = arrayList3.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList3.get(i2);
                i2++;
                ((ViewState) obj).setAlpha(1.0f);
            }
        }
        expandableNotificationRow.mSkipRemovalAnim = false;
        notificationChildrenContainer.applyRoundnessAndInvalidate();
        onAttachedChildrenCountChanged();
        expandableNotificationRow.setIsChildInGroup(this, true);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
            SubscreenDeviceModelParent.MainListHashMapItem mainListHashMapItem = (SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(expandableNotificationRow.mEntry.mKey);
            NotificationEntry notificationEntry = (NotificationEntry) subscreenDeviceModelParent.mMainListAddEntryHashMap.get(expandableNotificationRow.mEntry.mKey);
            if (subscreenDeviceModelParent.isShownGroup() && mainListHashMapItem == null && notificationEntry == null) {
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelParent.mSubRoomNotification;
                SubscreenNotificationInfo subscreenNotificationInfo = (subscreenSubRoomNotification == null || (subscreenNotificationGroupAdapter2 = subscreenSubRoomNotification.mNotificationGroupAdapter) == null) ? null : subscreenNotificationGroupAdapter2.mSummaryInfo;
                SubscreenNotificationInfo subscreenNotificationInfoCreateItemsData = (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager4 = subscreenSubRoomNotification.mNotificationInfoManager) == null) ? null : subscreenNotificationInfoManager4.createItemsData(expandableNotificationRow);
                if (StringsKt__StringsJVMKt.equals((subscreenNotificationInfo == null || (statusBarNotification2 = subscreenNotificationInfo.mSbn) == null) ? null : statusBarNotification2.getGroupKey(), (subscreenNotificationInfoCreateItemsData == null || (statusBarNotification = subscreenNotificationInfoCreateItemsData.mSbn) == null) ? null : statusBarNotification.getGroupKey(), false)) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelParent.mSubRoomNotification;
                    Integer numValueOf = (subscreenSubRoomNotification2 == null || (subscreenNotificationInfoManager3 = subscreenSubRoomNotification2.mNotificationInfoManager) == null) ? null : Integer.valueOf(subscreenNotificationInfoManager3.mGroupDataArray.size());
                    numValueOf.getClass();
                    int iIntValue = numValueOf.intValue();
                    for (int i3 = 0; i3 < iIntValue; i3++) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelParent.mSubRoomNotification;
                        SubscreenNotificationInfo subscreenNotificationInfo2 = (subscreenSubRoomNotification3 == null || (subscreenNotificationInfoManager2 = subscreenSubRoomNotification3.mNotificationInfoManager) == null || (arrayList2 = subscreenNotificationInfoManager2.mGroupDataArray) == null) ? null : (SubscreenNotificationInfo) arrayList2.get(i3);
                        if (StringsKt__StringsJVMKt.equals(subscreenNotificationInfo2 != null ? subscreenNotificationInfo2.mKey : null, subscreenNotificationInfoCreateItemsData != null ? subscreenNotificationInfoCreateItemsData.mKey : null, false)) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("addChildNotification parent - already Item  : ", subscreenNotificationInfoCreateItemsData != null ? subscreenNotificationInfoCreateItemsData.mKey : null, "S.S.N.");
                            return;
                        }
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification4 = subscreenDeviceModelParent.mSubRoomNotification;
                    if (subscreenSubRoomNotification4 != null && (subscreenNotificationInfoManager = subscreenSubRoomNotification4.mNotificationInfoManager) != null && (arrayList = subscreenNotificationInfoManager.mGroupDataArray) != null) {
                        arrayList.add(iIntValue, subscreenNotificationInfoCreateItemsData);
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification5 = subscreenDeviceModelParent.mSubRoomNotification;
                    if (subscreenSubRoomNotification5 != null && (subscreenNotificationGroupAdapter = subscreenSubRoomNotification5.mNotificationGroupAdapter) != null) {
                        subscreenNotificationGroupAdapter.notifyItemInserted(iIntValue);
                    }
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("addChildNotification parent - add Item  : ", subscreenNotificationInfoCreateItemsData != null ? subscreenNotificationInfoCreateItemsData.mKey : null, "S.S.N.");
                }
                SubscreenDeviceModelParent.putMainListArrayHashMap$default(subscreenDeviceModelParent, expandableNotificationRow.mEntry);
            }
        }
    }

    public final void addTransientView(View view, int i) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                String str = expandableNotificationRow.mLoggingKey;
                String str2 = this.mLoggingKey;
                NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = str2;
                logMessageImpl.int1 = i;
                logBuffer.commit(logMessageObtain);
            }
        }
        super.addTransientView(view, i);
    }

    public final String appendTraceStyleTag(String str) {
        if (!Trace.isEnabled()) {
            return str;
        }
        int i = NotificationBundleUi.$r8$clinit;
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "(");
        sbM.append(getEntryLegacy().getNotificationStyle());
        sbM.append(")");
        return sbM.toString();
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

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean canExpandableViewBeDismissed() {
        if (areGutsExposed() || !hasFinishedInitialization()) {
            return false;
        }
        return canViewBeDismissed$1();
    }

    public final boolean canShowHeadsUp$1() {
        KeyguardBypassController keyguardBypassController;
        int i = NotificationBundleUi.$r8$clinit;
        boolean zIsStickyAndNotDemoted = getEntryLegacy().isStickyAndNotDemoted();
        if (!this.mOnKeyguard) {
            return true;
        }
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if ((statusBarStateController != null && statusBarStateController.isDozing()) || (keyguardBypassController = this.mBypassController) == null || keyguardBypassController.getBypassEnabled()) {
            return true;
        }
        if (zIsStickyAndNotDemoted) {
            return this.mIgnoreLockscreenConstraints || !this.mSaveSpaceOnLockscreen;
        }
        return false;
    }

    public final boolean canViewBeCleared() {
        int i = NotificationBundleUi.$r8$clinit;
        if (getEntryLegacy().isClearable()) {
            return (shouldShowPublic() && this.mSensitiveHiddenInGeneral) ? false : true;
        }
        return false;
    }

    public final boolean canViewBeDismissed$1() {
        if (((NotificationDismissibilityProviderImpl) this.mDismissibilityProvider).nonDismissableEntryKeys.contains(getKey())) {
            return false;
        }
        return (shouldShowPublic() && this.mSensitiveHiddenInGeneral) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void cancelTranslationAnimations() {
        PhysicsAnimator.Companion.getClass();
        PhysicsAnimator physicsAnimator = (PhysicsAnimator) PhysicsAnimatorKt.animators.get(this);
        if (physicsAnimator != null) {
            physicsAnimator.cancel();
        }
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0044, code lost:
    
        if (r4 != false) goto L38;
     */
    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean childNeedsClipping(View view) {
        if (!(view instanceof NotificationContentView)) {
            if (view == this.mChildrenContainer) {
                if (isClippingNeeded() || hasRoundedCorner()) {
                    return NotiRune.NOTI_STYLE_POP_OVER_DISMISS_CLIP_VIEW && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && isClippingNeeded();
                }
            } else if (view instanceof NotificationGuts) {
                return hasRoundedCorner();
            }
            return super.childNeedsClipping(view);
        }
        NotificationContentView notificationContentView = (NotificationContentView) view;
        if (!isClippingNeeded()) {
            if (hasRoundedCorner()) {
                RoundableState roundableState = ((ExpandableOutlineView) this).mRoundableState;
                float f = roundableState.topRoundness;
                boolean z = roundableState.bottomRoundness != 0.0f;
                NotificationViewWrapper visibleWrapper = notificationContentView.getVisibleWrapper(notificationContentView.mVisibleType);
                boolean zShouldClipToRounding = visibleWrapper == null ? false : visibleWrapper.shouldClipToRounding(z);
                if (notificationContentView.mUserExpanding) {
                    NotificationViewWrapper visibleWrapper2 = notificationContentView.getVisibleWrapper(notificationContentView.mTransformationStartVisibleType);
                    zShouldClipToRounding |= visibleWrapper2 != null ? visibleWrapper2.shouldClipToRounding(z) : false;
                }
            }
            return super.childNeedsClipping(view);
        }
    }

    public final boolean childrenRequireOverlappingRendering() {
        RemoteInputView remoteInputView;
        int i = NotificationBundleUi.$r8$clinit;
        if (getEntryLegacy().mSbn.getNotification().isColorized()) {
            return true;
        }
        NotificationContentView showingLayout = getShowingLayout();
        return (showingLayout == null || (remoteInputView = showingLayout.mExpandedRemoteInput) == null || !remoteInputView.isActive()) ? false : true;
    }

    public final void collectVisibleLocations(Map map) {
        if (getVisibility() != 0) {
            return;
        }
        ((HashMap) map).put(getKey(), Integer.valueOf(this.mViewState.location));
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
            this.mMenuRow.createMenu(this);
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

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("Notification: " + getKey());
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriterAsIndenting, new ExpandableNotificationRow$$ExternalSyntheticLambda4(this, indentingPrintWriterAsIndenting, strArr, 0));
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        Boolean boolValueOf = Boolean.valueOf(this.mSensitive);
        panelScreenShotLogger.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "mSensitive", boolValueOf);
        PanelScreenShotLogger.addLogItem(arrayList, "mNeedsRedaction", Boolean.valueOf(needsRedaction()));
        PanelScreenShotLogger.addLogItem(arrayList, "shouldShowPublic", Boolean.valueOf(shouldShowPublic()));
        PanelScreenShotLogger.addLogItem(arrayList, "mHideSensitiveForIntrinsicHeight", Boolean.valueOf(this.mHideSensitiveForIntrinsicHeight));
        int i = 0;
        PanelScreenShotLogger.addLogItem(arrayList, "isExpanded", Boolean.valueOf(isExpanded(false)));
        PanelScreenShotLogger.addLogItem(arrayList, "getMaxExpandHeight", Integer.valueOf(getMaxExpandHeight()));
        PanelScreenShotLogger.addLogItem(arrayList, "getCollapsedHeight", Integer.valueOf(getCollapsedHeight()));
        PanelScreenShotLogger.addLogItem(arrayList, "getMinHeight", Integer.valueOf(getMinHeight(false)));
        PanelScreenShotLogger.addLogItem(arrayList, "hasInterrupted", Boolean.valueOf(this.mEntry.interruption));
        PanelScreenShotLogger.addLogItem(arrayList, "mDismissState", this.mEntry.mDismissState);
        NotificationDismissibilityProvider notificationDismissibilityProvider = this.mDismissibilityProvider;
        PanelScreenShotLogger.addLogItem(arrayList, "isDismissable", Boolean.valueOf(!((NotificationDismissibilityProviderImpl) notificationDismissibilityProvider).nonDismissableEntryKeys.contains(getKey())));
        PanelScreenShotLogger.addLogItem(arrayList, "mIsHeadsUp", Boolean.valueOf(this.mIsHeadsUp));
        PanelScreenShotLogger.addLogItem(arrayList, "mHeadsupDisappearRunning", Boolean.valueOf(this.mHeadsupDisappearRunning));
        PanelScreenShotLogger.addLogItem(arrayList, "mBackgroundNormal", this.mBackgroundNormal.toDumpString());
        StatusBarIconView statusBarIconView = this.mEntry.mIcons.mStatusBarIcon;
        if (statusBarIconView != null) {
            PanelScreenShotLogger.addLogItem(arrayList, "isGrayScale", Boolean.valueOf(NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(getContext()))));
        }
        arrayList.addAll(getShowingLayout().gatherState());
        if (getAttachedChildren() != null) {
            PanelScreenShotLogger.addHeaderLine("Attache Children : ", arrayList);
            ArrayList arrayList2 = (ArrayList) getAttachedChildren();
            int size = arrayList2.size();
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                arrayList.addAll(((ExpandableNotificationRow) obj).gatherState());
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
            childrenCountText.setTextColor(((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getExpandButtonColor(this.mDimmed, true));
            arrayList.add(childrenCountText);
        }
        if (getShowingLayout().getHeadsUpWrapper() != null && getShowingLayout().getHeadsUpWrapper().getChildrenCountText() != null) {
            TextView childrenCountText2 = getShowingLayout().getHeadsUpWrapper().getChildrenCountText();
            childrenCountText2.setTextColor(((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getExpandButtonColor(this.mDimmed, true));
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

    public final NotificationEntry getEntryLegacy() {
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return this.mEntry;
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
        if (!canShowHeadsUp$1() || !this.mIsHeadsUp) {
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
            return (!isChildInGroup() || isGroupExpanded$1()) ? (this.mSensitive && this.mHideSensitiveForIntrinsicHeight) ? getMinHeight(false) : this.mIsSummaryWithChildren ? this.mChildrenContainer.getIntrinsicHeight() : (canShowHeadsUp$1() && isHeadsUpState()) ? (this.mPinnedStatus.isPinned() || this.mHeadsupDisappearRunning) ? getPinnedHeadsUpHeight(true) : isExpanded(false) ? Math.max(getMaxExpandHeight(), getHeadsUpHeight()) : Math.max(getCollapsedHeight(), getHeadsUpHeight()) : isExpanded(false) ? getMaxExpandHeight() : getCollapsedHeight() : this.mPrivateLayout.getMinHeight(true);
        }
        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
        return (gutsContent == null || !z) ? notificationGuts.getHeight() : gutsContent.getActualHeight();
    }

    public final boolean getIsNonPackageBlockable() {
        return !SecNotificationBlockManager.isBlockablePackage(((FrameLayout) this).mContext, this.mEntry.mSbn.getPackageName());
    }

    public final boolean getIsNonblockable() {
        int iCheckSystemAppAndMetaData;
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Context context = ((FrameLayout) this).mContext;
        String packageName = this.mEntry.mSbn.getPackageName();
        NotificationChannel channel = this.mEntry.mRanking.getChannel();
        boolean z = false;
        if (SecNotificationBlockManager.checkConfigCSC(context, packageName, channel) != 2 && ((iCheckSystemAppAndMetaData = SecNotificationBlockManager.checkSystemAppAndMetaData(context, packageName)) == 4 || channel.isBlockable() || iCheckSystemAppAndMetaData != 2)) {
            z = true;
        }
        return !z;
    }

    public final String getKey() {
        int i = NotificationBundleUi.$r8$clinit;
        return getEntryLegacy().mKey;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0051  */
    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getMaxContentHeight() {
        if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
            return getShowingLayout().getMaxHeight();
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        int maxExpandHeight = notificationChildrenContainer.mContainingNotification.isGroupExpanded$1() ? notificationChildrenContainer.mHeaderExpandedHeight : notificationChildrenContainer.mHeaderExpandedHeight + notificationChildrenContainer.mAdditionalExpandedHeaderMargin;
        int size = ((ArrayList) notificationChildrenContainer.mAttachedChildren).size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (notificationChildrenContainer.mContainingNotification.isInsignificant()) {
                if (i >= 50) {
                    break;
                }
                maxExpandHeight = (int) (maxExpandHeight + (!((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i2)).isExpanded(true) ? r4.getMaxExpandHeight() : r4.getShowingLayout().getMinHeight(true)));
                i++;
            } else {
                if (i >= 8) {
                    break;
                }
                maxExpandHeight = (int) (maxExpandHeight + (!((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i2)).isExpanded(true) ? r4.getMaxExpandHeight() : r4.getShowingLayout().getMinHeight(true)));
                i++;
            }
        }
        return i > 0 ? (i * notificationChildrenContainer.mDividerHeight) + maxExpandHeight : maxExpandHeight;
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
            return (!z && canShowHeadsUp$1() && this.mIsHeadsUp && ((Boolean) ((HeadsUpManagerImpl) this.mHeadsUpManager).mTrackingHeadsUp.getValue()).booleanValue()) ? getPinnedHeadsUpHeight(false) : (!this.mIsSummaryWithChildren || isGroupExpanded$1() || shouldShowPublic()) ? (!z && canShowHeadsUp$1() && this.mIsHeadsUp) ? getHeadsUpHeight() : getShowingLayout().getMinHeight(true) : this.mChildrenContainer.getMinHeight(1);
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
        int i = NotificationBundleUi.$r8$clinit;
        return getEntryLegacy().mIcons.mShelfIcon;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final View getShelfTransformationTarget() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            NotificationViewWrapper visibleWrapper = this.mChildrenContainer.getVisibleWrapper();
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
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
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, TRANSLATE_CONTENT, f);
        if (animatorUpdateListener != null) {
            objectAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        }
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.3
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
        this.mTranslateAnim = objectAnimatorOfFloat;
        return objectAnimatorOfFloat;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final float getTranslation() {
        if (this.mDismissUsingRowTranslationX) {
            return getTranslationX();
        }
        ArrayList arrayList = this.mTranslateableViews;
        if (arrayList == null || arrayList.isEmpty()) {
            return 0.0f;
        }
        return ((View) this.mTranslateableViews.get(0)).getTranslationX();
    }

    public final ExpandableNotificationRow getViewAtPosition(float f) {
        ExpandableNotificationRow expandableNotificationRow;
        if (this.mIsSummaryWithChildren && this.mChildrenExpanded) {
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
                float fMax = Math.max(0, expandableNotificationRow.mClipTopAmount) + translationY;
                float f2 = translationY + expandableNotificationRow.mActualHeight;
                if (f >= fMax && f <= f2) {
                    break;
                }
                i++;
            }
            if (expandableNotificationRow != null) {
                return expandableNotificationRow;
            }
        }
        return this;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean hasExpandingChild() {
        return this.mChildIsExpanding;
    }

    public final boolean hasFinishedInitialization() {
        int i = NotificationBundleUi.$r8$clinit;
        NotificationEntry entryLegacy = getEntryLegacy();
        entryLegacy.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return entryLegacy.initializationTime != -1 && SystemClock.elapsedRealtime() > entryLegacy.initializationTime + 400;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final boolean hasOverlappingRendering() {
        return super.hasOverlappingRendering() && childrenRequireOverlappingRendering();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final boolean hideBackground() {
        return this.mShowNoBackground;
    }

    public final void initDimens$1() throws Resources.NotFoundException {
        this.mMaxSmallHeightBeforeN = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_legacy, ((FrameLayout) this).mContext);
        this.mMaxSmallHeightBeforeP = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_before_p, ((FrameLayout) this).mContext);
        this.mMaxSmallHeightBeforeS = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height_before_s, ((FrameLayout) this).mContext);
        this.mMaxSmallHeight = NotificationUtils.getFontScaledHeight(R.dimen.notification_min_height, ((FrameLayout) this).mContext);
        NotificationUtils.getFontScaledHeight(android.R.dimen.timepicker_time_label_size, ((FrameLayout) this).mContext);
        this.mMaxExpandedHeight = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_height, ((FrameLayout) this).mContext);
        NotificationUtils.getFontScaledHeight(R.dimen.notification_max_height_for_promoted_ongoing, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightBeforeN = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_legacy, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightBeforeP = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_before_p, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeightBeforeS = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height_before_s, ((FrameLayout) this).mContext);
        this.mMaxHeadsUpHeight = NotificationUtils.getFontScaledHeight(R.dimen.notification_max_heads_up_height, ((FrameLayout) this).mContext);
        Resources resources = getResources();
        this.mEnableNonGroupedNotificationExpand = resources.getBoolean(R.bool.config_enableNonGroupedNotificationExpand);
        resources.getBoolean(R.bool.config_showGroupNotificationBgWhenExpanded);
    }

    public final void initialize(PipelineEntry pipelineEntry, RemoteInputViewSubcomponent.Factory factory, String str, String str2, ExpandableNotificationRowController.AnonymousClass2 anonymousClass2, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, HeadsUpManager headsUpManager, RowContentBindStage rowContentBindStage, OnExpandClickListener onExpandClickListener, FalsingManager falsingManager, StatusBarStateController statusBarStateController, PeopleNotificationIdentifier peopleNotificationIdentifier, OnUserInteractionCallback onUserInteractionCallback, NotificationGutsManager notificationGutsManager, NotificationDismissibilityProvider notificationDismissibilityProvider, MetricsLogger metricsLogger, NotificationChildrenContainerLogger notificationChildrenContainerLogger, ColorUpdateLogger colorUpdateLogger, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, IStatusBarService iStatusBarService, UiEventLogger uiEventLogger, NotificationRebindingTracker notificationRebindingTracker, SecMediaHost secMediaHost, MediaDataManager mediaDataManager) {
        int i = NotificationBundleUi.$r8$clinit;
        this.mEntry = (NotificationEntry) pipelineEntry;
        this.mAppName = str;
        this.mRebindingTracker = notificationRebindingTracker;
        if (this.mMenuRow == null) {
            this.mMenuRow = new NotificationMenuRow(((FrameLayout) this).mContext, peopleNotificationIdentifier);
        }
        if (this.mMenuRow.getMenuView() != null) {
            this.mMenuRow.setAppName(this.mAppName);
        }
        this.mLogger = anonymousClass2;
        this.mLoggingKey = NotificationUtils.logKey(str2);
        this.mBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mPrivateLayout.getClass();
        this.mHeadsUpManager = headsUpManager;
        this.mRowContentBindStage = rowContentBindStage;
        this.mOnExpandClickListener = onExpandClickListener;
        this.mFalsingManager = falsingManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mPeopleIdentifier = this.mPeopleNotificationIdentifier;
            notificationContentView.mRemoteInputSubcomponentFactory = factory;
            notificationContentView.mSmartReplyConstants = smartReplyConstants;
            notificationContentView.mSmartReplyController = smartReplyController;
            notificationContentView.mStatusBarService = iStatusBarService;
            notificationContentView.mUiEventLogger = uiEventLogger;
            notificationContentView.setIsRootNamespace(true);
        }
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mMetricsLogger = metricsLogger;
        this.mChildrenContainerLogger = notificationChildrenContainerLogger;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        setHapticFeedbackEnabled(true);
        this.mMediaHost = secMediaHost;
        this.mMediaDataManager = mediaDataManager;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isAboveShelf() {
        if (!canShowHeadsUp$1()) {
            return false;
        }
        if (this.mPinnedStatus.isPinned() || this.mHeadsupDisappearRunning) {
            return true;
        }
        return (this.mIsHeadsUp && this.mAboveShelf) || this.mExpandAnimationRunning || this.mChildIsExpanding;
    }

    public final boolean isActionButtonLongClick(int i, int i2) {
        boolean zContains;
        if (this.mEntry.isOngoingActivity()) {
            View viewFindViewById = this.mPrivateLayout.mExpandedChild.findViewById(R.id.ongoing_activity_expand_icon_buttons);
            if (viewFindViewById instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) viewFindViewById;
                zContains = false;
                for (int i3 = 0; i3 < viewGroup.getChildCount() && !zContains; i3++) {
                    zContains = getButtonViewRect(viewGroup.getChildAt(i3), this).contains(i, i2);
                }
            } else {
                zContains = false;
            }
            if (zContains) {
                return true;
            }
        }
        ImageView imageView = this.mSnoozeButtonView;
        if ((imageView == null || imageView.getVisibility() != 0) ? false : getButtonViewRect(imageView, this).contains(i, i2)) {
            return true;
        }
        ArrayList arrayList = (ArrayList) this.mBubbleButtonViews;
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            ImageView imageView2 = (ImageView) obj;
            if (imageView2.isShown()) {
                if (imageView2.getVisibility() == 0 ? getButtonViewRect(imageView2, this).contains(i, i2) : false) {
                    return true;
                }
            }
        }
        ArrayList arrayList2 = (ArrayList) this.mInsigificantChildrenList;
        int size2 = arrayList2.size();
        int i5 = 0;
        while (i5 < size2) {
            Object obj2 = arrayList2.get(i5);
            i5++;
            ArrayList arrayList3 = (ArrayList) ((NotificationEntry) obj2).row.mBubbleButtonViews;
            int size3 = arrayList3.size();
            int i6 = 0;
            while (i6 < size3) {
                Object obj3 = arrayList3.get(i6);
                i6++;
                ImageView imageView3 = (ImageView) obj3;
                if (imageView3.isShown()) {
                    if (imageView3.getVisibility() == 0 ? getButtonViewRect(imageView3, this).contains(i, i2) : false) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public final boolean isExpandable() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic()) {
            return !this.mChildrenExpanded;
        }
        int i = PromotedNotificationUi.$r8$clinit;
        return this.mEnableNonGroupedNotificationExpand && this.mExpandable;
    }

    public final boolean isExpanded(boolean z) {
        int i = PromotedNotificationUi.$r8$clinit;
        if (shouldShowPublic()) {
            return false;
        }
        if (!this.mOnKeyguard || z || (this.mEntry.isOngoingActivity() && this.mEntry.isPromotedState())) {
            return (!this.mHasUserChangedExpansion && this.mIsSystemExpanded) || this.mUserExpanded;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isGroupExpanded$1() {
        int i = NotificationBundleUi.$r8$clinit;
        return ((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(getEntryLegacy());
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isGroupExpansionChanging() {
        return isChildInGroup() ? this.mNotificationParent.isGroupExpansionChanging() : this.mGroupExpansionChanging;
    }

    public final boolean isGroupRoot() {
        int i = NotificationBundleUi.$r8$clinit;
        return ((GroupMembershipManagerImpl) this.mGroupMembershipManager).isGroupSummary(getEntryLegacy());
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
        return this.mPinnedStatus.isPinned();
    }

    public final boolean isShowingExpanded() {
        if (!shouldShowPublic() && ((!this.mIsMinimized || isExpanded(false)) && isGroupRoot())) {
            return isGroupExpanded$1();
        }
        if (this.mEnableNonGroupedNotificationExpand) {
            return this.mPinnedStatus.isPinned() ? this.mExpandedWhenPinned : isExpanded(false);
        }
        return false;
    }

    @Override // android.view.View
    public final boolean isSoundEffectsEnabled() {
        StatusBarNotificationPresenter$$ExternalSyntheticLambda4 statusBarNotificationPresenter$$ExternalSyntheticLambda4;
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        return (statusBarStateController == null || !statusBarStateController.isDozing() || (statusBarNotificationPresenter$$ExternalSyntheticLambda4 = this.mSecureStateProvider) == null || statusBarNotificationPresenter$$ExternalSyntheticLambda4.getAsBoolean()) && super.isSoundEffectsEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isSummaryWithChildren() {
        return this.mIsSummaryWithChildren;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean isUserGroupExpanded() {
        return this.mUserLocked && isGroupRoot();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void markHeadsUpSeen() {
        this.mMustStayOnScreen = false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean mustStayOnScreen() {
        return (this.mIsHeadsUp && this.mMustStayOnScreen) || this.mPinnedStatus.isPinned();
    }

    public final boolean needsRedaction() {
        return ((NotificationLockscreenUserManagerImpl) ((NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class))).getRedactionType(this.mEntry) != 0;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void notifyHeightChanged(boolean z) {
        super.notifyHeightChanged(z);
        getShowingLayout().selectLayout(z || this.mUserLocked, false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void onAppearAnimationFinished(boolean z, boolean z2) {
        ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
        String str = this.mLoggingKey;
        NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
        notificationRowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(logMessageObtain);
        if (!z) {
            setHeadsUpAnimatingAway(false);
        } else {
            resetAllContentAlphas();
            setNotificationFaded(false);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void onAppearAnimationSkipped(boolean z) {
        ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
        String str = this.mLoggingKey;
        NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
        notificationRowLogger.getClass();
        LogLevel logLevel = LogLevel.WARNING;
        NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void onAppearAnimationStarted(boolean z) {
        ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
        String str = this.mLoggingKey;
        NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
        notificationRowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    public final void onAttachedChildrenCountChanged() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        NotificationViewWrapper notificationViewWrapper;
        boolean z = this.mIsSummaryWithChildren;
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        boolean z2 = notificationChildrenContainer != null && notificationChildrenContainer.getNotificationChildCount() > 0;
        this.mIsSummaryWithChildren = z2;
        if (z2) {
            Trace.beginSection("ExpNotRow#onChildCountChanged (summary)");
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
            NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = notificationChildrenContainer2.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper == null || notificationHeaderViewWrapper.mNotificationHeader == null) {
                AnonymousClass1 anonymousClass1 = this.mExpandClickListener;
                int i2 = NotificationBundleUi.$r8$clinit;
                notificationChildrenContainer2.recreateNotificationHeader(anonymousClass1, ((PeopleNotificationIdentifierImpl) this.mPeopleNotificationIdentifier).getPeopleNotificationType(getEntryLegacy()) != 0);
            }
        }
        if (!this.mIsSummaryWithChildren && z) {
            int i3 = NotificationBundleUi.$r8$clinit;
            NotificationContentView notificationContentView = this.mPublicLayout;
            long when = getEntryLegacy().mSbn.getNotification().getWhen();
            if ((notificationContentView.mContractedChild == null || (notificationViewWrapper = notificationContentView.mContractedWrapper) == null) && ((notificationContentView.mExpandedChild == null || (notificationViewWrapper = notificationContentView.mExpandedWrapper) == null) && (notificationContentView.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView.mHeadsUpWrapper) == null))) {
                notificationViewWrapper = null;
            }
            if (notificationViewWrapper instanceof NotificationHeaderViewWrapper) {
                ((NotificationHeaderViewWrapper) notificationViewWrapper).setNotificationWhen(when);
            }
        }
        getShowingLayout().updateBackgroundColor(false);
        this.mPrivateLayout.updateExpandButtonsDuringLayout(isExpandable(), false);
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
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String str = this.mEntry.mKey;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
        if (ongoingActivityDataByKey != null) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = getContext();
            NotificationContentView notificationContentView = this.mPrivateLayout;
            OngoingType ongoingType = OngoingType.ENR;
            ongoingActivityLayoutUtil.getClass();
            OngoingActivityLayoutUtil.updateNowbarSports(context, notificationContentView, ongoingActivityDataByKey, ongoingType);
        }
    }

    public final void onExpandedByGesture(boolean z) {
        this.mMetricsLogger.action(isGroupRoot() ? 410 : 409, z);
    }

    public final void onExpansionChanged(boolean z, boolean z2) {
        boolean zIsExpanded = isExpanded(false);
        if (this.mIsSummaryWithChildren) {
            zIsExpanded = isGroupExpanded$1();
        }
        if (zIsExpanded != z2) {
            updateShelfIconColor();
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                ExpandableNotificationRowController.this.mStatsLogger.onNotificationExpansionChanged(this.mLoggingKey, this.mViewState.location, zIsExpanded, z);
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
                conversationNotificationManager$onEntryViewBound$1.onExpansionChanged(zIsExpanded);
            }
            if (AccessibilityManager.getInstance(((FrameLayout) this).mContext).isEnabled()) {
                AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
                accessibilityEventObtain.setEventType(2048);
                accessibilityEventObtain.setContentChangeTypes(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
                sendAccessibilityEventUnchecked(accessibilityEventObtain);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    public final void onFinishInflate() {
        final int i = 1;
        super.onFinishInflate();
        this.mPublicLayout = (NotificationContentView) findViewById(R.id.expandedPublic);
        NotificationContentView notificationContentView = (NotificationContentView) findViewById(R.id.expanded);
        this.mPrivateLayout = notificationContentView;
        NotificationContentView[] notificationContentViewArr = {notificationContentView, this.mPublicLayout};
        this.mLayouts = notificationContentViewArr;
        final int i2 = 0;
        for (NotificationContentView notificationContentView2 : notificationContentViewArr) {
            notificationContentView2.mExpandClickListener = this.mExpandClickListener;
            notificationContentView2.mContainingNotification = this;
        }
        ViewStub viewStub = (ViewStub) findViewById(R.id.notification_guts_stub);
        this.mGutsStub = viewStub;
        viewStub.setOnInflateListener(new ViewStub.OnInflateListener(this) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda1
            public final /* synthetic */ ExpandableNotificationRow f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub2, View view) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
                int i3 = i2;
                ExpandableNotificationRow expandableNotificationRow = this.f$0;
                switch (i3) {
                    case 0:
                        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
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
                        NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
                        expandableNotificationRow.mChildrenContainer = notificationChildrenContainer;
                        notificationChildrenContainer.mIsMinimized = expandableNotificationRow.mIsMinimized;
                        if (notificationChildrenContainer.mContainingNotification != null) {
                            int i4 = AsyncGroupHeaderViewInflation.$r8$clinit;
                            notificationChildrenContainer.recreateLowPriorityHeader(null);
                            notificationChildrenContainer.updateHeaderVisibility(false, false);
                        }
                        boolean z = notificationChildrenContainer.mUserLocked;
                        if (z) {
                            notificationChildrenContainer.setUserLocked(z);
                        }
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
        viewStub2.setOnInflateListener(new ViewStub.OnInflateListener(this) { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda1
            public final /* synthetic */ ExpandableNotificationRow f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub22, View view) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
                int i3 = i;
                ExpandableNotificationRow expandableNotificationRow = this.f$0;
                switch (i3) {
                    case 0:
                        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
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
                        NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
                        expandableNotificationRow.mChildrenContainer = notificationChildrenContainer;
                        notificationChildrenContainer.mIsMinimized = expandableNotificationRow.mIsMinimized;
                        if (notificationChildrenContainer.mContainingNotification != null) {
                            int i4 = AsyncGroupHeaderViewInflation.$r8$clinit;
                            notificationChildrenContainer.recreateLowPriorityHeader(null);
                            notificationChildrenContainer.updateHeaderVisibility(false, false);
                        }
                        boolean z = notificationChildrenContainer.mUserLocked;
                        if (z) {
                            notificationChildrenContainer.setUserLocked(z);
                        }
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
        int i4 = NotificationAddXOnHoverToDismiss.$r8$clinit;
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mLongPressListener == null) {
            z = false;
        } else if (areGutsExposed()) {
            NotificationGuts notificationGuts = this.mGuts;
            z = !(notificationGuts != null && notificationGuts.isLeavebehind());
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
        if (this.mIsSnoozed || !isContentExpandable() || this.mEntry.isPromotedState()) {
            accessibilityNodeInfo.setExpandedState(0);
        } else if (isShowingExpanded()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            accessibilityNodeInfo.setExpandedState(3);
        } else {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            accessibilityNodeInfo.setExpandedState(1);
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin == null || notificationMenuRowPlugin.getSnoozeMenuItem(getContext()) == null) {
            return;
        }
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snooze, getContext().getResources().getString(R.string.notification_menu_snooze_action)));
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        int i = NotificationAddXOnHoverToDismiss.$r8$clinit;
        return super.onInterceptHoverEvent(motionEvent);
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
            getRelativeTopPadding(icon);
            icon.getHeight();
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

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        NotificationMenuRowPlugin notificationMenuRowPlugin = (NotificationMenuRowPlugin) plugin;
        NotificationMenuRowPlugin notificationMenuRowPlugin2 = this.mMenuRow;
        boolean z = (notificationMenuRowPlugin2 == null || notificationMenuRowPlugin2.getMenuView() == null) ? false : true;
        if (z) {
            removeView(this.mMenuRow.getMenuView());
        }
        if (notificationMenuRowPlugin == null) {
            return;
        }
        this.mMenuRow = notificationMenuRowPlugin;
        if (notificationMenuRowPlugin.shouldUseDefaultMenuItems()) {
            ArrayList<NotificationMenuRowPlugin.MenuItem> arrayList = new ArrayList<>();
            Context context2 = ((FrameLayout) this).mContext;
            arrayList.add(new NotificationMenuRow.NotificationMenuItem(context2, context2.getResources().getString(R.string.notification_menu_gear_description), (NotificationConversationInfo) LayoutInflater.from(context2).inflate(R.layout.notification_conversation_info, (ViewGroup) null, false), -1));
            Context context3 = ((FrameLayout) this).mContext;
            arrayList.add(new NotificationMenuRow.NotificationMenuItem(context3, context3.getResources().getString(R.string.notification_menu_gear_description), (PartialConversationInfo) LayoutInflater.from(context3).inflate(R.layout.partial_conversation_info, (ViewGroup) null, false), -1));
            Context context4 = ((FrameLayout) this).mContext;
            arrayList.add(new NotificationMenuRow.NotificationMenuItem(context4, context4.getResources().getString(R.string.notification_menu_gear_description), (NotificationInfo) LayoutInflater.from(context4).inflate(R.layout.notification_info, (ViewGroup) null, false), -1));
            arrayList.add(SecGutInflater.createNotificationMenuItem(((FrameLayout) this).mContext, R.string.notification_menu_snooze_description, R.layout.sec_notification_snooze));
            this.mMenuRow.setMenuItems(arrayList);
        }
        if (z) {
            createMenu();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
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
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(accessibilityEventObtain);
        dispatchPopulateAccessibilityEvent(accessibilityEventObtain);
        accessibilityEvent.appendRecord(accessibilityEventObtain);
        return true;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0 && isChildInGroup() && !isGroupExpanded$1()) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUiModeChanged() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.onNotificationUpdated();
        }
        if (this.mIsCustomNotification || this.mIsCustomBigNotification || this.mIsCustomHeadsUpNotification || this.mIsCustomPublicNotification) {
            reInflateViews$1();
        } else {
            int i = NotificationBundleUi.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            if (!getEntryLegacy().mSbn.getNotification().isMediaNotification() && ((!this.mEntry.mSbn.getNotification().isColorized() || this.mBgTint == 0) && !this.mEntry.isOngoingActivity())) {
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
                    int iIndexOfChild = indexOfChild(menuView);
                    removeView(menuView);
                    this.mMenuRow.createMenu(this);
                    this.mMenuRow.setAppName(this.mAppName);
                    addView(this.mMenuRow.getMenuView(), iIndexOfChild);
                }
                Arrays.stream(this.mLayouts).forEach(new ExpandableNotificationRow$$ExternalSyntheticLambda10());
            }
        }
        NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
        if (notificationChildrenContainer2 != null) {
            ArrayList arrayList = (ArrayList) notificationChildrenContainer2.mAttachedChildren;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((ExpandableNotificationRow) obj).onUiModeChanged();
            }
        }
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        NotificationMenuRowPlugin notificationMenuRowPlugin;
        if (!super.performAccessibilityActionInternal(i, bundle)) {
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
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, long j2, boolean z, boolean z2, Runnable runnable) {
        this.mLogger.logStartAppearAnimation(this.mLoggingKey, true);
        super.performAddAnimation(j, j2, z, z2, runnable);
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (((GroupMembershipManagerImpl) this.mGroupMembershipManager).isGroupSummary(this.mEntry)) {
            if (!((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(this.mEntry)) {
                if ((!this.mShowingPublic || this.mStatusBarStateController.getState() != 0) && !((SecPanelTouchBlockHelper) Dependency.sDependency.getDependencyInner(SecPanelTouchBlockHelper.class)).isKeyguardPanelDisabled()) {
                    this.mExpandClickListener.onClick(this);
                    return true;
                }
                int identifier = (this.mEntry.mSbn.getNotification().contentIntent != null ? this.mEntry.mSbn.getNotification().contentIntent : this.mEntry.mSbn.getNotification().fullScreenIntent).getCreatorUserHandle().getIdentifier();
                NotificationRemoteInputManager notificationRemoteInputManager = this.mNotificationRemoteInputManager;
                UserInfo profileParent = notificationRemoteInputManager.mUserManager.getProfileParent(identifier);
                boolean z = profileParent != null && notificationRemoteInputManager.mKeyguardManager.isDeviceLocked(profileParent.id);
                boolean z2 = (notificationRemoteInputManager.mUserManager.getUserInfo(identifier).isManagedProfile() || notificationRemoteInputManager.mUserManager.getUserInfo(identifier).isPrivateProfile()) && notificationRemoteInputManager.mKeyguardManager.isDeviceLocked(identifier);
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m(" summary clicked and isParentUserLocked - ", " isLockedManagedProfile : ", "NotifRemoteInputManager", z, z2);
                if (!z && z2) {
                    NotificationRemoteInputManager notificationRemoteInputManager2 = this.mNotificationRemoteInputManager;
                    notificationRemoteInputManager2.getClass();
                    Log.d("NotifRemoteInputManager", " Unlock workprofile only for group summary clicking ");
                    StatusBarRemoteInputCallback statusBarRemoteInputCallback = (StatusBarRemoteInputCallback) notificationRemoteInputManager2.mCallback;
                    statusBarRemoteInputCallback.mCommandQueue.animateCollapsePanels();
                    statusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier, null, null);
                    statusBarRemoteInputCallback.mPendingWorkRemoteInputView = null;
                }
                return super.performClick();
            }
        }
        return super.performClick();
    }

    public final void performDismiss(boolean z) {
        ArrayList arrayList;
        int iIndexOf;
        this.mMetricsLogger.count("notification_dismissed", 1);
        this.mDismissed = true;
        this.mRefocusOnDismiss = z;
        this.mLongPressListener = null;
        this.mDragController = null;
        this.mGroupParentWhenDismissed = this.mNotificationParent;
        this.mChildAfterViewWhenDismissed = null;
        LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 legacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 = this.mEntry.mIcons.mStatusBarIcon.mOnDismissListener;
        if (legacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 != null) {
            legacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4.run();
        }
        if (isChildInGroup() && (iIndexOf = (arrayList = (ArrayList) this.mNotificationParent.getAttachedChildren()).indexOf(this)) != -1 && iIndexOf < arrayList.size() - 1) {
            this.mChildAfterViewWhenDismissed = (View) arrayList.get(iIndexOf + 1);
        }
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("try performDismiss : "), this.mEntry.mKey, "ExpandableNotifRow");
        if (((NotificationDismissibilityProviderImpl) this.mDismissibilityProvider).nonDismissableEntryKeys.contains(getKey()) || this.mOnUserInteractionCallback == null) {
            return;
        }
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String str = this.mEntry.mKey;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
        if (this.mEntry.isPromotedState() && ongoingActivityDataByKey != null && ongoingActivityDataByKey.mIsMediaOngoingData) {
            Log.i("ExpandableNotifRow", "dismiss media data");
            SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaHost.mMediaPlayerData.get(MediaType.ENR);
            if (secMediaPlayerData != null) {
                secMediaPlayerData.getMediaData().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.mMediaDataManager.dismissMediaData((String) ((Map.Entry) obj).getKey(), 0L, true);
                    }
                });
            }
        }
        post(((OnUserInteractionCallbackImpl) this.mOnUserInteractionCallback).registerFutureDismissal(this.mEntry, 2));
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final long performRemoveAnimation(final long j, long j2, final float f, final boolean z, final boolean z2, final Runnable runnable, final Runnable runnable2, final AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide) {
        this.mLogger.logStartAppearAnimation(this.mLoggingKey, false);
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        final long j3 = 0;
        if (notificationMenuRowPlugin == null || !notificationMenuRowPlugin.isMenuVisible()) {
            super.performRemoveAnimation(j, 0L, f, z, z2, runnable, runnable2, animatorListenerAdapter, clipSide);
            return 0L;
        }
        Animator translateViewAnimator = getTranslateViewAnimator(0.0f, null);
        translateViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ExpandableNotificationRow.super.performRemoveAnimation(j, j3, f, z, z2, null, runnable2, animatorListenerAdapter, ExpandableView.ClipSide.BOTTOM);
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

    public final void reInflateViews$1() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        HybridNotificationView hybridNotificationView;
        Trace.beginSection("ExpandableNotificationRow#reInflateViews");
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            AnonymousClass1 anonymousClass1 = this.mExpandClickListener;
            int i = AsyncGroupHeaderViewInflation.$r8$clinit;
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
                int iIndexOfChild = notificationChildrenContainer.indexOfChild(view);
                notificationChildrenContainer.removeView(view);
                View viewInflateDivider = notificationChildrenContainer.inflateDivider();
                notificationChildrenContainer.addView(viewInflateDivider, iIndexOfChild);
                ((ArrayList) notificationChildrenContainer.mDividers).set(i2, viewInflateDivider);
            }
            notificationChildrenContainer.removeView(notificationChildrenContainer.mOverflowNumber);
            notificationChildrenContainer.mOverflowNumber = null;
            notificationChildrenContainer.mGroupOverFlowState = null;
            notificationChildrenContainer.updateGroupOverflow();
        }
        NotificationGuts notificationGuts = this.mGuts;
        if (notificationGuts != null) {
            int iIndexOfChild2 = indexOfChild(notificationGuts);
            removeView(notificationGuts);
            NotificationGuts notificationGuts2 = (NotificationGuts) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.notification_guts, (ViewGroup) this, false);
            this.mGuts = notificationGuts2;
            notificationGuts2.setVisibility(notificationGuts.mExposed ? 0 : 8);
            addView(this.mGuts, iIndexOfChild2);
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        View menuView = notificationMenuRowPlugin == null ? null : notificationMenuRowPlugin.getMenuView();
        if (menuView != null) {
            int iIndexOfChild3 = indexOfChild(menuView);
            removeView(menuView);
            this.mMenuRow.createMenu(this);
            this.mMenuRow.setAppName(this.mAppName);
            addView(this.mMenuRow.getMenuView(), iIndexOfChild3);
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.mMinContractedHeight = notificationContentView.getResources().getDimensionPixelSize(R.dimen.min_notification_layout_height);
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            notificationContentView.getResources().getDimensionPixelSize(R.dimen.conversation_single_line_face_pile_size);
            if (notificationContentView.mIsChildInGroup && (hybridNotificationView = notificationContentView.mSingleLineView) != null) {
                notificationContentView.removeView(hybridNotificationView);
                notificationContentView.mSingleLineView = null;
                NotificationContentView.updateAllSingleLineViews();
            }
        }
        int i4 = NotificationBundleUi.$r8$clinit;
        getEntryLegacy().mSbn.clearPackageContext();
        this.mTempSmallHeight = this.mMaxSmallHeight;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mRowContentBindStage.getStageParams(this.mEntry);
        rowContentBindParams.mViewsNeedReinflation = true;
        rowContentBindParams.mDirtyContentViews = rowContentBindParams.mContentViews | rowContentBindParams.mDirtyContentViews;
        NotificationRebindingTracker notificationRebindingTracker = this.mRebindingTracker;
        String str = getEntryLegacy().mKey;
        notificationRebindingTracker.getClass();
        int i5 = TraceUtils.$r8$clinit;
        final String strTrackGroup = TrackGroupUtils.trackGroup(RCPPolicy.NOTIFICATIONS, "Rebinding");
        final int iNextInt = ThreadLocalRandom.current().nextInt();
        Trace.asyncTraceForTrackBegin(4096L, strTrackGroup, "Rebinding in progress for " + str, iNextInt);
        Function0 function0 = new Function0() { // from class: com.android.app.tracing.TraceUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i6 = TraceUtils.$r8$clinit;
                Trace.asyncTraceForTrackEnd(4096L, strTrackGroup, iNextInt);
                return Unit.INSTANCE;
            }
        };
        StateFlowImpl stateFlowImpl = notificationRebindingTracker.rebindingKeys;
        stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), str));
        final NotificationRebindingTracker$trackRebinding$1 notificationRebindingTracker$trackRebinding$1 = new NotificationRebindingTracker$trackRebinding$1(function0, notificationRebindingTracker, str);
        this.mRowContentBindStage.requestRebind(this.mEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda5
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry) {
                SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                NotificationRebindingTracker$trackRebinding$1 notificationRebindingTracker$trackRebinding$12 = notificationRebindingTracker$trackRebinding$1;
                notificationRebindingTracker$trackRebinding$12.$endTrace.invoke();
                StateFlowImpl stateFlowImpl2 = notificationRebindingTracker$trackRebinding$12.this$0.rebindingKeys;
                stateFlowImpl2.updateState(null, SetsKt___SetsKt.minus((Set) stateFlowImpl2.getValue(), notificationRebindingTracker$trackRebinding$12.$key));
            }
        });
        Trace.endSection();
    }

    public final void removeChildNotification(ExpandableNotificationRow expandableNotificationRow) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
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
                Integer numValueOf = notificationChildrenContainer2 != null ? Integer.valueOf(notificationChildrenContainer2.getNotificationChildCount()) : null;
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(expandableNotificationRow.mEntry.mKey);
                if (numValueOf == null || numValueOf.intValue() != 0) {
                    return;
                }
                subscreenDeviceModelParent.mMainListArrayHashMap.remove(this.mEntry.mKey);
                subscreenDeviceModelParent.notifyListAdapterItemRemoved(this.mEntry);
            }
        }
    }

    public final void removeChildrenWithKeepInParent() {
        if (this.mChildrenContainer == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mChildrenContainer.mAttachedChildren);
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj;
            if (expandableNotificationRow.mKeepInParentForDismissAnimation) {
                this.mChildrenContainer.removeNotification(expandableNotificationRow);
                expandableNotificationRow.setIsChildInGroup(null, false);
                expandableNotificationRow.mKeepInParentForDismissAnimation = false;
                ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
                if (anonymousClass2 != null) {
                    String str = expandableNotificationRow.mLoggingKey;
                    String str2 = this.mLoggingKey;
                    NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                    notificationRowLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(14);
                    LogBuffer logBuffer = notificationRowLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.str1 = str;
                    logMessageImpl.str2 = str2;
                    logBuffer.commit(logMessageObtain);
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
                String str = this.mLoggingKey;
                String str2 = ((NotificationChildrenContainer) viewGroup).mContainingNotification.mLoggingKey;
                NotificationRowLogger notificationRowLogger = expandableNotificationRowController.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(6);
                LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = str2;
                logBuffer.commit(logMessageObtain);
            } else if (viewGroup instanceof NotificationStackScrollLayout) {
                String str3 = this.mLoggingKey;
                NotificationRowLogger notificationRowLogger2 = expandableNotificationRowController.mLogBufferLogger;
                notificationRowLogger2.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda02 = new NotificationRowLogger$$ExternalSyntheticLambda0(8);
                LogBuffer logBuffer2 = notificationRowLogger2.notificationRenderBuffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("NotifRow", logLevel2, notificationRowLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) logMessageObtain2).str1 = str3;
                logBuffer2.commit(logMessageObtain2);
            } else {
                String str4 = this.mLoggingKey;
                NotificationRowLogger notificationRowLogger3 = expandableNotificationRowController.mLogBufferLogger;
                notificationRowLogger3.getClass();
                LogLevel logLevel3 = LogLevel.WARNING;
                NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda03 = new NotificationRowLogger$$ExternalSyntheticLambda0(9);
                LogBuffer logBuffer3 = notificationRowLogger3.notificationRenderBuffer;
                LogMessage logMessageObtain3 = logBuffer3.obtain("NotifRow", logLevel3, notificationRowLogger$$ExternalSyntheticLambda03, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                logMessageImpl2.str1 = str4;
                logMessageImpl2.str2 = viewGroup.toString();
                logBuffer3.commit(logMessageObtain3);
            }
        }
        super.removeFromTransientContainer();
    }

    public final void removeTransientView(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
            if (anonymousClass2 != null) {
                String str = expandableNotificationRow.mLoggingKey;
                String str2 = this.mLoggingKey;
                NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
                notificationRowLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(10);
                LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = str2;
                logBuffer.commit(logMessageObtain);
            }
        }
        super.removeTransientView(view);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void resetAllContentAlphas() {
        ExpandableNotificationRowController.AnonymousClass2 anonymousClass2 = this.mLogger;
        String str = this.mLoggingKey;
        NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
        notificationRowLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
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
            getShelfIcon().setScrollX(0);
        }
        NotificationMenuRowPlugin notificationMenuRowPlugin = this.mMenuRow;
        if (notificationMenuRowPlugin != null) {
            notificationMenuRowPlugin.resetMenu();
        }
    }

    public final void setAboveShelf(boolean z) {
        boolean zIsAboveShelf = isAboveShelf();
        this.mAboveShelf = z;
        if (isAboveShelf() != zIsAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
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
            notificationContentView.mContentHeight = Math.min(notificationContentView.mUnrestrictedContentHeight, notificationContentView.mContainingNotification.getIntrinsicHeight() - notificationContentView.getExtraRemoteInputHeight(notificationContentView.mExpandedRemoteInput));
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
                    if (!notificationChildrenContainer.mContainingNotification.isGroupExpanded$1()) {
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
                        expandableNotificationRow.setActualHeight((int) NotificationUtils.interpolate(expandableNotificationRow.getShowingLayout().getMinHeight(true), maxExpandHeight, groupExpandFraction), true);
                    } else {
                        expandableNotificationRow.setActualHeight((int) maxExpandHeight, true);
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
                    NotificationContentView notificationContentView = showingLayout;
                    int i4 = i;
                    Notification notification2 = (Notification) obj;
                    int i5 = NotificationContentView.$r8$clinit;
                    boolean zIsColorized = notification2.isColorized();
                    RemoteInputView remoteInputView = notificationContentView.mExpandedRemoteInput;
                    if (remoteInputView != null) {
                        remoteInputView.setBackgroundTintColor(i4, notification2.color, zIsColorized);
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
            boolean z2 = z || this.mPinnedStatus.isPinned();
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
        this.mClipToActualHeight = z || this.mUserLocked || isChildInGroup();
        updateClipping$1();
        NotificationContentView showingLayout = getShowingLayout();
        if (!z && !this.mUserLocked && !isChildInGroup()) {
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
        int i = 0;
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setAlpha(f);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            if (notificationChildrenContainer.mGroupHeader != null) {
                for (int i2 = 0; i2 < notificationChildrenContainer.mGroupHeader.getChildCount(); i2++) {
                    notificationChildrenContainer.mGroupHeader.getChildAt(i2).setAlpha(f);
                }
            }
            ArrayList arrayList = (ArrayList) notificationChildrenContainer.mAttachedChildren;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((ExpandableNotificationRow) obj).setContentAlpha(f);
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

    public final void setDismissUsingRowTranslationX(boolean z, boolean z2) {
        if (!z2 && z == this.mDismissUsingRowTranslationX) {
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
            ((ExpandableNotificationRow) arrayList.get(i)).setDismissUsingRowTranslationX(z, z2);
            i++;
        }
    }

    @Deprecated
    public void setEntryLegacy(NotificationEntry notificationEntry) {
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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
        SeslRecoilDrawable seslRecoilDrawable = notificationBackgroundView.mBackground;
        if (seslRecoilDrawable != null) {
            ((GradientDrawable) seslRecoilDrawable.getDrawable(0)).setAntiAlias(!z);
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
        NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0 notificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0;
        boolean zIsAboveShelf = isAboveShelf();
        boolean z2 = z != this.mHeadsupDisappearRunning;
        this.mHeadsupDisappearRunning = z;
        NotificationContentView notificationContentView = this.mPrivateLayout;
        notificationContentView.mHeadsUpAnimatingAway = z;
        notificationContentView.selectLayout(false, true);
        if (z2 && (notificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0 = this.mHeadsUpAnimatingAwayListener) != null) {
            notificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0.accept(Boolean.valueOf(z));
        }
        if (isAboveShelf() != zIsAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
        }
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
        boolean z5 = z4 == z3;
        if (this.mShowingPublicInitialized && z5) {
            return;
        }
        int i = NotificationContentAlphaOptimization.$r8$clinit;
        this.mPublicLayout.animate().cancel();
        this.mPrivateLayout.animate().cancel();
        NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
        if (notificationChildrenContainer2 != null) {
            notificationChildrenContainer2.animate().cancel();
        }
        resetAllContentAlphas();
        this.mPublicLayout.setVisibility(this.mShowingPublic ? 0 : 4);
        updateChildrenVisibility();
        getShowingLayout().updateBackgroundColor(z2);
        this.mPrivateLayout.updateExpandButtonsDuringLayout(isExpandable(), false);
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

    public void setIgnoreLockscreenConstraints(boolean z) {
        this.mIgnoreLockscreenConstraints = z;
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
        this.mPrivateLayout.setIsChildInGroup(z);
        int i = LockscreenOtpRedaction.$r8$clinit;
        this.mPublicLayout.setIsChildInGroup(z);
        updateBackgroundForGroupState();
        updateClickAndFocus();
        if (this.mNotificationParent != null) {
            setBackgroundTintColor(calculateBgColor(true, true));
            this.mNotificationParent.updateBackgroundForGroupState();
        }
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        boolean zIsChildInGroup = true ^ isChildInGroup();
        if (zIsChildInGroup != notificationBackgroundView.mBottomAmountClips) {
            notificationBackgroundView.mBottomAmountClips = zIsChildInGroup;
            notificationBackgroundView.invalidate();
        }
        float f = this.mSmallRoundness;
        requestRoundness(f, f, BASE_VALUE, false);
    }

    public void setMagneticRowListener(MagneticRowListener magneticRowListener) {
        this.mMagneticRowListener = magneticRowListener;
    }

    public final void setNotificationFaded(boolean z) {
        this.mIsFaded = z;
        if (!childrenRequireOverlappingRendering()) {
            setLayerType(0, null);
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            if (notificationChildrenContainer != null) {
                notificationChildrenContainer.setNotificationFaded(z);
            } else {
                NotificationFadeAware.setLayerTypeForFaded(notificationChildrenContainer, z);
            }
            for (NotificationContentView notificationContentView : this.mLayouts) {
                if (notificationContentView != null) {
                    notificationContentView.setNotificationFaded(z);
                } else {
                    NotificationFadeAware.setLayerTypeForFaded(notificationContentView, z);
                }
            }
            return;
        }
        NotificationFadeAware.setLayerTypeForFaded(this, z);
        NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
        if (notificationChildrenContainer2 != null) {
            notificationChildrenContainer2.setNotificationFaded(false);
        } else {
            NotificationFadeAware.setLayerTypeForFaded(notificationChildrenContainer2, false);
        }
        for (NotificationContentView notificationContentView2 : this.mLayouts) {
            if (notificationContentView2 != null) {
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
            boolean zIsAboveShelf = isAboveShelf();
            boolean zIsExpanded = isExpanded(false);
            this.mOnKeyguard = z;
            onExpansionChanged(false, zIsExpanded);
            if (zIsExpanded != isExpanded(false)) {
                if (this.mIsSummaryWithChildren) {
                    this.mChildrenContainer.updateGroupOverflow();
                }
                notifyHeightChanged(false);
            }
            if (isAboveShelf() != zIsAboveShelf) {
                this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
            }
            int i = SceneContainerFlag.$r8$clinit;
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

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
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
            getShelfIcon().setScrollX((int) (-f));
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
            boolean zIsGroupExpanded$1 = isGroupExpanded$1();
            int i = NotificationBundleUi.$r8$clinit;
            ((GroupExpansionManagerImpl) this.mGroupExpansionManager).setGroupExpanded(getEntryLegacy(), z);
            onExpansionChanged(true, zIsGroupExpanded$1);
            return;
        }
        if (!z || this.mExpandable) {
            boolean zIsExpanded = isExpanded(false);
            this.mHasUserChangedExpansion = true;
            this.mUserExpanded = z;
            onExpansionChanged(true, zIsExpanded);
            if (zIsExpanded || !isExpanded(false) || this.mActualHeight == getIntrinsicHeight()) {
                return;
            }
            notifyHeightChanged(true);
        }
    }

    public final void setUserLocked(boolean z) {
        int i = PromotedNotificationUi.$r8$clinit;
        this.mUserLocked = z;
        NotificationContentView notificationContentView = this.mPrivateLayout;
        notificationContentView.mUserExpanding = z;
        if (z) {
            notificationContentView.mTransformationStartVisibleType = notificationContentView.mVisibleType;
        } else {
            notificationContentView.mTransformationStartVisibleType = -1;
            int iCalculateVisibleType = notificationContentView.calculateVisibleType();
            notificationContentView.mVisibleType = iCalculateVisibleType;
            notificationContentView.updateViewVisibilities(iCalculateVisibleType);
            notificationContentView.updateBackgroundColor(false);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setUserLocked(z);
            if (this.mIsSummaryWithChildren) {
                if (z || !isGroupExpanded$1()) {
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
        if (!isHeadsUpState()) {
            return false;
        }
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (statusBarStateController != null && statusBarStateController.isDozing()) {
            return true;
        }
        if (!this.mOnKeyguard) {
            return false;
        }
        KeyguardBypassController keyguardBypassController = this.mBypassController;
        return keyguardBypassController == null || keyguardBypassController.getBypassEnabled();
    }

    public final void toggleExpansionState(View view, boolean z) {
        boolean z2;
        NotificationChildrenContainer notificationChildrenContainer;
        if (isChildInGroup() && (notificationChildrenContainer = this.mNotificationParent.mChildrenContainer) != null && equals(notificationChildrenContainer.getFirstChild())) {
            if (!((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(this.mEntry)) {
                ExpandableNotificationRow expandableNotificationRow = this.mNotificationParent;
                expandableNotificationRow.mExpandClickListener.onClick(expandableNotificationRow);
                return;
            }
        }
        if (this.mIsMinimized) {
            ((GroupMembershipManagerImpl) this.mGroupMembershipManager).isGroupSummary(this.mEntry);
        }
        if (!shouldShowPublic()) {
            if (this.mIsMinimized) {
                isExpanded(false);
            }
            if (isGroupRoot()) {
                this.mGroupExpansionChanging = true;
                int i = NotificationBundleUi.$r8$clinit;
                boolean zIsGroupExpanded = ((GroupExpansionManagerImpl) this.mGroupExpansionManager).isGroupExpanded(getEntryLegacy());
                GroupExpansionManager groupExpansionManager = this.mGroupExpansionManager;
                NotificationEntry entryLegacy = getEntryLegacy();
                GroupExpansionManagerImpl groupExpansionManagerImpl = (GroupExpansionManagerImpl) groupExpansionManager;
                groupExpansionManagerImpl.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                groupExpansionManagerImpl.setGroupExpanded(entryLegacy, !groupExpansionManagerImpl.isGroupExpanded(entryLegacy));
                boolean zIsGroupExpanded2 = groupExpansionManagerImpl.isGroupExpanded(entryLegacy);
                ((StatusBarNotificationPresenter) this.mOnExpandClickListener).onExpandClicked(getEntryLegacy(), zIsGroupExpanded2);
                if (z) {
                    this.mMetricsLogger.action(VolteConstants.ErrorCode.REQUEST_TIMEOUT, zIsGroupExpanded2);
                }
                onExpansionChanged(true, zIsGroupExpanded);
                return;
            }
        }
        if (this.mEnableNonGroupedNotificationExpand) {
            if (view != null && view.isAccessibilityFocused()) {
                this.mPrivateLayout.mFocusOnVisibilityChange = true;
            }
            if (this.mPinnedStatus.isPinned()) {
                z2 = !this.mExpandedWhenPinned;
                this.mExpandedWhenPinned = z2;
                ConversationNotificationManager$onEntryViewBound$1 conversationNotificationManager$onEntryViewBound$1 = this.mExpansionChangedListener;
                if (conversationNotificationManager$onEntryViewBound$1 != null) {
                    conversationNotificationManager$onEntryViewBound$1.onExpansionChanged(z2);
                }
            } else {
                z2 = !isExpanded(false);
                setUserExpanded(z2, false);
            }
            notifyHeightChanged(true);
            int i2 = NotificationBundleUi.$r8$clinit;
            ((StatusBarNotificationPresenter) this.mOnExpandClickListener).onExpandClicked(getEntryLegacy(), z2);
            if (z) {
                this.mMetricsLogger.action(407, z2);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void updateBackgroundColors() {
        super.updateBackgroundColors();
        this.mColorUpdateLogger.getClass();
        if (this.mIsSummaryWithChildren) {
            ArrayList arrayList = (ArrayList) this.mChildrenContainer.mAttachedChildren;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj;
                ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getClass();
                if (NotificationColorPicker.isNeedToUpdated(expandableNotificationRow)) {
                    expandableNotificationRow.updateBackgroundColors();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004f A[LOOP:0: B:22:0x0046->B:24:0x004f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x009b A[EDGE_INSN: B:54:0x009b->B:44:0x009b BREAK  A[LOOP:0: B:22:0x0046->B:24:0x004f], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBackgroundForGroupState() {
        boolean z;
        CachingIconView cachingIconView;
        List list;
        ArrayList arrayList;
        int i = 0;
        if (this.mIsSummaryWithChildren) {
            if (isGroupExpanded$1()) {
                z = true;
                this.mShowNoBackground = z;
                this.mChildrenContainer.getClass();
                NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
                boolean z2 = this.mShowNoBackground;
                cachingIconView = notificationChildrenContainer.mGroupIconView;
                if (cachingIconView != null) {
                    cachingIconView.setVisibility(!z2 ? 0 : 4);
                }
                if (shouldShowPublic()) {
                    this.mShowNoBackground = false;
                } else {
                    this.mShowNoBackground = true;
                }
                this.mChildrenContainer.updateHeaderForExpansion(this.mShowNoBackground);
                list = this.mChildrenContainer.mAttachedChildren;
                while (true) {
                    arrayList = (ArrayList) list;
                    if (i >= arrayList.size()) {
                        break;
                    }
                    ((ExpandableNotificationRow) arrayList.get(i)).updateBackgroundForGroupState();
                    i++;
                }
            } else if (this.mUserLocked) {
                this.mChildrenContainer.getClass();
                z = true;
                this.mShowNoBackground = z;
                this.mChildrenContainer.getClass();
                NotificationChildrenContainer notificationChildrenContainer2 = this.mChildrenContainer;
                boolean z22 = this.mShowNoBackground;
                cachingIconView = notificationChildrenContainer2.mGroupIconView;
                if (cachingIconView != null) {
                }
                if (shouldShowPublic()) {
                }
                this.mChildrenContainer.updateHeaderForExpansion(this.mShowNoBackground);
                list = this.mChildrenContainer.mAttachedChildren;
                while (true) {
                    arrayList = (ArrayList) list;
                    if (i >= arrayList.size()) {
                    }
                    ((ExpandableNotificationRow) arrayList.get(i)).updateBackgroundForGroupState();
                    i++;
                }
            } else {
                z = false;
                this.mShowNoBackground = z;
                this.mChildrenContainer.getClass();
                NotificationChildrenContainer notificationChildrenContainer22 = this.mChildrenContainer;
                boolean z222 = this.mShowNoBackground;
                cachingIconView = notificationChildrenContainer22.mGroupIconView;
                if (cachingIconView != null) {
                }
                if (shouldShowPublic()) {
                }
                this.mChildrenContainer.updateHeaderForExpansion(this.mShowNoBackground);
                list = this.mChildrenContainer.mAttachedChildren;
                while (true) {
                    arrayList = (ArrayList) list;
                    if (i >= arrayList.size()) {
                    }
                    ((ExpandableNotificationRow) arrayList.get(i)).updateBackgroundForGroupState();
                    i++;
                }
            }
        } else if (isChildInGroup()) {
            NotificationContentView showingLayout = getShowingLayout();
            NotificationViewWrapper visibleWrapper = showingLayout.getVisibleWrapper((showingLayout.mContainingNotification.isGroupExpanded$1() || showingLayout.mContainingNotification.mUserLocked) ? showingLayout.calculateVisibleType() : showingLayout.mVisibleType);
            if (visibleWrapper != null) {
                visibleWrapper.getCustomBackgroundColor();
            }
            if (!isGroupExpanded$1() && !this.mNotificationParent.isGroupExpansionChanging()) {
                boolean z3 = this.mNotificationParent.mUserLocked;
            }
            this.mShowNoBackground = false;
        } else {
            this.mShowNoBackground = false;
        }
        if (!this.mCustomOutline) {
            setOutlineProvider(needsOutline() ? this.mProvider : null);
        }
        updateBackground();
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
        boolean z = !isChildInGroup() || isGroupExpanded$1();
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
        int i;
        for (NotificationContentView notificationContentView : this.mLayouts) {
            int i2 = PromotedNotificationUi.$r8$clinit;
            int i3 = this.mMaxExpandedHeight;
            View view = notificationContentView.mContractedChild;
            boolean z = (view == null || view.getId() == 16909885) ? false : true;
            int i4 = NotificationBundleUi.$r8$clinit;
            int i5 = getEntryLegacy().targetSdk;
            boolean z2 = i5 < 24;
            boolean z3 = i5 < 28;
            boolean z4 = i5 < 31;
            boolean z5 = view instanceof CallLayout;
            if (!(view instanceof MessagingLayout)) {
                boolean z6 = view instanceof ConversationLayout;
            }
            getEntryLegacy().mRanking.getSummarization();
            if (z && z4 && !this.mIsSummaryWithChildren) {
                i = z2 ? this.mMaxSmallHeightBeforeN : z3 ? this.mMaxSmallHeightBeforeP : this.mMaxSmallHeightBeforeS;
            } else if (z5) {
                i = i3;
            } else {
                int i6 = NmSummarizationUiFlag.$r8$clinit;
                if (this.mMaxSmallHeight != this.mTempSmallHeight) {
                    StringBuilder sb = new StringBuilder("mMaxSmallHeight different before reInflateViews mMaxSmallHeight: ");
                    sb.append(this.mMaxSmallHeight);
                    sb.append(" mTempSmallHeight:");
                    sb.append(this.mTempSmallHeight);
                    sb.append(" ");
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, this.mLoggingKey, "ExpandableNotifRow");
                }
                i = this.mMaxSmallHeight;
            }
            View view2 = notificationContentView.mHeadsUpChild;
            int iMax = (view2 == null || view2.getId() == 16909885 || !z4) ? this.mMaxHeadsUpHeight : z2 ? this.mMaxHeadsUpHeightBeforeN : z3 ? this.mMaxHeadsUpHeightBeforeP : this.mMaxHeadsUpHeightBeforeS;
            NotificationViewWrapper visibleWrapper = notificationContentView.getVisibleWrapper(2);
            if (visibleWrapper != null) {
                iMax = Math.max(iMax, visibleWrapper.getMinLayoutHeight());
            }
            notificationContentView.mSmallHeight = i;
            notificationContentView.mHeadsUpHeight = iMax;
            notificationContentView.mNotificationMaxHeight = i3;
        }
    }

    public void updateShelfIconColor() {
        int iResolveContrastColor;
        StatusBarIconView shelfIcon = getShelfIcon();
        Boolean.TRUE.equals(shelfIcon.getTag(R.id.icon_is_pre_L));
        if (NotificationUtils.isGrayscale(shelfIcon, ContrastColorUtil.getInstance(((FrameLayout) this).mContext))) {
            if (!this.mIsSummaryWithChildren || shouldShowPublic()) {
                NotificationContentView showingLayout = getShowingLayout();
                NotificationViewWrapper visibleWrapper = showingLayout.getVisibleWrapper(showingLayout.mVisibleType);
                int originalIconColor = visibleWrapper != null ? visibleWrapper.getOriginalIconColor() : 1;
                if (originalIconColor != 1) {
                    originalIconColor = originalIconColor;
                } else {
                    int i = NotificationBundleUi.$r8$clinit;
                    NotificationEntry entryLegacy = getEntryLegacy();
                    Context context = ((FrameLayout) this).mContext;
                    boolean z = this.mIsMinimized && !isExpanded(false);
                    int iCalculateBgColor = calculateBgColor(false, false);
                    originalIconColor = z ? 0 : entryLegacy.mSbn.getNotification().color;
                    if (entryLegacy.mCachedContrastColorIsFor != originalIconColor || (iResolveContrastColor = entryLegacy.mCachedContrastColor) == 1) {
                        iResolveContrastColor = ContrastColorUtil.resolveContrastColor(context, originalIconColor, iCalculateBgColor);
                        entryLegacy.mCachedContrastColorIsFor = originalIconColor;
                        entryLegacy.mCachedContrastColor = iResolveContrastColor;
                    }
                    originalIconColor = iResolveContrastColor;
                }
            } else {
                int i2 = AsyncGroupHeaderViewInflation.$r8$clinit;
                originalIconColor = this.mChildrenContainer.getVisibleWrapper().getOriginalIconColor();
            }
        }
        shelfIcon.setStaticDrawableColor(originalIconColor);
    }

    public final int getPinnedHeadsUpHeight(boolean z) {
        if (this.mIsSummaryWithChildren) {
            return this.mChildrenContainer.getIntrinsicHeight();
        }
        int i = PromotedNotificationUi.$r8$clinit;
        return this.mExpandedWhenPinned ? Math.max(getMaxExpandHeight(), getHeadsUpHeight()) : getShowingLayout().mIsHUNCompact ? getHeadsUpHeight() : z ? Math.max(getCollapsedHeight(), getHeadsUpHeight()) : getHeadsUpHeight();
    }

    public ExpandableNotificationRow(Context context, AttributeSet attributeSet, NotificationEntry notificationEntry) {
        this(context, attributeSet, context.getUserId() == notificationEntry.mSbn.getNormalizedUserId() ? context : context.createContextAsUser(UserHandle.of(notificationEntry.mSbn.getNormalizedUserId()), 0));
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doLongClickCallback(int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
        boolean z;
        ExpandableNotificationRowController$$ExternalSyntheticLambda1 expandableNotificationRowController$$ExternalSyntheticLambda1 = this.mLongPressListener;
        if (expandableNotificationRowController$$ExternalSyntheticLambda1 == null || menuItem == null) {
            return;
        }
        ExpandableNotificationRowController expandableNotificationRowController = expandableNotificationRowController$$ExternalSyntheticLambda1.f$0;
        ExpandableNotificationRow expandableNotificationRow = expandableNotificationRowController.mView;
        if (expandableNotificationRow.isActionButtonLongClick(i, i2)) {
            Log.d("NotifRowController", "notification action button tooltip showing.");
            return;
        }
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        boolean z2 = false;
        NotificationGutsManager notificationGutsManager = expandableNotificationRowController.mNotificationGutsManager;
        if (notificationGuts != null && notificationGuts.mExposed) {
            View view = notificationGutsManager.mGutsViewOffButton;
            if (view == null || notificationGutsManager.mGutsViewSettingsButton == null) {
                z = false;
                if (z) {
                    Log.d("NotifRowController", "notification guts button tooltip showing.");
                    return;
                }
            } else {
                if (!(view.getVisibility() == 0 ? getButtonViewRect(view, expandableNotificationRow).contains(i, i2) : false)) {
                    View view2 = notificationGutsManager.mGutsViewSettingsButton;
                    if ((view2 == null || view2.getVisibility() != 0) ? false : getButtonViewRect(view2, expandableNotificationRow).contains(i, i2)) {
                    }
                    if (z) {
                    }
                }
                z = true;
                if (z) {
                }
            }
        }
        if (expandableNotificationRow.mIsSummaryWithChildren && expandableNotificationRow.isGroupExpanded$1()) {
            ExpandableNotificationRow viewAtPosition = expandableNotificationRow.getViewAtPosition(i2);
            if (viewAtPosition.equals(expandableNotificationRow)) {
                return;
            }
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            expandableNotificationRow.getLocationOnScreen(iArr);
            viewAtPosition.getLocationOnScreen(iArr2);
            int i3 = i2 - (iArr2[1] - iArr[1]);
            if (viewAtPosition.isActionButtonLongClick(i, i3)) {
                Log.d("NotifRowController", "notification action button tooltip showing.");
                return;
            }
            NotificationGuts notificationGuts2 = viewAtPosition.mGuts;
            if (notificationGuts2 != null && notificationGuts2.mExposed) {
                View view3 = notificationGutsManager.mGutsViewOffButton;
                if (view3 != null && notificationGutsManager.mGutsViewSettingsButton != null) {
                    if (view3.getVisibility() == 0 ? getButtonViewRect(view3, viewAtPosition).contains(i, i3) : false) {
                        z2 = true;
                    } else {
                        View view4 = notificationGutsManager.mGutsViewSettingsButton;
                        if ((view4 == null || view4.getVisibility() != 0) ? false : getButtonViewRect(view4, viewAtPosition).contains(i, i3)) {
                        }
                    }
                }
                if (z2) {
                    Log.d("NotifRowController", "notification guts button tooltip showing.");
                    return;
                }
            }
            notificationGutsManager.openGuts(viewAtPosition, i, i2, menuItem);
            return;
        }
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String key = expandableNotificationRowController$$ExternalSyntheticLambda1.f$1.getKey();
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(key);
        if (ongoingActivityDataByKey == null || !ongoingActivityDataByKey.mIsMediaOngoingData) {
            notificationGutsManager.openGuts(this, i, i2, menuItem);
        }
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

    public ExpandableNotificationRow(Context context, AttributeSet attributeSet, UserHandle userHandle) {
        this(context, attributeSet, context.getUserId() == userHandle.getIdentifier() ? context : context.createContextAsUser(userHandle, 0));
        int i = NotificationBundleUi.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("New code path not supported when com.android.systemui.notification_bundle_ui is disabled.");
    }

    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda0] */
    private ExpandableNotificationRow(Context context, AttributeSet attributeSet, Context context2) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mBubbleButtonViews = new ArrayList();
        this.mImageModelIndex = null;
        this.mInsigificantChildrenList = new ArrayList();
        this.mShowPublicExpander = true;
        this.mHeaderVisibleAmount = 1.0f;
        this.mLastChronometerRunning = true;
        this.mPinnedStatus = PinnedStatus.NotPinned;
        this.mExpandClickListener = new AnonymousClass1();
        new ListenerSet();
        this.mRedactionType = 0;
        this.mTempSmallHeight = 0;
        this.mExpireRecentlyAlertedFlag = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableNotificationRow expandableNotificationRow = this.f$0;
                SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                expandableNotificationRow.applyAudiblyAlertedRecently(false);
            }
        };
        this.mImageResolver = new NotificationInlineImageResolver(context2, new NotificationInlineImageCache());
        getResources().getDimension(R.dimen.notification_corner_radius_small);
        this.mSmallRoundness = 1.0f;
        AnonymousClass2 anonymousClass2 = TRANSLATE_CONTENT;
        this.mMagneticAnimator = new SpringAnimation(this, new FloatPropertyCompat.AnonymousClass1(anonymousClass2.getName(), anonymousClass2));
        initDimens$1();
    }

    public void setEntryAdapter(EntryAdapter entryAdapter) {
    }
}
