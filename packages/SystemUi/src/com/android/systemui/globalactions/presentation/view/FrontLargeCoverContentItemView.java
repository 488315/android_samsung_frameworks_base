package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrontLargeCoverContentItemView {
    public final Context mContext;
    public final boolean mIsCameraViewCover;
    public final boolean mIsIconOnly;
    public final boolean mIsWhiteTheme;
    public final ViewGroup mParent;
    public final ResourceFactory mResourceFactory;
    public final ActionViewModel mViewModel;

    public FrontLargeCoverContentItemView(Context context, ActionViewModel actionViewModel, ViewGroup viewGroup, boolean z, boolean z2, boolean z3, ResourceFactory resourceFactory) {
        this.mContext = context;
        this.mViewModel = actionViewModel;
        this.mParent = viewGroup;
        this.mIsIconOnly = z;
        this.mIsCameraViewCover = z3;
        this.mResourceFactory = resourceFactory;
        this.mIsWhiteTheme = z2;
    }

    public final void setViewAttrs(View view, boolean z) {
        ResourceType resourceType = ResourceType.ID_COVER_BTN_BACKGROUND;
        ResourceFactory resourceFactory = this.mResourceFactory;
        LinearLayout linearLayout = (LinearLayout) view.findViewById(resourceFactory.get(resourceType));
        ImageView imageView = (ImageView) view.findViewById(resourceFactory.get(ResourceType.ID_ICON));
        TextView textView = (TextView) view.findViewById(resourceFactory.get(ResourceType.ID_LABEL));
        SubscreenUtil.setLabelTextSize(R.dimen.sec_global_actions_front_large_cover_text_label_size, textView);
        TextView textView2 = (TextView) view.findViewById(resourceFactory.get(ResourceType.ID_COVER_TEXT));
        boolean z2 = this.mIsIconOnly;
        Context context = this.mContext;
        boolean z3 = this.mIsCameraViewCover;
        if (z3) {
            imageView.getLayoutParams().height = context.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_item_image_size);
            imageView.getLayoutParams().width = context.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_item_image_size);
            textView.setTextSize(context.getResources().getDimensionPixelOffset(R.dimen.global_actions_camera_view_cover_item_label_font_size));
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
        ActionViewModel actionViewModel = this.mViewModel;
        imageView.setImageResource(actionViewModel.getActionInfo().getIcon());
        imageView.setForeground(context.getResources().getDrawable(resourceFactory.get(ResourceType.DRAWABLE_ICON_BG_FOCUSED), null));
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentItemView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FrontLargeCoverContentItemView.this.mViewModel.onPress();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentItemView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                FrontLargeCoverContentItemView.this.mViewModel.onLongPress();
                return true;
            }
        });
        imageView.setContentDescription(actionViewModel.getActionInfo().getLabel());
        textView.setTextColor(Color.parseColor(this.mIsWhiteTheme ? "#252528" : "#fafaff"));
        textView.setText(actionViewModel.getActionInfo().getLabel());
        if (z3 || !z) {
            return;
        }
        ActionInfo actionInfo = actionViewModel.getActionInfo();
        textView2.setText(z2 ? actionInfo.getLabel() : actionInfo.getDescription());
        textView2.setVisibility(0);
    }
}
