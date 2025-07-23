package android.renderscript;

import android.renderscript.Script;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes3.dex */
public final class ScriptGroup extends BaseObj {
    private static final String TAG = "ScriptGroup";
    private List<Closure> mClosures;
    IO[] mInputs;
    private List<Input> mInputs2;
    private String mName;
    IO[] mOutputs;
    private Future[] mOutputs2;

    static class IO {
        Allocation mAllocation;
        Script.KernelID mKID;

        IO(Script.KernelID kernelID) {
            this.mKID = kernelID;
        }
    }

    static class ConnectLine {
        Type mAllocationType;
        Script.KernelID mFrom;
        Script.FieldID mToF;
        Script.KernelID mToK;

        ConnectLine(Type type, Script.KernelID kernelID, Script.KernelID kernelID2) {
            this.mFrom = kernelID;
            this.mToK = kernelID2;
            this.mAllocationType = type;
        }

        ConnectLine(Type type, Script.KernelID kernelID, Script.FieldID fieldID) {
            this.mFrom = kernelID;
            this.mToF = fieldID;
            this.mAllocationType = type;
        }
    }

    static class Node {
        int dagNumber;
        Node mNext;
        Script mScript;
        ArrayList<Script.KernelID> mKernels = new ArrayList<>();
        ArrayList<ConnectLine> mInputs = new ArrayList<>();
        ArrayList<ConnectLine> mOutputs = new ArrayList<>();

        Node(Script script) {
            this.mScript = script;
        }
    }

    public static final class Closure extends BaseObj {
        private static final String TAG = "Closure";
        private Object[] mArgs;
        private Map<Script.FieldID, Object> mBindings;
        private FieldPacker mFP;
        private Map<Script.FieldID, Future> mGlobalFuture;
        private Future mReturnFuture;
        private Allocation mReturnValue;

        Closure(long j, RenderScript renderScript) {
            super(j, renderScript);
        }

        Closure(RenderScript renderScript, Script.KernelID kernelID, Type type, Object[] objArr, Map<Script.FieldID, Object> map) {
            super(0L, renderScript);
            this.mArgs = objArr;
            this.mReturnValue = Allocation.createTyped(renderScript, type);
            this.mBindings = map;
            this.mGlobalFuture = new HashMap();
            int length = objArr.length + map.size();
            long[] jArr = new long[length];
            long[] jArr2 = new long[length];
            int[] iArr = new int[length];
            long[] jArr3 = new long[length];
            long[] jArr4 = new long[length];
            int i = 0;
            while (i < objArr.length) {
                jArr[i] = 0;
                retrieveValueAndDependenceInfo(renderScript, i, null, objArr[i], jArr2, iArr, jArr3, jArr4);
                i++;
            }
            for (Map.Entry<Script.FieldID, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                Script.FieldID key = entry.getKey();
                jArr[i] = key.getID(renderScript);
                retrieveValueAndDependenceInfo(renderScript, i, key, value, jArr2, iArr, jArr3, jArr4);
                i++;
            }
            setID(renderScript.nClosureCreate(kernelID.getID(renderScript), this.mReturnValue.getID(renderScript), jArr, jArr2, iArr, jArr3, jArr4));
            this.guard.open("destroy");
        }

        Closure(RenderScript renderScript, Script.InvokeID invokeID, Object[] objArr, Map<Script.FieldID, Object> map) {
            super(0L, renderScript);
            this.mFP = FieldPacker.createFromArray(objArr);
            this.mArgs = objArr;
            this.mBindings = map;
            this.mGlobalFuture = new HashMap();
            int size = map.size();
            long[] jArr = new long[size];
            long[] jArr2 = new long[size];
            int[] iArr = new int[size];
            long[] jArr3 = new long[size];
            long[] jArr4 = new long[size];
            int i = 0;
            for (Map.Entry<Script.FieldID, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                Script.FieldID key = entry.getKey();
                jArr[i] = key.getID(renderScript);
                retrieveValueAndDependenceInfo(renderScript, i, key, value, jArr2, iArr, jArr3, jArr4);
                i++;
            }
            setID(renderScript.nInvokeClosureCreate(invokeID.getID(renderScript), this.mFP.getData(), jArr, jArr2, iArr));
            this.guard.open("destroy");
        }

        @Override // android.renderscript.BaseObj
        public void destroy() {
            super.destroy();
            Allocation allocation = this.mReturnValue;
            if (allocation != null) {
                allocation.destroy();
            }
        }

        @Override // android.renderscript.BaseObj
        protected void finalize() throws Throwable {
            this.mReturnValue = null;
            super.finalize();
        }

        private void retrieveValueAndDependenceInfo(RenderScript renderScript, int i, Script.FieldID fieldID, Object obj, long[] jArr, int[] iArr, long[] jArr2, long[] jArr3) {
            if (obj instanceof Future) {
                Future future = (Future) obj;
                Object value = future.getValue();
                jArr2[i] = future.getClosure().getID(renderScript);
                Script.FieldID fieldID2 = future.getFieldID();
                jArr3[i] = fieldID2 != null ? fieldID2.getID(renderScript) : 0L;
                obj = value;
            } else {
                jArr2[i] = 0;
                jArr3[i] = 0;
            }
            if (obj instanceof Input) {
                Input input = (Input) obj;
                if (i < this.mArgs.length) {
                    input.addReference(this, i);
                } else {
                    input.addReference(this, fieldID);
                }
                jArr[i] = 0;
                iArr[i] = 0;
                return;
            }
            ValueAndSize valueAndSize = new ValueAndSize(renderScript, obj);
            jArr[i] = valueAndSize.value;
            iArr[i] = valueAndSize.size;
        }

        public Future getReturn() {
            if (this.mReturnFuture == null) {
                this.mReturnFuture = new Future(this, null, this.mReturnValue);
            }
            return this.mReturnFuture;
        }

        public Future getGlobal(Script.FieldID fieldID) {
            Future future = this.mGlobalFuture.get(fieldID);
            if (future != null) {
                return future;
            }
            Object obj = this.mBindings.get(fieldID);
            if (obj instanceof Future) {
                obj = ((Future) obj).getValue();
            }
            Future future2 = new Future(this, fieldID, obj);
            this.mGlobalFuture.put(fieldID, future2);
            return future2;
        }

        void setArg(int i, Object obj) {
            if (obj instanceof Future) {
                obj = ((Future) obj).getValue();
            }
            this.mArgs[i] = obj;
            ValueAndSize valueAndSize = new ValueAndSize(this.mRS, obj);
            this.mRS.nClosureSetArg(getID(this.mRS), i, valueAndSize.value, valueAndSize.size);
        }

        void setGlobal(Script.FieldID fieldID, Object obj) {
            if (obj instanceof Future) {
                obj = ((Future) obj).getValue();
            }
            this.mBindings.put(fieldID, obj);
            ValueAndSize valueAndSize = new ValueAndSize(this.mRS, obj);
            this.mRS.nClosureSetGlobal(getID(this.mRS), fieldID.getID(this.mRS), valueAndSize.value, valueAndSize.size);
        }

        private static final class ValueAndSize {
            public int size;
            public long value;

            public ValueAndSize(RenderScript renderScript, Object obj) {
                if (obj instanceof Allocation) {
                    this.value = ((Allocation) obj).getID(renderScript);
                    this.size = -1;
                    return;
                }
                if (obj instanceof Boolean) {
                    this.value = ((Boolean) obj).booleanValue() ? 1L : 0L;
                    this.size = 4;
                    return;
                }
                if (obj instanceof Integer) {
                    this.value = ((Integer) obj).longValue();
                    this.size = 4;
                    return;
                }
                if (obj instanceof Long) {
                    this.value = ((Long) obj).longValue();
                    this.size = 8;
                } else if (obj instanceof Float) {
                    this.value = Float.floatToRawIntBits(((Float) obj).floatValue());
                    this.size = 4;
                } else if (obj instanceof Double) {
                    this.value = Double.doubleToRawLongBits(((Double) obj).doubleValue());
                    this.size = 8;
                }
            }
        }
    }

    public static final class Future {
        Closure mClosure;
        Script.FieldID mFieldID;
        Object mValue;

        Future(Closure closure, Script.FieldID fieldID, Object obj) {
            this.mClosure = closure;
            this.mFieldID = fieldID;
            this.mValue = obj;
        }

        Closure getClosure() {
            return this.mClosure;
        }

        Script.FieldID getFieldID() {
            return this.mFieldID;
        }

        Object getValue() {
            return this.mValue;
        }
    }

    public static final class Input {
        Object mValue;
        List<Pair<Closure, Script.FieldID>> mFieldID = new ArrayList();
        List<Pair<Closure, Integer>> mArgIndex = new ArrayList();

        Input() {
        }

        void addReference(Closure closure, int i) {
            this.mArgIndex.add(Pair.create(closure, Integer.valueOf(i)));
        }

        void addReference(Closure closure, Script.FieldID fieldID) {
            this.mFieldID.add(Pair.create(closure, fieldID));
        }

        void set(Object obj) {
            this.mValue = obj;
            for (Pair<Closure, Integer> pair : this.mArgIndex) {
                pair.first.setArg(pair.second.intValue(), obj);
            }
            for (Pair<Closure, Script.FieldID> pair2 : this.mFieldID) {
                pair2.first.setGlobal(pair2.second, obj);
            }
        }

        Object get() {
            return this.mValue;
        }
    }

    ScriptGroup(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    ScriptGroup(RenderScript renderScript, String str, List<Closure> list, List<Input> list2, Future[] futureArr) {
        super(0L, renderScript);
        this.mName = str;
        this.mClosures = list;
        this.mInputs2 = list2;
        this.mOutputs2 = futureArr;
        int size = list.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = list.get(i).getID(renderScript);
        }
        setID(renderScript.nScriptGroup2Create(str, RenderScript.getCachePath(), jArr));
        this.guard.open("destroy");
    }

    public Object[] execute(Object... objArr) {
        if (objArr.length < this.mInputs2.size()) {
            Log.e(TAG, toString() + " receives " + objArr.length + " inputs, less than expected " + this.mInputs2.size());
            return null;
        }
        if (objArr.length > this.mInputs2.size()) {
            Log.i(TAG, toString() + " receives " + objArr.length + " inputs, more than expected " + this.mInputs2.size());
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mInputs2.size(); i2++) {
            Object obj = objArr[i2];
            if ((obj instanceof Future) || (obj instanceof Input)) {
                Log.e(TAG, toString() + ": input " + i2 + " is a future or unbound value");
                return null;
            }
            this.mInputs2.get(i2).set(obj);
        }
        this.mRS.nScriptGroup2Execute(getID(this.mRS));
        Future[] futureArr = this.mOutputs2;
        Object[] objArr2 = new Object[futureArr.length];
        int length = futureArr.length;
        int i3 = 0;
        while (i < length) {
            Object value = futureArr[i].getValue();
            if (value instanceof Input) {
                value = ((Input) value).get();
            }
            objArr2[i3] = value;
            i++;
            i3++;
        }
        return objArr2;
    }

    public void setInput(Script.KernelID kernelID, Allocation allocation) {
        int i = 0;
        while (true) {
            IO[] ioArr = this.mInputs;
            if (i < ioArr.length) {
                if (ioArr[i].mKID == kernelID) {
                    this.mInputs[i].mAllocation = allocation;
                    this.mRS.nScriptGroupSetInput(getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
                    return;
                }
                i++;
            } else {
                throw new RSIllegalArgumentException("Script not found");
            }
        }
    }

    public void setOutput(Script.KernelID kernelID, Allocation allocation) {
        int i = 0;
        while (true) {
            IO[] ioArr = this.mOutputs;
            if (i < ioArr.length) {
                if (ioArr[i].mKID == kernelID) {
                    this.mOutputs[i].mAllocation = allocation;
                    this.mRS.nScriptGroupSetOutput(getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
                    return;
                }
                i++;
            } else {
                throw new RSIllegalArgumentException("Script not found");
            }
        }
    }

    public void execute() {
        this.mRS.nScriptGroupExecute(getID(this.mRS));
    }

    public static final class Builder {
        private int mKernelCount;
        private RenderScript mRS;
        private ArrayList<Node> mNodes = new ArrayList<>();
        private ArrayList<ConnectLine> mLines = new ArrayList<>();

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        private void validateCycle(Node node, Node node2) {
            for (int i = 0; i < node.mOutputs.size(); i++) {
                ConnectLine connectLine = node.mOutputs.get(i);
                if (connectLine.mToK != null) {
                    Node findNode = findNode(connectLine.mToK.mScript);
                    if (findNode.equals(node2)) {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                    validateCycle(findNode, node2);
                }
                if (connectLine.mToF != null) {
                    Node findNode2 = findNode(connectLine.mToF.mScript);
                    if (findNode2.equals(node2)) {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                    validateCycle(findNode2, node2);
                }
            }
        }

        private void mergeDAGs(int i, int i2) {
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                if (this.mNodes.get(i3).dagNumber == i2) {
                    this.mNodes.get(i3).dagNumber = i;
                }
            }
        }

        private void validateDAGRecurse(Node node, int i) {
            if (node.dagNumber != 0 && node.dagNumber != i) {
                mergeDAGs(node.dagNumber, i);
                return;
            }
            node.dagNumber = i;
            for (int i2 = 0; i2 < node.mOutputs.size(); i2++) {
                ConnectLine connectLine = node.mOutputs.get(i2);
                if (connectLine.mToK != null) {
                    validateDAGRecurse(findNode(connectLine.mToK.mScript), i);
                }
                if (connectLine.mToF != null) {
                    validateDAGRecurse(findNode(connectLine.mToF.mScript), i);
                }
            }
        }

        private void validateDAG() {
            for (int i = 0; i < this.mNodes.size(); i++) {
                Node node = this.mNodes.get(i);
                if (node.mInputs.size() == 0) {
                    if (node.mOutputs.size() == 0 && this.mNodes.size() > 1) {
                        throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                    }
                    validateDAGRecurse(node, i + 1);
                }
            }
            int i2 = this.mNodes.get(0).dagNumber;
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                if (this.mNodes.get(i3).dagNumber != i2) {
                    throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
                }
            }
        }

        private Node findNode(Script script) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                if (script == this.mNodes.get(i).mScript) {
                    return this.mNodes.get(i);
                }
            }
            return null;
        }

        private Node findNode(Script.KernelID kernelID) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                Node node = this.mNodes.get(i);
                for (int i2 = 0; i2 < node.mKernels.size(); i2++) {
                    if (kernelID == node.mKernels.get(i2)) {
                        return node;
                    }
                }
            }
            return null;
        }

        public Builder addKernel(Script.KernelID kernelID) {
            if (this.mLines.size() != 0) {
                throw new RSInvalidStateException("Kernels may not be added once connections exist.");
            }
            if (findNode(kernelID) != null) {
                return this;
            }
            this.mKernelCount++;
            Node findNode = findNode(kernelID.mScript);
            if (findNode == null) {
                findNode = new Node(kernelID.mScript);
                this.mNodes.add(findNode);
            }
            findNode.mKernels.add(kernelID);
            return this;
        }

        public Builder addConnection(Type type, Script.KernelID kernelID, Script.FieldID fieldID) {
            Node findNode = findNode(kernelID);
            if (findNode == null) {
                throw new RSInvalidStateException("From script not found.");
            }
            Node findNode2 = findNode(fieldID.mScript);
            if (findNode2 == null) {
                throw new RSInvalidStateException("To script not found.");
            }
            ConnectLine connectLine = new ConnectLine(type, kernelID, fieldID);
            this.mLines.add(new ConnectLine(type, kernelID, fieldID));
            findNode.mOutputs.add(connectLine);
            findNode2.mInputs.add(connectLine);
            validateCycle(findNode, findNode);
            return this;
        }

        public Builder addConnection(Type type, Script.KernelID kernelID, Script.KernelID kernelID2) {
            Node findNode = findNode(kernelID);
            if (findNode == null) {
                throw new RSInvalidStateException("From script not found.");
            }
            Node findNode2 = findNode(kernelID2);
            if (findNode2 == null) {
                throw new RSInvalidStateException("To script not found.");
            }
            ConnectLine connectLine = new ConnectLine(type, kernelID, kernelID2);
            this.mLines.add(new ConnectLine(type, kernelID, kernelID2));
            findNode.mOutputs.add(connectLine);
            findNode2.mInputs.add(connectLine);
            validateCycle(findNode, findNode);
            return this;
        }

        public ScriptGroup create() {
            if (this.mNodes.size() == 0) {
                throw new RSInvalidStateException("Empty script groups are not allowed");
            }
            for (int i = 0; i < this.mNodes.size(); i++) {
                this.mNodes.get(i).dagNumber = 0;
            }
            validateDAG();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            long[] jArr = new long[this.mKernelCount];
            int i2 = 0;
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                Node node = this.mNodes.get(i3);
                int i4 = 0;
                while (i4 < node.mKernels.size()) {
                    Script.KernelID kernelID = node.mKernels.get(i4);
                    int i5 = i2 + 1;
                    jArr[i2] = kernelID.getID(this.mRS);
                    boolean z = false;
                    for (int i6 = 0; i6 < node.mInputs.size(); i6++) {
                        if (node.mInputs.get(i6).mToK == kernelID) {
                            z = true;
                        }
                    }
                    boolean z2 = false;
                    for (int i7 = 0; i7 < node.mOutputs.size(); i7++) {
                        if (node.mOutputs.get(i7).mFrom == kernelID) {
                            z2 = true;
                        }
                    }
                    if (!z) {
                        arrayList.add(new IO(kernelID));
                    }
                    if (!z2) {
                        arrayList2.add(new IO(kernelID));
                    }
                    i4++;
                    i2 = i5;
                }
            }
            if (i2 != this.mKernelCount) {
                throw new RSRuntimeException("Count mismatch, should not happen.");
            }
            long[] jArr2 = new long[this.mLines.size()];
            long[] jArr3 = new long[this.mLines.size()];
            long[] jArr4 = new long[this.mLines.size()];
            long[] jArr5 = new long[this.mLines.size()];
            for (int i8 = 0; i8 < this.mLines.size(); i8++) {
                ConnectLine connectLine = this.mLines.get(i8);
                jArr2[i8] = connectLine.mFrom.getID(this.mRS);
                if (connectLine.mToK != null) {
                    jArr3[i8] = connectLine.mToK.getID(this.mRS);
                }
                if (connectLine.mToF != null) {
                    jArr4[i8] = connectLine.mToF.getID(this.mRS);
                }
                jArr5[i8] = connectLine.mAllocationType.getID(this.mRS);
            }
            long nScriptGroupCreate = this.mRS.nScriptGroupCreate(jArr, jArr2, jArr3, jArr4, jArr5);
            if (nScriptGroupCreate == 0) {
                throw new RSRuntimeException("Object creation error, should not happen.");
            }
            ScriptGroup scriptGroup = new ScriptGroup(nScriptGroupCreate, this.mRS);
            scriptGroup.mOutputs = new IO[arrayList2.size()];
            for (int i9 = 0; i9 < arrayList2.size(); i9++) {
                scriptGroup.mOutputs[i9] = (IO) arrayList2.get(i9);
            }
            scriptGroup.mInputs = new IO[arrayList.size()];
            for (int i10 = 0; i10 < arrayList.size(); i10++) {
                scriptGroup.mInputs[i10] = (IO) arrayList.get(i10);
            }
            return scriptGroup;
        }
    }

    public static final class Binding {
        private final Script.FieldID mField;
        private final Object mValue;

        public Binding(Script.FieldID fieldID, Object obj) {
            this.mField = fieldID;
            this.mValue = obj;
        }

        Script.FieldID getField() {
            return this.mField;
        }

        Object getValue() {
            return this.mValue;
        }
    }

    public static final class Builder2 {
        private static final String TAG = "ScriptGroup.Builder2";
        List<Closure> mClosures = new ArrayList();
        List<Input> mInputs = new ArrayList();
        RenderScript mRS;

        public Builder2(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        private Closure addKernelInternal(Script.KernelID kernelID, Type type, Object[] objArr, Map<Script.FieldID, Object> map) {
            Closure closure = new Closure(this.mRS, kernelID, type, objArr, map);
            this.mClosures.add(closure);
            return closure;
        }

        private Closure addInvokeInternal(Script.InvokeID invokeID, Object[] objArr, Map<Script.FieldID, Object> map) {
            Closure closure = new Closure(this.mRS, invokeID, objArr, map);
            this.mClosures.add(closure);
            return closure;
        }

        public Input addInput() {
            Input input = new Input();
            this.mInputs.add(input);
            return input;
        }

        public Closure addKernel(Script.KernelID kernelID, Type type, Object... objArr) {
            ArrayList<Object> arrayList = new ArrayList<>();
            HashMap hashMap = new HashMap();
            if (seperateArgsAndBindings(objArr, arrayList, hashMap)) {
                return addKernelInternal(kernelID, type, arrayList.toArray(), hashMap);
            }
            return null;
        }

        public Closure addInvoke(Script.InvokeID invokeID, Object... objArr) {
            ArrayList<Object> arrayList = new ArrayList<>();
            HashMap hashMap = new HashMap();
            if (seperateArgsAndBindings(objArr, arrayList, hashMap)) {
                return addInvokeInternal(invokeID, arrayList.toArray(), hashMap);
            }
            return null;
        }

        public ScriptGroup create(String str, Future... futureArr) {
            if (str == null || str.isEmpty() || str.length() > 100 || !str.equals(str.replaceAll("[^a-zA-Z0-9-]", Session.SESSION_SEPARATION_CHAR_CHILD))) {
                throw new RSIllegalArgumentException("invalid script group name");
            }
            ScriptGroup scriptGroup = new ScriptGroup(this.mRS, str, this.mClosures, this.mInputs, futureArr);
            this.mClosures = new ArrayList();
            this.mInputs = new ArrayList();
            return scriptGroup;
        }

        private boolean seperateArgsAndBindings(Object[] objArr, ArrayList<Object> arrayList, Map<Script.FieldID, Object> map) {
            int i = 0;
            while (i < objArr.length) {
                Object obj = objArr[i];
                if (obj instanceof Binding) {
                    break;
                }
                arrayList.add(obj);
                i++;
            }
            while (i < objArr.length) {
                Object obj2 = objArr[i];
                if (!(obj2 instanceof Binding)) {
                    return false;
                }
                Binding binding = (Binding) obj2;
                map.put(binding.getField(), binding.getValue());
                i++;
            }
            return true;
        }
    }

    @Override // android.renderscript.BaseObj
    public void destroy() {
        super.destroy();
        List<Closure> list = this.mClosures;
        if (list != null) {
            Iterator<Closure> it = list.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
        }
    }
}
