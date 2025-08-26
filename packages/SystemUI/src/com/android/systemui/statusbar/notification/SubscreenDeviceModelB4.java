package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.UserManager;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import dagger.Lazy;

/* loaded from: classes3.dex */
public final class SubscreenDeviceModelB4 extends SubscreenDeviceModelCommon {
    public final int DISPLAY_HEIGHT;

    public SubscreenDeviceModelB4(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.DISPLAY_HEIGHT = 260;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap == null || imageView == null) {
            return;
        }
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_image_size);
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("bindImageBitmap bitmapWidth : ", width, " bitmapHeight : ", height, " viewWidth : ");
        sbM.append(dimensionPixelSize);
        Log.d("S.S.N.", sbM.toString());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.gravity = 3;
        layoutParams.width = width > (((float) 320) * dimensionPixelSize) / ((float) 464) ? (int) dimensionPixelSize : (int) width;
        layoutParams.height = -2;
        imageView.setLayoutParams(layoutParams);
        imageView.setMaxHeight((int) dimensionPixelSize);
        imageView.setScaleType(height > width * ((float) 2) ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_CENTER);
        imageView.setVisibility(0);
        imageView.setImageBitmap(bitmap);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterContentViewResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getDetailAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? -1 : R.layout.subscreen_notification_detail_adapter_text_item : R.layout.subscreen_notification_detail_adapter_item, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterReplyWordResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_word;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final ImageView.ScaleType getDetailContentImageScaleType() {
        return ImageView.ScaleType.CENTER_CROP;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDispalyHeight() {
        return this.DISPLAY_HEIGHT;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getGroupAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 4 ? -1 : R.layout.subscreen_notification_group_adapter_hide_content : R.layout.subscreen_notification_adapter_header : R.layout.subscreen_notification_adapter_clear_all_footer : R.layout.subscreen_notification_group_adapter_item, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getListAdapterGroupItemResource() {
        return R.layout.subscreen_notification_list_adapter_group_summary_layout_item;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getListAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? -1 : R.layout.subscreen_notification_list_adapter_group_summary_layout : R.layout.subscreen_notification_list_adapter_hide_content : R.layout.subscreen_notification_adapter_no_notification : R.layout.subscreen_notification_list_adapter_custom_view : R.layout.subscreen_notification_adapter_clear_all_footer : R.layout.subscreen_notification_list_adapter_item, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewDismissAnimator(View view) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, -71.0f);
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.addListener(this.topPopupAnimationListener);
        return objectAnimatorOfFloat;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewShowAnimator(View view) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, -71.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(200L);
        return objectAnimatorOfFloat;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getReplyButtonView() {
        return LayoutInflater.from(((SubScreenManager) this.mSubScreenManagerLazy.get()).mActivity).inflate(R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_button, (ViewGroup) null);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getSelectedReplyBGColor() {
        return R.color.subscreen_notification_reply_word_select_color;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getSubscreenNotificationTipResource() {
        return R.layout.subscreen_notification_tip;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void hideDetailNotificationIfCallback() {
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null || !subscreenNotificationDetailAdapter.mCallbackClicked) {
            return;
        }
        hideDetailNotification();
        SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
        if (subscreenSubRoomNotification2 == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification2.mNotificationDetailAdapter) == null) {
            return;
        }
        subscreenNotificationDetailAdapter2.cleanAdapter();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDetailAdapterItemViewHolder(Context context, final SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        super.initDetailAdapterItemViewHolder(context, subscreenNotificationDetailAdapter, itemViewHolder);
        itemViewHolder.mReplyButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4.initDetailAdapterItemViewHolder.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (itemViewHolder.mInfo.mRemoteinput) {
                    Log.e("SubscreenNotificationDetailAdapter", "Click ReplyButton");
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subscreenNotificationDetailAdapter;
                    subscreenNotificationDetailAdapter2.mReplyclicked = true;
                    SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = itemViewHolder;
                    subscreenParentDetailItemViewHolder.startWaitState(subscreenNotificationDetailAdapter2, subscreenParentDetailItemViewHolder);
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY, SystemUIAnalytics.QPNE_KEY_APP, itemViewHolder.mInfo.mPkg);
                }
            }
        });
        itemViewHolder.mReplyVoiceButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4.initDetailAdapterItemViewHolder.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                subscreenNotificationDetailAdapter.mSvoiceEmojiClicked = true;
                if (itemViewHolder.mInfo.mRemoteinput) {
                    Log.d("SubscreenNotificationDetailAdapter", "Click ReplyVoiceButton");
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subscreenNotificationDetailAdapter;
                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = itemViewHolder;
                    subscreenNotificationDetailAdapter2.mSelectHolder = itemViewHolder2;
                    subscreenNotificationDetailAdapter2.mSubRoomNotification.startReplyActivity(1, itemViewHolder2.mInfo);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY_WITH_VOICE);
            }
        });
        itemViewHolder.mReplyEmojiButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4.initDetailAdapterItemViewHolder.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                subscreenNotificationDetailAdapter.mSvoiceEmojiClicked = true;
                if (itemViewHolder.mInfo.mRemoteinput) {
                    Log.d("SubscreenNotificationDetailAdapter", "Click ReplyEmojiButton");
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subscreenNotificationDetailAdapter;
                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = itemViewHolder;
                    subscreenNotificationDetailAdapter2.mSelectHolder = itemViewHolder2;
                    subscreenNotificationDetailAdapter2.mSubRoomNotification.startReplyActivity(2, itemViewHolder2.mInfo);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY_WITH_EMOJI);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isRunOnCoverAvailable() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        return !keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.isFingerprintOptionEnabled() || keyguardUpdateMonitor.isFaceOptionEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0077 A[PHI: r3 r9
      0x0077: PHI (r3v3 int) = (r3v1 int), (r3v9 int) binds: [B:33:0x0085, B:28:0x0075] A[DONT_GENERATE, DONT_INLINE]
      0x0077: PHI (r9v4 android.database.Cursor) = (r9v3 android.database.Cursor), (r9v5 android.database.Cursor) binds: [B:33:0x0085, B:28:0x0075] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008c  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        Exception exc;
        int i;
        String string;
        super.onBindDetailAdapterItemViewHolder(subscreenNotificationDetailAdapter, itemViewHolder);
        if (!itemViewHolder.mInfo.mRemoteinput || subscreenNotificationDetailAdapter.mItemPostionInGroup > 8) {
            itemViewHolder.mReplyButton.setVisibility(8);
            itemViewHolder.mReplyVoiceEmojiLayout.setVisibility(8);
            itemViewHolder.mReplylayout.setVisibility(8);
            setEditButton(itemViewHolder);
            return;
        }
        if (isRunOnCoverAvailable()) {
            itemViewHolder.mReplyButton.setVisibility(8);
            itemViewHolder.mReplyVoiceEmojiLayout.setVisibility(0);
            subscreenNotificationDetailAdapter.mSubRoomNotification.getClass();
            Cursor cursorQuery = null;
            try {
                try {
                    cursorQuery = SubscreenSubRoomNotification.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"), null, null, new String[]{"voice_input_type"}, null);
                    i = 0;
                    if (cursorQuery != null) {
                        while (cursorQuery.moveToNext()) {
                            try {
                                int columnIndex = cursorQuery.getColumnIndex("NAME");
                                if (columnIndex != -1 && (string = cursorQuery.getString(columnIndex)) != null && !string.isEmpty() && string.equals("voice_input_type")) {
                                    i = cursorQuery.getInt(cursorQuery.getColumnIndex("VALUE"));
                                }
                            } catch (Exception e) {
                                exc = e;
                                Log.e("SubscreenSubRoomNotification", "Error while get voice_input_type value ", exc);
                                if (cursorQuery != null) {
                                }
                                if (i != 1) {
                                }
                                itemViewHolder.inflateReplyWord();
                                itemViewHolder.mReplylayout.setVisibility(0);
                                setEditButton(itemViewHolder);
                            }
                        }
                    }
                } catch (Exception e2) {
                    exc = e2;
                    i = 0;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (i != 1) {
                    itemViewHolder.mReplyVoiceButton.setVisibility(8);
                }
            } finally {
            }
        } else {
            itemViewHolder.mReplyButton.setVisibility(0);
            itemViewHolder.mReplyVoiceEmojiLayout.setVisibility(8);
        }
        itemViewHolder.inflateReplyWord();
        itemViewHolder.mReplylayout.setVisibility(0);
        setEditButton(itemViewHolder);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void setMarqueeItem(TextView textView) {
        SubscreenNotificationDetail subscreenNotificationDetail = this.popupViewNotiTemplate;
        if (subscreenNotificationDetail != null) {
            subscreenNotificationDetail.mMarqueeText = textView;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void setPopupViewLayout(Context context, boolean z, FrameLayout frameLayout) {
        this.mPopUpViewLayout = z ? LayoutInflater.from(context).inflate(R.layout.subscreen_notification_detail_popup_top, frameLayout) : LayoutInflater.from(context).inflate(R.layout.subscreen_notification_detail_popup_full, frameLayout);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setReplyWordTextStyle(TextView textView, Typeface typeface) {
        textView.setTypeface(typeface);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final PopupWindow showReplyButtonViewPopupWindow(View view, View view2) {
        PopupWindow popupWindow = new PopupWindow(view, -1, -2);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, 0, 0, (view2.getHeight() / 2) + (this.DISPLAY_HEIGHT / 2));
        return popupWindow;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int smallIconPadding(boolean z, boolean z2, boolean z3) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_icon_circle_padding : z2 ? R.dimen.subscreen_noti_full_popup_icon_circle_padding : R.dimen.subscreen_noti_icon_circle_padding);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int squircleRadius(boolean z, boolean z2) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_small_icon_bg_radius : z2 ? R.dimen.subscreen_noti_popup_small_icon_bg_radius : R.dimen.subscreen_noti_list_small_icon_bg_radius);
    }
}
