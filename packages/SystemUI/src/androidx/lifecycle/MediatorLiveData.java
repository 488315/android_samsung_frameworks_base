package androidx.lifecycle;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class MediatorLiveData extends MutableLiveData {
    public final SafeIterableMap mSources;

    public class Source implements Observer {
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
            LiveData liveData = this.mLiveData;
            if (i != liveData.getVersion()) {
                this.mVersion = liveData.getVersion();
                this.mObserver.onChanged(obj);
            }
        }
    }

    public MediatorLiveData() {
        this.mSources = new SafeIterableMap();
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

    public MediatorLiveData(Object obj) {
        super(obj);
        this.mSources = new SafeIterableMap();
    }
}
