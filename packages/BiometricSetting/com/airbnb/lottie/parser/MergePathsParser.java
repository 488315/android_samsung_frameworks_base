package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.content.MergePaths;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes.dex */
final class MergePathsParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.m8of("nm", "mm", "hd");

    static MergePaths parse(JsonReader jsonReader) throws IOException {
        String str = null;
        boolean z = false;
        MergePaths.MergePathsMode mergePathsMode = null;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(NAMES);
            if (selectName == 0) {
                str = jsonReader.nextString();
            } else if (selectName == 1) {
                int nextInt = jsonReader.nextInt();
                MergePaths.MergePathsMode mergePathsMode2 = MergePaths.MergePathsMode.MERGE;
                if (nextInt != 1) {
                    if (nextInt == 2) {
                        mergePathsMode = MergePaths.MergePathsMode.ADD;
                    } else if (nextInt == 3) {
                        mergePathsMode = MergePaths.MergePathsMode.SUBTRACT;
                    } else if (nextInt == 4) {
                        mergePathsMode = MergePaths.MergePathsMode.INTERSECT;
                    } else if (nextInt == 5) {
                        mergePathsMode = MergePaths.MergePathsMode.EXCLUDE_INTERSECTIONS;
                    }
                }
                mergePathsMode = mergePathsMode2;
            } else if (selectName != 2) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new MergePaths(str, mergePathsMode, z);
    }
}
