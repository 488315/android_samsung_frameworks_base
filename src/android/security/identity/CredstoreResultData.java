package android.security.identity;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes3.dex */
class CredstoreResultData extends ResultData {
    int mFeatureVersion = 0;
    byte[] mStaticAuthenticationData = null;
    byte[] mAuthenticatedData = null;
    byte[] mMessageAuthenticationCode = null;
    byte[] mSignature = null;
    private Map<String, Map<String, EntryData>> mData = new LinkedHashMap();

    private static class EntryData {
        int mStatus;
        byte[] mValue;

        EntryData(byte[] bArr, int i) {
            this.mValue = bArr;
            this.mStatus = i;
        }
    }

    CredstoreResultData() {
    }

    @Override // android.security.identity.ResultData
    public byte[] getAuthenticatedData() {
        return this.mAuthenticatedData;
    }

    @Override // android.security.identity.ResultData
    public byte[] getMessageAuthenticationCode() {
        return this.mMessageAuthenticationCode;
    }

    @Override // android.security.identity.ResultData
    byte[] getSignature() {
        if (this.mFeatureVersion < 202301) {
            throw new UnsupportedOperationException();
        }
        return this.mSignature;
    }

    @Override // android.security.identity.ResultData
    public byte[] getStaticAuthenticationData() {
        return this.mStaticAuthenticationData;
    }

    @Override // android.security.identity.ResultData
    public Collection<String> getNamespaces() {
        return Collections.unmodifiableCollection(this.mData.keySet());
    }

    @Override // android.security.identity.ResultData
    public Collection<String> getEntryNames(String str) {
        Map<String, EntryData> map = this.mData.get(str);
        if (map == null) {
            return null;
        }
        return Collections.unmodifiableCollection(map.keySet());
    }

    @Override // android.security.identity.ResultData
    public Collection<String> getRetrievedEntryNames(String str) {
        Map<String, EntryData> map = this.mData.get(str);
        if (map == null) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<String, EntryData> entry : map.entrySet()) {
            if (entry.getValue().mStatus == 0) {
                linkedList.add(entry.getKey());
            }
        }
        return linkedList;
    }

    private EntryData getEntryData(String str, String str2) {
        Map<String, EntryData> map = this.mData.get(str);
        if (map == null) {
            return null;
        }
        return map.get(str2);
    }

    @Override // android.security.identity.ResultData
    public int getStatus(String str, String str2) {
        EntryData entryData = getEntryData(str, str2);
        if (entryData == null) {
            return 2;
        }
        return entryData.mStatus;
    }

    @Override // android.security.identity.ResultData
    public byte[] getEntry(String str, String str2) {
        EntryData entryData = getEntryData(str, str2);
        if (entryData == null) {
            return null;
        }
        return entryData.mValue;
    }

    static class Builder {
        private CredstoreResultData mResultData;

        Builder(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
            CredstoreResultData credstoreResultData = new CredstoreResultData();
            this.mResultData = credstoreResultData;
            credstoreResultData.mFeatureVersion = i;
            this.mResultData.mStaticAuthenticationData = bArr;
            this.mResultData.mAuthenticatedData = bArr2;
            this.mResultData.mMessageAuthenticationCode = bArr3;
            this.mResultData.mSignature = bArr4;
        }

        private Map<String, EntryData> getOrCreateInnerMap(String str) {
            Map<String, EntryData> map = (Map) this.mResultData.mData.get(str);
            if (map != null) {
                return map;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            this.mResultData.mData.put(str, linkedHashMap);
            return linkedHashMap;
        }

        Builder addEntry(String str, String str2, byte[] bArr) {
            getOrCreateInnerMap(str).put(str2, new EntryData(bArr, 0));
            return this;
        }

        Builder addErrorStatus(String str, String str2, int i) {
            getOrCreateInnerMap(str).put(str2, new EntryData(null, i));
            return this;
        }

        CredstoreResultData build() {
            return this.mResultData;
        }
    }
}
