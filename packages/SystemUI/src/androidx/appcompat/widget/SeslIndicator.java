package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.util.theme.SeslThemeResourceHelper;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$OpenThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ThemeResourceColor;
import com.android.systemui.R;
import com.google.android.material.appbar.model.view.ViewPagerAppBarView$inflate$1$1;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SeslIndicator extends LinearLayout {
    public Drawable defaultCircle;
    public final List indicator;
    public ViewPagerAppBarView$inflate$1$1 itemClickListener;
    public Drawable selectCircle;
    public int selectedPosition;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PageIndicatorMarker extends FrameLayout {
        public Drawable defaultCircle;
        public final ImageView imageView;
        public boolean isActive;
        public Drawable selectCircle;

        public PageIndicatorMarker(Context context) {
            this(context, null, 2, 0 == true ? 1 : 0);
        }

        public final void setActive(boolean z) {
            this.imageView.setImageDrawable(z ? this.selectCircle : this.defaultCircle);
            setSelected(z);
            this.isActive = z;
        }

        public /* synthetic */ PageIndicatorMarker(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i & 2) != 0 ? null : attributeSet);
        }

        public PageIndicatorMarker(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(this.selectCircle);
            addView(imageView);
            this.imageView = imageView;
        }
    }

    public SeslIndicator(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public final void invalidateIndicator() {
        int size = this.indicator.size();
        int i = 0;
        while (i < size) {
            ((PageIndicatorMarker) ((ArrayList) this.indicator).get(i)).setActive(i == this.selectedPosition);
            i++;
        }
    }

    public final void removeIndicator(int i) {
        if (i < 0 || i >= ((ArrayList) this.indicator).size()) {
            return;
        }
        removeView((PageIndicatorMarker) ((ArrayList) this.indicator).remove(i));
        if (this.selectedPosition >= ((ArrayList) this.indicator).size()) {
            setSelectedPosition(this.selectedPosition - 1);
        } else {
            invalidateIndicator();
        }
    }

    public final void setDefaultCircle(Drawable drawable) {
        ArrayList arrayList = (ArrayList) this.indicator;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            PageIndicatorMarker pageIndicatorMarker = (PageIndicatorMarker) obj;
            pageIndicatorMarker.defaultCircle = drawable;
            pageIndicatorMarker.setActive(pageIndicatorMarker.isActive);
        }
        this.defaultCircle = drawable;
    }

    public final void setSelectCircle(Drawable drawable) {
        ArrayList arrayList = (ArrayList) this.indicator;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            PageIndicatorMarker pageIndicatorMarker = (PageIndicatorMarker) obj;
            pageIndicatorMarker.selectCircle = drawable;
            pageIndicatorMarker.setActive(pageIndicatorMarker.isActive);
        }
        this.selectCircle = drawable;
    }

    public final void setSelectedPosition(int i) {
        if (i < 0) {
            i = 0;
        } else if (i >= ((ArrayList) this.indicator).size()) {
            i = ((ArrayList) this.indicator).size() - 1;
        }
        this.selectedPosition = i;
        invalidateIndicator();
    }

    public /* synthetic */ SeslIndicator(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public SeslIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Drawable drawable;
        Drawable mutate;
        this.indicator = new ArrayList();
        Drawable drawable2 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
        Drawable drawable3 = null;
        if (drawable2 == null || (drawable = drawable2.mutate()) == null) {
            drawable = null;
        } else {
            SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
            SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_off, R.color.sesl_appbar_viewpager_indicator_off_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_off_for_theme, R.color.sesl_appbar_viewpager_indicator_off_dark_for_theme));
            companion.getClass();
            drawable.setTint(context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context)));
        }
        this.defaultCircle = drawable;
        Drawable drawable4 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
        if (drawable4 != null && (mutate = drawable4.mutate()) != null) {
            SeslThemeResourceHelper.Companion companion2 = SeslThemeResourceHelper.Companion;
            SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor2 = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_on), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_on_for_theme));
            companion2.getClass();
            mutate.setTint(context.getColor(seslThemeResourceColor$OpenThemeResourceColor2.getColor(context)));
            drawable3 = mutate;
        }
        this.selectCircle = drawable3;
        this.selectedPosition = -1;
    }
}
