package com.android.systemui.dextouchpad.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.data.TouchpadGesturesGuideItems;
import com.android.systemui.dextouchpad.touchpad.TouchpadViewPager;
import com.android.systemui.dextouchpad.touchpad.TouchpadViewPagerAdapter;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.dextouchpad.util.Utils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadGesturesInternalDialog extends AlertDialog {
    public Button mBtnClosed;
    public final Context mContext;
    public final TouchpadGesturesGuideItems mItemList;
    public final boolean mNightMode;
    public TouchpadViewPager mTouchpadViewPager;
    public ViewPagerIndicator mViewPagerIndicator;

    public TouchpadGesturesInternalDialog(Context context, TouchpadGesturesGuideItems touchpadGesturesGuideItems, boolean z) {
        super(context, z ? 2132018766 : 0);
        this.mContext = context.getApplicationContext();
        this.mItemList = touchpadGesturesGuideItems;
        this.mNightMode = z;
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean z = Features.DEBUG;
        if (z) {
            Log.d("DexTouchpadTouchpadGesturesInternalDialog", "onCreate()");
        }
        getWindow().setGravity(17);
        getWindow().setGravity(80);
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_touchpad_gestures_internal, new FrameLayout(this.mContext));
        setContentView(inflate);
        this.mTouchpadViewPager = (TouchpadViewPager) findViewById(R.id.guide_view_pager);
        this.mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.guide_view_pager_indicator);
        Button button = (Button) findViewById(R.id.btnClosed);
        this.mBtnClosed = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.dextouchpad.view.TouchpadGesturesInternalDialog.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TouchpadGesturesInternalDialog.this.dismiss();
                Utils.sendSALogging("705", "7008", String.valueOf(TouchpadGesturesInternalDialog.this.mViewPagerIndicator.mLastReportedPosition + 1));
            }
        });
        TouchpadGesturesGuideItems touchpadGesturesGuideItems = this.mItemList;
        if (z) {
            Log.d("DexTouchpadTouchpadGesturesInternalDialog", "setLayout()");
        }
        this.mBtnClosed.setText(R.string.dex_touchpad_dialog_btn_close);
        this.mBtnClosed.setPadding(0, 10, 0, 25);
        this.mTouchpadViewPager.setClipToPadding(false);
        this.mTouchpadViewPager.setAdapter(new TouchpadViewPagerAdapter(this.mContext, touchpadGesturesGuideItems, this.mNightMode));
        this.mViewPagerIndicator.setViewPager(this.mTouchpadViewPager);
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int i = ((configuration.screenHeightDp * configuration.densityDpi) / 160) - 34;
        if (inflate.getHeight() > i) {
            ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
            layoutParams.height = i;
            inflate.setLayoutParams(layoutParams);
        }
        Utils.sendSALogging("705", "7006", String.valueOf(this.mContext.getResources().getConfiguration().orientation));
        Utils.sendSALogging("705", "7007", String.valueOf(this.mViewPagerIndicator.mLastReportedPosition + 1));
    }
}
