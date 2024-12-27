package com.samsung.vekit.Item;

import android.util.Log;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Layer.Layer;

public class EmptyItem extends Item {
    public EmptyItem(VEContext context, int id, String name) {
        super(context, ItemType.EMPTY, id, name);
    }

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        throw new Exception("isInvalidElement : EmptyItem cannot have a content.");
    }

    @Override // com.samsung.vekit.Item.Item
    public EmptyItem setParent(Layer parent) {
        return (EmptyItem) super.setParent(parent);
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
    public EmptyItem setPadding(long padding) {
        return (EmptyItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public EmptyItem setDuration(long duration) {
        return (EmptyItem) super.setDuration(duration);
    }
}
