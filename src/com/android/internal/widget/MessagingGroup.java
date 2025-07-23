package com.android.internal.widget;

import android.app.Flags;
import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.widget.MessagingLinearLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class MessagingGroup extends NotificationOptimizedLinearLayout implements MessagingLinearLayout.MessagingChild {
    public static final int IMAGE_DISPLAY_LOCATION_AT_END = 1;
    public static final int IMAGE_DISPLAY_LOCATION_EXTERNAL = 2;
    public static final int IMAGE_DISPLAY_LOCATION_INLINE = 0;
    private static final MessagingPool<MessagingGroup> sInstancePool = new MessagingPool<>(10);
    private ArrayList<MessagingMessage> mAddedMessages;
    private Icon mAvatarIcon;
    private CharSequence mAvatarName;
    private String mAvatarSymbol;
    private ImageView mAvatarView;
    private boolean mCanHideSenderIfFirst;
    private boolean mClippingDisabled;
    private LinearLayout mContentContainer;
    private int mConversationAvatarSize;
    private int mConversationContentStart;
    private Point mDisplaySize;
    private boolean mFirstLayout;
    private ViewGroup mImageContainer;
    private int mImageDisplayLocation;
    private boolean mIsCollapsed;
    private boolean mIsFirstGroupInLayout;
    private boolean mIsHidingAnimated;
    private boolean mIsInConversation;
    private MessagingImageMessage mIsolatedMessage;
    private int mLayoutColor;
    private MessagingLinearLayout mMessageContainer;
    private List<MessagingMessage> mMessages;
    private ViewGroup mMessagingIconContainer;
    private boolean mNeedsGeneratedAvatar;
    private int mNonConversationAvatarSize;
    private int mNonConversationContentStart;
    private int mNonConversationPaddingStart;
    private int mNotificationTextMarginTop;
    private int mRequestedMaxDisplayedLines;
    private Person mSender;
    private CharSequence mSenderName;
    private int mSenderTextPaddingSingleLine;
    ImageFloatingTextView mSenderView;
    private ProgressBar mSendingSpinner;
    private View mSendingSpinnerContainer;
    private int mSendingTextColor;
    private boolean mShowingAvatar;
    private boolean mSingleLine;
    private int mTextColor;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ImageDisplayLocation {
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public MessagingGroup(Context context) {
        super(context);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new ArrayList<>();
        this.mDisplaySize = new Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mIsCollapsed = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    public MessagingGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new ArrayList<>();
        this.mDisplaySize = new Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mIsCollapsed = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    public MessagingGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new ArrayList<>();
        this.mDisplaySize = new Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mIsCollapsed = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    public MessagingGroup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new ArrayList<>();
        this.mDisplaySize = new Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mIsCollapsed = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageContainer = (MessagingLinearLayout) findViewById(R.id.group_message_container);
        this.mSenderView = (ImageFloatingTextView) findViewById(R.id.message_name);
        this.mAvatarView = (ImageView) findViewById(R.id.message_icon);
        this.mImageContainer = (ViewGroup) findViewById(R.id.messaging_group_icon_container);
        this.mSendingSpinner = (ProgressBar) findViewById(R.id.messaging_group_sending_progress);
        this.mMessagingIconContainer = (ViewGroup) findViewById(R.id.message_icon_container);
        this.mContentContainer = (LinearLayout) findViewById(R.id.messaging_group_content_container);
        this.mSendingSpinnerContainer = findViewById(R.id.messaging_group_sending_progress_container);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mDisplaySize.x = displayMetrics.widthPixels;
        this.mDisplaySize.y = displayMetrics.heightPixels;
        this.mSenderTextPaddingSingleLine = resources.getDimensionPixelSize(R.dimen.messaging_group_singleline_sender_padding_end);
        this.mConversationContentStart = resources.getDimensionPixelSize(R.dimen.conversation_content_start);
        this.mNonConversationContentStart = resources.getDimensionPixelSize(R.dimen.notification_content_margin_start);
        this.mNonConversationPaddingStart = resources.getDimensionPixelSize(R.dimen.messaging_layout_icon_padding_start);
        this.mConversationAvatarSize = resources.getDimensionPixelSize(R.dimen.messaging_avatar_size);
        this.mNonConversationAvatarSize = resources.getDimensionPixelSize(R.dimen.notification_icon_circle_size);
        this.mNotificationTextMarginTop = resources.getDimensionPixelSize(R.dimen.notification_text_margin_top);
    }

    public void updateClipRect() {
        Rect rect;
        if (this.mSenderView.getVisibility() == 8 || this.mClippingDisabled) {
            rect = null;
        } else {
            int distanceFromParent = this.mSingleLine ? 0 : (getDistanceFromParent(this.mSenderView, this.mContentContainer) - getDistanceFromParent(this.mMessageContainer, this.mContentContainer)) + this.mSenderView.getHeight();
            int max = Math.max(this.mDisplaySize.x, this.mDisplaySize.y);
            rect = new Rect(-max, distanceFromParent, max, max);
        }
        this.mMessageContainer.setClipBounds(rect);
    }

    private int getDistanceFromParent(View view, ViewGroup viewGroup) {
        int i = 0;
        while (view != viewGroup) {
            i = (int) (i + view.getTop() + view.getTranslationY());
            view = (View) view.getParent();
        }
        return i;
    }

    public void setSender(Person person, CharSequence charSequence) {
        this.mSender = person;
        if (charSequence == null) {
            charSequence = person.getName();
        }
        if (Flags.cleanUpSpansAndNewLines() && charSequence != null) {
            charSequence = charSequence.toString();
        }
        this.mSenderName = charSequence;
        if (this.mSingleLine && !TextUtils.isEmpty(charSequence)) {
            charSequence = this.mContext.getResources().getString(R.string.conversation_single_line_name_display, charSequence);
        }
        this.mSenderView.lambda$setTextAsync$0(charSequence);
        boolean z = person.getIcon() == null;
        this.mNeedsGeneratedAvatar = z;
        if (!z) {
            setAvatar(person.getIcon());
        }
        updateSenderVisibility();
    }

    public void setShowingAvatar(boolean z) {
        this.mAvatarView.setVisibility(z ? 0 : 8);
        this.mMessagingIconContainer.setVisibility(z ? 0 : 8);
        this.mShowingAvatar = z;
    }

    public void setSending(boolean z) {
        int i = z ? 0 : 8;
        if (this.mSendingSpinnerContainer.getVisibility() != i) {
            this.mSendingSpinnerContainer.setVisibility(i);
            updateMessageColor();
        }
    }

    private int calculateSendingTextColor() {
        TypedValue typedValue = new TypedValue();
        this.mContext.getResources().getValue(R.dimen.notification_secondary_text_disabled_alpha, typedValue, true);
        return Color.argb((int) (Color.alpha(this.mTextColor) * typedValue.getFloat()), Color.red(this.mTextColor), Color.green(this.mTextColor), Color.blue(this.mTextColor));
    }

    public void setAvatar(Icon icon) {
        this.mAvatarIcon = icon;
        if (this.mShowingAvatar || icon == null) {
            this.mAvatarView.setImageIcon(icon);
        }
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
    }

    static MessagingGroup createGroup(MessagingLinearLayout messagingLinearLayout) {
        MessagingGroup acquire = sInstancePool.acquire();
        if (acquire == null) {
            acquire = (MessagingGroup) LayoutInflater.from(messagingLinearLayout.getContext()).inflate(getMessagingGroupLayoutResource(), (ViewGroup) messagingLinearLayout, false);
            acquire.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        messagingLinearLayout.addView(acquire);
        return acquire;
    }

    private static int getMessagingGroupLayoutResource() {
        return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_messaging_group : R.layout.notification_template_messaging_group;
    }

    public void removeMessage(final MessagingMessage messagingMessage, ArrayList<MessagingLinearLayout.MessagingChild> arrayList) {
        final View view = messagingMessage.getView();
        boolean isShown = view.isShown();
        final ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup == null) {
            return;
        }
        viewGroup.removeView(view);
        if (isShown && !MessagingLinearLayout.isGone(view)) {
            viewGroup.addTransientView(view, 0);
            performRemoveAnimation(view, new Runnable() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MessagingGroup.lambda$removeMessage$0(ViewGroup.this, view, messagingMessage);
                }
            });
        } else {
            arrayList.add(messagingMessage);
        }
    }

    static /* synthetic */ void lambda$removeMessage$0(ViewGroup viewGroup, View view, MessagingMessage messagingMessage) {
        viewGroup.removeTransientView(view);
        messagingMessage.recycle();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void recycle() {
        MessagingImageMessage messagingImageMessage = this.mIsolatedMessage;
        if (messagingImageMessage != null) {
            this.mImageContainer.removeView(messagingImageMessage);
        }
        for (int i = 0; i < this.mMessages.size(); i++) {
            MessagingMessage messagingMessage = this.mMessages.get(i);
            this.mMessageContainer.removeView(messagingMessage.getView());
            messagingMessage.recycle();
        }
        setAvatar(null);
        this.mAvatarView.setAlpha(1.0f);
        this.mAvatarView.setTranslationY(0.0f);
        this.mSenderView.setAlpha(1.0f);
        this.mSenderView.setTranslationY(0.0f);
        setAlpha(1.0f);
        this.mIsolatedMessage = null;
        this.mMessages = null;
        this.mSenderName = null;
        this.mAddedMessages.clear();
        this.mFirstLayout = true;
        setCanHideSenderIfFirst(false);
        setIsFirstInLayout(true);
        setMaxDisplayedLines(Integer.MAX_VALUE);
        setSingleLine(false);
        setShowingAvatar(true);
        MessagingPropertyAnimator.recycle(this);
        sInstancePool.release((MessagingPool<MessagingGroup>) this);
    }

    public void removeGroupAnimated(final Runnable runnable) {
        performRemoveAnimation(this, new Runnable() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MessagingGroup.this.lambda$removeGroupAnimated$1(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeGroupAnimated$1(Runnable runnable) {
        setAlpha(1.0f);
        MessagingPropertyAnimator.setToLaidOutPosition(this);
        if (runnable != null) {
            runnable.run();
        }
    }

    public void performRemoveAnimation(View view, Runnable runnable) {
        performRemoveAnimation(view, -view.getHeight(), runnable);
    }

    private void performRemoveAnimation(View view, int i, Runnable runnable) {
        MessagingPropertyAnimator.startLocalTranslationTo(view, i, MessagingLayout.FAST_OUT_LINEAR_IN);
        MessagingPropertyAnimator.fadeOut(view, runnable);
    }

    public CharSequence getSenderName() {
        return this.mSenderName;
    }

    public static void dropCache() {
        sInstancePool.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getMeasuredType() {
        if (this.mIsolatedMessage != null) {
            return 1;
        }
        boolean z = false;
        for (int childCount = this.mMessageContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mMessageContainer.getChildAt(childCount);
            if (childAt.getVisibility() != 8 && (childAt instanceof MessagingLinearLayout.MessagingChild)) {
                int measuredType = ((MessagingLinearLayout.MessagingChild) childAt).getMeasuredType();
                if (((MessagingLinearLayout.LayoutParams) childAt.getLayoutParams()).hide || (measuredType == 2)) {
                    return z ? 1 : 2;
                }
                if (measuredType == 1) {
                    return 1;
                }
                z = true;
            }
        }
        return 0;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        int i = 0;
        for (int i2 = 0; i2 < this.mMessageContainer.getChildCount(); i2++) {
            KeyEvent.Callback childAt = this.mMessageContainer.getChildAt(i2);
            if (childAt instanceof MessagingLinearLayout.MessagingChild) {
                i += ((MessagingLinearLayout.MessagingChild) childAt).getConsumedLines();
            }
        }
        if (this.mIsolatedMessage != null) {
            i = Math.max(i, 1);
        }
        return i + 1;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int i) {
        this.mRequestedMaxDisplayedLines = i;
        updateMaxDisplayedLines();
    }

    private void updateMaxDisplayedLines() {
        this.mMessageContainer.setMaxDisplayedLines(this.mSingleLine ? 1 : this.mRequestedMaxDisplayedLines);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void hideAnimated() {
        setIsHidingAnimated(true);
        removeGroupAnimated(new Runnable() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MessagingGroup.this.lambda$hideAnimated$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideAnimated$2() {
        setIsHidingAnimated(false);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setIsFirstInLayout(boolean z) {
        if (z != this.mIsFirstGroupInLayout) {
            this.mIsFirstGroupInLayout = z;
            updateSenderVisibility();
        }
    }

    public void setCanHideSenderIfFirst(boolean z) {
        if (this.mCanHideSenderIfFirst != z) {
            this.mCanHideSenderIfFirst = z;
            updateSenderVisibility();
        }
    }

    private void updateSenderVisibility() {
        this.mSenderView.setVisibility(((this.mIsFirstGroupInLayout || this.mSingleLine) && this.mCanHideSenderIfFirst) || TextUtils.isEmpty(this.mSenderName) ? 8 : 0);
    }

    private void updateIconVisibility() {
        if (Flags.notificationsRedesignTemplates()) {
            this.mMessagingIconContainer.setVisibility(this.mIsCollapsed ? 8 : 0);
        }
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public boolean hasDifferentHeightWhenFirst() {
        return (!this.mCanHideSenderIfFirst || this.mSingleLine || TextUtils.isEmpty(this.mSenderName)) ? false : true;
    }

    private void setIsHidingAnimated(boolean z) {
        ViewParent parent = getParent();
        this.mIsHidingAnimated = z;
        invalidate();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).invalidate();
        }
    }

    public Icon getAvatarSymbolIfMatching(CharSequence charSequence, String str, int i) {
        if (this.mAvatarName.equals(charSequence) && this.mAvatarSymbol.equals(str) && i == this.mLayoutColor) {
            return this.mAvatarIcon;
        }
        return null;
    }

    public void setCreatedAvatar(Icon icon, CharSequence charSequence, String str, int i) {
        if (this.mAvatarName.equals(charSequence) && this.mAvatarSymbol.equals(str) && i == this.mLayoutColor) {
            return;
        }
        setAvatar(icon);
        this.mAvatarSymbol = str;
        setLayoutColor(i);
        this.mAvatarName = charSequence;
    }

    public void setTextColors(int i, int i2) {
        this.mTextColor = i2;
        this.mSendingTextColor = calculateSendingTextColor();
        updateMessageColor();
        this.mSenderView.setTextColor(i);
    }

    public void setLayoutColor(int i) {
        if (i != this.mLayoutColor) {
            this.mLayoutColor = i;
            this.mSendingSpinner.setIndeterminateTintList(ColorStateList.valueOf(i));
        }
    }

    private void updateMessageColor() {
        if (this.mMessages != null) {
            int i = this.mSendingSpinnerContainer.getVisibility() == 0 ? this.mSendingTextColor : this.mTextColor;
            for (MessagingMessage messagingMessage : this.mMessages) {
                messagingMessage.setColor((messagingMessage.getMessage() == null || !messagingMessage.getMessage().isRemoteInputHistory()) ? this.mTextColor : i);
            }
        }
    }

    public void setMessages(List<MessagingMessage> list) {
        MessagingImageMessage messagingImageMessage = null;
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            MessagingMessage messagingMessage = list.get(i2);
            if (messagingMessage.getGroup() != this) {
                messagingMessage.setMessagingGroup(this);
                this.mAddedMessages.add(messagingMessage);
            }
            boolean z = messagingMessage instanceof MessagingImageMessage;
            if (this.mImageDisplayLocation != 0 && z) {
                messagingImageMessage = (MessagingImageMessage) messagingMessage;
            } else {
                if (removeFromParentIfDifferent(messagingMessage, this.mMessageContainer)) {
                    ViewGroup.LayoutParams layoutParams = messagingMessage.getView().getLayoutParams();
                    if (layoutParams != null && !(layoutParams instanceof MessagingLinearLayout.LayoutParams)) {
                        messagingMessage.getView().setLayoutParams(this.mMessageContainer.generateDefaultLayoutParams());
                    }
                    this.mMessageContainer.addView(messagingMessage.getView(), i);
                }
                if (z) {
                    ((MessagingImageMessage) messagingMessage).setIsolated(false);
                }
                if (i != this.mMessageContainer.indexOfChild(messagingMessage.getView())) {
                    this.mMessageContainer.removeView(messagingMessage.getView());
                    this.mMessageContainer.addView(messagingMessage.getView(), i);
                }
                i++;
            }
        }
        if (messagingImageMessage != null) {
            if (this.mImageDisplayLocation == 1 && removeFromParentIfDifferent(messagingImageMessage, this.mImageContainer)) {
                this.mImageContainer.removeAllViews();
                this.mImageContainer.addView(messagingImageMessage.getView());
            } else if (this.mImageDisplayLocation == 2) {
                this.mImageContainer.removeAllViews();
            }
            messagingImageMessage.setIsolated(true);
        } else if (this.mIsolatedMessage != null) {
            this.mImageContainer.removeAllViews();
        }
        this.mIsolatedMessage = messagingImageMessage;
        updateImageContainerVisibility();
        this.mMessages = list;
        if (android.widget.flags.Flags.dropNonExistingMessages()) {
            this.mAddedMessages.removeIf(new Predicate() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$setMessages$3;
                    lambda$setMessages$3 = MessagingGroup.this.lambda$setMessages$3((MessagingMessage) obj);
                    return lambda$setMessages$3;
                }
            });
        }
        updateMessageColor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setMessages$3(MessagingMessage messagingMessage) {
        return !this.mMessages.contains(messagingMessage);
    }

    private void updateImageContainerVisibility() {
        this.mImageContainer.setVisibility((this.mIsolatedMessage == null || this.mImageDisplayLocation != 1) ? 8 : 0);
    }

    private boolean removeFromParentIfDifferent(MessagingMessage messagingMessage, ViewGroup viewGroup) {
        ViewParent parent = messagingMessage.getView().getParent();
        if (parent == viewGroup) {
            return false;
        }
        if (!(parent instanceof ViewGroup)) {
            return true;
        }
        ((ViewGroup) parent).removeView(messagingMessage.getView());
        return true;
    }

    @Override // com.android.internal.widget.NotificationOptimizedLinearLayout, android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!this.mAddedMessages.isEmpty()) {
            final boolean z2 = this.mFirstLayout;
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.widget.MessagingGroup.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    Iterator it = MessagingGroup.this.mAddedMessages.iterator();
                    while (it.hasNext()) {
                        MessagingMessage messagingMessage = (MessagingMessage) it.next();
                        if (messagingMessage.getView().isShown()) {
                            MessagingPropertyAnimator.fadeIn(messagingMessage.getView());
                            if (!z2) {
                                MessagingPropertyAnimator.startLocalTranslationFrom(messagingMessage.getView(), messagingMessage.getView().getHeight(), MessagingLayout.LINEAR_OUT_SLOW_IN);
                            }
                        }
                    }
                    MessagingGroup.this.mAddedMessages.clear();
                    MessagingGroup.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        this.mFirstLayout = false;
        updateClipRect();
    }

    public int calculateGroupCompatibility(MessagingGroup messagingGroup) {
        if (!TextUtils.equals(getSenderName(), messagingGroup.getSenderName())) {
            return 0;
        }
        int i = 1;
        for (int i2 = 0; i2 < this.mMessages.size() && i2 < messagingGroup.mMessages.size(); i2++) {
            List<MessagingMessage> list = this.mMessages;
            MessagingMessage messagingMessage = list.get((list.size() - 1) - i2);
            List<MessagingMessage> list2 = messagingGroup.mMessages;
            if (!messagingMessage.sameAs(list2.get((list2.size() - 1) - i2))) {
                break;
            }
            i++;
        }
        return i;
    }

    public TextView getSenderView() {
        return this.mSenderView;
    }

    public View getAvatar() {
        return this.mAvatarView;
    }

    public Icon getAvatarIcon() {
        return this.mAvatarIcon;
    }

    public MessagingLinearLayout getMessageContainer() {
        return this.mMessageContainer;
    }

    public MessagingImageMessage getIsolatedMessage() {
        return this.mIsolatedMessage;
    }

    public boolean needsGeneratedAvatar() {
        return this.mNeedsGeneratedAvatar;
    }

    public Person getSender() {
        return this.mSender;
    }

    public void setClippingDisabled(boolean z) {
        this.mClippingDisabled = z;
    }

    public void setImageDisplayLocation(int i) {
        if (this.mImageDisplayLocation != i) {
            this.mImageDisplayLocation = i;
            updateImageContainerVisibility();
        }
    }

    public List<MessagingMessage> getMessages() {
        return this.mMessages;
    }

    public void setSingleLine(boolean z) {
        if (z != this.mSingleLine) {
            this.mSingleLine = z;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mMessageContainer.getLayoutParams();
            marginLayoutParams.topMargin = z ? 0 : this.mNotificationTextMarginTop;
            this.mMessageContainer.setLayoutParams(marginLayoutParams);
            this.mContentContainer.setOrientation(!z ? 1 : 0);
            ((ViewGroup.MarginLayoutParams) this.mSenderView.getLayoutParams()).setMarginEnd(z ? this.mSenderTextPaddingSingleLine : 0);
            this.mSenderView.setSingleLine(z);
            updateMaxDisplayedLines();
            updateClipRect();
            updateSenderVisibility();
        }
    }

    public void setIsCollapsed(boolean z) {
        this.mIsCollapsed = z;
        updateIconVisibility();
    }

    public boolean isSingleLine() {
        return this.mSingleLine;
    }

    public void setIsInConversation(boolean z) {
        int i;
        if (this.mIsInConversation != z) {
            this.mIsInConversation = z;
            if (Flags.notificationsRedesignTemplates()) {
                updateIconVisibility();
                return;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mMessagingIconContainer.getLayoutParams();
            if (this.mIsInConversation) {
                i = this.mConversationContentStart;
            } else {
                i = this.mNonConversationContentStart;
            }
            marginLayoutParams.width = i;
            this.mMessagingIconContainer.setLayoutParams(marginLayoutParams);
            this.mMessagingIconContainer.setPaddingRelative(z ? 0 : this.mNonConversationPaddingStart, 0, 0, 0);
            ViewGroup.LayoutParams layoutParams = this.mAvatarView.getLayoutParams();
            int i2 = this.mIsInConversation ? this.mConversationAvatarSize : this.mNonConversationAvatarSize;
            layoutParams.height = i2;
            layoutParams.width = i2;
            this.mAvatarView.setLayoutParams(layoutParams);
        }
    }
}
