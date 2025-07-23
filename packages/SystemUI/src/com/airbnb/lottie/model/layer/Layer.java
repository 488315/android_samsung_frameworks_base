package com.airbnb.lottie.model.layer;

import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Layer {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum LayerType {
        PRE_COMP,
        SOLID,
        IMAGE,
        NULL,
        SHAPE,
        TEXT,
        UNKNOWN
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str);
        m.append(this.layerName);
        m.append("\n");
        LottieComposition lottieComposition = this.composition;
        Layer layer = (Layer) lottieComposition.layerMap.get(this.parentId);
        if (layer != null) {
            m.append("\t\tParents: ");
            m.append(layer.layerName);
            for (Layer layer2 = (Layer) lottieComposition.layerMap.get(layer.parentId); layer2 != null; layer2 = (Layer) lottieComposition.layerMap.get(layer2.parentId)) {
                m.append("->");
                m.append(layer2.layerName);
            }
            m.append(str);
            m.append("\n");
        }
        if (!this.masks.isEmpty()) {
            m.append(str);
            m.append("\tMasks: ");
            m.append(this.masks.size());
            m.append("\n");
        }
        int i2 = this.solidWidth;
        if (i2 != 0 && (i = this.solidHeight) != 0) {
            m.append(str);
            m.append("\tBackground: ");
            m.append(String.format(Locale.US, "%dx%d %X\n", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(this.solidColor)));
        }
        if (!this.shapes.isEmpty()) {
            m.append(str);
            m.append("\tShapes:\n");
            for (Object obj : this.shapes) {
                m.append(str);
                m.append("\t\t");
                m.append(obj);
                m.append("\n");
            }
        }
        return m.toString();
    }

    public final String toString() {
        return toString("");
    }
}
