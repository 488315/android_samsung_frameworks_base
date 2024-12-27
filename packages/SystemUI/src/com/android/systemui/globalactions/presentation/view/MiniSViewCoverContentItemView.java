package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MiniSViewCoverContentItemView {
    public final Context mContext;
    public final ViewGroup mParent;
    public final ResourceFactory mResourceFactory;
    public final ActionViewModel mViewModel;

    public MiniSViewCoverContentItemView(Context context, ActionViewModel actionViewModel, ViewGroup viewGroup, ResourceFactory resourceFactory) {
        this.mContext = context;
        this.mViewModel = actionViewModel;
        this.mParent = viewGroup;
        this.mResourceFactory = resourceFactory;
    }

    public final View inflateView() {
        return this.mViewModel.getActionInfo().getViewType() == ViewType.COVER_NOTI_VIEW ? LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_MINI_SVIEW_COVER_NOTIFICATION), this.mParent, false) : LayoutInflater.from(this.mContext).inflate(this.mResourceFactory.get(ResourceType.LAYOUT_MINI_SVIEW_COVER_ITEM), this.mParent, false);
    }

    public final void setViewAttrs(View view) {
        ImageView imageView = (ImageView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_MINI_SVIEW_COVER_ITEM_ICON));
        TextView textView = (TextView) view.findViewById(this.mResourceFactory.get(ResourceType.ID_MINI_SVIEW_COVER_ITEM_TEXT));
        if (this.mViewModel.getActionInfo().getViewType() != ViewType.COVER_NOTI_VIEW) {
            if (this.mViewModel.getActionInfo().getName().equals("power")) {
                imageView.setImageResource(this.mResourceFactory.get(ResourceType.DRAWABLE_POWEROFF));
            } else if (this.mViewModel.getActionInfo().getName().equals("restart")) {
                imageView.setImageResource(this.mResourceFactory.get(ResourceType.DRAWABLE_RESTART));
            }
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverContentItemView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MiniSViewCoverContentItemView.this.mViewModel.onPress();
                }
            });
        }
        textView.setText(this.mViewModel.getActionInfo().getLabel());
    }
}
