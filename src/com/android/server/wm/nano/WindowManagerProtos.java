package com.android.server.wm.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public interface WindowManagerProtos {

    public static final class TaskSnapshotProto extends MessageNano {
        private static volatile TaskSnapshotProto[] _emptyArray;
        public int appearance;
        public int cutoutInsetBottom;
        public int cutoutInsetLeft;
        public int cutoutInsetRight;
        public int cutoutInsetTop;
        public long id;
        public int insetBottom;
        public int insetLeft;
        public int insetRight;
        public int insetTop;
        public boolean isRealSnapshot;
        public boolean isTranslucent;
        public float legacyScale;
        public int letterboxInsetBottom;
        public int letterboxInsetLeft;
        public int letterboxInsetRight;
        public int letterboxInsetTop;
        public int orientation;
        public int rotation;
        public int systemUiVisibility;
        public int taskHeight;
        public int taskWidth;
        public String topActivityComponent;
        public int uiMode;
        public int windowingMode;

        public static TaskSnapshotProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new TaskSnapshotProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public TaskSnapshotProto() {
            clear();
        }

        public TaskSnapshotProto clear() {
            this.orientation = 0;
            this.insetLeft = 0;
            this.insetTop = 0;
            this.insetRight = 0;
            this.insetBottom = 0;
            this.isRealSnapshot = false;
            this.windowingMode = 0;
            this.systemUiVisibility = 0;
            this.isTranslucent = false;
            this.topActivityComponent = "";
            this.legacyScale = 0.0f;
            this.id = 0L;
            this.rotation = 0;
            this.taskWidth = 0;
            this.taskHeight = 0;
            this.appearance = 0;
            this.letterboxInsetLeft = 0;
            this.letterboxInsetTop = 0;
            this.letterboxInsetRight = 0;
            this.letterboxInsetBottom = 0;
            this.uiMode = 0;
            this.cutoutInsetLeft = 0;
            this.cutoutInsetTop = 0;
            this.cutoutInsetRight = 0;
            this.cutoutInsetBottom = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.orientation;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.insetLeft;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int i3 = this.insetTop;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i3);
            }
            int i4 = this.insetRight;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i4);
            }
            int i5 = this.insetBottom;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i5);
            }
            boolean z = this.isRealSnapshot;
            if (z) {
                codedOutputByteBufferNano.writeBool(6, z);
            }
            int i6 = this.windowingMode;
            if (i6 != 0) {
                codedOutputByteBufferNano.writeInt32(7, i6);
            }
            int i7 = this.systemUiVisibility;
            if (i7 != 0) {
                codedOutputByteBufferNano.writeInt32(8, i7);
            }
            boolean z2 = this.isTranslucent;
            if (z2) {
                codedOutputByteBufferNano.writeBool(9, z2);
            }
            if (!this.topActivityComponent.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.legacyScale) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(11, this.legacyScale);
            }
            long j = this.id;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(12, j);
            }
            int i8 = this.rotation;
            if (i8 != 0) {
                codedOutputByteBufferNano.writeInt32(13, i8);
            }
            int i9 = this.taskWidth;
            if (i9 != 0) {
                codedOutputByteBufferNano.writeInt32(14, i9);
            }
            int i10 = this.taskHeight;
            if (i10 != 0) {
                codedOutputByteBufferNano.writeInt32(15, i10);
            }
            int i11 = this.appearance;
            if (i11 != 0) {
                codedOutputByteBufferNano.writeInt32(16, i11);
            }
            int i12 = this.letterboxInsetLeft;
            if (i12 != 0) {
                codedOutputByteBufferNano.writeInt32(17, i12);
            }
            int i13 = this.letterboxInsetTop;
            if (i13 != 0) {
                codedOutputByteBufferNano.writeInt32(18, i13);
            }
            int i14 = this.letterboxInsetRight;
            if (i14 != 0) {
                codedOutputByteBufferNano.writeInt32(19, i14);
            }
            int i15 = this.letterboxInsetBottom;
            if (i15 != 0) {
                codedOutputByteBufferNano.writeInt32(20, i15);
            }
            int i16 = this.uiMode;
            if (i16 != 0) {
                codedOutputByteBufferNano.writeInt32(21, i16);
            }
            int i17 = this.cutoutInsetLeft;
            if (i17 != 0) {
                codedOutputByteBufferNano.writeInt32(101, i17);
            }
            int i18 = this.cutoutInsetTop;
            if (i18 != 0) {
                codedOutputByteBufferNano.writeInt32(102, i18);
            }
            int i19 = this.cutoutInsetRight;
            if (i19 != 0) {
                codedOutputByteBufferNano.writeInt32(103, i19);
            }
            int i20 = this.cutoutInsetBottom;
            if (i20 != 0) {
                codedOutputByteBufferNano.writeInt32(104, i20);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.orientation;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.insetLeft;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int i3 = this.insetTop;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
            }
            int i4 = this.insetRight;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i4);
            }
            int i5 = this.insetBottom;
            if (i5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i5);
            }
            boolean z = this.isRealSnapshot;
            if (z) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(6, z);
            }
            int i6 = this.windowingMode;
            if (i6 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, i6);
            }
            int i7 = this.systemUiVisibility;
            if (i7 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, i7);
            }
            boolean z2 = this.isTranslucent;
            if (z2) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(9, z2);
            }
            if (!this.topActivityComponent.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.legacyScale) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(11, this.legacyScale);
            }
            long j = this.id;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(12, j);
            }
            int i8 = this.rotation;
            if (i8 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(13, i8);
            }
            int i9 = this.taskWidth;
            if (i9 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(14, i9);
            }
            int i10 = this.taskHeight;
            if (i10 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(15, i10);
            }
            int i11 = this.appearance;
            if (i11 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(16, i11);
            }
            int i12 = this.letterboxInsetLeft;
            if (i12 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(17, i12);
            }
            int i13 = this.letterboxInsetTop;
            if (i13 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(18, i13);
            }
            int i14 = this.letterboxInsetRight;
            if (i14 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(19, i14);
            }
            int i15 = this.letterboxInsetBottom;
            if (i15 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(20, i15);
            }
            int i16 = this.uiMode;
            if (i16 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(21, i16);
            }
            int i17 = this.cutoutInsetLeft;
            if (i17 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(101, i17);
            }
            int i18 = this.cutoutInsetTop;
            if (i18 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(102, i18);
            }
            int i19 = this.cutoutInsetRight;
            if (i19 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(103, i19);
            }
            int i20 = this.cutoutInsetBottom;
            return i20 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(104, i20) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public TaskSnapshotProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 8:
                        this.orientation = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.insetLeft = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.insetTop = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.insetRight = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.insetBottom = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.isRealSnapshot = codedInputByteBufferNano.readBool();
                        break;
                    case 56:
                        this.windowingMode = codedInputByteBufferNano.readInt32();
                        break;
                    case 64:
                        this.systemUiVisibility = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.isTranslucent = codedInputByteBufferNano.readBool();
                        break;
                    case 82:
                        this.topActivityComponent = codedInputByteBufferNano.readString();
                        break;
                    case 93:
                        this.legacyScale = codedInputByteBufferNano.readFloat();
                        break;
                    case 96:
                        this.id = codedInputByteBufferNano.readInt64();
                        break;
                    case 104:
                        this.rotation = codedInputByteBufferNano.readInt32();
                        break;
                    case 112:
                        this.taskWidth = codedInputByteBufferNano.readInt32();
                        break;
                    case 120:
                        this.taskHeight = codedInputByteBufferNano.readInt32();
                        break;
                    case 128:
                        this.appearance = codedInputByteBufferNano.readInt32();
                        break;
                    case 136:
                        this.letterboxInsetLeft = codedInputByteBufferNano.readInt32();
                        break;
                    case 144:
                        this.letterboxInsetTop = codedInputByteBufferNano.readInt32();
                        break;
                    case 152:
                        this.letterboxInsetRight = codedInputByteBufferNano.readInt32();
                        break;
                    case 160:
                        this.letterboxInsetBottom = codedInputByteBufferNano.readInt32();
                        break;
                    case 168:
                        this.uiMode = codedInputByteBufferNano.readInt32();
                        break;
                    case 808:
                        this.cutoutInsetLeft = codedInputByteBufferNano.readInt32();
                        break;
                    case 816:
                        this.cutoutInsetTop = codedInputByteBufferNano.readInt32();
                        break;
                    case 824:
                        this.cutoutInsetRight = codedInputByteBufferNano.readInt32();
                        break;
                    case 832:
                        this.cutoutInsetBottom = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static TaskSnapshotProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (TaskSnapshotProto) MessageNano.mergeFrom(new TaskSnapshotProto(), bArr);
        }

        public static TaskSnapshotProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TaskSnapshotProto().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class LetterboxProto extends MessageNano {
        public static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_CENTER = 1;
        public static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_LEFT = 0;
        public static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_RIGHT = 2;
        public static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_BOTTOM = 2;
        public static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_CENTER = 1;
        public static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_TOP = 0;
        private static volatile LetterboxProto[] _emptyArray;
        public int letterboxPositionForBookModeReachability;
        public int letterboxPositionForHorizontalReachability;
        public int letterboxPositionForTabletopModeReachability;
        public int letterboxPositionForVerticalReachability;

        public static LetterboxProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new LetterboxProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public LetterboxProto() {
            clear();
        }

        public LetterboxProto clear() {
            this.letterboxPositionForHorizontalReachability = 0;
            this.letterboxPositionForVerticalReachability = 0;
            this.letterboxPositionForBookModeReachability = 0;
            this.letterboxPositionForTabletopModeReachability = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.letterboxPositionForHorizontalReachability;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.letterboxPositionForVerticalReachability;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int i3 = this.letterboxPositionForBookModeReachability;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i3);
            }
            int i4 = this.letterboxPositionForTabletopModeReachability;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i4);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.letterboxPositionForHorizontalReachability;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.letterboxPositionForVerticalReachability;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int i3 = this.letterboxPositionForBookModeReachability;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
            }
            int i4 = this.letterboxPositionForTabletopModeReachability;
            return i4 != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, i4) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public LetterboxProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    int readInt32 = codedInputByteBufferNano.readInt32();
                    if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2) {
                        this.letterboxPositionForHorizontalReachability = readInt32;
                    }
                } else if (readTag == 16) {
                    int readInt322 = codedInputByteBufferNano.readInt32();
                    if (readInt322 == 0 || readInt322 == 1 || readInt322 == 2) {
                        this.letterboxPositionForVerticalReachability = readInt322;
                    }
                } else if (readTag == 24) {
                    int readInt323 = codedInputByteBufferNano.readInt32();
                    if (readInt323 == 0 || readInt323 == 1 || readInt323 == 2) {
                        this.letterboxPositionForBookModeReachability = readInt323;
                    }
                } else if (readTag != 32) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int readInt324 = codedInputByteBufferNano.readInt32();
                    if (readInt324 == 0 || readInt324 == 1 || readInt324 == 2) {
                        this.letterboxPositionForTabletopModeReachability = readInt324;
                    }
                }
            }
            return this;
        }

        public static LetterboxProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (LetterboxProto) MessageNano.mergeFrom(new LetterboxProto(), bArr);
        }

        public static LetterboxProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new LetterboxProto().mergeFrom(codedInputByteBufferNano);
        }
    }
}
