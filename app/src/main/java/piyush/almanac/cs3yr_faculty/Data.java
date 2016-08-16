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

    public static String getTeacherName(int pos,Context context)
    {
         String name;
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");

                JSONObject jsonObject1 = jsonArray.getJSONObject(pos);
                name=jsonObject1.getString("tname");

            return name;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static String getTeacherEmail(int pos,Context context)
    {
        String email;
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");

                JSONObject jsonObject1 = jsonArray.getJSONObject(pos);

                    email= jsonObject1.getString("temail");

            return email;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTeacherPhone(int pos,Context context)
    {
        String phone;
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");

                JSONObject jsonObject1 = jsonArray.getJSONObject(pos);

                   phone= jsonObject1.getString("phone");

            return phone;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSections(Context context,int pos)
    {
        String sec;
        try {
            JSONObject jsonObject = new JSONObject(new LoadContent().getStringFromJson(context));
            JSONArray jsonArray = jsonObject.getJSONArray("sections");
            JSONObject jsonObject1 = jsonArray.getJSONObject(pos);
            sec= jsonObject1.getString("section");
            return  sec;
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
