package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.util.Property;
import android.view.View;
import com.android.systemui.R;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AnimatableProperty {
    public static final AnonymousClass7 ALPHA;
    public static final AnonymousClass7 SCALE_X;
    public static final AnonymousClass7 SCALE_Y;
    public static final AnonymousClass7 TRANSLATION_X;
    public static final AnonymousClass7 Y;

    /* renamed from: com.android.systemui.statusbar.notification.AnimatableProperty$5, reason: invalid class name */
    public final class AnonymousClass5 extends FloatProperty {
        public final /* synthetic */ Function val$getter;
        public final /* synthetic */ BiConsumer val$setter;

        public AnonymousClass5(String str, Function function, BiConsumer biConsumer) {
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

    /* renamed from: com.android.systemui.statusbar.notification.AnimatableProperty$6, reason: invalid class name */
    public final class AnonymousClass6 extends AnimatableProperty {
        public final /* synthetic */ int val$animatorTag;
        public final /* synthetic */ int val$endValueTag;
        public final /* synthetic */ Property val$property;
        public final /* synthetic */ int val$startValueTag;

        public AnonymousClass6(int i, int i2, int i3, Property property) {
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

    /* renamed from: com.android.systemui.statusbar.notification.AnimatableProperty$7, reason: invalid class name */
    public final class AnonymousClass7 extends AnimatableProperty {
        public final /* synthetic */ int val$animatorTag;
        public final /* synthetic */ int val$endValueTag;
        public final /* synthetic */ Property val$property;
        public final /* synthetic */ int val$startValueTag;

        public AnonymousClass7(int i, int i2, int i3, Property property) {
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
        new AnonymousClass7(R.id.x_animator_tag_start_value, R.id.x_animator_tag_end_value, R.id.x_animator_tag, View.X);
        Y = new AnonymousClass7(R.id.y_animator_tag_start_value, R.id.y_animator_tag_end_value, R.id.y_animator_tag, View.Y);
        TRANSLATION_X = new AnonymousClass7(R.id.x_animator_tag_start_value, R.id.x_animator_tag_end_value, R.id.x_animator_tag, View.TRANSLATION_X);
        SCALE_X = new AnonymousClass7(R.id.scale_x_animator_start_value_tag, R.id.scale_x_animator_end_value_tag, R.id.scale_x_animator_tag, View.SCALE_X);
        SCALE_Y = new AnonymousClass7(R.id.scale_y_animator_start_value_tag, R.id.scale_y_animator_end_value_tag, R.id.scale_y_animator_tag, View.SCALE_Y);
        ALPHA = new AnonymousClass7(R.id.alpha_animator_start_value_tag, R.id.alpha_animator_end_value_tag, R.id.alpha_animator_tag, View.ALPHA);
        new AnonymousClass7(R.id.absolute_x_animator_start_tag, R.id.absolute_x_animator_end_tag, R.id.absolute_x_animator_tag, new FloatProperty("ViewAbsoluteX") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.1
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
        new AnonymousClass7(R.id.absolute_y_animator_start_tag, R.id.absolute_y_animator_end_tag, R.id.absolute_y_animator_tag, new FloatProperty("ViewAbsoluteY") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.2
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
        new AnonymousClass7(R.id.view_width_animator_start_tag, R.id.view_width_animator_end_tag, R.id.view_width_animator_tag, new FloatProperty("ViewWidth") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.3
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
        new AnonymousClass7(R.id.view_height_animator_start_tag, R.id.view_height_animator_end_tag, R.id.view_height_animator_tag, new FloatProperty("ViewHeight") { // from class: com.android.systemui.statusbar.notification.AnimatableProperty.4
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

    public abstract int getAnimationEndTag();

    public abstract int getAnimationStartTag();

    public abstract int getAnimatorTag();

    public abstract Property getProperty();
}
