package com.samsung.vekit.Item;

import android.graphics.Color;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.MeshType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Layer.Layer;

/* loaded from: classes6.dex */
public class ColorItem extends Item {
    private Color color;
    private float opacity;

    public ColorItem(VEContext context, int id, String name) {
        super(context, ItemType.COLOR, id, name);
        this.opacity = 1.0f;
    }

    @Override // com.samsung.vekit.Item.Item
    public ColorItem setParent(Layer parent) {
        return (ColorItem) super.setParent(parent);
    }

    @Override // com.samsung.vekit.Item.Item
    public ColorItem setPadding(long padding) {
        return (ColorItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public ColorItem setDuration(long duration) {
        return (ColorItem) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Item.Item
    public float getOpacity() {
        return this.opacity;
    }

    @Override // com.samsung.vekit.Item.Item, com.samsung.vekit.Common.Object.Element
    public ColorItem setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    public ColorItem setMeshType(MeshType meshType) {
        this.meshType = meshType;
        return this;
    }

    public MeshType getMeshType() {
        return this.meshType;
    }

    public Color getColor() {
        return this.color;
    }

    public ColorItem setColor(Color c) {
        this.color = c;
        return this;
    }
}
