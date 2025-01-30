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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        ViewType viewType = this.mViewModel.getActionInfo().getViewType();
        ViewType viewType2 = ViewType.COVER_NOTI_VIEW;
        ViewGroup viewGroup = this.mParent;
        ResourceFactory resourceFactory = this.mResourceFactory;
        Context context = this.mContext;
        return viewType == viewType2 ? LayoutInflater.from(context).inflate(resourceFactory.get(ResourceType.LAYOUT_MINI_SVIEW_COVER_NOTIFICATION), viewGroup, false) : LayoutInflater.from(context).inflate(resourceFactory.get(ResourceType.LAYOUT_MINI_SVIEW_COVER_ITEM), viewGroup, false);
    }

    public final void setViewAttrs(View view) {
        ResourceType resourceType = ResourceType.ID_MINI_SVIEW_COVER_ITEM_ICON;
        ResourceFactory resourceFactory = this.mResourceFactory;
        ImageView imageView = (ImageView) view.findViewById(resourceFactory.get(resourceType));
        TextView textView = (TextView) view.findViewById(resourceFactory.get(ResourceType.ID_MINI_SVIEW_COVER_ITEM_TEXT));
        ActionViewModel actionViewModel = this.mViewModel;
        if (actionViewModel.getActionInfo().getViewType() != ViewType.COVER_NOTI_VIEW) {
            if (actionViewModel.getActionInfo().getName().equals("power")) {
                imageView.setImageResource(resourceFactory.get(ResourceType.DRAWABLE_POWEROFF));
            } else if (actionViewModel.getActionInfo().getName().equals("restart")) {
                imageView.setImageResource(resourceFactory.get(ResourceType.DRAWABLE_RESTART));
            }
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverContentItemView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MiniSViewCoverContentItemView.this.mViewModel.onPress();
                }
            });
        }
        textView.setText(actionViewModel.getActionInfo().getLabel());
    }
}
