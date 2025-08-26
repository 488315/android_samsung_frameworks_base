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
        DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
        this.dbf = documentBuilderFactoryNewInstance;
        this.db = documentBuilderFactoryNewInstance.newDocumentBuilder();
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
        Node nodeItem = this.doc.getElementsByTagName(str).item(0);
        if (nodeItem != null) {
            int numberOfChild = getNumberOfChild(nodeItem);
            if (numberOfChild == 0) {
                return null;
            }
            String[] strArr3 = new String[numberOfChild];
            strArr = new String[numberOfChild];
            for (Node firstChild = nodeItem.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
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
                    String[] strArrSplit = nodeName.split(":");
                    String[] seqFromNode = null;
                    if (strArrSplit[0].compareTo("tiff") == 0 || strArrSplit[0].compareTo("dc") == 0 || strArrSplit[0].compareTo("xmp") == 0 || strArrSplit[0].compareTo("exif") == 0) {
                        String[] strArr = new String[2];
                        strArr[0] = firstChild.getNodeName();
                        if (firstChild.getChildNodes().getLength() > 1) {
                            for (Node firstChild2 = firstChild.getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNextSibling()) {
                                if (firstChild2.getNodeName().equals("rdf:Seq")) {
                                    seqFromNode = getSeqFromNode(firstChild2);
                                }
                            }
                            if (seqFromNode != null) {
                                setDataArrayToExif(strArr[0], seqFromNode);
                            }
                        } else {
                            strArr[1] = firstChild.getTextContent();
                            setDataToExif(strArr);
                        }
                    } else if (strArrSplit[0].compareTo("crs") == 0) {
                        String[] strArr2 = new String[2];
                        strArr2[0] = firstChild.getNodeName();
                        if (firstChild.getChildNodes().getLength() > 1) {
                            for (Node firstChild3 = firstChild.getFirstChild(); firstChild3 != null; firstChild3 = firstChild3.getNextSibling()) {
                                if (firstChild3.getNodeName().equals("rdf:Seq")) {
                                    seqFromNode = getSeqFromNode(firstChild3);
                                }
                            }
                            if (seqFromNode != null) {
                                setDataArrayToCrs(strArr2[0], seqFromNode);
                            }
                        } else {
                            strArr2[1] = firstChild.getTextContent();
                            setDataToCrs(strArr2);
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
        String[] strArrSplit = strArr[0].split(":");
        String strSubstring = strArr[1];
        if (strSubstring.charAt(0) == '+') {
            strSubstring = strSubstring.substring(1);
        }
        if (!strArrSplit[1].equals("AutoBrightness")) {
            if (!strArrSplit[1].equals("AutoContrast")) {
                if (!strArrSplit[1].equals("AutoExposure")) {
                    if (!strArrSplit[1].equals("AutoShadows")) {
                        if (!strArrSplit[1].equals("HasCrop")) {
                            if (!strArrSplit[1].equals("HasSettings")) {
                                if (!strArrSplit[1].equals("ChromaticAberrationB")) {
                                    if (!strArrSplit[1].equals("ChromaticAberrationR")) {
                                        if (!strArrSplit[1].equals("ColorNoiseReduction")) {
                                            if (!strArrSplit[1].equals(ExifInterface.TAG_CONTRAST)) {
                                                if (!strArrSplit[1].equals("CropUnits")) {
                                                    if (!strArrSplit[1].equals("GreenHue")) {
                                                        if (!strArrSplit[1].equals("GreenSaturation")) {
                                                            if (!strArrSplit[1].equals("LuminanceSmoothing")) {
                                                                if (!strArrSplit[1].equals("RedHue")) {
                                                                    if (!strArrSplit[1].equals("RedSaturation")) {
                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_SATURATION)) {
                                                                            if (!strArrSplit[1].equals("Shadows")) {
                                                                                if (!strArrSplit[1].equals("Shadows")) {
                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_SHARPNESS)) {
                                                                                        if (!strArrSplit[1].equals("Temperature")) {
                                                                                            if (!strArrSplit[1].equals("Tint")) {
                                                                                                if (!strArrSplit[1].equals("VignetteAmount")) {
                                                                                                    if (!strArrSplit[1].equals("VignetteMidpoint")) {
                                                                                                        if (!strArrSplit[1].equals("CropTop")) {
                                                                                                            if (!strArrSplit[1].equals("CropLeft")) {
                                                                                                                if (!strArrSplit[1].equals("CropBottom")) {
                                                                                                                    if (!strArrSplit[1].equals("CropRight")) {
                                                                                                                        if (!strArrSplit[1].equals("CropAngle")) {
                                                                                                                            if (!strArrSplit[1].equals("CropWidth")) {
                                                                                                                                if (!strArrSplit[1].equals("CropHeight")) {
                                                                                                                                    if (!strArrSplit[1].equals("Exposure")) {
                                                                                                                                        if (!strArrSplit[1].equals("CameraProfile")) {
                                                                                                                                            if (!strArrSplit[1].equals("RawFileName")) {
                                                                                                                                                if (!strArrSplit[1].equals("ToneCurveName")) {
                                                                                                                                                    if (!strArrSplit[1].equals("Version")) {
                                                                                                                                                        if (strArrSplit[1].equals(ExifInterface.TAG_WHITE_BALANCE)) {
                                                                                                                                                            this.crs.mWhiteBalance = strSubstring;
                                                                                                                                                            return;
                                                                                                                                                        }
                                                                                                                                                        return;
                                                                                                                                                    }
                                                                                                                                                    this.crs.mVersion = strSubstring;
                                                                                                                                                    return;
                                                                                                                                                }
                                                                                                                                                this.crs.mToneCurveName = strSubstring;
                                                                                                                                                return;
                                                                                                                                            }
                                                                                                                                            this.crs.mRawFileName = strSubstring;
                                                                                                                                            return;
                                                                                                                                        }
                                                                                                                                        this.crs.mCameraProfile = strSubstring;
                                                                                                                                        return;
                                                                                                                                    }
                                                                                                                                    this.crs.mExposure = Double.valueOf(strSubstring).doubleValue();
                                                                                                                                    return;
                                                                                                                                }
                                                                                                                                this.crs.mCropHeight = Double.valueOf(strSubstring).doubleValue();
                                                                                                                                return;
                                                                                                                            }
                                                                                                                            this.crs.mCropWidth = Double.valueOf(strSubstring).doubleValue();
                                                                                                                            return;
                                                                                                                        }
                                                                                                                        this.crs.mCropAngle = Double.valueOf(strSubstring).doubleValue();
                                                                                                                        return;
                                                                                                                    }
                                                                                                                    this.crs.mCropRight = Double.valueOf(strSubstring).doubleValue();
                                                                                                                    return;
                                                                                                                }
                                                                                                                this.crs.mCropBottom = Double.valueOf(strSubstring).doubleValue();
                                                                                                                return;
                                                                                                            }
                                                                                                            this.crs.mCropLeft = Double.valueOf(strSubstring).doubleValue();
                                                                                                            return;
                                                                                                        }
                                                                                                        this.crs.mCropTop = Double.valueOf(strSubstring).doubleValue();
                                                                                                        return;
                                                                                                    }
                                                                                                    this.crs.mVignetteMidpoint = Integer.valueOf(strSubstring).intValue();
                                                                                                    return;
                                                                                                }
                                                                                                this.crs.mVignetteAmount = Integer.valueOf(strSubstring).intValue();
                                                                                                return;
                                                                                            }
                                                                                            this.crs.mTint = Integer.valueOf(strSubstring).intValue();
                                                                                            return;
                                                                                        }
                                                                                        this.crs.mTemperature = Integer.valueOf(strSubstring).intValue();
                                                                                        return;
                                                                                    }
                                                                                    this.crs.mSharpness = Integer.valueOf(strSubstring).intValue();
                                                                                    return;
                                                                                }
                                                                                this.crs.mShadows = Integer.valueOf(strSubstring).intValue();
                                                                                return;
                                                                            }
                                                                            this.crs.mShadows = Integer.valueOf(strSubstring).intValue();
                                                                            return;
                                                                        }
                                                                        this.crs.mSaturation = Integer.valueOf(strSubstring).intValue();
                                                                        return;
                                                                    }
                                                                    this.crs.mRedSaturation = Integer.valueOf(strSubstring).intValue();
                                                                    return;
                                                                }
                                                                this.crs.mRedHue = Integer.valueOf(strSubstring).intValue();
                                                                return;
                                                            }
                                                            this.crs.mLuminanceSmoothing = Integer.valueOf(strSubstring).intValue();
                                                            return;
                                                        }
                                                        this.crs.mGreenSaturation = Integer.valueOf(strSubstring).intValue();
                                                        return;
                                                    }
                                                    this.crs.mGreenHue = Integer.valueOf(strSubstring).intValue();
                                                    return;
                                                }
                                                this.crs.mCropUnits = Integer.valueOf(strSubstring).intValue();
                                                return;
                                            }
                                            this.crs.mContrast = Integer.valueOf(strSubstring).intValue();
                                            return;
                                        }
                                        this.crs.mColorNoiseReduction = Integer.valueOf(strSubstring).intValue();
                                        return;
                                    }
                                    this.crs.mChromaticAberrationR = Integer.valueOf(strSubstring).intValue();
                                    return;
                                }
                                this.crs.mChromaticAberrationB = Integer.valueOf(strSubstring).intValue();
                                return;
                            }
                            this.crs.mHasSettings = Boolean.valueOf(strSubstring).booleanValue();
                            return;
                        }
                        this.crs.mHasCrop = Boolean.valueOf(strSubstring).booleanValue();
                        return;
                    }
                    this.crs.mAutoShadows = Boolean.valueOf(strSubstring).booleanValue();
                    return;
                }
                this.crs.mAutoExposure = Boolean.valueOf(strSubstring).booleanValue();
                return;
            }
            this.crs.mAutoContrast = Boolean.valueOf(strSubstring).booleanValue();
            return;
        }
        this.crs.mAutoBrightness = Boolean.valueOf(strSubstring).booleanValue();
    }

    public void setDataArrayToCrs(String str, String[] strArr) {
        String[] strArrSplit = str.split(":");
        this.crs.mToneCurve = new SemQuramDngJavaPoint[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this.crs.mToneCurve[i] = new SemQuramDngJavaPoint();
        }
        if (strArrSplit[1].equals("ToneCurve")) {
            int i2 = 0;
            for (String str2 : strArr) {
                String[] strArrSplit2 = str2.split(", ");
                this.crs.mToneCurve[i2].x = Integer.valueOf(strArrSplit2[0]).intValue();
                this.crs.mToneCurve[i2].y = Integer.valueOf(strArrSplit2[1]).intValue();
                i2++;
            }
        }
    }

    public void setDataToExif(String[] strArr) {
        String[] strArrSplit = strArr[0].split(":");
        String strSubstring = strArr[1];
        if (strSubstring.charAt(0) == '+') {
            strSubstring = strSubstring.substring(1);
        }
        if (strArrSplit[0].compareTo("tiff") == 0) {
            if (!strArrSplit[1].equals(ExifInterface.TAG_MAKE)) {
                if (strArrSplit[1].equals(ExifInterface.TAG_MODEL)) {
                    this.exif_primitive.mModel = strSubstring;
                    return;
                }
                return;
            }
            this.exif_primitive.mMake = strSubstring;
            return;
        }
        if (strArrSplit[0].compareTo("dc") == 0) {
            if (!strArrSplit[1].equals("description")) {
                if (!strArrSplit[1].equals("CreatorTool")) {
                    if (strArrSplit[1].equals("rights")) {
                        this.exif_primitive.mCopyright = strSubstring;
                        return;
                    }
                    return;
                }
                this.exif_primitive.mArtist = strSubstring;
                return;
            }
            this.exif_primitive.mImageDescription = strSubstring;
            return;
        }
        if (strArrSplit[0].compareTo("xmp") == 0) {
            if (!strArrSplit[1].equals("ModifyDate")) {
                if (!strArrSplit[1].equals("CreatorTool")) {
                    if (strArrSplit[1].equals("CreateDate")) {
                        this.exif_primitive.mDateTimeDigitized = strSubstring;
                        return;
                    }
                    return;
                }
                this.exif_primitive.mSoftware = strSubstring;
                return;
            }
            this.exif_primitive.mDateTime = strSubstring;
            return;
        }
        if (strArrSplit[0].compareTo("exif") == 0) {
            if (!strArrSplit[1].equals(ExifInterface.TAG_EXIF_VERSION)) {
                if (!strArrSplit[1].equals("FlashPixVersion")) {
                    if (!strArrSplit[1].equals(ExifInterface.TAG_COLOR_SPACE)) {
                        if (!strArrSplit[1].equals("Gamma")) {
                            if (!strArrSplit[1].equals(ExifInterface.TAG_COMPONENTS_CONFIGURATION)) {
                                if (!strArrSplit[1].equals(ExifInterface.TAG_PIXEL_X_DIMENSION)) {
                                    if (!strArrSplit[1].equals(ExifInterface.TAG_PIXEL_Y_DIMENSION)) {
                                        if (!strArrSplit[1].equals(ExifInterface.TAG_USER_COMMENT)) {
                                            if (!strArrSplit[1].equals(ExifInterface.TAG_DATETIME_ORIGINAL)) {
                                                if (!strArrSplit[1].equals(ExifInterface.TAG_EXPOSURE_TIME)) {
                                                    if (!strArrSplit[1].equals("FNumber")) {
                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_EXPOSURE_PROGRAM)) {
                                                            if (!strArrSplit[1].equals("SensitivityType")) {
                                                                if (!strArrSplit[1].equals("StandardOutputSensitivity")) {
                                                                    if (!strArrSplit[1].equals("RecommendedExposureIndex")) {
                                                                        if (!strArrSplit[1].equals("ISOSpeed")) {
                                                                            if (!strArrSplit[1].equals("ISOSpeedLatitudeyyy")) {
                                                                                if (!strArrSplit[1].equals("ISOSpeedLatitudezzz")) {
                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_SHUTTER_SPEED_VALUE)) {
                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_APERTURE_VALUE)) {
                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_BRIGHTNESS_VALUE)) {
                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_EXPOSURE_BIAS_VALUE)) {
                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_MAX_APERTURE_VALUE)) {
                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_SUBJECT_DISTANCE)) {
                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_METERING_MODE)) {
                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_LIGHT_SOURCE)) {
                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_FLASH)) {
                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_FOCAL_LENGTH)) {
                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION)) {
                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION)) {
                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT)) {
                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_EXPOSURE_INDEX)) {
                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_SENSING_METHOD)) {
                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_FILE_SOURCE)) {
                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_SCENE_TYPE)) {
                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_CUSTOM_RENDERED)) {
                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_EXPOSURE_MODE)) {
                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_WHITE_BALANCE)) {
                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_DIGITAL_ZOOM_RATIO)) {
                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM)) {
                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_SCENE_CAPTURE_TYPE)) {
                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GAIN_CONTROL)) {
                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_CONTRAST)) {
                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_SATURATION)) {
                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_SHARPNESS)) {
                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_SUBJECT_DISTANCE_RANGE)) {
                                                                                                                                                                                                    if (!strArrSplit[1].equals("LensMake")) {
                                                                                                                                                                                                        if (!strArrSplit[1].equals("LensSerialNumber")) {
                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_VERSION_ID)) {
                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_LATITUDE_REF)) {
                                                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_LONGITUDE_REF)) {
                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_ALTITUDE_REF)) {
                                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_ALTITUDE)) {
                                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_SATELLITES)) {
                                                                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_STATUS)) {
                                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_MEASURE_MODE)) {
                                                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DOP)) {
                                                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_SPEED_REF)) {
                                                                                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_SPEED)) {
                                                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_TRACK_REF)) {
                                                                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_TRACK)) {
                                                                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_IMG_DIRECTION_REF)) {
                                                                                                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_IMG_DIRECTION)) {
                                                                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_MAP_DATUM)) {
                                                                                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_LATITUDE_REF)) {
                                                                                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF)) {
                                                                                                                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_BEARING_REF)) {
                                                                                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_BEARING)) {
                                                                                                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_DISTANCE_REF)) {
                                                                                                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_DISTANCE)) {
                                                                                                                                                                                                                                                                                                    if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_PROCESSING_METHOD)) {
                                                                                                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_AREA_INFORMATION)) {
                                                                                                                                                                                                                                                                                                            if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DATESTAMP)) {
                                                                                                                                                                                                                                                                                                                if (!strArrSplit[1].equals(ExifInterface.TAG_GPS_DIFFERENTIAL)) {
                                                                                                                                                                                                                                                                                                                    if (!strArrSplit[1].equals("GPSHPositioningError")) {
                                                                                                                                                                                                                                                                                                                        if (!strArrSplit[1].equals(ExifInterface.TAG_INTEROPERABILITY_INDEX)) {
                                                                                                                                                                                                                                                                                                                            if (!strArrSplit[1].equals("Columns")) {
                                                                                                                                                                                                                                                                                                                                if (!strArrSplit[1].equals("Rows")) {
                                                                                                                                                                                                                                                                                                                                    if (strArrSplit[1].equals(ExifInterface.TAG_IMAGE_UNIQUE_ID)) {
                                                                                                                                                                                                                                                                                                                                        for (int i = 0; i < strSubstring.length() / 2; i++) {
                                                                                                                                                                                                                                                                                                                                            int i2 = i * 2;
                                                                                                                                                                                                                                                                                                                                            byte b = strSubstring.getBytes()[i2];
                                                                                                                                                                                                                                                                                                                                            byte b2 = strSubstring.getBytes()[i2 + 1];
                                                                                                                                                                                                                                                                                                                                            this.exif_primitive.mImageUniqueID[i] = (byte) ((((byte) ((b < 65 || b > 70) ? b + MidiConstants.STATUS_CHANNEL_PRESSURE : b - 55)) << 4) + ((byte) ((b2 < 65 || b2 > 70) ? b2 + MidiConstants.STATUS_CHANNEL_PRESSURE : b2 - 55)));
                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                        this.exif_primitive.mImageUniqueIDExist = true;
                                                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    if (strArrSplit[1].equals(ExifInterface.TAG_DATETIME_DIGITIZED)) {
                                                                                                                                                                                                                                                                                                                                        this.exif_primitive.mDateTimeDigitized = strSubstring;
                                                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                this.exif_primitive.mCFARepeatPatternRows = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                            this.exif_primitive.mCFARepeatPatternRows = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                        this.exif_primitive.mInteroperabilityIndex = strSubstring;
                                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSHPositioningError = strSubstring;
                                                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                this.exif_primitive.mGPSDifferential = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                            this.exif_primitive.mGPSDateStamp = strSubstring;
                                                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                        this.exif_primitive.mGPSAreaInformation = strSubstring;
                                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSProcessingMethod = strSubstring;
                                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                this.exif_primitive.mGPSDestDistance = strSubstring;
                                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                            this.exif_primitive.mGPSDestDistanceRef = strSubstring;
                                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                        this.exif_primitive.mGPSDestBearing = strSubstring;
                                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSDestBearingRef = strSubstring;
                                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                this.exif_primitive.mGPSDestLongitudeRef = strSubstring;
                                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            this.exif_primitive.mGPSDestLatitudeRef = strSubstring;
                                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        this.exif_primitive.mGPSMapDatum = strSubstring;
                                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    this.exif_primitive.mGPSImgDirection = strSubstring;
                                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                this.exif_primitive.mGPSImgDirectionRef = strSubstring;
                                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            this.exif_primitive.mGPSTrack = strSubstring;
                                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                        this.exif_primitive.mGPSTrackRef = strSubstring;
                                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                    this.exif_primitive.mGPSSpeed = strSubstring;
                                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                this.exif_primitive.mGPSSpeedRef = strSubstring;
                                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                            this.exif_primitive.mGPSDOP = strSubstring;
                                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        this.exif_primitive.mGPSMeasureMode = strSubstring;
                                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                    this.exif_primitive.mGPSStatus = strSubstring;
                                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                this.exif_primitive.mGPSSatellites = strSubstring;
                                                                                                                                                                                                                                return;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            this.exif_primitive.mGPSAltitude = strSubstring;
                                                                                                                                                                                                                            return;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        this.exif_primitive.mGPSAltitudeRef = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                                                        return;
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                    this.exif_primitive.mGPSLongitudeRef = strSubstring;
                                                                                                                                                                                                                    return;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                this.exif_primitive.mGPSLatitudeRef = strSubstring;
                                                                                                                                                                                                                return;
                                                                                                                                                                                                            }
                                                                                                                                                                                                            this.exif_primitive.mGPSVersionID = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                                            return;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        this.exif_primitive.mLensSerialNumber = strSubstring;
                                                                                                                                                                                                        return;
                                                                                                                                                                                                    }
                                                                                                                                                                                                    this.exif_primitive.mLensMake = strSubstring;
                                                                                                                                                                                                    return;
                                                                                                                                                                                                }
                                                                                                                                                                                                this.exif_primitive.mSubjectDistanceRange = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                                return;
                                                                                                                                                                                            }
                                                                                                                                                                                            this.exif_primitive.mSharpness = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                            return;
                                                                                                                                                                                        }
                                                                                                                                                                                        this.exif_primitive.mSaturation = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                        return;
                                                                                                                                                                                    }
                                                                                                                                                                                    this.exif_primitive.mContrast = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                    return;
                                                                                                                                                                                }
                                                                                                                                                                                this.exif_primitive.mGainControl = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                                return;
                                                                                                                                                                            }
                                                                                                                                                                            this.exif_primitive.mSceneCaptureType = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                            return;
                                                                                                                                                                        }
                                                                                                                                                                        this.exif_primitive.mFocalLengthIn35mmFilm = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                        return;
                                                                                                                                                                    }
                                                                                                                                                                    this.exif_primitive.mDigitalZoomRatio = strSubstring;
                                                                                                                                                                    return;
                                                                                                                                                                }
                                                                                                                                                                this.exif_primitive.mWhiteBalance = Long.valueOf(strSubstring).longValue();
                                                                                                                                                                return;
                                                                                                                                                            }
                                                                                                                                                            this.exif_primitive.mExposureMode = Long.valueOf(strSubstring).longValue();
                                                                                                                                                            return;
                                                                                                                                                        }
                                                                                                                                                        this.exif_primitive.mCustomRendered = Long.valueOf(strSubstring).longValue();
                                                                                                                                                        return;
                                                                                                                                                    }
                                                                                                                                                    this.exif_primitive.mSceneType = Long.valueOf(strSubstring).longValue();
                                                                                                                                                    return;
                                                                                                                                                }
                                                                                                                                                this.exif_primitive.mFileSource = Long.valueOf(strSubstring).longValue();
                                                                                                                                                return;
                                                                                                                                            }
                                                                                                                                            this.exif_primitive.mSensingMethod = Long.valueOf(strSubstring).longValue();
                                                                                                                                            return;
                                                                                                                                        }
                                                                                                                                        this.exif_primitive.mExposureIndex = strSubstring;
                                                                                                                                        return;
                                                                                                                                    }
                                                                                                                                    this.exif_primitive.mFocalPlaneResolutionUnit = Long.valueOf(strSubstring).longValue();
                                                                                                                                    return;
                                                                                                                                }
                                                                                                                                this.exif_primitive.mFocalPlaneYResolution = strSubstring;
                                                                                                                                return;
                                                                                                                            }
                                                                                                                            this.exif_primitive.mFocalPlaneXResolution = strSubstring;
                                                                                                                            return;
                                                                                                                        }
                                                                                                                        this.exif_primitive.mFocalLength = strSubstring;
                                                                                                                        return;
                                                                                                                    }
                                                                                                                    this.exif_primitive.mFlash = Long.valueOf(strSubstring).longValue();
                                                                                                                    return;
                                                                                                                }
                                                                                                                this.exif_primitive.mLightSource = Long.valueOf(strSubstring).longValue();
                                                                                                                return;
                                                                                                            }
                                                                                                            this.exif_primitive.mMeteringMode = Long.valueOf(strSubstring).longValue();
                                                                                                            return;
                                                                                                        }
                                                                                                        this.exif_primitive.mSubjectDistance = strSubstring;
                                                                                                        return;
                                                                                                    }
                                                                                                    this.exif_primitive.mMaxApertureValue = strSubstring;
                                                                                                    return;
                                                                                                }
                                                                                                this.exif_primitive.mExposureBiasValue = strSubstring;
                                                                                                return;
                                                                                            }
                                                                                            this.exif_primitive.mBrightnessValue = strSubstring;
                                                                                            return;
                                                                                        }
                                                                                        this.exif_primitive.mApertureValue = strSubstring;
                                                                                        return;
                                                                                    }
                                                                                    this.exif_primitive.mShutterSpeedValue = strSubstring;
                                                                                    return;
                                                                                }
                                                                                this.exif_primitive.mISOSpeedLatitudezzz = Long.valueOf(strSubstring).longValue();
                                                                                return;
                                                                            }
                                                                            this.exif_primitive.mISOSpeedLatitudeyyy = Long.valueOf(strSubstring).longValue();
                                                                            return;
                                                                        }
                                                                        this.exif_primitive.mISOSpeed = Long.valueOf(strSubstring).longValue();
                                                                        return;
                                                                    }
                                                                    this.exif_primitive.mRecommendedExposureIndex = Long.valueOf(strSubstring).longValue();
                                                                    return;
                                                                }
                                                                this.exif_primitive.mStandardOutputSensitivity = Long.valueOf(strSubstring).longValue();
                                                                return;
                                                            }
                                                            this.exif_primitive.mSensitivityType = Long.valueOf(strSubstring).longValue();
                                                            return;
                                                        }
                                                        this.exif_primitive.mExposureProgram = Long.valueOf(strSubstring).longValue();
                                                        return;
                                                    }
                                                    this.exif_primitive.mFNumber = strSubstring;
                                                    return;
                                                }
                                                this.exif_primitive.mExposureTime = strSubstring;
                                                return;
                                            }
                                            this.exif_primitive.mDateTimeOriginal = strSubstring;
                                            return;
                                        }
                                        this.exif_primitive.mUserComment = strSubstring;
                                        return;
                                    }
                                    this.exif_primitive.mPixelYDimension = Long.valueOf(strSubstring).longValue();
                                    return;
                                }
                                this.exif_primitive.mPixelXDimension = Long.valueOf(strSubstring).longValue();
                                return;
                            }
                            this.exif_primitive.mComponentsConfiguration = Long.valueOf(strSubstring).longValue();
                            return;
                        }
                        this.exif_primitive.mGamma = strSubstring;
                        return;
                    }
                    this.exif_primitive.mColorSpace = Long.valueOf(strSubstring).longValue();
                    return;
                }
                this.exif_primitive.mFlashPixVersion = Long.valueOf(strSubstring).longValue();
                return;
            }
            this.exif_primitive.mExifVersion = Long.valueOf(strSubstring).longValue();
            return;
        }
        if (strArrSplit[0].compareTo("exifEX") == 0) {
            if (!strArrSplit[1].equals("CameraOwnerName")) {
                if (!strArrSplit[1].equals("BodySerialNumber")) {
                    if (strArrSplit[1].equals("LensModel")) {
                        this.exif_primitive.mLensName = strSubstring;
                        return;
                    }
                    return;
                }
                this.exif_primitive.mCameraSerialNumber = strSubstring;
                return;
            }
            this.exif_primitive.mOwnerName = strSubstring;
        }
    }

    public boolean setDataArrayToExif(String str, String[] strArr) {
        String[] strArrSplit = str.split(":");
        if (strArrSplit[0].compareTo("exif") != 0) {
            return false;
        }
        if (strArrSplit[1].equals("ISOSpeedRatings")) {
            this.exif_primitive.mISOSpeedRatings0 = strArr.length >= 1 ? Long.valueOf(strArr[0]).longValue() : 0L;
            this.exif_primitive.mISOSpeedRatings1 = strArr.length >= 2 ? Long.valueOf(strArr[1]).longValue() : 0L;
            this.exif_primitive.mISOSpeedRatings2 = strArr.length >= 3 ? Long.valueOf(strArr[2]).longValue() : 0L;
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_SUBJECT_AREA)) {
            this.exif_primitive.mSubjectArea0 = strArr.length >= 1 ? Long.valueOf(strArr[0]).longValue() : 0L;
            this.exif_primitive.mSubjectArea1 = strArr.length >= 2 ? Long.valueOf(strArr[1]).longValue() : 0L;
            this.exif_primitive.mSubjectArea2 = strArr.length >= 3 ? Long.valueOf(strArr[2]).longValue() : 0L;
            this.exif_primitive.mSubjectArea3 = strArr.length >= 4 ? Long.valueOf(strArr[3]).longValue() : 0L;
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_CFA_PATTERN)) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                this.exif_primitive.mCFAPattern[i] = Byte.valueOf(strArr[i]).byteValue();
            }
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_GPS_LATITUDE)) {
            this.exif_primitive.mGPSLatitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSLatitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSLatitude2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_GPS_LONGITUDE)) {
            this.exif_primitive.mGPSLongitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSLongitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSLongitude2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_GPS_TIMESTAMP)) {
            this.exif_primitive.mGPSTimeStamp0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSTimeStamp1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSTimeStamp2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_LATITUDE)) {
            this.exif_primitive.mGPSDestLatitude0 = strArr.length >= 1 ? strArr[0] : null;
            this.exif_primitive.mGPSDestLatitude1 = strArr.length >= 2 ? strArr[1] : null;
            this.exif_primitive.mGPSDestLatitude2 = strArr.length >= 3 ? strArr[2] : null;
            return true;
        }
        if (strArrSplit[1].equals(ExifInterface.TAG_GPS_DEST_LONGITUDE)) {
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
