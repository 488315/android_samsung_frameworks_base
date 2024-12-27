package com.samsung.android.knox.tima.attestation;

public class SemEnhancedAttestationResult {
    private EnhancedAttestationResult result;

    public SemEnhancedAttestationResult(EnhancedAttestationResult result) {
        this.result = result;
    }

    public String getUniqueId() {
        if (this.result != null) {
            return this.result.getUniqueId();
        }
        return null;
    }

    public String getUrl() {
        if (this.result != null) {
            return this.result.getUrl();
        }
        return null;
    }

    public String getResponseRawData() {
        if (this.result != null) {
            return this.result.getResponseRawData();
        }
        return null;
    }

    public byte[] getBlob() {
        if (this.result != null) {
            return this.result.getBlob();
        }
        return null;
    }

    public String getResponseId() {
        if (this.result != null) {
            return this.result.getServerResponseId();
        }
        return null;
    }
}
