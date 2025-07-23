package android.util;

import java.io.BufferedReader;
import java.io.IOException;

@Deprecated
/* loaded from: classes4.dex */
public class EventLogTags {
    public Description get(int i) {
        return null;
    }

    public Description get(String str) {
        return null;
    }

    public static class Description {
        public final String mName;
        public final int mTag;

        Description(int i, String str) {
            this.mTag = i;
            this.mName = str;
        }
    }

    public EventLogTags() throws IOException {
    }

    public EventLogTags(BufferedReader bufferedReader) throws IOException {
    }
}
