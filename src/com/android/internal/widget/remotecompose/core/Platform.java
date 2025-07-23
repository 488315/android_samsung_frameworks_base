package com.android.internal.widget.remotecompose.core;

/* loaded from: classes6.dex */
public interface Platform {
    public static final Platform None = new Platform() { // from class: com.android.internal.widget.remotecompose.core.Platform.1
        @Override // com.android.internal.widget.remotecompose.core.Platform
        public void log(LogCategory logCategory, String str) {
        }

        @Override // com.android.internal.widget.remotecompose.core.Platform
        public byte[] imageToByteArray(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.android.internal.widget.remotecompose.core.Platform
        public int getImageWidth(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.android.internal.widget.remotecompose.core.Platform
        public int getImageHeight(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.android.internal.widget.remotecompose.core.Platform
        public boolean isAlpha8Image(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.android.internal.widget.remotecompose.core.Platform
        public float[] pathToFloatArray(Object obj) {
            throw new UnsupportedOperationException();
        }
    };

    public interface ComputedTextLayout {
        float getHeight();

        float getWidth();
    }

    public enum LogCategory {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        TODO
    }

    int getImageHeight(Object obj);

    int getImageWidth(Object obj);

    byte[] imageToByteArray(Object obj);

    boolean isAlpha8Image(Object obj);

    void log(LogCategory logCategory, String str);

    float[] pathToFloatArray(Object obj);
}
