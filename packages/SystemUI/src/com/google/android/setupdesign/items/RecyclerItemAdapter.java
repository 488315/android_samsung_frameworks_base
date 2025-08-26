package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.view.HeaderRecyclerView;

/* loaded from: classes4.dex */
public class RecyclerItemAdapter extends RecyclerView.Adapter {
    public final boolean applyPartnerHeavyThemeResource;
    public final ItemHierarchy itemHierarchy;
    public RecyclerView recyclerView;
    public final boolean useFullDynamicColor;

    class PatchedLayerDrawable extends LayerDrawable {
        public PatchedLayerDrawable(Drawable[] drawableArr) {
            super(drawableArr);
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public final boolean getPadding(Rect rect) {
            if (super.getPadding(rect)) {
                return (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) ? false : true;
            }
            return false;
        }
    }

    public RecyclerItemAdapter(ItemHierarchy itemHierarchy) {
        this(itemHierarchy, false);
    }

    public final AbstractItem getItem() {
        AbstractItem abstractItem = (AbstractItem) this.itemHierarchy;
        abstractItem.getClass();
        return abstractItem;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.itemHierarchy.getCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        int i2 = getItem().id;
        if (i2 > 0) {
            return i2;
        }
        return -1L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return getItem().getLayoutResource();
    }

    public final boolean isLastItemOfGroup(int i) {
        if (i == this.itemHierarchy.getCount() - 1) {
            return true;
        }
        getItem();
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x011b  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) throws Resources.NotFoundException {
        Drawable drawable;
        float f;
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        AbstractItem item = getItem();
        boolean zIsEnabled = item.isEnabled();
        itemViewHolder.isEnabled = zIsEnabled;
        itemViewHolder.itemView.setClickable(zIsEnabled);
        itemViewHolder.itemView.setEnabled(zIsEnabled);
        itemViewHolder.itemView.setFocusable(zIsEnabled);
        itemViewHolder.item = item;
        if (!itemViewHolder.isRecyclable()) {
            itemViewHolder.setIsRecyclable(true);
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(itemViewHolder.itemView.getContext())) {
            View view = itemViewHolder.itemView;
            if (!"noBackground".equals(view.getTag())) {
                getItem();
                float dimension = PartnerConfigHelper.get(view.getContext()).getDimension(view.getContext(), PartnerConfig.CONFIG_ITEMS_GROUP_CORNER_RADIUS, 0.0f);
                TypedArray typedArrayObtainStyledAttributes = view.getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.sudItemCornerRadius});
                float dimension2 = typedArrayObtainStyledAttributes.getDimension(0, 0.0f);
                typedArrayObtainStyledAttributes.recycle();
                Drawable background = view.getBackground();
                if (background instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) background;
                    if (layerDrawable.getNumberOfLayers() >= 2) {
                        Drawable drawable2 = layerDrawable.getDrawable(1);
                        if (i != 0) {
                            getItem();
                        } else {
                            if (isLastItemOfGroup(i)) {
                                Context context = view.getContext();
                                getItem();
                                TypedArray typedArrayObtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(new int[]{R.attr.sudItemBackgroundSingle});
                                drawable = typedArrayObtainStyledAttributes2.getDrawable(0);
                                typedArrayObtainStyledAttributes2.recycle();
                            }
                            if (drawable instanceof GradientDrawable) {
                                if (i != 0) {
                                    getItem();
                                    f = dimension2;
                                } else {
                                    f = dimension;
                                }
                                if (!isLastItemOfGroup(i)) {
                                    dimension = dimension2;
                                }
                                GradientDrawable gradientDrawable = (GradientDrawable) drawable;
                                gradientDrawable.setCornerRadii(new float[]{f, f, f, f, dimension, dimension, dimension, dimension});
                                view.setBackgroundDrawable(new PatchedLayerDrawable(new Drawable[]{gradientDrawable, drawable2}));
                                view.setClipToOutline(true);
                                view.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                            }
                        }
                        if (i != 0) {
                            getItem();
                            if (isLastItemOfGroup(i)) {
                                Context context2 = view.getContext();
                                getItem();
                                TypedArray typedArrayObtainStyledAttributes3 = context2.getTheme().obtainStyledAttributes(new int[]{R.attr.sudItemBackgroundLast});
                                drawable = typedArrayObtainStyledAttributes3.getDrawable(0);
                                typedArrayObtainStyledAttributes3.recycle();
                            } else {
                                Context context3 = view.getContext();
                                getItem();
                                TypedArray typedArrayObtainStyledAttributes4 = context3.getTheme().obtainStyledAttributes(new int[]{R.attr.sudItemBackground});
                                drawable = typedArrayObtainStyledAttributes4.getDrawable(0);
                                typedArrayObtainStyledAttributes4.recycle();
                            }
                        } else {
                            Context context4 = view.getContext();
                            getItem();
                            TypedArray typedArrayObtainStyledAttributes5 = context4.getTheme().obtainStyledAttributes(new int[]{R.attr.sudItemBackgroundFirst});
                            drawable = typedArrayObtainStyledAttributes5.getDrawable(0);
                            typedArrayObtainStyledAttributes5.recycle();
                        }
                        if (drawable instanceof GradientDrawable) {
                        }
                    }
                }
            }
            View view2 = itemViewHolder.itemView;
            RecyclerView recyclerView = this.recyclerView;
            if (recyclerView instanceof HeaderRecyclerView ? ((HeaderRecyclerView) recyclerView).shouldApplyAdditionalMargin : false) {
                Context context5 = view2.getContext();
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context5);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
                boolean zIsPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context5);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
                boolean zIsPartnerConfigAvailable2 = partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2);
                if (PartnerStyleHelper.shouldApplyPartnerResource(view2) && ((zIsPartnerConfigAvailable || zIsPartnerConfigAvailable2) && (view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                    marginLayoutParams.setMargins(zIsPartnerConfigAvailable ? (int) PartnerConfigHelper.get(context5).getDimension(context5, partnerConfig, 0.0f) : marginLayoutParams.leftMargin, marginLayoutParams.topMargin, zIsPartnerConfigAvailable2 ? (int) PartnerConfigHelper.get(context5).getDimension(context5, partnerConfig2, 0.0f) : marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                }
            } else {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                marginLayoutParams2.setMarginStart(0);
                marginLayoutParams2.setMarginEnd(0);
                view2.setLayoutParams(marginLayoutParams2);
            }
        }
        item.onBindView(itemViewHolder.itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Drawable background;
        View viewM = KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, i, viewGroup, false);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(viewM);
        if (!"noBackground".equals(viewM.getTag())) {
            TypedArray typedArrayObtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes(R$styleable.SudRecyclerItemAdapter);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
            if (drawable == null) {
                drawable = typedArrayObtainStyledAttributes.getDrawable(2);
                background = null;
            } else {
                background = viewM.getBackground();
                if (background == null) {
                    background = (!this.applyPartnerHeavyThemeResource || this.useFullDynamicColor) ? typedArrayObtainStyledAttributes.getDrawable(0) : new ColorDrawable(PartnerConfigHelper.get(viewM.getContext()).getColor(viewM.getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
                }
            }
            if (drawable == null || background == null) {
                Log.e("RecyclerItemAdapter", "Cannot resolve required attributes. selectableItemBackground=" + drawable + " background=" + background);
            } else {
                viewM.setBackgroundDrawable(new PatchedLayerDrawable(new Drawable[]{background, drawable}));
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        viewM.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.setupdesign.items.RecyclerItemAdapter.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AbstractItem abstractItem = itemViewHolder.item;
                RecyclerItemAdapter.this.getClass();
            }
        });
        return itemViewHolder;
    }

    public RecyclerItemAdapter(ItemHierarchy itemHierarchy, boolean z) {
        this(itemHierarchy, z, false);
    }

    public RecyclerItemAdapter(ItemHierarchy itemHierarchy, boolean z, boolean z2) {
        this.recyclerView = null;
        this.applyPartnerHeavyThemeResource = z;
        this.useFullDynamicColor = z2;
        this.itemHierarchy = itemHierarchy;
        ((AbstractItemHierarchy) itemHierarchy).observers.add(this);
    }
}
