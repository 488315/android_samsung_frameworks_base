package com.android.systemui.p014qp.util;

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
public final class SubscreenToolTipWindow {
    public final HandlerC20221 handler = new Handler() { // from class: com.android.systemui.qp.util.SubscreenToolTipWindow.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 100) {
                return;
            }
            PopupWindow popupWindow = SubscreenToolTipWindow.this.mTipWindow;
            if (popupWindow != null && popupWindow.isShowing()) {
                SubscreenToolTipWindow.this.mTipWindow.dismiss();
            }
        }
    };
    public final View mContentView;
    public final Context mContext;
    public final PopupWindow mTipWindow;
    public final int mToolTipString;
    public final TextView mTooltipText;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qp.util.SubscreenToolTipWindow$1] */
    public SubscreenToolTipWindow(Context context, int i) {
        this.mContext = context;
        PopupWindow popupWindow = new PopupWindow(context);
        this.mTipWindow = popupWindow;
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sec_qs_tooltip_layout, (ViewGroup) null);
        this.mContentView = inflate;
        this.mTooltipText = (TextView) inflate.findViewById(R.id.tooltip_text);
        this.mToolTipString = i;
        popupWindow.setHeight(-2);
        popupWindow.setWidth(-2);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setElevation(context.getResources().getDimension(R.dimen.qs_layout_detail_popup_menu_elevation));
        popupWindow.setContentView(inflate);
        popupWindow.setSoftInputMode(3);
    }

    public final void showToolTip(View view) {
        Context context = this.mContext;
        String string = context.getResources().getString(this.mToolTipString);
        TextView textView = this.mTooltipText;
        textView.setText(string);
        textView.setTextColor(context.getColor(R.color.tooltip_text_color));
        PopupWindow popupWindow = this.mTipWindow;
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.sec_qs_tooltip_frame_background));
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        Rect rect = new Rect(i, iArr[1], view.getWidth() + i, view.getHeight() + iArr[1]);
        View view2 = this.mContentView;
        view2.measure(-2, -2);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qs_multisim_preffered_slot_divider_padding_vertical);
        popupWindow.showAtLocation(view, 0, (rect.centerX() - (view2.getMeasuredWidth() / 2)) - dimensionPixelSize, (dimensionPixelSize / 2) + (rect.bottom - (rect.height() / 2)));
        HandlerC20221 handlerC20221 = this.handler;
        handlerC20221.removeMessages(100);
        handlerC20221.sendEmptyMessageDelayed(100, 4000L);
    }
}
