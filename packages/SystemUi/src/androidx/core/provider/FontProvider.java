package androidx.core.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.content.res.FontResourcesParserCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontProvider {
    public static final FontProvider$$ExternalSyntheticLambda1 sByteArrayComparator = new FontProvider$$ExternalSyntheticLambda1();

    private FontProvider() {
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0090 A[LOOP:1: B:13:0x004d->B:27:0x0090, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0094 A[EDGE_INSN: B:28:0x0094->B:29:0x0094 BREAK  A[LOOP:1: B:13:0x004d->B:27:0x0090], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FontsContractCompat$FontFamilyResult getFontFamilyResult(Context context, FontRequest fontRequest) {
        Cursor cursor;
        boolean z;
        boolean z2;
        PackageManager packageManager = context.getPackageManager();
        Resources resources = context.getResources();
        String str = fontRequest.mProviderAuthority;
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(str, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException(KeyAttributes$$ExternalSyntheticOutline0.m21m("No package found for authority: ", str));
        }
        String str2 = resolveContentProvider.packageName;
        String str3 = fontRequest.mProviderPackage;
        if (!str2.equals(str3)) {
            throw new PackageManager.NameNotFoundException(FontProvider$$ExternalSyntheticOutline0.m32m("Found content provider ", str, ", but package was not ", str3));
        }
        Signature[] signatureArr = packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        FontProvider$$ExternalSyntheticLambda1 fontProvider$$ExternalSyntheticLambda1 = sByteArrayComparator;
        Collections.sort(arrayList, fontProvider$$ExternalSyntheticLambda1);
        List list = fontRequest.mCertificates;
        if (list == null) {
            list = FontResourcesParserCompat.readCerts(fontRequest.mCertificatesArray, resources);
        }
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                resolveContentProvider = null;
                break;
            }
            ArrayList arrayList2 = new ArrayList((Collection) list.get(i));
            Collections.sort(arrayList2, fontProvider$$ExternalSyntheticLambda1);
            if (arrayList.size() == arrayList2.size()) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (Arrays.equals((byte[]) arrayList.get(i2), (byte[]) arrayList2.get(i2))) {
                    }
                }
                z2 = true;
                if (!z2) {
                    break;
                }
                i++;
            }
            z2 = false;
            if (!z2) {
            }
        }
        if (resolveContentProvider == null) {
            return new FontsContractCompat$FontFamilyResult(1, null);
        }
        String str4 = resolveContentProvider.authority;
        ArrayList arrayList3 = new ArrayList();
        Uri build = new Uri.Builder().scheme("content").authority(str4).build();
        Uri build2 = new Uri.Builder().scheme("content").authority(str4).appendPath("file").build();
        try {
            cursor = context.getContentResolver().query(build, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{fontRequest.mQuery}, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        int columnIndex = cursor.getColumnIndex("result_code");
                        arrayList3 = new ArrayList();
                        int columnIndex2 = cursor.getColumnIndex("_id");
                        int columnIndex3 = cursor.getColumnIndex("file_id");
                        int columnIndex4 = cursor.getColumnIndex("font_ttc_index");
                        int columnIndex5 = cursor.getColumnIndex("font_weight");
                        int columnIndex6 = cursor.getColumnIndex("font_italic");
                        while (cursor.moveToNext()) {
                            int i3 = columnIndex != -1 ? cursor.getInt(columnIndex) : 0;
                            int i4 = columnIndex4 != -1 ? cursor.getInt(columnIndex4) : 0;
                            Uri withAppendedId = columnIndex3 == -1 ? ContentUris.withAppendedId(build, cursor.getLong(columnIndex2)) : ContentUris.withAppendedId(build2, cursor.getLong(columnIndex3));
                            int i5 = columnIndex5 != -1 ? cursor.getInt(columnIndex5) : 400;
                            if (columnIndex6 != -1 && cursor.getInt(columnIndex6) == 1) {
                                z = true;
                                arrayList3.add(new FontsContractCompat$FontInfo(withAppendedId, i4, i5, z, i3));
                            }
                            z = false;
                            arrayList3.add(new FontsContractCompat$FontInfo(withAppendedId, i4, i5, z, i3));
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return new FontsContractCompat$FontFamilyResult(0, (FontsContractCompat$FontInfo[]) arrayList3.toArray(new FontsContractCompat$FontInfo[0]));
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }
}
