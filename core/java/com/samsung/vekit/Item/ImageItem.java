package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.FilterOption;
import com.samsung.vekit.Common.Object.ToneInfo;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.MeshType;
import com.samsung.vekit.Common.Type.ToneType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;

/* loaded from: classes6.dex */
public class ImageItem extends Item {
    private Filter filter;
    private float filterIntensity;
    private FilterOption filterOption;
    private float opacity;
    private ToneInfo toneInfo;

    public ImageItem(VEContext context, int id, String name) {
        super(context, ItemType.IMAGE, id, name);
        this.filterIntensity = 100.0f;
        this.opacity = 1.0f;
        this.toneInfo = new ToneInfo();
        this.filterOption = new FilterOption();
    }

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.IMAGE && content.getContentType() != ContentType.ANIMATED_IMAGE) {
            throw new Exception("isInvalidElement : please set image or animated_image(content).");
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public ImageItem setParent(Layer parent) {
        return (ImageItem) super.setParent(parent);
    }

    @Override // com.samsung.vekit.Item.Item
    public ImageItem setContent(Content content) {
        try {
            checkValidContent(content);
            return (ImageItem) super.setContent(content);
        } catch (Exception e) {
            Log.e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public ImageItem setPadding(long padding) {
        return (ImageItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public ImageItem setDuration(long duration) {
        return (ImageItem) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Item.Item
    public Filter getFilter() {
        return this.filter;
    }

    @Override // com.samsung.vekit.Item.Item
    public ImageItem setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public float getFilterIntensity() {
        return this.filterIntensity;
    }

    @Override // com.samsung.vekit.Item.Item
    public ImageItem setFilterIntensity(float filterIntensity) {
        this.filterIntensity = filterIntensity;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public Item setToneIntensity(ToneType type, int intensity) {
        this.toneInfo.setTone(type, intensity);
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public int getToneIntensity(ToneType type) {
        return (int) this.toneInfo.getTone(type);
    }

    @Override // com.samsung.vekit.Item.Item
    public float getOpacity() {
        return this.opacity;
    }

    @Override // com.samsung.vekit.Item.Item, com.samsung.vekit.Common.Object.Element
    public ImageItem setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    public ImageItem setMeshType(MeshType meshType) {
        this.meshType = meshType;
        return this;
    }

    public MeshType getMeshType() {
        return this.meshType;
    }

    private void initializeTone() {
        this.toneInfo = new ToneInfo();
    }

    public void setFilterOption(FilterOption filterOption) {
        this.filterOption = filterOption;
    }

    public FilterOption getFilterOption() {
        return this.filterOption;
    }
}
