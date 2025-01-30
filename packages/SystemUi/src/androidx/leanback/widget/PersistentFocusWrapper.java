package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class PersistentFocusWrapper extends FrameLayout {
    public final boolean mPersistFocusVertical;
    public int mSelectedPosition;

    public PersistentFocusWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSelectedPosition = -1;
        this.mPersistFocusVertical = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i, int i2) {
        if (!hasFocus()) {
            boolean z = false;
            ViewGroup viewGroup = (ViewGroup) getChildAt(0);
            if ((viewGroup == null ? 0 : viewGroup.getChildCount()) != 0) {
                boolean z2 = this.mPersistFocusVertical;
                if ((z2 && (i == 33 || i == 130)) || (!z2 && (i == 17 || i == 66))) {
                    z = true;
                }
                if (z) {
                    arrayList.add(this);
                    return;
                }
            }
        }
        super.addFocusables(arrayList, i, i2);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.mSelectedPosition = savedState.mSelectedPosition;
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mSelectedPosition = this.mSelectedPosition;
        return savedState;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        while (view2 != null && view2.getParent() != view) {
            view2 = (View) view2.getParent();
        }
        this.mSelectedPosition = view2 == null ? -1 : ((ViewGroup) view).indexOfChild(view2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i, Rect rect) {
        int i2;
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        if (viewGroup != null && (i2 = this.mSelectedPosition) >= 0) {
            ViewGroup viewGroup2 = (ViewGroup) getChildAt(0);
            if (i2 < (viewGroup2 != null ? viewGroup2.getChildCount() : 0) && viewGroup.getChildAt(this.mSelectedPosition).requestFocus(i, rect)) {
                return true;
            }
        }
        return super.requestFocus(i, rect);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.leanback.widget.PersistentFocusWrapper.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int mSelectedPosition;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mSelectedPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSelectedPosition);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public PersistentFocusWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectedPosition = -1;
        this.mPersistFocusVertical = true;
    }
}
