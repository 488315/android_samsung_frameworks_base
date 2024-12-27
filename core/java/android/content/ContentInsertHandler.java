package android.content;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public interface ContentInsertHandler extends ContentHandler {
    void insert(ContentResolver contentResolver, InputStream inputStream)
            throws IOException, SAXException;

    void insert(ContentResolver contentResolver, String str) throws SAXException;
}
