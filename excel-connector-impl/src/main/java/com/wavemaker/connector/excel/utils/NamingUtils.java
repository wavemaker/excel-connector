package com.wavemaker.connector.excel.utils;

import com.wavemaker.commons.util.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.beans.Introspector;

public class NamingUtils {

    /**
     * Converts a database name (table or column) to a java name.
     * eg,Hello_World : helloWorld
     * eq,HelloWorld : helloWorld
     *
     * @return The converted java identifier.
     */
    public static String toJavaIdentifier(final String identifier) {
        String rtn = columnToPropertyName(identifier);
        int upperIndex = 1;
        if (rtn.length() > 1 && Character.isUpperCase(rtn.charAt(1))) {
            upperIndex = 2;
        }
        rtn = rtn.substring(0, upperIndex).toLowerCase() + rtn.substring(upperIndex);
        return StringUtils.toJavaIdentifier(rtn);
    }

    private static String columnToPropertyName(String identifier) {
        String decapitalize = Introspector.decapitalize(ReverseEngineeringStrategyUtil.toUpperCamelCase(identifier));
        return keywordCheck(decapitalize);
    }

    private static String keywordCheck(String possibleKeyword) {
        if (ReverseEngineeringStrategyUtil.isReservedJavaKeyword(possibleKeyword)) {
            possibleKeyword = possibleKeyword + "_";
        }
        return possibleKeyword;
    }
}
