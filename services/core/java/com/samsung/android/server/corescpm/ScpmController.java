package com.samsung.android.server.corescpm;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.server.util.CoreLogger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public interface ScpmController {
    void dump(PrintWriter printWriter);

    FileDescriptor getFileDescriptor(String str);

    void registerScpm(Context context, Handler handler, Set set, Consumer consumer, CoreLogger coreLogger);

    public abstract class ConsumerInfo {
        public final String mAppId;
        public final String mPackageName;
        public final String mReceiverPackageName;
        public final String mVersion;

        public ConsumerInfo(String str, String str2, String str3, String str4) {
            this.mPackageName = str;
            this.mReceiverPackageName = str2;
            this.mAppId = str3;
            this.mVersion = str4;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, this.mReceiverPackageName, this.mAppId, this.mVersion);
        }
    }
}
