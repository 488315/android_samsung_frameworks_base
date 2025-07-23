package com.android.systemui.util;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.InsetDrawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RoundedCornerProgressDrawable extends InsetDrawable {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);
    private static final int MAX_LEVEL = 10000;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class RoundedCornerState extends Drawable.ConstantState {
        private final Drawable.ConstantState wrappedState;

        public RoundedCornerState(Drawable.ConstantState constantState) {
            this.wrappedState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.wrappedState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return newDrawable(null, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return new RoundedCornerProgressDrawable(((DrawableWrapper) this.wrappedState.newDrawable(resources, theme)).getDrawable());
        }
    }

    public RoundedCornerProgressDrawable() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = getDrawable();
        return (drawable != null ? drawable.canApplyTheme() : false) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | 4096;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        Drawable.ConstantState constantState = super.getConstantState();
        constantState.getClass();
        return new RoundedCornerState(constantState);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        onLevelChange(getLevel());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        onLevelChange(getLevel());
        return super.onLayoutDirectionChanged(i);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public boolean onLevelChange(int i) {
        Drawable drawable = getDrawable();
        Rect bounds = drawable != null ? drawable.getBounds() : null;
        bounds.getClass();
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
