package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.TextViewCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        AppCompatHintHelper.onCreateInputConnection(onCreateInputConnection, editorInfo, this);
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
    /* JADX WARN: Removed duplicated region for block: B:11:0x0091 A[Catch: all -> 0x0067, TryCatch #1 {all -> 0x0067, blocks: (B:3:0x0048, B:5:0x0051, B:8:0x0059, B:9:0x0088, B:11:0x0091, B:12:0x009a, B:14:0x00a3, B:24:0x006a, B:26:0x0073, B:28:0x007b), top: B:2:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00a3 A[Catch: all -> 0x0067, TRY_LEAVE, TryCatch #1 {all -> 0x0067, blocks: (B:3:0x0048, B:5:0x0051, B:8:0x0059, B:9:0x0088, B:11:0x0091, B:12:0x009a, B:14:0x00a3, B:24:0x006a, B:26:0x0073, B:28:0x007b), top: B:2:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AppCompatCheckedTextView(android.content.Context r11, android.util.AttributeSet r12, int r13) {
        /*
            r10 = this;
            androidx.appcompat.widget.TintContextWrapper.wrap(r11)
            r10.<init>(r11, r12, r13)
            android.content.Context r11 = r10.getContext()
            androidx.appcompat.widget.ThemeUtils.checkAppCompatTheme(r11, r10)
            androidx.appcompat.widget.AppCompatTextHelper r11 = new androidx.appcompat.widget.AppCompatTextHelper
            r11.<init>(r10)
            r10.mTextHelper = r11
            r11.loadFromAttributes(r12, r13)
            r11.applyCompoundDrawablesTints()
            androidx.appcompat.widget.AppCompatBackgroundHelper r11 = new androidx.appcompat.widget.AppCompatBackgroundHelper
            r11.<init>(r10)
            r10.mBackgroundTintHelper = r11
            r11.loadFromAttributes(r12, r13)
            androidx.appcompat.widget.AppCompatCheckedTextViewHelper r11 = new androidx.appcompat.widget.AppCompatCheckedTextViewHelper
            r11.<init>(r10)
            r10.mCheckedHelper = r11
            android.widget.CheckedTextView r0 = r11.mView
            android.content.Context r0 = r0.getContext()
            int[] r3 = androidx.appcompat.R$styleable.CheckedTextView
            r8 = 0
            androidx.appcompat.widget.TintTypedArray r9 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r0, r12, r3, r13, r8)
            android.widget.CheckedTextView r1 = r11.mView
            android.content.Context r2 = r1.getContext()
            android.content.res.TypedArray r5 = r9.mWrapped
            java.util.WeakHashMap r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            r7 = 0
            r4 = r12
            r6 = r13
            androidx.core.view.ViewCompat.Api29Impl.saveAttributeDataForStyleable(r1, r2, r3, r4, r5, r6, r7)
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            r13 = 4
            boolean r12 = r12.hasValue(r13)     // Catch: java.lang.Throwable -> L67
            if (r12 == 0) goto L6a
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            int r12 = r12.getResourceId(r13, r8)     // Catch: java.lang.Throwable -> L67
            if (r12 == 0) goto L6a
            android.widget.CheckedTextView r13 = r11.mView     // Catch: java.lang.Throwable -> L67 android.content.res.Resources.NotFoundException -> L6a
            android.content.Context r0 = r13.getContext()     // Catch: java.lang.Throwable -> L67 android.content.res.Resources.NotFoundException -> L6a
            android.graphics.drawable.Drawable r12 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r12, r0)     // Catch: java.lang.Throwable -> L67 android.content.res.Resources.NotFoundException -> L6a
            r13.setCheckMarkDrawable(r12)     // Catch: java.lang.Throwable -> L67 android.content.res.Resources.NotFoundException -> L6a
            goto L88
        L67:
            r0 = move-exception
            r10 = r0
            goto Lc8
        L6a:
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            r13 = 1
            boolean r12 = r12.hasValue(r13)     // Catch: java.lang.Throwable -> L67
            if (r12 == 0) goto L88
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            int r12 = r12.getResourceId(r13, r8)     // Catch: java.lang.Throwable -> L67
            if (r12 == 0) goto L88
            android.widget.CheckedTextView r13 = r11.mView     // Catch: java.lang.Throwable -> L67
            android.content.Context r0 = r13.getContext()     // Catch: java.lang.Throwable -> L67
            android.graphics.drawable.Drawable r12 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r12, r0)     // Catch: java.lang.Throwable -> L67
            r13.setCheckMarkDrawable(r12)     // Catch: java.lang.Throwable -> L67
        L88:
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            r13 = 6
            boolean r12 = r12.hasValue(r13)     // Catch: java.lang.Throwable -> L67
            if (r12 == 0) goto L9a
            android.widget.CheckedTextView r12 = r11.mView     // Catch: java.lang.Throwable -> L67
            android.content.res.ColorStateList r13 = r9.getColorStateList(r13)     // Catch: java.lang.Throwable -> L67
            r12.setCheckMarkTintList(r13)     // Catch: java.lang.Throwable -> L67
        L9a:
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            r13 = 7
            boolean r12 = r12.hasValue(r13)     // Catch: java.lang.Throwable -> L67
            if (r12 == 0) goto Lb4
            android.widget.CheckedTextView r11 = r11.mView     // Catch: java.lang.Throwable -> L67
            android.content.res.TypedArray r12 = r9.mWrapped     // Catch: java.lang.Throwable -> L67
            r0 = -1
            int r12 = r12.getInt(r13, r0)     // Catch: java.lang.Throwable -> L67
            r13 = 0
            android.graphics.PorterDuff$Mode r12 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r12, r13)     // Catch: java.lang.Throwable -> L67
            r11.setCheckMarkTintMode(r12)     // Catch: java.lang.Throwable -> L67
        Lb4:
            r9.recycle()
            androidx.appcompat.widget.AppCompatEmojiTextHelper r11 = r10.mAppCompatEmojiTextHelper
            if (r11 != 0) goto Lc2
            androidx.appcompat.widget.AppCompatEmojiTextHelper r11 = new androidx.appcompat.widget.AppCompatEmojiTextHelper
            r11.<init>(r10)
            r10.mAppCompatEmojiTextHelper = r11
        Lc2:
            androidx.appcompat.widget.AppCompatEmojiTextHelper r10 = r10.mAppCompatEmojiTextHelper
            r10.loadFromAttributes(r4, r6)
            return
        Lc8:
            r9.recycle()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatCheckedTextView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    @Override // android.widget.CheckedTextView
    public final void setCheckMarkDrawable(int i) {
        setCheckMarkDrawable(AppCompatResources.getDrawable(i, getContext()));
    }
}
