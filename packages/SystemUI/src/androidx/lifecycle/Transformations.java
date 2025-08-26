package androidx.lifecycle;

import androidx.lifecycle.MediatorLiveData;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class Transformations {
    public static final MediatorLiveData map(LiveData liveData, final Function1 function1) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        if (liveData.isInitialized()) {
            mediatorLiveData.setValue(function1.mo781invoke(liveData.getValue()));
        }
        Transformations$sam$androidx_lifecycle_Observer$0 transformations$sam$androidx_lifecycle_Observer$0 = new Transformations$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: androidx.lifecycle.Transformations.map.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                mediatorLiveData.setValue(function1.mo781invoke(obj));
                return Unit.INSTANCE;
            }
        });
        MediatorLiveData.Source source = new MediatorLiveData.Source(liveData, transformations$sam$androidx_lifecycle_Observer$0);
        MediatorLiveData.Source source2 = (MediatorLiveData.Source) mediatorLiveData.mSources.putIfAbsent(liveData, source);
        if (source2 != null && source2.mObserver != transformations$sam$androidx_lifecycle_Observer$0) {
            throw new IllegalArgumentException("This source was already added with the different observer");
        }
        if (source2 == null && mediatorLiveData.hasActiveObservers()) {
            source.mLiveData.observeForever(source);
        }
        return mediatorLiveData;
    }
}
