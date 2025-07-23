package android.content.integrity;

import android.annotation.SystemApi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public class RuleSet {
    private final List<Rule> mRules;
    private final String mVersion;

    private RuleSet(String str, List<Rule> list) {
        this.mVersion = str;
        this.mRules = Collections.unmodifiableList(list);
    }

    public String getVersion() {
        return this.mVersion;
    }

    public List<Rule> getRules() {
        return this.mRules;
    }

    public static class Builder {
        private List<Rule> mRules = new ArrayList();
        private String mVersion;

        public Builder setVersion(String str) {
            this.mVersion = str;
            return this;
        }

        public Builder addRules(List<Rule> list) {
            this.mRules.addAll(list);
            return this;
        }

        public RuleSet build() {
            Objects.requireNonNull(this.mVersion);
            return new RuleSet(this.mVersion, this.mRules);
        }
    }
}
