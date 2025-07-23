package android.view.inspector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class IntFlagMapping {
    private final List<Flag> mFlags = new ArrayList();

    public Set<String> get(int i) {
        HashSet hashSet = new HashSet(this.mFlags.size());
        for (Flag flag : this.mFlags) {
            if (flag.isEnabledFor(i)) {
                hashSet.add(flag.mName);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public void add(int i, int i2, String str) {
        this.mFlags.add(new Flag(i, i2, str));
    }

    private static final class Flag {
        private final int mMask;
        private final String mName;
        private final int mTarget;

        private Flag(int i, int i2, String str) {
            this.mTarget = i2;
            this.mMask = i;
            this.mName = (String) Objects.requireNonNull(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEnabledFor(int i) {
            return (i & this.mMask) == this.mTarget;
        }
    }
}
