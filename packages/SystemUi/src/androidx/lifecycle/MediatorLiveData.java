package androidx.lifecycle;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediatorLiveData extends MutableLiveData {
    public final SafeIterableMap mSources = new SafeIterableMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Source implements Observer {
        public final LiveData mLiveData;
        public final Observer mObserver;
        public int mVersion = -1;

        public Source(LiveData liveData, Observer observer) {
            this.mLiveData = liveData;
            this.mObserver = observer;
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            int i = this.mVersion;
            int i2 = this.mLiveData.mVersion;
            if (i != i2) {
                this.mVersion = i2;
                this.mObserver.onChanged(obj);
            }
        }
    }

    @Override // androidx.lifecycle.LiveData
    public final void onActive() {
        Iterator it = this.mSources.iterator();
        while (true) {
            SafeIterableMap.ListIterator listIterator = (SafeIterableMap.ListIterator) it;
            if (!listIterator.hasNext()) {
                return;
            }
            Source source = (Source) ((Map.Entry) listIterator.next()).getValue();
            source.mLiveData.observeForever(source);
        }
    }

    @Override // androidx.lifecycle.LiveData
    public final void onInactive() {
        Iterator it = this.mSources.iterator();
        while (true) {
            SafeIterableMap.ListIterator listIterator = (SafeIterableMap.ListIterator) it;
            if (!listIterator.hasNext()) {
                return;
            }
            Source source = (Source) ((Map.Entry) listIterator.next()).getValue();
            source.mLiveData.removeObserver(source);
        }
    }
}
