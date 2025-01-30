package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SideCoverContentItemView {
    public final Context mContext;
    public final ViewGroup mParent;
    public final ResourceFactory mResourceFactory;
    public final ActionViewModel mViewModel;

    public SideCoverContentItemView(Context context, ActionViewModel actionViewModel, ViewGroup viewGroup, ResourceFactory resourceFactory, boolean z, boolean z2) {
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
        return viewType == viewType2 ? LayoutInflater.from(context).inflate(resourceFactory.get(ResourceType.LAYOUT_SIDE_COVER_NOTIFICATION), viewGroup, false) : LayoutInflater.from(context).inflate(resourceFactory.get(ResourceType.LAYOUT_SIDE_COVER_ITEM_LIST_VIEW), viewGroup, false);
    }

    public final void setViewAttrs(View view) {
        Context context = this.mContext;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = i == 720 ? 320 : i == 1080 ? VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE : 640;
        int i3 = displayMetrics.densityDpi;
        if (i3 < i2) {
            view.setScaleX(1.143f);
            view.setScaleY(1.143f);
        } else if (i3 > i2) {
            view.setScaleX(0.889f);
            view.setScaleY(0.889f);
        }
        ResourceType resourceType = ResourceType.ID_COVER_ICON;
        ResourceFactory resourceFactory = this.mResourceFactory;
        ImageView imageView = (ImageView) view.findViewById(resourceFactory.get(resourceType));
        TextView textView = (TextView) view.findViewById(resourceFactory.get(ResourceType.ID_COVER_TEXT));
        ActionViewModel actionViewModel = this.mViewModel;
        if (actionViewModel.getActionInfo().getViewType() != ViewType.COVER_NOTI_VIEW) {
            if (actionViewModel.getActionInfo().getName().equals("power")) {
                imageView.setImageResource(R.drawable.ic_sidecover_btn_power_off);
            } else if (actionViewModel.getActionInfo().getName().equals("restart")) {
                imageView.setImageResource(R.drawable.ic_sidecover_btn_restart);
            }
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.presentation.view.SideCoverContentItemView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SideCoverContentItemView.this.mViewModel.onPress();
                }
            });
        }
        if (Settings.System.getIntForUser(context.getContentResolver(), "cover_text_direction", 0, -2) != 0) {
            view.setRotation(180.0f);
        }
        textView.setText(actionViewModel.getActionInfo().getLabel());
    }
}
