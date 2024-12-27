package com.android.server;

import android.util.Log;

public class FMPlayerNativeBase {
    public FMPlayerNativeBase(FMRadioService service) {}

    static class RDSData {
        public String mChannelName;
        public long mFreq;
        public String mRadioText;

        public RDSData(long freq, byte[] channelName, byte[] radioText) {
            this.mFreq = freq;
            this.mChannelName = null;
            this.mRadioText = null;
            try {
                this.mChannelName = new String(channelName);
            } catch (Exception e) {
                Log.e("FMPlayerNativeBase", "Exception in new String(channelName) :", e);
            }
            try {
                this.mRadioText = new String(radioText);
            } catch (Exception e2) {
                Log.e("FMPlayerNativeBase", "Exception in new String(radioText) :", e2);
            }
        }

        public RDSData(long freq, String channelName, String radioText) {
            this.mFreq = freq;
            this.mChannelName = null;
            this.mRadioText = null;
            if (channelName != null) {
                this.mChannelName = channelName;
            }
            if (radioText != null) {
                this.mRadioText = radioText;
            }
        }

        public String toString() {
            return "\n== RDSData :--> \nFreq :"
                    + this.mFreq
                    + " \nChannel Name:"
                    + this.mChannelName
                    + "<-- \nRadio Text :"
                    + this.mRadioText
                    + "<--: =====";
        }
    }

    static class RTPlusData {
        public int mAdditionalLen1;
        public int mAdditionalLen2;
        public int mContentType1;
        public int mContentType2;
        public int mStartPos1;
        public int mStartPos2;

        public RTPlusData(
                int contentType1,
                int startPos1,
                int additionalLen1,
                int contentType2,
                int startPos2,
                int additionalLen2) {
            this.mContentType1 = contentType1;
            this.mStartPos1 = startPos1;
            this.mAdditionalLen1 = additionalLen1;
            this.mContentType2 = contentType2;
            this.mStartPos2 = startPos2;
            this.mAdditionalLen2 = additionalLen2;
        }
    }

    static class PIECCData {
        public int mECC;
        public int mPI;

        public PIECCData(int PI, int ECC) {
            this.mPI = PI;
            this.mECC = ECC;
        }
    }

    public void tune(long freq) {}

    public long on() {
        return -1L;
    }

    public long preInitialize() {
        return -1L;
    }

    public void off() {}

    public void offFMService() {}

    public long seekUp() {
        return -1L;
    }

    public long seekDown() {
        return -1L;
    }

    public void cancelSeek() {}

    public long getCurrentChannel() {
        return -1L;
    }

    public long searchDown() {
        return -1L;
    }

    public long searchUp() {
        return -1L;
    }

    public long searchAll() {
        return -1L;
    }

    public void setVolume(long volume) {}

    public long getVolume() {
        return -1L;
    }

    public void muteOn() {}

    public void muteOff() {}

    public void enableRDS() {}

    public void disableRDS() {}

    public void enableAF() {}

    public void disableAF() {}

    public void cancelAFSwitching() {}

    public void setBand(int band) {}

    public void setChannelSpacing(int spacing) {}

    public void setStereo() {}

    public void setMono() {}

    public void setSpeakerOn(boolean setSpeakerOn) {}

    public void setRecordMode(int is_record) {}

    public long getMaxVolume() {
        return -1L;
    }

    public void setDEConstant(long value) {}

    public long getCurrentRSSI() {
        return -1L;
    }

    public long getCurrentSNR() {
        return -1L;
    }

    public void setSeekRSSI(long value) {}

    public void setSeekSNR(long value) {}

    public void setRSSI_th(int value) {}

    public void setSNR_th(int value) {}

    public void setCnt_th(int value) {}

    public void setRSSI_th_2(int value) {}

    public void setSNR_th_2(int value) {}

    public void setCnt_th_2(int value) {}

    public int getRSSI_th() {
        return -1;
    }

    public int getSNR_th() {
        return -1;
    }

    public int getCnt_th() {
        return -1;
    }

    public int getRSSI_th_2() {
        return -1;
    }

    public int getSNR_th_2() {
        return -1;
    }

    public int getCnt_th_2() {
        return -1;
    }

    public void setAF_th(int value) {}

    public int getAF_th() {
        return -1;
    }

    public void setAFValid_th(int value) {}

    public int getAFValid_th() {
        return -1;
    }

    public void setFMIntenna(boolean setFMIntenna) {}

    public void setSoftmute(boolean setSoftmute) {}

    public boolean getSoftMuteMode() {
        return false;
    }

    public void setSearchAlgoType(int value) {}

    public int getSearchAlgoType() {
        return -1;
    }

    public void setSINRSamples(int value) {}

    public int getSINRSamples() {
        return -1;
    }

    public void setOnChannelThreshold(int value) {}

    public int getOnChannelThreshold() {
        return -1;
    }

    public void setOffChannelThreshold(int value) {}

    public int getOffChannelThreshold() {
        return -1;
    }

    public void setSINRThreshold(int value) {}

    public int getSINRThreshold() {
        return -1;
    }

    public void setCFOTh12(int value) {}

    public int getCFOTh12() {
        return -1;
    }

    public void setRMSSIFirstStage(int value) {}

    public int getRMSSIFirstStage() {
        return -1;
    }

    public void setSINRFirstStage(int value) {}

    public int getSINRFirstStage() {
        return -1;
    }

    public void setAFRMSSIThreshold(int value) {}

    public int getAFRMSSIThreshold() {
        return -1;
    }

    public void setAFRMSSISamples(int value) {}

    public int getAFRMSSISamples() {
        return -1;
    }

    public void setGoodChannelRMSSIThreshold(int value) {}

    public int getGoodChannelRMSSIThreshold() {
        return -1;
    }

    public void setHybridSearch(String value) {}

    public String getHybridSearch() {
        return null;
    }

    public void setBlendRmssi(int value) {}

    public int getBlendRmssi() {
        return -1;
    }

    public void setBlendSinr(int value) {}

    public int getBlendSinr() {
        return -1;
    }

    public void setSeekDC(int value) {}

    public int getSeekDC() {
        return -1;
    }

    public void setSeekQA(int value) {}

    public int getSeekQA() {
        return -1;
    }

    public void setScanning(boolean value) {}

    public void setFrequencyOffsetThreshold(int value) {}

    public void setPilotPowerThreshold(int value) {}

    public void setNoisePowerThreshold(int value) {}

    public int getFrequencyOffsetThreshold() {
        return -1;
    }

    public int getPilotPowerThreshold() {
        return -1;
    }

    public int getNoisePowerThreshold() {
        return -1;
    }

    public void setIFCount1(int value) {}

    public void setIFCount2(int value) {}

    public void setSoftStereoBlendCoeff(long value) {}

    public void setSoftMuteCoeff(long value) {}

    public void setSoftStereoBlendRef(long value) {}

    public void setSeekDesenseRSSI(int value) {}

    public void setSeekSMG(int value) {}

    public void setSoftmute_th(int value) {}

    public void setBlendRSSI_th(int value) {}

    public void setBlendPAMD_th(int value) {}

    public void setFakeChannel(String value) {}

    public String getFakeChannel() {
        return null;
    }

    public void setDeSenseList(String value) {}

    public String getDeSenseList() {
        return null;
    }

    public void setATJ(int bATJOn) {}

    public void setSlimbusEnable(int mode) {}
}
