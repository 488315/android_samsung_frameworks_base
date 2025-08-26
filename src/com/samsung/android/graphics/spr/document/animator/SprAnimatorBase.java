package com.samsung.android.graphics.spr.document.animator;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import com.samsung.android.graphics.spr.animation.interpolator.BackEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.BackEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.BackEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.BounceEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.BounceEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.BounceEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.CircEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.CircEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.CircEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.CubicEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.CubicEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.CubicEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.ElasticEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.ElasticEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.ElasticEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.ExpoEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.ExpoEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.ExpoEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuadEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.QuadEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuadEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuartEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.QuartEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuartEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuintEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.QuintEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuintEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.QuintOut50;
import com.samsung.android.graphics.spr.animation.interpolator.QuintOut80;
import com.samsung.android.graphics.spr.animation.interpolator.SineEaseIn;
import com.samsung.android.graphics.spr.animation.interpolator.SineEaseInOut;
import com.samsung.android.graphics.spr.animation.interpolator.SineEaseOut;
import com.samsung.android.graphics.spr.animation.interpolator.SineIn33;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut33;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut50;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut60;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut70;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut80;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;
import com.samsung.android.graphics.spr.animation.interpolator.SineOut33;
import com.samsung.android.graphics.spr.document.SprInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public abstract class SprAnimatorBase extends ValueAnimator implements Cloneable {
    public static final byte INTERPOLATOR_TYPE_ACCELERATE = 2;
    public static final byte INTERPOLATOR_TYPE_ACCELERATE_DECELERATE = 1;
    public static final byte INTERPOLATOR_TYPE_ANTICIPATE = 3;
    public static final byte INTERPOLATOR_TYPE_ANTICIPATE_OVERSHOOT = 4;
    public static final byte INTERPOLATOR_TYPE_BACKEASEIN = 10;
    public static final byte INTERPOLATOR_TYPE_BACKEASEINOUT = 12;
    public static final byte INTERPOLATOR_TYPE_BACKEASEOUT = 11;
    public static final byte INTERPOLATOR_TYPE_BOUNCE = 5;
    public static final byte INTERPOLATOR_TYPE_BOUNCEEASEIN = 13;
    public static final byte INTERPOLATOR_TYPE_BOUNCEEASEINOUT = 15;
    public static final byte INTERPOLATOR_TYPE_BOUNCEEASEOUT = 14;
    public static final byte INTERPOLATOR_TYPE_CIRCEASEIN = 16;
    public static final byte INTERPOLATOR_TYPE_CIRCEASEINOUT = 18;
    public static final byte INTERPOLATOR_TYPE_CIRCEASEOUT = 17;
    public static final byte INTERPOLATOR_TYPE_CUBICEASEIN = 19;
    public static final byte INTERPOLATOR_TYPE_CUBICEASEINOUT = 21;
    public static final byte INTERPOLATOR_TYPE_CUBICEASEOUT = 20;
    public static final byte INTERPOLATOR_TYPE_CYCLE = 6;
    public static final byte INTERPOLATOR_TYPE_DECELERATE = 7;
    public static final byte INTERPOLATOR_TYPE_ELASTICEASEIN = 22;
    public static final byte INTERPOLATOR_TYPE_ELASTICEASEINOUT = 24;
    public static final byte INTERPOLATOR_TYPE_ELASTICEASEOUT = 23;
    public static final byte INTERPOLATOR_TYPE_EXPOEASEIN = 25;
    public static final byte INTERPOLATOR_TYPE_EXPOEASEINOUT = 27;
    public static final byte INTERPOLATOR_TYPE_EXPOEASEOUT = 26;
    public static final byte INTERPOLATOR_TYPE_LINEAR = 8;
    public static final byte INTERPOLATOR_TYPE_OVERSHOOT = 9;
    public static final byte INTERPOLATOR_TYPE_QUADEASEIN = 28;
    public static final byte INTERPOLATOR_TYPE_QUADEASEINOUT = 30;
    public static final byte INTERPOLATOR_TYPE_QUADEASEOUT = 29;
    public static final byte INTERPOLATOR_TYPE_QUARTEASEIN = 31;
    public static final byte INTERPOLATOR_TYPE_QUARTEASEINOUT = 33;
    public static final byte INTERPOLATOR_TYPE_QUARTEASEOUT = 32;
    public static final byte INTERPOLATOR_TYPE_QUINTEASEIN = 34;
    public static final byte INTERPOLATOR_TYPE_QUINTEASEINOUT = 36;
    public static final byte INTERPOLATOR_TYPE_QUINTEASEOUT = 35;
    public static final byte INTERPOLATOR_TYPE_QUINTOUT50 = 40;
    public static final byte INTERPOLATOR_TYPE_QUINTOUT80 = 41;
    public static final byte INTERPOLATOR_TYPE_SINEEASEIN = 37;
    public static final byte INTERPOLATOR_TYPE_SINEEASEINOUT = 39;
    public static final byte INTERPOLATOR_TYPE_SINEEASEOUT = 38;
    public static final byte INTERPOLATOR_TYPE_SINEIN33 = 42;
    public static final byte INTERPOLATOR_TYPE_SINEINOUT33 = 43;
    public static final byte INTERPOLATOR_TYPE_SINEINOUT50 = 44;
    public static final byte INTERPOLATOR_TYPE_SINEINOUT60 = 45;
    public static final byte INTERPOLATOR_TYPE_SINEINOUT70 = 46;
    public static final byte INTERPOLATOR_TYPE_SINEINOUT80 = 47;
    public static final byte INTERPOLATOR_TYPE_SINEINOUT90 = 48;
    public static final byte INTERPOLATOR_TYPE_SINEOUT33 = 49;
    public static final byte REPEAT_MODE_RESTART = 2;
    public static final byte REPEAT_MODE_REVERSE = 1;
    public static final byte TYPE_ALPHA = 6;
    public static final byte TYPE_FILL_COLOR = 5;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_ROTATE = 3;
    public static final byte TYPE_SCALE = 2;
    public static final byte TYPE_STROKE_COLOR = 4;
    public static final byte TYPE_TRANSLATE = 1;
    public final byte mType;
    private byte mInterpolatorType = 1;
    private float mInterpolatorCycle = 0.0f;
    private float mInterpolatorOvershot = 0.0f;
    private float mInterpolatorAmplitude = 0.0f;
    private float mInterpolatorPeriod = 0.0f;
    protected final SprAnimatorBase mIntrinsic = this;

    public static class UpdateParameter {
        public float alpha;
        public int fillColor;
        public boolean isLastFrame;
        public boolean isUpdatedFillColor;
        public boolean isUpdatedRotate;
        public boolean isUpdatedScale;
        public boolean isUpdatedStrokeColor;
        public boolean isUpdatedTranslate;
        public float rotateDegree;
        public float rotatePivotX;
        public float rotatePivotY;
        public float scalePivotX;
        public float scalePivotY;
        public float scaleX;
        public float scaleY;
        public int strokeColor;
        public float translateDx;
        public float translateDy;
    }

    public abstract boolean updateValues(UpdateParameter updateParameter);

    protected SprAnimatorBase(byte b) {
        this.mType = b;
    }

    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        this.mInterpolatorType = sprInputStream.readByte();
        int i = sprInputStream.readInt();
        byte[] bArr = new byte[i];
        sprInputStream.read(bArr, 0, i);
        byte b = this.mInterpolatorType;
        if (b == 6) {
            float f = ByteBuffer.wrap(bArr).getFloat();
            this.mInterpolatorCycle = f;
            setInterpolatorCycle(this.mInterpolatorType, f);
        } else if (b >= 10 && b <= 11) {
            float f2 = ByteBuffer.wrap(bArr).getFloat();
            this.mInterpolatorOvershot = f2;
            setInterpolatorBackEase(this.mInterpolatorType, f2);
        } else if (b >= 22 && b <= 23) {
            this.mInterpolatorAmplitude = ByteBuffer.wrap(bArr).getFloat();
            float f3 = ByteBuffer.wrap(bArr).getFloat();
            this.mInterpolatorPeriod = f3;
            setInterpolatorElastic(this.mInterpolatorType, this.mInterpolatorAmplitude, f3);
        } else {
            setInterpolator(b);
        }
        int i2 = sprInputStream.readInt();
        int i3 = sprInputStream.readInt();
        setStartDelay(i2);
        setDuration(i3);
        byte b2 = sprInputStream.readByte();
        if (b2 == 1) {
            setRepeatMode(2);
        } else if (b2 == 2) {
            setRepeatMode(1);
        } else {
            setRepeatMode(1);
        }
        setRepeatCount(sprInputStream.readInt());
    }

    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.mInterpolatorType);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte b = this.mInterpolatorType;
        if (b == 6) {
            new DataOutputStream(byteArrayOutputStream).writeFloat(this.mInterpolatorCycle);
        } else if (b >= 10 && b <= 11) {
            new DataOutputStream(byteArrayOutputStream).writeFloat(this.mInterpolatorOvershot);
        } else if (b >= 22 && b <= 23) {
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream2.writeFloat(this.mInterpolatorAmplitude);
            dataOutputStream2.writeFloat(this.mInterpolatorPeriod);
        }
        dataOutputStream.writeInt(byteArrayOutputStream.size());
        if (byteArrayOutputStream.size() > 0) {
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
        }
        dataOutputStream.writeInt((int) getStartDelay());
        dataOutputStream.writeInt((int) getDuration());
        int repeatMode = getRepeatMode();
        if (repeatMode != 1 && repeatMode == 2) {
            dataOutputStream.writeByte(1);
        } else {
            dataOutputStream.writeByte(2);
        }
        dataOutputStream.writeInt(getRepeatCount());
    }

    public int getSPRSize() {
        return this.mInterpolatorType == 6 ? 22 : 18;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    /* renamed from: clone */
    public SprAnimatorBase mo76clone() {
        return (SprAnimatorBase) super.mo76clone();
    }

    public boolean update(UpdateParameter updateParameter) {
        synchronized (this) {
            if (getCurrentPlayTime() <= 0 && !updateParameter.isLastFrame) {
                return false;
            }
            return updateValues(updateParameter);
        }
    }

    public void setInterpolator(byte b) {
        switch (b) {
            case 1:
                setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case 2:
            case 6:
            default:
                throw new RuntimeException("Unexpected interpolatorType : " + ((int) b));
            case 3:
                setInterpolator(new AnticipateInterpolator());
                break;
            case 4:
                setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case 5:
                setInterpolator(new BounceInterpolator());
                break;
            case 7:
                setInterpolator(new DecelerateInterpolator());
                break;
            case 8:
                setInterpolator(new LinearInterpolator());
                break;
            case 9:
                setInterpolator(new OvershootInterpolator());
                break;
            case 10:
                setInterpolator(new BackEaseIn());
                break;
            case 11:
                setInterpolator(new BackEaseOut());
                break;
            case 12:
                setInterpolator(new BackEaseInOut());
                break;
            case 13:
                setInterpolator(new BounceEaseIn());
                break;
            case 14:
                setInterpolator(new BounceEaseOut());
                break;
            case 15:
                setInterpolator(new BounceEaseInOut());
                break;
            case 16:
                setInterpolator(new CircEaseIn());
                break;
            case 17:
                setInterpolator(new CircEaseOut());
                break;
            case 18:
                setInterpolator(new CircEaseInOut());
                break;
            case 19:
                setInterpolator(new CubicEaseIn());
                break;
            case 20:
                setInterpolator(new CubicEaseOut());
                break;
            case 21:
                setInterpolator(new CubicEaseInOut());
                break;
            case 22:
                setInterpolator(new ElasticEaseIn());
                break;
            case 23:
                setInterpolator(new ElasticEaseOut());
                break;
            case 24:
                setInterpolator(new ElasticEaseInOut());
                break;
            case 25:
                setInterpolator(new ExpoEaseIn());
                break;
            case 26:
                setInterpolator(new ExpoEaseOut());
                break;
            case 27:
                setInterpolator(new ExpoEaseInOut());
                break;
            case 28:
                setInterpolator(new QuadEaseIn());
                break;
            case 29:
                setInterpolator(new QuadEaseOut());
                break;
            case 30:
                setInterpolator(new QuadEaseInOut());
                break;
            case 31:
                setInterpolator(new QuartEaseIn());
                break;
            case 32:
                setInterpolator(new QuartEaseOut());
                break;
            case 33:
                setInterpolator(new QuartEaseInOut());
                break;
            case 34:
                setInterpolator(new QuintEaseIn());
                break;
            case 35:
                setInterpolator(new QuintEaseOut());
                break;
            case 36:
                setInterpolator(new QuintEaseInOut());
                break;
            case 37:
                setInterpolator(new SineEaseIn());
                break;
            case 38:
                setInterpolator(new SineEaseOut());
                break;
            case 39:
                setInterpolator(new SineEaseInOut());
                break;
            case 40:
                setInterpolator(new QuintOut50());
                break;
            case 41:
                setInterpolator(new QuintOut80());
                break;
            case 42:
                setInterpolator(new SineIn33());
                break;
            case 43:
                setInterpolator(new SineInOut33());
                break;
            case 44:
                setInterpolator(new SineInOut50());
                break;
            case 45:
                setInterpolator(new SineInOut60());
                break;
            case 46:
                setInterpolator(new SineInOut70());
                break;
            case 47:
                setInterpolator(new SineInOut80());
                break;
            case 48:
                setInterpolator(new SineInOut90());
                break;
            case 49:
                setInterpolator(new SineOut33());
                break;
        }
        this.mInterpolatorType = b;
    }

    public void setInterpolatorCycle(byte b, float f) {
        if (b == 6) {
            setInterpolator(new CycleInterpolator(f));
            this.mInterpolatorType = b;
            this.mInterpolatorCycle = f;
        } else {
            throw new RuntimeException("Unexpected interpolatorType : " + ((int) b));
        }
    }

    public void setInterpolatorBackEase(byte b, float f) {
        switch (b) {
            case 10:
                setInterpolator(new BackEaseIn(f));
                break;
            case 11:
                setInterpolator(new BackEaseOut(f));
                break;
            case 12:
                setInterpolator(new BackEaseInOut(f));
                break;
            default:
                throw new RuntimeException("Unexpected interpolatorType : " + ((int) b));
        }
        this.mInterpolatorType = b;
        this.mInterpolatorOvershot = f;
    }

    public void setInterpolatorElastic(byte b, float f, float f2) {
        switch (b) {
            case 22:
                setInterpolator(new ElasticEaseIn(f, f2));
                break;
            case 23:
                setInterpolator(new ElasticEaseOut(f, f2));
                break;
            case 24:
                setInterpolator(new ElasticEaseInOut(f, f2));
                break;
            default:
                throw new RuntimeException("Unexpected interpolatorType : " + ((int) b));
        }
        this.mInterpolatorType = b;
        this.mInterpolatorAmplitude = f;
        this.mInterpolatorPeriod = f2;
    }
}
