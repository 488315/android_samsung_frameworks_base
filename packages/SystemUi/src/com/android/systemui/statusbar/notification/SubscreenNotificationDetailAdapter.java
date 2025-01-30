package com.android.systemui.statusbar.notification;

import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationDetailAdapter extends SubscreenParentAdapter {
    public static SubscreenNotificationDetailAdapter sInstance;
    public int mItemPostionInGroup;
    public PopupWindow mPopupWindow;
    public SubscreenNotificationInfo mPrevSelectNotificationInfo;
    public View mReplyButtonView;
    public SubscreenParentDetailItemViewHolder mSelectHolder;
    public SubscreenNotificationInfo mSelectNotificationInfo;
    public static final Typeface REGULAR = Typeface.create("roboto-regular", 0);
    public static final Typeface MEDIUM = Typeface.create("sec-roboto-light", 1);
    public boolean mReplyclicked = false;
    public boolean mCallbackClicked = false;
    public boolean mNeedToUnlock = false;
    public boolean mSvoiceEmojiClicked = false;
    public AlphaAnimation mReplySendAlphaAnimation = null;
    public boolean mUpdatedInfo = false;
    public boolean mIsShownReplyButtonWindow = false;
    public final ScrollInfo mScrollInfo = new ScrollInfo();
    public final ViewOnUnhandledKeyEventListenerC27421 mPopupWindowKeyEventListener = new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.1
        @Override // android.view.View.OnUnhandledKeyEventListener
        public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 67) {
                return false;
            }
            SubscreenNotificationDetailAdapter.this.dismissReplyButtons(false);
            return true;
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ItemViewHolder extends SubscreenParentDetailItemViewHolder {
        public String mBodyLayoutString;
        public String mPrevSender;

        public ItemViewHolder(View view) {
            super(view);
            this.mPrevSender = null;
            this.mBodyLayoutString = null;
            SubscreenNotificationDetailAdapter.this.mDeviceModel.initDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter.this.mContext, SubscreenNotificationDetailAdapter.this, this);
        }

        public final void inflateReplyWord() {
            LinearLayout linearLayout = this.mReplylayout;
            if (linearLayout.getChildCount() > 0) {
                linearLayout.removeAllViews();
            }
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = SubscreenNotificationDetailAdapter.this;
            int size = subscreenNotificationDetailAdapter.mNotificationInfoManager.mReplyWordList.size();
            for (int i = 0; i < size; i++) {
                View inflate = LayoutInflater.from(subscreenNotificationDetailAdapter.mContext).inflate(subscreenNotificationDetailAdapter.mDeviceModel.getDetailAdapterReplyWordResource(), (ViewGroup) this.mContentLayout, false);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.subscreen_detail_word_line_top);
                final TextView textView = (TextView) inflate.findViewById(R.id.subscreen_detail_word);
                String str = (String) subscreenNotificationDetailAdapter.mNotificationInfoManager.mReplyWordList.get(i);
                if (i == 0) {
                    imageView.setVisibility(0);
                }
                textView.setText(str);
                SubscreenNotificationDetailAdapter.this.setReplyWordTextStyle(inflate, SubscreenNotificationDetailAdapter.REGULAR, false, 0.2f, 1.0f);
                inflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.ItemViewHolder.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
                        if (subscreenNotificationDetailAdapter2.mReplyButtonView != null) {
                            Log.d("SubscreenNotificationDetailAdapter", "CoverReplyButtonView is already existed.");
                            return;
                        }
                        subscreenNotificationDetailAdapter2.mDeviceModel.enableGoToTopButton();
                        int dispalyHeight = SubscreenNotificationDetailAdapter.this.mDeviceModel.getDispalyHeight() - SubscreenNotificationDetailAdapter.this.mDeviceModel.getMainHeaderViewHeight();
                        int computeVerticalScrollOffset = SubscreenNotificationDetailAdapter.this.mSubRoomNotification.mNotificationRecyclerView.computeVerticalScrollOffset();
                        SubscreenNotificationDetailAdapter.this.mSubRoomNotification.mNotificationRecyclerView.smoothScrollBy(0, (int) ((view.getY() + (ItemViewHolder.this.mReplylayout.getY() + ItemViewHolder.this.mReplyContainer.getY())) - (((dispalyHeight / 2) + computeVerticalScrollOffset) - (view.getHeight() / 2))), false);
                        for (int i2 = 0; i2 < ItemViewHolder.this.mReplylayout.getChildCount(); i2++) {
                            View childAt = ItemViewHolder.this.mReplylayout.getChildAt(i2);
                            if (childAt.equals(view)) {
                                SubscreenNotificationDetailAdapter.this.setReplyWordTextStyle(childAt, SubscreenNotificationDetailAdapter.MEDIUM, true, 0.2f, 1.0f);
                                ItemViewHolder itemViewHolder = ItemViewHolder.this;
                                SubscreenNotificationDetailAdapter.this.showReplyButtons(itemViewHolder, textView.getText().toString(), view, "pre-defined");
                            } else {
                                SubscreenNotificationDetailAdapter.this.setReplyWordTextStyle(childAt, SubscreenNotificationDetailAdapter.REGULAR, false, 1.0f, 0.2f);
                            }
                        }
                        if (ItemViewHolder.this.mSmartReplyLayout != null) {
                            for (int i3 = 0; i3 < ItemViewHolder.this.mSmartReplyLayout.getChildCount(); i3++) {
                                SubscreenNotificationDetailAdapter.this.setSmartReplyWordTextStyle(ItemViewHolder.this.mSmartReplyLayout.getChildAt(i3), 1.0f, 0.2f);
                            }
                        }
                        ItemViewHolder itemViewHolder2 = ItemViewHolder.this;
                        SubscreenNotificationDetailAdapter.this.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder2.mDetailButtonLayout, null, 250L, 1.0f, 0.2f);
                        ItemViewHolder itemViewHolder3 = ItemViewHolder.this;
                        SubscreenNotificationDetailAdapter.this.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder3.mEditButton, null, 250L, 1.0f, 0.2f);
                        SystemUIAnalytics.sendEventLog("QPN102", "QPNE0207");
                    }
                });
                linearLayout.addView(inflate);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScrollInfo {
        public int mCompleteItemUpdateReason = 0;
        public boolean mIsSendedQuickReply = false;
        public View mPrevFirstHistoryView = null;
        public View mPrevLastHistoryView = null;
        public int mPrevHistoryCount = 0;
        public int mPrevFirstHistoryViewBottomMargin = 0;
        public int mPrevBodyLayoutHeght = 0;

        public ScrollInfo() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TextViewHolder extends SubscreenParentDetailItemViewHolder {
        public TextViewHolder(View view) {
            super(view);
            this.mAdapter = SubscreenNotificationDetailAdapter.this;
            this.mOpenAppButton.setOnClickListener(new View.OnClickListener(SubscreenNotificationDetailAdapter.this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.TextViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Log.e("SubscreenNotificationDetailAdapter", "Click OpenAppButton");
                    SubscreenNotificationDetailAdapter.this.getClass();
                    TextViewHolder textViewHolder = TextViewHolder.this;
                    textViewHolder.startWaitState(SubscreenNotificationDetailAdapter.this, textViewHolder);
                    SystemUIAnalytics.sendEventLog("QPN102", "QPNE0204");
                }
            });
            SubscreenNotificationDetailAdapter.this.mDeviceModel.initDetailAdapterTextViewHolder(this);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter$1] */
    private SubscreenNotificationDetailAdapter() {
    }

    public static SubscreenNotificationDetailAdapter getInstance() {
        if (sInstance == null) {
            sInstance = new SubscreenNotificationDetailAdapter();
        }
        return sInstance;
    }

    public final void cleanAdapter() {
        this.mSelectHolder = null;
        this.mReplyclicked = false;
        this.mCallbackClicked = false;
        this.mNeedToUnlock = false;
        this.mSvoiceEmojiClicked = false;
    }

    public final void dismissReplyButtons(boolean z) {
        int i = 0;
        if (z) {
            if (this.mPopupWindow != null && this.mReplyButtonView != null) {
                Log.e("SubscreenNotificationDetailAdapter", "dismissReplyButtons");
                if (this.mReplySendAlphaAnimation != null) {
                    this.mReplyButtonView.clearAnimation();
                    this.mReplySendAlphaAnimation.cancel();
                    this.mReplySendAlphaAnimation = null;
                }
                this.mPopupWindow.dismiss();
                this.mPopupWindow = null;
                this.mReplyButtonView.removeOnUnhandledKeyEventListener(this.mPopupWindowKeyEventListener);
                this.mReplyButtonView = null;
            }
        } else if (this.mReplyButtonView != null && this.mIsShownReplyButtonWindow) {
            this.mDeviceModel.cancelReplySendButtonAnimator();
            this.mNotificationAnimatorManager.replyButtonAnimated(this.mReplyButtonView, new SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0(this, i), 1.0f, 0.8f, 1.0f, 0.0f);
            if (this.mUpdatedInfo) {
                notifyItemChanged(0);
            }
            this.mDeviceModel.runSmartReplyUncompletedOperation();
        }
        this.mUpdatedInfo = false;
        this.mIsShownReplyButtonWindow = false;
        this.mDeviceModel.enableGoToTopButton();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        SubscreenNotificationInfo subscreenNotificationInfo = this.mSelectNotificationInfo;
        if (subscreenNotificationInfo == null) {
            return 0;
        }
        if (subscreenNotificationInfo.mRow.needsRedaction() && this.mDeviceModel.isNotShwonNotificationState(this.mSelectNotificationInfo.mRow.mEntry)) {
            return 1;
        }
        SubscreenNotificationInfo subscreenNotificationInfo2 = this.mSelectNotificationInfo;
        return (subscreenNotificationInfo2.mContentView == null || subscreenNotificationInfo2.mHasSemanticCall || subscreenNotificationInfo2.mIsMissedCall) ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!(viewHolder instanceof ItemViewHolder)) {
            if (viewHolder instanceof TextViewHolder) {
                TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
                SubscreenNotificationInfo subscreenNotificationInfo = this.mSelectNotificationInfo;
                String contentHiddenText = subscreenNotificationInfo.mRow.needsRedaction() ? subscreenNotificationInfo.getContentHiddenText(subscreenNotificationInfo) : subscreenNotificationInfo.mContext.getResources().getString(R.string.subscreen_detail_adapter_custom_view);
                textViewHolder.mInfo = subscreenNotificationInfo;
                textViewHolder.mAppName.setText(subscreenNotificationInfo.mAppName);
                textViewHolder.mTitle.setText(contentHiddenText);
                textViewHolder.setIconDrawable();
                textViewHolder.updateShowInAppButtonVisibility();
                textViewHolder.updateClearButtonVisibility();
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = SubscreenNotificationDetailAdapter.this;
                subscreenNotificationDetailAdapter.mDeviceModel.setDetailAdapterTextHolderButtonContentDescription(textViewHolder, subscreenNotificationDetailAdapter);
                textViewHolder.mHandler.postDelayed(textViewHolder.mInitFocusRunnable, 100L);
                subscreenNotificationDetailAdapter.mDeviceModel.onBindDetailAdapterTextViewHolder(textViewHolder);
                subscreenNotificationDetailAdapter.mDeviceModel.getClass();
                SubscreenDeviceModelParent.updateTwoPhoneIcon(textViewHolder.mTwoPhoneIcon, subscreenNotificationInfo);
                subscreenNotificationDetailAdapter.mDeviceModel.getClass();
                SubscreenDeviceModelParent.updateKnoxIcon(textViewHolder.mSecureIcon, subscreenNotificationInfo);
                return;
            }
            return;
        }
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        SubscreenNotificationInfo subscreenNotificationInfo2 = this.mSelectNotificationInfo;
        if (subscreenNotificationInfo2 == null) {
            Log.e("SubscreenNotificationDetailAdapter", "info is null");
            ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel.hideDetailNotificationAnimated(0, true);
            return;
        }
        String title = subscreenNotificationInfo2.getTitle();
        int indexOf = title != null ? title.indexOf(":") : -1;
        if (subscreenNotificationInfo2.mIsGroupConversation && indexOf > -1) {
            try {
                title = title.substring(0, indexOf);
            } catch (StringIndexOutOfBoundsException e) {
                Log.w("SubscreenNotificationDetailAdapter", "StringIndexOutOfBoundsException: " + e + "title : " + title);
            }
        }
        if (title != null) {
            title = title.replace("\n", " ").trim();
        }
        itemViewHolder.mBodyLayoutString = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(title, "\n");
        itemViewHolder.mInfo = subscreenNotificationInfo2;
        itemViewHolder.mAppName.setText(subscreenNotificationInfo2.mAppName);
        TextView textView = itemViewHolder.mTitle;
        textView.setText(title);
        if (title == null || title.trim().isEmpty()) {
            textView.setVisibility(8);
        }
        itemViewHolder.setIconDrawable();
        LinearLayout linearLayout = itemViewHolder.mContentLayout;
        if (linearLayout.getChildCount() > 0) {
            linearLayout.removeAllViews();
        }
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
        subscreenNotificationDetailAdapter2.mDeviceModel.setContentViewItem(subscreenNotificationDetailAdapter2.mContext, itemViewHolder);
        itemViewHolder.updateShowInAppButtonVisibility();
        itemViewHolder.updateClearButtonVisibility();
        subscreenNotificationDetailAdapter2.mDeviceModel.setDetailAdapterItemHolderButtonContentDescription(subscreenNotificationDetailAdapter2, itemViewHolder);
        itemViewHolder.mHandler.postDelayed(itemViewHolder.mInitFocusRunnable, 100L);
        subscreenNotificationDetailAdapter2.mDeviceModel.onBindDetailAdapterItemViewHolder(subscreenNotificationDetailAdapter2, itemViewHolder);
        subscreenNotificationDetailAdapter2.mDeviceModel.getClass();
        SubscreenDeviceModelParent.updateTwoPhoneIcon(itemViewHolder.mTwoPhoneIcon, subscreenNotificationInfo2);
        subscreenNotificationDetailAdapter2.mDeviceModel.getClass();
        SubscreenDeviceModelParent.updateKnoxIcon(itemViewHolder.mSecureIcon, subscreenNotificationInfo2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        View detailAdapterLayout = this.mDeviceModel.getDetailAdapterLayout(recyclerView, i, this.mContext);
        if (i == 0) {
            return new ItemViewHolder(detailAdapterLayout);
        }
        if (i == 1) {
            return new TextViewHolder(detailAdapterLayout);
        }
        return null;
    }

    public final void setReplyWordTextStyle(View view, Typeface typeface, boolean z, float f, float f2) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.subscreen_detail_reply_item_layout);
        TextView textView = (TextView) view.findViewById(R.id.subscreen_detail_word);
        if (linearLayout == null || textView == null) {
            return;
        }
        this.mDeviceModel.setReplyWordTextStyle(textView, typeface);
        if (z) {
            linearLayout.setBackgroundColor(this.mContext.getColor(this.mDeviceModel.getSelectedReplyBGColor()));
        } else {
            this.mDeviceModel.setQuickReplyFocusBackground(linearLayout);
        }
        if (textView.getAlpha() != f2) {
            this.mNotificationAnimatorManager.alphaViewAnimated(textView, null, 250L, f, f2);
        }
    }

    public final void setSmartReplyWordTextStyle(View view, float f, float f2) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.subscreen_detail_smart_reply_item_box);
        if (linearLayout.getAlpha() != f2) {
            this.mNotificationAnimatorManager.alphaViewAnimated(linearLayout, null, 250L, f, f2);
        }
    }

    public final void showReplyButtons(final ItemViewHolder itemViewHolder, final String str, View view, final String str2) {
        this.mIsShownReplyButtonWindow = true;
        View replyButtonView = this.mDeviceModel.getReplyButtonView();
        this.mReplyButtonView = replyButtonView;
        if (replyButtonView == null) {
            return;
        }
        PopupWindow showReplyButtonViewPopupWindow = this.mDeviceModel.showReplyButtonViewPopupWindow(replyButtonView, view);
        this.mPopupWindow = showReplyButtonViewPopupWindow;
        showReplyButtonViewPopupWindow.setFocusable(true);
        this.mPopupWindow.update();
        this.mReplyButtonView.requestFocus();
        this.mReplyButtonView.addOnUnhandledKeyEventListener(this.mPopupWindowKeyEventListener);
        this.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                Log.e("SubscreenNotificationDetailAdapter", "OutSide Touch Popup Dismiss");
                SubscreenNotificationDetailAdapter.this.dismissReplyButtons(false);
                ItemViewHolder itemViewHolder2 = itemViewHolder;
                int i = 0;
                while (true) {
                    LinearLayout linearLayout = itemViewHolder2.mReplylayout;
                    if (i >= linearLayout.getChildCount()) {
                        break;
                    }
                    SubscreenNotificationDetailAdapter.this.setReplyWordTextStyle(linearLayout.getChildAt(i), SubscreenNotificationDetailAdapter.REGULAR, false, 0.2f, 1.0f);
                    i++;
                }
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = SubscreenNotificationDetailAdapter.this;
                LinearLayout linearLayout2 = itemViewHolder2.mSmartReplyLayout;
                if (linearLayout2 != null) {
                    for (int i2 = 0; i2 < linearLayout2.getChildCount(); i2++) {
                        View childAt = linearLayout2.getChildAt(i2);
                        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.sInstance;
                        subscreenNotificationDetailAdapter.setSmartReplyWordTextStyle(childAt, 0.2f, 1.0f);
                    }
                }
                subscreenNotificationDetailAdapter.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder2.mDetailButtonLayout, null, 250L, 0.2f, 1.0f);
                subscreenNotificationDetailAdapter.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder2.mEditButton, null, 250L, 0.2f, 1.0f);
            }
        });
        this.mNotificationAnimatorManager.replyButtonAnimated(this.mReplyButtonView, null, 0.8f, 1.0f, 0.0f, 1.0f);
        TextView textView = (TextView) this.mReplyButtonView.findViewById(R.id.cancel);
        View findViewById = this.mReplyButtonView.findViewById(R.id.send);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SubscreenNotificationDetailAdapter.this.dismissReplyButtons(false);
            }
        });
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.4
            public boolean isSent = false;

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                if (this.isSent) {
                    Log.e("SubscreenNotificationDetailAdapter", "send button is disabled");
                    return;
                }
                final SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
                boolean useHistory = subscreenNotificationController.useHistory(itemViewHolder.mInfo.mRow.mEntry);
                SubscreenNotificationDetailAdapter.this.mDeviceModel.setSmartReplyResultValue(-1, null, null);
                subscreenNotificationController.replyNotification(itemViewHolder.mInfo.mKey, str);
                SubscreenNotificationDetailAdapter.this.mReplySendAlphaAnimation = new AlphaAnimation(0.8f, 0.2f);
                SubscreenNotificationDetailAdapter.this.mReplySendAlphaAnimation.setDuration(1000L);
                SubscreenNotificationDetailAdapter.this.mReplySendAlphaAnimation.setRepeatCount(useHistory ? -1 : 0);
                if (!useHistory) {
                    SubscreenNotificationDetailAdapter.this.mReplySendAlphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.4.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationEnd(Animation animation) {
                            subscreenNotificationController.mDeviceModel.hideDetailNotificationAnimated(100, true);
                            View view3 = SubscreenNotificationDetailAdapter.this.mReplyButtonView;
                            if (view3 != null) {
                                view3.setAlpha(0.0f);
                            }
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationStart(Animation animation) {
                        }
                    });
                }
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = SubscreenNotificationDetailAdapter.this;
                subscreenNotificationDetailAdapter.mReplyButtonView.startAnimation(subscreenNotificationDetailAdapter.mReplySendAlphaAnimation);
                this.isSent = true;
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
                subscreenNotificationDetailAdapter2.mSelectNotificationInfo.mRow.mEntry.remoteInputText = "";
                subscreenNotificationDetailAdapter2.mScrollInfo.mIsSendedQuickReply = true;
                SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0211", "type", str2);
            }
        });
    }
}
