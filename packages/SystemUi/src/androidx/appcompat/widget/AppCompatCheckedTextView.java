package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppCompatCheckedTextView extends CheckedTextView {
    public AppCompatEmojiTextHelper mAppCompatEmojiTextHelper;
    public final AppCompatBackgroundHelper mBackgroundTintHelper;
    public final AppCompatCheckedTextViewHelper mCheckedHelper;
    public final AppCompatTextHelper mTextHelper;

    public AppCompatCheckedTextView(Context context) {
        this(context, null);
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.applyCompoundDrawablesTints();
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.applySupportBackgroundTint();
        }
        AppCompatCheckedTextViewHelper appCompatCheckedTextViewHelper = this.mCheckedHelper;
        if (appCompatCheckedTextViewHelper != null) {
            appCompatCheckedTextViewHelper.applyCheckMarkTint();
        }
    }

    @Override // android.widget.TextView
    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return TextViewCompat.unwrapCustomSelectionActionModeCallback(super.getCustomSelectionActionModeCallback());
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        AppCompatHintHelper.onCreateInputConnection(this, editorInfo, onCreateInputConnection);
        return onCreateInputConnection;
    }

    @Override // android.widget.TextView
    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        if (this.mAppCompatEmojiTextHelper == null) {
            this.mAppCompatEmojiTextHelper = new AppCompatEmojiTextHelper(this);
        }
        this.mAppCompatEmojiTextHelper.setAllCaps(z);
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundDrawable();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundResource(i);
        }
    }

    @Override // android.widget.CheckedTextView
    public final void setCheckMarkDrawable(Drawable drawable) {
        super.setCheckMarkDrawable(drawable);
        AppCompatCheckedTextViewHelper appCompatCheckedTextViewHelper = this.mCheckedHelper;
        if (appCompatCheckedTextViewHelper != null) {
            if (appCompatCheckedTextViewHelper.mSkipNextApply) {
                appCompatCheckedTextViewHelper.mSkipNextApply = false;
            } else {
                appCompatCheckedTextViewHelper.mSkipNextApply = true;
                appCompatCheckedTextViewHelper.applyCheckMarkTint();
            }
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.applyCompoundDrawablesTints();
        }
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.applyCompoundDrawablesTints();
        }
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.onSetTextAppearance(i, context);
        }
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkedTextViewStyle);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0083 A[Catch: all -> 0x00b2, TryCatch #0 {all -> 0x00b2, blocks: (B:3:0x0048, B:5:0x004f, B:8:0x0055, B:11:0x0065, B:13:0x006b, B:15:0x0071, B:16:0x007c, B:18:0x0083, B:19:0x008a, B:21:0x0091), top: B:2:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0091 A[Catch: all -> 0x00b2, TRY_LEAVE, TryCatch #0 {all -> 0x00b2, blocks: (B:3:0x0048, B:5:0x004f, B:8:0x0055, B:11:0x0065, B:13:0x006b, B:15:0x0071, B:16:0x007c, B:18:0x0083, B:19:0x008a, B:21:0x0091), top: B:2:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        boolean z;
        int resourceId;
        int resourceId2;
        TintContextWrapper.wrap(context);
        ThemeUtils.checkAppCompatTheme(getContext(), this);
        AppCompatTextHelper appCompatTextHelper = new AppCompatTextHelper(this);
        this.mTextHelper = appCompatTextHelper;
        appCompatTextHelper.loadFromAttributes(attributeSet, i);
        appCompatTextHelper.applyCompoundDrawablesTints();
        AppCompatBackgroundHelper appCompatBackgroundHelper = new AppCompatBackgroundHelper(this);
        this.mBackgroundTintHelper = appCompatBackgroundHelper;
        appCompatBackgroundHelper.loadFromAttributes(attributeSet, i);
        AppCompatCheckedTextViewHelper appCompatCheckedTextViewHelper = new AppCompatCheckedTextViewHelper(this);
        this.mCheckedHelper = appCompatCheckedTextViewHelper;
        CheckedTextView checkedTextView = appCompatCheckedTextViewHelper.mView;
        Context context2 = checkedTextView.getContext();
        int[] iArr = R$styleable.CheckedTextView;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attributeSet, iArr, i, 0);
        Context context3 = checkedTextView.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(checkedTextView, context3, iArr, attributeSet, typedArray, i, 0);
        try {
            if (obtainStyledAttributes.hasValue(4) && (resourceId2 = obtainStyledAttributes.getResourceId(4, 0)) != 0) {
                try {
                    checkedTextView.setCheckMarkDrawable(AppCompatResources.getDrawable(resourceId2, checkedTextView.getContext()));
                    z = true;
                } catch (Resources.NotFoundException unused) {
                }
                if (!z && obtainStyledAttributes.hasValue(1) && (resourceId = obtainStyledAttributes.getResourceId(1, 0)) != 0) {
                    checkedTextView.setCheckMarkDrawable(AppCompatResources.getDrawable(resourceId, checkedTextView.getContext()));
                }
                if (obtainStyledAttributes.hasValue(6)) {
                    checkedTextView.setCheckMarkTintList(obtainStyledAttributes.getColorStateList(6));
                }
                if (obtainStyledAttributes.hasValue(7)) {
                    checkedTextView.setCheckMarkTintMode(DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(7, -1), null));
                }
                obtainStyledAttributes.recycle();
                if (this.mAppCompatEmojiTextHelper == null) {
                    this.mAppCompatEmojiTextHelper = new AppCompatEmojiTextHelper(this);
                }
                this.mAppCompatEmojiTextHelper.loadFromAttributes(attributeSet, i);
            }
            z = false;
            if (!z) {
                checkedTextView.setCheckMarkDrawable(AppCompatResources.getDrawable(resourceId, checkedTextView.getContext()));
            }
            if (obtainStyledAttributes.hasValue(6)) {
            }
            if (obtainStyledAttributes.hasValue(7)) {
            }
            obtainStyledAttributes.recycle();
            if (this.mAppCompatEmojiTextHelper == null) {
            }
            this.mAppCompatEmojiTextHelper.loadFromAttributes(attributeSet, i);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.CheckedTextView
    public final void setCheckMarkDrawable(int i) {
        setCheckMarkDrawable(AppCompatResources.getDrawable(i, getContext()));
    }
}
