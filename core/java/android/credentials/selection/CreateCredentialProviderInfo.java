package android.credentials.selection;

import android.annotation.SystemApi;

import com.android.internal.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class CreateCredentialProviderInfo {
    private final String mProviderName;
    private final Entry mRemoteEntry;
    private final List<Entry> mSaveEntries;

    CreateCredentialProviderInfo(String providerName, List<Entry> saveEntries, Entry remoteEntry) {
        this.mProviderName = (String) Preconditions.checkStringNotEmpty(providerName);
        this.mSaveEntries = new ArrayList(saveEntries);
        this.mRemoteEntry = remoteEntry;
    }

    public String getProviderName() {
        return this.mProviderName;
    }

    public List<Entry> getSaveEntries() {
        return this.mSaveEntries;
    }

    public Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    @SystemApi
    public static final class Builder {
        private String mProviderName;
        private List<Entry> mSaveEntries = new ArrayList();
        private Entry mRemoteEntry = null;

        public Builder(String providerName) {
            this.mProviderName = (String) Preconditions.checkStringNotEmpty(providerName);
        }

        public Builder setSaveEntries(List<Entry> credentialEntries) {
            this.mSaveEntries = credentialEntries;
            return this;
        }

        public Builder setRemoteEntry(Entry remoteEntry) {
            this.mRemoteEntry = remoteEntry;
            return this;
        }

        public CreateCredentialProviderInfo build() {
            return new CreateCredentialProviderInfo(
                    this.mProviderName, this.mSaveEntries, this.mRemoteEntry);
        }
    }
}
