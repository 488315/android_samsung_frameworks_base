package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntegerExpressionEvaluator;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class IntegerExpression extends Operation implements VariableSupport, Serializable {
    private static final String CLASS_NAME = "IntegerExpression";
    public static final int MAX_SIZE = 320;
    private static final int OP_CODE = 144;
    public int mId;
    private int mMask;
    public int[] mPreCalcValue;
    private int mPreMask;
    public final int[] mSrcValue;
    private float mLastChange = Float.NaN;
    IntegerExpressionEvaluator mExp = new IntegerExpressionEvaluator();

    public static int id() {
        return 144;
    }

    public static boolean isId(int i, int i2, int i3) {
        return (i & (1 << i2)) != 0 && i3 < 65536;
    }

    public IntegerExpression(int i, int i2, int[] iArr) {
        this.mId = i;
        this.mMask = i2;
        this.mSrcValue = iArr;
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        int[] iArr = this.mPreCalcValue;
        if (iArr == null || iArr.length != this.mSrcValue.length) {
            this.mPreCalcValue = new int[this.mSrcValue.length];
        }
        this.mPreMask = this.mMask;
        int i = 0;
        while (true) {
            int[] iArr2 = this.mSrcValue;
            if (i >= iArr2.length) {
                return;
            }
            if (isId(this.mMask, i, iArr2[i])) {
                this.mPreMask &= ~(1 << i);
                this.mPreCalcValue[i] = remoteContext.getInteger(this.mSrcValue[i]);
            } else {
                this.mPreCalcValue[i] = this.mSrcValue[i];
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        int i = 0;
        while (true) {
            int[] iArr = this.mSrcValue;
            if (i >= iArr.length) {
                return;
            }
            if (isId(this.mMask, i, iArr[i])) {
                remoteContext.listensTo(this.mSrcValue[i], this);
            }
            i++;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        updateVariables(remoteContext);
        float animationTime = remoteContext.getAnimationTime();
        if (Float.isNaN(this.mLastChange)) {
            this.mLastChange = animationTime;
        }
        IntegerExpressionEvaluator integerExpressionEvaluator = this.mExp;
        int i = this.mPreMask;
        int[] iArr = this.mPreCalcValue;
        remoteContext.loadInteger(this.mId, integerExpressionEvaluator.eval(i, Arrays.copyOf(iArr, iArr.length), new int[0]));
    }

    public int evaluate(RemoteContext remoteContext) {
        updateVariables(remoteContext);
        float animationTime = remoteContext.getAnimationTime();
        if (Float.isNaN(this.mLastChange)) {
            this.mLastChange = animationTime;
        }
        IntegerExpressionEvaluator integerExpressionEvaluator = this.mExp;
        int i = this.mPreMask;
        int[] iArr = this.mPreCalcValue;
        return integerExpressionEvaluator.eval(i, Arrays.copyOf(iArr, iArr.length), new int[0]);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mId, this.mMask, this.mSrcValue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.mPreCalcValue == null) {
            return "";
        }
        for (int i = 0; i < this.mPreCalcValue.length; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            if (IntegerExpressionEvaluator.isOperation(this.mMask, i)) {
                if (isId(this.mMask, i, this.mSrcValue[i])) {
                    sb.append(NavigationBarInflaterView.SIZE_MOD_START + this.mSrcValue[i] + NavigationBarInflaterView.SIZE_MOD_END);
                } else {
                    sb.append(IntegerExpressionEvaluator.toMathName(this.mPreCalcValue[i]));
                }
            } else {
                sb.append(this.mSrcValue[i]);
            }
        }
        return "IntegerExpression[" + this.mId + "] = (" + ((Object) sb) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String name() {
        return CLASS_NAME;
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int[] iArr) {
        wireBuffer.start(144);
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(iArr.length);
        for (int i3 : iArr) {
            wireBuffer.writeInt(i3);
        }
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        int readInt = wireBuffer.readInt();
        int readInt2 = wireBuffer.readInt();
        int readInt3 = wireBuffer.readInt();
        if (readInt3 > 320) {
            throw new RuntimeException("buffer corrupt integer expression " + readInt3);
        }
        int[] iArr = new int[readInt3];
        for (int i = 0; i < readInt3; i++) {
            iArr[i] = wireBuffer.readInt();
        }
        list.add(new IntegerExpression(readInt, readInt2, iArr));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Data Operations", 144, CLASS_NAME).description("Expression that computes an integer").field(0, "id", "id of integer").field(0, "mask", "bits representing operators or other id's").field(0, Contract.CompressedEvents.Field.LENGTH, "length of array").field(11, "values", Contract.CompressedEvents.Field.LENGTH, "Array of ints");
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.EXPRESSION).addType(CLASS_NAME).add("id", Integer.valueOf(this.mId)).add("mask", Integer.valueOf(this.mId)).addIntExpressionSrc("srcValues", this.mSrcValue, this.mMask);
    }
}
