package com.android.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class XmlPullParserWrapper implements XmlPullParser {
    private final XmlPullParser mWrapped;

    public XmlPullParserWrapper(XmlPullParser xmlPullParser) {
        this.mWrapped = (XmlPullParser) Objects.requireNonNull(xmlPullParser);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(String str, boolean z) throws XmlPullParserException {
        this.mWrapped.setFeature(str, z);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(String str) {
        return this.mWrapped.getFeature(str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(String str, Object obj) throws XmlPullParserException {
        this.mWrapped.setProperty(str, obj);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public Object getProperty(String str) {
        return this.mWrapped.getProperty(str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) throws XmlPullParserException {
        this.mWrapped.setInput(reader);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
        this.mWrapped.setInput(inputStream, str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getInputEncoding() {
        return this.mWrapped.getInputEncoding();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
        this.mWrapped.defineEntityReplacementText(str, str2);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) throws XmlPullParserException {
        return this.mWrapped.getNamespaceCount(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespacePrefix(int i) throws XmlPullParserException {
        return this.mWrapped.getNamespacePrefix(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespaceUri(int i) throws XmlPullParserException {
        return this.mWrapped.getNamespaceUri(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace(String str) {
        return this.mWrapped.getNamespace(str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return this.mWrapped.getDepth();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPositionDescription() {
        return this.mWrapped.getPositionDescription();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return this.mWrapped.getLineNumber();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return this.mWrapped.getColumnNumber();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws XmlPullParserException {
        return this.mWrapped.isWhitespace();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getText() {
        return this.mWrapped.getText();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        return this.mWrapped.getTextCharacters(iArr);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace() {
        return this.mWrapped.getNamespace();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getName() {
        return this.mWrapped.getName();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPrefix() {
        return this.mWrapped.getPrefix();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws XmlPullParserException {
        return this.mWrapped.isEmptyElementTag();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getAttributeCount() {
        return this.mWrapped.getAttributeCount();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeNamespace(int i) {
        return this.mWrapped.getAttributeNamespace(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeName(int i) {
        return this.mWrapped.getAttributeName(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributePrefix(int i) {
        return this.mWrapped.getAttributePrefix(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeType(int i) {
        return this.mWrapped.getAttributeType(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return this.mWrapped.isAttributeDefault(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(int i) {
        return this.mWrapped.getAttributeValue(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(String str, String str2) {
        return this.mWrapped.getAttributeValue(str, str2);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getEventType() throws XmlPullParserException {
        return this.mWrapped.getEventType();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int next() throws XmlPullParserException, IOException {
        return this.mWrapped.next();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws XmlPullParserException, IOException {
        return this.mWrapped.nextToken();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
        this.mWrapped.require(i, str, str2);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String nextText() throws XmlPullParserException, IOException {
        return this.mWrapped.nextText();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws XmlPullParserException, IOException {
        return this.mWrapped.nextTag();
    }
}
