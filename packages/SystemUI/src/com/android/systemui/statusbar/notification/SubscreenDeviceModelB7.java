package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenDeviceModelB7 extends SubscreenDeviceModelB5 {
    public int cutoutBottomMargin;
    public int cutoutTopMargin;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SubscreenDeviceModelB7(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void adjustLayoutByCutout(int i, int i2) {
        SubscreenRecyclerView subscreenRecyclerView;
        RecyclerView.Adapter adapter;
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_first_top_margin_b5);
        this.cutoutTopMargin = i;
        this.cutoutBottomMargin = i2;
        this.listFirstTopMargin = i + dimensionPixelSize;
        this.footerBottomMargin = i2;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || !subscreenSubRoomNotification.mIsInNotiRoom || (subscreenRecyclerView = subscreenSubRoomNotification.mNotificationRecyclerView) == null || (adapter = subscreenRecyclerView.mAdapter) == null) {
            return;
        }
        View view = this.mHeaderViewLayout;
        if (view == null || view.getVisibility() != 0) {
            subscreenRecyclerView.setAdapter(adapter);
            subscreenRecyclerView.scrollToPosition(subscreenSubRoomNotification.mRecyclerViewFirstVisibleItemPosition);
            return;
        }
        View view2 = this.mHeaderViewLayout;
        if (view2 != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view2.getLayoutParams();
            layoutParams.topMargin = this.cutoutTopMargin;
            view2.setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5
    public final int coverCutoutSize() {
        return this.mIsFlexMode ? this.cutoutTopMargin : this.cutoutBottomMargin;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDispalyHeight() {
        return 1048;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getGroupAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        if (i != 1) {
            return super.getGroupAdapterLayout(viewGroup, i, context);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_adapter_clear_all_footer_b7, viewGroup, false);
        ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) inflate.getLayoutParams())).bottomMargin += this.footerBottomMargin;
        return inflate;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getListAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        if (i == 1) {
            return LayoutInflater.from(context).inflate(R.layout.subscreen_notification_adapter_clear_all_footer_b7, viewGroup, false);
        }
        if (i != 3) {
            return super.getListAdapterLayout(viewGroup, i, context);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_adapter_no_notification_b7, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.subscreen_no_notification_layout);
        boolean z = this.mIsFlexMode;
        linearLayout.setPadding(0, z ? this.cutoutTopMargin : 0, 0, z ? 0 : this.cutoutBottomMargin);
        return inflate;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getMainHeaderViewHeight() {
        View view = this.mHeaderViewLayout;
        if (view == null) {
            return 0;
        }
        return view.getHeight() + this.cutoutTopMargin;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final WindowManager.LayoutParams getTopPopupLp() {
        WindowManager.LayoutParams topPopupLp = super.getTopPopupLp();
        topPopupLp.layoutInDisplayCutoutMode = 2;
        return topPopupLp;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initMainHeaderViewItems(Context context, SubscreenNotificationInfo subscreenNotificationInfo, boolean z) {
        super.initMainHeaderViewItems(context, subscreenNotificationInfo, z);
        View view = this.mHeaderViewLayout;
        if (view != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = this.cutoutTopMargin;
            view.setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setDimOnMainBackground(View view) {
        GradientDrawable gradientDrawable = (GradientDrawable) this.mContext.getDrawable(R.drawable.subscreen_notification_main_layout_background_b5);
        gradientDrawable.setCornerRadius(this.mContext.getResources().getDimension(R.dimen.subscreen_noti_dim_background_radius_b7));
        view.setBackground(gradientDrawable);
        view.setClipToOutline(true);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelB5, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setTipViewPadding(View view) {
        view.setPadding(0, this.cutoutTopMargin, 0, this.cutoutBottomMargin);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void showSubscreenNotification() {
        int i;
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        Context context = this.mDisplayContext;
        SubscreenNotificationDetail subscreenNotificationDetail = null;
        if (context == null) {
            context = null;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_top_popup_layout_top_margin_b5);
        int i2 = this.mNotiPopupType;
        if (i2 != 1) {
            i = 0;
            if (i2 == 2) {
                if (this.mIsFlexMode) {
                    dimensionPixelSize = 0;
                }
                subscreenNotificationDetail = this.popupViewNotiTemplate;
            }
            if (subscreenNotificationDetail != null && (frameLayout = subscreenNotificationDetail.mLayout) != null && (frameLayout2 = (FrameLayout) frameLayout.findViewById(R.id.subscreen_notification_top_popup_frame)) != null) {
                ((FrameLayout.LayoutParams) frameLayout2.getLayoutParams()).topMargin = i;
            }
            super.showSubscreenNotification();
        }
        if (this.mIsFlexMode) {
            dimensionPixelSize = this.cutoutTopMargin;
        }
        subscreenNotificationDetail = this.presentationNotiTemplate;
        i = dimensionPixelSize;
        if (subscreenNotificationDetail != null) {
            ((FrameLayout.LayoutParams) frameLayout2.getLayoutParams()).topMargin = i;
        }
        super.showSubscreenNotification();
    }
}
