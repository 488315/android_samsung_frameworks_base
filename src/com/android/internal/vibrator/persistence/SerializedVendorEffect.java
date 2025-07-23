package com.android.internal.vibrator.persistence;

import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.text.TextUtils;
import android.util.Base64;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes4.dex */
final class SerializedVendorEffect implements XmlSerializedVibration<VibrationEffect.VendorEffect> {
    private final PersistableBundle mVendorData;

    SerializedVendorEffect(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle);
        this.mVendorData = persistableBundle;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public VibrationEffect.VendorEffect deserialize() {
        return (VibrationEffect.VendorEffect) VibrationEffect.createVendorEffect(this.mVendorData);
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void write(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VIBRATION_EFFECT);
        writeContent(typedXmlSerializer);
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VIBRATION_EFFECT);
    }

    @Override // com.android.internal.vibrator.persistence.XmlSerializedVibration
    public void writeContent(TypedXmlSerializer typedXmlSerializer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.mVendorData.writeToStream(byteArrayOutputStream);
        typedXmlSerializer.startTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VENDOR_EFFECT);
        typedXmlSerializer.text(Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2));
        typedXmlSerializer.endTag(XmlConstants.NAMESPACE, XmlConstants.TAG_VENDOR_EFFECT);
    }

    public String toString() {
        return "SerializedVendorEffect{vendorData=" + this.mVendorData + '}';
    }

    static final class Parser {
        Parser() {
        }

        static SerializedVendorEffect parseNext(TypedXmlPullParser typedXmlPullParser, int i) throws XmlParserException, IOException {
            XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_VENDOR_EFFECT);
            XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
            XmlReader.readNextText(typedXmlPullParser, XmlConstants.TAG_VENDOR_EFFECT);
            try {
                String trim = typedXmlPullParser.getText().trim();
                XmlValidator.checkParserCondition(!trim.isEmpty(), "Expected tag %s to have base64 representation of vendor data, got empty", XmlConstants.TAG_VENDOR_EFFECT);
                PersistableBundle readFromStream = PersistableBundle.readFromStream(new ByteArrayInputStream(Base64.decode(trim, 0)));
                XmlValidator.checkParserCondition(!readFromStream.isEmpty(), "Expected tag %s to have non-empty vendor data, got empty bundle", XmlConstants.TAG_VENDOR_EFFECT);
                XmlReader.readEndTag(typedXmlPullParser);
                return new SerializedVendorEffect(readFromStream);
            } catch (IOException e) {
                throw new XmlParserException("Error reading vendor data from decoded bytes", e);
            } catch (IllegalArgumentException | NullPointerException e2) {
                throw new XmlParserException(TextUtils.formatSimple("Expected base64 representation of vendor data in tag %s, got %s", XmlConstants.TAG_VENDOR_EFFECT, typedXmlPullParser.getText()), e2);
            }
        }
    }
}
