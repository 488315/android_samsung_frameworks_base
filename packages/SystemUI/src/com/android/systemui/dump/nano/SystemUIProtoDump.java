package com.android.systemui.dump.nano;

import com.android.systemui.qs.nano.QsTileState;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class SystemUIProtoDump extends MessageNano {
    public QsTileState[] tiles;

    public SystemUIProtoDump() {
        clear();
    }

    public SystemUIProtoDump clear() {
        this.tiles = QsTileState.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        QsTileState[] qsTileStateArr = this.tiles;
        int i = 0;
        if (qsTileStateArr == null || qsTileStateArr.length <= 0) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            QsTileState[] qsTileStateArr2 = this.tiles;
            if (i >= qsTileStateArr2.length) {
                return i2;
            }
            QsTileState qsTileState = qsTileStateArr2[i];
            if (qsTileState != null) {
                i2 = CodedOutputByteBufferNano.computeMessageSize(1, qsTileState) + i2;
            }
            i++;
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        QsTileState[] qsTileStateArr = this.tiles;
        if (qsTileStateArr == null || qsTileStateArr.length <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            QsTileState[] qsTileStateArr2 = this.tiles;
            if (i >= qsTileStateArr2.length) {
                return;
            }
            QsTileState qsTileState = qsTileStateArr2[i];
            if (qsTileState != null) {
                codedOutputByteBufferNano.writeMessage(1, qsTileState);
            }
            i++;
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public SystemUIProtoDump mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                QsTileState[] qsTileStateArr = this.tiles;
                int length = qsTileStateArr == null ? 0 : qsTileStateArr.length;
                int i = repeatedFieldArrayLength + length;
                QsTileState[] qsTileStateArr2 = new QsTileState[i];
                if (length != 0) {
                    System.arraycopy(qsTileStateArr, 0, qsTileStateArr2, 0, length);
                }
                while (length < i - 1) {
                    QsTileState qsTileState = new QsTileState();
                    qsTileStateArr2[length] = qsTileState;
                    codedInputByteBufferNano.readMessage(qsTileState);
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                QsTileState qsTileState2 = new QsTileState();
                qsTileStateArr2[length] = qsTileState2;
                codedInputByteBufferNano.readMessage(qsTileState2);
                this.tiles = qsTileStateArr2;
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                return this;
            }
        }
    }
}
