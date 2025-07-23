package android.media.effect;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterFactory;
import android.filterfw.core.FilterFunction;
import android.filterfw.core.Frame;
import com.samsung.epic.Request;

/* loaded from: classes3.dex */
public class SingleFilterEffect extends FilterEffect {
    protected FilterFunction mFunction;
    protected String mInputName;
    private boolean mLocked;
    protected String mOutputName;
    private Request mScenario;

    public SingleFilterEffect(EffectContext effectContext, String str, Class cls, String str2, String str3, Object... objArr) {
        super(effectContext, str);
        this.mLocked = false;
        this.mInputName = str2;
        this.mOutputName = str3;
        Filter createFilterByClass = FilterFactory.sharedFactory().createFilterByClass(cls, cls.getSimpleName());
        createFilterByClass.initWithAssignmentList(objArr);
        this.mFunction = new FilterFunction(getFilterContext(), createFilterByClass);
    }

    @Override // android.media.effect.Effect
    public void apply(int i, int i2, int i3, int i4) {
        beginGLEffect();
        Frame frameFromTexture = frameFromTexture(i, i2, i3);
        Frame frameFromTexture2 = frameFromTexture(i4, i2, i3);
        Frame executeWithArgList = this.mFunction.executeWithArgList(this.mInputName, frameFromTexture);
        frameFromTexture2.setDataFromFrame(executeWithArgList);
        frameFromTexture.release();
        frameFromTexture2.release();
        executeWithArgList.release();
        endGLEffect();
    }

    @Override // android.media.effect.Effect
    public void setParameter(String str, Object obj) {
        this.mFunction.setInputValue(str, obj);
    }

    @Override // android.media.effect.Effect
    public void release() {
        this.mFunction.tearDown();
        this.mFunction = null;
    }
}
