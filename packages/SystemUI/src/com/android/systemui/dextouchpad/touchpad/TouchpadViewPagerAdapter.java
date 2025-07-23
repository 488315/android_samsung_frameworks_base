package com.android.systemui.dextouchpad.touchpad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.data.GuideItems;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadViewPagerAdapter extends PagerAdapter {
    public final GuideItems itemList;
    public final Context mContext;
    public final boolean mNightMode;
    public TextView tv_content;
    public TextView tv_title;

    public TouchpadViewPagerAdapter(Context context, GuideItems guideItems, boolean z) {
        this.mContext = context;
        this.itemList = guideItems;
        this.mNightMode = z;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewPager viewPager, int i, Object obj) {
        viewPager.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        return this.itemList.mItemList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewPager viewPager, int i) {
        View inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.dialog_touchpad_gesture_internal_content, (ViewGroup) null);
        this.tv_title = (TextView) inflate.findViewById(R.id.title);
        this.tv_content = (TextView) inflate.findViewById(R.id.content_text);
        TextView textView = this.tv_title;
        GuideItems guideItems = this.itemList;
        textView.setText(((GuideItems.ItemInfo) guideItems.mItemList.get(i)).mTitle);
        this.tv_content.setText(((GuideItems.ItemInfo) guideItems.mItemList.get(i)).mContent);
        if (this.mNightMode) {
            this.tv_title.setTextColor(-1);
            this.tv_content.setTextColor(-1);
        } else {
            this.tv_title.setTextColor(-16777216);
            this.tv_content.setTextColor(-16777216);
        }
        LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.content_image);
        if (((GuideItems.ItemInfo) guideItems.mItemList.get(i)).mIsAnimation) {
            lottieAnimationView.setAnimation(((GuideItems.ItemInfo) guideItems.mItemList.get(i)).mView);
            lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
            lottieAnimationView.setRepeatCount(-1);
            lottieAnimationView.playAnimation();
        } else {
            lottieAnimationView.setImageResource(((GuideItems.ItemInfo) guideItems.mItemList.get(i)).mView);
        }
        viewPager.addView(inflate);
        return inflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }
}
