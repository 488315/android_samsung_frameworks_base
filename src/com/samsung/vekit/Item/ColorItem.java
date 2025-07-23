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

    public ColorItem(VEContext vEContext, int i, String str) {
        super(vEContext, ItemType.COLOR, i, str);
        this.opacity = 1.0f;
    }

    @Override // com.samsung.vekit.Item.Item
    public ColorItem setParent(Layer layer) {
        return (ColorItem) super.setParent(layer);
    }

    @Override // com.samsung.vekit.Item.Item
    public ColorItem setPadding(long j) {
        return (ColorItem) super.setPadding(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public ColorItem setDuration(long j) {
        return (ColorItem) super.setDuration(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public float getOpacity() {
        return this.opacity;
    }

    @Override // com.samsung.vekit.Item.Item, com.samsung.vekit.Common.Object.Element
    public ColorItem setOpacity(float f) {
        this.opacity = f;
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

    public ColorItem setColor(Color color) {
        this.color = color;
        return this;
    }
}
