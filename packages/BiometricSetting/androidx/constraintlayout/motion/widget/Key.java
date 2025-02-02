package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public abstract class Key {
    HashMap<String, ConstraintAttribute> mCustomConstraints;
    int mFramePosition = -1;
    int mTargetId = -1;
    String mTargetString = null;

    static float toFloat(Object obj) {
        return obj instanceof Float ? ((Float) obj).floatValue() : Float.parseFloat(obj.toString());
    }

    public abstract void addValues(HashMap<String, ViewSpline> hashMap);

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract Key mo15clone();

    public Key copy(Key key) {
        this.mFramePosition = key.mFramePosition;
        this.mTargetId = key.mTargetId;
        this.mTargetString = key.mTargetString;
        this.mCustomConstraints = key.mCustomConstraints;
        return this;
    }

    abstract void getAttributeNames(HashSet<String> hashSet);

    abstract void load(Context context, AttributeSet attributeSet);

    public final void setFramePosition(int i) {
        this.mFramePosition = i;
    }

    public void setInterpolation(HashMap<String, Integer> hashMap) {
    }
}
