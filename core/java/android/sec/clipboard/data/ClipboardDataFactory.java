package android.sec.clipboard.data;

import android.p009os.Parcel;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.content.clipboard.data.SemIntentClipData;
import com.samsung.android.content.clipboard.data.SemTextClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import com.samsung.android.content.clipboard.data.SemUriListClipData;

/* loaded from: classes3.dex */
public class ClipboardDataFactory {
    public static SemClipData createClipBoardData(int format) {
        switch (format) {
            case 1:
                SemClipData result = new SemTextClipData();
                return result;
            case 2:
                SemClipData result2 = new SemImageClipData();
                return result2;
            case 4:
                SemClipData result3 = new SemHtmlClipData();
                return result3;
            case 8:
                SemClipData result4 = new SemIntentClipData();
                return result4;
            case 16:
                SemClipData result5 = new SemUriClipData();
                return result5;
            case 32:
                SemClipData result6 = new SemUriListClipData();
                return result6;
            default:
                return null;
        }
    }

    public static SemClipData createClipBoardData(Parcel source) {
        switch (source.readInt()) {
            case 1:
                SemClipData result = new SemTextClipData(source);
                return result;
            case 2:
                SemClipData result2 = new SemImageClipData(source);
                return result2;
            case 4:
                SemClipData result3 = new SemHtmlClipData(source);
                return result3;
            case 8:
                SemClipData result4 = new SemIntentClipData(source);
                return result4;
            case 16:
                SemClipData result5 = new SemUriClipData(source);
                return result5;
            case 32:
                SemClipData result6 = new SemUriListClipData(source);
                return result6;
            default:
                return null;
        }
    }
}
