package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathIterator;
import android.util.Log;
import com.android.internal.widget.remotecompose.core.Platform;
import com.android.internal.widget.remotecompose.core.operations.PathData;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class AndroidPlatformServices implements Platform {
    private static final String LOG_TAG = "RemoteCompose";

    @Override // com.android.internal.widget.remotecompose.core.Platform
    public byte[] imageToByteArray(Object obj) {
        if (!(obj instanceof Bitmap)) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((Bitmap) obj).compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform
    public int getImageWidth(Object obj) {
        if (obj instanceof Bitmap) {
            return ((Bitmap) obj).getWidth();
        }
        return 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform
    public int getImageHeight(Object obj) {
        if (obj instanceof Bitmap) {
            return ((Bitmap) obj).getHeight();
        }
        return 0;
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform
    public boolean isAlpha8Image(Object obj) {
        if (obj instanceof Bitmap) {
            return ((Bitmap) obj).getConfig().equals(Bitmap.Config.ALPHA_8);
        }
        return false;
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform
    public float[] pathToFloatArray(Object obj) {
        if (obj instanceof Path) {
            return androidPathToFloatArray((Path) obj);
        }
        return null;
    }

    /* renamed from: com.android.internal.widget.remotecompose.player.platform.AndroidPlatformServices$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$widget$remotecompose$core$Platform$LogCategory;

        static {
            int[] iArr = new int[Platform.LogCategory.values().length];
            $SwitchMap$com$android$internal$widget$remotecompose$core$Platform$LogCategory = iArr;
            try {
                iArr[Platform.LogCategory.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$internal$widget$remotecompose$core$Platform$LogCategory[Platform.LogCategory.INFO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$internal$widget$remotecompose$core$Platform$LogCategory[Platform.LogCategory.WARN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform
    public void log(Platform.LogCategory logCategory, String str) {
        int i = AnonymousClass1.$SwitchMap$com$android$internal$widget$remotecompose$core$Platform$LogCategory[logCategory.ordinal()];
        if (i == 1) {
            Log.d(LOG_TAG, str);
            return;
        }
        if (i == 2) {
            Log.i(LOG_TAG, str);
        } else if (i == 3) {
            Log.w(LOG_TAG, str);
        } else {
            Log.e(LOG_TAG, str);
        }
    }

    private float[] androidPathToFloatArray(Path path) {
        int i;
        PathIterator pathIterator = path.getPathIterator();
        int i2 = 0;
        while (pathIterator.hasNext()) {
            pathIterator.next();
            i2++;
        }
        path.getPathIterator();
        float[] fArr = new float[i2 * 10];
        int i3 = 0;
        while (pathIterator.hasNext()) {
            PathIterator.Segment next = pathIterator.next();
            switch (next.getVerb()) {
                case 0:
                    i = i3 + 1;
                    fArr[i3] = PathData.MOVE_NAN;
                    break;
                case 1:
                    i = i3 + 1;
                    fArr[i3] = PathData.LINE_NAN;
                    break;
                case 2:
                    i = i3 + 1;
                    fArr[i3] = PathData.QUADRATIC_NAN;
                    break;
                case 3:
                    i = i3 + 1;
                    fArr[i3] = PathData.CONIC_NAN;
                    break;
                case 4:
                    i = i3 + 1;
                    fArr[i3] = PathData.CUBIC_NAN;
                    break;
                case 5:
                    i = i3 + 1;
                    fArr[i3] = PathData.CLOSE_NAN;
                    break;
                case 6:
                    i = i3 + 1;
                    fArr[i3] = PathData.DONE_NAN;
                    break;
            }
            i3 = i;
            float[] points = next.getPoints();
            int length = points.length;
            int i4 = 0;
            while (i4 < length) {
                fArr[i3] = points[i4];
                i4++;
                i3++;
            }
            if (next.getVerb() == 3) {
                fArr[i3] = next.getConicWeight();
                i3++;
            }
        }
        return Arrays.copyOf(fArr, i3);
    }
}
