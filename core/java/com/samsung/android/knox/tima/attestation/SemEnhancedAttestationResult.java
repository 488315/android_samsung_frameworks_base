package com.samsung.android.knox.tima.attestation;

/* loaded from: classes5.dex */
public class SemEnhancedAttestationResult {
    private EnhancedAttestationResult result;

    public SemEnhancedAttestationResult(EnhancedAttestationResult result) {
        this.result = result;
    }

    public String getUniqueId() {
        EnhancedAttestationResult enhancedAttestationResult = this.result;
        if (enhancedAttestationResult != null) {
            return enhancedAttestationResult.getUniqueId();
        }
        return null;
    }

    public String getUrl() {
        EnhancedAttestationResult enhancedAttestationResult = this.result;
        if (enhancedAttestationResult != null) {
            return enhancedAttestationResult.getUrl();
        }
        return null;
    }

    public String getResponseRawData() {
        EnhancedAttestationResult enhancedAttestationResult = this.result;
        if (enhancedAttestationResult != null) {
            return enhancedAttestationResult.getResponseRawData();
        }
        return null;
    }

    public byte[] getBlob() {
        EnhancedAttestationResult enhancedAttestationResult = this.result;
        if (enhancedAttestationResult != null) {
            return enhancedAttestationResult.getBlob();
        }
        return null;
    }

    public String getResponseId() {
        EnhancedAttestationResult enhancedAttestationResult = this.result;
        if (enhancedAttestationResult != null) {
            return enhancedAttestationResult.getServerResponseId();
        }
        return null;
    }
}
