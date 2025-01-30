package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class Item extends AbstractItem {
    public final CharSequence contentDescription;
    public final boolean enabled;
    public final Drawable icon;
    public int iconGravity;
    public final int iconTint;
    public final int layoutRes;
    public final CharSequence summary;
    public final CharSequence title;
    public final boolean visible;

    public Item() {
        this.enabled = true;
        this.visible = true;
        this.iconTint = 0;
        this.iconGravity = 16;
        this.layoutRes = getDefaultLayoutResource();
    }

    @Override // com.google.android.setupdesign.items.AbstractItem, com.google.android.setupdesign.items.ItemHierarchy
    public final int getCount() {
        return this.visible ? 1 : 0;
    }

    public int getDefaultLayoutResource() {
        return R.layout.sud_items_default;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public final int getLayoutResource() {
        return this.layoutRes;
    }

    public CharSequence getSummary() {
        return this.summary;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem
    public void onBindView(View view) {
        ((TextView) view.findViewById(R.id.sud_items_title)).setText(this.title);
        TextView textView = (TextView) view.findViewById(R.id.sud_items_summary);
        CharSequence summary = getSummary();
        if (summary != null && summary.length() > 0) {
            textView.setText(summary);
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        view.setContentDescription(this.contentDescription);
        View findViewById = view.findViewById(R.id.sud_items_icon_container);
        Drawable drawable = this.icon;
        if (drawable != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.sud_items_icon);
            imageView.setImageDrawable(null);
            imageView.setImageState(drawable.getState(), false);
            imageView.setImageLevel(drawable.getLevel());
            imageView.setImageDrawable(drawable);
            int i = this.iconTint;
            if (i != 0) {
                imageView.setColorFilter(i);
            } else {
                imageView.clearColorFilter();
            }
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = this.iconGravity;
            }
            findViewById.setVisibility(0);
        } else {
            findViewById.setVisibility(8);
        }
        view.setId(this.f465id);
        if (!(this instanceof ExpandableSwitchItem) && view.getId() != R.id.sud_layout_header) {
            LayoutStyler.applyPartnerCustomizationLayoutPaddingStyle(view);
        }
        if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(view)) {
            TextView textView2 = (TextView) view.findViewById(R.id.sud_items_title);
            if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(textView2)) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(textView2, new TextViewPartnerStyler.TextPartnerConfigs(null, null, PartnerConfig.CONFIG_ITEMS_TITLE_TEXT_SIZE, PartnerConfig.CONFIG_ITEMS_TITLE_FONT_FAMILY, null, null, null, PartnerStyleHelper.getLayoutGravity(textView2.getContext())));
            }
            TextView textView3 = (TextView) view.findViewById(R.id.sud_items_summary);
            if (textView3.getVisibility() == 8 && (view instanceof LinearLayout)) {
                ((LinearLayout) view).setGravity(16);
            }
            if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(textView3)) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(textView3, new TextViewPartnerStyler.TextPartnerConfigs(null, null, PartnerConfig.CONFIG_ITEMS_SUMMARY_TEXT_SIZE, PartnerConfig.CONFIG_ITEMS_SUMMARY_FONT_FAMILY, null, PartnerConfig.CONFIG_ITEMS_SUMMARY_MARGIN_TOP, null, PartnerStyleHelper.getLayoutGravity(textView3.getContext())));
            }
            Context context = view.getContext();
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_ITEMS_PADDING_TOP;
            float dimension = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig) ? PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f) : view.getPaddingTop();
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_ITEMS_PADDING_BOTTOM;
            float dimension2 = partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2) ? PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f) : view.getPaddingBottom();
            if (dimension != view.getPaddingTop() || dimension2 != view.getPaddingBottom()) {
                view.setPadding(view.getPaddingStart(), (int) dimension, view.getPaddingEnd(), (int) dimension2);
            }
            PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_ITEMS_MIN_HEIGHT;
            if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                view.setMinimumHeight((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig3, 0.0f));
            }
        }
    }

    public Item(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.enabled = true;
        this.visible = true;
        this.iconTint = 0;
        this.iconGravity = 16;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudItem);
        this.enabled = obtainStyledAttributes.getBoolean(1, true);
        this.icon = obtainStyledAttributes.getDrawable(0);
        this.title = obtainStyledAttributes.getText(4);
        this.summary = obtainStyledAttributes.getText(5);
        this.contentDescription = obtainStyledAttributes.getText(6);
        this.layoutRes = obtainStyledAttributes.getResourceId(2, getDefaultLayoutResource());
        this.visible = obtainStyledAttributes.getBoolean(3, true);
        this.iconTint = obtainStyledAttributes.getColor(8, 0);
        this.iconGravity = obtainStyledAttributes.getInt(7, 16);
        obtainStyledAttributes.recycle();
    }
}
