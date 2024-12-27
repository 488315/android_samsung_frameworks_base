package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.SoundModel;

import java.util.Objects;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ValidationUtil {
    public static void validateModel(SoundModel soundModel, int i) {
        Objects.requireNonNull(soundModel);
        if (soundModel.type != i) {
            throw new IllegalArgumentException("Invalid type");
        }
        String str = soundModel.uuid;
        Objects.requireNonNull(str);
        Pattern pattern = UuidUtil.PATTERN;
        if (!pattern.matcher(str).matches()) {
            throw new IllegalArgumentException("Illegal format for UUID: ".concat(str));
        }
        String str2 = soundModel.vendorUuid;
        Objects.requireNonNull(str2);
        if (!pattern.matcher(str2).matches()) {
            throw new IllegalArgumentException("Illegal format for UUID: ".concat(str2));
        }
        if (soundModel.dataSize > 0) {
            Objects.requireNonNull(soundModel.data);
        }
    }
}
