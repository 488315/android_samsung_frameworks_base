package android.content.type;

import java.io.InputStream;
import java.util.function.Function;
import libcore.content.type.MimeMap;

/* loaded from: classes.dex */
public class DefaultMimeMapFactory {
    private DefaultMimeMapFactory() {
    }

    public static MimeMap create() {
        final Class c = DefaultMimeMapFactory.class;
        return create(new Function() { // from class: android.content.type.DefaultMimeMapFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InputStream resourceAsStream;
                resourceAsStream = c.getResourceAsStream("/res/" + ((String) obj));
                return resourceAsStream;
            }
        });
    }

    public static MimeMap create(Function<String, InputStream> resourceSupplier) {
        MimeMap.Builder builder = MimeMap.builder();
        parseTypes(builder, resourceSupplier, "debian.mime.types");
        parseTypes(builder, resourceSupplier, "android.mime.types");
        parseTypes(builder, resourceSupplier, "vendor.mime.types");
        return builder.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0077, code lost:
    
        throw new java.lang.IllegalArgumentException("Malformed line: " + r3);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void parseTypes(libcore.content.type.MimeMap.Builder r10, java.util.function.Function<java.lang.String, java.io.InputStream> r11, java.lang.String r12) {
        /*
            java.lang.Object r0 = r11.apply(r12)     // Catch: java.lang.Throwable -> L98
            java.io.InputStream r0 = (java.io.InputStream) r0     // Catch: java.lang.Throwable -> L98
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)     // Catch: java.lang.Throwable -> L98
            java.io.InputStream r0 = (java.io.InputStream) r0     // Catch: java.lang.Throwable -> L98
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L8c
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L8c
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L8c
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L8c
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L82
            r3 = 10
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L82
        L1d:
            java.lang.String r3 = r1.readLine()     // Catch: java.lang.Throwable -> L82
            r4 = r3
            if (r3 == 0) goto L78
            r2.clear()     // Catch: java.lang.Throwable -> L82
            r3 = 0
        L28:
            r5 = 32
            int r5 = r4.indexOf(r5, r3)     // Catch: java.lang.Throwable -> L82
            if (r5 >= 0) goto L35
            int r6 = r4.length()     // Catch: java.lang.Throwable -> L82
            r5 = r6
        L35:
            java.lang.String r6 = r4.substring(r3, r5)     // Catch: java.lang.Throwable -> L82
            boolean r7 = r6.isEmpty()     // Catch: java.lang.Throwable -> L82
            if (r7 != 0) goto L5f
            r2.add(r6)     // Catch: java.lang.Throwable -> L82
            int r3 = r5 + 1
            int r5 = r4.length()     // Catch: java.lang.Throwable -> L82
            if (r3 < r5) goto L28
            r5 = 0
            java.lang.Object r5 = r2.get(r5)     // Catch: java.lang.Throwable -> L82
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Throwable -> L82
            int r6 = r2.size()     // Catch: java.lang.Throwable -> L82
            r7 = 1
            java.util.List r6 = r2.subList(r7, r6)     // Catch: java.lang.Throwable -> L82
            r10.addMimeMapping(r5, r6)     // Catch: java.lang.Throwable -> L82
            goto L1d
        L5f:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L82
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L82
            r8.<init>()     // Catch: java.lang.Throwable -> L82
            java.lang.String r9 = "Malformed line: "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch: java.lang.Throwable -> L82
            java.lang.StringBuilder r8 = r8.append(r4)     // Catch: java.lang.Throwable -> L82
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L82
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L82
            throw r7     // Catch: java.lang.Throwable -> L82
        L78:
            r1.close()     // Catch: java.lang.Throwable -> L8c
            if (r0 == 0) goto L80
            r0.close()     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L98
        L80:
            return
        L82:
            r2 = move-exception
            r1.close()     // Catch: java.lang.Throwable -> L87
            goto L8b
        L87:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch: java.lang.Throwable -> L8c
        L8b:
            throw r2     // Catch: java.lang.Throwable -> L8c
        L8c:
            r1 = move-exception
            if (r0 == 0) goto L97
            r0.close()     // Catch: java.lang.Throwable -> L93
            goto L97
        L93:
            r2 = move-exception
            r1.addSuppressed(r2)     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L98
        L97:
            throw r1     // Catch: java.lang.Throwable -> L98 java.lang.Throwable -> L98
        L98:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to parse "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r12)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.type.DefaultMimeMapFactory.parseTypes(libcore.content.type.MimeMap$Builder, java.util.function.Function, java.lang.String):void");
    }
}
