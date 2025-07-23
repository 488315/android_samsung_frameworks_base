package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.format.ImageFormat;
import android.graphics.Bitmap;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class ImageEncoder extends Filter {

    @GenerateFieldPort(name = "stream")
    private OutputStream mOutputStream;

    @GenerateFieldPort(hasDefault = true, name = "quality")
    private int mQuality;

    public ImageEncoder(String str) {
        super(str);
        this.mQuality = 80;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("image", ImageFormat.create(3, 0));
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        pullInput("image").getBitmap().compress(Bitmap.CompressFormat.JPEG, this.mQuality, this.mOutputStream);
    }
}
