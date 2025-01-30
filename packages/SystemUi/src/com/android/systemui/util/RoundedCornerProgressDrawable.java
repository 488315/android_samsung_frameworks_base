package com.android.systemui.util;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RoundedCornerProgressDrawable extends InsetDrawable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RoundedCornerState extends Drawable.ConstantState {
        public final Drawable.ConstantState wrappedState;

        public RoundedCornerState(Drawable.ConstantState constantState) {
            this.wrappedState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return this.wrappedState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return newDrawable(null, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return new RoundedCornerProgressDrawable(((DrawableWrapper) this.wrappedState.newDrawable(resources, theme)).getDrawable());
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RoundedCornerProgressDrawable() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = getDrawable();
        return (drawable != null ? drawable.canApplyTheme() : false) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | 4096;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        Drawable.ConstantState constantState = super.getConstantState();
        Intrinsics.checkNotNull(constantState);
        return new RoundedCornerState(constantState);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        onLevelChange(getLevel());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        onLevelChange(getLevel());
        return super.onLayoutDirectionChanged(i);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        Drawable drawable = getDrawable();
        Rect bounds = drawable != null ? drawable.getBounds() : null;
        Intrinsics.checkNotNull(bounds);
        int width = (((getBounds().width() - getBounds().height()) * i) / 10000) + getBounds().height();
        Drawable drawable2 = getDrawable();
        if (drawable2 != null) {
            drawable2.setBounds(getBounds().left, bounds.top, getBounds().left + width, bounds.bottom);
        }
        return super.onLevelChange(i);
    }

    public /* synthetic */ RoundedCornerProgressDrawable(Drawable drawable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : drawable);
    }

    public RoundedCornerProgressDrawable(Drawable drawable) {
        super(drawable, 0);
    }
}
