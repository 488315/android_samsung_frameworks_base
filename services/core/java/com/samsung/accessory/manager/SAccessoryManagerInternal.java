package com.samsung.accessory.manager;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class SAccessoryManagerInternal {
    public abstract void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);
}
