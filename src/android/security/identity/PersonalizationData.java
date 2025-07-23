package android.security.identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/* loaded from: classes3.dex */
public class PersonalizationData {
    private LinkedHashMap<String, NamespaceData> mNamespaces;
    private ArrayList<AccessControlProfile> mProfiles;

    private PersonalizationData() {
        this.mProfiles = new ArrayList<>();
        this.mNamespaces = new LinkedHashMap<>();
    }

    Collection<AccessControlProfile> getAccessControlProfiles() {
        return Collections.unmodifiableCollection(this.mProfiles);
    }

    Collection<String> getNamespaces() {
        return Collections.unmodifiableCollection(this.mNamespaces.keySet());
    }

    NamespaceData getNamespaceData(String str) {
        return this.mNamespaces.get(str);
    }

    static class NamespaceData {
        private LinkedHashMap<String, EntryData> mEntries;
        private String mNamespace;

        private NamespaceData(String str) {
            this.mEntries = new LinkedHashMap<>();
            this.mNamespace = str;
        }

        String getNamespaceName() {
            return this.mNamespace;
        }

        Collection<String> getEntryNames() {
            return Collections.unmodifiableCollection(this.mEntries.keySet());
        }

        Collection<AccessControlProfileId> getAccessControlProfileIds(String str) {
            EntryData entryData = this.mEntries.get(str);
            if (entryData != null) {
                return entryData.mAccessControlProfileIds;
            }
            return null;
        }

        byte[] getEntryValue(String str) {
            EntryData entryData = this.mEntries.get(str);
            if (entryData != null) {
                return entryData.mValue;
            }
            return null;
        }
    }

    private static class EntryData {
        Collection<AccessControlProfileId> mAccessControlProfileIds;
        byte[] mValue;

        EntryData(byte[] bArr, Collection<AccessControlProfileId> collection) {
            this.mValue = bArr;
            this.mAccessControlProfileIds = collection;
        }
    }

    public static final class Builder {
        private PersonalizationData mData = new PersonalizationData();

        public Builder putEntry(String str, String str2, Collection<AccessControlProfileId> collection, byte[] bArr) {
            NamespaceData namespaceData = (NamespaceData) this.mData.mNamespaces.get(str);
            if (namespaceData == null) {
                namespaceData = new NamespaceData(str);
                this.mData.mNamespaces.put(str, namespaceData);
            }
            namespaceData.mEntries.put(str2, new EntryData(bArr, collection));
            return this;
        }

        public Builder addAccessControlProfile(AccessControlProfile accessControlProfile) {
            this.mData.mProfiles.add(accessControlProfile);
            return this;
        }

        public PersonalizationData build() {
            return this.mData;
        }
    }
}
