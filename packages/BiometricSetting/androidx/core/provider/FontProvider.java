package androidx.core.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class FontProvider {
    private static final FontProvider$$ExternalSyntheticLambda0 sByteArrayComparator = new FontProvider$$ExternalSyntheticLambda0();

    /* JADX WARN: Removed duplicated region for block: B:27:0x0097 A[LOOP:1: B:13:0x0054->B:27:0x0097, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009b A[EDGE_INSN: B:28:0x009b->B:29:0x009b BREAK  A[LOOP:1: B:13:0x0054->B:27:0x0097], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0196  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static FontsContractCompat.FontFamilyResult getFontFamilyResult(Context context, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        Cursor cursor;
        ArrayList arrayList;
        boolean z;
        PackageManager packageManager = context.getPackageManager();
        Resources resources = context.getResources();
        String providerAuthority = fontRequest.getProviderAuthority();
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        }
        if (!resolveContentProvider.packageName.equals(fontRequest.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + fontRequest.getProviderPackage());
        }
        Signature[] signatureArr = packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList2 = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList2.add(signature.toByteArray());
        }
        FontProvider$$ExternalSyntheticLambda0 fontProvider$$ExternalSyntheticLambda0 = sByteArrayComparator;
        Collections.sort(arrayList2, fontProvider$$ExternalSyntheticLambda0);
        List<List<byte[]>> certificates = fontRequest.getCertificates() != null ? fontRequest.getCertificates() : FontResourcesParserCompat.readCerts(resources, 0);
        int i = 0;
        while (true) {
            if (i >= certificates.size()) {
                resolveContentProvider = null;
                break;
            }
            ArrayList arrayList3 = new ArrayList(certificates.get(i));
            Collections.sort(arrayList3, fontProvider$$ExternalSyntheticLambda0);
            if (arrayList2.size() == arrayList3.size()) {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    if (Arrays.equals((byte[]) arrayList2.get(i2), (byte[]) arrayList3.get(i2))) {
                    }
                }
                z = true;
                if (!z) {
                    break;
                }
                i++;
            }
            z = false;
            if (!z) {
            }
        }
        if (resolveContentProvider == null) {
            return new FontsContractCompat.FontFamilyResult(1, null);
        }
        String str = resolveContentProvider.authority;
        ArrayList arrayList4 = new ArrayList();
        Uri build = new Uri.Builder().scheme("content").authority(str).build();
        Uri build2 = new Uri.Builder().scheme("content").authority(str).appendPath("file").build();
        try {
            Cursor query = context.getContentResolver().query(build, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{fontRequest.getQuery()}, null, null);
            if (query != null) {
                try {
                    if (query.getCount() > 0) {
                        int columnIndex = query.getColumnIndex("result_code");
                        arrayList = new ArrayList();
                        int columnIndex2 = query.getColumnIndex("_id");
                        int columnIndex3 = query.getColumnIndex("file_id");
                        int columnIndex4 = query.getColumnIndex("font_ttc_index");
                        int columnIndex5 = query.getColumnIndex("font_weight");
                        int columnIndex6 = query.getColumnIndex("font_italic");
                        while (query.moveToNext()) {
                            arrayList.add(new FontsContractCompat.FontInfo(columnIndex3 == -1 ? ContentUris.withAppendedId(build, query.getLong(columnIndex2)) : ContentUris.withAppendedId(build2, query.getLong(columnIndex3)), columnIndex4 != -1 ? query.getInt(columnIndex4) : 0, columnIndex5 != -1 ? query.getInt(columnIndex5) : 400, columnIndex6 != -1 && query.getInt(columnIndex6) == 1, columnIndex != -1 ? query.getInt(columnIndex) : 0));
                        }
                        if (query != null) {
                            query.close();
                        }
                        return new FontsContractCompat.FontFamilyResult(0, (FontsContractCompat.FontInfo[]) arrayList.toArray(new FontsContractCompat.FontInfo[0]));
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            arrayList = arrayList4;
            if (query != null) {
            }
            return new FontsContractCompat.FontFamilyResult(0, (FontsContractCompat.FontInfo[]) arrayList.toArray(new FontsContractCompat.FontInfo[0]));
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }
}
