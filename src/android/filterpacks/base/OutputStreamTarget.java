package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GenerateFieldPort;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class OutputStreamTarget extends Filter {

    @GenerateFieldPort(name = "stream")
    private OutputStream mOutputStream;

    public OutputStreamTarget(String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("data");
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        ByteBuffer data;
        Frame pullInput = pullInput("data");
        if (pullInput.getFormat().getObjectClass() == String.class) {
            data = ByteBuffer.wrap(((String) pullInput.getObjectValue()).getBytes());
        } else {
            data = pullInput.getData();
        }
        try {
            this.mOutputStream.write(data.array(), 0, data.limit());
            this.mOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("OutputStreamTarget: Could not write to stream: " + e.getMessage() + "!");
        }
    }
}
