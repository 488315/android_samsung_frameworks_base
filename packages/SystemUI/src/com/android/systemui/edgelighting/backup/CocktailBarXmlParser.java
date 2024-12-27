package com.android.systemui.edgelighting.backup;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CocktailBarXmlParser extends DefaultHandler {
    public Boolean mCurrentElement = Boolean.FALSE;
    public String mCurrentValue = "";
    public CocktailBarSettingValue mItem = null;
    public final ArrayList itemsList = new ArrayList();

    public CocktailBarXmlParser(Context context, File file) throws ParserConfigurationException, SAXException, IOException {
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        xMLReader.setContentHandler(this);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                xMLReader.parse(new InputSource(new InputStreamReader(fileInputStream)));
                fileInputStream.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void characters(char[] cArr, int i, int i2) {
        if (this.mCurrentElement.booleanValue()) {
            this.mCurrentValue += new String(cArr, i, i2);
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void endElement(String str, String str2, String str3) {
        this.mCurrentElement = Boolean.FALSE;
        if (str2.equalsIgnoreCase("value")) {
            this.mItem.mValue = this.mCurrentValue;
        } else if (str2.equalsIgnoreCase("type")) {
            this.mItem.mType = this.mCurrentValue;
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void startElement(String str, String str2, String str3, Attributes attributes) {
        this.mCurrentElement = Boolean.TRUE;
        this.mCurrentValue = "";
        if (str2.equalsIgnoreCase("setting")) {
            CocktailBarSettingValue cocktailBarSettingValue = new CocktailBarSettingValue();
            this.mItem = cocktailBarSettingValue;
            cocktailBarSettingValue.mName = attributes.getValue("name");
            this.itemsList.add(this.mItem);
        }
    }
}
