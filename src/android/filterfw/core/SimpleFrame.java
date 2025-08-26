package android.filterfw.core;

import android.filterfw.format.ObjectFormat;
import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class SimpleFrame extends Frame {
    private Object mObject;

    @Override // android.filterfw.core.Frame
    protected boolean hasNativeAllocation() {
        return false;
    }

    @Override // android.filterfw.core.Frame
    protected void releaseNativeAllocation() {
    }

    SimpleFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        initWithFormat(frameFormat);
        setReusable(false);
    }

    static SimpleFrame wrapObject(Object obj, FrameManager frameManager) {
        SimpleFrame simpleFrame = new SimpleFrame(ObjectFormat.fromObject(obj, 1), frameManager);
        simpleFrame.setObjectValue(obj);
        return simpleFrame;
    }

    private void initWithFormat(FrameFormat frameFormat) {
        int length = frameFormat.getLength();
        int baseType = frameFormat.getBaseType();
        if (baseType == 2) {
            this.mObject = new byte[length];
            return;
        }
        if (baseType == 3) {
            this.mObject = new short[length];
            return;
        }
        if (baseType == 4) {
            this.mObject = new int[length];
            return;
        }
        if (baseType == 5) {
            this.mObject = new float[length];
        } else if (baseType == 6) {
            this.mObject = new double[length];
        } else {
            this.mObject = null;
        }
    }

    @Override // android.filterfw.core.Frame
    public Object getObjectValue() {
        return this.mObject;
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        setGenericObjectValue(iArr);
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        Object obj = this.mObject;
        if (obj instanceof int[]) {
            return (int[]) obj;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        setGenericObjectValue(fArr);
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        Object obj = this.mObject;
        if (obj instanceof float[]) {
            return (float[]) obj;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setData(ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        setGenericObjectValue(ByteBuffer.wrap(byteBuffer.array(), i, i2));
    }

    @Override // android.filterfw.core.Frame
    public ByteBuffer getData() {
        Object obj = this.mObject;
        if (obj instanceof ByteBuffer) {
            return (ByteBuffer) obj;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(Bitmap bitmap) {
        assertFrameMutable();
        setGenericObjectValue(bitmap);
    }

    @Override // android.filterfw.core.Frame
    public Bitmap getBitmap() {
        Object obj = this.mObject;
        if (obj instanceof Bitmap) {
            return (Bitmap) obj;
        }
        return null;
    }

    private void setFormatObjectClass(Class cls) {
        MutableFrameFormat mutableFrameFormatMutableCopy = getFormat().mutableCopy();
        mutableFrameFormatMutableCopy.setObjectClass(cls);
        setFormat(mutableFrameFormatMutableCopy);
    }

    @Override // android.filterfw.core.Frame
    protected void setGenericObjectValue(Object obj) {
        FrameFormat format = getFormat();
        if (format.getObjectClass() == null) {
            setFormatObjectClass(obj.getClass());
        } else if (!format.getObjectClass().isAssignableFrom(obj.getClass())) {
            throw new RuntimeException("Attempting to set object value of type '" + obj.getClass() + "' on SimpleFrame of type '" + format.getObjectClass() + "'!");
        }
        this.mObject = obj;
    }

    public String toString() {
        return "SimpleFrame (" + getFormat() + NavigationBarInflaterView.KEY_CODE_END;
    }
}
