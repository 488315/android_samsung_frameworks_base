package com.android.internal.widget.remotecompose.core.operations.layout.animation;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class AnimationSpec extends Operation implements ModifierOperation {
    public static final AnimationSpec DEFAULT = new AnimationSpec();
    public static final AnimationSpec DISABLED = new AnimationSpec(0);
    int mAnimationId;
    ANIMATION mEnterAnimation;
    ANIMATION mExitAnimation;
    float mMotionDuration;
    int mMotionEasingType;
    float mVisibilityDuration;
    int mVisibilityEasingType;

    public enum ANIMATION {
        FADE_IN,
        FADE_OUT,
        SLIDE_LEFT,
        SLIDE_RIGHT,
        SLIDE_TOP,
        SLIDE_BOTTOM,
        ROTATE,
        PARTICLE
    }

    public static int id() {
        return 14;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    public AnimationSpec(int i, float f, int i2, float f2, int i3, ANIMATION animation, ANIMATION animation2) {
        this.mAnimationId = -1;
        this.mMotionDuration = 300.0f;
        this.mMotionEasingType = 1;
        this.mVisibilityDuration = 300.0f;
        this.mVisibilityEasingType = 1;
        this.mEnterAnimation = ANIMATION.FADE_IN;
        ANIMATION animation3 = ANIMATION.FADE_OUT;
        this.mAnimationId = i;
        this.mMotionDuration = f;
        this.mMotionEasingType = i2;
        this.mVisibilityDuration = f2;
        this.mVisibilityEasingType = i3;
        this.mEnterAnimation = animation;
        this.mExitAnimation = animation2;
    }

    public AnimationSpec() {
        this(-1, 600.0f, 1, 500.0f, 1, ANIMATION.FADE_IN, ANIMATION.FADE_OUT);
    }

    public AnimationSpec(int i) {
        this();
        this.mAnimationId = i;
    }

    public boolean isAnimationEnabled() {
        return this.mAnimationId != 0;
    }

    public int getAnimationId() {
        return this.mAnimationId;
    }

    public float getMotionDuration() {
        return this.mMotionDuration;
    }

    public int getMotionEasingType() {
        return this.mMotionEasingType;
    }

    public float getVisibilityDuration() {
        return this.mVisibilityDuration;
    }

    public int getVisibilityEasingType() {
        return this.mVisibilityEasingType;
    }

    public ANIMATION getEnterAnimation() {
        return this.mEnterAnimation;
    }

    public ANIMATION getExitAnimation() {
        return this.mExitAnimation;
    }

    public String toString() {
        return "ANIMATION_SPEC (" + this.mMotionDuration + " ms)";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "ANIMATION_SPEC = [" + getMotionDuration() + ", " + getMotionEasingType() + ", " + getVisibilityDuration() + ", " + getVisibilityEasingType() + ", " + getEnterAnimation() + ", " + getExitAnimation() + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType("AnimationSpec").add("animationId", Integer.valueOf(this.mAnimationId)).add("motionDuration", Float.valueOf(getMotionDuration())).add("motionEasingType", Easing.getString(getMotionEasingType())).add("visibilityDuration", Float.valueOf(getVisibilityDuration())).add("visibilityEasingType", Easing.getString(getVisibilityEasingType())).add("enterAnimation", getEnterAnimation()).add("exitAnimation", getExitAnimation());
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mAnimationId, this.mMotionDuration, this.mMotionEasingType, this.mVisibilityDuration, this.mVisibilityEasingType, this.mEnterAnimation, this.mExitAnimation);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    public static String name() {
        return "AnimationSpec";
    }

    public static int animationToInt(ANIMATION animation) {
        return animation.ordinal();
    }

    public static ANIMATION intToAnimation(int i) {
        switch (i) {
            case 0:
                return ANIMATION.FADE_IN;
            case 1:
                return ANIMATION.FADE_OUT;
            case 2:
                return ANIMATION.SLIDE_LEFT;
            case 3:
                return ANIMATION.SLIDE_RIGHT;
            case 4:
                return ANIMATION.SLIDE_TOP;
            case 5:
                return ANIMATION.SLIDE_BOTTOM;
            case 6:
                return ANIMATION.ROTATE;
            case 7:
                return ANIMATION.PARTICLE;
            default:
                return ANIMATION.FADE_IN;
        }
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, int i2, float f2, int i3, ANIMATION animation, ANIMATION animation2) {
        wireBuffer.start(14);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeInt(i2);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(animationToInt(animation));
        wireBuffer.writeInt(animationToInt(animation2));
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new AnimationSpec(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readInt(), intToAnimation(wireBuffer.readInt()), intToAnimation(wireBuffer.readInt())));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("define the animation").field(0, "animationId", "").field(0, "motionDuration", "").field(0, "motionEasingType", "").field(0, "visibilityDuration", "").field(0, "visibilityEasingType", "");
    }
}
