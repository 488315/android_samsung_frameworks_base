package com.android.p038wm.shell.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WmShellTransitionTraceProto extends MessageNano {
    public HandlerMapping[] handlerMappings;
    public long magicNumber;
    public long realToElapsedTimeOffsetNanos;
    public Transition[] transitions;

    public WmShellTransitionTraceProto() {
        clear();
    }

    public WmShellTransitionTraceProto clear() {
        this.magicNumber = 0L;
        this.transitions = Transition.emptyArray();
        this.handlerMappings = HandlerMapping.emptyArray();
        this.realToElapsedTimeOffsetNanos = 0L;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int i = 0;
        int computeTagSize = CodedOutputByteBufferNano.computeTagSize(1) + 8 + 0;
        Transition[] transitionArr = this.transitions;
        if (transitionArr != null && transitionArr.length > 0) {
            int i2 = 0;
            while (true) {
                Transition[] transitionArr2 = this.transitions;
                if (i2 >= transitionArr2.length) {
                    break;
                }
                Transition transition = transitionArr2[i2];
                if (transition != null) {
                    computeTagSize += CodedOutputByteBufferNano.computeMessageSize(2, transition);
                }
                i2++;
            }
        }
        HandlerMapping[] handlerMappingArr = this.handlerMappings;
        if (handlerMappingArr != null && handlerMappingArr.length > 0) {
            while (true) {
                HandlerMapping[] handlerMappingArr2 = this.handlerMappings;
                if (i >= handlerMappingArr2.length) {
                    break;
                }
                HandlerMapping handlerMapping = handlerMappingArr2[i];
                if (handlerMapping != null) {
                    computeTagSize += CodedOutputByteBufferNano.computeMessageSize(3, handlerMapping);
                }
                i++;
            }
        }
        return this.realToElapsedTimeOffsetNanos != 0 ? computeTagSize + CodedOutputByteBufferNano.computeTagSize(4) + 8 : computeTagSize;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        codedOutputByteBufferNano.writeFixed64(1, this.magicNumber);
        Transition[] transitionArr = this.transitions;
        int i = 0;
        if (transitionArr != null && transitionArr.length > 0) {
            int i2 = 0;
            while (true) {
                Transition[] transitionArr2 = this.transitions;
                if (i2 >= transitionArr2.length) {
                    break;
                }
                Transition transition = transitionArr2[i2];
                if (transition != null) {
                    codedOutputByteBufferNano.writeMessage(2, transition);
                }
                i2++;
            }
        }
        HandlerMapping[] handlerMappingArr = this.handlerMappings;
        if (handlerMappingArr != null && handlerMappingArr.length > 0) {
            while (true) {
                HandlerMapping[] handlerMappingArr2 = this.handlerMappings;
                if (i >= handlerMappingArr2.length) {
                    break;
                }
                HandlerMapping handlerMapping = handlerMappingArr2[i];
                if (handlerMapping != null) {
                    codedOutputByteBufferNano.writeMessage(3, handlerMapping);
                }
                i++;
            }
        }
        long j = this.realToElapsedTimeOffsetNanos;
        if (j != 0) {
            codedOutputByteBufferNano.writeFixed64(4, j);
        }
    }
}
