package android.view.contentprotection;

import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.ViewNode;

public final class ContentProtectionUtils {
    public static String getEventTextLower(ContentCaptureEvent event) {
        CharSequence text = event.getText();
        if (text == null) {
            return null;
        }
        return text.toString().toLowerCase();
    }

    public static String getViewNodeTextLower(ViewNode viewNode) {
        CharSequence text;
        if (viewNode == null || (text = viewNode.getText()) == null) {
            return null;
        }
        return text.toString().toLowerCase();
    }

    public static String getHintTextLower(ViewNode viewNode) {
        String text;
        if (viewNode == null || (text = viewNode.getHint()) == null) {
            return null;
        }
        return text.toLowerCase();
    }
}
