package com.android.systemui.qp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ViewPagerAdapter extends PagerAdapter {
    public final Context context;
    public ImageView mBrightnessButton;
    public ImageView mFlashLightButton;
    public SubRoomButtonListener subRoomButtonListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SubRoomButtonListener {
    }

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((RTLViewPager) viewGroup).removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        return 2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(int i, ViewGroup viewGroup) {
        LayoutInflater from = LayoutInflater.from(this.context);
        if (i == 0) {
            View inflate = from.inflate(R.layout.page_one_layout, (ViewGroup) null);
            ((RTLViewPager) viewGroup).addView(inflate, 0);
            return inflate;
        }
        if (i != 1) {
            return null;
        }
        View inflate2 = from.inflate(R.layout.page_two_layout, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate2.findViewById(R.id.brightness_image_view);
        this.mBrightnessButton = imageView;
        final int i2 = 1;
        imageView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qp.ViewPagerAdapter$$ExternalSyntheticLambda0
            public final /* synthetic */ ViewPagerAdapter f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i3 = i2;
                ViewPagerAdapter viewPagerAdapter = this.f$0;
                viewPagerAdapter.getClass();
                switch (i3) {
                    case 0:
                        if (!((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isFlashlightTileBlocked()) {
                            SubroomQuickSettingsBaseView subroomQuickSettingsBaseView = (SubroomQuickSettingsBaseView) viewPagerAdapter.subRoomButtonListener;
                            subroomQuickSettingsBaseView.getClass();
                            Log.d("SubroomQuickSettingsBaseView", "isFlashLightButtonClicked: ");
                            SubscreenFlashLightController subscreenFlashLightController = subroomQuickSettingsBaseView.mSubscreenFlashlightController;
                            if (subscreenFlashLightController != null) {
                                subscreenFlashLightController.startFlashActivity();
                                break;
                            }
                        } else {
                            Log.d("ViewPagerAdapter", "Subscreen Flashlight tile not available by KnoxStateMonitor.");
                            break;
                        }
                        break;
                    default:
                        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBrightnessBlocked()) {
                            Log.d("ViewPagerAdapter", "Subscreen Brightness tile not available by KnoxStateMonitor.");
                        } else {
                            final SubroomQuickSettingsBaseView subroomQuickSettingsBaseView2 = (SubroomQuickSettingsBaseView) viewPagerAdapter.subRoomButtonListener;
                            subroomQuickSettingsBaseView2.setSubscreenSettings(3);
                            subroomQuickSettingsBaseView2.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomQuickSettingsBaseView$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    SubroomQuickSettingsBaseView subroomQuickSettingsBaseView3 = SubroomQuickSettingsBaseView.this;
                                    int i4 = SubroomQuickSettingsBaseView.$r8$clinit;
                                    subroomQuickSettingsBaseView3.setSubscreenSettings(0);
                                }
                            });
                        }
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_COVER);
                        break;
                }
            }
        });
        final int i3 = 1;
        this.mBrightnessButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qp.ViewPagerAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                switch (i3) {
                    case 0:
                        Log.d("ViewPagerAdapter", "addClickBrightnessListeners mFlashLightButton onLongClick");
                        break;
                    default:
                        Log.d("ViewPagerAdapter", "addClickBrightnessListeners mBrightnessButton onLongClick");
                        break;
                }
                return true;
            }
        });
        ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.flashlight_image_view);
        this.mFlashLightButton = imageView2;
        final int i4 = 0;
        imageView2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qp.ViewPagerAdapter$$ExternalSyntheticLambda0
            public final /* synthetic */ ViewPagerAdapter f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i32 = i4;
                ViewPagerAdapter viewPagerAdapter = this.f$0;
                viewPagerAdapter.getClass();
                switch (i32) {
                    case 0:
                        if (!((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isFlashlightTileBlocked()) {
                            SubroomQuickSettingsBaseView subroomQuickSettingsBaseView = (SubroomQuickSettingsBaseView) viewPagerAdapter.subRoomButtonListener;
                            subroomQuickSettingsBaseView.getClass();
                            Log.d("SubroomQuickSettingsBaseView", "isFlashLightButtonClicked: ");
                            SubscreenFlashLightController subscreenFlashLightController = subroomQuickSettingsBaseView.mSubscreenFlashlightController;
                            if (subscreenFlashLightController != null) {
                                subscreenFlashLightController.startFlashActivity();
                                break;
                            }
                        } else {
                            Log.d("ViewPagerAdapter", "Subscreen Flashlight tile not available by KnoxStateMonitor.");
                            break;
                        }
                        break;
                    default:
                        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBrightnessBlocked()) {
                            Log.d("ViewPagerAdapter", "Subscreen Brightness tile not available by KnoxStateMonitor.");
                        } else {
                            final SubroomQuickSettingsBaseView subroomQuickSettingsBaseView2 = (SubroomQuickSettingsBaseView) viewPagerAdapter.subRoomButtonListener;
                            subroomQuickSettingsBaseView2.setSubscreenSettings(3);
                            subroomQuickSettingsBaseView2.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomQuickSettingsBaseView$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    SubroomQuickSettingsBaseView subroomQuickSettingsBaseView3 = SubroomQuickSettingsBaseView.this;
                                    int i42 = SubroomQuickSettingsBaseView.$r8$clinit;
                                    subroomQuickSettingsBaseView3.setSubscreenSettings(0);
                                }
                            });
                        }
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_COVER);
                        break;
                }
            }
        });
        final int i5 = 0;
        this.mFlashLightButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qp.ViewPagerAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                switch (i5) {
                    case 0:
                        Log.d("ViewPagerAdapter", "addClickBrightnessListeners mFlashLightButton onLongClick");
                        break;
                    default:
                        Log.d("ViewPagerAdapter", "addClickBrightnessListeners mBrightnessButton onLongClick");
                        break;
                }
                return true;
            }
        });
        ((RTLViewPager) viewGroup).addView(inflate2, 0);
        return inflate2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
