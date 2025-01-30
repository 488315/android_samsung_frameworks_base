package com.android.systemui.statusbar.notification.row;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.ConversationLayout;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.Flags;
import com.android.systemui.logging.NotiCinemaLogger;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationCustomViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wmshell.BubblesManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Consumer;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.ArrayIterator;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationContentView extends FrameLayout implements NotificationFadeAware, PanelScreenShotLogger.LogProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimate;
    public int mAnimationStartVisibleType;
    public boolean mBeforeN;
    public RemoteInputView mCachedExpandedRemoteInput;
    public RemoteInputViewController mCachedExpandedRemoteInputViewController;
    public RemoteInputView mCachedHeadsUpRemoteInput;
    public RemoteInputViewController mCachedHeadsUpRemoteInputViewController;
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
    public final ViewTreeObserverOnPreDrawListenerC28821 mEnableAnimationPredrawListener;
    public View.OnClickListener mExpandClickListener;
    public boolean mExpandable;
    public View mExpandedChild;
    public InflatedSmartReplyViewHolder mExpandedInflatedSmartReplies;
    public RemoteInputView mExpandedRemoteInput;
    public RemoteInputViewController mExpandedRemoteInputController;
    public SmartReplyView mExpandedSmartReplyView;
    public Runnable mExpandedVisibleListener;
    public NotificationViewWrapper mExpandedWrapper;
    public boolean mFocusOnVisibilityChange;
    public boolean mForceSelectNextLayout;
    public boolean mHeadsUpAnimatingAway;
    public View mHeadsUpChild;
    public int mHeadsUpHeight;
    public InflatedSmartReplyViewHolder mHeadsUpInflatedSmartReplies;
    public RemoteInputView mHeadsUpRemoteInput;
    public RemoteInputViewController mHeadsUpRemoteInputController;
    public SmartReplyView mHeadsUpSmartReplyView;
    public NotificationViewWrapper mHeadsUpWrapper;
    public final HybridGroupManager mHybridGroupManager;
    public boolean mIsChildInGroup;
    public boolean mIsContentExpandable;
    public boolean mIsContractedHeaderContainAtMark;
    public boolean mIsExpandedHeaderContainAtMark;
    public boolean mIsHeadsUp;
    public boolean mLegacy;
    public int mMaxChildSizeOnMeasure;
    public int mMaxSizeOnMeasure;
    public int mMinContractedHeight;
    public NotificationEntry mNotificationEntry;
    public int mNotificationMaxHeight;
    public final ArrayMap mOnContentViewInactiveListeners;
    public PeopleNotificationIdentifier mPeopleIdentifier;
    public PendingIntent mPreviousExpandedRemoteInputIntent;
    public PendingIntent mPreviousHeadsUpRemoteInputIntent;
    public RemoteInputController mRemoteInputController;
    public RemoteInputViewSubcomponent.Factory mRemoteInputSubcomponentFactory;
    public boolean mRemoteInputVisible;
    public HybridNotificationView mSingleLineView;
    public int mSingleLineWidthIndention;
    public int mSmallHeight;
    public SmartReplyConstants mSmartReplyConstants;
    public SmartReplyController mSmartReplyController;
    public IStatusBarService mStatusBarService;
    public int mTransformationStartVisibleType;
    public int mUnrestrictedContentHeight;
    public boolean mUserExpanding;
    public int mVisibleType;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RemoteInputViewData {
        public RemoteInputViewController mController;
        public RemoteInputView mView;

        private RemoteInputViewData() {
        }

        public /* synthetic */ RemoteInputViewData(int i) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.row.NotificationContentView$1] */
    public NotificationContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClipBounds = new Rect();
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
        this.mHybridGroupManager = new HybridGroupManager(getContext());
        this.mMinContractedHeight = getResources().getDimensionPixelSize(R.dimen.min_notification_layout_height);
    }

    public static void applyExternalSmartReplyState(View view, InflatedSmartReplyState inflatedSmartReplyState) {
        List emptyList;
        boolean z = inflatedSmartReplyState != null && inflatedSmartReplyState.hasPhishingAction;
        View findViewById = view.findViewById(android.R.id.retailDemo);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 0 : 8);
        }
        if (inflatedSmartReplyState != null) {
            InflatedSmartReplyState.SuppressedActions suppressedActions = inflatedSmartReplyState.suppressedActions;
            if (suppressedActions == null || (emptyList = suppressedActions.suppressedActionIndices) == null) {
                emptyList = EmptyList.INSTANCE;
            }
        } else {
            emptyList = Collections.emptyList();
        }
        ViewGroup viewGroup = (ViewGroup) view.findViewById(android.R.id.actions_container);
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                Object tag = childAt.getTag(android.R.id.pickers);
                childAt.setVisibility((tag instanceof Integer) && emptyList.contains(tag) ? 8 : 0);
            }
        }
    }

    public static SmartReplyView applySmartReplyView(View view, InflatedSmartReplyState inflatedSmartReplyState, NotificationEntry notificationEntry, InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder) {
        SmartReplyView smartReplyView;
        View findViewById = view.findViewById(android.R.id.xhdpi);
        SmartReplyView smartReplyView2 = null;
        if (!(findViewById instanceof LinearLayout)) {
            return null;
        }
        LinearLayout linearLayout = (LinearLayout) findViewById;
        if (!SmartReplyStateInflaterKt.shouldShowSmartReplyView(notificationEntry, inflatedSmartReplyState)) {
            linearLayout.setVisibility(8);
            return null;
        }
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

    public static void updateViewVisibility(int i, int i2, View view, TransformableView transformableView) {
        if (view != null) {
            transformableView.setVisible(i == i2);
        }
    }

    public final void applyBubbleAction(View view, NotificationEntry notificationEntry) {
        if (view == null || this.mContainingNotification == null || this.mPeopleIdentifier == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(android.R.id.button7);
        View findViewById = view.findViewById(android.R.id.actions_container_layout);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(android.R.id.pin_cancel_button);
        if (imageView == null || findViewById == null) {
            return;
        }
        if (!(notificationEntry.mBubbleMetadata != null && BubblesManager.areBubblesEnabled(((FrameLayout) this).mContext, notificationEntry.mSbn.getUser()) && ((PeopleNotificationIdentifierImpl) this.mPeopleIdentifier).getPeopleNotificationType(notificationEntry) >= 2 && ActivityTaskManager.supportsMultiWindow(((FrameLayout) this).mContext) && !MultiWindowManager.getInstance().isMultiWindowBlockListApp(notificationEntry.mSbn.getPackageName()))) {
            imageView.setVisibility(8);
            return;
        }
        Drawable drawable = ((FrameLayout) this).mContext.getDrawable(notificationEntry.isBubble() ? R.drawable.ic_bubble_off : R.drawable.ic_bubble_on);
        imageView.setContentDescription(((FrameLayout) this).mContext.getResources().getString(notificationEntry.isBubble() ? R.string.notification_conversation_unbubble : R.string.notification_conversation_bubble));
        imageView.setImageDrawable(drawable);
        final ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        expandableNotificationRow.getClass();
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                final ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                if (expandableNotificationRow2.mBubblesManagerOptional.isPresent()) {
                    expandableNotificationRow2.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda7
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            ExpandableNotificationRow expandableNotificationRow3 = ExpandableNotificationRow.this;
                            NotificationEntry notificationEntry2 = expandableNotificationRow3.mEntry;
                            if (notificationEntry2 == null) {
                                return false;
                            }
                            SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0012", "type", notificationEntry2.isBubble() ? "don't bubble" : "bubble");
                            ((BubblesManager) expandableNotificationRow3.mBubblesManagerOptional.get()).onUserChangedBubble(expandableNotificationRow3.mEntry, !r4.isBubble());
                            return false;
                        }
                    }, null, true);
                }
                expandableNotificationRow2.mHeadsUpManager.removeNotification(expandableNotificationRow2.mEntry.mKey, true);
            }
        });
        imageView.setVisibility(0);
        findViewById.setVisibility(0);
        if (linearLayout != null) {
            ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                if (marginLayoutParams.bottomMargin > 0) {
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, 0);
                }
            }
        }
    }

    public final RemoteInputViewData applyRemoteInput(View view, NotificationEntry notificationEntry, boolean z, PendingIntent pendingIntent, NotificationViewWrapper notificationViewWrapper) {
        RemoteInput[] remoteInputs;
        RemoteInput remoteInput;
        boolean z2;
        RemoteInputViewData remoteInputViewData = new RemoteInputViewData(0);
        View findViewById = view.findViewById(android.R.id.actions_container_layout);
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
                RemoteInputView remoteInputView2 = (RemoteInputView) LayoutInflater.from(context).inflate(R.layout.remote_input, (ViewGroup) frameLayout, false);
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
                RemoteInputViewController controller = this.mRemoteInputSubcomponentFactory.create(remoteInputView2, this.mRemoteInputController).getController();
                remoteInputViewData.mController = controller;
                remoteInputViewData.mView.mViewController = controller;
            }
            if (z) {
                RemoteInputView remoteInputView3 = remoteInputViewData.mView;
                remoteInputView3.mWrapper = notificationViewWrapper;
                remoteInputView3.mOnVisibilityChangedListeners.add(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        View findViewById2;
                        View findViewById3;
                        NotificationContentView notificationContentView = NotificationContentView.this;
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        notificationContentView.mRemoteInputVisible = booleanValue;
                        notificationContentView.setClipChildren(!booleanValue);
                        int i = booleanValue ? 4 : 0;
                        View view2 = notificationContentView.mExpandedChild;
                        if (view2 != null && (findViewById3 = view2.findViewById(android.R.id.actions_container)) != null) {
                            findViewById3.setImportantForAccessibility(i);
                        }
                        View view3 = notificationContentView.mHeadsUpChild;
                        if (view3 == null || (findViewById2 = view3.findViewById(android.R.id.actions_container)) == null) {
                            return;
                        }
                        findViewById2.setImportantForAccessibility(i);
                    }
                });
                if (pendingIntent != null || remoteInputViewData.mView.isActive()) {
                    Notification.Action[] actionArr = notificationEntry.mSbn.getNotification().actions;
                    if (pendingIntent != null) {
                        ((RemoteInputViewControllerImpl) remoteInputViewData.mController).pendingIntent = pendingIntent;
                    }
                    RemoteInputViewControllerImpl remoteInputViewControllerImpl = (RemoteInputViewControllerImpl) remoteInputViewData.mController;
                    if (actionArr != null) {
                        PendingIntent pendingIntent2 = remoteInputViewControllerImpl.pendingIntent;
                        Intent intent = pendingIntent2 != null ? pendingIntent2.getIntent() : null;
                        if (intent != null) {
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
                                        remoteInputViewControllerImpl.pendingIntent = pendingIntent3;
                                        remoteInputViewControllerImpl.setRemoteInput(remoteInput);
                                        remoteInputViewControllerImpl.remoteInputs = remoteInputs;
                                        remoteInputViewControllerImpl.entry.editedSuggestionInfo = null;
                                        z2 = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        remoteInputViewControllerImpl.getClass();
                    }
                    z2 = false;
                    if (z2) {
                        if (!remoteInputViewData.mView.isActive()) {
                            remoteInputViewData.mView.focus();
                        }
                    } else if (remoteInputViewData.mView.isActive()) {
                        RemoteInputView.RemoteEditText remoteEditText2 = remoteInputViewData.mView.mEditText;
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
        ImageView imageView = (ImageView) view.findViewById(android.R.id.zoomIn);
        View findViewById = view.findViewById(android.R.id.actions_container_layout);
        if (imageView == null || findViewById == null) {
            return;
        }
        boolean z = !imageView.isEnabled();
        this.mContainingNotification.getClass();
        int i = 0;
        if (!(((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("show_notification_snooze").getIntValue() == 1) || z) {
            imageView.setVisibility(8);
            return;
        }
        imageView.setImageDrawable(((FrameLayout) this).mContext.getDrawable(R.drawable.quickpanel_ic_snooze));
        NotificationMenuRow.NotificationMenuItem createNotificationMenuItem = SecGutInflater.createNotificationMenuItem(R.string.notification_menu_snooze_description, ((FrameLayout) this).mContext, R.layout.sec_notification_snooze);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        expandableNotificationRow.getClass();
        imageView.setOnClickListener(new ExpandableNotificationRow$$ExternalSyntheticLambda3(expandableNotificationRow, createNotificationMenuItem, i));
        imageView.setContentDescription(((FrameLayout) this).mContext.getResources().getString(R.string.notification_menu_snooze_description));
        imageView.setVisibility(0);
        findViewById.setVisibility(0);
        View findViewById2 = view.findViewById(android.R.id.pin_cancel_button);
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
        int maxContentHeight = (!this.mIsChildInGroup || isGroupExpanded() || this.mContainingNotification.isExpanded(true)) ? this.mContainingNotification.getMaxContentHeight() : this.mContainingNotification.getShowingLayout().getMinHeight(false);
        if (maxContentHeight == 0) {
            maxContentHeight = this.mContentHeight;
        }
        int visualTypeForHeight = getVisualTypeForHeight(maxContentHeight);
        int visualTypeForHeight2 = (!this.mIsChildInGroup || isGroupExpanded()) ? getVisualTypeForHeight(this.mContainingNotification.getCollapsedHeight()) : 3;
        return this.mTransformationStartVisibleType == visualTypeForHeight2 ? visualTypeForHeight : visualTypeForHeight2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("Drawing view failed: ", e, "NotificationContentView");
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
        View viewForVisibleType = getViewForVisibleType(this.mVisibleType);
        RemoteInputView remoteInputView = viewForVisibleType == this.mExpandedChild ? this.mExpandedRemoteInput : viewForVisibleType == this.mHeadsUpChild ? this.mHeadsUpRemoteInput : null;
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
        Runnable runnable = this.mExpandedVisibleListener;
        this.mExpandedVisibleListener = null;
        runnable.run();
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
            NotiCinemaLogger notiCinemaLogger = (NotiCinemaLogger) Dependency.get(NotiCinemaLogger.class);
            ViewGroup viewGroup = (ViewGroup) this.mContractedChild;
            ArrayList arrayList2 = notiCinemaLogger.mTmpLog;
            arrayList2.clear();
            notiCinemaLogger.visitLayoutTreeToAssembleLogLine(viewGroup, 0);
            arrayList.addAll(arrayList2);
        }
        View view2 = this.mExpandedChild;
        if (view2 != null && (view2 instanceof ViewGroup)) {
            arrayList.add("====== mExpandedChild ====== ");
            NotiCinemaLogger notiCinemaLogger2 = (NotiCinemaLogger) Dependency.get(NotiCinemaLogger.class);
            ViewGroup viewGroup2 = (ViewGroup) this.mExpandedChild;
            ArrayList arrayList3 = notiCinemaLogger2.mTmpLog;
            arrayList3.clear();
            notiCinemaLogger2.visitLayoutTreeToAssembleLogLine(viewGroup2, 0);
            arrayList.addAll(arrayList3);
        }
        View view3 = this.mHeadsUpChild;
        if (view3 != null && (view3 instanceof ViewGroup)) {
            arrayList.add("====== mHeadsUpChild ====== ");
            NotiCinemaLogger notiCinemaLogger3 = (NotiCinemaLogger) Dependency.get(NotiCinemaLogger.class);
            ViewGroup viewGroup3 = (ViewGroup) this.mHeadsUpChild;
            ArrayList arrayList4 = notiCinemaLogger3.mTmpLog;
            arrayList4.clear();
            notiCinemaLogger3.visitLayoutTreeToAssembleLogLine(viewGroup3, 0);
            arrayList.addAll(arrayList4);
        }
        if (this.mSingleLineView != null) {
            arrayList.add("====== mSingleLineView ====== ");
            NotiCinemaLogger notiCinemaLogger4 = (NotiCinemaLogger) Dependency.get(NotiCinemaLogger.class);
            HybridNotificationView hybridNotificationView = this.mSingleLineView;
            ArrayList arrayList5 = notiCinemaLogger4.mTmpLog;
            arrayList5.clear();
            notiCinemaLogger4.visitLayoutTreeToAssembleLogLine(hybridNotificationView, 0);
            arrayList.addAll(arrayList5);
        }
        arrayList.add("===============================");
        return arrayList;
    }

    public final int getBackgroundColor(int i) {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(i);
        if (visibleWrapper != null) {
            return visibleWrapper.getCustomBackgroundColor();
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0023, code lost:
    
        if ((r1 == r4) != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
    
        if (r2 != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getExtraRemoteInputHeight(RemoteInputView remoteInputView) {
        boolean z;
        if (remoteInputView != null) {
            if (!remoteInputView.isActive()) {
                if (remoteInputView.getVisibility() == 0) {
                    RemoteInputController remoteInputController = remoteInputView.mController;
                    String str = remoteInputView.mEntry.mKey;
                    Object obj = remoteInputView.mToken;
                    Object obj2 = remoteInputController.mSpinning.get(str);
                    z = true;
                }
                z = false;
            }
            return getResources().getDimensionPixelSize(android.R.dimen.notification_left_icon_size);
        }
        return 0;
    }

    public final int getHeadsUpHeight(boolean z) {
        int i;
        if (this.mHeadsUpChild != null) {
            i = 2;
        } else {
            if (this.mContractedChild == null) {
                return getMinHeight(false);
            }
            i = 0;
        }
        return getExtraRemoteInputHeight(this.mExpandedRemoteInput) + getExtraRemoteInputHeight(this.mHeadsUpRemoteInput) + getViewHeight(i, z);
    }

    public final int getMinContentHeightHint() {
        int viewHeight;
        int i;
        boolean z;
        if (this.mIsChildInGroup && isVisibleOrTransitioning(3)) {
            return ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(android.R.dimen.notification_header_icon_size_ambient);
        }
        if (this.mHeadsUpChild != null && this.mExpandedChild != null) {
            int i2 = this.mTransformationStartVisibleType;
            if (!((i2 == 2 || this.mAnimationStartVisibleType == 2) && this.mVisibleType == 1)) {
                if (!((i2 == 1 || this.mAnimationStartVisibleType == 1) && this.mVisibleType == 2)) {
                    z = false;
                    boolean z2 = isVisibleOrTransitioning(0) && (this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mContainingNotification.canShowHeadsUp();
                    if (!z || z2) {
                        return Math.min(getViewHeight(2, false), getViewHeight(1, false));
                    }
                }
            }
            z = true;
            if (isVisibleOrTransitioning(0)) {
            }
            if (!z) {
            }
            return Math.min(getViewHeight(2, false), getViewHeight(1, false));
        }
        if (this.mVisibleType == 1 && (i = this.mContentHeightAtAnimationStart) != -1 && this.mExpandedChild != null) {
            return Math.min(i, getViewHeight(1, false));
        }
        if (this.mHeadsUpChild == null || !isVisibleOrTransitioning(2)) {
            viewHeight = this.mExpandedChild != null ? getViewHeight(1, false) : this.mContractedChild != null ? getViewHeight(0, false) + ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(android.R.dimen.notification_header_icon_size_ambient) : getMinHeight(false);
        } else {
            viewHeight = getViewHeight(2, false);
            RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
            if (remoteInputView != null && remoteInputView.mIsAnimatingAppearance) {
                RemoteInputViewControllerImpl remoteInputViewControllerImpl = (RemoteInputViewControllerImpl) this.mHeadsUpRemoteInputController;
                remoteInputViewControllerImpl.getClass();
                Flags flags = Flags.INSTANCE;
                remoteInputViewControllerImpl.mFlags.getClass();
            }
        }
        return (this.mExpandedChild == null || !isVisibleOrTransitioning(1)) ? viewHeight : Math.min(viewHeight, getViewHeight(1, false));
    }

    public final int getMinHeight(boolean z) {
        return (z || !this.mIsChildInGroup || isGroupExpanded()) ? this.mContractedChild != null ? getViewHeight(0, false) : this.mMinContractedHeight : this.mSingleLineView.getHeight();
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
        return notificationViewWrapper != null ? height + notificationViewWrapper.getHeaderTranslation(z) : height;
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
        ExpandableNotificationRow expandableNotificationRow;
        boolean z;
        boolean z2 = false;
        boolean z3 = this.mExpandedChild == null;
        if (!z3 && f == getViewHeight(1, false) && !this.mContainingNotification.mIsPinned) {
            return 1;
        }
        if (!this.mUserExpanding && this.mIsChildInGroup && !isGroupExpanded()) {
            return 3;
        }
        if ((!this.mIsHeadsUp && !this.mHeadsUpAnimatingAway) || this.mHeadsUpChild == null || !this.mContainingNotification.canShowHeadsUp()) {
            if (!z3 && this.mContainingNotification.isExpanded(false)) {
                return 1;
            }
            if (z3 || !(this.mContractedChild == null || f > getViewHeight(0, false) || (this.mIsChildInGroup && !isGroupExpanded() && this.mContainingNotification.isExpanded(true)))) {
                return 0;
            }
            return !z3 ? 1 : -1;
        }
        if (f > getViewHeight(2, false) && !z3) {
            RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
            if (remoteInputView != null && remoteInputView.isActive() && this.mIsHeadsUp && (z = (expandableNotificationRow = this.mContainingNotification).mIsPinned)) {
                if (!(!z ? false : expandableNotificationRow.mExpandedWhenPinned)) {
                    z2 = true;
                }
            }
            if (!z2) {
                return 1;
            }
        }
        return 2;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isAnimatingVisibleType() {
        return this.mAnimationStartVisibleType != -1;
    }

    public final boolean isGroupExpanded() {
        return this.mContainingNotification.isGroupExpanded();
    }

    public final boolean isVisibleOrTransitioning(int i) {
        return this.mVisibleType == i || this.mTransformationStartVisibleType == i || this.mAnimationStartVisibleType == i;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateVisibility();
    }

    public final void onChildVisibilityChanged(View view, int i, int i2) {
        Runnable runnable;
        super.onChildVisibilityChanged(view, i, i2);
        boolean z = true;
        if (view != null && isShown() && (view.getVisibility() == 0 || getViewForVisibleType(this.mVisibleType) == view)) {
            z = false;
        }
        if (!z || (runnable = (Runnable) this.mOnContentViewInactiveListeners.remove(view)) == null) {
            return;
        }
        runnable.run();
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
        boolean z3 = true;
        boolean z4 = mode == 1073741824;
        boolean z5 = mode == Integer.MIN_VALUE;
        int size = View.MeasureSpec.getSize(i);
        int size2 = (z4 || z5) ? View.MeasureSpec.getSize(i2) : 1073741823;
        if (this.mExpandedChild != null) {
            int i4 = this.mNotificationMaxHeight;
            if (this.mContainingNotification.mIsPinned) {
                Display display = getContext().getDisplay();
                if (display.getRotation() == 1 || display.getRotation() == 3) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    display.getRealMetrics(displayMetrics);
                    i4 = displayMetrics.heightPixels - (getResources().getDimensionPixelSize(R.dimen.notification_shelf_height) + getResources().getDimensionPixelSize(R.dimen.heads_up_status_bar_padding));
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
            measureChildWithMargins(this.mContractedChild, i, 0, ((this.mBeforeN && (this.mContractedWrapper instanceof NotificationCustomViewWrapper)) || z) ? View.MeasureSpec.makeMeasureSpec(i6, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) : View.MeasureSpec.makeMeasureSpec(i6, VideoPlayer.MEDIA_ERROR_SYSTEM), 0);
            int measuredHeight = this.mContractedChild.getMeasuredHeight();
            int i8 = this.mMinContractedHeight;
            if (measuredHeight < i8) {
                measureChildWithMargins(this.mContractedChild, i, 0, View.MeasureSpec.makeMeasureSpec(i8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), 0);
            }
            i3 = Math.max(i3, measuredHeight);
            if (this.mExpandedChild != null && this.mContractedChild.getMeasuredHeight() > this.mExpandedChild.getMeasuredHeight()) {
                measureChildWithMargins(this.mExpandedChild, i, 0, View.MeasureSpec.makeMeasureSpec(this.mContractedChild.getMeasuredHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), 0);
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
            } else {
                z3 = false;
            }
            measureChildWithMargins(this.mHeadsUpChild, i, 0, View.MeasureSpec.makeMeasureSpec(extraMeasureHeight2, z3 ? 1073741824 : Integer.MIN_VALUE), 0);
            i3 = Math.max(i3, this.mHeadsUpChild.getMeasuredHeight());
        }
        if (this.mSingleLineView != null) {
            this.mSingleLineView.measure((this.mSingleLineWidthIndention == 0 || View.MeasureSpec.getMode(i) == 0) ? i : View.MeasureSpec.makeMeasureSpec(this.mSingleLineView.getPaddingEnd() + (size - this.mSingleLineWidthIndention), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(this.mNotificationMaxHeight, VideoPlayer.MEDIA_ERROR_SYSTEM));
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
        if (z) {
            fireExpandedVisibleListenerIfVisible();
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        updateVisibility();
        if (i == 0 || this.mOnContentViewInactiveListeners.isEmpty()) {
            return;
        }
        Iterator it = new ArrayList(this.mOnContentViewInactiveListeners.values()).iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.mOnContentViewInactiveListeners.clear();
    }

    public final void performWhenContentInactive(int i, NotificationContentInflater$$ExternalSyntheticLambda2 notificationContentInflater$$ExternalSyntheticLambda2) {
        View viewForVisibleType = getViewForVisibleType(i);
        if (viewForVisibleType != null) {
            View viewForVisibleType2 = getViewForVisibleType(i);
            boolean z = true;
            if (viewForVisibleType2 != null && isShown() && (viewForVisibleType2.getVisibility() == 0 || getViewForVisibleType(this.mVisibleType) == viewForVisibleType2)) {
                z = false;
            }
            if (!z) {
                this.mOnContentViewInactiveListeners.put(viewForVisibleType, notificationContentInflater$$ExternalSyntheticLambda2);
                return;
            }
        }
        notificationContentInflater$$ExternalSyntheticLambda2.run();
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
        RemoteInputViewController remoteInputViewController;
        RemoteInputViewController remoteInputViewController2;
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
                    if (calculateVisibleType == 2 && this.mHeadsUpRemoteInputController != null && (remoteInputViewController2 = this.mExpandedRemoteInputController) != null && ((RemoteInputViewControllerImpl) remoteInputViewController2).view.isActive()) {
                        ((RemoteInputViewControllerImpl) this.mHeadsUpRemoteInputController).stealFocusFrom(this.mExpandedRemoteInputController);
                    }
                    if (calculateVisibleType == 1 && this.mExpandedRemoteInputController != null && (remoteInputViewController = this.mHeadsUpRemoteInputController) != null && ((RemoteInputViewControllerImpl) remoteInputViewController).view.isActive()) {
                        ((RemoteInputViewControllerImpl) this.mExpandedRemoteInputController).stealFocusFrom(this.mHeadsUpRemoteInputController);
                    }
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
                        transformableViewForVisibleType2.transformTo(transformableViewForVisibleType, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                TransformableView transformableView = transformableViewForVisibleType2;
                                NotificationContentView notificationContentView = NotificationContentView.this;
                                if (transformableView != notificationContentView.getTransformableViewForVisibleType(notificationContentView.mVisibleType)) {
                                    transformableViewForVisibleType2.setVisible(false);
                                }
                                NotificationContentView.this.mAnimationStartVisibleType = -1;
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
        int i = this.mVisibleType;
        if (calculateVisibleType2 != i) {
            this.mTransformationStartVisibleType = i;
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
            fireExpandedVisibleListenerIfVisible();
            this.mAnimationStartVisibleType = -1;
        }
        int i2 = this.mTransformationStartVisibleType;
        if (i2 == -1 || this.mVisibleType == i2 || getViewForVisibleType(i2) == null) {
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
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.mTransformationStartVisibleType, " height: ", viewHeight, "\n VisibleType: ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.mVisibleType, " height: ", viewHeight2, "\n mContentHeight: ");
            sb.append(this.mContentHeight);
            Log.wtf("NotificationContentView", sb.toString());
        } else {
            f = Math.min(1.0f, abs / abs2);
        }
        transformableViewForVisibleType5.transformFrom(f, transformableViewForVisibleType6);
        transformableViewForVisibleType6.transformTo(f, transformableViewForVisibleType5);
        int backgroundColor = getBackgroundColor(this.mVisibleType);
        int backgroundColor2 = getBackgroundColor(this.mTransformationStartVisibleType);
        if (backgroundColor != backgroundColor2) {
            if (backgroundColor2 == 0) {
                backgroundColor2 = this.mContainingNotification.calculateBgColor(false, false);
            }
            if (backgroundColor == 0) {
                backgroundColor = this.mContainingNotification.calculateBgColor(false, false);
            }
            backgroundColor = NotificationUtils.interpolateColors(f, backgroundColor2, backgroundColor);
        }
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow.getShowingLayout() != this || backgroundColor == expandableNotificationRow.mBgTint) {
            return;
        }
        expandableNotificationRow.mBgTint = backgroundColor;
        expandableNotificationRow.updateBackgroundTint(false);
    }

    @Override // android.view.ViewGroup
    public final void setClipChildren(boolean z) {
        super.setClipChildren(z && !this.mRemoteInputVisible);
    }

    public final void setContentHeight(int i) {
        this.mUnrestrictedContentHeight = Math.max(i, getMinHeight(false));
        this.mContentHeight = Math.min(this.mUnrestrictedContentHeight, (this.mContainingNotification.getIntrinsicHeight() - getExtraRemoteInputHeight(this.mExpandedRemoteInput)) - getExtraRemoteInputHeight(this.mHeadsUpRemoteInput));
        selectLayout(this.mAnimate, false);
        if (this.mContractedChild == null) {
            return;
        }
        int minContentHeightHint = getMinContentHeightHint();
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(this.mVisibleType);
        if (visibleWrapper != null) {
            visibleWrapper.setContentHeight(this.mUnrestrictedContentHeight, minContentHeightHint);
        }
        NotificationViewWrapper visibleWrapper2 = getVisibleWrapper(this.mTransformationStartVisibleType);
        if (visibleWrapper2 != null) {
            visibleWrapper2.setContentHeight(this.mUnrestrictedContentHeight, minContentHeightHint);
        }
        updateClipping();
        invalidateOutline();
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
        } else {
            this.mContractedChild = null;
            this.mContractedWrapper = null;
            if (this.mTransformationStartVisibleType == 0) {
                this.mTransformationStartVisibleType = -1;
            }
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
    }

    public void setExpandedWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mExpandedWrapper = notificationViewWrapper;
    }

    public final void setHeadsUpChild(View view) {
        if (this.mHeadsUpChild != null) {
            this.mPreviousHeadsUpRemoteInputIntent = null;
            RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
            if (remoteInputView != null) {
                remoteInputView.onNotificationUpdateOrReset();
                if (this.mHeadsUpRemoteInput.isActive()) {
                    RemoteInputViewController remoteInputViewController = this.mHeadsUpRemoteInputController;
                    if (remoteInputViewController != null) {
                        this.mPreviousHeadsUpRemoteInputIntent = ((RemoteInputViewControllerImpl) remoteInputViewController).pendingIntent;
                    }
                    RemoteInputView remoteInputView2 = this.mHeadsUpRemoteInput;
                    this.mCachedHeadsUpRemoteInput = remoteInputView2;
                    this.mCachedHeadsUpRemoteInputViewController = remoteInputViewController;
                    remoteInputView2.dispatchStartTemporaryDetach();
                    ((ViewGroup) this.mHeadsUpRemoteInput.getParent()).removeView(this.mHeadsUpRemoteInput);
                }
            }
            this.mOnContentViewInactiveListeners.remove(this.mHeadsUpChild);
            this.mHeadsUpChild.animate().cancel();
            removeView(this.mHeadsUpChild);
            this.mHeadsUpRemoteInput = null;
            RemoteInputViewController remoteInputViewController2 = this.mHeadsUpRemoteInputController;
            if (remoteInputViewController2 != null) {
                ((RemoteInputViewControllerImpl) remoteInputViewController2).unbind();
            }
            this.mHeadsUpRemoteInputController = null;
        }
        if (view == null) {
            this.mHeadsUpChild = null;
            this.mHeadsUpWrapper = null;
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
        this.mHeadsUpWrapper = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow != null) {
            View view2 = this.mHeadsUpChild;
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            applySnoozeAction(view2);
            applyBubbleAction(view2, notificationEntry);
            view.measure(0, 0);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (view.getMeasuredHeight() > (getResources().getConfiguration().orientation == 1 ? displayMetrics.heightPixels : displayMetrics.widthPixels) * 0.4f) {
                FrameworkStatsLog.write(100301, this.mContainingNotification.mEntry.mSbn.getPackageName(), "NOTIFICATION");
            }
        }
    }

    public void setHeadsUpWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mHeadsUpWrapper = notificationViewWrapper;
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

    @Override // android.view.View
    public final void setTranslationY(float f) {
        super.setTranslationY(f);
        updateClipping();
    }

    public boolean shouldShowBubbleButton(NotificationEntry notificationEntry) {
        return BubblesManager.areBubblesEnabled(((FrameLayout) this).mContext, notificationEntry.mSbn.getUser()) && (((PeopleNotificationIdentifierImpl) this.mPeopleIdentifier).getPeopleNotificationType(notificationEntry) >= 2) && notificationEntry.mBubbleMetadata != null;
    }

    public final void updateAllSingleLineViews() {
        boolean z;
        TextView textView;
        if (this.mIsChildInGroup) {
            Trace.beginSection("NotifContentView#updateSingleLineView");
            HybridNotificationView hybridNotificationView = this.mSingleLineView;
            boolean z2 = hybridNotificationView == null;
            HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
            View view = this.mContractedChild;
            StatusBarNotification statusBarNotification = this.mNotificationEntry.mSbn;
            hybridGroupManager.getClass();
            if (hybridNotificationView == null) {
                Trace.beginSection("HybridGroupManager#bindFromNotification");
                Trace.beginSection("HybridGroupManager#inflateHybridView");
                hybridNotificationView = (HybridNotificationView) LayoutInflater.from(hybridGroupManager.mContext).inflate(view instanceof ConversationLayout ? R.layout.hybrid_conversation_notification : R.layout.hybrid_notification, (ViewGroup) this, false);
                addView(hybridNotificationView);
                Trace.endSection();
                z = true;
            } else {
                z = false;
            }
            Notification notification2 = statusBarNotification.getNotification();
            CharSequence charSequence = notification2.extras.getCharSequence("android.title");
            if (charSequence == null) {
                charSequence = notification2.extras.getCharSequence("android.title.big");
            }
            Notification notification3 = statusBarNotification.getNotification();
            CharSequence charSequence2 = notification3.extras.getCharSequence("android.text");
            if (charSequence2 == null) {
                charSequence2 = notification3.extras.getCharSequence("android.bigText");
            }
            hybridNotificationView.bind(charSequence, charSequence2, view);
            if (z) {
                Trace.endSection();
            }
            this.mSingleLineView = hybridNotificationView;
            NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
            HybridNotificationView hybridNotificationView2 = this.mSingleLineView;
            int textColor = notificationColorPicker.getTextColor(1, false, true);
            if ((hybridNotificationView2 instanceof HybridConversationNotificationView) && (textView = (TextView) ((HybridConversationNotificationView) hybridNotificationView2).findViewById(R.id.conversation_notification_sender)) != null) {
                textView.setTextColor(textColor);
            }
            if (z2) {
                int i = this.mVisibleType;
                HybridNotificationView hybridNotificationView3 = this.mSingleLineView;
                updateViewVisibility(i, 3, hybridNotificationView3, hybridNotificationView3);
            }
            Trace.endSection();
        } else {
            View view2 = this.mSingleLineView;
            if (view2 != null) {
                removeView(view2);
                this.mSingleLineView = null;
            }
        }
        if (this.mSingleLineView != null) {
            if (this.mContainingNotification.mDimmed || DeviceState.isOpenTheme(getContext()) || ((FrameLayout) this).mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).updateSingleLine(this.mSingleLineView, true);
            }
        }
    }

    public final void updateBackgroundColor(boolean z) {
        int backgroundColor = getBackgroundColor(this.mVisibleType);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow.getShowingLayout() != this || backgroundColor == expandableNotificationRow.mBgTint) {
            return;
        }
        expandableNotificationRow.mBgTint = backgroundColor;
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

    public final void updateExpandButtonsDuringLayout(boolean z, boolean z2) {
        this.mExpandable = z;
        View view = this.mExpandedChild;
        boolean z3 = false;
        if (view != null && view.getHeight() != 0) {
            int height = this.mExpandedChild.getHeight();
            if ((this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mHeadsUpChild != null && this.mContainingNotification.canShowHeadsUp()) {
                ImageView imageView = (ImageView) this.mExpandedChild.findViewById(android.R.id.zoomIn);
                if (imageView != null) {
                    imageView.getVisibility();
                }
                if (this.mExpandedChild.getHeight() <= this.mHeadsUpChild.getHeight()) {
                    Log.d("NotificationContentView", "entry : " + this.mContainingNotification.mLoggingKey + " >>> mHeadsUpChild is tall : " + this.mHeadsUpChild.getHeight() + " , mExpandedChild : " + this.mExpandedChild.getHeight());
                    z = false;
                }
            } else {
                View view2 = this.mContractedChild;
                if (view2 == null || height <= view2.getHeight()) {
                    StringBuilder sb = new StringBuilder("entry : ");
                    sb.append(this.mContainingNotification.mLoggingKey);
                    sb.append(" >>> mContractedChild is tall : ");
                    View view3 = this.mContractedChild;
                    sb.append(view3 == null ? "NULL" : Integer.valueOf(view3.getHeight()));
                    sb.append(" , expandedChildHeight : ");
                    sb.append(height);
                    Log.d("NotificationContentView", sb.toString());
                    z = false;
                }
            }
        }
        if (z2 && this.mIsContentExpandable != z) {
            z3 = true;
        }
        if (z3) {
            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("we relayout notification header by value : "), this.mIsContentExpandable, " : ", z, "NotificationContentView");
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.updateExpandability(z, this.mExpandClickListener, z3);
        }
        if (this.mContractedChild != null) {
            this.mContractedWrapper.updateExpandability(z, this.mExpandClickListener, z3);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.updateExpandability(z, this.mExpandClickListener, z3);
        }
        if (z3) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("we update IsContentExpandable : ", z, "NotificationContentView");
        }
        this.mIsContentExpandable = z;
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

    public final void updateViewVisibilities(int i) {
        updateViewVisibility(i, 0, this.mContractedChild, this.mContractedWrapper);
        updateViewVisibility(i, 1, this.mExpandedChild, this.mExpandedWrapper);
        updateViewVisibility(i, 2, this.mHeadsUpChild, this.mHeadsUpWrapper);
        HybridNotificationView hybridNotificationView = this.mSingleLineView;
        updateViewVisibility(i, 3, hybridNotificationView, hybridNotificationView);
        fireExpandedVisibleListenerIfVisible();
        this.mAnimationStartVisibleType = -1;
    }

    public final void updateVisibility() {
        if (isShown()) {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            getViewTreeObserver().addOnPreDrawListener(this.mEnableAnimationPredrawListener);
        } else {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            this.mAnimate = false;
        }
    }
}
