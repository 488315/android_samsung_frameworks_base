package com.samsung.android.server.packagefeature;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.server.util.CoreLogger;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes2.dex */
public interface PackageFeatureController extends Consumer {
    void dump(PrintWriter printWriter);

    boolean executeShellCommand(PrintWriter printWriter, String[] strArr, String str);

    Set getGroupNames();

    String getScpmVersion(String str);

    void registerCallback(PackageFeature packageFeature, PackageFeatureCallback packageFeatureCallback);

    void setFileDescriptorFunction(Function function);

    void startController(Context context, Handler handler, CoreLogger coreLogger);

    void updateGroupData(String str);

    @Override // java.util.function.Consumer
    default void accept(String str) {
        updateGroupData(str);
    }
}
