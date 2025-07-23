package com.google.android.setupdesign.items;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RecyclerItemAdapter extends RecyclerView.Adapter {
    public final boolean applyPartnerHeavyThemeResource;
    public final ItemHierarchy itemHierarchy;
    public RecyclerView recyclerView;
    public final boolean useFullDynamicColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x011b  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r12, int r13) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.items.RecyclerItemAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Drawable background;
        View m = KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, i, viewGroup, false);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(m);
        if (!"noBackground".equals(m.getTag())) {
            TypedArray obtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes(R$styleable.SudRecyclerItemAdapter);
            Drawable drawable = obtainStyledAttributes.getDrawable(1);
            if (drawable == null) {
                drawable = obtainStyledAttributes.getDrawable(2);
                background = null;
            } else {
                background = m.getBackground();
                if (background == null) {
                    background = (!this.applyPartnerHeavyThemeResource || this.useFullDynamicColor) ? obtainStyledAttributes.getDrawable(0) : new ColorDrawable(PartnerConfigHelper.get(m.getContext()).getColor(m.getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
                }
            }
            if (drawable == null || background == null) {
                Log.e("RecyclerItemAdapter", "Cannot resolve required attributes. selectableItemBackground=" + drawable + " background=" + background);
            } else {
                m.setBackgroundDrawable(new PatchedLayerDrawable(new Drawable[]{background, drawable}));
            }
            obtainStyledAttributes.recycle();
        }
        m.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.setupdesign.items.RecyclerItemAdapter.1
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
