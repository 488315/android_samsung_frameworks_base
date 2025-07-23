package android.graphics.fonts;

import android.annotation.SystemApi;
import android.graphics.fonts.FontFamilyUpdateRequest;
import android.os.RemoteException;
import android.text.FontConfig;
import com.android.internal.graphics.fonts.IFontManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public class FontManager {
    public static final int RESULT_ERROR_DOWNGRADING = -5;
    public static final int RESULT_ERROR_FAILED_TO_OPEN_FONT_FILE = -10001;
    public static final int RESULT_ERROR_FAILED_TO_OPEN_SIGNATURE_FILE = -10002;
    public static final int RESULT_ERROR_FAILED_TO_OPEN_XML_FILE = -10006;
    public static final int RESULT_ERROR_FAILED_TO_WRITE_FONT_FILE = -1;
    public static final int RESULT_ERROR_FAILED_UPDATE_CONFIG = -6;
    public static final int RESULT_ERROR_FONT_NOT_FOUND = -9;
    public static final int RESULT_ERROR_FONT_UPDATER_DISABLED = -7;
    public static final int RESULT_ERROR_INVALID_DEBUG_CERTIFICATE = -10008;
    public static final int RESULT_ERROR_INVALID_FONT_FILE = -3;
    public static final int RESULT_ERROR_INVALID_FONT_NAME = -4;
    public static final int RESULT_ERROR_INVALID_SHELL_ARGUMENT = -10003;
    public static final int RESULT_ERROR_INVALID_SIGNATURE_FILE = -10004;
    public static final int RESULT_ERROR_INVALID_XML = -10007;
    public static final int RESULT_ERROR_SIGNATURE_TOO_LARGE = -10005;
    public static final int RESULT_ERROR_VERIFICATION_FAILURE = -2;
    public static final int RESULT_ERROR_VERSION_MISMATCH = -8;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "FontManager";
    private final IFontManager mIFontManager;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    private FontManager(IFontManager iFontManager) {
        this.mIFontManager = iFontManager;
    }

    public FontConfig getFontConfig() {
        try {
            return this.mIFontManager.getFontConfig();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public int updateFontFamily(FontFamilyUpdateRequest fontFamilyUpdateRequest, int i) {
        ArrayList arrayList = new ArrayList();
        List<FontFileUpdateRequest> fontFileUpdateRequests = fontFamilyUpdateRequest.getFontFileUpdateRequests();
        for (int i2 = 0; i2 < fontFileUpdateRequests.size(); i2++) {
            FontFileUpdateRequest fontFileUpdateRequest = fontFileUpdateRequests.get(i2);
            arrayList.add(new FontUpdateRequest(fontFileUpdateRequest.getParcelFileDescriptor(), fontFileUpdateRequest.getSignature()));
        }
        List<FontFamilyUpdateRequest.FontFamily> fontFamilies = fontFamilyUpdateRequest.getFontFamilies();
        for (int i3 = 0; i3 < fontFamilies.size(); i3++) {
            FontFamilyUpdateRequest.FontFamily fontFamily = fontFamilies.get(i3);
            arrayList.add(new FontUpdateRequest(fontFamily.getName(), fontFamily.getFonts()));
        }
        try {
            return this.mIFontManager.updateFontFamily(arrayList, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static FontManager create(IFontManager iFontManager) {
        Objects.requireNonNull(iFontManager);
        return new FontManager(iFontManager);
    }
}
