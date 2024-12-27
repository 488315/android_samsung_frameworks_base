package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

public final class FrontCoverContentItemView {
    public final Context mContext;
    public final boolean mIsCameraViewCover;
    public final boolean mIsIconOnly;
    public final ViewGroup mParent;
    public final ResourceFactory mResourceFactory;
    public final ActionViewModel mViewModel;

    public FrontCoverContentItemView(Context context, ActionViewModel actionViewModel, ViewGroup viewGroup, boolean z, boolean z2, ResourceFactory resourceFactory) {
        this.mContext = context;
        this.mViewModel = actionViewModel;
        this.mParent = viewGroup;
        this.mIsIconOnly = z;
        this.mIsCameraViewCover = z2;
        this.mResourceFactory = resourceFactory;
    }

    public final void setViewAttrs(View view, boolean z) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(this.mResourceFactory.get(ResourceType.ID_COVER_BTN_BACKGROUND));
        ImageView imageView = (ImageView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_ICON));
        TextView textView = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_LABEL));
        TextView textView2 = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_COVER_TEXT));
        boolean z2 = this.mIsIconOnly;
        boolean z3 = this.mIsCameraViewCover;
        if (z3) {
            imageView.getLayoutParams().height = this.mContext.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_item_image_size);
            imageView.getLayoutParams().width = this.mContext.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_item_image_size);
            textView.setTextSize(this.mContext.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_item_label_font_size));
        } else if (z2) {
            textView.setVisibility(8);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.width = 64;
            layoutParams.height = 64;
            linearLayout.setLayoutParams(layoutParams);
            imageView.getLayoutParams().width = 40;
            imageView.getLayoutParams().height = 40;
            textView2.setPadding(0, 0, 0, 0);
        }
        if (this.mViewModel.getActionInfo().getName().equals("power")) {
            if (z2) {
                linearLayout.setBackground(this.mContext.getResources().getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_COVER_POWER_OFF_BG)));
                imageView.setImageResource(this.mResourceFactory.get(ResourceType.DRAWABLE_COVER_POWER_OFF_ICON));
            } else {
                imageView.setImageResource(this.mResourceFactory.get(ResourceType.DRAWABLE_POWEROFF));
            }
        } else if (this.mViewModel.getActionInfo().getName().equals("restart")) {
            if (z2) {
                linearLayout.setBackground(this.mContext.getResources().getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_COVER_RESTART_BG)));
                imageView.setImageResource(this.mResourceFactory.get(ResourceType.DRAWABLE_COVER_RESTART_ICON));
            } else {
                imageView.setImageResource(this.mResourceFactory.get(ResourceType.DRAWABLE_RESTART));
            }
        }
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontCoverContentItemView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FrontCoverContentItemView.this.mViewModel.onPress();
            }
        });
        textView.setText(this.mViewModel.getActionInfo().getLabel());
        if (z3 || !z) {
            return;
        }
        ActionInfo actionInfo = this.mViewModel.getActionInfo();
        textView2.setText(z2 ? actionInfo.getLabel() : actionInfo.getDescription());
        textView2.setVisibility(0);
    }
}
