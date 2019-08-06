package p32929.myhouseads2lib;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by p32929 on 10/14/2018.
 */

public class FayazSP {
    public static SharedPreferences sharedPreferences;
    private static Context context;

    //
    public static SharedPreferences init(Context context) {
        FayazSP.context = context;
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("MyAds", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    //
    public static void put(String title, boolean value) {
        sharedPreferences.edit().putBoolean(title, value).apply();
    }

    public static void put(String title, float value) {
        sharedPreferences.edit().putFloat(title, value).apply();
    }

    public static void put(String title, int value) {
        sharedPreferences.edit().putInt(title, value).apply();
    }

    public static void put(String title, long value) {
        sharedPreferences.edit().putLong(title, value).apply();
    }

    public static void put(String title, String value) {
        sharedPreferences.edit().putString(title, value).apply();
    }


    //
    public static boolean getBoolean(String title, boolean defaultValue) {
        return sharedPreferences.getBoolean(title, defaultValue);
    }

    public static float getFloat(String title, float defaultValue) {
        return sharedPreferences.getFloat(title, defaultValue);
    }

    public static int getInt(String title, int defaultValue) {
        return sharedPreferences.getInt(title, defaultValue);
    }

    public static long getLong(String title, long defaultValue) {
        return sharedPreferences.getLong(title, defaultValue);
    }

    public static String getString(String title, String defaultValue) {
        return sharedPreferences.getString(title, defaultValue);
    }

    //
    public static void storeBoolArray(boolean[] array, String arrayName, Context mContext) {
        FayazSP.init(mContext);
        FayazSP.put(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            FayazSP.put(arrayName + "_" + i, array[i]);
    }

    public static boolean[] loadBoolArray(String arrayName, Context mContext) {
        FayazSP.init(mContext);
        int size = FayazSP.getInt(arrayName + "_size", 0);
        boolean[] array = new boolean[size];
        for (int i = 0; i < size; i++)
            array[i] = FayazSP.getBoolean(arrayName + "_" + i, false);

        return array;
    }

    //
    public static void storeStringArray(String[] array, String arrayName, Context mContext) {
        FayazSP.init(mContext);
        FayazSP.put(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            FayazSP.put(arrayName + "_" + i, array[i]);
    }

    public static String[] loadStringArray(String arrayName, Context mContext) {
        FayazSP.init(mContext);
        int size = FayazSP.getInt(arrayName + "_size", 0);
        String[] array = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = FayazSP.getString(arrayName + "_" + i, "");

        return array;
    }

    //
    public static void clearAll() {
        sharedPreferences.edit().clear().commit();
    }

}
