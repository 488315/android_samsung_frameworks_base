package android.content.type;

import java.io.InputStream;
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
                InputStream resourceAsStream;
                resourceAsStream = cls.getResourceAsStream("/res/" + ((String) obj));
                return resourceAsStream;
            }
        });
    }

    public static MimeMap create(Function<String, InputStream> function) {
        MimeMap.Builder builder = MimeMap.builder();
        parseTypes(builder, function, "debian.mime.types");
        parseTypes(builder, function, "android.mime.types");
        parseTypes(builder, function, "vendor.mime.types");
        return builder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0072, code lost:
    
        throw new java.lang.IllegalArgumentException("Malformed line: " + r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void parseTypes(libcore.content.type.MimeMap.Builder r7, java.util.function.Function<java.lang.String, java.io.InputStream> r8, java.lang.String r9) {
        /*
            java.lang.Object r8 = r8.apply(r9)     // Catch: java.lang.Throwable -> L92
            java.io.InputStream r8 = (java.io.InputStream) r8     // Catch: java.lang.Throwable -> L92
            java.lang.Object r8 = java.util.Objects.requireNonNull(r8)     // Catch: java.lang.Throwable -> L92
            java.io.InputStream r8 = (java.io.InputStream) r8     // Catch: java.lang.Throwable -> L92
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L86
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L86
            r1.<init>(r8)     // Catch: java.lang.Throwable -> L86
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L86
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L7c
            r2 = 10
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L7c
        L1d:
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L7c
            if (r2 == 0) goto L73
            r1.clear()     // Catch: java.lang.Throwable -> L7c
            r3 = 0
            r4 = r3
        L28:
            r5 = 32
            int r5 = r2.indexOf(r5, r4)     // Catch: java.lang.Throwable -> L7c
            if (r5 >= 0) goto L34
            int r5 = r2.length()     // Catch: java.lang.Throwable -> L7c
        L34:
            java.lang.String r4 = r2.substring(r4, r5)     // Catch: java.lang.Throwable -> L7c
            boolean r6 = r4.isEmpty()     // Catch: java.lang.Throwable -> L7c
            if (r6 != 0) goto L5c
            r1.add(r4)     // Catch: java.lang.Throwable -> L7c
            int r4 = r5 + 1
            int r5 = r2.length()     // Catch: java.lang.Throwable -> L7c
            if (r4 < r5) goto L28
            java.lang.Object r2 = r1.get(r3)     // Catch: java.lang.Throwable -> L7c
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> L7c
            int r3 = r1.size()     // Catch: java.lang.Throwable -> L7c
            r4 = 1
            java.util.List r3 = r1.subList(r4, r3)     // Catch: java.lang.Throwable -> L7c
            r7.addMimeMapping(r2, r3)     // Catch: java.lang.Throwable -> L7c
            goto L1d
        L5c:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L7c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c
            r1.<init>()     // Catch: java.lang.Throwable -> L7c
            java.lang.String r3 = "Malformed line: "
            r1.append(r3)     // Catch: java.lang.Throwable -> L7c
            r1.append(r2)     // Catch: java.lang.Throwable -> L7c
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L7c
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L7c
            throw r7     // Catch: java.lang.Throwable -> L7c
        L73:
            r0.close()     // Catch: java.lang.Throwable -> L86
            if (r8 == 0) goto L7b
            r8.close()     // Catch: java.lang.Throwable -> L92 java.lang.Throwable -> L92
        L7b:
            return
        L7c:
            r7 = move-exception
            r0.close()     // Catch: java.lang.Throwable -> L81
            goto L85
        L81:
            r0 = move-exception
            r7.addSuppressed(r0)     // Catch: java.lang.Throwable -> L86
        L85:
            throw r7     // Catch: java.lang.Throwable -> L86
        L86:
            r7 = move-exception
            if (r8 == 0) goto L91
            r8.close()     // Catch: java.lang.Throwable -> L8d
            goto L91
        L8d:
            r8 = move-exception
            r7.addSuppressed(r8)     // Catch: java.lang.Throwable -> L92 java.lang.Throwable -> L92
        L91:
            throw r7     // Catch: java.lang.Throwable -> L92 java.lang.Throwable -> L92
        L92:
            r7 = move-exception
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Failed to parse "
            r0.<init>(r1)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            r8.<init>(r9, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.type.DefaultMimeMapFactory.parseTypes(libcore.content.type.MimeMap$Builder, java.util.function.Function, java.lang.String):void");
    }
}
