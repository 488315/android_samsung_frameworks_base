package com.android.server.criticalevents.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class CriticalEventProto extends MessageNano {
    public static final int ANR_FIELD_NUMBER = 4;
    public static final int DATA_APP = 1;
    public static final int EXCESSIVE_BINDER_CALLS_FIELD_NUMBER = 9;
    public static final int HALF_WATCHDOG_FIELD_NUMBER = 3;
    public static final int INSTALL_PACKAGES_FIELD_NUMBER = 8;
    public static final int JAVA_CRASH_FIELD_NUMBER = 5;
    public static final int NATIVE_CRASH_FIELD_NUMBER = 6;
    public static final int PROCESS_CLASS_UNKNOWN = 0;
    public static final int SYSTEM_APP = 2;
    public static final int SYSTEM_SERVER = 3;
    public static final int SYSTEM_SERVER_STARTED_FIELD_NUMBER = 7;
    public static final int WATCHDOG_FIELD_NUMBER = 2;
    private static volatile CriticalEventProto[] _emptyArray;
    private int eventCase_ = 0;
    private Object event_;
    public long timestampMs;

    public static final class ExcessiveBinderCalls extends MessageNano {
        private static volatile ExcessiveBinderCalls[] _emptyArray;
        public int uid;

        public static ExcessiveBinderCalls[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ExcessiveBinderCalls[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ExcessiveBinderCalls() {
            clear();
        }

        public ExcessiveBinderCalls clear() {
            this.uid = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.uid;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.uid;
            return i != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ExcessiveBinderCalls mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag != 8) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.uid = codedInputByteBufferNano.readInt32();
                }
            }
            return this;
        }

        public static ExcessiveBinderCalls parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ExcessiveBinderCalls) MessageNano.mergeFrom(new ExcessiveBinderCalls(), bArr);
        }

        public static ExcessiveBinderCalls parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ExcessiveBinderCalls().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class InstallPackages extends MessageNano {
        private static volatile InstallPackages[] _emptyArray;

        public static InstallPackages[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new InstallPackages[0];
                    }
                }
            }
            return _emptyArray;
        }

        public InstallPackages() {
            clear();
        }

        public InstallPackages clear() {
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public InstallPackages mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int readTag;
            do {
                readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
            } while (WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag));
            return this;
        }

        public static InstallPackages parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (InstallPackages) MessageNano.mergeFrom(new InstallPackages(), bArr);
        }

        public static InstallPackages parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new InstallPackages().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class SystemServerStarted extends MessageNano {
        private static volatile SystemServerStarted[] _emptyArray;

        public static SystemServerStarted[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new SystemServerStarted[0];
                    }
                }
            }
            return _emptyArray;
        }

        public SystemServerStarted() {
            clear();
        }

        public SystemServerStarted clear() {
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public SystemServerStarted mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int readTag;
            do {
                readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
            } while (WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag));
            return this;
        }

        public static SystemServerStarted parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (SystemServerStarted) MessageNano.mergeFrom(new SystemServerStarted(), bArr);
        }

        public static SystemServerStarted parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SystemServerStarted().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class Watchdog extends MessageNano {
        private static volatile Watchdog[] _emptyArray;
        public String subject;
        public String uuid;

        public static Watchdog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new Watchdog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public Watchdog() {
            clear();
        }

        public Watchdog clear() {
            this.subject = "";
            this.uuid = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.subject.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.subject);
            }
            if (!this.uuid.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.uuid);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            return !this.uuid.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.uuid) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public Watchdog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.subject = codedInputByteBufferNano.readString();
                } else if (readTag != 18) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.uuid = codedInputByteBufferNano.readString();
                }
            }
            return this;
        }

        public static Watchdog parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (Watchdog) MessageNano.mergeFrom(new Watchdog(), bArr);
        }

        public static Watchdog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new Watchdog().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class HalfWatchdog extends MessageNano {
        private static volatile HalfWatchdog[] _emptyArray;
        public String subject;

        public static HalfWatchdog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new HalfWatchdog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public HalfWatchdog() {
            clear();
        }

        public HalfWatchdog clear() {
            this.subject = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.subject.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.subject);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            return !this.subject.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(1, this.subject) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public HalfWatchdog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag != 10) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    this.subject = codedInputByteBufferNano.readString();
                }
            }
            return this;
        }

        public static HalfWatchdog parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (HalfWatchdog) MessageNano.mergeFrom(new HalfWatchdog(), bArr);
        }

        public static HalfWatchdog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new HalfWatchdog().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class AppNotResponding extends MessageNano {
        private static volatile AppNotResponding[] _emptyArray;
        public int pid;
        public String process;
        public int processClass;
        public String subject;
        public int uid;

        public static AppNotResponding[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new AppNotResponding[0];
                    }
                }
            }
            return _emptyArray;
        }

        public AppNotResponding() {
            clear();
        }

        public AppNotResponding clear() {
            this.subject = "";
            this.process = "";
            this.pid = 0;
            this.uid = 0;
            this.processClass = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.subject.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.subject);
            }
            if (!this.process.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.process);
            }
            int i = this.pid;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(3, i);
            }
            int i2 = this.uid;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i2);
            }
            int i3 = this.processClass;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i3);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            if (!this.process.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.process);
            }
            int i = this.pid;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
            }
            int i2 = this.uid;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i2);
            }
            int i3 = this.processClass;
            return i3 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(5, i3) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public AppNotResponding mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.subject = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.process = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    this.pid = codedInputByteBufferNano.readInt32();
                } else if (readTag == 32) {
                    this.uid = codedInputByteBufferNano.readInt32();
                } else if (readTag != 40) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2 || readInt32 == 3) {
                        this.processClass = readInt32;
                    }
                }
            }
            return this;
        }

        public static AppNotResponding parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (AppNotResponding) MessageNano.mergeFrom(new AppNotResponding(), bArr);
        }

        public static AppNotResponding parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new AppNotResponding().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class JavaCrash extends MessageNano {
        private static volatile JavaCrash[] _emptyArray;
        public String exceptionClass;
        public int pid;
        public String process;
        public int processClass;
        public int uid;

        public static JavaCrash[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new JavaCrash[0];
                    }
                }
            }
            return _emptyArray;
        }

        public JavaCrash() {
            clear();
        }

        public JavaCrash clear() {
            this.exceptionClass = "";
            this.process = "";
            this.pid = 0;
            this.uid = 0;
            this.processClass = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.exceptionClass.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.exceptionClass);
            }
            if (!this.process.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.process);
            }
            int i = this.pid;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(3, i);
            }
            int i2 = this.uid;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i2);
            }
            int i3 = this.processClass;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i3);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.exceptionClass.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.exceptionClass);
            }
            if (!this.process.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.process);
            }
            int i = this.pid;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i);
            }
            int i2 = this.uid;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i2);
            }
            int i3 = this.processClass;
            return i3 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(5, i3) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public JavaCrash mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.exceptionClass = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.process = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    this.pid = codedInputByteBufferNano.readInt32();
                } else if (readTag == 32) {
                    this.uid = codedInputByteBufferNano.readInt32();
                } else if (readTag != 40) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2 || readInt32 == 3) {
                        this.processClass = readInt32;
                    }
                }
            }
            return this;
        }

        public static JavaCrash parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (JavaCrash) MessageNano.mergeFrom(new JavaCrash(), bArr);
        }

        public static JavaCrash parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new JavaCrash().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class NativeCrash extends MessageNano {
        private static volatile NativeCrash[] _emptyArray;
        public int pid;
        public String process;
        public int processClass;
        public int uid;

        public static NativeCrash[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new NativeCrash[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NativeCrash() {
            clear();
        }

        public NativeCrash clear() {
            this.process = "";
            this.pid = 0;
            this.uid = 0;
            this.processClass = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.process.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.process);
            }
            int i = this.pid;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(2, i);
            }
            int i2 = this.uid;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i2);
            }
            int i3 = this.processClass;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i3);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.process.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.process);
            }
            int i = this.pid;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            int i2 = this.uid;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i2);
            }
            int i3 = this.processClass;
            return i3 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, i3) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public NativeCrash mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 10) {
                    this.process = codedInputByteBufferNano.readString();
                } else if (readTag == 16) {
                    this.pid = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    this.uid = codedInputByteBufferNano.readInt32();
                } else if (readTag != 32) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2 || readInt32 == 3) {
                        this.processClass = readInt32;
                    }
                }
            }
            return this;
        }

        public static NativeCrash parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (NativeCrash) MessageNano.mergeFrom(new NativeCrash(), bArr);
        }

        public static NativeCrash parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NativeCrash().mergeFrom(codedInputByteBufferNano);
        }
    }

    public int getEventCase() {
        return this.eventCase_;
    }

    public CriticalEventProto clearEvent() {
        this.eventCase_ = 0;
        this.event_ = null;
        return this;
    }

    public static CriticalEventProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new CriticalEventProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public boolean hasWatchdog() {
        return this.eventCase_ == 2;
    }

    public Watchdog getWatchdog() {
        if (this.eventCase_ == 2) {
            return (Watchdog) this.event_;
        }
        return null;
    }

    public CriticalEventProto setWatchdog(Watchdog watchdog) {
        watchdog.getClass();
        this.eventCase_ = 2;
        this.event_ = watchdog;
        return this;
    }

    public boolean hasHalfWatchdog() {
        return this.eventCase_ == 3;
    }

    public HalfWatchdog getHalfWatchdog() {
        if (this.eventCase_ == 3) {
            return (HalfWatchdog) this.event_;
        }
        return null;
    }

    public CriticalEventProto setHalfWatchdog(HalfWatchdog halfWatchdog) {
        halfWatchdog.getClass();
        this.eventCase_ = 3;
        this.event_ = halfWatchdog;
        return this;
    }

    public boolean hasAnr() {
        return this.eventCase_ == 4;
    }

    public AppNotResponding getAnr() {
        if (this.eventCase_ == 4) {
            return (AppNotResponding) this.event_;
        }
        return null;
    }

    public CriticalEventProto setAnr(AppNotResponding appNotResponding) {
        appNotResponding.getClass();
        this.eventCase_ = 4;
        this.event_ = appNotResponding;
        return this;
    }

    public boolean hasJavaCrash() {
        return this.eventCase_ == 5;
    }

    public JavaCrash getJavaCrash() {
        if (this.eventCase_ == 5) {
            return (JavaCrash) this.event_;
        }
        return null;
    }

    public CriticalEventProto setJavaCrash(JavaCrash javaCrash) {
        javaCrash.getClass();
        this.eventCase_ = 5;
        this.event_ = javaCrash;
        return this;
    }

    public boolean hasNativeCrash() {
        return this.eventCase_ == 6;
    }

    public NativeCrash getNativeCrash() {
        if (this.eventCase_ == 6) {
            return (NativeCrash) this.event_;
        }
        return null;
    }

    public CriticalEventProto setNativeCrash(NativeCrash nativeCrash) {
        nativeCrash.getClass();
        this.eventCase_ = 6;
        this.event_ = nativeCrash;
        return this;
    }

    public boolean hasSystemServerStarted() {
        return this.eventCase_ == 7;
    }

    public SystemServerStarted getSystemServerStarted() {
        if (this.eventCase_ == 7) {
            return (SystemServerStarted) this.event_;
        }
        return null;
    }

    public CriticalEventProto setSystemServerStarted(SystemServerStarted systemServerStarted) {
        systemServerStarted.getClass();
        this.eventCase_ = 7;
        this.event_ = systemServerStarted;
        return this;
    }

    public boolean hasInstallPackages() {
        return this.eventCase_ == 8;
    }

    public InstallPackages getInstallPackages() {
        if (this.eventCase_ == 8) {
            return (InstallPackages) this.event_;
        }
        return null;
    }

    public CriticalEventProto setInstallPackages(InstallPackages installPackages) {
        installPackages.getClass();
        this.eventCase_ = 8;
        this.event_ = installPackages;
        return this;
    }

    public boolean hasExcessiveBinderCalls() {
        return this.eventCase_ == 9;
    }

    public ExcessiveBinderCalls getExcessiveBinderCalls() {
        if (this.eventCase_ == 9) {
            return (ExcessiveBinderCalls) this.event_;
        }
        return null;
    }

    public CriticalEventProto setExcessiveBinderCalls(ExcessiveBinderCalls excessiveBinderCalls) {
        excessiveBinderCalls.getClass();
        this.eventCase_ = 9;
        this.event_ = excessiveBinderCalls;
        return this;
    }

    public CriticalEventProto() {
        clear();
    }

    public CriticalEventProto clear() {
        this.timestampMs = 0L;
        clearEvent();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        long j = this.timestampMs;
        if (j != 0) {
            codedOutputByteBufferNano.writeInt64(1, j);
        }
        if (this.eventCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 3) {
            codedOutputByteBufferNano.writeMessage(3, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 4) {
            codedOutputByteBufferNano.writeMessage(4, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 5) {
            codedOutputByteBufferNano.writeMessage(5, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 6) {
            codedOutputByteBufferNano.writeMessage(6, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 7) {
            codedOutputByteBufferNano.writeMessage(7, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 8) {
            codedOutputByteBufferNano.writeMessage(8, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 9) {
            codedOutputByteBufferNano.writeMessage(9, (MessageNano) this.event_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        long j = this.timestampMs;
        if (j != 0) {
            computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
        }
        if (this.eventCase_ == 2) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 3) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 4) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 5) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 6) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 7) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 8) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano) this.event_);
        }
        return this.eventCase_ == 9 ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano) this.event_) : computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public CriticalEventProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == 8) {
                this.timestampMs = codedInputByteBufferNano.readInt64();
            } else if (readTag == 18) {
                if (this.eventCase_ != 2) {
                    this.event_ = new Watchdog();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 2;
            } else if (readTag == 26) {
                if (this.eventCase_ != 3) {
                    this.event_ = new HalfWatchdog();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 3;
            } else if (readTag == 34) {
                if (this.eventCase_ != 4) {
                    this.event_ = new AppNotResponding();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 4;
            } else if (readTag == 42) {
                if (this.eventCase_ != 5) {
                    this.event_ = new JavaCrash();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 5;
            } else if (readTag == 50) {
                if (this.eventCase_ != 6) {
                    this.event_ = new NativeCrash();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 6;
            } else if (readTag == 58) {
                if (this.eventCase_ != 7) {
                    this.event_ = new SystemServerStarted();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 7;
            } else if (readTag == 66) {
                if (this.eventCase_ != 8) {
                    this.event_ = new InstallPackages();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 8;
            } else if (readTag != 74) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    break;
                }
            } else {
                if (this.eventCase_ != 9) {
                    this.event_ = new ExcessiveBinderCalls();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.event_);
                this.eventCase_ = 9;
            }
        }
        return this;
    }

    public static CriticalEventProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (CriticalEventProto) MessageNano.mergeFrom(new CriticalEventProto(), bArr);
    }

    public static CriticalEventProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new CriticalEventProto().mergeFrom(codedInputByteBufferNano);
    }
}
