package com.android.systemui.qs.buttons;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSTooltipWindow {
    public static QSTooltipWindow sInstance;
    public final HandlerC21251 handler = new Handler() { // from class: com.android.systemui.qs.buttons.QSTooltipWindow.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 100 && QSTooltipWindow.this.isTooltipShown()) {
                QSTooltipWindow.this.mTipWindow.dismiss();
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
        popupWindow.setElevation(context.getResources().getDimension(R.dimen.qs_layout_detail_popup_menu_elevation));
        popupWindow.setContentView(inflate);
        popupWindow.setSoftInputMode(3);
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
        Context context = this.mContext;
        String string = context.getResources().getString(this.mToolTipString);
        TextView textView = this.mTooltipText;
        textView.setText(string);
        textView.setTextColor(context.getColor(R.color.tooltip_text_color));
        PopupWindow popupWindow = this.mTipWindow;
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.sec_qs_tooltip_frame_background));
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        Rect rect = new Rect(i2, iArr[1], view.getWidth() + i2, view.getHeight() + iArr[1]);
        View view2 = this.mContentView;
        view2.measure(-2, -2);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qs_multisim_preffered_slot_divider_padding_vertical);
        popupWindow.showAtLocation(view, 0, (rect.centerX() - (view2.getMeasuredWidth() / 2)) - dimensionPixelSize, (dimensionPixelSize / 2) + (rect.bottom - (rect.height() / 2)));
        HandlerC21251 handlerC21251 = this.handler;
        handlerC21251.removeMessages(100);
        handlerC21251.sendEmptyMessageDelayed(100, 4000L);
    }
}
