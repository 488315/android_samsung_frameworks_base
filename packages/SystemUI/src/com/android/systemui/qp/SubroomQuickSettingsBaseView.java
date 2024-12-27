package com.android.systemui.qp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import com.android.systemui.qp.ViewPagerAdapter;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class SubroomQuickSettingsBaseView extends FrameLayout implements ViewPagerAdapter.SubRoomButtonListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageView[] dots;
    public int dotscount;
    public RelativeLayout mBackButton;
    public ImageView mBackImageView;
    public SubroomBrightnessSettingsView mBrightnessView;
    public final Context mContext;
    public LinearLayout mSettingsContainer;
    public final SubscreenFlashLightController mSubscreenFlashlightController;
    public final AnonymousClass1 mViewPagerOnPageChangeListener;
    public LinearLayout sliderDotspanel;
    public RTLViewPager viewPager;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qp.SubroomQuickSettingsBaseView$1] */
    public SubroomQuickSettingsBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mViewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.android.systemui.qp.SubroomQuickSettingsBaseView.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i) {
                int i2 = 0;
                while (true) {
                    SubroomQuickSettingsBaseView subroomQuickSettingsBaseView = SubroomQuickSettingsBaseView.this;
                    if (i2 >= subroomQuickSettingsBaseView.dotscount) {
                        subroomQuickSettingsBaseView.dots[i].setImageDrawable(subroomQuickSettingsBaseView.mContext.getResources().getDrawable(R.drawable.active_dot_indicator));
                        return;
                    } else {
                        subroomQuickSettingsBaseView.dots[i2].setImageDrawable(subroomQuickSettingsBaseView.mContext.getResources().getDrawable(R.drawable.non_active_dot_indicator));
                        i2++;
                    }
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i) {
            }
        };
        Log.d("SubroomQuickSettingsBaseView", "SubroomQuickSettingsBaseView");
        this.mContext = context;
        this.mSubscreenFlashlightController = SubscreenFlashLightController.getInstance(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSubscreenSettings(0);
        this.viewPager = (RTLViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.mContext);
        this.viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.subRoomButtonListener = this;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pager_dots);
        this.sliderDotspanel = linearLayout;
        this.dotscount = 2;
        this.dots = new ImageView[2];
        linearLayout.removeAllViews();
        for (int i = 0; i < this.dotscount; i++) {
            this.dots[i] = new ImageView(this.mContext);
            this.dots[i].setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.non_active_dot_indicator));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_page_indicator_dot_width), -1);
            layoutParams.setMargins(8, 0, 8, 0);
            this.sliderDotspanel.addView(this.dots[i], layoutParams);
        }
        this.dots[0].setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.active_dot_indicator));
        this.viewPager.mOnPageChangeListener = this.mViewPagerOnPageChangeListener;
        this.mSettingsContainer.requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        Log.d("SubroomQuickSettingsBaseView", "onDetachedFromWindow");
        this.viewPager.mOnPageChangeListener = null;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Log.d("SubroomQuickSettingsBaseView", "onFinishInflate");
        this.mSettingsContainer = (LinearLayout) findViewById(R.id.subscreen_quick_settings);
        this.mBrightnessView = (SubroomBrightnessSettingsView) findViewById(R.id.subroom_brightness_settings);
        this.mBackButton = (RelativeLayout) findViewById(R.id.subroom_back_button);
        this.mBackImageView = (ImageView) findViewById(R.id.back_image_view);
        Log.d("SubroomQuickSettingsBaseView", "SettingContainer==" + this.mSettingsContainer);
    }

    public final void setSubscreenSettings(int i) {
        this.mSettingsContainer.setVisibility(i == 0 ? 0 : 8);
        this.mBrightnessView.setVisibility(i == 3 ? 0 : 8);
        this.mBackButton.setVisibility(i != 0 ? 0 : 8);
        if (Character.getDirectionality(Locale.getDefault().getDisplayName().charAt(0)) == 1) {
            this.mBackImageView.setRotation(180.0f);
        } else {
            this.mBackImageView.setRotation(0.0f);
        }
    }
}
