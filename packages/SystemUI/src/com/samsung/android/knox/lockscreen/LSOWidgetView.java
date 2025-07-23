package com.samsung.android.knox.lockscreen;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LSOWidgetView {
    public static final String TAG = "LSO";

    /* JADX WARN: Removed duplicated region for block: B:17:0x0056 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.View getWidget(android.content.Context r9, com.samsung.android.knox.lockscreen.LSOItemWidget r10) {
        /*
            java.lang.String r0 = "LSO"
            java.lang.String r1 = " directly/indirectly not inherited from View object"
            java.lang.String r2 = r10.getWidget()
            r3 = 0
            if (r2 == 0) goto Ld6
            int r4 = r2.length()
            if (r4 != 0) goto L13
            goto Ld6
        L13:
            r4 = 0
            r5 = 1
            java.lang.Class r6 = java.lang.Class.forName(r2)     // Catch: java.lang.Exception -> L40 java.lang.ClassNotFoundException -> L49
            java.lang.Class<android.view.View> r7 = android.view.View.class
            boolean r7 = r7.isAssignableFrom(r6)     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            if (r7 != 0) goto L2b
            java.lang.String r9 = r2.concat(r1)     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            android.util.Log.d(r0, r9)     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            return r3
        L29:
            r9 = move-exception
            goto L42
        L2b:
            java.lang.Class[] r1 = new java.lang.Class[r5]     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r1[r4] = r7     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            java.lang.Object[] r9 = new java.lang.Object[]{r9}     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            java.lang.reflect.Constructor r1 = r6.getConstructor(r1)     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            java.lang.Object r9 = r1.newInstance(r9)     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            android.view.View r9 = (android.view.View) r9     // Catch: java.lang.Exception -> L29 java.lang.ClassNotFoundException -> L4a
            goto L54
        L40:
            r9 = move-exception
            r6 = r3
        L42:
            java.lang.String r1 = "Unhandled Exception: "
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r1, r9, r0)
        L47:
            r9 = r3
            goto L54
        L49:
            r6 = r3
        L4a:
            java.lang.String r9 = " Class not found Exception: "
            java.lang.String r9 = r2.concat(r9)
            android.util.Log.e(r0, r9)
            goto L47
        L54:
            if (r9 != 0) goto L57
            return r3
        L57:
            com.samsung.android.knox.lockscreen.LSOAttributeSet r1 = r10.getAttrs()
            int r3 = r1.size()
            if (r3 > 0) goto L63
            goto Ld5
        L63:
            r3 = 2
            java.lang.Class[] r7 = new java.lang.Class[r3]     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r7[r4] = r8     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r7[r5] = r8     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.String r8 = "setAttribute"
            java.lang.reflect.Method r6 = r6.getMethod(r8, r7)     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            r7 = 32
            boolean r7 = r10.isFieldUpdated(r7)     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            if (r7 == 0) goto L94
            java.lang.String r7 = "android:gravity"
            r3[r4] = r7     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            int r10 = r10.getGravity()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            r3[r5] = r10     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            r6.invoke(r9, r3)     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            goto L94
        L90:
            r10 = move-exception
            goto Lb8
        L92:
            r10 = move-exception
            goto Lbe
        L94:
            java.util.Set r10 = r1.valueSet()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.util.Iterator r10 = r10.iterator()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
        L9c:
            boolean r1 = r10.hasNext()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            if (r1 == 0) goto Ld5
            java.lang.Object r1 = r10.next()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.Object r7 = r1.getKey()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            r3[r4] = r7     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            java.lang.Object r1 = r1.getValue()     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            r3[r5] = r1     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            r6.invoke(r9, r3)     // Catch: java.lang.Exception -> L90 java.lang.NoSuchMethodException -> L92
            goto L9c
        Lb8:
            java.lang.String r1 = "Exception: "
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r1, r10, r0)
            goto Ld5
        Lbe:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            java.lang.String r2 = " does not support method setAttribute(String,Object) : "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r10 = r1.toString()
            android.util.Log.e(r0, r10)
        Ld5:
            return r9
        Ld6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.lockscreen.LSOWidgetView.getWidget(android.content.Context, com.samsung.android.knox.lockscreen.LSOItemWidget):android.view.View");
    }
}
