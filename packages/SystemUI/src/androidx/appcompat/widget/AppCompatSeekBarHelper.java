package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.R$styleable;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
    public boolean mHasTickMarkTint;
    public boolean mHasTickMarkTintMode;
    public Drawable mTickMark;
    public ColorStateList mTickMarkTintList;
    public PorterDuff.Mode mTickMarkTintMode;
    public final SeekBar mView;

    public AppCompatSeekBarHelper(SeekBar seekBar) {
        super(seekBar);
        this.mTickMarkTintList = null;
        this.mTickMarkTintMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkTintMode = false;
        this.mView = seekBar;
    }

    public final void applyTickMarkTint() {
        Drawable drawable = this.mTickMark;
        if (drawable != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkTintMode) {
                Drawable mutate = drawable.mutate();
                this.mTickMark = mutate;
                if (this.mHasTickMarkTint) {
                    mutate.setTintList(this.mTickMarkTintList);
                }
                if (this.mHasTickMarkTintMode) {
                    this.mTickMark.setTintMode(this.mTickMarkTintMode);
                }
                if (this.mTickMark.isStateful()) {
                    this.mTickMark.setState(this.mView.getDrawableState());
                }
            }
        }
    }

    public final void drawTickMarks(Canvas canvas) {
        if (this.mTickMark != null) {
            int max = this.mView.getMax();
            if (max > 1) {
                int intrinsicWidth = this.mTickMark.getIntrinsicWidth();
                int intrinsicHeight = this.mTickMark.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.mTickMark.setBounds(-i, -i2, i, i2);
                float width = ((this.mView.getWidth() - this.mView.getPaddingLeft()) - this.mView.getPaddingRight()) / max;
                int save = canvas.save();
                canvas.translate(this.mView.getPaddingLeft(), this.mView.getHeight() / 2);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    @Override // androidx.appcompat.widget.AppCompatProgressBarHelper
    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        super.loadFromAttributes(attributeSet, i);
        Context context = this.mView.getContext();
        int[] iArr = R$styleable.AppCompatSeekBar;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        SeekBar seekBar = this.mView;
        Context context2 = seekBar.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(seekBar, context2, iArr, attributeSet, typedArray, i, 0);
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            this.mView.setThumb(drawableIfKnown);
        }
        Drawable drawable = obtainStyledAttributes.getDrawable(10);
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mTickMark = drawable;
        if (drawable != null) {
            drawable.setCallback(this.mView);
            drawable.setLayoutDirection(this.mView.getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(this.mView.getDrawableState());
            }
            applyTickMarkTint();
        }
        this.mView.invalidate();
        if (obtainStyledAttributes.mWrapped.hasValue(12)) {
            this.mTickMarkTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.mWrapped.getInt(12, -1), this.mTickMarkTintMode);
            this.mHasTickMarkTintMode = true;
        }
        if (obtainStyledAttributes.mWrapped.hasValue(11)) {
            this.mTickMarkTintList = obtainStyledAttributes.getColorStateList(11);
            this.mHasTickMarkTint = true;
        }
        obtainStyledAttributes.recycle();
        applyTickMarkTint();
    }
}
