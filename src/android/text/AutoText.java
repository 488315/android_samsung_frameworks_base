package android.text;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.provider.UserDictionary;
import android.view.View;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class AutoText {
    private static final int DEFAULT = 14337;
    private static final int INCREMENT = 1024;
    private static final int RIGHT = 9300;
    private static final int TRIE_C = 0;
    private static final int TRIE_CHILD = 2;
    private static final int TRIE_NEXT = 3;
    private static final char TRIE_NULL = 65535;
    private static final int TRIE_OFF = 1;
    private static final int TRIE_ROOT = 0;
    private static final int TRIE_SIZEOF = 4;
    private static AutoText sInstance = new AutoText(Resources.getSystem());
    private static Object sLock = new Object();
    private Locale mLocale;
    private int mSize;
    private String mText;
    private char[] mTrie;
    private char mTrieUsed;

    private AutoText(Resources resources) {
        this.mLocale = resources.getConfiguration().locale;
        init(resources);
    }

    private static AutoText getInstance(View view) {
        AutoText autoText;
        Resources resources = view.getContext().getResources();
        Locale locale = resources.getConfiguration().locale;
        synchronized (sLock) {
            autoText = sInstance;
            if (!locale.equals(autoText.mLocale)) {
                autoText = new AutoText(resources);
                sInstance = autoText;
            }
        }
        return autoText;
    }

    public static String get(CharSequence charSequence, int i, int i2, View view) {
        return getInstance(view).lookup(charSequence, i, i2);
    }

    public static int getSize(View view) {
        return getInstance(view).getSize();
    }

    private int getSize() {
        return this.mSize;
    }

    private String lookup(CharSequence charSequence, int i, int i2) {
        char c;
        char c2 = this.mTrie[0];
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            while (true) {
                if (c2 == 65535) {
                    break;
                }
                char[] cArr = this.mTrie;
                if (charAt != cArr[c2]) {
                    c2 = cArr[c2 + 3];
                } else {
                    if (i == i2 - 1 && (c = cArr[c2 + 1]) != 65535) {
                        char charAt2 = this.mText.charAt(c);
                        int i3 = c + 1;
                        return this.mText.substring(i3, charAt2 + i3);
                    }
                    c2 = cArr[c2 + 2];
                }
            }
            if (c2 == 65535) {
                return null;
            }
            i++;
        }
        return null;
    }

    private void init(Resources resources) {
        char length;
        XmlResourceParser xml = resources.getXml(R.xml.autotext);
        StringBuilder sb = new StringBuilder(9300);
        char[] cArr = new char[14337];
        this.mTrie = cArr;
        cArr[0] = TRIE_NULL;
        this.mTrieUsed = (char) 1;
        try {
            try {
                try {
                    XmlUtils.beginDocument(xml, "words");
                    while (true) {
                        XmlUtils.nextElement(xml);
                        String name = xml.getName();
                        if (name == null || !name.equals(UserDictionary.Words.WORD)) {
                            break;
                        }
                        String attributeValue = xml.getAttributeValue(null, "src");
                        if (xml.next() == 4) {
                            String text = xml.getText();
                            if (text.equals("")) {
                                length = 0;
                            } else {
                                length = (char) sb.length();
                                sb.append((char) text.length());
                                sb.append(text);
                            }
                            add(attributeValue, length);
                        }
                    }
                    resources.flushLayoutCache();
                    xml.close();
                    this.mText = sb.toString();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (XmlPullParserException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            xml.close();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0030, code lost:
    
        if (r6 != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
    
        r6 = newTrieNode();
        r7 = r9.mTrie;
        r7[r4] = r6;
        r7[r6] = r5;
        r7[r7[r4] + 1] = android.text.AutoText.TRIE_NULL;
        r7[r7[r4] + 3] = android.text.AutoText.TRIE_NULL;
        r7[r7[r4] + 2] = android.text.AutoText.TRIE_NULL;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004f, code lost:
    
        if (r3 != (r0 - 1)) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0057, code lost:
    
        r4 = r7[r4] + 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005b, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
    
        r7[r7[r4] + 1] = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0056, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void add(java.lang.String r10, char r11) {
        /*
            r9 = this;
            int r0 = r10.length()
            int r1 = r9.mSize
            r2 = 1
            int r1 = r1 + r2
            r9.mSize = r1
            r1 = 0
            r3 = r1
            r4 = r3
        Ld:
            if (r3 >= r0) goto L5e
            char r5 = r10.charAt(r3)
        L13:
            char[] r6 = r9.mTrie
            char r7 = r6[r4]
            r8 = 65535(0xffff, float:9.1834E-41)
            if (r7 == r8) goto L2f
            char r4 = r6[r7]
            if (r5 != r4) goto L2c
            int r4 = r0 + (-1)
            if (r3 != r4) goto L28
            int r7 = r7 + r2
            r6[r7] = r11
            return
        L28:
            int r4 = r7 + 2
            r6 = r2
            goto L30
        L2c:
            int r4 = r7 + 3
            goto L13
        L2f:
            r6 = r1
        L30:
            if (r6 != 0) goto L5b
            char r6 = r9.newTrieNode()
            char[] r7 = r9.mTrie
            r7[r4] = r6
            r7[r6] = r5
            char r5 = r7[r4]
            int r5 = r5 + r2
            r7[r5] = r8
            char r5 = r7[r4]
            int r5 = r5 + 3
            r7[r5] = r8
            char r5 = r7[r4]
            int r5 = r5 + 2
            r7[r5] = r8
            int r5 = r0 + (-1)
            if (r3 != r5) goto L57
            char r9 = r7[r4]
            int r9 = r9 + r2
            r7[r9] = r11
            return
        L57:
            char r4 = r7[r4]
            int r4 = r4 + 2
        L5b:
            int r3 = r3 + 1
            goto Ld
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.AutoText.add(java.lang.String, char):void");
    }

    private char newTrieNode() {
        int i = this.mTrieUsed + 4;
        char[] cArr = this.mTrie;
        if (i > cArr.length) {
            char[] cArr2 = new char[cArr.length + 1024];
            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
            this.mTrie = cArr2;
        }
        char c = this.mTrieUsed;
        this.mTrieUsed = (char) (c + 4);
        return c;
    }
}
