package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.util.Property;
import android.view.View;
import com.android.systemui.R;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AnimatableProperty {
    public static final C27067 ALPHA;
    public static final C27067 TRANSLATION_X;

    /* renamed from: Y */
    public static final C27067 f351Y;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.AnimatableProperty$5 */
    public final class C27045 extends FloatProperty {
        public final /* synthetic */ Function val$getter;
        public final /* synthetic */ BiConsumer val$setter;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C27045(String str, Function function, BiConsumer biConsumer) {
            super(str);
            this.val$getter = function;
            this.val$setter = biConsumer;
        }

        @Override // android.util.Property
        public final Float get(Object obj) {
            return (Float) this.val$getter.apply((View) obj);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            this.val$setter.accept((View) obj, Float.valueOf(f));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.AnimatableProperty$6 */
    public final class C27056 extends AnimatableProperty {
        public final /* synthetic */ int val$animatorTag;
        public final /* synthetic */ int val$endValueTag;
        public final /* synthetic */ Property val$property;
        public final /* synthetic */ int val$startValueTag;

        public C27056(int i, int i2, int i3, Property property) {
            this.val$startValueTag = i;
            this.val$endValueTag = i2;
            this.val$animatorTag = i3;
            this.val$property = property;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationEndTag() {
            return this.val$endValueTag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationStartTag() {
            return this.val$startValueTag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimatorTag() {
            return this.val$animatorTag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final Property getProperty() {
            return this.val$property;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.AnimatableProperty$7 */
    public final class C27067 extends AnimatableProperty {
        public final /* synthetic */ int val$animatorTag;
        public final /* synthetic */ int val$endValueTag;
        public final /* synthetic */ Property val$property;
        public final /* synthetic */ int val$startValueTag;

        public C27067(int i, int i2, int i3, Property property) {
            this.val$startValueTag = i;
            this.val$endValueTag = i2;
            this.val$animatorTag = i3;
            this.val$property = property;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationEndTag() {
            return this.val$endValueTag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimationStartTag() {
            return this.val$startValueTag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final int getAnimatorTag() {
            return this.val$animatorTag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public final Property getProperty() {
            return this.val$property;
        }
    }

    static {
        from(R.id.x_animator_tag, R.id.x_animator_tag_start_value, R.id.x_animator_tag_end_value, View.X);
        f351Y = new C27067(R.id.y_animator_tag_start_value, R.id.y_animator_tag_end_value, R.id.y_animator_tag, View.Y);
        TRANSLATION_X = new C27067(R.id.x_animator_tag_start_value, R.id.x_animator_tag_end_value, R.id.x_animator_tag, View.TRANSLATION_X);
        from(R.id.scale_x_animator_tag, R.id.scale_x_animator_start_value_tag, R.id.scale_x_animator_end_value_tag, View.SCALE_X);
        from(R.id.scale_y_animator_tag, R.id.scale_y_animator_start_value_tag, R.id.scale_y_animator_end_value_tag, View.SCALE_Y);
        ALPHA = new C27067(R.id.alpha_animator_start_value_tag, R.id.alpha_animator_end_value_tag, R.id.alpha_animator_tag, View.ALPHA);
        from(R.id.absolute_x_animator_tag, R.id.absolute_x_animator_start_tag, R.id.absolute_x_animator_end_tag, new FloatProperty("ViewAbsoluteX") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                View view = (View) obj;
                Object tag = view.getTag(R.id.absolute_x_current_value);
                return tag instanceof Float ? (Float) tag : (Float) View.X.get(view);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(R.id.absolute_x_current_value, Float.valueOf(f));
                View.X.set(view, Float.valueOf(f));
            }
        });
        from(R.id.absolute_y_animator_tag, R.id.absolute_y_animator_start_tag, R.id.absolute_y_animator_end_tag, new FloatProperty("ViewAbsoluteY") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.2
            @Override // android.util.Property
            public final Float get(Object obj) {
                View view = (View) obj;
                Object tag = view.getTag(R.id.absolute_y_current_value);
                return tag instanceof Float ? (Float) tag : (Float) View.Y.get(view);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(R.id.absolute_y_current_value, Float.valueOf(f));
                View.Y.set(view, Float.valueOf(f));
            }
        });
        from(R.id.view_width_animator_tag, R.id.view_width_animator_start_tag, R.id.view_width_animator_end_tag, new FloatProperty("ViewWidth") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.3
            @Override // android.util.Property
            public final Float get(Object obj) {
                Object tag = ((View) obj).getTag(R.id.view_width_current_value);
                return tag instanceof Float ? (Float) tag : Float.valueOf(r2.getWidth());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(R.id.view_width_current_value, Float.valueOf(f));
                view.setRight((int) (view.getLeft() + f));
            }
        });
        from(R.id.view_height_animator_tag, R.id.view_height_animator_start_tag, R.id.view_height_animator_end_tag, new FloatProperty("ViewHeight") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.4
            @Override // android.util.Property
            public final Float get(Object obj) {
                Object tag = ((View) obj).getTag(R.id.view_height_current_value);
                return tag instanceof Float ? (Float) tag : Float.valueOf(r2.getHeight());
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(R.id.view_height_current_value, Float.valueOf(f));
                view.setBottom((int) (view.getTop() + f));
            }
        });
    }

    public static void from(int i, int i2, int i3, Property property) {
        new C27067(i2, i3, i, property);
    }

    public abstract int getAnimationEndTag();

    public abstract int getAnimationStartTag();

    public abstract int getAnimatorTag();

    public abstract Property getProperty();
}
