package com.samsung.android.media.fmradio;

import android.os.Handler;
import android.os.Message;
import com.samsung.android.media.fmradio.internal.IFMEventListener;

/* loaded from: classes6.dex */
public class SemFmEventListener {
    private static final int EVENT_AF_RECEIVED = 14;
    private static final int EVENT_AF_STARTED = 13;
    private static final int EVENT_CHANNEL_FOUND = 1;
    private static final int EVENT_EAR_PHONE_CONNECT = 8;
    private static final int EVENT_EAR_PHONE_DISCONNECT = 9;
    private static final int EVENT_OFF = 6;
    private static final int EVENT_ON = 5;
    private static final int EVENT_PIECC_EVENT = 18;
    private static final int EVENT_RDS_DISABLED = 12;
    private static final int EVENT_RDS_ENABLED = 11;
    private static final int EVENT_RDS_EVENT = 10;
    private static final int EVENT_REC_FINISH = 17;
    private static final int EVENT_RTPLUS_EVENT = 16;
    private static final int EVENT_SCAN_FINISHED = 3;
    private static final int EVENT_SCAN_STARTED = 2;
    private static final int EVENT_SCAN_STOPPED = 4;
    private static final int EVENT_TUNE = 7;
    private static final int EVENT_VOLUME_LOCK = 15;
    IFMEventListener callback = new IFMEventListener.Stub() { // from class: com.samsung.android.media.fmradio.SemFmEventListener.1
        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onChannelFound(long j) {
            Message.obtain(SemFmEventListener.this.mHandler, 1, 0, 0, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStarted() {
            Message.obtain(SemFmEventListener.this.mHandler, 2, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStopped(long[] jArr) {
            Message.obtain(SemFmEventListener.this.mHandler, 4, 0, 0, jArr).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanFinished(long[] jArr) {
            Message.obtain(SemFmEventListener.this.mHandler, 3, 0, 0, jArr).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioEnabled() {
            Message.obtain(SemFmEventListener.this.mHandler, 5, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDisabled(int i) {
            Message.obtain(SemFmEventListener.this.mHandler, 6, 0, 0, Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onTuned(long j) {
            Message.obtain(SemFmEventListener.this.mHandler, 7, 0, 0, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onHeadsetConnected() {
            Message.obtain(SemFmEventListener.this.mHandler, 8, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onHeadsetDisconnected() {
            Message.obtain(SemFmEventListener.this.mHandler, 9, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemReceived(long j, String str, String str2) {
            Message.obtain(SemFmEventListener.this.mHandler, 10, 0, 0, new Object[]{Long.valueOf(j), str, str2}).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioTextPlusReceived(int i, int i2, int i3, int i4, int i5, int i6) {
            Message.obtain(SemFmEventListener.this.mHandler, 16, 0, 0, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)}).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onProgrammeIdentificationExtendedCountryCodesReceived(int i, int i2) {
            Message.obtain(SemFmEventListener.this.mHandler, 18, 0, 0, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemEnabled() {
            Message.obtain(SemFmEventListener.this.mHandler, 11, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDataSystemDisabled() {
            Message.obtain(SemFmEventListener.this.mHandler, 12, 0, 0, null).sendToTarget();
            SemFmEventListener.this.mHandler.removeMessages(10);
            SemFmEventListener.this.mHandler.removeMessages(16);
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onAlternateFrequencyStarted() {
            Message.obtain(SemFmEventListener.this.mHandler, 13, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onAlternateFrequencyReceived(long j) {
            Message.obtain(SemFmEventListener.this.mHandler, 14, 0, 0, Long.valueOf(j)).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onVolumeLocked() {
            Message.obtain(SemFmEventListener.this.mHandler, 15, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRecordingFinished() {
            Message.obtain(SemFmEventListener.this.mHandler, 17, 0, 0, null).sendToTarget();
        }
    };
    Handler mHandler = new Handler() { // from class: com.samsung.android.media.fmradio.SemFmEventListener.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SemFmEventListener.this.onChannelFound(((Long) message.obj).longValue());
                    break;
                case 2:
                    SemFmEventListener.this.onScanStarted();
                    break;
                case 3:
                    SemFmEventListener.this.onScanFinished((long[]) message.obj);
                    break;
                case 4:
                    SemFmEventListener.this.onScanStopped((long[]) message.obj);
                    break;
                case 5:
                    SemFmEventListener.this.onRadioEnabled();
                    break;
                case 6:
                    SemFmEventListener.this.onRadioDisabled(((Integer) message.obj).intValue());
                    break;
                case 7:
                    SemFmEventListener.this.onTuned(((Long) message.obj).longValue());
                    break;
                case 8:
                    SemFmEventListener.this.onHeadsetConnected();
                    break;
                case 9:
                    SemFmEventListener.this.onHeadsetDisconnected();
                    break;
                case 10:
                    Object[] objArr = (Object[]) message.obj;
                    SemFmEventListener.this.onRadioDataSystemReceived(((Long) objArr[0]).longValue(), (String) objArr[1], (String) objArr[2]);
                    break;
                case 11:
                    SemFmEventListener.this.onRadioDataSystemEnabled();
                    break;
                case 12:
                    SemFmEventListener.this.onRadioDataSystemDisabled();
                    break;
                case 13:
                    SemFmEventListener.this.onAlternateFrequencyStarted();
                    break;
                case 14:
                    Long l = (Long) message.obj;
                    SemFmEventListener.this.onAlternateFrequencyReceived(l.longValue());
                    SemFmEventListener.this.onTuned(l.longValue());
                    break;
                case 15:
                    SemFmEventListener.this.onVolumeLocked();
                    break;
                case 16:
                    Object[] objArr2 = (Object[]) message.obj;
                    SemFmEventListener.this.onRadioTextPlusReceived(((Integer) objArr2[0]).intValue(), ((Integer) objArr2[1]).intValue(), ((Integer) objArr2[2]).intValue(), ((Integer) objArr2[3]).intValue(), ((Integer) objArr2[4]).intValue(), ((Integer) objArr2[5]).intValue());
                    break;
                case 17:
                    SemFmEventListener.this.onRecordingFinished();
                    break;
                case 18:
                    Object[] objArr3 = (Object[]) message.obj;
                    SemFmEventListener.this.onProgrammeIdentificationExtendedCountryCodesReceived(((Integer) objArr3[0]).intValue(), ((Integer) objArr3[1]).intValue());
                    break;
            }
        }
    };

    public void onAlternateFrequencyReceived(long j) {
    }

    public void onAlternateFrequencyStarted() {
    }

    public void onChannelFound(long j) {
    }

    public void onHeadsetConnected() {
    }

    public void onHeadsetDisconnected() {
    }

    public void onProgrammeIdentificationExtendedCountryCodesReceived(int i, int i2) {
    }

    public void onRadioDataSystemDisabled() {
    }

    public void onRadioDataSystemEnabled() {
    }

    public void onRadioDataSystemReceived(long j, String str, String str2) {
    }

    public void onRadioDisabled(int i) {
    }

    public void onRadioEnabled() {
    }

    public void onRadioTextPlusReceived(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    public void onRecordingFinished() {
    }

    public void onScanFinished(long[] jArr) {
    }

    public void onScanStarted() {
    }

    public void onScanStopped(long[] jArr) {
    }

    public void onTuned(long j) {
    }

    public void onVolumeLocked() {
    }
}
