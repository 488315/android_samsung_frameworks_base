package com.samsung.android.sume.core.plugin;

import com.samsung.android.sume.core.functional.BufferProcessor;
import com.samsung.android.sume.core.functional.ExecuteDelegator;
import com.samsung.android.sume.core.functional.ModelLoader;
import com.samsung.android.sume.core.functional.ModelPathLoader;
import com.samsung.android.sume.core.functional.ModelSelector;
import com.samsung.android.sume.core.types.p045nn.NNFileDescriptor;
import java.util.Optional;

/* loaded from: classes4.dex */
public class NNPlugin extends PluginFixture<NNPlugin> {
    private ExecuteDelegator executeDelegator;
    private ModelLoader<NNFileDescriptor> modelLoader;
    private ModelPathLoader modelPathLoader;
    private ModelSelector modelSelector;
    private BufferProcessor postExecutor;
    private BufferProcessor preExecutor;

    public NNPlugin(Plugin<NNPlugin> plugin) {
        super(plugin);
    }

    @Override // com.samsung.android.sume.core.plugin.PluginFixture
    public boolean validate() {
        return (this.modelLoader == null && this.modelPathLoader == null) ? false : true;
    }

    public ModelLoader<NNFileDescriptor> getModelLoader() {
        return this.modelLoader;
    }

    public void setModelLoader(ModelLoader<NNFileDescriptor> modelLoader) {
        this.modelLoader = modelLoader;
    }

    public ModelPathLoader getModelPathLoader() {
        return this.modelPathLoader;
    }

    public void setModelPathLoader(ModelPathLoader modelPathLoader) {
        this.modelPathLoader = modelPathLoader;
    }

    public void setModelSelector(ModelSelector modelSelector) {
        this.modelSelector = modelSelector;
    }

    public ModelSelector getModelSelector() {
        return this.modelSelector;
    }

    public Optional<BufferProcessor> getPreExecutor() {
        return Optional.ofNullable(this.preExecutor);
    }

    public void setPreExecutor(BufferProcessor preExecutor) {
        this.preExecutor = preExecutor;
    }

    public ExecuteDelegator getExecuteDelegator() {
        return this.executeDelegator;
    }

    public void setExecuteDelegator(ExecuteDelegator executeDelegator) {
        this.executeDelegator = executeDelegator;
    }

    public BufferProcessor getPostExecutor() {
        return this.postExecutor;
    }

    public void setPostExecutor(BufferProcessor postExecutor) {
        this.postExecutor = postExecutor;
    }
}
