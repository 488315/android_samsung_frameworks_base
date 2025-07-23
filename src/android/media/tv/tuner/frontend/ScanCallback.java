package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public interface ScanCallback {
    void onAnalogSifStandardReported(int i);

    void onAtsc3PlpInfosReported(Atsc3PlpInfo[] atsc3PlpInfoArr);

    default void onDvbcAnnexReported(int i) {
    }

    void onDvbsStandardReported(int i);

    default void onDvbtCellIdsReported(int[] iArr) {
    }

    void onDvbtStandardReported(int i);

    @Deprecated
    void onFrequenciesReported(int[] iArr);

    void onGroupIdsReported(int[] iArr);

    void onHierarchyReported(int i);

    void onInputStreamIdsReported(int[] iArr);

    void onLocked();

    default void onModulationReported(int i) {
    }

    void onPlpIdsReported(int[] iArr);

    default void onPriorityReported(boolean z) {
    }

    void onProgress(int i);

    void onScanStopped();

    void onSignalTypeReported(int i);

    void onSymbolRatesReported(int[] iArr);

    default void onUnlocked() {
    }

    default void onFrequenciesLongReported(long[] jArr) {
        int[] iArr = new int[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            iArr[i] = (int) jArr[i];
        }
        onFrequenciesReported(iArr);
    }
}
