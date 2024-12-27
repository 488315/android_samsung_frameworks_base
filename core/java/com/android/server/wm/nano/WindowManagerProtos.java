package com.android.server.wm.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

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
            this.cutoutInsetLeft = 0;
            this.cutoutInsetTop = 0;
            this.cutoutInsetRight = 0;
            this.cutoutInsetBottom = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.orientation != 0) {
                output.writeInt32(1, this.orientation);
            }
            if (this.insetLeft != 0) {
                output.writeInt32(2, this.insetLeft);
            }
            if (this.insetTop != 0) {
                output.writeInt32(3, this.insetTop);
            }
            if (this.insetRight != 0) {
                output.writeInt32(4, this.insetRight);
            }
            if (this.insetBottom != 0) {
                output.writeInt32(5, this.insetBottom);
            }
            if (this.isRealSnapshot) {
                output.writeBool(6, this.isRealSnapshot);
            }
            if (this.windowingMode != 0) {
                output.writeInt32(7, this.windowingMode);
            }
            if (this.systemUiVisibility != 0) {
                output.writeInt32(8, this.systemUiVisibility);
            }
            if (this.isTranslucent) {
                output.writeBool(9, this.isTranslucent);
            }
            if (!this.topActivityComponent.equals("")) {
                output.writeString(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.legacyScale) != Float.floatToIntBits(0.0f)) {
                output.writeFloat(11, this.legacyScale);
            }
            if (this.id != 0) {
                output.writeInt64(12, this.id);
            }
            if (this.rotation != 0) {
                output.writeInt32(13, this.rotation);
            }
            if (this.taskWidth != 0) {
                output.writeInt32(14, this.taskWidth);
            }
            if (this.taskHeight != 0) {
                output.writeInt32(15, this.taskHeight);
            }
            if (this.appearance != 0) {
                output.writeInt32(16, this.appearance);
            }
            if (this.letterboxInsetLeft != 0) {
                output.writeInt32(17, this.letterboxInsetLeft);
            }
            if (this.letterboxInsetTop != 0) {
                output.writeInt32(18, this.letterboxInsetTop);
            }
            if (this.letterboxInsetRight != 0) {
                output.writeInt32(19, this.letterboxInsetRight);
            }
            if (this.letterboxInsetBottom != 0) {
                output.writeInt32(20, this.letterboxInsetBottom);
            }
            if (this.cutoutInsetLeft != 0) {
                output.writeInt32(101, this.cutoutInsetLeft);
            }
            if (this.cutoutInsetTop != 0) {
                output.writeInt32(102, this.cutoutInsetTop);
            }
            if (this.cutoutInsetRight != 0) {
                output.writeInt32(103, this.cutoutInsetRight);
            }
            if (this.cutoutInsetBottom != 0) {
                output.writeInt32(104, this.cutoutInsetBottom);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.orientation != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(1, this.orientation);
            }
            if (this.insetLeft != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(2, this.insetLeft);
            }
            if (this.insetTop != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.insetTop);
            }
            if (this.insetRight != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(4, this.insetRight);
            }
            if (this.insetBottom != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(5, this.insetBottom);
            }
            if (this.isRealSnapshot) {
                size += CodedOutputByteBufferNano.computeBoolSize(6, this.isRealSnapshot);
            }
            if (this.windowingMode != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(7, this.windowingMode);
            }
            if (this.systemUiVisibility != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(8, this.systemUiVisibility);
            }
            if (this.isTranslucent) {
                size += CodedOutputByteBufferNano.computeBoolSize(9, this.isTranslucent);
            }
            if (!this.topActivityComponent.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.legacyScale) != Float.floatToIntBits(0.0f)) {
                size += CodedOutputByteBufferNano.computeFloatSize(11, this.legacyScale);
            }
            if (this.id != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(12, this.id);
            }
            if (this.rotation != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(13, this.rotation);
            }
            if (this.taskWidth != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(14, this.taskWidth);
            }
            if (this.taskHeight != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(15, this.taskHeight);
            }
            if (this.appearance != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(16, this.appearance);
            }
            if (this.letterboxInsetLeft != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(17, this.letterboxInsetLeft);
            }
            if (this.letterboxInsetTop != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(18, this.letterboxInsetTop);
            }
            if (this.letterboxInsetRight != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(19, this.letterboxInsetRight);
            }
            if (this.letterboxInsetBottom != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(20, this.letterboxInsetBottom);
            }
            if (this.cutoutInsetLeft != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(101, this.cutoutInsetLeft);
            }
            if (this.cutoutInsetTop != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(102, this.cutoutInsetTop);
            }
            if (this.cutoutInsetRight != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(103, this.cutoutInsetRight);
            }
            if (this.cutoutInsetBottom != 0) {
                return size
                        + CodedOutputByteBufferNano.computeInt32Size(104, this.cutoutInsetBottom);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public TaskSnapshotProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 8:
                        this.orientation = input.readInt32();
                        break;
                    case 16:
                        this.insetLeft = input.readInt32();
                        break;
                    case 24:
                        this.insetTop = input.readInt32();
                        break;
                    case 32:
                        this.insetRight = input.readInt32();
                        break;
                    case 40:
                        this.insetBottom = input.readInt32();
                        break;
                    case 48:
                        this.isRealSnapshot = input.readBool();
                        break;
                    case 56:
                        this.windowingMode = input.readInt32();
                        break;
                    case 64:
                        this.systemUiVisibility = input.readInt32();
                        break;
                    case 72:
                        this.isTranslucent = input.readBool();
                        break;
                    case 82:
                        this.topActivityComponent = input.readString();
                        break;
                    case 93:
                        this.legacyScale = input.readFloat();
                        break;
                    case 96:
                        this.id = input.readInt64();
                        break;
                    case 104:
                        this.rotation = input.readInt32();
                        break;
                    case 112:
                        this.taskWidth = input.readInt32();
                        break;
                    case 120:
                        this.taskHeight = input.readInt32();
                        break;
                    case 128:
                        this.appearance = input.readInt32();
                        break;
                    case 136:
                        this.letterboxInsetLeft = input.readInt32();
                        break;
                    case 144:
                        this.letterboxInsetTop = input.readInt32();
                        break;
                    case 152:
                        this.letterboxInsetRight = input.readInt32();
                        break;
                    case 160:
                        this.letterboxInsetBottom = input.readInt32();
                        break;
                    case 808:
                        this.cutoutInsetLeft = input.readInt32();
                        break;
                    case 816:
                        this.cutoutInsetTop = input.readInt32();
                        break;
                    case 824:
                        this.cutoutInsetRight = input.readInt32();
                        break;
                    case 832:
                        this.cutoutInsetBottom = input.readInt32();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static TaskSnapshotProto parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (TaskSnapshotProto) MessageNano.mergeFrom(new TaskSnapshotProto(), data);
        }

        public static TaskSnapshotProto parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new TaskSnapshotProto().mergeFrom(input);
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
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.letterboxPositionForHorizontalReachability != 0) {
                output.writeInt32(1, this.letterboxPositionForHorizontalReachability);
            }
            if (this.letterboxPositionForVerticalReachability != 0) {
                output.writeInt32(2, this.letterboxPositionForVerticalReachability);
            }
            if (this.letterboxPositionForBookModeReachability != 0) {
                output.writeInt32(3, this.letterboxPositionForBookModeReachability);
            }
            if (this.letterboxPositionForTabletopModeReachability != 0) {
                output.writeInt32(4, this.letterboxPositionForTabletopModeReachability);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.letterboxPositionForHorizontalReachability != 0) {
                size +=
                        CodedOutputByteBufferNano.computeInt32Size(
                                1, this.letterboxPositionForHorizontalReachability);
            }
            if (this.letterboxPositionForVerticalReachability != 0) {
                size +=
                        CodedOutputByteBufferNano.computeInt32Size(
                                2, this.letterboxPositionForVerticalReachability);
            }
            if (this.letterboxPositionForBookModeReachability != 0) {
                size +=
                        CodedOutputByteBufferNano.computeInt32Size(
                                3, this.letterboxPositionForBookModeReachability);
            }
            if (this.letterboxPositionForTabletopModeReachability != 0) {
                return size
                        + CodedOutputByteBufferNano.computeInt32Size(
                                4, this.letterboxPositionForTabletopModeReachability);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public LetterboxProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 8:
                        int value = input.readInt32();
                        switch (value) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForHorizontalReachability = value;
                                break;
                        }
                    case 16:
                        int value2 = input.readInt32();
                        switch (value2) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForVerticalReachability = value2;
                                break;
                        }
                    case 24:
                        int value3 = input.readInt32();
                        switch (value3) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForBookModeReachability = value3;
                                break;
                        }
                    case 32:
                        int value4 = input.readInt32();
                        switch (value4) {
                            case 0:
                            case 1:
                            case 2:
                                this.letterboxPositionForTabletopModeReachability = value4;
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

        public static LetterboxProto parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (LetterboxProto) MessageNano.mergeFrom(new LetterboxProto(), data);
        }

        public static LetterboxProto parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new LetterboxProto().mergeFrom(input);
        }
    }
}
