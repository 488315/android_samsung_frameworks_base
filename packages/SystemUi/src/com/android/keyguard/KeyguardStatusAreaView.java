package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardStatusAreaView extends LinearLayout {
    public static final AnimatableProperty.C27067 TRANSLATE_X_CLOCK_DESIGN;
    public static final AnimatableProperty.C27067 TRANSLATE_Y_CLOCK_DESIGN;
    public static final AnimatableProperty.C27067 TRANSLATE_Y_CLOCK_SIZE;
    public float translateXFromAod;
    public float translateXFromClockDesign;
    public float translateXFromUnfold;
    public float translateYFromClockDesign;
    public float translateYFromClockSize;

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
        FloatProperty floatProperty = new FloatProperty() { // from class: com.android.keyguard.KeyguardStatusAreaView$Companion$TRANSLATE_X_CLOCK_DESIGN$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((KeyguardStatusAreaView) obj).translateXFromClockDesign);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                KeyguardStatusAreaView keyguardStatusAreaView = (KeyguardStatusAreaView) obj;
                keyguardStatusAreaView.translateXFromClockDesign = f;
                keyguardStatusAreaView.setTranslationX(keyguardStatusAreaView.translateXFromAod + f + keyguardStatusAreaView.translateXFromUnfold);
            }
        };
        AnimatableProperty.C27067 c27067 = AnimatableProperty.f351Y;
        TRANSLATE_X_CLOCK_DESIGN = new AnimatableProperty.C27067(R.id.translate_x_clock_design_animator_start_tag, R.id.translate_x_clock_design_animator_end_tag, R.id.translate_x_clock_design_animator_tag, floatProperty);
        AnimatableProperty.from(R.id.translate_x_aod_animator_tag, R.id.translate_x_aod_animator_start_tag, R.id.translate_x_aod_animator_end_tag, new FloatProperty() { // from class: com.android.keyguard.KeyguardStatusAreaView$Companion$TRANSLATE_X_AOD$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((KeyguardStatusAreaView) obj).translateXFromAod);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                KeyguardStatusAreaView keyguardStatusAreaView = (KeyguardStatusAreaView) obj;
                keyguardStatusAreaView.translateXFromAod = f;
                keyguardStatusAreaView.setTranslationX(f + keyguardStatusAreaView.translateXFromClockDesign + keyguardStatusAreaView.translateXFromUnfold);
            }
        });
        TRANSLATE_Y_CLOCK_SIZE = new AnimatableProperty.C27067(R.id.translate_y_clock_size_animator_start_tag, R.id.translate_y_clock_size_animator_end_tag, R.id.translate_y_clock_size_animator_tag, new FloatProperty() { // from class: com.android.keyguard.KeyguardStatusAreaView$Companion$TRANSLATE_Y_CLOCK_SIZE$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((KeyguardStatusAreaView) obj).translateYFromClockSize);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                KeyguardStatusAreaView keyguardStatusAreaView = (KeyguardStatusAreaView) obj;
                keyguardStatusAreaView.translateYFromClockSize = f;
                keyguardStatusAreaView.setTranslationY(f + keyguardStatusAreaView.translateYFromClockDesign);
            }
        });
        TRANSLATE_Y_CLOCK_DESIGN = new AnimatableProperty.C27067(R.id.translate_y_clock_design_animator_start_tag, R.id.translate_y_clock_design_animator_end_tag, R.id.translate_y_clock_design_animator_tag, new FloatProperty() { // from class: com.android.keyguard.KeyguardStatusAreaView$Companion$TRANSLATE_Y_CLOCK_DESIGN$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((KeyguardStatusAreaView) obj).translateYFromClockDesign);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                KeyguardStatusAreaView keyguardStatusAreaView = (KeyguardStatusAreaView) obj;
                keyguardStatusAreaView.translateYFromClockDesign = f;
                keyguardStatusAreaView.setTranslationY(f + keyguardStatusAreaView.translateYFromClockSize);
            }
        });
    }

    public /* synthetic */ KeyguardStatusAreaView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public KeyguardStatusAreaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
