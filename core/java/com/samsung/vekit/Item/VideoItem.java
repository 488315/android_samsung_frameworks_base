package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.Region;
import com.samsung.vekit.Common.Object.Tone;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.MeshType;
import com.samsung.vekit.Common.Type.ToneType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes6.dex */
public class VideoItem extends Item {
    private boolean enableDeflicker;
    private boolean enableFRC;
    private long endContentTime;
    private long fadeInDuration;
    private long fadeOutDuration;
    private Filter filter;
    private float filterIntensity;
    private float opacity;
    private long startContentTime;
    protected HashMap<Integer, Tone> toneMap;
    private int volume;

    public VideoItem(VEContext context, int id, String name) {
        super(context, ItemType.VIDEO, id, name);
        this.volume = 100;
        this.filterIntensity = 100.0f;
        this.opacity = 1.0f;
        this.enableDeflicker = false;
        this.enableFRC = false;
        this.fadeInDuration = 0L;
        this.fadeOutDuration = 0L;
        initializeTone();
    }

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.VIDEO) {
            throw new Exception("isInvalidElement : please set video(content).");
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setParent(Layer parent) {
        return (VideoItem) super.setParent(parent);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setContent(Content content) {
        try {
            checkValidContent(content);
            return (VideoItem) super.setContent(content);
        } catch (Exception e) {
            Log.m97e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setPadding(long padding) {
        return (VideoItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setDuration(long duration) {
        return (VideoItem) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setSpeed(float speed) {
        return (VideoItem) super.setSpeed(speed);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem addRegion(Region region) {
        return (VideoItem) super.addRegion(region);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem removeRegion(Region region) {
        return (VideoItem) super.removeRegion(region);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem clearRegions() {
        return (VideoItem) super.clearRegions();
    }

    public long getStartContentTime() {
        return this.startContentTime;
    }

    public VideoItem setStartContentTime(long startContentTime) {
        this.startContentTime = startContentTime;
        return this;
    }

    public long getEndContentTime() {
        return this.endContentTime;
    }

    public VideoItem setEndContentTime(long endContentTime) {
        this.endContentTime = endContentTime;
        return this;
    }

    public int getVolume() {
        return this.volume;
    }

    public VideoItem setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public Filter getFilter() {
        return this.filter;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public float getFilterIntensity() {
        return this.filterIntensity;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setFilterIntensity(float filterIntensity) {
        this.filterIntensity = filterIntensity;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public float getOpacity() {
        return this.opacity;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    public VideoItem setToneIntensity(ToneType type, int intensity) {
        ((Tone) Objects.requireNonNull(this.toneMap.get(Integer.valueOf(type.ordinal())))).setIntensity(intensity);
        return this;
    }

    public int getToneIntensity(ToneType type) {
        return ((Tone) Objects.requireNonNull(this.toneMap.get(Integer.valueOf(type.ordinal())))).getIntensity();
    }

    private void initializeTone() {
        this.toneMap = new HashMap<>();
        ToneType[] values = ToneType.values();
        for (ToneType type : values) {
            this.toneMap.put(Integer.valueOf(type.ordinal()), new Tone(type, 0));
        }
    }

    public VideoItem setMeshType(MeshType meshType) {
        this.meshType = meshType;
        return this;
    }

    public MeshType getMeshType() {
        return this.meshType;
    }

    public boolean isEnableDeflicker() {
        return this.enableDeflicker;
    }

    public VideoItem setEnableDeflicker(boolean enableDeflicker) {
        this.enableDeflicker = enableDeflicker;
        return this;
    }

    public boolean isEnableFRC() {
        return this.enableFRC;
    }

    public VideoItem setEnableFRC(boolean enableFRC) {
        this.enableFRC = enableFRC;
        return this;
    }

    public VideoItem setFadeInDuration(long duration) {
        this.fadeInDuration = duration;
        return this;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public VideoItem setFadeOutDuration(long duration) {
        this.fadeOutDuration = duration;
        return this;
    }

    public long getFadeOutDuration() {
        return this.fadeOutDuration;
    }
}
