package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Flags;
import android.app.Notification;
import android.app.Person;
import android.app.RemoteInputHistoryItem;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
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
import com.android.internal.R;
import com.android.internal.widget.ConversationAvatarData;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.PeopleHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class ConversationLayout extends FrameLayout implements ImageMessageConsumer, IMessagingLayout {
    public static final int IMPORTANCE_ANIM_GROW_DURATION = 250;
    public static final int IMPORTANCE_ANIM_SHRINK_DELAY = 25;
    public static final int IMPORTANCE_ANIM_SHRINK_DURATION = 200;
    private static final int MAX_SUMMARIZATION_LINES = 3;
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
    private int mConversationUnreadCount;
    private TextView mConversationUnreadCountText;
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
    private final ArrayList<MessagingGroup> mGroups;
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
    private CharSequence mSummarizedContent;
    private final ArrayList<MessagingLinearLayout.MessagingChild> mToRecycle;
    private final TouchDelegateComposite mTouchDelegate;
    private Person mUser;
    public static final Interpolator LINEAR_OUT_SLOW_IN = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator FAST_OUT_LINEAR_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator OVERSHOOT = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);

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

    public ConversationLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
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

    public ConversationLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
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

    public ConversationLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
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
        this.mMessagingLinearLayout = (MessagingLinearLayout) findViewById(R.id.notification_messaging);
        this.mActions = (NotificationActionListLayout) findViewById(R.id.actions);
        this.mImageMessageContainer = (MessagingLinearLayout) findViewById(R.id.conversation_image_message_container);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int iMax = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMessagingClipRect = new Rect(0, 0, iMax, iMax);
        setMessagingClippingDisabled(false);
        this.mConversationIconView = (CachingIconView) findViewById(R.id.conversation_icon);
        this.mConversationIconContainer = findViewById(R.id.conversation_icon_container);
        this.mIcon = (CachingIconView) findViewById(16908294);
        this.mFeedbackIcon = findViewById(R.id.feedback);
        this.mMinTouchSize = getResources().getDisplayMetrics().density * 48.0f;
        this.mImportanceRingView = (CachingIconView) findViewById(R.id.conversation_icon_badge_ring);
        this.mConversationIconBadge = findViewById(R.id.conversation_icon_badge);
        this.mConversationIconBadgeBg = (CachingIconView) findViewById(R.id.conversation_icon_badge_bg);
        this.mIcon.setOnVisibilityChangedListener(new Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$onFinishInflate$0((Integer) obj);
            }
        });
        this.mIcon.setOnForceHiddenChangedListener(new Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$onFinishInflate$1((Boolean) obj);
            }
        });
        this.mConversationIconView.setOnForceHiddenChangedListener(new Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$onFinishInflate$2((Boolean) obj);
            }
        });
        this.mConversationText = (TextView) findViewById(Flags.notificationsRedesignTemplates() ? 16908310 : R.id.conversation_text);
        this.mExpandButtonContainer = findViewById(R.id.expand_button_container);
        this.mExpandButtonContainerA11yContainer = (ViewGroup) findViewById(R.id.expand_button_a11y_container);
        this.mConversationHeader = findViewById(R.id.conversation_header);
        this.mContentContainer = findViewById(R.id.notification_action_list_margin_target);
        this.mExpandButtonAndContentContainer = (ViewGroup) findViewById(R.id.expand_button_and_content_container);
        this.mExpandButton = (NotificationExpandButton) findViewById(R.id.expand_button);
        this.mMessageSpacingStandard = getResources().getDimensionPixelSize(R.dimen.notification_messaging_spacing);
        this.mMessageSpacingGroup = getResources().getDimensionPixelSize(R.dimen.notification_messaging_spacing_conversation_group);
        this.mNotificationHeaderExpandedPadding = getResources().getDimensionPixelSize(R.dimen.conversation_header_expanded_padding_end);
        this.mContentMarginEnd = getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        this.mBadgeProtrusion = getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion);
        this.mConversationAvatarSize = getResources().getDimensionPixelSize(R.dimen.conversation_avatar_size);
        this.mConversationAvatarSizeExpanded = getResources().getDimensionPixelSize(R.dimen.conversation_avatar_size_group_expanded);
        this.mConversationIconTopPaddingExpandedGroup = getResources().getDimensionPixelSize(R.dimen.conversation_icon_container_top_padding_small_avatar);
        this.mConversationBadgeSize = getResources().getDimensionPixelSize(R.dimen.conversation_icon_size_badged);
        this.mConversationBadgeSizeExpanded = getResources().getDimensionPixelSize(R.dimen.conversation_icon_size_badged_group_expanded);
        this.mConversationBadgeMargin = getResources().getDimensionPixelSize(R.dimen.conversation_badge_side_margin);
        this.mConversationBadgeMarginExpanded = getResources().getDimensionPixelSize(R.dimen.conversation_badge_side_margin_group_expanded);
        this.mConversationIconTopPadding = getResources().getDimensionPixelSize(R.dimen.conversation_icon_container_top_padding);
        this.mExpandedGroupMessagePadding = getResources().getDimensionPixelSize(R.dimen.expanded_group_conversation_message_padding);
        this.mExpandedGroupBadgeProtrusion = getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion_group_expanded);
        this.mExpandedGroupBadgeProtrusionFacePile = getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion_group_expanded_face_pile);
        this.mConversationFacePile = findViewById(R.id.conversation_face_pile);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(R.dimen.conversation_face_pile_avatar_size);
        this.mFacePileAvatarSizeExpandedGroup = getResources().getDimensionPixelSize(R.dimen.conversation_face_pile_avatar_size_group_expanded);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(R.dimen.conversation_face_pile_protection_width);
        this.mFacePileProtectionWidthExpanded = getResources().getDimensionPixelSize(R.dimen.conversation_face_pile_protection_width_expanded);
        this.mFallbackChatName = getResources().getString(R.string.conversation_title_fallback_one_to_one);
        this.mFallbackGroupChatName = getResources().getString(R.string.conversation_title_fallback_group_chat);
        this.mAppName = (ObservableTextView) findViewById(R.id.app_name_text);
        this.mAppNameDivider = findViewById(R.id.app_name_divider);
        this.mAppNameGone = this.mAppName.getVisibility() == 8;
        this.mAppName.setOnVisibilityChangedListener(new Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$onFinishInflate$3((Integer) obj);
            }
        });
        this.mConversationStartMargin = getResources().getDimensionPixelSize(R.dimen.notification_content_margin_start);
        this.mConversationTopMargin = getFontScaledMarginHeight(this.mContext, R.dimen.conversation_expand_button_top_margin_expanded);
        this.mConversationMinHeight = getResources().getDimensionPixelSize(R.dimen.conversation_expand_button_height);
        this.mConversationUnreadCountText = (TextView) findViewById(R.id.conversation_unread_count_text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(Integer num) {
        boolean z = num.intValue() == 8;
        if ((this.mConversationIconBadgeBg.getVisibility() == 8) != z) {
            this.mConversationIconBadgeBg.animate().cancel();
        }
        boolean z2 = this.mImportanceRingView.getVisibility() == 8;
        int iIntValue = !this.mImportantConversation ? 8 : num.intValue();
        Integer numValueOf = Integer.valueOf(iIntValue);
        numValueOf.getClass();
        if (z2 != (iIntValue == 8)) {
            this.mImportanceRingView.animate().cancel();
            CachingIconView cachingIconView = this.mImportanceRingView;
            numValueOf.getClass();
            cachingIconView.setVisibility(iIntValue);
        }
        if ((this.mConversationIconBadge.getVisibility() == 8) != z) {
            this.mConversationIconBadge.animate().cancel();
            View view = this.mConversationIconBadge;
            numValueOf.getClass();
            view.setVisibility(iIntValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$1(Boolean bool) {
        this.mPeopleHelper.animateViewForceHidden(this.mConversationIconBadgeBg, bool.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mImportanceRingView, bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$2(Boolean bool) {
        this.mPeopleHelper.animateViewForceHidden(this.mConversationIconBadgeBg, bool.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mImportanceRingView, bool.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mIcon, bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$3(Integer num) {
        onAppNameVisibilityChanged();
    }

    @RemotableViewMethod
    public void setAvatarReplacement(Icon icon) {
        this.mAvatarReplacement = icon;
    }

    @RemotableViewMethod
    public void setNameReplacement(CharSequence charSequence) {
        this.mNameReplacement = charSequence;
    }

    @RemotableViewMethod
    public void setIsImportantConversation(boolean z) throws Resources.NotFoundException {
        setIsImportantConversation(z, false);
    }

    public void setIsImportantConversation(boolean z, boolean z2) throws Resources.NotFoundException {
        this.mImportantConversation = z;
        CachingIconView cachingIconView = this.mImportanceRingView;
        int i = 8;
        if (z && this.mIcon.getVisibility() != 8) {
            i = 0;
        }
        cachingIconView.setVisibility(i);
        if (z2 && z) {
            final GradientDrawable gradientDrawable = (GradientDrawable) this.mImportanceRingView.getDrawable();
            gradientDrawable.mutate();
            final GradientDrawable gradientDrawable2 = (GradientDrawable) this.mConversationIconBadgeBg.getDrawable();
            gradientDrawable2.mutate();
            final int color = getResources().getColor(R.color.conversation_important_highlight);
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.importance_ring_stroke_width);
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.importance_ring_anim_max_stroke_width);
            final int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.importance_ring_size) - (dimensionPixelSize * 2);
            final int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.conversation_icon_size_badged);
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$setIsImportantConversation$4(gradientDrawable, color, dimensionPixelSize3, valueAnimator);
                }
            };
            float f = dimensionPixelSize2;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, f);
            valueAnimatorOfFloat.setInterpolator(LINEAR_OUT_SLOW_IN);
            valueAnimatorOfFloat.setDuration(250L);
            valueAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(f, dimensionPixelSize);
            valueAnimatorOfFloat2.setDuration(200L);
            valueAnimatorOfFloat2.setStartDelay(25L);
            valueAnimatorOfFloat2.setInterpolator(OVERSHOOT);
            valueAnimatorOfFloat2.addUpdateListener(animatorUpdateListener);
            valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.widget.ConversationLayout.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    GradientDrawable gradientDrawable3 = gradientDrawable2;
                    int i2 = dimensionPixelSize3;
                    gradientDrawable3.setSize(i2, i2);
                    ConversationLayout.this.mConversationIconBadgeBg.invalidate();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    GradientDrawable gradientDrawable3 = gradientDrawable2;
                    int i2 = dimensionPixelSize4;
                    gradientDrawable3.setSize(i2, i2);
                    ConversationLayout.this.mConversationIconBadgeBg.invalidate();
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(valueAnimatorOfFloat, valueAnimatorOfFloat2);
            animatorSet.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIsImportantConversation$4(GradientDrawable gradientDrawable, int i, int i2, ValueAnimator valueAnimator) {
        int iRound = Math.round(((Float) valueAnimator.getAnimatedValue()).floatValue());
        gradientDrawable.setStroke(iRound, i);
        int i3 = i2 + (iRound * 2);
        gradientDrawable.setSize(i3, i3);
        this.mImportanceRingView.invalidate();
    }

    public boolean isImportantConversation() {
        return this.mImportantConversation;
    }

    @RemotableViewMethod(asyncImpl = "setIsCollapsedAsync")
    /* renamed from: setIsCollapsed, reason: merged with bridge method [inline-methods] */
    public void lambda$setIsCollapsedAsync$5(boolean z) throws Resources.NotFoundException {
        this.mIsCollapsed = z;
        this.mMessagingLinearLayout.setMaxDisplayedLines(z ? 2 : Integer.MAX_VALUE);
        updateExpandButton();
        updateContentEndPaddings();
    }

    public Runnable setIsCollapsedAsync(final boolean z) {
        this.mIsCollapsed = z;
        return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                this.f$0.lambda$setIsCollapsedAsync$5(z);
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$6(Bundle bundle) throws Resources.NotFoundException {
        bind(parseMessagingData(bundle, false, false));
    }

    private MessagingData parseMessagingData(Bundle bundle, boolean z, boolean z2) {
        List<MessagingMessage> listCreateMessages;
        ConversationHeaderData conversationHeaderDataLoadConversationHeaderData;
        List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_MESSAGES));
        List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES));
        Person person = (Person) bundle.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
        addRemoteInputHistoryToMessages(messagesFromBundleArray, (RemoteInputHistoryItem[]) bundle.getParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class));
        boolean z3 = bundle.getBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, false);
        int i = bundle.getInt(Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT);
        CharSequence charSequence = bundle.getCharSequence(Notification.EXTRA_SUMMARIZED_CONTENT);
        this.mSummarizedContent = charSequence;
        if (!TextUtils.isEmpty(charSequence) && this.mIsCollapsed) {
            listCreateMessages = createMessages(List.of(new Notification.MessagingStyle.Message(this.mSummarizedContent, 0L, "")), false, z);
        } else {
            listCreateMessages = createMessages(messagesFromBundleArray, false, z);
        }
        List<MessagingMessage> list = listCreateMessages;
        List<MessagingMessage> listCreateMessages2 = createMessages(messagesFromBundleArray2, true, z);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<MessagingMessage> list2 = list;
        Person person2 = person;
        findGroups(listCreateMessages2, list2, person2, arrayList, arrayList2);
        if (z2 && android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            conversationHeaderDataLoadConversationHeaderData = loadConversationHeaderData(this.mIsOneToOne, this.mConversationTitle, this.mShortcutIcon, this.mLargeIcon, list2, person2, arrayList, this.mLayoutColor);
            list2 = list2;
            person2 = person2;
        } else {
            conversationHeaderDataLoadConversationHeaderData = null;
        }
        return new MessagingData(person2, z3, i, listCreateMessages2, list2, arrayList, arrayList2, conversationHeaderDataLoadConversationHeaderData, this.mSummarizedContent);
    }

    public Runnable setDataAsync(final Bundle bundle) {
        if (!this.mPrecomputedTextEnabled) {
            return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    this.f$0.lambda$setDataAsync$6(bundle);
                }
            };
        }
        final MessagingData messagingData = parseMessagingData(bundle, true, true);
        return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                this.f$0.lambda$setDataAsync$7(messagingData);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDataAsync$7(MessagingData messagingData) throws Resources.NotFoundException {
        finalizeInflate(messagingData.getHistoricMessagingMessages());
        finalizeInflate(messagingData.getNewMessagingMessages());
        bind(messagingData);
    }

    public void setPrecomputedTextEnabled(boolean z) {
        this.mPrecomputedTextEnabled = z;
    }

    private void finalizeInflate(List<MessagingMessage> list) {
        Iterator<MessagingMessage> it = list.iterator();
        while (it.hasNext()) {
            it.next().finalizeInflate();
        }
    }

    @Override // com.android.internal.widget.ImageMessageConsumer
    public void setImageResolver(ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    public void setUnreadCount(int i) throws Resources.NotFoundException {
        this.mExpandButton.setNumber(i);
        updateConversationUnreadCountText(i);
    }

    private void updateConversationUnreadCountText(int i) {
        if (this.mConversationUnreadCount != i) {
            this.mConversationUnreadCount = i;
            if (i > 1) {
                this.mConversationUnreadCountText.lambda$setTextAsync$0(Integer.toString(i));
                this.mConversationUnreadCountText.setVisibility(0);
            } else {
                this.mConversationUnreadCountText.setVisibility(8);
            }
        }
    }

    private void addRemoteInputHistoryToMessages(List<Notification.MessagingStyle.Message> list, RemoteInputHistoryItem[] remoteInputHistoryItemArr) {
        if (remoteInputHistoryItemArr == null || remoteInputHistoryItemArr.length == 0) {
            return;
        }
        for (int length = remoteInputHistoryItemArr.length - 1; length >= 0; length--) {
            RemoteInputHistoryItem remoteInputHistoryItem = remoteInputHistoryItemArr[length];
            Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(remoteInputHistoryItem.getText(), 0L, null, true);
            if (remoteInputHistoryItem.getUri() != null) {
                message.setData(remoteInputHistoryItem.getMimeType(), remoteInputHistoryItem.getUri());
            }
            list.add(message);
        }
    }

    private void bind(MessagingData messagingData) throws Resources.NotFoundException {
        setUser(messagingData.getUser());
        setUnreadCount(messagingData.getUnreadCount());
        ArrayList<MessagingGroup> arrayList = new ArrayList<>(this.mGroups);
        createGroupViews(messagingData.getGroups(), messagingData.getSenders(), messagingData.getShowSpinner());
        removeGroups(arrayList);
        Iterator<MessagingMessage> it = this.mMessages.iterator();
        while (it.hasNext()) {
            it.next().removeMessage(this.mToRecycle);
        }
        Iterator<MessagingMessage> it2 = this.mHistoricMessages.iterator();
        while (it2.hasNext()) {
            it2.next().removeMessage(this.mToRecycle);
        }
        this.mMessages = messagingData.getNewMessagingMessages();
        this.mHistoricMessages = messagingData.getHistoricMessagingMessages();
        updateHistoricMessageVisibility();
        updateTitleAndNamesDisplay();
        updateConversationLayout(messagingData);
        Iterator<MessagingLinearLayout.MessagingChild> it3 = this.mToRecycle.iterator();
        while (it3.hasNext()) {
            it3.next().recycle();
        }
        this.mToRecycle.clear();
    }

    private void updateConversationLayout(MessagingData messagingData) {
        ConversationLayout conversationLayout;
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            computeAndSetConversationAvatarAndName();
            conversationLayout = this;
        } else {
            ConversationHeaderData conversationHeaderData = messagingData.getConversationHeaderData();
            if (conversationHeaderData == null) {
                conversationLayout = this;
                conversationHeaderData = conversationLayout.loadConversationHeaderData(this.mIsOneToOne, this.mConversationTitle, this.mShortcutIcon, this.mLargeIcon, this.mMessages, this.mUser, messagingData.getGroups(), this.mLayoutColor);
            } else {
                conversationLayout = this;
            }
            conversationLayout.setConversationAvatarAndNameFromData(conversationHeaderData);
        }
        conversationLayout.updateAppName();
        conversationLayout.updateIconPositionAndSize();
        conversationLayout.updateImageMessages();
        conversationLayout.updatePaddingsBasedOnContentAvailability();
        conversationLayout.updateActionListPadding();
        conversationLayout.updateAppNameDividerVisibility();
    }

    @Deprecated
    private void computeAndSetConversationAvatarAndName() {
        CharSequence senderName = this.mConversationTitle;
        this.mConversationIcon = this.mShortcutIcon;
        if (this.mIsOneToOne) {
            CharSequence key = getKey(this.mUser);
            for (int size = this.mGroups.size() - 1; size >= 0; size--) {
                MessagingGroup messagingGroup = this.mGroups.get(size);
                Person sender = messagingGroup.getSender();
                if ((sender != null && !TextUtils.equals(key, getKey(sender))) || size == 0) {
                    if (TextUtils.isEmpty(senderName)) {
                        senderName = messagingGroup.getSenderName();
                    }
                    if (this.mConversationIcon == null) {
                        Icon avatarIcon = messagingGroup.getAvatarIcon();
                        if (avatarIcon == null) {
                            avatarIcon = this.mPeopleHelper.createAvatarSymbol(senderName, "", this.mLayoutColor);
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
        if (TextUtils.isEmpty(senderName)) {
            senderName = this.mIsOneToOne ? this.mFallbackChatName : this.mFallbackGroupChatName;
        }
        this.mConversationText.lambda$setTextAsync$0(senderName);
        this.mPeopleHelper.maybeHideFirstSenderName(this.mGroups, this.mIsOneToOne, senderName);
    }

    private void setConversationAvatarAndNameFromData(ConversationHeaderData conversationHeaderData) {
        ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData;
        this.mConversationHeaderData = conversationHeaderData;
        ConversationAvatarData conversationAvatar = conversationHeaderData.getConversationAvatar();
        ConversationAvatarData.OneToOneConversationAvatarData oneToOneConversationAvatarData = null;
        if (conversationAvatar instanceof ConversationAvatarData.OneToOneConversationAvatarData) {
            oneToOneConversationAvatarData = (ConversationAvatarData.OneToOneConversationAvatarData) conversationAvatar;
            groupConversationAvatarData = null;
        } else {
            groupConversationAvatarData = (ConversationAvatarData.GroupConversationAvatarData) conversationAvatar;
        }
        if (oneToOneConversationAvatarData != null) {
            this.mConversationIconView.setVisibility(0);
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.lambda$setImageURIAsync$0(oneToOneConversationAvatarData.mDrawable);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            this.mConversationFacePile = findViewById(R.id.conversation_face_pile);
            bindFacePile(groupConversationAvatarData);
        }
        CharSequence conversationText = conversationHeaderData.getConversationText();
        if (TextUtils.isEmpty(conversationText)) {
            conversationText = this.mIsOneToOne ? this.mFallbackChatName : this.mFallbackGroupChatName;
        }
        this.mConversationText.lambda$setTextAsync$0(conversationText);
        this.mPeopleHelper.maybeHideFirstSenderName(this.mGroups, this.mIsOneToOne, conversationText);
    }

    private void updateActionListPadding() {
        NotificationActionListLayout notificationActionListLayout;
        if (Flags.notificationsRedesignTemplates() || (notificationActionListLayout = this.mActions) == null) {
            return;
        }
        notificationActionListLayout.setCollapsibleIndentDimen(R.dimen.call_notification_collapsible_indent);
    }

    private void updateImageMessages() {
        if (this.mImageMessageContainer == null) {
            return;
        }
        View newImageMessage = getNewImageMessage();
        View childAt = this.mImageMessageContainer.getChildAt(0);
        if (childAt != newImageMessage) {
            this.mImageMessageContainer.removeView(childAt);
            if (newImageMessage != null) {
                this.mImageMessageContainer.addView(newImageMessage);
            }
        }
        this.mImageMessageContainer.setVisibility(newImageMessage == null ? 8 : 0);
    }

    private View getNewImageMessage() {
        MessagingImageMessage isolatedMessage;
        if (!this.mIsCollapsed || this.mGroups.isEmpty() || (isolatedMessage = ((MessagingGroup) this.mGroups.getLast()).getIsolatedMessage()) == null) {
            return null;
        }
        return isolatedMessage.getView();
    }

    public void bindFacePile(ImageView imageView, ImageView imageView2, ImageView imageView3) {
        imageView.setImageTintList(ColorStateList.valueOf(0));
        CharSequence key = getKey(this.mUser);
        int size = this.mGroups.size() - 1;
        Icon iconCreateAvatarSymbol = null;
        CharSequence key2 = null;
        Icon iconCreateAvatarSymbol2 = null;
        while (true) {
            if (size < 0) {
                break;
            }
            MessagingGroup messagingGroup = this.mGroups.get(size);
            Person sender = messagingGroup.getSender();
            boolean z = (sender == null || TextUtils.equals(key, getKey(sender))) ? false : true;
            boolean z2 = (sender == null || TextUtils.equals(key2, getKey(sender))) ? false : true;
            if ((z && z2) || (size == 0 && key2 == null)) {
                if (iconCreateAvatarSymbol2 == null) {
                    iconCreateAvatarSymbol2 = messagingGroup.getAvatarIcon();
                    key2 = getKey(sender);
                } else {
                    iconCreateAvatarSymbol = messagingGroup.getAvatarIcon();
                    break;
                }
            }
            size--;
        }
        if (iconCreateAvatarSymbol2 == null) {
            iconCreateAvatarSymbol2 = this.mPeopleHelper.createAvatarSymbol(" ", "", this.mLayoutColor);
        }
        imageView2.setImageIcon(iconCreateAvatarSymbol2);
        if (iconCreateAvatarSymbol == null) {
            iconCreateAvatarSymbol = this.mPeopleHelper.createAvatarSymbol("", "", this.mLayoutColor);
        }
        imageView3.setImageIcon(iconCreateAvatarSymbol);
    }

    @Deprecated
    private void bindFacePile() {
        bindFacePile(null);
    }

    private void bindFacePile(ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData) {
        int i;
        int i2;
        int i3;
        ImageView imageView = (ImageView) this.mConversationFacePile.findViewById(R.id.conversation_face_pile_bottom_background);
        ImageView imageView2 = (ImageView) this.mConversationFacePile.findViewById(R.id.conversation_face_pile_bottom);
        ImageView imageView3 = (ImageView) this.mConversationFacePile.findViewById(R.id.conversation_face_pile_top);
        if (groupConversationAvatarData == null) {
            bindFacePile(imageView, imageView2, imageView3);
        } else {
            bindFacePileWithDrawable(imageView, imageView2, imageView3, groupConversationAvatarData);
        }
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        if (this.mIsCollapsed) {
            i = this.mConversationAvatarSize;
            i2 = this.mFacePileAvatarSize;
            i3 = this.mFacePileProtectionWidth;
        } else {
            i = this.mConversationAvatarSizeExpanded;
            i2 = this.mFacePileAvatarSizeExpandedGroup;
            i3 = this.mFacePileProtectionWidthExpanded;
        }
        int i4 = (i3 * 2) + i2;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mConversationFacePile.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.mConversationFacePile.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView2.getLayoutParams();
        layoutParams2.width = i2;
        layoutParams2.height = i2;
        imageView2.setLayoutParams(layoutParams2);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) imageView3.getLayoutParams();
        layoutParams3.width = i2;
        layoutParams3.height = i2;
        imageView3.setLayoutParams(layoutParams3);
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams4.width = i4;
        layoutParams4.height = i4;
        imageView.setLayoutParams(layoutParams4);
    }

    public void bindFacePileWithDrawable(ImageView imageView, ImageView imageView2, ImageView imageView3, ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData) {
        applyNotificationBackgroundColor(imageView);
        imageView2.lambda$setImageURIAsync$0(groupConversationAvatarData.mLastIcon);
        imageView3.lambda$setImageURIAsync$0(groupConversationAvatarData.mSecondLastIcon);
    }

    private void updateAppName() {
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mAppName.setVisibility(8);
    }

    public boolean shouldHideAppName() {
        return this.mIsCollapsed;
    }

    private void updateIconPositionAndSize() {
        int i;
        int i2;
        int i3;
        int i4;
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        if (this.mIsOneToOne || this.mIsCollapsed) {
            i = this.mBadgeProtrusion;
            i2 = this.mConversationAvatarSize;
            i3 = this.mConversationBadgeSize;
            i4 = this.mConversationBadgeMargin;
        } else {
            if (this.mConversationFacePile.getVisibility() == 0) {
                i = this.mExpandedGroupBadgeProtrusionFacePile;
            } else {
                i = this.mExpandedGroupBadgeProtrusion;
            }
            i2 = this.mConversationAvatarSizeExpanded;
            i3 = this.mConversationBadgeSizeExpanded;
            i4 = this.mConversationBadgeMarginExpanded;
        }
        if (this.mConversationIconView.getVisibility() == 0) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mConversationIconView.getLayoutParams();
            layoutParams.width = i2;
            layoutParams.height = i2;
            layoutParams.leftMargin = i;
            layoutParams.rightMargin = i;
            layoutParams.bottomMargin = i;
            this.mConversationIconView.setLayoutParams(layoutParams);
        }
        if (this.mConversationIconBadge.getVisibility() == 0) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mConversationIconBadge.getLayoutParams();
            layoutParams2.width = i3;
            layoutParams2.height = i3;
            layoutParams2.leftMargin = i4;
            layoutParams2.topMargin = i4;
            this.mConversationIconBadge.setLayoutParams(layoutParams2);
        }
        if (this.mConversationFacePile.getVisibility() == 0) {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mConversationFacePile.getLayoutParams();
            layoutParams3.leftMargin = i;
            layoutParams3.rightMargin = i;
            layoutParams3.bottomMargin = i;
            this.mConversationFacePile.setLayoutParams(layoutParams3);
        }
    }

    private void updatePaddingsBasedOnContentAvailability() {
        int i;
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mMessagingLinearLayout.setSpacing(this.mIsOneToOne ? this.mMessageSpacingStandard : this.mMessageSpacingGroup);
        boolean z = this.mIsOneToOne;
        int i2 = (z || this.mIsCollapsed) ? 0 : this.mExpandedGroupMessagePadding;
        if (z || this.mIsCollapsed) {
            i = this.mConversationIconTopPadding;
        } else {
            i = this.mConversationIconTopPaddingExpandedGroup;
        }
        View view = this.mConversationIconContainer;
        view.setPaddingRelative(view.getPaddingStart(), i, this.mConversationIconContainer.getPaddingEnd(), this.mConversationIconContainer.getPaddingBottom());
        MessagingLinearLayout messagingLinearLayout = this.mMessagingLinearLayout;
        messagingLinearLayout.setPaddingRelative(messagingLinearLayout.getPaddingStart(), i2, this.mMessagingLinearLayout.getPaddingEnd(), this.mMessagingLinearLayout.getPaddingBottom());
        boolean z2 = (this.mIsCollapsed || this.mIsOneToOne) ? false : true;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mMessagingLinearLayout.getLayoutParams();
        marginLayoutParams.leftMargin = z2 ? 0 : this.mConversationStartMargin;
        this.mMessagingLinearLayout.setLayoutParams(marginLayoutParams);
    }

    @RemotableViewMethod
    public Runnable setLargeIconAsync(final Icon icon) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setLargeIconAsync$8(icon);
                }
            };
        }
        this.mLargeIcon = icon;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setLargeIconAsync")
    /* renamed from: setLargeIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setLargeIconAsync$8(Icon icon) {
        this.mLargeIcon = icon;
    }

    @RemotableViewMethod
    public Runnable setShortcutIconAsync(final Icon icon) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setShortcutIconAsync$9(icon);
                }
            };
        }
        this.mShortcutIcon = icon;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setShortcutIconAsync")
    /* renamed from: setShortcutIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setShortcutIconAsync$9(Icon icon) {
        this.mShortcutIcon = icon;
    }

    @RemotableViewMethod
    public Runnable setConversationTitleAsync(final CharSequence charSequence) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setConversationTitleAsync$10(charSequence);
                }
            };
        }
        this.mConversationTitle = charSequence != null ? charSequence.toString() : null;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setConversationTitleAsync")
    /* renamed from: setConversationTitle, reason: merged with bridge method [inline-methods] */
    public void lambda$setConversationTitleAsync$10(CharSequence charSequence) {
        this.mConversationTitle = charSequence != null ? charSequence.toString() : null;
    }

    public CharSequence getConversationTitle() {
        return this.mConversationText.getText();
    }

    private void removeGroups(ArrayList<MessagingGroup> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            final MessagingGroup messagingGroup = arrayList.get(i);
            if (!this.mGroups.contains(messagingGroup)) {
                List<MessagingMessage> messages = messagingGroup.getMessages();
                boolean zIsShown = messagingGroup.isShown();
                this.mMessagingLinearLayout.removeView(messagingGroup);
                if (zIsShown && !MessagingLinearLayout.isGone(messagingGroup)) {
                    this.mMessagingLinearLayout.addTransientView(messagingGroup, 0);
                    messagingGroup.removeGroupAnimated(new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$removeGroups$11(messagingGroup);
                        }
                    });
                } else {
                    this.mToRecycle.add(messagingGroup);
                }
                this.mMessages.removeAll(messages);
                this.mHistoricMessages.removeAll(messages);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeGroups$11(MessagingGroup messagingGroup) {
        this.mMessagingLinearLayout.removeTransientView(messagingGroup);
        messagingGroup.recycle();
    }

    private void updateTitleAndNamesDisplay() {
        Icon avatarSymbolIfMatching;
        Map<CharSequence, String> mapMapUniqueNamesToPrefix = this.mPeopleHelper.mapUniqueNamesToPrefix(this.mGroups);
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < this.mGroups.size(); i++) {
            MessagingGroup messagingGroup = this.mGroups.get(i);
            boolean z = messagingGroup.getSender() == this.mUser;
            CharSequence senderName = messagingGroup.getSenderName();
            if (messagingGroup.needsGeneratedAvatar() && !TextUtils.isEmpty(senderName) && ((!this.mIsOneToOne || this.mAvatarReplacement == null || z) && (avatarSymbolIfMatching = messagingGroup.getAvatarSymbolIfMatching(senderName, mapMapUniqueNamesToPrefix.get(senderName), this.mLayoutColor)) != null)) {
                arrayMap.put(senderName, avatarSymbolIfMatching);
            }
        }
        for (int i2 = 0; i2 < this.mGroups.size(); i2++) {
            MessagingGroup messagingGroup2 = this.mGroups.get(i2);
            CharSequence senderName2 = messagingGroup2.getSenderName();
            if (messagingGroup2.needsGeneratedAvatar() && !TextUtils.isEmpty(senderName2)) {
                if (this.mIsOneToOne && this.mAvatarReplacement != null && messagingGroup2.getSender() != this.mUser) {
                    messagingGroup2.setAvatar(this.mAvatarReplacement);
                } else {
                    Icon iconCreateAvatarSymbol = (Icon) arrayMap.get(senderName2);
                    if (iconCreateAvatarSymbol == null) {
                        iconCreateAvatarSymbol = this.mPeopleHelper.createAvatarSymbol(senderName2, mapMapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                        arrayMap.put(senderName2, iconCreateAvatarSymbol);
                    }
                    messagingGroup2.setCreatedAvatar(iconCreateAvatarSymbol, senderName2, mapMapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                }
            }
        }
    }

    @RemotableViewMethod
    public Runnable setLayoutColorAsync(final int i) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setLayoutColorAsync$12(i);
                }
            };
        }
        this.mLayoutColor = i;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    /* renamed from: setLayoutColor, reason: merged with bridge method [inline-methods] */
    public void lambda$setLayoutColorAsync$12(int i) {
        this.mLayoutColor = i;
    }

    @RemotableViewMethod
    public Runnable setIsOneToOneAsync(final boolean z) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setIsOneToOneAsync$13(z);
                }
            };
        }
        this.mIsOneToOne = z;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setIsOneToOneAsync")
    /* renamed from: setIsOneToOne, reason: merged with bridge method [inline-methods] */
    public void lambda$setIsOneToOneAsync$13(boolean z) {
        this.mIsOneToOne = z;
    }

    @RemotableViewMethod
    public void setSenderTextColor(int i) {
        this.mSenderTextColor = i;
        this.mConversationText.setTextColor(i);
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
        this.mNotificationBackgroundColor = i;
        applyNotificationBackgroundColor(this.mConversationIconBadgeBg);
    }

    private void applyNotificationBackgroundColor(ImageView imageView) {
        imageView.setImageTintList(ColorStateList.valueOf(this.mNotificationBackgroundColor));
    }

    @RemotableViewMethod
    public void setMessageTextColor(int i) {
        this.mMessageTextColor = i;
    }

    private void setUser(Person person) {
        this.mUser = person;
        if (person.getIcon() == null) {
            Icon iconCreateWithResource = Icon.createWithResource(getContext(), R.drawable.messaging_user);
            iconCreateWithResource.setTint(this.mLayoutColor);
            this.mUser = this.mUser.toBuilder().setIcon(iconCreateWithResource).build();
        }
    }

    private void createGroupViews(List<List<MessagingMessage>> list, List<Person> list2, boolean z) throws Resources.NotFoundException {
        CharSequence charSequence;
        this.mGroups.clear();
        int i = 0;
        while (i < list.size()) {
            List<MessagingMessage> list3 = list.get(i);
            CharSequence charSequence2 = null;
            MessagingGroup messagingGroupCreateGroup = null;
            for (int size = list3.size() - 1; size >= 0; size--) {
                messagingGroupCreateGroup = list3.get(size).getGroup();
                if (messagingGroupCreateGroup != null) {
                    break;
                }
            }
            if (messagingGroupCreateGroup == null) {
                messagingGroupCreateGroup = MessagingGroup.createGroup(this.mMessagingLinearLayout);
                if (this.mAddedQueue.size() < 10) {
                    this.mAddedQueue.add(messagingGroupCreateGroup);
                } else {
                    this.mAddedQueue.remove();
                    this.mAddedQueue.add(messagingGroupCreateGroup);
                }
            } else if (messagingGroupCreateGroup.getParent() != this.mMessagingLinearLayout) {
                throw new IllegalStateException("group parent was " + messagingGroupCreateGroup.getParent() + " but expected " + this.mMessagingLinearLayout);
            }
            messagingGroupCreateGroup.setImageDisplayLocation(this.mIsCollapsed ? 2 : 0);
            messagingGroupCreateGroup.setIsInConversation(true);
            messagingGroupCreateGroup.setLayoutColor(this.mLayoutColor);
            messagingGroupCreateGroup.setTextColors(this.mSenderTextColor, this.mMessageTextColor);
            Person person = list2.get(i);
            if (person != this.mUser && (charSequence = this.mNameReplacement) != null) {
                charSequence2 = charSequence;
            }
            messagingGroupCreateGroup.setShowingAvatar((this.mIsOneToOne || this.mIsCollapsed) ? false : true);
            messagingGroupCreateGroup.setSingleLine(false);
            messagingGroupCreateGroup.setSender(person, charSequence2);
            messagingGroupCreateGroup.setSending(i == list.size() - 1 && z);
            this.mGroups.add(messagingGroupCreateGroup);
            if (this.mMessagingLinearLayout.indexOfChild(messagingGroupCreateGroup) != i) {
                this.mMessagingLinearLayout.removeView(messagingGroupCreateGroup);
                this.mMessagingLinearLayout.addView(messagingGroupCreateGroup, i);
            }
            messagingGroupCreateGroup.setMessages(list3);
            i++;
        }
        if (android.widget.flags.Flags.dropNonExistingMessages()) {
            this.mAddedGroups.removeIf(new Predicate() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.lambda$createGroupViews$14((MessagingGroup) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createGroupViews$14(MessagingGroup messagingGroup) {
        return !this.mGroups.contains(messagingGroup);
    }

    private void findGroups(List<MessagingMessage> list, List<MessagingMessage> list2, Person person, List<List<MessagingMessage>> list3, List<Person> list4) {
        MessagingMessage messagingMessage;
        int size = list.size();
        ArrayList arrayList = null;
        CharSequence charSequence = null;
        for (int i = 0; i < list2.size() + size; i++) {
            if (i < size) {
                messagingMessage = list.get(i);
            } else {
                messagingMessage = list2.get(i - size);
            }
            boolean z = arrayList == null;
            Person senderPerson = messagingMessage.getMessage() == null ? null : messagingMessage.getMessage().getSenderPerson();
            CharSequence key = getKey(senderPerson);
            if ((true ^ TextUtils.equals(key, charSequence)) | z) {
                arrayList = new ArrayList();
                list3.add(arrayList);
                list4.add(senderPerson == null ? person : senderPerson.toBuilder().setName(Objects.toString(senderPerson.getName())).build());
                charSequence = key;
            }
            arrayList.add(messagingMessage);
        }
    }

    private CharSequence getKey(Person person) {
        if (person == null) {
            return null;
        }
        return person.getKey() == null ? person.getName() : person.getKey();
    }

    private ConversationHeaderData loadConversationHeaderData(boolean z, CharSequence charSequence, Icon icon, Icon icon2, List<MessagingMessage> list, Person person, List<List<MessagingMessage>> list2, int i) {
        CharSequence string;
        Icon icon3;
        Icon iconCreateAvatarSymbol;
        CharSequence key = getKey(person);
        if (z) {
            for (int size = list.size() - 1; size >= 0; size--) {
                Person senderPerson = list.get(size).getMessage().getSenderPerson();
                CharSequence key2 = getKey(senderPerson);
                if ((senderPerson != null && key2 != key) || size == 0) {
                    if (charSequence != null && !charSequence.isEmpty()) {
                        string = charSequence;
                    } else if (senderPerson == null) {
                        string = "";
                    } else {
                        string = senderPerson.getName();
                    }
                    if (icon != null || senderPerson == null) {
                        icon3 = icon;
                    } else {
                        icon3 = senderPerson.getIcon();
                        if (senderPerson.getIcon() == null) {
                            icon3 = this.mPeopleHelper.createAvatarSymbol(string, "", i);
                        }
                    }
                }
            }
            string = charSequence;
            icon3 = icon;
        } else {
            string = charSequence;
            icon3 = icon;
        }
        if (Flags.cleanUpSpansAndNewLines() && string != null) {
            string = string.toString();
        }
        if (icon3 == null) {
            icon3 = icon2;
        }
        if (z || icon3 != null) {
            return new ConversationHeaderData(string, new ConversationAvatarData.OneToOneConversationAvatarData(resolveAvatarImageForOneToOne(icon3)));
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list2.size(); i2++) {
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < list2.get(i2).size(); i3++) {
                arrayList2.add(list2.get(i2).get(i3).getMessage());
            }
            arrayList.add(arrayList2);
        }
        PeopleHelper.NameToPrefixMap nameToPrefixMapMapUniqueNamesToPrefixWithGroupList = this.mPeopleHelper.mapUniqueNamesToPrefixWithGroupList(arrayList);
        int size2 = list2.size() - 1;
        Icon iconCreateAvatarSymbol2 = null;
        CharSequence charSequence2 = null;
        Icon iconCreateAvatarSymbol3 = null;
        while (true) {
            if (size2 < 0) {
                break;
            }
            Notification.MessagingStyle.Message message = list2.get(size2).get(0).getMessage();
            Person senderPerson2 = message.getSenderPerson() != null ? message.getSenderPerson() : person;
            CharSequence key3 = getKey(senderPerson2);
            boolean z2 = key3 != key;
            boolean z3 = key3 != charSequence2;
            if ((z2 && z3) || (size2 == 0 && charSequence2 == null)) {
                if (iconCreateAvatarSymbol3 == null) {
                    if (senderPerson2.getIcon() != null) {
                        iconCreateAvatarSymbol = senderPerson2.getIcon();
                    } else {
                        String name = senderPerson2.getName() != null ? senderPerson2.getName() : "";
                        iconCreateAvatarSymbol = this.mPeopleHelper.createAvatarSymbol(name, nameToPrefixMapMapUniqueNamesToPrefixWithGroupList.getPrefix(name), i);
                    }
                    iconCreateAvatarSymbol3 = iconCreateAvatarSymbol;
                    charSequence2 = key3;
                } else if (senderPerson2.getIcon() != null) {
                    iconCreateAvatarSymbol2 = senderPerson2.getIcon();
                } else {
                    String name2 = senderPerson2.getName() != null ? senderPerson2.getName() : "";
                    iconCreateAvatarSymbol2 = this.mPeopleHelper.createAvatarSymbol(name2, nameToPrefixMapMapUniqueNamesToPrefixWithGroupList.getPrefix(name2), i);
                }
            }
            size2--;
        }
        if (iconCreateAvatarSymbol3 == null) {
            iconCreateAvatarSymbol3 = this.mPeopleHelper.createAvatarSymbol("", "", i);
        }
        if (iconCreateAvatarSymbol2 == null) {
            iconCreateAvatarSymbol2 = this.mPeopleHelper.createAvatarSymbol("", "", i);
        }
        return new ConversationHeaderData(string, new ConversationAvatarData.GroupConversationAvatarData(resolveAvatarImageForFacePile(iconCreateAvatarSymbol3), resolveAvatarImageForFacePile(iconCreateAvatarSymbol2)));
    }

    private Drawable resolveAvatarImageForOneToOne(Icon icon) {
        Drawable drawableTryLoadingSizeRestrictedIconForOneToOne = tryLoadingSizeRestrictedIconForOneToOne(icon);
        return drawableTryLoadingSizeRestrictedIconForOneToOne != null ? drawableTryLoadingSizeRestrictedIconForOneToOne : loadDrawableFromIcon(icon);
    }

    private Drawable tryLoadingSizeRestrictedIconForOneToOne(Icon icon) {
        try {
            return this.mConversationIconView.loadSizeRestrictedIcon(icon);
        } catch (Exception unused) {
            return null;
        }
    }

    private Drawable resolveAvatarImageForFacePile(Icon icon) {
        return loadDrawableFromIcon(icon);
    }

    private Drawable loadDrawableFromIcon(Icon icon) {
        try {
            return icon.loadDrawable(getContext());
        } catch (Exception unused) {
            return null;
        }
    }

    private List<MessagingMessage> createMessages(List<Notification.MessagingStyle.Message> list, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Notification.MessagingStyle.Message message = list.get(i);
            MessagingMessage messagingMessageFindAndRemoveMatchingMessage = findAndRemoveMatchingMessage(message);
            if (messagingMessageFindAndRemoveMatchingMessage == null) {
                messagingMessageFindAndRemoveMatchingMessage = MessagingMessage.createMessage(this, message, this.mImageResolver, z2);
            }
            messagingMessageFindAndRemoveMatchingMessage.setIsHistoric(z);
            arrayList.add(messagingMessageFindAndRemoveMatchingMessage);
        }
        return arrayList;
    }

    private MessagingMessage findAndRemoveMatchingMessage(Notification.MessagingStyle.Message message) {
        for (int i = 0; i < this.mMessages.size(); i++) {
            MessagingMessage messagingMessage = this.mMessages.get(i);
            if (messagingMessage.sameAs(message)) {
                this.mMessages.remove(i);
                return messagingMessage;
            }
        }
        for (int i2 = 0; i2 < this.mHistoricMessages.size(); i2++) {
            MessagingMessage messagingMessage2 = this.mHistoricMessages.get(i2);
            if (messagingMessage2.sameAs(message)) {
                this.mHistoricMessages.remove(i2);
                return messagingMessage2;
            }
        }
        return null;
    }

    public void showHistoricMessages(boolean z) {
        this.mShowHistoricMessages = z;
        updateHistoricMessageVisibility();
    }

    private void updateHistoricMessageVisibility() {
        int size = this.mHistoricMessages.size();
        int i = 0;
        while (true) {
            int i2 = 8;
            if (i >= size) {
                break;
            }
            MessagingMessage messagingMessage = this.mHistoricMessages.get(i);
            if (this.mShowHistoricMessages) {
                i2 = 0;
            }
            messagingMessage.setVisibility(i2);
            i++;
        }
        int size2 = this.mGroups.size();
        for (int i3 = 0; i3 < size2; i3++) {
            MessagingGroup messagingGroup = this.mGroups.get(i3);
            List<MessagingMessage> messages = messagingGroup.getMessages();
            int size3 = messages.size();
            int i4 = 0;
            for (int i5 = 0; i5 < size3; i5++) {
                if (messages.get(i5).getVisibility() != 8) {
                    i4++;
                }
            }
            if (i4 > 0 && messagingGroup.getVisibility() == 8) {
                messagingGroup.setVisibility(0);
            } else if (i4 == 0 && messagingGroup.getVisibility() != 8) {
                messagingGroup.setVisibility(8);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (android.widget.flags.Flags.conversationLayoutUseMaximumChildHeight()) {
            int measuredHeight = getMeasuredHeight();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != null && childAt.getVisibility() != 8) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                    measuredHeight = Math.max(measuredHeight, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                }
            }
            int iMax = Math.max(measuredHeight, getSuggestedMinimumHeight());
            if (iMax != getMeasuredHeight()) {
                setMeasuredDimension(getMeasuredWidth(), iMax);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!this.mAddedQueue.isEmpty()) {
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.widget.ConversationLayout.2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    for (MessagingGroup messagingGroup : ConversationLayout.this.mAddedQueue) {
                        if (messagingGroup.isShown()) {
                            MessagingPropertyAnimator.fadeIn(messagingGroup.getAvatar());
                            MessagingPropertyAnimator.fadeIn(messagingGroup.getSenderView());
                            MessagingPropertyAnimator.startLocalTranslationFrom(messagingGroup, messagingGroup.getHeight(), ConversationLayout.LINEAR_OUT_SLOW_IN);
                        }
                    }
                    ConversationLayout.this.mAddedQueue.clear();
                    ConversationLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        this.mTouchDelegate.clear();
        if (this.mFeedbackIcon.getVisibility() == 0) {
            float fMax = Math.max(this.mMinTouchSize, this.mFeedbackIcon.getWidth());
            float fMax2 = Math.max(this.mMinTouchSize, this.mFeedbackIcon.getHeight());
            Rect rect = new Rect();
            rect.left = (int) (((this.mFeedbackIcon.getLeft() + this.mFeedbackIcon.getRight()) / 2.0f) - (fMax / 2.0f));
            rect.top = (int) (((this.mFeedbackIcon.getTop() + this.mFeedbackIcon.getBottom()) / 2.0f) - (fMax2 / 2.0f));
            rect.bottom = (int) (rect.top + fMax2);
            rect.right = (int) (rect.left + fMax);
            getRelativeTouchRect(rect, this.mFeedbackIcon);
            this.mTouchDelegate.add(new TouchDelegate(rect, this.mFeedbackIcon));
        }
        setTouchDelegate(this.mTouchDelegate);
    }

    private void getRelativeTouchRect(Rect rect, View view) {
        for (ViewGroup viewGroup = (ViewGroup) view.getParent(); viewGroup != this; viewGroup = (ViewGroup) viewGroup.getParent()) {
            rect.offset(viewGroup.getLeft(), viewGroup.getTop());
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

    private void updateExpandButton() throws Resources.NotFoundException {
        NotificationActionListLayout notificationActionListLayout;
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        boolean z = this.mIsCollapsed;
        int i = z ? 17 : 49;
        ViewGroup viewGroup = this.mExpandButtonAndContentContainer;
        this.mExpandButton.setExpanded(!z);
        if (viewGroup != this.mExpandButtonContainer.getParent()) {
            ((ViewGroup) this.mExpandButtonContainer.getParent()).removeView(this.mExpandButtonContainer);
            viewGroup.addView(this.mExpandButtonContainer);
            setMinimumHeight(this.mConversationMinHeight);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mExpandButton.getLayoutParams();
        if (this.mIsCollapsed && (notificationActionListLayout = this.mActions) != null && notificationActionListLayout.getVisibility() == 0) {
            layoutParams.gravity = 48;
            layoutParams.topMargin = this.mConversationTopMargin;
        } else {
            layoutParams.gravity = i;
            layoutParams.topMargin = this.mIsCollapsed ? 0 : this.mConversationTopMargin;
        }
        this.mExpandButton.setLayoutParams(layoutParams);
    }

    private void updateContentEndPaddings() {
        int i;
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        int i2 = 0;
        if (!this.mExpandable) {
            i = this.mContentMarginEnd;
        } else if (this.mIsCollapsed) {
            i = 0;
        } else {
            i2 = this.mNotificationHeaderExpandedPadding;
            i = this.mContentMarginEnd;
        }
        View view = this.mConversationHeader;
        view.setPaddingRelative(view.getPaddingStart(), this.mConversationHeader.getPaddingTop(), i2, this.mConversationHeader.getPaddingBottom());
        View view2 = this.mContentContainer;
        view2.setPaddingRelative(view2.getPaddingStart(), this.mContentContainer.getPaddingTop(), i, this.mContentContainer.getPaddingBottom());
    }

    private void onAppNameVisibilityChanged() {
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mAppName.setVisibility(8);
        boolean z = this.mAppName.getVisibility() == 8;
        if (z != this.mAppNameGone) {
            this.mAppNameGone = z;
            updateAppNameDividerVisibility();
        }
    }

    private void updateAppNameDividerVisibility() {
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mAppNameDivider.setVisibility(8);
    }

    public void updateExpandability(boolean z, View.OnClickListener onClickListener) {
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mExpandable = z;
        if (z) {
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
    public void setMessagingClippingDisabled(boolean z) {
        this.mMessagingLinearLayout.setClipBounds(z ? null : this.mMessagingClipRect);
    }

    public CharSequence getConversationSenderName() {
        if (this.mGroups.isEmpty()) {
            return null;
        }
        return getResources().getString(R.string.conversation_single_line_name_display, this.mGroups.get(r0.size() - 1).getSenderName());
    }

    public boolean isOneToOne() {
        return this.mIsOneToOne;
    }

    public CharSequence getConversationText() {
        CharSequence charSequence = this.mSummarizedContent;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.mMessages.isEmpty()) {
            return null;
        }
        MessagingMessage messagingMessage = this.mMessages.get(r0.size() - 1);
        CharSequence text = messagingMessage.getMessage() != null ? messagingMessage.getMessage().getText() : null;
        if (text != null || !(messagingMessage instanceof MessagingImageMessage)) {
            return text;
        }
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.conversation_single_line_image_placeholder));
        spannableString.setSpan(new StyleSpan(2), 0, spannableString.length(), 17);
        return spannableString;
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

        public void add(TouchDelegate touchDelegate) {
            this.mDelegates.add(touchDelegate);
        }

        public void clear() {
            this.mDelegates.clear();
        }

        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            Iterator<TouchDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                TouchDelegate next = it.next();
                motionEvent.setLocation(x, y);
                if (next.onTouchEvent(motionEvent)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static int getFontScaledMarginHeight(Context context, int i) throws Resources.NotFoundException {
        return (int) (context.getResources().getDimensionPixelSize(i) * ((((context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density) - 1.0f) / 2.0f) + 1.0f));
    }
}
