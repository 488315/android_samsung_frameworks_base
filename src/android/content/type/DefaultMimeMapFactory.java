package android.content.type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import libcore.content.type.MimeMap;

/* loaded from: classes.dex */
public class DefaultMimeMapFactory {
    private DefaultMimeMapFactory() {
    }

    public static MimeMap create() {
        final Class<DefaultMimeMapFactory> cls = DefaultMimeMapFactory.class;
        return create(new Function() { // from class: android.content.type.DefaultMimeMapFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return cls.getResourceAsStream("/res/" + ((String) obj));
            }
        });
    }

    public static MimeMap create(Function<String, InputStream> function) throws IOException {
        MimeMap.Builder builder = MimeMap.builder();
        parseTypes(builder, function, "debian.mime.types");
        parseTypes(builder, function, "android.mime.types");
        parseTypes(builder, function, "vendor.mime.types");
        return builder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:
    
        throw new java.lang.IllegalArgumentException("Malformed line: " + r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void parseTypes(MimeMap.Builder builder, Function<String, InputStream> function, String str) throws IOException {
        try {
            InputStream inputStream = (InputStream) Objects.requireNonNull(function.apply(str));
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    ArrayList arrayList = new ArrayList(10);
                    loop0: while (true) {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            arrayList.clear();
                            int i = 0;
                            do {
                                int iIndexOf = line.indexOf(32, i);
                                if (iIndexOf < 0) {
                                    iIndexOf = line.length();
                                }
                                String strSubstring = line.substring(i, iIndexOf);
                                if (strSubstring.isEmpty()) {
                                    break loop0;
                                }
                                arrayList.add(strSubstring);
                                i = iIndexOf + 1;
                            } while (i < line.length());
                            builder.addMimeMapping((String) arrayList.get(0), arrayList.subList(1, arrayList.size()));
                        } else {
                            bufferedReader.close();
                            if (inputStream != null) {
                                inputStream.close();
                                return;
                            }
                            return;
                        }
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Failed to parse " + str, e);
        }
    }
}
