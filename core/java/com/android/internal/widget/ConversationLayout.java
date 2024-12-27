package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.Person;
import android.app.RemoteInputHistoryItem;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.flags.Flags;

import com.android.internal.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

@RemoteViews.RemoteView
public class ConversationLayout extends FrameLayout
        implements ImageMessageConsumer, IMessagingLayout {
    public static final int IMPORTANCE_ANIM_GROW_DURATION = 250;
    public static final int IMPORTANCE_ANIM_SHRINK_DELAY = 25;
    public static final int IMPORTANCE_ANIM_SHRINK_DURATION = 200;
    private NotificationActionListLayout mActions;
    private ArrayList<MessagingGroup> mAddedGroups;
    private Queue<MessagingGroup> mAddedQueue;
    private ObservableTextView mAppName;
    private View mAppNameDivider;
    private boolean mAppNameGone;
    private Icon mAvatarReplacement;
    private int mBadgeProtrusion;
    private View mContentContainer;
    private int mContentMarginEnd;
    private int mConversationAvatarSize;
    private int mConversationAvatarSizeExpanded;
    private int mConversationBadgeMargin;
    private int mConversationBadgeMarginExpanded;
    private int mConversationBadgeSize;
    private int mConversationBadgeSizeExpanded;
    private View mConversationFacePile;
    private View mConversationHeader;
    private ConversationHeaderData mConversationHeaderData;
    private Icon mConversationIcon;
    private View mConversationIconBadge;
    private CachingIconView mConversationIconBadgeBg;
    private View mConversationIconContainer;
    private int mConversationIconTopPadding;
    private int mConversationIconTopPaddingExpandedGroup;
    private CachingIconView mConversationIconView;
    private int mConversationMinHeight;
    private int mConversationStartMargin;
    private TextView mConversationText;
    private CharSequence mConversationTitle;
    private int mConversationTopMargin;
    private NotificationExpandButton mExpandButton;
    private ViewGroup mExpandButtonAndContentContainer;
    private View mExpandButtonContainer;
    private ViewGroup mExpandButtonContainerA11yContainer;
    private boolean mExpandable;
    private int mExpandedGroupBadgeProtrusion;
    private int mExpandedGroupBadgeProtrusionFacePile;
    private int mExpandedGroupMessagePadding;
    private int mFacePileAvatarSize;
    private int mFacePileAvatarSizeExpandedGroup;
    private int mFacePileProtectionWidth;
    private int mFacePileProtectionWidthExpanded;
    private CharSequence mFallbackChatName;
    private CharSequence mFallbackGroupChatName;
    private View mFeedbackIcon;
    private ArrayList<MessagingGroup> mGroups;
    private List<MessagingMessage> mHistoricMessages;
    private CachingIconView mIcon;
    private MessagingLinearLayout mImageMessageContainer;
    private ImageResolver mImageResolver;
    private CachingIconView mImportanceRingView;
    private boolean mImportantConversation;
    private boolean mIsCollapsed;
    private boolean mIsOneToOne;
    private Icon mLargeIcon;
    private int mLayoutColor;
    private int mMessageSpacingGroup;
    private int mMessageSpacingStandard;
    private int mMessageTextColor;
    private List<MessagingMessage> mMessages;
    private Rect mMessagingClipRect;
    private MessagingLinearLayout mMessagingLinearLayout;
    private float mMinTouchSize;
    private CharSequence mNameReplacement;
    private int mNotificationBackgroundColor;
    private int mNotificationHeaderExpandedPadding;
    private final PeopleHelper mPeopleHelper;
    private boolean mPrecomputedTextEnabled;
    private int mSenderTextColor;
    private Icon mShortcutIcon;
    private boolean mShowHistoricMessages;
    private ArrayList<MessagingLinearLayout.MessagingChild> mToRecycle;
    private TouchDelegateComposite mTouchDelegate;
    private Person mUser;
    public static final Interpolator LINEAR_OUT_SLOW_IN =
            new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator FAST_OUT_LINEAR_IN =
            new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator FAST_OUT_SLOW_IN =
            new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator OVERSHOOT = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
    public static final View.OnLayoutChangeListener MESSAGING_PROPERTY_ANIMATOR =
            new MessagingPropertyAnimator();

    public ConversationLayout(Context context) {
        super(context);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mAddedQueue = new LinkedList();
        this.mExpandable = true;
        this.mTouchDelegate = new TouchDelegateComposite(this);
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public ConversationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mAddedQueue = new LinkedList();
        this.mExpandable = true;
        this.mTouchDelegate = new TouchDelegateComposite(this);
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public ConversationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mAddedQueue = new LinkedList();
        this.mExpandable = true;
        this.mTouchDelegate = new TouchDelegateComposite(this);
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public ConversationLayout(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPeopleHelper = new PeopleHelper();
        this.mMessages = new ArrayList();
        this.mHistoricMessages = new ArrayList();
        this.mGroups = new ArrayList<>();
        this.mAddedGroups = new ArrayList<>();
        this.mAddedQueue = new LinkedList();
        this.mExpandable = true;
        this.mTouchDelegate = new TouchDelegateComposite(this);
        this.mToRecycle = new ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mMessagingLinearLayout =
                (MessagingLinearLayout) findViewById(R.id.notification_messaging);
        this.mActions = (NotificationActionListLayout) findViewById(R.id.actions);
        this.mImageMessageContainer =
                (MessagingLinearLayout) findViewById(R.id.conversation_image_message_container);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int size = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMessagingClipRect = new Rect(0, 0, size, size);
        setMessagingClippingDisabled(false);
        this.mConversationIconView = (CachingIconView) findViewById(R.id.conversation_icon);
        this.mConversationIconContainer = findViewById(R.id.conversation_icon_container);
        this.mIcon = (CachingIconView) findViewById(16908294);
        this.mFeedbackIcon = findViewById(R.id.feedback);
        this.mMinTouchSize = getResources().getDisplayMetrics().density * 48.0f;
        this.mImportanceRingView =
                (CachingIconView) findViewById(R.id.conversation_icon_badge_ring);
        this.mConversationIconBadge = findViewById(R.id.conversation_icon_badge);
        this.mConversationIconBadgeBg =
                (CachingIconView) findViewById(R.id.conversation_icon_badge_bg);
        this.mIcon.setOnVisibilityChangedListener(
                new Consumer() { // from class:
                                 // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ConversationLayout.this.lambda$onFinishInflate$0((Integer) obj);
                    }
                });
        this.mIcon.setOnForceHiddenChangedListener(
                new Consumer() { // from class:
                                 // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ConversationLayout.this.lambda$onFinishInflate$1((Boolean) obj);
                    }
                });
        this.mConversationIconView.setOnForceHiddenChangedListener(
                new Consumer() { // from class:
                                 // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ConversationLayout.this.lambda$onFinishInflate$2((Boolean) obj);
                    }
                });
        this.mConversationText = (TextView) findViewById(R.id.conversation_text);
        this.mExpandButtonContainer = findViewById(R.id.expand_button_container);
        this.mExpandButtonContainerA11yContainer =
                (ViewGroup) findViewById(R.id.expand_button_a11y_container);
        this.mConversationHeader = findViewById(R.id.conversation_header);
        this.mContentContainer = findViewById(R.id.notification_action_list_margin_target);
        this.mExpandButtonAndContentContainer =
                (ViewGroup) findViewById(R.id.expand_button_and_content_container);
        this.mExpandButton = (NotificationExpandButton) findViewById(R.id.expand_button);
        this.mMessageSpacingStandard =
                getResources().getDimensionPixelSize(R.dimen.notification_messaging_spacing);
        this.mMessageSpacingGroup =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.notification_messaging_spacing_conversation_group);
        this.mNotificationHeaderExpandedPadding =
                getResources()
                        .getDimensionPixelSize(R.dimen.conversation_header_expanded_padding_end);
        this.mContentMarginEnd =
                getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        this.mBadgeProtrusion =
                getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion);
        this.mConversationAvatarSize =
                getResources().getDimensionPixelSize(R.dimen.conversation_avatar_size);
        this.mConversationAvatarSizeExpanded =
                getResources()
                        .getDimensionPixelSize(R.dimen.conversation_avatar_size_group_expanded);
        this.mConversationIconTopPaddingExpandedGroup =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_icon_container_top_padding_small_avatar);
        this.mConversationBadgeSize =
                getResources().getDimensionPixelSize(R.dimen.conversation_icon_size_badged);
        this.mConversationBadgeSizeExpanded =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_icon_size_badged_group_expanded);
        this.mConversationBadgeMargin =
                getResources().getDimensionPixelSize(R.dimen.conversation_badge_side_margin);
        this.mConversationBadgeMarginExpanded =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_badge_side_margin_group_expanded);
        this.mConversationIconTopPadding =
                getResources()
                        .getDimensionPixelSize(R.dimen.conversation_icon_container_top_padding);
        this.mExpandedGroupMessagePadding =
                getResources()
                        .getDimensionPixelSize(R.dimen.expanded_group_conversation_message_padding);
        this.mExpandedGroupBadgeProtrusion =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_badge_protrusion_group_expanded);
        this.mExpandedGroupBadgeProtrusionFacePile =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_badge_protrusion_group_expanded_face_pile);
        this.mConversationFacePile = findViewById(R.id.conversation_face_pile);
        this.mFacePileAvatarSize =
                getResources().getDimensionPixelSize(R.dimen.conversation_face_pile_avatar_size);
        this.mFacePileAvatarSizeExpandedGroup =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_face_pile_avatar_size_group_expanded);
        this.mFacePileProtectionWidth =
                getResources()
                        .getDimensionPixelSize(R.dimen.conversation_face_pile_protection_width);
        this.mFacePileProtectionWidthExpanded =
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.conversation_face_pile_protection_width_expanded);
        this.mFallbackChatName =
                getResources().getString(R.string.conversation_title_fallback_one_to_one);
        this.mFallbackGroupChatName =
                getResources().getString(R.string.conversation_title_fallback_group_chat);
        this.mAppName = (ObservableTextView) findViewById(R.id.app_name_text);
        this.mAppNameDivider = findViewById(R.id.app_name_divider);
        this.mAppNameGone = this.mAppName.getVisibility() == 8;
        this.mAppName.setOnVisibilityChangedListener(
                new Consumer() { // from class:
                                 // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ConversationLayout.this.lambda$onFinishInflate$3((Integer) obj);
                    }
                });
        this.mConversationStartMargin =
                getResources().getDimensionPixelSize(R.dimen.notification_content_margin_start);
        this.mConversationTopMargin =
                getFontScaledMarginHeight(
                        this.mContext, R.dimen.conversation_expand_button_top_margin_expanded);
        this.mConversationMinHeight =
                getResources().getDimensionPixelSize(R.dimen.conversation_expand_button_height);
    }

    public /* synthetic */ void lambda$onFinishInflate$0(Integer visibility) {
        boolean isGone = visibility.intValue() == 8;
        int oldVisibility = this.mConversationIconBadgeBg.getVisibility();
        boolean wasGone = oldVisibility == 8;
        if (wasGone != isGone) {
            this.mConversationIconBadgeBg.animate().cancel();
        }
        int oldVisibility2 = this.mImportanceRingView.getVisibility();
        boolean wasGone2 = oldVisibility2 == 8;
        Integer visibility2 =
                Integer.valueOf(!this.mImportantConversation ? 8 : visibility.intValue());
        boolean isRingGone = visibility2.intValue() == 8;
        if (wasGone2 != isRingGone) {
            this.mImportanceRingView.animate().cancel();
            this.mImportanceRingView.setVisibility(visibility2.intValue());
        }
        int oldVisibility3 = this.mConversationIconBadge.getVisibility();
        boolean wasGone3 = oldVisibility3 == 8;
        if (wasGone3 != isGone) {
            this.mConversationIconBadge.animate().cancel();
            this.mConversationIconBadge.setVisibility(visibility2.intValue());
        }
    }

    public /* synthetic */ void lambda$onFinishInflate$1(Boolean forceHidden) {
        this.mPeopleHelper.animateViewForceHidden(
                this.mConversationIconBadgeBg, forceHidden.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(
                this.mImportanceRingView, forceHidden.booleanValue());
    }

    public /* synthetic */ void lambda$onFinishInflate$2(Boolean forceHidden) {
        this.mPeopleHelper.animateViewForceHidden(
                this.mConversationIconBadgeBg, forceHidden.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(
                this.mImportanceRingView, forceHidden.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mIcon, forceHidden.booleanValue());
    }

    public /* synthetic */ void lambda$onFinishInflate$3(Integer visibility) {
        onAppNameVisibilityChanged();
    }

    @RemotableViewMethod
    public void setAvatarReplacement(Icon icon) {
        this.mAvatarReplacement = icon;
    }

    @RemotableViewMethod
    public void setNameReplacement(CharSequence nameReplacement) {
        this.mNameReplacement = nameReplacement;
    }

    @RemotableViewMethod
    public void setIsImportantConversation(boolean isImportantConversation) {
        setIsImportantConversation(isImportantConversation, false);
    }

    public void setIsImportantConversation(boolean isImportantConversation, boolean animate) {
        this.mImportantConversation = isImportantConversation;
        CachingIconView cachingIconView = this.mImportanceRingView;
        int i = 8;
        if (isImportantConversation && this.mIcon.getVisibility() != 8) {
            i = 0;
        }
        cachingIconView.setVisibility(i);
        if (animate && isImportantConversation) {
            final GradientDrawable ring = (GradientDrawable) this.mImportanceRingView.getDrawable();
            ring.mutate();
            final GradientDrawable bg =
                    (GradientDrawable) this.mConversationIconBadgeBg.getDrawable();
            bg.mutate();
            final int ringColor = getResources().getColor(R.color.conversation_important_highlight);
            int standardThickness =
                    getResources().getDimensionPixelSize(R.dimen.importance_ring_stroke_width);
            int largeThickness =
                    getResources()
                            .getDimensionPixelSize(R.dimen.importance_ring_anim_max_stroke_width);
            int standardSize = getResources().getDimensionPixelSize(R.dimen.importance_ring_size);
            final int baseSize = standardSize - (standardThickness * 2);
            final int bgSize =
                    getResources().getDimensionPixelSize(R.dimen.conversation_icon_size_badged);
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda5
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            ConversationLayout.this.lambda$setIsImportantConversation$4(
                                    ring, ringColor, baseSize, valueAnimator);
                        }
                    };
            ValueAnimator growAnimation = ValueAnimator.ofFloat(0.0f, largeThickness);
            growAnimation.setInterpolator(LINEAR_OUT_SLOW_IN);
            growAnimation.setDuration(250L);
            growAnimation.addUpdateListener(animatorUpdateListener);
            ValueAnimator shrinkAnimation =
                    ValueAnimator.ofFloat(largeThickness, standardThickness);
            shrinkAnimation.setDuration(200L);
            shrinkAnimation.setStartDelay(25L);
            shrinkAnimation.setInterpolator(OVERSHOOT);
            shrinkAnimation.addUpdateListener(animatorUpdateListener);
            shrinkAnimation.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.android.internal.widget.ConversationLayout.1
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            bg.setSize(baseSize, baseSize);
                            ConversationLayout.this.mConversationIconBadgeBg.invalidate();
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            bg.setSize(bgSize, bgSize);
                            ConversationLayout.this.mConversationIconBadgeBg.invalidate();
                        }
                    });
            AnimatorSet anims = new AnimatorSet();
            anims.playSequentially(growAnimation, shrinkAnimation);
            anims.start();
        }
    }

    public /* synthetic */ void lambda$setIsImportantConversation$4(
            GradientDrawable ring, int ringColor, int baseSize, ValueAnimator animation) {
        int strokeWidth = Math.round(((Float) animation.getAnimatedValue()).floatValue());
        ring.setStroke(strokeWidth, ringColor);
        int newSize = (strokeWidth * 2) + baseSize;
        ring.setSize(newSize, newSize);
        this.mImportanceRingView.invalidate();
    }

    public boolean isImportantConversation() {
        return this.mImportantConversation;
    }

    @RemotableViewMethod
    public void setIsCollapsed(boolean isCollapsed) {
        this.mIsCollapsed = isCollapsed;
        this.mMessagingLinearLayout.setMaxDisplayedLines(isCollapsed ? 2 : Integer.MAX_VALUE);
        updateExpandButton();
        updateContentEndPaddings();
    }

    @RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$5(Bundle extras) {
        bind(parseMessagingData(extras, false, false));
    }

    private MessagingData parseMessagingData(
            Bundle extras, boolean usePrecomputedText, boolean includeConversationIcon) {
        ConversationHeaderData conversationHeaderData;
        Parcelable[] messages = extras.getParcelableArray(Notification.EXTRA_MESSAGES);
        List<Notification.MessagingStyle.Message> newMessages =
                Notification.MessagingStyle.Message.getMessagesFromBundleArray(messages);
        Parcelable[] histMessages = extras.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES);
        List<Notification.MessagingStyle.Message> newHistoricMessages =
                Notification.MessagingStyle.Message.getMessagesFromBundleArray(histMessages);
        Person user =
                (Person) extras.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
        RemoteInputHistoryItem[] history =
                (RemoteInputHistoryItem[])
                        extras.getParcelableArray(
                                Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS,
                                RemoteInputHistoryItem.class);
        addRemoteInputHistoryToMessages(newMessages, history);
        boolean showSpinner =
                extras.getBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, false);
        int unreadCount = extras.getInt(Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT);
        List<MessagingMessage> newMessagingMessages =
                createMessages(newMessages, false, usePrecomputedText);
        List<MessagingMessage> newHistoricMessagingMessages =
                createMessages(newHistoricMessages, true, usePrecomputedText);
        List<List<MessagingMessage>> groups = new ArrayList<>();
        List<Person> senders = new ArrayList<>();
        findGroups(newHistoricMessagingMessages, newMessagingMessages, user, groups, senders);
        if (includeConversationIcon && Flags.conversationStyleSetAvatarAsync()) {
            conversationHeaderData =
                    loadConversationHeaderData(
                            this.mIsOneToOne,
                            this.mConversationTitle,
                            this.mShortcutIcon,
                            this.mLargeIcon,
                            newMessagingMessages,
                            user,
                            groups,
                            this.mLayoutColor);
        } else {
            conversationHeaderData = null;
        }
        return new MessagingData(
                user,
                showSpinner,
                unreadCount,
                newHistoricMessagingMessages,
                newMessagingMessages,
                groups,
                senders,
                conversationHeaderData);
    }

    public Runnable setDataAsync(final Bundle extras) {
        if (!this.mPrecomputedTextEnabled) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    ConversationLayout.this.lambda$setDataAsync$5(extras);
                }
            };
        }
        final MessagingData messagingData = parseMessagingData(extras, true, true);
        return new Runnable() { // from class:
                                // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ConversationLayout.this.lambda$setDataAsync$6(messagingData);
            }
        };
    }

    public /* synthetic */ void lambda$setDataAsync$6(MessagingData messagingData) {
        finalizeInflate(messagingData.getHistoricMessagingMessages());
        finalizeInflate(messagingData.getNewMessagingMessages());
        bind(messagingData);
    }

    public void setPrecomputedTextEnabled(boolean precomputedTextEnabled) {
        this.mPrecomputedTextEnabled = precomputedTextEnabled;
    }

    private void finalizeInflate(List<MessagingMessage> historicMessagingMessages) {
        for (MessagingMessage messagingMessage : historicMessagingMessages) {
            messagingMessage.finalizeInflate();
        }
    }

    @Override // com.android.internal.widget.ImageMessageConsumer
    public void setImageResolver(ImageResolver resolver) {
        this.mImageResolver = resolver;
    }

    public void setUnreadCount(int unreadCount) {
        this.mExpandButton.setNumber(unreadCount);
    }

    private void addRemoteInputHistoryToMessages(
            List<Notification.MessagingStyle.Message> newMessages,
            RemoteInputHistoryItem[] remoteInputHistory) {
        if (remoteInputHistory == null || remoteInputHistory.length == 0) {
            return;
        }
        for (int i = remoteInputHistory.length - 1; i >= 0; i--) {
            RemoteInputHistoryItem historyMessage = remoteInputHistory[i];
            Notification.MessagingStyle.Message message =
                    new Notification.MessagingStyle.Message(
                            historyMessage.getText(), 0L, null, true);
            if (historyMessage.getUri() != null) {
                message.setData(historyMessage.getMimeType(), historyMessage.getUri());
            }
            newMessages.add(message);
        }
    }

    private void bind(MessagingData messagingData) {
        setUser(messagingData.getUser());
        setUnreadCount(messagingData.getUnreadCount());
        ArrayList<MessagingGroup> oldGroups = new ArrayList<>(this.mGroups);
        createGroupViews(
                messagingData.getGroups(),
                messagingData.getSenders(),
                messagingData.getShowSpinner());
        removeGroups(oldGroups);
        for (MessagingMessage message : this.mMessages) {
            message.removeMessage(this.mToRecycle);
        }
        for (MessagingMessage historicMessage : this.mHistoricMessages) {
            historicMessage.removeMessage(this.mToRecycle);
        }
        this.mMessages = messagingData.getNewMessagingMessages();
        this.mHistoricMessages = messagingData.getHistoricMessagingMessages();
        updateHistoricMessageVisibility();
        updateTitleAndNamesDisplay();
        updateConversationLayout(messagingData);
        Iterator<MessagingLinearLayout.MessagingChild> it = this.mToRecycle.iterator();
        while (it.hasNext()) {
            MessagingLinearLayout.MessagingChild child = it.next();
            child.recycle();
        }
        this.mToRecycle.clear();
    }

    private void updateConversationLayout(MessagingData messagingData) {
        if (!Flags.conversationStyleSetAvatarAsync()) {
            computeAndSetConversationAvatarAndName();
        } else {
            ConversationHeaderData conversationHeaderData =
                    messagingData.getConversationHeaderData();
            if (conversationHeaderData == null) {
                conversationHeaderData =
                        loadConversationHeaderData(
                                this.mIsOneToOne,
                                this.mConversationTitle,
                                this.mShortcutIcon,
                                this.mLargeIcon,
                                this.mMessages,
                                this.mUser,
                                messagingData.getGroups(),
                                this.mLayoutColor);
            }
            setConversationAvatarAndNameFromData(conversationHeaderData);
        }
        updateAppName();
        updateIconPositionAndSize();
        updateImageMessages();
        updatePaddingsBasedOnContentAvailability();
        updateActionListPadding();
        updateAppNameDividerVisibility();
    }

    @Deprecated
    private void computeAndSetConversationAvatarAndName() {
        CharSequence conversationText = this.mConversationTitle;
        this.mConversationIcon = this.mShortcutIcon;
        if (this.mIsOneToOne) {
            CharSequence userKey = getKey(this.mUser);
            for (int i = this.mGroups.size() - 1; i >= 0; i--) {
                MessagingGroup messagingGroup = this.mGroups.get(i);
                Person messageSender = messagingGroup.getSender();
                if ((messageSender != null && !TextUtils.equals(userKey, getKey(messageSender)))
                        || i == 0) {
                    if (TextUtils.isEmpty(conversationText)) {
                        conversationText = messagingGroup.getSenderName();
                    }
                    if (this.mConversationIcon == null) {
                        Icon avatarIcon = messagingGroup.getAvatarIcon();
                        if (avatarIcon == null) {
                            avatarIcon =
                                    this.mPeopleHelper.createAvatarSymbol(
                                            conversationText, "", this.mLayoutColor);
                        }
                        this.mConversationIcon = avatarIcon;
                    }
                }
            }
        }
        if (this.mConversationIcon == null) {
            this.mConversationIcon = this.mLargeIcon;
        }
        if (this.mIsOneToOne || this.mConversationIcon != null) {
            this.mConversationIconView.setVisibility(0);
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.setImageIcon(this.mConversationIcon);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            this.mConversationFacePile = findViewById(R.id.conversation_face_pile);
            bindFacePile();
        }
        if (TextUtils.isEmpty(conversationText)) {
            conversationText =
                    this.mIsOneToOne ? this.mFallbackChatName : this.mFallbackGroupChatName;
        }
        this.mConversationText.lambda$setTextAsync$0(conversationText);
        this.mPeopleHelper.maybeHideFirstSenderName(
                this.mGroups, this.mIsOneToOne, conversationText);
    }

    private void setConversationAvatarAndNameFromData(
            ConversationHeaderData conversationHeaderData) {
        ConversationAvatarData.OneToOneConversationAvatarData oneToOneConversationDrawable;
        ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData;
        this.mConversationHeaderData = conversationHeaderData;
        ConversationAvatarData conversationAvatar = conversationHeaderData.getConversationAvatar();
        if (conversationAvatar instanceof ConversationAvatarData.OneToOneConversationAvatarData) {
            oneToOneConversationDrawable =
                    (ConversationAvatarData.OneToOneConversationAvatarData) conversationAvatar;
            groupConversationAvatarData = null;
        } else {
            oneToOneConversationDrawable = null;
            groupConversationAvatarData =
                    (ConversationAvatarData.GroupConversationAvatarData) conversationAvatar;
        }
        if (oneToOneConversationDrawable != null) {
            this.mConversationIconView.setVisibility(0);
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.lambda$setImageURIAsync$0(
                    oneToOneConversationDrawable.mDrawable);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            this.mConversationFacePile = findViewById(R.id.conversation_face_pile);
            bindFacePile(groupConversationAvatarData);
        }
        CharSequence conversationText = conversationHeaderData.getConversationText();
        if (TextUtils.isEmpty(conversationText)) {
            conversationText =
                    this.mIsOneToOne ? this.mFallbackChatName : this.mFallbackGroupChatName;
        }
        this.mConversationText.lambda$setTextAsync$0(conversationText);
        this.mPeopleHelper.maybeHideFirstSenderName(
                this.mGroups, this.mIsOneToOne, conversationText);
    }

    private void updateActionListPadding() {
        if (this.mActions != null) {
            this.mActions.setCollapsibleIndentDimen(R.dimen.call_notification_collapsible_indent);
        }
    }

    private void updateImageMessages() {
        View newMessage = null;
        if (this.mIsCollapsed && this.mGroups.size() > 0) {
            MessagingGroup messagingGroup = this.mGroups.get(this.mGroups.size() - 1);
            MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
            if (isolatedMessage != null) {
                newMessage = isolatedMessage.getView();
            }
        }
        View previousMessage = this.mImageMessageContainer.getChildAt(0);
        if (previousMessage != newMessage) {
            this.mImageMessageContainer.removeView(previousMessage);
            if (newMessage != null) {
                this.mImageMessageContainer.addView(newMessage);
            }
        }
        this.mImageMessageContainer.setVisibility(newMessage == null ? 8 : 0);
    }

    public void bindFacePile(ImageView bottomBackground, ImageView bottomView, ImageView topView) {
        bottomBackground.setImageTintList(ColorStateList.valueOf(0));
        Icon secondLastIcon = null;
        CharSequence lastKey = null;
        Icon lastIcon = null;
        CharSequence userKey = getKey(this.mUser);
        int i = this.mGroups.size() - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            MessagingGroup messagingGroup = this.mGroups.get(i);
            Person messageSender = messagingGroup.getSender();
            boolean notUser =
                    (messageSender == null || TextUtils.equals(userKey, getKey(messageSender)))
                            ? false
                            : true;
            boolean notIncluded =
                    (messageSender == null || TextUtils.equals(lastKey, getKey(messageSender)))
                            ? false
                            : true;
            if ((notUser && notIncluded) || (i == 0 && lastKey == null)) {
                if (lastIcon == null) {
                    lastIcon = messagingGroup.getAvatarIcon();
                    lastKey = getKey(messageSender);
                } else {
                    secondLastIcon = messagingGroup.getAvatarIcon();
                    break;
                }
            }
            i--;
        }
        if (lastIcon == null) {
            lastIcon = this.mPeopleHelper.createAvatarSymbol(" ", "", this.mLayoutColor);
        }
        bottomView.setImageIcon(lastIcon);
        if (secondLastIcon == null) {
            secondLastIcon = this.mPeopleHelper.createAvatarSymbol("", "", this.mLayoutColor);
        }
        topView.setImageIcon(secondLastIcon);
    }

    @Deprecated
    private void bindFacePile() {
        bindFacePile(null);
    }

    private void bindFacePile(
            ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData) {
        int conversationAvatarSize;
        int facepileAvatarSize;
        int facePileBackgroundSize;
        ImageView bottomBackground =
                (ImageView)
                        this.mConversationFacePile.findViewById(
                                R.id.conversation_face_pile_bottom_background);
        ImageView bottomView =
                (ImageView)
                        this.mConversationFacePile.findViewById(R.id.conversation_face_pile_bottom);
        ImageView topView =
                (ImageView)
                        this.mConversationFacePile.findViewById(R.id.conversation_face_pile_top);
        if (groupConversationAvatarData == null) {
            bindFacePile(bottomBackground, bottomView, topView);
        } else {
            bindFacePileWithDrawable(
                    bottomBackground, bottomView, topView, groupConversationAvatarData);
        }
        if (this.mIsCollapsed) {
            conversationAvatarSize = this.mConversationAvatarSize;
            facepileAvatarSize = this.mFacePileAvatarSize;
            facePileBackgroundSize = (this.mFacePileProtectionWidth * 2) + facepileAvatarSize;
        } else {
            conversationAvatarSize = this.mConversationAvatarSizeExpanded;
            facepileAvatarSize = this.mFacePileAvatarSizeExpandedGroup;
            facePileBackgroundSize =
                    (this.mFacePileProtectionWidthExpanded * 2) + facepileAvatarSize;
        }
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.mConversationFacePile.getLayoutParams();
        layoutParams.width = conversationAvatarSize;
        layoutParams.height = conversationAvatarSize;
        this.mConversationFacePile.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 =
                (FrameLayout.LayoutParams) bottomView.getLayoutParams();
        layoutParams2.width = facepileAvatarSize;
        layoutParams2.height = facepileAvatarSize;
        bottomView.setLayoutParams(layoutParams2);
        FrameLayout.LayoutParams layoutParams3 =
                (FrameLayout.LayoutParams) topView.getLayoutParams();
        layoutParams3.width = facepileAvatarSize;
        layoutParams3.height = facepileAvatarSize;
        topView.setLayoutParams(layoutParams3);
        FrameLayout.LayoutParams layoutParams4 =
                (FrameLayout.LayoutParams) bottomBackground.getLayoutParams();
        layoutParams4.width = facePileBackgroundSize;
        layoutParams4.height = facePileBackgroundSize;
        bottomBackground.setLayoutParams(layoutParams4);
    }

    public void bindFacePileWithDrawable(
            ImageView bottomBackground,
            ImageView bottomView,
            ImageView topView,
            ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData) {
        applyNotificationBackgroundColor(bottomBackground);
        bottomView.lambda$setImageURIAsync$0(groupConversationAvatarData.mLastIcon);
        topView.lambda$setImageURIAsync$0(groupConversationAvatarData.mSecondLastIcon);
    }

    private void updateAppName() {
        this.mAppName.setVisibility(8);
    }

    public boolean shouldHideAppName() {
        return this.mIsCollapsed;
    }

    private void updateIconPositionAndSize() {
        int badgeProtrusion;
        int conversationAvatarSize;
        int conversationBadgeSize;
        int conversationBadgeMargin;
        if (this.mIsOneToOne || this.mIsCollapsed) {
            badgeProtrusion = this.mBadgeProtrusion;
            conversationAvatarSize = this.mConversationAvatarSize;
            conversationBadgeSize = this.mConversationBadgeSize;
            conversationBadgeMargin = this.mConversationBadgeMargin;
        } else {
            if (this.mConversationFacePile.getVisibility() == 0) {
                badgeProtrusion = this.mExpandedGroupBadgeProtrusionFacePile;
            } else {
                badgeProtrusion = this.mExpandedGroupBadgeProtrusion;
            }
            conversationAvatarSize = this.mConversationAvatarSizeExpanded;
            conversationBadgeSize = this.mConversationBadgeSizeExpanded;
            conversationBadgeMargin = this.mConversationBadgeMarginExpanded;
        }
        if (this.mConversationIconView.getVisibility() == 0) {
            FrameLayout.LayoutParams layoutParams =
                    (FrameLayout.LayoutParams) this.mConversationIconView.getLayoutParams();
            layoutParams.width = conversationAvatarSize;
            layoutParams.height = conversationAvatarSize;
            layoutParams.leftMargin = badgeProtrusion;
            layoutParams.rightMargin = badgeProtrusion;
            layoutParams.bottomMargin = badgeProtrusion;
            this.mConversationIconView.setLayoutParams(layoutParams);
        }
        if (this.mConversationIconBadge.getVisibility() == 0) {
            FrameLayout.LayoutParams layoutParams2 =
                    (FrameLayout.LayoutParams) this.mConversationIconBadge.getLayoutParams();
            layoutParams2.width = conversationBadgeSize;
            layoutParams2.height = conversationBadgeSize;
            layoutParams2.leftMargin = conversationBadgeMargin;
            layoutParams2.topMargin = conversationBadgeMargin;
            this.mConversationIconBadge.setLayoutParams(layoutParams2);
        }
        if (this.mConversationFacePile.getVisibility() == 0) {
            FrameLayout.LayoutParams layoutParams3 =
                    (FrameLayout.LayoutParams) this.mConversationFacePile.getLayoutParams();
            layoutParams3.leftMargin = badgeProtrusion;
            layoutParams3.rightMargin = badgeProtrusion;
            layoutParams3.bottomMargin = badgeProtrusion;
            this.mConversationFacePile.setLayoutParams(layoutParams3);
        }
    }

    private void updatePaddingsBasedOnContentAvailability() {
        int messagingPadding;
        int iconPadding;
        this.mMessagingLinearLayout.setSpacing(
                this.mIsOneToOne ? this.mMessageSpacingStandard : this.mMessageSpacingGroup);
        if (this.mIsOneToOne || this.mIsCollapsed) {
            messagingPadding = 0;
        } else {
            messagingPadding = this.mExpandedGroupMessagePadding;
        }
        if (this.mIsOneToOne || this.mIsCollapsed) {
            iconPadding = this.mConversationIconTopPadding;
        } else {
            iconPadding = this.mConversationIconTopPaddingExpandedGroup;
        }
        this.mConversationIconContainer.setPaddingRelative(
                this.mConversationIconContainer.getPaddingStart(),
                iconPadding,
                this.mConversationIconContainer.getPaddingEnd(),
                this.mConversationIconContainer.getPaddingBottom());
        this.mMessagingLinearLayout.setPaddingRelative(
                this.mMessagingLinearLayout.getPaddingStart(),
                messagingPadding,
                this.mMessagingLinearLayout.getPaddingEnd(),
                this.mMessagingLinearLayout.getPaddingBottom());
        boolean isGroupExpanded = (this.mIsCollapsed || this.mIsOneToOne) ? false : true;
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) this.mMessagingLinearLayout.getLayoutParams();
        params.leftMargin = isGroupExpanded ? 0 : this.mConversationStartMargin;
        this.mMessagingLinearLayout.setLayoutParams(params);
    }

    @RemotableViewMethod
    public Runnable setLargeIconAsync(final Icon largeIcon) {
        if (!Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    ConversationLayout.this.lambda$setLargeIconAsync$7(largeIcon);
                }
            };
        }
        this.mLargeIcon = largeIcon;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setLargeIconAsync")
    /* renamed from: setLargeIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setLargeIconAsync$7(Icon largeIcon) {
        this.mLargeIcon = largeIcon;
    }

    @RemotableViewMethod
    public Runnable setShortcutIconAsync(final Icon shortcutIcon) {
        if (!Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ConversationLayout.this.lambda$setShortcutIconAsync$8(shortcutIcon);
                }
            };
        }
        this.mShortcutIcon = shortcutIcon;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setShortcutIconAsync")
    /* renamed from: setShortcutIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setShortcutIconAsync$8(Icon shortcutIcon) {
        this.mShortcutIcon = shortcutIcon;
    }

    @RemotableViewMethod
    public Runnable setConversationTitleAsync(final CharSequence conversationTitle) {
        if (!Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    ConversationLayout.this.lambda$setConversationTitleAsync$9(conversationTitle);
                }
            };
        }
        this.mConversationTitle = conversationTitle != null ? conversationTitle.toString() : null;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setConversationTitleAsync")
    /* renamed from: setConversationTitle, reason: merged with bridge method [inline-methods] */
    public void lambda$setConversationTitleAsync$9(CharSequence conversationTitle) {
        this.mConversationTitle = conversationTitle != null ? conversationTitle.toString() : null;
    }

    public CharSequence getConversationTitle() {
        return this.mConversationText.getText();
    }

    private void removeGroups(ArrayList<MessagingGroup> oldGroups) {
        int size = oldGroups.size();
        for (int i = 0; i < size; i++) {
            final MessagingGroup group = oldGroups.get(i);
            if (!this.mGroups.contains(group)) {
                List<MessagingMessage> messages = group.getMessages();
                boolean wasShown = group.isShown();
                this.mMessagingLinearLayout.removeView(group);
                if (wasShown && !MessagingLinearLayout.isGone(group)) {
                    this.mMessagingLinearLayout.addTransientView(group, 0);
                    group.removeGroupAnimated(
                            new Runnable() { // from class:
                                             // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda11
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ConversationLayout.this.lambda$removeGroups$10(group);
                                }
                            });
                } else {
                    this.mToRecycle.add(group);
                }
                this.mMessages.removeAll(messages);
                this.mHistoricMessages.removeAll(messages);
            }
        }
    }

    public /* synthetic */ void lambda$removeGroups$10(MessagingGroup group) {
        this.mMessagingLinearLayout.removeTransientView(group);
        group.recycle();
    }

    private void updateTitleAndNamesDisplay() {
        Map<CharSequence, String> uniqueNames =
                this.mPeopleHelper.mapUniqueNamesToPrefix(this.mGroups);
        ArrayMap<CharSequence, Icon> cachedAvatars = new ArrayMap<>();
        for (int i = 0; i < this.mGroups.size(); i++) {
            MessagingGroup group = this.mGroups.get(i);
            boolean isOwnMessage = group.getSender() == this.mUser;
            CharSequence senderName = group.getSenderName();
            if (group.needsGeneratedAvatar()
                    && !TextUtils.isEmpty(senderName)
                    && (!this.mIsOneToOne || this.mAvatarReplacement == null || isOwnMessage)) {
                String symbol = uniqueNames.get(senderName);
                Icon cachedIcon =
                        group.getAvatarSymbolIfMatching(senderName, symbol, this.mLayoutColor);
                if (cachedIcon != null) {
                    cachedAvatars.put(senderName, cachedIcon);
                }
            }
        }
        for (int i2 = 0; i2 < this.mGroups.size(); i2++) {
            MessagingGroup group2 = this.mGroups.get(i2);
            CharSequence senderName2 = group2.getSenderName();
            if (group2.needsGeneratedAvatar() && !TextUtils.isEmpty(senderName2)) {
                if (this.mIsOneToOne
                        && this.mAvatarReplacement != null
                        && group2.getSender() != this.mUser) {
                    group2.setAvatar(this.mAvatarReplacement);
                } else {
                    Icon cachedIcon2 = cachedAvatars.get(senderName2);
                    if (cachedIcon2 == null) {
                        cachedIcon2 =
                                this.mPeopleHelper.createAvatarSymbol(
                                        senderName2,
                                        uniqueNames.get(senderName2),
                                        this.mLayoutColor);
                        cachedAvatars.put(senderName2, cachedIcon2);
                    }
                    group2.setCreatedAvatar(
                            cachedIcon2,
                            senderName2,
                            uniqueNames.get(senderName2),
                            this.mLayoutColor);
                }
            }
        }
    }

    @RemotableViewMethod
    public Runnable setLayoutColorAsync(final int color) {
        if (!Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ConversationLayout.this.lambda$setLayoutColorAsync$11(color);
                }
            };
        }
        this.mLayoutColor = color;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    /* renamed from: setLayoutColor, reason: merged with bridge method [inline-methods] */
    public void lambda$setLayoutColorAsync$11(int color) {
        this.mLayoutColor = color;
    }

    @RemotableViewMethod
    public Runnable setIsOneToOneAsync(final boolean oneToOne) {
        if (!Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ConversationLayout.this.lambda$setIsOneToOneAsync$12(oneToOne);
                }
            };
        }
        this.mIsOneToOne = oneToOne;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setIsOneToOneAsync")
    /* renamed from: setIsOneToOne, reason: merged with bridge method [inline-methods] */
    public void lambda$setIsOneToOneAsync$12(boolean oneToOne) {
        this.mIsOneToOne = oneToOne;
    }

    @RemotableViewMethod
    public void setSenderTextColor(int color) {
        this.mSenderTextColor = color;
        this.mConversationText.setTextColor(color);
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int color) {
        this.mNotificationBackgroundColor = color;
        applyNotificationBackgroundColor(this.mConversationIconBadgeBg);
    }

    private void applyNotificationBackgroundColor(ImageView view) {
        view.setImageTintList(ColorStateList.valueOf(this.mNotificationBackgroundColor));
    }

    @RemotableViewMethod
    public void setMessageTextColor(int color) {
        this.mMessageTextColor = color;
    }

    private void setUser(Person user) {
        this.mUser = user;
        if (this.mUser.getIcon() == null) {
            Icon userIcon = Icon.createWithResource(getContext(), R.drawable.messaging_user);
            userIcon.setTint(this.mLayoutColor);
            this.mUser = this.mUser.toBuilder().setIcon(userIcon).build();
        }
    }

    private void createGroupViews(
            List<List<MessagingMessage>> groups, List<Person> senders, boolean showSpinner) {
        int i;
        this.mGroups.clear();
        int groupIndex = 0;
        while (groupIndex < groups.size()) {
            List<MessagingMessage> group = groups.get(groupIndex);
            MessagingGroup newGroup = null;
            for (int messageIndex = group.size() - 1; messageIndex >= 0; messageIndex--) {
                MessagingMessage message = group.get(messageIndex);
                newGroup = message.getGroup();
                if (newGroup != null) {
                    break;
                }
            }
            if (newGroup == null) {
                newGroup = MessagingGroup.createGroup(this.mMessagingLinearLayout);
                if (this.mAddedQueue.size() < 10) {
                    this.mAddedQueue.add(newGroup);
                } else {
                    this.mAddedQueue.remove();
                    this.mAddedQueue.add(newGroup);
                }
            } else if (newGroup.getParent() != this.mMessagingLinearLayout) {
                throw new IllegalStateException(
                        "group parent was "
                                + newGroup.getParent()
                                + " but expected "
                                + this.mMessagingLinearLayout);
            }
            if (this.mIsCollapsed) {
                i = 2;
            } else {
                i = 0;
            }
            newGroup.setImageDisplayLocation(i);
            newGroup.setIsInConversation(true);
            newGroup.setLayoutColor(this.mLayoutColor);
            newGroup.setTextColors(this.mSenderTextColor, this.mMessageTextColor);
            Person sender = senders.get(groupIndex);
            CharSequence nameOverride = null;
            if (sender != this.mUser && this.mNameReplacement != null) {
                nameOverride = this.mNameReplacement;
            }
            newGroup.setShowingAvatar((this.mIsOneToOne || this.mIsCollapsed) ? false : true);
            newGroup.setSingleLine(false);
            newGroup.setSender(sender, nameOverride);
            newGroup.setSending(groupIndex == groups.size() - 1 && showSpinner);
            this.mGroups.add(newGroup);
            if (this.mMessagingLinearLayout.indexOfChild(newGroup) != groupIndex) {
                this.mMessagingLinearLayout.removeView(newGroup);
                this.mMessagingLinearLayout.addView(newGroup, groupIndex);
            }
            newGroup.setMessages(group);
            groupIndex++;
        }
    }

    private void findGroups(
            List<MessagingMessage> historicMessages,
            List<MessagingMessage> messages,
            Person user,
            List<List<MessagingMessage>> outGroups,
            List<Person> outSenders) {
        MessagingMessage message;
        Person sender;
        CharSequence currentSenderKey = null;
        List<MessagingMessage> currentGroup = null;
        int histSize = historicMessages.size();
        for (int i = 0; i < messages.size() + histSize; i++) {
            if (i < histSize) {
                message = historicMessages.get(i);
            } else {
                message = messages.get(i - histSize);
            }
            boolean isNewGroup = currentGroup == null;
            Person sender2 =
                    message.getMessage() == null ? null : message.getMessage().getSenderPerson();
            CharSequence key = getKey(sender2);
            if ((true ^ TextUtils.equals(key, currentSenderKey)) | isNewGroup) {
                currentGroup = new ArrayList<>();
                outGroups.add(currentGroup);
                if (sender2 == null) {
                    sender = user;
                } else {
                    sender =
                            sender2.toBuilder()
                                    .setName(Objects.toString(sender2.getName()))
                                    .build();
                }
                outSenders.add(sender);
                currentSenderKey = key;
            }
            currentGroup.add(message);
        }
    }

    private CharSequence getKey(Person person) {
        if (person == null) {
            return null;
        }
        return person.getKey() == null ? person.getName() : person.getKey();
    }

    private ConversationHeaderData loadConversationHeaderData(
            boolean isOneToOne,
            CharSequence conversationTitle,
            Icon shortcutIcon,
            Icon largeIcon,
            List<MessagingMessage> messages,
            Person user,
            List<List<MessagingMessage>> groups,
            int layoutColor) {
        CharSequence userKey;
        List<List<Notification.MessagingStyle.Message>> groupMessages;
        List<List<MessagingMessage>> list = groups;
        Icon conversationIcon = shortcutIcon;
        CharSequence conversationText = conversationTitle;
        Person person = user;
        CharSequence userKey2 = getKey(person);
        if (isOneToOne) {
            for (int i = messages.size() - 1; i >= 0; i--) {
                Person sender = messages.get(i).getMessage().getSenderPerson();
                CharSequence senderKey = getKey(sender);
                if ((sender != null && senderKey != userKey2) || i == 0) {
                    if (conversationText == null || conversationText.length() == 0) {
                        conversationText = sender != null ? sender.getName() : "";
                    }
                    if (conversationIcon == null && sender != null) {
                        conversationIcon = sender.getIcon();
                        if (sender.getIcon() == null) {
                            conversationIcon =
                                    this.mPeopleHelper.createAvatarSymbol(
                                            conversationText, "", layoutColor);
                        }
                    }
                }
            }
        }
        if (android.app.Flags.cleanUpSpansAndNewLines() && conversationText != null) {
            conversationText = conversationText.toString();
        }
        if (conversationIcon == null) {
            conversationIcon = largeIcon;
        }
        if (!isOneToOne && conversationIcon == null) {
            List<List<Notification.MessagingStyle.Message>> groupMessages2 = new ArrayList<>();
            for (int i2 = 0; i2 < groups.size(); i2++) {
                List<Notification.MessagingStyle.Message> groupMessage = new ArrayList<>();
                for (int j = 0; j < list.get(i2).size(); j++) {
                    groupMessage.add(list.get(i2).get(j).getMessage());
                }
                groupMessages2.add(groupMessage);
            }
            PeopleHelper.NameToPrefixMap nameToPrefixMap =
                    this.mPeopleHelper.mapUniqueNamesToPrefixWithGroupList(groupMessages2);
            Icon lastIcon = null;
            Icon secondLastIcon = null;
            CharSequence lastKey = null;
            int i3 = groups.size() - 1;
            while (true) {
                if (i3 < 0) {
                    break;
                }
                Notification.MessagingStyle.Message message = list.get(i3).get(0).getMessage();
                Person sender2 =
                        message.getSenderPerson() != null ? message.getSenderPerson() : person;
                CharSequence senderKey2 = getKey(sender2);
                boolean notUser = senderKey2 != userKey2;
                boolean notIncluded = senderKey2 != lastKey;
                if ((!notUser || !notIncluded) && (i3 != 0 || lastKey != null)) {
                    userKey = userKey2;
                    groupMessages = groupMessages2;
                } else if (lastIcon != null) {
                    if (sender2.getIcon() != null) {
                        secondLastIcon = sender2.getIcon();
                    } else {
                        String senderName = sender2.getName() != null ? sender2.getName() : "";
                        secondLastIcon =
                                this.mPeopleHelper.createAvatarSymbol(
                                        senderName,
                                        nameToPrefixMap.getPrefix(senderName),
                                        layoutColor);
                    }
                } else {
                    if (sender2.getIcon() != null) {
                        lastIcon = sender2.getIcon();
                        userKey = userKey2;
                        groupMessages = groupMessages2;
                    } else {
                        CharSequence senderName2 =
                                sender2.getName() != null ? sender2.getName() : "";
                        userKey = userKey2;
                        groupMessages = groupMessages2;
                        lastIcon =
                                this.mPeopleHelper.createAvatarSymbol(
                                        senderName2,
                                        nameToPrefixMap.getPrefix(senderName2),
                                        layoutColor);
                    }
                    lastKey = senderKey2;
                }
                i3--;
                person = user;
                list = groups;
                userKey2 = userKey;
                groupMessages2 = groupMessages;
            }
            if (lastIcon == null) {
                lastIcon = this.mPeopleHelper.createAvatarSymbol("", "", layoutColor);
            }
            if (secondLastIcon == null) {
                secondLastIcon = this.mPeopleHelper.createAvatarSymbol("", "", layoutColor);
            }
            return new ConversationHeaderData(
                    conversationText,
                    new ConversationAvatarData.GroupConversationAvatarData(
                            resolveAvatarImageForFacePile(lastIcon),
                            resolveAvatarImageForFacePile(secondLastIcon)));
        }
        return new ConversationHeaderData(
                conversationText,
                new ConversationAvatarData.OneToOneConversationAvatarData(
                        resolveAvatarImageForOneToOne(conversationIcon)));
    }

    private Drawable resolveAvatarImageForOneToOne(Icon conversationIcon) {
        Drawable conversationIconDrawable =
                tryLoadingSizeRestrictedIconForOneToOne(conversationIcon);
        if (conversationIconDrawable != null) {
            return conversationIconDrawable;
        }
        return loadDrawableFromIcon(conversationIcon);
    }

    private Drawable tryLoadingSizeRestrictedIconForOneToOne(Icon conversationIcon) {
        try {
            return this.mConversationIconView.loadSizeRestrictedIcon(conversationIcon);
        } catch (Exception e) {
            return null;
        }
    }

    private Drawable resolveAvatarImageForFacePile(Icon conversationIcon) {
        return loadDrawableFromIcon(conversationIcon);
    }

    private Drawable loadDrawableFromIcon(Icon conversationIcon) {
        try {
            return conversationIcon.loadDrawable(getContext());
        } catch (Exception e) {
            return null;
        }
    }

    private List<MessagingMessage> createMessages(
            List<Notification.MessagingStyle.Message> newMessages,
            boolean isHistoric,
            boolean usePrecomputedText) {
        List<MessagingMessage> result = new ArrayList<>();
        for (int i = 0; i < newMessages.size(); i++) {
            Notification.MessagingStyle.Message m = newMessages.get(i);
            MessagingMessage message = findAndRemoveMatchingMessage(m);
            if (message == null) {
                message =
                        MessagingMessage.createMessage(
                                this, m, this.mImageResolver, usePrecomputedText);
            }
            message.setIsHistoric(isHistoric);
            result.add(message);
        }
        return result;
    }

    private MessagingMessage findAndRemoveMatchingMessage(Notification.MessagingStyle.Message m) {
        for (int i = 0; i < this.mMessages.size(); i++) {
            MessagingMessage existing = this.mMessages.get(i);
            if (existing.sameAs(m)) {
                this.mMessages.remove(i);
                return existing;
            }
        }
        for (int i2 = 0; i2 < this.mHistoricMessages.size(); i2++) {
            MessagingMessage existing2 = this.mHistoricMessages.get(i2);
            if (existing2.sameAs(m)) {
                this.mHistoricMessages.remove(i2);
                return existing2;
            }
        }
        return null;
    }

    public void showHistoricMessages(boolean show) {
        this.mShowHistoricMessages = show;
        updateHistoricMessageVisibility();
    }

    private void updateHistoricMessageVisibility() {
        int numHistoric = this.mHistoricMessages.size();
        int i = 0;
        while (true) {
            int i2 = 0;
            if (i >= numHistoric) {
                break;
            }
            MessagingMessage existing = this.mHistoricMessages.get(i);
            if (!this.mShowHistoricMessages) {
                i2 = 8;
            }
            existing.setVisibility(i2);
            i++;
        }
        int numGroups = this.mGroups.size();
        for (int i3 = 0; i3 < numGroups; i3++) {
            MessagingGroup group = this.mGroups.get(i3);
            int visibleChildren = 0;
            List<MessagingMessage> messages = group.getMessages();
            int numGroupMessages = messages.size();
            for (int j = 0; j < numGroupMessages; j++) {
                MessagingMessage message = messages.get(j);
                if (message.getVisibility() != 8) {
                    visibleChildren++;
                }
            }
            if (visibleChildren > 0 && group.getVisibility() == 8) {
                group.setVisibility(0);
            } else if (visibleChildren == 0 && group.getVisibility() != 8) {
                group.setVisibility(8);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.mAddedQueue.isEmpty()) {
            getViewTreeObserver()
                    .addOnPreDrawListener(
                            new ViewTreeObserver
                                    .OnPreDrawListener() { // from class:
                                                           // com.android.internal.widget.ConversationLayout.2
                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                public boolean onPreDraw() {
                                    for (MessagingGroup group :
                                            ConversationLayout.this.mAddedQueue) {
                                        if (group.isShown()) {
                                            MessagingPropertyAnimator.fadeIn(group.getAvatar());
                                            MessagingPropertyAnimator.fadeIn(group.getSenderView());
                                            MessagingPropertyAnimator.startLocalTranslationFrom(
                                                    group,
                                                    group.getHeight(),
                                                    ConversationLayout.LINEAR_OUT_SLOW_IN);
                                        }
                                    }
                                    ConversationLayout.this.mAddedQueue.clear();
                                    ConversationLayout.this
                                            .getViewTreeObserver()
                                            .removeOnPreDrawListener(this);
                                    return true;
                                }
                            });
        }
        this.mTouchDelegate.clear();
        if (this.mFeedbackIcon.getVisibility() == 0) {
            float width = Math.max(this.mMinTouchSize, this.mFeedbackIcon.getWidth());
            float height = Math.max(this.mMinTouchSize, this.mFeedbackIcon.getHeight());
            Rect feedbackTouchRect = new Rect();
            feedbackTouchRect.left =
                    (int)
                            (((this.mFeedbackIcon.getLeft() + this.mFeedbackIcon.getRight()) / 2.0f)
                                    - (width / 2.0f));
            feedbackTouchRect.top =
                    (int)
                            (((this.mFeedbackIcon.getTop() + this.mFeedbackIcon.getBottom()) / 2.0f)
                                    - (height / 2.0f));
            feedbackTouchRect.bottom = (int) (feedbackTouchRect.top + height);
            feedbackTouchRect.right = (int) (feedbackTouchRect.left + width);
            getRelativeTouchRect(feedbackTouchRect, this.mFeedbackIcon);
            this.mTouchDelegate.add(new TouchDelegate(feedbackTouchRect, this.mFeedbackIcon));
        }
        setTouchDelegate(this.mTouchDelegate);
    }

    private void getRelativeTouchRect(Rect touchRect, View view) {
        for (ViewGroup viewGroup = (ViewGroup) view.getParent();
                viewGroup != this;
                viewGroup = (ViewGroup) viewGroup.getParent()) {
            touchRect.offset(viewGroup.getLeft(), viewGroup.getTop());
        }
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public MessagingLinearLayout getMessagingLinearLayout() {
        return this.mMessagingLinearLayout;
    }

    public ViewGroup getImageMessageContainer() {
        return this.mImageMessageContainer;
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public ArrayList<MessagingGroup> getMessagingGroups() {
        return this.mGroups;
    }

    private void updateExpandButton() {
        int buttonGravity;
        if (this.mIsCollapsed) {
            buttonGravity = 17;
        } else {
            buttonGravity = 49;
        }
        ViewGroup newContainer = this.mExpandButtonAndContentContainer;
        this.mExpandButton.setExpanded(!this.mIsCollapsed);
        if (newContainer != this.mExpandButtonContainer.getParent()) {
            ((ViewGroup) this.mExpandButtonContainer.getParent())
                    .removeView(this.mExpandButtonContainer);
            newContainer.addView(this.mExpandButtonContainer);
            setMinimumHeight(this.mConversationMinHeight);
        }
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mExpandButton.getLayoutParams();
        if (this.mIsCollapsed && this.mActions != null && this.mActions.getVisibility() == 0) {
            layoutParams.gravity = 48;
            layoutParams.topMargin = this.mConversationTopMargin;
        } else {
            layoutParams.gravity = buttonGravity;
            layoutParams.topMargin = this.mIsCollapsed ? 0 : this.mConversationTopMargin;
        }
        this.mExpandButton.setLayoutParams(layoutParams);
    }

    private void updateContentEndPaddings() {
        int headerPaddingEnd;
        int contentPaddingEnd;
        if (!this.mExpandable) {
            headerPaddingEnd = 0;
            contentPaddingEnd = this.mContentMarginEnd;
        } else if (this.mIsCollapsed) {
            headerPaddingEnd = 0;
            contentPaddingEnd = 0;
        } else {
            headerPaddingEnd = this.mNotificationHeaderExpandedPadding;
            contentPaddingEnd = this.mContentMarginEnd;
        }
        this.mConversationHeader.setPaddingRelative(
                this.mConversationHeader.getPaddingStart(),
                this.mConversationHeader.getPaddingTop(),
                headerPaddingEnd,
                this.mConversationHeader.getPaddingBottom());
        this.mContentContainer.setPaddingRelative(
                this.mContentContainer.getPaddingStart(),
                this.mContentContainer.getPaddingTop(),
                contentPaddingEnd,
                this.mContentContainer.getPaddingBottom());
    }

    private void onAppNameVisibilityChanged() {
        this.mAppName.setVisibility(8);
        boolean appNameGone = this.mAppName.getVisibility() == 8;
        if (appNameGone != this.mAppNameGone) {
            this.mAppNameGone = appNameGone;
            updateAppNameDividerVisibility();
        }
    }

    private void updateAppNameDividerVisibility() {
        this.mAppNameDivider.setVisibility(8);
    }

    public void updateExpandability(boolean expandable, View.OnClickListener onClickListener) {
        this.mExpandable = expandable;
        if (expandable) {
            this.mExpandButtonContainer.setVisibility(0);
            this.mExpandButton.setOnClickListener(onClickListener);
            this.mConversationIconContainer.setOnClickListener(onClickListener);
        } else {
            this.mExpandButtonContainer.setVisibility(8);
            this.mConversationIconContainer.setOnClickListener(null);
        }
        this.mExpandButton.setVisibility(0);
        updateContentEndPaddings();
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public void setMessagingClippingDisabled(boolean clippingDisabled) {
        this.mMessagingLinearLayout.setClipBounds(
                clippingDisabled ? null : this.mMessagingClipRect);
    }

    public CharSequence getConversationSenderName() {
        if (this.mGroups.isEmpty()) {
            return null;
        }
        CharSequence name = this.mGroups.get(this.mGroups.size() - 1).getSenderName();
        return getResources().getString(R.string.conversation_single_line_name_display, name);
    }

    public boolean isOneToOne() {
        return this.mIsOneToOne;
    }

    public CharSequence getConversationText() {
        if (this.mMessages.isEmpty()) {
            return null;
        }
        MessagingMessage messagingMessage = this.mMessages.get(this.mMessages.size() - 1);
        CharSequence text =
                messagingMessage.getMessage() != null
                        ? messagingMessage.getMessage().getText()
                        : null;
        if (text == null && (messagingMessage instanceof MessagingImageMessage)) {
            String unformatted =
                    getResources().getString(R.string.conversation_single_line_image_placeholder);
            SpannableString spannableString = new SpannableString(unformatted);
            spannableString.setSpan(new StyleSpan(2), 0, spannableString.length(), 17);
            return spannableString;
        }
        return text;
    }

    public Icon getConversationIcon() {
        return this.mConversationIcon;
    }

    public ConversationHeaderData getConversationHeaderData() {
        return this.mConversationHeaderData;
    }

    private static class TouchDelegateComposite extends TouchDelegate {
        private final ArrayList<TouchDelegate> mDelegates;

        private TouchDelegateComposite(View view) {
            super(new Rect(), view);
            this.mDelegates = new ArrayList<>();
        }

        public void add(TouchDelegate delegate) {
            this.mDelegates.add(delegate);
        }

        public void clear() {
            this.mDelegates.clear();
        }

        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            Iterator<TouchDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                TouchDelegate delegate = it.next();
                event.setLocation(x, y);
                if (delegate.onTouchEvent(event)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static int getFontScaledMarginHeight(Context context, int dimenId) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(dimenId);
        float factor =
                context.getResources().getDisplayMetrics().scaledDensity
                        / context.getResources().getDisplayMetrics().density;
        float factor2 = ((factor - 1.0f) / 2.0f) + 1.0f;
        float factor3 = dimensionPixelSize;
        return (int) (factor3 * factor2);
    }
}
