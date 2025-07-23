package com.android.internal.util.dump;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public class DualDumpOutputStream {
    private static final String LOG_TAG = "DualDumpOutputStream";
    private final LinkedList<DumpObject> mDumpObjects;
    private final IndentingPrintWriter mIpw;
    private final ProtoOutputStream mProtoStream;

    private static abstract class Dumpable {
        final String name;

        abstract void print(IndentingPrintWriter indentingPrintWriter, boolean z);

        private Dumpable(String str) {
            this.name = str;
        }
    }

    private static class DumpObject extends Dumpable {
        private final LinkedHashMap<String, ArrayList<Dumpable>> mSubObjects;

        private DumpObject(String str) {
            super(str);
            this.mSubObjects = new LinkedHashMap<>();
        }

        @Override // com.android.internal.util.dump.DualDumpOutputStream.Dumpable
        void print(IndentingPrintWriter indentingPrintWriter, boolean z) {
            if (z) {
                indentingPrintWriter.println(this.name + "={");
            } else {
                indentingPrintWriter.println("{");
            }
            indentingPrintWriter.increaseIndent();
            for (ArrayList<Dumpable> arrayList : this.mSubObjects.values()) {
                int size = arrayList.size();
                if (size == 1) {
                    arrayList.get(0).print(indentingPrintWriter, true);
                } else {
                    indentingPrintWriter.println(arrayList.get(0).name + "=[");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < size; i++) {
                        arrayList.get(i).print(indentingPrintWriter, false);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println(NavigationBarInflaterView.SIZE_MOD_END);
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("}");
        }

        public void add(String str, Dumpable dumpable) {
            ArrayList<Dumpable> arrayList = this.mSubObjects.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.mSubObjects.put(str, arrayList);
            }
            arrayList.add(dumpable);
        }
    }

    private static class DumpField extends Dumpable {
        private final String mValue;

        private DumpField(String str, String str2) {
            super(str);
            this.mValue = str2;
        }

        @Override // com.android.internal.util.dump.DualDumpOutputStream.Dumpable
        void print(IndentingPrintWriter indentingPrintWriter, boolean z) {
            if (z) {
                indentingPrintWriter.println(this.name + "=" + this.mValue);
                return;
            }
            indentingPrintWriter.println(this.mValue);
        }
    }

    public DualDumpOutputStream(ProtoOutputStream protoOutputStream) {
        this.mDumpObjects = new LinkedList<>();
        this.mProtoStream = protoOutputStream;
        this.mIpw = null;
    }

    public DualDumpOutputStream(IndentingPrintWriter indentingPrintWriter) {
        LinkedList<DumpObject> linkedList = new LinkedList<>();
        this.mDumpObjects = linkedList;
        this.mProtoStream = null;
        this.mIpw = indentingPrintWriter;
        linkedList.add(new DumpObject(null));
    }

    public void write(String str, long j, double d) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, d);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, String.valueOf(d)));
        }
    }

    public void write(String str, long j, boolean z) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, z);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, String.valueOf(z)));
        }
    }

    public void write(String str, long j, int i) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, i);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, String.valueOf(i)));
        }
    }

    public void write(String str, long j, float f) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, f);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, String.valueOf(f)));
        }
    }

    public void write(String str, long j, byte[] bArr) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, bArr);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, Arrays.toString(bArr)));
        }
    }

    public void write(String str, long j, long j2) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, j2);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, String.valueOf(j2)));
        }
    }

    public void write(String str, long j, String str2) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(j, str2);
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, String.valueOf(str2)));
        }
    }

    public long start(String str, long j) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            return protoOutputStream.start(j);
        }
        DumpObject dumpObject = new DumpObject(str);
        this.mDumpObjects.getLast().add(str, dumpObject);
        this.mDumpObjects.addLast(dumpObject);
        return System.identityHashCode(dumpObject);
    }

    public void end(long j) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.end(j);
            return;
        }
        if (System.identityHashCode(this.mDumpObjects.getLast()) != j) {
            Log.w(LOG_TAG, "Unexpected token for ending " + this.mDumpObjects.getLast().name + " at " + Arrays.toString(Thread.currentThread().getStackTrace()));
        }
        this.mDumpObjects.removeLast();
    }

    public void flush() {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.flush();
            return;
        }
        if (this.mDumpObjects.size() == 1) {
            this.mDumpObjects.getFirst().print(this.mIpw, false);
            this.mDumpObjects.clear();
            this.mDumpObjects.add(new DumpObject(null));
        }
        this.mIpw.flush();
    }

    public void writeNested(String str, byte[] bArr) {
        if (this.mIpw == null) {
            Log.w(LOG_TAG, "writeNested does not work for proto logging");
        } else {
            this.mDumpObjects.getLast().add(str, new DumpField(str, new String(bArr, StandardCharsets.UTF_8).trim()));
        }
    }

    public boolean isProto() {
        return this.mProtoStream != null;
    }
}
