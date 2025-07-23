package android.util.jar;

import android.util.jar.StrictJarManifest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

/* loaded from: classes4.dex */
class StrictJarManifestReader {
    private final byte[] buf;
    private final int endOfMainSection;
    private Attributes.Name name;
    private int pos;
    private String value;
    private final HashMap<String, Attributes.Name> attributeNameCache = new HashMap<>();
    private final ByteArrayOutputStream valueBuffer = new ByteArrayOutputStream(80);
    private int consecutiveLineBreaks = 0;

    public StrictJarManifestReader(byte[] bArr, Attributes attributes) throws IOException {
        this.buf = bArr;
        while (readHeader()) {
            attributes.put(this.name, this.value);
        }
        this.endOfMainSection = this.pos;
    }

    public void readEntries(Map<String, Attributes> map, Map<String, StrictJarManifest.Chunk> map2) throws IOException {
        int i = this.pos;
        while (readHeader()) {
            if (!StrictJarManifest.ATTRIBUTE_NAME_NAME.equals(this.name)) {
                throw new IOException("Entry is not named");
            }
            String str = this.value;
            Attributes attributes = map.get(str);
            if (attributes == null) {
                attributes = new Attributes(12);
            }
            while (readHeader()) {
                attributes.put(this.name, this.value);
            }
            if (map2 != null) {
                if (map2.get(str) != null) {
                    throw new IOException("A jar verifier does not support more than one entry with the same name");
                }
                map2.put(str, new StrictJarManifest.Chunk(i, this.pos));
                i = this.pos;
            }
            map.put(str, attributes);
        }
    }

    public int getEndOfMainSection() {
        return this.endOfMainSection;
    }

    private boolean readHeader() throws IOException {
        if (this.consecutiveLineBreaks > 1) {
            this.consecutiveLineBreaks = 0;
            return false;
        }
        readName();
        this.consecutiveLineBreaks = 0;
        readValue();
        return this.consecutiveLineBreaks > 0;
    }

    private void readName() throws IOException {
        int i;
        byte[] bArr;
        int i2 = this.pos;
        do {
            i = this.pos;
            bArr = this.buf;
            if (i >= bArr.length) {
                return;
            } else {
                this.pos = i + 1;
            }
        } while (bArr[i] != 58);
        String str = new String(this.buf, i2, (this.pos - i2) - 1, StandardCharsets.US_ASCII);
        byte[] bArr2 = this.buf;
        int i3 = this.pos;
        this.pos = i3 + 1;
        if (bArr2[i3] != 32) {
            throw new IOException(String.format("Invalid value for attribute '%s'", str));
        }
        try {
            Attributes.Name name = this.attributeNameCache.get(str);
            this.name = name;
            if (name == null) {
                Attributes.Name name2 = new Attributes.Name(str);
                this.name = name2;
                this.attributeNameCache.put(str, name2);
            }
        } catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void readValue() throws IOException {
        byte[] bArr;
        int i = this.pos;
        this.valueBuffer.reset();
        int i2 = i;
        loop0: while (true) {
            boolean z = false;
            while (true) {
                int i3 = this.pos;
                bArr = this.buf;
                if (i3 >= bArr.length) {
                    break loop0;
                }
                int i4 = i3 + 1;
                this.pos = i4;
                byte b = bArr[i3];
                if (b == 0) {
                    throw new IOException("NUL character in a manifest");
                }
                if (b != 10) {
                    if (b == 13) {
                        this.consecutiveLineBreaks++;
                        z = true;
                    } else if (b == 32 && this.consecutiveLineBreaks == 1) {
                        this.valueBuffer.write(bArr, i, i2 - i);
                        i = this.pos;
                        this.consecutiveLineBreaks = 0;
                    } else {
                        if (this.consecutiveLineBreaks >= 1) {
                            this.pos = i3;
                            break loop0;
                        }
                        i2 = i4;
                    }
                } else if (z) {
                    break;
                } else {
                    this.consecutiveLineBreaks++;
                }
            }
        }
        this.valueBuffer.write(bArr, i, i2 - i);
        this.value = this.valueBuffer.toString(StandardCharsets.UTF_8.name());
    }
}
