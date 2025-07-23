package com.android.internal.widget.remotecompose.player;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteComposeBuffer;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import java.io.InputStream;

/* loaded from: classes6.dex */
public class RemoteComposeDocument {
    CoreDocument mDocument;

    public int hasSensorListeners(int[] iArr) {
        return 0;
    }

    public RemoteComposeDocument(InputStream inputStream) {
        CoreDocument coreDocument = new CoreDocument();
        this.mDocument = coreDocument;
        this.mDocument.initFromBuffer(RemoteComposeBuffer.fromInputStream(inputStream, coreDocument.getRemoteComposeState()));
    }

    public RemoteComposeDocument(CoreDocument coreDocument) {
        new CoreDocument();
        this.mDocument = coreDocument;
    }

    public CoreDocument getDocument() {
        return this.mDocument;
    }

    public void setDocument(CoreDocument coreDocument) {
        this.mDocument = coreDocument;
    }

    public void initializeContext(RemoteContext remoteContext) {
        this.mDocument.initializeContext(remoteContext);
    }

    public int getWidth() {
        return this.mDocument.getWidth();
    }

    public int getHeight() {
        return this.mDocument.getHeight();
    }

    public void paint(RemoteContext remoteContext, int i) {
        this.mDocument.paint(remoteContext, i);
    }

    public int needsRepaint() {
        return this.mDocument.needsRepaint();
    }

    public boolean canBeDisplayed(int i, int i2, long j) {
        return this.mDocument.canBeDisplayed(i, i2, j);
    }

    public String toString() {
        return "Document{\n" + this.mDocument + '}';
    }

    public String[] getNamedColors() {
        return this.mDocument.getNamedColors();
    }

    public String[] getNamedVariables(int i) {
        return this.mDocument.getNamedVariables(i);
    }

    public Component getComponent(int i) {
        return this.mDocument.getComponent(i);
    }

    public void invalidate() {
        this.mDocument.invalidateMeasure();
    }

    public String[] getStats() {
        CoreDocument coreDocument = this.mDocument;
        if (coreDocument == null) {
            return new String[0];
        }
        return coreDocument.getStats();
    }

    public boolean isUpdateDoc() {
        return this.mDocument.isUpdateDoc();
    }
}
