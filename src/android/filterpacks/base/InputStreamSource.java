package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.PrimitiveFormat;
import android.provider.Telephony;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class InputStreamSource extends Filter {

    @GenerateFieldPort(name = "stream")
    private InputStream mInputStream;

    @GenerateFinalPort(hasDefault = true, name = Telephony.CellBroadcasts.MESSAGE_FORMAT)
    private MutableFrameFormat mOutputFormat;

    @GenerateFinalPort(name = "target")
    private String mTarget;

    public InputStreamSource(String str) {
        super(str);
        this.mOutputFormat = null;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        int targetString = FrameFormat.readTargetString(this.mTarget);
        if (this.mOutputFormat == null) {
            this.mOutputFormat = PrimitiveFormat.createByteFormat(targetString);
        }
        addOutputPort("data", this.mOutputFormat);
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            int i = 0;
            while (true) {
                int i2 = this.mInputStream.read(bArr);
                if (i2 > 0) {
                    byteArrayOutputStream.write(bArr, 0, i2);
                    i += i2;
                } else {
                    ByteBuffer byteBufferWrap = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                    this.mOutputFormat.setDimensions(i);
                    Frame frameNewFrame = filterContext.getFrameManager().newFrame(this.mOutputFormat);
                    frameNewFrame.setData(byteBufferWrap);
                    pushOutput("data", frameNewFrame);
                    frameNewFrame.release();
                    closeOutputPort("data");
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("InputStreamSource: Could not read stream: " + e.getMessage() + "!");
        }
    }
}
