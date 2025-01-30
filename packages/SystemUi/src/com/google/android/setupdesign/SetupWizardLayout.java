package com.google.android.setupdesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.template.NavigationBarMixin;
import com.google.android.setupdesign.template.ProgressBarMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.template.ScrollViewScrollHandlingDelegate;
import com.google.android.setupdesign.view.Illustration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SetupWizardLayout extends TemplateLayout {
    public SetupWizardLayout(Context context) {
        super(context, 0, 0);
        init(null, R.attr.sudLayoutTheme);
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public final void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        registerMixin(SystemNavBarMixin.class, new SystemNavBarMixin(this, null));
        registerMixin(HeaderMixin.class, new HeaderMixin(this, attributeSet, i));
        registerMixin(DescriptionMixin.class, new DescriptionMixin(this, attributeSet, i));
        registerMixin(ProgressBarMixin.class, new ProgressBarMixin(this));
        registerMixin(NavigationBarMixin.class, new NavigationBarMixin(this));
        RequireScrollMixin requireScrollMixin = new RequireScrollMixin(this);
        registerMixin(RequireScrollMixin.class, requireScrollMixin);
        View findManagedViewById = findManagedViewById(R.id.sud_bottom_scroll_view);
        ScrollView scrollView = findManagedViewById instanceof ScrollView ? (ScrollView) findManagedViewById : null;
        if (scrollView != null) {
            new ScrollViewScrollHandlingDelegate(requireScrollMixin, scrollView);
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudSetupWizardLayout, i, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            View findManagedViewById2 = findManagedViewById(R.id.sud_layout_decor);
            if (findManagedViewById2 != null) {
                findManagedViewById2.setBackgroundDrawable(drawable);
            }
        } else {
            Drawable drawable2 = obtainStyledAttributes.getDrawable(1);
            if (drawable2 != null) {
                if (drawable2 instanceof BitmapDrawable) {
                    Shader.TileMode tileMode = Shader.TileMode.REPEAT;
                    ((BitmapDrawable) drawable2).setTileModeXY(tileMode, tileMode);
                }
                View findManagedViewById3 = findManagedViewById(R.id.sud_layout_decor);
                if (findManagedViewById3 != null) {
                    findManagedViewById3.setBackgroundDrawable(drawable2);
                }
            }
        }
        Drawable drawable3 = obtainStyledAttributes.getDrawable(3);
        if (drawable3 != null) {
            View findManagedViewById4 = findManagedViewById(R.id.sud_layout_decor);
            if (findManagedViewById4 instanceof Illustration) {
                Illustration illustration = (Illustration) findManagedViewById4;
                if (drawable3 != illustration.illustration) {
                    illustration.illustration = drawable3;
                    illustration.invalidate();
                    illustration.requestLayout();
                }
            }
        } else {
            Drawable drawable4 = obtainStyledAttributes.getDrawable(6);
            Drawable drawable5 = obtainStyledAttributes.getDrawable(5);
            if (drawable4 != null && drawable5 != null) {
                View findManagedViewById5 = findManagedViewById(R.id.sud_layout_decor);
                if (findManagedViewById5 instanceof Illustration) {
                    Illustration illustration2 = (Illustration) findManagedViewById5;
                    if (getContext().getResources().getBoolean(R.bool.sudUseTabletLayout)) {
                        if (drawable5 instanceof BitmapDrawable) {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable5;
                            bitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
                            bitmapDrawable.setGravity(48);
                        }
                        if (drawable4 instanceof BitmapDrawable) {
                            ((BitmapDrawable) drawable4).setGravity(51);
                        }
                        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable5, drawable4});
                        layerDrawable.setAutoMirrored(true);
                        drawable4 = layerDrawable;
                    } else {
                        drawable4.setAutoMirrored(true);
                    }
                    if (drawable4 != illustration2.illustration) {
                        illustration2.illustration = drawable4;
                        illustration2.invalidate();
                        illustration2.requestLayout();
                    }
                }
            }
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, -1);
        if (dimensionPixelSize == -1) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sud_decor_padding_top);
        }
        View findManagedViewById6 = findManagedViewById(R.id.sud_layout_decor);
        if (findManagedViewById6 != null) {
            findManagedViewById6.setPadding(findManagedViewById6.getPaddingLeft(), dimensionPixelSize, findManagedViewById6.getPaddingRight(), findManagedViewById6.getPaddingBottom());
        }
        float f = obtainStyledAttributes.getFloat(4, -1.0f);
        if (f == -1.0f) {
            TypedValue typedValue = new TypedValue();
            getResources().getValue(R.dimen.sud_illustration_aspect_ratio, typedValue, true);
            f = typedValue.getFloat();
        }
        View findManagedViewById7 = findManagedViewById(R.id.sud_layout_decor);
        if (findManagedViewById7 instanceof Illustration) {
            Illustration illustration3 = (Illustration) findManagedViewById7;
            illustration3.aspectRatio = f;
            illustration3.invalidate();
            illustration3.requestLayout();
        }
        obtainStyledAttributes.recycle();
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = R.layout.sud_template;
        }
        return inflateTemplate(layoutInflater, 2132018022, i);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            ((ProgressBarMixin) getMixin(ProgressBarMixin.class)).setShown(savedState.isProgressBarShown);
            return;
        }
        Log.w("SetupWizardLayout", "Ignoring restore instance state " + parcelable);
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        View findManagedViewById = progressBarMixin.templateLayout.findManagedViewById(progressBarMixin.useBottomProgressBar ? R.id.sud_glif_progress_bar : R.id.sud_layout_progress);
        savedState.isProgressBarShown = findManagedViewById != null && findManagedViewById.getVisibility() == 0;
        return savedState;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.google.android.setupdesign.SetupWizardLayout.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean isProgressBarShown;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
            this.isProgressBarShown = false;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isProgressBarShown ? 1 : 0);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isProgressBarShown = false;
            this.isProgressBarShown = parcel.readInt() != 0;
        }
    }

    public SetupWizardLayout(Context context, int i) {
        this(context, i, 0);
    }

    public SetupWizardLayout(Context context, int i, int i2) {
        super(context, i, i2);
        init(null, R.attr.sudLayoutTheme);
    }

    public SetupWizardLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, R.attr.sudLayoutTheme);
    }

    public SetupWizardLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }
}
