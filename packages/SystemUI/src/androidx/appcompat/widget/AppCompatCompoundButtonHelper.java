package androidx.appcompat.widget;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatCompoundButtonHelper {
    public PorterDuff.Mode mButtonTintMode = null;
    public boolean mHasButtonTint = false;
    public boolean mHasButtonTintMode = false;
    public boolean mSkipNextApply;
    public final CompoundButton mView;

    public AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
    }

    public final void applyButtonTint() {
        Drawable buttonDrawable = this.mView.getButtonDrawable();
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable mutate = buttonDrawable.mutate();
                if (this.mHasButtonTint) {
                    mutate.setTintList(null);
                }
                if (this.mHasButtonTintMode) {
                    mutate.setTintMode(this.mButtonTintMode);
                }
                if (mutate.isStateful()) {
                    mutate.setState(this.mView.getDrawableState());
                }
                this.mView.setButtonDrawable(mutate);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0065 A[Catch: all -> 0x003c, TryCatch #1 {all -> 0x003c, blocks: (B:3:0x001d, B:5:0x0026, B:8:0x002e, B:9:0x005c, B:11:0x0065, B:12:0x006e, B:14:0x0077, B:21:0x003f, B:23:0x0047, B:25:0x004f), top: B:2:0x001d }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0077 A[Catch: all -> 0x003c, TRY_LEAVE, TryCatch #1 {all -> 0x003c, blocks: (B:3:0x001d, B:5:0x0026, B:8:0x002e, B:9:0x005c, B:11:0x0065, B:12:0x006e, B:14:0x0077, B:21:0x003f, B:23:0x0047, B:25:0x004f), top: B:2:0x001d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadFromAttributes(android.util.AttributeSet r11, int r12) {
        /*
            r10 = this;
            android.widget.CompoundButton r0 = r10.mView
            android.content.Context r0 = r0.getContext()
            int[] r3 = androidx.appcompat.R$styleable.CompoundButton
            r8 = 0
            androidx.appcompat.widget.TintTypedArray r9 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r0, r11, r3, r12, r8)
            android.widget.CompoundButton r1 = r10.mView
            android.content.Context r2 = r1.getContext()
            android.content.res.TypedArray r5 = r9.mWrapped
            java.util.WeakHashMap r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            r7 = 0
            r4 = r11
            r6 = r12
            androidx.core.view.ViewCompat.Api29Impl.saveAttributeDataForStyleable(r1, r2, r3, r4, r5, r6, r7)
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            r12 = 1
            boolean r11 = r11.hasValue(r12)     // Catch: java.lang.Throwable -> L3c
            if (r11 == 0) goto L3f
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            int r11 = r11.getResourceId(r12, r8)     // Catch: java.lang.Throwable -> L3c
            if (r11 == 0) goto L3f
            android.widget.CompoundButton r12 = r10.mView     // Catch: java.lang.Throwable -> L3c android.content.res.Resources.NotFoundException -> L3f
            android.content.Context r0 = r12.getContext()     // Catch: java.lang.Throwable -> L3c android.content.res.Resources.NotFoundException -> L3f
            android.graphics.drawable.Drawable r11 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r11, r0)     // Catch: java.lang.Throwable -> L3c android.content.res.Resources.NotFoundException -> L3f
            r12.setButtonDrawable(r11)     // Catch: java.lang.Throwable -> L3c android.content.res.Resources.NotFoundException -> L3f
            goto L5c
        L3c:
            r0 = move-exception
            r10 = r0
            goto L8c
        L3f:
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            boolean r11 = r11.hasValue(r8)     // Catch: java.lang.Throwable -> L3c
            if (r11 == 0) goto L5c
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            int r11 = r11.getResourceId(r8, r8)     // Catch: java.lang.Throwable -> L3c
            if (r11 == 0) goto L5c
            android.widget.CompoundButton r12 = r10.mView     // Catch: java.lang.Throwable -> L3c
            android.content.Context r0 = r12.getContext()     // Catch: java.lang.Throwable -> L3c
            android.graphics.drawable.Drawable r11 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r11, r0)     // Catch: java.lang.Throwable -> L3c
            r12.setButtonDrawable(r11)     // Catch: java.lang.Throwable -> L3c
        L5c:
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            r12 = 2
            boolean r11 = r11.hasValue(r12)     // Catch: java.lang.Throwable -> L3c
            if (r11 == 0) goto L6e
            android.widget.CompoundButton r11 = r10.mView     // Catch: java.lang.Throwable -> L3c
            android.content.res.ColorStateList r12 = r9.getColorStateList(r12)     // Catch: java.lang.Throwable -> L3c
            r11.setButtonTintList(r12)     // Catch: java.lang.Throwable -> L3c
        L6e:
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            r12 = 3
            boolean r11 = r11.hasValue(r12)     // Catch: java.lang.Throwable -> L3c
            if (r11 == 0) goto L88
            android.widget.CompoundButton r10 = r10.mView     // Catch: java.lang.Throwable -> L3c
            android.content.res.TypedArray r11 = r9.mWrapped     // Catch: java.lang.Throwable -> L3c
            r0 = -1
            int r11 = r11.getInt(r12, r0)     // Catch: java.lang.Throwable -> L3c
            r12 = 0
            android.graphics.PorterDuff$Mode r11 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r11, r12)     // Catch: java.lang.Throwable -> L3c
            r10.setButtonTintMode(r11)     // Catch: java.lang.Throwable -> L3c
        L88:
            r9.recycle()
            return
        L8c:
            r9.recycle()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatCompoundButtonHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }
}
