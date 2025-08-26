package android.hardware.radio;

import android.graphics.Bitmap;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.hardware.radio.Flags;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
final class TunerAdapter extends RadioTuner {
    private static final String TAG = "BroadcastRadio.TunerAdapter";
    private int mBand;
    private final TunerCallbackAdapter mCallback;
    private boolean mIsClosed;
    private Map<String, String> mLegacyListFilter;
    private ProgramList mLegacyListProxy;
    private final Object mLock = new Object();
    private final ITuner mTuner;

    TunerAdapter(ITuner iTuner, TunerCallbackAdapter tunerCallbackAdapter, int i) {
        this.mTuner = (ITuner) Objects.requireNonNull(iTuner, "Tuner cannot be null");
        this.mCallback = (TunerCallbackAdapter) Objects.requireNonNull(tunerCallbackAdapter, "Callback cannot be null");
        this.mBand = i;
    }

    @Override // android.hardware.radio.RadioTuner
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                Log.v(TAG, "Tuner is already closed");
                return;
            }
            this.mIsClosed = true;
            ProgramList programList = this.mLegacyListProxy;
            if (programList != null) {
                programList.close();
                this.mLegacyListProxy = null;
            }
            this.mCallback.close();
            try {
                this.mTuner.close();
            } catch (RemoteException e) {
                Log.e(TAG, "Exception trying to close tuner", e);
            }
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int setConfiguration(RadioManager.BandConfig bandConfig) {
        if (bandConfig == null) {
            return -22;
        }
        try {
            this.mTuner.setConfiguration(bandConfig);
            synchronized (this.mLock) {
                this.mBand = bandConfig.getType();
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "Can't set configuration", e2);
            return -22;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int getConfiguration(RadioManager.BandConfig[] bandConfigArr) {
        if (bandConfigArr == null || bandConfigArr.length != 1) {
            throw new IllegalArgumentException("The argument must be an array of length 1");
        }
        try {
            bandConfigArr[0] = this.mTuner.getConfiguration();
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int setMute(boolean z) {
        try {
            this.mTuner.setMuted(z);
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Can't set muted", e2);
            return Integer.MIN_VALUE;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean getMute() {
        try {
            return this.mTuner.isMuted();
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return true;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int step(int i, boolean z) {
        try {
            ITuner iTuner = this.mTuner;
            boolean z2 = true;
            if (i != 1) {
                z2 = false;
            }
            iTuner.step(z2, z);
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Can't step", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int scan(int i, boolean z) {
        try {
            ITuner iTuner = this.mTuner;
            boolean z2 = true;
            if (i != 1) {
                z2 = false;
            }
            iTuner.seek(z2, z);
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Can't scan", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int seek(int i, boolean z) {
        try {
            ITuner iTuner = this.mTuner;
            boolean z2 = true;
            if (i != 1) {
                z2 = false;
            }
            iTuner.seek(z2, z);
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Can't seek", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int tune(int i, int i2) {
        int i3;
        try {
            synchronized (this.mLock) {
                i3 = this.mBand;
            }
            this.mTuner.tune(ProgramSelector.createAmFmSelector(i3, i, i2));
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "Can't tune", e2);
            return -22;
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Can't tune", e3);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void tune(ProgramSelector programSelector) {
        try {
            this.mTuner.tune(programSelector);
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int cancel() {
        try {
            this.mTuner.cancel();
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "Service died", e);
            return -32;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Can't cancel", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void cancelAnnouncement() {
        try {
            this.mTuner.cancelAnnouncement();
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int getProgramInformation(RadioManager.ProgramInfo[] programInfoArr) {
        if (programInfoArr == null || programInfoArr.length != 1) {
            Log.e(TAG, "The argument must be an array of length 1");
            return -22;
        }
        RadioManager.ProgramInfo currentProgramInformation = this.mCallback.getCurrentProgramInformation();
        if (currentProgramInformation == null) {
            Log.w(TAG, "Didn't get program info yet");
            return -38;
        }
        programInfoArr[0] = currentProgramInformation;
        return 0;
    }

    @Override // android.hardware.radio.RadioTuner
    public Bitmap getMetadataImage(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Invalid metadata image id 0");
        }
        try {
            Bitmap image = this.mTuner.getImage(i);
            if (image != null) {
                return image;
            }
            throw new IllegalArgumentException("Metadata image with id " + i + " is not available");
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean startBackgroundScan() {
        try {
            return this.mTuner.startBackgroundScan();
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public List<RadioManager.ProgramInfo> getProgramList(Map<String, String> map) {
        synchronized (this.mLock) {
            if (this.mLegacyListProxy == null || !Objects.equals(this.mLegacyListFilter, map)) {
                Log.i(TAG, "Program list filter has changed, requesting new list");
                this.mLegacyListProxy = new ProgramList();
                this.mLegacyListFilter = map;
                this.mCallback.clearLastCompleteList();
                this.mCallback.setProgramListObserver(this.mLegacyListProxy, new ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerAdapter$$ExternalSyntheticLambda0
                    @Override // android.hardware.radio.ProgramList.OnCloseListener
                    public final void onClose() {
                        Log.i(TunerAdapter.TAG, "Empty closeListener in programListObserver");
                    }
                });
            }
        }
        try {
            this.mTuner.startProgramListUpdates(new ProgramList.Filter(map));
            List<RadioManager.ProgramInfo> lastCompleteList = this.mCallback.getLastCompleteList();
            if (lastCompleteList != null) {
                return lastCompleteList;
            }
            throw new IllegalStateException("Program list is not ready yet");
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public ProgramList getDynamicProgramList(ProgramList.Filter filter) {
        synchronized (this.mLock) {
            ProgramList programList = this.mLegacyListProxy;
            if (programList != null) {
                programList.close();
                this.mLegacyListProxy = null;
            }
            this.mLegacyListFilter = null;
        }
        ProgramList programList2 = new ProgramList();
        this.mCallback.setProgramListObserver(programList2, new ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerAdapter$$ExternalSyntheticLambda1
            @Override // android.hardware.radio.ProgramList.OnCloseListener
            public final void onClose() {
                this.f$0.lambda$getDynamicProgramList$1();
            }
        });
        try {
            this.mTuner.startProgramListUpdates(filter);
            return programList2;
        } catch (RemoteException e) {
            this.mCallback.setProgramListObserver(null, new ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerAdapter$$ExternalSyntheticLambda2
                @Override // android.hardware.radio.ProgramList.OnCloseListener
                public final void onClose() {
                    Log.i(TunerAdapter.TAG, "Empty closeListener in programListObserver");
                }
            });
            throw new RuntimeException("Service died", e);
        } catch (UnsupportedOperationException unused) {
            Log.i(TAG, "Program list is not supported with this hardware");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDynamicProgramList$1() {
        try {
            this.mTuner.stopProgramListUpdates();
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't stop program list updates", e);
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Tuner may already be closed", e2);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isAnalogForced() {
        try {
            return isConfigFlagSet(2);
        } catch (UnsupportedOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void setAnalogForced(boolean z) {
        try {
            setConfigFlag(2, z);
        } catch (UnsupportedOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isConfigFlagSupported(int i) {
        try {
            return this.mTuner.isConfigFlagSupported(i);
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isConfigFlagSet(int i) {
        try {
            return this.mTuner.isConfigFlagSet(convertForceAnalogConfigFlag(i));
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void setConfigFlag(int i, boolean z) {
        try {
            this.mTuner.setConfigFlag(convertForceAnalogConfigFlag(i), z);
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public Map<String, String> setParameters(Map<String, String> map) {
        try {
            return this.mTuner.setParameters((Map) Objects.requireNonNull(map, "Parameters cannot be null"));
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public Map<String, String> getParameters(List<String> list) {
        try {
            return this.mTuner.getParameters((List) Objects.requireNonNull(list, "Keys cannot be null"));
        } catch (RemoteException e) {
            throw new RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isAntennaConnected() {
        return this.mCallback.isAntennaConnected();
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean hasControl() {
        try {
            return !this.mTuner.isClosed();
        } catch (RemoteException unused) {
            return false;
        }
    }

    private int convertForceAnalogConfigFlag(int i) throws RemoteException {
        if (Flags.hdRadioImproved() && i == 2 && this.mTuner.isConfigFlagSupported(10)) {
            return 10;
        }
        return i;
    }
}
