package android.widget;

import android.compat.Compatibility;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.ContentInfo;
import android.view.OnReceiveContentListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class TextViewOnReceiveContentListener implements OnReceiveContentListener {
    private static final long AUTOFILL_NON_TEXT_REQUIRES_ON_RECEIVE_CONTENT_LISTENER = 163400105;
    private static final String LOG_TAG = "ReceiveContent";
    private InputConnectionInfo mInputConnectionInfo;

    @Override // android.view.OnReceiveContentListener
    public ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
        CharSequence coerceToStyledText;
        if (Log.isLoggable(LOG_TAG, 3)) {
            Log.d(LOG_TAG, "onReceive: " + contentInfo);
        }
        int source = contentInfo.getSource();
        if (source == 2) {
            return contentInfo;
        }
        if (source == 4) {
            onReceiveForAutofill((TextView) view, contentInfo);
            return null;
        }
        ClipData clip = contentInfo.getClip();
        int flags = contentInfo.getFlags();
        Editable editable = (Editable) ((TextView) view).getText();
        Context context = view.getContext();
        boolean z = false;
        for (int i = 0; i < clip.getItemCount(); i++) {
            if ((flags & 1) != 0) {
                coerceToStyledText = clip.getItemAt(i).coerceToText(context);
                if (coerceToStyledText instanceof Spanned) {
                    coerceToStyledText = coerceToStyledText.toString();
                }
            } else {
                coerceToStyledText = clip.getItemAt(i).coerceToStyledText(context);
            }
            if (coerceToStyledText != null) {
                if (!z) {
                    replaceSelection(editable, coerceToStyledText);
                    z = true;
                } else {
                    editable.insert(Selection.getSelectionEnd(editable), ShaderAssembler.NEWLINE);
                    editable.insert(Selection.getSelectionEnd(editable), coerceToStyledText);
                }
            }
        }
        return null;
    }

    private static void replaceSelection(Editable editable, CharSequence charSequence) {
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        int max = Math.max(0, Math.min(selectionStart, selectionEnd));
        int max2 = Math.max(0, Math.max(selectionStart, selectionEnd));
        Selection.setSelection(editable, max2);
        editable.replace(max, max2, charSequence);
    }

    private void onReceiveForAutofill(TextView textView, ContentInfo contentInfo) {
        ClipData clip = contentInfo.getClip();
        if (isUsageOfImeCommitContentEnabled(textView) && (clip = handleNonTextViaImeCommitContent(clip)) == null) {
            if (Log.isLoggable(LOG_TAG, 2)) {
                Log.v(LOG_TAG, "onReceive: Handled via IME");
            }
        } else {
            textView.lambda$setTextAsync$0(coerceToText(clip, textView.getContext(), contentInfo.getFlags()));
            Editable editable = (Editable) textView.getText();
            Selection.setSelection(editable, editable.length());
        }
    }

    private static CharSequence coerceToText(ClipData clipData, Context context, int i) {
        CharSequence coerceToStyledText;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (int i2 = 0; i2 < clipData.getItemCount(); i2++) {
            if ((i & 1) != 0) {
                coerceToStyledText = clipData.getItemAt(i2).coerceToText(context);
                if (coerceToStyledText instanceof Spanned) {
                    coerceToStyledText = coerceToStyledText.toString();
                }
            } else {
                coerceToStyledText = clipData.getItemAt(i2).coerceToStyledText(context);
            }
            if (coerceToStyledText != null) {
                spannableStringBuilder.append(coerceToStyledText);
            }
        }
        return spannableStringBuilder;
    }

    private static boolean isUsageOfImeCommitContentEnabled(View view) {
        return view.getReceiveContentMimeTypes() == null && !Compatibility.isChangeEnabled(AUTOFILL_NON_TEXT_REQUIRES_ON_RECEIVE_CONTENT_LISTENER);
    }

    private static final class InputConnectionInfo {
        private final String[] mEditorInfoContentMimeTypes;
        private final WeakReference<InputConnection> mInputConnection;

        private InputConnectionInfo(InputConnection inputConnection, String[] strArr) {
            this.mInputConnection = new WeakReference<>(inputConnection);
            this.mEditorInfoContentMimeTypes = strArr;
        }

        public String toString() {
            return "InputConnectionInfo{mimeTypes=" + Arrays.toString(this.mEditorInfoContentMimeTypes) + ", ic=" + this.mInputConnection + '}';
        }
    }

    void setInputConnectionInfo(TextView textView, InputConnection inputConnection, EditorInfo editorInfo) {
        if (!isUsageOfImeCommitContentEnabled(textView)) {
            this.mInputConnectionInfo = null;
            return;
        }
        String[] strArr = editorInfo.contentMimeTypes;
        if (strArr == null || strArr.length == 0) {
            this.mInputConnectionInfo = null;
        } else {
            this.mInputConnectionInfo = new InputConnectionInfo(inputConnection, strArr);
        }
    }

    void clearInputConnectionInfo() {
        this.mInputConnectionInfo = null;
    }

    public String[] getFallbackMimeTypesForAutofill(TextView textView) {
        InputConnectionInfo inputConnectionInfo;
        if (isUsageOfImeCommitContentEnabled(textView) && (inputConnectionInfo = this.mInputConnectionInfo) != null) {
            return inputConnectionInfo.mEditorInfoContentMimeTypes;
        }
        return null;
    }

    private ClipData handleNonTextViaImeCommitContent(ClipData clipData) {
        ClipDescription description = clipData.getDescription();
        if (!containsUri(clipData) || containsOnlyText(clipData)) {
            if (Log.isLoggable(LOG_TAG, 2)) {
                Log.v(LOG_TAG, "onReceive: Clip doesn't contain any non-text URIs: " + description);
            }
        } else {
            InputConnectionInfo inputConnectionInfo = this.mInputConnectionInfo;
            InputConnection inputConnection = inputConnectionInfo != null ? (InputConnection) inputConnectionInfo.mInputConnection.get() : null;
            if (inputConnection == null) {
                if (Log.isLoggable(LOG_TAG, 3)) {
                    Log.d(LOG_TAG, "onReceive: No usable EditorInfo/InputConnection");
                    return clipData;
                }
            } else if (!isClipMimeTypeSupported(inputConnectionInfo.mEditorInfoContentMimeTypes, clipData.getDescription())) {
                if (Log.isLoggable(LOG_TAG, 3)) {
                    Log.d(LOG_TAG, "onReceive: MIME type is not supported by the app's commitContent impl");
                    return clipData;
                }
            } else {
                if (Log.isLoggable(LOG_TAG, 2)) {
                    Log.v(LOG_TAG, "onReceive: Trying to insert via IME: " + description);
                }
                ArrayList arrayList = new ArrayList(0);
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item itemAt = clipData.getItemAt(i);
                    Uri uri = itemAt.getUri();
                    if (uri == null || !"content".equals(uri.getScheme())) {
                        if (Log.isLoggable(LOG_TAG, 2)) {
                            Log.v(LOG_TAG, "onReceive: No content URI in item: uri=" + uri);
                        }
                        arrayList.add(itemAt);
                    } else {
                        if (Log.isLoggable(LOG_TAG, 2)) {
                            Log.v(LOG_TAG, "onReceive: Calling commitContent: uri=" + uri);
                        }
                        if (!inputConnection.commitContent(new InputContentInfo(uri, description), 0, null)) {
                            if (Log.isLoggable(LOG_TAG, 2)) {
                                Log.v(LOG_TAG, "onReceive: Call to commitContent returned false: uri=" + uri);
                            }
                            arrayList.add(itemAt);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return new ClipData(description, (ArrayList<ClipData.Item>) arrayList);
            }
        }
        return clipData;
    }

    private static boolean isClipMimeTypeSupported(String[] strArr, ClipDescription clipDescription) {
        for (String str : strArr) {
            if (clipDescription.hasMimeType(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsUri(ClipData clipData) {
        for (int i = 0; i < clipData.getItemCount(); i++) {
            if (clipData.getItemAt(i).getUri() != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsOnlyText(ClipData clipData) {
        ClipDescription description = clipData.getDescription();
        for (int i = 0; i < description.getMimeTypeCount(); i++) {
            if (!description.getMimeType(i).startsWith("text/")) {
                return false;
            }
        }
        return true;
    }
}
