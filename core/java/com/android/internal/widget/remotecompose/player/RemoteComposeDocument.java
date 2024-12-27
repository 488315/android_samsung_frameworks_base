package com.android.internal.widget.remotecompose.player;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteComposeBuffer;
import com.android.internal.widget.remotecompose.core.RemoteContext;

import java.io.InputStream;

/* loaded from: classes5.dex */
public class RemoteComposeDocument {
    CoreDocument mDocument = new CoreDocument();

    public RemoteComposeDocument(InputStream inputStream) {
        RemoteComposeBuffer buffer =
                RemoteComposeBuffer.fromInputStream(
                        inputStream, this.mDocument.getRemoteComposeState());
        this.mDocument.initFromBuffer(buffer);
    }

    public CoreDocument getDocument() {
        return this.mDocument;
    }

    public void setDocument(CoreDocument document) {
        this.mDocument = document;
    }

    public void initializeContext(RemoteContext context) {
        this.mDocument.initializeContext(context);
    }

    public int getWidth() {
        return this.mDocument.getWidth();
    }

    public int getHeight() {
        return this.mDocument.getHeight();
    }

    public void paint(RemoteContext context, int theme) {
        this.mDocument.paint(context, theme);
    }

    public int needsRepaint() {
        return this.mDocument.needsRepaint();
    }

    public boolean canBeDisplayed(int majorVersion, int minorVersion, long capabilities) {
        return this.mDocument.canBeDisplayed(majorVersion, minorVersion, capabilities);
    }

    public String toString() {
        return "Document{\n" + this.mDocument + '}';
    }
}
