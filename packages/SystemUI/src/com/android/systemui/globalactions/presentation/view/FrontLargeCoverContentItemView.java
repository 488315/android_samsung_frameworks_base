package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        LinearLayout linearLayout = (LinearLayout) view.findViewById(this.mResourceFactory.get(ResourceType.ID_COVER_BTN_BACKGROUND));
        ImageView imageView = (ImageView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_ICON));
        TextView textView = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_LABEL));
        if (textView != null) {
            FontSizeUtils.updateFontSize(textView, R.dimen.sec_global_actions_front_large_cover_text_label_size, 0.9f, 1.3f);
        }
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
        imageView.setImageResource(this.mViewModel.getActionInfo().getIcon());
        imageView.setForeground(this.mContext.getResources().getDrawable(this.mResourceFactory.get(ResourceType.DRAWABLE_ICON_BG_FOCUSED), null));
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
        imageView.setContentDescription(this.mViewModel.getActionInfo().getLabel());
        textView.setTextColor(Color.parseColor(this.mIsWhiteTheme ? "#252528" : "#fafaff"));
        textView.setText(this.mViewModel.getActionInfo().getLabel());
        if (z3 || !z) {
            return;
        }
        ActionInfo actionInfo = this.mViewModel.getActionInfo();
        textView2.setText(z2 ? actionInfo.getLabel() : actionInfo.getDescription());
        textView2.setVisibility(0);
    }
}
