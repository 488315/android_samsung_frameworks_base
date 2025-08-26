package android.renderscript;

import android.util.SparseArray;
import java.io.UnsupportedEncodingException;

@Deprecated
/* loaded from: classes3.dex */
public class Script extends BaseObj {
    private final SparseArray<FieldID> mFIDs;
    private final SparseArray<InvokeID> mIIDs;
    long[] mInIdsBuffer;
    private final SparseArray<KernelID> mKIDs;

    public static final class KernelID extends BaseObj {
        Script mScript;
        int mSig;
        int mSlot;

        KernelID(long j, RenderScript renderScript, Script script, int i, int i2) {
            super(j, renderScript);
            this.mScript = script;
            this.mSlot = i;
            this.mSig = i2;
        }
    }

    protected KernelID createKernelID(int i, int i2, Element element, Element element2) throws Throwable {
        KernelID kernelID = this.mKIDs.get(i);
        if (kernelID != null) {
            return kernelID;
        }
        long jNScriptKernelIDCreate = this.mRS.nScriptKernelIDCreate(getID(this.mRS), i, i2);
        if (jNScriptKernelIDCreate == 0) {
            throw new RSDriverException("Failed to create KernelID");
        }
        KernelID kernelID2 = new KernelID(jNScriptKernelIDCreate, this.mRS, this, i, i2);
        this.mKIDs.put(i, kernelID2);
        return kernelID2;
    }

    public static final class InvokeID extends BaseObj {
        Script mScript;
        int mSlot;

        InvokeID(long j, RenderScript renderScript, Script script, int i) {
            super(j, renderScript);
            this.mScript = script;
            this.mSlot = i;
        }
    }

    protected InvokeID createInvokeID(int i) throws Throwable {
        InvokeID invokeID = this.mIIDs.get(i);
        if (invokeID != null) {
            return invokeID;
        }
        long jNScriptInvokeIDCreate = this.mRS.nScriptInvokeIDCreate(getID(this.mRS), i);
        if (jNScriptInvokeIDCreate == 0) {
            throw new RSDriverException("Failed to create KernelID");
        }
        InvokeID invokeID2 = new InvokeID(jNScriptInvokeIDCreate, this.mRS, this, i);
        this.mIIDs.put(i, invokeID2);
        return invokeID2;
    }

    public static final class FieldID extends BaseObj {
        Script mScript;
        int mSlot;

        FieldID(long j, RenderScript renderScript, Script script, int i) {
            super(j, renderScript);
            this.mScript = script;
            this.mSlot = i;
        }
    }

    protected FieldID createFieldID(int i, Element element) throws Throwable {
        FieldID fieldID = this.mFIDs.get(i);
        if (fieldID != null) {
            return fieldID;
        }
        long jNScriptFieldIDCreate = this.mRS.nScriptFieldIDCreate(getID(this.mRS), i);
        if (jNScriptFieldIDCreate == 0) {
            throw new RSDriverException("Failed to create FieldID");
        }
        FieldID fieldID2 = new FieldID(jNScriptFieldIDCreate, this.mRS, this, i);
        this.mFIDs.put(i, fieldID2);
        return fieldID2;
    }

    protected void invoke(int i) throws Throwable {
        this.mRS.nScriptInvoke(getID(this.mRS), i);
    }

    protected void invoke(int i, FieldPacker fieldPacker) throws Throwable {
        if (fieldPacker != null) {
            this.mRS.nScriptInvokeV(getID(this.mRS), i, fieldPacker.getData());
        } else {
            this.mRS.nScriptInvoke(getID(this.mRS), i);
        }
    }

    protected void forEach(int i, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker) {
        forEach(i, allocation, allocation2, fieldPacker, (LaunchOptions) null);
    }

    protected void forEach(int i, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker, LaunchOptions launchOptions) {
        long[] jArr;
        this.mRS.validate();
        this.mRS.validateObject(allocation);
        this.mRS.validateObject(allocation2);
        if (allocation == null && allocation2 == null && launchOptions == null) {
            throw new RSIllegalArgumentException("At least one of input allocation, output allocation, or LaunchOptions is required to be non-null.");
        }
        if (allocation != null) {
            long[] jArr2 = this.mInIdsBuffer;
            jArr2[0] = allocation.getID(this.mRS);
            jArr = jArr2;
        } else {
            jArr = null;
        }
        this.mRS.nScriptForEach(getID(this.mRS), i, jArr, allocation2 != null ? allocation2.getID(this.mRS) : 0L, fieldPacker != null ? fieldPacker.getData() : null, launchOptions != null ? new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend} : null);
    }

    protected void forEach(int i, Allocation[] allocationArr, Allocation allocation, FieldPacker fieldPacker) {
        forEach(i, allocationArr, allocation, fieldPacker, (LaunchOptions) null);
    }

    protected void forEach(int i, Allocation[] allocationArr, Allocation allocation, FieldPacker fieldPacker, LaunchOptions launchOptions) {
        long[] jArr;
        this.mRS.validate();
        if (allocationArr != null) {
            for (Allocation allocation2 : allocationArr) {
                this.mRS.validateObject(allocation2);
            }
        }
        this.mRS.validateObject(allocation);
        if (allocationArr == null && allocation == null) {
            throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
        }
        if (allocationArr != null) {
            long[] jArr2 = new long[allocationArr.length];
            for (int i2 = 0; i2 < allocationArr.length; i2++) {
                jArr2[i2] = allocationArr[i2].getID(this.mRS);
            }
            jArr = jArr2;
        } else {
            jArr = null;
        }
        this.mRS.nScriptForEach(getID(this.mRS), i, jArr, allocation != null ? allocation.getID(this.mRS) : 0L, fieldPacker != null ? fieldPacker.getData() : null, launchOptions != null ? new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend} : null);
    }

    protected void reduce(int i, Allocation[] allocationArr, Allocation allocation, LaunchOptions launchOptions) {
        this.mRS.validate();
        if (allocationArr == null || allocationArr.length < 1) {
            throw new RSIllegalArgumentException("At least one input is required.");
        }
        if (allocation == null) {
            throw new RSIllegalArgumentException("aout is required to be non-null.");
        }
        for (Allocation allocation2 : allocationArr) {
            this.mRS.validateObject(allocation2);
        }
        long[] jArr = new long[allocationArr.length];
        for (int i2 = 0; i2 < allocationArr.length; i2++) {
            jArr[i2] = allocationArr[i2].getID(this.mRS);
        }
        this.mRS.nScriptReduce(getID(this.mRS), i, jArr, allocation.getID(this.mRS), launchOptions != null ? new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend} : null);
    }

    Script(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.mKIDs = new SparseArray<>();
        this.mIIDs = new SparseArray<>();
        this.mFIDs = new SparseArray<>();
        this.mInIdsBuffer = new long[1];
        this.guard.open("destroy");
    }

    public void bindAllocation(Allocation allocation, int i) throws Throwable {
        this.mRS.validate();
        this.mRS.validateObject(allocation);
        if (allocation != null) {
            if (this.mRS.getApplicationContext().getApplicationInfo().targetSdkVersion >= 20) {
                Type type = allocation.mType;
                if (type.hasMipmaps() || type.hasFaces() || type.getY() != 0 || type.getZ() != 0) {
                    throw new RSIllegalArgumentException("API 20+ only allows simple 1D allocations to be used with bind.");
                }
            }
            this.mRS.nScriptBindAllocation(getID(this.mRS), allocation.getID(this.mRS), i);
            return;
        }
        this.mRS.nScriptBindAllocation(getID(this.mRS), 0L, i);
    }

    public void setVar(int i, float f) throws Throwable {
        this.mRS.nScriptSetVarF(getID(this.mRS), i, f);
    }

    public float getVarF(int i) {
        return this.mRS.nScriptGetVarF(getID(this.mRS), i);
    }

    public void setVar(int i, double d) throws Throwable {
        this.mRS.nScriptSetVarD(getID(this.mRS), i, d);
    }

    public double getVarD(int i) {
        return this.mRS.nScriptGetVarD(getID(this.mRS), i);
    }

    public void setVar(int i, int i2) throws Throwable {
        this.mRS.nScriptSetVarI(getID(this.mRS), i, i2);
    }

    public int getVarI(int i) {
        return this.mRS.nScriptGetVarI(getID(this.mRS), i);
    }

    public void setVar(int i, long j) throws Throwable {
        this.mRS.nScriptSetVarJ(getID(this.mRS), i, j);
    }

    public long getVarJ(int i) {
        return this.mRS.nScriptGetVarJ(getID(this.mRS), i);
    }

    public void setVar(int i, boolean z) throws Throwable {
        this.mRS.nScriptSetVarI(getID(this.mRS), i, z ? 1 : 0);
    }

    public boolean getVarB(int i) {
        return this.mRS.nScriptGetVarI(getID(this.mRS), i) > 0;
    }

    public void setVar(int i, BaseObj baseObj) throws Throwable {
        this.mRS.validate();
        this.mRS.validateObject(baseObj);
        this.mRS.nScriptSetVarObj(getID(this.mRS), i, baseObj == null ? 0L : baseObj.getID(this.mRS));
    }

    public void setVar(int i, FieldPacker fieldPacker) throws Throwable {
        this.mRS.nScriptSetVarV(getID(this.mRS), i, fieldPacker.getData());
    }

    public void setVar(int i, FieldPacker fieldPacker, Element element, int[] iArr) {
        this.mRS.nScriptSetVarVE(getID(this.mRS), i, fieldPacker.getData(), element.getID(this.mRS), iArr);
    }

    public void getVarV(int i, FieldPacker fieldPacker) throws Throwable {
        this.mRS.nScriptGetVarV(getID(this.mRS), i, fieldPacker.getData());
    }

    public void setTimeZone(String str) throws Throwable {
        this.mRS.validate();
        try {
            this.mRS.nScriptSetTimeZone(getID(this.mRS), str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Builder {
        RenderScript mRS;

        Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }
    }

    public static class FieldBase {
        protected Allocation mAllocation;
        protected Element mElement;

        public void updateAllocation() {
        }

        protected void init(RenderScript renderScript, int i) {
            this.mAllocation = Allocation.createSized(renderScript, this.mElement, i, 1);
        }

        protected void init(RenderScript renderScript, int i, int i2) {
            this.mAllocation = Allocation.createSized(renderScript, this.mElement, i, i2 | 1);
        }

        protected FieldBase() {
        }

        public Element getElement() {
            return this.mElement;
        }

        public Type getType() {
            return this.mAllocation.getType();
        }

        public Allocation getAllocation() {
            return this.mAllocation;
        }
    }

    public static final class LaunchOptions {
        private int strategy;
        private int xstart = 0;
        private int ystart = 0;
        private int xend = 0;
        private int yend = 0;
        private int zstart = 0;
        private int zend = 0;

        public LaunchOptions setX(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new RSIllegalArgumentException("Invalid dimensions");
            }
            this.xstart = i;
            this.xend = i2;
            return this;
        }

        public LaunchOptions setY(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new RSIllegalArgumentException("Invalid dimensions");
            }
            this.ystart = i;
            this.yend = i2;
            return this;
        }

        public LaunchOptions setZ(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new RSIllegalArgumentException("Invalid dimensions");
            }
            this.zstart = i;
            this.zend = i2;
            return this;
        }

        public int getXStart() {
            return this.xstart;
        }

        public int getXEnd() {
            return this.xend;
        }

        public int getYStart() {
            return this.ystart;
        }

        public int getYEnd() {
            return this.yend;
        }

        public int getZStart() {
            return this.zstart;
        }

        public int getZEnd() {
            return this.zend;
        }
    }
}
