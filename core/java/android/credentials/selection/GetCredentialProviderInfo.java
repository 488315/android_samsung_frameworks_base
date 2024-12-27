package android.credentials.selection;

import android.annotation.SystemApi;

import com.android.internal.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class GetCredentialProviderInfo {
    private final List<Entry> mActionChips;
    private final List<AuthenticationEntry> mAuthenticationEntries;
    private final List<Entry> mCredentialEntries;
    private final String mProviderName;
    private final Entry mRemoteEntry;

    GetCredentialProviderInfo(
            String providerName,
            List<Entry> credentialEntries,
            List<Entry> actionChips,
            List<AuthenticationEntry> authenticationEntries,
            Entry remoteEntry) {
        this.mProviderName = (String) Preconditions.checkStringNotEmpty(providerName);
        this.mCredentialEntries = new ArrayList(credentialEntries);
        this.mActionChips = new ArrayList(actionChips);
        this.mAuthenticationEntries = new ArrayList(authenticationEntries);
        this.mRemoteEntry = remoteEntry;
    }

    public String getProviderName() {
        return this.mProviderName;
    }

    public List<Entry> getCredentialEntries() {
        return this.mCredentialEntries;
    }

    public List<Entry> getActionChips() {
        return this.mActionChips;
    }

    public List<AuthenticationEntry> getAuthenticationEntries() {
        return this.mAuthenticationEntries;
    }

    public Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    @SystemApi
    public static final class Builder {
        private String mProviderName;
        private List<Entry> mCredentialEntries = new ArrayList();
        private List<Entry> mActionChips = new ArrayList();
        private List<AuthenticationEntry> mAuthenticationEntries = new ArrayList();
        private Entry mRemoteEntry = null;

        public Builder(String providerName) {
            this.mProviderName = (String) Preconditions.checkStringNotEmpty(providerName);
        }

        public Builder setCredentialEntries(List<Entry> credentialEntries) {
            this.mCredentialEntries = credentialEntries;
            return this;
        }

        public Builder setActionChips(List<Entry> actionChips) {
            this.mActionChips = actionChips;
            return this;
        }

        public Builder setAuthenticationEntries(List<AuthenticationEntry> authenticationEntry) {
            this.mAuthenticationEntries = authenticationEntry;
            return this;
        }

        public Builder setRemoteEntry(Entry remoteEntry) {
            this.mRemoteEntry = remoteEntry;
            return this;
        }

        public GetCredentialProviderInfo build() {
            return new GetCredentialProviderInfo(
                    this.mProviderName,
                    this.mCredentialEntries,
                    this.mActionChips,
                    this.mAuthenticationEntries,
                    this.mRemoteEntry);
        }
    }
}
