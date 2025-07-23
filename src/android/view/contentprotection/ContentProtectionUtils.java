package android.view.contentprotection;

import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.ViewNode;

/* loaded from: classes4.dex */
public final class ContentProtectionUtils {
    public static String getEventTextLower(ContentCaptureEvent contentCaptureEvent) {
        CharSequence text = contentCaptureEvent.getText();
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
        String hint;
        if (viewNode == null || (hint = viewNode.getHint()) == null) {
            return null;
        }
        return hint.toLowerCase();
    }
}
