package com.samsung.android.media;

import android.media.ExifInterface;
import com.android.internal.midi.MidiConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes6.dex */
public class XMLParser {
    static final int PRT = 2;
    static final int TAG = 0;
    static final int VALUE = 1;
    public SemQuramDngJavaCRS crs;
    private DocumentBuilder db;
    private DocumentBuilderFactory dbf;
    private Document doc;
    public SemQuramDngJavaExif exif;
    public SemQuramDngJavaExifPrimitive exif_primitive;
    private InputSource is;
    public Node root;

    public XMLParser() throws ParserConfigurationException {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        this.dbf = newInstance;
        this.db = newInstance.newDocumentBuilder();
        this.is = new InputSource();
        this.exif_primitive = new SemQuramDngJavaExifPrimitive();
        this.exif = new SemQuramDngJavaExif();
        this.crs = new SemQuramDngJavaCRS();
        this.doc = null;
        this.root = null;
    }

    public Document getDom(ByteArrayInputStream byteArrayInputStream) {
        this.is.setByteStream(byteArrayInputStream);
        try {
            this.doc = this.db.parse(this.is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e2) {
            e2.printStackTrace();
        }
        return this.doc;
    }

    public void setRootNode(Document document) {
        if (document == null) {
            return;
        }
        this.root = document.getElementsByTagName("*").item(0);
    }

    public String[][] getChildDescByTagName(String str) {
        String[] strArr;
        String[] strArr2 = null;
        if (str == null) {
            return null;
        }
        int i = 0;
        Node item = this.doc.getElementsByTagName(str).item(0);
        if (item != null) {
            int numberOfChild = getNumberOfChild(item);
            if (numberOfChild == 0) {
                return null;
            }
            String[] strArr3 = new String[numberOfChild];
            strArr = new String[numberOfChild];
            for (Node firstChild = item.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                strArr3[i] = firstChild.getNodeName();
                strArr[i] = firstChild.getTextContent();
                i++;
            }
            strArr2 = strArr3;
        } else {
            strArr = null;
        }
        return new String[][]{strArr2, strArr};
    }

    public int getNumberOfChild(Node node) {
        int i = 0;
        if (!node.hasChildNodes()) {
            return 0;
        }
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            i++;
        }
        return i;
    }

    public String[][] getChildDesc(Node node) {
        String[] strArr;
        String[] strArr2 = null;
        if (node == null) {
            return null;
        }
        if (node != null) {
            int numberOfChild = getNumberOfChild(node);
            if (numberOfChild == 0) {
                return null;
            }
            String[] strArr3 = new String[numberOfChild];
            strArr = new String[numberOfChild];
            int i = 0;
            for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                strArr3[i] = firstChild.getNodeName();
                strArr[i] = firstChild.getTextContent();
                i++;
            }
            strArr2 = strArr3;
        } else {
            strArr = null;
        }
        return new String[][]{strArr2, strArr};
    }

    public boolean getAllDesc(Node node) {
        if (node == null) {
            return false;
        }
        if (node.hasChildNodes()) {
            for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                String nodeName = firstChild.getNodeName();
                if (nodeName != null) {
                    String[] split = nodeName.split(":");
                    String[] strArr = null;
                    if (split[0].compareTo("tiff") == 0 || split[0].compareTo("dc") == 0 || split[0].compareTo("xmp") == 0 || split[0].compareTo("exif") == 0) {
                        String[] strArr2 = new String[2];
                        strArr2[0] = firstChild.getNodeName();
                        if (firstChild.getChildNodes().getLength() > 1) {
                            for (Node firstChild2 = firstChild.getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNextSibling()) {
                                if (firstChild2.getNodeName().equals("rdf:Seq")) {
                                    strArr = getSeqFromNode(firstChild2);
                                }
                            }
                            if (strArr != null) {
                                setDataArrayToExif(strArr2[0], strArr);
                            }
                        } else {
                            strArr2[1] = firstChild.getTextContent();
                            setDataToExif(strArr2);
                        }
                    } else if (split[0].compareTo("crs") == 0) {
                        String[] strArr3 = new String[2];
                        strArr3[0] = firstChild.getNodeName();
                        if (firstChild.getChildNodes().getLength() > 1) {
                            for (Node firstChild3 = firstChild.getFirstChild(); firstChild3 != null; firstChild3 = firstChild3.getNextSibling()) {
                                if (firstChild3.getNodeName().equals("rdf:Seq")) {
                                    strArr = getSeqFromNode(firstChild3);
                                }
                            }
                            if (strArr != null) {
                                setDataArrayToCrs(strArr3[0], strArr);
                            }
                        } else {
                            strArr3[1] = firstChild.getTextContent();
                            setDataToCrs(strArr3);
                        }
                    } else {
                        getAllDesc(firstChild);
                    }
                }
            }
        }
        return true;
    }

    public void setDataToCrs(String[] strArr) {
        String[] split = strArr[0].split(":");
        String str = strArr[1];
        if (str.charAt(0) == '+') {
            str = str.substring(1);
        }
        if (!split[1].equals("AutoBrightness")) {
            if (!split[1].equals("AutoContrast")) {
                if (!split[1].equals("AutoExposure")) {
                    if (!split[1].equals("AutoShadows")) {
                        if (!split[1].equals("HasCrop")) {
                            if (!split[1].equals("HasSettings")) {
                                if (!split[1].equals("ChromaticAberrationB")) {
                                    if (!split[1].equals("ChromaticAberrationR")) {
                                        if (!split[1].equals("ColorNoiseReduction")) {
                                            if (!split[1].equals(ExifInterface.TAG_CONTRAST)) {
                                                if (!split[1].equals("CropUnits")) {
                                                    if (!split[1].equals("GreenHue")) {
                                                        if (!split[1].equals("GreenSaturation")) {
                                                            if (!split[1].equals("LuminanceSmoothing")) {
                                                                if (!split[1].equals("RedHue")) {
                                                                    if (!split[1].equals("RedSaturation")) {
                                                                        if (!split[1].equals(ExifInterface.TAG_SATURATION)) {
                                                                            if (!split[1].equals("Shadows")) {
                                                                                if (!split[1].equals("Shadows")) {
                                                                                    if (!split[1].equals(ExifInterface.TAG_SHARPNESS)) {
                                                                                        if (!split[1].equals("Temperature")) {
                                                                                            if (!split[1].equals("Tint")) {
                                                                                                if (!split[1].equals("VignetteAmount")) {
                                                                                                    if (!split[1].equals("VignetteMidpoint")) {
                                                                                                        if (!split[1].equals("CropTop")) {
                                                                                                            if (!split[1].equals("CropLeft")) {
                                                                                                                if (!split[1].equals("CropBottom")) {
                                                                                                                    if (!split[1].equals("CropRight")) {
                                                                                                                        if (!split[1].equals("CropAngle")) {
                                                                                                                            if (!split[1].equals("CropWidth")) {
                                                                                                                                if (!split[1].equals("CropHeight")) {
                                                                                                                                    if (!split[1].equals("Exposure")) {
                                                                                                                                        if (!split[1].equals("CameraProfile")) {
                                                                                                                                            if (!split[1].equals("RawFileName")) {
                                                                                                                                                if (!split[1].equals("ToneCurveName")) {
                                                                                                                                                    if (!split[1].equals("Version")) {
                                                                                                                                                        if (split[1].equals(ExifInterface.TAG_WHITE_BALANCE)) {
                                                                                                                                                            this.crs.mWhiteBalance = str;
                                                                                                                                                            return;
                                                                                                                                                        }
                                                                                                                                                        return;
                                                                                                                                                    }
                                                                                                                                                    this.crs.mVersion = str;
                                                                                                                                                    return;
                                                                                                                                                }
                                                                                                                                                this.crs.mToneCurveName = str;
                                                                                                                                                return;
                                                                                                                                            }
                                                                                                                                            this.crs.mRawFileName = str;
                                                                                                                                            return;
                                                                                                                                        }
                                                                                                                                        this.crs.mCameraProfile = str;
                                                                                                                                        return;
                                                                                                                                    }
                                                                                                                                    this.crs.mExposure = Double.valueOf(str).doubleValue();
                                                                                                                                    return;
                                                                                                                                }
                                                                                                                                this.crs.mCropHeight = Double.valueOf(str).doubleValue();
                                                                                                                                return;
                                                                                                                            }
                                                                                                                            this.crs.mCropWidth = Double.valueOf(str).doubleValue();
                                                                                                                            return;
                                                                                                                        }
                                                                                                                        this.crs.mCropAngle = Double.valueOf(str).doubleValue();
                                                                                                                        return;
                                                                                                                    }
                                                                                                                    this.crs.mCropRight = Double.valueOf(str).doubleValue();
                                                                                                                    return;
                                                                                                                }
                                                                                                                this.crs.mCropBottom = Double.valueOf(str).doubleValue();
                                                                                                                return;
                                                                                                            }
                                                                                                            this.crs.mCropLeft = Double.valueOf(str).doubleValue();
                                                                                                            return;
                                                                                                        }
                                                                                                        this.crs.mCropTop = Double.valueOf(str).doubleValue();
                                                                                                        return;
                                                                                                    }
                                                                                                    this.crs.mVignetteMidpoint = Integer.valueOf(str).intValue();
                                                                                                    return;
                                                                                                }
                                                                                                this.crs.mVignetteAmount = Integer.valueOf(str).intValue();
                                                                                                return;
                                                                                            }
                                                                                            this.crs.mTint = Integer.valueOf(str).intValue();
                                                                                            return;
                                                                                        }
                                                                                        this.crs.mTemperature = Integer.valueOf(str).intValue();
                                                                                        return;
                                                                                    }
                                                                                    this.crs.mSharpness = Integer.valueOf(str).intValue();
                                                                                    return;
                                                                                }
                                                                                this.crs.mShadows = Integer.valueOf(str).intValue();
                                                                                return;
                                                                            }
                                                                            this.crs.mShadows = Integer.valueOf(str).intValue();
                                                                            return;
                                                                        }
                                                                        this.crs.mSaturation = Integer.valueOf(str).intValue();
                                                                        return;
                                                                    }
                                                                    this.crs.mRedSaturation = Integer.valueOf(str).intValue();
                                                                    return;
                                                                }
                                                                this.crs.mRedHue = Integer.valueOf(str).intValue();
                                                                return;
                                                            }
                                                            this.crs.mLuminanceSmoothing = Integer.valueOf(str).intValue();
                                                            return;
                                                        }
                                                        this.crs.mGreenSaturation = Integer.valueOf(str).intValue();
                                                        return;
                                                    }
                                                    this.crs.mGreenHue = Integer.valueOf(str).intValue();
                                                    return;
                                                }
                                                this.crs.mCropUnits = Integer.valueOf(str).intValue();
                                                return;
                                            }
                                            this.crs.mContrast = Integer.valueOf(str).intValue();
                                            return;
                                        }
                                        this.crs.mColorNoiseReduction = Integer.valueOf(str).intValue();
                                        return;
                                    }
                                    this.crs.mChromaticAberrationR = Integer.valueOf(str).intValue();
                                    return;
                                }
                                this.crs.mChromaticAberrationB = Integer.valueOf(str).intValue();
                                return;
                            }
                            this.crs.mHasSettings = Boolean.valueOf(str).booleanValue();
                            return;
                        }
                        this.crs.mHasCrop = Boolean.valueOf(str).booleanValue();
                        return;
                    }
                    this.crs.mAutoShadows = Boolean.valueOf(str).booleanValue();
                    return;
                }
                this.crs.mAutoExposure = Boolean.valueOf(str).booleanValue();
                return;
            }
            this.crs.mAutoContrast = Boolean.valueOf(str).booleanValue();
            return;
        }
        this.crs.mAutoBrightness = Boolean.valueOf(str).booleanValue();
    }

    public void setDataArrayToCrs(String str, String[] strArr) {
        String[] split = str.split(":");
        this.crs.mToneCurve = new SemQuramDngJavaPoint[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this.crs.mToneCurve[i] = new SemQuramDngJavaPoint();
        }
        if (split[1].equals("ToneCurve")) {
            int i2 = 0;
            for (String str2 : strArr) {
                String[] split2 = str2.split(", ");
                this.crs.mToneCurve[i2].x = Integer.valueOf(split2[0]).intValue();
                this.crs.mToneCurve[i2].y = Integer.valueOf(split2[1]).intValue();
                i2++;
            }
        }
    }

    public void setDataToExif(String[] strArr) {
        String[] split = strArr[0].split(":");
        String str = strArr[1];
        if (str.charAt(0) == '+') {
            str = str.substring(1);
        }
        if (split[0].compareTo("tiff") == 0) {
            if (!split[1].equals(ExifInterface.TAG_MAKE)) {
                if (split[1].equals(ExifInterface.TAG_MODEL)) {
                    this.exif_primitive.mModel = str;
                    return;
                }
                return;
            }
            this.exif_primitive.mMake = str;
            return;
        }
        if (split[0].compareTo("dc") == 0) {
            if (!split[1].equals("description")) {
                if (!split[1].equals("CreatorTool")) {
                    if (split[1].equals("rights")) {
                        this.exif_primitive.mCopyright = str;
                        return;
                    }
                    return;
                }
                this.exif_primitive.mArtist = str;
                return;
            }
            this.exif_primitive.mImageDescription = str;
            return;
        }
        if (split[0].compareTo("xmp") == 0) {
            if (!split[1].equals("ModifyDate")) {
                if (!split[1].equals("CreatorTool")) {
                    if (split[1].equals("CreateDate")) {
                        this.exif_primitive.mDateTimeDigitized = str;
                        return;
                    }
                    return;
                }
                this.exif_primitive.mSoftware = str;
                return;
            }
            this.exif_primitive.mDateTime = str;
            return;
        }
        if (split[0].compareTo("exif") == 0) {
            if (!split[1].equals(ExifInterface.TAG_EXIF_VERSION)) {
                if (!split[1].equals("FlashPixVersion")) {
                    if (!split[1].equals(ExifInterface.TAG_COLOR_SPACE)) {
                        if (!split[1].equals("Gamma")) {
                            if (!split[1].equals(ExifInterface.TAG_COMPONENTS_CONFIGURATION)) {
                                if (!split[1].equals(ExifInterface.TAG_PIXEL_X_DIMENSION)) {
                                    if (!split[1].equals(ExifInterface.TAG_PIXEL_Y_DIMENSION)) {
                                        if (!split[1].equals(ExifInterface.TAG_USER_COMMENT)) {
                                            if (!split[1].equals(ExifInterface.TAG_DATETIME_ORIGINAL)) {
                                                if (!split[1].equals(ExifInterface.TAG_EXPOSURE_TIME)) {
                                                    if (!split[1].equals("FNumber")) {
                                                        if (!split[1].equals(ExifInterface.TAG_EXPOSURE_PROGRAM)) {
                                                            if (!split[1].equals("SensitivityType")) {
                                                                if (!split[1].equals("StandardOutputSensitivity")) {
                                                                    if (!split[1].equals("RecommendedExposureIndex")) {
                                                                        if (!split[1].equals("ISOSpeed")) {
                                                                            if (!split[1].equals("ISOSpeedLatitudeyyy")) {
                                                                                if (!split[1].equals("ISOSpeedLatitudezzz")) {
                                                                                    if (!split[1].equals(ExifInterface.TAG_SHUTTER_SPEED_VALUE)) {
                                                                                        if (!split[1].equals(ExifInterface.TAG_APERTURE_VALUE)) {
                                                                                            if (!split[1].equals(ExifInterface.TAG_BRIGHTNESS_VALUE)) {
                                                                                                if (!split[1].equals(ExifInterface.TAG_EXPOSURE_BIAS_VALUE)) {
                                                                                                    if (!split[1].equals(ExifInterface.TAG_MAX_APERTURE_VALUE)) {
                                                                                                        if (!split[1].equals(ExifInterface.TAG_SUBJECT_DISTANCE)) {
                                                                                                            if (!split[1].equals(ExifInterface.TAG_METERING_MODE)) {
                                                                                                                if (!split[1].equals(ExifInterface.TAG_LIGHT_SOURCE)) {
                                                                                                                    if (!split[1].equals(ExifInterface.TAG_FLASH)) {
                                                                                                                        if (!split[1].equals(ExifInterface.TAG_FOCAL_LENGTH)) {
                                                                                                                            if (!split[1].equals(ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION)) {
                                                                                                                                if (!split[1].equals(ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION)) {
                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT)) {
                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_EXPOSURE_INDEX)) {
                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_SENSING_METHOD)) {
                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_FILE_SOURCE)) {
                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_SCENE_TYPE)) {
                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_CUSTOM_RENDERED)) {
                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_EXPOSURE_MODE)) {
                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_WHITE_BALANCE)) {
                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_DIGITAL_ZOOM_RATIO)) {
                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM)) {
                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_SCENE_CAPTURE_TYPE)) {
                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GAIN_CONTROL)) {
                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_CONTRAST)) {
                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_SATURATION)) {
                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_SHARPNESS)) {
                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_SUBJECT_DISTANCE_RANGE)) {
                                                                                                                                                                                                    if (!split[1].equals("LensMake")) {
                                                                                                                                                                                                        if (!split[1].equals("LensSerialNumber")) {
                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_VERSION_ID)) {
                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_LATITUDE_REF)) {
                                                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_GPS_LONGITUDE_REF)) {
                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_GPS_ALTITUDE_REF)) {
                                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_ALTITUDE)) {
                                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_SATELLITES)) {
                                                                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_GPS_STATUS)) {
                                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_GPS_MEASURE_MODE)) {
                                                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_DOP)) {
                                                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_SPEED_REF)) {
                                                                                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_GPS_SPEED)) {
                                                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_GPS_TRACK_REF)) {
                                                                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_TRACK)) {
                                                                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_IMG_DIRECTION_REF)) {
                                                                                                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_GPS_IMG_DIRECTION)) {
                                                                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_GPS_MAP_DATUM)) {
                                                                                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_DEST_LATITUDE_REF)) {
                                                                                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF)) {
                                                                                                                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_GPS_DEST_BEARING_REF)) {
                                                                                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_GPS_DEST_BEARING)) {
                                                                                                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_DEST_DISTANCE_REF)) {
                                                                                                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_DEST_DISTANCE)) {
                                                                                                                                                                                                                                                                                                    if (!split[1].equals(ExifInterface.TAG_GPS_PROCESSING_METHOD)) {
                                                                                                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_GPS_AREA_INFORMATION)) {
                                                                                                                                                                                                                                                                                                            if (!split[1].equals(ExifInterface.TAG_GPS_DATESTAMP)) {
                                                                                                                                                                                                                                                                                                                if (!split[1].equals(ExifInterface.TAG_GPS_DIFFERENTIAL)) {
                                                                                                                                                                                                                                                                                                                    if (!split[1].equals("GPSHPositioningError")) {
                                                                                                                                                                                                                                                                                                                        if (!split[1].equals(ExifInterface.TAG_INTEROPERABILITY_INDEX)) {
                                                                                                                                                                                                                                                                                                                            if (!split[1].equals("Columns")) {
                                                                                                                                                                                                                                                                                                                                if (!split[1].equals("Rows")) {
                                                                                                                                                                                                                                                                                                                                    if (split[1].equals(ExifInterface.TAG_IMAGE_UNIQUE_ID)) {
                                                                                                                                                                                                                                                                                                                                        for (int i = 0; i < str.length() / 2; i++) {
                                                                                                                                                                                                                                                                                                                                            int i2 = i * 2;
                                                                                                                                                                                                                                                                                                                                            byte b = str.getBytes()[i2];
                                                                                                                                                                                                                                                                                                                                            byte b2 = str.getBytes()[i2 + 1];
                                                                                                                                                                                                                                                                                                                                            this.exif_primitive.mImageUniqueID[i] = (byte) ((((byte) ((b < 65 || b > 70) ? b + MidiConstants.STATUS_CHANNEL_PRESSURE : b - 55)) << 4) + ((byte) ((b2 < 65 || b2 > 70) ? b2 + MidiConstants.STATUS_CHANNEL_PRESSURE : b2 - 55)));
                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                        this.exif_primitive.mImageUniqueIDExist = true;
                                                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    if (split[1].equals(ExifInterface.TAG_DATETIME_DIGITIZED)) {
                                                                                                                                                                                                                                                                                                                                        this.exif_primitive.mDateTimeDigitized = str;
                                                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                this.exif_primitive.mCFARepeatPatternRows = Long.valueOf(str).longValue();
                                                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                            this.exif_primitive.mCFARepeatPatternRows = Long.valueOf(str).longValue();
                                                                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                        this.exif_primitive.mInteroperabilityIndex = str;
                                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSHPositioningError = str;
                                                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                this.exif_primitive.mGPSDifferential = Long.valueOf(str).longValue();
                                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                            this.exif_primitive.mGPSDateStamp = str;
                                                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                        this.exif_primitive.mGPSAreaInformation = str;
                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSProcessingMethod = str;
                                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                this.exif_primitive.mGPSDestDistance = str;
                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                            this.exif_primitive.mGPSDestDistanceRef = str;
                                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                        this.exif_primitive.mGPSDestBearing = str;
                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSDestBearingRef = str;
                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                this.exif_primitive.mGPSDestLongitudeRef = str;
                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            this.exif_primitive.mGPSDestLatitudeRef = str;
                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        this.exif_primitive.mGPSMapDatum = str;
                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSImgDirection = str;
                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                this.exif_primitive.mGPSImgDirectionRef = str;
                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            this.exif_primitive.mGPSTrack = str;
                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                        this.exif_primitive.mGPSTrackRef = str;
                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                    this.exif_primitive.mGPSSpeed = str;
                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                this.exif_primitive.mGPSSpeedRef = str;
                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                            this.exif_primitive.mGPSDOP = str;
                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        this.exif_primitive.mGPSMeasureMode = str;
                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                    this.exif_primitive.mGPSStatus = str;
                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                this.exif_primitive.mGPSSatellites = str;
                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            this.exif_primitive.mGPSAltitude = str;
                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        this.exif_primitive.mGPSAltitudeRef = Long.valueOf(str).longValue();
                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                    this.exif_primitive.mGPSLongitudeRef = str;
                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                this.exif_primitive.mGPSLatitudeRef = str;
                                                                                                                                                                                                                return;
                                                                                                                                                                                                            }
                                                                                                                                                                                                            this.exif_primitive.mGPSVersionID = Long.valueOf(str).longValue();
                                                                                                                                                                                                            return;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        this.exif_primitive.mLensSerialNumber = str;
                                                                                                                                                                                                        return;
                                                                                                                                                                                                    }
                                                                                                                                                                                                    this.exif_primitive.mLensMake = str;
                                                                                                                                                                                                    return;
                                                                                                                                                                                                }
                                                                                                                                                                                                this.exif_primitive.mSubjectDistanceRange = Long.valueOf(str).longValue();
                                                                                                                                                                                                return;
                                                                                                                                                                                            }
                                                                                                                                                                                            this.exif_primitive.mSharpness = Long.valueOf(str).longValue();
                                                                                                                                                                                            return;
                                                                                                                                                                                        }
                                                                                                                                                                                        this.exif_primitive.mSaturation = Long.valueOf(str).longValue();
                                                                                                                                                                                        return;
                                                                                                                                                                                    }
                                                                                                                                                                                    this.exif_primitive.mContrast = Long.valueOf(str).longValue();
                                                                                                                                                                                    return;
                                                                                                                                                                                }
                                                                                                                                                                                this.exif_primitive.mGainControl = Long.valueOf(str).longValue();
                                                                                                                                                                                return;
                                                                                                                                                                            }
                                                                                                                                                                            this.exif_primitive.mSceneCaptureType = Long.valueOf(str).longValue();
                                                                                                                                                                            return;
                                                                                                                                                                        }
                                                                                                                                                                        this.exif_primitive.mFocalLengthIn35mmFilm = Long.valueOf(str).longValue();
                                                                                                                                                                        return;
                                                                                                                                                                    }
                                                                                                                                                                    this.exif_primitive.mDigitalZoomRatio = str;
                                                                                                                                                                    return;
                                                                                                                                                                }
                                                                                                                                                                this.exif_primitive.mWhiteBalance = Long.valueOf(str).longValue();
                                                                                                                                                                return;
                                                                                                                                                            }
                                                                                                                                                            this.exif_primitive.mExposureMode = Long.valueOf(str).longValue();
                                                                                                                                                            return;
                                                                                                                                                        }
                                                                                                                                                        this.exif_primitive.mCustomRendered = Long.valueOf(str).longValue();
                                                                                                                                                        return;
                                                                                                                                                    }
                                                                                                                                                    this.exif_primitive.mSceneType = Long.valueOf(str).longValue();
                                                                                                                                                    return;
                                                                                                                                                }
                                                                                                                                                this.exif_primitive.mFileSource = Long.valueOf(str).longValue();
                                                                                                                                                return;
                                                                                                                                            }
                                                                                                                                            this.exif_primitive.mSensingMethod = Long.valueOf(str).longValue();
                                                                                                                                            return;
                                                                                                                                        }
                                                                                                                                        this.exif_primitive.mExposureIndex = str;
                                                                                                                                        return;
                                                                                                                                    }
                                                                                                                                    this.exif_primitive.mFocalPlaneResolutionUnit = Long.valueOf(str).longValue();
                                                                                                                                    return;
                                                                                                                                }
                                                                                                                                this.exif_primitive.mFocalPlaneYResolution = str;
                                                                                                                                return;
                                                                                                                            }
                                                                                                                            this.exif_primitive.mFocalPlaneXResolution = str;
                                                                                                                            return;
                                                                                                                        }
                                                                                                                        this.exif_primitive.mFocalLength = str;
                                                                                                                        return;
                                                                                                                    }
                                                                                                                    this.exif_primitive.mFlash = Long.valueOf(str).longValue();
                                                                                                                    return;
                                                                                                                }
                                                                                                                this.exif_primitive.mLightSource = Long.valueOf(str).longValue();
                                                                                                                return;
                                                                                                            }
                                                                                                            this.exif_primitive.mMeteringMode = Long.valueOf(str).longValue();
                                                                                                            return;
                                                                                                        }
                                                                                                        this.exif_primitive.mSubjectDistance = str;
                                                                                                        return;
                                                                                                    }
                                                                                                    this.exif_primitive.mMaxApertureValue = str;
                                                                                                    return;
                                                                                                }
                                                                                                this.exif_primitive.mExposureBiasValue = str;
                                                                                                return;
                                                                                            }
                                                                                            this.exif_primitive.mBrightnessValue = str;
                                                                                            return;
                                                                                        }
                                                                                        this.exif_primitive.mApertureValue = str;
                                                                                        return;
                                                                                    }
                                                                                    this.exif_primitive.mShutterSpeedValue = str;
                                                                                    return;
                                                                                }
                                                                                this.exif_primitive.mISOSpeedLatitudezzz = Long.valueOf(str).longValue();
                                                                                return;
                                                                            }
                                                                            this.exif_primitive.mISOSpeedLatitudeyyy = Long.valueOf(str).longValue();
                                                                            return;
                                                                        }
                                                                        this.exif_primitive.mISOSpeed = Long.valueOf(str).longValue();
                                                                        return;
                                                                    }
                                                                    this.exif_primitive.mRecommendedExposureIndex = Long.valueOf(str).longValue();
                                                                    return;
                                                                }
                                                                this.exif_primitive.mStandardOutputSensitivity = Long.valueOf(str).longValue();
                                                                return;
                                                            }
                                                            this.exif_primitive.mSensitivityType = Long.valueOf(str).longValue();
                                                            return;
                                                        }
                                                        this.exif_primitive.mExposureProgram = Long.valueOf(str).longValue();
                                                        return;
                                                    }
                                                    this.exif_primitive.mFNumber = str;
                                                    return;
                                                }
                                                this.exif_primitive.mExposureTime = str;
                                                return;
                                            }
                                            this.exif_primitive.mDateTimeOriginal = str;
                                            return;
                                        }
                                        this.exif_primitive.mUserComment = str;
                                        return;
                                    }
                                    this.exif_primitive.mPixelYDimension = Long.valueOf(str).longValue();
                                    return;
                                }
                                this.exif_primitive.mPixelXDimension = Long.valueOf(str).longValue();
                                return;
                            }
                            this.exif_primitive.mComponentsConfiguration = Long.valueOf(str).longValue();
                            return;
                        }
                        this.exif_primitive.mGamma = str;
                        return;
                    }
                    this.exif_primitive.mColorSpace = Long.valueOf(str).longValue();
                    return;
                }
                this.exif_primitive.mFlashPixVersion = Long.valueOf(str).longValue();
                return;
            }
            this.exif_primitive.mExifVersion = Long.valueOf(str).longValue();
            return;
        }
        if (split[0].compareTo("exifEX") == 0) {
            if (!split[1].equals("CameraOwnerName")) {
                if (!split[1].equals("BodySerialNumber")) {
                    if (split[1].equals("LensModel")) {
                        this.exif_primitive.mLensName = str;
                        return;
                    }
                    return;
                }
                this.exif_primitive.mCameraSerialNumber = str;
                return;
            }
            this.exif_primitive.mOwnerName = str;
        }
    }

    public boolean setDataArrayToExif(String str, String[] strArr) {
        String[] split = str.split(":");
        if (split[0].compareTo("exif") != 0) {
            return false;
        }
        if (split[1].equals("ISOSpeedRatings")) {
            this.exif_primitive.mISOSpeedRatings0 = strArr.length >= 1 ? Long.valueOf(strArr[0]).longValue() : 0L;
            this.exif_primitive.mISOSpeedRatings1 = strArr.length >= 2 ? Long.valueOf(strArr[1]).longValue() : 0L;
            this.exif_primitive.mISOSpeedRatings2 = strArr.length >= 3 ? Long.valueOf(strArr[2]).longValue() : 0L;
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_SUBJECT_AREA)) {
            this.exif_primitive.mSubjectArea0 = strArr.length >= 1 ? Long.valueOf(strArr[0]).longValue() : 0L;
            this.exif_primitive.mSubjectArea1 = strArr.length >= 2 ? Long.valueOf(strArr[1]).longValue() : 0L;
            this.exif_primitive.mSubjectArea2 = strArr.length >= 3 ? Long.valueOf(strArr[2]).longValue() : 0L;
            this.exif_primitive.mSubjectArea3 = strArr.length >= 4 ? Long.valueOf(strArr[3]).longValue() : 0L;
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_CFA_PATTERN)) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                this.exif_primitive.mCFAPattern[i] = Byte.valueOf(strArr[i]).byteValue();
            }
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_GPS_LATITUDE)) {
            this.exif_primitive.mGPSLatitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSLatitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSLatitude2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_GPS_LONGITUDE)) {
            this.exif_primitive.mGPSLongitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSLongitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSLongitude2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_GPS_TIMESTAMP)) {
            this.exif_primitive.mGPSTimeStamp0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSTimeStamp1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSTimeStamp2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_GPS_DEST_LATITUDE)) {
            this.exif_primitive.mGPSDestLatitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSDestLatitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSDestLatitude2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (split[1].equals(ExifInterface.TAG_GPS_DEST_LONGITUDE)) {
            this.exif_primitive.mGPSDestLongitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSDestLongitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSDestLongitude2 = strArr.length >= 3 ? strArr[2] : null;
        }
        return true;
    }

    public String[] getSeqFromNode(Node node) {
        if (node == null) {
            return null;
        }
        String[] strArr = new String[(node.getChildNodes().getLength() - 1) / 2];
        int i = 0;
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild.getNodeName().equals("rdf:li")) {
                strArr[i] = firstChild.getTextContent();
                i++;
            }
        }
        return strArr;
    }
}
