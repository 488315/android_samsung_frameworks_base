package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Key {
    public HashMap mCustomConstraints;
    public int mFramePosition = -1;
    public int mTargetId = -1;
    public String mTargetString = null;

    public static float toFloat(Object obj) {
        return obj instanceof Float ? ((Float) obj).floatValue() : Float.parseFloat(obj.toString());
    }

    public abstract void addValues(HashMap hashMap);

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract Key mo303clone();

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
