package com.android.systemui.dextouchpad.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.data.SPenGesturesGuideItems;
import com.android.systemui.dextouchpad.touchpad.TouchpadViewPager;
import com.android.systemui.dextouchpad.touchpad.TouchpadViewPagerAdapter;

/* loaded from: classes2.dex */
public class TouchpadSpenGesturesDialog extends AlertDialog {
    public Button mBtnClosed;
    public final Context mContext;
    public final SPenGesturesGuideItems mItemList;
    public final boolean mNightMode;
    public TouchpadViewPager mTouchpadViewPager;
    public ViewPagerIndicator mViewPagerIndicator;

    public TouchpadSpenGesturesDialog(Context context, SPenGesturesGuideItems sPenGesturesGuideItems, boolean z) {
        super(context, z ? 2132018766 : 0);
        this.mContext = context.getApplicationContext();
        this.mItemList = sPenGesturesGuideItems;
        this.mNightMode = z;
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) throws Resources.NotFoundException, NoSuchMethodException, SecurityException {
        super.onCreate(bundle);
        getWindow().setGravity(17);
        getWindow().setGravity(80);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_spen_gestures_internal, new FrameLayout(this.mContext));
        setContentView(viewInflate);
        this.mTouchpadViewPager = (TouchpadViewPager) findViewById(R.id.guide_view_pager);
        this.mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.guide_view_pager_indicator);
        Button button = (Button) findViewById(R.id.btnClosed);
        this.mBtnClosed = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.dextouchpad.view.TouchpadSpenGesturesDialog.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TouchpadSpenGesturesDialog.this.dismiss();
            }
        });
        SPenGesturesGuideItems sPenGesturesGuideItems = this.mItemList;
        this.mBtnClosed.setText(R.string.dex_touchpad_dialog_btn_close);
        this.mBtnClosed.setPadding(0, 10, 0, 25);
        this.mTouchpadViewPager.setClipToPadding(false);
        this.mTouchpadViewPager.setAdapter(new TouchpadViewPagerAdapter(this.mContext, sPenGesturesGuideItems, this.mNightMode));
        this.mViewPagerIndicator.setViewPager(this.mTouchpadViewPager);
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int i = ((configuration.screenHeightDp * configuration.densityDpi) / 160) - 34;
        if (viewInflate.getHeight() > i) {
            ViewGroup.LayoutParams layoutParams = viewInflate.getLayoutParams();
            layoutParams.height = i;
            viewInflate.setLayoutParams(layoutParams);
        }
    }
}
