package com.android.internal.widget.remotecompose.core.operations;

import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;

import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;

import java.util.Arrays;
import java.util.List;

public class PathData implements Operation, VariableSupport {
    public static final int CLOSE = 15;
    public static final int CONIC = 13;
    public static final int CUBIC = 14;
    public static final int DONE = 16;
    public static final int LINE = 11;
    public static final int MOVE = 10;
    public static final int QUADRATIC = 12;
    float[] mFloatPath;
    int mInstanceId;
    float[] mOutputPath;
    public static final Companion COMPANION = new Companion();
    public static final float MOVE_NAN = Utils.asNan(10);
    public static final float LINE_NAN = Utils.asNan(11);
    public static final float QUADRATIC_NAN = Utils.asNan(12);
    public static final float CONIC_NAN = Utils.asNan(13);
    public static final float CUBIC_NAN = Utils.asNan(14);
    public static final float CLOSE_NAN = Utils.asNan(15);
    public static final float DONE_NAN = Utils.asNan(16);

    PathData(int instanceId, float[] floatPath) {
        this.mInstanceId = instanceId;
        this.mFloatPath = floatPath;
        this.mOutputPath = Arrays.copyOf(this.mFloatPath, this.mFloatPath.length);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext context) {
        for (int i = 0; i < this.mFloatPath.length; i++) {
            float v = this.mFloatPath[i];
            if (Utils.isVariable(v)) {
                this.mOutputPath[i] = Float.isNaN(v) ? context.getFloat(Utils.idFromNan(v)) : v;
            } else {
                this.mOutputPath[i] = v;
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext context) {
        for (int i = 0; i < this.mFloatPath.length; i++) {
            if (Float.isNaN(this.mFloatPath[i])) {
                context.listensTo(Utils.idFromNan(this.mFloatPath[i]), this);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mInstanceId, this.mOutputPath);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return pathString(this.mFloatPath);
    }

    public String toString() {
        return "PathData[" + this.mInstanceId + "] = \"" + deepToString(" ") + "\"";
    }

    public static class Companion implements CompanionOperation {
        private Companion() {}

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "BitmapData";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 123;
        }

        public void apply(WireBuffer buffer, int id, float[] data) {
            buffer.start(123);
            buffer.writeInt(id);
            buffer.writeInt(data.length);
            for (float f : data) {
                buffer.writeFloat(f);
            }
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int imageId = buffer.readInt();
            int len = buffer.readInt();
            float[] data = new float[len];
            for (int i = 0; i < data.length; i++) {
                data[i] = buffer.readFloat();
            }
            operations.add(new PathData(imageId, data));
        }
    }

    public static String pathString(float[] path) {
        if (path == null) {
            return "null";
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < path.length; i++) {
            if (i != 0) {
                str.append(" ");
            }
            if (Float.isNaN(path[i])) {
                int id = Utils.idFromNan(path[i]);
                if (id <= 16) {
                    switch (id) {
                        case 10:
                            str.append(GnssSignalType.CODE_TYPE_M);
                            break;
                        case 11:
                            str.append(GnssSignalType.CODE_TYPE_L);
                            break;
                        case 12:
                            str.append(GnssSignalType.CODE_TYPE_Q);
                            break;
                        case 13:
                            str.append("R");
                            break;
                        case 14:
                            str.append(GnssSignalType.CODE_TYPE_C);
                            break;
                        case 15:
                            str.append(GnssSignalType.CODE_TYPE_Z);
                            break;
                        case 16:
                            str.append(MediaMetrics.SEPARATOR);
                            break;
                        default:
                            str.append(
                                    NavigationBarInflaterView.SIZE_MOD_START
                                            + id
                                            + NavigationBarInflaterView.SIZE_MOD_END);
                            break;
                    }
                } else {
                    str.append(
                            NavigationBarInflaterView.KEY_CODE_START
                                    + id
                                    + NavigationBarInflaterView.KEY_CODE_END);
                }
            } else {
                str.append(path[i]);
            }
        }
        return str.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.loadPathData(this.mInstanceId, this.mOutputPath);
    }
}
