package com.airbnb.lottie.model.layer;

import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Layer {
    public final BlurEffect blurEffect;
    public final LottieComposition composition;
    public final DropShadowEffect dropShadowEffect;
    public final boolean hidden;
    public final List inOutKeyframes;
    public final long layerId;
    public final String layerName;
    public final LayerType layerType;
    public final List masks;
    public final MatteType matteType;
    public final long parentId;
    public final float preCompHeight;
    public final float preCompWidth;
    public final String refId;
    public final List shapes;
    public final int solidColor;
    public final int solidHeight;
    public final int solidWidth;
    public final float startFrame;
    public final AnimatableTextFrame text;
    public final AnimatableTextProperties textProperties;
    public final AnimatableFloatValue timeRemapping;
    public final float timeStretch;
    public final AnimatableTransform transform;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum LayerType {
        PRE_COMP,
        SOLID,
        IMAGE,
        NULL,
        SHAPE,
        TEXT,
        UNKNOWN
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum MatteType {
        NONE,
        ADD,
        INVERT,
        LUMA,
        LUMA_INVERTED,
        /* JADX INFO: Fake field, exist only in values array */
        UNKNOWN
    }

    public Layer(List<ContentModel> list, LottieComposition lottieComposition, String str, long j, LayerType layerType, long j2, String str2, List<Mask> list2, AnimatableTransform animatableTransform, int i, int i2, int i3, float f, float f2, float f3, float f4, AnimatableTextFrame animatableTextFrame, AnimatableTextProperties animatableTextProperties, List<Keyframe> list3, MatteType matteType, AnimatableFloatValue animatableFloatValue, boolean z, BlurEffect blurEffect, DropShadowEffect dropShadowEffect) {
        this.shapes = list;
        this.composition = lottieComposition;
        this.layerName = str;
        this.layerId = j;
        this.layerType = layerType;
        this.parentId = j2;
        this.refId = str2;
        this.masks = list2;
        this.transform = animatableTransform;
        this.solidWidth = i;
        this.solidHeight = i2;
        this.solidColor = i3;
        this.timeStretch = f;
        this.startFrame = f2;
        this.preCompWidth = f3;
        this.preCompHeight = f4;
        this.text = animatableTextFrame;
        this.textProperties = animatableTextProperties;
        this.inOutKeyframes = list3;
        this.matteType = matteType;
        this.timeRemapping = animatableFloatValue;
        this.hidden = z;
        this.blurEffect = blurEffect;
        this.dropShadowEffect = dropShadowEffect;
    }

    public final String toString(String str) {
        int i;
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
        m18m.append(this.layerName);
        m18m.append("\n");
        LottieComposition lottieComposition = this.composition;
        Layer layer = (Layer) lottieComposition.layerMap.get(this.parentId);
        if (layer != null) {
            m18m.append("\t\tParents: ");
            m18m.append(layer.layerName);
            for (Layer layer2 = (Layer) lottieComposition.layerMap.get(layer.parentId); layer2 != null; layer2 = (Layer) lottieComposition.layerMap.get(layer2.parentId)) {
                m18m.append("->");
                m18m.append(layer2.layerName);
            }
            m18m.append(str);
            m18m.append("\n");
        }
        List list = this.masks;
        if (!list.isEmpty()) {
            m18m.append(str);
            m18m.append("\tMasks: ");
            m18m.append(list.size());
            m18m.append("\n");
        }
        int i2 = this.solidWidth;
        if (i2 != 0 && (i = this.solidHeight) != 0) {
            m18m.append(str);
            m18m.append("\tBackground: ");
            m18m.append(String.format(Locale.US, "%dx%d %X\n", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(this.solidColor)));
        }
        List list2 = this.shapes;
        if (!list2.isEmpty()) {
            m18m.append(str);
            m18m.append("\tShapes:\n");
            for (Object obj : list2) {
                m18m.append(str);
                m18m.append("\t\t");
                m18m.append(obj);
                m18m.append("\n");
            }
        }
        return m18m.toString();
    }

    public final String toString() {
        return toString("");
    }
}
