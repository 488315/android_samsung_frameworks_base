package org.tensorflow.lite;

import java.nio.ByteBuffer;

final class NativeInterpreterWrapperExperimental extends NativeInterpreterWrapper {
    private static native void resetVariableTensors(long interpreterHandle, long errorHandle);

    NativeInterpreterWrapperExperimental(String modelPath) {
        super(modelPath);
    }

    NativeInterpreterWrapperExperimental(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    NativeInterpreterWrapperExperimental(String modelPath, InterpreterImpl.Options options) {
        super(modelPath, options);
    }

    NativeInterpreterWrapperExperimental(ByteBuffer buffer, InterpreterImpl.Options options) {
        super(buffer, options);
    }

    void resetVariableTensors() {
        resetVariableTensors(this.interpreterHandle, this.errorHandle);
    }
}
