package com.android.server;

import android.os.SystemProperties;
import android.util.Log;

/* loaded from: classes5.dex */
public class FMPlayerNative extends FMPlayerNativeBase {
  private static final boolean DEBUGGABLE;
  private static FMRadioService mService;

  @Override // com.android.server.FMPlayerNativeBase
  public native void cancelAFSwitching();

  @Override // com.android.server.FMPlayerNativeBase
  public native void cancelSeek();

  @Override // com.android.server.FMPlayerNativeBase
  public native void disableAF();

  public native void disableDNS();

  @Override // com.android.server.FMPlayerNativeBase
  public native void disableRDS();

  @Override // com.android.server.FMPlayerNativeBase
  public native void enableAF();

  public native void enableDNS();

  @Override // com.android.server.FMPlayerNativeBase
  public native void enableRDS();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getAFRMSSISamples();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getAFRMSSIThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getAFValid_th();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getAF_th();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getBlendRmssi();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getBlendSinr();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getCFOTh12();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getCnt_th();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getCnt_th_2();

  @Override // com.android.server.FMPlayerNativeBase
  public native long getCurrentChannel();

  @Override // com.android.server.FMPlayerNativeBase
  public native long getCurrentRSSI();

  @Override // com.android.server.FMPlayerNativeBase
  public native long getCurrentSNR();

  @Override // com.android.server.FMPlayerNativeBase
  public native String getDeSenseList();

  @Override // com.android.server.FMPlayerNativeBase
  public native String getFakeChannel();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getFrequencyOffsetThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getGoodChannelRMSSIThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native String getHybridSearch();

  @Override // com.android.server.FMPlayerNativeBase
  public native long getMaxVolume();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getNoisePowerThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getOffChannelThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getOnChannelThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getPilotPowerThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getRMSSIFirstStage();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getRSSI_th();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getRSSI_th_2();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSINRFirstStage();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSINRSamples();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSINRThreshold();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSNR_th();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSNR_th_2();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSearchAlgoType();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSeekDC();

  @Override // com.android.server.FMPlayerNativeBase
  public native int getSeekQA();

  @Override // com.android.server.FMPlayerNativeBase
  public native boolean getSoftMuteMode();

  @Override // com.android.server.FMPlayerNativeBase
  public native long getVolume();

  @Override // com.android.server.FMPlayerNativeBase
  public native void muteOff();

  @Override // com.android.server.FMPlayerNativeBase
  public native void muteOn();

  @Override // com.android.server.FMPlayerNativeBase
  public native void off();

  @Override // com.android.server.FMPlayerNativeBase
  /* renamed from: on */
  public native long mo232on();

  @Override // com.android.server.FMPlayerNativeBase
  public native long preInitialize();

  @Override // com.android.server.FMPlayerNativeBase
  public native long searchAll();

  @Override // com.android.server.FMPlayerNativeBase
  public native long searchDown();

  @Override // com.android.server.FMPlayerNativeBase
  public native long searchUp();

  @Override // com.android.server.FMPlayerNativeBase
  public native long seekDown();

  @Override // com.android.server.FMPlayerNativeBase
  public native long seekUp();

  @Override // com.android.server.FMPlayerNativeBase
  public native void setAFRMSSISamples(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setAFRMSSIThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setAFValid_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setAF_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setATJ(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setBand(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setBlendPAMD_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setBlendRSSI_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setBlendRmssi(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setBlendSinr(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setCFOTh12(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setChannelSpacing(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setCnt_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setCnt_th_2(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setDEConstant(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setDeSenseList(String str);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setFMIntenna(boolean z);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setFakeChannel(String str);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setFrequencyOffsetThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setGoodChannelRMSSIThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setHybridSearch(String str);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setIFCount1(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setIFCount2(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setMono();

  @Override // com.android.server.FMPlayerNativeBase
  public native void setNoisePowerThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setOffChannelThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setOnChannelThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setPilotPowerThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setRMSSIFirstStage(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setRSSI_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setRSSI_th_2(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setRecordMode(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSINRFirstStage(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSINRSamples(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSINRThreshold(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSNR_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSNR_th_2(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setScanning(boolean z);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSearchAlgoType(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSeekDC(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSeekDesenseRSSI(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSeekQA(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSeekRSSI(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSeekSMG(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSeekSNR(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSlimbusEnable(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSoftMuteCoeff(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSoftStereoBlendCoeff(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSoftStereoBlendRef(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSoftmute(boolean z);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSoftmute_th(int i);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setSpeakerOn(boolean z);

  @Override // com.android.server.FMPlayerNativeBase
  public native void setStereo();

  @Override // com.android.server.FMPlayerNativeBase
  public native void setVolume(long j);

  @Override // com.android.server.FMPlayerNativeBase
  public native void tune(long j);

  static {
    if (FMRadioServiceFeature.CHIP_VENDOR > 0) {
      System.loadLibrary("fmradio_jni");
      Log.m98i("FMPlayerNative", "FMRadio lib loaded");
    } else {
      Log.m98i("FMPlayerNative", "FMRadio lib not loaded");
    }
    DEBUGGABLE = SystemProperties.getInt("ro.debuggable", 0) == 1;
  }

  public FMPlayerNative(FMRadioService service) {
    super(service);
    mService = service;
  }

  protected void finalize() throws Throwable {
    super.finalize();
    mService = null;
  }

  public static void notifyRDSEvent(FMPlayerNativeBase.RDSData ob) {
    if (!mService.isRDSEnable()) {
      return;
    }
    if (DEBUGGABLE) {
      FMRadioService.log("Got Events :" + ob);
    }
    mService.notifyEvent(10, ob);
  }

  public static void notifyRTPlusEvent(FMPlayerNativeBase.RTPlusData ob) {
    if (!mService.isRDSEnable()) {
      return;
    }
    if (DEBUGGABLE) {
      FMRadioService.log("Got notifyRTPlusEvents :" + ob);
    }
    mService.notifyEvent(16, ob);
  }

  public static void notifyPIECCEvent(FMPlayerNativeBase.PIECCData ob) {}

  public static void notifyAFStarted() {
    if (!mService.isAFEnable()) {
      return;
    }
    FMRadioService.log("NotifyAFStarted :");
    mService.notifyEvent(13, null);
  }

  public static void notifyAFDataReceived(long af) {
    if (!mService.isAFEnable()) {
      return;
    }
    if (DEBUGGABLE) {
      FMRadioService.log("notifyAFDataReceived :" + af);
    }
    mService.notifyEvent(14, Long.valueOf(af));
  }

  @Override // com.android.server.FMPlayerNativeBase
  public void offFMService() {}
}
