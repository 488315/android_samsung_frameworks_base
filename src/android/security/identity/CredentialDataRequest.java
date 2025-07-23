package android.security.identity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class CredentialDataRequest {
    Map<String, Collection<String>> mDeviceSignedEntriesToRequest = new LinkedHashMap();
    Map<String, Collection<String>> mIssuerSignedEntriesToRequest = new LinkedHashMap();
    boolean mAllowUsingExhaustedKeys = true;
    boolean mAllowUsingExpiredKeys = false;
    boolean mIncrementUseCount = true;
    byte[] mRequestMessage = null;
    byte[] mReaderSignature = null;

    CredentialDataRequest() {
    }

    public Map<String, Collection<String>> getDeviceSignedEntriesToRequest() {
        return this.mDeviceSignedEntriesToRequest;
    }

    public Map<String, Collection<String>> getIssuerSignedEntriesToRequest() {
        return this.mIssuerSignedEntriesToRequest;
    }

    public boolean isAllowUsingExhaustedKeys() {
        return this.mAllowUsingExhaustedKeys;
    }

    public boolean isAllowUsingExpiredKeys() {
        return this.mAllowUsingExpiredKeys;
    }

    public boolean isIncrementUseCount() {
        return this.mIncrementUseCount;
    }

    public byte[] getRequestMessage() {
        return this.mRequestMessage;
    }

    public byte[] getReaderSignature() {
        return this.mReaderSignature;
    }

    public static final class Builder {
        private CredentialDataRequest mData = new CredentialDataRequest();

        public Builder setDeviceSignedEntriesToRequest(Map<String, Collection<String>> map) {
            this.mData.mDeviceSignedEntriesToRequest = map;
            return this;
        }

        public Builder setIssuerSignedEntriesToRequest(Map<String, Collection<String>> map) {
            this.mData.mIssuerSignedEntriesToRequest = map;
            return this;
        }

        public Builder setAllowUsingExhaustedKeys(boolean z) {
            this.mData.mAllowUsingExhaustedKeys = z;
            return this;
        }

        public Builder setAllowUsingExpiredKeys(boolean z) {
            this.mData.mAllowUsingExpiredKeys = z;
            return this;
        }

        public Builder setIncrementUseCount(boolean z) {
            this.mData.mIncrementUseCount = z;
            return this;
        }

        public Builder setRequestMessage(byte[] bArr) {
            this.mData.mRequestMessage = bArr;
            return this;
        }

        public Builder setReaderSignature(byte[] bArr) {
            this.mData.mReaderSignature = bArr;
            return this;
        }

        public CredentialDataRequest build() {
            return this.mData;
        }
    }
}
