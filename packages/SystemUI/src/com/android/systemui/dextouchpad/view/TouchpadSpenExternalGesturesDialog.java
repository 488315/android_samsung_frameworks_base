package com.android.systemui.dextouchpad.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.data.GuideItems;
import com.android.systemui.dextouchpad.data.SPenGesturesGuideItems;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadSpenExternalGesturesDialog extends AlertDialog {
    public final Context mContext;
    public final LayoutInflater mInflater;
    public final SPenGesturesGuideItems mItemList;

    public TouchpadSpenExternalGesturesDialog(Context context, SPenGesturesGuideItems sPenGesturesGuideItems, boolean z) {
        super(context, z ? 2132018766 : 0);
        this.mContext = context;
        this.mItemList = sPenGesturesGuideItems;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(17);
        getWindow().setType(2008);
        getWindow().setLayout((int) this.mContext.getResources().getDimension(R.dimen.touchpad_spen_popup_width), -2);
        View inflate = this.mInflater.inflate(R.layout.dialog_spen_gestures_external, new LinearLayout(this.mContext));
        setContentView(inflate);
        ((Button) findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.dextouchpad.view.TouchpadSpenExternalGesturesDialog.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TouchpadSpenExternalGesturesDialog.this.dismiss();
            }
        });
        SPenGesturesGuideItems sPenGesturesGuideItems = this.mItemList;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container_layout);
        for (int i = 0; i < sPenGesturesGuideItems.mItemList.size(); i++) {
            View inflate2 = this.mInflater.inflate(R.layout.dialog_spen_gestures_external_content, (ViewGroup) null);
            TextView textView = (TextView) inflate2.findViewById(R.id.title);
            TextView textView2 = (TextView) inflate2.findViewById(R.id.content_text);
            textView.setText(((GuideItems.ItemInfo) sPenGesturesGuideItems.mItemList.get(i)).mTitle);
            textView2.setText(((GuideItems.ItemInfo) sPenGesturesGuideItems.mItemList.get(i)).mContent);
            linearLayout.addView(inflate2);
        }
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int i2 = ((configuration.screenHeightDp * configuration.densityDpi) / 160) - 34;
        if (inflate.getHeight() > i2) {
            ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
            layoutParams.height = i2;
            inflate.setLayoutParams(layoutParams);
        }
    }
}
