package com.android.internal.widget.remotecompose.core.operations;

import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.app.slice.Slice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.telephony.DctConstants;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.PaintOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.layout.Container;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class ConditionalOperations extends PaintOperation implements Container, VariableSupport, Serializable {
    private static final String CLASS_NAME = "ConditionalOperations";
    private static final int OP_CODE = 178;
    public static final byte TYPE_EQ = 0;
    public static final byte TYPE_GT = 4;
    public static final byte TYPE_GTE = 5;
    public static final byte TYPE_LT = 2;
    public static final byte TYPE_LTE = 3;
    public static final byte TYPE_NEQ = 1;
    private static final String[] TYPE_STR = {"EQ", "NEQ", "LT", DctConstants.RAT_NAME_LTE, "GT", "GTE"};
    int mIndexVariableId;
    public ArrayList<Operation> mList = new ArrayList<>();
    byte mType;
    float mVarA;
    float mVarAOut;
    float mVarB;
    float mVarBOut;

    public int estimateIterations() {
        return 1;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        if (Float.isNaN(this.mVarA)) {
            remoteContext.listensTo(Utils.idFromNan(this.mVarA), this);
        }
        if (Float.isNaN(this.mVarB)) {
            remoteContext.listensTo(Utils.idFromNan(this.mVarB), this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        this.mVarAOut = Float.isNaN(this.mVarA) ? remoteContext.getFloat(Utils.idFromNan(this.mVarA)) : this.mVarA;
        this.mVarBOut = Float.isNaN(this.mVarB) ? remoteContext.getFloat(Utils.idFromNan(this.mVarB)) : this.mVarB;
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            if ((next instanceof VariableSupport) && next.isDirty()) {
                ((VariableSupport) next).updateVariables(remoteContext);
            }
        }
    }

    public ConditionalOperations(byte b, float f, float f2) {
        this.mType = b;
        this.mVarA = f;
        this.mVarAOut = f;
        this.mVarB = f2;
        this.mVarBOut = f2;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Container
    public ArrayList<Operation> getList() {
        return this.mList;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mType, this.mVarA, this.mVarB);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ConditionalOperations " + TYPE_STR[this.mType] + NavigationBarInflaterView.KEY_CODE_START + Utils.idFromNan(this.mVarA) + "," + Utils.idFromNan(this.mVarB) + ")\n");
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            sb.append("  ");
            sb.append(next);
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation, com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(toString());
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext paintContext) {
        RemoteContext context = paintContext.getContext();
        byte b = this.mType;
        if (b != 0) {
            if (b != 1) {
                if (b != 2) {
                    if (b != 3) {
                        if (b != 4) {
                            if (b != 5 || this.mVarAOut < this.mVarBOut) {
                                return;
                            }
                        } else if (this.mVarAOut <= this.mVarBOut) {
                            return;
                        }
                    } else if (this.mVarAOut > this.mVarBOut) {
                        return;
                    }
                } else if (this.mVarAOut >= this.mVarBOut) {
                    return;
                }
            } else if (this.mVarAOut == this.mVarBOut) {
                return;
            }
        } else if (this.mVarAOut != this.mVarBOut) {
            return;
        }
        Iterator<Operation> it = this.mList.iterator();
        while (it.hasNext()) {
            Operation next = it.next();
            context.incrementOpCount();
            next.apply(paintContext.getContext());
        }
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, byte b, float f, float f2) {
        wireBuffer.start(178);
        wireBuffer.writeByte(b);
        wireBuffer.writeFloat(f);
        wireBuffer.writeFloat(f2);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new ConditionalOperations((byte) wireBuffer.readByte(), wireBuffer.readFloat(), wireBuffer.readFloat()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Operations", 178, name()).description("Run if the condition is true").field(6, "type", "type of comparison").field(1, FullBackup.APK_TREE_TOKEN, "first value").field(1, XmlTags.TAG_BLOB, "second value");
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType(CLASS_NAME).add("type", Byte.valueOf(this.mType)).add("varA", this.mVarA, this.mVarAOut).add("VarB", this.mVarB, this.mVarBOut).add(Slice.HINT_LIST, this.mList);
    }
}
