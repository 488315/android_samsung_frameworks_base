package android.credentials.selection;

import android.annotation.SystemApi;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class CreateCredentialProviderInfo {
    private final String mProviderName;
    private final Entry mRemoteEntry;
    private final List<Entry> mSaveEntries;

    CreateCredentialProviderInfo(String str, List<Entry> list, Entry entry) {
        this.mProviderName = (String) Preconditions.checkStringNotEmpty(str);
        this.mSaveEntries = new ArrayList(list);
        this.mRemoteEntry = entry;
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

        public Builder(String str) {
            this.mProviderName = (String) Preconditions.checkStringNotEmpty(str);
        }

        public Builder setSaveEntries(List<Entry> list) {
            this.mSaveEntries = list;
            return this;
        }

        public Builder setRemoteEntry(Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public CreateCredentialProviderInfo build() {
            return new CreateCredentialProviderInfo(this.mProviderName, this.mSaveEntries, this.mRemoteEntry);
        }
    }
}
