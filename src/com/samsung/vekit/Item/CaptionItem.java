package com.samsung.vekit.Item;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;

/* loaded from: classes6.dex */
public class CaptionItem extends Item {
    public CaptionItem(VEContext vEContext, int i, String str) {
        super(vEContext, ItemType.CAPTION, i, str);
    }

    @Override // com.samsung.vekit.Item.Item
    public CaptionItem setParent(Layer layer) {
        return (CaptionItem) super.setParent(layer);
    }

    @Override // com.samsung.vekit.Item.Item
    public CaptionItem setContent(Content content) {
        return (CaptionItem) super.setContent(content);
    }

    @Override // com.samsung.vekit.Item.Item
    public CaptionItem setPadding(long j) {
        return (CaptionItem) super.setPadding(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public CaptionItem setDuration(long j) {
        return (CaptionItem) super.setDuration(j);
    }
}
