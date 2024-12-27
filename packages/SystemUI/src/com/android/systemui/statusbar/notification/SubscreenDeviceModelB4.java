package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("bindImageBitmap bitmapWidth : ", width, " bitmapHeight : ", height, " viewWidth : ");
        m.append(dimensionPixelSize);
        Log.d("S.S.N.", m.toString());
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
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, -71.0f);
        ofFloat.setDuration(200L);
        ofFloat.addListener(this.topPopupAnimationListener);
        return ofFloat;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewShowAnimator(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, -71.0f, 0.0f);
        ofFloat.setDuration(200L);
        return ofFloat;
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
        itemViewHolder.mReplyButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4$initDetailAdapterItemViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (SubscreenNotificationDetailAdapter.ItemViewHolder.this.mInfo.mRemoteinput) {
                    Log.e("SubscreenNotificationDetailAdapter", "Click ReplyButton");
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subscreenNotificationDetailAdapter;
                    subscreenNotificationDetailAdapter2.mReplyclicked = true;
                    SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = SubscreenNotificationDetailAdapter.ItemViewHolder.this;
                    subscreenParentDetailItemViewHolder.startWaitState(subscreenNotificationDetailAdapter2, subscreenParentDetailItemViewHolder);
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY, SystemUIAnalytics.QPNE_KEY_APP, SubscreenNotificationDetailAdapter.ItemViewHolder.this.mInfo.mPkg);
                }
            }
        });
        itemViewHolder.mReplyVoiceButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4$initDetailAdapterItemViewHolder$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationDetailAdapter.this.mSvoiceEmojiClicked = true;
                if (itemViewHolder.mInfo.mRemoteinput) {
                    Log.d("SubscreenNotificationDetailAdapter", "Click ReplyVoiceButton");
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = itemViewHolder;
                    subscreenNotificationDetailAdapter2.mSelectHolder = itemViewHolder2;
                    subscreenNotificationDetailAdapter2.mSubRoomNotification.startReplyActivity(1, itemViewHolder2.mInfo);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY_WITH_VOICE);
            }
        });
        itemViewHolder.mReplyEmojiButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4$initDetailAdapterItemViewHolder$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationDetailAdapter.this.mSvoiceEmojiClicked = true;
                if (itemViewHolder.mInfo.mRemoteinput) {
                    Log.d("SubscreenNotificationDetailAdapter", "Click ReplyEmojiButton");
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0086, code lost:
    
        if (r3 != 1) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0089, code lost:
    
        r11.mReplyVoiceButton.setVisibility(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0082, code lost:
    
        if (r0 == null) goto L35;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindDetailAdapterItemViewHolder(com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter r10, com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.ItemViewHolder r11) {
        /*
            r9 = this;
            super.onBindDetailAdapterItemViewHolder(r10, r11)
            com.android.systemui.statusbar.notification.SubscreenNotificationInfo r0 = r11.mInfo
            boolean r0 = r0.mRemoteinput
            r1 = 8
            if (r0 == 0) goto Lab
            int r0 = r10.mItemPostionInGroup
            if (r0 > r1) goto Lab
            boolean r0 = r9.isRunOnCoverAvailable()
            r2 = 0
            if (r0 == 0) goto L95
            android.widget.TextView r0 = r11.mReplyButton
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r11.mReplyVoiceEmojiLayout
            r0.setVisibility(r2)
            com.android.systemui.statusbar.notification.SubscreenSubRoomNotification r10 = r10.mSubRoomNotification
            r10.getClass()
            java.lang.String r10 = "voice_input_type"
            java.lang.String[] r7 = new java.lang.String[]{r10}
            r0 = 0
            android.content.Context r3 = com.android.systemui.statusbar.notification.SubscreenSubRoomNotification.mContext     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L79
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L79
            java.lang.String r4 = "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"
            android.net.Uri r4 = android.net.Uri.parse(r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L79
            r5 = 0
            r6 = 0
            r8 = 0
            android.database.Cursor r0 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L79
            r3 = r2
            if (r0 == 0) goto L73
        L43:
            boolean r4 = r0.moveToNext()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            if (r4 == 0) goto L73
            java.lang.String r4 = "NAME"
            int r4 = r0.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            r5 = -1
            if (r4 == r5) goto L43
            java.lang.String r4 = r0.getString(r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            if (r4 == 0) goto L43
            boolean r5 = r4.isEmpty()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            if (r5 != 0) goto L43
            boolean r4 = r4.equals(r10)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            if (r4 == 0) goto L43
            java.lang.String r4 = "VALUE"
            int r4 = r0.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            int r3 = r0.getInt(r4)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            goto L43
        L6f:
            r9 = move-exception
            goto L8f
        L71:
            r10 = move-exception
            goto L7b
        L73:
            if (r0 == 0) goto L85
        L75:
            r0.close()
            goto L85
        L79:
            r10 = move-exception
            r3 = r2
        L7b:
            java.lang.String r4 = "SubscreenSubRoomNotification"
            java.lang.String r5 = "Error while get voice_input_type value "
            android.util.Log.e(r4, r5, r10)     // Catch: java.lang.Throwable -> L6f
            if (r0 == 0) goto L85
            goto L75
        L85:
            r10 = 1
            if (r3 != r10) goto L89
            goto L9f
        L89:
            android.widget.ImageView r10 = r11.mReplyVoiceButton
            r10.setVisibility(r1)
            goto L9f
        L8f:
            if (r0 == 0) goto L94
            r0.close()
        L94:
            throw r9
        L95:
            android.widget.TextView r10 = r11.mReplyButton
            r10.setVisibility(r2)
            android.widget.LinearLayout r10 = r11.mReplyVoiceEmojiLayout
            r10.setVisibility(r1)
        L9f:
            r11.inflateReplyWord()
            android.widget.LinearLayout r10 = r11.mReplylayout
            r10.setVisibility(r2)
            r9.setEditButton(r11)
            goto Lbd
        Lab:
            android.widget.TextView r10 = r11.mReplyButton
            r10.setVisibility(r1)
            android.widget.LinearLayout r10 = r11.mReplyVoiceEmojiLayout
            r10.setVisibility(r1)
            android.widget.LinearLayout r10 = r11.mReplylayout
            r10.setVisibility(r1)
            r9.setEditButton(r11)
        Lbd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB4.onBindDetailAdapterItemViewHolder(com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter, com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter$ItemViewHolder):void");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void setMarqueeItem(TextView textView) {
        SubscreenNotificationTemplate subscreenNotificationTemplate = this.popupViewNotiTemplate;
        if (subscreenNotificationTemplate == null) {
            return;
        }
        subscreenNotificationTemplate.mMarqueeText = textView;
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
