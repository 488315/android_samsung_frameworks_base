package androidx.appcompat.widget;

import android.graphics.drawable.Drawable;
import android.widget.CheckedTextView;

/* loaded from: classes.dex */
public class AppCompatCheckedTextViewHelper {
    public final boolean mHasCheckMarkTint = false;
    public final boolean mHasCheckMarkTintMode = false;
    public boolean mSkipNextApply;
    public final CheckedTextView mView;

    public AppCompatCheckedTextViewHelper(CheckedTextView checkedTextView) {
        this.mView = checkedTextView;
    }

    public final void applyCheckMarkTint() {
        Drawable checkMarkDrawable = this.mView.getCheckMarkDrawable();
        if (checkMarkDrawable != null) {
            if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
                Drawable drawableMutate = checkMarkDrawable.mutate();
                if (this.mHasCheckMarkTint) {
                    drawableMutate.setTintList(null);
                }
                if (this.mHasCheckMarkTintMode) {
                    drawableMutate.setTintMode(null);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(this.mView.getDrawableState());
                }
                this.mView.setCheckMarkDrawable(drawableMutate);
            }
        }
    }
}
