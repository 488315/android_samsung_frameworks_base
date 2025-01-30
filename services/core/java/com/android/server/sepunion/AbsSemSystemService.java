package com.android.server.sepunion;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public interface AbsSemSystemService {
    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void onBootPhase(int i);

    void onCreate(Bundle bundle);

    default void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
    }
}
