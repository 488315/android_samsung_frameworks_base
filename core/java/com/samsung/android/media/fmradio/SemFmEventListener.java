package com.samsung.android.media.fmradio;

import android.os.Handler;
import android.os.Message;
import com.samsung.android.media.fmradio.internal.IFMEventListener;

/* loaded from: classes5.dex */
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
  IFMEventListener callback =
      new IFMEventListener
          .Stub() { // from class: com.samsung.android.media.fmradio.SemFmEventListener.1
        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onChannelFound(long freq) {
          Message.obtain(SemFmEventListener.this.mHandler, 1, 0, 0, Long.valueOf(freq))
              .sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStarted() {
          Message.obtain(SemFmEventListener.this.mHandler, 2, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanStopped(long[] freqArry) {
          Message.obtain(SemFmEventListener.this.mHandler, 4, 0, 0, freqArry).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onScanFinished(long[] freqArry) {
          Message.obtain(SemFmEventListener.this.mHandler, 3, 0, 0, freqArry).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioEnabled() {
          Message.obtain(SemFmEventListener.this.mHandler, 5, 0, 0, null).sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioDisabled(int reasonCode) {
          Message.obtain(SemFmEventListener.this.mHandler, 6, 0, 0, Integer.valueOf(reasonCode))
              .sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onTuned(long freq) {
          Message.obtain(SemFmEventListener.this.mHandler, 7, 0, 0, Long.valueOf(freq))
              .sendToTarget();
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
        public void onRadioDataSystemReceived(long freq, String channelName, String radioText) {
          Message.obtain(
                  SemFmEventListener.this.mHandler,
                  10,
                  0,
                  0,
                  new Object[] {Long.valueOf(freq), channelName, radioText})
              .sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onRadioTextPlusReceived(
            int contentType1,
            int startPos1,
            int additionalLen1,
            int contentType2,
            int startPos2,
            int additionalLen2) {
          Message.obtain(
                  SemFmEventListener.this.mHandler,
                  16,
                  0,
                  0,
                  new Object[] {
                    Integer.valueOf(contentType1),
                    Integer.valueOf(startPos1),
                    Integer.valueOf(additionalLen1),
                    Integer.valueOf(contentType2),
                    Integer.valueOf(startPos2),
                    Integer.valueOf(additionalLen2)
                  })
              .sendToTarget();
        }

        @Override // com.samsung.android.media.fmradio.internal.IFMEventListener
        public void onProgrammeIdentificationExtendedCountryCodesReceived(int pi, int ecc) {
          Message.obtain(
                  SemFmEventListener.this.mHandler,
                  18,
                  0,
                  0,
                  new Object[] {Integer.valueOf(pi), Integer.valueOf(ecc)})
              .sendToTarget();
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
        public void onAlternateFrequencyReceived(long freq) {
          Message.obtain(SemFmEventListener.this.mHandler, 14, 0, 0, Long.valueOf(freq))
              .sendToTarget();
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
  Handler mHandler =
      new Handler() { // from class: com.samsung.android.media.fmradio.SemFmEventListener.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
          switch (msg.what) {
            case 1:
              Long freq = (Long) msg.obj;
              SemFmEventListener.this.onChannelFound(freq.longValue());
              break;
            case 2:
              SemFmEventListener.this.onScanStarted();
              break;
            case 3:
              long[] freqArry = (long[]) msg.obj;
              SemFmEventListener.this.onScanFinished(freqArry);
              break;
            case 4:
              long[] freqArry2 = (long[]) msg.obj;
              SemFmEventListener.this.onScanStopped(freqArry2);
              break;
            case 5:
              SemFmEventListener.this.onRadioEnabled();
              break;
            case 6:
              int reasonCode = ((Integer) msg.obj).intValue();
              SemFmEventListener.this.onRadioDisabled(reasonCode);
              break;
            case 7:
              Long freq2 = (Long) msg.obj;
              SemFmEventListener.this.onTuned(freq2.longValue());
              break;
            case 8:
              SemFmEventListener.this.onHeadsetConnected();
              break;
            case 9:
              SemFmEventListener.this.onHeadsetDisconnected();
              break;
            case 10:
              Object[] obArry = (Object[]) msg.obj;
              SemFmEventListener.this.onRadioDataSystemReceived(
                  ((Long) obArry[0]).longValue(), (String) obArry[1], (String) obArry[2]);
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
              Long freq3 = (Long) msg.obj;
              SemFmEventListener.this.onAlternateFrequencyReceived(freq3.longValue());
              SemFmEventListener.this.onTuned(freq3.longValue());
              break;
            case 15:
              SemFmEventListener.this.onVolumeLocked();
              break;
            case 16:
              Object[] rtpArry = (Object[]) msg.obj;
              SemFmEventListener.this.onRadioTextPlusReceived(
                  ((Integer) rtpArry[0]).intValue(),
                  ((Integer) rtpArry[1]).intValue(),
                  ((Integer) rtpArry[2]).intValue(),
                  ((Integer) rtpArry[3]).intValue(),
                  ((Integer) rtpArry[4]).intValue(),
                  ((Integer) rtpArry[5]).intValue());
              break;
            case 17:
              SemFmEventListener.this.onRecordingFinished();
              break;
            case 18:
              Object[] pieccArry = (Object[]) msg.obj;
              SemFmEventListener.this.onProgrammeIdentificationExtendedCountryCodesReceived(
                  ((Integer) pieccArry[0]).intValue(), ((Integer) pieccArry[1]).intValue());
              break;
          }
        }
      };

  public void onChannelFound(long frequency) {}

  public void onScanStarted() {}

  public void onScanStopped(long[] frequency) {}

  public void onScanFinished(long[] frequency) {}

  public void onRadioEnabled() {}

  public void onRadioDisabled(int reasonCode) {}

  public void onTuned(long frequency) {}

  public void onHeadsetConnected() {}

  public void onHeadsetDisconnected() {}

  public void onRadioDataSystemReceived(long freq, String channelName, String radioText) {}

  public void onRadioTextPlusReceived(
      int contentType1,
      int startPos1,
      int additionalLen1,
      int contentType2,
      int startPos2,
      int additionalLen2) {}

  public void onProgrammeIdentificationExtendedCountryCodesReceived(int pi, int ecc) {}

  public void onRadioDataSystemEnabled() {}

  public void onRadioDataSystemDisabled() {}

  public void onAlternateFrequencyStarted() {}

  public void onAlternateFrequencyReceived(long freq) {}

  public void onVolumeLocked() {}

  public void onRecordingFinished() {}
}
