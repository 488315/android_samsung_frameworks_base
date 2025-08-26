package com.android.wm.shell.windowdecor;

import android.util.Slog;
import com.samsung.android.rune.CoreRune;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes3.dex */
public class MultiTaskingCaptionButtonLogger {
    public static final HashMap sLoggerMethods = new HashMap();
    public static boolean sInitialized = false;

    public class CaptionLoggerPairKey {
        public final int mBehavior;
        public final int mInteraction;
        public final boolean mIsDexMode;
        public final int mWindowingMode;

        public CaptionLoggerPairKey(MultiTaskingCaptionButtonLogger multiTaskingCaptionButtonLogger, int i, int i2, boolean z, int i3) {
            this.mBehavior = i;
            this.mInteraction = i2;
            this.mIsDexMode = z;
            this.mWindowingMode = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                CaptionLoggerPairKey captionLoggerPairKey = (CaptionLoggerPairKey) obj;
                if (this.mBehavior == captionLoggerPairKey.mBehavior && this.mInteraction == captionLoggerPairKey.mInteraction && this.mIsDexMode == captionLoggerPairKey.mIsDexMode && this.mWindowingMode == captionLoggerPairKey.mWindowingMode) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mBehavior), Integer.valueOf(this.mInteraction), Boolean.valueOf(this.mIsDexMode), Integer.valueOf(this.mWindowingMode));
        }
    }

    public MultiTaskingCaptionButtonLogger() throws SecurityException {
        MultiTaskingCaptionButtonLogger multiTaskingCaptionButtonLogger;
        if (sInitialized || !CoreRune.MW_SA_LOGGING) {
            return;
        }
        sInitialized = true;
        Method[] declaredMethods = MultiTaskingCaptionButtonLogger.class.getDeclaredMethods();
        int length = declaredMethods.length;
        int i = 0;
        while (i < length) {
            Method method = declaredMethods[i];
            if (method.isAnnotationPresent(CaptionLoggerInfo.class)) {
                CaptionLoggerInfo captionLoggerInfo = (CaptionLoggerInfo) method.getAnnotation(CaptionLoggerInfo.class);
                multiTaskingCaptionButtonLogger = this;
                sLoggerMethods.put(new CaptionLoggerPairKey(multiTaskingCaptionButtonLogger, captionLoggerInfo.behavior(), captionLoggerInfo.interaction(), captionLoggerInfo.isDexMode(), captionLoggerInfo.windowingMode()), method);
            } else {
                multiTaskingCaptionButtonLogger = this;
            }
            i++;
            this = multiTaskingCaptionButtonLogger;
        }
    }

    public void invokeLog(Method method, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (CoreRune.MW_SA_LOGGING && method != null) {
            try {
                Slog.d("MultiTaskingCaptionButtonLogger", "invoke logger=" + method.getName());
                method.invoke(this, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
