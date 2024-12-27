package com.android.systemui;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.PathParser;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsKt;

public final class CameraProtectionLoaderImpl implements CameraProtectionLoader {
    public final Context context;

    public CameraProtectionLoaderImpl(Context context) {
        this.context = context;
    }

    public final CameraProtectionInfo loadCameraProtectionInfo(int i, int i2, int i3, int i4) {
        String string = this.context.getString(i);
        if (string.length() == 0) {
            return null;
        }
        String string2 = this.context.getString(i2);
        try {
            Path createPathFromPathData = PathParser.createPathFromPathData(StringsKt__StringsKt.trim(this.context.getString(i3)).toString());
            Intrinsics.checkNotNull(createPathFromPathData);
            RectF rectF = new RectF();
            createPathFromPathData.computeBounds(rectF);
            return new CameraProtectionInfo(string, string2, createPathFromPathData, new Rect(MathKt__MathJVMKt.roundToInt(rectF.left), MathKt__MathJVMKt.roundToInt(rectF.top), MathKt__MathJVMKt.roundToInt(rectF.right), MathKt__MathJVMKt.roundToInt(rectF.bottom)), this.context.getString(i4));
        } catch (Throwable th) {
            throw new IllegalArgumentException("Invalid protection path", th);
        }
    }

    public final List loadCameraProtectionInfoList() {
        ArrayList arrayList = new ArrayList();
        CameraProtectionInfo loadCameraProtectionInfo = loadCameraProtectionInfo(R.string.config_protectedCameraId, R.string.config_protectedPhysicalCameraId, R.string.config_frontBuiltInDisplayCutoutProtection, R.string.config_protectedScreenUniqueId);
        if (loadCameraProtectionInfo != null) {
            arrayList.add(loadCameraProtectionInfo);
        }
        CameraProtectionInfo loadCameraProtectionInfo2 = loadCameraProtectionInfo(R.string.config_protectedInnerCameraId, R.string.config_protectedInnerPhysicalCameraId, R.string.config_innerBuiltInDisplayCutoutProtection, R.string.config_protectedInnerScreenUniqueId);
        if (loadCameraProtectionInfo2 != null) {
            arrayList.add(loadCameraProtectionInfo2);
        }
        return arrayList;
    }
}
