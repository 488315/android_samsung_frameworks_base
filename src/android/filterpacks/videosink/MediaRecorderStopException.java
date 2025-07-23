package android.filterpacks.videosink;

/* loaded from: classes.dex */
public class MediaRecorderStopException extends RuntimeException {
    private static final String TAG = "MediaRecorderStopException";

    public MediaRecorderStopException(String str) {
        super(str);
    }

    public MediaRecorderStopException() {
    }

    public MediaRecorderStopException(String str, Throwable th) {
        super(str, th);
    }

    public MediaRecorderStopException(Throwable th) {
        super(th);
    }
}
