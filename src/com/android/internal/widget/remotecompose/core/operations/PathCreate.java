package com.android.internal.widget.remotecompose.core.operations;

import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class PathCreate extends PaintOperation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "PathCreate";
    public static final int CLOSE = 15;
    public static final int CONIC = 13;
    public static final int CUBIC = 14;
    public static final int DONE = 16;
    public static final int LINE = 11;
    public static final int MOVE = 10;
    private static final int OP_CODE = 159;
    public static final int QUADRATIC = 12;
    float[] mFloatPath;
    int mInstanceId;
    float[] mOutputPath;
    public static final float MOVE_NAN = Utils.asNan(10);
    public static final float LINE_NAN = Utils.asNan(11);
    public static final float QUADRATIC_NAN = Utils.asNan(12);
    public static final float CONIC_NAN = Utils.asNan(13);
    public static final float CUBIC_NAN = Utils.asNan(14);
    public static final float CLOSE_NAN = Utils.asNan(15);
    public static final float DONE_NAN = Utils.asNan(16);

    public static int id() {
        return 159;
    }

    PathCreate(int i, float f, float f2) {
        this.mInstanceId = i;
        float[] fArr = {PathData.MOVE_NAN, f, f2};
        this.mFloatPath = fArr;
        this.mOutputPath = Arrays.copyOf(fArr, fArr.length);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        int i = 0;
        while (true) {
            float[] fArr = this.mFloatPath;
            if (i >= fArr.length) {
                return;
            }
            float f = fArr[i];
            if (Utils.isVariable(f)) {
                float[] fArr2 = this.mOutputPath;
                if (Float.isNaN(f)) {
                    f = remoteContext.getFloat(Utils.idFromNan(f));
                }
                fArr2[i] = f;
            } else {
                this.mOutputPath[i] = f;
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        for (float f : this.mFloatPath) {
            if (Float.isNaN(f)) {
                remoteContext.listensTo(Utils.idFromNan(f), this);
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        int i = this.mInstanceId;
        float[] fArr = this.mFloatPath;
        apply(wireBuffer, i, fArr[1], fArr[2]);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return pathString(this.mFloatPath);
    }

    public String toString() {
        return "PathCreate[" + this.mInstanceId + "] = \"" + deepToString(" ") + "\"[" + Utils.idStringFromNan(this.mFloatPath[1]) + "] " + this.mOutputPath[1] + " [" + Utils.idStringFromNan(this.mFloatPath[2]) + "] " + this.mOutputPath[2];
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, float f, float f2) {
        wireBuffer.start(159);
        wireBuffer.writeInt(i);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new PathCreate(wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 159, CLASS_NAME).description("Encode a Path ").field(0, "id", "id of path").field(1, "startX", "initial start x").field(1, "startX", "initial start y");
    }

    public static String pathString(float[] fArr) {
        if (fArr == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fArr.length; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            if (Float.isNaN(fArr[i])) {
                int idFromNan = Utils.idFromNan(fArr[i]);
                if (idFromNan <= 16) {
                    switch (idFromNan) {
                        case 10:
                            sb.append(GnssSignalType.CODE_TYPE_M);
                            break;
                        case 11:
                            sb.append(GnssSignalType.CODE_TYPE_L);
                            break;
                        case 12:
                            sb.append(GnssSignalType.CODE_TYPE_Q);
                            break;
                        case 13:
                            sb.append("R");
                            break;
                        case 14:
                            sb.append(GnssSignalType.CODE_TYPE_C);
                            break;
                        case 15:
                            sb.append(GnssSignalType.CODE_TYPE_Z);
                            break;
                        case 16:
                            sb.append(MediaMetrics.SEPARATOR);
                            break;
                        default:
                            sb.append(NavigationBarInflaterView.SIZE_MOD_START + idFromNan + NavigationBarInflaterView.SIZE_MOD_END);
                            break;
                    }
                } else {
                    sb.append(NavigationBarInflaterView.KEY_CODE_START + idFromNan + NavigationBarInflaterView.KEY_CODE_END);
                }
            } else {
                sb.append(fArr[i]);
            }
        }
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        apply(paintContext.getContext());
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        remoteContext.loadPathData(this.mInstanceId, this.mOutputPath);
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("id", Integer.valueOf(this.mInstanceId)).addPath("path", this.mFloatPath);
    }
}
