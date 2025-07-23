package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Key {
    public HashMap mCustomConstraints;
    public int mFramePosition = -1;
    public int mTargetId = -1;
    public String mTargetString = null;

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract Key mo886clone();

    public Key copy(Key key) {
        this.mFramePosition = key.mFramePosition;
        this.mTargetId = key.mTargetId;
        this.mTargetString = key.mTargetString;
        this.mCustomConstraints = key.mCustomConstraints;
        return this;
    }

    public abstract void getAttributeNames(HashSet hashSet);

    public abstract void load(Context context, AttributeSet attributeSet);

    public void setInterpolation(HashMap hashMap) {
    }
}
