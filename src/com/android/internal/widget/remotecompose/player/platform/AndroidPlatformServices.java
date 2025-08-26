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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065 A[LOOP:2: B:21:0x0063->B:22:0x0065, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0076 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x001a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private float[] androidPathToFloatArray(Path path) {
        int length;
        int i;
        int i2;
        PathIterator pathIterator = path.getPathIterator();
        int i3 = 0;
        while (pathIterator.hasNext()) {
            pathIterator.next();
            i3++;
        }
        path.getPathIterator();
        float[] fArr = new float[i3 * 10];
        int i4 = 0;
        while (pathIterator.hasNext()) {
            PathIterator.Segment next = pathIterator.next();
            switch (next.getVerb()) {
                case 0:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.MOVE_NAN;
                    break;
                case 1:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.LINE_NAN;
                    break;
                case 2:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.QUADRATIC_NAN;
                    break;
                case 3:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.CONIC_NAN;
                    break;
                case 4:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.CUBIC_NAN;
                    break;
                case 5:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.CLOSE_NAN;
                    break;
                case 6:
                    i2 = i4 + 1;
                    fArr[i4] = PathData.DONE_NAN;
                    break;
                default:
                    float[] points = next.getPoints();
                    length = points.length;
                    i = 0;
                    while (i < length) {
                        fArr[i4] = points[i];
                        i++;
                        i4++;
                    }
                    if (next.getVerb() != 3) {
                        fArr[i4] = next.getConicWeight();
                        i4++;
                    }
            }
            i4 = i2;
            float[] points2 = next.getPoints();
            length = points2.length;
            i = 0;
            while (i < length) {
            }
            if (next.getVerb() != 3) {
            }
        }
        return Arrays.copyOf(fArr, i4);
    }
}
