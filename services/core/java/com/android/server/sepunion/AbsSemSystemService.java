package com.android.server.sepunion;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.ShellCallback;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface AbsSemSystemService {
    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void onBootPhase(int i);

    void onCreate(Bundle bundle);

    default void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {}
}
