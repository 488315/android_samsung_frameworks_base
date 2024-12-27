package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HybridGroupManager {
    public final Context mContext;
    public int mOverflowNumberColor;
    public int mOverflowNumberPadding;
    public float mOverflowNumberSize;

    public HybridGroupManager(Context context) {
        this.mContext = context;
        Resources resources = context.getResources();
        this.mOverflowNumberSize = resources.getDimensionPixelSize(R.dimen.group_overflow_number_size);
        this.mOverflowNumberPadding = resources.getDimensionPixelSize(R.dimen.group_overflow_number_padding);
    }

    public static CharSequence resolveText(Notification notification2) {
        CharSequence charSequence = notification2.extras.getCharSequence("android.text");
        return charSequence == null ? notification2.extras.getCharSequence("android.bigText") : charSequence;
    }

    public static CharSequence resolveTitle(Notification notification2) {
        CharSequence charSequence = notification2.extras.getCharSequence("android.title");
        return charSequence == null ? notification2.extras.getCharSequence("android.title.big") : charSequence;
    }

    public final HybridNotificationView bindFromNotification(HybridNotificationView hybridNotificationView, View view, StatusBarNotification statusBarNotification, ViewGroup viewGroup) {
        AsyncHybridViewInflation.assertInLegacyMode();
        boolean z = false;
        if (hybridNotificationView == null) {
            Trace.beginSection("HybridGroupManager#bindFromNotification");
            Trace.beginSection("HybridGroupManager#inflateHybridView");
            hybridNotificationView = (HybridNotificationView) LayoutInflater.from(this.mContext).inflate(view instanceof ConversationLayout ? R.layout.hybrid_conversation_notification : R.layout.hybrid_notification, viewGroup, false);
            viewGroup.addView(hybridNotificationView);
            Trace.endSection();
            z = true;
        }
        AsyncHybridViewInflation.assertInLegacyMode();
        CharSequence resolveTitle = resolveTitle(statusBarNotification.getNotification());
        CharSequence resolveText = resolveText(statusBarNotification.getNotification());
        if (hybridNotificationView != null) {
            hybridNotificationView.bind(resolveTitle, resolveText, view);
        }
        if (z) {
            Trace.endSection();
        }
        return hybridNotificationView;
    }
}
