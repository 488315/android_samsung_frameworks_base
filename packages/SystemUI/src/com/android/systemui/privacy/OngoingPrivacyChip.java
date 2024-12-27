package com.android.systemui.privacy;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.res.R$styleable;
import com.android.systemui.statusbar.events.BackgroundAnimatableView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OngoingPrivacyChip extends FrameLayout implements BackgroundAnimatableView {
    public final Configuration configuration;
    public int firstIconStartMargin;
    public int iconColor;
    public int iconMargin;
    public final LinearLayout iconsContainer;
    public List privacyList;

    public OngoingPrivacyChip(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration != null) {
            int diff = configuration.diff(this.configuration);
            this.configuration.setTo(configuration);
            if ((1073745920 & diff) != 0) {
                updateResources$6();
            }
        }
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final void setBoundsForAnimation(int i, int i2, int i3, int i4) {
        this.iconsContainer.setLeftTopRightBottom(i - getLeft(), i2 - getTop(), i3 - getLeft(), i4 - getTop());
    }

    public final void setPrivacyList(List list) {
        this.privacyList = list;
        PrivacyChipBuilder privacyChipBuilder = new PrivacyChipBuilder(getContext(), this.privacyList);
        if (this.privacyList.isEmpty()) {
            this.iconsContainer.removeAllViews();
        } else {
            setContentDescription(getContext().getString(R.string.ongoing_privacy_chip_content_multiple_apps, privacyChipBuilder.joinTypes()));
            LinearLayout linearLayout = this.iconsContainer;
            linearLayout.removeAllViews();
            List list2 = privacyChipBuilder.types;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((PrivacyType) it.next()).getIcon(privacyChipBuilder.context));
            }
            int i = 0;
            boolean z = arrayList.size() > 1;
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                Drawable drawable = (Drawable) next;
                drawable.mutate();
                drawable.setTint(this.iconColor);
                ImageView imageView = new ImageView(getContext());
                imageView.setImageDrawable(drawable);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                linearLayout.addView(imageView);
                if (i == 0 && z) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                    marginLayoutParams.setMarginStart(this.firstIconStartMargin);
                    imageView.setLayoutParams(marginLayoutParams);
                }
                if (i != 0) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                    marginLayoutParams2.setMarginStart(this.iconMargin);
                    imageView.setLayoutParams(marginLayoutParams2);
                }
                i = i2;
            }
        }
        requestLayout();
    }

    public final void updateResources$6() {
        this.iconColor = getContext().getColor(R.color.privacy_chip_icon_color);
        this.iconMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_icon_margin);
        this.firstIconStartMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_appops_chip_first_icon_start_margin);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.privacyList = EmptyList.INSTANCE;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.OngoingPrivacyChip, i, i2);
        try {
            int resourceId = obtainStyledAttributes.getResourceId(0, R.layout.ongoing_privacy_chip);
            obtainStyledAttributes.recycle();
            FrameLayout.inflate(context, resourceId, this);
            setId(R.id.privacy_chip);
            setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388629));
            setClipChildren(true);
            setClipToPadding(true);
            this.iconsContainer = (LinearLayout) requireViewById(R.id.icons_container);
            this.configuration = new Configuration(context.getResources().getConfiguration());
            updateResources$6();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
