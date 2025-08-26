package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.settingslib.widget.theme.R$styleable;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class CollapsableTextView extends ConstraintLayout {
    public static final int isCollapsableAttr;
    public static final int minLinesAttr;
    public final MaterialButton collapseButton;
    public final CollapseButtonResources collapseButtonResources;
    public boolean isCollapsable;
    public boolean isCollapsed;
    public final LinkableTextView learnMoreTextView;
    public int minLines;
    public final TextView titleTextView;

    public final class CollapseButtonResources {
        public final Drawable collapseIcon;
        public final String collapseText;
        public final Drawable expandIcon;
        public final String expandText;

        public CollapseButtonResources(Drawable drawable, Drawable drawable2, String str, String str2) {
            this.collapseIcon = drawable;
            this.expandIcon = drawable2;
            this.collapseText = str;
            this.expandText = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CollapseButtonResources)) {
                return false;
            }
            CollapseButtonResources collapseButtonResources = (CollapseButtonResources) obj;
            return Intrinsics.areEqual(this.collapseIcon, collapseButtonResources.collapseIcon) && Intrinsics.areEqual(this.expandIcon, collapseButtonResources.expandIcon) && Intrinsics.areEqual(this.collapseText, collapseButtonResources.collapseText) && Intrinsics.areEqual(this.expandText, collapseButtonResources.expandText);
        }

        public final int hashCode() {
            return this.expandText.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.expandIcon.hashCode() + (this.collapseIcon.hashCode() * 31)) * 31, 31, this.collapseText);
        }

        public final String toString() {
            Drawable drawable = this.collapseIcon;
            Drawable drawable2 = this.expandIcon;
            StringBuilder sb = new StringBuilder("CollapseButtonResources(collapseIcon=");
            sb.append(drawable);
            sb.append(", expandIcon=");
            sb.append(drawable2);
            sb.append(", collapseText=");
            sb.append(this.collapseText);
            sb.append(", expandText=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.expandText, ")");
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        minLinesAttr = 1;
        isCollapsableAttr = 2;
    }

    public CollapsableTextView(Context context) {
        this(context, null, 0, 6, null);
    }

    public static void centerHorizontally(View view) {
        if (view instanceof MaterialButton) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((MaterialButton) view).getLayoutParams();
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
        } else {
            if (view instanceof TextView) {
                ((TextView) view).setGravity(17);
                return;
            }
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            layoutParams2.startToStart = 0;
            layoutParams2.endToEnd = 0;
        }
    }

    public final void updateView$1() {
        if (this.isCollapsed) {
            MaterialButton materialButton = this.collapseButton;
            materialButton.setText(this.collapseButtonResources.expandText);
            materialButton.setIcon(this.collapseButtonResources.expandIcon);
            this.titleTextView.setMaxLines(this.minLines);
            this.titleTextView.setEllipsize(null);
            this.titleTextView.setScrollBarSize(0);
        } else {
            MaterialButton materialButton2 = this.collapseButton;
            materialButton2.setText(this.collapseButtonResources.collapseText);
            materialButton2.setIcon(this.collapseButtonResources.collapseIcon);
            this.titleTextView.setMaxLines(10);
            this.titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        }
        this.collapseButton.setVisibility(this.isCollapsable ? 0 : 8);
        this.learnMoreTextView.setVisibility(8);
    }

    public CollapsableTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ CollapsableTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public CollapsableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isCollapsable = true;
        this.minLines = 2;
        LayoutInflater.from(context).inflate(R.layout.settingslib_expressive_collapsable_textview, this);
        TextView textView = (TextView) findViewById(android.R.id.title);
        this.titleTextView = textView;
        MaterialButton materialButton = (MaterialButton) findViewById(R.id.collapse_button);
        this.collapseButton = materialButton;
        LinkableTextView linkableTextView = (LinkableTextView) findViewById(R.id.settingslib_expressive_learn_more);
        this.learnMoreTextView = linkableTextView;
        Drawable drawable = context.getDrawable(R.drawable.settingslib_expressive_icon_collapse);
        drawable.getClass();
        Drawable drawable2 = context.getDrawable(R.drawable.settingslib_expressive_icon_expand);
        drawable2.getClass();
        this.collapseButtonResources = new CollapseButtonResources(drawable, drawable2, context.getString(R.string.settingslib_expressive_text_collapse), context.getString(R.string.settingslib_expressive_text_expand));
        materialButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.CollapsableTextView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollapsableTextView collapsableTextView = CollapsableTextView.this;
                collapsableTextView.isCollapsed = !collapsableTextView.isCollapsed;
                collapsableTextView.updateView$1();
            }
        });
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CollapsableTextView, i, 0);
        int i2 = typedArrayObtainStyledAttributes.getInt(0, 8388611);
        if (i2 == 1 || i2 == 16 || i2 == 17) {
            centerHorizontally(textView);
            centerHorizontally(materialButton);
            centerHorizontally(linkableTextView);
        }
        this.isCollapsable = typedArrayObtainStyledAttributes.getBoolean(isCollapsableAttr, true);
        this.minLines = typedArrayObtainStyledAttributes.getInt(minLinesAttr, 2);
        typedArrayObtainStyledAttributes.recycle();
    }
}
