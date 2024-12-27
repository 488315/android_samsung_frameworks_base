package com.android.internal.logging.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;

import java.io.IOException;

/* loaded from: classes5.dex */
public interface OlogProto {

    public static final class OlogTestEnum extends MessageNano {
        public static final int PERFLOG_ACTIVITYSLOW = 8;
        public static final int PERFLOG_AMPSS = 18;
        public static final int PERFLOG_APPLAUNCH = 4;
        public static final int PERFLOG_ARGOS = 3;
        public static final int PERFLOG_BIGDATA = 25;
        public static final int PERFLOG_BROADCAST = 9;
        public static final int PERFLOG_CPU = 13;
        public static final int PERFLOG_CPUFREQ = 15;
        public static final int PERFLOG_CPUTOP = 11;
        public static final int PERFLOG_CRI = 4;
        public static final int PERFLOG_DEF = 0;
        public static final int PERFLOG_EVT = 2;
        public static final int PERFLOG_EXCESSIVECPUUSAGE = 7;
        public static final int PERFLOG_INPUTD = 17;
        public static final int PERFLOG_IPCSTARVE = 20;
        public static final int PERFLOG_JANK = 27;
        public static final int PERFLOG_LCD = 12;
        public static final int PERFLOG_LCDV = 2;
        public static final int PERFLOG_LOADAPK = 5;
        public static final int PERFLOG_LOCKCONTENTION = 14;
        public static final int PERFLOG_LOG = 1;
        public static final int PERFLOG_MAINLOOPER = 6;
        public static final int PERFLOG_MEMPRESSURE = 16;
        public static final int PERFLOG_MUTEX = 22;
        public static final int PERFLOG_PERFETTOLOGGINGENABLED = 24;
        public static final int PERFLOG_PSI = 26;
        public static final int PERFLOG_SCREENSHOT = 21;
        public static final int PERFLOG_SERVICEMANAGERSLOW = 19;
        public static final int PERFLOG_STORE = 10;
        public static final int PERFLOG_SYSTEMSERVER = 23;
        public static final int PERFLOG_UNKNOWN = 0;
        public static final int PERFLOG_WRN = 3;
        private static volatile OlogTestEnum[] _emptyArray;

        public static OlogTestEnum[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new OlogTestEnum[0];
                    }
                }
            }
            return _emptyArray;
        }

        public OlogTestEnum() {
            clear();
        }

        public OlogTestEnum clear() {
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
        public com.android.internal.logging.nano.OlogProto.OlogTestEnum mergeFrom(
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
                        + " com.android.internal.logging.nano.OlogProto.OlogTestEnum.mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano):com.android.internal.logging.nano.OlogProto$OlogTestEnum");
        }

        public static OlogTestEnum parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (OlogTestEnum) MessageNano.mergeFrom(new OlogTestEnum(), data);
        }

        public static OlogTestEnum parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new OlogTestEnum().mergeFrom(input);
        }
    }
}
