package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Object.Filter;
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
public class ImageItem extends Item {
  private Filter filter;
  private float filterIntensity;
  private float opacity;
  protected HashMap<Integer, Tone> toneMap;

  public ImageItem(VEContext context, int id, String name) {
    super(context, ItemType.IMAGE, id, name);
    this.filterIntensity = 100.0f;
    this.opacity = 1.0f;
    initializeTone();
  }

  @Override // com.samsung.vekit.Item.Item
  public void checkValidContent(Content content) throws Exception {
    if (content.getContentType() != ContentType.IMAGE
        && content.getContentType() != ContentType.ANIMATED_IMAGE) {
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
      Log.m97e(this.TAG, "setContent: ", e);
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
  public float getOpacity() {
    return this.opacity;
  }

  @Override // com.samsung.vekit.Item.Item
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

  public Item setToneIntensity(ToneType type, int intensity) {
    ((Tone) Objects.requireNonNull(this.toneMap.get(Integer.valueOf(type.ordinal()))))
        .setIntensity(intensity);
    return this;
  }

  public int getToneIntensity(ToneType type) {
    return ((Tone) Objects.requireNonNull(this.toneMap.get(Integer.valueOf(type.ordinal()))))
        .getIntensity();
  }

  private void initializeTone() {
    this.toneMap = new HashMap<>();
    ToneType[] values = ToneType.values();
    for (ToneType type : values) {
      this.toneMap.put(Integer.valueOf(type.ordinal()), new Tone(type, 0));
    }
  }
}
