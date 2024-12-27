package com.android.systemui.volume.store;

import android.os.Handler;
import android.os.Looper;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumeUnsubscriber;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StoreInteractor {
    public VolumeUnsubscriber disposable;
    public final Lazy mainThreadHandler$delegate;
    public final VolumeObserver stateObserver;
    public VolumePanelStore store;

    public StoreInteractor() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final void dispose() {
        VolumeUnsubscriber volumeUnsubscriber = this.disposable;
        if (volumeUnsubscriber != null) {
            volumeUnsubscriber.dispose();
        }
        this.disposable = null;
    }

    public final void observeStore() {
        VolumeObserver volumeObserver = this.stateObserver;
        if (volumeObserver != null) {
            VolumePanelStore volumePanelStore = this.store;
            this.disposable = (VolumeUnsubscriber) (volumePanelStore != null ? volumePanelStore.subscribe(volumeObserver) : null);
        }
    }

    public final void sendAction(final VolumePanelAction volumePanelAction, boolean z) {
        final VolumePanelStore volumePanelStore = this.store;
        if (volumePanelStore == null) {
            return;
        }
        if (z) {
            ((Handler) this.mainThreadHandler$delegate.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.store.StoreInteractor$sendAction$1
                @Override // java.lang.Runnable
                public final void run() {
                    VolumePanelStore.this.onChanged(volumePanelAction);
                }
            });
        } else {
            volumePanelStore.onChanged(volumePanelAction);
        }
    }

    public StoreInteractor(VolumeObserver<VolumePanelState> volumeObserver, VolumePanelStore volumePanelStore) {
        this.stateObserver = volumeObserver;
        this.store = volumePanelStore;
        this.mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.store.StoreInteractor$mainThreadHandler$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new Handler(Looper.getMainLooper());
            }
        });
    }

    public /* synthetic */ StoreInteractor(VolumeObserver volumeObserver, VolumePanelStore volumePanelStore, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : volumeObserver, (i & 2) != 0 ? null : volumePanelStore);
    }
}
