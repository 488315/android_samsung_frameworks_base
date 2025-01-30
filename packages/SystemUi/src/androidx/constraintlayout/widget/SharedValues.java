package androidx.constraintlayout.widget;

import android.util.SparseIntArray;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SharedValues {
    public final HashMap mValuesListeners;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SharedValuesListener {
    }

    public SharedValues() {
        new SparseIntArray();
        this.mValuesListeners = new HashMap();
    }

    public final void addListener(int i, SharedValuesListener sharedValuesListener) {
        HashMap hashMap = this.mValuesListeners;
        HashSet hashSet = (HashSet) hashMap.get(Integer.valueOf(i));
        if (hashSet == null) {
            hashSet = new HashSet();
            hashMap.put(Integer.valueOf(i), hashSet);
        }
        hashSet.add(new WeakReference(sharedValuesListener));
    }
}
