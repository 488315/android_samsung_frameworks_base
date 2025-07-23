package android.hardware.radio;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioTuner;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
final class TunerCallbackAdapter extends ITunerCallback.Stub {
    private static final String TAG = "BroadcastRadio.TunerCallbackAdapter";
    private final RadioTuner.Callback mCallback;
    RadioManager.ProgramInfo mCurrentProgramInfo;
    private boolean mDelayedCompleteCallback;
    private final Handler mHandler;
    List<RadioManager.ProgramInfo> mLastCompleteList;
    ProgramList mProgramList;
    private final Object mLock = new Object();
    boolean mIsAntennaConnected = true;

    TunerCallbackAdapter(RadioTuner.Callback callback, Handler handler) {
        this.mCallback = (RadioTuner.Callback) Objects.requireNonNull(callback, "Callback cannot be null");
        if (handler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        } else {
            this.mHandler = handler;
        }
    }

    void close() {
        synchronized (this.mLock) {
            ProgramList programList = this.mProgramList;
            if (programList == null) {
                return;
            }
            programList.close();
        }
    }

    void setProgramListObserver(final ProgramList programList, final ProgramList.OnCloseListener onCloseListener) {
        ProgramList programList2;
        Objects.requireNonNull(onCloseListener, "CloseListener cannot be null");
        synchronized (this.mLock) {
            programList2 = this.mProgramList;
            this.mProgramList = programList;
        }
        if (programList2 != null) {
            Log.w(TAG, "Previous program list observer wasn't properly closed, closing it...");
            programList2.close();
        }
        if (programList == null) {
            return;
        }
        programList.setOnCloseListener(new ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda4
            @Override // android.hardware.radio.ProgramList.OnCloseListener
            public final void onClose() {
                TunerCallbackAdapter.this.lambda$setProgramListObserver$0(programList, onCloseListener);
            }
        });
        programList.addOnCompleteListener(new ProgramList.OnCompleteListener() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda5
            @Override // android.hardware.radio.ProgramList.OnCompleteListener
            public final void onComplete() {
                TunerCallbackAdapter.this.lambda$setProgramListObserver$1(programList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgramListObserver$0(ProgramList programList, ProgramList.OnCloseListener onCloseListener) {
        synchronized (this.mLock) {
            if (this.mProgramList != programList) {
                return;
            }
            this.mProgramList = null;
            this.mLastCompleteList = null;
            onCloseListener.onClose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgramListObserver$1(ProgramList programList) {
        synchronized (this.mLock) {
            if (this.mProgramList != programList) {
                return;
            }
            this.mLastCompleteList = programList.toList();
            if (this.mDelayedCompleteCallback) {
                Log.d(TAG, "Sending delayed onBackgroundScanComplete callback");
                sendBackgroundScanCompleteLocked();
            }
        }
    }

    List<RadioManager.ProgramInfo> getLastCompleteList() {
        List<RadioManager.ProgramInfo> list;
        synchronized (this.mLock) {
            list = this.mLastCompleteList;
        }
        return list;
    }

    void clearLastCompleteList() {
        synchronized (this.mLock) {
            this.mLastCompleteList = null;
        }
    }

    RadioManager.ProgramInfo getCurrentProgramInformation() {
        RadioManager.ProgramInfo programInfo;
        synchronized (this.mLock) {
            programInfo = this.mCurrentProgramInfo;
        }
        return programInfo;
    }

    boolean isAntennaConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsAntennaConnected;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$2(int i) {
        this.mCallback.onError(i);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onError(final int i) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onError$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTuneFailed$3(int i, ProgramSelector programSelector) {
        this.mCallback.onTuneFailed(i, programSelector);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0031, code lost:
    
        if (r3 != 7) goto L28;
     */
    @Override // android.hardware.radio.ITunerCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onTuneFailed(final int r3, final android.hardware.radio.ProgramSelector r4) {
        /*
            r2 = this;
            android.os.Handler r0 = r2.mHandler
            android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda0 r1 = new android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda0
            r1.<init>()
            r0.post(r1)
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = 3
            if (r3 == r4) goto L38
            r4 = -38
            if (r3 == r4) goto L38
            r4 = -32
            r1 = 1
            if (r3 == r4) goto L36
            r4 = -22
            if (r3 == r4) goto L38
            r4 = -19
            if (r3 == r4) goto L38
            r4 = -1
            if (r3 == r4) goto L36
            if (r3 == r1) goto L38
            r4 = 2
            if (r3 == r4) goto L38
            if (r3 == r0) goto L38
            r1 = 4
            if (r3 == r1) goto L38
            r1 = 6
            if (r3 == r1) goto L34
            r4 = 7
            if (r3 == r4) goto L38
            goto L50
        L34:
            r0 = r4
            goto L50
        L36:
            r0 = r1
            goto L50
        L38:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r1 = "Got an error with no mapping to the legacy API ("
            r4.<init>(r1)
            r4.append(r3)
            java.lang.String r3 = "), doing a best-effort conversion to ERROR_SCAN_TIMEOUT"
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.String r4 = "BroadcastRadio.TunerCallbackAdapter"
            android.util.Log.i(r4, r3)
        L50:
            android.os.Handler r3 = r2.mHandler
            android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda1 r4 = new android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda1
            r4.<init>()
            r3.post(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.TunerCallbackAdapter.onTuneFailed(int, android.hardware.radio.ProgramSelector):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTuneFailed$4(int i) {
        this.mCallback.onError(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigurationChanged$5(RadioManager.BandConfig bandConfig) {
        this.mCallback.onConfigurationChanged(bandConfig);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onConfigurationChanged(final RadioManager.BandConfig bandConfig) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onConfigurationChanged$5(bandConfig);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onCurrentProgramInfoChanged(final RadioManager.ProgramInfo programInfo) {
        if (programInfo == null) {
            Log.e(TAG, "ProgramInfo must not be null");
            return;
        }
        synchronized (this.mLock) {
            this.mCurrentProgramInfo = programInfo;
        }
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onCurrentProgramInfoChanged$6(programInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCurrentProgramInfoChanged$6(RadioManager.ProgramInfo programInfo) {
        this.mCallback.onProgramInfoChanged(programInfo);
        RadioMetadata metadata = programInfo.getMetadata();
        if (metadata != null) {
            this.mCallback.onMetadataChanged(metadata);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTrafficAnnouncement$7(boolean z) {
        this.mCallback.onTrafficAnnouncement(z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onTrafficAnnouncement(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onTrafficAnnouncement$7(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEmergencyAnnouncement$8(boolean z) {
        this.mCallback.onEmergencyAnnouncement(z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onEmergencyAnnouncement(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onEmergencyAnnouncement$8(z);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onAntennaState(final boolean z) {
        synchronized (this.mLock) {
            this.mIsAntennaConnected = z;
        }
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onAntennaState$9(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAntennaState$9(boolean z) {
        this.mCallback.onAntennaState(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackgroundScanAvailabilityChange$10(boolean z) {
        this.mCallback.onBackgroundScanAvailabilityChange(z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onBackgroundScanAvailabilityChange(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onBackgroundScanAvailabilityChange$10(z);
            }
        });
    }

    private void sendBackgroundScanCompleteLocked() {
        this.mDelayedCompleteCallback = false;
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$sendBackgroundScanCompleteLocked$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendBackgroundScanCompleteLocked$11() {
        this.mCallback.onBackgroundScanComplete();
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onBackgroundScanComplete() {
        synchronized (this.mLock) {
            if (this.mLastCompleteList == null) {
                Log.i(TAG, "Got onBackgroundScanComplete callback, but the program list didn't get through yet. Delaying it...");
                this.mDelayedCompleteCallback = true;
            } else {
                sendBackgroundScanCompleteLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgramListChanged$12() {
        this.mCallback.onProgramListChanged();
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onProgramListChanged() {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onProgramListChanged$12();
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onProgramListUpdated(final ProgramList.Chunk chunk) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onProgramListUpdated$13(chunk);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgramListUpdated$13(ProgramList.Chunk chunk) {
        synchronized (this.mLock) {
            ProgramList programList = this.mProgramList;
            if (programList == null) {
                return;
            }
            programList.apply((ProgramList.Chunk) Objects.requireNonNull(chunk, "Chunk cannot be null"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigFlagUpdated$14(int i, boolean z) {
        this.mCallback.onConfigFlagUpdated(i, z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onConfigFlagUpdated(final int i, final boolean z) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onConfigFlagUpdated$14(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onParametersUpdated$15(Map map) {
        this.mCallback.onParametersUpdated(map);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onParametersUpdated(final Map<String, String> map) {
        this.mHandler.post(new Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                TunerCallbackAdapter.this.lambda$onParametersUpdated$15(map);
            }
        });
    }
}
