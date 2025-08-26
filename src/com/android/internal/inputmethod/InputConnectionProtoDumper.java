package com.android.internal.inputmethod;

import android.util.proto.ProtoOutputStream;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.SurroundingText;

/* loaded from: classes5.dex */
public final class InputConnectionProtoDumper {
    static final String TAG = "InputConnectionProtoDumper";

    private InputConnectionProtoDumper() {
    }

    public static byte[] buildGetTextAfterCursorProto(int i, int i2, CharSequence charSequence) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        long jStart = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        protoOutputStream.end(jStart);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetTextBeforeCursorProto(int i, int i2, CharSequence charSequence) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        long jStart = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        protoOutputStream.end(jStart);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetSelectedTextProto(int i, CharSequence charSequence) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        long jStart = protoOutputStream.start(1146756268035L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.end(jStart);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetSurroundingTextProto(int i, int i2, int i3, SurroundingText surroundingText) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        long jStart = protoOutputStream.start(1146756268036L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        protoOutputStream.write(1120986464259L, i3);
        if (surroundingText != null) {
            long jStart2 = protoOutputStream.start(1146756268036L);
            protoOutputStream.write(1120986464258L, surroundingText.getSelectionStart());
            protoOutputStream.write(1120986464259L, surroundingText.getSelectionEnd());
            protoOutputStream.write(1120986464260L, surroundingText.getOffset());
            protoOutputStream.end(jStart2);
        }
        protoOutputStream.end(jStart);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetCursorCapsModeProto(int i, int i2) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        long jStart = protoOutputStream.start(1146756268037L);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1120986464258L, i2);
        protoOutputStream.end(jStart);
        return protoOutputStream.getBytes();
    }

    public static byte[] buildGetExtractedTextProto(ExtractedTextRequest extractedTextRequest, int i, ExtractedText extractedText) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        long jStart = protoOutputStream.start(1146756268038L);
        long jStart2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, extractedTextRequest.token);
        protoOutputStream.write(1120986464258L, extractedTextRequest.flags);
        protoOutputStream.write(1120986464259L, extractedTextRequest.hintMaxLines);
        protoOutputStream.write(1120986464260L, extractedTextRequest.hintMaxChars);
        protoOutputStream.end(jStart2);
        protoOutputStream.write(1120986464258L, i);
        protoOutputStream.end(jStart);
        return protoOutputStream.getBytes();
    }
}
