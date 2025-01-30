package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Object.Region;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;

/* loaded from: classes6.dex */
public class AudioItem extends Item {
  private long endContentTime;
  private long fadeInDuration;
  private long fadeOutDuration;
  private long startContentTime;
  private int volume;

  public AudioItem(VEContext context, int id, String name) {
    super(context, ItemType.AUDIO, id, name);
    this.volume = 100;
    this.fadeInDuration = 0L;
    this.fadeOutDuration = 0L;
  }

  @Override // com.samsung.vekit.Item.Item
  public void checkValidContent(Content content) throws Exception {
    if (content.getContentType() != ContentType.AUDIO) {
      throw new Exception("isInvalidElement : please set audio(content).");
    }
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem setParent(Layer parent) {
    return (AudioItem) super.setParent(parent);
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem setContent(Content content) {
    try {
      checkValidContent(content);
      return (AudioItem) super.setContent(content);
    } catch (Exception e) {
      Log.m97e(this.TAG, "setContent: ", e);
      return this;
    }
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem setPadding(long padding) {
    return (AudioItem) super.setPadding(padding);
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem setDuration(long duration) {
    return (AudioItem) super.setDuration(duration);
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem setSpeed(float speed) {
    return (AudioItem) super.setSpeed(speed);
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem addRegion(Region region) {
    return (AudioItem) super.addRegion(region);
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem removeRegion(Region region) {
    return (AudioItem) super.removeRegion(region);
  }

  @Override // com.samsung.vekit.Item.Item
  public AudioItem clearRegions() {
    return (AudioItem) super.clearRegions();
  }

  public long getStartContentTime() {
    return this.startContentTime;
  }

  public AudioItem setStartContentTime(long startContentTime) {
    this.startContentTime = startContentTime;
    return this;
  }

  public long getEndContentTime() {
    return this.endContentTime;
  }

  public AudioItem setEndContentTime(long endContentTime) {
    this.endContentTime = endContentTime;
    return this;
  }

  public int getVolume() {
    return this.volume;
  }

  public AudioItem setVolume(int volume) {
    this.volume = volume;
    return this;
  }

  public AudioItem setFadeInDuration(long duration) {
    this.fadeInDuration = duration;
    return this;
  }

  public long getFadeInDuration() {
    return this.fadeInDuration;
  }

  public AudioItem setFadeOutDuration(long duration) {
    this.fadeOutDuration = duration;
    return this;
  }

  public long getFadeOutDuration() {
    return this.fadeOutDuration;
  }
}
