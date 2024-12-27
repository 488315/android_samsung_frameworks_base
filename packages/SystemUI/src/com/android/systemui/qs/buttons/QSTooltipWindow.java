package com.android.systemui.qs.buttons;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSTooltipWindow {
    public static QSTooltipWindow sInstance;
    public final AnonymousClass1 handler = new Handler() { // from class: com.android.systemui.qs.buttons.QSTooltipWindow.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 100) {
                return;
            }
            QSTooltipWindow qSTooltipWindow = QSTooltipWindow.this;
            if (qSTooltipWindow.isTooltipShown()) {
                qSTooltipWindow.mTipWindow.dismiss();
            }
        }
    };
    public final View mContentView;
    public final Context mContext;
    public final PopupWindow mTipWindow;
    public int mToolTipString;
    public final TextView mTooltipText;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.buttons.QSTooltipWindow$1] */
    public QSTooltipWindow(Context context) {
        this.mContext = context;
        PopupWindow popupWindow = new PopupWindow(context);
        this.mTipWindow = popupWindow;
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sec_qs_tooltip_layout, (ViewGroup) null);
        this.mContentView = inflate;
        this.mTooltipText = (TextView) inflate.findViewById(R.id.tooltip_text);
        popupWindow.setHeight(-2);
        popupWindow.setWidth(-2);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setContentView(inflate);
        popupWindow.setSoftInputMode(3);
        popupWindow.setAnimationStyle(2132017216);
    }

    public static QSTooltipWindow getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QSTooltipWindow(context);
        }
        return sInstance;
    }

    public final void hideToolTip() {
        if (isTooltipShown()) {
            this.mTipWindow.dismiss();
        }
    }

    public final boolean isTooltipShown() {
        PopupWindow popupWindow = this.mTipWindow;
        return popupWindow != null && popupWindow.isShowing();
    }

    public final void showToolTip(View view, int i) {
        this.mToolTipString = i;
        this.mTooltipText.setText(this.mContext.getResources().getString(this.mToolTipString));
        this.mTooltipText.setTextColor(this.mContext.getColor(R.color.tooltip_text_color));
        this.mTipWindow.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.sec_qs_tooltip_frame_background));
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        Rect rect = new Rect(i2, iArr[1], view.getWidth() + i2, view.getHeight() + iArr[1]);
        this.mContentView.measure(-2, -2);
        Drawable background = this.mTipWindow.getBackground();
        Rect rect2 = new Rect();
        background.getPadding(rect2);
        int i3 = rect2.left + rect2.right;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_tooltip_frame_padding_left_right);
        this.mTipWindow.showAtLocation(view, 0, (rect.centerX() - (this.mContentView.getMeasuredWidth() + i3)) + this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_tooltip_frame_offset_left_right), (dimensionPixelSize / 2) + (rect.bottom - (rect.height() / 2)));
        AnonymousClass1 anonymousClass1 = this.handler;
        anonymousClass1.removeMessages(100);
        anonymousClass1.sendEmptyMessageDelayed(100, 4000L);
    }
}
