package android.filterfw.core;

import java.lang.reflect.Field;

/* loaded from: classes.dex */
public class ProgramPort extends FieldPort {
    protected String mVarName;

    public ProgramPort(Filter filter, String str, String str2, Field field, boolean z) {
        super(filter, str, field, z);
        this.mVarName = str2;
    }

    @Override // android.filterfw.core.FieldPort, android.filterfw.core.FilterPort
    public String toString() {
        return "Program " + super.toString();
    }

    @Override // android.filterfw.core.FieldPort, android.filterfw.core.InputPort
    public synchronized void transfer(FilterContext filterContext) {
        if (this.mValueWaiting) {
            try {
                Object obj = this.mField.get(this.mFilter);
                if (obj != null) {
                    ((Program) obj).setHostValue(this.mVarName, this.mValue);
                    this.mValueWaiting = false;
                }
            } catch (ClassCastException unused) {
                throw new RuntimeException("Non Program field '" + this.mField.getName() + "' annotated with ProgramParameter!");
            } catch (IllegalAccessException unused2) {
                throw new RuntimeException("Access to program field '" + this.mField.getName() + "' was denied!");
            }
        }
    }
}
