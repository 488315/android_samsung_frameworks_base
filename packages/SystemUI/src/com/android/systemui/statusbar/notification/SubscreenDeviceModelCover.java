package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Property;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import dagger.Lazy;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public final class SubscreenDeviceModelCover extends SubscreenDeviceModelParent {
    public View mClearCoverHeaderLayout;
    public View mPopUpViewLayout;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public int mTopPopupHeight;
    public final SubscreenDeviceModelCover$mUpdateMonitorCallback$1 mUpdateMonitorCallback;

    public SubscreenDeviceModelCover(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCover$mUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUpdateCoverState(CoverState coverState) {
                if (coverState != null) {
                    int type = coverState.getType();
                    SubscreenDeviceModelCover subscreenDeviceModelCover = SubscreenDeviceModelCover.this;
                    if (type == 17) {
                        subscreenDeviceModelCover.mIsCovered = !coverState.getSwitchState();
                        if (subscreenDeviceModelCover.popupViewShowing) {
                            subscreenDeviceModelCover.dismissImmediately(2);
                        }
                        if (subscreenDeviceModelCover.presentationShowing) {
                            subscreenDeviceModelCover.dismissImmediately(1);
                        }
                    } else {
                        subscreenDeviceModelCover.mIsCovered = false;
                    }
                    if (subscreenDeviceModelCover.mIsCovered) {
                        subscreenDeviceModelCover.clearMainList();
                        subscreenDeviceModelCover.mController.notifPipeline.mShadeListBuilder.buildList();
                    } else {
                        subscreenDeviceModelCover.clearMainList();
                        if (subscreenDeviceModelCover.mFullScreenIntentEntries.size() > 0) {
                            subscreenDeviceModelCover.mFullScreenIntentEntries.clear();
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelCover.mSubRoomNotification;
                        if (subscreenSubRoomNotification != null) {
                            subscreenSubRoomNotification.updateNotificationState(null, 2);
                        }
                    }
                    KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m(" onUpdateCoverState - coverType = ", type, " isCovered = ", subscreenDeviceModelCover.mIsCovered, "S.S.N.");
                }
            }
        };
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCover$initKeyguardStateConroller$1
                @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                public final void onKeyguardShowingChanged() {
                    SubscreenDeviceModelCover subscreenDeviceModelCover = SubscreenDeviceModelCover.this;
                    if (subscreenDeviceModelCover.mIsCovered) {
                        subscreenDeviceModelCover.getClass();
                        subscreenDeviceModelCover.clearMainList();
                    }
                }
            });
        }
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCover$mSettingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS)) || Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION)) || Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_COVER_SCREEN_SHOW_NOTIFICATION))) {
                    SubscreenDeviceModelCover.this.updateNotiShowBlocked();
                }
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterContentViewResource() {
        return R.layout.clear_cover_subscreen_notification_detail_adapter_content_layout_item;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getDetailAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? -1 : R.layout.clear_cover_subscreen_notification_detail_adapter_text_item : R.layout.clear_cover_subscreen_notification_detail_adapter_item, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getGroupAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 4 ? -1 : R.layout.subscreen_notification_group_adapter_hide_content : R.layout.clear_cover_subscreen_notification_adapter_header : R.layout.clear_cover_subscreen_notification_adapter_clear_all_footer : R.layout.clear_cover_subscreen_notification_group_adapter_item, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getListAdapterGroupItemResource() {
        return R.layout.clear_cover_subscreen_notification_list_adapter_group_summary_layout_item;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getListAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? -1 : R.layout.clear_cover_subscreen_notification_list_adapter_group_summary_layout : R.layout.clear_cover_subscreen_notification_list_adapter_hide_content : R.layout.clear_cover_subscreen_notification_adapter_no_notification : R.layout.clear_cover_subscreen_notification_list_adapter_custom_view : R.layout.clear_cover_subscreen_notification_adapter_clear_all_footer : R.layout.clear_cover_subscreen_notification_list_adapter_item, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewDismissAnimator(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, this.mTopPopupHeight * (-1));
        ofFloat.setDuration(400L);
        ofFloat.addListener(this.topPopupAnimationListener);
        return ofFloat;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewShowAnimator(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, this.mTopPopupHeight * (-1), 0.0f);
        ofFloat.setDuration(400L);
        return ofFloat;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final WindowManager.LayoutParams getTopPopupLp() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 2021, android.R.id.KEYCODE_NUMPAD_DIVIDE, -3);
        layoutParams.setTitle("SubscreenNotification");
        layoutParams.gravity = 48;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        return layoutParams;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDisplay() {
        Display[] displays = ((DisplayManager) this.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (!(displays.length == 0)) {
            Display display = displays[0];
            this.mSubDisplay = display;
            Context context = this.mContext;
            Intrinsics.checkNotNull(display);
            this.mDisplayContext = context.createDisplayContext(display);
        } else {
            this.mDisplayContext = this.mContext;
            Log.d("S.S.N.", "Cover - fail to get subDisplay");
        }
        Context context2 = this.mDisplayContext;
        if (context2 == null) {
            context2 = null;
        }
        this.mTopPopupHeight = context2.getResources().getDimensionPixelSize(R.dimen.clear_cover_subscreen_noti_top_popup_height);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initMainHeaderView(LinearLayout linearLayout) {
        LinearLayout linearLayout2;
        View findViewById = linearLayout.findViewById(R.id.clear_cover_header_layout);
        this.mClearCoverHeaderLayout = findViewById;
        if (findViewById == null || (linearLayout2 = (LinearLayout) findViewById.findViewById(R.id.back_key)) == null) {
            return;
        }
        linearLayout2.setContentDescription(linearLayout2.getContext().getString(R.string.subscreen_back_button_content_description));
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCover$initMainHeaderView$backButton$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenDeviceModelCover subscreenDeviceModelCover = SubscreenDeviceModelCover.this;
                if (subscreenDeviceModelCover.mMainViewAnimator != null) {
                    return;
                }
                final SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelCover.mSubRoomNotification;
                subscreenDeviceModelCover.mMainViewAnimator = subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mNotificationAnimatorManager.alphaAnimatedMainView(300L, subscreenSubRoomNotification.mSubscreenMainLayout, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelCover$initMainHeaderView$backButton$1$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenSubRoomNotification.this;
                        if (subscreenSubRoomNotification2.mIsShownDetail) {
                            subscreenSubRoomNotification2.hideDetailNotification();
                        } else {
                            subscreenSubRoomNotification2.hideGroupNotification();
                        }
                    }
                }) : null;
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initialize() {
        super.initialize();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        getMSettingsHelper().registerCallback(this.mSettingsListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS), Settings.Secure.getUriFor(SettingsHelper.INDEX_TURN_ON_COVER_SCREEN_FOR_NOTIFICATION), Settings.Secure.getUriFor(SettingsHelper.INDEX_COVER_SCREEN_SHOW_NOTIFICATION));
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isProper(NotificationEntry notificationEntry, boolean z) {
        return super.isProper(notificationEntry, z) && this.mIsCovered;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void makePopupDetailView(Context context, NotificationEntry notificationEntry, boolean z, FrameLayout frameLayout) {
        SubscreenNotificationInfo subscreenNotificationInfo;
        boolean z2;
        int i;
        ExpandableNotificationRow expandableNotificationRow;
        SubscreenNotificationInfoManager subscreenNotificationInfoManager;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("makePopupDetailView Cover- ", notificationEntry != null ? notificationEntry.mKey : null, "S.S.N.");
        this.mPopUpViewLayout = z ? LayoutInflater.from(context).inflate(R.layout.clear_cover_subscreen_notification_detail_popup_top, frameLayout) : LayoutInflater.from(context).inflate(R.layout.clear_cover_subscreen_notification_detail_popup_full, frameLayout);
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || (subscreenNotificationInfoManager = subscreenSubRoomNotification.mNotificationInfoManager) == null) {
            subscreenNotificationInfo = null;
        } else {
            subscreenNotificationInfo = subscreenNotificationInfoManager.createItemsData(notificationEntry != null ? notificationEntry.row : null);
        }
        View view = this.mPopUpViewLayout;
        TextView textView = view != null ? (TextView) view.findViewById(R.id.subscreen_notification_title_text) : null;
        View view2 = this.mPopUpViewLayout;
        TextView textView2 = view2 != null ? (TextView) view2.findViewById(R.id.subscreen_notification_content_text) : null;
        View view3 = this.mPopUpViewLayout;
        ImageView imageView = view3 != null ? (ImageView) view3.findViewById(R.id.subscreen_notification_header_icon) : null;
        View view4 = this.mPopUpViewLayout;
        ImageView imageView2 = view4 != null ? (ImageView) view4.findViewById(R.id.two_phone_icon) : null;
        View view5 = this.mPopUpViewLayout;
        ImageView imageView3 = view5 != null ? (ImageView) view5.findViewById(R.id.secure_icon) : null;
        if (textView != null) {
            textView.setVisibility(0);
        }
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
        boolean z3 = subscreenNotificationInfo != null && subscreenNotificationInfo.mIsMessagingStyle;
        Drawable drawable = subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null;
        Icon icon = subscreenNotificationInfo != null ? subscreenNotificationInfo.mConversationIcon : null;
        Drawable drawable2 = subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppIcon : null;
        String title = subscreenNotificationInfo != null ? subscreenNotificationInfo.getTitle() : null;
        String str = subscreenNotificationInfo != null ? subscreenNotificationInfo.mContent : null;
        boolean z4 = (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null || !expandableNotificationRow.needsRedaction()) ? false : true;
        if (z4) {
            title = subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppName : null;
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
        }
        if (textView != null) {
            textView.setText(removeSpannableColor(title));
        }
        if (textView2 != null) {
            textView2.setText(removeSpannableColor(str));
        }
        boolean z5 = title == null || StringsKt__StringsKt.trim(title).toString().length() == 0;
        boolean z6 = str == null || StringsKt__StringsKt.trim(str).toString().length() == 0;
        if (z5) {
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            if (textView != null) {
                textView.setText(str);
            }
            if (z6 && textView != null) {
                textView.setText(subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppName : null);
            }
        }
        if (z6 && textView2 != null) {
            textView2.setVisibility(8);
        }
        if (z4 || z || !z3 || icon == null) {
            if (drawable2 == null || subscreenNotificationInfo == null || subscreenNotificationInfo.useSmallIcon()) {
                if (imageView != null) {
                    imageView.setImageDrawable(drawable);
                }
                updateSmallIconSquircleBg(imageView, false, !z);
                updateIconColor(imageView, notificationEntry);
            } else if (imageView != null) {
                imageView.setImageDrawable(drawable2);
            }
            z2 = false;
        } else {
            if (imageView != null) {
                imageView.setImageIcon(icon);
            }
            z2 = true;
        }
        if (z) {
            i = 8;
        } else {
            View view6 = this.mPopUpViewLayout;
            ImageView imageView4 = view6 != null ? (ImageView) view6.findViewById(R.id.subscreen_notification_sub_icon) : null;
            if (z2) {
                if (imageView4 != null) {
                    imageView4.setImageDrawable(drawable2);
                }
                if (imageView4 != null) {
                    imageView4.setVisibility(0);
                }
            } else if (imageView4 != null) {
                i = 8;
                imageView4.setVisibility(8);
                SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo);
                SubscreenDeviceModelParent.updateKnoxIcon(imageView3, subscreenNotificationInfo);
            }
            i = 8;
            SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView2, subscreenNotificationInfo);
            SubscreenDeviceModelParent.updateKnoxIcon(imageView3, subscreenNotificationInfo);
        }
        if (z && textView2 != null) {
            textView2.setVisibility(i);
        }
        SubscreenNotificationTemplate subscreenNotificationTemplate = this.popupViewNotiTemplate;
        if (subscreenNotificationTemplate == null) {
            return;
        }
        subscreenNotificationTemplate.mMarqueeText = textView2;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onDisplayReady() {
        Display[] displays = ((DisplayManager) this.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (!(displays.length == 0)) {
            Display display = displays[0];
            this.mSubDisplay = display;
            Context context = this.mContext;
            Intrinsics.checkNotNull(display);
            Context createDisplayContext = context.createDisplayContext(display);
            this.mDisplayContext = createDisplayContext;
            this.mWindowManager = (WindowManager) createDisplayContext.getSystemService("window");
            Log.d("S.S.N.", " CC screen - onDisplayReady");
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification != null) {
            Context context2 = this.mDisplayContext;
            if (context2 == null) {
                context2 = null;
            }
            if (SubscreenSubRoomNotification.sInstance == null || SubscreenSubRoomNotification.mContext.getResources().getConfiguration().densityDpi == context2.getResources().getConfiguration().densityDpi) {
                return;
            }
            SubscreenSubRoomNotification.mContext = context2;
            subscreenSubRoomNotification.mNotificationInfoManager.mContext = context2;
            subscreenSubRoomNotification.mNotificationTouchManager.mContext = context2;
            subscreenSubRoomNotification.mMainView = LayoutInflater.from(context2).inflate(R.layout.subscreen_notification_main, (ViewGroup) null);
            SubscreenSubRoomNotification.getDeviceModel().initMainHeaderView((LinearLayout) subscreenSubRoomNotification.mMainView.findViewById(R.id.subscreen_main_layout));
            subscreenSubRoomNotification.initRecyclerView();
            subscreenSubRoomNotification.initAdapter(subscreenSubRoomNotification.mNotificationDetailAdapter);
            subscreenSubRoomNotification.initAdapter(subscreenSubRoomNotification.mNotificationListAdapter);
            subscreenSubRoomNotification.initAdapter(subscreenSubRoomNotification.mNotificationGroupAdapter);
        }
    }

    public final CharSequence removeSpannableColor(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return null;
        }
        int color = this.mContext.getColor(R.color.subscreen_notification_primary_default);
        SpannableString spannableString = new SpannableString(charSequence);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        Intrinsics.checkNotNull(charSequence);
        spannableString.setSpan(foregroundColorSpan, 0, charSequence.length(), 33);
        return spannableString;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setListAdpaterFirstChildTopMargin(SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
        if (this.mListAdapterItemPosition == 0) {
            View view = subscreenParentItemViewHolder.itemView;
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.clear_cover_subscreen_noti_list_layout_margin);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = dimensionPixelSize;
            view.setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setListAdpaterPosition(int i) {
        this.mListAdapterItemPosition = i;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int smallIconPadding(boolean z, boolean z2, boolean z3) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(z2 ? R.dimen.clear_cover_subscreen_noti_full_popup_icon_circle_padding : R.dimen.clear_cover_subscreen_noti_icon_circle_padding);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int squircleRadius(boolean z, boolean z2) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(z2 ? R.dimen.clear_cover_subscreen_noti_popup_small_icon_bg_radius : R.dimen.clear_cover_subscreen_noti_list_small_icon_bg_radius);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateMainHeaderViewVisibility(int i) {
        View view = this.mClearCoverHeaderLayout;
        if (view == null) {
            return;
        }
        view.setVisibility(i);
    }
}
