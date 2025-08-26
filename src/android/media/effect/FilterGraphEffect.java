package android.media.effect;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.GraphRunner;
import android.filterfw.core.SyncRunner;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.TextGraphReader;

/* loaded from: classes3.dex */
public class FilterGraphEffect extends FilterEffect {
    private static final String TAG = "FilterGraphEffect";
    protected FilterGraph mGraph;
    protected String mInputName;
    protected String mOutputName;
    protected GraphRunner mRunner;
    protected Class mSchedulerClass;

    @Override // android.media.effect.Effect
    public void setParameter(String str, Object obj) {
    }

    public FilterGraphEffect(EffectContext effectContext, String str, String str2, String str3, String str4, Class cls) {
        super(effectContext, str);
        this.mInputName = str3;
        this.mOutputName = str4;
        this.mSchedulerClass = cls;
        createGraph(str2);
    }

    private void createGraph(String str) {
        try {
            FilterGraph graphString = new TextGraphReader().readGraphString(str);
            this.mGraph = graphString;
            if (graphString == null) {
                throw new RuntimeException("Could not setup effect");
            }
            this.mRunner = new SyncRunner(getFilterContext(), this.mGraph, this.mSchedulerClass);
        } catch (GraphIOException e) {
            throw new RuntimeException("Could not setup effect", e);
        }
    }

    @Override // android.media.effect.Effect
    public void apply(int i, int i2, int i3, int i4) {
        beginGLEffect();
        Filter filter = this.mGraph.getFilter(this.mInputName);
        if (filter != null) {
            filter.setInputValue("texId", Integer.valueOf(i));
            filter.setInputValue("width", Integer.valueOf(i2));
            filter.setInputValue("height", Integer.valueOf(i3));
            Filter filter2 = this.mGraph.getFilter(this.mOutputName);
            if (filter2 != null) {
                filter2.setInputValue("texId", Integer.valueOf(i4));
                try {
                    this.mRunner.run();
                    endGLEffect();
                    return;
                } catch (RuntimeException e) {
                    throw new RuntimeException("Internal error applying effect: ", e);
                }
            }
            throw new RuntimeException("Internal error applying effect");
        }
        throw new RuntimeException("Internal error applying effect");
    }

    @Override // android.media.effect.Effect
    public void release() {
        this.mGraph.tearDown(getFilterContext());
        this.mGraph = null;
    }
}
