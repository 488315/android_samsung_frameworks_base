package android.security.identity;

import android.security.identity.CredentialDataResult;
import java.util.Collection;
import java.util.LinkedList;

/* loaded from: classes3.dex */
class CredstoreCredentialDataResult extends CredentialDataResult {
    CredstoreEntries mDeviceSignedEntries;
    ResultData mDeviceSignedResult;
    CredstoreEntries mIssuerSignedEntries;
    ResultData mIssuerSignedResult;

    CredstoreCredentialDataResult(ResultData resultData, ResultData resultData2) {
        this.mDeviceSignedResult = resultData;
        this.mIssuerSignedResult = resultData2;
        this.mDeviceSignedEntries = new CredstoreEntries(resultData);
        this.mIssuerSignedEntries = new CredstoreEntries(resultData2);
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getDeviceNameSpaces() {
        return this.mDeviceSignedResult.getAuthenticatedData();
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getDeviceMac() {
        return this.mDeviceSignedResult.getMessageAuthenticationCode();
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getDeviceSignature() {
        return this.mDeviceSignedResult.getSignature();
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getStaticAuthenticationData() {
        return this.mDeviceSignedResult.getStaticAuthenticationData();
    }

    @Override // android.security.identity.CredentialDataResult
    public CredentialDataResult.Entries getDeviceSignedEntries() {
        return this.mDeviceSignedEntries;
    }

    @Override // android.security.identity.CredentialDataResult
    public CredentialDataResult.Entries getIssuerSignedEntries() {
        return this.mIssuerSignedEntries;
    }

    static class CredstoreEntries implements CredentialDataResult.Entries {
        ResultData mResultData;

        CredstoreEntries(ResultData resultData) {
            this.mResultData = resultData;
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public Collection<String> getNamespaces() {
            return this.mResultData.getNamespaces();
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public Collection<String> getEntryNames(String str) {
            Collection<String> entryNames = this.mResultData.getEntryNames(str);
            return entryNames == null ? new LinkedList() : entryNames;
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public Collection<String> getRetrievedEntryNames(String str) {
            Collection<String> retrievedEntryNames = this.mResultData.getRetrievedEntryNames(str);
            return retrievedEntryNames == null ? new LinkedList() : retrievedEntryNames;
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public int getStatus(String str, String str2) {
            return this.mResultData.getStatus(str, str2);
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public byte[] getEntry(String str, String str2) {
            return this.mResultData.getEntry(str, str2);
        }
    }
}
