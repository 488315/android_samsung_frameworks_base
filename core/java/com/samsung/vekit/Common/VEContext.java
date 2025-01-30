package com.samsung.vekit.Common;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.FrcSupportInfo;
import com.samsung.vekit.Common.State.VEKitState;
import com.samsung.vekit.Common.State.VEStateInterface;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FpsType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.SpeedType;
import com.samsung.vekit.Common.Type.ViewMode;
import com.samsung.vekit.Layer.LayerGroup;
import com.samsung.vekit.Manager.AnimationManager;
import com.samsung.vekit.Manager.ContentManager;
import com.samsung.vekit.Manager.FilterManager;
import com.samsung.vekit.Manager.ItemManager;
import com.samsung.vekit.Manager.LayerManager;

/* loaded from: classes6.dex */
public class VEContext extends Element implements VEStateInterface {
    private final AnimationManager animationManager;
    private final ContentManager contentManager;
    private final FilterManager filterManager;
    private FrameworkType frameworkType;
    private FrcSupportInfo[] frcSupportInfo;
    private final ItemManager itemManager;
    private final LayerGroup layerGroup;
    private final LayerManager layerManager;
    private NativeInterfaceWrapper nativeInterfaceWrapper;
    private VEKitState state;

    public VEContext() {
        super(null, ElementType.CONTEXT, 0, "Context");
        this.itemManager = new ItemManager(this);
        this.layerManager = new LayerManager(this);
        this.animationManager = new AnimationManager(this);
        this.contentManager = new ContentManager(this);
        this.filterManager = new FilterManager(this);
        this.layerGroup = new LayerGroup(this);
        this.frcSupportInfo = new FrcSupportInfo[ViewMode.values().length];
        setState(VEKitState.IDLE);
        this.nativeInterfaceWrapper = new NativeInterfaceWrapper(this);
    }

    public void initialize() {
        this.nativeInterfaceWrapper.create(this.layerGroup);
        this.frcSupportInfo[ViewMode.PREVIEW.ordinal()] = this.nativeInterfaceWrapper.getFrcSupportInfo(ViewMode.PREVIEW.ordinal());
        this.frcSupportInfo[ViewMode.EXPORT.ordinal()] = this.nativeInterfaceWrapper.getFrcSupportInfo(ViewMode.EXPORT.ordinal());
    }

    public void release() {
        this.nativeInterfaceWrapper.releaseFramework();
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public LayerManager getLayerManager() {
        return this.layerManager;
    }

    public AnimationManager getAnimationManager() {
        return this.animationManager;
    }

    public ContentManager getContentManager() {
        return this.contentManager;
    }

    public FilterManager getFilterManager() {
        return this.filterManager;
    }

    public LayerGroup getLayerGroup() {
        return this.layerGroup;
    }

    public FrcSupportInfo getFrcSupportInfo(ViewMode viewMode) {
        return this.frcSupportInfo[viewMode.ordinal()];
    }

    public boolean checkFrcAvailable(ViewMode viewMode, FpsType fpsType, SpeedType speedType) {
        return this.frcSupportInfo[viewMode.ordinal()].checkFrcAvailable(fpsType, speedType);
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public void update() {
        this.nativeInterfaceWrapper.update(this);
    }

    @Override // com.samsung.vekit.Common.State.VEStateInterface
    public synchronized VEKitState getState() {
        return this.state;
    }

    @Override // com.samsung.vekit.Common.State.VEStateInterface
    public synchronized void setState(VEKitState state) {
        this.state = state;
    }

    public NativeInterfaceWrapper getNativeInterface() {
        return this.nativeInterfaceWrapper;
    }

    public FrameworkType getFrameworkType() {
        return this.frameworkType;
    }

    public void setFrameworkType(FrameworkType frameworkType) {
        this.frameworkType = frameworkType;
    }
}
