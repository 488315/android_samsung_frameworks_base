package android.security;

import android.util.Log;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class CredentialManagementApp {
    private static final String KEY_PACKAGE_NAME = "package_name";
    private static final String TAG = "CredentialManagementApp";
    private AppUriAuthenticationPolicy mAuthenticationPolicy;
    private final String mPackageName;

    public CredentialManagementApp(String str, AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(appUriAuthenticationPolicy);
        this.mPackageName = str;
        this.mAuthenticationPolicy = appUriAuthenticationPolicy;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public AppUriAuthenticationPolicy getAuthenticationPolicy() {
        return this.mAuthenticationPolicy;
    }

    public void setAuthenticationPolicy(AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        Objects.requireNonNull(appUriAuthenticationPolicy);
        this.mAuthenticationPolicy = appUriAuthenticationPolicy;
    }

    public static CredentialManagementApp readFromXml(XmlPullParser xmlPullParser) {
        try {
            return new CredentialManagementApp(xmlPullParser.getAttributeValue(null, "package_name"), AppUriAuthenticationPolicy.readFromXml(xmlPullParser));
        } catch (IOException | XmlPullParserException e) {
            Log.w(TAG, "Reading from xml failed", e);
            return null;
        }
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        xmlSerializer.attribute(null, "package_name", this.mPackageName);
        AppUriAuthenticationPolicy appUriAuthenticationPolicy = this.mAuthenticationPolicy;
        if (appUriAuthenticationPolicy != null) {
            appUriAuthenticationPolicy.writeToXml(xmlSerializer);
        }
    }
}
