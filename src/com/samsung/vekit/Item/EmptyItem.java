package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;

/* loaded from: classes6.dex */
public class EmptyItem extends Item {
    public EmptyItem(VEContext vEContext, int i, String str) {
        super(vEContext, ItemType.EMPTY, i, str);
    }

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        throw new Exception("isInvalidElement : EmptyItem cannot have a content.");
    }

    @Override // com.samsung.vekit.Item.Item
    public EmptyItem setParent(Layer layer) {
        return (EmptyItem) super.setParent(layer);
    }

    @Override // com.samsung.vekit.Item.Item
    public EmptyItem setContent(Content content) {
        try {
            checkValidContent(content);
            return (EmptyItem) super.setContent(content);
        } catch (Exception e) {
            Log.e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public EmptyItem setPadding(long j) {
        return (EmptyItem) super.setPadding(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public EmptyItem setDuration(long j) {
        return (EmptyItem) super.setDuration(j);
    }
}
