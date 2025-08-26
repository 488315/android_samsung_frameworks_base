package com.samsung.systemui.splugins.volume;

/* loaded from: classes4.dex */
public interface VolumeObservable<T> {
    void dispatch(T t, boolean z);

    VolumeDisposable subscribe(VolumeObserver<T> volumeObserver);
}
