package com.android.systemui.statusbar.notification.row;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingTextMessage;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.logging.NotiCinemaLogger;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationCompactHeadsUpTemplateViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationCustomViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ExpandHeadsUpOnInlineReply;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingSeekBarCreator;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingType;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wmshell.BubblesManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.ArrayIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationContentView extends FrameLayout implements NotificationFadeAware, PanelScreenShotLogger.LogProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimate;
    public int mAnimationStartVisibleType;
    public boolean mBeforeN;
    public boolean mBubblesEnabledForUser;
    public RemoteInputView mCachedExpandedRemoteInput;
    public RemoteInputViewController mCachedExpandedRemoteInputViewController;
    public int mClipBottomAmount;
    public final Rect mClipBounds;
    public boolean mClipToActualHeight;
    public int mClipTopAmount;
    public ExpandableNotificationRow mContainingNotification;
    public boolean mContentAnimating;
    public int mContentHeight;
    public int mContentHeightAtAnimationStart;
    public View mContractedChild;
    public NotificationViewWrapper mContractedWrapper;
    public InflatedSmartReplyState mCurrentSmartReplyState;
    public final AnonymousClass1 mEnableAnimationPredrawListener;
    public ExpandableNotificationRow.AnonymousClass1 mExpandClickListener;
    public boolean mExpandable;
    public View mExpandedChild;
    public InflatedSmartReplyViewHolder mExpandedInflatedSmartReplies;
    public RemoteInputView mExpandedRemoteInput;
    public RemoteInputViewController mExpandedRemoteInputController;
    public SmartReplyView mExpandedSmartReplyView;
    public NotificationRemoteInputManager$$ExternalSyntheticLambda0 mExpandedVisibleListener;
    public NotificationViewWrapper mExpandedWrapper;
    public boolean mFocusOnVisibilityChange;
    public boolean mForceSelectNextLayout;
    public boolean mHeadsUpAnimatingAway;
    public View mHeadsUpChild;
    public int mHeadsUpHeight;
    public InflatedSmartReplyViewHolder mHeadsUpInflatedSmartReplies;
    public SmartReplyView mHeadsUpSmartReplyView;
    public NotificationViewWrapper mHeadsUpWrapper;
    public boolean mIsChildInGroup;
    public boolean mIsContentExpandable;
    public boolean mIsContractedHeaderContainAtMark;
    public boolean mIsExpandedHeaderContainAtMark;
    public boolean mIsHUNCompact;
    public boolean mIsHeadsUp;
    public boolean mIsLockedAlpha;
    public boolean mLegacy;
    public int mMaxChildSizeOnMeasure;
    public int mMaxSizeOnMeasure;
    public SecMediaHost mMediaHost;
    public int mMinContractedHeight;
    public NotificationEntry mNotificationEntry;
    public int mNotificationMaxHeight;
    public final ArrayMap mOnContentViewInactiveListeners;
    public PeopleNotificationIdentifier mPeopleIdentifier;
    public PendingIntent mPreviousExpandedRemoteInputIntent;
    public RemoteInputController mRemoteInputController;
    public RemoteInputViewSubcomponent.Factory mRemoteInputSubcomponentFactory;
    public boolean mRemoteInputVisible;
    public NotificationViewWrapper mShownWrapper;
    protected HybridNotificationView mSingleLineView;
    public int mSingleLineWidthIndention;
    public int mSmallHeight;
    public SmartReplyConstants mSmartReplyConstants;
    public SmartReplyController mSmartReplyController;
    public IStatusBarService mStatusBarService;
    public int mTransformationStartVisibleType;
    public UiEventLogger mUiEventLogger;
    public int mUnrestrictedContentHeight;
    public boolean mUserExpanding;
    public int mVisibleType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RemoteInputViewData {
        public RemoteInputViewController mController;
        public RemoteInputView mView;

        public /* synthetic */ RemoteInputViewData(int i) {
            this();
        }

        private RemoteInputViewData() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.row.NotificationContentView$1] */
    public NotificationContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClipBounds = new Rect();
        this.mShownWrapper = null;
        this.mVisibleType = -1;
        this.mOnContentViewInactiveListeners = new ArrayMap();
        this.mEnableAnimationPredrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                NotificationContentView.this.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationContentView.this.mAnimate = true;
                    }
                });
                NotificationContentView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        this.mClipToActualHeight = true;
        this.mAnimationStartVisibleType = -1;
        this.mForceSelectNextLayout = true;
        this.mContentHeightAtAnimationStart = -1;
        this.mIsContractedHeaderContainAtMark = false;
        this.mIsExpandedHeaderContainAtMark = false;
        new HybridGroupManager(getContext());
        this.mMinContractedHeight = getResources().getDimensionPixelSize(R.dimen.min_notification_layout_height);
        int i = AsyncHybridViewInflation.$r8$clinit;
        getResources().getDimensionPixelSize(R.dimen.conversation_single_line_face_pile_size);
    }

    public static void applyExternalSmartReplyState(View view, InflatedSmartReplyState inflatedSmartReplyState) {
        List list;
        boolean z = inflatedSmartReplyState != null && inflatedSmartReplyState.hasPhishingAction;
        View findViewById = view.findViewById(android.R.id.sequentially);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 0 : 8);
        }
        if (inflatedSmartReplyState != null) {
            InflatedSmartReplyState.SuppressedActions suppressedActions = inflatedSmartReplyState.suppressedActions;
            if (suppressedActions == null || (list = suppressedActions.suppressedActionIndices) == null) {
                list = EmptyList.INSTANCE;
            }
        } else {
            list = Collections.EMPTY_LIST;
        }
        ViewGroup viewGroup = (ViewGroup) view.findViewById(android.R.id.animation);
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                Object tag = childAt.getTag(android.R.id.rectangle);
                childAt.setVisibility(((tag instanceof Integer) && list.contains(tag)) ? 8 : 0);
            }
        }
    }

    public static SmartReplyView applySmartReplyView(View view, InflatedSmartReplyState inflatedSmartReplyState, NotificationEntry notificationEntry, InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder, boolean z) {
        SmartReplyView smartReplyView;
        View findViewById;
        View findViewById2 = view.findViewById(16909845);
        SmartReplyView smartReplyView2 = null;
        if (findViewById2 instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) findViewById2;
            if (!SmartReplyStateInflaterKt.shouldShowSmartReplyView(notificationEntry, inflatedSmartReplyState)) {
                linearLayout.setVisibility(8);
                return null;
            }
            if (!z || ((findViewById = view.findViewById(android.R.id.overlay_display_window_title)) != null && !(findViewById instanceof MessagingImageMessage))) {
                int childCount = linearLayout.getChildCount();
                int i = 0;
                while (i < childCount) {
                    View childAt = linearLayout.getChildAt(i);
                    if (childAt.getId() == R.id.smart_reply_view && (childAt instanceof SmartReplyView)) {
                        break;
                    }
                    i++;
                }
                if (i < childCount) {
                    linearLayout.removeViewAt(i);
                }
                if (inflatedSmartReplyViewHolder != null && (smartReplyView = inflatedSmartReplyViewHolder.smartReplyView) != null) {
                    linearLayout.addView(smartReplyView, i);
                    smartReplyView2 = smartReplyView;
                }
                if (smartReplyView2 != null) {
                    smartReplyView2.mSmartReplyContainer = linearLayout;
                    smartReplyView2.removeAllViews();
                    for (Button button : inflatedSmartReplyViewHolder.smartSuggestionButtons) {
                        smartReplyView2.addView(button);
                        smartReplyView2.setButtonColors(button);
                    }
                    smartReplyView2.mCandidateButtonQueueForSqueezing = new PriorityQueue(Math.max(smartReplyView2.getChildCount(), 1), SmartReplyView.DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
                    int i2 = notificationEntry.row.mCurrentBackgroundTint;
                    notificationEntry.mSbn.getNotification().isColorized();
                    linearLayout.setVisibility(0);
                }
                return smartReplyView2;
            }
        }
        return null;
    }

    public static void dumpChildViewDimensions(IndentingPrintWriter indentingPrintWriter, View view, String str) {
        indentingPrintWriter.print(str.concat(" "));
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriter, new NotificationContentView$$ExternalSyntheticLambda3(indentingPrintWriter, view));
    }

    public static void updateAllSingleLineViews() {
        try {
            Trace.beginSection("NotifContentView#updateSingleLineView");
            int i = AsyncHybridViewInflation.$r8$clinit;
        } finally {
            Trace.endSection();
        }
    }

    public final void applyBubbleAction(View view, NotificationEntry notificationEntry) {
        if (view == null || this.mContainingNotification == null || this.mPeopleIdentifier == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(android.R.id.clamp);
        View findViewById = view.findViewById(android.R.id.animator);
        if (imageView == null || findViewById == null) {
            return;
        }
        if (notificationEntry.mBubbleMetadata == null || !BubblesManager.areBubblesEnabled(((FrameLayout) this).mContext, notificationEntry.mSbn.getUser()) || ((PeopleNotificationIdentifierImpl) this.mPeopleIdentifier).getPeopleNotificationType(notificationEntry) < 2 || !ActivityTaskManager.supportsMultiWindow(((FrameLayout) this).mContext) || MultiWindowManager.getInstance().isMultiWindowBlockListApp(notificationEntry.mSbn.getPackageName())) {
            imageView.setVisibility(8);
            return;
        }
        int i = NotificationBundleUi.$r8$clinit;
        boolean isBubble = notificationEntry.isBubble();
        ((FrameLayout) this).mContext.getDrawable(isBubble ? R.drawable.bubble_ic_stop_bubble : R.drawable.bubble_ic_create_bubble);
        Drawable drawable = ((FrameLayout) this).mContext.getDrawable(notificationEntry.isBubble() ? R.drawable.ic_bubble_off : R.drawable.ic_bubble_on);
        String string = ((FrameLayout) this).mContext.getResources().getString(isBubble ? R.string.notification_conversation_unbubble : R.string.notification_conversation_bubble);
        imageView.setTooltipText(string);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (!((ArrayList) expandableNotificationRow.mBubbleButtonViews).contains(imageView)) {
            ((ArrayList) expandableNotificationRow.mBubbleButtonViews).add(imageView);
        }
        imageView.setContentDescription(string);
        imageView.setImageDrawable(drawable);
        imageView.setOnClickListener(this.mContainingNotification.mBubbleClickListener);
        imageView.setVisibility(0);
        findViewById.setVisibility(0);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(android.R.id.remoteMessaging);
        if (viewGroup != null) {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                if (marginLayoutParams.bottomMargin > 0) {
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, 0);
                }
            }
        }
    }

    public final RemoteInputViewData applyRemoteInput(View view, NotificationEntry notificationEntry, boolean z, PendingIntent pendingIntent, NotificationViewWrapper notificationViewWrapper) {
        Intent intent;
        RemoteInput[] remoteInputs;
        RemoteInput remoteInput;
        RemoteInputViewData remoteInputViewData = new RemoteInputViewData(0);
        View findViewById = view.findViewById(android.R.id.animator);
        if (findViewById instanceof FrameLayout) {
            Object obj = RemoteInputView.VIEW_TAG;
            RemoteInputView remoteInputView = (RemoteInputView) view.findViewWithTag(obj);
            remoteInputViewData.mView = remoteInputView;
            if (remoteInputView != null) {
                remoteInputView.onNotificationUpdateOrReset();
                remoteInputViewData.mController = remoteInputViewData.mView.mViewController;
            }
            if (remoteInputViewData.mView == null && z) {
                FrameLayout frameLayout = (FrameLayout) findViewById;
                Context context = ((FrameLayout) this).mContext;
                RemoteInputController remoteInputController = this.mRemoteInputController;
                RemoteInputView remoteInputView2 = (RemoteInputView) LayoutInflater.from(context).inflate(R.layout.sec_remote_input, (ViewGroup) frameLayout, false);
                remoteInputView2.mController = remoteInputController;
                remoteInputView2.mEntry = notificationEntry;
                UserHandle user = notificationEntry.mSbn.getUser();
                if (UserHandle.ALL.equals(user)) {
                    user = UserHandle.of(ActivityManager.getCurrentUser());
                }
                RemoteInputView.RemoteEditText remoteEditText = remoteInputView2.mEditText;
                remoteEditText.mUser = user;
                remoteEditText.setTextOperationUser(user);
                remoteInputView2.setTag(obj);
                remoteInputView2.setVisibility(8);
                frameLayout.addView(remoteInputView2, new FrameLayout.LayoutParams(-1, -1));
                remoteInputViewData.mView = remoteInputView2;
                RemoteInputViewControllerImpl remoteInputViewControllerImpl = ((DaggerReferenceGlobalRootComponent.RemoteInputViewSubcomponentImpl) this.mRemoteInputSubcomponentFactory.create(remoteInputView2, this.mRemoteInputController)).remoteInputViewControllerImpl();
                remoteInputViewData.mController = remoteInputViewControllerImpl;
                remoteInputViewData.mView.mViewController = remoteInputViewControllerImpl;
            }
            if (z) {
                RemoteInputView remoteInputView3 = remoteInputViewData.mView;
                remoteInputView3.mWrapper = notificationViewWrapper;
                remoteInputView3.mOnVisibilityChangedListener = new NotificationContentView$$ExternalSyntheticLambda5(this);
                if (pendingIntent != null || remoteInputView3.isActive()) {
                    Notification.Action[] actionArr = notificationEntry.mSbn.getNotification().actions;
                    if (pendingIntent != null) {
                        ((RemoteInputViewControllerImpl) remoteInputViewData.mController).pendingIntent = pendingIntent;
                    }
                    RemoteInputViewControllerImpl remoteInputViewControllerImpl2 = (RemoteInputViewControllerImpl) remoteInputViewData.mController;
                    if (actionArr == null) {
                        remoteInputViewControllerImpl2.getClass();
                    } else {
                        PendingIntent pendingIntent2 = remoteInputViewControllerImpl2.pendingIntent;
                        if (pendingIntent2 != null && (intent = pendingIntent2.getIntent()) != null) {
                            ArrayIterator arrayIterator = new ArrayIterator(actionArr);
                            while (arrayIterator.hasNext()) {
                                Notification.Action action = (Notification.Action) arrayIterator.next();
                                PendingIntent pendingIntent3 = action.actionIntent;
                                if (pendingIntent3 != null && (remoteInputs = action.getRemoteInputs()) != null && intent.filterEquals(pendingIntent3.getIntent())) {
                                    int length = remoteInputs.length;
                                    int i = 0;
                                    while (true) {
                                        if (i >= length) {
                                            remoteInput = null;
                                            break;
                                        }
                                        remoteInput = remoteInputs[i];
                                        if (remoteInput.getAllowFreeFormInput()) {
                                            break;
                                        }
                                        i++;
                                    }
                                    if (remoteInput != null) {
                                        remoteInputViewControllerImpl2.pendingIntent = pendingIntent3;
                                        remoteInputViewControllerImpl2.setRemoteInput(remoteInput);
                                        remoteInputViewControllerImpl2.remoteInputs = remoteInputs;
                                        remoteInputViewControllerImpl2.entry.editedSuggestionInfo = null;
                                        if (!((RemoteInputViewControllerImpl) remoteInputViewData.mController).view.isActive()) {
                                            ((RemoteInputViewControllerImpl) remoteInputViewData.mController).view.focus();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (((RemoteInputViewControllerImpl) remoteInputViewData.mController).view.isActive()) {
                        RemoteInputView.RemoteEditText remoteEditText2 = ((RemoteInputViewControllerImpl) remoteInputViewData.mController).view.mEditText;
                        int i2 = RemoteInputView.RemoteEditText.$r8$clinit;
                        remoteEditText2.defocusIfNeeded(false);
                    }
                }
            }
            if (remoteInputViewData.mView != null) {
                remoteInputViewData.mView.setBackgroundTintColor(notificationEntry.row.mCurrentBackgroundTint, notificationEntry.mSbn.getNotification().color, notificationEntry.mSbn.getNotification().isColorized());
            }
        }
        return remoteInputViewData;
    }

    public final void applySnoozeAction(View view) {
        if (view == null || this.mContainingNotification == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(16909854);
        View findViewById = view.findViewById(android.R.id.animator);
        if (imageView == null || findViewById == null) {
            return;
        }
        boolean isEnabled = imageView.isEnabled();
        this.mContainingNotification.getClass();
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableSnooze() || !isEnabled) {
            imageView.setVisibility(8);
            return;
        }
        imageView.setImageDrawable(((FrameLayout) this).mContext.getDrawable(R.drawable.quickpanel_ic_snooze));
        imageView.setTooltipText(getContext().getString(R.string.notification_menu_snooze_description));
        this.mContainingNotification.mSnoozeButtonView = imageView;
        final NotificationMenuRow.NotificationMenuItem createNotificationMenuItem = SecGutInflater.createNotificationMenuItem(((FrameLayout) this).mContext, R.string.notification_menu_snooze_description, R.layout.sec_notification_snooze);
        final ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        expandableNotificationRow.getClass();
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                NotificationMenuRow.NotificationMenuItem notificationMenuItem = createNotificationMenuItem;
                expandableNotificationRow2.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
                expandableNotificationRow2.mNotificationGutsManager.openGuts(expandableNotificationRow2, 0, 0, notificationMenuItem);
                expandableNotificationRow2.mIsSnoozed = true;
            }
        });
        imageView.setContentDescription(((FrameLayout) this).mContext.getResources().getString(R.string.notification_menu_snooze_description));
        imageView.setVisibility(0);
        findViewById.setVisibility(0);
        View findViewById2 = view.findViewById(android.R.id.remoteMessaging);
        if (findViewById2 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById2.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.getMarginStart(), marginLayoutParams.topMargin, marginLayoutParams.getMarginEnd(), 0);
        }
    }

    public final int calculateVisibleType() {
        if (!this.mUserExpanding) {
            int intrinsicHeight = this.mContainingNotification.getIntrinsicHeight();
            int i = this.mContentHeight;
            if (intrinsicHeight != 0) {
                i = Math.min(i, intrinsicHeight);
            }
            return getVisualTypeForHeight(i);
        }
        int maxContentHeight = (!this.mIsChildInGroup || this.mContainingNotification.isGroupExpanded$1() || this.mContainingNotification.isExpanded(true)) ? this.mContainingNotification.getMaxContentHeight() : this.mContainingNotification.getShowingLayout().getMinHeight(true);
        if (maxContentHeight == 0) {
            maxContentHeight = this.mContentHeight;
        }
        int visualTypeForHeight = getVisualTypeForHeight(maxContentHeight);
        boolean z = this.mIsChildInGroup && !this.mContainingNotification.isGroupExpanded$1();
        boolean z2 = this.mSingleLineView != null;
        if (z && !z2) {
            Log.wtf("NotificationContentView", "calculateVisibleType: SingleLineView is not available!");
        }
        int visualTypeForHeight2 = getVisualTypeForHeight(this.mContainingNotification.getCollapsedHeight());
        return this.mTransformationStartVisibleType == visualTypeForHeight2 ? visualTypeForHeight : visualTypeForHeight2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("Drawing view failed: ", e, "NotificationContentView");
            try {
                setVisibility(8);
                StatusBarNotification statusBarNotification = this.mNotificationEntry.mSbn;
                IStatusBarService iStatusBarService = this.mStatusBarService;
                if (iStatusBarService != null) {
                    iStatusBarService.onNotificationError(statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), e.getMessage(), statusBarNotification.getUser().getIdentifier());
                }
            } catch (RemoteException e2) {
                Log.e("NotificationContentView", "cancelNotification failed: " + e2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        RemoteInputView remoteInputView = getViewForVisibleType(this.mVisibleType) == this.mExpandedChild ? this.mExpandedRemoteInput : null;
        if (remoteInputView != null && remoteInputView.getVisibility() == 0) {
            int height = this.mUnrestrictedContentHeight - remoteInputView.getHeight();
            if (y <= this.mUnrestrictedContentHeight && y >= height) {
                motionEvent.offsetLocation(0.0f, -height);
                return remoteInputView.dispatchTouchEvent(motionEvent);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void fireExpandedVisibleListenerIfVisible() {
        if (this.mExpandedVisibleListener == null || this.mExpandedChild == null || !isShown() || this.mExpandedChild.getVisibility() != 0) {
            return;
        }
        NotificationRemoteInputManager$$ExternalSyntheticLambda0 notificationRemoteInputManager$$ExternalSyntheticLambda0 = this.mExpandedVisibleListener;
        this.mExpandedVisibleListener = null;
        notificationRemoteInputManager$$ExternalSyntheticLambda0.run();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        ViewParent viewParent = ((FrameLayout) this).mParent;
        if (viewParent != null) {
            return viewParent.focusSearch(view, i);
        }
        Log.wtf("NotificationContentView", "NotificationContentView doesn't have parent");
        return null;
    }

    public final void forceUpdateVisibility(int i, View view, TransformableView transformableView) {
        if (view == null) {
            return;
        }
        if (this.mVisibleType == i || this.mTransformationStartVisibleType == i) {
            transformableView.setVisible(true);
        } else {
            view.setVisibility(4);
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("===============================");
        arrayList.add("Showing NotificationContentView");
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        Integer valueOf = Integer.valueOf(this.mVisibleType);
        panelScreenShotLogger.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "getVisibleType", valueOf);
        PanelScreenShotLogger.addLogItem(arrayList, "getViewHeight(HeadsUp)", Integer.valueOf(getViewHeight(2, false)));
        PanelScreenShotLogger.addLogItem(arrayList, "getViewHeight(contracted)", Integer.valueOf(getViewHeight(0, false)));
        PanelScreenShotLogger.addLogItem(arrayList, "getViewHeight(expanded)", Integer.valueOf(getViewHeight(1, false)));
        PanelScreenShotLogger.addLogItem(arrayList, "getViewHeight(singleline)", Integer.valueOf(getViewHeight(3, false)));
        PanelScreenShotLogger.addLogItem(arrayList, "mSmallHeight", Integer.valueOf(this.mSmallHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mNotificationMaxHeight", Integer.valueOf(this.mNotificationMaxHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mMaxChildSizeOnMeasure", Integer.valueOf(this.mMaxChildSizeOnMeasure));
        PanelScreenShotLogger.addLogItem(arrayList, "mMaxSizeOnMeasure", Integer.valueOf(this.mMaxSizeOnMeasure));
        PanelScreenShotLogger.addLogItem(arrayList, "mIsLockedAlpha", Boolean.valueOf(this.mIsLockedAlpha));
        if (this.mExpandedSmartReplyView != null) {
            arrayList.add("mExpandedSmartReplyView : ");
            arrayList.addAll(this.mExpandedSmartReplyView.gatherState());
        }
        if (this.mHeadsUpSmartReplyView != null) {
            arrayList.add("mHeadsUpSmartReplyView : ");
            arrayList.addAll(this.mHeadsUpSmartReplyView.gatherState());
        }
        arrayList.add("===============================");
        arrayList.add("NotiCinema");
        View view = this.mContractedChild;
        if (view != null && (view instanceof ViewGroup)) {
            arrayList.add("====== mContractedChild ====== ");
            NotiCinemaLogger notiCinemaLogger = (NotiCinemaLogger) Dependency.sDependency.getDependencyInner(NotiCinemaLogger.class);
            ViewGroup viewGroup = (ViewGroup) this.mContractedChild;
            notiCinemaLogger.mTmpLog.clear();
            notiCinemaLogger.visitLayoutTreeToAssembleLogLine(viewGroup, 0);
            arrayList.addAll(notiCinemaLogger.mTmpLog);
        }
        View view2 = this.mExpandedChild;
        if (view2 != null && (view2 instanceof ViewGroup)) {
            arrayList.add("====== mExpandedChild ====== ");
            NotiCinemaLogger notiCinemaLogger2 = (NotiCinemaLogger) Dependency.sDependency.getDependencyInner(NotiCinemaLogger.class);
            ViewGroup viewGroup2 = (ViewGroup) this.mExpandedChild;
            notiCinemaLogger2.mTmpLog.clear();
            notiCinemaLogger2.visitLayoutTreeToAssembleLogLine(viewGroup2, 0);
            arrayList.addAll(notiCinemaLogger2.mTmpLog);
        }
        View view3 = this.mHeadsUpChild;
        if (view3 != null && (view3 instanceof ViewGroup)) {
            arrayList.add("====== mHeadsUpChild ====== ");
            NotiCinemaLogger notiCinemaLogger3 = (NotiCinemaLogger) Dependency.sDependency.getDependencyInner(NotiCinemaLogger.class);
            ViewGroup viewGroup3 = (ViewGroup) this.mHeadsUpChild;
            notiCinemaLogger3.mTmpLog.clear();
            notiCinemaLogger3.visitLayoutTreeToAssembleLogLine(viewGroup3, 0);
            arrayList.addAll(notiCinemaLogger3.mTmpLog);
        }
        if (this.mSingleLineView != null) {
            arrayList.add("====== mSingleLineView ====== ");
            NotiCinemaLogger notiCinemaLogger4 = (NotiCinemaLogger) Dependency.sDependency.getDependencyInner(NotiCinemaLogger.class);
            HybridNotificationView hybridNotificationView = this.mSingleLineView;
            notiCinemaLogger4.mTmpLog.clear();
            notiCinemaLogger4.visitLayoutTreeToAssembleLogLine(hybridNotificationView, 0);
            arrayList.addAll(notiCinemaLogger4.mTmpLog);
        }
        arrayList.add("===============================");
        return arrayList;
    }

    public final View[] getAllViews() {
        return new View[]{this.mContractedChild, this.mHeadsUpChild, this.mExpandedChild, this.mSingleLineView};
    }

    public NotificationViewWrapper getContractedWrapper() {
        return this.mContractedWrapper;
    }

    public NotificationViewWrapper getExpandedWrapper() {
        return this.mExpandedWrapper;
    }

    public final int getExtraRemoteInputHeight(RemoteInputView remoteInputView) {
        if (remoteInputView == null) {
            return 0;
        }
        if (!remoteInputView.isActive()) {
            if (remoteInputView.getVisibility() != 0) {
                return 0;
            }
            RemoteInputController remoteInputController = remoteInputView.mController;
            String str = remoteInputView.mEntry.mKey;
            if (remoteInputController.mSpinning.get(str) != remoteInputView.mToken) {
                return 0;
            }
        }
        return getResources().getDimensionPixelSize(android.R.dimen.toast_elevation);
    }

    public final int getHeadsUpHeight(boolean z) {
        int i;
        if (this.mHeadsUpChild != null) {
            i = 2;
        } else {
            if (this.mContractedChild == null) {
                return getMinHeight(true);
            }
            i = 0;
        }
        return getExtraRemoteInputHeight(this.mExpandedRemoteInput) + getViewHeight(i, z);
    }

    public NotificationViewWrapper getHeadsUpWrapper() {
        return this.mHeadsUpWrapper;
    }

    public final int getMaxHeight() {
        if (this.mExpandedChild != null) {
            return getExtraRemoteInputHeight(this.mExpandedRemoteInput) + getViewHeight(1, false);
        }
        return (this.mIsHeadsUp && this.mHeadsUpChild != null && this.mContainingNotification.canShowHeadsUp$1()) ? getViewHeight(2, false) : this.mContractedChild != null ? getViewHeight(0, false) : this.mNotificationMaxHeight;
    }

    public final int getMinContentHeightHint() {
        int i;
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(android.R.dimen.text_view_start_margin);
        if (this.mIsChildInGroup && isVisibleOrTransitioning(3)) {
            return dimensionPixelSize;
        }
        if (this.mHeadsUpChild != null && this.mExpandedChild != null) {
            int i2 = this.mTransformationStartVisibleType;
            boolean z = ((i2 == 2 || this.mAnimationStartVisibleType == 2) && this.mVisibleType == 1) || ((i2 == 1 || this.mAnimationStartVisibleType == 1) && this.mVisibleType == 2);
            boolean z2 = !isVisibleOrTransitioning(0) && (this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mContainingNotification.canShowHeadsUp$1();
            if (z || z2) {
                return Math.min(getViewHeight(2, false), getViewHeight(1, false));
            }
        }
        if (this.mVisibleType == 1 && (i = this.mContentHeightAtAnimationStart) != -1 && this.mExpandedChild != null) {
            return Math.min(i, getViewHeight(1, false));
        }
        int viewHeight = (this.mHeadsUpChild == null || !isVisibleOrTransitioning(2)) ? this.mExpandedChild != null ? getViewHeight(1, false) : this.mContractedChild != null ? dimensionPixelSize + getViewHeight(0, false) : getMinHeight(true) : getViewHeight(2, false);
        return (this.mExpandedChild == null || !isVisibleOrTransitioning(1)) ? viewHeight : Math.min(viewHeight, getViewHeight(1, false));
    }

    public final int getMinHeight(boolean z) {
        return (this.mContainingNotification.mEntry.isOngoingActivity() && this.mContainingNotification.mEntry.isPromotedState() && !this.mContainingNotification.shouldShowPublic()) ? this.mExpandedChild != null ? getViewHeight(1, false) : this.mMinContractedHeight : this.mContractedChild != null ? getViewHeight(0, false) : this.mMinContractedHeight;
    }

    public final HybridNotificationView getSingleLineView() {
        return this.mSingleLineView;
    }

    public final TransformableView getTransformableViewForVisibleType(int i) {
        return i != 1 ? i != 2 ? i != 3 ? this.mContractedWrapper : this.mSingleLineView : this.mHeadsUpWrapper : this.mExpandedWrapper;
    }

    public final View getViewForVisibleType(int i) {
        return i != 1 ? i != 2 ? i != 3 ? this.mContractedChild : this.mSingleLineView : this.mHeadsUpChild : this.mExpandedChild;
    }

    public final int getViewHeight(int i, boolean z) {
        View viewForVisibleType = getViewForVisibleType(i);
        int height = viewForVisibleType == null ? 0 : viewForVisibleType.getHeight();
        NotificationViewWrapper notificationViewWrapper = viewForVisibleType == this.mContractedChild ? this.mContractedWrapper : viewForVisibleType == this.mExpandedChild ? this.mExpandedWrapper : viewForVisibleType == this.mHeadsUpChild ? this.mHeadsUpWrapper : null;
        return notificationViewWrapper != null ? notificationViewWrapper.getHeaderTranslation(z) + height : height;
    }

    public final NotificationViewWrapper getVisibleWrapper(int i) {
        if (i == 0) {
            return this.mContractedWrapper;
        }
        if (i == 1) {
            return this.mExpandedWrapper;
        }
        if (i != 2) {
            return null;
        }
        return this.mHeadsUpWrapper;
    }

    public final int getVisualTypeForHeight(float f) {
        boolean z = this.mExpandedChild == null;
        if (z || f != getViewHeight(1, false)) {
            boolean z2 = this.mIsChildInGroup && !this.mContainingNotification.isGroupExpanded$1();
            boolean z3 = this.mSingleLineView != null;
            if (this.mUserExpanding || !z2 || !z3) {
                if ((this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mHeadsUpChild != null && this.mContainingNotification.canShowHeadsUp$1()) {
                    if (f <= getViewHeight(2, false) || z) {
                        return 2;
                    }
                } else if (z || !this.mContainingNotification.isExpanded(false)) {
                    if (!z && (this.mContractedChild == null || f > getViewHeight(0, false) || (this.mIsChildInGroup && !this.mContainingNotification.isGroupExpanded$1() && this.mContainingNotification.isExpanded(true)))) {
                        if (z) {
                            return -1;
                        }
                    }
                }
            }
            return 0;
        }
        return 1;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isAnimatingVisibleType() {
        return this.mAnimationStartVisibleType != -1;
    }

    public final boolean isVisibleOrTransitioning(int i) {
        return this.mVisibleType == i || this.mTransformationStartVisibleType == i || this.mAnimationStartVisibleType == i;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void notifySubtreeAccessibilityStateChanged(View view, View view2, int i) {
        if (isAnimatingVisibleType()) {
            return;
        }
        super.notifySubtreeAccessibilityStateChanged(view, view2, i);
    }

    public final void notifySubtreeForAccessibilityContentChange() {
        ViewParent viewParent = ((FrameLayout) this).mParent;
        if (viewParent != null) {
            viewParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateVisibility$1();
    }

    public final void onChildVisibilityChanged(View view, int i, int i2) {
        Runnable runnable;
        super.onChildVisibilityChanged(view, i, i2);
        if ((view != null && isShown() && (view.getVisibility() == 0 || getViewForVisibleType(this.mVisibleType) == view)) || (runnable = (Runnable) this.mOnContentViewInactiveListeners.remove(view)) == null) {
            return;
        }
        runnable.run();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        ImageView imageView;
        int i;
        super.onConfigurationChanged(configuration);
        if (!this.mContainingNotification.mEntry.isOngoingActivity() || (imageView = (ImageView) findViewWithTag("tintedProgress")) == null) {
            return;
        }
        NotificationEntry notificationEntry = this.mContainingNotification.mEntry;
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String str = notificationEntry.mKey;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
        if (ongoingActivityDataByKey == null || (i = ongoingActivityDataByKey.mProgress) <= -1 || ongoingActivityDataByKey.mProgressSegments.length <= 0) {
            return;
        }
        float f = i;
        int i2 = ongoingActivityDataByKey.mProgressMax;
        if (i2 > 0) {
            f = (f * 100.0f) / i2;
        }
        imageView.setImageBitmap(new OngoingSeekBarCreator(getContext(), ongoingActivityDataByKey.mProgressSegments, f, ongoingActivityDataByKey.mProgressSegmentIcon, ongoingActivityDataByKey.mProgressColor).makeImage(OngoingType.ENR));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view = this.mExpandedChild;
        int height = view != null ? view.getHeight() : 0;
        super.onLayout(z, i, i2, i3, i4);
        if (height != 0 && this.mExpandedChild.getHeight() != height) {
            this.mContentHeightAtAnimationStart = height;
        }
        updateClipping();
        invalidateOutline();
        selectLayout(false, this.mForceSelectNextLayout);
        this.mForceSelectNextLayout = false;
        updateExpandButtonsDuringLayout(this.mExpandable, true);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        boolean z2;
        int mode = View.MeasureSpec.getMode(i2);
        boolean z3 = false;
        boolean z4 = mode == 1073741824;
        boolean z5 = mode == Integer.MIN_VALUE;
        int size = View.MeasureSpec.getSize(i);
        int size2 = (z4 || z5) ? View.MeasureSpec.getSize(i2) : 1073741823;
        if (this.mExpandedChild != null) {
            int i4 = this.mNotificationMaxHeight;
            if (this.mContainingNotification.mPinnedStatus.isPinned()) {
                Display display = getContext().getDisplay();
                if (display.getRotation() == 1 || display.getRotation() == 3) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    display.getRealMetrics(displayMetrics);
                    i4 = displayMetrics.heightPixels - (((NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class)).getShelfHeight() + getResources().getDimensionPixelSize(R.dimen.heads_up_status_bar_padding));
                }
            }
            SmartReplyView smartReplyView = this.mExpandedSmartReplyView;
            if (smartReplyView != null) {
                i4 += smartReplyView.mHeightUpperLimit;
            }
            int extraMeasureHeight = this.mExpandedWrapper.getExtraMeasureHeight() + i4;
            int i5 = this.mExpandedChild.getLayoutParams().height;
            if (i5 >= 0) {
                extraMeasureHeight = Math.min(extraMeasureHeight, i5);
                z2 = true;
            } else {
                z2 = false;
            }
            NotificationEntry notificationEntry = this.mContainingNotification.mEntry;
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            String str = notificationEntry.mKey;
            ongoingActivityDataHelper.getClass();
            OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
            if (notificationEntry.isPromotedState() && ongoingActivityDataByKey != null && ongoingActivityDataByKey.mIsMediaOngoingData) {
                SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                Context context = getContext();
                secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
                SecQSPanelResourceCommon.Companion.getClass();
                extraMeasureHeight = Math.min(extraMeasureHeight, SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_media_player_height_expanded, context));
                z2 = true;
            }
            measureChildWithMargins(this.mExpandedChild, i, 0, View.MeasureSpec.makeMeasureSpec(extraMeasureHeight, z2 ? 1073741824 : Integer.MIN_VALUE), 0);
            i3 = Math.max(0, this.mExpandedChild.getMeasuredHeight());
        } else {
            i3 = 0;
        }
        View view = this.mContractedChild;
        if (view != null) {
            int i6 = this.mSmallHeight;
            int i7 = view.getLayoutParams().height;
            if (i7 >= 0) {
                i6 = Math.min(i6, i7);
                z = true;
            } else {
                z = false;
            }
            measureChildWithMargins(this.mContractedChild, i, 0, ((this.mBeforeN && (this.mContractedWrapper instanceof NotificationCustomViewWrapper)) || z) ? View.MeasureSpec.makeMeasureSpec(i6, 1073741824) : View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE), 0);
            int measuredHeight = this.mContractedChild.getMeasuredHeight();
            int i8 = this.mMinContractedHeight;
            if (measuredHeight < i8) {
                measureChildWithMargins(this.mContractedChild, i, 0, View.MeasureSpec.makeMeasureSpec(i8, 1073741824), 0);
            }
            i3 = Math.max(i3, measuredHeight);
            if (this.mExpandedChild != null && this.mContractedChild.getMeasuredHeight() > this.mExpandedChild.getMeasuredHeight()) {
                measureChildWithMargins(this.mExpandedChild, i, 0, View.MeasureSpec.makeMeasureSpec(this.mContractedChild.getMeasuredHeight(), 1073741824), 0);
            }
        }
        if (this.mHeadsUpChild != null) {
            int i9 = this.mHeadsUpHeight;
            SmartReplyView smartReplyView2 = this.mHeadsUpSmartReplyView;
            if (smartReplyView2 != null) {
                i9 += smartReplyView2.mHeightUpperLimit;
            }
            int extraMeasureHeight2 = this.mHeadsUpWrapper.getExtraMeasureHeight() + i9;
            int i10 = this.mHeadsUpChild.getLayoutParams().height;
            if (i10 >= 0) {
                extraMeasureHeight2 = Math.min(extraMeasureHeight2, i10);
                z3 = true;
            }
            measureChildWithMargins(this.mHeadsUpChild, i, 0, View.MeasureSpec.makeMeasureSpec(extraMeasureHeight2, z3 ? 1073741824 : Integer.MIN_VALUE), 0);
            i3 = Math.max(i3, this.mHeadsUpChild.getMeasuredHeight());
        }
        if (this.mSingleLineView != null) {
            this.mSingleLineView.measure((this.mSingleLineWidthIndention == 0 || View.MeasureSpec.getMode(i) == 0) ? i : View.MeasureSpec.makeMeasureSpec(this.mSingleLineView.getPaddingEnd() + (size - this.mSingleLineWidthIndention), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mNotificationMaxHeight, Integer.MIN_VALUE));
            i3 = Math.max(i3, this.mSingleLineView.getMeasuredHeight());
        }
        int min = Math.min(i3, size2);
        this.mMaxChildSizeOnMeasure = i3;
        this.mMaxSizeOnMeasure = size2;
        setMeasuredDimension(size, min);
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        view.setTag(R.id.row_tag_for_content_view, this.mContainingNotification);
    }

    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        updateShownWrapper(this.mVisibleType);
        if (z) {
            fireExpandedVisibleListenerIfVisible();
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        updateVisibility$1();
        if (i == 0 || this.mOnContentViewInactiveListeners.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOnContentViewInactiveListeners.values());
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Runnable) obj).run();
        }
        this.mOnContentViewInactiveListeners.clear();
    }

    public final void performWhenContentInactive(int i, Runnable runnable) {
        View viewForVisibleType;
        View viewForVisibleType2 = getViewForVisibleType(i);
        if (viewForVisibleType2 == null || (viewForVisibleType = getViewForVisibleType(i)) == null || !isShown() || !(viewForVisibleType.getVisibility() == 0 || getViewForVisibleType(this.mVisibleType) == viewForVisibleType)) {
            runnable.run();
        } else {
            this.mOnContentViewInactiveListeners.put(viewForVisibleType2, runnable);
        }
    }

    public final boolean pointInView(float f, float f2, float f3) {
        return f >= (-f3) && f2 >= ((float) this.mClipTopAmount) - f3 && f < ((float) (((FrameLayout) this).mRight - ((FrameLayout) this).mLeft)) + f3 && f2 < ((float) this.mUnrestrictedContentHeight) + f3;
    }

    public final void removeContentInactiveRunnable(int i) {
        View viewForVisibleType = getViewForVisibleType(i);
        if (viewForVisibleType == null) {
            return;
        }
        this.mOnContentViewInactiveListeners.remove(viewForVisibleType);
    }

    public final void selectLayout(boolean z, boolean z2) {
        View expandButton;
        if (this.mContractedChild == null) {
            return;
        }
        if (!this.mUserExpanding) {
            int calculateVisibleType = calculateVisibleType();
            boolean z3 = calculateVisibleType != this.mVisibleType;
            if (z3 || z2) {
                View viewForVisibleType = getViewForVisibleType(calculateVisibleType);
                if (viewForVisibleType != null) {
                    viewForVisibleType.setVisibility(0);
                    int i = ExpandHeadsUpOnInlineReply.$r8$clinit;
                }
                if (!z || ((calculateVisibleType != 1 || this.mExpandedChild == null) && ((calculateVisibleType != 2 || this.mHeadsUpChild == null) && ((calculateVisibleType != 3 || this.mSingleLineView == null) && calculateVisibleType != 0)))) {
                    updateViewVisibilities(calculateVisibleType);
                } else {
                    TransformableView transformableViewForVisibleType = getTransformableViewForVisibleType(calculateVisibleType);
                    final TransformableView transformableViewForVisibleType2 = getTransformableViewForVisibleType(this.mVisibleType);
                    if (transformableViewForVisibleType == transformableViewForVisibleType2 || transformableViewForVisibleType2 == null) {
                        transformableViewForVisibleType.setVisible(true);
                    } else {
                        this.mAnimationStartVisibleType = this.mVisibleType;
                        transformableViewForVisibleType.transformFrom(transformableViewForVisibleType2);
                        getViewForVisibleType(calculateVisibleType).setVisibility(0);
                        updateShownWrapper(calculateVisibleType);
                        transformableViewForVisibleType2.transformTo(transformableViewForVisibleType, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                TransformableView transformableView = transformableViewForVisibleType2;
                                NotificationContentView notificationContentView = NotificationContentView.this;
                                if (transformableView != notificationContentView.getTransformableViewForVisibleType(notificationContentView.mVisibleType)) {
                                    transformableViewForVisibleType2.setVisible(false);
                                }
                                NotificationContentView notificationContentView2 = NotificationContentView.this;
                                notificationContentView2.mAnimationStartVisibleType = -1;
                                notificationContentView2.notifySubtreeForAccessibilityContentChange();
                            }
                        });
                        fireExpandedVisibleListenerIfVisible();
                    }
                }
                this.mVisibleType = calculateVisibleType;
                if (z3 && this.mFocusOnVisibilityChange) {
                    NotificationViewWrapper visibleWrapper = getVisibleWrapper(calculateVisibleType);
                    if (visibleWrapper != null && (expandButton = visibleWrapper.getExpandButton()) != null) {
                        expandButton.requestAccessibilityFocus();
                    }
                    this.mFocusOnVisibilityChange = false;
                }
                NotificationViewWrapper visibleWrapper2 = getVisibleWrapper(calculateVisibleType);
                if (visibleWrapper2 != null) {
                    visibleWrapper2.setContentHeight(this.mUnrestrictedContentHeight, getMinContentHeightHint());
                }
                updateBackgroundColor(z);
                return;
            }
            return;
        }
        int calculateVisibleType2 = calculateVisibleType();
        if (getTransformableViewForVisibleType(this.mVisibleType) == null) {
            this.mVisibleType = calculateVisibleType2;
            updateViewVisibilities(calculateVisibleType2);
            updateBackgroundColor(false);
            return;
        }
        int i2 = this.mVisibleType;
        if (calculateVisibleType2 != i2) {
            this.mTransformationStartVisibleType = i2;
            TransformableView transformableViewForVisibleType3 = getTransformableViewForVisibleType(calculateVisibleType2);
            TransformableView transformableViewForVisibleType4 = getTransformableViewForVisibleType(this.mTransformationStartVisibleType);
            transformableViewForVisibleType3.transformFrom(0.0f, transformableViewForVisibleType4);
            getViewForVisibleType(calculateVisibleType2).setVisibility(0);
            transformableViewForVisibleType4.transformTo(0.0f, transformableViewForVisibleType3);
            this.mVisibleType = calculateVisibleType2;
            updateBackgroundColor(true);
        }
        if (this.mForceSelectNextLayout) {
            forceUpdateVisibility(0, this.mContractedChild, this.mContractedWrapper);
            forceUpdateVisibility(1, this.mExpandedChild, this.mExpandedWrapper);
            forceUpdateVisibility(2, this.mHeadsUpChild, this.mHeadsUpWrapper);
            HybridNotificationView hybridNotificationView = this.mSingleLineView;
            forceUpdateVisibility(3, hybridNotificationView, hybridNotificationView);
            updateShownWrapper(this.mVisibleType);
            fireExpandedVisibleListenerIfVisible();
            this.mAnimationStartVisibleType = -1;
            notifySubtreeForAccessibilityContentChange();
        }
        int i3 = this.mTransformationStartVisibleType;
        if (i3 == -1 || this.mVisibleType == i3 || getViewForVisibleType(i3) == null) {
            updateViewVisibilities(calculateVisibleType2);
            updateBackgroundColor(false);
            return;
        }
        TransformableView transformableViewForVisibleType5 = getTransformableViewForVisibleType(this.mVisibleType);
        TransformableView transformableViewForVisibleType6 = getTransformableViewForVisibleType(this.mTransformationStartVisibleType);
        int viewHeight = getViewHeight(this.mTransformationStartVisibleType, false);
        int viewHeight2 = getViewHeight(this.mVisibleType, false);
        int abs = Math.abs(this.mContentHeight - viewHeight);
        int abs2 = Math.abs(viewHeight2 - viewHeight);
        float f = 1.0f;
        if (abs2 == 0) {
            StringBuilder sb = new StringBuilder("the total transformation distance is 0\n StartType: ");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.mTransformationStartVisibleType, " height: ", viewHeight, "\n VisibleType: ");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.mVisibleType, " height: ", viewHeight2, "\n mContentHeight: ");
            sb.append(this.mContentHeight);
            Log.wtf("NotificationContentView", sb.toString());
        } else {
            f = Math.min(1.0f, abs / abs2);
        }
        transformableViewForVisibleType5.transformFrom(f, transformableViewForVisibleType6);
        transformableViewForVisibleType6.transformTo(f, transformableViewForVisibleType5);
        NotificationViewWrapper visibleWrapper3 = getVisibleWrapper(this.mVisibleType);
        int customBackgroundColor = visibleWrapper3 != null ? visibleWrapper3.getCustomBackgroundColor() : 0;
        NotificationViewWrapper visibleWrapper4 = getVisibleWrapper(this.mTransformationStartVisibleType);
        int customBackgroundColor2 = visibleWrapper4 != null ? visibleWrapper4.getCustomBackgroundColor() : 0;
        if (customBackgroundColor != customBackgroundColor2) {
            if (customBackgroundColor2 == 0) {
                customBackgroundColor2 = this.mContainingNotification.calculateBgColor(false, false);
            }
            if (customBackgroundColor == 0) {
                customBackgroundColor = this.mContainingNotification.calculateBgColor(false, false);
            }
            customBackgroundColor = NotificationUtils.interpolateColors(f, customBackgroundColor2, customBackgroundColor);
        }
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow.getShowingLayout() != this || customBackgroundColor == expandableNotificationRow.mBgTint) {
            return;
        }
        expandableNotificationRow.mBgTint = customBackgroundColor;
        expandableNotificationRow.updateBackgroundTint(false);
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (this.mIsLockedAlpha) {
            return;
        }
        super.setAlpha(f);
    }

    public void setAnimationStartVisibleType(int i) {
        this.mAnimationStartVisibleType = i;
    }

    @Override // android.view.ViewGroup
    public final void setClipChildren(boolean z) {
        super.setClipChildren(z && !this.mRemoteInputVisible);
    }

    public final void setContractedChild(View view) {
        View view2 = this.mContractedChild;
        if (view2 != null) {
            this.mOnContentViewInactiveListeners.remove(view2);
            this.mContractedChild.animate().cancel();
            removeView(this.mContractedChild);
        }
        if (view != null) {
            addView(view);
            this.mContractedChild = view;
            this.mContractedWrapper = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
            updateShownWrapper(this.mVisibleType);
            return;
        }
        this.mContractedChild = null;
        this.mContractedWrapper = null;
        if (this.mTransformationStartVisibleType == 0) {
            this.mTransformationStartVisibleType = -1;
        }
    }

    public void setContractedWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mContractedWrapper = notificationViewWrapper;
    }

    public final void setExpandedChild(View view) {
        if (this.mExpandedChild != null) {
            this.mPreviousExpandedRemoteInputIntent = null;
            RemoteInputView remoteInputView = this.mExpandedRemoteInput;
            if (remoteInputView != null) {
                remoteInputView.onNotificationUpdateOrReset();
                if (this.mExpandedRemoteInput.isActive()) {
                    RemoteInputViewController remoteInputViewController = this.mExpandedRemoteInputController;
                    if (remoteInputViewController != null) {
                        this.mPreviousExpandedRemoteInputIntent = ((RemoteInputViewControllerImpl) remoteInputViewController).pendingIntent;
                    }
                    RemoteInputView remoteInputView2 = this.mExpandedRemoteInput;
                    this.mCachedExpandedRemoteInput = remoteInputView2;
                    this.mCachedExpandedRemoteInputViewController = remoteInputViewController;
                    remoteInputView2.dispatchStartTemporaryDetach();
                    ((ViewGroup) this.mExpandedRemoteInput.getParent()).removeView(this.mExpandedRemoteInput);
                }
            }
            this.mOnContentViewInactiveListeners.remove(this.mExpandedChild);
            this.mExpandedChild.animate().cancel();
            removeView(this.mExpandedChild);
            this.mExpandedRemoteInput = null;
            RemoteInputViewController remoteInputViewController2 = this.mExpandedRemoteInputController;
            if (remoteInputViewController2 != null) {
                ((RemoteInputViewControllerImpl) remoteInputViewController2).unbind();
            }
            this.mExpandedRemoteInputController = null;
        }
        if (view == null) {
            this.mExpandedChild = null;
            this.mExpandedWrapper = null;
            if (this.mTransformationStartVisibleType == 1) {
                this.mTransformationStartVisibleType = -1;
            }
            if (this.mVisibleType == 1) {
                selectLayout(false, true);
                return;
            }
            return;
        }
        addView(view);
        this.mExpandedChild = view;
        this.mExpandedWrapper = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow != null) {
            View view2 = this.mExpandedChild;
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            applySnoozeAction(view2);
            applyBubbleAction(view2, notificationEntry);
        }
        updateShownWrapper(this.mVisibleType);
    }

    public void setExpandedWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mExpandedWrapper = notificationViewWrapper;
    }

    public final void setHeadsUpChild(View view) {
        View view2 = this.mHeadsUpChild;
        if (view2 != null) {
            this.mOnContentViewInactiveListeners.remove(view2);
            this.mHeadsUpChild.animate().cancel();
            removeView(this.mHeadsUpChild);
        }
        StatusBarNotification statusBarNotification = null;
        if (view == null) {
            this.mHeadsUpChild = null;
            this.mHeadsUpWrapper = null;
            this.mIsHUNCompact = false;
            if (this.mTransformationStartVisibleType == 2) {
                this.mTransformationStartVisibleType = -1;
            }
            if (this.mVisibleType == 2) {
                selectLayout(false, true);
                return;
            }
            return;
        }
        addView(view);
        this.mHeadsUpChild = view;
        NotificationViewWrapper wrap = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
        this.mHeadsUpWrapper = wrap;
        boolean z = wrap instanceof NotificationCompactHeadsUpTemplateViewWrapper;
        this.mIsHUNCompact = z;
        if (z && this.mUiEventLogger != null) {
            ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
            if (expandableNotificationRow != null) {
                int i = NotificationBundleUi.$r8$clinit;
                statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
            }
            if (statusBarNotification != null) {
                this.mUiEventLogger.logWithInstanceId(NotificationCompactHeadsUpEvent.NOTIFICATION_COMPACT_HUN_SHOWN, statusBarNotification.getUid(), statusBarNotification.getPackageName(), statusBarNotification.getInstanceId());
            }
        }
        ExpandableNotificationRow expandableNotificationRow2 = this.mContainingNotification;
        if (expandableNotificationRow2 != null) {
            View view3 = this.mHeadsUpChild;
            NotificationEntry notificationEntry = expandableNotificationRow2.mEntry;
            applySnoozeAction(view3);
            applyBubbleAction(view3, notificationEntry);
        }
        updateShownWrapper(this.mVisibleType);
    }

    public void setHeadsUpWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mHeadsUpWrapper = notificationViewWrapper;
    }

    public final void setIsChildInGroup(boolean z) {
        this.mIsChildInGroup = z;
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setIsChildInGroup(z);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        updateAllSingleLineViews();
    }

    public final void setNotificationFaded(boolean z) {
        NotificationViewWrapper notificationViewWrapper = this.mContractedWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setNotificationFaded(z);
        }
        NotificationViewWrapper notificationViewWrapper2 = this.mHeadsUpWrapper;
        if (notificationViewWrapper2 != null) {
            notificationViewWrapper2.setNotificationFaded(z);
        }
        NotificationViewWrapper notificationViewWrapper3 = this.mExpandedWrapper;
        if (notificationViewWrapper3 != null) {
            notificationViewWrapper3.setNotificationFaded(z);
        }
        HybridNotificationView hybridNotificationView = this.mSingleLineView;
        if (hybridNotificationView != null) {
            hybridNotificationView.setNotificationFaded(z);
        }
    }

    public final void setSingleLineView(HybridNotificationView hybridNotificationView) {
        int i = AsyncHybridViewInflation.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        HybridNotificationView hybridNotificationView2 = this.mSingleLineView;
        if (hybridNotificationView2 != null) {
            this.mOnContentViewInactiveListeners.remove(hybridNotificationView2);
            this.mSingleLineView.animate().cancel();
            removeView(this.mSingleLineView);
        }
        if (hybridNotificationView != null) {
            addView(hybridNotificationView);
            this.mSingleLineView = hybridNotificationView;
        } else {
            this.mSingleLineView = null;
            if (this.mTransformationStartVisibleType == 3) {
                this.mTransformationStartVisibleType = -1;
            }
        }
    }

    @Override // android.view.View
    public final void setTranslationY(float f) {
        super.setTranslationY(f);
        updateClipping();
    }

    public boolean shouldShowBubbleButton(NotificationEntry notificationEntry) {
        int i = NotificationBundleUi.$r8$clinit;
        return this.mBubblesEnabledForUser && (((PeopleNotificationIdentifierImpl) this.mPeopleIdentifier).getPeopleNotificationType(notificationEntry) >= 2) && notificationEntry.mBubbleMetadata != null;
    }

    public final void updateBackgroundColor(boolean z) {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(this.mVisibleType);
        int customBackgroundColor = visibleWrapper != null ? visibleWrapper.getCustomBackgroundColor() : 0;
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow.getShowingLayout() != this || customBackgroundColor == expandableNotificationRow.mBgTint) {
            return;
        }
        expandableNotificationRow.mBgTint = customBackgroundColor;
        expandableNotificationRow.updateBackgroundTint(z);
    }

    public final void updateClipping() {
        if (!this.mClipToActualHeight) {
            setClipBounds(null);
            return;
        }
        int translationY = (int) (this.mClipTopAmount - getTranslationY());
        this.mClipBounds.set(0, translationY, getWidth(), Math.max(translationY, (int) ((this.mUnrestrictedContentHeight - this.mClipBottomAmount) - getTranslationY())));
        setClipBounds(this.mClipBounds);
    }

    public final void updateContentViewMarginBottom(View view, boolean z) {
        if (view == null || !(view.findViewById(16909884) instanceof ConversationLayout)) {
            return;
        }
        View findViewById = view.findViewById(android.R.id.animator);
        View findViewById2 = view.findViewById(android.R.id.animation);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(android.R.id.remoteMessaging);
        if (findViewById == null || findViewById2 == null || viewGroup == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            if (findViewById2.getVisibility() == 0) {
                MessagingTextMessage findViewById3 = view.findViewById(android.R.id.overlay_display_window_title);
                ViewGroup viewGroup2 = (ViewGroup) view.findViewById(android.R.id.insertion_handle);
                if (viewGroup2 == null || viewGroup2.getChildCount() > 1 || !(findViewById3 instanceof MessagingTextMessage)) {
                    return;
                }
                TextView textView = new TextView(((FrameLayout) this).mContext);
                textView.setText(findViewById3.getText().toString());
                textView.measure(getResources().getDimensionPixelSize(z ? R.dimen.notification_messaging_text_max_width_expanded : R.dimen.notification_messaging_text_max_width_collapsed), 0);
                int lineCount = textView.getLineCount();
                int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notification_action_list_margin_target_single_line_bottom_margin);
                int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.notification_action_list_margin_target_multi_line_bottom_margin);
                if (lineCount > 1 || !z) {
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, z ? dimensionPixelSize2 : 0);
                } else {
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, dimensionPixelSize);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e3, code lost:
    
        if (r10 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x009f, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x009d, code lost:
    
        if (r10 == false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateExpandButtonsDuringLayout(boolean r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationContentView.updateExpandButtonsDuringLayout(boolean, boolean):void");
    }

    public final void updateLegacy() {
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setLegacy(this.mLegacy);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setLegacy(this.mLegacy);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setLegacy(this.mLegacy);
        }
    }

    public final void updateShownWrapper(int i) {
        NotificationViewWrapper visibleWrapper = isShown() ? getVisibleWrapper(i) : null;
        NotificationViewWrapper notificationViewWrapper = this.mShownWrapper;
        if (notificationViewWrapper != visibleWrapper) {
            this.mShownWrapper = visibleWrapper;
            if (notificationViewWrapper != null) {
                notificationViewWrapper.onContentShown(false);
            }
            if (visibleWrapper != null) {
                visibleWrapper.onContentShown(true);
            }
        }
    }

    public final void updateViewVisibilities(int i) {
        View view = this.mContractedChild;
        NotificationViewWrapper notificationViewWrapper = this.mContractedWrapper;
        if (view != null) {
            notificationViewWrapper.setVisible(i == 0);
        }
        View view2 = this.mExpandedChild;
        NotificationViewWrapper notificationViewWrapper2 = this.mExpandedWrapper;
        if (view2 != null) {
            notificationViewWrapper2.setVisible(i == 1);
        }
        View view3 = this.mHeadsUpChild;
        NotificationViewWrapper notificationViewWrapper3 = this.mHeadsUpWrapper;
        if (view3 != null) {
            notificationViewWrapper3.setVisible(i == 2);
        }
        HybridNotificationView hybridNotificationView = this.mSingleLineView;
        if (hybridNotificationView != null) {
            hybridNotificationView.setVisible(i == 3);
        }
        updateShownWrapper(i);
        fireExpandedVisibleListenerIfVisible();
        this.mAnimationStartVisibleType = -1;
        notifySubtreeForAccessibilityContentChange();
    }

    public final void updateVisibility$1() {
        if (isShown()) {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            getViewTreeObserver().addOnPreDrawListener(this.mEnableAnimationPredrawListener);
        } else {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            this.mAnimate = false;
        }
    }
}
