package com.samsung.vekit.Item;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;

/* loaded from: classes6.dex */
public class CaptionItem extends Item {
  public CaptionItem(VEContext context, int id, String name) {
    super(context, ItemType.CAPTION, id, name);
  }

  @Override // com.samsung.vekit.Item.Item
  public CaptionItem setParent(Layer parent) {
    return (CaptionItem) super.setParent(parent);
  }

  @Override // com.samsung.vekit.Item.Item
  public CaptionItem setContent(Content content) {
    return (CaptionItem) super.setContent(content);
  }

  @Override // com.samsung.vekit.Item.Item
  public CaptionItem setPadding(long padding) {
    return (CaptionItem) super.setPadding(padding);
  }

  @Override // com.samsung.vekit.Item.Item
  public CaptionItem setDuration(long duration) {
    return (CaptionItem) super.setDuration(duration);
  }
}
