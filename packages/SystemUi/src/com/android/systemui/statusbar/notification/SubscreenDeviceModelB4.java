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
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenDeviceModelB4 extends SubscreenDeviceModelCommon {
    public final int DISPLAY_HEIGHT;

    public SubscreenDeviceModelB4(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.DISPLAY_HEIGHT = 260;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (bitmap != null) {
            float width = bitmap.getWidth();
            float height = bitmap.getHeight();
            float dimensionPixelSize = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_image_size);
            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("bindImageBitmap bitmapWidth : ", width, " bitmapHeight : ", height, " viewWidth : ");
            m88m.append(dimensionPixelSize);
            Log.d("S.S.N.", m88m.toString());
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
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterContentViewResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getDetailAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? -1 : R.layout.subscreen_notification_detail_adapter_text_item : R.layout.subscreen_notification_detail_adapter_item, (ViewGroup) recyclerView, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterReplyWordResource() {
        return R.layout.f764x57398719;
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
    public final View getGroupAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 4 ? -1 : R.layout.subscreen_notification_group_adapter_hide_content : R.layout.subscreen_notification_adapter_header : R.layout.subscreen_notification_adapter_clear_all_footer : R.layout.subscreen_notification_group_adapter_item, (ViewGroup) recyclerView, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getListAdapterGroupItemResource() {
        return R.layout.subscreen_notification_list_adapter_group_summary_layout_item;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getListAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? -1 : R.layout.subscreen_notification_list_adapter_group_summary_layout : R.layout.subscreen_notification_list_adapter_hide_content : R.layout.subscreen_notification_adapter_no_notification : R.layout.subscreen_notification_list_adapter_custom_view : R.layout.subscreen_notification_adapter_clear_all_footer : R.layout.subscreen_notification_list_adapter_item, (ViewGroup) recyclerView, false);
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
        return LayoutInflater.from(((SubScreenManager) this.mSubScreenManagerLazy.get()).mActivity).inflate(R.layout.f762x4b741f81, (ViewGroup) null);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getSelectedReplyBGColor() {
        return R.color.subscreen_notification_reply_word_select_color;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getSubscreenNotificationTipResource() {
        return R.layout.subscreen_notification_tip;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000b, code lost:
    
        if (r0.mCallbackClicked == true) goto L10;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void hideDetailNotificationIfCallback() {
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        boolean z = (subscreenSubRoomNotification == null || (r0 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? false : true;
        if (z) {
            hideDetailNotification();
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification2 == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification2.mNotificationDetailAdapter) == null) {
                return;
            }
            subscreenNotificationDetailAdapter.cleanAdapter();
        }
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
                    SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0203", "app", SubscreenNotificationDetailAdapter.ItemViewHolder.this.mInfo.mPkg);
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
                SystemUIAnalytics.sendEventLog("QPN102", "QPNE0205");
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
                SystemUIAnalytics.sendEventLog("QPN102", "QPNE0206");
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isRunOnCoverAvailable() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        return !keyguardUpdateMonitor.isSecure() || keyguardUpdateMonitor.isFingerprintOptionEnabled() || keyguardUpdateMonitor.isFaceOptionEnabled();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0073, code lost:
    
        if (r0 != null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0086, code lost:
    
        r13 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0087, code lost:
    
        if (r3 != 1) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x008a, code lost:
    
        r13 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x008b, code lost:
    
        if (r13 != false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x008d, code lost:
    
        r14.mReplyVoiceButton.setVisibility(8);
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        int i;
        String string;
        super.onBindDetailAdapterItemViewHolder(subscreenNotificationDetailAdapter, itemViewHolder);
        boolean z = itemViewHolder.mInfo.mRemoteinput;
        LinearLayout linearLayout = itemViewHolder.mReplylayout;
        LinearLayout linearLayout2 = itemViewHolder.mReplyVoiceEmojiLayout;
        TextView textView = itemViewHolder.mReplyButton;
        if (!z || subscreenNotificationDetailAdapter.mItemPostionInGroup > 8) {
            textView.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout.setVisibility(8);
            setEditButton(itemViewHolder);
            return;
        }
        if (isRunOnCoverAvailable()) {
            textView.setVisibility(8);
            linearLayout2.setVisibility(0);
            subscreenNotificationDetailAdapter.mSubRoomNotification.getClass();
            Cursor cursor = null;
            try {
                try {
                    cursor = SubscreenSubRoomNotification.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"), null, null, new String[]{"voice_input_type"}, null);
                    i = 0;
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            try {
                                int columnIndex = cursor.getColumnIndex("NAME");
                                if (columnIndex != -1 && (string = cursor.getString(columnIndex)) != null && !string.isEmpty() && string.equals("voice_input_type")) {
                                    i = cursor.getInt(cursor.getColumnIndex("VALUE"));
                                }
                            } catch (Exception e) {
                                e = e;
                                Log.e("SubscreenSubRoomNotification", "Error while get voice_input_type value ", e);
                            }
                        }
                    }
                } finally {
                    if (0 != 0) {
                        cursor.close();
                    }
                }
            } catch (Exception e2) {
                e = e2;
                i = 0;
            }
        } else {
            textView.setVisibility(0);
            linearLayout2.setVisibility(8);
        }
        itemViewHolder.inflateReplyWord();
        linearLayout.setVisibility(0);
        setEditButton(itemViewHolder);
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
        popupWindow.showAtLocation(view, 0, 0, (view2.getHeight() / 2) + ((this.DISPLAY_HEIGHT + 0) / 2) + 0);
        return popupWindow;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int smallIconPadding(boolean z, boolean z2, boolean z3) {
        return getMDisplayContext().getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_icon_circle_padding : z2 ? R.dimen.subscreen_noti_full_popup_icon_circle_padding : R.dimen.subscreen_noti_icon_circle_padding);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int squircleRadius(boolean z, boolean z2) {
        return getMDisplayContext().getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_small_icon_bg_radius : z2 ? R.dimen.subscreen_noti_popup_small_icon_bg_radius : R.dimen.subscreen_noti_list_small_icon_bg_radius);
    }
}
