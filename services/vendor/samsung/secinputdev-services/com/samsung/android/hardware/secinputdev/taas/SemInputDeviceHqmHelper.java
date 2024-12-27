package com.samsung.android.hardware.secinputdev.taas;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SemInputDeviceHqmHelper {
    private static final String[] BIG_DATA = {"CASA", "CASB"};
    private static final int HQMCASEALISTITEMCOUNT = 5;
    private static final int HQMCASEALISTLENGTH = 1000;
    private static final String TAG = "SemInputDeviceHqmHelper";

    private String printTopCount(SemInputDeviceHqmData data) {
        String strCaseAHqmList;
        String strListResult = "";
        Map<String, Integer> caseAMap = data.getCaseAMap();
        Map<String, Integer> sortedMap =
                (Map)
                        caseAMap.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue().reversed())
                                .limit(5L)
                                .collect(
                                        Collectors.toMap(
                                                new Function() { // from class:
                                                                 // com.samsung.android.hardware.secinputdev.taas.SemInputDeviceHqmHelper$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return (String) ((Map.Entry) obj).getKey();
                                                    }
                                                },
                                                new Function() { // from class:
                                                                 // com.samsung.android.hardware.secinputdev.taas.SemInputDeviceHqmHelper$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        return (Integer)
                                                                ((Map.Entry) obj).getValue();
                                                    }
                                                },
                                                new BinaryOperator() { // from class:
                                                                       // com.samsung.android.hardware.secinputdev.taas.SemInputDeviceHqmHelper$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(
                                                            Object obj, Object obj2) {
                                                        return SemInputDeviceHqmHelper
                                                                .lambda$printTopCount$0(
                                                                        (Integer) obj,
                                                                        (Integer) obj2);
                                                    }
                                                },
                                                new Supplier() { // from class:
                                                                 // com.samsung.android.hardware.secinputdev.taas.SemInputDeviceHqmHelper$$ExternalSyntheticLambda3
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        return new LinkedHashMap();
                                                    }
                                                }));
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            strListResult = strListResult + entry.getValue() + "," + entry.getKey() + ";";
        }
        if (strListResult == null || strListResult.isEmpty()) {
            strCaseAHqmList = "\"CSAL\":\"\"";
        } else {
            String strListResult2 = strListResult.replaceAll(".$", "");
            strCaseAHqmList =
                    "\"CSAL\":\""
                            + strListResult2.substring(0, Math.min(strListResult2.length(), 998))
                            + "\"";
        }
        Log.d(TAG, "Print HQM DATA strCaseAHqmList(" + strCaseAHqmList + ")");
        return strCaseAHqmList;
    }

    static /* synthetic */ Integer lambda$printTopCount$0(Integer e1, Integer e2) {
        return e1;
    }

    public String sendHqmTspData(SemInputDeviceHqmData data) {
        ArrayList<String> types = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < BIG_DATA.length; i++) {
            String strVal = Integer.toString(data.get(BIG_DATA[i]));
            if (strVal != null) {
                types.add(BIG_DATA[i]);
                values.add(strVal);
            }
        }
        return sendLoggingDataToHQM(types, values, data);
    }

    private String convertToBigDataFormat(ArrayList<String> types, ArrayList<String> values) {
        int size = types.size();
        try {
            JSONObject obj = new JSONObject();
            for (int i = 0; i < size; i++) {
                obj.put(types.get(i), values.get(i));
            }
            return obj.toString();
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(TAG, "convertToBigDataFormat", e);
            return null;
        }
    }

    private String sendLoggingDataToHQM(
            ArrayList<String> types, ArrayList<String> values, SemInputDeviceHqmData data) {
        String basic_customDataSet = convertToBigDataFormat(types, values);
        if (basic_customDataSet == null) {
            return "null";
        }
        String strHqmResultVal =
                basic_customDataSet.replaceAll("\\{", "").replaceAll("\\}", "")
                        + ","
                        + printTopCount(data);
        return strHqmResultVal;
    }
}
