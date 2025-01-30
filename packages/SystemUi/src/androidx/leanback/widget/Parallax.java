package androidx.leanback.widget;

import android.util.Property;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class Parallax {
    public final List mEffects;
    public final float[] mFloatValues;
    public final List mProperties;
    public final int[] mValues;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IntProperty extends Property {
        public final int mIndex;

        public IntProperty(String str, int i) {
            super(Integer.class, str);
            this.mIndex = i;
        }

        @Override // android.util.Property
        public final Object get(Object obj) {
            return Integer.valueOf(((Parallax) obj).mValues[this.mIndex]);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            Parallax parallax = (Parallax) obj;
            int i = this.mIndex;
            int intValue = ((Integer) obj2).intValue();
            if (i >= ((ArrayList) parallax.mProperties).size()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            parallax.mValues[i] = intValue;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IntPropertyMarkerValue extends PropertyMarkerValue {
        public final float mFactionOfMax;
        public final int mValue;

        public IntPropertyMarkerValue(IntProperty intProperty, int i) {
            this(intProperty, i, 0.0f);
        }

        public final int getMarkerValue(Parallax parallax) {
            float f = this.mFactionOfMax;
            int i = this.mValue;
            return f == 0.0f ? i : i + Math.round(parallax.getMaxValue() * f);
        }

        public IntPropertyMarkerValue(IntProperty intProperty, int i, float f) {
            super(intProperty);
            this.mValue = i;
            this.mFactionOfMax = f;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PropertyMarkerValue {
        public final Object mProperty;

        public PropertyMarkerValue(Object obj) {
            this.mProperty = obj;
        }
    }

    public Parallax() {
        ArrayList arrayList = new ArrayList();
        this.mProperties = arrayList;
        Collections.unmodifiableList(arrayList);
        this.mValues = new int[4];
        this.mFloatValues = new float[4];
        this.mEffects = new ArrayList(4);
    }

    public abstract float getMaxValue();
}
