package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RoundableState {
    public static final Companion Companion = new Companion(null);
    public static final AnimationProperties DURATION;
    public final AnimatableProperty.C27067 bottomAnimatable;
    public float bottomRoundness;
    public final Map bottomRoundnessMap;
    public float maxRadius;
    public final float[] radiiBuffer;
    public final Roundable roundable;
    public final View targetView;
    public final AnimatableProperty.C27067 topAnimatable;
    public float topRoundness;
    public final Map topRoundnessMap;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        AnimationProperties animationProperties = new AnimationProperties();
        animationProperties.duration = 200L;
        DURATION = animationProperties;
    }

    public RoundableState(View view, final Roundable roundable, float f) {
        this.targetView = view;
        this.roundable = roundable;
        this.maxRadius = f;
        Companion.getClass();
        FloatProperty floatProperty = new FloatProperty() { // from class: com.android.systemui.statusbar.notification.RoundableState$Companion$topAnimatable$1
            {
                super("topRoundness");
            }

            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(Roundable.this.getTopRoundness());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f2) {
                Roundable.this.getRoundableState().topRoundness = f2;
                Roundable.this.applyRoundnessAndInvalidate();
            }
        };
        AnimatableProperty.C27067 c27067 = AnimatableProperty.f351Y;
        this.topAnimatable = new AnimatableProperty.C27067(R.id.top_roundess_animator_end_tag, R.id.top_roundess_animator_start_tag, R.id.top_roundess_animator_tag, floatProperty);
        this.bottomAnimatable = new AnimatableProperty.C27067(R.id.bottom_roundess_animator_end_tag, R.id.bottom_roundess_animator_start_tag, R.id.bottom_roundess_animator_tag, new FloatProperty() { // from class: com.android.systemui.statusbar.notification.RoundableState$Companion$bottomAnimatable$1
            {
                super("bottomRoundness");
            }

            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(Roundable.this.getBottomRoundness());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f2) {
                Roundable.this.getRoundableState().bottomRoundness = f2;
                Roundable.this.applyRoundnessAndInvalidate();
            }
        });
        this.topRoundnessMap = new LinkedHashMap();
        this.bottomRoundnessMap = new LinkedHashMap();
        this.radiiBuffer = new float[8];
    }

    public final String debugString() {
        StringBuilder sb = new StringBuilder("Roundable { ");
        sb.append("top: { value: " + this.topRoundness + ", requests: " + this.topRoundnessMap + "}");
        sb.append(", ");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, "bottom: { value: " + this.bottomRoundness + ", requests: " + this.bottomRoundnessMap + "}", "}");
    }
}
