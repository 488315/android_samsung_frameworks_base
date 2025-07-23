package com.android.server;

import android.util.Log;

/* loaded from: classes6.dex */
public class FMPlayerNativeBase {
    public void cancelAFSwitching() {
    }

    public void cancelSeek() {
    }

    public void disableAF() {
    }

    public void disableRDS() {
    }

    public void enableAF() {
    }

    public void enableRDS() {
    }

    public int getAFRMSSISamples() {
        return -1;
    }

    public int getAFRMSSIThreshold() {
        return -1;
    }

    public int getAFValid_th() {
        return -1;
    }

    public int getAF_th() {
        return -1;
    }

    public int getBlendRmssi() {
        return -1;
    }

    public int getBlendSinr() {
        return -1;
    }

    public int getCFOTh12() {
        return -1;
    }

    public int getCnt_th() {
        return -1;
    }

    public int getCnt_th_2() {
        return -1;
    }

    public long getCurrentChannel() {
        return -1L;
    }

    public long getCurrentRSSI() {
        return -1L;
    }

    public long getCurrentSNR() {
        return -1L;
    }

    public String getDeSenseList() {
        return null;
    }

    public String getFakeChannel() {
        return null;
    }

    public int getFrequencyOffsetThreshold() {
        return -1;
    }

    public int getGoodChannelRMSSIThreshold() {
        return -1;
    }

    public String getHybridSearch() {
        return null;
    }

    public long getMaxVolume() {
        return -1L;
    }

    public int getNoisePowerThreshold() {
        return -1;
    }

    public int getOffChannelThreshold() {
        return -1;
    }

    public int getOnChannelThreshold() {
        return -1;
    }

    public int getPilotPowerThreshold() {
        return -1;
    }

    public int getRMSSIFirstStage() {
        return -1;
    }

    public int getRSSI_th() {
        return -1;
    }

    public int getRSSI_th_2() {
        return -1;
    }

    public int getSINRFirstStage() {
        return -1;
    }

    public int getSINRSamples() {
        return -1;
    }

    public int getSINRThreshold() {
        return -1;
    }

    public int getSNR_th() {
        return -1;
    }

    public int getSNR_th_2() {
        return -1;
    }

    public int getSearchAlgoType() {
        return -1;
    }

    public int getSeekDC() {
        return -1;
    }

    public int getSeekQA() {
        return -1;
    }

    public boolean getSoftMuteMode() {
        return false;
    }

    public long getVolume() {
        return -1L;
    }

    public void muteOff() {
    }

    public void muteOn() {
    }

    public void off() {
    }

    public void offFMService() {
    }

    public long on() {
        return -1L;
    }

    public long preInitialize() {
        return -1L;
    }

    public long searchAll() {
        return -1L;
    }

    public long searchDown() {
        return -1L;
    }

    public long searchUp() {
        return -1L;
    }

    public long seekDown() {
        return -1L;
    }

    public long seekUp() {
        return -1L;
    }

    public void setAFRMSSISamples(int i) {
    }

    public void setAFRMSSIThreshold(int i) {
    }

    public void setAFValid_th(int i) {
    }

    public void setAF_th(int i) {
    }

    public void setATJ(int i) {
    }

    public void setBand(int i) {
    }

    public void setBlendPAMD_th(int i) {
    }

    public void setBlendRSSI_th(int i) {
    }

    public void setBlendRmssi(int i) {
    }

    public void setBlendSinr(int i) {
    }

    public void setCFOTh12(int i) {
    }

    public void setChannelSpacing(int i) {
    }

    public void setCnt_th(int i) {
    }

    public void setCnt_th_2(int i) {
    }

    public void setDEConstant(long j) {
    }

    public void setDeSenseList(String str) {
    }

    public void setFMIntenna(boolean z) {
    }

    public void setFakeChannel(String str) {
    }

    public void setFrequencyOffsetThreshold(int i) {
    }

    public void setGoodChannelRMSSIThreshold(int i) {
    }

    public void setHybridSearch(String str) {
    }

    public void setIFCount1(int i) {
    }

    public void setIFCount2(int i) {
    }

    public void setMono() {
    }

    public void setNoisePowerThreshold(int i) {
    }

    public void setOffChannelThreshold(int i) {
    }

    public void setOnChannelThreshold(int i) {
    }

    public void setPilotPowerThreshold(int i) {
    }

    public void setRMSSIFirstStage(int i) {
    }

    public void setRSSI_th(int i) {
    }

    public void setRSSI_th_2(int i) {
    }

    public void setRecordMode(int i) {
    }

    public void setSINRFirstStage(int i) {
    }

    public void setSINRSamples(int i) {
    }

    public void setSINRThreshold(int i) {
    }

    public void setSNR_th(int i) {
    }

    public void setSNR_th_2(int i) {
    }

    public void setScanning(boolean z) {
    }

    public void setSearchAlgoType(int i) {
    }

    public void setSeekDC(int i) {
    }

    public void setSeekDesenseRSSI(int i) {
    }

    public void setSeekQA(int i) {
    }

    public void setSeekRSSI(long j) {
    }

    public void setSeekSMG(int i) {
    }

    public void setSeekSNR(long j) {
    }

    public void setSlimbusEnable(int i) {
    }

    public void setSoftMuteCoeff(long j) {
    }

    public void setSoftStereoBlendCoeff(long j) {
    }

    public void setSoftStereoBlendRef(long j) {
    }

    public void setSoftmute(boolean z) {
    }

    public void setSoftmute_th(int i) {
    }

    public void setSpeakerOn(boolean z) {
    }

    public void setStereo() {
    }

    public void setVolume(long j) {
    }

    public void tune(long j) {
    }

    public FMPlayerNativeBase(FMRadioService fMRadioService) {
    }

    static class RDSData {
        public String mChannelName;
        public long mFreq;
        public String mRadioText;

        public RDSData(long j, byte[] bArr, byte[] bArr2) {
            this.mFreq = j;
            this.mChannelName = null;
            this.mRadioText = null;
            try {
                this.mChannelName = new String(bArr);
            } catch (Exception e) {
                Log.e("FMPlayerNativeBase", "Exception in new String(channelName) :", e);
            }
            try {
                this.mRadioText = new String(bArr2);
            } catch (Exception e2) {
                Log.e("FMPlayerNativeBase", "Exception in new String(radioText) :", e2);
            }
        }

        public RDSData(long j, String str, String str2) {
            this.mFreq = j;
            this.mChannelName = null;
            this.mRadioText = null;
            if (str != null) {
                this.mChannelName = str;
            }
            if (str2 != null) {
                this.mRadioText = str2;
            }
        }

        public String toString() {
            return "\n== RDSData :--> \nFreq :" + this.mFreq + " \nChannel Name:" + this.mChannelName + "<-- \nRadio Text :" + this.mRadioText + "<--: =====";
        }
    }

    static class RTPlusData {
        public int mAdditionalLen1;
        public int mAdditionalLen2;
        public int mContentType1;
        public int mContentType2;
        public int mStartPos1;
        public int mStartPos2;

        public RTPlusData(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mContentType1 = i;
            this.mStartPos1 = i2;
            this.mAdditionalLen1 = i3;
            this.mContentType2 = i4;
            this.mStartPos2 = i5;
            this.mAdditionalLen2 = i6;
        }
    }

    static class PIECCData {
        public int mECC;
        public int mPI;

        public PIECCData(int i, int i2) {
            this.mPI = i;
            this.mECC = i2;
        }
    }
}
