package android.media;

public class MediaDrmResetException extends IllegalStateException implements MediaDrmThrowable {
    public MediaDrmResetException(String detailMessage) {
        super(detailMessage);
    }
}
