package com.android.systemui.controls;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.recents.TriangleShape;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TooltipManager {
    public final View arrowView;
    public final boolean below;
    public final View dismissView;
    public final ViewGroup layout;
    public final int maxTimesShown;
    public final String preferenceName;
    public final Function1 preferenceStorer;
    public int shown;
    public final TextView textView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TooltipManager(Context context, String str, int i, boolean z) {
        this.preferenceName = str;
        this.maxTimesShown = i;
        this.below = z;
        this.shown = Prefs.getInt(context, str, 0);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.controls_onboarding, (ViewGroup) null);
        this.layout = viewGroup;
        this.preferenceStorer = new TooltipManager$preferenceStorer$1(context, this);
        viewGroup.setAlpha(0.0f);
        this.textView = (TextView) viewGroup.requireViewById(R.id.onboarding_text);
        View requireViewById = viewGroup.requireViewById(R.id.dismiss);
        requireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.TooltipManager$dismissView$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TooltipManager.this.hide(true);
            }
        });
        this.dismissView = requireViewById;
        View requireViewById2 = viewGroup.requireViewById(R.id.arrow);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorAccent, typedValue, true);
        int color = context.getResources().getColor(typedValue.resourceId, context.getTheme());
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.recents_onboarding_toast_arrow_corner_radius);
        ViewGroup.LayoutParams layoutParams = requireViewById2.getLayoutParams();
        float f = layoutParams.width;
        float f2 = layoutParams.height;
        int i2 = TriangleShape.$r8$clinit;
        Path path = new Path();
        if (z) {
            path.moveTo(0.0f, f2);
            path.lineTo(f, f2);
            path.lineTo(f / 2.0f, 0.0f);
            path.close();
        } else {
            path.moveTo(0.0f, 0.0f);
            path.lineTo(f / 2.0f, f2);
            path.lineTo(f, 0.0f);
            path.close();
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new TriangleShape(path, f, f2));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(color);
        paint.setPathEffect(new CornerPathEffect(dimensionPixelSize));
        requireViewById2.setBackground(shapeDrawable);
        this.arrowView = requireViewById2;
        if (z) {
            return;
        }
        viewGroup.removeView(requireViewById2);
        viewGroup.addView(requireViewById2);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) requireViewById2.getLayoutParams();
        marginLayoutParams.bottomMargin = marginLayoutParams.topMargin;
        marginLayoutParams.topMargin = 0;
    }

    public final void hide(final boolean z) {
        ViewGroup viewGroup = this.layout;
        if (viewGroup.getAlpha() == 0.0f) {
            return;
        }
        viewGroup.post(new Runnable() { // from class: com.android.systemui.controls.TooltipManager$hide$1
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    this.layout.animate().alpha(0.0f).withLayer().setStartDelay(0L).setDuration(100L).setInterpolator(new AccelerateInterpolator()).start();
                } else {
                    this.layout.animate().cancel();
                    this.layout.setAlpha(0.0f);
                }
            }
        });
    }

    public final void show(final int i, final int i2) {
        if (this.shown < this.maxTimesShown) {
            this.textView.setText(R.string.controls_structure_tooltip);
            int i3 = this.shown + 1;
            this.shown = i3;
            ((TooltipManager$preferenceStorer$1) this.preferenceStorer).invoke(Integer.valueOf(i3));
            this.layout.post(new Runnable() { // from class: com.android.systemui.controls.TooltipManager$show$1
                @Override // java.lang.Runnable
                public final void run() {
                    TooltipManager.this.layout.getLocationOnScreen(new int[2]);
                    TooltipManager.this.layout.setTranslationX((i - r1[0]) - (r2.getWidth() / 2));
                    TooltipManager tooltipManager = TooltipManager.this;
                    tooltipManager.layout.setTranslationY((i2 - r1[1]) - (!tooltipManager.below ? r2.getHeight() : 0));
                    if (TooltipManager.this.layout.getAlpha() == 0.0f) {
                        TooltipManager.this.layout.animate().alpha(1.0f).withLayer().setStartDelay(500L).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
                    }
                }
            });
        }
    }

    public /* synthetic */ TooltipManager(Context context, String str, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i2 & 4) != 0 ? 2 : i, (i2 & 8) != 0 ? true : z);
    }
}
