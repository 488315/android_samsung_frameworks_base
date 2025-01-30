package com.samsung.android.media;

/* loaded from: classes5.dex */
public class PhotoHdrGain {

    public static class GainBuf {
        private int length;
        private int offset;

        public GainBuf(int offset, int length) {
            this.offset = 0;
            this.length = 0;
            this.offset = offset;
            this.length = length;
        }

        public int getOffset() {
            return this.offset;
        }

        public int getLength() {
            return this.length;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    public static class GainInfo {
        private String BaseRendition;
        private float GainMapMax;
        private float GainMapMin;
        private float Gamma;
        private float HDRCapacityMax;
        private float HDRCapacityMin;
        private float OffsetHDR;
        private float OffsetSDR;
        private float Version;

        public GainInfo(float GainMapMin, float GainMapMax, float HDRCapacityMin, float HDRCapacityMax) {
            this.Version = 1.0f;
            this.GainMapMin = GainMapMin;
            this.GainMapMax = GainMapMax;
            this.Gamma = 1.0f;
            this.OffsetSDR = 0.0f;
            this.OffsetHDR = 0.0f;
            this.HDRCapacityMin = HDRCapacityMin;
            this.HDRCapacityMax = HDRCapacityMax;
            this.BaseRendition = "SDR";
        }

        public GainInfo(float GainMapMin, float GainMapMax, float Gamma, float OffsetSDR, float OffsetHDR, float HDRCapacityMin, float HDRCapacityMax) {
            this.Version = 1.0f;
            this.GainMapMin = GainMapMin;
            this.GainMapMax = GainMapMax;
            this.Gamma = Gamma;
            this.OffsetSDR = OffsetSDR;
            this.OffsetHDR = OffsetHDR;
            this.HDRCapacityMin = HDRCapacityMin;
            this.HDRCapacityMax = HDRCapacityMax;
            this.BaseRendition = "SDR";
        }

        public float getGainMapMin() {
            return this.GainMapMin;
        }

        public float getGainMapMax() {
            return this.GainMapMax;
        }

        public float getGamma() {
            return this.Gamma;
        }

        public float getOffsetSDR() {
            return this.OffsetSDR;
        }

        public float getOffsetHDR() {
            return this.OffsetHDR;
        }

        public float getHDRCapacityMin() {
            return this.HDRCapacityMin;
        }

        public float getHDRCapacityMax() {
            return this.HDRCapacityMax;
        }

        public void setGainMapMin(float GainMapMin) {
            this.GainMapMin = GainMapMin;
        }

        public void setGainMapMax(float GainMapMax) {
            this.GainMapMax = GainMapMax;
        }

        public void setGamma(float Gamma) {
            this.Gamma = Gamma;
        }

        public void setOffsetSDR(float OffsetSDR) {
            this.OffsetSDR = OffsetSDR;
        }

        public void setOffsetHDR(float OffsetHDR) {
            this.OffsetHDR = OffsetHDR;
        }

        public void setHDRCapacityMin(float HDRCapacityMin) {
            this.HDRCapacityMin = HDRCapacityMin;
        }

        public void setHDRCapacityMax(float HDRCapacityMax) {
            this.HDRCapacityMax = HDRCapacityMax;
        }
    }
}
