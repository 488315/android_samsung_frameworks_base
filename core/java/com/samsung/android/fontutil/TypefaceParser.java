package com.samsung.android.fontutil;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes5.dex */
public class TypefaceParser extends DefaultHandler {
    private static final String ATTR_NAME = "displayname";
    private static final String NODE_DROIDNAME = "droidname";
    private static final String NODE_FILE = "file";
    private static final String NODE_FILENAME = "filename";
    private static final String NODE_FONT = "font";
    private static final String NODE_MONOSPACE = "monospace";
    private static final String NODE_SANS = "sans";
    private static final String NODE_SERIF = "serif";
    private boolean in_sans = false;
    private boolean in_serif = false;
    private boolean in_monospace = false;
    private boolean in_filename = false;
    private boolean in_droidname = false;
    private SemTypeface mFont = null;
    private TypefaceFile mFontFile = null;

    public SemTypeface getParsedData() {
        return this.mFont;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        this.mFont = new SemTypeface();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals("font")) {
            String attrValue = atts.getValue(ATTR_NAME);
            this.mFont.setName(attrValue);
            return;
        }
        if (localName.equals(NODE_SANS)) {
            this.in_sans = true;
            return;
        }
        if (localName.equals(NODE_SERIF)) {
            this.in_serif = true;
            return;
        }
        if (localName.equals(NODE_MONOSPACE)) {
            this.in_monospace = true;
            return;
        }
        if (localName.equals("file")) {
            this.mFontFile = new TypefaceFile();
        } else if (localName.equals(NODE_FILENAME)) {
            this.in_filename = true;
        } else if (localName.equals(NODE_DROIDNAME)) {
            this.in_droidname = true;
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (!localName.equals("font")) {
            if (localName.equals(NODE_SANS)) {
                this.in_sans = false;
                return;
            }
            if (localName.equals(NODE_SERIF)) {
                this.in_serif = false;
                return;
            }
            if (localName.equals(NODE_MONOSPACE)) {
                this.in_monospace = false;
                return;
            }
            if (localName.equals("file")) {
                if (this.mFontFile != null) {
                    if (this.in_sans) {
                        this.mFont.mSansFonts.add(this.mFontFile);
                        return;
                    } else if (this.in_serif) {
                        this.mFont.mSerifFonts.add(this.mFontFile);
                        return;
                    } else {
                        if (this.in_monospace) {
                            this.mFont.mMonospaceFonts.add(this.mFontFile);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (localName.equals(NODE_FILENAME)) {
                this.in_filename = false;
            } else if (localName.equals(NODE_DROIDNAME)) {
                this.in_droidname = false;
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] ch, int start, int length) {
        if (this.in_filename) {
            this.mFontFile.setFileName(new String(ch, start, length));
        } else if (this.in_droidname) {
            this.mFontFile.setDroidName(new String(ch, start, length));
        }
    }
}
