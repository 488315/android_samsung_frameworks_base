package com.android.server.criticalevents.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.uid != 0) {
                output.writeInt32(1, this.uid);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.uid != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(1, this.uid);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public ExcessiveBinderCalls mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 8:
                        this.uid = input.readInt32();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static ExcessiveBinderCalls parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (ExcessiveBinderCalls) MessageNano.mergeFrom(new ExcessiveBinderCalls(), data);
        }

        public static ExcessiveBinderCalls parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new ExcessiveBinderCalls().mergeFrom(input);
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

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:103)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages mergeFrom(
                com.android.framework.protobuf.nano.CodedInputByteBufferNano r3)
                throws java.io.IOException {
            /*
                r2 = this;
            L1:
                int r0 = r3.readTag()
                switch(r0) {
                    case 0: goto Lf;
                    default: goto L8;
                }
            L8:
                boolean r1 = com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(r3, r0)
                if (r1 != 0) goto L10
                return r2
            Lf:
                return r2
            L10:
                goto L1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages.mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano):com.android.server.criticalevents.nano.CriticalEventProto$InstallPackages");
        }

        public static InstallPackages parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (InstallPackages) MessageNano.mergeFrom(new InstallPackages(), data);
        }

        public static InstallPackages parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new InstallPackages().mergeFrom(input);
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

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:103)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted
                mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano r3)
                        throws java.io.IOException {
            /*
                r2 = this;
            L1:
                int r0 = r3.readTag()
                switch(r0) {
                    case 0: goto Lf;
                    default: goto L8;
                }
            L8:
                boolean r1 = com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(r3, r0)
                if (r1 != 0) goto L10
                return r2
            Lf:
                return r2
            L10:
                goto L1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted.mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano):com.android.server.criticalevents.nano.CriticalEventProto$SystemServerStarted");
        }

        public static SystemServerStarted parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (SystemServerStarted) MessageNano.mergeFrom(new SystemServerStarted(), data);
        }

        public static SystemServerStarted parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new SystemServerStarted().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.subject.equals("")) {
                output.writeString(1, this.subject);
            }
            if (!this.uuid.equals("")) {
                output.writeString(2, this.uuid);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            if (!this.uuid.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(2, this.uuid);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public Watchdog mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.subject = input.readString();
                        break;
                    case 18:
                        this.uuid = input.readString();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static Watchdog parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (Watchdog) MessageNano.mergeFrom(new Watchdog(), data);
        }

        public static Watchdog parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new Watchdog().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.subject.equals("")) {
                output.writeString(1, this.subject);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public HalfWatchdog mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.subject = input.readString();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static HalfWatchdog parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (HalfWatchdog) MessageNano.mergeFrom(new HalfWatchdog(), data);
        }

        public static HalfWatchdog parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new HalfWatchdog().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.subject.equals("")) {
                output.writeString(1, this.subject);
            }
            if (!this.process.equals("")) {
                output.writeString(2, this.process);
            }
            if (this.pid != 0) {
                output.writeInt32(3, this.pid);
            }
            if (this.uid != 0) {
                output.writeInt32(4, this.uid);
            }
            if (this.processClass != 0) {
                output.writeInt32(5, this.processClass);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            if (!this.process.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.process);
            }
            if (this.pid != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.pid);
            }
            if (this.uid != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(4, this.uid);
            }
            if (this.processClass != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(5, this.processClass);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public AppNotResponding mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.subject = input.readString();
                        break;
                    case 18:
                        this.process = input.readString();
                        break;
                    case 24:
                        this.pid = input.readInt32();
                        break;
                    case 32:
                        this.uid = input.readInt32();
                        break;
                    case 40:
                        int value = input.readInt32();
                        switch (value) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.processClass = value;
                                break;
                        }
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static AppNotResponding parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (AppNotResponding) MessageNano.mergeFrom(new AppNotResponding(), data);
        }

        public static AppNotResponding parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new AppNotResponding().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.exceptionClass.equals("")) {
                output.writeString(1, this.exceptionClass);
            }
            if (!this.process.equals("")) {
                output.writeString(2, this.process);
            }
            if (this.pid != 0) {
                output.writeInt32(3, this.pid);
            }
            if (this.uid != 0) {
                output.writeInt32(4, this.uid);
            }
            if (this.processClass != 0) {
                output.writeInt32(5, this.processClass);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.exceptionClass.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.exceptionClass);
            }
            if (!this.process.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.process);
            }
            if (this.pid != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.pid);
            }
            if (this.uid != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(4, this.uid);
            }
            if (this.processClass != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(5, this.processClass);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public JavaCrash mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.exceptionClass = input.readString();
                        break;
                    case 18:
                        this.process = input.readString();
                        break;
                    case 24:
                        this.pid = input.readInt32();
                        break;
                    case 32:
                        this.uid = input.readInt32();
                        break;
                    case 40:
                        int value = input.readInt32();
                        switch (value) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.processClass = value;
                                break;
                        }
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static JavaCrash parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (JavaCrash) MessageNano.mergeFrom(new JavaCrash(), data);
        }

        public static JavaCrash parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new JavaCrash().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.process.equals("")) {
                output.writeString(1, this.process);
            }
            if (this.pid != 0) {
                output.writeInt32(2, this.pid);
            }
            if (this.uid != 0) {
                output.writeInt32(3, this.uid);
            }
            if (this.processClass != 0) {
                output.writeInt32(4, this.processClass);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.process.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.process);
            }
            if (this.pid != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(2, this.pid);
            }
            if (this.uid != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.uid);
            }
            if (this.processClass != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(4, this.processClass);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public NativeCrash mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.process = input.readString();
                        break;
                    case 16:
                        this.pid = input.readInt32();
                        break;
                    case 24:
                        this.uid = input.readInt32();
                        break;
                    case 32:
                        int value = input.readInt32();
                        switch (value) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.processClass = value;
                                break;
                        }
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static NativeCrash parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (NativeCrash) MessageNano.mergeFrom(new NativeCrash(), data);
        }

        public static NativeCrash parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new NativeCrash().mergeFrom(input);
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

    public CriticalEventProto setWatchdog(Watchdog value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 2;
        this.event_ = value;
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

    public CriticalEventProto setHalfWatchdog(HalfWatchdog value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 3;
        this.event_ = value;
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

    public CriticalEventProto setAnr(AppNotResponding value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 4;
        this.event_ = value;
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

    public CriticalEventProto setJavaCrash(JavaCrash value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 5;
        this.event_ = value;
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

    public CriticalEventProto setNativeCrash(NativeCrash value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 6;
        this.event_ = value;
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

    public CriticalEventProto setSystemServerStarted(SystemServerStarted value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 7;
        this.event_ = value;
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

    public CriticalEventProto setInstallPackages(InstallPackages value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 8;
        this.event_ = value;
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

    public CriticalEventProto setExcessiveBinderCalls(ExcessiveBinderCalls value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.eventCase_ = 9;
        this.event_ = value;
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
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.timestampMs != 0) {
            output.writeInt64(1, this.timestampMs);
        }
        if (this.eventCase_ == 2) {
            output.writeMessage(2, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 3) {
            output.writeMessage(3, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 4) {
            output.writeMessage(4, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 5) {
            output.writeMessage(5, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 6) {
            output.writeMessage(6, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 7) {
            output.writeMessage(7, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 8) {
            output.writeMessage(8, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 9) {
            output.writeMessage(9, (MessageNano) this.event_);
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.timestampMs != 0) {
            size += CodedOutputByteBufferNano.computeInt64Size(1, this.timestampMs);
        }
        if (this.eventCase_ == 2) {
            size += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 3) {
            size += CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 4) {
            size += CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 5) {
            size += CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 6) {
            size += CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 7) {
            size += CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 8) {
            size += CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano) this.event_);
        }
        if (this.eventCase_ == 9) {
            return size
                    + CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano) this.event_);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public CriticalEventProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 8:
                    this.timestampMs = input.readInt64();
                    break;
                case 18:
                    if (this.eventCase_ != 2) {
                        this.event_ = new Watchdog();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 2;
                    break;
                case 26:
                    if (this.eventCase_ != 3) {
                        this.event_ = new HalfWatchdog();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 3;
                    break;
                case 34:
                    if (this.eventCase_ != 4) {
                        this.event_ = new AppNotResponding();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 4;
                    break;
                case 42:
                    if (this.eventCase_ != 5) {
                        this.event_ = new JavaCrash();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 5;
                    break;
                case 50:
                    if (this.eventCase_ != 6) {
                        this.event_ = new NativeCrash();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 6;
                    break;
                case 58:
                    if (this.eventCase_ != 7) {
                        this.event_ = new SystemServerStarted();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 7;
                    break;
                case 66:
                    if (this.eventCase_ != 8) {
                        this.event_ = new InstallPackages();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 8;
                    break;
                case 74:
                    if (this.eventCase_ != 9) {
                        this.event_ = new ExcessiveBinderCalls();
                    }
                    input.readMessage((MessageNano) this.event_);
                    this.eventCase_ = 9;
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static CriticalEventProto parseFrom(byte[] data)
            throws InvalidProtocolBufferNanoException {
        return (CriticalEventProto) MessageNano.mergeFrom(new CriticalEventProto(), data);
    }

    public static CriticalEventProto parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new CriticalEventProto().mergeFrom(input);
    }
}
