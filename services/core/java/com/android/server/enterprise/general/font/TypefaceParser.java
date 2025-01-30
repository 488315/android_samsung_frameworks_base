package com.android.server.enterprise.general.font;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes2.dex */
public class TypefaceParser extends DefaultHandler {
    public boolean in_font = false;
    public boolean in_sans = false;
    public boolean in_serif = false;
    public boolean in_monospace = false;
    public boolean in_file = false;
    public boolean in_filename = false;
    public boolean in_droidname = false;
    public Typeface mFont = null;
    public TypefaceFile mFontFile = null;

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endDocument() {
    }

    public Typeface getParsedData() {
        return this.mFont;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() {
        this.mFont = new Typeface();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) {
        if (str2.equals("font")) {
            this.in_font = true;
            this.mFont.setName(attributes.getValue("displayname"));
            return;
        }
        if (str2.equals("sans")) {
            this.in_sans = true;
            return;
        }
        if (str2.equals("serif")) {
            this.in_serif = true;
            return;
        }
        if (str2.equals("monospace")) {
            this.in_monospace = true;
            return;
        }
        if (str2.equals("file")) {
            this.in_file = true;
            this.mFontFile = new TypefaceFile();
        } else if (str2.equals("filename")) {
            this.in_filename = true;
        } else if (str2.equals("droidname")) {
            this.in_droidname = true;
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) {
        if (str2.equals("font")) {
            this.in_font = false;
            return;
        }
        if (str2.equals("sans")) {
            this.in_sans = false;
            return;
        }
        if (str2.equals("serif")) {
            this.in_serif = false;
            return;
        }
        if (str2.equals("monospace")) {
            this.in_monospace = false;
            return;
        }
        if (str2.equals("file")) {
            this.in_file = false;
            TypefaceFile typefaceFile = this.mFontFile;
            if (typefaceFile != null) {
                if (this.in_sans) {
                    this.mFont.mSansFonts.add(typefaceFile);
                    return;
                } else if (this.in_serif) {
                    this.mFont.mSerifFonts.add(typefaceFile);
                    return;
                } else {
                    if (this.in_monospace) {
                        this.mFont.mMonospaceFonts.add(typefaceFile);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (str2.equals("filename")) {
            this.in_filename = false;
        } else if (str2.equals("droidname")) {
            this.in_droidname = false;
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) {
        if (this.in_filename) {
            this.mFontFile.setFileName(new String(cArr, i, i2));
        } else if (this.in_droidname) {
            this.mFontFile.setDroidName(new String(cArr, i, i2));
        }
    }
}
