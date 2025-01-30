package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.FragmentAudio;
import com.samsung.vekit.Layer.Layer;
import java.util.List;

/* loaded from: classes6.dex */
public class FragmentAudioItem extends Item {
  public static final int TARGET_DURATION_GAP = 2000;
  private int bodyFragmentCount;
  private boolean enableAnimation;
  private boolean enableAutoDuration;
  private boolean enableOutro;
  private int volume;

  @Override // com.samsung.vekit.Item.Item
  public void checkValidContent(Content content) throws Exception {
    if (content.getContentType() != ContentType.FRAGMENT_AUDIO) {
      throw new Exception("isInvalidElement : please set fragment_audio(content).");
    }
  }

  public FragmentAudioItem(VEContext context, int id, String name) {
    super(context, ItemType.FRAGMENT_AUDIO, id, name);
    this.volume = 100;
    this.enableOutro = true;
    this.enableAnimation = false;
    this.enableAutoDuration = true;
    this.bodyFragmentCount = 0;
  }

  @Override // com.samsung.vekit.Item.Item
  public FragmentAudioItem setParent(Layer parent) {
    return (FragmentAudioItem) super.setParent(parent);
  }

  @Override // com.samsung.vekit.Item.Item
  public FragmentAudioItem setContent(Content content) {
    try {
      checkValidContent(content);
      super.setContent(content);
      if (this.duration != 0) {
        updateOptions();
      }
      return this;
    } catch (Exception e) {
      Log.m97e(this.TAG, "setContent: ", e);
      return this;
    }
  }

  @Override // com.samsung.vekit.Item.Item
  public FragmentAudioItem setPadding(long padding) {
    return (FragmentAudioItem) super.setPadding(padding);
  }

  @Override // com.samsung.vekit.Item.Item
  public FragmentAudioItem setDuration(long duration) {
    super.setDuration(duration);
    updateOptions();
    return this;
  }

  public FragmentAudioItem setEnableOutro(boolean enableOutro) {
    this.enableOutro = enableOutro;
    return this;
  }

  public boolean isEnableOutro() {
    return this.enableOutro;
  }

  public FragmentAudioItem setEnableAnimation(boolean enableAnimation) {
    this.enableAnimation = enableAnimation;
    return this;
  }

  public boolean getEnableAnimation() {
    return this.enableAnimation;
  }

  public FragmentAudioItem setEnableAutoDuration(boolean enableAutoDuration) {
    this.enableAutoDuration = enableAutoDuration;
    return this;
  }

  public boolean isEnableAutoDuration() {
    return this.enableAutoDuration;
  }

  public int getVolume() {
    return this.volume;
  }

  public FragmentAudioItem setVolume(int volume) {
    this.volume = volume;
    return this;
  }

  private void updateOptions() {
    if (this.content == null) {
      Log.m96e(this.TAG, "Content is null.");
      return;
    }
    long contentDuration = calculateContentDuration();
    long durationGap = this.duration - contentDuration;
    if (contentDuration <= 0) {
      Log.m96e(this.TAG, "contentDuration is 0 or negative");
      return;
    }
    if (Math.abs(durationGap) <= 2000) {
      this.enableOutro = true;
      this.enableAnimation = durationGap < 0;
    } else {
      appendBodyCount(contentDuration);
      this.enableOutro = false;
      this.enableAnimation = true;
    }
    Log.m94d(
        this.TAG,
        "updateOptions() => duration : "
            + this.duration
            + " enableOutro : "
            + this.enableOutro
            + " enableAnimation : "
            + this.enableAnimation);
  }

  private void appendBodyCount(long contentDuration) {
    FragmentAudio fragmentAudio = (FragmentAudio) this.content;
    long currentDuration = contentDuration - fragmentAudio.getOutroDuration();
    long remainedDuration = this.duration - currentDuration;
    if (remainedDuration < 0) {
      return;
    }
    List<Long> bodyDurationList = fragmentAudio.getBodyDurationList();
    int size = bodyDurationList.size();
    while (remainedDuration > 0) {
      int i = this.bodyFragmentCount + 1;
      this.bodyFragmentCount = i;
      remainedDuration -= bodyDurationList.get(i % size).longValue();
    }
  }

  private long calculateContentDuration() {
    FragmentAudio fragmentAudio = (FragmentAudio) this.content;
    long contentDuration = fragmentAudio.getIntroDuration() + fragmentAudio.getOutroDuration();
    List<Long> bodyDurationList = fragmentAudio.getBodyDurationList();
    int size = bodyDurationList.size();
    if (size == 0) {
      return contentDuration;
    }
    int bodyIndex = 0;
    long minimumGap = Math.abs(this.duration - contentDuration);
    this.bodyFragmentCount = 0;
    while (Math.abs(this.duration - (bodyDurationList.get(bodyIndex).longValue() + contentDuration))
        < minimumGap) {
      contentDuration += bodyDurationList.get(bodyIndex).longValue();
      minimumGap = Math.abs(this.duration - contentDuration);
      bodyIndex = (bodyIndex + 1) % size;
      this.bodyFragmentCount++;
    }
    Log.m94d(this.TAG, "contentDuration is " + contentDuration);
    return contentDuration;
  }
}
