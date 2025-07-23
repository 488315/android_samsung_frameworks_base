package android.filterfw.core;

import java.lang.reflect.Field;

/* loaded from: classes.dex */
public class FieldPort extends InputPort {
    protected Field mField;
    protected boolean mHasFrame;
    protected Object mValue;
    protected boolean mValueWaiting;

    @Override // android.filterfw.core.FilterPort
    public void clear() {
    }

    public FieldPort(Filter filter, String str, Field field, boolean z) {
        super(filter, str);
        this.mValueWaiting = false;
        this.mField = field;
        this.mHasFrame = z;
    }

    @Override // android.filterfw.core.FilterPort
    public void pushFrame(Frame frame) {
        setFieldFrame(frame, false);
    }

    @Override // android.filterfw.core.FilterPort
    public void setFrame(Frame frame) {
        setFieldFrame(frame, true);
    }

    @Override // android.filterfw.core.InputPort
    public Object getTarget() {
        try {
            return this.mField.get(this.mFilter);
        } catch (IllegalAccessException unused) {
            return null;
        }
    }

    @Override // android.filterfw.core.InputPort
    public synchronized void transfer(FilterContext filterContext) {
        if (this.mValueWaiting) {
            try {
                this.mField.set(this.mFilter, this.mValue);
                this.mValueWaiting = false;
                if (filterContext != null) {
                    this.mFilter.notifyFieldPortValueUpdated(this.mName, filterContext);
                }
            } catch (IllegalAccessException unused) {
                throw new RuntimeException("Access to field '" + this.mField.getName() + "' was denied!");
            }
        }
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized Frame pullFrame() {
        throw new RuntimeException("Cannot pull frame on " + this + "!");
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized boolean hasFrame() {
        return this.mHasFrame;
    }

    @Override // android.filterfw.core.InputPort
    public synchronized boolean acceptsFrame() {
        return !this.mValueWaiting;
    }

    @Override // android.filterfw.core.FilterPort
    public String toString() {
        return "field " + super.toString();
    }

    protected synchronized void setFieldFrame(Frame frame, boolean z) {
        assertPortIsOpen();
        checkFrameType(frame, z);
        Object objectValue = frame.getObjectValue();
        if ((objectValue == null && this.mValue != null) || !objectValue.equals(this.mValue)) {
            this.mValue = objectValue;
            this.mValueWaiting = true;
        }
        this.mHasFrame = true;
    }
}
