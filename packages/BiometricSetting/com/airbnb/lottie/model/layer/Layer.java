package com.airbnb.lottie.model.layer;

import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class Layer {
    private final LottieComposition composition;
    private final boolean hidden;
    private final List<Keyframe<Float>> inOutKeyframes;
    private final long layerId;
    private final String layerName;
    private final LayerType layerType;
    private final List<Mask> masks;
    private final MatteType matteType;
    private final long parentId;
    private final int preCompHeight;
    private final int preCompWidth;
    private final String refId;
    private final List<ContentModel> shapes;
    private final int solidColor;
    private final int solidHeight;
    private final int solidWidth;
    private final float startFrame;
    private final AnimatableTextFrame text;
    private final AnimatableTextProperties textProperties;
    private final AnimatableFloatValue timeRemapping;
    private final float timeStretch;
    private final AnimatableTransform transform;

    public enum LayerType {
        PRE_COMP,
        /* JADX INFO: Fake field, exist only in values array */
        EF1,
        IMAGE,
        /* JADX INFO: Fake field, exist only in values array */
        EF3,
        /* JADX INFO: Fake field, exist only in values array */
        EF4,
        /* JADX INFO: Fake field, exist only in values array */
        EF5,
        UNKNOWN;

        LayerType() {
        }
    }

    public enum MatteType {
        NONE,
        /* JADX INFO: Fake field, exist only in values array */
        EF1,
        INVERT,
        /* JADX INFO: Fake field, exist only in values array */
        EF33,
        /* JADX INFO: Fake field, exist only in values array */
        EF41,
        /* JADX INFO: Fake field, exist only in values array */
        EF49;

        MatteType() {
        }
    }

    public Layer(List<ContentModel> list, LottieComposition lottieComposition, String str, long j, LayerType layerType, long j2, String str2, List<Mask> list2, AnimatableTransform animatableTransform, int i, int i2, int i3, float f, float f2, int i4, int i5, AnimatableTextFrame animatableTextFrame, AnimatableTextProperties animatableTextProperties, List<Keyframe<Float>> list3, MatteType matteType, AnimatableFloatValue animatableFloatValue, boolean z) {
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
        this.preCompWidth = i4;
        this.preCompHeight = i5;
        this.text = animatableTextFrame;
        this.textProperties = animatableTextProperties;
        this.inOutKeyframes = list3;
        this.matteType = matteType;
        this.timeRemapping = animatableFloatValue;
        this.hidden = z;
    }

    final LottieComposition getComposition() {
        return this.composition;
    }

    public final long getId() {
        return this.layerId;
    }

    final List<Keyframe<Float>> getInOutKeyframes() {
        return this.inOutKeyframes;
    }

    public final LayerType getLayerType() {
        return this.layerType;
    }

    final List<Mask> getMasks() {
        return this.masks;
    }

    final MatteType getMatteType() {
        return this.matteType;
    }

    final String getName() {
        return this.layerName;
    }

    final long getParentId() {
        return this.parentId;
    }

    final int getPreCompHeight() {
        return this.preCompHeight;
    }

    final int getPreCompWidth() {
        return this.preCompWidth;
    }

    final String getRefId() {
        return this.refId;
    }

    final List<ContentModel> getShapes() {
        return this.shapes;
    }

    final int getSolidColor() {
        return this.solidColor;
    }

    final int getSolidHeight() {
        return this.solidHeight;
    }

    final int getSolidWidth() {
        return this.solidWidth;
    }

    final float getStartProgress() {
        return this.startFrame / this.composition.getDurationFrames();
    }

    final AnimatableTextFrame getText() {
        return this.text;
    }

    final AnimatableTextProperties getTextProperties() {
        return this.textProperties;
    }

    final AnimatableFloatValue getTimeRemapping() {
        return this.timeRemapping;
    }

    final float getTimeStretch() {
        return this.timeStretch;
    }

    final AnimatableTransform getTransform() {
        return this.transform;
    }

    public final boolean isHidden() {
        return this.hidden;
    }

    public final String toString(String str) {
        int i;
        StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str);
        m2m.append(this.layerName);
        m2m.append("\n");
        long j = this.parentId;
        LottieComposition lottieComposition = this.composition;
        Layer layerModelForId = lottieComposition.layerModelForId(j);
        if (layerModelForId != null) {
            m2m.append("\t\tParents: ");
            m2m.append(layerModelForId.layerName);
            for (Layer layerModelForId2 = lottieComposition.layerModelForId(layerModelForId.parentId); layerModelForId2 != null; layerModelForId2 = lottieComposition.layerModelForId(layerModelForId2.parentId)) {
                m2m.append("->");
                m2m.append(layerModelForId2.layerName);
            }
            m2m.append(str);
            m2m.append("\n");
        }
        List<Mask> list = this.masks;
        if (!list.isEmpty()) {
            m2m.append(str);
            m2m.append("\tMasks: ");
            m2m.append(list.size());
            m2m.append("\n");
        }
        int i2 = this.solidWidth;
        if (i2 != 0 && (i = this.solidHeight) != 0) {
            m2m.append(str);
            m2m.append("\tBackground: ");
            m2m.append(String.format(Locale.US, "%dx%d %X\n", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(this.solidColor)));
        }
        List<ContentModel> list2 = this.shapes;
        if (!list2.isEmpty()) {
            m2m.append(str);
            m2m.append("\tShapes:\n");
            for (ContentModel contentModel : list2) {
                m2m.append(str);
                m2m.append("\t\t");
                m2m.append(contentModel);
                m2m.append("\n");
            }
        }
        return m2m.toString();
    }

    public final String toString() {
        return toString("");
    }
}
