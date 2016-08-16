package piyush.almanac.cs3yr_faculty;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;

public class Data {

    public static String getName(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            return jsonObject1.getString("name");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getEmail(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            return jsonObject1.getString("email");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSummary(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            return jsonObject1.getString("summary");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDesignation(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            return jsonObject1.getString("designation");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPhone(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            return jsonObject1.getString("phone");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMacid(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachdetails");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            return jsonObject1.getString("macid");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getTeacherName(Context context)
    {

        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");

            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static JSONArray getTeacherEmail(Context context)
    {

        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");





            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getTeacherPhone(Context context)
    {
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");

            return jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getSections(Context context)
    {
        String sec;
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("sections");
            return  jsonArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }




    public static boolean isNetworkAvailable(Activity context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    public static Bitmap getSavedImage(Context context, String name){
        name=name+".JPG";
        try{
            FileInputStream fis = context.openFileInput(name);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
