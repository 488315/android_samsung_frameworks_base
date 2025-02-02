package com.airbnb.lottie.parser;

import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes.dex */
final class ShapeTrimPathParser {
    private static JsonReader.Options NAMES = JsonReader.Options.m8of("s", "e", "o", "nm", "m", "hd");

    static ShapeTrimPath parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ShapeTrimPath.Type type;
        String str = null;
        ShapeTrimPath.Type type2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(NAMES);
            if (selectName == 0) {
                animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            } else if (selectName == 1) {
                animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            } else if (selectName == 2) {
                animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
            } else if (selectName == 3) {
                str = jsonReader.nextString();
            } else if (selectName == 4) {
                int nextInt = jsonReader.nextInt();
                if (nextInt == 1) {
                    type = ShapeTrimPath.Type.SIMULTANEOUSLY;
                } else {
                    if (nextInt != 2) {
                        throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unknown trim path type ", nextInt));
                    }
                    type = ShapeTrimPath.Type.INDIVIDUALLY;
                }
                type2 = type;
            } else if (selectName != 5) {
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new ShapeTrimPath(str, type2, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, z);
    }
}
