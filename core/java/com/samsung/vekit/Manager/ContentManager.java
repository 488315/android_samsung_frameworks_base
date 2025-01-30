package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.AnimatedImage;
import com.samsung.vekit.Content.Audio;
import com.samsung.vekit.Content.Caption;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.Doodle;
import com.samsung.vekit.Content.FragmentAudio;
import com.samsung.vekit.Content.Image;
import com.samsung.vekit.Content.Video;

/* loaded from: classes6.dex */
public class ContentManager extends Manager<Content> {
  public ContentManager(VEContext context) {
    super(context, ManagerType.CONTENT);
    this.TAG = getClass().getSimpleName();
  }

  public Content create(ContentType type, String name) {
    Content content;
    try {
      int uniqueId = generateUniqueId();
      switch (C53831.$SwitchMap$com$samsung$vekit$Common$Type$ContentType[type.ordinal()]) {
        case 1:
          content = new Audio(this.context, uniqueId, name);
          break;
        case 2:
          content = new Doodle(this.context, uniqueId, name);
          break;
        case 3:
          content = new Image(this.context, uniqueId, name);
          break;
        case 4:
          content = new AnimatedImage(this.context, uniqueId, name);
          break;
        case 5:
          content = new Video(this.context, uniqueId, name);
          break;
        case 6:
          content = new Caption(this.context, uniqueId, name);
          break;
        case 7:
          content = new FragmentAudio(this.context, uniqueId, name);
          break;
        default:
          return null;
      }
      add(content);
      return content;
    } catch (Exception e) {
      Log.m97e(this.TAG, "create: ", e);
      return null;
    }
  }

  /* renamed from: com.samsung.vekit.Manager.ContentManager$1 */
  static /* synthetic */ class C53831 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ContentType;

    static {
      int[] iArr = new int[ContentType.values().length];
      $SwitchMap$com$samsung$vekit$Common$Type$ContentType = iArr;
      try {
        iArr[ContentType.AUDIO.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.DOODLE.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.IMAGE.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.ANIMATED_IMAGE.ordinal()] =
            4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.VIDEO.ordinal()] = 5;
      } catch (NoSuchFieldError e5) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.CAPTION.ordinal()] = 6;
      } catch (NoSuchFieldError e6) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.FRAGMENT_AUDIO.ordinal()] =
            7;
      } catch (NoSuchFieldError e7) {
      }
    }
  }
}
