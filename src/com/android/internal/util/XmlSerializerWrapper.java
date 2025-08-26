package com.android.internal.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Objects;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes4.dex */
public class XmlSerializerWrapper implements XmlSerializer {
    private final XmlSerializer mWrapped;

    public XmlSerializerWrapper(XmlSerializer xmlSerializer) {
        this.mWrapped = (XmlSerializer) Objects.requireNonNull(xmlSerializer);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) throws IllegalStateException, IllegalArgumentException {
        this.mWrapped.setFeature(str, z);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        return this.mWrapped.getFeature(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) throws IllegalStateException, IllegalArgumentException {
        this.mWrapped.setProperty(str, obj);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        return this.mWrapped.getProperty(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.setOutput(outputStream, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.setOutput(writer);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.startDocument(str, bool);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.endDocument();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.setPrefix(str, str2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) {
        return this.mWrapped.getPrefix(str, z);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        return this.mWrapped.getDepth();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        return this.mWrapped.getNamespace();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        return this.mWrapped.getName();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IOException {
        return this.mWrapped.startTag(str, str2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        return this.mWrapped.attribute(str, str2, str3);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IOException {
        return this.mWrapped.endTag(str, str2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IOException {
        return this.mWrapped.text(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i, int i2) throws IOException {
        return this.mWrapped.text(cArr, i, i2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.cdsect(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.entityRef(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.processingInstruction(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.comment(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.docdecl(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mWrapped.ignorableWhitespace(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        this.mWrapped.flush();
    }
}
