package com.android.server.enterprise.general.font;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TypefaceParser extends DefaultHandler {
    public boolean in_droidname;
    public boolean in_filename;
    public boolean in_monospace;
    public boolean in_sans;
    public boolean in_serif;
    public Typeface mFont;
    public TypefaceFile mFontFile;

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void characters(char[] cArr, int i, int i2) {
        if (this.in_filename) {
            this.mFontFile.fileName = new String(cArr, i, i2);
        } else if (this.in_droidname) {
            this.mFontFile.droidName = new String(cArr, i, i2);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void endDocument() {}

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void endElement(String str, String str2, String str3) {
        if (str2.equals("font")) {
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
        if (!str2.equals("file")) {
            if (str2.equals("filename")) {
                this.in_filename = false;
                return;
            } else {
                if (str2.equals("droidname")) {
                    this.in_droidname = false;
                    return;
                }
                return;
            }
        }
        TypefaceFile typefaceFile = this.mFontFile;
        if (typefaceFile != null) {
            if (this.in_sans) {
                ((ArrayList) this.mFont.mSansFonts).add(typefaceFile);
            } else if (this.in_serif) {
                ((ArrayList) this.mFont.mSerifFonts).add(typefaceFile);
            } else if (this.in_monospace) {
                ((ArrayList) this.mFont.mMonospaceFonts).add(typefaceFile);
            }
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void startDocument() {
        this.mFont = new Typeface();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void startElement(String str, String str2, String str3, Attributes attributes) {
        if (str2.equals("font")) {
            this.mFont.mName = attributes.getValue("displayname");
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
            TypefaceFile typefaceFile = new TypefaceFile();
            typefaceFile.fileName = null;
            typefaceFile.droidName = null;
            this.mFontFile = typefaceFile;
            return;
        }
        if (str2.equals("filename")) {
            this.in_filename = true;
        } else if (str2.equals("droidname")) {
            this.in_droidname = true;
        }
    }
}
