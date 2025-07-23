package android.credentials.selection;

import android.annotation.SystemApi;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class GetCredentialProviderInfo {
    private final List<Entry> mActionChips;
    private final List<AuthenticationEntry> mAuthenticationEntries;
    private final List<Entry> mCredentialEntries;
    private final String mProviderName;
    private final Entry mRemoteEntry;

    GetCredentialProviderInfo(String str, List<Entry> list, List<Entry> list2, List<AuthenticationEntry> list3, Entry entry) {
        this.mProviderName = (String) Preconditions.checkStringNotEmpty(str);
        this.mCredentialEntries = new ArrayList(list);
        this.mActionChips = new ArrayList(list2);
        this.mAuthenticationEntries = new ArrayList(list3);
        this.mRemoteEntry = entry;
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

        public Builder(String str) {
            this.mProviderName = (String) Preconditions.checkStringNotEmpty(str);
        }

        public Builder setCredentialEntries(List<Entry> list) {
            this.mCredentialEntries = list;
            return this;
        }

        public Builder setActionChips(List<Entry> list) {
            this.mActionChips = list;
            return this;
        }

        public Builder setAuthenticationEntries(List<AuthenticationEntry> list) {
            this.mAuthenticationEntries = list;
            return this;
        }

        public Builder setRemoteEntry(Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public GetCredentialProviderInfo build() {
            return new GetCredentialProviderInfo(this.mProviderName, this.mCredentialEntries, this.mActionChips, this.mAuthenticationEntries, this.mRemoteEntry);
        }
    }
}
