package com.android.systemui.power;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class WirelessMisalignView extends RelativeLayout implements View.OnClickListener {
    public Button mButton;
    public ImageView mCenterImageView;
    public WirelessMisalignListener mListener;
    public final AnonymousClass1 mOnClickListener;
    public RelativeLayout mTextContainerLayout;

    public WirelessMisalignView(Context context) {
        this(context, null);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Log.d("PowerUI.WirelessMisalignView", "onClick : misalign view gone");
        setWirelessMisalignViewVisibility(8);
        SecPowerUI secPowerUI = (SecPowerUI) this.mListener;
        secPowerUI.getClass();
        Log.i("PowerUI", "onWirelessMisalignCompleted");
        secPowerUI.removeMisalignView();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("PowerUI.WirelessMisalignView", "onFinishInflate");
        this.mTextContainerLayout = (RelativeLayout) findViewById(R.id.misalign_text_container);
        this.mCenterImageView = (ImageView) findViewById(R.id.center_align_image);
        Button button = (Button) findViewById(R.id.misalign_ok_button);
        this.mButton = button;
        button.setOnClickListener(this.mOnClickListener);
        setOnClickListener(this.mOnClickListener);
    }

    public final void setWirelessMisalignViewVisibility(int i) {
        Log.d("PowerUI.WirelessMisalignView", "setWirelessMisalignViewVisibility : " + (i == 0));
        setVisibility(i);
    }

    public WirelessMisalignView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.power.WirelessMisalignView$1] */
    public WirelessMisalignView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.power.WirelessMisalignView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Log.d("PowerUI.WirelessMisalignView", "button click : misalign view gone");
                WirelessMisalignView.this.setWirelessMisalignViewVisibility(8);
                SecPowerUI secPowerUI = (SecPowerUI) WirelessMisalignView.this.mListener;
                secPowerUI.getClass();
                Log.i("PowerUI", "onWirelessMisalignCompleted");
                secPowerUI.removeMisalignView();
            }
        };
    }
}
